package com.alassaneniang.buckpal.account.adapter.outgoing.persistence;

import com.alassaneniang.buckpal.account.application.port.outgoing.AccountLock;
import com.alassaneniang.buckpal.account.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class NoOpsAccountLock implements AccountLock {


    @Override
    public void lockAccount(Account.AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(Account.AccountId accountId) {
        // do nothing
    }

}
