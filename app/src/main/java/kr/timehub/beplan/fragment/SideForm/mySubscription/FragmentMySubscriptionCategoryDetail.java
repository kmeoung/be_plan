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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityFormTaskDetail;
import kr.timehub.beplan.activity.ActivityUsingNewProject;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogSelectProjectSubscription;
import kr.timehub.beplan.entry.BeanSubScriptionCategoryDetail;
import kr.timehub.beplan.entry.BeanSubscriptionSelectVersionCategory;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentMySubscriptionCategoryDetail extends BaseListFragment<BeanSubScriptionCategoryDetail.CtegoryList> {
    private static final int REQ_MAIN = 624;
    private static final int REQ_USINGCATEGORYS = 180;
    private LinkedHashMap<Integer, Integer> firstSelected;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private String mCGTitle;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.ll_use)
    LinearLayout mLlUse;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    private int mSMCSEQ;
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
    private int selectCategoryNum = -1;
    private int mSelectCategorySeq = -1;
    private final int REQ_CATEGORY_DETAIL = 23;
    private final int REQ_CATEGORY_DETAIL_SELECT_VERSION = 24;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentMySubscriptionCategoryDetail.this.handler != null) {
                FragmentMySubscriptionCategoryDetail.this.httpPostCategoryDetail();
            }
        }
    };
    private HashMap<Integer, String> selectMap = new HashMap<>();

    public static FragmentMySubscriptionCategoryDetail newInstance(String str, String str2, int i) {
        FragmentMySubscriptionCategoryDetail fragmentMySubscriptionCategoryDetail = new FragmentMySubscriptionCategoryDetail();
        fragmentMySubscriptionCategoryDetail.mTitle = str;
        fragmentMySubscriptionCategoryDetail.mCGTitle = str2;
        fragmentMySubscriptionCategoryDetail.mSMCSEQ = i;
        return fragmentMySubscriptionCategoryDetail;
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
        this.firstSelected = new LinkedHashMap<>();
        httpPostCategoryDetail();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        activity.setTitle("false</true</" + this.mTitle + "</false</false");
        this.mTvTitle.setText(this.mCGTitle);
        this.mLlUse.setVisibility(8);
        this.mRlBg.setVisibility(8);
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
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (i != 0 || i2 != 0 || i3 != 0) {
                    FragmentMySubscriptionCategoryDetail.this.handler.removeMessages(0);
                    FragmentMySubscriptionCategoryDetail.this.handler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostCategoryDetail() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.SubscriptionCategorDetail(getContext(), 23, String.valueOf(this.mSMCSEQ), str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostCategoryDetailSelectVersion(int i) {
        new BeplanOkHttpClient().SubscriptionSelectCategoryVersion(getContext(), 24, String.valueOf(i), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostMainIndex() {
        new BeplanOkHttpClient().MainNewIndex(getContext(), REQ_MAIN, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostUsingCategorys(int i, int i2, String str) {
        new BeplanOkHttpClient().SubscriptionUsingCategorys(getContext(), REQ_USINGCATEGORYS, String.valueOf(i), String.valueOf(i2), str, null, this);
    }

    /* renamed from: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail$3  reason: invalid class name */
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
            return BaseViewHolder.newInstance(FragmentMySubscriptionCategoryDetail.this.getContext(), R.layout.listitem_form_detail, viewGroup);
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            onBindList((BaseViewHolder) viewHolder, i);
        }

        private void onBindList(BaseViewHolder baseViewHolder, final int i) {
            final BeanSubScriptionCategoryDetail.CtegoryList ctegoryList = FragmentMySubscriptionCategoryDetail.this.get(i);
            baseViewHolder.setText(R.id.tv_project, ctegoryList.ShopCategoryTitle);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_tab);
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_more);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.list_rv);
            LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_use);
            baseViewHolder.getView(R.id.ll_version_set).setVisibility(0);
            if (TextUtils.equals((CharSequence) FragmentMySubscriptionCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                imageView.setImageResource(R.drawable.btn_tab_open);
                linearLayout.setVisibility(0);
            } else {
                imageView.setImageResource(R.drawable.btn_tab_close);
                linearLayout.setVisibility(8);
            }
            FragmentMySubscriptionCategoryDetail.this.setDropdownTeam((Spinner) baseViewHolder.getView(R.id.sp_dropdown), ctegoryList, i);
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.3.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.equals((CharSequence) FragmentMySubscriptionCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                        FragmentMySubscriptionCategoryDetail.this.selectMap.put(Integer.valueOf(i), null);
                    } else {
                        FragmentMySubscriptionCategoryDetail.this.selectMap.put(Integer.valueOf(i), "select");
                    }
                    FragmentMySubscriptionCategoryDetail.this.notifyDataSetChanged();
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.3.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentMySubscriptionCategoryDetail.this.mSelectCategorySeq = ctegoryList.SCGSEQ;
                    FragmentMySubscriptionCategoryDetail.this.mSelectCategory = ctegoryList.ShopCategoryTitle;
                    FragmentMySubscriptionCategoryDetail.this.httpPostMainIndex();
                }
            });
            ((ImageView) baseViewHolder.getView(R.id.iv_more)).setVisibility(8);
            final BaseRecyclerViewAdapter2 baseRecyclerViewAdapter2 = new BaseRecyclerViewAdapter2(FragmentMySubscriptionCategoryDetail.this.getContext());
            baseRecyclerViewAdapter2.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.3.3
                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public int getItemViewType(int i2) {
                    return 1;
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return BaseViewHolder.newInstance(FragmentMySubscriptionCategoryDetail.this.getContext(), R.layout.listitem_rank_item, viewGroup);
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    BaseViewHolder baseViewHolder2 = (BaseViewHolder) viewHolder;
                    final BeanSubScriptionCategoryDetail.TaskList taskList = (BeanSubScriptionCategoryDetail.TaskList) baseRecyclerViewAdapter2.get(i2);
                    baseViewHolder2.setText(R.id.tv_project, taskList.TaskTitle);
                    baseViewHolder2.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.3.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Intent intent = new Intent(FragmentMySubscriptionCategoryDetail.this.getContext(), ActivityFormTaskDetail.class);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TSEQ, taskList.STSEQ);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TITLE, FragmentMySubscriptionCategoryDetail.this.mTitle);
                            FragmentMySubscriptionCategoryDetail.this.startActivity(intent);
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
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentMySubscriptionCategoryDetail.this.getContext()));
            recyclerView.setAdapter(baseRecyclerViewAdapter2);
            for (BeanSubScriptionCategoryDetail.TaskList taskList : ctegoryList.taskList) {
                baseRecyclerViewAdapter2.add(taskList);
                baseRecyclerViewAdapter2.notifyDataSetChanged();
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentMySubscriptionCategoryDetail.this.size();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new AnonymousClass3();
    }

    public void setDropdownTeam(Spinner spinner, final BeanSubScriptionCategoryDetail.CtegoryList ctegoryList, final int i) {
        ArrayList arrayList = new ArrayList();
        for (BeanSubScriptionCategoryDetail.ShopversionList shopversionList : ctegoryList.shopversionList) {
            arrayList.add(shopversionList.VersionTitle);
        }
        spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_form_theme, arrayList));
        spinner.setOnItemSelectedListener(null);
        spinner.setSelection(this.firstSelected.get(Integer.valueOf(i)).intValue());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.4
            boolean isFirst = true;

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                if (!this.isFirst) {
                    FragmentMySubscriptionCategoryDetail.this.httpPostCategoryDetailSelectVersion(ctegoryList.shopversionList.get(i2).SVSEQ);
                    FragmentMySubscriptionCategoryDetail.this.selectCategoryNum = i;
                    FragmentMySubscriptionCategoryDetail.this.firstSelected.put(Integer.valueOf(i), Integer.valueOf(i2));
                    return;
                }
                this.isFirst = false;
            }
        });
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                if (intExtra == 23) {
                    setCategoryDetail((BeanSubScriptionCategoryDetail) gson.fromJson(str, (Class<Object>) BeanSubScriptionCategoryDetail.class));
                } else if (intExtra == 24) {
                    setCategorySelectVersion((BeanSubscriptionSelectVersionCategory) gson.fromJson(str, (Class<Object>) BeanSubscriptionSelectVersionCategory.class));
                } else if (intExtra == REQ_MAIN) {
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

    private void showSelectProjectDialog(BeanMain beanMain) {
        DialogSelectProjectSubscription.newInstance(getContext(), (int) R.string.dialog_title_category_use, beanMain.Project_List, new DialogSelectProjectSubscription.OnDialogSelectProjectListener() { // from class: kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail.5
            @Override // kr.timehub.beplan.dialog.DialogSelectProjectSubscription.OnDialogSelectProjectListener
            public void sendPositive(DialogSelectProjectSubscription dialogSelectProjectSubscription, BeanMain.Project_List project_List) {
                if (project_List == null) {
                    Intent intent = new Intent(FragmentMySubscriptionCategoryDetail.this.getContext(), ActivityUsingNewProject.class);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGSEQ, FragmentMySubscriptionCategoryDetail.this.mSelectCategorySeq);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGTITLE, FragmentMySubscriptionCategoryDetail.this.mSelectCategory);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_TYPE, ActivityUsingNewProject.TYPE_SUBSCRIPTION);
                    dialogSelectProjectSubscription.dismiss();
                    FragmentMySubscriptionCategoryDetail.this.startActivity(intent);
                    return;
                }
                FragmentMySubscriptionCategoryDetail.this.httpPostUsingCategorys(FragmentMySubscriptionCategoryDetail.this.mSelectCategorySeq, project_List.PSEQ, project_List.ProjectTitle);
                dialogSelectProjectSubscription.dismiss();
            }
        }).show();
    }

    private void setCategoryDetail(BeanSubScriptionCategoryDetail beanSubScriptionCategoryDetail) {
        clear();
        this.firstSelected.clear();
        for (int i = 0; i < beanSubScriptionCategoryDetail.ctegoryList.size(); i++) {
            this.firstSelected.put(Integer.valueOf(i), Integer.valueOf(beanSubScriptionCategoryDetail.ctegoryList.get(i).shopversionList.size() - 1));
            add(beanSubScriptionCategoryDetail.ctegoryList.get(i));
        }
    }

    private void setCategorySelectVersion(BeanSubscriptionSelectVersionCategory beanSubscriptionSelectVersionCategory) {
        String str = beanSubscriptionSelectVersionCategory.category.ShopCategoryTitle;
        int i = beanSubscriptionSelectVersionCategory.category.SCGSEQ;
        if (this.selectCategoryNum != -1) {
            int i2 = this.selectCategoryNum;
            get(i2).ShopCategoryTitle = str;
            get(i2).shopversionList.clear();
            get(i2).SCGSEQ = i;
            for (BeanSubscriptionSelectVersionCategory.ShopversionList shopversionList : beanSubscriptionSelectVersionCategory.category.shopversionList) {
                BeanSubScriptionCategoryDetail.ShopversionList shopversionList2 = new BeanSubScriptionCategoryDetail.ShopversionList();
                shopversionList2.SVSEQ = shopversionList.SVSEQ;
                shopversionList2.VersionTitle = shopversionList.VersionTitle;
                get(i2).shopversionList.add(shopversionList2);
            }
            get(i2).taskList.clear();
            for (BeanSubscriptionSelectVersionCategory.TaskList taskList : beanSubscriptionSelectVersionCategory.category.taskList) {
                BeanSubScriptionCategoryDetail.TaskList taskList2 = new BeanSubScriptionCategoryDetail.TaskList();
                taskList2.STSEQ = taskList.STSEQ;
                taskList2.TaskTitle = taskList.TaskTitle;
                get(i2).taskList.add(taskList2);
            }
        }
        notifyDataSetChanged();
    }
}
