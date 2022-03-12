package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.objects.BaseMoveRecyclerViewAdapter;
import kr.timehub.beplan.base.objects.SwipeHelper2;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.dialog.DialogEditCommon;
import kr.timehub.beplan.dialog.DialogPutInTemplate;
import kr.timehub.beplan.entry.BeanPutInTemplateGetList;
import kr.timehub.beplan.entry.database.DbStopWatch;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.entry.plugin.BeanProjectList;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentProjectMy extends BaseFragmentProject implements BaseRecyclerViewAdapterInterface {
    private static final int REQ_PUT_IN_CATEGORY = 931;
    private static final int REQ_PUT_IN_TEMPLATE_GET_LIST = 699;
    private static final String args_title = "args_title";
    private ActivityMain activityMain;
    BaseMoveRecyclerViewAdapter mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private BeanProjectList mBeanProjectList;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    private ItemTouchHelper mItemTouchHelper;
    @BindView(R.id.ll_list)
    LinearLayout mLlList;
    private BeanMain.Project_List mMainList;
    private String mProjectTitle;
    Realm mRealm;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    private List<BeanProjectList.NewTapListBean> mTapList;
    private String mTapState;
    @BindView(R.id.tv_list)
    TextView mTvList;
    Unbinder unbinder;
    private final int KEYWORD_DELAY = 1000;
    private boolean isMoving = false;
    private int mPSEQ = -1;
    private String mKeyword = "";
    private int mSelectCGSEQ = -1;
    private boolean isWrite = false;
    private Handler keywordHandler = new Handler() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentProjectMy.this.mEtKeyword != null) {
                FragmentProjectMy.this.mKeyword = FragmentProjectMy.this.mEtKeyword.getText().toString();
                FragmentProjectMy.this.httpPostProjectList(FragmentProjectMy.this.mKeyword, FragmentProjectMy.this.mTapState);
            }
        }
    };

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_main_categorys, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        this.mRealm = Realm.getDefaultInstance();
        setRetainInstance(true);
        setupSavedViews(bundle);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.activityMain = (ActivityMain) getContext();
        initAdapter();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        setToolbar();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        setToolbar();
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mTapList = null;
        this.mTapState = null;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mRealm != null && !this.mRealm.isClosed()) {
            this.mRealm.close();
        }
    }

    @Override // kr.timehub.beplan.fragment.BaseFragmentProject
    public void onUpdatedTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mProjectTitle = str;
        }
        setToolbar();
    }

    public static final FragmentProjectMy newInstance(int i, String str, BeanMain.Project_List project_List) {
        FragmentProjectMy fragmentProjectMy = new FragmentProjectMy();
        fragmentProjectMy.mPSEQ = i;
        fragmentProjectMy.mProjectTitle = str;
        fragmentProjectMy.mMainList = project_List;
        return fragmentProjectMy;
    }

    private void setupSavedViews(Bundle bundle) {
        if (bundle != null) {
            this.mProjectTitle = bundle.getString(args_title);
            setToolbar();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(args_title, this.mProjectTitle);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z) {
        super.onPageChanged(viewPager, z);
        if (z) {
            httpPostProjectList(this.mKeyword, this.mTapState);
            setToolbar();
        }
    }

    private void setToolbar() {
        BaseMainToolBar baseMainToolBar;
        if ((getActivity() instanceof ActivityMain) && (baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar()) != null) {
            baseMainToolBar.setTitle(this.mProjectTitle);
        }
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!TextUtils.isEmpty(FragmentProjectMy.this.mTapState)) {
                    FragmentProjectMy.this.keywordHandler.removeMessages(0);
                    FragmentProjectMy.this.keywordHandler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    public void httpPostProjectList(String str, String str2) {
        new BeplanOkHttpClient().MyProjectList(getContext(), 2, this.mPSEQ < 0 ? "" : String.valueOf(this.mPSEQ), str, str2, this);
    }

    @Nullable
    public void httpPostUptTaskStateSuccess(@Nullable String str) {
        new BeplanOkHttpClient().UptTaskStateSuccess(getContext(), 6, str, this);
    }

    public void httpPostModifyCategoryTitle(String str, String str2) {
        new BeplanOkHttpClient().ModifyCategoryTitle(getContext(), 8, str, str2, this);
    }

    public void httpPutPutInTemplateGetList() {
        new BeplanOkHttpClient().PutInTemplateGetList(getContext(), REQ_PUT_IN_TEMPLATE_GET_LIST, this.mPSEQ == -1 ? "" : String.valueOf(this.mPSEQ), this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "", this);
    }

    public void httpPostPutInTemplateCategory(String str, String str2, String str3) {
        new BeplanOkHttpClient().PutInTemplateCategory(getContext(), REQ_PUT_IN_CATEGORY, str, str2, str3, this);
    }

    private void setProjectList(BeanProjectList beanProjectList) {
        if (beanProjectList != null) {
            if (beanProjectList.getCategoryList() != null) {
                for (BeanProjectList.CategoryListBean categoryListBean : beanProjectList.getCategoryList()) {
                    this.mAdapter.add(categoryListBean);
                    for (BeanProjectList.CategoryListBean.TaskListBean taskListBean : categoryListBean.getTaskList()) {
                        this.mAdapter.add(taskListBean);
                    }
                }
            }
            this.mAdapter.notifyDataSetChanged();
            DropDownSet(beanProjectList.getNewTapList());
        }
    }

    private void DropDownSet(final List<BeanProjectList.NewTapListBean> list) {
        if (this.mTapList == null || this.mTapList.size() < 1) {
            this.mTapList = list;
            ArrayList arrayList = new ArrayList();
            for (BeanProjectList.NewTapListBean newTapListBean : this.mTapList) {
                arrayList.add(newTapListBean.getTapName());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), (int) R.layout.listitem_bottom_view, arrayList);
            if (this.mSpDropdown != null) {
                this.mSpDropdown.setAdapter((SpinnerAdapter) arrayAdapter);
                if (TextUtils.isEmpty(this.mTapState)) {
                    BeanProjectList.NewTapListBean defaultTap = this.mBeanProjectList.getDefaultTap();
                    this.mTapState = defaultTap.getTapCode();
                    this.mSpDropdown.setSelection(((ArrayAdapter) this.mSpDropdown.getAdapter()).getPosition(defaultTap.getTapName()));
                }
                this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.3
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (adapterView.getItemAtPosition(i) instanceof String) {
                            String str = (String) adapterView.getItemAtPosition(i);
                            if (FragmentProjectMy.this.mAdapter != null && FragmentProjectMy.this.mBeanProjectList != null) {
                                if (!TextUtils.isEmpty(FragmentProjectMy.this.mTapState)) {
                                    FragmentProjectMy.this.httpPostProjectList(FragmentProjectMy.this.mKeyword, ((BeanProjectList.NewTapListBean) list.get(i)).getTapCode());
                                }
                                FragmentProjectMy.this.mTapState = ((BeanProjectList.NewTapListBean) list.get(i)).getTapCode();
                            }
                        }
                    }
                });
            }
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseMoveRecyclerViewAdapter(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
        new BaseItemDecoration(0).setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
        new SwipeHelper2((ActivityMain) getContext(), this.mBaseRv, this.mAdapter) { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.4
            @Override // kr.timehub.beplan.base.objects.SwipeHelper2
            public SwipeHelper2.UnderlayButton instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, SwipeHelper2.UnderlayButton underlayButton) {
                if (!(FragmentProjectMy.this.mAdapter.get(viewHolder.getAdapterPosition()) instanceof BeanProjectList.CategoryListBean.TaskListBean)) {
                    return underlayButton;
                }
                final BeanProjectList.CategoryListBean.TaskListBean taskListBean = (BeanProjectList.CategoryListBean.TaskListBean) FragmentProjectMy.this.mAdapter.get(viewHolder.getAdapterPosition());
                String str = "시작";
                final RealmResults findAllSorted = FragmentProjectMy.this.mRealm.where(DbStopWatch.class).equalTo("PSEQ", Integer.valueOf(FragmentProjectMy.this.mPSEQ)).equalTo("CGSEQ", Integer.valueOf(taskListBean.getCGSEQ())).equalTo("TSEQ", Integer.valueOf(taskListBean.getTSEQ())).findAllSorted("startTime", Sort.DESCENDING);
                if (findAllSorted.size() > 0) {
                    Iterator it = findAllSorted.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (((DbStopWatch) it.next()).getEll() < 0) {
                            str = "종료";
                            break;
                        } else {
                            str = "시작";
                        }
                    }
                } else {
                    str = "시작";
                }
                return new SwipeHelper2.UnderlayButton(FragmentProjectMy.this.getContext(), str, 0, Color.parseColor("#A1B1C5"), new SwipeHelper2.UnderlayButtonClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.4.1
                    @Override // kr.timehub.beplan.base.objects.SwipeHelper2.UnderlayButtonClickListener
                    public void onClick(int i) {
                        boolean z;
                        if (findAllSorted.size() > 0) {
                            Iterator it2 = findAllSorted.iterator();
                            while (true) {
                                z = false;
                                if (it2.hasNext()) {
                                    if (((DbStopWatch) it2.next()).getEll() < 0) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                            if (z) {
                                FragmentProjectMy.this.activityMain.stopTimer();
                            } else {
                                FragmentProjectMy.this.activityMain.stopTimer();
                                FragmentProjectMy.this.activityMain.startTimer(FragmentProjectMy.this.mPSEQ, taskListBean.getCGSEQ(), taskListBean.getTSEQ(), FragmentProjectMy.this.mProjectTitle, taskListBean.getCateGoryTitle(), taskListBean.getTaskTitle(), 0L);
                            }
                        } else {
                            FragmentProjectMy.this.activityMain.stopTimer();
                            FragmentProjectMy.this.activityMain.startTimer(FragmentProjectMy.this.mPSEQ, taskListBean.getCGSEQ(), taskListBean.getTSEQ(), FragmentProjectMy.this.mProjectTitle, taskListBean.getCateGoryTitle(), taskListBean.getTaskTitle(), 0L);
                        }
                        FragmentProjectMy.this.mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onListItemViewHolder(com.naver.temy123.baseproject.base.Widgets.BaseViewHolder r13, int r14) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kr.timehub.beplan.fragment.FragmentProjectMy.onListItemViewHolder(com.naver.temy123.baseproject.base.Widgets.BaseViewHolder, int):void");
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
        final BeanProjectList.CategoryListBean categoryListBean = (BeanProjectList.CategoryListBean) this.mAdapter.get(i);
        baseViewHolder.setText(R.id.tv_business_title_name, categoryListBean.getCateGoryTitle());
        if (!this.isWrite) {
            baseViewHolder.getView(R.id.iv_add).setVisibility(8);
        } else {
            baseViewHolder.getView(R.id.iv_add).setVisibility(0);
        }
        baseViewHolder.getView(R.id.iv_sell).setVisibility(8);
        baseViewHolder.getView(R.id.iv_add).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTransaction beginTransaction = FragmentProjectMy.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                beginTransaction.addToBackStack(null);
                beginTransaction.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                beginTransaction.replace(R.id.base_container, FragmentNewTask.newInstance(FragmentProjectMy.this.mPSEQ, categoryListBean.getCGSEQ(), categoryListBean.getCateGoryTitle()));
                beginTransaction.commit();
            }
        });
        ((ImageView) baseViewHolder.getView(R.id.iv_more)).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(FragmentProjectMy.this.getContext(), view);
                popupMenu.inflate(R.menu.popup_menu);
                if (categoryListBean.isIsModify()) {
                    popupMenu.getMenu().add("이름 변경");
                }
                if (categoryListBean.isIsRunnerModify()) {
                    popupMenu.getMenu().add("담당 수정");
                }
                if (categoryListBean.isIsDelete()) {
                    popupMenu.getMenu().add("삭제");
                }
                popupMenu.getMenu().add("담기");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.9.1
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        char c;
                        String charSequence = menuItem.getTitle().toString();
                        switch (charSequence.hashCode()) {
                            case 1464764:
                                if (charSequence.equals("담기")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1580303:
                                if (charSequence.equals("삭제")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1722580:
                                if (charSequence.equals("판매")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 725227640:
                                if (charSequence.equals("담당 수정")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1326749069:
                                if (charSequence.equals("이름 변경")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 1:
                                int i2 = 0;
                                for (BeanProjectList.CategoryListBean.TaskListBean taskListBean : categoryListBean.getTaskList()) {
                                    if (taskListBean.isIsModify()) {
                                        i2++;
                                    }
                                }
                                if (i2 > 0) {
                                    FragmentTransaction beginTransaction = FragmentProjectMy.this.getParentFragment().getActivity().getSupportFragmentManager().beginTransaction();
                                    beginTransaction.addToBackStack(null);
                                    beginTransaction.setCustomAnimations(R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                    beginTransaction.replace(R.id.base_container, FragmentEditCategoryRep.newInstance(categoryListBean.getTaskList(), FragmentProjectMy.this.mMainList.PSEQ));
                                    beginTransaction.commit();
                                    break;
                                } else {
                                    Toast.makeText(FragmentProjectMy.this.getContext(), FragmentProjectMy.this.getString(R.string.error_no_edit_task), 0).show();
                                    break;
                                }
                            case 2:
                                FragmentProjectMy.this.showDeleteCategoryDialog(String.valueOf(categoryListBean.getCGSEQ()));
                                break;
                            case 3:
                                FragmentProjectMy.this.showEditCategoryTitle(String.valueOf(categoryListBean.getCGSEQ()), categoryListBean.getCateGoryTitle());
                                break;
                            case 4:
                                FragmentProjectMy.this.mSelectCGSEQ = categoryListBean.getCGSEQ();
                                FragmentProjectMy.this.httpPutPutInTemplateGetList();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void showDeleteCategoryDialog(final String str) {
        DialogCommon.newInstance(getContext(), getString(R.string.dialog_delete_category_title), getString(R.string.dialog_delete_category_contents), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.10
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                new BeplanOkHttpClient().DeleteCategory(FragmentProjectMy.this.getContext(), 7, str, FragmentProjectMy.this);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentProjectMy.this.getString(R.string.btn_yes));
                dialogCommon.getmBtnNegative().setText(FragmentProjectMy.this.getString(R.string.btn_no));
            }
        }).show();
    }

    public void showEditCategoryTitle(final String str, String str2) {
        DialogEditCommon.newInstance(getContext(), getString(R.string.dialog_edit_category_title), str2, getString(R.string.dialog_edit_category_contents_hint), new DialogEditCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.11
            @Override // kr.timehub.beplan.dialog.DialogEditCommon.DialogCommonListener
            public void clickClose(DialogEditCommon dialogEditCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogEditCommon.DialogCommonListener
            public void onPositive(DialogEditCommon dialogEditCommon, Button button, String str3) {
                FragmentProjectMy.this.httpPostModifyCategoryTitle(str, str3);
                dialogEditCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogEditCommon.DialogCommonListener
            public void onNegative(DialogEditCommon dialogEditCommon, Button button) {
                dialogEditCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogEditCommon.DialogCommonListener
            public void onCreated(DialogEditCommon dialogEditCommon) {
                dialogEditCommon.getmBtnPositive().setText(FragmentProjectMy.this.getString(R.string.btn_ok));
                dialogEditCommon.getmBtnNegative().setVisibility(8);
            }
        }).show();
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (this.mAdapter.get(i) instanceof BeanProjectList.CategoryListBean) {
            BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
            return 1;
        } else if (!(this.mAdapter.get(i) instanceof BeanProjectList.CategoryListBean.TaskListBean)) {
            return -1;
        } else {
            BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter2 = this.mAdapter;
            return 0;
        }
    }

    public String getProjectTitle() {
        return this.mProjectTitle;
    }

    public void setProjectTitle(String str) {
        this.mProjectTitle = str;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 2) {
                this.mBeanProjectList = (BeanProjectList) gson.fromJson(str, (Class<Object>) BeanProjectList.class);
                if (this.mBeanProjectList != null) {
                    BaseMainToolBar baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar();
                    String projectName = this.mBeanProjectList.getProjectName();
                    if (!TextUtils.isEmpty(projectName)) {
                        setProjectTitle(projectName);
                        setToolbar();
                    }
                    this.isWrite = this.mBeanProjectList.isIsWrite();
                    if (baseMainToolBar != null) {
                        if (this.isWrite) {
                            baseMainToolBar.setToolbarVisibleState(true, false, true, true);
                        } else {
                            baseMainToolBar.setToolbarVisibleState(true, false, false, true);
                        }
                    }
                    this.mAdapter.clear();
                    if (!TextUtils.equals(this.mBeanProjectList.getRtnKey(), "CMOK") && !TextUtils.equals(this.mBeanProjectList.getRtnKey(), "DAOK")) {
                        Toast.makeText(getContext(), this.mBeanProjectList.getRtnValue(), 0).show();
                    } else if (this.mBeanProjectList.getNewTapList() != null && this.mBeanProjectList.getNewTapList().size() > 0) {
                        setProjectList(this.mBeanProjectList);
                    }
                }
            } else if (intExtra == 6) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                    httpPostProjectList(this.mKeyword, this.mTapState);
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
            } else if (intExtra == 7) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(getContext(), "성공적으로 삭제되었습니다", 0).show();
                    httpPostProjectList(this.mKeyword, this.mTapState);
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == 8) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(getContext(), "성공적으로 변경되었습니다", 0).show();
                    httpPostProjectList(this.mKeyword, this.mTapState);
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == REQ_PUT_IN_TEMPLATE_GET_LIST) {
                BeanPutInTemplateGetList beanPutInTemplateGetList = (BeanPutInTemplateGetList) gson.fromJson(str, (Class<Object>) BeanPutInTemplateGetList.class);
                if (TextUtils.equals(beanPutInTemplateGetList.RtnKey, "DAOK")) {
                    bindCategoryList(beanPutInTemplateGetList);
                } else if (TextUtils.equals(beanPutInTemplateGetList.RtnKey, Comm_RtnKey.DANO)) {
                    Toast.makeText(getActivity().getApplicationContext(), (int) R.string.alert_error_templete_no_have_list, 0).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), beanPutInTemplateGetList.RtnValue, 0).show();
                }
            } else if (intExtra == REQ_PUT_IN_CATEGORY) {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                httpPostProjectList(this.mKeyword, this.mTapState);
            }
        } else {
            Toast.makeText(this.activityMain, getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void bindCategoryList(BeanPutInTemplateGetList beanPutInTemplateGetList) {
        ArrayList<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> arrayList = new ArrayList<>();
        for (BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle : beanPutInTemplateGetList.TemplateMainCateGoryTitle) {
            arrayList.add(templateMainCateGoryTitle);
        }
        showPutInTemplateDialog(arrayList);
    }

    private void showPutInTemplateDialog(ArrayList<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> arrayList) {
        DialogPutInTemplate.newInstance(getContext(), "그룹 선택", arrayList, new DialogPutInTemplate.IOnDialogPutInTemplateListener() { // from class: kr.timehub.beplan.fragment.FragmentProjectMy.12
            @Override // kr.timehub.beplan.dialog.DialogPutInTemplate.IOnDialogPutInTemplateListener
            public void onSubmit(DialogPutInTemplate dialogPutInTemplate, BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle, String str) {
                if (templateMainCateGoryTitle != null) {
                    FragmentProjectMy.this.httpPostPutInTemplateCategory(String.valueOf(templateMainCateGoryTitle.MTCSEQ), templateMainCateGoryTitle.MainCategoryTitle, String.valueOf(FragmentProjectMy.this.mSelectCGSEQ));
                } else {
                    FragmentProjectMy.this.httpPostPutInTemplateCategory(null, str, String.valueOf(FragmentProjectMy.this.mSelectCGSEQ));
                }
                dialogPutInTemplate.dismiss();
            }
        }).show();
    }
}
