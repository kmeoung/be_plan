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
import kr.timehub.beplan.entry.plugin.BeanTaskReceiveList;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentProjectReceive extends BaseFragmentProject implements BaseRecyclerViewAdapterInterface {
    private static final String args_title = "args_title";
    private BeanTaskReceiveList beanTaskReceiveList;
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
    private List<BeanTaskReceiveList.TapList> mTapList;
    private String mTapState;
    @BindView(R.id.tv_default)
    TextView mTvDefault;
    @BindView(R.id.tv_list)
    TextView mTvList;
    private String projectName;
    Unbinder unbinder;
    private final int KEYWORD_DELAY = 1000;
    private boolean isMoving = false;
    private int mPSEQ = -1;
    private String mKeyword = "";
    private boolean isWrite = false;
    private Handler keywordHandler = new Handler() { // from class: kr.timehub.beplan.fragment.FragmentProjectReceive.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentProjectReceive.this.mEtKeyword != null) {
                FragmentProjectReceive.this.mKeyword = FragmentProjectReceive.this.mEtKeyword.getText().toString();
                if (TextUtils.isEmpty(FragmentProjectReceive.this.mTapState)) {
                    FragmentProjectReceive.this.httpPostMyTaskReceiveList("S", FragmentProjectReceive.this.mKeyword);
                } else {
                    FragmentProjectReceive.this.httpPostMyTaskReceiveList(FragmentProjectReceive.this.mTapState, FragmentProjectReceive.this.mKeyword);
                }
            }
        }
    };
    HashMap<Integer, String> runTimer = new HashMap<>();

    public static final FragmentProjectReceive newInstance(int i, String str, BeanMain.Project_List project_List) {
        FragmentProjectReceive fragmentProjectReceive = new FragmentProjectReceive();
        fragmentProjectReceive.mPSEQ = i;
        fragmentProjectReceive.projectName = str;
        fragmentProjectReceive.mMainList = project_List;
        return fragmentProjectReceive;
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(args_title, this.projectName);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.support.v4.app.Fragment
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        setupSavedViews(bundle);
    }

    private void setupSavedViews(Bundle bundle) {
        if (bundle != null) {
            this.projectName = bundle.getString(args_title);
            setToolbar();
        }
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
        initAdapter();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // kr.timehub.beplan.fragment.BaseFragmentProject
    public void onUpdatedTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.projectName = str;
        }
        setToolbar();
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentProjectReceive.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!TextUtils.isEmpty(FragmentProjectReceive.this.mTapState)) {
                    FragmentProjectReceive.this.keywordHandler.removeMessages(0);
                    FragmentProjectReceive.this.keywordHandler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mTapList = null;
        this.mTapState = null;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z) {
        super.onPageChanged(viewPager, z);
        if (z) {
            if (TextUtils.isEmpty(this.mTapState)) {
                httpPostMyTaskReceiveList("S", this.mKeyword);
            } else {
                httpPostMyTaskReceiveList(this.mTapState, this.mKeyword);
            }
            setToolbar();
        }
    }

    private void setToolbar() {
        BaseMainToolBar baseMainToolBar;
        if ((getActivity() instanceof ActivityMain) && (baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar()) != null) {
            baseMainToolBar.setTitle(getProjectName());
        }
    }

    public void httpPostMyTaskReceiveList(String str, String str2) {
        new BeplanOkHttpClient().NewMyTaskReceiveList(getContext(), 3, String.valueOf(this.mPSEQ), str, str2, this);
    }

    private void setTaskReceiveList(BeanTaskReceiveList beanTaskReceiveList) {
        this.mAdapter.clear();
        if (beanTaskReceiveList.getCategoryList() != null) {
            for (BeanTaskReceiveList.CategoryListBean categoryListBean : beanTaskReceiveList.getCategoryList()) {
                this.mAdapter.add(categoryListBean);
                if (categoryListBean.getTaskList() != null) {
                    for (BeanTaskReceiveList.CategoryListBean.TaskList taskList : categoryListBean.getTaskList()) {
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
            this.mTvDefault.setText("받은 요청이 없습니다.");
        }
        DropDownSet(beanTaskReceiveList.getTapList());
    }

    private void DropDownSet(final List<BeanTaskReceiveList.TapList> list) {
        if (this.mTapList == null || this.mTapList.size() < 1) {
            this.mTapList = list;
            ArrayList arrayList = new ArrayList();
            for (BeanTaskReceiveList.TapList tapList : this.mTapList) {
                arrayList.add(tapList.TapName);
            }
            this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_bottom_view, arrayList));
            if (TextUtils.isEmpty(this.mTapState)) {
                this.mSpDropdown.setSelection(((ArrayAdapter) this.mSpDropdown.getAdapter()).getPosition("대기중"));
            }
            this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectReceive.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i) instanceof String) {
                        String str = (String) adapterView.getItemAtPosition(i);
                        if (FragmentProjectReceive.this.mAdapter != null && FragmentProjectReceive.this.beanTaskReceiveList != null) {
                            if (!TextUtils.isEmpty(FragmentProjectReceive.this.mTapState)) {
                                FragmentProjectReceive.this.httpPostMyTaskReceiveList(((BeanTaskReceiveList.TapList) list.get(i)).TapCode, FragmentProjectReceive.this.mKeyword);
                            }
                            FragmentProjectReceive.this.mTapState = ((BeanTaskReceiveList.TapList) list.get(i)).TapCode;
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
        final BeanTaskReceiveList.CategoryListBean.TaskList taskList = (BeanTaskReceiveList.CategoryListBean.TaskList) this.mAdapter.get(i);
        ((ImageView) baseViewHolder.getView(R.id.iv_icn_check)).setImageResource(R.drawable.icn_todo_recieve);
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
            } else if (this.mAdapter.get(i + 1) instanceof BeanTaskReceiveList.CategoryListBean) {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
            } else {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_middle);
            }
            boolean z = this.mAdapter.get(i - 1) instanceof String;
        }
        String taskTitle = taskList.getTaskTitle();
        String makeName = taskList.getMakeName();
        baseViewHolder.setText(R.id.tv_business, taskTitle);
        baseViewHolder.setText(R.id.tv_name, makeName);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_me_button);
        Glide.with(getContext()).asBitmap().load(taskList.getMakeProfileImgUrl()).apply(new RequestOptions().placeholder(R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((ImageView) baseViewHolder.getView(R.id.iv_photo));
        ((TextView) baseViewHolder.getView(R.id.tv_finish_date)).setVisibility(8);
        ((TextView) baseViewHolder.getView(R.id.tv_date_middle)).setVisibility(8);
        baseViewHolder.setText(R.id.tv_start_date, new SimpleDateFormat("yy. MM. dd").format(Utils.ConvertDate(taskList.getRegDate())));
        baseViewHolder.setText(R.id.tv_business_state, taskList.getStringTaskState());
        imageView.setVisibility(8);
        imageView.setVisibility(8);
        baseViewHolder.getView(R.id.ll_business_content).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectReceive.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (taskList.getIsRequestOrTask() == 1) {
                    FragmentTransaction beginTransaction = FragmentProjectReceive.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    beginTransaction.addToBackStack(null);
                    beginTransaction.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    beginTransaction.replace(R.id.base_container, FragmentTaskDetail.newInstance(FragmentProjectReceive.this.mPSEQ, taskList.CGSEQ, taskList.getTSEQ(), FragmentProjectReceive.this.projectName));
                    beginTransaction.commit();
                } else if (taskList.getIsRequestOrTask() == 2) {
                    FragmentTransaction beginTransaction2 = FragmentProjectReceive.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    beginTransaction2.addToBackStack(null);
                    beginTransaction2.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    beginTransaction2.replace(R.id.base_container, FragmentRequestDetail.newInstance(FragmentRequestDetail.REQUEST_TYPE_RECEIVE, FragmentProjectReceive.this.mPSEQ, taskList.CGSEQ, taskList.getTSEQ(), taskList.getTaskState(), taskList.CategoryTitle, FragmentProjectReceive.this.projectName, String.valueOf(taskList.getMaKeID()), taskList.getMakeName(), taskList.getMakeProfileImgUrl()));
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
        baseViewHolder.setText(R.id.tv_business_title_name, ((BeanTaskReceiveList.CategoryListBean) this.mAdapter.get(i)).getCategoryTitle());
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
        if (this.mAdapter.get(i) instanceof BeanTaskReceiveList.CategoryListBean) {
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
            if (intExtra == 3) {
                this.beanTaskReceiveList = (BeanTaskReceiveList) gson.fromJson(str, (Class<Object>) BeanTaskReceiveList.class);
                if (TextUtils.equals(this.beanTaskReceiveList.getRtnKey(), "DAOK") || TextUtils.equals(this.beanTaskReceiveList.getRtnKey(), "CMOK") || TextUtils.equals(this.beanTaskReceiveList.getRtnKey(), Comm_RtnKey.DANO)) {
                    BaseMainToolBar baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar();
                    String projectTitle = this.beanTaskReceiveList.getProjectTitle();
                    if (!TextUtils.isEmpty(projectTitle)) {
                        setProjectName(projectTitle);
                        setToolbar();
                    }
                    this.isWrite = this.beanTaskReceiveList.isIsWrite();
                    if (baseMainToolBar != null) {
                        if (this.isWrite) {
                            baseMainToolBar.setToolbarVisibleState(true, false, true, true);
                        } else {
                            baseMainToolBar.setToolbarVisibleState(true, false, false, true);
                        }
                    }
                    setTaskReceiveList(this.beanTaskReceiveList);
                    return;
                }
                this.mAdapter.clear();
                if (!TextUtils.equals(Comm_RtnKey.DANO, this.beanTaskReceiveList.getRtnKey())) {
                    Toast.makeText(getContext(), this.beanTaskReceiveList.getRtnValue(), 0).show();
                    return;
                }
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }
}
