package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanNewTeam {
    String email;
    String name;
    String permissions;
    int photo;
    String right_text;

    public BeanNewTeam(int i, String str, String str2, String str3, String str4) {
        this.photo = i;
        this.name = str;
        this.email = str2;
        this.permissions = str3;
        this.right_text = str4;
    }

    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int i) {
        this.photo = i;
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

    public String getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String str) {
        this.permissions = str;
    }

    public String getRight_text() {
        return this.right_text;
    }

    public void setRight_text(String str) {
        this.right_text = str;
    }
}
