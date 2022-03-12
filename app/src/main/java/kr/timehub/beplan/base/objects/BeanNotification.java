package kr.timehub.beplan.base.objects;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class BeanNotification {
    @SerializedName("NoCheckNotiCnt")
    public int NoCheckNotiCnt;
    @SerializedName("NotificationList")
    public List<NotificationList> NotificationList;
    @SerializedName("RtnKey")
    public String RtnKey;
    @SerializedName("RtnUrl")
    public String RtnUrl;
    @SerializedName("RtnValue")
    public String RtnValue;
    @SerializedName("TapList")
    public List<String> TapList;

    /* loaded from: classes.dex */
    public static class NotificationList {
        @SerializedName("IsCheck")
        public boolean IsCheck;
        @SerializedName("IsGoToHome")
        public String IsGoToHome;
        @SerializedName("IsRead")
        public boolean IsRead;
        @SerializedName("ModDate")
        public String ModDate;
        @SerializedName("NSEQ")
        public int NSEQ;
        @SerializedName("Part")
        public String Part;
        @SerializedName("PartSEQ")
        public int PartSEQ;
        @SerializedName("ProjectTitle")
        public String ProjectTitle;
        @SerializedName("RegDate")
        public String RegDate;
        @SerializedName("SenderId")
        public int SenderId;
        @SerializedName("SenderName")
        public String SenderName;
        @SerializedName("SenderProfile")
        public String SenderProfile;
        @SerializedName("SubTitle")
        public String SubTitle;
        @SerializedName("Title")
        public String Title;
    }
}
