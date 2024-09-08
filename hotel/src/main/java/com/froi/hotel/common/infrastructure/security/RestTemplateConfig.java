package com.froi.hotel.common.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class RestTemplateConfig {

    /**
     * This specialized version of the Spring RestTemplate supports
     * forwarding the authorization token to the target service for
     * the request. If the current session is not authenticated, no
     * token will be used.
     */
    @Bean
    @RequestScope
    public RestTemplate keycloakRestTemplate(HttpServletRequest inReq) {
        // retrieve the auth header from incoming request
        final String authHeader =
                inReq.getHeader(HttpHeaders.AUTHORIZATION);
        final RestTemplate restTemplate = new RestTemplate();
        // add a token if an incoming auth header exists, only
        if (authHeader != null && !authHeader.isEmpty()) {
            // since the header should be added to each outgoing request,
            // add an interceptor that handles this.
            restTemplate.getInterceptors().add(
                    (outReq, bytes, clientHttpReqExec) -> {
                        outReq.getHeaders().set(
                                HttpHeaders.AUTHORIZATION, authHeader
                        );
                        return clientHttpReqExec.execute(outReq, bytes);
                    });
        }
        return restTemplate;
    }
}