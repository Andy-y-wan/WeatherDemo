package com.lmjssjj.weatherwidget.weather.rainydayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;
import com.lmjssjj.weatherwidget.weather.snowdayweather.SnowDayRenderer;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class RainyDayWeatherView extends GLView {

    public RainyDayWeatherView(Context context) {
        super(context);
    }

    public RainyDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new RainyDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
