package kr.timehub.beplan.entry;

import java.util.List;

/* loaded from: classes.dex */
public class BeanShopIndex {
    private String BoughtYN;
    private List<CateGoryListBean> CateGoryList;
    private int CommID;
    private String MainTitle;
    private int PSEQ;
    private String ProjectTitle;
    private String RtnKey;
    private String RtnUrl;
    private String RtnValue;
    private int SMCSEQ;
    private List<ShopCateGoryListBean> ShopCateGoryList;
    private List<ShopMainListBean> ShopMainList;
    private List<ShopProjectListBean> ShopProjectList;
    private List<ViewSMCateGoryListBean> ViewSMCateGoryList;

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

    public String getBoughtYN() {
        return this.BoughtYN;
    }

    public void setBoughtYN(String str) {
        this.BoughtYN = str;
    }

    public int getCommID() {
        return this.CommID;
    }

    public void setCommID(int i) {
        this.CommID = i;
    }

    public String getMainTitle() {
        return this.MainTitle;
    }

    public void setMainTitle(String str) {
        this.MainTitle = str;
    }

    public int getPSEQ() {
        return this.PSEQ;
    }

    public void setPSEQ(int i) {
        this.PSEQ = i;
    }

    public String getProjectTitle() {
        return this.ProjectTitle;
    }

    public void setProjectTitle(String str) {
        this.ProjectTitle = str;
    }

    public int getSMCSEQ() {
        return this.SMCSEQ;
    }

    public void setSMCSEQ(int i) {
        this.SMCSEQ = i;
    }

    public List<CateGoryListBean> getCateGoryList() {
        return this.CateGoryList;
    }

    public void setCateGoryList(List<CateGoryListBean> list) {
        this.CateGoryList = list;
    }

    public List<ShopCateGoryListBean> getShopCateGoryList() {
        return this.ShopCateGoryList;
    }

    public void setShopCateGoryList(List<ShopCateGoryListBean> list) {
        this.ShopCateGoryList = list;
    }

    public List<ShopMainListBean> getShopMainList() {
        return this.ShopMainList;
    }

    public void setShopMainList(List<ShopMainListBean> list) {
        this.ShopMainList = list;
    }

    public List<ShopProjectListBean> getShopProjectList() {
        return this.ShopProjectList;
    }

    public void setShopProjectList(List<ShopProjectListBean> list) {
        this.ShopProjectList = list;
    }

    public List<ViewSMCateGoryListBean> getViewSMCateGoryList() {
        return this.ViewSMCateGoryList;
    }

    public void setViewSMCateGoryList(List<ViewSMCateGoryListBean> list) {
        this.ViewSMCateGoryList = list;
    }

    /* loaded from: classes.dex */
    public static class CateGoryListBean {
        private String BoughtYN;
        private int CGSEQ;
        private String CateGoryTitle;
        private String CategorySate;
        private int MakeID;
        private int PSEQ;
        private String ProjectTitle;
        private List<TasksListBean> Tasks_List;

        public String getBoughtYN() {
            return this.BoughtYN;
        }

        public void setBoughtYN(String str) {
            this.BoughtYN = str;
        }

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

        public String getCategorySate() {
            return this.CategorySate;
        }

        public void setCategorySate(String str) {
            this.CategorySate = str;
        }

        public int getMakeID() {
            return this.MakeID;
        }

        public void setMakeID(int i) {
            this.MakeID = i;
        }

        public int getPSEQ() {
            return this.PSEQ;
        }

        public void setPSEQ(int i) {
            this.PSEQ = i;
        }

        public String getProjectTitle() {
            return this.ProjectTitle;
        }

        public void setProjectTitle(String str) {
            this.ProjectTitle = str;
        }

        public List<TasksListBean> getTasks_List() {
            return this.Tasks_List;
        }

        public void setTasks_List(List<TasksListBean> list) {
            this.Tasks_List = list;
        }

        /* loaded from: classes.dex */
        public static class TasksListBean {
            private int CGSEQ;
            private String Comment;
            private String EDate;
            private int MakeID;
            private String MakerName;
            private String ProfileImgUrl;
            private String RealName;
            private String RecieveName;
            private String RegDate;
            private int RunnerID;
            private String SDate;
            private String SenderName;
            private int TSEQ;
            private String TaskState;
            private String TaskTitle;

