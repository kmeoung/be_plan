package kr.timehub.beplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.dialog.DialogTask;
import kr.timehub.beplan.entry.BeanRankItem;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class ActivitySavePlanList extends BaseActivity implements BaseRecyclerViewAdapterInterface {
    BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.base_toolbar)
    BaseMainToolBar mBaseToolbar;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.et_project)
    EditText mEtProject;
    @BindView(R.id.v_state)
    View mVState;

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_save_plan);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVState);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        initAdapter();
        onAction();
        bindTempData();
        showTimer();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideTimer();
    }

    public void showTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_SHOW_TIMER);
            startService(intent);
        }
    }

    public void hideTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_HIDE_TIMER);
            startService(intent);
        }
    }

    private void bindTempData() {
        for (int i = 0; i < 5; i++) {
            this.mAdapter.add(new BeanRankItem(String.valueOf(i)));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(this);
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(this));
        this.mBaseRv.setAdapter(this.mAdapter);
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(0);
        baseItemDecoration.setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
        this.mBaseRv.addItemDecoration(baseItemDecoration);
    }

    private void onAction() {
        this.mBaseToolbar.setTitle("플랜 저장하기");
        this.mBaseToolbar.setToolbarVisibleState(false, true, false, false);
        this.mBaseToolbar.setOnToolbarClickListener(new BaseMainToolBar.OnToolbarClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlanList.1
            @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
            public void onToolbarAddClicked(View view) {
            }

            @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
            public void onToolbarDrawerClicked(View view) {
            }

            @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
            public void onToolbarMenuClicked(View view) {
            }

            @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
            public void onToolbarCloseClicked(View view) {
                ActivitySavePlanList.this.onBackPressed();
            }
        });
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(this, R.layout.listitem_save_plan_list, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        onBindList((BaseViewHolder) viewHolder, i);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    private void onBindList(BaseViewHolder baseViewHolder, final int i) {
        final BeanRankItem beanRankItem = (BeanRankItem) this.mAdapter.get(i);
        baseViewHolder.setText(R.id.tv_project, beanRankItem.getTitle());
        baseViewHolder.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlanList.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DialogTask.newInstance(ActivitySavePlanList.this, beanRankItem.getTitle()).show();
            }
        });
        baseViewHolder.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlanList.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActivitySavePlanList.this.mAdapter.remove(i);
                ActivitySavePlanList.this.mAdapter.notifyDataSetChanged();
            }
        });
    }
}
