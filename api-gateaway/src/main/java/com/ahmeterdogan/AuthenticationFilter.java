package com.ahmeterdogan;

import com.ahmeterdogan.dto.request.UserAuthDTO;
import com.ahmeterdogan.feign.IAuthServiceFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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
        UserAuthDTO authDTO = authServiceFeign.login("ahmeterdogann", "ahmet1903");

        // JSON verisini dönüştürüp alıyoruz
        String jsonAuthDTO = null;
        try {
            jsonAuthDTO = objectMapper.writeValueAsString(authDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // X-User başlığını değiştiriyoruz
        exchange.getRequest().mutate().header("X-User", jsonAuthDTO);

        return chain.filter(exchange);
    }
}