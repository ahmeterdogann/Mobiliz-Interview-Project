package com.ahmeterdogan.filter;

import com.ahmeterdogan.dto.request.GeneralRequestHeaderDto;
import com.ahmeterdogan.feign.IAuthServiceFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {
    private final IAuthServiceFeign authServiceFeign;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(IAuthServiceFeign authServiceFeign, ObjectMapper objectMapper) {
        this.authServiceFeign = authServiceFeign;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String username = headers.getFirst("username");
        String password = headers.getFirst("password");

        GeneralRequestHeaderDto authDTO = authServiceFeign.login(username, password);

        String jsonAuthDTO = null;
        try {
            jsonAuthDTO = objectMapper.writeValueAsString(authDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        exchange.getRequest().mutate().header("X-User", jsonAuthDTO);

        return chain.filter(exchange);
    }
}