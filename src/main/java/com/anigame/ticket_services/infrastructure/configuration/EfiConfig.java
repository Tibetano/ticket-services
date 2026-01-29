package com.anigame.ticket_services.infrastructure.configuration;

import br.com.efi.efisdk.EfiPay;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;

@Configuration
@RequiredArgsConstructor
public class EfiConfig {

    private final EfiProperties props;

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

}
