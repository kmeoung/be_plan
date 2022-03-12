package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSubscriptionCategoryList {
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
        @SerializedName("SMCGSEQ")
        public int SMCGSEQ;
        @SerializedName("ShopMainCategoryTitle")
        public String ShopMainCategoryTitle;
        @SerializedName("cnt")
        public int cnt;
    }
}
