package com.bingye.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGateWayLoggerFilter implements GlobalFilter,Ordered {

	private final ObjectMapper mapper = new ObjectMapper();
    private final DataBufferFactory dataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
	
	@Override
    public int getOrder() {
        return 0;
    }

	@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();
        HttpMethod method = request.getMethod();
        StringBuilder builder = new StringBuilder();
        if (HttpMethod.GET.equals(method)) {
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            try {
                builder.append(mapper.writeValueAsString(queryParams));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        } else if (HttpMethod.POST.equals(method)) {
            Flux<DataBuffer> body = request.getBody();
            ServerHttpRequest serverHttpRequest = request.mutate().uri(request.getURI()).build();
            body.subscribe(dataBuffer -> {
                InputStream inputStream = dataBuffer.asInputStream();
                try {
                    builder.append(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            });
            request = new ServerHttpRequestDecorator(serverHttpRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return Flux.just(dataBufferFactory.wrap(builder.toString().getBytes(StandardCharsets.UTF_8)));
                }
            };
        }
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpStatus statusCode = response.getStatusCode();
 			log.info("\n请求路径:{}\n远程IP地址:{}\n请求方法:{}\n请求参数:{}\n响应码:{}", path, remoteAddress, method, builder.toString(), statusCode);
        }));
    }

}
