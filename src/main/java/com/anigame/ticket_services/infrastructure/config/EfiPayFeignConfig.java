package com.anigame.ticket_services.infrastructure.config;

import com.anigame.ticket_services.infrastructure.security.EfiCreditCardBearerTokenInterceptor;
import com.anigame.ticket_services.infrastructure.security.EfiCreditCardTokenService;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EfiPayFeignConfig {

    private final EfiCreditCardTokenService tokenService;

    @Bean
    public RequestInterceptor creditCardRequestInterceptor() {

        return new EfiCreditCardBearerTokenInterceptor(tokenService);
    }
}
