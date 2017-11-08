package com.lmjssjj.weatherwidget.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lmjssjj.weatherwidget.StartupReceiver;

import java.lang.reflect.Method;

/**
 * Created by ya.wan on 2017/11/3.
 */

public class Utilities {

    private static final String TAG = "Utilities";

    public static boolean isNycOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    // TODO: use the full N name (e.g. ATLEAST_N*****) when available
    public static final boolean ATLEAST_N = Build.VERSION.SDK_INT >= 24;
    // TODO: use Build.VERSION_CODES when available
    public static final boolean ATLEAST_MARSHMALLOW = Build.VERSION.SDK_INT >= 23;

    public static final boolean ATLEAST_LOLLIPOP_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;

    public static final boolean ATLEAST_LOLLIPOP =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    public static final boolean ATLEAST_KITKAT =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static final boolean ATLEAST_JB_MR1 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;

    public static final boolean ATLEAST_JB_MR2 =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl(Resources res) {
        return ATLEAST_JB_MR1 &&
                (res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    /**
     *
     * @Title: setupTransparentSystemBars
     * @Description: setupTransparentSystemBars

     * @author: y.wan
     * @date:  2017年6月9日 下午2:12:51
     * @param: @param mActivity
     * @return: void
     * @throws
     */
    public static void setupTransparentSystemBars(Activity mActivity) {
        mActivity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Utilities.ATLEAST_LOLLIPOP) {
            Window window = ((Activity) mActivity).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    /**
     *
     * @Title: isBootCompleted
     * @Description: TODO(这里用一句话描述这个方法的作用)

     * @author: lmjssjj
     * @date:  2017年6月16日 下午2:14:20
     * @param: @return
     * @return: boolean
     * @throws
     */
    public static boolean isBootCompleted() {
        return "1".equals(getSystemProperty("sys.boot_completed", "1"));
    }
    /**
     * @Title: getSystemProperty
     * @Description: TODO(这里用一句话描述这个方法的作用)

     * @author: lmjssjj
     * @date:  2017年6月16日 下午2:14:11
     * @param: @param property
     * @param: @param defaultValue
     * @param: @return
     * @return: String
     * @throws
     */
    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method getter = clazz.getDeclaredMethod("get", String.class);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to read system properties");
        }
        return defaultValue;
    }
    /**
     * @Title: aaa
     * @Description: TODO(这里用一句话描述这个方法的作用)

     * @author: lmjssjj
     * @date:  2017年6月16日 下午2:13:41
     * @param:
     * @return: void
     * @throws
     */
    public static boolean isSdCardReady(Context context) {
        if(isNycOrAbove()){
            return isBootCompleted();
        }else{
            return context.registerReceiver(null,
                    new IntentFilter(StartupReceiver.SYSTEM_READY)) != null;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }else{
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if(networkInfo!=null){
                return networkInfo.isAvailable();
            }else{
                return false;
            }
        }
    }

}
