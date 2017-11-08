package com.lmjssjj.weatherwidget;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *
 * @author ya.wan
 * @date 2017/11/2
 */

public class WeatherAppState {

    private static final String TAG = "WeatherAppState";
    private static Context sContext;
    private static WeatherAppState INSTANCE;
    private RequestQueue mRequestQueue;
    private WeatherRequest mRequest;

    public static WeatherAppState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WeatherAppState();
        }
        return INSTANCE;
    }

    public static WeatherAppState getInstanceNoCreate() {
        return INSTANCE;
    }

    public Context getContext() {
        return sContext;
    }

    public static void setApplicationContext(Context context) {
        if (sContext != null) {
            Log.w(TAG, "setApplicationContext called twice! old=" + sContext
                    + " new=" + context);
        }
        sContext = context.getApplicationContext();
    }

    private WeatherAppState() {
        if (sContext == null) {
            throw new IllegalStateException(
                    "AppState inited before app context set");
        }
        mRequestQueue = Volley.newRequestQueue(sContext);
        mRequest = new WeatherRequest(mRequestQueue);
    }

    public void onTerminate() {
        mRequestQueue.stop();
    }

    public WeatherRequest getRequestApi() {
        return mRequest;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
