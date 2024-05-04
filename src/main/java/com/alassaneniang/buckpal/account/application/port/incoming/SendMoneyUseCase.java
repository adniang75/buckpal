package com.alassaneniang.buckpal.account.application.port.incoming;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand command);

}
