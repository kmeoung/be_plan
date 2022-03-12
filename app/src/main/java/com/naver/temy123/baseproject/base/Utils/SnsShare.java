package com.naver.temy123.baseproject.base.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class SnsShare {
    public static final void shareToSns(Context context, int i, String str, String str2, String str3, String str4) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!queryIntentActivities.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                Intent intent2 = (Intent) intent.clone();
                if (TextUtils.equals(resolveInfo.activityInfo.packageName.toLowerCase(), "com.facebook.katana")) {
                    intent2.setType("text/plain");
                    intent2.putExtra("android.intent.extra.TEXT", str3);
                } else {
                    intent2.setType("image/*");
                    intent2.putExtra("android.intent.extra.SUBJECT", str2);
                    intent2.putExtra("android.intent.extra.TEXT", str4);
                }
                intent2.setPackage(resolveInfo.activityInfo.packageName);
                arrayList.add(intent2);
            }
            Intent createChooser = Intent.createChooser((Intent) arrayList.remove(0), str);
            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
            ((Activity) context).startActivityForResult(createChooser, i);
        }
    }
}
