package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanStopWatch;
import kr.timehub.beplan.entry.database.DbStopWatch;

/* loaded from: classes.dex */
public class FragmentTimeLine extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    private int pSeq;
    Unbinder unbinder;
    Calendar mCal = Calendar.getInstance();
    private final int TYPE_HEADER = 0;
    private final int TYPE_LIST = 1;

    public static FragmentTimeLine newInstance(int i) {
        FragmentTimeLine fragmentTimeLine = new FragmentTimeLine();
        fragmentTimeLine.pSeq = i;
        return fragmentTimeLine;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_time_line, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initAdapter();
        onAction();
        if (((ActivityMain) getActivity()).getmBaseToolbar() != null) {
            BaseMainToolBar baseMainToolBar = ((ActivityMain) getActivity()).getmBaseToolbar();
            baseMainToolBar.setToolbarVisibleState(false, true, false, false);
            baseMainToolBar.setTitle("기록");
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void onAction() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy. MM. dd");
        String format = simpleDateFormat.format(this.mCal.getTime());
        this.mTvDate.setText(format);
        Realm defaultInstance = Realm.getDefaultInstance();
        RealmResults findAllSorted = defaultInstance.where(DbStopWatch.class).equalTo("PSEQ", Integer.valueOf(this.pSeq)).findAllSorted(new String[]{"CGSEQ", "startTime"}, new Sort[]{Sort.ASCENDING, Sort.DESCENDING});
        this.mAdapter.clear();
        Iterator it = findAllSorted.iterator();
        int i = -1;
        while (it.hasNext()) {
            DbStopWatch dbStopWatch = (DbStopWatch) it.next();
            if (dbStopWatch != null && dbStopWatch.getEll() >= 0 && TextUtils.equals(simpleDateFormat.format(new Date(dbStopWatch.getStartTime())), format)) {
                if (!(i == dbStopWatch.getCGSEQ() || dbStopWatch.getCgTitle() == null)) {
                    i = dbStopWatch.getCGSEQ();
                    this.mAdapter.add(dbStopWatch.getCgTitle());
                }
                this.mAdapter.add(new BeanStopWatch(dbStopWatch.getPSEQ(), dbStopWatch.getCGSEQ(), dbStopWatch.getTSEQ(), dbStopWatch.getpTitle(), dbStopWatch.getCgTitle(), dbStopWatch.gettTitle(), dbStopWatch.getStartTime(), dbStopWatch.getEndTime(), dbStopWatch.getEll()));
            }
        }
        defaultInstance.close();
    }

    /* loaded from: classes.dex */
    static class NameDescCompare implements Comparator<DbStopWatch> {
        NameDescCompare() {
        }

        public int compare(DbStopWatch dbStopWatch, DbStopWatch dbStopWatch2) {
            if (dbStopWatch.getCGSEQ() > dbStopWatch2.getCGSEQ()) {
                return -1;
            }
            return dbStopWatch.getCGSEQ() < dbStopWatch2.getCGSEQ() ? 1 : 0;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_time_line, viewGroup);
        }
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_time_line, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        if (getItemViewType(i) == 0) {
            baseViewHolder.setText(R.id.tv_title, (String) this.mAdapter.get(i));
        } else if (getItemViewType(i) == 1) {
            BeanStopWatch beanStopWatch = (BeanStopWatch) this.mAdapter.get(i);
            if (beanStopWatch != null) {
                if (!TextUtils.isEmpty(beanStopWatch.gettTitle())) {
                    baseViewHolder.setText(R.id.tv_task, beanStopWatch.gettTitle());
                }
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(beanStopWatch.getStartTime());
                baseViewHolder.setText(R.id.tv_start_date, String.format("%02d:%02d:%02d", Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)), Integer.valueOf(instance.get(13))));
                instance.setTimeInMillis(beanStopWatch.getEndTime());
                baseViewHolder.setText(R.id.tv_end_date, String.format("%02d:%02d:%02d", Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)), Integer.valueOf(instance.get(13))));
                baseViewHolder.setText(R.id.tv_ell, String.format("%02d:%02d:%02d", Integer.valueOf((((int) beanStopWatch.getEll()) / 1000) / 3600), Integer.valueOf(((int) ((beanStopWatch.getEll() / 1000) / 60)) % 60), Integer.valueOf(((int) (beanStopWatch.getEll() / 1000)) % 60)));
                return;
            }
            this.mAdapter.remove(i);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return this.mAdapter.get(i) instanceof String ? 0 : 1;
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_left) {
            this.mCal.add(5, -1);
            onAction();
        } else if (id == R.id.iv_right) {
            this.mCal.add(5, 1);
            onAction();
        }
    }
}
