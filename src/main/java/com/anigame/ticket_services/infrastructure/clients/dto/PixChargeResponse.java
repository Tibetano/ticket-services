package com.anigame.ticket_services.infrastructure.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PixChargeResponse {

    private String txid;

    private LocationInfo loc;

    private String location;

    private String status;

    @JsonProperty("pixCopiaECola")
    private String pixCopyAndPaste;

    @JsonProperty("valor")
    private Amount amount;

    @Data
    public static class LocationInfo {
        private Long id;
    }

    @Data
    public static class Amount {
        @JsonProperty("original")
        private String originalAmount;
    }
}
