package com.method76.faultnotification.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.method76.faultnotification.MainActivity;
import com.method76.faultnotification.R;

/**
 * Created by lgcns on 2016-08-10.
 */
public class BaseUtil {

    private static final int NOTI_ID   = 10000;
    private static NotificationCompat.Builder build;

    /**
     *
     * @param ctx
     */
    public static void showNoti(Context ctx, boolean success) {

        NotificationManager man = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent intent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (build==null) {
            build = new NotificationCompat.Builder(ctx);
        }
//        build.setTicker("Checking service health");
//        build.setNumber(10);
        build.setWhen(System.currentTimeMillis());
        build.setContentTitle("Your service status is...");
        if (success) {
            build.setSmallIcon(R.drawable.ic_noti_posi);
            build.setContentText("It seems to be OK");
        } else {
            build.setSmallIcon(R.drawable.ic_noti_nega);
            build.setContentText("No response from the sucker");
            build.setDefaults(Notification.DEFAULT_SOUND
                    | Notification.DEFAULT_VIBRATE);
        }
        // Todo: Add Open Browser Action
        build.addAction(android.R.drawable.ic_menu_mapmode, "DASHBOARD", intent);
        build.addAction(android.R.drawable.ic_menu_view, "SITE", intent);
        build.setContentIntent(intent);
        build.setAutoCancel(false);

        man.notify(NOTI_ID, build.build());
    }

}
