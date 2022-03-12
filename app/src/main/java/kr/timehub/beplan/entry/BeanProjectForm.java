package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanProjectForm {
    @SerializedName("ProjectName")
    private String ProjectName;
    @SerializedName("ProjectType")
    private String ProjectType;
    @SerializedName("Project_Member")
    public List<Project_Member> Project_Member;

    /* loaded from: classes.dex */
    public static class Project_Member {
        @SerializedName("Auth")
        public String Auth;
        @SerializedName("Email")
        public String Email;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("MemberId")
        public int MemberId;
        @SerializedName("PMSEQ")
        public int PMSEQ;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
    }

    public String getProjectName() {
        return this.ProjectName;
    }

    public void setProjectName(String str) {
        this.ProjectName = str;
    }

    public String getProjectType() {
        return this.ProjectType;
    }

    public void setProjectType(String str) {
        this.ProjectType = str;
    }
}
