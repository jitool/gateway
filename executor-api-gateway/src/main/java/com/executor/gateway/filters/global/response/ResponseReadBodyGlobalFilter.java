package com.executor.gateway.filters.global.response;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.ApiResult;
import com.executor.gateway.core.constant.RESPONSE;
import com.executor.gateway.core.rule.support.RibbonFilterContextHolder;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/26 21:26
 * @Description: 读取响应体过滤器
 */
@Component
@Slf4j
public class ResponseReadBodyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        //重写原始响应
        BodyHandlerServerHttpResponseDecorator responseDecorator = new BodyHandlerServerHttpResponseDecorator(
                initBodyHandler(exchange, startTime), exchange.getResponse());
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        //WRITE_RESPONSE_FILTER 之前执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    /**
     * 响应body处理，添加响应的打印
     *
     * @param exchange
     * @param startTime
     * @return
     */
    private BodyHandlerServerHttpResponseDecorator.BodyHandlerFunction initBodyHandler(ServerWebExchange exchange, long startTime) {
        return (resp, body) -> {
            //清理线程钩子
            RibbonFilterContextHolder.clearCurrentContext();
            //拦截
            String trace = exchange.getRequest().getHeaders().getFirst("trace");
            MediaType originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(originalResponseContentType);
            DefaultClientResponseAdapter clientResponseAdapter = new DefaultClientResponseAdapter(body, httpHeaders);
            Mono<String> bodyMono = clientResponseAdapter.bodyToMono(String.class);
            return bodyMono.flatMap(respBody -> {
                //打印返回响应日志
                log.debug("[Trace:{}]-gateway response:ct=[{}], status=[{}],headers=[{}],body=[{}]",
                        trace, System.currentTimeMillis() - startTime, resp.getStatusCode(), resp.getHeaders(), respBody);
                String errRespString = getErrRespString(resp.getStatusCode());
                if (!Strings.isNullOrEmpty(errRespString)) {
                    resp.setStatusCode(HttpStatus.OK);
                    respBody = errRespString;
                }
                HttpHeaders headers = resp.getHeaders();
                //特别声明：响应体改变必须设置contentLength，且长度要保持一致，(经过测试，如果过短则会截断，过长则会导致超时。）
                headers.setContentLength(respBody.getBytes(Charset.forName("UTF-8")).length);
                return resp.writeWith(Flux.just(respBody).map(bx -> resp.bufferFactory().wrap(bx.getBytes())));
            }).then();
        };
    }

    private String getErrRespString(HttpStatus httpStatus) {
        String respStr = "";
        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            ApiResult apiResult = new ApiResult(RESPONSE.ERROR, "出了点小问题，但是问题不大");
            respStr = JSON.toJSONString(apiResult);
        } else if (httpStatus == HttpStatus.BAD_REQUEST) {
            ApiResult apiResult = new ApiResult(RESPONSE.ERROR, "请求参数错误，客户端请检查参数");
            respStr = JSON.toJSONString(apiResult);
        } else if (httpStatus == HttpStatus.NOT_FOUND) {
            ApiResult apiResult = new ApiResult(RESPONSE.ERROR, "糟糕，api走丢了~~~");
            respStr = JSON.toJSONString(apiResult);
        }
        return respStr;
    }
}
