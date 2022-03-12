package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanTeamDetail {
    @SerializedName("MemberPreviewList")
    public List<MemberPreviewList> MemberPreviewList;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TeamName")
    public String TeamName;

    /* loaded from: classes.dex */
    public static class MemberPreviewList {
        @SerializedName("Email")
        public String Email;
        @SerializedName("Id")
        public int Id;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
        @SerializedName("amI")
        public boolean amI;
    }
}
