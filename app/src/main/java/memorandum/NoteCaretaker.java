package memorandum;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyue on 2018/10/26.
 * describe: 负责管理Memoto对象
 */

public class NoteCaretaker {
    // 最大存储数量
    private static final int MAX = 30;

    //  存储备忘录30条记录
    private List<Memote> memoteList = new ArrayList<>();

    private int mIndex = 0;
    //  保存备忘录

    private Memote memote;  //  默认空白备忘录

    public NoteCaretaker(){
        memote = new Memote();
        memote.setContent("");
        memote.setCursor(0);
    }


    public void saveMemote(Memote memote) {
        if (memoteList.size() > MAX) {
            memoteList.remove(0);
        }
        memoteList.add(memote);
        mIndex = memoteList.size() - 1;
    }

    // 获取上一个存档信息，相当于撤销功能
    public Memote getPreMemote() {
        Log.d("MemorandumFragment:","getPreMemote mIndex: " +mIndex);
        mIndex = mIndex > 0 ? mIndex - 1 : mIndex;
        if(memoteList.size()>0){
            return memoteList.get(mIndex);
        } else {
            return memote;
        }
    }

    // 获取下一个存档信息，相当于重做
    public Memote getNextMemote() {
        Log.d("MemorandumFragment:","getNextMemote mIndex: " +mIndex);
        mIndex = mIndex < memoteList.size() - 1 ? mIndex + 1 : mIndex;
        if(memoteList.size()>0){
            return memoteList.get(mIndex);
        } else {
            return memote;
        }
    }
}
