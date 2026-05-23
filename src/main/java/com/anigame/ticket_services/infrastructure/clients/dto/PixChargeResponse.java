package com.anigame.ticket_services.infrastructure.clients.dto;

import lombok.Data;

@Data
public class PixChargeResponse {

    private String txid;

    private LocationInfo loc;

    private String location;

    private String status;

    private String pixCopiaECola;

    @Data
    public static class LocationInfo {
        private Long id;
    }
}
