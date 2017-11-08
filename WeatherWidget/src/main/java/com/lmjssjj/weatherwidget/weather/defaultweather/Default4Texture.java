package com.lmjssjj.weatherwidget.weather.defaultweather;

import android.content.Context;

import com.lmjssjj.weatherwidget.common.GLImage;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class Default4Texture extends GLImage {

    public static float scaling = 1.1f;
    public static float depth = 0.3f;

    public static float[] vertex_position_xyz = {
            //x y z
            -unit*aspect*scaling,unit*scaling,depth,
            -unit*aspect*scaling,-unit*scaling,depth,
            unit*aspect*scaling,unit*scaling,depth,
            unit*aspect*scaling,-unit*scaling,depth,
    };
    public static float[] vertex_content_st = {
            //st
            0,0,
            0,1,
            1,0,
            1,1

    };


    public Default4Texture(Context context, int resId) {
        super(context, resId, vertex_position_xyz, vertex_content_st);
    }

}
