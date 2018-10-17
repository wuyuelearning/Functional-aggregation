package draw_board;

import android.graphics.Path;

/**
 * Created by wuyue on 2018/10/17.
 * describe:
 */

public interface IBrush {
    /**
     *  触点接触时
     * @param path  路径对象
     * @param x 当前位置的x坐标
     * @param y 当前位置的y坐标
     */
     void down (Path path, float x, float y);


    /**
     *  触点移动时
     * @param path  路径对象
     * @param x 当前位置的x坐标
     * @param y 当前位置的y坐标
     */
     void move(Path path , float x, float y );

    /**
     *  触点离开时
     * @param path  路径对象
     * @param x 当前位置的x坐标
     * @param y 当前位置的y坐标
     */
     void up(Path path, float x, float y);

}
