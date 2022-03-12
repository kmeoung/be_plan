package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanReg {
    String ACCESSKEY;
    String RtnKey;
    String RtnValue;
    String UserId;

    public BeanReg(String str, String str2, String str3, String str4) {
        this.ACCESSKEY = str;
        this.RtnKey = str2;
        this.RtnValue = str3;
        this.UserId = str4;
    }

    public String getACCESSKEY() {
        return this.ACCESSKEY;
    }

    public void setACCESSKEY(String str) {
        this.ACCESSKEY = str;
    }

    public String getRtnKey() {
        return this.RtnKey;
    }

    public void setRtnKey(String str) {
        this.RtnKey = str;
    }

    public String getRtnValue() {
        return this.RtnValue;
    }

    public void setRtnValue(String str) {
        this.RtnValue = str;
    }

    public String getUserId() {
        return this.UserId;
    }

    public void setUserId(String str) {
        this.UserId = str;
    }
}
