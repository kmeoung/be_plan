package kr.timehub.beplan.fragment.SideForm.mySubscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityFormTaskDetail;
import kr.timehub.beplan.activity.ActivityUsingNewProject;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogSelectProjectSubscription;
import kr.timehub.beplan.entry.BeanRank;
import kr.timehub.beplan.entry.BeanRankItem;
import kr.timehub.beplan.entry.BeanSubscriptionProjectDetail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentMySubscriptionProjectDetail extends BaseListFragment {
    private static final int REQ_MAIN = 36;
    private static final int REQ_USINGCATEGORYS = 559;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private String mCGTitle;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.ll_use)
    LinearLayout mLlUse;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    private String mPTitle;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    private String mSelectCategory;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_use)
    TextView mTvUse;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    Unbinder unbinder;
    private final int TYPE_LIST = 1;
    private final int TYPE_HEADER = 0;
    private final int TYPE_LISTITEM = 1;
    private int mSPSEQ = -1;
    private int mSVSEQ = -1;
    private final int REQ_SUBSCRIPTION_PROJECTDETAIL = 22;
    private int mSelectCategorySeq = -1;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentMySubscriptionProjectDetail.this.handler != null) {
                FragmentMySubscriptionProjectDetail.this.httpPostProjectDetail();
            }
        }
    };
    private HashMap<Integer, String> selectMap = new HashMap<>();
    private boolean mSelect = false;

    @OnClick({R.id.ll_use})
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), ActivityUsingNewProject.class);
        intent.putExtra(ActivityUsingNewProject.EXTRA_PSEQ, this.mSPSEQ);
        intent.putExtra(ActivityUsingNewProject.EXTRA_PTITLE, this.mCGTitle);
        intent.putExtra(ActivityUsingNewProject.EXTRA_TYPE, ActivityUsingNewProject.TYPE_SUBSCRIPTION);
        startActivity(intent);
    }

    public static FragmentMySubscriptionProjectDetail newInstance(String str, String str2, int i) {
        FragmentMySubscriptionProjectDetail fragmentMySubscriptionProjectDetail = new FragmentMySubscriptionProjectDetail();
        fragmentMySubscriptionProjectDetail.mTitle = str;
        fragmentMySubscriptionProjectDetail.mCGTitle = str2;
        fragmentMySubscriptionProjectDetail.mSPSEQ = i;
        return fragmentMySubscriptionProjectDetail;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_subscription_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        listViewSet();
        httpPostProjectDetail();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        activity.setTitle("false</true</" + this.mTitle + "</false</false");
        this.mTvTitle.setText("버전");
        this.mLlUse.setVisibility(0);
    }

    private void listViewSet() {
        this.mBaseRv.setAdapter(getAdapter());
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(0);
        baseItemDecoration.setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
        getListView().addItemDecoration(baseItemDecoration);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.handler != null) {
            this.handler.removeMessages(0);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.handler != null) {
            this.handler.removeMessages(0);
        }
        this.handler = null;
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (i != 0 || i2 != 0 || i3 != 0) {
                    FragmentMySubscriptionProjectDetail.this.handler.removeMessages(0);
                    FragmentMySubscriptionProjectDetail.this.handler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    private void bindTempData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new BeanRankItem(String.valueOf(i)));
        }
        add(new BeanRank("1. 할 일 목록", arrayList));
        add(new BeanRank("2. 할 일 목록", arrayList));
        add(new BeanRank("3. 할 일 목록", arrayList));
        add(new BeanRank("4. 할 일 목록", arrayList));
        notifyDataSetChanged();
    }

    /* renamed from: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail$3  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements BaseRecyclerViewAdapterInterface {
        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemViewType(int i) {
            return 1;
        }

        AnonymousClass3() {
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return BaseViewHolder.newInstance(FragmentMySubscriptionProjectDetail.this.getContext(), R.layout.listitem_form_detail, viewGroup);
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            onBindList((BaseViewHolder) viewHolder, i);
        }

        private void onBindList(BaseViewHolder baseViewHolder, final int i) {
            final BeanSubscriptionProjectDetail.ShopCategoryList shopCategoryList = (BeanSubscriptionProjectDetail.ShopCategoryList) FragmentMySubscriptionProjectDetail.this.get(i);
            baseViewHolder.setText(R.id.tv_project, shopCategoryList.ShopCategoryTitle);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_tab);
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_more);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.list_rv);
            LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_use);
            baseViewHolder.getView(R.id.ll_version_set).setVisibility(8);
            if (TextUtils.equals((CharSequence) FragmentMySubscriptionProjectDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                imageView.setImageResource(R.drawable.btn_tab_open);
                linearLayout.setVisibility(0);
            } else {
                imageView.setImageResource(R.drawable.btn_tab_close);
                linearLayout.setVisibility(8);
            }
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.equals((CharSequence) FragmentMySubscriptionProjectDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                        FragmentMySubscriptionProjectDetail.this.selectMap.put(Integer.valueOf(i), null);
                    } else {
                        FragmentMySubscriptionProjectDetail.this.selectMap.put(Integer.valueOf(i), "select");
                    }
                    FragmentMySubscriptionProjectDetail.this.notifyDataSetChanged();
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentMySubscriptionProjectDetail.this.mSelectCategorySeq = shopCategoryList.SCGSEQ;
                    FragmentMySubscriptionProjectDetail.this.mSelectCategory = shopCategoryList.ShopCategoryTitle;
                    FragmentMySubscriptionProjectDetail.this.httpPostMainIndex();
                }
            });
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
            imageView2.setVisibility(8);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(FragmentMySubscriptionProjectDetail.this.getContext(), view);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.getMenu().add("삭제");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.3.1
                        @Override // android.widget.PopupMenu.OnMenuItemClickListener
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            final BaseRecyclerViewAdapter2 baseRecyclerViewAdapter2 = new BaseRecyclerViewAdapter2(FragmentMySubscriptionProjectDetail.this.getContext());
            baseRecyclerViewAdapter2.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.4
                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public int getItemViewType(int i2) {
                    return 1;
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return BaseViewHolder.newInstance(FragmentMySubscriptionProjectDetail.this.getContext(), R.layout.listitem_rank_item, viewGroup);
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    BaseViewHolder baseViewHolder2 = (BaseViewHolder) viewHolder;
                    final BeanSubscriptionProjectDetail.TaskList taskList = (BeanSubscriptionProjectDetail.TaskList) baseRecyclerViewAdapter2.get(i2);
                    baseViewHolder2.setText(R.id.tv_project, taskList.TaskTitle);
                    baseViewHolder2.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.3.4.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Intent intent = new Intent(FragmentMySubscriptionProjectDetail.this.getContext(), ActivityFormTaskDetail.class);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TSEQ, taskList.STSEQ);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TITLE, FragmentMySubscriptionProjectDetail.this.mTitle);
                            FragmentMySubscriptionProjectDetail.this.startActivity(intent);
                        }
                    });
                    if (baseRecyclerViewAdapter2.size() - 1 == i2) {
                        baseViewHolder2.getView(R.id.iv_divider).setVisibility(8);
                    }
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public int getItemCount() {
                    return baseRecyclerViewAdapter2.size();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentMySubscriptionProjectDetail.this.getContext()));
            recyclerView.setAdapter(baseRecyclerViewAdapter2);
            for (BeanSubscriptionProjectDetail.TaskList taskList : shopCategoryList.taskList) {
                baseRecyclerViewAdapter2.add(taskList);
                baseRecyclerViewAdapter2.notifyDataSetChanged();
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentMySubscriptionProjectDetail.this.size();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new AnonymousClass3();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostProjectDetail() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String valueOf = this.mSVSEQ == -1 ? "" : String.valueOf(this.mSVSEQ);
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.SubscriptionProjectDetail(getContext(), 22, String.valueOf(this.mSPSEQ), valueOf, str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostMainIndex() {
        new BeplanOkHttpClient().MainNewIndex(getContext(), 36, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostUsingCategorys(int i, int i2, String str) {
        new BeplanOkHttpClient().SubscriptionUsingCategorys(getContext(), REQ_USINGCATEGORYS, String.valueOf(i), String.valueOf(i2), str, null, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                if (intExtra == 22) {
                    setSubscriptionDetail((BeanSubscriptionProjectDetail) gson.fromJson(str, (Class<Object>) BeanSubscriptionProjectDetail.class));
                } else if (intExtra == 36) {
                    showSelectProjectDialog((BeanMain) gson.fromJson(str, (Class<Object>) BeanMain.class));
                } else if (intExtra == REQ_USINGCATEGORYS && !TextUtils.isEmpty(this.mSelectCategory)) {
                    Context context = getContext();
                    Toast.makeText(context, this.mSelectCategory + "카테고리를 사용합니다.", 0).show();
                }
            } else if (TextUtils.equals(beanCommon.RtnKey, Comm_RtnKey.DANO)) {
                Toast.makeText(getContext(), "데이터가 없습니다.", 0).show();
            } else {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setSubscriptionDetail(BeanSubscriptionProjectDetail beanSubscriptionProjectDetail) {
        clear();
        this.selectMap.clear();
        for (BeanSubscriptionProjectDetail.ShopCategoryList shopCategoryList : beanSubscriptionProjectDetail.shopCategoryList) {
            add(shopCategoryList);
        }
        setDropdownTeam(beanSubscriptionProjectDetail.shopVersionList, beanSubscriptionProjectDetail.ShopVersionNO);
    }

    private void showSelectProjectDialog(BeanMain beanMain) {
        DialogSelectProjectSubscription.newInstance(getContext(), (int) R.string.dialog_title_category_use, beanMain.Project_List, new DialogSelectProjectSubscription.OnDialogSelectProjectListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.4
            @Override // kr.timehub.beplan.dialog.DialogSelectProjectSubscription.OnDialogSelectProjectListener
            public void sendPositive(DialogSelectProjectSubscription dialogSelectProjectSubscription, BeanMain.Project_List project_List) {
                if (project_List == null) {
                    Intent intent = new Intent(FragmentMySubscriptionProjectDetail.this.getContext(), ActivityUsingNewProject.class);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGSEQ, FragmentMySubscriptionProjectDetail.this.mSelectCategorySeq);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGTITLE, FragmentMySubscriptionProjectDetail.this.mSelectCategory);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_TYPE, ActivityUsingNewProject.TYPE_SUBSCRIPTION);
                    dialogSelectProjectSubscription.dismiss();
                    FragmentMySubscriptionProjectDetail.this.startActivity(intent);
                    return;
                }
                FragmentMySubscriptionProjectDetail.this.httpPostUsingCategorys(FragmentMySubscriptionProjectDetail.this.mSelectCategorySeq, project_List.PSEQ, project_List.ProjectTitle);
                dialogSelectProjectSubscription.dismiss();
            }
        }).show();
    }

    public void setDropdownTeam(final List<BeanSubscriptionProjectDetail.ShopVersionList> list, String str) {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (int i2 = 0; i2 < list.size(); i2++) {
            BeanSubscriptionProjectDetail.ShopVersionList shopVersionList = list.get(i2);
            arrayList.add(shopVersionList.VersionTitle);
            if (TextUtils.equals(str, shopVersionList.VersionTitle)) {
                i = i2;
            }
        }
        if (this.mSpDropdown.getAdapter() == null && i != -1) {
            this.mSVSEQ = list.get(i).SVSEQ;
        }
        this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_add_team_member, arrayList));
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(null);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i3, long j) {
                FragmentMySubscriptionProjectDetail.this.mSVSEQ = ((BeanSubscriptionProjectDetail.ShopVersionList) list.get(i3)).SVSEQ;
                if (!FragmentMySubscriptionProjectDetail.this.mSelect) {
                    FragmentMySubscriptionProjectDetail.this.httpPostProjectDetail();
                } else {
                    FragmentMySubscriptionProjectDetail.this.mSelect = false;
                }
            }
        });
        if (this.mSVSEQ != -1) {
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (list.get(i3).SVSEQ == this.mSVSEQ) {
                    if (!this.mSelect) {
                        this.mSpDropdown.setSelection(i3);
                        this.mSelect = true;
                        return;
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
