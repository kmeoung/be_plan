package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanFormShopTaskDetail {
    @SerializedName("CateGoryTitle")
    public String CateGoryTitle;
    @SerializedName("ContentsList")
    public List<ContentsList> ContentsList;
    @SerializedName("MakerProfileImgUrl")
    public String MakerProfileImgUrl;
    @SerializedName("MakerRealName")
    public String MakerRealName;
    @SerializedName("ModDate")
    public String ModDate;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("RegDate")
    public String RegDate;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TSEQ")
    public int TSEQ;
    @SerializedName("TaskTitle")
    public String TaskTitle;

    /* loaded from: classes.dex */
    public static class ContentsList {
        @SerializedName("ContentsPart")
        public String ContentsPart;
        @SerializedName("ContentsUrl")
        public String ContentsUrl;
        @SerializedName("DeleteYN")
        public String DeleteYN;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("STACSEQ")
        public int STACSEQ;
        @SerializedName("TSEQ")
        public int TSEQ;
        @SerializedName("ThumnailUrl")
        public String ThumnailUrl;
    }
}
