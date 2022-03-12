package kr.timehub.beplan.entry.common;

import java.util.List;
import kr.timehub.beplan.entry.plugin.BeanMain;

/* loaded from: classes.dex */
public class BeanDrawerList {
    public static final int TYPE_MY_PROJECT = 1;
    public static final int TYPE_SHARED_PROJECT = 2;
    private String InviteState;
    private int ProjectType;
    private List<BeanMain.Project_Member> project_members;
    private int seq;
    private String title;

    public BeanDrawerList() {
    }

    public BeanDrawerList(int i, String str, List<BeanMain.Project_Member> list, String str2) {
        this.seq = i;
        this.title = str;
        this.project_members = list;
        this.InviteState = str2;
    }

    public int getProjectType() {
        return this.ProjectType;
    }

    public void setProjectType(int i) {
        this.ProjectType = i;
    }

    public String getInviteState() {
        return this.InviteState;
    }

    public void setInviteState(String str) {
        this.InviteState = str;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int i) {
        this.seq = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public List<BeanMain.Project_Member> getProject_members() {
        return this.project_members;
    }

    public void setProject_members(List<BeanMain.Project_Member> list) {
        this.project_members = list;
    }
}
