package utils.web;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import project_utils.FileUtil;
import utils.bring.AppComponentsHolder;
import utils.bring.L;
import utils.bring.MobileUtil;

/**
 * 下载本地包任务，下载完成解压
 * Created by Steve on 2017/3/18.
 */
public class DownloadBundleRunnable implements Runnable {
    private OkHttpClient okHttpClient;
    private String url;
    private String bundleName;
    private TaskListener taskListener;
    private Handler handler;

    public DownloadBundleRunnable(OkHttpClient okHttpClient, String bundleName, String url, TaskListener taskListener) {
        this.okHttpClient = okHttpClient;
        this.url = url;
        this.bundleName = bundleName;
        this.taskListener = taskListener;
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 下载 bundle 文件
     *
     * @param url 待下载的文件 URL
     * @return 返回下载的文件流
     */
    private InputStream download(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        L.d("DownloadBundleRunnable download() url:" + url);
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            } else {
                return response.body().byteStream();
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取存储 bundle 的路径
     *
     * @param bundleName bundle  的包名
     * @return 返回存储的路径
     */
    private String filePath(String bundleName) {
        String dir = MobileUtil.getSDPath();
        if (TextUtils.isEmpty(dir)) {
            File cacheDir = AppComponentsHolder.inst().getApplication().getCacheDir();
            dir = cacheDir.getAbsolutePath();
        }
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir = dir + "lvmama";
            File targetFile = new File(dir);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            File nomedia = new File(dir + "/.nomedia");
            if (!nomedia.exists()) {
                try {
                    nomedia.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dir = dir + File.separator + bundleName;
        }
        return dir;
    }

    /**
     * 解压下载好的 bundle 压缩包
     *
     * @param inPath  待解压的压缩包路径
     * @param outPath 解压到的路径
     * @return true表示解压成功，反正解压失败
     */
    private boolean unpack(String inPath, String outPath) {
        File outDir = new File(outPath);
        if (!outDir.exists() || !outDir.isDirectory()) {
            outDir.mkdirs();
        }
        //fix  java.lang.UnsatisfiedLinkError: dalvik.system.PathClassLoader[DexPathList
        //                                     Couldn't load un7zip: findLibrary returned null
        //java.lang.ExceptionInInitializerError
        //java.lang.NoClassDefFoundError: com/hzy/lib7z/Un7Zip
        boolean extract7z =false;
        try {
//            extract7z = Un7Zip.extract7z(inPath, outPath);
        } catch (UnsatisfiedLinkError error) {
            extract7z = false;
        } catch (ExceptionInInitializerError error) {
            extract7z = false;
        } catch (NoClassDefFoundError error) {
            extract7z = false;
        } catch (Exception ec) {
            extract7z = false;
        }
        return extract7z;
    }

    /**
     * 保存文件到 SD 卡上
     *
     * @param targetPath 待保存的文件路径
     * @param is         文件的 InputStream
     * @return true 表示保存成功，反之保存失败
     */
    private boolean saveToSDCard(String targetPath, InputStream is) {
        File h5File = new File(targetPath);
        try {
            //true  if the named file does not exist and was successfully created;  false if the named file already exists
            boolean newFile = h5File.createNewFile();
        } catch (IOException e) {
            return false;
        }
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(is);
            outBuff = new BufferedOutputStream(new FileOutputStream(targetPath));
            byte[] buffer = new byte[1024 * 2];
            int len;
            while ((len = inBuff.read(buffer)) != -1) {
                outBuff.write(buffer, 0, len);
            }
            outBuff.flush();
        } catch (Exception e) {
            L.e("DownloadBundleRunnable save " + bundleName + "failed");
            return false;
        } finally {
            if (inBuff != null) {
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outBuff != null) {
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        InputStream inputStream = download(url);
        if (inputStream == null) {
            failed();
            return;
        }
        // 储存的路径
        String targetPath = filePath(bundleName + ".7z");
        boolean save = saveToSDCard(targetPath, inputStream);
        if (save) {
            String outPath = targetPath.substring(0, targetPath.lastIndexOf(File.separator));
            boolean unpack = unpack(targetPath, outPath);
            if (unpack) {
                // 成功，回调
                complete();
            } else {
                failed();
                L.e("DownloadBundleRunnable unpack " + bundleName + " failed");
            }
        } else {
            failed();
            L.d("DownloadBundleRunnable run() :" + "保存文件失败");
        }
        FileUtil.closeSilently(inputStream);
    }

    private void failed() {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                if (taskListener == null) {
                    return;
                }
                taskListener.onFailed();
            }
        });
    }

    private void complete() {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                if (taskListener == null) {
                    return;
                }
                taskListener.onComplete();
            }
        });
    }

    public interface TaskListener {
        void onComplete();

        void onFailed();
    }
}