package com.alassaneniang.buckpal.account.application.port.incoming;

import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Money;
import jakarta.validation.constraints.NotNull;

import static com.alassaneniang.buckpal.account.common.validation.Validation.validate;

public record SendMoneyCommand(
        @NotNull Account.AccountId sourceAccountId,
        @NotNull Account.AccountId targetAccountId,
        @NotNull @PositiveMoney Money money) {

    public SendMoneyCommand(
            Account.AccountId sourceAccountId,
            Account.AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        validate(this);
    }

}
