package com.lmjssjj.weatherwidget;

import android.app.Application;

/**
 *
 * @author ya.wan
 * @date 2017/11/2
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WeatherAppState.setApplicationContext(this);
        WeatherAppState.getInstance();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        WeatherAppState.getInstance().onTerminate();
    }
}
