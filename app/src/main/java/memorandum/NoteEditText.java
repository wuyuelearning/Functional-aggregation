package memorandum;



import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by wuyue on 2018/10/26.
 * describe: Note编辑器类
 */

public class NoteEditText extends AppCompatEditText {


    public NoteEditText(Context context) {
        this(context,null);
    }

    public NoteEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //  创建备忘录对象，即存储编辑器的指定数据
    public Memote createMemoto(){
        Memote memote =new Memote();
        // 存储文本与光标位置
        memote.setContent(getText().toString());
        memote.setCursor(getSelectionStart());
        return memote;
    }
    // 从备忘录中恢复数据
    public void restore(Memote memote){
        setText(memote.getContent());
        //  设置光标位置
        setSelection(memote.getCursor());
    }

}
