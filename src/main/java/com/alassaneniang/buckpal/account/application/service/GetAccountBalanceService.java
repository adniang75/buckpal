package com.alassaneniang.buckpal.account.application.service;

import com.alassaneniang.buckpal.account.application.port.incoming.GetAccountBalanceUseCase;
import com.alassaneniang.buckpal.account.application.port.outgoing.LoadAccountPort;
import com.alassaneniang.buckpal.account.domain.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(GetAccountBalanceQuery query) {
        return loadAccountPort
                .loadAccount(query.accountId(), LocalDateTime.now())
                .calculateBalance();
    }

}
