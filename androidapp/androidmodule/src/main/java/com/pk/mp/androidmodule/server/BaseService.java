package com.pk.mp.androidmodule.server;

import android.app.Service;
import android.content.Intent;

import com.pk.mp.androidmodule.utils.AppUtil;

public abstract class BaseService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopForeground(true);
        AppUtil.startApp(getApplicationContext(),"");
    }
}
