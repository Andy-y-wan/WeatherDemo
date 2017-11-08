package com.lmjssjj.weatherwidget.weather.fogdayweather;

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

public class FogDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mFogDay1Texture;
    private GLImage mFogDay2Texture;
    private GLImage mFogDay3Texture;

    public FogDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.fog_day_card,1.6f,-1);
        mFogDay1Texture = new GLImage(mContext, R.mipmap.fog_day_1,1.15f,0f);
        mFogDay2Texture = new GLImage(mContext, R.mipmap.fog_day_2,1.15f,0f);
        mFogDay3Texture = new GLImage(mContext, R.mipmap.fog_day_3,1.045f,0.3f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.fog_day_icon,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mFogDay1Texture.bindData(mTextureShaderProgram);
        mFogDay1Texture.draw();

        mFogDay2Texture.bindData(mTextureShaderProgram);
        mFogDay2Texture.draw();

        mFogDay3Texture.bindData(mTextureShaderProgram);
        mFogDay3Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mFogDay1Texture.swing(angle,x,y,z);
        mFogDay2Texture.swing(angle,x,y,z);
        mFogDay3Texture.swing(angle,x,y,z);
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
        mFogDay1Texture.touchSwing();
        mFogDay2Texture.touchSwing();
        mFogDay3Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
