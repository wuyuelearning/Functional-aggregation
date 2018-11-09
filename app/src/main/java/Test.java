import java.util.ArrayList;
import java.util.List;

import tow_tab.PRODUCT_TYPE_ENUM;

/**
 * Created by wuyue on 2018/11/8.
 * describe:
 */

public class Test {
    public static void main(String[] args) {
        Enum e = new Enum();
        e.fun();
    }
}

 class Enum {
    private List<String> mTitles = new ArrayList<>();

    public void fun() {
        PRODUCT_TYPE_ENUM[] enums = PRODUCT_TYPE_ENUM.values();
        System.out.println( PRODUCT_TYPE_ENUM.values().toString());
        for (PRODUCT_TYPE_ENUM e : enums) {
            mTitles.add(e.getType());
        }
        System.out.println(mTitles.size());
        for (int i = 0; i < mTitles.size(); i++) {
            System.out.println(mTitles.get(i));
        }
    }
}

