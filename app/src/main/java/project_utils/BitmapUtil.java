package project_utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class BitmapUtil {

    public static Bitmap greyBitmap(Context context, int drawable) {
        Bitmap bitmapSrc = BitmapFactory.decodeResource(context.getResources(),
                drawable);
        Bitmap buffer = Bitmap.createBitmap(bitmapSrc.getWidth(),
                bitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(buffer);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmapSrc, 0, 0, paint);
        canvas.save();
        return buffer;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static Bitmap toRoundBitmap(int width, String text, int bgColor,
                                       int tvColor) {
        if (width == 0 || TextUtils.isEmpty(text)) {
            return null;
        }

        int round = 6;

        Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));

        String text_1 = "";
        String text_2 = "";
        String text_3 = "";
        for (int i = 0; i < text.split("#").length; i++) {
            if (i == 0) {
                text_1 = text.split("#")[0];
            } else if (i == 1) {
                text_2 = text.split("#")[1];

            } else if (i == 2) {
                text_3 = text.split("#")[2];
            }
        }

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(12);
        Rect rect = new Rect();
        if (text_1.length() >= text_2.length()
                && text_1.length() >= text_3.length()) {
            mPaint.getTextBounds(text_1, 0, text_1.length(), rect);

        } else if (text_3.length() >= text_1.length()
                && text_3.length() >= text_2.length()) {
            mPaint.getTextBounds(text_3, 0, text_3.length(), rect);

        } else if (text_2.length() >= text_1.length()
                && text_2.length() >= text_3.length()) {
            mPaint.getTextBounds(text_2, 0, text_2.length(), rect);

        }
        float size = (float) ((12 * width * 0.7) / rect.width());
        mPaint.setTextSize(size);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseY = (fontMetrics.bottom - fontMetrics.top) / 2
                - fontMetrics.bottom;
        float heightLine = fontMetrics.bottom - fontMetrics.top;

        Paint round_mPaint = new Paint();
        round_mPaint.setAntiAlias(true);
        round_mPaint.setColor(tvColor);
        RectF round_rectF = new RectF(0, 0, width, width);
        canvas.drawRoundRect(round_rectF, width / 2, width / 2, round_mPaint);
        round_mPaint.setColor(bgColor);
        RectF round_rectF_1 = new RectF(round, round, width - round, width
                - round);
        canvas.drawRoundRect(round_rectF_1, (width - round) / 2,
                (width - round) / 2, round_mPaint);

        if (!TextUtils.isEmpty(text_1)) {
            mPaint.setColor(tvColor);
            mPaint.getTextBounds(text_1, 0, text_1.length(), rect);
            canvas.drawText(text_1, (width - rect.width()) / 2, width / 2
                    + baseY - heightLine, mPaint);
        }

        if (!TextUtils.isEmpty(text_2)) {
            mPaint.getTextBounds(text_2, 0, text_2.length(), rect);
            round_mPaint.setColor(tvColor);
            RectF rectF = new RectF((width - rect.width() - heightLine) / 2,
                    (width - heightLine) / 2,
                    (width + rect.width() + heightLine) / 2,
                    (width + heightLine) / 2);
            canvas.drawRoundRect(rectF, heightLine / 2, heightLine / 2,
                    round_mPaint);
            mPaint.setColor(bgColor);
            canvas.drawText(text_2, (width - rect.width()) / 2, width / 2
                    + baseY, mPaint);
        }

        if (!TextUtils.isEmpty(text_3)) {
            mPaint.setColor(tvColor);
            mPaint.getTextBounds(text_3, 0, text_3.length(), rect);
            canvas.drawText(text_3, (width - rect.width()) / 2, width / 2
                    + baseY + heightLine, mPaint);
        }
        return output;
    }

    /**
     * 生成圆角图片
     *
     * @return Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap sourceBitmap, float roundPx, float roundPy) {
        try {
            Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
                    sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Rect rect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
            RectF rectF = new RectF(rect);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, roundPx, roundPy, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(sourceBitmap, rect, rect, paint);
            return targetBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap toBitmapAndText(Bitmap bitmap, String text,
                                         int textColor) {
        if (bitmap == null || TextUtils.isEmpty(text)) {
            return null;
        }
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap((int) width, (int) height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint mPaint = new Paint();
        mPaint.setTextSize(40);
        mPaint.setColor(textColor);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));

        Rect mRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        if (mRect.width() > width * 2 / 3) {
            float size = (40 * width * 2) / (3 * mRect.width());
            mPaint.setTextSize(size);
        }

        Paint.FontMetrics mFontMetrics = mPaint.getFontMetrics();
        float baseY = (mFontMetrics.bottom - mFontMetrics.top) / 2
                - mFontMetrics.bottom;
        float heightLine = mFontMetrics.bottom - mFontMetrics.top;
        if (heightLine > height * 3 / 4) {
            float size = (mPaint.getTextSize() * 2 * height) / (heightLine * 3);
            mPaint.setTextSize(size);
            mPaint.getTextBounds(text, 0, text.length(), mRect);
            mFontMetrics = mPaint.getFontMetrics();
            baseY = (mFontMetrics.bottom - mFontMetrics.top) / 2
                    - mFontMetrics.bottom - 3;
            heightLine = mFontMetrics.bottom - mFontMetrics.top;
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.drawText(text, (width - mRect.width()) / 2, height / 2 + baseY,
                mPaint);
        return output;
    }

    public static Bitmap initSpecialSaleTab(Activity activity, int id) {
        Bitmap bt = BitmapFactory.decodeResource(activity.getResources(), id);
        int width = bt.getWidth();
        int height = bt.getHeight();
        float scale = MobileUtil.imageViewWidth(activity, 30, 2) / width;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        return Bitmap.createBitmap(bt, 0, 0, width, height, matrix, false);
    }

    public static Bitmap normalBitmap(Activity context, int drawable) {
        return BitmapFactory.decodeResource(context.getResources(),
                drawable);
    }

    /**
     * 获取压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap getCompressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * bitmap转file
     *
     * @param bmp
     * @return
     */
    public static File saveBitmap2file(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File imageFile = null;
        try {
            imageFile = File.createTempFile("tempImage" + System.currentTimeMillis(), ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(imageFile);
            BufferedOutputStream bStream = new BufferedOutputStream(fstream);
            bStream.write(byteArray);
            if (bStream != null) {
                bStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;

    }

    /**
     * 获取压缩图片
     *
     * @param srcPath
     * @return
     */
    public static File getCompressImage(String srcPath) {
        Options newOpts = new Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        newOpts.inPreferredConfig = Config.RGB_565;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return saveBitmap2file(compressImage(bitmap));//压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length / 1024 > 150) {    //循环判断如果压缩后图片是否大于80kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10

            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 对Bitmap做半透明玻璃玻璃效果
     *
     */
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    public static final Bitmap alpha(Bitmap bitmap, int alpha) {
        float[] matrixItems = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, alpha / 255f, 0, 0, 0, 0, 0, 1};
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap alphaBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(alphaBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix(matrixItems);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return alphaBitmap;
    }

    public static Bitmap toBitmap(Context context, int resId, Integer maxWidth, Integer maxHeight) {
        Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resId);
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight,
                true);
        bitmap.recycle();
        return scaled;
    }

    public static Bitmap toBitmap(InputStream input, Integer maxWidth,
                                  Integer maxHeight) {

        Options options = new Options();
        if ((maxWidth != null) && (maxHeight != null)) {
            // Set the scaling options.
            float scale = Math.min(maxWidth.floatValue() / options.outWidth,
                    maxHeight.floatValue() / options.outHeight);
            options.inSampleSize = Math.round(1 / scale);
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(input, null, options);
    }

    /**
     * Gets a {@link Bitmap} from a {@link Uri}. Resizes the image to a
     * determined width and height.
     *
     * @param uri       The {@link Uri} from which the image is obtained.
     * @param maxWidth  The maximum width of the image used to scale it. If null, the
     *                  image won't be scaled
     * @param maxHeight The maximum height of the image used to scale it. If null, the
     *                  image won't be scaled
     * @return {@link Bitmap} The resized image.
     */
    public static Bitmap toBitmap(Context context, Uri uri, Integer maxWidth, Integer maxHeight) {
        try {
            // First decode with inJustDecodeBounds=true to check dimensions
            Options options = new Options();
            options.inJustDecodeBounds = true;
            InputStream openInputStream = context.getContentResolver()
                    .openInputStream(uri);
            BitmapFactory.decodeStream(openInputStream, null, options);
            openInputStream.close();

            // Calculate inSampleSize
            if ((maxWidth != null) && (maxHeight != null)) {
                float scale = Math.min(
                        maxWidth.floatValue() / options.outWidth,
                        maxHeight.floatValue() / options.outHeight);
                options.inSampleSize = Math.round(1 / scale);
            }

            // Decode bitmap with inSampleSize set
            openInputStream = context.getContentResolver().openInputStream(uri);
            options.inJustDecodeBounds = false;
            Bitmap result = BitmapFactory.decodeStream(openInputStream, null,
                    options);
            openInputStream.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static ByteArrayInputStream toPNGInputStream(Context context, Uri uri,
                                                        Integer maxWidth, Integer maxHeight) {
        Bitmap bitmap = BitmapUtil.toBitmap(context, uri, maxWidth, maxHeight);
        return BitmapUtil.toPNGInputStream(bitmap);
    }

    /**
     * Compress the bitmap to a PNG and return its {@link ByteArrayInputStream}
     *
     * @param bitmap The {@link Bitmap} to compress
     * @return The {@link ByteArrayInputStream}
     */
    public static ByteArrayInputStream toPNGInputStream(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return new ByteArrayInputStream(bytes.toByteArray());
    }

    public static int getExifRotation(String imageFilePath) {
        if (imageFilePath == null)
            return 0;
        try {
            ExifInterface exif = new ExifInterface(imageFilePath);
            // We only recognize a subset of orientation tag values
            switch (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return ExifInterface.ORIENTATION_UNDEFINED;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    public static int calculateInSampleSize(Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        return calculateInSampleSize(width, height, reqWidth, reqHeight);
    }

    public static int calculateInSampleSize(int width, int height,
                                            int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth,
                                                     int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static int getExifRotation(File tempFile, Bitmap bitmap) {
        try {
            // 将图片保存
            FileOutputStream b = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
            b.flush();
            b.close();
            return getExifRotation(tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap,
                                                       int reqWidth, int reqHeight) {
        // Calculate inSampleSize
        final Options options = new Options();
        options.inSampleSize = calculateInSampleSize(bitmap.getWidth(),
                bitmap.getHeight(), reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        byte[] data = bitmap2Bytes(bitmap);
        if (data != null) {
            bitmap.recycle();
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } else {
            return bitmap;
        }
    }

    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static byte[] bitmap2Bytes(Bitmap bm) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap greyBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(greyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return greyBitmap;
    }

    public static Bitmap circular(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        // 保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // 圆形，所有只用一个
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
