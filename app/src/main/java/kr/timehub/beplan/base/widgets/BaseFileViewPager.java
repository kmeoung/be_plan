package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityPhotoView;
import kr.timehub.beplan.base.objects.BaseViewHolder;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.http.HttpBindTempData;

/* loaded from: classes.dex */
public class BaseFileViewPager extends ViewPager {
    private IOnItemDeletedListener mOnItemDeletedListener;
    private Handler mainThreadHandler = new Handler();
    private ArrayList<BeanViewPager> mArray = new ArrayList<>();
    private float height = 0.0f;
    private float count = 0.0f;
    private View viewLeft = null;
    private View viewRight = null;

    /* loaded from: classes.dex */
    public interface IOnItemDeletedListener {
        void submitItemDeleted(int i);
    }

    public void attachButtonLeft(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int currentItem = BaseFileViewPager.this.getCurrentItem();
                BaseFileViewPager.this.getAdapter().getCount();
                int i = currentItem - 1;
                if (i >= 0) {
                    BaseFileViewPager.this.setCurrentItem(i);
                }
            }
        });
    }

    public void attachButtonRight(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int currentItem = BaseFileViewPager.this.getCurrentItem() + 1;
                if (currentItem < BaseFileViewPager.this.getAdapter().getCount() - 1) {
                    BaseFileViewPager.this.setCurrentItem(currentItem);
                }
            }
        });
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getAdapter() == null || getAdapter().getCount() >= ((int) this.count)) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public BaseFileViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public BaseFileViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager();
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setAdapter(new CustomPagerAdapter());
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAdapter(null);
    }

    public void initView(IOnItemDeletedListener iOnItemDeletedListener) {
        initViewPager();
        setAdapter(new CustomPagerAdapter());
        setmOnItemDeletedListener(iOnItemDeletedListener);
    }

    public IOnItemDeletedListener getmOnItemDeletedListener() {
        return this.mOnItemDeletedListener;
    }

    public void setmOnItemDeletedListener(IOnItemDeletedListener iOnItemDeletedListener) {
        this.mOnItemDeletedListener = iOnItemDeletedListener;
    }

    private void initViewPager() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                BaseFileViewPager.this.height = BaseFileViewPager.this.getMeasuredHeight();
                BaseFileViewPager.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BaseFileViewPager.this.post(new Runnable() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseFileViewPager.this.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        });
        setPageMargin((int) TypedValue.applyDimension(1, 50.0f, getResources().getDisplayMetrics()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class CustomPagerAdapter extends PagerAdapter {
        LayoutInflater mInflater;

        @Override // android.support.v4.view.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return obj == view;
        }

        private CustomPagerAdapter() {
            BaseFileViewPager.this = r1;
            this.mInflater = LayoutInflater.from(BaseFileViewPager.this.getContext());
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return BaseFileViewPager.this.mArray.size();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, final int i) {
            char c;
            int i2;
            BaseViewHolder newInstance = BaseViewHolder.newInstance(BaseFileViewPager.this.getContext(), R.layout.listitem_file_view_pager, viewGroup);
            final BeanViewPager beanViewPager = (BeanViewPager) BaseFileViewPager.this.mArray.get(i);
            String type = beanViewPager.getType();
            int hashCode = type.hashCode();
            if (hashCode != 73) {
                switch (hashCode) {
                    case 76:
                        if (type.equals("L")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 77:
                        if (type.equals("M")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
            } else {
                if (type.equals("I")) {
                    c = 0;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    i2 = R.drawable.img_thumnail_camera;
                    break;
                case 1:
                    i2 = R.drawable.img_thumnail_video;
                    break;
                case 2:
                    i2 = R.drawable.img_thumnail_link;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            ImageView imageView = newInstance.getImageView(R.id.iv_img);
            if (TextUtils.equals(beanViewPager.getImageUrl(), HttpBindTempData.PostFileLinkUrl)) {
                Glide.with(BaseFileViewPager.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_thumnail_link)).into(imageView);
            } else {
                newInstance.itemView.measure(0, 0);
                int measuredWidth = newInstance.itemView.getMeasuredWidth();
                int measuredHeight = newInstance.itemView.getMeasuredHeight();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(i2);
                requestOptions.override(measuredWidth, measuredHeight);
                requestOptions.encodeQuality(60);
                Glide.with(BaseFileViewPager.this.getContext()).asBitmap().load(beanViewPager.getImageUrl()).apply(requestOptions).into(imageView);
            }
            if (beanViewPager.isDelete()) {
                newInstance.getView(R.id.iv_remove).setVisibility(0);
                newInstance.getView(R.id.iv_remove).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.CustomPagerAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        BaseFileViewPager.this.mArray.remove(i);
                        if (BaseFileViewPager.this.getmOnItemDeletedListener() != null) {
                            BaseFileViewPager.this.getmOnItemDeletedListener().submitItemDeleted(BaseFileViewPager.this.size());
                        }
                        CustomPagerAdapter.this.notifyDataSetChanged();
                    }
                });
            } else {
                newInstance.getView(R.id.iv_remove).setVisibility(8);
            }
            ImageView imageView2 = (ImageView) newInstance.getView(R.id.iv_img);
            if (TextUtils.equals(beanViewPager.getType(), "I")) {
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                newInstance.getView(R.id.iv_type).setVisibility(8);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.CustomPagerAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(beanViewPager.getImageUrl())) {
                            Intent intent = new Intent(BaseFileViewPager.this.getContext(), ActivityPhotoView.class);
                            intent.putExtra(ActivityPhotoView.args_url, beanViewPager.getImageUrl());
                            BaseFileViewPager.this.getContext().startActivity(intent);
                        }
                    }
                });
            } else if (TextUtils.equals(beanViewPager.getType(), "L")) {
                imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
                newInstance.getView(R.id.iv_type).setVisibility(0);
                ((ImageView) newInstance.getView(R.id.iv_type)).setImageResource(R.drawable.img_thumb_l);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseFileViewPager.CustomPagerAdapter.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(beanViewPager.getWebUrl())) {
                            BaseFileViewPager.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(beanViewPager.getWebUrl())));
                        }
                    }
                });
            } else if (TextUtils.equals(beanViewPager.getType(), "M")) {
                newInstance.getView(R.id.iv_type).setVisibility(0);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ((ImageView) newInstance.getView(R.id.iv_type)).setImageResource(R.drawable.img_thumb_v);
            } else {
                newInstance.getView(R.id.iv_type).setVisibility(8);
            }
            viewGroup.addView(newInstance.itemView, 0);
            return newInstance.itemView;
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // android.support.v4.view.PagerAdapter
        public float getPageWidth(int i) {
            BaseFileViewPager.this.count = BaseFileViewPager.this.getMeasuredWidth() / BaseFileViewPager.this.height;
            return (1.0f / BaseFileViewPager.this.count) + 0.03f;
        }
    }

    public ArrayList<BeanViewPager> getArray() {
        return this.mArray;
    }

    public void setArray(ArrayList<BeanViewPager> arrayList) {
        this.mArray = arrayList;
    }

    public void ensureCapacity(int i) {
        this.mArray.ensureCapacity(i);
    }

    public int size() {
        return this.mArray.size();
    }

    public boolean isEmpty() {
        return this.mArray.isEmpty();
    }

    public boolean contains(Object obj) {
        return this.mArray.contains(obj);
    }

    public int indexOf(Object obj) {
        return this.mArray.indexOf(obj);
    }

    public int lastIndexOf(Object obj) {
        return this.mArray.lastIndexOf(obj);
    }

    public BeanViewPager get(int i) {
        return this.mArray.get(i);
    }

    public BeanViewPager set(int i, BeanViewPager beanViewPager) {
        return this.mArray.set(i, beanViewPager);
    }

    public boolean add(BeanViewPager beanViewPager) {
        return this.mArray.add(beanViewPager);
    }

    public void add(int i, BeanViewPager beanViewPager) {
        this.mArray.add(i, beanViewPager);
    }

    public BeanViewPager remove(int i) {
        return this.mArray.remove(i);
    }

    public boolean remove(Object obj) {
        return this.mArray.remove(obj);
    }

    public void clear() {
        this.mArray.clear();
    }

    public boolean addAll(Collection<? extends BeanViewPager> collection) {
        return this.mArray.addAll(collection);
    }

    public boolean addAll(int i, Collection<? extends BeanViewPager> collection) {
        return this.mArray.addAll(i, collection);
    }

    public boolean removeAll(Collection<?> collection) {
        return this.mArray.removeAll(collection);
    }

    public ListIterator<BeanViewPager> listIterator(int i) {
        return this.mArray.listIterator(i);
    }

    public ListIterator<BeanViewPager> listIterator() {
        return this.mArray.listIterator();
    }

    public Iterator<BeanViewPager> iterator() {
        return this.mArray.iterator();
    }

    public boolean containsAll(Collection<?> collection) {
        return this.mArray.containsAll(collection);
    }
}
