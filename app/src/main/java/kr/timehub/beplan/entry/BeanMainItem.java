package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import kr.timehub.beplan.entry.plugin.BeanMain;

/* loaded from: classes.dex */
public class BeanMainItem {
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
    public List<BeanMain.Project_Member> Project_Member;
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

    public BeanMainItem(BeanMain.AllProjectList allProjectList) {
        this.InviteState = allProjectList.InviteState;
        this.IsDelete = allProjectList.IsDelete;
        this.IsInvite = allProjectList.IsInvite;
        this.MakeID = allProjectList.MakeID;
        this.ModDate = allProjectList.ModDate;
        this.PSEQ = allProjectList.PSEQ;
        this.ProjectCnt = allProjectList.ProjectCnt;
        this.ProjectSate = allProjectList.ProjectSate;
        this.ProjectTitle = allProjectList.ProjectTitle;
        this.Project_Member = allProjectList.Project_Member;
        this.Project_Type = allProjectList.Project_Type;
        this.RegDate = allProjectList.RegDate;
        this.SellAuth = allProjectList.SellAuth;
        this.SellPart = allProjectList.SellPart;
        this.State = allProjectList.State;
        this.CategoryCnt = allProjectList.CategoryCnt;
        this.MakeName = allProjectList.MakeName;
        this.MemberCnt = allProjectList.MemberCnt;
        this.OKCategoryCnt = allProjectList.OKCategoryCnt;
        this.OKTaskCnt = allProjectList.OKTaskCnt;
        this.TaskCnt = allProjectList.TaskCnt;
    }

    public BeanMainItem(BeanMain.Project_List project_List) {
        this.InviteState = project_List.InviteState;
        this.IsDelete = project_List.IsDelete;
        this.IsInvite = project_List.IsInvite;
        this.MakeID = project_List.MakeID;
        this.ModDate = project_List.ModDate;
        this.PSEQ = project_List.PSEQ;
        this.ProjectCnt = project_List.ProjectCnt;
        this.ProjectSate = project_List.ProjectSate;
        this.ProjectTitle = project_List.ProjectTitle;
        this.Project_Member = project_List.Project_Member;
        this.Project_Type = project_List.Project_Type;
        this.RegDate = project_List.RegDate;
        this.SellAuth = project_List.SellAuth;
        this.SellPart = project_List.SellPart;
        this.State = project_List.State;
        this.CategoryCnt = project_List.CategoryCnt;
        this.MakeName = project_List.MakeName;
        this.MemberCnt = project_List.MemberCnt;
        this.OKCategoryCnt = project_List.OKCategoryCnt;
        this.OKTaskCnt = project_List.OKTaskCnt;
        this.TaskCnt = project_List.TaskCnt;
    }

    public BeanMainItem(BeanMain.Shared_Project_List shared_Project_List) {
        this.InviteState = shared_Project_List.InviteState;
        this.IsDelete = shared_Project_List.IsDelete;
        this.IsInvite = shared_Project_List.IsInvite;
        this.MakeID = shared_Project_List.MakeID;
        this.ModDate = shared_Project_List.ModDate;
        this.PSEQ = shared_Project_List.PSEQ;
        this.ProjectCnt = shared_Project_List.ProjectCnt;
        this.ProjectSate = shared_Project_List.ProjectSate;
        this.ProjectTitle = shared_Project_List.ProjectTitle;
        this.Project_Member = shared_Project_List.Project_Member;
        this.Project_Type = shared_Project_List.Project_Type;
        this.RegDate = shared_Project_List.RegDate;
        this.SellAuth = shared_Project_List.SellAuth;
        this.SellPart = shared_Project_List.SellPart;
        this.State = shared_Project_List.State;
        this.CategoryCnt = shared_Project_List.CategoryCnt;
        this.MakeName = shared_Project_List.MakeName;
        this.MemberCnt = shared_Project_List.MemberCnt;
        this.OKCategoryCnt = shared_Project_List.OKCategoryCnt;
        this.OKTaskCnt = shared_Project_List.OKTaskCnt;
        this.TaskCnt = shared_Project_List.TaskCnt;
    }

    public BeanMain.Project_List getProjectList() {
        BeanMain.Project_List project_List = new BeanMain.Project_List();
        project_List.InviteState = this.InviteState;
        project_List.IsDelete = this.IsDelete;
        project_List.IsInvite = this.IsInvite;
        project_List.MakeID = this.MakeID;
        project_List.ModDate = this.ModDate;
        project_List.PSEQ = this.PSEQ;
        project_List.ProjectCnt = this.ProjectCnt;
        project_List.ProjectSate = this.ProjectSate;
        project_List.ProjectTitle = this.ProjectTitle;
        project_List.Project_Member = this.Project_Member;
        project_List.Project_Type = this.Project_Type;
        project_List.RegDate = this.RegDate;
        project_List.SellAuth = this.SellAuth;
        project_List.SellPart = this.SellPart;
        project_List.State = this.State;
        project_List.CategoryCnt = this.CategoryCnt;
        project_List.MakeName = this.MakeName;
        project_List.MemberCnt = this.MemberCnt;
        project_List.OKCategoryCnt = this.OKCategoryCnt;
        project_List.OKTaskCnt = this.OKTaskCnt;
        project_List.TaskCnt = this.TaskCnt;
        return project_List;
    }

    public BeanMain.Shared_Project_List getSharedProjectList() {
        BeanMain.Shared_Project_List shared_Project_List = new BeanMain.Shared_Project_List();
        shared_Project_List.InviteState = this.InviteState;
        shared_Project_List.IsDelete = this.IsDelete;
        shared_Project_List.IsInvite = this.IsInvite;
        shared_Project_List.MakeID = this.MakeID;
        shared_Project_List.ModDate = this.ModDate;
        shared_Project_List.PSEQ = this.PSEQ;
        shared_Project_List.ProjectCnt = this.ProjectCnt;
        shared_Project_List.ProjectSate = this.ProjectSate;
        shared_Project_List.ProjectTitle = this.ProjectTitle;
        shared_Project_List.Project_Member = this.Project_Member;
        shared_Project_List.Project_Type = this.Project_Type;
        shared_Project_List.RegDate = this.RegDate;
        shared_Project_List.SellAuth = this.SellAuth;
        shared_Project_List.SellPart = this.SellPart;
        shared_Project_List.State = this.State;
        shared_Project_List.CategoryCnt = this.CategoryCnt;
        shared_Project_List.MakeName = this.MakeName;
        shared_Project_List.MemberCnt = this.MemberCnt;
        shared_Project_List.OKCategoryCnt = this.OKCategoryCnt;
        shared_Project_List.OKTaskCnt = this.OKTaskCnt;
        shared_Project_List.TaskCnt = this.TaskCnt;
        return shared_Project_List;
    }
}
