package com.executor.gateway.core.constant;


/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/29 0029 14:47
 * @Description:
 */
public class RedisConst {
    /**
     * app生成secret 前缀
     */
    public static final String APP_SECRET_PREFIX = "app_secret_prefix:";
    public static final int REDIS_APP_SECRET_TIMEOUT = 7; // 天
    public static final String APP_API_VISIT_TIMEOUT_PREFIX = "app_api_visit_timeout_prefix:";// app接口访问超时前缀
    public static final String APP_API_VISIT_CONCURRENCE_LIMIT_PREFIX = "app_api_visit_concurrence_limit_prefix:";// app接口访问并发限制
    public static final String APP_API_VISIT_ERROR_LIMIT_PREFIX = "app_api_visit_error_count_limit_prefix:";// app接口访问错误次数限制
    public static final String VISIT_LIMIT_REDIS_KEY_PREFIX = "visit_limit_redis_key:"; // 需要限制的ip存储到redis的key前缀
    public static final String  API_CONFIGS = "api_configs";
    public static  String APP_CONNECT_PWD  = "";
}
