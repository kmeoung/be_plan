package kr.timehub.beplan.entry;

/* loaded from: classes.dex */
public class BeanProjectRequestContent {
    private String business;
    private String finish_date;
    private Boolean me_button;
    private boolean middle = false;
    private String name;
    private int photo;
    private String start_date;

    public BeanProjectRequestContent(String str, String str2, String str3, Boolean bool, int i) {
        this.business = str;
        this.start_date = str2;
        this.name = str3;
        this.me_button = bool;
        this.photo = i;
    }

    public BeanProjectRequestContent(String str, String str2, String str3, String str4, Boolean bool, int i) {
        this.business = str;
        this.start_date = str2;
        this.finish_date = str3;
        this.name = str4;
        this.me_button = bool;
        this.photo = i;
    }

    public boolean isMiddle() {
        return this.middle;
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
