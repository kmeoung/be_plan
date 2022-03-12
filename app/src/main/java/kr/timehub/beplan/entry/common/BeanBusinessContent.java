package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanBusinessContent {
    String business;
    String finish_date;
    Boolean icn_check;
    Boolean me_button;
    Boolean middle = false;
    String name;
    int photo;
    String start_date;

    public BeanBusinessContent(Boolean bool, String str, String str2, String str3, Boolean bool2, int i) {
        this.icn_check = bool;
        this.business = str;
        this.start_date = str2;
        this.name = str3;
        this.me_button = bool2;
        this.photo = i;
    }

    public BeanBusinessContent(Boolean bool, String str, String str2, String str3, String str4, Boolean bool2, int i) {
        this.icn_check = bool;
        this.business = str;
        this.start_date = str2;
        this.finish_date = str3;
        this.name = str4;
        this.me_button = bool2;
        this.photo = i;
    }

    public Boolean getIcn_check() {
        return this.icn_check;
    }

    public Boolean getMiddle() {
        return this.middle;
    }

    public void setMiddle(Boolean bool) {
        this.middle = bool;
    }

    public void setIcn_check(Boolean bool) {
        this.icn_check = bool;
    }

    public String getBusiness() {
        return this.business;
    }

    public void setBusiness(String str) {
        this.business = str;
    }

    public String getStart_date() {
        return this.start_date;
    }

    public void setStart_date(String str) {
        this.start_date = str;
    }

    public String getFinish_date() {
        return this.finish_date;
    }

    public void setFinish_date(String str) {
        this.finish_date = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Boolean getMe_button() {
        return this.me_button;
    }

    public void setMe_button(Boolean bool) {
        this.me_button = bool;
    }

    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int i) {
        this.photo = i;
    }
}
