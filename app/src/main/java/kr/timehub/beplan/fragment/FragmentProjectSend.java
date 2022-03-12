package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.objects.BaseMoveRecyclerViewAdapter;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.entry.plugin.BeanTaskSendList;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentProjectSend extends BaseFragmentProject implements BaseRecyclerViewAdapterInterface {
    private static final String args_title = "args_title";
    private BeanTaskSendList beanTaskSendList;
    BaseMoveRecyclerViewAdapter mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    private ItemTouchHelper mItemTouchHelper;
    @BindView(R.id.ll_list)
    LinearLayout mLlList;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    private BeanMain.Project_List mMainList;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    private List<BeanTaskSendList.TapList> mTapList;
    private String mTapState;
    @BindView(R.id.tv_default)
    TextView mTvDefault;
    @BindView(R.id.tv_list)
    TextView mTvList;
    private String projectName;
    Unbinder unbinder;
    private final int KEYWORD_DELAY = 2000;
    private boolean isMoving = false;
    private int mPSEQ = -1;
    private boolean isWrite = false;
    private String mKeyword = "";
    private Handler keywordHandler = new Handler() { // from class: kr.timehub.beplan.fragment.FragmentProjectSend.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentProjectSend.this.mEtKeyword != null) {
                FragmentProjectSend.this.mKeyword = FragmentProjectSend.this.mEtKeyword.getText().toString();
                FragmentProjectSend.this.httpPostMyTaskSendList(FragmentProjectSend.this.mTapState, FragmentProjectSend.this.mKeyword);
            }
        }
    };
    HashMap<Integer, String> runTimer = new HashMap<>();

    public static final FragmentProjectSend newInstance(int i, String str, BeanMain.Project_List project_List) {
        FragmentProjectSend fragmentProjectSend = new FragmentProjectSend();
        fragmentProjectSend.mPSEQ = i;
        fragmentProjectSend.projectName = str;
        fragmentProjectSend.mMainList = project_List;
        return fragmentProjectSend;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_main_categorys, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        setRetainInstance(true);
        setupSavedViews(bundle);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        setToolbar();
        initAdapter();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(args_title, this.projectName);
        super.onSaveInstanceState(bundle);
    }

    @Override // kr.timehub.beplan.fragment.BaseFragmentProject
    public void onUpdatedTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.projectName = str;
        }
        setToolbar();
    }

    private void setupSavedViews(Bundle bundle) {
        if (bundle != null) {
            this.projectName = bundle.getString(args_title);
            setToolbar();
        }
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentProjectSend.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!TextUtils.isEmpty(FragmentProjectSend.this.mTapState)) {
                    FragmentProjectSend.this.keywordHandler.removeMessages(0);
                    FragmentProjectSend.this.keywordHandler.sendEmptyMessageDelayed(0, 2000L);
                }
            }
        });
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z) {
        super.onPageChanged(viewPager, z);
        if (z) {
            httpPostMyTaskSendList(this.mTapState, this.mKeyword);
            setToolbar();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mTapList = null;
        this.mTapState = null;
    }

    private void setToolbar() {
        BaseMainToolBar baseMainToolBar;
        if ((getActivity() instanceof ActivityMain) && (baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar()) != null) {
            baseMainToolBar.setTitle(getProjectName());
        }
    }

    public void httpPostMyTaskSendList(String str, String str2) {
        new BeplanOkHttpClient().NewMyTaskSendList(getContext(), 4, String.valueOf(this.mPSEQ), str, str2, this);
    }

    private void setTaskSendList(BeanTaskSendList beanTaskSendList) {
        this.mAdapter.clear();
        if (beanTaskSendList.getCategoryList() != null) {
            for (BeanTaskSendList.CategoryListBean categoryListBean : beanTaskSendList.getCategoryList()) {
                this.mAdapter.add(categoryListBean);
                if (categoryListBean.getTaskList() != null) {
                    for (BeanTaskSendList.CategoryListBean.TaskList taskList : categoryListBean.getTaskList()) {
                        taskList.CategoryTitle = categoryListBean.getCategoryTitle();
                        taskList.CGSEQ = categoryListBean.getCGSEQ();
                        this.mAdapter.add(taskList);
                    }
                }
            }
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.mAdapter.size() > 0) {
            this.mTvDefault.setVisibility(8);
        } else {
            this.mTvDefault.setVisibility(0);
            this.mTvDefault.setText("보낸 요청이 없습니다.");
        }
        DropDownSet(beanTaskSendList.getTapList());
    }

    private void DropDownSet(final List<BeanTaskSendList.TapList> list) {
        if (this.mTapList == null || this.mTapList.size() < 1) {
            this.mTapList = list;
            ArrayList arrayList = new ArrayList();
            for (BeanTaskSendList.TapList tapList : list) {
                arrayList.add(tapList.TapName);
            }
            this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_bottom_view, arrayList));
            this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectSend.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i) instanceof String) {
                        String str = (String) adapterView.getItemAtPosition(i);
                        if (FragmentProjectSend.this.mAdapter != null && FragmentProjectSend.this.beanTaskSendList != null) {
                            if (!TextUtils.isEmpty(FragmentProjectSend.this.mTapState)) {
                                FragmentProjectSend.this.httpPostMyTaskSendList(((BeanTaskSendList.TapList) list.get(i)).TapCode, FragmentProjectSend.this.mKeyword);
                            }
                            FragmentProjectSend.this.mTapState = ((BeanTaskSendList.TapList) list.get(i)).TapCode;
                        }
                    }
                }
            });
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseMoveRecyclerViewAdapter(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
        new BaseItemDecoration(0).setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
    }

    public void onListItemViewHolder(BaseViewHolder baseViewHolder, int i) {
        final BeanTaskSendList.CategoryListBean.TaskList taskList = (BeanTaskSendList.CategoryListBean.TaskList) this.mAdapter.get(i);
        ((ImageView) baseViewHolder.getView(R.id.iv_icn_check)).setImageResource(R.drawable.icn_todo_send);
        Button button = (Button) baseViewHolder.getView(R.id.btn_timer);
        if (this.runTimer.get(Integer.valueOf(i)) != null) {
            button.setText("종료");
        } else {
            button.setText("시작");
        }
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_business_content);
        if (i > 0) {
            if (this.mAdapter.size() - 1 == i) {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
            } else if (this.mAdapter.get(i + 1) instanceof BeanTaskSendList.CategoryListBean) {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
            } else {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_middle);
            }
            boolean z = this.mAdapter.get(i - 1) instanceof String;
        }
        String taskTitle = taskList.getTaskTitle();
        String runnerName = taskList.getRunnerName();
        baseViewHolder.setText(R.id.tv_business, taskTitle);
        baseViewHolder.setText(R.id.tv_name, runnerName);
        Glide.with(getContext()).asBitmap().load(taskList.getRunnerProfile()).apply(new RequestOptions().placeholder(R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((ImageView) baseViewHolder.getView(R.id.iv_photo));
        ((TextView) baseViewHolder.getView(R.id.tv_finish_date)).setVisibility(8);
        ((TextView) baseViewHolder.getView(R.id.tv_date_middle)).setVisibility(8);
        baseViewHolder.setText(R.id.tv_start_date, new SimpleDateFormat("yy. MM. dd").format(Utils.ConvertDate(taskList.getRegDate())));
        baseViewHolder.setText(R.id.tv_business_state, taskList.getStringTaskState());
        ((ImageView) baseViewHolder.getView(R.id.iv_me_button)).setVisibility(8);
        baseViewHolder.getView(R.id.ll_business_content).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectSend.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (taskList.getIsRequestOrTask() == 1) {
                    FragmentTransaction beginTransaction = FragmentProjectSend.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    beginTransaction.addToBackStack(null);
                    beginTransaction.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    beginTransaction.replace(R.id.base_container, FragmentTaskDetail.newInstance(FragmentProjectSend.this.mPSEQ, taskList.CGSEQ, taskList.getTSEQ(), FragmentProjectSend.this.projectName));
                    beginTransaction.commit();
                } else if (taskList.getIsRequestOrTask() == 2) {
                    FragmentTransaction beginTransaction2 = FragmentProjectSend.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    beginTransaction2.addToBackStack(null);
                    beginTransaction2.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    beginTransaction2.replace(R.id.base_container, FragmentRequestDetail.newInstance(FragmentRequestDetail.REQUEST_TYPE_SEND, FragmentProjectSend.this.mPSEQ, taskList.CGSEQ, taskList.getTSEQ(), taskList.getTaskState(), taskList.CategoryTitle, FragmentProjectSend.this.projectName, String.valueOf(taskList.getRunnerID()), taskList.getRunnerName(), taskList.getRunnerProfile()));
                    beginTransaction2.commit();
                }
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
        if (i == 1) {
            return BaseViewHolder.newInstance(getContext(), R.layout.listitem_business_title, viewGroup);
        }
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_business_content, viewGroup);
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        int itemViewType = getItemViewType(i);
        BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
        if (itemViewType == 0) {
            onListItemViewHolder(baseViewHolder, i);
            return;
        }
        baseViewHolder.setText(R.id.tv_business_title_name, ((BeanTaskSendList.CategoryListBean) this.mAdapter.get(i)).getCategoryTitle());
        baseViewHolder.getView(R.id.iv_add).setVisibility(8);
        baseViewHolder.getView(R.id.iv_more).setVisibility(8);
        baseViewHolder.getView(R.id.iv_sell).setVisibility(8);
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (this.mAdapter.get(i) instanceof BeanTaskSendList.CategoryListBean) {
            BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
            return 1;
        }
        BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter2 = this.mAdapter;
        return 0;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String str) {
        this.projectName = str;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            if (intExtra == 4) {
                this.beanTaskSendList = (BeanTaskSendList) gson.fromJson(str, (Class<Object>) BeanTaskSendList.class);
                if (TextUtils.equals(this.beanTaskSendList.getRtnKey(), "DAOK") || TextUtils.equals(this.beanTaskSendList.getRtnKey(), "CMOK") || TextUtils.equals(this.beanTaskSendList.getRtnKey(), Comm_RtnKey.DANO)) {
                    BaseMainToolBar baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar();
                    String projectTitle = this.beanTaskSendList.getProjectTitle();
                    if (!TextUtils.isEmpty(projectTitle)) {
                        setProjectName(projectTitle);
                        setToolbar();
                    }
                    this.isWrite = this.beanTaskSendList.isIsWrite();
                    if (baseMainToolBar != null) {
                        if (this.isWrite) {
                            baseMainToolBar.setToolbarVisibleState(true, false, true, true);
                        } else {
                            baseMainToolBar.setToolbarVisibleState(true, false, false, true);
                        }
                    }
                    setTaskSendList(this.beanTaskSendList);
                    return;
                }
                this.mAdapter.clear();
                if (!TextUtils.equals(Comm_RtnKey.DANO, this.beanTaskSendList.getRtnKey())) {
                    Toast.makeText(getContext(), this.beanTaskSendList.getRtnValue(), 0).show();
                    return;
                }
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }
}
