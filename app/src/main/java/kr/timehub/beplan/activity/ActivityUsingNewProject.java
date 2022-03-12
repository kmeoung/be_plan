package kr.timehub.beplan.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterMemberEmail;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterPermission;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.BaseSpinner;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanAddTeam;
import kr.timehub.beplan.entry.plugin.BeanAddTeamMemberEmail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanInitProject;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ActivityUsingNewProject extends BaseActivity implements BaseRecyclerViewAdapterInterface, BaseMainToolBar.OnToolbarClickListener {
    public static final String EXTRA_CGSEQ = "EXTRA_CGSEQ";
    public static final String EXTRA_CGTITLE = "EXTRA_CGTITLE";
    public static final String EXTRA_PSEQ = "EXTRA_PSEQ";
    public static final String EXTRA_PTITLE = "EXTRA_PTITLE";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    private static final int REQ_MY_FORM_USING = 584;
    private static final int REQ_MY_SUBSCRIPTION_USING_CATEGORY = 333;
    private static final int REQ_MY_SUBSCRIPTION_USING_PROJECT = 855;
    public static final String TYPE_MY_FORM = "TYPE_MY_FORM";
    public static final String TYPE_SUBSCRIPTION = "TYPE_SUBSCRIPTION";
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.base_toolbar)
    BaseMainToolBar mBaseToolbar;
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btn_more)
    ImageButton mBtnMore;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    private String mCGTitle;
    private int mCGseq;
    private String mChildType;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_project)
    android.widget.EditText mEtProject;
    private ArrayList<BeanAddTeam.memberList> mList;
    @BindView(R.id.ll_select_team)
    LinearLayout mLlSelectTeam;
    private String mPTitle;
    private List<BeanInitProject.Auth> mPermissionList;
    private int mPseq;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.sp_team_member)
    BaseSpinner mSpTeamMember;
    private List<BeanInitProject.My_Team> mTeamList;
    @BindView(R.id.tv_project_member)
    TextView mTvProjectMember;
    @BindView(R.id.tv_select_team)
    TextView mTvSelectTeam;
    private String mType;
    @BindView(R.id.v_status)
    View mVStatus;
    private int TYPE_LISTITEM = 1;
    private final int REQ_NEW_PROJECT = 1;
    private final int REQ_PROJECT_FORM = 2;
    private final int REQ_ADD_TEAM_MEMBER = 3;
    private final int REQ_ADD_TEAM_MEMBER_EMAIL = 4;
    private final String TYPE_CATEGORY = "TYPE_CATEGORY";
    private final String TYPE_PROJECT = "TYPE_PROJECT";

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_project);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVStatus);
        initAdapter();
        onAction();
    }

    public void showTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_SHOW_TIMER);
            startService(intent);
        }
    }

    public void hideTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_HIDE_TIMER);
            startService(intent);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        showTimer();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideTimer();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(this);
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(this));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void onAction() {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
            BeanAddTeam.memberList memberlist = new BeanAddTeam.memberList();
            Comm_Prefs instance = Comm_Prefs.getInstance(this);
            memberlist.email = instance.getUserEmail();
            memberlist.RealName = instance.getUserName();
            memberlist.ProfileImgUrl = instance.getUserUrl();
            memberlist.setAuth_key("B");
            memberlist.setAuth_value(getString(R.string.str_permission_admin));
            this.mList.add(memberlist);
            this.mAdapter.notifyDataSetChanged();
        }
        this.mBaseToolbar.setOnToolbarClickListener(this);
        this.mBaseToolbar.setToolbarVisibleState(false, true, false, false);
        this.mBaseToolbar.setTitle("새 프로젝트 만들기");
        httpPostInitProject();
        this.mEtEmail.setOnKeyListener(new View.OnKeyListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 66) {
                    return false;
                }
                ActivityUsingNewProject.this.httpPostAddTeamMemberEmail(ActivityUsingNewProject.this.mEtEmail.getText().toString());
                Utils.hideKeyboard(ActivityUsingNewProject.this, ActivityUsingNewProject.this.mEtEmail);
                return true;
            }
        });
        this.mEtProject.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    ActivityUsingNewProject.this.mBtnSave.setBackground(ContextCompat.getDrawable(ActivityUsingNewProject.this, R.drawable.btn_grdt_02));
                } else {
                    ActivityUsingNewProject.this.mBtnSave.setBackground(ContextCompat.getDrawable(ActivityUsingNewProject.this, R.drawable.btn_gray_02));
                }
            }
        });
        if (getIntent() != null) {
            Intent intent = getIntent();
            this.mPseq = intent.getIntExtra(EXTRA_PSEQ, -1);
            this.mCGseq = intent.getIntExtra(EXTRA_CGSEQ, -1);
            this.mPTitle = intent.getStringExtra(EXTRA_PTITLE);
            this.mCGTitle = intent.getStringExtra(EXTRA_CGTITLE);
            if (this.mPseq == -1) {
                this.mChildType = "TYPE_CATEGORY";
            } else if (this.mCGseq == -1) {
                this.mChildType = "TYPE_PROJECT";
            } else {
                Toast.makeText(this, getString(R.string.error_server_not_success), 0).show();
                finish();
            }
            if (intent.getStringExtra(EXTRA_TYPE) != null) {
                this.mType = intent.getStringExtra(EXTRA_TYPE);
                return;
            }
            return;
        }
        Toast.makeText(this, getString(R.string.error_server_not_success), 0).show();
        finish();
    }

    private void CustomDropDownSet(BeanAddTeamMemberEmail beanAddTeamMemberEmail) {
        if (beanAddTeamMemberEmail.memberList != null && beanAddTeamMemberEmail.memberList.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (BeanAddTeamMemberEmail.MemberList memberList : beanAddTeamMemberEmail.memberList) {
                BeanAddProjectMember.List list = new BeanAddProjectMember.List();
                list.MemberId = memberList.Id;
                list.ProfileImgUrl = memberList.profileImgUrl;
                list.RealName = memberList.realName;
                list.Email = memberList.email;
                arrayList.add(list);
            }
            this.mSpTeamMember.performClick();
            this.mSpTeamMember.setAdapter((SpinnerAdapter) new DropdownAdapterMemberEmail(this, arrayList));
            Utils.setDropDownHeight(this.mSpTeamMember, HttpStatus.SC_INTERNAL_SERVER_ERROR);
            this.mSpTeamMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.3
                private boolean isAvailableClick = false;

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (this.isAvailableClick) {
                        BeanAddProjectMember.List list2 = (BeanAddProjectMember.List) adapterView.getItemAtPosition(i);
                        BeanAddTeamMemberEmail.MemberList memberList2 = new BeanAddTeamMemberEmail.MemberList();
                        memberList2.email = list2.Email;
                        memberList2.Id = list2.MemberId;
                        memberList2.profileImgUrl = list2.ProfileImgUrl;
                        memberList2.realName = list2.RealName;
                        ActivityUsingNewProject.this.setAddTeamMemberEmail(memberList2);
                        return;
                    }
                    this.isAvailableClick = true;
                }
            });
        }
    }

    public void setDropdownSetPermission(Spinner spinner, final int i) {
        if (this.mPermissionList != null) {
            for (BeanInitProject.Auth auth : this.mPermissionList) {
                if (TextUtils.equals(auth.Key.toLowerCase(), "c")) {
                    this.mPermissionList.remove(auth);
                }
            }
        }
        final List arrayList = this.mPermissionList != null ? this.mPermissionList : new ArrayList();
        spinner.setAdapter((SpinnerAdapter) new DropdownAdapterPermission(this, arrayList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                BeanInitProject.Auth auth2 = (BeanInitProject.Auth) arrayList.get(i2);
                ((BeanAddTeam.memberList) ActivityUsingNewProject.this.mList.get(i)).setAuth_key(auth2.Key);
                ((BeanAddTeam.memberList) ActivityUsingNewProject.this.mList.get(i)).setAuth_value(auth2.Value);
            }
        });
    }

    public void setDropdownTeam() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        for (BeanInitProject.My_Team my_Team : this.mTeamList) {
            arrayList.add(my_Team.Value);
        }
        this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(this, (int) R.layout.listitem_add_team_member, arrayList));
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i > 0) {
                    ActivityUsingNewProject.this.httpPostAddTeamMember(String.valueOf(((BeanInitProject.My_Team) ActivityUsingNewProject.this.mTeamList.get(i - 1)).Key));
                    ActivityUsingNewProject.this.mSpDropdown.setSelection(0);
                }
            }
        });
    }

    private void httpPostInitProject() {
        new BeplanOkHttpClient().InitProject(this, 2, this);
    }

    private void httpPostCreateProject() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        new ArrayList();
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.mList.size(); i++) {
            try {
                BeanAddTeam.memberList memberlist = this.mList.get(i);
                if (i == 0) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("UserId", Integer.parseInt(Comm_Prefs.getInstance(this).getUserId()));
                    jSONObject.put("UserAuth", "B");
                    jSONArray.put(jSONObject);
                } else {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("UserId", memberlist.Id);
                    jSONObject2.put("UserAuth", memberlist.getAuth_key());
                    jSONArray.put(jSONObject2);
                }
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        String obj = this.mEtProject.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(this, getString(R.string.alert_plz_input_all_info), 0).show();
        } else {
            beplanOkHttpClient.CreateProject(this, 1, obj, jSONArray, this);
        }
    }

    private void httpUsingProject() {
        String obj = this.mEtProject.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(this, getString(R.string.alert_plz_input_all_info), 0).show();
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.mList.size(); i++) {
            try {
                BeanAddTeam.memberList memberlist = this.mList.get(i);
                if (i == 0) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("UserId", Integer.parseInt(Comm_Prefs.getInstance(this).getUserId()));
                    jSONObject.put("UserAuth", "B");
                    jSONArray.put(jSONObject);
                } else {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("UserId", memberlist.Id);
                    jSONObject2.put("UserAuth", memberlist.getAuth_key());
                    jSONArray.put(jSONObject2);
                }
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        new BeplanOkHttpClient().SubscriptionUsingProject(this, REQ_MY_SUBSCRIPTION_USING_PROJECT, obj, jSONArray, String.valueOf(this.mPseq), this);
    }

    private void httpUsingCategory() {
        String obj = this.mEtProject.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(this, getString(R.string.alert_plz_input_all_info), 0).show();
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.mList.size(); i++) {
            try {
                BeanAddTeam.memberList memberlist = this.mList.get(i);
                if (i == 0) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("UserId", Integer.parseInt(Comm_Prefs.getInstance(this).getUserId()));
                    jSONObject.put("UserAuth", "B");
                    jSONArray.put(jSONObject);
                } else {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("UserId", memberlist.Id);
                    jSONObject2.put("UserAuth", memberlist.getAuth_key());
                    jSONArray.put(jSONObject2);
                }
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        new BeplanOkHttpClient().SubscriptionUsingCategorys(this, REQ_MY_SUBSCRIPTION_USING_CATEGORY, String.valueOf(this.mCGseq), "0", obj, jSONArray, this);
    }

    private void httpPostMyFormUsing() {
        String str;
        String str2;
        String obj = this.mEtProject.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(this, getString(R.string.alert_plz_input_all_info), 0).show();
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.mList.size(); i++) {
            BeanAddTeam.memberList memberlist = this.mList.get(i);
            if (i == 0) {
                arrayList2.add(Comm_Prefs.getInstance(this).getUserEmail());
                arrayList.add("B");
            } else {
                arrayList2.add(memberlist.email);
                arrayList.add(memberlist.getAuth_key());
            }
        }
        if (TextUtils.equals(this.mChildType, "TYPE_PROJECT")) {
            str2 = String.valueOf(this.mPseq);
            str = null;
        } else if (TextUtils.equals(this.mChildType, "TYPE_CATEGORY")) {
            str = String.valueOf(this.mCGseq);
            str2 = null;
        } else {
            str2 = null;
            str = null;
        }
        new BeplanOkHttpClient().MyFormUsing(this, REQ_MY_FORM_USING, obj, null, null, arrayList2, arrayList, str2, str, this);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(this, R.layout.listitem_new_project, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        BeanAddTeam.memberList memberlist = this.mList.get(i);
        Spinner spinner = (Spinner) baseViewHolder.getView(R.id.sp_dropdown);
        if (i == 0) {
            baseViewHolder.getView(R.id.iv_me).setVisibility(0);
            baseViewHolder.getView(R.id.ll_right_button).setVisibility(8);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_permissions);
            textView.setText(getString(R.string.str_permission_admin));
            textView.setTextColor(getResources().getColor(R.color.hintColor));
            spinner.setVisibility(8);
        } else {
            spinner.setVisibility(0);
            setDropdownSetPermission(spinner, i);
        }
        this.mTvProjectMember.setText(String.format("프로젝트 참여인원 (%d명)", Integer.valueOf(this.mList.size())));
        baseViewHolder.getView(R.id.ll_right_button).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActivityUsingNewProject.this.mList.remove(i);
                ActivityUsingNewProject.this.mAdapter.notifyDataSetChanged();
            }
        });
        String str = memberlist.RealName;
        String str2 = memberlist.email;
        final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo);
        if (TextUtils.isEmpty(memberlist.ProfileImgUrl)) {
            Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
        } else {
            Glide.with((FragmentActivity) this).load(memberlist.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.activity.ActivityUsingNewProject.7
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with((FragmentActivity) ActivityUsingNewProject.this).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                    return false;
                }
            }).into(imageView);
        }
        baseViewHolder.setText(R.id.tv_name, str);
        baseViewHolder.setText(R.id.tv_email, str2);
        baseViewHolder.setText(R.id.tv_right_button, "제외");
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mList.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return this.TYPE_LISTITEM;
    }

    public void httpPostAddTeamMember(String str) {
        new BeplanOkHttpClient().AddTeamMember(this, 3, str, "", "", "", "", this);
    }

    public void httpPostAddTeamMemberEmail(String str) {
        new BeplanOkHttpClient().AddTeamMemberEmail(this, 4, str, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseActivity, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                if (intExtra == 1) {
                    if (beanCommon != null) {
                        if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                            Toast.makeText(this, "프로젝트 추가 성공", 0).show();
                        } else {
                            Toast.makeText(this, beanCommon.RtnValue, 0).show();
                        }
                    }
                    onBackPressed();
                } else if (intExtra == 2) {
                    setInitProject((BeanInitProject) gson.fromJson(str, (Class<Object>) BeanInitProject.class));
                } else if (intExtra == 3) {
                    if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                        Toast.makeText(this, "팀 멤버 추가 성공", 0).show();
                        setAddTeamMember((BeanAddTeam) gson.fromJson(str, (Class<Object>) BeanAddTeam.class));
                        return;
                    }
                    Toast.makeText(this, beanCommon.RtnValue, 0).show();
                } else if (intExtra == 4) {
                    if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                        CustomDropDownSet((BeanAddTeamMemberEmail) gson.fromJson(str, (Class<Object>) BeanAddTeamMemberEmail.class));
                    } else {
                        Toast.makeText(this, beanCommon.RtnValue, 0).show();
                    }
                } else if (intExtra == REQ_MY_SUBSCRIPTION_USING_PROJECT) {
                    if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                        if (!TextUtils.isEmpty(this.mPTitle)) {
                            Toast.makeText(this, this.mPTitle + "프로젝트를 사용합니다.", 0).show();
                        }
                        finish();
                        return;
                    }
                    Toast.makeText(this, beanCommon.RtnValue, 0).show();
                } else if (intExtra == REQ_MY_SUBSCRIPTION_USING_CATEGORY) {
                    if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                        if (!TextUtils.isEmpty(this.mCGTitle)) {
                            Toast.makeText(this, this.mCGTitle + "카테고리를 사용합니다.", 0).show();
                        }
                        finish();
                        return;
                    }
                    Toast.makeText(this, beanCommon.RtnValue, 0).show();
                } else if (intExtra == REQ_MY_FORM_USING) {
                    if (TextUtils.equals(this.mChildType, "TYPE_PROJECT")) {
                        if (!TextUtils.isEmpty(this.mPTitle)) {
                            Toast.makeText(this, this.mPTitle + "프로젝트를 사용합니다.", 0).show();
                        }
                    } else if (TextUtils.equals(this.mChildType, "TYPE_CATEGORY") && !TextUtils.isEmpty(this.mCGTitle)) {
                        Toast.makeText(this, this.mCGTitle + "카테고리를 사용합니다.", 0).show();
                    }
                    finish();
                }
            } else if (TextUtils.isEmpty(beanCommon.RtnValue)) {
                Toast.makeText(this, getString(R.string.error_server_not_success), 0).show();
            } else {
                Toast.makeText(this, beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setInitProject(BeanInitProject beanInitProject) {
        this.mPermissionList = beanInitProject.Auth;
        this.mTeamList = beanInitProject.My_Team;
        setDropdownTeam();
    }

    private void setAddTeamMember(BeanAddTeam beanAddTeam) {
        boolean z;
        if (beanAddTeam.memberList != null) {
            for (BeanAddTeam.memberList memberlist : beanAddTeam.memberList) {
                Iterator<BeanAddTeam.memberList> it = this.mList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (TextUtils.equals(it.next().email, memberlist.email)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    memberlist.setPMSEQ(0);
                    this.mList.add(memberlist);
                }
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void setAddTeamMemberEmail(BeanAddTeamMemberEmail.MemberList memberList) {
        Iterator<BeanAddTeam.memberList> it = this.mList.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().email, memberList.email)) {
                return;
            }
        }
        BeanAddTeam.memberList memberlist = new BeanAddTeam.memberList();
        memberlist.ProfileImgUrl = memberList.profileImgUrl;
        memberlist.RealName = memberList.realName;
        memberlist.Id = memberList.Id;
        memberlist.email = memberList.email;
        memberlist.setPMSEQ(0);
        this.mList.add(memberlist);
        this.mAdapter.notifyDataSetChanged();
        this.mEtEmail.setText("");
    }

    private void setAddTeamMemberEmail(BeanMain.Project_Member project_Member) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (TextUtils.equals(this.mList.get(i).email, project_Member.Email)) {
                this.mList.get(i).setPMSEQ(project_Member.PMSEQ);
                return;
            }
        }
        BeanAddTeam.memberList memberlist = new BeanAddTeam.memberList();
        memberlist.ProfileImgUrl = project_Member.ProfileImgUrl;
        memberlist.RealName = project_Member.RealName;
        memberlist.Id = project_Member.MemberId;
        memberlist.email = project_Member.Email;
        memberlist.setOldAuthKey(project_Member.Auth);
        memberlist.setPMSEQ(project_Member.PMSEQ);
        this.mList.add(memberlist);
        this.mAdapter.notifyDataSetChanged();
        this.mEtEmail.setText("");
    }

    @OnClick({R.id.btn_save})
    public void onViewClicked() {
        char c;
        String str = this.mType;
        int hashCode = str.hashCode();
        if (hashCode != -1438895246) {
            if (hashCode == -102747646 && str.equals(TYPE_SUBSCRIPTION)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(TYPE_MY_FORM)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                httpPostMyFormUsing();
                return;
            case 1:
                if (TextUtils.equals(this.mChildType, "TYPE_PROJECT")) {
                    httpUsingProject();
                    return;
                } else if (TextUtils.equals(this.mChildType, "TYPE_CATEGORY")) {
                    httpUsingCategory();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
        finish();
    }
}
