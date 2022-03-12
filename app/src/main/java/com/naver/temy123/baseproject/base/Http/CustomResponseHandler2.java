package com.naver.temy123.baseproject.base.Http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.TokenParser;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes.dex */
public abstract class CustomResponseHandler2 extends AsyncHttpResponseHandler {
    private static String STT_RTNKEY = "RtnKey";
    private static String STT_RTNVALUE = "RtnValue";
    private boolean isCustom;
    private boolean mBoolRetry;
    private Context mContext;
    private String mData;
    private int mDelay;
    private JSONObject mJsonObject;
    private ProgressDialog mProgressDialog;
    private String mRtnKey;
    private String mRtnValue;
    private int retryCount;
    private int retryLimitCount;

    public boolean CMOK() {
        return false;
    }

    public boolean CMSE() {
        return false;
    }

    public void CUSTOM() {
    }

    public boolean DANO() {
        return false;
    }

    public boolean DAOK() {
        return false;
    }

    public void DEFAULT() {
    }

    public boolean MBFD() {
        return false;
    }

    public boolean MBGD() {
        return false;
    }

    public boolean MBRF() {
        return false;
    }

    public boolean MBRS() {
        return false;
    }

    public boolean MBVD() {
        return false;
    }

    public boolean MINO() {
        return false;
    }

    public boolean MLFD() {
        return false;
    }

    public boolean MLGD() {
        return false;
    }

    public boolean MLOF() {
        return false;
    }

    public boolean MLOS() {
        return false;
    }

    public void OTHER() {
    }

    public boolean PWRF() {
        return false;
    }

    public boolean PWRS() {
        return false;
    }

