package utils.banner;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;



import java.io.IOException;

import utils.bring.L;

/**
 * Created by Hukuan
 * 2019/1/9.
 */
public class SplashMediaView extends TextureView implements
        TextureView.SurfaceTextureListener {
    private MediaPlayer mMediaPlayer;
    private String url;
    public SplashMediaView(Context context) {
        super(context);
    }

    public SplashMediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashMediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUrl(String videoUrl) {
        this.url = videoUrl;
        setSurfaceTextureListener(this);
        /*
            Maybe setSurfaceTextureListener after the surface is available,  won't get the onSurfaceTextureAvailable callback.
         */
        if (isAvailable()) {
            onSurfaceTextureAvailable(getSurfaceTexture(), getWidth(), getHeight());
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        updateTextureViewSizeCenterCrop();
                    }
                });
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
//                        mediaComplete.complete();
                    }
                });
                mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        L.e("mMediaPlayer onError");
                        if (mediaComplete != null) {
                            mediaComplete.error();
                        }
                        return false;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMediaPlayer.setSurface(new Surface(surface));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setSurface(null);
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void onPause() {
        if (mMediaPlayer != null){
            mMediaPlayer.pause();
        }
    }

    public void onResume() {
        if (mMediaPlayer != null){
            mMediaPlayer.start();
        }
    }

    public void release() {
        // 播放完成，释放掉资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void updateTextureViewSizeCenterCrop() {
        int mVideoHeight = mMediaPlayer.getVideoHeight();
        int mVideoWidth = mMediaPlayer.getVideoWidth();
        float sx = (float) getWidth() / (float) mVideoWidth;
        float sy = (float) getHeight() / (float) mVideoHeight;

        Matrix matrix = new Matrix();
        float maxScale = Math.max(sx, sy);

        // 第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate((getWidth() - mVideoWidth) / 2,
                (getHeight() - mVideoHeight) / 2);

        // 第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(mVideoWidth / (float) getWidth(), mVideoHeight
                / (float) getHeight());

        // 第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等.
        // 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
        matrix.postScale(maxScale, maxScale, getWidth() / 2, getHeight() / 2);// 后两个参数坐标是以整个View的坐标系以参考的

        setTransform(matrix);
        postInvalidate();
    }

    public interface MediaComplete{
        void error();
    }

    MediaComplete mediaComplete;

    public void setOnMediaCompleteListener(MediaComplete mediaComplete){
        this.mediaComplete = mediaComplete;
    }
}
