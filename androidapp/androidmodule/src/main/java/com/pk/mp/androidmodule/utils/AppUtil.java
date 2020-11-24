package com.pk.mp.androidmodule.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.pk.mp.androidmodule.WsClient;
import com.pk.mp.androidmodule.server.impl.WsService;

import java.util.List;

public class AppUtil {

    public static void startApp(Context context, String packageName) {
        Intent intent = null;
        if (isAvilible(context, packageName)) {
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        }
        try {
            if (intent != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }

    private static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


}
