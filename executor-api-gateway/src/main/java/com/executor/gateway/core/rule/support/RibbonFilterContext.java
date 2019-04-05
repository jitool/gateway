package com.executor.gateway.core.rule.support;

import java.util.Map;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 18:08
 * @Description: ribbon上下文，用于获取一些信息
 */
public interface RibbonFilterContext {

    RibbonFilterContext add(String key, String value);

    String get(String key);

    RibbonFilterContext remove(String key);

    Map<String, String> getAttributes();
}
