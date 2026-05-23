package com.anigame.ticket_services.infrastructure.clients.dto;

import lombok.Data;

@Data
public class PixChargeRequest {

    private Calendario calendario;
    private Devedor devedor;
    private Valor valor;
    private String chave;
    private String solicitacaoPagador;

    @Data
    public static class Calendario {
        private Integer expiracao;
    }

    @Data
    public static class Devedor {
        private String cpf;
        private String nome;
    }

    @Data
    public static class Valor {
        private String original;
    }
}
