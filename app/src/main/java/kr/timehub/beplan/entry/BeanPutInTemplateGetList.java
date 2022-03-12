package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanPutInTemplateGetList {
    @SerializedName("PSEQ")
    public int PSEQ;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TemplateCateGorys")
    public List<TemplateCateGorys> TemplateCateGorys;
    @SerializedName("TemplateMainCateGoryTitle")
    public List<TemplateMainCateGoryTitle> TemplateMainCateGoryTitle;

    /* loaded from: classes.dex */
    public static class TaskList {
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("TSEQ")
        public int TSEQ;
        @SerializedName("TaskTitle")
        public String TaskTitle;
    }

    /* loaded from: classes.dex */
    public static class TemplateCateGorys {
        @SerializedName("CGSEQ")
        public int CGSEQ;
        @SerializedName("CateGoryTitle")
        public String CateGoryTitle;
        @SerializedName("DeleteYN")
        public String DeleteYN;
        @SerializedName("MakeID")
        public int MakeID;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("TaskList")
        public List<TaskList> TaskList;
    }

    /* loaded from: classes.dex */
    public static class TemplateMainCateGoryTitle {
        @SerializedName("DeleteYN")
        public String DeleteYN;
        @SerializedName("MTCSEQ")
        public int MTCSEQ;
        @SerializedName("MainCategoryTitle")
        public String MainCategoryTitle;
    }
}
