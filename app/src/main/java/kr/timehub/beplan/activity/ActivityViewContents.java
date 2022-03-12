package kr.timehub.beplan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import java.util.ArrayList;
import java.util.HashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.objects.BaseViewHolder;
import kr.timehub.beplan.base.widgets.BaseImagesViewpager;
import kr.timehub.beplan.base.widgets.BaseLinearLayoutManager;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ActivityViewContents extends BaseActivity implements BaseRecyclerViewAdapterInterface {
    public static final String EXTRA_IMAGES = "EXTRA_IMAGES";
    public static final String EXTRA_SELECT = "EXTRA_SELECT";
    ArrayList<String> images;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.base_vp)
    BaseImagesViewpager mBaseVp;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_bottom_list)
    LinearLayout mLlBottomList;
    @BindView(R.id.ll_header)
    LinearLayout mLlHeader;
    BaseRecyclerViewAdapter2 mRvAdapter;
    @BindView(R.id.tv_img_count)
    TextView mTvImgCount;
    ImageAdapter mVpAdapter;
    HashMap<Integer, String> imageSelected = new HashMap<>();
    private boolean isShow = true;
    private int selected = 0;

    private void onAction() {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_view_contents);
        ButterKnife.bind(this);
        bindData();
        initAdapter();
        onAction();
    }

    private void bindData() {
        this.images = new ArrayList<>();
        this.images = getIntent().getStringArrayListExtra(EXTRA_IMAGES);
        this.mTvImgCount.setText(String.format("%d/%d", 1, Integer.valueOf(this.images.size())));
        this.selected = getIntent().getIntExtra(EXTRA_SELECT, 0);
    }

    private void initAdapter() {
        this.mRvAdapter = new BaseRecyclerViewAdapter2(this);
        this.mRvAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new BaseLinearLayoutManager(this, 0, false));
        this.mBaseRv.setAdapter(this.mRvAdapter);
        this.mRvAdapter.notifyDataSetChanged();
        this.imageSelected.put(0, "Selected");
        this.mVpAdapter = new ImageAdapter(this);
        this.mBaseVp.setAdapter(this.mVpAdapter);
        this.mBaseVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents.1
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                ActivityViewContents.this.imageSelected.clear();
                ActivityViewContents.this.imageSelected.put(Integer.valueOf(i), "Selected");
                ActivityViewContents.this.mRvAdapter.notifyDataSetChanged();
                ActivityViewContents.this.mTvImgCount.setText(String.format("%d/%d", Integer.valueOf(i + 1), Integer.valueOf(ActivityViewContents.this.images.size())));
                ActivityViewContents.this.mBaseRv.scrollToPosition(i);
            }
        });
        if (this.selected != -1) {
            this.mBaseVp.setCurrentItem(this.selected);
            this.selected = -1;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(this, R.layout.listitem_view_contents, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_out_line);
        Glide.with((FragmentActivity) this).asBitmap().load(this.images.get(i)).into((ImageView) baseViewHolder.getView(R.id.iv_item));
        if (this.imageSelected.get(Integer.valueOf(i)) != null) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActivityViewContents.this.mBaseVp.setCurrentItem(i);
            }
        });
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.images.size();
    }

    @OnClick({R.id.iv_close, R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        int currentItem = this.mBaseVp.getCurrentItem();
        int id = view.getId();
        if (id == R.id.iv_close) {
            finish();
        } else if (id != R.id.iv_left) {
            if (id == R.id.iv_right && currentItem < this.images.size() - 1) {
                this.mBaseVp.setCurrentItem(currentItem + 1);
            }
        } else if (currentItem > 0) {
            this.mBaseVp.setCurrentItem(currentItem - 1);
        }
    }

    /* loaded from: classes.dex */
    public class ImageAdapter extends PagerAdapter {
        Context mContext;

        public ImageAdapter(Context context) {
            ActivityViewContents.this = r1;
            this.mContext = context;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return ActivityViewContents.this.images.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((PhotoView) obj);
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            PhotoView photoView = new PhotoView(this.mContext);
            photoView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            Glide.with(this.mContext).asBitmap().load(ActivityViewContents.this.images.get(i)).into(photoView);
            ((ViewPager) viewGroup).addView(photoView, 0);
            photoView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.activity.ActivityViewContents.ImageAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ActivityViewContents.this.isShow) {
                        ActivityViewContents.this.isShow = false;
                    } else {
                        ActivityViewContents.this.isShow = true;
                    }
                    ActivityViewContents.this.showUi(ActivityViewContents.this.isShow);
                }
            });
            return photoView;
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            ((ViewPager) viewGroup).removeView((PhotoView) obj);
        }
    }

    public void showUi(boolean z) {
        if (z) {
            this.mLlHeader.setVisibility(0);
            this.mLlBottomList.setVisibility(0);
            return;
        }
        this.mLlHeader.setVisibility(8);
        this.mLlBottomList.setVisibility(8);
    }
}
