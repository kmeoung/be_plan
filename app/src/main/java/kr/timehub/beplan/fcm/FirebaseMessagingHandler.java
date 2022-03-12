package kr.timehub.beplan.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.naver.temy123.baseproject.base.Utils.Log;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Date;
import java.util.Map;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.utils.Comm_Prefs;

/* loaded from: classes.dex */
public class FirebaseMessagingHandler extends FirebaseMessagingService {
    private String seq = "";
    private String type = "";
    private String title = "";
    private String nseq = "";
    private String ProjectTitle = "";

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageSent(String str) {
        super.onMessageSent(str);
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("FirebaseMessagingHandler", "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            if (!data.isEmpty()) {
                if (this.seq.equals("")) {
                    this.seq = data.get("seq");
                }
                this.nseq = data.get("nseq");
                this.type = data.get("type");
                if (this.type.equals("P_A")) {
                    this.ProjectTitle = data.get("ProjectTitle");
                }
            }
        }
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            this.title = notification.getTitle();
            String body = notification.getBody();
            if (isNotification()) {
                sendNotification(this.title, body);
            }
        }
    }

    private boolean isNotification() {
        Comm_Prefs instance = Comm_Prefs.getInstance(getApplicationContext());
        if (instance.getSettingPush()) {
            long settingPushStartTime = instance.getSettingPushStartTime();
            long settingPushEndTime = instance.getSettingPushEndTime();
            if (!(settingPushStartTime == 0 || settingPushEndTime == 0)) {
                Date date = new Date(System.currentTimeMillis());
                Date date2 = new Date(settingPushStartTime);
                Date date3 = new Date(settingPushEndTime);
                if (!date3.after(date2)) {
                    date2.setDate(date2.getDate() - 1);
                }
                return !date.after(date2) || date.after(date3);
            }
        }
        return true;
    }

    private void sendNotification(String str, String str2) {
        NotificationCompat.Builder builder;
        Intent intent = new Intent(this, ActivityMain.class);
        intent.addFlags(604012544);
        intent.addCategory("check");
        intent.putExtra("seq", this.seq);
        intent.putExtra("nseq", this.nseq);
        intent.putExtra("ProjectTitle", this.ProjectTitle);
        intent.putExtra("type", this.type);
        intent.putExtra(SettingsJsonConstants.PROMPT_TITLE_KEY, this.title);
        PendingIntent activity = PendingIntent.getActivity(getApplicationContext(), 0, intent, 134217728);
        String string = getString(R.string.project_id);
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(str);
        bigTextStyle.bigText(str2);
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(this, "channel_beplan").setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(defaultUri).setStyle(bigTextStyle).setContentIntent(activity);
        } else {
            builder = new NotificationCompat.Builder(this, string).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(defaultUri).setStyle(bigTextStyle).setContentIntent(activity);
        }
        ((NotificationManager) getSystemService("notification")).notify(0, builder.build());
    }
}
