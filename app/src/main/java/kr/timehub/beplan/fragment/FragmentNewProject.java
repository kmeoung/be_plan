package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import butterknife.Unbinder;
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
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterMemberEmail;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterPermission;
import kr.timehub.beplan.base.widgets.BaseSpinner;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanProjectForm;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanAddTeam;
import kr.timehub.beplan.entry.plugin.BeanAddTeamMemberEmail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanInitProject;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class FragmentNewProject extends BaseListFragment<BeanAddTeam.memberList> {
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btn_more)
    ImageButton mBtnMore;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.btn_search)
    ImageButton mBtnSearch;
    private ArrayList<BeanAddTeam.memberList> mDeleteList;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_project)
    android.widget.EditText mEtProject;
    private ArrayList<BeanAddTeam.memberList> mList;
    @BindView(R.id.ll_select_team)
    LinearLayout mLlSelectTeam;
    private ArrayList<BeanAddTeam.memberList> mOldList;
    private int mPSEQ;
    private List<BeanInitProject.Auth> mPermissionList;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.sp_team_member)
    BaseSpinner mSpTeamMember;
    private List<BeanInitProject.My_Team> mTeamList;
    @BindView(R.id.tv_project_member)
    TextView mTvProjectMember;
    @BindView(R.id.tv_select_team)
    TextView mTvSelectTeam;
    @BindView(R.id.v_status)
    View mVStatus;
    Unbinder unbinder;
    private int TYPE_LISTITEM = 1;
    private final int REQ_NEW_PROJECT = 1;
    private final int REQ_PROJECT_FORM = 2;
    private final int REQ_ADD_TEAM_MEMBER = 3;
    private final int REQ_ADD_TEAM_MEMBER_EMAIL = 4;
    private final int REQ_CHANGE_PROJECT_INFO = 5;
    private BeanMain.Project_List mFormBean = null;
    private BeanProjectForm mForm = null;

    public static FragmentNewProject newInstance(BeanMain.Project_List project_List) {
        FragmentNewProject fragmentNewProject = new FragmentNewProject();
        fragmentNewProject.mFormBean = project_List;
        return fragmentNewProject;
    }

    public static FragmentNewProject newInstance(BeanProjectForm beanProjectForm, int i) {
        FragmentNewProject fragmentNewProject = new FragmentNewProject();
        fragmentNewProject.mForm = beanProjectForm;
        fragmentNewProject.mPSEQ = i;
        return fragmentNewProject;
    }

    @OnClick({R.id.btn_search})
    public void onSearch() {
        httpPostAddTeamMemberEmail();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_new_project, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mList == null) {
            this.mList = new ArrayList<>();
            BeanAddTeam.memberList memberlist = new BeanAddTeam.memberList();
            Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
            memberlist.email = instance.getUserEmail();
            memberlist.RealName = instance.getUserName();
            memberlist.ProfileImgUrl = instance.getUserUrl();
            memberlist.setAuth_key("B");
            memberlist.setAuth_value(getString(R.string.str_permission_admin));
            this.mList.add(memberlist);
            notifyDataSetChanged();
        }
        if (this.mFormBean != null) {
            getActivity().setTitle("false</true</프로젝트 수정하기</false</false");
        } else {
            getActivity().setTitle("false</true</새 프로젝트 만들기</false</false");
            if (this.mForm != null) {
                getActivity().setTitle("false</true</프로젝트 수정하기</false</false");
            }
        }
        httpPostInitProject();
        onAction();
    }

    private void onAction() {
        this.mEtEmail.setOnKeyListener(new View.OnKeyListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 66) {
                    return false;
                }
                FragmentNewProject.this.onSearch();
                return true;
            }
        });
        this.mEtProject.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    FragmentNewProject.this.mBtnSave.setBackground(ContextCompat.getDrawable(FragmentNewProject.this.getContext(), R.drawable.btn_grdt_02));
                } else {
                    FragmentNewProject.this.mBtnSave.setBackground(ContextCompat.getDrawable(FragmentNewProject.this.getContext(), R.drawable.btn_gray_02));
                }
            }
        });
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
            this.mSpTeamMember.setAdapter((SpinnerAdapter) new DropdownAdapterMemberEmail(getContext(), arrayList));
            Utils.setDropDownHeight(this.mSpTeamMember, HttpStatus.SC_INTERNAL_SERVER_ERROR);
            this.mSpTeamMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.3
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
                        FragmentNewProject.this.setAddTeamMemberEmail(memberList2);
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
        spinner.setAdapter((SpinnerAdapter) new DropdownAdapterPermission(getContext(), arrayList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                BeanInitProject.Auth auth2 = (BeanInitProject.Auth) arrayList.get(i2);
                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_key(auth2.Key);
                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_value(auth2.Value);
            }
        });
    }

    public void setDeleteDropdownSetPermission(final BeanAddTeam.memberList memberlist, Spinner spinner, final int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("재요청");
        arrayList.add("제외");
        spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_fragment_new_project_auth, arrayList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                if (i2 == 0) {
                    if (FragmentNewProject.this.mOldList != null) {
                        Iterator it = FragmentNewProject.this.mOldList.iterator();
                        while (it.hasNext()) {
                            if (((BeanAddTeam.memberList) it.next()).getPMSEQ() == memberlist.getPMSEQ()) {
                                FragmentNewProject.this.mDeleteList.remove(memberlist);
                                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_key("I");
                                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_value(memberlist.getAuth_value());
                                return;
                            }
                        }
                    }
                } else if (FragmentNewProject.this.mOldList != null) {
                    Iterator it2 = FragmentNewProject.this.mOldList.iterator();
                    while (it2.hasNext()) {
                        if (((BeanAddTeam.memberList) it2.next()).getPMSEQ() == memberlist.getPMSEQ()) {
                            FragmentNewProject.this.mDeleteList.add(memberlist);
                            return;
                        }
                    }
                }
            }
        });
    }

    public void setDropdownTeam() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        for (BeanInitProject.My_Team my_Team : this.mTeamList) {
            arrayList.add(my_Team.Value);
        }
        if (arrayList != null) {
            this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_add_team_member, arrayList));
            Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
            this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.6
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i > 0) {
                        FragmentNewProject.this.httpPostAddTeamMember(String.valueOf(((BeanInitProject.My_Team) FragmentNewProject.this.mTeamList.get(i - 1)).Key));
                        FragmentNewProject.this.mSpDropdown.setSelection(0);
                    }
                }
            });
        }
    }

    private void httpPostInitProject() {
        new BeplanOkHttpClient().InitProject(getContext(), 2, this);
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
                    jSONObject.put("UserId", Integer.parseInt(Comm_Prefs.getInstance(getContext()).getUserId()));
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
            Toast.makeText(getContext(), getString(R.string.alert_plz_input_all_info), 0).show();
        } else {
            beplanOkHttpClient.CreateProject(getContext(), 1, obj, jSONArray, this);
        }
    }

    private void httpPostChangeProject() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        new ArrayList();
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.mList.size(); i++) {
            try {
                BeanAddTeam.memberList memberlist = this.mList.get(i);
                if (i == 0) {
                    JSONObject jSONObject = new JSONObject();
                    String userId = Comm_Prefs.getInstance(getContext()).getUserId();
                    jSONObject.put("PMSEQ", memberlist.getPMSEQ());
                    jSONObject.put("UserId", Integer.parseInt(userId));
                    jSONObject.put("UserAuth", "B");
                    jSONArray.put(jSONObject);
                } else {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("PMSEQ", memberlist.getPMSEQ());
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
            Toast.makeText(getContext(), getString(R.string.alert_plz_input_all_info), 0).show();
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (this.mDeleteList == null || this.mDeleteList.size() <= 0) {
            arrayList = null;
        } else {
            Iterator<BeanAddTeam.memberList> it = this.mOldList.iterator();
            while (it.hasNext()) {
                BeanAddTeam.memberList next = it.next();
                Iterator<BeanAddTeam.memberList> it2 = this.mDeleteList.iterator();
                while (it2.hasNext()) {
                    BeanAddTeam.memberList next2 = it2.next();
                    if (next2.getPMSEQ() == next.getPMSEQ()) {
                        arrayList.add(Integer.valueOf(next2.getPMSEQ()));
                    }
                }
            }
        }
        if (this.mFormBean != null) {
            beplanOkHttpClient.ChangeProectInfo(getContext(), 5, String.valueOf(this.mFormBean.PSEQ), obj, jSONArray, arrayList, this);
        } else if (this.mForm != null) {
            beplanOkHttpClient.ChangeProectInfo(getContext(), 5, String.valueOf(this.mPSEQ), obj, jSONArray, arrayList, this);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.7
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return BaseViewHolder.newInstance(FragmentNewProject.this.getContext(), R.layout.listitem_new_project, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                final BeanAddTeam.memberList memberlist = (BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i);
                Spinner spinner = (Spinner) baseViewHolder.getView(R.id.sp_dropdown);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_invite_state);
                int i2 = 0;
                if (i == 0) {
                    baseViewHolder.getView(R.id.iv_me).setVisibility(0);
                    baseViewHolder.getView(R.id.ll_right_button).setVisibility(8);
                    TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_permissions);
                    textView2.setText(FragmentNewProject.this.getString(R.string.str_permission_admin));
                    textView2.setTextColor(FragmentNewProject.this.getResources().getColor(R.color.hintColor));
                    spinner.setVisibility(8);
                    textView.setVisibility(8);
                } else {
                    spinner.setVisibility(0);
                    if (TextUtils.isEmpty(memberlist.InviteState)) {
                        baseViewHolder.getView(R.id.ll_right_button).setVisibility(0);
                        FragmentNewProject.this.setDropdownSetPermission(spinner, i);
                        textView.setVisibility(8);
                    } else if (TextUtils.equals(memberlist.InviteState, "D")) {
                        FragmentNewProject.this.setDeleteDropdownSetPermission(memberlist, spinner, i);
                        textView.setVisibility(0);
                        baseViewHolder.getView(R.id.ll_right_button).setVisibility(8);
                        textView.setText("승인거절");
                    } else {
                        FragmentNewProject.this.setDropdownSetPermission(spinner, i);
                        textView.setVisibility(8);
                        baseViewHolder.getView(R.id.ll_right_button).setVisibility(0);
                        if (TextUtils.equals(memberlist.InviteState, "W")) {
                            textView.setText("승인대기");
                            textView.setVisibility(0);
                        }
                    }
                }
                FragmentNewProject.this.mTvProjectMember.setText(String.format("프로젝트 참여인원 (%d명)", Integer.valueOf(FragmentNewProject.this.mList.size())));
                baseViewHolder.getView(R.id.ll_right_button).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.7.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FragmentNewProject.this.mList.remove(i);
                        if (FragmentNewProject.this.mOldList != null) {
                            Iterator it = FragmentNewProject.this.mOldList.iterator();
                            while (it.hasNext()) {
                                if (((BeanAddTeam.memberList) it.next()).getPMSEQ() == memberlist.getPMSEQ()) {
                                    FragmentNewProject.this.mDeleteList.add(memberlist);
                                }
                            }
                        }
                        FragmentNewProject.this.notifyDataSetChanged();
                    }
                });
                String str = memberlist.RealName;
                String str2 = memberlist.email;
                final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo);
                if (TextUtils.isEmpty(memberlist.ProfileImgUrl)) {
                    Glide.with(FragmentNewProject.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
                } else {
                    Glide.with(FragmentNewProject.this.getContext()).load(memberlist.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentNewProject.7.2
                        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                            return false;
                        }

                        @Override // com.bumptech.glide.request.RequestListener
                        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                            Glide.with(FragmentNewProject.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                            return false;
                        }
                    }).into(imageView);
                }
                baseViewHolder.setText(R.id.tv_name, str);
                baseViewHolder.setText(R.id.tv_email, str2);
                baseViewHolder.setText(R.id.tv_right_button, "제외");
                if (FragmentNewProject.this.mFormBean != null) {
                    if (FragmentNewProject.this.mPermissionList != null) {
                        while (i2 < FragmentNewProject.this.mPermissionList.size()) {
                            BeanInitProject.Auth auth = (BeanInitProject.Auth) FragmentNewProject.this.mPermissionList.get(i2);
                            if (TextUtils.equals(memberlist.InviteState, "D")) {
                                if (TextUtils.equals(auth.Key, memberlist.getOldAuthKey())) {
                                    ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_key(memberlist.getAuth_key());
                                    ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_value(memberlist.getAuth_value());
                                    memberlist.setOldAuthKey("Used");
                                }
                            } else if (TextUtils.equals(auth.Key, memberlist.getOldAuthKey())) {
                                spinner.setSelection(i2);
                                memberlist.setOldAuthKey(memberlist.getOldAuthKey());
                                return;
                            }
                            i2++;
                        }
                    }
                } else if (FragmentNewProject.this.mForm != null && FragmentNewProject.this.mPermissionList != null) {
                    while (i2 < FragmentNewProject.this.mPermissionList.size()) {
                        BeanInitProject.Auth auth2 = (BeanInitProject.Auth) FragmentNewProject.this.mPermissionList.get(i2);
                        if (TextUtils.equals(memberlist.InviteState, "D")) {
                            if (TextUtils.equals(auth2.Key, memberlist.getOldAuthKey())) {
                                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_key(memberlist.getAuth_key());
                                ((BeanAddTeam.memberList) FragmentNewProject.this.mList.get(i)).setAuth_value(memberlist.getAuth_value());
                                memberlist.setOldAuthKey("Used");
                            }
                        } else if (TextUtils.equals(auth2.Key, memberlist.getOldAuthKey())) {
                            spinner.setSelection(i2);
                            memberlist.setOldAuthKey(memberlist.getOldAuthKey());
                            return;
                        }
                        i2++;
                    }
                }
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return FragmentNewProject.this.mList.size();
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i) {
                return FragmentNewProject.this.TYPE_LISTITEM;
            }
        };
    }

    public void httpPostAddTeamMember(String str) {
        new BeplanOkHttpClient().AddTeamMember(getContext(), 3, str, "", "", "", "", this);
    }

    private void httpPostAddTeamMemberEmail() {
        String obj = this.mEtEmail.getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            new BeplanOkHttpClient().AddTeamMemberEmail(getContext(), 4, obj, this);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                if (beanCommon != null) {
                    if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                        Toast.makeText(getContext(), "프로젝트 추가 성공", 0).show();
                    } else {
                        Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    }
                }
                getActivity().onBackPressed();
            } else if (intExtra == 5) {
                if (beanCommon != null) {
                    if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                        Toast.makeText(getContext(), "프로젝트 수정 성공", 0).show();
                    } else {
                        Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    }
                }
                getActivity().onBackPressed();
            } else if (intExtra == 2) {
                setInitProject((BeanInitProject) gson.fromJson(str, (Class<Object>) BeanInitProject.class));
            } else if (intExtra == 3) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(getContext(), "팀 멤버 추가 성공", 0).show();
                    setAddTeamMember((BeanAddTeam) gson.fromJson(str, (Class<Object>) BeanAddTeam.class));
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra != 4) {
            } else {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    CustomDropDownSet((BeanAddTeamMemberEmail) gson.fromJson(str, (Class<Object>) BeanAddTeamMemberEmail.class));
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setInitProject(BeanInitProject beanInitProject) {
        this.mPermissionList = beanInitProject.Auth;
        this.mTeamList = beanInitProject.My_Team;
        if (this.mFormBean != null) {
            this.mEtProject.setText(this.mFormBean.ProjectTitle);
            for (BeanMain.Project_Member project_Member : this.mFormBean.Project_Member) {
                setAddTeamMemberEmail(project_Member);
            }
            this.mOldList = new ArrayList<>();
            this.mDeleteList = new ArrayList<>();
            this.mOldList.addAll(this.mList);
        } else if (this.mForm != null) {
            this.mEtProject.setText(this.mForm.getProjectName());
            for (BeanProjectForm.Project_Member project_Member2 : this.mForm.Project_Member) {
                setAddTeamMemberEmail(project_Member2);
            }
            this.mOldList = new ArrayList<>();
            this.mDeleteList = new ArrayList<>();
            this.mOldList.addAll(this.mList);
        }
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
        notifyDataSetChanged();
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
        notifyDataSetChanged();
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
        memberlist.InviteState = project_Member.InviteState;
        memberlist.setOldAuthKey(project_Member.Auth);
        memberlist.setPMSEQ(project_Member.PMSEQ);
        memberlist.setAuth_key(project_Member.Auth);
        this.mList.add(memberlist);
        notifyDataSetChanged();
        this.mEtEmail.setText("");
    }

    private void setAddTeamMemberEmail(BeanProjectForm.Project_Member project_Member) {
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
        memberlist.InviteState = project_Member.InviteState;
        memberlist.setOldAuthKey(project_Member.Auth);
        memberlist.setPMSEQ(project_Member.PMSEQ);
        memberlist.setAuth_key(project_Member.Auth);
        this.mList.add(memberlist);
        notifyDataSetChanged();
        this.mEtEmail.setText("");
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @OnClick({R.id.btn_save})
    public void onViewClicked() {
        if (this.mFormBean != null) {
            httpPostChangeProject();
        } else if (this.mForm != null) {
            httpPostChangeProject();
        } else {
            httpPostCreateProject();
        }
    }
}
