package com.alassaneniang.buckpal.account.domain;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * A money transfer activity between {@link Account}s
 */
@Getter
@Value
@RequiredArgsConstructor
@Accessors(fluent = true)
public class Activity {

    ActivityId id;

    /**
     * The account that owns this activity
     */
    @NonNull
    Account.AccountId ownerAccountId;

    /**
     * The debited account
     */
    @NonNull
    Account.AccountId sourceAccountId;

    /**
     * The credited account
     */
    @NonNull
    Account.AccountId targetAccountId;

    /**
     * The timestamp of the activity
     */
    @NonNull
    LocalDateTime timestamp;

    /**
     * The money that was transferred between the accounts
     */
    @NonNull
    Money money;

    public Activity(
            @NonNull Account.AccountId ownerAccountId,
            @NonNull Account.AccountId sourceAccountId,
            @NonNull Account.AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public record ActivityId(Long value) {

    }

}
