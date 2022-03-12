package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanAddTeam {
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("User_Auth")
    public List<User_Auth> User_Auth;
    @SerializedName("memberList")
    public List<memberList> memberList;

    /* loaded from: classes.dex */
    public static class User_Auth {
        @SerializedName("Key")
        public String Key;
        @SerializedName("Value")
        public String Value;
    }

    /* loaded from: classes.dex */
    public static class memberList {
        @SerializedName("Id")
        public int Id;
        @SerializedName("InviteState")
        public String InviteState;
        private int PMSEQ;
        @SerializedName("profileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("realName")
        public String RealName;
        @SerializedName("TMSEQ")
        public int TMSEQ;
        @SerializedName("TeamSEQ")
        public int TeamSEQ;
        private String auth_key;
        private String auth_value;
        @SerializedName("email")
        public String email;
        private String oldAuthKey;

        public int getPMSEQ() {
            return this.PMSEQ;
        }

        public void setPMSEQ(int i) {
            this.PMSEQ = i;
        }

        public String getAuth_key() {
            return this.auth_key;
        }

        public void setAuth_key(String str) {
            this.auth_key = str;
        }

        public String getAuth_value() {
            return this.auth_value;
        }

        public void setAuth_value(String str) {
            this.auth_value = str;
        }

        public String getOldAuthKey() {
            return this.oldAuthKey;
        }

        public void setOldAuthKey(String str) {
            this.oldAuthKey = str;
        }
    }
}
