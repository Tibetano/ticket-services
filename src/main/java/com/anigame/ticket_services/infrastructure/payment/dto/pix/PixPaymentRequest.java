package com.anigame.ticket_services.infrastructure.payment.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PixPaymentRequest(
        @JsonProperty("calendario")
        Calendar calendar,
        @JsonProperty("devedor")
        Debtor debtor,
        @JsonProperty("valor")
        Amount amount,
        @JsonProperty("chave")
        String key
) {

    public static PixPaymentRequest from(
            String fullName,
            String cpf,
            Integer expirationTime,
            String originalAmount,
            String pixKey
    ) {

        Calendar calendar = new Calendar(expirationTime);
        Debtor debtor = new Debtor(cpf, fullName);
        Amount amount = new Amount(originalAmount);

        return new PixPaymentRequest(
                calendar,
                debtor,
                amount,
                pixKey
        );
    }

}
