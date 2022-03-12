package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanPopDetail {
    @SerializedName("CGSEQ")
    public int CGSEQ;
    @SerializedName("CateGoryTitle")
    public String CateGoryTitle;
    @SerializedName("Comment")
    public String Comment;
    @SerializedName("ContentsUrl")
    public List<String> ContentsUrl;
    @SerializedName("DenyComment")
    public String DenyComment;
    @SerializedName("EDate")
    public String EDate;
    @SerializedName("PSEQ")
    public int PSEQ;
    @SerializedName("ProfileImgUrl")
    public String ProfileImgUrl;
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
    @SerializedName("TCSEQ")
    public int TCSEQ;
    @SerializedName("TSEQ")
    public int TSEQ;
    @SerializedName("TaskState")
    public String TaskState;
    @SerializedName("TaskTitle")
    public String TaskTitle;
    @SerializedName("TaskType")
    public String TaskType;
    @SerializedName("ThumnailUrl")
    public List<String> ThumnailUrl;
    @SerializedName("UserList")
    public List<UserList> UserList;

    /* loaded from: classes.dex */
    public static class Auth {
        @SerializedName("Key")
        public String Key;
        @SerializedName("Value")
        public String Value;
    }

    /* loaded from: classes.dex */
    public static class UserList {
        @SerializedName("Auth")
        public List<Auth> Auth;
        @SerializedName("Email")
        public String Email;
        @SerializedName("Id")
        public int Id;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
    }
}
