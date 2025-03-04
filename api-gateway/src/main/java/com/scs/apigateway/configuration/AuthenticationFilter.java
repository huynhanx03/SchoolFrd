package com.scs.apigateway.configuration;

import com.scs.apigateway.dto.response.ApiResponse;
import com.scs.apigateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scs.apigateway.utils.ArrayUtils;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;

    @NonFinal
    private final Map<String, String[]> publicEndpoints = new HashMap<>(Map.of(
            "/identity", new String[]{
                    "/auth/.*",
                    "/users/registration"
            },
            "/notification", new String[]{
                    "/email/send"
            },
            "/college-exam-score", new String[]{
                    "/scores/.*"
            },
            "/school", new String[]{
                    "/schools.*"
            },
            "/profile", new String[]{
            },
            "/post", new String[]{
            }
    ));

    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefix;

    @PostConstruct
    private void initializePublicEndpoints() {
        String[] additionalEndpoints = {
                "/v3/api-docs"
        };

        for (Map.Entry<String, String[]> entry : publicEndpoints.entrySet()) {
            String[] mergedEndpoints = ArrayUtils.mergeArrays(entry.getValue(), additionalEndpoints);
            publicEndpoints.put(entry.getKey(), mergedEndpoints);
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoint(exchange.getRequest()))
            return chain.filter(exchange);

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse());

        String token = authHeader.get(0).replace("Bearer ", "");

        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getData().isValid())
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse());
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        String requestPath = request.getURI().getPath();
        return publicEndpoints.entrySet().stream()
                .anyMatch(entry ->
                        Arrays.stream(entry.getValue())
                                .anyMatch(endpoint -> requestPath.matches(apiPrefix + entry.getKey() + endpoint))
                );
    }


    Mono<Void> unauthenticated(ServerHttpResponse response){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
