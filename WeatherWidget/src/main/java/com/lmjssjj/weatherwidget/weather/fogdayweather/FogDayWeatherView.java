package com.lmjssjj.weatherwidget.weather.fogdayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class FogDayWeatherView extends GLView {

    public FogDayWeatherView(Context context) {
        super(context);
    }

    public FogDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new FogDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
