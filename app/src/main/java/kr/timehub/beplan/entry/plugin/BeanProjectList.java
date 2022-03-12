package kr.timehub.beplan.entry.plugin;

import java.util.List;

/* loaded from: classes.dex */
public class BeanProjectList {
    private List<CategoryListBean> CategoryList;
    private NewTapListBean DefaultTap;
    private boolean IsWrite;
    private List<NewTapListBean> NewTapList;
    private String ProjectName;
    private String ProjectType;
    private String RtnKey;
    private String RtnUrl;
    private String RtnValue;
    private List<String> TapList;

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

    public String getProjectName() {
        return this.ProjectName;
    }

    public void setProjectName(String str) {
        this.ProjectName = str;
    }

    public List<CategoryListBean> getCategoryList() {
        return this.CategoryList;
    }

    public void setCategoryList(List<CategoryListBean> list) {
        this.CategoryList = list;
    }

    public List<NewTapListBean> getNewTapList() {
        return this.NewTapList;
    }

    public void setNewTapList(List<NewTapListBean> list) {
        this.NewTapList = list;
    }

    public List<String> getTapList() {
        return this.TapList;
    }

    public void setTapList(List<String> list) {
        this.TapList = list;
    }

    public NewTapListBean getDefaultTap() {
        return this.DefaultTap;
    }

    public void setDefaultTap(NewTapListBean newTapListBean) {
        this.DefaultTap = newTapListBean;
    }

    public String getProjectType() {
        return this.ProjectType;
    }

    public void setProjectType(String str) {
        this.ProjectType = str;
    }

    /* loaded from: classes.dex */
    public static class NewTapListBean {
        private String TapCode;
        private String TapName;

        public String getTapCode() {
            return this.TapCode;
        }

        public void setTapCode(String str) {
            this.TapCode = str;
        }

        public String getTapName() {
            return this.TapName;
        }

        public void setTapName(String str) {
            this.TapName = str;
        }
    }

    /* loaded from: classes.dex */
    public static class CategoryListBean {
        private int CGSEQ;
        private String CateGoryTitle;
        private boolean IsDelete;
        private boolean IsModify;
        private boolean IsRunnerModify;
        private int MakeID;
        private String ProjectType;
        private int SelectTab;
        private String State;
        private List<TaskListBean> TaskList;

        public int getCGSEQ() {
            return this.CGSEQ;
        }

        public void setCGSEQ(int i) {
            this.CGSEQ = i;
        }

        public String getCateGoryTitle() {
            return this.CateGoryTitle;
        }

        public void setCateGoryTitle(String str) {
            this.CateGoryTitle = str;
        }

        public boolean isIsDelete() {
            return this.IsDelete;
        }

        public void setIsDelete(boolean z) {
            this.IsDelete = z;
        }

        public boolean isIsModify() {
            return this.IsModify;
        }

        public void setIsModify(boolean z) {
            this.IsModify = z;
        }

        public boolean isIsRunnerModify() {
            return this.IsRunnerModify;
        }

        public void setIsRunnerModify(boolean z) {
            this.IsRunnerModify = z;
        }

        public int getMakeID() {
            return this.MakeID;
        }

        public void setMakeID(int i) {
            this.MakeID = i;
        }

        public int getSelectTab() {
            return this.SelectTab;
        }

        public void setSelectTab(int i) {
            this.SelectTab = i;
        }

        public String getState() {
            return this.State;
        }

        public void setState(String str) {
            this.State = str;
        }

        public List<TaskListBean> getTaskList() {
            return this.TaskList;
        }

        public void setTaskList(List<TaskListBean> list) {
            this.TaskList = list;
        }

        public String getProjectType() {
            return this.ProjectType;
        }

        public void setProjectType(String str) {
            this.ProjectType = str;
        }

        /* loaded from: classes.dex */
        public static class TaskListBean {
            private int CGSEQ;
            private String CateGoryTitle;
            private String Edate;
            private boolean IsDelete;
            private boolean IsFinish;
            private boolean IsModify;
            private int MakeID;
            private int RunnerID;
            private String Sdate;
            private int TSEQ;
            private String TaskState;
            private String TaskTitle;
            private boolean amI;
            private String name;
            private String profile;
            private String stringTaskState;

            public int getCGSEQ() {
                return this.CGSEQ;
            }

            public void setCGSEQ(int i) {
                this.CGSEQ = i;
            }

            public String getEdate() {
                return this.Edate;
            }

            public void setEdate(String str) {
                this.Edate = str;
            }

            public boolean isIsDelete() {
                return this.IsDelete;
            }

            public void setIsDelete(boolean z) {
                this.IsDelete = z;
            }

            public boolean isIsFinish() {
                return this.IsFinish;
            }

            public void setIsFinish(boolean z) {
                this.IsFinish = z;
            }

            public boolean isIsModify() {
                return this.IsModify;
            }

            public void setIsModify(boolean z) {
                this.IsModify = z;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public int getRunnerID() {
                return this.RunnerID;
            }

            public void setRunnerID(int i) {
                this.RunnerID = i;
            }

            public String getSdate() {
                return this.Sdate;
            }

            public void setSdate(String str) {
                this.Sdate = str;
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

            public boolean isAmI() {
                return this.amI;
            }

            public void setAmI(boolean z) {
                this.amI = z;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getProfile() {
                return this.profile;
            }

            public void setProfile(String str) {
                this.profile = str;
            }

            public String getStringTaskState() {
                return this.stringTaskState;
            }

            public void setStringTaskState(String str) {
                this.stringTaskState = str;
            }

            public String getCateGoryTitle() {
                return this.CateGoryTitle;
            }

            public void setCateGoryTitle(String str) {
                this.CateGoryTitle = str;
            }
        }
    }
}
