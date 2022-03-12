package kr.timehub.beplan.entry.common;

/* loaded from: classes.dex */
public class BeanDropdownRep {
    String email;
    String name;
    int photoID;

    public BeanDropdownRep(String str, String str2, int i) {
        this.name = str;
        this.email = str2;
        this.photoID = i;
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

    public int getPhotoID() {
        return this.photoID;
    }

    public void setPhotoID(int i) {
        this.photoID = i;
    }
}
