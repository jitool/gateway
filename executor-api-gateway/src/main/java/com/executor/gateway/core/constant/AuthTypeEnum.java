package com.executor.gateway.core.constant;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/9 20:27
 * @Description:
 */
public enum AuthTypeEnum {
    NO_AUTH(0,"不需要权限"),
    AUTH(1,"需要权限"),
    ;
    /**
     * 编码
     */
    private int value;

    private String desc;


    // 构造方法
    AuthTypeEnum(int value, String desc) {
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
    public static AuthTypeEnum getByValue(int value) {
        for (AuthTypeEnum e : values()) {
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
