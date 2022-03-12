package kr.timehub.beplan.fragment.SideForm.formShop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.util.LinkedHashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanMyFormIndex;
import kr.timehub.beplan.entry.BeanShopProject;
import kr.timehub.beplan.entry.BeanSubScriptionProjectList;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormProjectDetail;
import kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentFormShopProject extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    public static final String ACTION_CODE_PROJECT = "kr.timehub.beplan.FragmentFormProject.DropdwonData";
    public static final String EXTRA_DROPDOWN_SEQ = "EXTRA_DROPDOWN_SEQ";
    private FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener;
    BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    private LinkedHashMap<Integer, String> mMap;
    private String mTitle;
    @BindView(R.id.tv_default)
    TextView mTvDefault;
    private Spinner parentSpinner;
    Unbinder unbinder;
    private final int TYPE_HEADER = 0;
    private final int TYPE_LISTITEM = 1;
    private int DropDownSeq = 0;
    private int buyDropdownSeq = -1;
    private final int REQ_MYFORM_PROJECTLIST = 10;
    private final int REQ_MYFORM_DELETE_PROJECT = 12;
    private final int REQ_SUBSCRIPTION_PROJECTLIST = 20;
    private final int REQ_FORMSHOP_PROJECTLIST = 30;
    private boolean mIsShow = false;
    private boolean viewAlive = true;
    Handler callServerHandler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentFormShopProject.this.callServerHandler != null) {
                FragmentFormShopProject.this.callServer();
            }
        }
    };
    private boolean isFirst = false;
    BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.8
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("EXTRA_DROPDOWN_SEQ", -1);
            if (intExtra != -1) {
                FragmentFormShopProject.this.DropDownSeq = intExtra;
                FragmentFormShopProject.this.callServer();
            }
        }
    };

    public static FragmentFormShopProject newInstance(String str, Spinner spinner, FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener) {
        FragmentFormShopProject fragmentFormShopProject = new FragmentFormShopProject();
        fragmentFormShopProject.mTitle = str;
        fragmentFormShopProject.parentSpinner = spinner;
        fragmentFormShopProject.iOnDropdownSelectListener = iOnDropdownSelectListener;
        return fragmentFormShopProject;
    }

    public static FragmentFormShopProject newInstance(String str, Spinner spinner, int i, FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener) {
        FragmentFormShopProject fragmentFormShopProject = new FragmentFormShopProject();
        fragmentFormShopProject.mTitle = str;
        fragmentFormShopProject.parentSpinner = spinner;
        fragmentFormShopProject.buyDropdownSeq = i;
        fragmentFormShopProject.iOnDropdownSelectListener = iOnDropdownSelectListener;
        return fragmentFormShopProject;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_form_container, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        initAdapter();
        onAction();
        this.mIsShow = true;
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        activity.setTitle("true</false</" + this.mTitle + "</false</false");
        this.viewAlive = true;
        if (this.mReceiver != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("kr.timehub.beplan.FragmentFormProject.DropdwonData");
            getContext().registerReceiver(this.mReceiver, intentFilter);
        }
    }

    @Override // kr.timehub.beplan.base.objects.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z, Spinner spinner) {
        super.onPageChanged(viewPager, z, spinner);
        this.mIsShow = z;
        if (z) {
            callServer();
            if (this.parentSpinner != null) {
                this.parentSpinner.setClickable(true);
                this.parentSpinner.setEnabled(true);
            }
        } else if (this.parentSpinner != null) {
            this.parentSpinner.setClickable(false);
            this.parentSpinner.setEnabled(false);
        }
        if (spinner != null) {
            this.parentSpinner = spinner;
        }
    }

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
                httpPostMyFormIndex();
                return;
            case 1:
                httpPostSubscriptionProjectList();
                return;
            case 2:
                httpPostFormShop();
                return;
            default:
                return;
        }
    }

    private void onAction() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if ((i != 0 || i2 != 0 || i3 != 0) && FragmentFormShopProject.this.callServerHandler != null) {
                    FragmentFormShopProject.this.callServerHandler.removeMessages(0);
                    FragmentFormShopProject.this.callServerHandler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
        this.viewAlive = false;
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.callServerHandler != null) {
            this.callServerHandler.removeMessages(0);
        }
        if (this.mReceiver != null) {
            getContext().unregisterReceiver(this.mReceiver);
        }
    }

    private void initAdapter() {
        this.mBaseRv.setBackgroundColor(getResources().getColor(R.color.formshop_bg));
        if (this.mAdapter == null) {
            this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
            if (this.mAdapter.size() < 1) {
                callServer();
            }
        }
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_formshop, viewGroup);
        }
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_remake_form_shop, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        switch (getItemViewType(i)) {
            case 0:
                onBindHeader(baseViewHolder, i);
                return;
            case 1:
                onBindListItem(baseViewHolder, i);
                return;
            default:
                return;
        }
    }

    private void onBindHeader(BaseViewHolder baseViewHolder, int i) {
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
                onBindHeaderMyForm(baseViewHolder, i);
                return;
            case 1:
                onBindHeaderMySubscription(baseViewHolder, i);
                return;
            case 2:
                onBindHeaderFormShop(baseViewHolder, i);
                return;
            default:
                return;
        }
    }

    private void onBindHeaderMyForm(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanMyFormIndex.FormList) {
            baseViewHolder.setText(R.id.tv_header, ((BeanMyFormIndex.FormList) this.mAdapter.get(i)).MTCateGoryTitle);
        }
    }

    private void onBindHeaderMySubscription(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanSubScriptionProjectList.CategoryList) {
            baseViewHolder.setText(R.id.tv_header, ((BeanSubScriptionProjectList.CategoryList) this.mAdapter.get(i)).ShopMainCategoryTitle);
        }
    }

    private Drawable onBindHeaderFormShop(BaseViewHolder baseViewHolder, final int i) {
        if (!(this.mAdapter.get(i) instanceof BeanShopProject.ShopMainListBean)) {
            return null;
        }
        final BeanShopProject.ShopMainListBean shopMainListBean = (BeanShopProject.ShopMainListBean) this.mAdapter.get(i);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_icon);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
        ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(shopMainListBean.getSMCateGoryTitle());
        int parseColor = Color.parseColor("#" + shopMainListBean.getColorRgb());
        ((LinearLayout) baseViewHolder.getView(R.id.ll_header_bg)).setBackgroundColor(parseColor);
        if (shopMainListBean.isExpend()) {
            Drawable wrap = DrawableCompat.wrap(getResources().getDrawable(R.drawable.btn_reduce_blue));
            DrawableCompat.setTint(wrap, parseColor);
            imageView2.setImageDrawable(wrap);
        } else {
            Drawable wrap2 = DrawableCompat.wrap(getResources().getDrawable(R.drawable.btn_expand_blue));
            DrawableCompat.setTint(wrap2, parseColor);
            imageView2.setImageDrawable(wrap2);
        }
        Glide.with(getContext()).asBitmap().load(shopMainListBean.getBackImgUrl()).into(imageView);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int size = shopMainListBean.getProjectList().size();
                for (int i2 = 1; i2 <= size; i2++) {
                    int i3 = i + i2;
                    if (FragmentFormShopProject.this.mAdapter.get(i3) instanceof BeanShopProject.ShopMainListBean.ProjectListBean) {
                        if (shopMainListBean.isExpend()) {
                            ((BeanShopProject.ShopMainListBean.ProjectListBean) FragmentFormShopProject.this.mAdapter.get(i3)).setExpend(false);
                        } else {
                            ((BeanShopProject.ShopMainListBean.ProjectListBean) FragmentFormShopProject.this.mAdapter.get(i3)).setExpend(true);
                        }
                    }
                }
                if (shopMainListBean.isExpend()) {
                    ((BeanShopProject.ShopMainListBean) FragmentFormShopProject.this.mAdapter.get(i)).setExpend(false);
                } else {
                    ((BeanShopProject.ShopMainListBean) FragmentFormShopProject.this.mAdapter.get(i)).setExpend(true);
                }
                FragmentFormShopProject.this.mAdapter.notifyDataSetChanged();
            }
        });
        return null;
    }

    private void onBindListItem(BaseViewHolder baseViewHolder, int i) {
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
                onBindListItemMyForm(baseViewHolder, i);
                return;
            case 1:
                onBindListItemMySubscription(baseViewHolder, i);
                return;
            case 2:
                onBindListItemFormShop(baseViewHolder, i);
                return;
            default:
                return;
        }
    }

    private void onBindListItemMyForm(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanMyFormIndex.MTProjectList) {
            final BeanMyFormIndex.MTProjectList mTProjectList = (BeanMyFormIndex.MTProjectList) this.mAdapter.get(i);
            baseViewHolder.getView(R.id.iv_more).setVisibility(0);
            baseViewHolder.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(FragmentFormShopProject.this.getContext(), view);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.getMenu().add("삭제");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.4.1
                        @Override // android.widget.PopupMenu.OnMenuItemClickListener
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            String charSequence = menuItem.getTitle().toString();
                            if (((charSequence.hashCode() == 1580303 && charSequence.equals("삭제")) ? (char) 0 : (char) 65535) == 0) {
                                FragmentFormShopProject.this.httpPostMyFormDeleteProject(mTProjectList.MTPSEQ);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormShopProject.this.replaceFragment(R.id.base_project_container, FragmentMyFormProjectDetail.newInstance(FragmentFormShopProject.this.mTitle, mTProjectList.ProjectTitle, mTProjectList.MTPSEQ), true);
                }
            });
            baseViewHolder.setText(R.id.tv_item, mTProjectList.ProjectTitle);
        }
    }

    private void onBindListItemMySubscription(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanSubScriptionProjectList.ProjectList) {
            final BeanSubScriptionProjectList.ProjectList projectList = (BeanSubScriptionProjectList.ProjectList) this.mAdapter.get(i);
            baseViewHolder.getView(R.id.iv_more).setVisibility(8);
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormShopProject.this.replaceFragment(R.id.base_project_container, FragmentMySubscriptionProjectDetail.newInstance(FragmentFormShopProject.this.mTitle, projectList.ShopProjectTitle, projectList.SPSEQ), true);
                }
            });
            baseViewHolder.setText(R.id.tv_item, projectList.ShopProjectTitle);
        }
    }

    private void onBindListItemFormShop(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanShopProject.ShopMainListBean.ProjectListBean) {
            final BeanShopProject.ShopMainListBean.ProjectListBean projectListBean = (BeanShopProject.ShopMainListBean.ProjectListBean) this.mAdapter.get(i);
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_bg);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_icon);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_title);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_project_date);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
            ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_notification);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_project_maker);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_category_size);
            TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_task_size);
            if (projectListBean.getMakeID() == Integer.parseInt(Comm_Prefs.getInstance(getContext()).getUserId())) {
                imageView.setImageResource(R.drawable.img_my);
            } else {
                imageView.setImageResource(R.drawable.img_share);
            }
            textView.setText(projectListBean.getProjectTitle());
            textView2.setVisibility(8);
            imageView2.setVisibility(8);
            imageView3.setVisibility(8);
            textView3.setText(projectListBean.getMakeName());
            textView4.setText(String.valueOf(projectListBean.getCategoryCnt()));
            textView5.setText(String.valueOf(projectListBean.getTaskCnt()));
            if (projectListBean.isExpend()) {
                linearLayout2.setVisibility(0);
                int applyDimension = (int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics());
                if (i == this.mAdapter.size() - 1) {
                    linearLayout.setPadding(applyDimension, applyDimension, applyDimension, applyDimension);
                } else if (this.mAdapter.get(i + 1) instanceof BeanShopProject.ShopMainListBean) {
                    linearLayout.setPadding(applyDimension, applyDimension, applyDimension, applyDimension);
                } else {
                    linearLayout.setPadding(applyDimension, applyDimension, applyDimension, 0);
                }
            } else {
                linearLayout2.setVisibility(8);
                linearLayout.setPadding(0, 0, 0, 0);
            }
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentFormShopProject.this.replaceFragment(R.id.base_project_container, FragmentFormShopProjectDetail.newInstance(FragmentFormShopProject.this.mTitle, projectListBean.getSPSEQ(), projectListBean.getSMCSEQ(), projectListBean.getSMCateGoryTitle(), projectListBean.getProjectTitle()), true);
                }
            });
            baseViewHolder.setText(R.id.tv_item, projectListBean.getProjectTitle());
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
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
                return this.mAdapter.get(i) instanceof BeanMyFormIndex.FormList ? 0 : 1;
            case 1:
                return this.mAdapter.get(i) instanceof BeanSubScriptionProjectList.CategoryList ? 0 : 1;
            case 2:
                return this.mAdapter.get(i) instanceof BeanShopProject.ShopMainListBean ? 0 : 1;
            default:
                return 0;
        }
    }

    private void httpPostMyFormIndex() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.MyFormIndex(getContext(), 10, String.valueOf(this.DropDownSeq), str, this);
    }

    private void httpPostSubscriptionProjectList() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.SubscriptionProjectList(getContext(), 20, String.valueOf(this.DropDownSeq), str, this);
    }

    private void httpPostFormShop() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.ShopProject(getContext(), 30, String.valueOf(this.DropDownSeq), str, this);
    }

    public void httpPostMyFormDeleteProject(int i) {
        new BeplanOkHttpClient().MyFormDeleteProject(getContext(), 12, String.valueOf(i), this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (this.viewAlive && this.mIsShow) {
            if (i == 200) {
                Gson gson = new Gson();
                BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    if (intExtra == 10) {
                        onMyFormList((BeanMyFormIndex) gson.fromJson(str, (Class<Object>) BeanMyFormIndex.class));
                    } else if (intExtra == 20) {
                        onSubscriptionList((BeanSubScriptionProjectList) gson.fromJson(str, (Class<Object>) BeanSubScriptionProjectList.class));
                    } else if (intExtra == 30) {
                        onFormShopList((BeanShopProject) gson.fromJson(str, (Class<Object>) BeanShopProject.class));
                    } else if (intExtra == 12) {
                        callServer();
                        Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    }
                } else if (TextUtils.isEmpty(beanCommon.RtnValue)) {
                    Toast.makeText(getContext(), "데이터가 없습니다.", 0).show();
                    LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
                    if ((this.mMap == null || !this.mMap.equals(linkedHashMap)) && linkedHashMap != null) {
                        setDropdownTeam(linkedHashMap);
                    }
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
            }
        }
    }

    private void onMyFormList(BeanMyFormIndex beanMyFormIndex) {
        this.mAdapter.clear();
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (BeanMyFormIndex.FormList formList : beanMyFormIndex.formList) {
            this.mAdapter.add(formList);
            linkedHashMap.put(Integer.valueOf(formList.MTCSEQ), formList.MTCateGoryTitle);
            for (BeanMyFormIndex.MTProjectList mTProjectList : formList.MTProjectList) {
                this.mAdapter.add(mTProjectList);
            }
        }
        if ((this.mMap == null || !this.mMap.equals(linkedHashMap)) && linkedHashMap != null) {
            setDropdownTeam(linkedHashMap);
        }
    }

    private void onSubscriptionList(BeanSubScriptionProjectList beanSubScriptionProjectList) {
        this.mAdapter.clear();
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (BeanSubScriptionProjectList.CategoryList categoryList : beanSubScriptionProjectList.CategoryList) {
            this.mAdapter.add(categoryList);
            linkedHashMap.put(Integer.valueOf(categoryList.SMCSEQ), categoryList.ShopMainCategoryTitle);
            for (BeanSubScriptionProjectList.ProjectList projectList : categoryList.projectList) {
                this.mAdapter.add(projectList);
            }
        }
        if ((this.mMap == null || !this.mMap.equals(linkedHashMap)) && linkedHashMap != null) {
            setDropdownTeam(linkedHashMap);
        }
    }

    private void onFormShopList(BeanShopProject beanShopProject) {
        this.mAdapter.clear();
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (BeanShopProject.ShopMainListBean shopMainListBean : beanShopProject.getShopMainList()) {
            if (this.mEtKeyword.getText().length() <= 0 || (shopMainListBean.getProjectList() != null && shopMainListBean.getProjectList().size() >= 1)) {
                shopMainListBean.setExpend(false);
                this.mAdapter.add(shopMainListBean);
                linkedHashMap.put(Integer.valueOf(shopMainListBean.getSMCSEQ()), shopMainListBean.getSMCateGoryTitle());
                for (BeanShopProject.ShopMainListBean.ProjectListBean projectListBean : shopMainListBean.getProjectList()) {
                    projectListBean.setSMCateGoryTitle(shopMainListBean.getSMCateGoryTitle());
                    projectListBean.setExpend(false);
                    this.mAdapter.add(projectListBean);
                }
            }
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.mAdapter.size() < 1) {
            this.mTvDefault.setVisibility(0);
        } else {
            this.mTvDefault.setVisibility(8);
        }
        if ((this.mMap == null || !this.mMap.equals(linkedHashMap)) && linkedHashMap != null) {
            setDropdownTeam(linkedHashMap);
        }
    }

    public FragmentForm.IOnDropdownSelectListener getiOnDropdownSelectListener() {
        return this.iOnDropdownSelectListener;
    }

    public void setiOnDropdownSelectListener(FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener) {
        this.iOnDropdownSelectListener = iOnDropdownSelectListener;
    }

    public void setDropdownTeam(LinkedHashMap<Integer, String> linkedHashMap) {
        this.mMap = linkedHashMap;
        if (!this.isFirst) {
            this.isFirst = true;
        }
        if (getiOnDropdownSelectListener() != null) {
            getiOnDropdownSelectListener().onSelected(linkedHashMap, this.mEtKeyword.getText().toString(), this.isFirst);
        }
    }
}
