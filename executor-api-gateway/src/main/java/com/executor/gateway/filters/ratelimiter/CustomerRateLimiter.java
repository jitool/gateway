package com.executor.gateway.filters.ratelimiter;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.constant.LimitLevelEnum;
import com.executor.gateway.model.bo.BaseLimitKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/8 13:23
 * @Description:
 */
@Component
@Primary
@Slf4j
public class CustomerRateLimiter extends AbstractRateLimiter<CustomerRateLimiter.Config> {
    private static final String CONFIGURATION_PROPERTY_NAME = "rate-limiter";
    private static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisScript<List<Long>> script;
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public CustomerRateLimiter() {
        super(Config.class, CONFIGURATION_PROPERTY_NAME, null);
        if (initialized.compareAndSet(false, true)) {
//            this.redisTemplate = SpringBeanUtils.getBean("stringReactiveRedisTemplate", ReactiveRedisTemplate.class);
//            this.script = SpringBeanUtils.getBean(REDIS_SCRIPT_NAME, RedisScript.class);
//            if (SpringBeanUtils.getApplicationContext().getBeanNamesForType(Validator.class).length > 0) {
//                this.setValidator(SpringBeanUtils.getBean(Validator.class));
//            }
        }
    }


    @Override
    public Mono<Response> isAllowed(String routeId, String key) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("RedisRateLimiter is not initialized");
        }

        try {
            //获取限流配置
            BaseLimitKey limitKey = JSON.parseObject(key, BaseLimitKey.class);
            List<String> scriptArgs = this.getScriptArgs(routeId, limitKey);
            List<String> keys = getKeys(limitKey.getLimitBiz());
            Flux<List<Long>> flux = this.redisTemplate.execute(this.script, keys, scriptArgs);
            return flux.onErrorResume(throwable -> Flux.just(Arrays.asList(1L, -1L)))
                    .reduce(new ArrayList<Long>(), (longs, l) -> {
                        longs.addAll(l);
                        return longs;
                    }).map(results -> {
                        boolean allowed = results.get(0) == 1L;
                        Long tokensLeft = results.get(1);
                        return getResponse(allowed, tokensLeft);
                    });
        } catch (Exception e) {
            log.error("Error determining if user allowed from redis", e);
        }
        return Mono.just(getResponse(true, -1L));

    }

    private List<String> getScriptArgs(String routeId, BaseLimitKey limitKey) {
        Config config = this.getConfig().get(routeId);
        int replenishRate;
        int burstCapacity;
        if (LimitLevelEnum.GLOBAL.getValue() == config.getLimitLevel()) {
            replenishRate = config.getReplenishRate();
            burstCapacity = config.getBurstCapacity();
        } else {
            replenishRate = limitKey.getReplenishRate();
            burstCapacity = limitKey.getBurstCapacity();
            //重新组装一下key，以每个api为基准进行限流
            limitKey.setLimitBiz(limitKey.getLimitBiz()+"_"+limitKey.getApiUri());
        }
        return Arrays.asList(replenishRate + "", burstCapacity + "",
                Instant.now().getEpochSecond() + "", "1");
    }


    private Response getResponse(boolean allowed, Long tokensLeft) {
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put(REMAINING_HEADER, String.valueOf(tokensLeft));
        return new Response(allowed, httpHeaders);
    }

    @Validated
    @Data
    public static class Config {
        @Min(1)
        private int replenishRate;

        @Min(0)
        private int burstCapacity;

        private int limitLevel = LimitLevelEnum.API.getValue();//1:全局级别 2：api级别

        @Override
        public String toString() {
            return "Config{" +
                    "replenishRate=" + replenishRate +
                    ", burstCapacity=" + burstCapacity +
                    '}';
        }
    }

    private static List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "request_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }


}
