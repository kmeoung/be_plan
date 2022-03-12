package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanSSOCommon {
    private String RtnKey;
    private String RtnUrl;
    private String RtnValue;

    public BeanSSOCommon(String str, String str2) {
        this.RtnKey = str;
        this.RtnValue = str2;
    }

    public BeanSSOCommon(String str, String str2, String str3) {
        this.RtnKey = str;
        this.RtnValue = str2;
        this.RtnUrl = str3;
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

    public String getRtnUrl() {
        return this.RtnUrl;
    }

    public void setRtnUrl(String str) {
        this.RtnUrl = str;
    }
}
