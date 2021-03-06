package kr.timehub.beplan.fragment.SideForm.myForm;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityFormTaskDetail;
import kr.timehub.beplan.activity.ActivityUsingNewProject;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogSelectProjectMyForm;
import kr.timehub.beplan.entry.BeanMyFormCategoryDetail;
import kr.timehub.beplan.entry.BeanMyFormPopUsing;
import kr.timehub.beplan.entry.BeanRank;
import kr.timehub.beplan.entry.BeanRankItem;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentMyFormCategoryDetail extends BaseListFragment {
    private static final int REQ_CATEGORY_DELETE = 868;
    private static final int REQ_POP_USING = 514;
    private static final int REQ_USING = 456;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.ll_use)
    LinearLayout mLlUse;
    private int mMTCSEQ;
    private int mMTPSEQ;
    private String mSelectCategory;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_use)
    TextView mTvUse;
    Unbinder unbinder;
    private final int TYPE_LIST = 1;
    private final int TYPE_HEADER = 0;
    private final int TYPE_LISTITEM = 1;
    private int mSelectCategorySeq = -1;
    private final int REQ_CATEGORY_DETAIL = 13;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (FragmentMyFormCategoryDetail.this.handler != null) {
                FragmentMyFormCategoryDetail.this.httpPostMyFormCategoryDetail();
            }
        }
    };
    private HashMap<Integer, String> selectMap = new HashMap<>();

    public static FragmentMyFormCategoryDetail newInstance(String str, int i) {
        FragmentMyFormCategoryDetail fragmentMyFormCategoryDetail = new FragmentMyFormCategoryDetail();
        fragmentMyFormCategoryDetail.mTitle = str;
        fragmentMyFormCategoryDetail.mMTCSEQ = i;
        return fragmentMyFormCategoryDetail;
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
        httpPostMyFormCategoryDetail();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        activity.setTitle("false</true</" + this.mTitle + "</false</false");
        this.mTvTitle.setText("");
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
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (i != 0 || i2 != 0 || i3 != 0) {
                    FragmentMyFormCategoryDetail.this.handler.removeMessages(0);
                    FragmentMyFormCategoryDetail.this.handler.sendEmptyMessageDelayed(0, 1000L);
                }
            }
        });
    }

    private void bindTempData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new BeanRankItem(String.valueOf(i)));
        }
        add(new BeanRank("1. ??? ??? ??????", arrayList));
        add(new BeanRank("2. ??? ??? ??????", arrayList));
        add(new BeanRank("3. ??? ??? ??????", arrayList));
        add(new BeanRank("4. ??? ??? ??????", arrayList));
        notifyDataSetChanged();
    }

    /* renamed from: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail$3  reason: invalid class name */
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
            return BaseViewHolder.newInstance(FragmentMyFormCategoryDetail.this.getContext(), R.layout.listitem_form_detail, viewGroup);
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            onBindList((BaseViewHolder) viewHolder, i);
        }

        private void onBindList(BaseViewHolder baseViewHolder, final int i) {
            final BeanMyFormCategoryDetail.CategoryList categoryList = (BeanMyFormCategoryDetail.CategoryList) FragmentMyFormCategoryDetail.this.get(i);
            baseViewHolder.setText(R.id.tv_project, categoryList.CategoryTitle);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_tab);
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_more);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.list_rv);
            LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.ll_use);
            baseViewHolder.getView(R.id.ll_version_set).setVisibility(8);
            if (TextUtils.equals((CharSequence) FragmentMyFormCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                imageView.setImageResource(R.drawable.btn_tab_open);
                linearLayout.setVisibility(0);
            } else {
                imageView.setImageResource(R.drawable.btn_tab_close);
                linearLayout.setVisibility(8);
            }
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.equals((CharSequence) FragmentMyFormCategoryDetail.this.selectMap.get(Integer.valueOf(i)), "select")) {
                        FragmentMyFormCategoryDetail.this.selectMap.put(Integer.valueOf(i), null);
                    } else {
                        FragmentMyFormCategoryDetail.this.selectMap.put(Integer.valueOf(i), "select");
                    }
                    FragmentMyFormCategoryDetail.this.notifyDataSetChanged();
                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    FragmentMyFormCategoryDetail.this.mSelectCategorySeq = categoryList.MTCGSEQ;
                    FragmentMyFormCategoryDetail.this.mSelectCategory = categoryList.CategoryTitle;
                    FragmentMyFormCategoryDetail.this.httpPostMyFormPopUsing(categoryList.MTCGSEQ);
                }
            });
            ((ImageView) baseViewHolder.getView(R.id.iv_more)).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(FragmentMyFormCategoryDetail.this.getContext(), view);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.getMenu().add("??????");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.3.1
                        @Override // android.widget.PopupMenu.OnMenuItemClickListener
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            String charSequence = menuItem.getTitle().toString();
                            if (((charSequence.hashCode() == 1580303 && charSequence.equals("??????")) ? (char) 0 : (char) 65535) != 0) {
                                return true;
                            }
                            FragmentMyFormCategoryDetail.this.httpPostMyFormCategoryDelete(categoryList.MTCGSEQ);
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
            final BaseRecyclerViewAdapter2 baseRecyclerViewAdapter2 = new BaseRecyclerViewAdapter2(FragmentMyFormCategoryDetail.this.getContext());
            baseRecyclerViewAdapter2.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.4
                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public int getItemViewType(int i2) {
                    return 1;
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return BaseViewHolder.newInstance(FragmentMyFormCategoryDetail.this.getContext(), R.layout.listitem_rank_item, viewGroup);
                }

                @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    BaseViewHolder baseViewHolder2 = (BaseViewHolder) viewHolder;
                    final BeanMyFormCategoryDetail.TaskList taskList = (BeanMyFormCategoryDetail.TaskList) baseRecyclerViewAdapter2.get(i2);
                    baseViewHolder2.setText(R.id.tv_project, taskList.TaskTitle);
                    baseViewHolder2.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.3.4.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Intent intent = new Intent(FragmentMyFormCategoryDetail.this.getContext(), ActivityFormTaskDetail.class);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TSEQ, taskList.MTTSEQ);
                            intent.putExtra(ActivityFormTaskDetail.EXTRA_TITLE, FragmentMyFormCategoryDetail.this.mTitle);
                            FragmentMyFormCategoryDetail.this.startActivity(intent);
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
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentMyFormCategoryDetail.this.getContext()));
            recyclerView.setAdapter(baseRecyclerViewAdapter2);
            baseRecyclerViewAdapter2.clear();
            for (BeanMyFormCategoryDetail.TaskList taskList : categoryList.TaskList) {
                baseRecyclerViewAdapter2.add(taskList);
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentMyFormCategoryDetail.this.size();
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
    public void httpPostMyFormCategoryDetail() {
        clear();
        notifyDataSetChanged();
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        if (this.mEtKeyword != null) {
            str = this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "";
        }
        beplanOkHttpClient.MyFormCategoryDetail(getContext(), 13, String.valueOf(this.mMTCSEQ), str, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostMyFormCategoryDelete(int i) {
        new BeplanOkHttpClient().MyFormDeleteCategory(getContext(), REQ_CATEGORY_DELETE, String.valueOf(i), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostMyFormPopUsing(int i) {
        new BeplanOkHttpClient().MyFormPopUsing(getContext(), REQ_POP_USING, String.valueOf(this.mMTPSEQ), String.valueOf(i), this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void httpPostMyFormUsing(int i, int i2, String str) {
        new BeplanOkHttpClient().MyFormUsing(getContext(), REQ_USING, str, String.valueOf(i2), null, null, null, String.valueOf(i2), String.valueOf(i), this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                if (intExtra == 13) {
                    setMyFormCategoryDetail((BeanMyFormCategoryDetail) gson.fromJson(str, (Class<Object>) BeanMyFormCategoryDetail.class));
                } else if (intExtra == REQ_CATEGORY_DELETE) {
                    httpPostMyFormCategoryDetail();
                } else if (intExtra == REQ_POP_USING) {
                    showSelectProjectDialog((BeanMyFormPopUsing) gson.fromJson(str, (Class<Object>) BeanMyFormPopUsing.class));
                } else if (intExtra == REQ_USING && !TextUtils.isEmpty(this.mSelectCategory)) {
                    Context context = getContext();
                    Toast.makeText(context, this.mSelectCategory + "??????????????? ???????????????.", 0).show();
                }
            } else if (TextUtils.equals(beanCommon.RtnKey, Comm_RtnKey.DANO)) {
                Toast.makeText(getContext(), "???????????? ????????????.", 0).show();
            } else {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setMyFormCategoryDetail(BeanMyFormCategoryDetail beanMyFormCategoryDetail) {
        this.mTvTitle.setText(beanMyFormCategoryDetail.MTCategoryTitle);
        this.mMTPSEQ = beanMyFormCategoryDetail.MTPSEQ;
        clear();
        for (BeanMyFormCategoryDetail.CategoryList categoryList : beanMyFormCategoryDetail.CategoryList) {
            add(categoryList);
        }
    }

    private void showSelectProjectDialog(BeanMyFormPopUsing beanMyFormPopUsing) {
        DialogSelectProjectMyForm.newInstance(getContext(), beanMyFormPopUsing.PList, new DialogSelectProjectMyForm.OnDialogSelectProjectListener() { // from class: kr.timehub.beplan.fragment.SideForm.myForm.FragmentMyFormCategoryDetail.4
            @Override // kr.timehub.beplan.dialog.DialogSelectProjectMyForm.OnDialogSelectProjectListener
            public void sendPositive(DialogSelectProjectMyForm dialogSelectProjectMyForm, BeanMyFormPopUsing.PList pList) {
                if (pList == null) {
                    Intent intent = new Intent(FragmentMyFormCategoryDetail.this.getContext(), ActivityUsingNewProject.class);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGSEQ, FragmentMyFormCategoryDetail.this.mSelectCategorySeq);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_CGTITLE, FragmentMyFormCategoryDetail.this.mSelectCategory);
                    intent.putExtra(ActivityUsingNewProject.EXTRA_TYPE, ActivityUsingNewProject.TYPE_MY_FORM);
                    dialogSelectProjectMyForm.dismiss();
                    FragmentMyFormCategoryDetail.this.startActivity(intent);
                    return;
                }
                FragmentMyFormCategoryDetail.this.httpPostMyFormUsing(FragmentMyFormCategoryDetail.this.mSelectCategorySeq, pList.PSEQ, pList.ProjectTitle);
                dialogSelectProjectMyForm.dismiss();
            }
        }).show();
    }
}
