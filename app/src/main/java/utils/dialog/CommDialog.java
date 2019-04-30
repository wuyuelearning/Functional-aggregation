package utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.example.admin.projecttest.R;


/**
 * @description: 自定义对话框
 * @author: yangyang
 * @time: 2012-8-20下午6:07:39
 */
public abstract class CommDialog extends Dialog {
	private Window window;
	protected LayoutParams layoutParams;
	protected Context context;

	/**
	 * 
	 * @param context
	 */
	protected CommDialog(Context context) {
		this(context, R.style.DT_DIALOG_THEME);
	}

	/**
	 * 
	 * @param context
	 * @param theme
	 */
	protected CommDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	protected void createView() {
		super.setContentView(getView());
		window = getWindow();
		layoutParams = window.getAttributes();
		super.setCancelable(true);
		super.setCanceledOnTouchOutside(true);
		window.addFlags(LayoutParams.FLAG_DIM_BEHIND);
		layoutParams.alpha = 0.98765f;
		layoutParams.dimAmount = 0.56789f;
		window.setWindowAnimations(R.style.DT_DIALOG_ANIMATIONS);
		// 设置支持可弹出软键盘(默认情况不可以)
		window.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
	}

	/**
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public void setWindowPosition(int xPos, int yPos) {
		layoutParams.x = xPos;
		layoutParams.y = yPos;
	}

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setWindowSize(int width, int height) {
		layoutParams.width = width;
		layoutParams.height = height;
	}

	/**
	 * 
	 * @return
	 */
	public int getWindowWidth() {
		return layoutParams.width;
	}

	/**
	 * 
	 * @return
	 */
	public int getWindowHeight() {
		return layoutParams.height;
	}

	/**
	 * 
	 * @param gravity
	 */
	public void setWindowGravity(int gravity) {
		window.setGravity(gravity);
	}

	/**
	 * 
	 * @param anims
	 */
	public void setWindowAnimations(int anims) {
		window.setWindowAnimations(anims);
	}

	/**
	 * 
	 * @param alpha
	 */
	public void setWindowAlpha(float alpha) {
		layoutParams.alpha = alpha;
	}

	/**
	 * 
	 * @param bgAlpha
	 */
	public void setWindowBgAlpha(float bgAlpha) {
		layoutParams.dimAmount = bgAlpha;
	}

	/**
	 * 
	 * @return
	 */
	protected abstract View getView();
}