package com.anigame.ticket_services.infrastructure.config;


import com.anigame.ticket_services.infrastructure.security.EfiPixTokenService;
import com.anigame.ticket_services.infrastructure.security.EfiPixBearerTokenInterceptor;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EfiPayPixFeignConfig {

    /*private final EfiBearerTokenInterceptor interceptor;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return interceptor;
    }*/

    private final EfiPixTokenService tokenService;

    @Bean
    public RequestInterceptor pixRequestInterceptor() {

        return new EfiPixBearerTokenInterceptor(tokenService);
    }

}
