package kr.timehub.beplan.fragment.SideForm.formShop;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityFormTaskDetail;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.BeanRank;
import kr.timehub.beplan.entry.BeanRankItem;
import kr.timehub.beplan.entry.BeanShopCategoryDetail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentFormShopCategoryDetail extends BaseListFragment {
    private static final int REQ_BUY_CATEGORY = 174;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private String mCGTitle;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.ll_use)
    LinearLayout mLlUse;
    private int mSMCSEQ;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_use)
    TextView mTvUse;
    Unbinder unbinder;
    private final int TYPE_LIST = 1;
    private final int TYPE_HEADER = 0;
    private final int TYPE_LISTITEM = 1;
    private final int REQ_CATEGORY_DETAIL = 34;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentFormShopCategoryDetail.this.handler != null) {
                FragmentFormShopCategoryDetail.this.httpPostCategoryDetail();
            }
        }
    };
    private HashMap<Integer, String> selectMap = new HashMap<>();

    public static FragmentFormShopCategoryDetail newInstance(String str, String str2, int i) {
        FragmentFormShopCategoryDetail fragmentFormShopCategoryDetail = new FragmentFormShopCategoryDetail();
        fragmentFormShopCategoryDetail.mTitle = str;
        fragmentFormShopCategoryDetail.mCGTitle = str2;
        fragmentFormShopCategoryDetail.mSMCSEQ = i;
        return fragmentFormShopCategoryDetail;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_form_project_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        listViewSet();
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
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (i != 0 || i2 != 0 || i3 != 0) {
                    FragmentFormShopCategoryDetail.this.handler.removeMessages(0);
                    FragmentFormShopCategoryDetail.this.handler.sendEmptyMessageDelayed(0, 1000L);
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

    /* renamed from: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail$3  reason: invalid class name */
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
            return BaseViewHolder.newInstance(FragmentFormShopCategoryDetail.this.getContext(), R.layout.listitem_form_detail, viewGroup);
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            onBindList((BaseViewHolder) viewHolder, i);
        }

        private void onBindList(BaseViewHolder baseViewHolder, final int i) {
            final BeanShopCategoryDetail.CategoryListBean categoryListBean = (BeanShopCategoryDetail.CategoryListBean) FragmentFormShopCategoryDetail.this.get(i);
            baseViewHolder.setText(R.id.tv_project, categoryListBean.getCateGoryTitle());
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_tab);
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_more);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.list_rv);
            LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_use);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_use);
            baseViewHolder.getView(R.id.ll_version_set).setVisibility(8);
            if (TextUtils.equals((CharSequence) FragmentFormShopCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                imageView.setImageResource(R.drawable.btn_tab_open);
                linearLayout.setVisibility(0);
            } else {
                imageView.setImageResource(R.drawable.btn_tab_close);
                linearLayout.setVisibility(8);
            }
            if (TextUtils.equals(categoryListBean.getBoughtYn().toLowerCase(), "y")) {
                linearLayout3.setBackgroundResource(R.drawable.btn_gray_03);
                imageView2.setVisibility(8);
                baseViewHolder.setText(R.id.tv_use, "이미 구매한 규격입니다");
            } else {
                linearLayout3.setBackgroundResource(R.drawable.btn_grdt_07);
                imageView2.setVisibility(0);
                baseViewHolder.setText(R.id.tv_use, "규격 구매하기 (무료)");
                linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FragmentFormShopCategoryDetail.this.showBuyCategoryDialog(categoryListBean.getCateGoryTitle(), categoryListBean.getAmount(), categoryListBean.getSCGSEQ());
                    }
                });
            }
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.3.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.equals((CharSequence) FragmentFormShopCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                        FragmentFormShopCategoryDetail.this.selectMap.put(Integer.valueOf(i), null);
                    } else {
                        FragmentFormShopCategoryDetail.this.selectMap.put(Integer.valueOf(i), "select");
                    }
                    FragmentFormShopCategoryDetail.this.notifyDataSetChanged();
                }
            });
            ((ImageView) baseViewHolder.getView(R.id.iv_more)).setVisibility(8);
            final BaseRecyclerViewAdapter2 baseRecyclerViewAdapter2 = new BaseRecyclerViewAdapter2(FragmentFormShopCategoryDetail.this.getContext());
            baseRecyclerViewAdapter2.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.3.3
                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public int getItemViewType(int i2) {
                    return 1;
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return BaseViewHolder.newInstance(FragmentFormShopCategoryDetail.this.getContext(), R.layout.listitem_rank_item, viewGroup);
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    BaseViewHolder baseViewHolder2 = (BaseViewHolder) viewHolder;
                    final BeanShopCategoryDetail.CategoryListBean.TaskListBean taskListBean = (BeanShopCategoryDetail.CategoryListBean.TaskListBean) baseRecyclerViewAdapter2.get(i2);
                    baseViewHolder2.setText(R.id.tv_project, taskListBean.getTaskTitle());
                    baseViewHolder2.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.3.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Intent intent = new Intent(FragmentFormShopCategoryDetail.this.getContext(), ActivityFormTaskDetail.class);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TSEQ, taskListBean.getSTSEQ());
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TITLE, FragmentFormShopCategoryDetail.this.mTitle);
                            FragmentFormShopCategoryDetail.this.startActivity(intent);
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
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentFormShopCategoryDetail.this.getContext()));
            recyclerView.setAdapter(baseRecyclerViewAdapter2);
            baseRecyclerViewAdapter2.clear();
            for (BeanShopCategoryDetail.CategoryListBean.TaskListBean taskListBean : categoryListBean.getTaskList()) {
                baseRecyclerViewAdapter2.add(taskListBean);
                baseRecyclerViewAdapter2.notifyDataSetChanged();
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentFormShopCategoryDetail.this.size();
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
    public void showBuyCategoryDialog(String str, int i, final int i2) {
        DialogCommon.newInstance(getContext(), getString(R.string.btn_ok), String.format("[%s] 규격을 %,d원에 구매하시겠습니까?", str, Integer.valueOf(i)), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail.4
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentFormShopCategoryDetail.this.httpPostBuyCategory(i2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentFormShopCategoryDetail.this.getString(R.string.btn_ok));
                dialogCommon.getmBtnNegative().setText(FragmentFormShopCategoryDetail.this.getString(R.string.btn_cancel));
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostCategoryDetail() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.ShopCategoryDetail(getContext(), 34, String.valueOf(this.mSMCSEQ), str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostBuyCategory(int i) {
        new BeplanOkHttpClient().ShopBuyCategory(getContext(), REQ_BUY_CATEGORY, String.valueOf(i), this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (!TextUtils.equals(beanCommon.RtnKey, "DAOK") && !TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == 34) {
                setCategoryDetail((BeanShopCategoryDetail) gson.fromJson(str, (Class<Object>) BeanShopCategoryDetail.class));
            } else if (intExtra == REQ_BUY_CATEGORY) {
                ((ActivityMain) getContext()).replaceMySubscriptionCategory();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setCategoryDetail(BeanShopCategoryDetail beanShopCategoryDetail) {
        clear();
        for (BeanShopCategoryDetail.CategoryListBean categoryListBean : beanShopCategoryDetail.getCategoryList()) {
            add(categoryListBean);
        }
    }
}
