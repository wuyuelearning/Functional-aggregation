package utils.banner;

import android.graphics.Paint;

public interface AdIndicator {

	void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

	void onPageSelected(int position);

	void onPageScrollStateChanged(int state);

	void notifyPageCountChanged(int current, int pageCount);

	void setColor(int color);

	void setSelectedColor(int color);

	void setUnselectedColor(int color);

	void setSelectedPaintStyle(Paint.Style style);
}
