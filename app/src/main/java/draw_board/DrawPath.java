package draw_board;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by wuyue on 2018/10/17.
 * describe:
 */

public class DrawPath implements IDraw {

    public Path path;
    public Paint paint;

    @Override
    public void draw(Canvas canvas) {
         canvas.drawPath(path,paint);
    }

    @Override
    public void undo() {
    }
}
