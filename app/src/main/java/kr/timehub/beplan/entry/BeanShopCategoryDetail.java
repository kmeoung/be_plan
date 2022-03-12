package kr.timehub.beplan.entry;

import java.util.List;

/* loaded from: classes.dex */
public class BeanShopCategoryDetail {
    private int CGSEQ;
    private List<CategoryListBean> CategoryList;
    private int CommID;
    private String MainCategoryTitle;
    private String RtnKey;
    private String RtnUrl;
    private String RtnValue;
    private List<ShopMainListBean> ShopMainList;

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

    public int getCGSEQ() {
        return this.CGSEQ;
    }

    public void setCGSEQ(int i) {
        this.CGSEQ = i;
    }

    public int getCommID() {
        return this.CommID;
    }

    public void setCommID(int i) {
        this.CommID = i;
    }

    public String getMainCategoryTitle() {
        return this.MainCategoryTitle;
    }

    public void setMainCategoryTitle(String str) {
        this.MainCategoryTitle = str;
    }

    public List<CategoryListBean> getCategoryList() {
        return this.CategoryList;
    }

    public void setCategoryList(List<CategoryListBean> list) {
        this.CategoryList = list;
    }

    public List<ShopMainListBean> getShopMainList() {
        return this.ShopMainList;
    }

    public void setShopMainList(List<ShopMainListBean> list) {
        this.ShopMainList = list;
    }

    /* loaded from: classes.dex */
    public static class CategoryListBean {
        private int Amount;
        private String BoughtYn;
        private String CateGoryTitle;
        private int MakeID;
        private String MakerName;
        private String MakerProfileImgUrl;
        private String PaidFree;
        private int SCGSEQ;
        private String SMCateGoryTitle;
        private int SPSEQ;
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
        private List<CateGoryListBean> CateGoryList;
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

        public List<CateGoryListBean> getCateGoryList() {
            return this.CateGoryList;
        }

        public void setCateGoryList(List<CateGoryListBean> list) {
            this.CateGoryList = list;
        }

        public List<ProjectListBean> getProjectList() {
            return this.ProjectList;
        }

        public void setProjectList(List<ProjectListBean> list) {
            this.ProjectList = list;
        }

        /* loaded from: classes.dex */
        public static class CateGoryListBean {
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
}
