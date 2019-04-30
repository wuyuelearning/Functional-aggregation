package utils.bring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import android.text.TextUtils;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Class Name: JsonUtil.java
 *
 * @author yangyang DateTime 2013-5-11 上午10:06:47
 * @version 1.0
 */
public class JsonUtil {
    private static final boolean ERROR_PRINTABLE = BuildConstant.DEBUG;

    private static Gson gson = gsonBuilder().create();

    //fix Gson 之前jar包无setLenient方法
    private static GsonBuilder gsonBuilder() {
        GsonBuilder mBuilder = new GsonBuilder();
        mBuilder.disableHtmlEscaping();
        if (!TextUtils.isEmpty(invokeMethod())) {
            mBuilder.setLenient();
        }
        return mBuilder;
    }

    private static String invokeMethod() {
        try {
            //动态加载类，获取当前类的Class对象
            Class<?> clazz = Class.forName(GsonBuilder.class.getName());
            //获取本类的所有方法，存放入数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if ("setLenient".equals(methodName)) {
                    return methodName;
                }
            }
        } catch (ClassNotFoundException ce) {
            if (ERROR_PRINTABLE) {
                ce.printStackTrace();
            }
            return null;
        } catch (SecurityException se) {
            if (ERROR_PRINTABLE) {
                se.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            if (ERROR_PRINTABLE) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    @SuppressWarnings("hiding")
    public static <T> T parseJson(String response, Class<T> clazz) {
        try {
            return gson.fromJson(response, clazz);
        } catch (Exception e) {
            if (ERROR_PRINTABLE) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //781 add lhw
    public static <T> T parseJsonReader(JsonReader jsonReader, Class<T> clazz) {
        try {
            return gson.fromJson(jsonReader, clazz);
        } catch (Exception e) {
            if (ERROR_PRINTABLE) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static JsonReader setJsLenient(String response) {
        JsonReader reader = new JsonReader(new StringReader(response));
        reader.setLenient(true);
        return reader;
    }

    public static <T> T parseJson(String response, Type type) {
        try {
            return gson.fromJson(response, type);
        } catch (Exception e) {
            if (ERROR_PRINTABLE) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            if (ERROR_PRINTABLE) {
                e.printStackTrace();
            }
            return null;
        }
    }
}