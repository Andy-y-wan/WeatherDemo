package com.lmjssjj.weatherwidget.weather.snowdayweather;

import android.content.Context;

import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.common.GLImage;
import com.lmjssjj.weatherwidget.common.GLRenderer;
import com.lmjssjj.weatherwidget.utils.MatrixState;
import com.lmjssjj.weatherwidget.weather.commomtexture.BgCardTexture;
import com.lmjssjj.weatherwidget.weather.commomtexture.WeatherIconTexture;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class SnowDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mSnowDaySnow1Texture;
    private GLImage mSnowDaySnow2Texture;

    public SnowDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.snow_day_card,1.6f,-1);
        mSnowDaySnow1Texture = new GLImage(mContext, R.mipmap.snow_day_snow1,1.15f,-0.3f);
        mSnowDaySnow2Texture = new GLImage(mContext, R.mipmap.snow_day_snow2,1.05f,0.3f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.snow_day_icon,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mSnowDaySnow1Texture.bindData(mTextureShaderProgram);
        mSnowDaySnow1Texture.draw();

        mSnowDaySnow2Texture.bindData(mTextureShaderProgram);
        mSnowDaySnow2Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mSnowDaySnow1Texture.swing(angle,x,y,z);
        mSnowDaySnow2Texture.swing(angle,x,y,z);
        mWeatherIconTexture.swing(angle,x,y,z);
        MatrixState.popMatrix();
    }

    @Override
    public void touchSwing(float angleX,float angleY){
        MatrixState.pushMatrix();
        if(angleX!=0){
            MatrixState.rotate(angleX,1,0,0);
        }
        if(angleY!=0){
            MatrixState.rotate(angleY,0,1,0);
        }
        super.touchSwing(angleX,angleY);
        mBgCardTexture.touchSwing();
        mWeatherIconTexture.touchSwing();
        mSnowDaySnow1Texture.touchSwing();
        mSnowDaySnow2Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
