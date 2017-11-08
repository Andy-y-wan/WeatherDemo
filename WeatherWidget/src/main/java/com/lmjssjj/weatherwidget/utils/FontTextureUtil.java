package com.lmjssjj.weatherwidget.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.model.CurrentWeather;

public class FontTextureUtil {
    private static final Canvas sCanvas = new Canvas();
    private static  int WIDTH = -1;
    private static  int HEIGHT = -1;
    private static void initBitmapSize(Context context) {
        if (WIDTH ==-1 || HEIGHT ==-1) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sunny_day_1, options);
            WIDTH = bitmap.getWidth();
            HEIGHT = bitmap.getHeight();
            bitmap.recycle();
        }
    }

    /**
     * Create a bitmap from  text.
     * @param context
     * @param fontSize
     * @param fontPath path from assets dir.
     * @param fontColor
     * @param text
     * @return the texture of text.
     */
    public static Bitmap createFontBitmap(Context context,float fontSize,
                                         String fontPath, int fontColor,String text) {


        Paint p = new Paint();
        //字体设置
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath/*"fonts/Akrobat-Regular.otf"*/);
        //消除锯齿
        p.setAntiAlias(true);
        //字体为红色
        p.setColor(fontColor);
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        p.setTextAlign(Paint.Align.CENTER);
        //绘制字体
        int width = (int) getFontlength(p,text);
        int height = (int) getFontHeight(p);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);
        canvas.drawText(text,width/2, getFontLeading(p), p);
        canvas.setBitmap(null);
        return bitmap;
    }

    /**
     * Create a bitmap from  text with the special width and height.
     * @param context
     * @param width
     * @param height
     * @param fontSize
     * @param baseLine
     * @param fontPath path from assets dir.
     * @param fontColor
     * @param text
     * @return the texture of text.
     */
    public static Bitmap createFontBitmap(Context context,int width,int height,float fontSize,
                                         float baseLine,String fontPath, int fontColor,String text) {


        Paint p = new Paint();
        //字体设置
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath/*"fonts/Akrobat-Regular.otf"*/);
        //消除锯齿
        p.setAntiAlias(true);
        //字体为红色
        p.setColor(fontColor);
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        p.setTextAlign(Paint.Align.CENTER);
        //绘制字体
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);
        canvas.drawText(text,width/2,baseLine, p);
        canvas.setBitmap(null);
        return bitmap;
    }

    public static Bitmap createWeatherBitmap(Context context, CurrentWeather weather) {
        initBitmapSize(context);

        float weatherTextSize = context.getResources().getDimension(R.dimen.weather_text_size);
        Bitmap weatherBitmap = createFontBitmap(context,weatherTextSize,"Roboto-Bold.ttf",
                Color.WHITE, weather == null ? "N/A" : weather.temperature + " " + weather.weatherData);

        float locationTextSize = context.getResources().getDimension(R.dimen.location_text_size);
        Bitmap locationBitmap = createFontBitmapWithIcon(context,locationTextSize,"Roboto-Regular.ttf",
                Color.parseColor("#B3FFFFFF"), weather == null ? "N/A" : weather.city, R.drawable.location_icon);

        final Canvas canvas = sCanvas;
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        int paddingEnd = context.getResources().getDimensionPixelSize(R.dimen.weather_text_paddingEnd);
        int paddingTop = context.getResources().getDimensionPixelSize(R.dimen.weather_text_paddingTop);
        int left = WIDTH - paddingEnd - weatherBitmap.getWidth();
        int top = paddingTop;
        Rect rect = new Rect(left,top,left + weatherBitmap.getWidth(),top + weatherBitmap.getHeight());
        canvas.drawBitmap(weatherBitmap,null,rect,null);

        int margin = context.getResources().getDimensionPixelSize(R.dimen.location_text_marginTop);
        left = WIDTH - paddingEnd - locationBitmap.getWidth();
        top = paddingTop + weatherBitmap.getHeight() + margin;
        rect.set(left,top,left+locationBitmap.getWidth(),top + locationBitmap.getHeight());
        canvas.drawBitmap(locationBitmap,null,rect,null);

        canvas.setBitmap(null);
        return bitmap;
    }

    /**
     * Create a bitmap with text and other icon.
     * @param context
     * @param fontSize
     * @param fontPath  path from assets dir.
     * @param fontColor
     * @param text
     * @param iconRes The resource id of icon that drew on the left of text.
     * @return the texture of text.
     */
    public static Bitmap createFontBitmapWithIcon(Context context,float fontSize,
                                           String fontPath, int fontColor,String text,int iconRes) {


        Paint p = new Paint();
        //字体设置
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath/*"fonts/Akrobat-Regular.otf"*/);
        //消除锯齿
        p.setAntiAlias(true);
        //字体为红色
        p.setColor(fontColor);
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        p.setTextAlign(Paint.Align.CENTER);
        //绘制字体
        int height = (int) getFontHeight(p);
        int width = (int) getFontlength(p,text) + ((iconRes != -1)?height:0)/*图标宽度*/;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);
        if (iconRes != -1) {
            Drawable icon = context.getDrawable(iconRes);
            icon.setBounds(0, 0, height/3*2, height/4*3);
            canvas.save();
            canvas.translate(0, height / 6);
            icon.draw(canvas);
            canvas.restore();
        }
        canvas.drawText(text,width/2 + height/3, getFontLeading(p), p);
        canvas.setBitmap(null);
        return bitmap;
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }
    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint)  {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }
    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint)  {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading- fm.ascent;
    }

}
