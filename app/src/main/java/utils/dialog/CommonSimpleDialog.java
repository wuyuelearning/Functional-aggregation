package utils.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import utils.bring.StringUtil;

/**
 * Created by wuyue on 2019/4/28.
 * description:
 */

public class CommonSimpleDialog extends CommDialog {

    private Intent mIntent;
    private String msgTitle;
    private String msgValue;
    private TextView cancelView, okView;
    private View.OnClickListener clickListener;
    private TextView msgView;

    public CommonSimpleDialog(Context context, String msgTitle, String msgValue, Intent mIntent) {
        super(context, R.style.COMMON_SIMPLE_DIALOG_THEME);
        this.msgTitle = msgTitle;
        this.msgValue = msgValue;
        this.mIntent = mIntent;
        createView();
    }

    public CommonSimpleDialog(Context context, String msgTitle, String msgValue, View.OnClickListener clickListener) {
        super(context, R.style.COMMON_SIMPLE_DIALOG_THEME);
        this.msgTitle = msgTitle;
        this.msgValue = msgValue;
        this.clickListener = clickListener;
        createView();
    }

    @Override
    protected View getView() {
        View view = View.inflate(context, getLayoutId(), null);
        if (!StringUtil.isEmpty(msgTitle)) {
            TextView msgTitleView = (TextView) view.findViewById(R.id.msg_title_view);
            msgTitleView.setVisibility(View.VISIBLE);
            msgTitleView.setText(msgTitle);
        }
        msgView = (TextView) view.findViewById(R.id.msg_view);
        msgView.setMovementMethod(ScrollingMovementMethod.getInstance());
        msgView.setText(msgValue);
        cancelView = (TextView) view.findViewById(R.id.cancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonSimpleDialog.this.dismiss();
            }
        });

        if (null == clickListener && null != mIntent) {
            // 未设置点击事件，则默认是跳转
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(mIntent);
                }
            };
        }
        okView = (TextView) view.findViewById(R.id.ok);
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonSimpleDialog.this.dismiss();
                if (null != clickListener) {
                    clickListener.onClick(v);
                }
            }
        });
        return view;
    }

    public int getLayoutId() {
        return R.layout.dialog_common_simple;
    }

    public void setContentTextSize(int contentTextSize) {
        if (null != msgView) {
            msgView.setTextSize(contentTextSize);
        }
    }

    public void setContent(String content) {
        if (null != msgView) {
            msgView.setText(String.format("您已进入\n%s\n范围内", content));
        }
    }

    public void setCancel(String cancelValue) {
        if (!StringUtil.isEmpty(cancelValue)) {
            cancelView.setText(cancelValue);
        } else {
            cancelView.setVisibility(View.GONE);
        }
    }

    public void setOk(String okValue) {
        if (!StringUtil.isEmpty(okValue)) {
            okView.setText(okValue);
        } else {
            okView.setVisibility(View.GONE);
        }
    }

    /**
     * 一般默认取消是没有事件的，所以没有将该事件放到构造器当中
     */
    public void setOnCancelClickListener(View.OnClickListener onClickListener) {
        cancelView.setOnClickListener(onClickListener);
    }
}
