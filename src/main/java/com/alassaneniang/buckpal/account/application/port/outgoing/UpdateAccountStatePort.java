package com.alassaneniang.buckpal.account.application.port.outgoing;

import com.alassaneniang.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
