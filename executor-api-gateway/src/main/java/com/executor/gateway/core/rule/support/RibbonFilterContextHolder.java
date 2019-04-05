package com.executor.gateway.core.rule.support;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 18:10
 * @Description:
 */
public class RibbonFilterContextHolder {
    private static final ThreadLocal<RibbonFilterContext> CONTEXT_HOLDER = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    public static RibbonFilterContext getCurrentContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearCurrentContext() {
        CONTEXT_HOLDER.remove();
    }
}
