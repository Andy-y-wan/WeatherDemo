package com.lmjssjj.weatherwidget.weather.sunnydayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class SunyDayWeatherView extends GLView {

    public SunyDayWeatherView(Context context) {
        super(context);
    }

    public SunyDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new SunnyDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
