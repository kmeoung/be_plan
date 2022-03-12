package kr.timehub.beplan.http;

import com.naver.temy123.baseproject.base.Utils.HW_Params;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class HttpBindTempData extends HW_Params {
    public static String ACCESSKEY = "AAAAAAABBBBBBCVCCCCCCCC";
    public static String CommLoginEmail = "syb2530@timehub.kr";
    public static String CommProfileImgUrl = "https://tv.pstatic.net/thm?size=120x150&quality=9&q=http://sstatic.naver.net/people/37/201604181854386741.jpg";
    public static String CommUserName = "송영범";
    public static String Commid = "3";
    public static String PostFileAddLinkUrl = null;
    public static String PostFileCommentImgUrl = null;
    public static String PostFileImageUrl = null;
    public static String PostFileLinkUrl = null;
    public static String ProfileImageUrl = null;
    public static String RunnerID = "3";

    static {
        if (!"release".contains("debug")) {
            PostFileImageUrl = "http://file.timehub.kr/Beplan/image/";
            PostFileLinkUrl = "";
            ProfileImageUrl = "http://file.timehub.kr/Common/ProfileIMG/";
            PostFileCommentImgUrl = "http://file.timehub.kr/Beplan/image/Comment/";
            PostFileAddLinkUrl = "http://file.timehub.kr/Beplan/thumnail/";
        } else if (Comm_Params.IS_REAL_SERVER_TEST.booleanValue()) {
            PostFileImageUrl = "http://file.timehub.kr/Beplan/image/";
            PostFileLinkUrl = "";
            ProfileImageUrl = "http://file.timehub.kr/Common/ProfileIMG/";
            PostFileCommentImgUrl = "http://file.timehub.kr/Beplan/image/Comment/";
            PostFileAddLinkUrl = "http://file.timehub.kr/Beplan/thumnail/";
        } else {
            PostFileImageUrl = "http://devfile.timehub.kr/Beplan/image/";
            PostFileLinkUrl = "";
            ProfileImageUrl = "http://devfile.timehub.kr/Common/ProfileIMG/";
            PostFileCommentImgUrl = "http://devfile.timehub.kr/Beplan/image/Comment/";
            PostFileAddLinkUrl = "http://devfile.timehub.kr/Beplan/thumnail/";
        }
    }
}