    public void onRetryFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
    }

    public CustomResponseHandler2() {
        this.mBoolRetry = false;
        this.isCustom = false;
        this.mDelay = 100;
        this.retryLimitCount = 3;
        this.retryCount = 0;
    }

    public CustomResponseHandler2(Context context) {
        this.mBoolRetry = false;
        this.isCustom = false;
        this.mDelay = 100;
        this.retryLimitCount = 3;
        this.retryCount = 0;
        this.mContext = context;
    }

    public CustomResponseHandler2(Context context, boolean z) {
        this.mBoolRetry = false;
        this.isCustom = false;
        this.mDelay = 100;
        this.retryLimitCount = 3;
        this.retryCount = 0;
        this.mContext = context;
        if (z) {
            this.mProgressDialog = new ProgressDialog(context);
        }
    }

    public CustomResponseHandler2(Context context, boolean z, boolean z2) {
        this.mBoolRetry = false;
        this.isCustom = false;
        this.mDelay = 100;
        this.retryLimitCount = 3;
        this.retryCount = 0;
        this.mContext = context;
        this.mBoolRetry = z2;
        if (z) {
            this.mProgressDialog = new ProgressDialog(context);
        }
    }

    public CustomResponseHandler2(Context context, boolean z, int i) {
        this.mBoolRetry = false;
        this.isCustom = false;
        this.mDelay = 100;
        this.retryLimitCount = 3;
        this.retryCount = 0;
        this.mContext = context;
        this.mBoolRetry = true;
        this.mDelay = i;
        if (z) {
            this.mProgressDialog = new ProgressDialog(context);
        }
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onStart() {
        super.onStart();
        if (this.mProgressDialog != null) {
            this.mProgressDialog.setCancelable(false);
            this.mProgressDialog.show();
        }
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onFinish() {
        super.onFinish();
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
        this.mData = new String(bArr);
        try {
            this.mJsonObject = new JSONObject(this.mData);
            this.mRtnKey = this.mJsonObject.getString(getSttRtnkey());
            this.mRtnValue = this.mJsonObject.getString(getSttRtnvalue());
            String str = this.mRtnKey;
            char c = 65535;
            boolean z = false;
            switch (str.hashCode()) {
                case 2072518:
                    if (str.equals("CMOK")) {
                        c = 0;
                        break;
                    }
                    break;
                case 2072636:
                    if (str.equals(Comm_RtnKey.CMSE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 2090750:
                    if (str.equals(Comm_RtnKey.DANO)) {
                        c = 3;
                        break;
                    }
                    break;
                case 2090777:
                    if (str.equals("DAOK")) {
                        c = 2;
                        break;
                    }
                    break;
                case 2359571:
                    if (str.equals(Comm_RtnKey.MBFD)) {
                        c = '\n';
                        break;
                    }
                    break;
                case 2359602:
                    if (str.equals(Comm_RtnKey.MBGD)) {
                        c = 11;
                        break;
                    }
                    break;
                case 2359945:
                    if (str.equals(Comm_RtnKey.MBRF)) {
                        c = 5;
                        break;
                    }
                    break;
                case 2359958:
                    if (str.equals(Comm_RtnKey.MBRS)) {
                        c = 4;
                        break;
                    }
                    break;
                case 2360067:
                    if (str.equals(Comm_RtnKey.MBVD)) {
                        c = 6;
                        break;
                    }
                    break;
                case 2366557:
                    if (str.equals(Comm_RtnKey.MINO)) {
                        c = '\t';
                        break;
                    }
                    break;
                case 2369181:
                    if (str.equals(Comm_RtnKey.MLFD)) {
                        c = TokenParser.CR;
                        break;
                    }
                    break;
                case 2369212:
                    if (str.equals(Comm_RtnKey.MLGD)) {
                        c = '\f';
                        break;
                    }
                    break;
                case 2369462:
                    if (str.equals(Comm_RtnKey.MLOF)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 2369475:
                    if (str.equals(Comm_RtnKey.MLOS)) {
                        c = 7;
                        break;
                    }
                    break;
                case 2469499:
                    if (str.equals(Comm_RtnKey.PWRF)) {
                        c = 15;
                        break;
                    }
                    break;
                case 2469512:
                    if (str.equals(Comm_RtnKey.PWRS)) {
                        c = 14;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    z = CMOK();
                    break;
                case 1:
                    z = CMSE();
                    break;
                case 2:
                    z = DAOK();
                    break;
                case 3:
                    z = DANO();
                    break;
                case 4:
                    z = MBRS();
                    break;
                case 5:
                    z = MBRF();
                    break;
                case 6:
                    z = MBVD();
                    break;
                case 7:
                    z = MLOS();
                    break;
                case '\b':
                    z = MLOF();
                    break;
                case '\t':
                    z = MINO();
                    break;
                case '\n':
                    z = MBFD();
                    break;
                case 11:
                    z = MBGD();
                    break;
                case '\f':
                    z = MLGD();
                    break;
                case '\r':
                    z = MLFD();
                    break;
                case 14:
                    z = PWRS();
                    break;
                case 15:
                    z = PWRF();
                    break;
            }
            DEFAULT();
            if (!z) {
                OTHER();
            }
            if (this.isCustom) {
                CUSTOM();
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        if (isRetry()) {
            int i2 = this.retryCount;
            this.retryCount = i2 + 1;
            if (i2 >= this.retryLimitCount) {
                onRetryFailure(i, headerArr, bArr, th);
            } else if (Looper.myLooper() == Looper.getMainLooper()) {
                new Handler(Looper.myLooper()).postDelayed(new Runnable() { // from class: com.naver.temy123.baseproject.base.Http.CustomResponseHandler2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        String uri = CustomResponseHandler2.this.getRequestURI().toString();
                        if (CustomResponseHandler2.this.getRequestHeaders() == null || CustomResponseHandler2.this.getRequestHeaders().length <= 0) {
                            ApiClient.getInstance().get(uri, null, CustomResponseHandler2.this);
                        } else {
                            ApiClient.getInstance().getClient().post(CustomResponseHandler2.this.getmContext(), uri, CustomResponseHandler2.this.getRequestHeaders(), (RequestParams) null, (String) null, CustomResponseHandler2.this);
                        }
                    }
                }, this.mDelay);
            }
        }
    }

    public String getRtnKey() {
        return this.mRtnKey;
    }

    public void setRtnKey(String str) {
        this.mRtnKey = str;
    }

    public String getRtnValue() {
        return this.mRtnValue;
    }

    public void setRtnValue(String str) {
        this.mRtnValue = str;
    }

    public String getData() {
        return this.mData;
    }

    public void setData(String str) {
        this.mData = str;
    }

    public JSONObject getJsonObject() {
        return this.mJsonObject;
    }

    public void setJsonObject(JSONObject jSONObject) {
        this.mJsonObject = jSONObject;
    }

    public boolean isRetry() {
        return this.mBoolRetry;
    }

    public void setRetry(boolean z) {
        this.mBoolRetry = z;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void callCustom() {
        this.isCustom = true;
    }

    public Context getmContext() {
        return this.mContext;
    }

    public void setmContext(Context context) {
        this.mContext = context;
    }

    public static String getSttRtnkey() {
        return STT_RTNKEY;
    }

    public static void setSttRtnkey(String str) {
        STT_RTNKEY = str;
    }

    public static String getSttRtnvalue() {
        return STT_RTNVALUE;
    }

    public static void setSttRtnvalue(String str) {
        STT_RTNVALUE = str;
    }

    public ProgressDialog getProgressDialog() {
        return this.mProgressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.mProgressDialog = progressDialog;
    }

    public int getRetryLimitCount() {
        return this.retryLimitCount;
    }

    public void setRetryLimitCount(int i) {
        this.retryLimitCount = i;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int i) {
        this.retryCount = i;
    }
}
