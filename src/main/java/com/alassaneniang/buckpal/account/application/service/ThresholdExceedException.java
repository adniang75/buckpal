package com.alassaneniang.buckpal.account.application.service;

import com.alassaneniang.buckpal.account.domain.Money;

public class ThresholdExceedException extends RuntimeException {

    public ThresholdExceedException(Money threshold, Money actual) {
        super(String.format(
                "Maximum threshold for transferring money exceed: tried to transfer %s but threshold is %s!",
                actual, threshold));
    }

}
