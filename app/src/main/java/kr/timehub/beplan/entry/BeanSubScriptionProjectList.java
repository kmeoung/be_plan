package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSubScriptionProjectList {
    @SerializedName("CategoryList")
    public List<CategoryList> CategoryList;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;

    /* loaded from: classes.dex */
    public static class CategoryList {
        @SerializedName("SMCSEQ")
        public int SMCSEQ;
        @SerializedName("ShopMainCategoryTitle")
        public String ShopMainCategoryTitle;
        @SerializedName("projectList")
        public List<ProjectList> projectList;
    }

    /* loaded from: classes.dex */
    public static class ProjectList {
        @SerializedName("SPSEQ")
        public int SPSEQ;
        @SerializedName("ShopProjectTitle")
        public String ShopProjectTitle;
    }
}
