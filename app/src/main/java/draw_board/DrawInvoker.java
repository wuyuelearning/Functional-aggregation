package draw_board;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wuyue on 2018/10/17.
 * describe:  请求者，请求命令
 */

public class DrawInvoker {

    // 绘制列表
    private List<DrawPath> drawList = Collections.synchronizedList(new ArrayList<DrawPath>());
    // 重做列表
    private List<DrawPath> redoList = Collections.synchronizedList(new ArrayList<DrawPath>());

    /**
     * 增加一个命令
     *
     * @param path
     */
    public void add(DrawPath path) {
        redoList.clear();
        drawList.add(path);
    }

    /**
     * 撤销上一步命令
     */
    public void undo() {
        if (drawList.size() > 0) {
            DrawPath undo = drawList.get(drawList.size() - 1);
            drawList.remove(drawList.size() - 1);
            undo.undo();
            redoList.add(undo);
        }
    }

    /**
     * 重做上一步撤销的命令
     */

    public void redo() {
        if (redoList.size() > 0) {
            DrawPath redo = redoList.get(redoList.size() - 1);
            redoList.remove(redoList.size() - 1);
            drawList.add(redo);
        }
    }

    public void excute(Canvas canvas) {
        if (drawList != null) {
            for (DrawPath path : drawList) {
                path.draw(canvas);
            }
        }
    }

    /**
     * 是否可重做
     */
    public boolean canRedo() {
        return redoList.size() > 0;
    }

    /**
     * 是否可撤销
     */

    public boolean canUndo() {
        return drawList.size() > 0;
    }

}
