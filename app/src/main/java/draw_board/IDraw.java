package draw_board;

import android.graphics.Canvas;

/**
 * Created by wuyue on 2018/10/17.
 * describe:
 */

public interface IDraw {

    /**
     *  绘制命令
     * @param canvas 画布对象
     */
    void draw(Canvas canvas );

    /**
     *  撤销命令
     */
    void undo();

}
