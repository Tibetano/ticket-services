package com.anigame.ticket_services.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "efi")
@Getter
@Setter
public class EfiProperties {
    private String clientId;
    private String clientSecret;
    private String certificatePath;
    private boolean sandbox;
    private String pixKey;

    private String originalTicketPrice;
    private Integer QRCodeExpirationTime;
}
