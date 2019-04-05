package com.executor.gateway.predicate;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/25 0025 16:30
 * @Description:  参考org.springframework.cloud.gateway.support.CachedBodyOutputMessage
 */
public class CustomCacheBodyOutputMessage implements ReactiveHttpOutputMessage {
    private Flux<DataBuffer> body =Flux.empty();
    private final DataBufferFactory bufferFactory;
    private final HttpHeaders httpHeaders;

    private Function<Flux<DataBuffer>, Mono<Void>> writeHandler = initDefaultWriteHandler();

    public CustomCacheBodyOutputMessage(ServerWebExchange exchange, HttpHeaders httpHeaders) {
        this.bufferFactory = exchange.getResponse().bufferFactory();
        this.httpHeaders = httpHeaders;
    }

    @Override
    public DataBufferFactory bufferFactory() {
        return this.bufferFactory;
    }

    @Override
    public void beforeCommit(Supplier<? extends Mono<Void>> action) {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return Mono.defer(() -> this.writeHandler.apply(Flux.from(body)));
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return  writeWith(Flux.from(body).flatMap(p -> p));
    }

    @Override
    public Mono<Void> setComplete() {
        return writeWith(Flux.empty());

    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    private Function<Flux<DataBuffer>, Mono<Void>> initDefaultWriteHandler() {
        return body -> {
            this.body = body.cache();
            return this.body.then();
        };
    }

    public Flux<DataBuffer> getBody() {
        return this.body;
    }
}
