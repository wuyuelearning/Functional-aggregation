package utils.bring;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public final class SharedPreferencesHelper {
    private static final String ACTIVED_NAME = "active_info";//lib_storage#CommentDraftModel对应同步修改

    public static void saveBoolean(Context context, String key, boolean values) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putBoolean(key, values);
            editor.apply();
        }
    }

    public static boolean readBoolean(Context context, String key) {
        if (context == null) {
            return false;
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getBoolean(key, false);
    }

    public static boolean readBoolean(Context context, String key, boolean defaultValue) {
        if (context == null) {
            return false;
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getBoolean(key, defaultValue);
    }

    public static void saveInt(Context context, String key, int values) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putInt(key, values);
            editor.apply();
        }
    }

    public static int readInt(Context context, String key) {
        if (context == null) {
            return 0;
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getInt(key, 0);
    }

    public static void saveLong(Context context, String key, long values) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putLong(key, values);
            editor.apply();
        }
    }

    public static long readLong(Context context, String key) {
        if (context == null) {
            return 0L;
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getLong(key, 0);
    }

    public static void saveFloat(Context context, String key, float values) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putFloat(key, values);
            editor.apply();
        }
    }

    public static float readFloat(Context context, String key) {
        if (context == null) {
            return 0f;
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getFloat(key, 0);
    }

    public static void save(Context context, String key, String values) {
        if (context != null) {
//            L.d("SharedPreferencesHelper save():" + context + ",," + key + ",," + values);
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putString(key, values);
            editor.apply();
        }
    }

    public static String read(Context context, String key) {
        if (context == null) {
            return "";
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getString(key, null);
    }

    public static void saveList(Context context, String key, List<String> values) {
        String valueArr = "";
        if (values != null && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                valueArr += values.get(i) + ";";
            }
            valueArr.substring(0, values.size() - 1);
        }
        save(context, key, valueArr);
    }

    public static List<String> readList(Context context, String key) {
        String valueList = read(context, key);
        List<String> list = new ArrayList<>();
        if (!StringUtil.equalsNullOrEmpty(valueList)) {
            String[] valueArr = valueList.split(";");
            for (int i = 0; i < valueArr.length; i++) {
                list.add(valueArr[i]);
            }
        }
        return list;
    }

    public static void saveString(Context context, String key, String values) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.putString(key, values);
            editor.apply();
        }
    }

    public static String readString(Context context, String key) {
        if (context == null) {
            return "";
        }
        SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
        return pref.getString(key, null);
    }

    public static void remove(Context context, String key) {
        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
            Editor editor = pref.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public static SharedPreferences getPreferences(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        return context.getSharedPreferences(ACTIVED_NAME, Context.MODE_APPEND);
    }
}