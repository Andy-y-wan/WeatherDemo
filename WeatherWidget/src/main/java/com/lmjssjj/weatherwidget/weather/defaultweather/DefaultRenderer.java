package com.lmjssjj.weatherwidget.weather.defaultweather;

import android.content.Context;

import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.common.GLRenderer;
import com.lmjssjj.weatherwidget.utils.MatrixState;
import com.lmjssjj.weatherwidget.weather.commomtexture.BgCardTexture;
import com.lmjssjj.weatherwidget.weather.commomtexture.WeatherIconTexture;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class DefaultRenderer extends GLRenderer {

    private BgCardTexture mBgCardTexture;
    private WeatherIconTexture mWeatherIconTexture;
    private Default1Texture mDefault1Texture;
    private Default2Texture mDefault2Texture;
    private Default3Texture mDefault3Texture;
    private Default4Texture mDefault4Texture;

    public DefaultRenderer(Context context){
        super(context);
    }

    @Override
    public void onCreated() {
        mBgCardTexture = new BgCardTexture(mContext, R.mipmap.unknown_card);
        mDefault1Texture = new Default1Texture(mContext, R.mipmap.unknown_1);
        mDefault2Texture = new Default2Texture(mContext, R.mipmap.unknown_2);
        mDefault3Texture = new Default3Texture(mContext, R.mipmap.unknown_3);
        mDefault4Texture = new Default4Texture(mContext, R.mipmap.unknown_4);
        mWeatherIconTexture = new WeatherIconTexture(mContext, R.mipmap.unknown_icon);
    }

    @Override
    public void onDrawFrame() {
        mTextureShaderProgram.useProgram();
        mBgCardTexture.bindData(mTextureShaderProgram);
        mBgCardTexture.draw();

        mDefault1Texture.bindData(mTextureShaderProgram);
        mDefault1Texture.draw();

        mDefault2Texture.bindData(mTextureShaderProgram);
        mDefault2Texture.draw();

        mDefault3Texture.bindData(mTextureShaderProgram);
        mDefault3Texture.draw();

        mDefault4Texture.bindData(mTextureShaderProgram);
        mDefault4Texture.draw();

        mWeatherIconTexture.bindData(mTextureShaderProgram);
        mWeatherIconTexture.draw();
    }

    @Override
    public void swing(float angle,int x,int y,int z) {
        MatrixState.pushMatrix();
        MatrixState.rotate(angle,x,y,z);
        mBgCardTexture.swing(angle,x,y,z);
        mDefault1Texture.swing(angle,x,y,z);
        mDefault2Texture.swing(angle,x,y,z);
        mDefault3Texture.swing(angle,x,y,z);
        mDefault4Texture.swing(angle,x,y,z);
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
        mDefault1Texture.touchSwing();
        mDefault2Texture.touchSwing();
        mDefault3Texture.touchSwing();
        mDefault4Texture.touchSwing();

        MatrixState.popMatrix();
    }

}
