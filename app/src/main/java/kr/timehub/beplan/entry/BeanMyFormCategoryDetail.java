package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanMyFormCategoryDetail {
    @SerializedName("CategoryList")
    public List<CategoryList> CategoryList;
    @SerializedName("MTCSEQ")
    public int MTCSEQ;
    @SerializedName("MTCategoryTitle")
    public String MTCategoryTitle;
    @SerializedName("MTPSEQ")
    public int MTPSEQ;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;

    /* loaded from: classes.dex */
    public static class CategoryList {
        @SerializedName("CategoryTitle")
        public String CategoryTitle;
        @SerializedName("MTCGSEQ")
        public int MTCGSEQ;
        @SerializedName("MTCSEQ")
        public int MTCSEQ;
        @SerializedName("TaskList")
        public List<TaskList> TaskList;
    }

    /* loaded from: classes.dex */
    public static class TaskList {
        @SerializedName("MTTSEQ")
        public int MTTSEQ;
        @SerializedName("TaskTitle")
        public String TaskTitle;
    }
}
