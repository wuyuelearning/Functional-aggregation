package tow_tab;

/**
 * Created by wuyue on 2018/11/8.
 * describe:
 */

public enum PRODUCT_TYPE_ENUM {

    ALL("全部"),
    TICKET("门票"),
    ROUTE("线路"),
    HOTEL("酒店");


    private String type;

    PRODUCT_TYPE_ENUM(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
