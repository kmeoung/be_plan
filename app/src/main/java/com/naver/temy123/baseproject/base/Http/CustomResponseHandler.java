package com.naver.temy123.baseproject.base.Http;

import android.app.ProgressDialog;
import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.TokenParser;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes.dex */
public abstract class CustomResponseHandler extends AsyncHttpResponseHandler {
    private boolean isShowDialog;
    private Context mContext;
    private String mData;
    private JSONObject mJsonObject;
    private ProgressDialog mProgressDialog;
    private String mRtnKey;
    private String mRtnValue;
    private boolean mBoolRetry = true;
    private boolean isOther = true;
    private boolean isCustom = false;

    protected void CMOK() {
    }

    protected void CMSE() {
    }

    protected void CUSTOM() {
    }

    protected void DANO() {
    }

    protected void DAOK() {
    }

    protected void DEFAULT() {
    }

    protected void MBFD() {
    }

    protected void MBGD() {
    }

    protected void MBRF() {
    }

    protected void MBRS() {
    }

    protected void MBVD() {
    }

    protected void MINO() {
    }

    protected void MLFD() {
    }

    protected void MLGD() {
    }

    protected void MLOF() {
    }

    protected void MLOS() {
    }

    protected void OTHER() {
    }

    protected void PWRF() {
    }

    protected void PWRS() {
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
    }

    public CustomResponseHandler() {
    }

    public CustomResponseHandler(Context context, boolean z) {
        this.mContext = context;
        this.mProgressDialog = new ProgressDialog(context);
        this.isShowDialog = z;
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
            this.mRtnKey = this.mJsonObject.getString("RtnKey");
            this.mRtnValue = this.mJsonObject.getString("RtnValue");
            String str = this.mRtnKey;
            char c = 65535;
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
                    CMOK();
                    break;
                case 1:
                    CMSE();
                    break;
                case 2:
                    DAOK();
                    break;
                case 3:
                    DANO();
                    break;
                case 4:
                    MBRS();
                    break;
                case 5:
                    MBRF();
                    break;
                case 6:
                    MBVD();
                    break;
                case 7:
                    MLOS();
                    break;
                case '\b':
                    MLOF();
                    break;
                case '\t':
                    MINO();
                    break;
                case '\n':
                    MBFD();
                    break;
                case 11:
                    MBGD();
                    break;
                case '\f':
                    MLGD();
                    break;
                case '\r':
                    MLFD();
                    break;
                case 14:
                    PWRS();
                    break;
                case 15:
                    PWRF();
                    break;
            }
            DEFAULT();
            if (this.isOther) {
                OTHER();
            }
            if (this.isCustom) {
                CUSTOM();
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public boolean canRetry() {
        if (!isRetry()) {
            return false;
        }
        setRetry(false);
        return true;
    }

    protected void ignoreOther() {
        this.isOther = false;
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
}
