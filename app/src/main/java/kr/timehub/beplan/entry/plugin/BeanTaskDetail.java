package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanTaskDetail {
    public static final int TYPE_HEADER_RUNNER = 1;
    public static final int TYPE_HEADER_TITLE = 0;
    @SerializedName("Auth")
    public String Auth;
    @SerializedName("CGSEQ")
    public int CGSEQ;
    @SerializedName("CateGoryTitle")
    public String CateGoryTitle;
    @SerializedName("ContentsUrl")
    public List<ContentsUrl> ContentsUrl;
    @SerializedName("EDate")
    public String EDate;
    @SerializedName("IsDelete")
    public boolean IsDelete;
    @SerializedName("IsFinish")
    public boolean IsFinish;
    @SerializedName("IsModify")
    public boolean IsModify;
    @SerializedName("IsRequestOrTask")
    public int IsRequestOrTask;
    @SerializedName("MaKeID")
    public String MaKeID;
    @SerializedName("MakerProfileImgUrl")
    public String MakerProfileImgUrl;
    @SerializedName("MakerRealName")
    public String MakerRealName;
    @SerializedName("ProfileImgUrl")
    public String ProfileImgUrl;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("RealName")
    public String RealName;
    @SerializedName("RegDate")
    public String RegDate;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("RunnerID")
    public String RunnerID;
    @SerializedName("SDate")
    public String SDate;
    @SerializedName("TSEQ")
    public int TSEQ;
    @SerializedName("TaskState")
    public String TaskState;
    @SerializedName("TaskTitle")
    public String TaskTitle;
    @SerializedName("Tasks_Comment")
    public List<Tasks_Comment> Tasks_Comment;
    public int Type;

    /* loaded from: classes.dex */
    public static class CommentAddContentList {
        @SerializedName("CACSEQ")
        public int CACSEQ;
        @SerializedName("ContentsPart")
        public String ContentsPart;
        @SerializedName("ContentsUrl")
        public String ContentsUrl;
        @SerializedName("DeleteYN")
        public String DeleteYN;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("TCSEQ")
        public int TCSEQ;
        @SerializedName("ThumnailUrl")
        public String ThumnailUrl;
        @SerializedName("ThumnailUrlResult")
        public String ThumnailUrlResult;
        @SerializedName("WebThumnailUrl")
        public String WebThumnailUrl;
        @SerializedName("WorkLinkThUrl")
        public String WorkLinkThUrl;
    }

    /* loaded from: classes.dex */
    public static class Tasks_Comment {
        @SerializedName("Comment")
        public String Comment;
        @SerializedName("CommentAddContentList")
        public List<CommentAddContentList> CommentAddContentList;
        @SerializedName("Comment_Type")
        public String Comment_Type;
        @SerializedName("Email")
        public String Email;
        @SerializedName("IsDelete")
        public boolean IsDelete;
        @SerializedName("IsModify")
        public boolean IsModify;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("RegID")
        public int RegID;
        @SerializedName("TCSEQ")
        public int TCSEQ;
        @SerializedName("TSEQ")
        public int TSEQ;
        @SerializedName("makeId")
        public int makeId;
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int i) {
        this.Type = i;
    }

    public BeanTaskDetail() {
    }

    public BeanTaskDetail(BeanTaskDetail beanTaskDetail) {
        this.Type = beanTaskDetail.Type;
        this.RtnKey = beanTaskDetail.RtnKey;
        this.RtnUrl = beanTaskDetail.RtnUrl;
        this.RtnValue = beanTaskDetail.RtnValue;
        this.Auth = beanTaskDetail.Auth;
        this.CGSEQ = beanTaskDetail.CGSEQ;
        this.CateGoryTitle = beanTaskDetail.CateGoryTitle;
        this.ContentsUrl = beanTaskDetail.ContentsUrl;
        this.EDate = beanTaskDetail.EDate;
        this.IsDelete = beanTaskDetail.IsDelete;
        this.IsFinish = beanTaskDetail.IsFinish;
        this.IsModify = beanTaskDetail.IsModify;
        this.IsRequestOrTask = beanTaskDetail.IsRequestOrTask;
        this.MaKeID = beanTaskDetail.MaKeID;
        this.MakerProfileImgUrl = beanTaskDetail.MakerProfileImgUrl;
        this.MakerRealName = beanTaskDetail.MakerRealName;
        this.ProfileImgUrl = beanTaskDetail.ProfileImgUrl;
        this.ProjectTitle = beanTaskDetail.ProjectTitle;
        this.RealName = beanTaskDetail.RealName;
        this.RegDate = beanTaskDetail.RegDate;
        this.RunnerID = beanTaskDetail.RunnerID;
        this.SDate = beanTaskDetail.SDate;
        this.TSEQ = beanTaskDetail.TSEQ;
        this.TaskState = beanTaskDetail.TaskState;
        this.TaskTitle = beanTaskDetail.TaskTitle;
        this.Tasks_Comment = beanTaskDetail.Tasks_Comment;
    }

    /* loaded from: classes.dex */
    public static class ContentsUrl {
        private List<ContentsUrl> ContentList;
        @SerializedName("ContentsPart")
        public String ContentsPart;
        @SerializedName("ContentsUrl")
        public String ContentsUrl;
        @SerializedName("ModDate")
        public String ModDate;
        private int Position;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("TACSEQ")
        public int TACSEQ;
        @SerializedName("TSEQ")
        public int TSEQ;
        @SerializedName("ThumnailUrl")
        public String ThumnailUrl;
        @SerializedName("ThumnailUrlResult")
        public String ThumnailUrlResult;
        @SerializedName("WebThumnailUrl")
        public String WebThumnailUrl;
        @SerializedName("WorkLinkThUrl")
        public String WorkLinkThUrl;
        private boolean isMore;

        public int getPosition() {
            return this.Position;
        }

        public void setPosition(int i) {
            this.Position = i;
        }

        public boolean isMore() {
            return this.isMore;
        }

        public void setMore(boolean z) {
            this.isMore = z;
        }

        public List<ContentsUrl> getContentList() {
            return this.ContentList;
        }

        public void setContentList(List<ContentsUrl> list) {
            this.ContentList = list;
        }
    }
}
