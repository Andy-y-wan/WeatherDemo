package com.lmjssjj.weatherwidget.weather.overcastdayweather;

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

public class OvercastDayRenderer extends GLRenderer {

    private GLImage mBgCardTexture;
    private GLImage mWeatherIconTexture;
    private GLImage mOvercastDay1Texture;
    private GLImage mOvercastDay2Texture;
    private GLImage mOvercastDay3Texture;

    public OvercastDayRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new GLImage(mContext, R.mipmap.overcast_day_card,1.6f,-1);
        mOvercastDay1Texture = new GLImage(mContext, R.mipmap.overcast_day_1,1.2f,0f);
        mOvercastDay2Texture = new GLImage(mContext, R.mipmap.overcast_day_2,1.1f,0.3f);
        mOvercastDay3Texture = new GLImage(mContext, R.mipmap.overcast_day_3,1.1f,0.3f);
        mWeatherIconTexture = new GLImage(mContext, R.mipmap.overcast_day_icon,0.9f,0.8f);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mOvercastDay1Texture.bindData(mTextureShaderProgram);
        mOvercastDay1Texture.draw();

        mOvercastDay2Texture.bindData(mTextureShaderProgram);
        mOvercastDay2Texture.draw();

        mOvercastDay3Texture.bindData(mTextureShaderProgram);
        mOvercastDay3Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mOvercastDay1Texture.swing(angle,x,y,z);
        mOvercastDay2Texture.swing(angle,x,y,z);
        mOvercastDay3Texture.swing(angle,x,y,z);
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
        mOvercastDay1Texture.touchSwing();
        mOvercastDay2Texture.touchSwing();
        mOvercastDay3Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
