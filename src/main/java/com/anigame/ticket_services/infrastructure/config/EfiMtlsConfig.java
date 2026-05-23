package com.anigame.ticket_services.infrastructure.config;

import feign.Client;
import feign.hc5.ApacheHttp5Client;

import lombok.RequiredArgsConstructor;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;


@Configuration
@RequiredArgsConstructor
public class EfiMtlsConfig {

    private final EfiProperties properties;

    @Bean
    public Client feignClient() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (
                InputStream is = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(
                                properties.getCertificatePath()
                        )
        ) {

            if (is == null) {
                throw new RuntimeException(
                        "Certificado não encontrado: "
                                + properties.getCertificatePath()
                );
            }

            keyStore.load(
                    is,
                    properties.getCertificatePassword().toCharArray()
            );
        }

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(
                        keyStore,
                        properties.getCertificatePassword().toCharArray()
                )
                .build();

        SSLConnectionSocketFactory socketFactory =
                new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(
                        PoolingHttpClientConnectionManagerBuilder.create()
                                .setSSLSocketFactory(socketFactory)
                                .build()
                )
                .build();

        return new ApacheHttp5Client(httpClient);
    }

}
