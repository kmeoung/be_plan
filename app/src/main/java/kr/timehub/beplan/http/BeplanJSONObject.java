package kr.timehub.beplan.http;

import android.text.TextUtils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class BeplanJSONObject extends JSONObject {
    @Override // org.json.JSONObject
    public JSONObject put(String str, Object obj) throws JSONException {
        if (obj instanceof String) {
            return !TextUtils.isEmpty((String) obj) ? super.put(str, obj) : this;
        }
        if (obj == null) {
            return this;
        }
        if (!(obj instanceof List)) {
            return super.put(str, obj);
        }
        JSONArray jSONArray = new JSONArray();
        List<Object> list = (List) obj;
        if (list.size() <= 0) {
            return this;
        }
        for (Object obj2 : list) {
            jSONArray.put(obj2);
        }
        return super.put(str, jSONArray);
    }
}
