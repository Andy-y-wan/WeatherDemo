package com.lmjssjj.weatherwidget.weather.commomtexture;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.lmjssjj.weatherwidget.common.GLImage;
import com.lmjssjj.weatherwidget.shader.TextureShaderProgram;

/**
 * Created by junjie.shi on 2017/11/2.
 */

public class TimeTexture extends GLImage {

    public static float scaling = 1f;
    public static float depth = 0.5f;

    public static float[] vertex_position_xyz = {
        //x y z
            -unit*aspect*scaling,unit*scaling,depth,
            -unit*aspect*scaling,-unit*scaling,depth,
            unit*aspect*scaling,unit*scaling,depth,
            unit*aspect*scaling,-unit*scaling,depth,
    };
    public static float[] vertex_content_st = {
            //st
            0,0,
            0,1,
            1,0,
            1,1

    };


    public TimeTexture(Context context, int resId) {
        super(context, resId, vertex_position_xyz, vertex_content_st);
    }

    public TimeTexture(Bitmap bitmap) {
        super(bitmap, vertex_position_xyz, vertex_content_st);
    }

    @Override
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


}
