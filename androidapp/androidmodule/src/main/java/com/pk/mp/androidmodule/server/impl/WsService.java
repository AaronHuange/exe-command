package com.pk.mp.androidmodule.server.impl;

import android.content.Intent;
import android.os.IBinder;

import com.pk.mp.androidmodule.WsClient;
import com.pk.mp.androidmodule.server.BaseService;
import com.pk.mp.androidmodule.utils.AppUtil;

import androidx.annotation.Nullable;

public class WsService extends BaseService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WsClient.get(getApplicationContext());
//        AppUtil.startApp(getApplicationContext(), "org.esbuilder.mp.dfh.sit");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(WsService.this,WsService.class);
        startService(intent);
    }
}
