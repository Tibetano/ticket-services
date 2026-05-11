package com.anigame.ticket_services.usecase.webhook.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayerDTO(
        @JsonProperty("nome")
        String name,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("cnpj")
        String cnpj,

        @JsonProperty("codigoBanco")
        String bankCode,

        @JsonProperty("contaBanco")
        BankAccountDTO bankAccount
) {
}
