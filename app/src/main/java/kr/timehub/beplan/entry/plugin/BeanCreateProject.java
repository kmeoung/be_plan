package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanCreateProject {
    @SerializedName("ProejctTitle")
    public String ProejctTitle;
    @SerializedName("Project_Member")
    public List<Project_Member> Project_Member;

    /* loaded from: classes.dex */
    public static class Project_Member {
        @SerializedName("UserAuth")
        public String UserAuth;
        @SerializedName("UserId")
        public int UserId;
    }
}
