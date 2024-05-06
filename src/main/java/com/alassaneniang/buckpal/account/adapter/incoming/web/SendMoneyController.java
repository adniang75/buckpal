package com.alassaneniang.buckpal.account.adapter.incoming.web;

import com.alassaneniang.buckpal.account.application.port.incoming.SendMoneyCommand;
import com.alassaneniang.buckpal.account.application.port.incoming.SendMoneyUseCase;
import com.alassaneniang.buckpal.account.common.WebAdapter;
import com.alassaneniang.buckpal.account.domain.Account;
import com.alassaneniang.buckpal.account.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    ResponseEntity<?> sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount) {
        try {
            // validate inputs
            if (sourceAccountId == null || targetAccountId == null || amount == null || amount < 0) {
                return ResponseEntity.badRequest()
                        .body("Invalid input parameters");
            }

            SendMoneyCommand command = new SendMoneyCommand(
                    new Account.AccountId(sourceAccountId),
                    new Account.AccountId(targetAccountId),
                    Money.of(amount));
            boolean result = sendMoneyUseCase.sendMoney(command);
            if (result) {
                return ResponseEntity.ok("Money sent successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unable to send Money due to internal error");
            }
        } catch (Exception exception) {
            // log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + exception.getMessage());
        }
    }

}
