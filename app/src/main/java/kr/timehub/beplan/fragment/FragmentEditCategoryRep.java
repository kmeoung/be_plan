package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseViewHolder;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanProjectList;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class FragmentEditCategoryRep extends BaseFragment implements BaseRecyclerViewAdapterInterface, BaseMainToolBar.OnToolbarClickListener {
    private int PSEQ;
    private BeanAddProjectMember ProjectMembers;
    private ActivityMain context;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    private HashMap<Integer, Integer> mRunnerList;
    private List<BeanProjectList.CategoryListBean.TaskListBean> mTaskList;
    Unbinder unbinder;
    private final int REQ_MODIFY_CATEGORY_RUNNER = 1;
    private final int REQ_ADD_PROJECT_MEMBER = 2;

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    public static FragmentEditCategoryRep newInstance(List<BeanProjectList.CategoryListBean.TaskListBean> list, int i) {
        FragmentEditCategoryRep fragmentEditCategoryRep = new FragmentEditCategoryRep();
        fragmentEditCategoryRep.mTaskList = list;
        fragmentEditCategoryRep.PSEQ = i;
        return fragmentEditCategoryRep;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_background_set_2, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initAdapter();
        initToolbar();
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void initToolbar() {
        this.context = (ActivityMain) getContext();
        this.context.setTitle("false</true</담당 수정</false</false");
        this.context.setOnToolbarListener(this);
    }

    private void onAction() {
        httpPostAddProjectMember();
        bindData();
        this.mBtnSave.setText(getString(R.string.btn_ok));
    }

    private void bindData() {
        this.mRunnerList = new HashMap<>();
        this.mAdapter.clear();
        for (BeanProjectList.CategoryListBean.TaskListBean taskListBean : this.mTaskList) {
            if (taskListBean.isIsModify()) {
                this.mAdapter.add(taskListBean);
            }
            this.mRunnerList.put(Integer.valueOf(taskListBean.getTSEQ()), Integer.valueOf(taskListBean.getRunnerID()));
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_fragment_edit_category_rep, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        BeanProjectList.CategoryListBean.TaskListBean taskListBean = (BeanProjectList.CategoryListBean.TaskListBean) this.mAdapter.get(i);
        baseViewHolder.setText(R.id.tv_task_title, taskListBean.getTaskTitle());
        setDropdownTeam((Spinner) baseViewHolder.getView(R.id.sp_dropdown), taskListBean.getTSEQ(), taskListBean.getRunnerID());
    }

    public void setDropdownTeam(Spinner spinner, final int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (this.ProjectMembers != null) {
            arrayList.add("담당자를 선택해주세요.");
            for (BeanAddProjectMember.List list : this.ProjectMembers.List) {
                arrayList.add(list.RealName);
            }
            spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_edit_category_rep, arrayList));
            Utils.setDropDownHeight(spinner, HttpStatus.SC_INTERNAL_SERVER_ERROR);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentEditCategoryRep.1
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i3, long j) {
                    if (i3 == 0) {
                        FragmentEditCategoryRep.this.mRunnerList.put(Integer.valueOf(i), -1);
                    } else {
                        FragmentEditCategoryRep.this.mRunnerList.put(Integer.valueOf(i), Integer.valueOf(FragmentEditCategoryRep.this.ProjectMembers.List.get(i3 - 1).MemberId));
                    }
                }
            });
            for (int i3 = 1; i3 < this.ProjectMembers.List.size() + 1; i3++) {
                if (i2 == this.ProjectMembers.List.get(i3 - 1).MemberId) {
                    spinner.setSelection(i3);
                    return;
                }
            }
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
        this.context.onBackPressed();
    }

    private void httpPostModifyCategoryRunner() {
        JSONArray jSONArray = new JSONArray();
        try {
            Iterator<Integer> it = this.mRunnerList.keySet().iterator();
            int i = 0;
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    break;
                }
                Integer next = it.next();
                int intValue = this.mRunnerList.get(next).intValue();
                Iterator<BeanProjectList.CategoryListBean.TaskListBean> it2 = this.mTaskList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    BeanProjectList.CategoryListBean.TaskListBean next2 = it2.next();
                    if (next.intValue() == next2.getTSEQ() && intValue == next2.getRunnerID()) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    JSONObject jSONObject = new JSONObject();
                    if (intValue != -1) {
                        jSONObject.put("RunnerId", intValue);
                        jSONObject.put("TSEQ", next);
                        jSONArray.put(jSONObject);
                        i++;
                    }
                }
            }
            if (i > 0) {
                new BeplanOkHttpClient().ModifyCategoryRunner(getContext(), 1, jSONArray, this);
                return;
            }
            Toast.makeText(this.context, "변경된 사항이 없습니다", 0).show();
            this.context.onBackPressed();
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void httpPostAddProjectMember() {
        new BeplanOkHttpClient().AddProjectMember(getContext(), 2, String.valueOf(this.PSEQ), "", this);
    }

    @OnClick({R.id.btn_save})
    public void onViewClicked() {
        httpPostModifyCategoryRunner();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(this.context, "담당자가 변경되었습니다", 0).show();
                    this.context.onBackPressed();
                    return;
                }
                Toast.makeText(this.context, beanCommon.RtnValue, 0).show();
            } else if (intExtra != 2) {
            } else {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    this.ProjectMembers = (BeanAddProjectMember) gson.fromJson(str, (Class<Object>) BeanAddProjectMember.class);
                    this.mAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(this.context, beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(this.context, getString(R.string.error_server_not_success), 0).show();
        }
    }
}
