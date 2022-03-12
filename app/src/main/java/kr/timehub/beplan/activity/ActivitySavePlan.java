package kr.timehub.beplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.dialog.DialogTask;
import kr.timehub.beplan.entry.BeanRank;
import kr.timehub.beplan.entry.BeanRankItem;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class ActivitySavePlan extends BaseActivity implements BaseRecyclerViewAdapterInterface {
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
    private HashMap<Integer, String> selectMap = new HashMap<>();

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
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            arrayList.add(new BeanRankItem(String.valueOf(i)));
        }
        this.mAdapter.add(new BeanRank("1. 할 일 목록", arrayList));
        this.mAdapter.add(new BeanRank("2. 할 일 목록", arrayList));
        this.mAdapter.add(new BeanRank("3. 할 일 목록", arrayList));
        this.mAdapter.add(new BeanRank("4. 할 일 목록", arrayList));
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
        this.mBaseToolbar.setOnToolbarClickListener(new BaseMainToolBar.OnToolbarClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlan.1
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
                ActivitySavePlan.this.onBackPressed();
            }
        });
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(this, R.layout.listitem_save_plan, viewGroup);
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
        BeanRank beanRank = (BeanRank) this.mAdapter.get(i);
        baseViewHolder.setText(R.id.tv_project, beanRank.getTitle());
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_tab);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_more);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_item);
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.list_rv);
        if (TextUtils.equals(this.selectMap.get(Integer.valueOf(i)), "select")) {
            imageView.setImageResource(R.drawable.btn_tab_open);
            linearLayout.setVisibility(0);
        } else {
            imageView.setImageResource(R.drawable.btn_tab_close);
            linearLayout.setVisibility(8);
        }
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlan.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TextUtils.equals((CharSequence) ActivitySavePlan.this.selectMap.get(Integer.valueOf(i)), "select")) {
                    ActivitySavePlan.this.selectMap.put(Integer.valueOf(i), null);
                } else {
                    ActivitySavePlan.this.selectMap.put(Integer.valueOf(i), "select");
                }
                ActivitySavePlan.this.mAdapter.notifyDataSetChanged();
            }
        });
        final BaseRecyclerViewAdapter2 baseRecyclerViewAdapter2 = new BaseRecyclerViewAdapter2(this);
        baseRecyclerViewAdapter2.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.activity.ActivitySavePlan.3
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i2) {
                return 0;
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                return BaseViewHolder.newInstance(ActivitySavePlan.this, R.layout.listitem_save_plan_item, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i2) {
                BaseViewHolder baseViewHolder2 = (BaseViewHolder) viewHolder;
                final BeanRankItem beanRankItem = (BeanRankItem) baseRecyclerViewAdapter2.get(i2);
                baseViewHolder2.setText(R.id.tv_project, beanRankItem.getTitle());
                baseViewHolder2.getView(R.id.tv_project).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlan.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        DialogTask.newInstance(ActivitySavePlan.this, beanRankItem.getTitle()).show();
                    }
                });
                baseViewHolder2.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivitySavePlan.3.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        baseRecyclerViewAdapter2.remove(i2);
                        baseRecyclerViewAdapter2.notifyDataSetChanged();
                    }
                });
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return baseRecyclerViewAdapter2.size();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseRecyclerViewAdapter2);
        recyclerView.addItemDecoration(new BaseItemDecoration((int) R.color.project_divider).setSize(1).setDeleteLine(1));
        Iterator<BeanRankItem> it = beanRank.getList().iterator();
        while (it.hasNext()) {
            baseRecyclerViewAdapter2.add(it.next());
            baseRecyclerViewAdapter2.notifyDataSetChanged();
        }
    }
}
