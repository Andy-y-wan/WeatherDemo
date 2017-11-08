package com.lmjssjj.weatherwidget.weather.sunnydayweather;

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

public class SunnyDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mSunyDayCloudRightTexture;
    private GLImage mSunyDayCloudLeftTexture;
    private GLImage mSunyDayBirdTexture;

    public SunnyDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.sunny_day_1,1.6f,-1);
        mSunyDayCloudRightTexture = new GLImage(mContext, R.mipmap.sunny_day_2,0.85f,0.6f);
        mSunyDayCloudLeftTexture = new GLImage(mContext, R.mipmap.sunny_day_4,1.1f,0.2f);
        mSunyDayBirdTexture = new GLImage(mContext, R.mipmap.sunny_day_5_2,1.22f,0f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.sunny_day_7,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mSunyDayCloudRightTexture.bindData(mTextureShaderProgram);
        mSunyDayCloudRightTexture.draw();
//
        mSunyDayCloudLeftTexture.bindData(mTextureShaderProgram);
        mSunyDayCloudLeftTexture.draw();
//
        mSunyDayBirdTexture.bindData(mTextureShaderProgram);
        mSunyDayBirdTexture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mSunyDayCloudRightTexture.swing(angle,x,y,z);
        mSunyDayCloudLeftTexture.swing(angle,x,y,z);
        mSunyDayBirdTexture.swing(angle,x,y,z);
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
        mSunyDayBirdTexture.touchSwing();
        mSunyDayCloudRightTexture.touchSwing();
        mSunyDayCloudLeftTexture.touchSwing();

        MatrixState.popMatrix();
    }

}
