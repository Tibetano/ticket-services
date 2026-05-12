package com.anigame.ticket_services.infrastructure.config;

import br.com.efi.efisdk.EfiPay;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

    private Long creditCardFee;
    private Long pixFee;

    private String chargeNotificationUrl;

    private String originalTicketPrice;
    private Integer QRCodeExpirationTime;

    public JSONObject getGatewayProps () throws Exception {

        InputStream certStream = getClass()
                .getClassLoader()
                .getResourceAsStream(this.certificatePath);

        if (certStream == null) {
            throw new RuntimeException("Certificado não encontrado");
        }

        Path tempFile = Files.createTempFile("cert", ".p12");
        Files.copy(certStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        JSONObject options = new JSONObject();
        options.put("client_id",this.clientId);
        options.put("client_secret",this.clientSecret);
        options.put("certificate", tempFile.toAbsolutePath().toString());
        options.put("sandbox",this.sandbox);

        return options;
    }
}
