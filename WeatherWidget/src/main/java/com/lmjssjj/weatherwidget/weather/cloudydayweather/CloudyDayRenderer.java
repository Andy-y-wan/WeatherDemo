package com.lmjssjj.weatherwidget.weather.cloudydayweather;

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

public class CloudyDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mCloudyDay1Texture;
    private GLImage mCloudyDay2Texture;
    private GLImage mCloudyDay3Texture;

    public CloudyDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.cloudy_day_card,1.6f,-1);
        mCloudyDay1Texture = new GLImage(mContext, R.mipmap.cloudy_day_1,1.15f,0f);
        mCloudyDay2Texture = new GLImage(mContext, R.mipmap.cloudy_day_2,0.85f,0.8f);
        mCloudyDay3Texture = new GLImage(mContext, R.mipmap.cloudy_day_3,1.0f,0.5f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.cloudy_day_icon,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mCloudyDay1Texture.bindData(mTextureShaderProgram);
        mCloudyDay1Texture.draw();

        mCloudyDay2Texture.bindData(mTextureShaderProgram);
        mCloudyDay2Texture.draw();

        mCloudyDay3Texture.bindData(mTextureShaderProgram);
        mCloudyDay3Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mCloudyDay1Texture.swing(angle,x,y,z);
        mCloudyDay2Texture.swing(angle,x,y,z);
        mCloudyDay3Texture.swing(angle,x,y,z);
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
        mCloudyDay1Texture.touchSwing();
        mCloudyDay2Texture.touchSwing();
        mCloudyDay3Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
