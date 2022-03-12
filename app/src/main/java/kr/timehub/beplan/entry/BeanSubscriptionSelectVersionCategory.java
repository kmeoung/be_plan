package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSubscriptionSelectVersionCategory {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("category")
    public Category category;

    /* loaded from: classes.dex */
    public static class Category {
        @SerializedName("SCGSEQ")
        public int SCGSEQ;
        @SerializedName("ShopCategoryTitle")
        public String ShopCategoryTitle;
        @SerializedName("shopversionList")
        public List<ShopversionList> shopversionList;
        @SerializedName("taskList")
        public List<TaskList> taskList;
    }

    /* loaded from: classes.dex */
    public static class ShopversionList {
        @SerializedName("SVSEQ")
        public int SVSEQ;
        @SerializedName("VersionTitle")
        public String VersionTitle;
    }

    /* loaded from: classes.dex */
    public static class TaskList {
        @SerializedName("STSEQ")
        public int STSEQ;
        @SerializedName("TaskTitle")
        public String TaskTitle;
    }
}
