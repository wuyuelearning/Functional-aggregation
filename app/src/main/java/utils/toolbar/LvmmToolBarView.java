package utils.toolbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import utils.bring.MobileUtil;


/**
 * 对TitleBar的封装，使用的时候，直接添加到布局的顶层，当做一个普通的view即可
 * <p>
 * Created by yantinggeng on 2016/8/12.
 * <p>
 * Alter by hcf on 2016/10/19
 */
public class LvmmToolBarView extends RelativeLayout {

    private Activity context;
    private ViewGroup customTitleView = null;
    private ImageButton navigationIcon;
    private TextView titleTextView, toolBarRight, hotelToolBarTitle, txt_hotel_day;
    private View bottomLine;

    public LvmmToolBarView(Context context) {
        this(context, null);
    }

    public LvmmToolBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (Activity) context;
        setId(R.id.toolBar);
        initViews(context);
        initDefalut();
        initMenu();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MobileUtil.dip2px(48), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //初始化控件
    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this, true);
        navigationIcon = (ImageButton) findViewById(R.id.navigationIcon);
        titleTextView = (TextView) findViewById(R.id.toolBarTitle);
        toolBarRight = (TextView) findViewById(R.id.toolBarRight);
        hotelToolBarTitle = (TextView) findViewById(R.id.hotelToolBarTitle);
        txt_hotel_day = (TextView) findViewById(R.id.txt_hotel_day);
        customTitleView = (ViewGroup) findViewById(R.id.customTitleView);
        bottomLine = findViewById(R.id.bottom_line);
    }

    //定义默认值
    private void initDefalut() {
        //默认是返回键
        navigationIcon.setImageResource(R.drawable.comm_back_ic);
        navigationIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileUtil.hideKeyBoard(context);
                context.onBackPressed();
            }
        });
    }

    //初始化右边菜单
    private void initMenu() {
        //toolbar.inflateMenu(R.menu.toolbar_right);
        //menu = toolbar.getMenu();
    }

    /**
     * 设置自定义的Title部分
     *
     * @param titleView 替换的view
     */
    public void setCustomTitleView(View titleView) {
        this.titleTextView.setVisibility(GONE);
        customTitleView.addView(titleView);
    }

    public TextView getRightText() {
        return toolBarRight;
    }

    /**
     * 设置右边的图标
     *
     * @param icon 图标id
     */
    public void setToolBarRightIcon(@DrawableRes int icon) {
        toolBarRight.setVisibility(VISIBLE);
        toolBarRight.setBackgroundResource(icon);
        if (toolBarRight.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) toolBarRight.getLayoutParams();
            p.setMargins(0, 0, MobileUtil.dip2px(10), 0);
            toolBarRight.requestLayout();
        }
    }

    public void setToolBarRightTextColor(@ColorRes int color) {
        toolBarRight.setVisibility(VISIBLE);
        toolBarRight.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置标题的颜色
     *
     * @param color 颜色资源文件
     */
    public void setBackgroundColor(@ColorRes int color) {
        super.setBackgroundColor(getResources().getColor(color));
    }


    /**
     * 设置居中的标题
     *
     * @param title 标题内容
     */
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setRightAppearance(int style) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolBarRight.setTextAppearance(style);
        } else {
            toolBarRight.setTextAppearance(context, style);
        }
    }

    /**
     * 设置文本的大小
     *
     * @param size 标题大小
     */

    public void setTitleSize(float size) {
        titleTextView.setTextSize(size);
    }

    /**
     * 设置右边的文本
     *
     * @param text 文本内容
     */
    public void setToolBarRightTitle(String text) {
        toolBarRight.setVisibility(VISIBLE);
        toolBarRight.setText(text);
    }

    public void setToolBarRightVisible(int visibility) {
        toolBarRight.setVisibility(visibility);
    }

    /**
     * 设置右边的文本的点击事件
     *
     * @param listener 点击监听器
     */
    public void setToolBarRightOnClickListener(OnClickListener listener) {
        toolBarRight.setOnClickListener(listener);
    }


    /**
     * 设置标题的颜色
     *
     * @param color 颜色资源文件
     */
    public void setTitleTextColor(@ColorRes int color) {
        titleTextView.setTextColor(getResources().getColor(color));
    }


    /**
     * 设置导航图标，默认是返回键
     *
     * @param icon 图标的资源id
     */
    public void setNavigationIcon(@DrawableRes int icon) {
        navigationIcon.setImageResource(icon);
    }

    /**
     * 设置导航图标的点击事件
     *
     * @param listener 点击监听器
     */
    public void setNavigationOnClickListener(OnClickListener listener) {
        navigationIcon.setOnClickListener(listener);
    }

    public void setNavigationIconVisibility(int visibility) {
        navigationIcon.setVisibility(visibility);
    }

    public void setBottomLineVisibility(int visibility) {
        bottomLine.setVisibility(visibility);
    }

    public TextView getHotelToolBarTitle() {
        return hotelToolBarTitle;
    }

    public TextView getTxt_hotel_day() {
        return txt_hotel_day;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }
}
