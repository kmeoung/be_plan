package kr.timehub.beplan.FnMarketUpdate;

import android.content.Context;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;
import kr.timehub.beplan.utils.JLog;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/* loaded from: classes.dex */
public class MarketUpdate extends AsyncTask<Void, Void, String> {
    public static final int MARKET_UPDATE_NOTHING = 0;
    public static final int MARKET_UPDATE_OPTIONAL = 2;
    public static final int MARKET_UPDATE_REQUIRED = 1;
    private WeakReference<Context> contextWeakReference;
    private MarketUpdateTaskCallback taskCallback;

    /* loaded from: classes.dex */
    public interface MarketUpdateTaskCallback {
        void marketUpdate(int i);
    }

    public MarketUpdate(Context context, MarketUpdateTaskCallback marketUpdateTaskCallback) {
        JLog.i();
        this.contextWeakReference = new WeakReference<>(context);
        this.taskCallback = marketUpdateTaskCallback;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public String doInBackground(Void... voidArr) {
        JLog.i();
        try {
            Elements select = Jsoup.connect("https://play.google.com/store/apps/details?id=" + this.contextWeakReference.get().getPackageName()).get().select(".htlgb");
            for (int i = 0; i < select.size(); i++) {
                String text = select.get(i).text();
                if (Pattern.matches("^[0-9\\\\.]*$", text)) {
                    return text;
                }
            }
            return null;
        } catch (Exception e) {
            JLog.e(e.getMessage());
            return null;
        }
    }

    public void onPostExecute(String str) {
        String[] strArr;
        JLog.i("marketVersion : " + str);
        if (str != null) {
            try {
                Context context = this.contextWeakReference.get();
                String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                JLog.i("marketVersion : " + str);
                JLog.i("deviceVersion : " + str2);
                String[] split = str.split("\\.");
                if (str2.contains("-")) {
                    strArr = str2.split("-")[0].split("\\.");
                } else {
                    strArr = str2.split("\\.");
                }
                int i = 0;
                boolean z = false;
                z = false;
                while (true) {
                    z = true;
                    if (i >= 3) {
                        break;
                    }
                    int parseInt = Integer.parseInt(split[i]);
                    int parseInt2 = Integer.parseInt(strArr[i]);
                    if (i != 2) {
                        if (!z) {
                            if (parseInt <= parseInt2) {
                                z = false;
                            }
                        }
                    } else if (parseInt <= parseInt2) {
                        z = false;
                    }
                    i++;
                }
                int parseInt3 = Integer.parseInt(split[0] + split[1]);
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[0]);
                sb.append(strArr[1]);
                boolean z2 = parseInt3 >= Integer.parseInt(sb.toString());
                JLog.d("required : " + z);
                JLog.d("optional : " + z);
                JLog.d("requiredSum : " + z2);
                if (z && z2) {
                    this.taskCallback.marketUpdate(1);
                } else if (!z || !z2) {
                    this.taskCallback.marketUpdate(0);
                } else {
                    this.taskCallback.marketUpdate(2);
                }
            } catch (Exception e) {
                JLog.e(e.getMessage());
                this.taskCallback.marketUpdate(0);
            }
        } else {
            this.taskCallback.marketUpdate(0);
        }
    }
}
