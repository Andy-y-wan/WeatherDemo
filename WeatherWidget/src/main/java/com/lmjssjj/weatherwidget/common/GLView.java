package com.lmjssjj.weatherwidget.common;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lmjssjj.weatherwidget.model.CurrentWeather;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public abstract class GLView extends GLSurfaceView {

    public GLRenderer mRenderer;
    public Context mContext;
    private float mMaxRotate = 15;
    private float mRotateX;
    private float mRotateY;
    public CurrentWeather mWeather;

    public GLView(Context context){
        this(context,null);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public void init(Context context){
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        setRenderer();
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                stopRockAnim();
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.v("lmjssjj","MotionEvent.ACTION_DOWN");
                        Log.v("lmjssjj","e.getX():"+e.getX()+"  --  e.getY():"+e.getY());
                        Log.v("lmjssjj","e.getX()/getWidth():"+e.getX()/getWidth()+"  --  e.getY()/getHeight():"+e.getY()/getHeight());
                        getRotate(e);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Log.v("lmjssjj","MotionEvent.ACTION_MOVE");
                        getRotate(e);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        resetRotate();
                        Log.v("lmjssjj","MotionEvent.ACTION_UP");
                        break;
                }
                requestRender();
//                stopRockAnim();
//                swing(1,1,0);
                return true;
            }
        });
        mRenderer.setGlView(this);

    }

    public abstract void setRenderer();

    public ValueAnimator mRockAnim;
    public void swing(final int x,final int y,final int z){
        stopRockAnim();
        mRockAnim = ValueAnimator.ofFloat(0,10,0,-2,0);
        mRockAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float angle = (float) valueAnimator.getAnimatedValue();
//                Log.v("lmjssjj",angle+"---------");
                mRenderer.swing(angle,x,y,z);
                requestRender();
            }
        });
        mRockAnim.setDuration(1000*2);
        mRockAnim.start();
    }

    private ValueAnimator getShakeAnim(float start, float end) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end,-start/2f,0);
//        anim.setInterpolator(new OvershootInterpolator(10));
        anim.setDuration(800);
        anim.start();
        return anim;
    }

    private void getRotate(MotionEvent event) {
        float rotateX = (event.getY() - getHeight() / 2);
        float rotateY = (event.getX() - getWidth() / 2);
//        Log.v("lmjssjj","rotateX:"+rotateX+"  --  rotateY:"+rotateY);
        //求出此时旋转的大小与半径之比
        float percentX = rotateX / getWidth() / 2;
        float percentY = rotateY / getHeight() / 2;
        if (percentX > 1) {
            percentX = 1;
        } else if (percentX < -1) {
            percentX = -1;
        }
        if (percentY > 1) {
            percentY = 1;
        } else if (percentY < -1) {
            percentY = -1;
        }
        //最终旋转的大小按比例匀称改变
        mRotateX = percentX * mMaxRotate;
        mRotateY = percentY * mMaxRotate;
        mRenderer.touchSwing(mRotateX,mRotateY);
//        Log.v("lmjssjj","mRotateX:"+mRotateX+"  --  mRotateY:"+mRotateY);
    }

    public void resetRotate(){
        stopRockAnim();
        mRockAnim = ValueAnimator.ofFloat(1f,0f,-0.3f,0);
        mRockAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();

                mRenderer.touchSwing(mRotateX*value,mRotateY*value);
                requestRender();
            }
        });
        mRockAnim.setDuration(800);
        mRockAnim.start();
    }

    private ValueAnimator mShakeAnimX;
    private ValueAnimator mShakeAnimY;
    public void resetRotate1(){
        mShakeAnimX = getShakeAnim(mRotateX, 0);
        mShakeAnimX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRotateX = (float) valueAnimator.getAnimatedValue();
                mRenderer.touchSwing(mRotateX,mRotateY);
                requestRender();
            }
        });
        mShakeAnimY = getShakeAnim(mRotateY, 0);
        mShakeAnimY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRotateY = (float) valueAnimator.getAnimatedValue();
                mRenderer.touchSwing(mRotateX,mRotateY);
                requestRender();
            }
        });
    }

    public void stopRockAnim(){
        if(mRockAnim!=null&&mRockAnim.isRunning()){
            mRockAnim.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRenderer.destroy();
    }
}
