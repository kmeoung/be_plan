package kr.timehub.beplan.entry;

/* loaded from: classes.dex */
public class BeanDrawerProfile {
    private String email;
    private String name;
    private String profileUrl;

    public BeanDrawerProfile(String str, String str2, String str3) {
        this.profileUrl = str;
        this.name = str2;
        this.email = str3;
    }

    public BeanDrawerProfile() {
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public void setProfileUrl(String str) {
        this.profileUrl = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }
}
