package com.alassaneniang.buckpal.account.application.port.incoming;

import com.alassaneniang.buckpal.account.domain.Money;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveMoneyValidator.class)
@Documented
public @interface PositiveMoney {

    String message() default "must be positive " +
            "found: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Money>[] playload() default {};

}
