package com.lmjssjj.weatherwidget.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.lmjssjj.weatherwidget.shader.TextureShaderProgram;
import com.lmjssjj.weatherwidget.utils.MatrixState;
import com.lmjssjj.weatherwidget.utils.TextureHelper;
import com.lmjssjj.weatherwidget.utils.VertexArray;

/**
 * Created by junjie.shi on 2017/11/1.
 */

public class GLImage {

    public static float aspect = 1.7647f;
    public static float unit = 1f;
    public static float mScaling = 0f;
    public static float mDepth = 0f;

    public int mTextureId;
    public VertexArray mVertexPositionArray;
    public VertexArray mVertexContentArray;
    public static float[] mProjectionMatrix = new float[16];
    public static float[] mCameraMatrix = new float[16];
    public static float[] mConversion = new float[16];

    public static float[] vertex_position_xyz = null;
    public static float[] vertex_content_st = {
            //st
            0,0,
            0,1,
            1,0,
            1,1
    };

    static {
        Matrix.setIdentityM(mConversion,0);
    }

    public GLImage(Context context, int resId){
        mTextureId = TextureHelper.loadTexture(context, resId);
        mVertexPositionArray = new VertexArray(vertex_position_xyz);
        mVertexContentArray = new VertexArray(vertex_content_st);
    }

    public GLImage(Bitmap bitmap,float[] position,float[] content){
        mTextureId = TextureHelper.loadTexture(bitmap);
        mVertexPositionArray = new VertexArray(position);
        mVertexContentArray = new VertexArray(content);
    }

    public GLImage(Context context, int resId,float[] position,float[] content){
        mTextureId = TextureHelper.loadTexture(context, resId);
        mVertexPositionArray = new VertexArray(position);
        mVertexContentArray = new VertexArray(content);
    }

    public GLImage(Context context, int resId,float scaling,float depth){
        mScaling = scaling;
        mDepth = depth;
        initVertex();
        mTextureId = TextureHelper.loadTexture(context, resId);
        mVertexPositionArray = new VertexArray(vertex_position_xyz);
        mVertexContentArray = new VertexArray(vertex_content_st);
    }

    private void initVertex(){
        vertex_position_xyz = new float[]{
                //x y z
                -unit*aspect*mScaling,unit*mScaling,mDepth,
                -unit*aspect*mScaling,-unit*mScaling,mDepth,
                unit*aspect*mScaling,unit*mScaling,mDepth,
                unit*aspect*mScaling,-unit*mScaling,mDepth,
        };
    }

    public void bindData(TextureShaderProgram textureShaderProgram) {
        mVertexPositionArray.setVertexAttribPointer(0,
                textureShaderProgram.getPositionAttributeLocation(),
                3, 0);
        mVertexContentArray.setVertexAttribPointer(0,
                textureShaderProgram.getTextureCoordinatesLocation(),
                2, 0);

        GLES20.glUniformMatrix4fv(textureShaderProgram.getTextureMatrixLocation(), 1, false, getGlobalMatrix(), 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(textureShaderProgram.getTextureUnitLocation(), 0);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }

    public void resetMatrix(){

    }

    public void setConversion(float[] conversion){
        System.arraycopy(conversion,0,mConversion,0,conversion.length);
    }



    public float[] getGlobalMatrix(){
        float[] temp = new float[16];
//        Matrix.setIdentityM(temp,0);
//        Matrix.multiplyMM(temp, 0, mCameraMatrix, 0, mConversion, 0);
//        Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, temp, 0);
        Matrix.multiplyMM(temp, 0, MatrixState.getCaMatrix(), 0, mConversion, 0);
        Matrix.multiplyMM(temp, 0, MatrixState.getProjMatrix(), 0, temp, 0);
        return temp;
    }

    public static void translate(float x,float y,float z)//设置沿xyz轴移动
    {
        Matrix.translateM(mConversion, 0, x, y, z);
    }

    public static void rotate(float angle,float x,float y,float z)//设置绕xyz轴移动
    {
        Matrix.rotateM(mConversion,0,angle,x,y,z);
    }

    public static void scale(float x,float y,float z)
    {
        Matrix.scaleM(mConversion,0, x, y, z);
    }


    public void swing(float angle,int x,int y,int z ){
        setConversion(MatrixState.getMMatrix());
    }

    public void touchSwing(){
        setConversion(MatrixState.getMMatrix());
    }

    public void recycle(){
        TextureHelper.deleteTexture(new int[]{mTextureId});
    }

}
