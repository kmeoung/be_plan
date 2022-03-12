package kr.timehub.beplan.entry.plugin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanInitProject {
    @SerializedName("Auth")
    public List<Auth> Auth;
    @SerializedName("email")
    public String Email;
    @SerializedName("My_Team")
    public List<My_Team> My_Team;
    @SerializedName("RealName")
    public String RealName;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;

    /* loaded from: classes.dex */
    public static class Auth {
        @SerializedName("Key")
        public String Key;
        @SerializedName("Value")
        public String Value;
    }

    /* loaded from: classes.dex */
    public static class My_Team {
        @SerializedName("Key")
        public int Key;
        @SerializedName("Value")
        public String Value;
    }
}
