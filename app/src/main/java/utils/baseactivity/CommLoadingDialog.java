package utils.baseactivity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.admin.projecttest.R;

import utils.bring.L;
import utils.bring.MobileUtil;
import utils.dialog.CommDialog;

public class CommLoadingDialog extends CommDialog {
	private ImageView anim_img;
	private TextView content_tv;

	public CommLoadingDialog(Context context) {
		super(context, R.style.KC_DIALOG_Translucent);
		createView();
		setWindowBgAlpha(0);
		// setWindowAlpha((float)0.5);
		int width = MobileUtil.dip2px(context, 120);
		L.d("CommLoadingDialog:" + width);
		setWindowSize(width, width);
	}

	@Override
	protected View getView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dt_now_page_loading, null);
		anim_img = (ImageView) view.findViewById(R.id.anim_img);
		content_tv = (TextView) view.findViewById(R.id.content_tv);
		return view;
	}

	public void startAnimation() {
		L.d("CommLoadingDialog startAnimation:" + anim_img);
		AnimationDrawable anim = (AnimationDrawable) anim_img.getBackground();
		L.d("CommLoadingDialog startAnimation:" + anim);
		if (!anim.isRunning()) {
			anim.start();
		}
	}

	public void stopAnimation() {
		AnimationDrawable anim = (AnimationDrawable) anim_img.getBackground();
		if (anim.isRunning()) {
			anim.stop();
		}
	}

	public ImageView getAnim_img() {
		return anim_img;
	}

	public TextView getContent_tv() {
		return content_tv;
	}
}