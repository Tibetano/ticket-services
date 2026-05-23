package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.config.EfiPayFeignConfig;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.creditcard.CreditCardPaymentRequest;
import com.anigame.ticket_services.usecase.webhook.dto.charge.EfiTransactionStatusResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        contextId = "efiCreditCardClient",
        name = "efi-credit-card",
        url = "${efi.credit-card-base-url}",
        configuration = EfiPayFeignConfig.class
)
public interface EfiPayClient {

    @PostMapping("/v1/charge/one-step")
    CardResponse createCharge(
            @RequestBody CreditCardPaymentRequest body
    );

    @GetMapping("/v1/notification/{token}")
    EfiTransactionStatusResponseDTO getNotification (@PathVariable String token);

}
