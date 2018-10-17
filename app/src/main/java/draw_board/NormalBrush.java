package draw_board;

import android.graphics.Path;

/**
 * Created by wuyue on 2018/10/17.
 * describe: 普通线条
 */

public class NormalBrush  implements IBrush{

    @Override
    public void down(Path path, float x, float y) {
        path.moveTo(x,y);
    }

    @Override
    public void move(Path path, float x, float y) {
        path.lineTo(x,y);

    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
