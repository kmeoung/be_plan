package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanMyFormFormList {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("SelectedMTCName")
    public String SelectedMTCName;
    @SerializedName("formList")
    public List<FormList> formList;

    /* loaded from: classes.dex */
    public static class FormList {
        @SerializedName("CategoryCnt")
        public int CategoryCnt;
        @SerializedName("MTCSEQ")
        public int MTCSEQ;
        @SerializedName("MTCateGoryTitle")
        public String MTCateGoryTitle;
        @SerializedName("MTProjectList")
        public List<MTProjectList> MTProjectList;
    }

    /* loaded from: classes.dex */
    public static class MTProjectList {
        @SerializedName("MTCSEQ")
        public int MTCSEQ;
        @SerializedName("MTPSEQ")
        public int MTPSEQ;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
    }
}
