package draw_board;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by wuyue on 2018/10/17.
 * describe:  具体的接收者，绘制操作都在SurfaceView中进行
 */

public class DrawCanvans extends SurfaceView implements SurfaceHolder.Callback {

    public boolean isDrawing, isRunning;// 标识是否可以绘制，绘制线程是否可以运行

    private Bitmap mBitmap;
    private DrawInvoker mDrawInvoker;
    private DrawThread mThread;


    public DrawCanvans(Context context) {
        super(context);
    }

    public DrawCanvans(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawInvoker = new DrawInvoker();
        mThread = new DrawThread();
        getHolder().addCallback(this);
    }

    public DrawCanvans(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 增加一条绘制路径
     */

    public void add(DrawPath path) {
        mDrawInvoker.add(path);
    }


    /**
     * 重做上一步撤销绘制
     */

    public void redo() {
        isDrawing = true;
        mDrawInvoker.redo();
    }


    /**
     * 撤销上一步的绘制
     */
    public void undo() {
        isDrawing = true;
        mDrawInvoker.undo();
    }

    /**
     * 是否可以撤销
     */
    public boolean canUndo() {
        return mDrawInvoker.canUndo();
    }


    /**
     * 是否可以重做
     */
    public boolean canRedo() {
        return mDrawInvoker.canRedo();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class DrawThread extends Thread {

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning) {
                if (isDrawing) {
                    try {
                        canvas = getHolder().lockCanvas(null);
                        if (mBitmap == null) {
                            mBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        }
                        Canvas c = new Canvas(mBitmap);
                        c.drawColor(0, PorterDuff.Mode.CLEAR);
                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        mDrawInvoker.excute(c);
                        canvas.drawBitmap(mBitmap, 0, 0, null);
                    } finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                    isDrawing = false;
                }
            }
        }

    }
}
