package com.lmjssjj.weatherwidget.weather.sunnynightweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class SunyNightWeatherView extends GLView {

    public SunyNightWeatherView(Context context) {
        super(context);
    }

    public SunyNightWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new SunnyNightRenderer(mContext);
        setRenderer(mRenderer);
    }
}
