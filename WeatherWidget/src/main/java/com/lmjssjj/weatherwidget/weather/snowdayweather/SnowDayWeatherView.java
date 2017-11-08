package com.lmjssjj.weatherwidget.weather.snowdayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;
import com.lmjssjj.weatherwidget.weather.sunnydayweather.SunnyDayRenderer;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class SnowDayWeatherView extends GLView {

    public SnowDayWeatherView(Context context) {
        super(context);
    }

    public SnowDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new SnowDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
