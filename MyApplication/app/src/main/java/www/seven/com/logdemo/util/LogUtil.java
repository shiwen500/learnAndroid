package www.seven.com.logdemo.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by Seven on 2016/3/29.
 */
public class LogUtil {

    private static final boolean mIsOpen = true;

    public static String getClassName() {

//        StackTraceElement[] s = (new Exception()).getStackTrace();
//        for (StackTraceElement s1: s) {
//            Log.d("DEBUG", s1.getClassName());
//        }

        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    public static void d(@Nullable String info, Object... args) {
        if (!mIsOpen) {
            // 如果把开关关闭了，那么就不进行打印
            return;
        }
        Logger.d(info, args);
    }

    public static void d(@NonNull String tag, String info, Object... args) {


        Log.d(tag, info);
        Logger.t(tag).d(info, args);
    }
}
