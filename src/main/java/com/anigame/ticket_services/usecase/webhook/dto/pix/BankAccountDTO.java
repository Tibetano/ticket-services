package com.anigame.ticket_services.usecase.webhook.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BankAccountDTO(
        @JsonProperty("codigoBanco")
        String bankCode,

        @JsonProperty("agencia")
        String branch,

        @JsonProperty("conta")
        String account,

        @JsonProperty("tipoConta")
        String accountType
) {
}
