package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanSNSLogin {
    private String cc;
    private String id;
    private String memberPart;
    private String name;

    public BeanSNSLogin() {
    }

    public BeanSNSLogin(String str, String str2, String str3, String str4) {
        this.id = str;
        this.name = str2;
        this.cc = str3;
        this.memberPart = str4;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String str) {
        this.cc = str;
    }

    public String getMemberPart() {
        return this.memberPart;
    }

    public void setMemberPart(String str) {
        this.memberPart = str;
    }
}
