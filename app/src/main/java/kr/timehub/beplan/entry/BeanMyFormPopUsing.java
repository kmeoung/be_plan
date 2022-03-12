package kr.timehub.beplan.entry;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanMyFormPopUsing {
    @SerializedName("BSVSEQ")
    public int BSVSEQ;
    @SerializedName("DicUserAuth")
    public List<DicUserAuth> DicUserAuth;
    @SerializedName("MTCGSEQ")
    public int MTCGSEQ;
    @SerializedName("MTPSEQ")
    public int MTPSEQ;
    @SerializedName("MyEmail")
    public String MyEmail;
    @SerializedName("MyName")
    public String MyName;
    @SerializedName("MyTeamList")
    public List<MyTeamList> MyTeamList;
    @SerializedName("PList")
    public List<PList> PList;
    @SerializedName("ProfileImgUrl")
    public String ProfileImgUrl;
    @SerializedName("ProjectTitle")
    public String ProjectTitle;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("SCGSEQ")
    public int SCGSEQ;
    @SerializedName("SVSEQ")
    public int SVSEQ;
    @SerializedName("itemCategory")
    public int itemCategory;

    /* loaded from: classes.dex */
    public static class DicUserAuth {
        @SerializedName("Key")
        public String Key;
        @SerializedName("Value")
        public String Value;
    }

    /* loaded from: classes.dex */
    public static class MyTeamList {
        @SerializedName("AccessToken")
        public String AccessToken;
        @SerializedName("CommId")
        public int CommId;
        @SerializedName("CommLoginEmail")
        public String CommLoginEmail;
        @SerializedName("CommProfileImgUrl")
        public String CommProfileImgUrl;
        @SerializedName("CommUserName")
        public String CommUserName;
        @SerializedName("TeamMemberCnt")
        public int TeamMemberCnt;
        @SerializedName("TeamName")
        public String TeamName;
        @SerializedName("TeamSEQ")
        public int TeamSEQ;
        @SerializedName("Team_Member")
        public List<Team_Member> Team_Member;
    }

    /* loaded from: classes.dex */
    public static class PList {
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
    }

    /* loaded from: classes.dex */
    public static class Team_Member {
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
        @SerializedName("TMSEQ")
        public int TMSEQ;
        @SerializedName("TeamSEQ")
        public int TeamSEQ;
    }
}
