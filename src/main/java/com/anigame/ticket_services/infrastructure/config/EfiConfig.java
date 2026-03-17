package com.anigame.ticket_services.infrastructure.config;

import br.com.efi.efisdk.EfiPay;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Configuration
@RequiredArgsConstructor
public class EfiConfig {

    private final EfiProperties props;

    /*
    @Bean
    public EfiPay efiPay () throws Exception {
        URL certUrl = getClass().getClassLoader()
                .getResource(props.getCertificatePath());

        if (certUrl == null) {
            throw new IllegalStateException("Certificado EFI não encontrado.");
        }

        JSONObject options = new JSONObject();
        options.put("client_id",props.getClientId());
        options.put("client_secret",props.getClientSecret());
        options.put("certificate", new File(certUrl.toURI()).getAbsolutePath());
        options.put("sandbox",props.isSandbox());

        return new EfiPay(options);
    }
    */

    @Bean
    public EfiPay efiPay () throws Exception {

        InputStream certStream = getClass()
                .getClassLoader()
                .getResourceAsStream(props.getCertificatePath());

        if (certStream == null) {
            throw new RuntimeException("Certificado não encontrado");
        }

        Path tempFile = Files.createTempFile("cert", ".p12");
        Files.copy(certStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        JSONObject options = new JSONObject();
        options.put("client_id",props.getClientId());
        options.put("client_secret",props.getClientSecret());
        options.put("certificate", tempFile.toAbsolutePath().toString());
        options.put("sandbox",props.isSandbox());

        return new EfiPay(options);
    }

}
