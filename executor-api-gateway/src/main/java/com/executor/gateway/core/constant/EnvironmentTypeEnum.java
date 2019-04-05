package com.executor.gateway.core.constant;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/11 0011 16:08
 * @Description:环境配置
 */
public enum EnvironmentTypeEnum {
    DEBUG(1,"debug"),
    TEST(2,"测试"),
    PROD(3,"生产"),
    MAINTENANCE(4,"维护")
    ;
    /**
     * 编码
     */
    private int value;

    private String desc;


    // 构造方法
    EnvironmentTypeEnum(int value, String desc) {
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
    public static EnvironmentTypeEnum getByValue(int value) {
        for (EnvironmentTypeEnum e : values()) {
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
