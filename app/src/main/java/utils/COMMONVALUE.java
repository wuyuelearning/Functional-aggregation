package utils;

/**
 * Created by wuyue on 2018/4/17.
 * <p>
 * 将常量集中于COMMONVALUE类中
 * 使用时导入 import static Utils.COMMONVALUE.*; 即可
 * <p>
 * 但是有一些方便的是： int型常量需要一直往后添加，可能存在混淆，当数字使用错误，编译器不会报错
 * 参考博客：
 * https://blog.csdn.net/javazejian/article/details/71333103
 */

public class COMMONVALUE {

    public static final int GET_SCREEN_WH_1 = 1;
    public static final int GET_SCREEN_WH_2 = 2;
    public static final int GET_SCREEN_WH_3 = 3;
    public static final int GET_SCREEN_WH_4 = 4;

    // ListView 选择不同的Adapter
    public static final int SELECT_ADAPTER_1 = 1;
    public static final int SELECT_ADAPTER_2 = 2;
    public static final int SELECT_ADAPTER_3 = 3;
    public static final int SELECT_ADAPTER_4 = 4;

    // 选择RecycleView类型 ：SINGLE_ITEM 单种类型的Item  MULTI_ITEM ： 多种类型的Item
    public static final String SINGLE_ITEM = "single_item";
    public static final String MULTI_ITEM = "multi_item";

    public static final String FRAGMENT_TYPE_1 = "PopupWindow";
    public static final String FRAGMENT_TYPE_2 = "ListViewAdapter";
    public static final String FRAGMENT_TYPE_3 = "RecycleViewAdapter";
    public static final String FRAGMENT_TYPE_4 = "Notification";
    public static final String FRAGMENT_TYPE_5 = "ImageSpan";
    public static final String FRAGMENT_TYPE_6 = "DynamicLoadLayout";
    public static final String FRAGMENT_TYPE_7 = "MultipleChoice";
    public static final String FRAGMENT_TYPE_8 = "Animation";
    public static final String FRAGMENT_TYPE_9 = "QRCode";
    public static final String FRAGMENT_TYPE_10 = "ScreenShot";
    public static final String FRAGMENT_TYPE_11 = "Bitmap";
    public static final String FRAGMENT_TYPE_12 = "BitmapWall";
    public static final String FRAGMENT_TYPE_13 = "MVP";
    public static final String FRAGMENT_TYPE_14 = "Bezier";
    public static final String FRAGMENT_TYPE_15 = "DivideEditText";
    public static final String FRAGMENT_TYPE_16 = "ExpandableTextView";
    public static final String FRAGMENT_TYPE_17 = "DrawBoard";
    public static final String FRAGMENT_TYPE_18 = "Memorandum";
    public static final String FRAGMENT_TYPE_19 = "TwoTab";
    public static final String FRAGMENT_TYPE_20 = "MutilRV";
}
