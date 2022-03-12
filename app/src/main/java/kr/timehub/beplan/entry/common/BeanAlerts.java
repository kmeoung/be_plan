package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanAlerts {
    String categort_name;
    String comment_status;
    String commnet_content;
    String project_name;
    String send_date;
    String send_time;
    String sender_name;
    String task_name;

    public BeanAlerts(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.comment_status = str;
        this.commnet_content = str2;
        this.sender_name = str3;
        this.send_date = str4;
        this.send_time = str5;
        this.project_name = str6;
        this.categort_name = str7;
        this.task_name = str8;
    }

    public String getComment_status() {
        return this.comment_status;
    }

    public void setComment_status(String str) {
        this.comment_status = str;
    }

    public String getCommnet_content() {
        return this.commnet_content;
    }

    public void setCommnet_content(String str) {
        this.commnet_content = str;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String str) {
        this.sender_name = str;
    }

    public String getSend_date() {
        return this.send_date;
    }

    public void setSend_date(String str) {
        this.send_date = str;
    }

    public String getSend_time() {
        return this.send_time;
    }

    public void setSend_time(String str) {
        this.send_time = str;
    }

    public String getProject_name() {
        return this.project_name;
    }

    public void setProject_name(String str) {
        this.project_name = str;
    }

    public String getCategort_name() {
        return this.categort_name;
    }

    public void setCategort_name(String str) {
        this.categort_name = str;
    }

    public String getTask_name() {
        return this.task_name;
    }

    public void setTask_name(String str) {
        this.task_name = str;
    }
}
