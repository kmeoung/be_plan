package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
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
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterMemberEmail;
import kr.timehub.beplan.base.widgets.BaseLinearLayoutManager;
import kr.timehub.beplan.base.widgets.BaseSpinner;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanAddTeam;
import kr.timehub.beplan.entry.plugin.BeanAddTeamMemberEmail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanInitProject;
import kr.timehub.beplan.entry.plugin.BeanTeamDetail;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentTeamDetail extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    private BaseRecyclerViewAdapter2<BeanTeamDetail.MemberPreviewList> mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.base_scroll)
    NestedScrollView mBaseScroll;
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btn_more)
    ImageButton mBtnMore;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.btn_search)
    ImageButton mBtnSearch;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_team_title)
    android.widget.EditText mEtTeamTitle;
    @BindView(R.id.ll_select_team)
    LinearLayout mLlSelectTeam;
    private List<BeanInitProject.Auth> mPermissionList;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.sp_team_member)
    BaseSpinner mSpTeamMember;
    private List<BeanInitProject.My_Team> mTeamList;
    private int mTeamSeq;
    @BindView(R.id.tv_select_team)
    TextView mTvSelectTeam;
    @BindView(R.id.tv_team_member)
    TextView mTvTeamMember;
    @BindView(R.id.v_status)
    View mVStatus;
    Unbinder unbinder;
    private int TYPE_LISTITEM = 1;
    private final int REQ_TEAM_DETAIL = 1;
    private final int REQ_PROJECT_FORM = 2;
    private final int REQ_ADD_TEAM_MEMBER = 3;
    private final int REQ_TEAM_CHANGE = 4;
    private final int REQ_ADD_TEAM_MEMBER_EMAIL = 5;

    public static FragmentTeamDetail newInstance(int i) {
        FragmentTeamDetail fragmentTeamDetail = new FragmentTeamDetail();
        fragmentTeamDetail.mTeamSeq = i;
        return fragmentTeamDetail;
    }

    @OnClick({R.id.btn_search})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_search && !String.valueOf(this.mEtEmail.getText()).equals("")) {
            httpPostAddTeamMemberEmail(this.mEtEmail.getText().toString());
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_new_team, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("false</true</팀 관리</false</false");
        initAdapter();
        httpPostInitProject();
        httpPostTeamDetailPopup();
        onAction();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2<>(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new BaseLinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void setTeamDetail(BeanTeamDetail beanTeamDetail) {
        if (!TextUtils.isEmpty(beanTeamDetail.TeamName)) {
            this.mEtTeamTitle.setText(beanTeamDetail.TeamName);
        }
        this.mTvTeamMember.setText(String.format("팀 참여인원 (%d명)", Integer.valueOf(beanTeamDetail.MemberPreviewList.size())));
        if (beanTeamDetail.MemberPreviewList != null) {
            for (BeanTeamDetail.MemberPreviewList memberPreviewList : beanTeamDetail.MemberPreviewList) {
                this.mAdapter.add(memberPreviewList);
            }
        }
    }

    private void onAction() {
        this.mEtEmail.setOnKeyListener(new View.OnKeyListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 66) {
                    return false;
                }
                FragmentTeamDetail.this.httpPostAddTeamMemberEmail(FragmentTeamDetail.this.mEtEmail.getText().toString());
                return true;
            }
        });
        this.mEtTeamTitle.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() <= 0) {
                    FragmentTeamDetail.this.mBtnSave.setBackground(ContextCompat.getDrawable(FragmentTeamDetail.this.getContext(), R.drawable.btn_gray_02));
                } else if (FragmentTeamDetail.this.mAdapter.size() > 1) {
                    FragmentTeamDetail.this.mBtnSave.setBackground(ContextCompat.getDrawable(FragmentTeamDetail.this.getContext(), R.drawable.btn_grdt_02));
                } else {
                    FragmentTeamDetail.this.mBtnSave.setBackground(ContextCompat.getDrawable(FragmentTeamDetail.this.getContext(), R.drawable.btn_gray_02));
                }
            }
        });
    }

    public void httpPostAddTeamMemberEmail(String str) {
        new BeplanOkHttpClient().AddTeamMemberEmail(getContext(), 5, str, this);
    }

    public void setDropdownTeam() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        for (BeanInitProject.My_Team my_Team : this.mTeamList) {
            arrayList.add(my_Team.Value);
        }
        this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_add_team_member, arrayList));
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i > 0) {
                    FragmentTeamDetail.this.httpPostAddTeamMember(String.valueOf(((BeanInitProject.My_Team) FragmentTeamDetail.this.mTeamList.get(i - 1)).Key));
                    FragmentTeamDetail.this.mSpDropdown.setSelection(0);
                }
            }
        });
    }

    private void httpPostTeamDetailPopup() {
        new BeplanOkHttpClient().TeamDetailPopup(getContext(), 1, String.valueOf(this.mTeamSeq), this);
    }

    private void httpPostInitProject() {
        new BeplanOkHttpClient().InitProject(getContext(), 2, this);
    }

    private void httpPostTeamChange() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        ArrayList arrayList = new ArrayList();
        Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        int i = 0;
        while (true) {
            if (i >= this.mAdapter.size()) {
                break;
            }
            BeanTeamDetail.MemberPreviewList memberPreviewList = (BeanTeamDetail.MemberPreviewList) this.mAdapter.get(i);
            if (memberPreviewList.Id == Integer.parseInt(instance.getUserId())) {
                arrayList.add(Integer.valueOf(memberPreviewList.Id));
                break;
            }
            i++;
        }
        for (int i2 = 0; i2 < this.mAdapter.size(); i2++) {
            BeanTeamDetail.MemberPreviewList memberPreviewList2 = (BeanTeamDetail.MemberPreviewList) this.mAdapter.get(i2);
            if (memberPreviewList2.Id != Integer.parseInt(instance.getUserId())) {
                arrayList.add(Integer.valueOf(memberPreviewList2.Id));
            }
        }
        String obj = this.mEtTeamTitle.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(getContext(), getString(R.string.alert_plz_input_all_info), 0).show();
        } else {
            beplanOkHttpClient.TeamChange(getContext(), 4, String.valueOf(this.mTeamSeq), obj, arrayList, this);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_team_member, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        BeanTeamDetail.MemberPreviewList memberPreviewList = (BeanTeamDetail.MemberPreviewList) this.mAdapter.get(i);
        if (i == 0) {
            baseViewHolder.getView(R.id.ll_right_button).setVisibility(8);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_permissions);
            baseViewHolder.getView(R.id.iv_me).setVisibility(0);
            textView.setText(getString(R.string.str_permission_admin));
            textView.setTextColor(getResources().getColor(R.color.hintColor));
        } else {
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_permissions);
            textView2.setText("팀원");
            textView2.setTextColor(getResources().getColor(R.color.hintColor));
        }
        this.mTvTeamMember.setText(String.format("팀 참여인원 (%d명)", Integer.valueOf(this.mAdapter.size())));
        baseViewHolder.getView(R.id.ll_right_button).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTeamDetail.this.mAdapter.remove(i);
            }
        });
        String str = memberPreviewList.RealName;
        String str2 = memberPreviewList.Email;
        final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo);
        if (TextUtils.isEmpty(memberPreviewList.ProfileImgUrl)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
        } else {
            Glide.with(getContext()).load(memberPreviewList.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.5
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentTeamDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                    return false;
                }
            }).into(imageView);
        }
        baseViewHolder.setText(R.id.tv_name, str);
        baseViewHolder.setText(R.id.tv_email, str2);
        baseViewHolder.setText(R.id.tv_right_button, "제외");
        if (this.mAdapter.size() > 1) {
            this.mBtnSave.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_grdt_02));
        } else {
            this.mBtnSave.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_gray_02));
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return this.TYPE_LISTITEM;
    }

    public void httpPostAddTeamMember(String str) {
        new BeplanOkHttpClient().AddTeamMember(getContext(), 3, str, "", "", "", "", this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                setTeamDetail((BeanTeamDetail) gson.fromJson(str, (Class<Object>) BeanTeamDetail.class));
            } else if (intExtra == 2) {
                setInitProject((BeanInitProject) gson.fromJson(str, (Class<Object>) BeanInitProject.class));
            } else if (intExtra == 3) {
                if (TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    this.mEtEmail.setText("");
                    Toast.makeText(getContext(), "팀 멤버 추가 성공", 0).show();
                    setAddTeamMember((BeanAddTeam) gson.fromJson(str, (Class<Object>) BeanAddTeam.class));
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == 4) {
                if (beanCommon == null) {
                    return;
                }
                if (TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(getContext(), "팀 정보 변경 성공", 0).show();
                    getActivity().onBackPressed();
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra != 5) {
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
            this.mSpTeamMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamDetail.6
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
                        FragmentTeamDetail.this.setAddTeamMemberEmail(memberList2);
                        return;
                    }
                    this.isAvailableClick = true;
                }
            });
        }
    }

    public void setAddTeamMemberEmail(BeanAddTeamMemberEmail.MemberList memberList) {
        for (int i = 0; i < this.mAdapter.size(); i++) {
            if (((BeanTeamDetail.MemberPreviewList) this.mAdapter.get(i)).Id == memberList.Id) {
                this.mEtEmail.setText("");
                Toast.makeText(getContext(), "이미 추가된 멤버입니다.", 0).show();
                return;
            }
        }
        BeanTeamDetail.MemberPreviewList memberPreviewList = new BeanTeamDetail.MemberPreviewList();
        memberPreviewList.ProfileImgUrl = memberList.profileImgUrl;
        memberPreviewList.RealName = memberList.realName;
        memberPreviewList.Id = memberList.Id;
        memberPreviewList.Email = memberList.email;
        this.mAdapter.add(memberPreviewList);
        this.mEtEmail.setText("");
    }

    private void setInitProject(BeanInitProject beanInitProject) {
        this.mPermissionList = beanInitProject.Auth;
        this.mTeamList = beanInitProject.My_Team;
        setDropdownTeam();
    }

    private void setAddTeamMember(BeanAddTeam beanAddTeam) {
        if (beanAddTeam.memberList != null) {
            for (BeanAddTeam.memberList memberlist : beanAddTeam.memberList) {
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= this.mAdapter.size()) {
                        break;
                    } else if (((BeanTeamDetail.MemberPreviewList) this.mAdapter.get(i)).Id == memberlist.Id) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    BeanTeamDetail.MemberPreviewList memberPreviewList = new BeanTeamDetail.MemberPreviewList();
                    memberPreviewList.Email = memberlist.email;
                    memberPreviewList.Id = memberlist.Id;
                    memberPreviewList.InviteState = memberlist.InviteState;
                    memberPreviewList.ProfileImgUrl = memberlist.ProfileImgUrl;
                    memberPreviewList.RealName = memberlist.RealName;
                    this.mAdapter.add(memberPreviewList);
                }
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @OnClick({R.id.btn_save})
    public void onViewClicked() {
        if (this.mAdapter.size() != 1) {
            httpPostTeamChange();
        }
    }
}
