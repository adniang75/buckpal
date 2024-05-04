package com.alassaneniang.buckpal.account.common;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseCase {

    String value() default "";

}
