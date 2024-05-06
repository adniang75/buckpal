package com.alassaneniang.buckpal.account.application.service;

import com.alassaneniang.buckpal.account.application.port.incoming.SendMoneyCommand;
import com.alassaneniang.buckpal.account.application.port.incoming.SendMoneyUseCase;
import com.alassaneniang.buckpal.account.application.port.outgoing.AccountLock;
import com.alassaneniang.buckpal.account.application.port.outgoing.LoadAccountPort;
import com.alassaneniang.buckpal.account.application.port.outgoing.UpdateAccountStatePort;
import com.alassaneniang.buckpal.account.common.UseCase;
import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Account.AccountId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@UseCase
class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {

        checkThreshold(command);

        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(
                command.sourceAccountId(),
                baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(
                command.targetAccountId(),
                baselineDate);

        AccountId sourceAccountId = sourceAccount.id()
                .orElseThrow(() -> new IllegalArgumentException("expected source account ID not to be empty"));

        AccountId targetAccountId = targetAccount.id()
                .orElseThrow(() -> new IllegalArgumentException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.money(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);

        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw new ThresholdExceedException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
        }
    }

}
