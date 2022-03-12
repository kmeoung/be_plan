package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSharedProjectDetail {
    @SerializedName("Admin")
    public Admin Admin;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("Project_Member")
    public List<Project_Member> Project_Member;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;

    /* loaded from: classes.dex */
    public static class Admin {
        @SerializedName("Auth")
        public String Auth;
        @SerializedName("Email")
        public String Email;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
    }

    /* loaded from: classes.dex */
    public static class Project_Member {
        @SerializedName("Auth")
        public String Auth;
        @SerializedName("Email")
        public String Email;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
    }
}
