package kr.timehub.beplan.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import java.util.concurrent.atomic.AtomicInteger;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public final class NotificationHelper {
    private static NotificationHelper instance;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private Context context;
    private NotificationManager notificationManager;

    private NotificationHelper(Context context) {
        this.context = context.getApplicationContext();
        this.notificationManager = (NotificationManager) this.context.getSystemService("notification");
        createNotificationChannel(context);
    }

    public static NotificationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationHelper(context);
        }
        return instance;
    }

    public final void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26 && this.notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel(Comm_Params.NOTIFICATION_ID_DEFAULT_ALARM, Comm_Params.NOTIFICATION_ID_DEFAULT_ALARM, 3);
            notificationChannel.setDescription(context.getString(R.string.notification_default_description));
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setLockscreenVisibility(1);
            this.notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public final Notification createBigTextNotification(String str, String str2, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 26) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(str);
            bigTextStyle.bigText(str2);
            return new Notification.Builder(this.context, Comm_Params.NOTIFICATION_ID_DEFAULT_ALARM).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setStyle(bigTextStyle).setContentIntent(pendingIntent).build();
        }
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.BigTextStyle bigTextStyle2 = new NotificationCompat.BigTextStyle();
        bigTextStyle2.setBigContentTitle(str);
        bigTextStyle2.bigText(str2);
        return new NotificationCompat.Builder(this.context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(defaultUri).setStyle(bigTextStyle2).setContentIntent(pendingIntent).build();
    }

    public final Notification createDefaultNotification(String str, String str2, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = new Notification.Builder(this.context, Comm_Params.NOTIFICATION_ID_DEFAULT_ALARM);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setTicker(str);
            builder.setContentTitle(str);
            builder.setContentText(str2);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            return builder.build();
        }
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this.context);
        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setTicker(str);
        builder2.setContentTitle(str);
        builder2.setContentText(str2);
        builder2.setContentIntent(pendingIntent);
        builder2.setDefaults(7);
        builder2.setAutoCancel(true);
        return builder2.build();
    }

    public final void notify(Notification notification) {
        notify(this.atomicInteger.getAndIncrement(), notification);
    }

    public final void notify(int i, Notification notification) {
        if (this.notificationManager != null) {
            this.notificationManager.notify(i, notification);
        }
    }
}
