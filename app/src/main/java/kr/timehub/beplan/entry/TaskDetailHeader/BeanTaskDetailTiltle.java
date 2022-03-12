package kr.timehub.beplan.entry.TaskDetailHeader;

/* loaded from: classes.dex */
public class BeanTaskDetailTiltle {
    private String Business;
    private String BusinessTitle;
    private boolean isCheck;

    public BeanTaskDetailTiltle(String str, String str2, boolean z) {
        this.BusinessTitle = str;
        this.Business = str2;
        this.isCheck = z;
    }

    public String getBusinessTitle() {
        return this.BusinessTitle;
    }

    public void setBusinessTitle(String str) {
        this.BusinessTitle = str;
    }

    public String getBusiness() {
        return this.Business;
    }

    public void setBusiness(String str) {
        this.Business = str;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean z) {
        this.isCheck = z;
    }
}
