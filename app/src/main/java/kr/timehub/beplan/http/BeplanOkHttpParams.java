package kr.timehub.beplan.http;

import android.text.TextUtils;
import com.naver.temy123.baseproject.base.Http.HWOkHttpNameValuePair;
import com.naver.temy123.baseproject.base.Http.HWOkHttpParams;
import java.io.File;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class BeplanOkHttpParams extends HWOkHttpParams {
    private boolean isDelete;

    public BeplanOkHttpParams() {
        super(2);
        this.isDelete = false;
    }

    public BeplanOkHttpParams(boolean z) {
        super(2);
        this.isDelete = false;
        this.isDelete = z;
    }

    @Override // com.naver.temy123.baseproject.base.Http.HWOkHttpParams
    public HWOkHttpParams add(String str, String str2) {
        if (this.isDelete) {
            return TextUtils.isEmpty(str2) ? this : super.add(str, str2);
        }
        return super.add(str, str2);
    }

    @Override // com.naver.temy123.baseproject.base.Http.HWOkHttpParams
    public HWOkHttpParams add(String str, File file) {
        HWOkHttpParams hWOkHttpParams = new HWOkHttpParams(0);
        Iterator<HWOkHttpNameValuePair> it = iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            hWOkHttpParams.add(next.getKey(), next.getValue());
        }
        return file != null ? hWOkHttpParams.add(str, file) : this;
    }

    public void setEmptyDelete(boolean z) {
        this.isDelete = z;
    }

    public BeplanOkHttpParams(int i) {
        super(i);
        this.isDelete = false;
    }

    public BeplanOkHttpParams(JSONObject jSONObject) {
        super(jSONObject);
        this.isDelete = false;
    }

    public BeplanOkHttpParams(JSONArray jSONArray) {
        super(jSONArray);
        this.isDelete = false;
    }
}
