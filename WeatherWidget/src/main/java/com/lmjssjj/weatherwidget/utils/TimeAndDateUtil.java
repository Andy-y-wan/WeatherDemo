package com.lmjssjj.weatherwidget.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.format.DateFormat;

import com.lmjssjj.weatherwidget.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeAndDateUtil {
	private Context mContext;

	private Bitmap timeBitmap;
    private String time;
    private Bitmap dateBitmap;
	private String date;
	private float timeSize;
	private float dateSize;

	private String mTimeFormatString;
	private SimpleDateFormat mTimeFormat;
	private Calendar mCalendar;
	private Locale mLocale;
	private TimeChangedCallback mCallback;

	private int mWidth;
	private int mHeight;

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                String tz = intent.getStringExtra("time-zone");
                mCalendar = Calendar.getInstance(TimeZone.getTimeZone(tz));
                if (mTimeFormat != null) {
                    mTimeFormat.setTimeZone(mCalendar.getTimeZone());
                }
            } else if (action.equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
                final Locale newLocale = context.getResources().getConfiguration().locale;
                if (! newLocale.equals(mLocale)) {
                    mLocale = newLocale;
                    mTimeFormatString = ""; // force refresh
                }
            }
            refreshTime();
            updateTimeAndDateBitmap();
            mCallback.timeChanged();
        }
    };

    public TimeAndDateUtil(Context context,TimeChangedCallback callback) {
		mContext = context;
		mCallback = callback;

        mCalendar = Calendar.getInstance(TimeZone.getDefault());
        mLocale = context.getResources().getConfiguration().locale;

        initBitmapSize();
        dateSize = context.getResources().getDimension(R.dimen.date_text_size);
        timeSize = context.getResources().getDimension(R.dimen.time_text_size);

        refreshTime();
        updateTimeAndDateBitmap();

        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);

        context.registerReceiver(mIntentReceiver, filter);
    }

    private void initBitmapSize() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sunny_day_1, options);
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        bitmap.recycle();
    }

    /**
     * 更新时间 几点几分
     */
    private void refreshTime() {
    	mCalendar.setTimeInMillis(System.currentTimeMillis());
        boolean is24 = DateFormat.is24HourFormat(mContext);

        SimpleDateFormat sdf;
        String format = is24 ? "HH : mm": "hh : mm";
        if (!format.equals(mTimeFormatString)) {
            mTimeFormat = sdf = new SimpleDateFormat(format, mLocale);
            mTimeFormatString = format;
        } else {
            sdf = mTimeFormat;
        }
       	time = sdf.format(mCalendar.getTime());

        format = "MMM d , EEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, mLocale);
        date = dateFormat.format(mCalendar.getTime());
    }

    /**
     * android中绘制字体，使用画布canvas
     */
    public void updateTimeAndDateBitmap(){
        timeBitmap = FontTextureUtil.createFontBitmap(mContext,mWidth,mHeight,timeSize,mHeight*290f/408f,
                "Akrobat-Regular.otf",Color.WHITE,time);
        dateBitmap = FontTextureUtil.createFontBitmap(mContext,mWidth,mHeight,dateSize,mHeight*328f/408f,
				"Roboto-Regular.ttf",Color.parseColor("#B3FFFFFF"),date);
    }

    public interface TimeChangedCallback {
		void timeChanged();
	}

	public Bitmap getTimeBitmap() {
		return timeBitmap;
	}

	public Bitmap getDateBitmap() {
		return dateBitmap;
	}

	public void unregisterReceiver() {
        mContext.unregisterReceiver(mIntentReceiver);
    }
}
