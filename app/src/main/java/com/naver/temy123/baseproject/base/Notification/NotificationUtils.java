package com.naver.temy123.baseproject.base.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/* loaded from: classes.dex */
public class NotificationUtils {
    public static void notify(Context context, int i, int i2, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = null;
        notification.defaults = i2;
        notification.contentIntent = pendingIntent;
        ((NotificationManager) context.getSystemService("notification")).notify(i, builder.build());
    }
}
