package ProjectUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


import java.io.StringReader;
import java.lang.reflect.Type;



public class JsonUtil {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setLenient()
            .create();
    private static final boolean ERROR_PRINTABLE = BuildConstant.DEBUG;

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