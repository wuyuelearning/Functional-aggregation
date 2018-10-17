package draw_board;

import android.graphics.Path;

/**
 * Created by wuyue on 2018/10/17.
 * describe: 圆点组成的线条
 */

public class CircleBrush implements IBrush {
    @Override
    public void down(Path path, float x, float y) {

    }

    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x, y,10,Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
