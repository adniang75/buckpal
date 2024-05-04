package com.alassaneniang.buckpal.account.common;

import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.ActivityWindow;
import com.alassaneniang.buckpal.account.domain.Money;

import static com.alassaneniang.buckpal.account.common.ActivityTestData.defaultActivity;

public class AccountTestData {

    public static AccountBuilder defaultAccount() {
        return new AccountBuilder()
                .withAccountId(new Account.AccountId(42L))
                .withBaselineBalance(Money.of(999L))
                .withActivityWindow(new ActivityWindow(
                        defaultActivity().build(),
                        defaultActivity().build()));
    }


    public static class AccountBuilder {

        private Account.AccountId accountId;
        private Money baselineBalance;
        private ActivityWindow activityWindow;

        public AccountBuilder withAccountId(Account.AccountId accountId) {
            this.accountId = accountId;
            return this;
        }

        public AccountBuilder withBaselineBalance(Money baselineBalance) {
            this.baselineBalance = baselineBalance;
            return this;
        }

        public AccountBuilder withActivityWindow(ActivityWindow activityWindow) {
            this.activityWindow = activityWindow;
            return this;
        }

        public Account build() {
            return Account.withId(this.accountId, this.baselineBalance, this.activityWindow);
        }

    }

}
