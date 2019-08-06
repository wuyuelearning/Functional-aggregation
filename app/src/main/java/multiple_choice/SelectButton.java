package multiple_choice;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.projecttest.R;

import java.util.List;

import multiple_choice.MultipleChoiceBean;

/**
 * Created by admin on 2018/6/8.
 */

public class SelectButton implements View.OnClickListener {
    private MultipleChoiceBean itemInfo;

    private ImageView img;
    private List<MultipleChoiceBean> mDatas;
    private int selectedCount =0;


    SelectButton(MultipleChoiceBean itemInfo, ImageView img, List<MultipleChoiceBean> mDatas) {
        this.itemInfo = itemInfo;
        this.img = img;
        this.mDatas = mDatas;

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
