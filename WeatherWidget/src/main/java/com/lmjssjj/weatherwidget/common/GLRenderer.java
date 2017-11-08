package com.lmjssjj.weatherwidget.common;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.model.CurrentWeather;
import com.lmjssjj.weatherwidget.shader.TextureShaderProgram;
import com.lmjssjj.weatherwidget.utils.FontTextureUtil;
import com.lmjssjj.weatherwidget.utils.MatrixState;
import com.lmjssjj.weatherwidget.utils.TextureHelper;
import com.lmjssjj.weatherwidget.utils.TimeAndDateUtil;
import com.lmjssjj.weatherwidget.weather.commomtexture.DateTexture;
import com.lmjssjj.weatherwidget.weather.commomtexture.TimeTexture;
import com.lmjssjj.weatherwidget.weather.commomtexture.WeatherTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public abstract class GLRenderer implements GLSurfaceView.Renderer,TimeAndDateUtil.TimeChangedCallback {

    public Context mContext;
    public TextureShaderProgram mTextureShaderProgram;
    public DateTexture mDateTexture;
    public TimeTexture mTimeTexture;
    public WeatherTexture mWeatherTexture;
    private TimeAndDateUtil mTimeAndDateUtil;
    private GLView glView;
    private CurrentWeather currentWeather;

    public GLRenderer(Context context){
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        mTextureShaderProgram = new TextureShaderProgram(mContext);
        MatrixState.setInitStack();
        mTimeAndDateUtil = new TimeAndDateUtil(mContext,this);
        mDateTexture = new DateTexture(mTimeAndDateUtil.getDateBitmap());
        mTimeTexture = new TimeTexture(mTimeAndDateUtil.getTimeBitmap());
        mWeatherTexture = new WeatherTexture(FontTextureUtil.createWeatherBitmap(mContext, currentWeather));
        onCreated();
    }


    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
//        MatrixState.setProjectFrustum(-1,1, -1, 1, 1, 5);
//        MatrixState.setProjectOrtho(-1,1, -1, 1, 20, 100);
        MatrixState.perspectiveM(45,ratio,1,5);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixState.setCamera(0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        onDrawFrame();

        TextureHelper.updateTexture(mDateTexture.mTextureId,mTimeAndDateUtil.getDateBitmap());
        mDateTexture.bindData(mTextureShaderProgram);
        mDateTexture.draw();

        TextureHelper.updateTexture(mTimeTexture.mTextureId,mTimeAndDateUtil.getTimeBitmap());
        mTimeTexture.bindData(mTextureShaderProgram);
        mTimeTexture.draw();

        TextureHelper.updateTexture(mWeatherTexture.mTextureId,FontTextureUtil.createWeatherBitmap(mContext,currentWeather));
        mWeatherTexture.bindData(mTextureShaderProgram);
        mWeatherTexture.draw();

        GLES20.glDisable(GLES20.GL_BLEND);

    }

    @Override
    public void timeChanged() {
        glView.requestRender();
    }

    public abstract void onCreated();
    public abstract void onDrawFrame();
    public abstract void swing(float angle,int x,int y,int z);
    public void touchSwing(float angleX,float angleY){
        mDateTexture.touchSwing();
        mTimeTexture.touchSwing();
        mWeatherTexture.touchSwing();
    }

    public void setGlView(GLView glView) {
        this.glView = glView;
    }

    public void destroy() {
        mTimeAndDateUtil.unregisterReceiver();
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
