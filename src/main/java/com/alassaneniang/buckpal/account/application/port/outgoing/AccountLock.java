package com.alassaneniang.buckpal.account.application.port.outgoing;

import com.alassaneniang.buckpal.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
