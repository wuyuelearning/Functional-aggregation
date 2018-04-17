package Utils;

/**
 * Created by wuyue on 2018/4/17.
 */

public enum VALUE {

    GET_SCREEN_WH_1("1"),
    GET_SCREEN_WH_2(3);

    private String name;
    private int index;

    VALUE(String name) {
        this.name = name;
    }

    VALUE(int index) {
        this.index = index;
    }

    //  得到（）中的内容
    public String getName() {
        return name;
    }

    public static String getValue(String value) {
        for (VALUE code : VALUE.values()) {
            if (code.getName().equals(value))
                return code.getName();
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.print(VALUE.getValue("1"));
    }
}
