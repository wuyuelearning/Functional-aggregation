package memorandum;

/**
 * Created by wuyue on 2018/10/26.
 * describe: 备忘录
 */

public class Memote {

    private String content;  //  文字内容
    private int cursor;   // 光标的位置


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }
}
