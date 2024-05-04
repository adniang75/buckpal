package com.alassaneniang.buckpal.account.application.port.incoming;

import com.alassaneniang.buckpal.account.domain.Account.AccountId;
import com.alassaneniang.buckpal.account.domain.Money;

public interface GetAccountBalanceUseCase {

    Money getAccountBalance(GetAccountBalanceQuery query);

    record GetAccountBalanceQuery(AccountId accountId) {
        
    }

}
