package com.alassaneniang.buckpal.account.application.port.outgoing;

import com.alassaneniang.buckpal.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);

}
