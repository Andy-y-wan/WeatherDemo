package com.lmjssjj.weatherwidget.weather.rainydayweather;

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

public class RainyDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mRainyDayRainy1Texture;
    private GLImage mRainyDayRainy2Texture;
    private GLImage mRainyDayCloud1Texture;
    private GLImage mRainyDayCloud2Texture;

    public RainyDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.rainy_day_card,1.6f,-1);
        mRainyDayRainy1Texture = new GLImage(mContext, R.mipmap.rainy_day_rain1,0.95f,0.6f);
        mRainyDayRainy2Texture = new GLImage(mContext, R.mipmap.rainy_day_rain2,1.445f,-0.6f);
        mRainyDayCloud1Texture = new GLImage(mContext, R.mipmap.rainy_day_cloud1,0.88f,0.7f);
        mRainyDayCloud2Texture = new GLImage(mContext, R.mipmap.rainy_day_cloud2,1.2f,0f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.rainy_day_icon,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mRainyDayRainy1Texture.bindData(mTextureShaderProgram);
        mRainyDayRainy1Texture.draw();

        mRainyDayRainy2Texture.bindData(mTextureShaderProgram);
        mRainyDayRainy2Texture.draw();

        mRainyDayCloud1Texture.bindData(mTextureShaderProgram);
        mRainyDayCloud1Texture.draw();

        mRainyDayCloud2Texture.bindData(mTextureShaderProgram);
        mRainyDayCloud2Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mRainyDayRainy1Texture.swing(angle,x,y,z);
        mRainyDayRainy2Texture.swing(angle,x,y,z);
        mRainyDayCloud1Texture.swing(angle,x,y,z);
        mRainyDayCloud2Texture.swing(angle,x,y,z);
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
        mRainyDayRainy1Texture.touchSwing();
        mRainyDayRainy2Texture.touchSwing();
        mRainyDayCloud1Texture.touchSwing();
        mRainyDayCloud2Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
