package com.nedra.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebFluxSecurity


public class SecurityConfig {
    @Bean
public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
    serverHttpSecurity
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200")); // Replace with your Angular frontend URL
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
                return corsConfiguration;
            }))
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                    .pathMatchers("/eureka/**")
                    .permitAll()
                    .anyExchange()
                    .authenticated()
            )
            .oauth2ResourceServer(auth -> auth.jwt(token ->
                    token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())
            ));

    return serverHttpSecurity.build();
}

}

   /* @Bean
    public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity

                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(auth ->auth.jwt(token -> token.jwtAuthenticationConverter
                        (new KeycloakJwtAuthenticationConverter())));

        //oauth2-> oauth2.jwt(Customizer.withDefaults()));
        return serverHttpSecurity.build();

    }*/



