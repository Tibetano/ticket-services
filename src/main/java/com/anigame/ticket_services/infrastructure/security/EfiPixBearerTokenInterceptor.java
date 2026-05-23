package com.anigame.ticket_services.infrastructure.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EfiPixBearerTokenInterceptor
        implements RequestInterceptor {

    private final EfiPixTokenService tokenService;

    @Override
    public void apply(RequestTemplate template) {


        String token = tokenService.getAccessToken();
        //log.info("Feign URL: {}", template.url());
        //log.info("Token enviado: {}", token);

        // Se já existe Authorization, não sobrescreve
        if (template.headers().containsKey("Authorization")) {
            return;
        }

        template.header(
                "Authorization",
                "Bearer " + tokenService.getAccessToken()
        );
    }
}
