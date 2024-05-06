package com.alassaneniang.buckpal.account.adapter.outgoing.persistence;

import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Account.AccountId;
import com.alassaneniang.buckpal.account.domain.Activity;
import com.alassaneniang.buckpal.account.domain.Activity.ActivityId;
import com.alassaneniang.buckpal.account.domain.ActivityWindow;
import com.alassaneniang.buckpal.account.domain.Money;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

    Account mapToDomainEntity(
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance) {
        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));
        return Account.withId(
                new AccountId(account.getId()),
                baselineBalance,
                mapToActivityWindow(activities)
        );
    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = activities.stream()
                .map(a -> new Activity(
                        new ActivityId(a.getId()),
                        new AccountId(a.getOwnerAccountId()),
                        new AccountId(a.getSourceAccountId()),
                        new AccountId(a.getTargetAccountId()),
                        a.getTimestamp(),
                        Money.of(a.getAmount())))
                .toList();
        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.id() == null ? null : activity.id().value(),
                activity.timestamp(),
                activity.ownerAccountId().value(),
                activity.sourceAccountId().value(),
                activity.targetAccountId().value(),
                activity.money().amount().longValue());
    }

}
