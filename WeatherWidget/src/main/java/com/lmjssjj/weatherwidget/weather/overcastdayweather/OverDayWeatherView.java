package com.lmjssjj.weatherwidget.weather.overcastdayweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class OverDayWeatherView extends GLView {

    public OverDayWeatherView(Context context) {
        super(context);
    }

    public OverDayWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new OvercastDayRenderer(mContext);
        setRenderer(mRenderer);
    }
}
