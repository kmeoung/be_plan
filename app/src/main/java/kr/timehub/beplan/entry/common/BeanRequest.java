package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanRequest {
    String business;
    String business_comment;
    String business_title;
    String name;
    int photo;
    String request_date;

    public BeanRequest(String str, String str2, String str3, String str4, String str5, int i) {
        this.business_title = str;
        this.business = str2;
        this.business_comment = str3;
        this.name = str4;
        this.request_date = str5;
        this.photo = i;
    }

    public String getBusiness_title() {
        return this.business_title;
    }

    public void setBusiness_title(String str) {
        this.business_title = str;
    }

    public String getBusiness() {
        return this.business;
    }

    public void setBusiness(String str) {
        this.business = str;
    }

    public String getBusiness_comment() {
        return this.business_comment;
    }

    public void setBusiness_comment(String str) {
        this.business_comment = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getRequest_date() {
        return this.request_date;
    }

    public void setRequest_date(String str) {
        this.request_date = str;
    }

    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int i) {
        this.photo = i;
    }
}
