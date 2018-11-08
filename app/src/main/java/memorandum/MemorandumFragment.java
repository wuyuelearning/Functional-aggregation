package memorandum;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/10/26.
 * describe: 备忘录
 * 《Android源码设计模式解析与实战》 第十三章 ：备忘录模式
 */

public class MemorandumFragment extends Fragment implements View.OnClickListener {
    TextView tvUndo;  // 撤销
    TextView tvSave;  // 保存
    TextView tvRedo;  // 重做
    NoteEditText etWriteNote;  // 编辑器
    // note 备忘录管理器
    NoteCaretaker noteCaretaker = new NoteCaretaker();
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_memorandum, container, false);
        initView();
        return mView;
    }

    private void initView() {
        tvUndo = (TextView) mView.findViewById(R.id.tv_undo);
        tvRedo = (TextView) mView.findViewById(R.id.tv_redo);
        tvSave = (TextView) mView.findViewById(R.id.tv_save);
        etWriteNote = (NoteEditText) mView.findViewById(R.id.et_write_note);

        tvUndo.setOnClickListener(this);
        tvRedo.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    private void makeToast(String content){
        Toast.makeText(getContext(),content+etWriteNote.getText()+" , 光标位置"+etWriteNote.getSelectionStart(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_undo:
                // 返回上一个记录
                etWriteNote.restore(noteCaretaker.getPreMemote());
                makeToast("撤销：");
                break;
            case R.id.tv_redo:
                // 恢复到下一个记录
                etWriteNote.restore(noteCaretaker.getNextMemote());
                makeToast("重做：");
                break;
            case R.id.tv_save:
                noteCaretaker.saveMemote(etWriteNote.createMemoto());
                makeToast("保存笔记：");
                break;
            default:
                break;
        }
    }
}
