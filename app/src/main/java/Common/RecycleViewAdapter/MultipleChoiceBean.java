package Common.RecycleViewAdapter;

/**
 * Created by wuyue on 2018/6/6.
 */

public class MultipleChoiceBean {


    private String count;
    private String type;
    private boolean isSelect =false;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
