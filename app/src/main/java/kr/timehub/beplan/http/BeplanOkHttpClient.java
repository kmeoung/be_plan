package kr.timehub.beplan.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import com.naver.temy123.baseproject.base.Http.HWOkHttpNameValuePair;
import com.naver.temy123.baseproject.base.Http.HWOkHttpParams;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.utils.Comm_Params;
import kr.timehub.beplan.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes.dex */
public class BeplanOkHttpClient {
    public void accountReg(Context context, int i, String str, String str2, String str3, String str4, String str5, String str6, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_SSO_ACCOUNT_REGISTER, new BeplanOkHttpParams(2).add("email", str).add("RealName", str2).add("Password", str3).add("ConfirmPassword", str4).add("ServicePart", str5).add("DevicePart", str6), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void accountLogin(Context context, int i, String str, String str2, String str3, String str4, String str5, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_SSO_ACCOUNT_LOGIN, new BeplanOkHttpParams().add("email", str).add("Password", str2).add("ServicePart", str3).add("DevicePart", str4).add("DeviceId", str5), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void accountLogin(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(0, i, str, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void findPassword(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_SSO_ACCOUNT_FORGOTPASSWORD, new BeplanOkHttpParams().add("email", str).add("ServicePart", Comm_Params.TAG).add("DevicePart", Comm_Params.DEVICE_PART), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void popMyInfo(Context context, int i, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYINFO_POP_MYINFO, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ChangeInfo(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYINFO_CHANGE_NAME, new BeplanOkHttpParams().add("ChangeName", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ChangeProfile(Context context, int i, File file, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYINFO_CHANGE_PROFILE, new BeplanOkHttpParams().add("fileattach", file), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void TeamPreview(Context context, int i, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TEAMS_TEAMPREVIEW, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void TeamDetailPopup(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TEAMS_TEAMDETAILPOPUP, new BeplanOkHttpParams().add("TeamSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void checkNotification(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_NOTIFICATION_READ_NOTIFICATION.replace(Comm_Params.URL_PARAM_NSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void Notification(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_NOTIFICATION_RECEIVE_NOTIFICATION, new BeplanOkHttpParams().add("Id", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MainIndex(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanOkHttpParams beplanOkHttpParams = new BeplanOkHttpParams();
        beplanOkHttpParams.add("Keyword", str);
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MAIN_INDEX, beplanOkHttpParams, new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MainNewIndex(Context context, int i, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MAIN_NEWINDEX, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void InitProject(Context context, int i, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_INIT_PROJECT, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void CreateProject(Context context, int i, String str, JSONArray jSONArray, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("ProejctTitle", str);
            beplanJSONObject.put("Project_Member", jSONArray);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_CREATE_PROJECT, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    @Nullable
    public void AddProjectMember(Context context, int i, @Nullable String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_ADDPROJECTMEMBER, new BeplanOkHttpParams().add("PSEQ", str).add("keyword", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    @Nullable
    public void ProjectAccept(Context context, int i, @Nullable String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_UPTINVITESTATEACCEPT, new BeplanOkHttpParams().add("PSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    @Nullable
    public void ProjectRefuse(Context context, int i, @Nullable String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_UPTINVITESTATEREFUSE, new BeplanOkHttpParams().add("PSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ChangeProectInfo(Context context, int i, @Nullable String str, String str2, JSONArray jSONArray, List<Integer> list, @Nullable OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("PSEQ", str);
            beplanJSONObject.put("ChangeName", str2);
            beplanJSONObject.put("MemberList", jSONArray);
            beplanJSONObject.put("BeDelMemberList", list);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_CHANGEPROJECTINFO, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void AddTeamMember(Context context, int i, String str, String str2, String str3, String str4, String str5, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_ADD_TEAMMEMBER, new BeplanOkHttpParams(true).add("TeamSEQ", str).add("TMSEQ", str2).add("PSEQ", str3).add("Email", str4).add("SelectTeamSEQ", str5), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void AddTeamMemberEmail(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_ADDTEAMMEMBEREMAIL, new BeplanOkHttpParams().add("email", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MakeTeam(Context context, int i, String str, List<Integer> list, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("TeamName", str);
            beplanJSONObject.put("TeamMemberList", list);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TEAMS_MAKETEAM, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void TeamChange(Context context, int i, String str, String str2, List<Integer> list, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("TeamSEQ", str);
            beplanJSONObject.put("TeamName", str2);
            beplanJSONObject.put("MembersID", list);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TEAMS_TEAMCHANGE, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ProjectList(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_PROJECTLIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("keyword", str2).add("TabState", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ProjectListForm(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        String str2 = Comm_Params.URL_API_PROJECT_PROJECTLIST_FORM;
        if (!TextUtils.isEmpty(str)) {
            str2 = (str2 + Comm_Params.URL_PSEQ + Comm_Params.URL_PARAM_PSEQ).replace(Comm_Params.URL_PARAM_PSEQ, str);
        }
        HWOkHttpClient.getInstance(context).request(1, i, str2, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyProjectList(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_MYPROJECTLIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("keyword", str2).add("TabState", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyTaskReceiveList(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_MYYASKRECEIVELIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("State", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void NewMyTaskReceiveList(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_NEWMYTASKRECEIVELIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("State", str2).add("keyword", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyTaskSendList(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_MYTASKSENDLIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("State", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void NewMyTaskSendList(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_PROJECT_NEWMYTASKSENDLIST, new BeplanOkHttpParams(true).add("PSEQ", str).add("State", str2).add("keyword", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void TaskDetail(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_DETAIL, new BeplanOkHttpParams().add("TSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SendComment(@Nullable Context context, @Nullable int i, String str, @Nullable String str2, @Nullable String str3, List<String> list, List<String> list2, List<String> list3, List<Integer> list4, List<String> list5, @Nullable OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("TCSEQ", str);
            beplanJSONObject.put("TSEQ", str2);
            beplanJSONObject.put("Comment", str3);
            beplanJSONObject.put("TasksContents", list);
            beplanJSONObject.put("Thumnail", list2);
            beplanJSONObject.put("ContentsPart", list3);
            beplanJSONObject.put("DeleteFileList", list4);
            beplanJSONObject.put("TaskAddContent", list5);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_SENDCOMMENT, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MakeCategory(Context context, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, List<String> list, List<String> list2, List<String> list3, String str9, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("PSEQ", str);
            beplanJSONObject.put("CGSEQ", str2);
            beplanJSONObject.put("CateGoryTitle", str3);
            beplanJSONObject.put("TaskTitle", str4);
            beplanJSONObject.put("RunnerId", str5);
            beplanJSONObject.put("SDate", str6);
            beplanJSONObject.put("EDate", str7);
            beplanJSONObject.put("Comment", str8);
            beplanJSONObject.put("ContentsUrl", list);
            beplanJSONObject.put("TasksContents", list2);
            beplanJSONObject.put("ContentsPart", list3);
            beplanJSONObject.put("PreviousView", str9);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_MAKECATEGORY, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MakeCategoryNew(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("PSEQ", str);
            beplanJSONObject.put("CGSEQ", str2);
            beplanJSONObject.put("CategoryName", str3);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_NEW_MAKECATEGORY, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MakeTask(Context context, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, List<String> list, List<String> list2, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("PSEQ", str);
            beplanJSONObject.put("CGSEQ", str2);
            beplanJSONObject.put("TSEQ", str3);
            beplanJSONObject.put("TCSEQ", str4);
            beplanJSONObject.put("Comment", str5);
            beplanJSONObject.put("DateType", str6);
            beplanJSONObject.put("RunnerID", str7);
            beplanJSONObject.put("TaskTitle", str8);
            beplanJSONObject.put("SDate", str9);
            beplanJSONObject.put("EDate", str10);
            beplanJSONObject.put("TasksContents", list);
            beplanJSONObject.put("ContentsPart", list2);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_MAKETASK, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void UptTaskStateRefuse(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_UPTTASKSTATEREFUSE, new BeplanOkHttpParams().add("TSEQ", str).add("DenyComment", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void UptTaskStateAccept(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_UPTTASKSTATEACCEPT, new BeplanOkHttpParams().add("TSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void UptTaskStateCancel(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_UPTTASKSTATECANCEL, new BeplanOkHttpParams().add("TSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    @Nullable
    public void UptTaskStateSuccess(Context context, int i, @Nullable String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_UPTTASKSTATESUCCESS, new BeplanOkHttpParams().add("TSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    @Deprecated
    public void Pop_Detail(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_POP_DETAIL, new BeplanOkHttpParams().add("MemberId", str).add("TSEQ", str2).add("TaskType", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PopReceiveDetail(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanOkHttpParams beplanOkHttpParams = new BeplanOkHttpParams();
        beplanOkHttpParams.add("MemberId", str).add("TSEQ", str2).add("TaskType", str3);
        BeplanOkHttpParams beplanOkHttpParams2 = new BeplanOkHttpParams();
        Iterator<HWOkHttpNameValuePair> it = beplanOkHttpParams.iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value != null) {
                beplanOkHttpParams2.add(key, value);
            }
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_POP_RECEIVEDETAIL, beplanOkHttpParams2, new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PopSendDetail(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanOkHttpParams beplanOkHttpParams = new BeplanOkHttpParams();
        beplanOkHttpParams.add("MemberId", str).add("TSEQ", str2).add("TaskType", str3);
        BeplanOkHttpParams beplanOkHttpParams2 = new BeplanOkHttpParams();
        Iterator<HWOkHttpNameValuePair> it = beplanOkHttpParams.iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value != null) {
                beplanOkHttpParams2.add(key, value);
            }
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_POP_SENDDETAIL, beplanOkHttpParams2, new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PostFile(Context context, int i, String str, File file, File file2, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_FILE_POSTFILE, new BeplanOkHttpParams().add("FilePart", str2).add("Link", str).add("ContentPart", str3).add("fileattach", file).add("Thumnail", file2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void DeleteContents(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_DELETECONTENTS, new BeplanOkHttpParams().add("TACSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void DeleteProject(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(3, i, Comm_Params.URL_API_PROJECT_DELETEPROJECT, new BeplanOkHttpParams().add("PSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void DeleteCategory(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(3, i, Comm_Params.URL_API_PROJECT_DELETECATEGORY, new BeplanOkHttpParams().add("CGSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void DeleteTask(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_DELETETASK.replace(Comm_Params.URL_PARAM_ID, str2), new BeplanOkHttpParams().add("TSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ModifyCategoryTitle(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_CATEGORY_MODIFYCATEGORYTITLE, new BeplanOkHttpParams().add("CGSEQ", str).add("CategoryTitle", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ModifyCategoryRunner(Context context, int i, JSONArray jSONArray, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("mList", jSONArray);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_CATEGORY_MODIFYCATEGORYRUNNER, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PutInTemplateCategory(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_CATEGORY_PUTINTEMPLATECATEGORY, new BeplanOkHttpParams().add("MTCSEQ", str).add("MTCateGoryTitle", str2).add("CGSEQ", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MakeTask(Context context, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, List<String> list, List<String> list2, List<String> list3, String str11, List<Integer> list4, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpParams hWOkHttpParams = new HWOkHttpParams(2);
        if (!TextUtils.isEmpty(str)) {
            hWOkHttpParams.add("PSEQ", str);
        }
        if (!TextUtils.isEmpty(str2)) {
            hWOkHttpParams.add("CGSEQ", str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            hWOkHttpParams.add("TSEQ", str3);
        } else {
            hWOkHttpParams.add("TSEQ", "0");
        }
        if (!TextUtils.isEmpty(str4)) {
            hWOkHttpParams.add("TCSEQ", str4);
        }
        if (!TextUtils.isEmpty(str5)) {
            hWOkHttpParams.add("Comment", str5);
        }
        if (!TextUtils.isEmpty(str6)) {
            hWOkHttpParams.add("DateType", str6);
        }
        if (!TextUtils.isEmpty(str7)) {
            hWOkHttpParams.add("RunnerID", str7);
        }
        if (!TextUtils.isEmpty(str8)) {
            hWOkHttpParams.add("TaskTitle", str8);
        }
        if (!TextUtils.isEmpty(str9)) {
            hWOkHttpParams.add("SDate", str9);
        }
        if (!TextUtils.isEmpty(str10)) {
            hWOkHttpParams.add("EDate", str10);
        }
        if (!TextUtils.isEmpty(str11)) {
            hWOkHttpParams.add("DeleteYN", str11);
        }
        for (String str12 : list) {
            hWOkHttpParams.add("TasksContents", str12);
        }
        for (Integer num : list4) {
            hWOkHttpParams.add("DeleteFileList", String.valueOf(num));
        }
        for (String str13 : list3) {
            hWOkHttpParams.add("ContentsPart", str13);
        }
        for (String str14 : list2) {
            hWOkHttpParams.add("Thumnail", str14);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_MAKE_TASK, hWOkHttpParams, new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SharedProjectDetail(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        String str2 = Comm_Params.URL_API_PROJECT_SHAREDPROJECTDETAIL;
        if (!TextUtils.isEmpty(str)) {
            str2 = (str2 + Comm_Params.URL_PSEQ + Comm_Params.URL_PARAM_PSEQ).replace(Comm_Params.URL_PARAM_PSEQ, str);
        }
        HWOkHttpClient.getInstance(context).request(1, i, str2, new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PutInTemplate(Context context, int i, JSONArray jSONArray, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("TemplateCateGorys", jSONArray);
            beplanJSONObject.put("PSEQ", str);
            beplanJSONObject.put("MainCateGoryTitle", str2);
            beplanJSONObject.put("CGSEQ", str3);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(4, i, Comm_Params.URL_API_PROJECT_PUTINTEMPLATE, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void PutInTemplateGetList(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(4, i, Comm_Params.URL_API_PROJECT_PUTINTEMPLATE_GET_LIST.replace(Comm_Params.URL_PARAM_PSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormIndex(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_INDEX.replace(Comm_Params.URL_PARAM_MTCSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormFormList(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_FORMLIST.replace(Comm_Params.URL_PARAM_KEYWORD, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormTaskDetail(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_TASKDETAIL.replace(Comm_Params.URL_PARAM_MTPSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormCategoryDetail(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_CATEGORYDETAIL.replace(Comm_Params.URL_PARAM_MTCSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormDeleteProject(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_DELETEPROJECT.replace(Comm_Params.URL_PARAM_MTPSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormDeleteCategory(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_DELETECATEGORY.replace(Comm_Params.URL_PARAM_MTCGSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormPop_TaskDetail(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_POP_TASKLIST.replace(Comm_Params.URL_PARAM_MTTSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormPopUsing(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_POP_USING.replace(Comm_Params.URL_PARAM_MTPSEQ, str).replace(Comm_Params.URL_PARAM_MTCGSEQ, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void MyFormUsing(Context context, int i, String str, String str2, List<Integer> list, List<String> list2, List<String> list3, String str3, String str4, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("ProjectTitle", str);
            beplanJSONObject.put("PSEQ", str2);
            beplanJSONObject.put("Member_PMSEQ", list);
            beplanJSONObject.put("Member_Email", list2);
            beplanJSONObject.put("Member_Auth", list3);
            beplanJSONObject.put("MTPSEQ", str3);
            beplanJSONObject.put("MTCGSEQ", str4);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYFORM_USING, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionProjectList(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_PROJECTLIST, new BeplanOkHttpParams().add("SMCSEQ", str).add("keyword", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionCategoryList(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_CATEGORYLIST, new BeplanOkHttpParams().add("keyword", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionProjectDetail(Context context, int i, String str, String str2, String str3, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_PROJECTDETAIL, new BeplanOkHttpParams().add("SPSEQ", str).add("SVSEQ", str2).add("keyword", str3), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionCategorDetail(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_CATEGORYDETAIL, new BeplanOkHttpParams().add("SMCSEQ", str).add("keyword", str2), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionSelectCategoryVersion(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_SELECTCATEGORYVERSION, new BeplanOkHttpParams().add("SVSEQ", str), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionUsingProject(Context context, int i, String str, JSONArray jSONArray, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("ProejctTitle", str);
            beplanJSONObject.put("Project_Member", jSONArray);
            beplanJSONObject.put("SPSEQ", str2);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_USINGPROJECT, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void SubscriptionUsingCategorys(Context context, int i, String str, String str2, String str3, JSONArray jSONArray, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("SCGSEQ", str);
            beplanJSONObject.put("PSEQ", str2);
            beplanJSONObject.put("ProejctTitle", str3);
            beplanJSONObject.put("Project_Member", jSONArray);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_MYSUBSCRIPTION_USINGCATEGORYS, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopProject(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_SHOPPROJECT.replace(Comm_Params.URL_PARAM_SMCSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopIndex(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_INDEX.replace(Comm_Params.URL_PARAM_KEYWORD, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopProjectDetail(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_PROJECTDETAIL.replace(Comm_Params.URL_PARAM_SPSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopCategoryDetail(Context context, int i, String str, String str2, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_CATEGORYDETAIL.replace(Comm_Params.URL_PARAM_SMCSEQ, str).replace(Comm_Params.URL_PARAM_KEYWORD, str2), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopBuyProject(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_BUYPROJECT.replace(Comm_Params.URL_PARAM_SPSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopBuyCategory(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_BUYCATEGORY.replace(Comm_Params.URL_PARAM_SCGSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void ShopPopTaskList(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_SHOP_POP_TASKLIST.replace(Comm_Params.URL_PARAM_STSEQ, str), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback) { // from class: kr.timehub.beplan.http.BeplanOkHttpClient.1
        });
    }

    public void multiPostFileComment(Context context, int i, ArrayList<File> arrayList, OnHwNetworkCallback onHwNetworkCallback) {
        String str;
        HWOkHttpParams hWOkHttpParams = new HWOkHttpParams(0);
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            File file = arrayList.get(i3);
            if (Utils.FileToBitmap(file) != null) {
                String substring = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                hWOkHttpParams.add("fileattach", file, "imageFile" + i2 + "." + substring);
                if ("release".contains("debug")) {
                    if (file.exists()) {
                        long length = file.length();
                        str = Long.toString(length) + " bytes";
                    } else {
                        str = "파일없음";
                    }
                    Log.d("BeplanOkHttpClient", "파일 명 : imageFile" + i2 + "." + substring);
                    StringBuilder sb = new StringBuilder();
                    sb.append("파일 욜량 : ");
                    sb.append(str);
                    Log.d("BeplanOkHttpClient", sb.toString());
                }
                i2++;
            }
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_FILE_MULTIPOSTFILE, hWOkHttpParams.add("ContentPart", "C").add("FilePart", "I"), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void multiPostFileTask(Context context, int i, ArrayList<File> arrayList, OnHwNetworkCallback onHwNetworkCallback) {
        String str;
        HWOkHttpParams hWOkHttpParams = new HWOkHttpParams(0);
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            File file = arrayList.get(i3);
            if (Utils.FileToBitmap(file) != null) {
                String substring = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                hWOkHttpParams.add("fileattach", file, "imageFile" + i2 + "." + substring);
                if ("release".contains("debug")) {
                    if (file.exists()) {
                        long length = file.length();
                        str = Long.toString(length) + " bytes";
                    } else {
                        str = "파일없음";
                    }
                    Log.d("BeplanOkHttpClient", "파일 명 : imageFile" + i2 + "." + substring);
                    StringBuilder sb = new StringBuilder();
                    sb.append("파일 욜량 : ");
                    sb.append(str);
                    Log.d("BeplanOkHttpClient", sb.toString());
                }
                i2++;
            }
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_FILE_MULTIPOSTFILE, hWOkHttpParams.add("ContentPart", "T").add("FilePart", "I"), new BeplanOkHttpParams(), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void deleteTaskComment(Context context, int i, int i2, OnHwNetworkCallback onHwNetworkCallback) {
        BeplanJSONObject beplanJSONObject = new BeplanJSONObject();
        try {
            beplanJSONObject.put("TCSEQ", i2);
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        HWOkHttpClient.getInstance(context).request(1, i, Comm_Params.URL_API_TASK_DELETETASKCOMMENT, new BeplanOkHttpParams(beplanJSONObject), new BeplanOkhttpCallback(context, onHwNetworkCallback));
    }

    public void notifyReceiveID(Context context, int i, String str, OnHwNetworkCallback onHwNetworkCallback) {
        HWOkHttpClient.getInstance(context).request(1, i, String.format(Comm_Params.URL_API_MAIN_NOTIFY_RECEIVEID, str), new BeplanOkHttpParams(), onHwNetworkCallback);
    }
}
