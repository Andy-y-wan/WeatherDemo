package com.lmjssjj.weatherwidget.weather.cloudydayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;
import com.lmjssjj.weatherwidget.weather.fogdayweather.FogDayRenderer;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class CloudyDayWeatherView extends GLView {

    public CloudyDayWeatherView(Context context) {
        super(context);
    }

    public CloudyDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new CloudyDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