            public int getCGSEQ() {
                return this.CGSEQ;
            }

            public void setCGSEQ(int i) {
                this.CGSEQ = i;
            }

            public String getComment() {
                return this.Comment;
            }

            public void setComment(String str) {
                this.Comment = str;
            }

            public String getEDate() {
                return this.EDate;
            }

            public void setEDate(String str) {
                this.EDate = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public String getMakerName() {
                return this.MakerName;
            }

            public void setMakerName(String str) {
                this.MakerName = str;
            }

            public String getProfileImgUrl() {
                return this.ProfileImgUrl;
            }

            public void setProfileImgUrl(String str) {
                this.ProfileImgUrl = str;
            }

            public String getRealName() {
                return this.RealName;
            }

            public void setRealName(String str) {
                this.RealName = str;
            }

            public String getRecieveName() {
                return this.RecieveName;
            }

            public void setRecieveName(String str) {
                this.RecieveName = str;
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

            public String getSDate() {
                return this.SDate;
            }

            public void setSDate(String str) {
                this.SDate = str;
            }

            public String getSenderName() {
                return this.SenderName;
            }

            public void setSenderName(String str) {
                this.SenderName = str;
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
        }
    }

    /* loaded from: classes.dex */
    public static class ShopCateGoryListBean {
        private int Amount;
        private String BoughtYn;
        private String CateGoryTitle;
        private String DeleteYn;
        private int MakeID;
        private String MakerName;
        private String MakerProfileImgUrl;
        private String PaidFree;
        private int SCGSEQ;
        private int SMCSEQ;
        private String SMCateGoryTitle;
        private int SPSEQ;
        private int SameNameCount;
        private List<TaskListBean> TaskList;

        public int getAmount() {
            return this.Amount;
        }

        public void setAmount(int i) {
            this.Amount = i;
        }

        public String getBoughtYn() {
            return this.BoughtYn;
        }

        public void setBoughtYn(String str) {
            this.BoughtYn = str;
        }

        public String getCateGoryTitle() {
            return this.CateGoryTitle;
        }

        public void setCateGoryTitle(String str) {
            this.CateGoryTitle = str;
        }

        public String getDeleteYn() {
            return this.DeleteYn;
        }

        public void setDeleteYn(String str) {
            this.DeleteYn = str;
        }

        public int getMakeID() {
            return this.MakeID;
        }

        public void setMakeID(int i) {
            this.MakeID = i;
        }

        public String getMakerName() {
            return this.MakerName;
        }

        public void setMakerName(String str) {
            this.MakerName = str;
        }

        public String getMakerProfileImgUrl() {
            return this.MakerProfileImgUrl;
        }

        public void setMakerProfileImgUrl(String str) {
            this.MakerProfileImgUrl = str;
        }

        public String getPaidFree() {
            return this.PaidFree;
        }

        public void setPaidFree(String str) {
            this.PaidFree = str;
        }

        public int getSCGSEQ() {
            return this.SCGSEQ;
        }

        public void setSCGSEQ(int i) {
            this.SCGSEQ = i;
        }

        public int getSMCSEQ() {
            return this.SMCSEQ;
        }

        public void setSMCSEQ(int i) {
            this.SMCSEQ = i;
        }

        public String getSMCateGoryTitle() {
            return this.SMCateGoryTitle;
        }

        public void setSMCateGoryTitle(String str) {
            this.SMCateGoryTitle = str;
        }

        public int getSPSEQ() {
            return this.SPSEQ;
        }

        public void setSPSEQ(int i) {
            this.SPSEQ = i;
        }

        public int getSameNameCount() {
            return this.SameNameCount;
        }

        public void setSameNameCount(int i) {
            this.SameNameCount = i;
        }

        public List<TaskListBean> getTaskList() {
            return this.TaskList;
        }

        public void setTaskList(List<TaskListBean> list) {
            this.TaskList = list;
        }

        /* loaded from: classes.dex */
        public static class TaskListBean {
            private String DeleteYn;
            private int MakeID;
            private int SCGSEQ;
            private int STSEQ;
            private String TaskTitle;

            public String getDeleteYn() {
                return this.DeleteYn;
            }

            public void setDeleteYn(String str) {
                this.DeleteYn = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public int getSCGSEQ() {
                return this.SCGSEQ;
            }

            public void setSCGSEQ(int i) {
                this.SCGSEQ = i;
            }

            public int getSTSEQ() {
                return this.STSEQ;
            }

            public void setSTSEQ(int i) {
                this.STSEQ = i;
            }

            public String getTaskTitle() {
                return this.TaskTitle;
            }

            public void setTaskTitle(String str) {
                this.TaskTitle = str;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ShopMainListBean {
        private int CateGoryCount;
        private List<CateGoryListBeanX> CateGoryList;
        private String DeleteYN;
        private List<ProjectListBean> ProjectList;
        private int SMCSEQ;
        private String SMCateGoryTitle;

        public int getCateGoryCount() {
            return this.CateGoryCount;
        }

        public void setCateGoryCount(int i) {
            this.CateGoryCount = i;
        }

        public String getDeleteYN() {
            return this.DeleteYN;
        }

        public void setDeleteYN(String str) {
            this.DeleteYN = str;
        }

        public int getSMCSEQ() {
            return this.SMCSEQ;
        }

        public void setSMCSEQ(int i) {
            this.SMCSEQ = i;
        }

        public String getSMCateGoryTitle() {
            return this.SMCateGoryTitle;
        }

        public void setSMCateGoryTitle(String str) {
            this.SMCateGoryTitle = str;
        }

        public List<CateGoryListBeanX> getCateGoryList() {
            return this.CateGoryList;
        }

        public void setCateGoryList(List<CateGoryListBeanX> list) {
            this.CateGoryList = list;
        }

        public List<ProjectListBean> getProjectList() {
            return this.ProjectList;
        }

        public void setProjectList(List<ProjectListBean> list) {
            this.ProjectList = list;
        }

        /* loaded from: classes.dex */
        public static class CateGoryListBeanX {
            private int Amount;
            private String BoughtYn;
            private String CateGoryTitle;
            private String DeleteYn;
            private int MakeID;
            private String MakerName;
            private String MakerProfileImgUrl;
            private String PaidFree;
            private int SCGSEQ;
            private int SMCSEQ;
            private String SMCateGoryTitle;
            private int SPSEQ;
            private int SameNameCount;
            private List<TaskListBeanX> TaskList;

            public int getAmount() {
                return this.Amount;
            }

            public void setAmount(int i) {
                this.Amount = i;
            }

            public String getBoughtYn() {
                return this.BoughtYn;
            }

            public void setBoughtYn(String str) {
                this.BoughtYn = str;
            }

            public String getCateGoryTitle() {
                return this.CateGoryTitle;
            }

            public void setCateGoryTitle(String str) {
                this.CateGoryTitle = str;
            }

            public String getDeleteYn() {
                return this.DeleteYn;
            }

            public void setDeleteYn(String str) {
                this.DeleteYn = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public String getMakerName() {
                return this.MakerName;
            }

            public void setMakerName(String str) {
                this.MakerName = str;
            }

            public String getMakerProfileImgUrl() {
                return this.MakerProfileImgUrl;
            }

            public void setMakerProfileImgUrl(String str) {
                this.MakerProfileImgUrl = str;
            }

            public String getPaidFree() {
                return this.PaidFree;
            }

            public void setPaidFree(String str) {
                this.PaidFree = str;
            }

            public int getSCGSEQ() {
                return this.SCGSEQ;
            }

            public void setSCGSEQ(int i) {
                this.SCGSEQ = i;
            }

            public int getSMCSEQ() {
                return this.SMCSEQ;
            }

            public void setSMCSEQ(int i) {
                this.SMCSEQ = i;
            }

            public String getSMCateGoryTitle() {
                return this.SMCateGoryTitle;
            }

            public void setSMCateGoryTitle(String str) {
                this.SMCateGoryTitle = str;
            }

            public int getSPSEQ() {
                return this.SPSEQ;
            }

            public void setSPSEQ(int i) {
                this.SPSEQ = i;
            }

            public int getSameNameCount() {
                return this.SameNameCount;
            }

            public void setSameNameCount(int i) {
                this.SameNameCount = i;
            }

            public List<TaskListBeanX> getTaskList() {
                return this.TaskList;
            }

            public void setTaskList(List<TaskListBeanX> list) {
                this.TaskList = list;
            }

            /* loaded from: classes.dex */
            public static class TaskListBeanX {
                private String DeleteYn;
                private int MakeID;
                private int SCGSEQ;
                private int STSEQ;
                private String TaskTitle;

                public String getDeleteYn() {
                    return this.DeleteYn;
                }

                public void setDeleteYn(String str) {
                    this.DeleteYn = str;
                }

                public int getMakeID() {
                    return this.MakeID;
                }

                public void setMakeID(int i) {
                    this.MakeID = i;
                }

                public int getSCGSEQ() {
                    return this.SCGSEQ;
                }

                public void setSCGSEQ(int i) {
                    this.SCGSEQ = i;
                }

                public int getSTSEQ() {
                    return this.STSEQ;
                }

                public void setSTSEQ(int i) {
                    this.STSEQ = i;
                }

                public String getTaskTitle() {
                    return this.TaskTitle;
                }

                public void setTaskTitle(String str) {
                    this.TaskTitle = str;
                }
            }
        }

        /* loaded from: classes.dex */
        public static class ProjectListBean {
            private String BoughtYn;
            private String DeleteYn;
            private int MakeID;
            private int MasterSEQ;
            private String ModDate;
            private String PaidFree;
            private String ProjectTitle;
            private String RegDate;
            private int SMCSEQ;
            private String SMCateGoryTitle;
            private int SPSEQ;
            private int TotalAmount;

            public String getBoughtYn() {
                return this.BoughtYn;
            }

            public void setBoughtYn(String str) {
                this.BoughtYn = str;
            }

            public String getDeleteYn() {
                return this.DeleteYn;
            }

            public void setDeleteYn(String str) {
                this.DeleteYn = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public int getMasterSEQ() {
                return this.MasterSEQ;
            }

            public void setMasterSEQ(int i) {
                this.MasterSEQ = i;
            }

            public String getModDate() {
                return this.ModDate;
            }

            public void setModDate(String str) {
                this.ModDate = str;
            }

            public String getPaidFree() {
                return this.PaidFree;
            }

            public void setPaidFree(String str) {
                this.PaidFree = str;
            }

            public String getProjectTitle() {
                return this.ProjectTitle;
            }

            public void setProjectTitle(String str) {
                this.ProjectTitle = str;
            }

            public String getRegDate() {
                return this.RegDate;
            }

            public void setRegDate(String str) {
                this.RegDate = str;
            }

            public int getSMCSEQ() {
                return this.SMCSEQ;
            }

            public void setSMCSEQ(int i) {
                this.SMCSEQ = i;
            }

            public String getSMCateGoryTitle() {
                return this.SMCateGoryTitle;
            }

            public void setSMCateGoryTitle(String str) {
                this.SMCateGoryTitle = str;
            }

            public int getSPSEQ() {
                return this.SPSEQ;
            }

            public void setSPSEQ(int i) {
                this.SPSEQ = i;
            }

            public int getTotalAmount() {
                return this.TotalAmount;
            }

            public void setTotalAmount(int i) {
                this.TotalAmount = i;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ShopProjectListBean {
        private String BoughtYn;
        private String DeleteYn;
        private int MakeID;
        private int MasterSEQ;
        private String ModDate;
        private String PaidFree;
        private String ProjectTitle;
        private String RegDate;
        private int SMCSEQ;
        private String SMCateGoryTitle;
        private int SPSEQ;
        private int TotalAmount;

        public String getBoughtYn() {
            return this.BoughtYn;
        }

        public void setBoughtYn(String str) {
            this.BoughtYn = str;
        }

        public String getDeleteYn() {
            return this.DeleteYn;
        }

        public void setDeleteYn(String str) {
            this.DeleteYn = str;
        }

        public int getMakeID() {
            return this.MakeID;
        }

        public void setMakeID(int i) {
            this.MakeID = i;
        }

        public int getMasterSEQ() {
            return this.MasterSEQ;
        }

        public void setMasterSEQ(int i) {
            this.MasterSEQ = i;
        }

        public String getModDate() {
            return this.ModDate;
        }

        public void setModDate(String str) {
            this.ModDate = str;
        }

        public String getPaidFree() {
            return this.PaidFree;
        }

        public void setPaidFree(String str) {
            this.PaidFree = str;
        }

        public String getProjectTitle() {
            return this.ProjectTitle;
        }

        public void setProjectTitle(String str) {
            this.ProjectTitle = str;
        }

        public String getRegDate() {
            return this.RegDate;
        }

        public void setRegDate(String str) {
            this.RegDate = str;
        }

        public int getSMCSEQ() {
            return this.SMCSEQ;
        }

        public void setSMCSEQ(int i) {
            this.SMCSEQ = i;
        }

        public String getSMCateGoryTitle() {
            return this.SMCateGoryTitle;
        }

        public void setSMCateGoryTitle(String str) {
            this.SMCateGoryTitle = str;
        }

        public int getSPSEQ() {
            return this.SPSEQ;
        }

        public void setSPSEQ(int i) {
            this.SPSEQ = i;
        }

        public int getTotalAmount() {
            return this.TotalAmount;
        }

        public void setTotalAmount(int i) {
            this.TotalAmount = i;
        }
    }

    /* loaded from: classes.dex */
    public static class ViewSMCateGoryListBean {
        private int CateGoryCount;
        private List<CateGoryListBeanXX> CateGoryList;
        private String DeleteYN;
        private List<ProjectListBeanX> ProjectList;
        private int SMCSEQ;
        private String SMCateGoryTitle;

        public int getCateGoryCount() {
            return this.CateGoryCount;
        }

        public void setCateGoryCount(int i) {
            this.CateGoryCount = i;
        }

        public String getDeleteYN() {
            return this.DeleteYN;
        }

        public void setDeleteYN(String str) {
            this.DeleteYN = str;
        }

        public int getSMCSEQ() {
            return this.SMCSEQ;
        }

        public void setSMCSEQ(int i) {
            this.SMCSEQ = i;
        }

        public String getSMCateGoryTitle() {
            return this.SMCateGoryTitle;
        }

        public void setSMCateGoryTitle(String str) {
            this.SMCateGoryTitle = str;
        }

        public List<CateGoryListBeanXX> getCateGoryList() {
            return this.CateGoryList;
        }

        public void setCateGoryList(List<CateGoryListBeanXX> list) {
            this.CateGoryList = list;
        }

        public List<ProjectListBeanX> getProjectList() {
            return this.ProjectList;
        }

        public void setProjectList(List<ProjectListBeanX> list) {
            this.ProjectList = list;
        }

        /* loaded from: classes.dex */
        public static class CateGoryListBeanXX {
            private int Amount;
            private String BoughtYn;
            private String CateGoryTitle;
            private String DeleteYn;
            private int MakeID;
            private String MakerName;
            private String MakerProfileImgUrl;
            private String PaidFree;
            private int SCGSEQ;
            private int SMCSEQ;
            private String SMCateGoryTitle;
            private int SPSEQ;
            private int SameNameCount;
            private List<TaskListBeanXX> TaskList;

            public int getAmount() {
                return this.Amount;
            }

            public void setAmount(int i) {
                this.Amount = i;
            }

            public String getBoughtYn() {
                return this.BoughtYn;
            }

            public void setBoughtYn(String str) {
                this.BoughtYn = str;
            }

            public String getCateGoryTitle() {
                return this.CateGoryTitle;
            }

            public void setCateGoryTitle(String str) {
                this.CateGoryTitle = str;
            }

            public String getDeleteYn() {
                return this.DeleteYn;
            }

            public void setDeleteYn(String str) {
                this.DeleteYn = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public String getMakerName() {
                return this.MakerName;
            }

            public void setMakerName(String str) {
                this.MakerName = str;
            }

            public String getMakerProfileImgUrl() {
                return this.MakerProfileImgUrl;
            }

            public void setMakerProfileImgUrl(String str) {
                this.MakerProfileImgUrl = str;
            }

            public String getPaidFree() {
                return this.PaidFree;
            }

            public void setPaidFree(String str) {
                this.PaidFree = str;
            }

            public int getSCGSEQ() {
                return this.SCGSEQ;
            }

            public void setSCGSEQ(int i) {
                this.SCGSEQ = i;
            }

            public int getSMCSEQ() {
                return this.SMCSEQ;
            }

            public void setSMCSEQ(int i) {
                this.SMCSEQ = i;
            }

            public String getSMCateGoryTitle() {
                return this.SMCateGoryTitle;
            }

            public void setSMCateGoryTitle(String str) {
                this.SMCateGoryTitle = str;
            }

            public int getSPSEQ() {
                return this.SPSEQ;
            }

            public void setSPSEQ(int i) {
                this.SPSEQ = i;
            }

            public int getSameNameCount() {
                return this.SameNameCount;
            }

            public void setSameNameCount(int i) {
                this.SameNameCount = i;
            }

            public List<TaskListBeanXX> getTaskList() {
                return this.TaskList;
            }

            public void setTaskList(List<TaskListBeanXX> list) {
                this.TaskList = list;
            }

            /* loaded from: classes.dex */
            public static class TaskListBeanXX {
                private String DeleteYn;
                private int MakeID;
                private int SCGSEQ;
                private int STSEQ;
                private String TaskTitle;

                public String getDeleteYn() {
                    return this.DeleteYn;
                }

                public void setDeleteYn(String str) {
                    this.DeleteYn = str;
                }

                public int getMakeID() {
                    return this.MakeID;
                }

                public void setMakeID(int i) {
                    this.MakeID = i;
                }

                public int getSCGSEQ() {
                    return this.SCGSEQ;
                }

                public void setSCGSEQ(int i) {
                    this.SCGSEQ = i;
                }

                public int getSTSEQ() {
                    return this.STSEQ;
                }

                public void setSTSEQ(int i) {
                    this.STSEQ = i;
                }

                public String getTaskTitle() {
                    return this.TaskTitle;
                }

                public void setTaskTitle(String str) {
                    this.TaskTitle = str;
                }
            }
        }

        /* loaded from: classes.dex */
        public static class ProjectListBeanX {
            private String BoughtYn;
            private String DeleteYn;
            private int MakeID;
            private int MasterSEQ;
            private String ModDate;
            private String PaidFree;
            private String ProjectTitle;
            private String RegDate;
            private int SMCSEQ;
            private String SMCateGoryTitle;
            private int SPSEQ;
            private int TotalAmount;

            public String getBoughtYn() {
                return this.BoughtYn;
            }

            public void setBoughtYn(String str) {
                this.BoughtYn = str;
            }

            public String getDeleteYn() {
                return this.DeleteYn;
            }

            public void setDeleteYn(String str) {
                this.DeleteYn = str;
            }

            public int getMakeID() {
                return this.MakeID;
            }

            public void setMakeID(int i) {
                this.MakeID = i;
            }

            public int getMasterSEQ() {
                return this.MasterSEQ;
            }

            public void setMasterSEQ(int i) {
                this.MasterSEQ = i;
            }

            public String getModDate() {
                return this.ModDate;
            }

            public void setModDate(String str) {
                this.ModDate = str;
            }

            public String getPaidFree() {
                return this.PaidFree;
            }

            public void setPaidFree(String str) {
                this.PaidFree = str;
            }

            public String getProjectTitle() {
                return this.ProjectTitle;
            }

            public void setProjectTitle(String str) {
                this.ProjectTitle = str;
            }

            public String getRegDate() {
                return this.RegDate;
            }

            public void setRegDate(String str) {
                this.RegDate = str;
            }

            public int getSMCSEQ() {
                return this.SMCSEQ;
            }

            public void setSMCSEQ(int i) {
                this.SMCSEQ = i;
            }

            public String getSMCateGoryTitle() {
                return this.SMCateGoryTitle;
            }

            public void setSMCateGoryTitle(String str) {
                this.SMCateGoryTitle = str;
            }

            public int getSPSEQ() {
                return this.SPSEQ;
            }

            public void setSPSEQ(int i) {
                this.SPSEQ = i;
            }

            public int getTotalAmount() {
                return this.TotalAmount;
            }

            public void setTotalAmount(int i) {
                this.TotalAmount = i;
            }
        }
    }
}
