package Utils;

/**
 * Created by wuyue on 2018/9/13.
 */

public class DivideEditTextUtil {

    /**
     *  输入框输入内容 自动分隔
     * @param originalString  原始字符串
     * @param divideSize    每个分隔部分的大小
     * @param dividiStyle   以dividiStyle字符分隔
     * @return
     */
    public static String divideString(String originalString ,int[] divideSize, String dividiStyle) {
        String content = originalString.replace(dividiStyle, "");
        if (content.length() == 0) {
            return content;
        }
        int countNum = 0;
        for (int i : divideSize) {
            if (i >= 1 ) {
                countNum++;
            }
        }

        if (countNum == 0) {
            return content;
        }
        int[] newDivideSize = new int[countNum];
        int k = 0;
        for (int i : divideSize) {
            if (i >= 1 ) {
                newDivideSize[k] = i;
                k++;
            }
        }

        StringBuilder builder = new StringBuilder();
        int j = 0;
        int count = 0;
        for (int i = 0; i < content.length(); i++) {
            builder.append(content.charAt(i));
            count++;
            //  由于count都是从1开始，所以可以排除divideCount[0] = 0的情况
            if ((j < newDivideSize.length && count == newDivideSize[j])) {
                j++;
                if (j >= newDivideSize.length) {
                    break;
                }
                builder.append(dividiStyle);
                count = 0;
            }
        }
        return backString(builder.toString(),dividiStyle);
    }


    private static String backString(String string, String divideStyle) {
        int end = string.length();
        if (end >= 2 && string.charAt(end - 1) == divideStyle.toCharArray()[0]) {
            string = string.replace(string, string.substring(0, end - 1));
        }
        return string;
    }


    /**
     * 仿照 trim() 方法，删除字母至分隔符处可以自动消除相应分隔符
     */
    private static  String trim(String s, String dividiStyle) {
        int len = s.length();
        int st = 0;
        char divide = dividiStyle.toCharArray()[0];

        // 原方法使用：s.charAt(st) <= ‘ ' ，使用<= 是因为 空格的ASCII为32 ，在之前的符号是
        // 一些非常用的用符号，但是如果都用<= 则只有ASCII>divide的字符才能输入
        while ((st < len) && (s.charAt(st) <= divide)) {
            st++;
        }
        while ((st < len) && (s.charAt(len - 1) <= divide)) {
            len--;
        }
        return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
    }


    /**
     *  输入框输入内容 自动分隔
     * @param originalString   原始字符串
     * @param dividiStyle      以dividiStyle字符分隔
     * @return
     */
    public static String divideString2(String originalString ,String dividiStyle) {
        String content = originalString.replace(dividiStyle, "");
        String first = "";
        String second = "";
        String third = "";

        if (content.length() >= 6) {
            first = content.substring(0, 6);
        } else {
            first = content;
        }

        if (content.length() >= 14) {
            second = content.substring(6, 14);
            third = content.substring(14, content.length());
        } else if (content.length() > 6 && content.length() < 14) {
            second = content.substring(6, content.length());
        }

        StringBuilder builder = new StringBuilder();

        if (first.length() > 0) {
            builder.append(first);
            if (first.length() == 6) {
                builder.append(dividiStyle);
            }
        }

        if (second.length() > 0) {
            builder.append(second);
            if (second.length() == 8) {
                builder.append(dividiStyle);
            }
        }

        if (third.length() > 0) {
            builder.append(third);
        }

        return backString(builder.toString(),dividiStyle);

    }
}
