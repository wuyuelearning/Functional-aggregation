package draw_board;


import android.support.v4.app.Fragment;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/10/17.
 * describe: 绘图画板
 * 《Android源码设计模式解析与实战》 第十一章 ：命令模式
 */

public class DrawBoardFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private DrawCanvans mCanvans;
    private DrawPath mPath;
    private Paint mPaint;
    private IBrush mBrush;

    private Button btnRedo, btnUndo,btnRed,btnGreen,btnBlue,btnNormalBrush,btnCircleBrush;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_draw_board, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(3);
        mBrush = new NormalBrush();

        mCanvans = (DrawCanvans) mView.findViewById(R.id.dc_draw_board);
        mCanvans.setOnTouchListener(new DrawTouchListener());

        btnRedo = (Button) mView.findViewById(R.id.dc_redo);
        btnRedo.setOnClickListener(this);
        btnRedo.setEnabled(false);

        btnUndo = (Button) mView.findViewById(R.id.dc_undo);
        btnUndo.setOnClickListener(this);
        btnUndo.setEnabled(false);

        btnRed = (Button)mView.findViewById(R.id.dc_color_red);
        btnGreen = (Button)mView.findViewById(R.id.dc_color_green);
        btnBlue = (Button)mView.findViewById(R.id.dc_color_blue);
        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        btnNormalBrush =(Button)mView.findViewById(R.id.dc_normal_brush);
        btnCircleBrush =(Button)mView.findViewById(R.id.dc_circle_brush);
        btnNormalBrush.setOnClickListener(this);
        btnCircleBrush.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dc_color_red:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFFFF0000);
                break;
            case R.id.dc_color_green:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF00FF00);
                break;
            case R.id.dc_color_blue:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF0000FF);
                break;
            case R.id.dc_undo:   //  撤销操作
                mCanvans.undo();
                if (!mCanvans.canUndo()) {
                    btnUndo.setEnabled(false);
                }
                btnRed.setEnabled(true);
                break;
            case R.id.dc_redo:   //  重做操作
                mCanvans.redo();
                if (!mCanvans.canRedo()) {
                    btnRedo.setEnabled(false);
                }
                btnUndo.setEnabled(true);
                break;
            case R.id.dc_normal_brush:
                mBrush = new NormalBrush();
                break;
            case R.id.dc_circle_brush:
                mBrush = new CircleBrush();
                break;
        }
    }


    private class DrawTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                mPath =new DrawPath();
                mPath.paint =mPaint;
                mPath.path =new Path();
                mBrush.down(mPath.path,event.getX(),event.getY());
            } else if(event.getAction()==MotionEvent.ACTION_MOVE){
                mBrush.move(mPath.path,event.getX(),event.getY());
            } else if (event.getAction()==MotionEvent.ACTION_UP){
                mBrush.up(mPath.path,event.getX(),event.getY());
                mCanvans.add(mPath);
                mCanvans.isDrawing =true;
                btnUndo.setEnabled(true);
                btnRedo.setEnabled(false);
            }
            return true;
        }
    }
}
