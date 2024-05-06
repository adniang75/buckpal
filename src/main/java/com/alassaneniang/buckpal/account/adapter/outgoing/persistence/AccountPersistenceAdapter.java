package com.alassaneniang.buckpal.account.adapter.outgoing.persistence;

import com.alassaneniang.buckpal.account.application.port.outgoing.LoadAccountPort;
import com.alassaneniang.buckpal.account.application.port.outgoing.UpdateAccountStatePort;
import com.alassaneniang.buckpal.account.common.PersistenceAdapter;
import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Account.AccountId;
import com.alassaneniang.buckpal.account.domain.Activity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;


    @Override
    public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account =
                accountRepository
                        .findById(accountId.value())
                        .orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities =
                activityRepository
                        .findByOwnerSince(accountId.value(), baselineDate);
        Long withdrawalBalance =
                activityRepository
                        .getWithdrawalBalanceUntil(accountId.value(), baselineDate)
                        .orElse(0L);
        Long depositBalance =
                activityRepository
                        .getDepositBalanceUntil(accountId.value(), baselineDate)
                        .orElse(0L);
        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.activityWindow().activities()) {
            if (activity.id() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}
