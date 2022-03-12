package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogPutInTemplate;
import kr.timehub.beplan.entry.BeanPutInTemplateGetList;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanProjectList;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class FragmentPutInTemplate extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    private static final int REQ_PUT_IN_TEMPLATE_GET_LIST = 164;
    private int CGSEQ;
    private String MainCateGoryTitle;
    private int PSEQ;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    private ArrayList<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> mCategoryTitles;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.iv_icn_check)
    ImageView mIvIcnCheck;
    @BindView(R.id.ll_list)
    LinearLayout mLlList;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.tv_list)
    TextView mTvList;
    Unbinder unbinder;
    private final int REQ_PUT_IN_TEMPLATE = 7;
    private final int TYPE_HEADER = 0;
    private final int TYPE_LISTITEM = 1;
    private int mCategorySize = -1;
    private HashMap<Integer, BeanPutInTemplateGetList.TemplateCateGorys> mListCheck = new HashMap<>();

    public static FragmentPutInTemplate newInstance(int i) {
        FragmentPutInTemplate fragmentPutInTemplate = new FragmentPutInTemplate();
        fragmentPutInTemplate.PSEQ = i;
        return fragmentPutInTemplate;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_put_in_template, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initAdapter();
        httpPutPutInTemplateGetList();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
        new BaseItemDecoration(0).setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
    }

    public void httpPutPutInTemplate(BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle, String str) {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String valueOf = this.PSEQ == -1 ? "" : String.valueOf(this.PSEQ);
        try {
            JSONArray jSONArray = new JSONArray();
            for (Integer num : this.mListCheck.keySet()) {
                JSONObject jSONObject = new JSONObject();
                BeanPutInTemplateGetList.TemplateCateGorys templateCateGorys = this.mListCheck.get(num);
                jSONObject.put("CGSEQ", templateCateGorys.CGSEQ);
                jSONObject.put("MakeID", templateCateGorys.MakeID);
                jSONObject.put("CateGoryTitle", templateCateGorys.CateGoryTitle);
                jSONObject.put("PSEQ", templateCateGorys.PSEQ);
                jSONObject.put("ProjectTitle", templateCateGorys.ProjectTitle);
                jSONObject.put("RegDate", templateCateGorys.RegDate);
                jSONObject.put("DeleteYN", templateCateGorys.DeleteYN);
                JSONArray jSONArray2 = new JSONArray();
                for (BeanPutInTemplateGetList.TaskList taskList : templateCateGorys.TaskList) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("TSEQ", taskList.TSEQ);
                    jSONObject2.put("TaskTitle", taskList.TaskTitle);
                    jSONObject2.put("RegDate", taskList.RegDate);
                    jSONArray2.put(jSONObject2);
                }
                jSONObject.put("TaskList", jSONArray2);
                jSONArray.put(jSONObject);
            }
            if (templateMainCateGoryTitle != null) {
                beplanOkHttpClient.PutInTemplate(getContext(), 7, jSONArray, valueOf, templateMainCateGoryTitle.MainCategoryTitle, null, this);
            } else {
                beplanOkHttpClient.PutInTemplate(getContext(), 7, jSONArray, valueOf, str, null, this);
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void httpPutPutInTemplateGetList() {
        new BeplanOkHttpClient().PutInTemplateGetList(getContext(), REQ_PUT_IN_TEMPLATE_GET_LIST, this.PSEQ == -1 ? "" : String.valueOf(this.PSEQ), this.mEtKeyword.getText().length() > 0 ? this.mEtKeyword.getText().toString() : "", this);
    }

    @OnClick({R.id.iv_icn_check, R.id.btn_save})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id != R.id.btn_save) {
            if (id == R.id.iv_icn_check) {
                if (this.mListCheck.size() == this.mCategorySize) {
                    this.mIvIcnCheck.setImageResource(R.drawable.btn_check_off);
                    this.mListCheck.clear();
                    this.mAdapter.notifyDataSetChanged();
                } else {
                    this.mIvIcnCheck.setImageResource(R.drawable.btn_check_on);
                    for (int i = 0; i < this.mAdapter.size(); i++) {
                        if (getItemViewType(i) == 0) {
                            BeanPutInTemplateGetList.TemplateCateGorys templateCateGorys = (BeanPutInTemplateGetList.TemplateCateGorys) this.mAdapter.get(i);
                            this.mListCheck.put(Integer.valueOf(templateCateGorys.CGSEQ), templateCateGorys);
                        }
                    }
                    this.mAdapter.notifyDataSetChanged();
                }
                if (this.mListCheck.size() < 1) {
                    this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
                } else {
                    this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                }
            }
        } else if (this.mListCheck.size() > 0) {
            showPutInTemplateDialog();
        }
    }

    private void showPutInTemplateDialog() {
        if (this.mCategoryTitles != null) {
            DialogPutInTemplate.newInstance(getContext(), "그룹 선택", this.mCategoryTitles, new DialogPutInTemplate.IOnDialogPutInTemplateListener() { // from class: kr.timehub.beplan.fragment.FragmentPutInTemplate.1
                @Override // kr.timehub.beplan.dialog.DialogPutInTemplate.IOnDialogPutInTemplateListener
                public void onSubmit(DialogPutInTemplate dialogPutInTemplate, BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle, String str) {
                    FragmentPutInTemplate.this.httpPutPutInTemplate(templateMainCateGoryTitle, str);
                    dialogPutInTemplate.dismiss();
                }
            }).show();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return BaseViewHolder.newInstance(getContext(), R.layout.listitem_business_title, viewGroup);
        }
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_put_in_template, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        if (getItemViewType(i) == 0) {
            final BeanPutInTemplateGetList.TemplateCateGorys templateCateGorys = (BeanPutInTemplateGetList.TemplateCateGorys) this.mAdapter.get(i);
            baseViewHolder.setText(R.id.tv_business_title_name, templateCateGorys.CateGoryTitle);
            baseViewHolder.getView(R.id.iv_add).setVisibility(8);
            baseViewHolder.getView(R.id.iv_more).setVisibility(8);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_check);
            imageView.setVisibility(0);
            baseViewHolder.getView(R.id.iv_sell).setVisibility(8);
            if (this.mListCheck != null) {
                if (this.mListCheck.get(Integer.valueOf(templateCateGorys.CGSEQ)) != null) {
                    imageView.setImageResource(R.drawable.btn_check_on);
                } else {
                    imageView.setImageResource(R.drawable.btn_check_off);
                }
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentPutInTemplate.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (FragmentPutInTemplate.this.mListCheck.get(Integer.valueOf(templateCateGorys.CGSEQ)) != null) {
                        if (FragmentPutInTemplate.this.mListCheck.size() == FragmentPutInTemplate.this.mCategorySize) {
                            FragmentPutInTemplate.this.mIvIcnCheck.setImageResource(R.drawable.btn_check_off);
                        }
                        FragmentPutInTemplate.this.mListCheck.remove(Integer.valueOf(templateCateGorys.CGSEQ));
                        FragmentPutInTemplate.this.mAdapter.notifyDataSetChanged();
                    } else {
                        FragmentPutInTemplate.this.mListCheck.put(Integer.valueOf(templateCateGorys.CGSEQ), templateCateGorys);
                        if (FragmentPutInTemplate.this.mListCheck.size() == FragmentPutInTemplate.this.mCategorySize) {
                            FragmentPutInTemplate.this.mIvIcnCheck.setImageResource(R.drawable.btn_check_on);
                        }
                        FragmentPutInTemplate.this.mAdapter.notifyDataSetChanged();
                    }
                    if (FragmentPutInTemplate.this.mListCheck.size() < 1) {
                        FragmentPutInTemplate.this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
                    } else {
                        FragmentPutInTemplate.this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                    }
                }
            });
            return;
        }
        onBindViewHolderList(baseViewHolder, i);
    }

    private void onBindViewHolderList(BaseViewHolder baseViewHolder, int i) {
        String str;
        BeanPutInTemplateGetList.TaskList taskList = (BeanPutInTemplateGetList.TaskList) this.mAdapter.get(i);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_business_content);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_task);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_reg_date);
        Date ConvertDate = Utils.ConvertDate(taskList.RegDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(ConvertDate);
        int i2 = instance.get(11);
        int i3 = instance.get(12);
        int i4 = instance.get(13);
        if (i2 >= 12) {
            str = "오후";
            if (i2 > 12) {
                i2 -= 12;
            }
        } else {
            str = "오전";
        }
        textView2.setText(String.format("%s %s %d:%02d:%02d", simpleDateFormat.format(ConvertDate), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
        textView.setText(taskList.TaskTitle);
        if (i > 0) {
            if (this.mAdapter.size() - 1 == i) {
                linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
            } else {
                int i5 = i + 1;
                if (this.mAdapter.get(i5) instanceof BeanProjectList.CategoryListBean) {
                    linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
                } else if (this.mAdapter.getItemViewType(i5) == 0) {
                    linearLayout.setBackgroundResource(R.drawable.wht_post_bg_bottom);
                } else {
                    linearLayout.setBackgroundResource(R.drawable.wht_post_bg_middle);
                }
            }
            boolean z = this.mAdapter.get(i - 1) instanceof String;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (this.mAdapter.get(i) instanceof BeanPutInTemplateGetList.TemplateCateGorys) {
            return 0;
        }
        return this.mAdapter.get(i) instanceof BeanPutInTemplateGetList.TaskList ? 1 : -1;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "DAOK") || TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                if (intExtra == REQ_PUT_IN_TEMPLATE_GET_LIST) {
                    bindCategoryList((BeanPutInTemplateGetList) gson.fromJson(str, (Class<Object>) BeanPutInTemplateGetList.class));
                } else if (intExtra == 7) {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    ((ActivityMain) getContext()).replaceMyForm();
                }
            } else if (!TextUtils.equals(beanCommon.RtnKey, Comm_RtnKey.DANO)) {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == REQ_PUT_IN_TEMPLATE_GET_LIST) {
                Toast.makeText(getContext(), (int) R.string.alert_error_templete_no_have_list, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void bindCategoryList(BeanPutInTemplateGetList beanPutInTemplateGetList) {
        getActivity().setTitle("false</true</" + beanPutInTemplateGetList.ProjectTitle + "</false</false");
        this.mCategorySize = 0;
        this.mAdapter.clear();
        for (BeanPutInTemplateGetList.TemplateCateGorys templateCateGorys : beanPutInTemplateGetList.TemplateCateGorys) {
            this.mAdapter.add(templateCateGorys);
            this.mCategorySize++;
            this.MainCateGoryTitle = templateCateGorys.CateGoryTitle;
            this.PSEQ = templateCateGorys.PSEQ;
            for (BeanPutInTemplateGetList.TaskList taskList : templateCateGorys.TaskList) {
                this.mAdapter.add(taskList);
            }
        }
        this.mCategoryTitles = new ArrayList<>();
        for (BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle : beanPutInTemplateGetList.TemplateMainCateGoryTitle) {
            this.mCategoryTitles.add(templateMainCateGoryTitle);
        }
    }
}
