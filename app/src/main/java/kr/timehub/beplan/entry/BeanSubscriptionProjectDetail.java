package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanSubscriptionProjectDetail {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("ShopVersionNO")
    public String ShopVersionNO;
    @SerializedName("shopCategoryList")
    public List<ShopCategoryList> shopCategoryList;
    @SerializedName("shopVersionList")
    public List<ShopVersionList> shopVersionList;

    /* loaded from: classes.dex */
    public static class ShopCategoryList {
        @SerializedName("SCGSEQ")
        public int SCGSEQ;
        @SerializedName("ShopCategoryTitle")
        public String ShopCategoryTitle;
        @SerializedName("taskList")
        public List<TaskList> taskList;
    }

    /* loaded from: classes.dex */
    public static class ShopVersionList {
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
