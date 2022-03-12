package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanTaskReceiveList {
    private List<CategoryListBean> Categorys;
    private TapList DefaultTap;
    private boolean IsWrite;
    private String ProjectTitle;
    private String RtnKey;
    private String RtnUrl;
    private String RtnValue;
    private List<TapList> TapList;

    /* loaded from: classes.dex */
    public static class TapList {
        @SerializedName("TapCode")
        public String TapCode;
        @SerializedName("TapName")
        public String TapName;
    }

    public String getRtnKey() {
        return this.RtnKey;
    }

    public void setRtnKey(String str) {
        this.RtnKey = str;
    }

    public String getRtnUrl() {
        return this.RtnUrl;
    }

    public void setRtnUrl(String str) {
        this.RtnUrl = str;
    }

    public String getRtnValue() {
        return this.RtnValue;
    }

    public void setRtnValue(String str) {
        this.RtnValue = str;
    }

    public boolean isIsWrite() {
        return this.IsWrite;
    }

    public void setIsWrite(boolean z) {
        this.IsWrite = z;
    }

    public List<CategoryListBean> getCategoryList() {
        return this.Categorys;
    }

    public void setCategoryList(List<CategoryListBean> list) {
        this.Categorys = list;
    }

    public List<TapList> getTapList() {
        return this.TapList;
    }

    public void setTapList(List<TapList> list) {
        this.TapList = list;
    }

    public TapList getDefaultTap() {
        return this.DefaultTap;
    }

    public void setDefaultTap(TapList tapList) {
        this.DefaultTap = tapList;
    }

    public String getProjectTitle() {
        return this.ProjectTitle;
    }

    public void setProjectTitle(String str) {
        this.ProjectTitle = str;
    }

    /* loaded from: classes.dex */
    public static class CategoryListBean {
        private int CGSEQ;
        private String CategoryTitle;
        private List<TaskList> Tasks;

        public int getCGSEQ() {
            return this.CGSEQ;
        }

        public void setCGSEQ(int i) {
            this.CGSEQ = i;
        }

        public List<TaskList> getTaskList() {
            return this.Tasks;
        }

        public void setTaskList(List<TaskList> list) {
            this.Tasks = list;
        }

        public String getCategoryTitle() {
            return this.CategoryTitle;
        }

        public void setCategoryTitle(String str) {
            this.CategoryTitle = str;
        }

        /* loaded from: classes.dex */
        public static class TaskList {
            public int CGSEQ;
            public String CategoryTitle;
            private String EDate;
            private int IsRequestOrTask;
            private int MaKeID;
            private String MakeName;
            private String MakeProfileImgUrl;
            private String RegDate;
            private int RunnerID;
            private String RunnerName;
            private String RunnerProfile;
            private String SDate;
            private int TSEQ;
            private String TaskState;
            private String TaskTitle;
            private String stringTaskState;

            public String getEDate() {
                return this.EDate;
            }

            public void setEDate(String str) {
                this.EDate = str;
            }

            public int getIsRequestOrTask() {
                return this.IsRequestOrTask;
            }

            public void setIsRequestOrTask(int i) {
                this.IsRequestOrTask = i;
            }

            public int getMaKeID() {
                return this.MaKeID;
            }

            public void setMaKeID(int i) {
                this.MaKeID = i;
            }

            public String getMakeName() {
                return this.MakeName;
            }

            public void setMakeName(String str) {
                this.MakeName = str;
            }

            public String getMakeProfileImgUrl() {
                return this.MakeProfileImgUrl;
            }

            public void setMakeProfileImgUrl(String str) {
                this.MakeProfileImgUrl = str;
            }

            public String getRegDate() {
                return this.RegDate;
            }

            public void setRegDate(String str) {
                this.RegDate = str;
            }

            public int getRunnerID() {
                return this.RunnerID;
            }

            public void setRunnerID(int i) {
                this.RunnerID = i;
            }

            public String getRunnerName() {
                return this.RunnerName;
            }

            public void setRunnerName(String str) {
                this.RunnerName = str;
            }

            public String getRunnerProfile() {
                return this.RunnerProfile;
            }

            public void setRunnerProfile(String str) {
                this.RunnerProfile = str;
            }

            public String getSDate() {
                return this.SDate;
            }

            public void setSDate(String str) {
                this.SDate = str;
            }

            public int getTSEQ() {
                return this.TSEQ;
            }

            public void setTSEQ(int i) {
                this.TSEQ = i;
            }

            public String getTaskState() {
                return this.TaskState;
            }

            public void setTaskState(String str) {
                this.TaskState = str;
            }

            public String getTaskTitle() {
                return this.TaskTitle;
            }

            public void setTaskTitle(String str) {
                this.TaskTitle = str;
            }

            public String getStringTaskState() {
                return this.stringTaskState;
            }

            public void setStringTaskState(String str) {
                this.stringTaskState = str;
            }
        }
    }
}
