package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanMain {
    @SerializedName("AllProjectList")
    public List<AllProjectList> AllProjectList;
    @SerializedName("Email")
    public String Email;
    @SerializedName("IsNewNotification")
    public boolean IsNewNotification;
    @SerializedName("MyTaskCnt")
    public int MyTaskCnt;
    @SerializedName("NoCheckNotiCnt")
    public int NoCheckNotiCnt;
    @SerializedName("ProfileImgUrl")
    public String ProfileImgUrl;
    @SerializedName("Project_List")
    public List<Project_List> Project_List;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("Shared_Project_List")
    public List<Shared_Project_List> Shared_Project_List;
    @SerializedName("UserName")
    public String UserName;

    /* loaded from: classes.dex */
    public static class AllProjectList {
        @SerializedName("CategoryCnt")
        public int CategoryCnt;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("IsDelete")
        public boolean IsDelete;
        @SerializedName("IsInvite")
        public boolean IsInvite;
        @SerializedName("MakeID")
        public int MakeID;
        @SerializedName("MakeName")
        public String MakeName;
        @SerializedName("MemberCnt")
        public int MemberCnt;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("OKCategoryCnt")
        public int OKCategoryCnt;
        @SerializedName("OKTaskCnt")
        public int OKTaskCnt;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProjectCnt")
        public int ProjectCnt;
        @SerializedName("ProjectSate")
        public String ProjectSate;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
        @SerializedName("Project_Member")
        public List<Project_Member> Project_Member;
        @SerializedName("Project_Type")
        public String Project_Type;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("SellAuth")
        public String SellAuth;
        @SerializedName("SellPart")
        public String SellPart;
        @SerializedName("State")
        public String State;
        @SerializedName("TaskCnt")
        public int TaskCnt;
    }

    /* loaded from: classes.dex */
    public static class Project_List {
        @SerializedName("CategoryCnt")
        public int CategoryCnt;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("IsDelete")
        public boolean IsDelete;
        @SerializedName("IsInvite")
        public boolean IsInvite;
        @SerializedName("MakeID")
        public int MakeID;
        @SerializedName("MakeName")
        public String MakeName;
        @SerializedName("MemberCnt")
        public int MemberCnt;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("OKCategoryCnt")
        public int OKCategoryCnt;
        @SerializedName("OKTaskCnt")
        public int OKTaskCnt;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProjectCnt")
        public int ProjectCnt;
        @SerializedName("ProjectSate")
        public String ProjectSate;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
        @SerializedName("Project_Member")
        public List<Project_Member> Project_Member;
        @SerializedName("Project_Type")
        public String Project_Type;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("SellAuth")
        public String SellAuth;
        @SerializedName("SellPart")
        public String SellPart;
        @SerializedName("State")
        public String State;
        @SerializedName("TaskCnt")
        public int TaskCnt;
    }

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

    /* loaded from: classes.dex */
    public static class Shared_Project_List {
        @SerializedName("CategoryCnt")
        public int CategoryCnt;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("IsDelete")
        public boolean IsDelete;
        @SerializedName("IsInvite")
        public boolean IsInvite;
        @SerializedName("MakeID")
        public int MakeID;
        @SerializedName("MakeName")
        public String MakeName;
        @SerializedName("MemberCnt")
        public int MemberCnt;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("OKCategoryCnt")
        public int OKCategoryCnt;
        @SerializedName("OKTaskCnt")
        public int OKTaskCnt;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProjectCnt")
        public int ProjectCnt;
        @SerializedName("ProjectSate")
        public String ProjectSate;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
        @SerializedName("Project_Member")
        public List<Project_Member> Project_Member;
        @SerializedName("Project_Type")
        public String Project_Type;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("SellAuth")
        public String SellAuth;
        @SerializedName("SellPart")
        public String SellPart;
        @SerializedName("State")
        public String State;
        @SerializedName("TaskCnt")
        public int TaskCnt;
    }
}
