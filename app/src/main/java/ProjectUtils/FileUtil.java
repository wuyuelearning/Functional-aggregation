package ProjectUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;


public class FileUtil {

    public static final String fileName = "lvmama";

    /**
     * 获取文件MD5值
     */
    public static String getFileMd5(String filePath) {
        String value = null;
        File file = new File(filePath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            MappedByteBuffer byteBuffer = inputStream.getChannel()
                .map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16).trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 获取单个文件的MD5值！
     */
    public static String getOneFileMD5(File file) {

        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5value = bigInt.toString(16);
        return md5value;
    }

    /**
     * 获取文件夹中文件的MD5值,true递归子目录中的文件
     */
    public static Map<String, String> getH5PathMD5(String key, File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getH5PathMD5(key, f, listChild));
            } else {
                md5 = getOneFileMD5(f);
                if (!TextUtils.isEmpty(md5)) {
                    map.put(key, md5);
                }
            }
        }
        return map;
    }

    /**
     * asstes目录下的文件
     */
    public static boolean bAssetsFile(Context context, String pt) {
        try {
            AssetManager am = context.getAssets();
            String[] names = am.list("");
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(pt.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    public static long getFileDirSize(File file) {
        long size = 0;
        if (!file.isDirectory()) {
            return 0;
        }
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            File f = flist[i];
            if (f.isDirectory()) {
                size = size + getFileDirSize(f);
            } else {
                size = size + getOneFileSize(f);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     */
    public static long getOneFileSize(File file) {
        long size = 0;
        if (!file.isFile()) {
            return 0;
        }
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            } catch (Exception e) {
                size = 0;
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * @param path
     * @return
     */
    public static int getFileCounts(String path) {
        int lens = 0;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] fileItems = file.listFiles();
            if (fileItems != null) {
                for (int i = 0; i < fileItems.length; i++) {
                    File item = fileItems[i];
                    if (item.isFile()) {
                        lens++;
                    } else if (item.isDirectory()) {
                        File[] childFiles = item.listFiles();
                        if (childFiles != null) {
                            lens += childFiles.length;
                        }
                    }
                }
            }
        }
        return lens;
    }

    public static String readAssetFile(Context context, String fileName) {
        String text = "";
        InputStream in = null;
        try {
            in = context.getAssets().open(fileName);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(in);
        }
        return text;
    }


    public static void saveFile(Context context, final String name, final String content) {
        saveFile(context, name, content, false);
    }

    /**
     * @param name
     * @param content
     */
    public static void saveFile(Context context, final String name, final String content, final boolean append) {
        try {
            if (content == null) {
                return;
            }
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
            String sdPath = "";
            if (sdCardExist) {
                sdPath = Environment.getExternalStorageDirectory().getPath() + File.separator;// 获取跟目录
            } else {
                File cacheDir = context.getCacheDir();
                sdPath = cacheDir.getAbsolutePath() + File.separator;
            }
            File f = new File(sdPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            final String rootPath = sdPath + fileName + File.separator;
            File file = new File(rootPath + name);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new Thread() {
                @Override
                public void run() {
                    FileOutputStream os = null;
                    OutputStreamWriter osw = null;
                    BufferedWriter bw = null;
                    try {
                        os = new FileOutputStream(rootPath + name, append);
                        osw = new OutputStreamWriter(os);
                        bw = new BufferedWriter(osw);
                        bw.write(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (bw != null) {
                                bw.flush();
                            }
                            if (osw != null) {
                                osw.flush();
                            }
                            if (bw != null) {
                                bw.close();
                            }
                            if (osw != null) {
                                osw.close();
                            }
                            if (os != null) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name
     * @return
     */
    public static String readFile(String name) {
        FileInputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        File file = new File(name);
        if (!file.exists()) {
            return null;
        }
        StringBuffer res = new StringBuffer();
        try {
            is = new FileInputStream(name);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String x = null;
            while ((x = br.readLine()) != null) {
                res.append(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res.toString();
    }

    /**
     * @param filename
     * @param content
     */
    public static void writeToSd(String filename, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(content);
            bw.flush();
            osw.flush();
            bw.close();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     * @return
     */
    public static String readFromSd(Context context, String filename) {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
            android.os.Environment.MEDIA_MOUNTED);
        String sdPath = "";
        if (sdCardExist) {
            sdPath = Environment.getExternalStorageDirectory().getPath() + File.separator;// 获取跟目录
        } else {
            File cacheDir = context.getCacheDir();
            sdPath = cacheDir.getAbsolutePath() + File.separator;
        }
        File file_file = new File(sdPath + fileName + File.separator + filename);
        if (!file_file.exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(sdPath + fileName + File.separator + filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer res = new StringBuffer();
            String x = null;
            while ((x = br.readLine()) != null) {
                res.append(x);
            }
            br.close();
            isr.close();
            fis.close();
            return res.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拷贝文件
     */
    public static void copyFile(String src, String dst) throws IOException {
        FileInputStream fis = new FileInputStream(new File(src));
        BufferedInputStream inBuff = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(new File(dst));
        BufferedOutputStream outBuff = new BufferedOutputStream(fos);
        byte[] b = new byte[1024];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        outBuff.flush();
        outBuff.close();
        fos.close();
        inBuff.close();
        fis.close();
    }

    /**
     * 递归删除文件,文件夹
     */
    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return file.delete();
            }
            boolean b = true;
            for (File childFile : childFiles) {
                b &= delete(childFile);
            }
            return b & file.delete();
        }
        return false;
    }

    /**
     * 获取指定文件的大小
     *
     * @param path 需要获取的文件的绝度路径
     * @return 返回文件的大小，若是不存在则返回-1
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 压缩数据
     *
     * @param source
     * @return
     */
    public static String compress(String source) {
        if (source != null && source.length() > 0) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                GZIPOutputStream gzipos = new GZIPOutputStream(baos);
                gzipos.write(source.getBytes());
                gzipos.close();
                return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeSilently(baos);
            }
        }
        return null;
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}