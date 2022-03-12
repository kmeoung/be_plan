package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanTeamsPreview {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TeamPreviewList")
    public List<TeamPreviewList> TeamPreviewList;

    /* loaded from: classes.dex */
    public static class TeamPreviewList {
        @SerializedName("TeamCount")
        public int TeamCount;
        @SerializedName("TeamName")
        public String TeamName;
        @SerializedName("TeamSEQ")
        public int TeamSEQ;
        @SerializedName("Top4Member")
        public List<String> Top4Member;
        @SerializedName("Top4MemberProfile")
        public List<String> Top4MemberProfile;
    }
}
