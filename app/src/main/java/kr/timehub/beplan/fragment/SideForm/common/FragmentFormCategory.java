package kr.timehub.beplan.fragment.SideForm.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.objects.BaseViewHolder;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanMyFormFormList;
import kr.timehub.beplan.entry.BeanShopIndex;
import kr.timehub.beplan.entry.BeanSubscriptionCategoryList;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopCategoryDetail;
import kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail;
import kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentFormCategory extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    private Handler callServerHandler;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    private String mTitle;
    private Spinner parentSpinner;
    Unbinder unbinder;
    private final int REQ_MY_FORM_CATEGORY_LIST = 11;
    private final int REQ_MY_SUBSCRIPTION_CATEGORY_LIST = 21;
    private final int REQ_FORM_SHOP_INDEX = 31;
    private boolean mIsShow = false;
    private boolean isCategory = false;

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return 0;
    }

    public static FragmentFormCategory newInstance(String str, Spinner spinner, boolean z) {
        FragmentFormCategory fragmentFormCategory = new FragmentFormCategory();
        fragmentFormCategory.mTitle = str;
        fragmentFormCategory.parentSpinner = spinner;
        fragmentFormCategory.isCategory = z;
        return fragmentFormCategory;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_form_container, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        this.mBaseRv.setPadding(0, 0, (int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()), 0);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initAdapter();
        onAction();
        if (this.isCategory) {
            this.mIsShow = this.isCategory;
            if (this.parentSpinner != null) {
                this.parentSpinner.setClickable(false);
                this.parentSpinner.setEnabled(false);
            }
            this.isCategory = false;
        }
    }

    @Override // kr.timehub.beplan.base.objects.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z, Spinner spinner) {
        super.onPageChanged(viewPager, z, spinner);
        this.mIsShow = z;
        if (z) {
            callServer();
        }
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentFormCategory.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (FragmentFormCategory.this.callServerHandler != null) {
                    FragmentFormCategory.this.callServerHandler.removeMessages(0);
                    FragmentFormCategory.this.callServerHandler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
        this.callServerHandler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentFormCategory.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (FragmentFormCategory.this.callServerHandler != null) {
                    FragmentFormCategory.this.callServer();
                }
            }
        };
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.callServerHandler != null) {
            this.callServerHandler.removeMessages(0);
        }
        this.callServerHandler = null;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.callServerHandler != null) {
            this.callServerHandler.removeMessages(0);
        }
        this.callServerHandler = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callServer() {
        char c;
        String str = this.mTitle;
        int hashCode = str.hashCode();
        if (hashCode == -1119707572) {
            if (str.equals(FragmentForm.FRAGMENT_MY_FORM)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 49598904) {
            if (hashCode == 1369786289 && str.equals(FragmentForm.FRAGMENT_MY_SUBSCRIPTION)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(FragmentForm.FRAGMENT_FORM_SHOP)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                httpPostMyFormFormList();
                return;
            case 1:
                httpPostSubscriptionCategoryList();
                return;
            case 2:
                httpPostShopIndex();
                return;
            default:
                return;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        activity.setTitle("true</false</" + this.mTitle + "</false</false");
    }

    private void initAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
            if (this.mAdapter.size() < 1) {
                callServer();
            }
        }
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(0);
        baseItemDecoration.setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
        this.mBaseRv.addItemDecoration(baseItemDecoration);
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_form_category, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        char c;
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        String str = this.mTitle;
        int hashCode = str.hashCode();
        if (hashCode == -1119707572) {
            if (str.equals(FragmentForm.FRAGMENT_MY_FORM)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 49598904) {
            if (hashCode == 1369786289 && str.equals(FragmentForm.FRAGMENT_MY_SUBSCRIPTION)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(FragmentForm.FRAGMENT_FORM_SHOP)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                onBindListItemMyForm(baseViewHolder, i);
                return;
            case 1:
                onBindListItemSubscription(baseViewHolder, i);
                return;
            case 2:
                onBindListItemFormShop(baseViewHolder, i);
                return;
            default:
                return;
        }
    }

    private void onBindListItemMyForm(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanMyFormFormList.FormList) {
            final BeanMyFormFormList.FormList formList = (BeanMyFormFormList.FormList) this.mAdapter.get(i);
            ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(formList.MTCateGoryTitle);
            ((TextView) baseViewHolder.getView(R.id.tv_count)).setText(String.format("+ %d", Integer.valueOf(formList.CategoryCnt)));
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentFormCategory.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormCategory.this.replaceFragment(R.id.base_category_container, FragmentMyFormCategoryDetail.newInstance(FragmentFormCategory.this.mTitle, formList.MTCSEQ), true);
                }
            });
        }
    }

    private void onBindListItemSubscription(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanSubscriptionCategoryList.CategoryList) {
            final BeanSubscriptionCategoryList.CategoryList categoryList = (BeanSubscriptionCategoryList.CategoryList) this.mAdapter.get(i);
            ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(categoryList.ShopMainCategoryTitle);
            ((TextView) baseViewHolder.getView(R.id.tv_count)).setText(String.format("+ %d", Integer.valueOf(categoryList.cnt)));
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentFormCategory.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormCategory.this.replaceFragment(R.id.base_category_container, FragmentMySubscriptionCategoryDetail.newInstance(FragmentFormCategory.this.mTitle, categoryList.ShopMainCategoryTitle, categoryList.SMCGSEQ), true);
                }
            });
        }
    }

    private void onBindListItemFormShop(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanShopIndex.ShopMainListBean) {
            final BeanShopIndex.ShopMainListBean shopMainListBean = (BeanShopIndex.ShopMainListBean) this.mAdapter.get(i);
            ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(shopMainListBean.getSMCateGoryTitle());
            ((TextView) baseViewHolder.getView(R.id.tv_count)).setText(String.format("+ %d", Integer.valueOf(shopMainListBean.getCateGoryCount())));
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentFormCategory.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormCategory.this.replaceFragment(R.id.base_category_container, FragmentFormShopCategoryDetail.newInstance(FragmentFormCategory.this.mTitle, shopMainListBean.getSMCateGoryTitle(), shopMainListBean.getSMCSEQ()), true);
                }
            });
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    private void httpPostMyFormFormList() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.MyFormFormList(getContext(), 11, str, this);
    }

    private void httpPostSubscriptionCategoryList() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.SubscriptionCategoryList(getContext(), 21, str, this);
    }

    private void httpPostShopIndex() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.ShopIndex(getContext(), 31, str, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (!this.mIsShow) {
            return;
        }
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                if (intExtra == 11) {
                    setMyForm((BeanMyFormFormList) gson.fromJson(str, (Class<Object>) BeanMyFormFormList.class));
                } else if (intExtra == 21) {
                    setSubscription((BeanSubscriptionCategoryList) gson.fromJson(str, (Class<Object>) BeanSubscriptionCategoryList.class));
                } else if (intExtra == 31) {
                    setFormShop((BeanShopIndex) gson.fromJson(str, (Class<Object>) BeanShopIndex.class));
                }
            } else if (TextUtils.isEmpty(beanCommon.RtnValue)) {
                Toast.makeText(getContext(), "데이터가 없습니다.", 0).show();
            } else {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setMyForm(BeanMyFormFormList beanMyFormFormList) {
        this.mAdapter.clear();
        for (BeanMyFormFormList.FormList formList : beanMyFormFormList.formList) {
            this.mAdapter.add(formList);
        }
    }

    private void setSubscription(BeanSubscriptionCategoryList beanSubscriptionCategoryList) {
        this.mAdapter.clear();
        for (BeanSubscriptionCategoryList.CategoryList categoryList : beanSubscriptionCategoryList.CategoryList) {
            this.mAdapter.add(categoryList);
        }
    }

    private void setFormShop(BeanShopIndex beanShopIndex) {
        this.mAdapter.clear();
        for (BeanShopIndex.ShopMainListBean shopMainListBean : beanShopIndex.getShopMainList()) {
            this.mAdapter.add(shopMainListBean);
        }
    }
}
