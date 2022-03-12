package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class BeanAddTeamMember {
    @SerializedName("List")
    public java.util.List<List> List;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;

    /* loaded from: classes.dex */
    public static class List {
        @SerializedName("Auth")
        public String Auth;
        @SerializedName("Email")
        public String Email;
        @SerializedName("InviteState")
        public String InviteState;
        @SerializedName("MemberId")
        public int MemberId;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("PMSEQ")
        public int PMSEQ;
        @SerializedName("PSEQ")
        public int PSEQ;
        @SerializedName("ProfileImgUrl")
        public String ProfileImgUrl;
        @SerializedName("RealName")
        public String RealName;
        @SerializedName("RegDate")
        public String RegDate;
        private String auth_key;
        private String auth_value;
        private String oldAuthKey;

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
