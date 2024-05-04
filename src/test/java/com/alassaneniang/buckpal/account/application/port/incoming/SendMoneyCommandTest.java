package com.alassaneniang.buckpal.account.application.port.incoming;

import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Money;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class SendMoneyCommandTest {

    @Test
    void validationOk() {
        new SendMoneyCommand(
                new Account.AccountId(42L),
                new Account.AccountId(43L),
                new Money(new BigInteger("10")));
        // no exception
    }

    @Test
    void moneyValidationFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            new SendMoneyCommand(
                    new Account.AccountId(42L),
                    new Account.AccountId(43L),
                    new Money(new BigInteger("-10")));
        });
    }

    @Test
    void accountIdValidationFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            new SendMoneyCommand(
                    new Account.AccountId(42L),
                    null,
                    new Money(new BigInteger("10")));
        });
    }

}