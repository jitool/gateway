package com.ruoyi.common.enums;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/4 0004 17:23
 * @Description: redis topic通知事件类型枚举
 */
public enum  TopicNotifyEventType {
    UPDATE("update","更新或添加"),
    DEL("del","删除"),
    REFRESH("refresh","刷新")
    ;
    /**
     * 编码
     */
    private String value;

    private String desc;


    // 构造方法
    TopicNotifyEventType(String value, String desc) {
        this.value = value;
        this.desc=desc;
    }

    public String getValue() {
        return value;
    }
    /**
     * 获取枚举
     * @param value
     * @return
     */
    public static TopicNotifyEventType getByValue(String value) {
        for (TopicNotifyEventType e : values()) {
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
