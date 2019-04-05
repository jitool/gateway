package com.executor.gateway.core.constant;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/28 19:35
 * @Description: 限流级别枚举
 */
public enum LimitLevelEnum {
    GLOBAL(1,"全局级别"),
    API(2,"api级别"),
    ;
    /**
     * 编码
     */
    private int value;

    private String desc;


    // 构造方法
    LimitLevelEnum(int value, String desc) {
        this.value = value;
        this.desc=desc;
    }

    public int getValue() {
        return value;
    }
    /**
     * 获取枚举
     * @param value
     * @return
     */
    public static LimitLevelEnum getByValue(int value) {
        for (LimitLevelEnum e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }
}
