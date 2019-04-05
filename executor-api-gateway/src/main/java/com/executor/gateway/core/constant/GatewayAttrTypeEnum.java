package com.executor.gateway.core.constant;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/9 20:27
 * @Description: 网关属性类型（类型含义需参见spring cloud gateway官方文档）
 */
public enum GatewayAttrTypeEnum {
    PREDICATE(1,"断言配置"),
    FILTER(2,"过滤器"),
    ;
    /**
     * 编码
     */
    private int value;

    private String desc;


    // 构造方法
    GatewayAttrTypeEnum(int value, String desc) {
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
    public static GatewayAttrTypeEnum getByValue(int value) {
        for (GatewayAttrTypeEnum e : values()) {
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
