package Common.RecycleViewAdapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2018/6/6.
 */

public class MultipleChioceAdapter extends RecycleViewCommonAdapter<MultipleChoiceBean> {
    private List<MultipleChoiceBean> mDatas;
    private int selectedCount = 0;

    public MultipleChioceAdapter(Context context, int layoutId, List<MultipleChoiceBean> datas) {
        super(context, layoutId, datas);
        mDatas = datas;
    }

    @Override
    public void convert(ViewHolder holder, final MultipleChoiceBean itemInfo) {
        holder.setText(R.id.tv_discount_amount, itemInfo.getCount());
        holder.setText(R.id.tv_coupon_type, itemInfo.getType());
        final ImageView img = holder.getView(R.id.iv_use_coupon);
        SelectButton selectButton = new SelectButton(itemInfo, img);
        holder.getView(R.id.iv_use_coupon).setOnClickListener(selectButton);
    }

    private class SelectButton implements View.OnClickListener {
        private MultipleChoiceBean itemInfo;
        private ImageView img;

        SelectButton(MultipleChoiceBean itemInfo, ImageView img) {
            this.itemInfo = itemInfo;
            this.img = img;
        }

        @Override
        public void onClick(View v) {
            selectItem(itemInfo, img);
        }

        private void selectItem(MultipleChoiceBean itemInfo, ImageView img) {
            if (itemInfo.isSelect()) {   //  如果已选 ，任何时候都可以取消
                img.setImageResource(R.drawable.coupon_not_check);
                selectedCount--;
                itemInfo.setSelect(!itemInfo.isSelect());
                Log.d("MultipleChoiceFragment", itemInfo.getCount() + "  " + itemInfo.isSelect() + "  unselected");
            } else if (!itemInfo.isSelect()) {  // 如果是未选，小于5个的，可以选上，否则提示 最多选5个
                if (selectedCount < 5) {
                    img.setImageResource(R.drawable.coupon_check);
                    selectedCount++;
                    itemInfo.setSelect(!itemInfo.isSelect());
                    Log.d("MultipleChoiceFragment", itemInfo.getCount() + "  " + itemInfo.isSelect() + "  selected");
                } else {
                    Log.d("MultipleChoiceFragment", "超出五个");
                }
            }
            printInfo();
        }

        private void printInfo() {
            Log.d("MultipleChoiceFragment", "===============================================");
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isSelect()) {
                    Log.d("MultipleChoiceFragment", "||" + mDatas.get(i).getCount() + "  " + mDatas.get(i).isSelect());
                }
            }
            Log.d("MultipleChoiceFragment", "||    selectedCount: " + selectedCount);
            Log.d("MultipleChoiceFragment", "===============================================");
        }
    }
}
