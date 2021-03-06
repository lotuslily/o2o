package com.cincc.o2o.enums;

/**
 * @author li
 * Date: 2018/05/07
 */
public enum ShopStateEnum {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),
    INNER_ERRO(-10001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop信息为空");

    private  int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 带参构造方法设为private是不希望外部访问到 枚举类型
     * @param state
     * @param stateInfo
     */
    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }
}
