package com.anigame.ticket_services.infrastructure.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EfiCreditCardBearerTokenInterceptor
        implements RequestInterceptor {

    private final EfiCreditCardTokenService tokenService;

    @Override
    public void apply(RequestTemplate template) {

        if (template.headers().containsKey("Authorization")) {
            return;
        }

        template.header(
                "Authorization",
                "Bearer " + tokenService.getAccessToken()
        );
    }
}
