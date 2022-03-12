package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanAddTeamMemberEmail {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("memberList")
    public List<MemberList> memberList;

    /* loaded from: classes.dex */
    public static class MemberList {
        @SerializedName("Id")
        public int Id;
        @SerializedName("email")
        public String email;
        @SerializedName("profileImgUrl")
        public String profileImgUrl;
        @SerializedName("realName")
        public String realName;
    }
}
