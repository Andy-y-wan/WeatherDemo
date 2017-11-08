package com.lmjssjj.weatherwidget;

/**
 * Created by ya.wan on 2017/11/3.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver {

    public static final String SYSTEM_READY = "com.android.launcher3.SYSTEM_READY";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendStickyBroadcast(new Intent(SYSTEM_READY));
    }
}
