package com.lmjssjj.weatherwidget.weather;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.lmjssjj.weatherwidget.common.GLRenderer;
import com.lmjssjj.weatherwidget.weather.defaultweather.DefaultRenderer;

import java.lang.reflect.Constructor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class ChooseRender implements GLSurfaceView.Renderer {

    private GLRenderer mRendererWeather;
    private Class<? extends GLRenderer> mClazz=DefaultRenderer.class;
    private Context mContext;

    public ChooseRender(Context context) {
        this.mContext = context;
    }

    public void setWeather(Class<? extends GLRenderer> clazz){
        this.mClazz = clazz;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,0f);
        Log.e("lmjssjj","onSurfaceCreated");
        try {
            Constructor constructor=mClazz.getDeclaredConstructor(Context.class);
            constructor.setAccessible(true);
            mRendererWeather= (GLRenderer) constructor.newInstance(mContext);
        } catch (Exception e) {
            e.printStackTrace();
            mRendererWeather=new DefaultRenderer(mContext);
        }
        mRendererWeather.onSurfaceCreated(gl10,eglConfig);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
