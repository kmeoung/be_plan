package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanProjectDetail {
    String comment;
    String comment_date;
    String comment_time;
    int photo;
    String sender_name;

    public BeanProjectDetail(int i, String str, String str2, String str3, String str4) {
        this.photo = i;
        this.sender_name = str;
        this.comment = str2;
        this.comment_date = str3;
        this.comment_time = str4;
    }

    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int i) {
        this.photo = i;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String str) {
        this.sender_name = str;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public String getComment_date() {
        return this.comment_date;
    }

    public void setComment_date(String str) {
        this.comment_date = str;
    }

    public String getComment_time() {
        return this.comment_time;
    }

    public void setComment_time(String str) {
        this.comment_time = str;
    }
}
