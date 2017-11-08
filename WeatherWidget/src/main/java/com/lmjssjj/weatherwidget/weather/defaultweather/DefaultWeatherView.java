package com.lmjssjj.weatherwidget.weather.defaultweather;

import android.content.Context;
import android.util.AttributeSet;

import com.lmjssjj.weatherwidget.common.GLView;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class DefaultWeatherView extends GLView {

    public DefaultWeatherView(Context context) {
        super(context);
    }

    public DefaultWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer() {
        mRenderer = new DefaultRenderer(mContext);
        setRenderer(mRenderer);
    }
}
