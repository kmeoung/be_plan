package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanMyFormPopTaskDetail {
    @SerializedName("CateGoryTitle")
    public String CateGoryTitle;
    @SerializedName("ContentsList")
    public List<ContentsList> ContentsList;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TaskTitle")
    public String TaskTitle;

    /* loaded from: classes.dex */
    public static class ContentsList {
        @SerializedName("ContentsPart")
        public String ContentsPart;
        @SerializedName("ContentsUrl")
        public String ContentsUrl;
        @SerializedName("TACSEQ")
        public int TACSEQ;
        @SerializedName("ThumnailUrl")
        public String ThumnailUrl;
    }
}
