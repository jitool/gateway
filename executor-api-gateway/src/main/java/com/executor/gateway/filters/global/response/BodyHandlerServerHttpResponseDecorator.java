package com.executor.gateway.filters.global.response;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/26 0026 14:21
 * @Description:
 */
public class BodyHandlerServerHttpResponseDecorator extends ServerHttpResponseDecorator {
    interface BodyHandlerFunction extends BiFunction<ServerHttpResponse, Publisher<? extends DataBuffer>, Mono<Void>> {
    }

    /**
     * body 处理拦截器
     */
    private BodyHandlerFunction bodyHandler = initDefaultBodyHandler();
    /**
     * 构造函数
     *
     * @param bodyHandler
     * @param delegate
     */
    public BodyHandlerServerHttpResponseDecorator(BodyHandlerFunction bodyHandler, ServerHttpResponse delegate) {
        super(delegate);
        if (bodyHandler != null) {
            this.bodyHandler = bodyHandler;
        }
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        //body 拦截处理器处理响应
        return bodyHandler.apply(getDelegate(), body);
    }

    @Override
    public Mono<Void> writeAndFlushWith(
            Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMapSequential(p -> p));
    }


    /**
     * 默认body拦截处理器
     *
     * @return
     */
    private BodyHandlerFunction initDefaultBodyHandler() {
        return ReactiveHttpOutputMessage::writeWith;
    }
}
