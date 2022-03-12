package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSendDetail {
    @SerializedName("ContentsUrl")
    public List<ContentsUrl> ContentsUrl;
    @SerializedName("EDate")
    public String EDate;
    @SerializedName("IsRequest")
    public boolean IsRequest;
    @SerializedName("ProfileURL")
    public String ProfileURL;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("SDate")
    public String SDate;
    @SerializedName("TaskComment")
    public String TaskComment;
    @SerializedName("TaskState")
    public String TaskState;
    @SerializedName("projectTitle")
    public String projectTitle;
    @SerializedName("realName")
    public String realName;
    @SerializedName("taskTitle")
    public String taskTitle;

    /* loaded from: classes.dex */
    public static class ContentsUrl {
        @SerializedName("ContentsPart")
        public String ContentsPart;
        @SerializedName("ContentsUrl")
        public String ContentsUrl;
        @SerializedName("ModDate")
        public String ModDate;
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
    }
}
