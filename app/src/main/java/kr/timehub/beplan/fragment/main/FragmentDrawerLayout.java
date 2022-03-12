package kr.timehub.beplan.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseViewHolder;
import kr.timehub.beplan.entry.BeanDrawerProfile;
import kr.timehub.beplan.entry.common.BeanDrawerList;
import kr.timehub.beplan.entry.common.BeanDrawerTitle;

/* loaded from: classes.dex */
public class FragmentDrawerLayout extends BaseListFragment {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_HEADER_PROFILE = 0;
    public static final int TYPE_LIST = 2;
    public static final int TYPE_PROFILE_INFORMATION = 1;
    public static final int TYPE_PROFILE_NOTIFICATION = 0;
    public static final int TYPE_PROFILE_PROFILE = 3;
    public static final int TYPE_PROFILE_SETTINGS = 2;
    private OnDrawerClickListener onDrawerClickListener;

    /* loaded from: classes.dex */
    public interface OnDrawerClickListener {
        void onDrawerProfileClicked(View view, int i);

        void onDrawerProjectClicked(View view, BeanDrawerList beanDrawerList);

        void onDrawerTitleClicked(View view, BeanDrawerTitle beanDrawerTitle);
    }

    public static final FragmentDrawerLayout newInstance(OnDrawerClickListener onDrawerClickListener) {
        FragmentDrawerLayout fragmentDrawerLayout = new FragmentDrawerLayout();
        fragmentDrawerLayout.setOnDrawerClickListener(onDrawerClickListener);
        return fragmentDrawerLayout;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                if (i == 0) {
                    return BaseViewHolder.newInstance(FragmentDrawerLayout.this.getContext(), R.layout.header_drawer_profile, viewGroup);
                }
                if (i == 2) {
                    return BaseViewHolder.newInstance(FragmentDrawerLayout.this.getContext(), R.layout.listitem_drawer, viewGroup);
                }
                return BaseViewHolder.newInstance(FragmentDrawerLayout.this.getContext(), R.layout.header_drawer_title, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                int itemViewType = getItemViewType(i);
                if (itemViewType == 0) {
                    onBindProfileHeaderViewHolder(baseViewHolder, i);
                } else if (itemViewType != 2) {
                    onBindHeaderViewHolder(baseViewHolder, i);
                } else {
                    onBindListViewHolder(baseViewHolder, i);
                }
            }

            private void onBindListViewHolder(BaseViewHolder baseViewHolder, int i) {
                final BeanDrawerList beanDrawerList = (BeanDrawerList) FragmentDrawerLayout.this.get(i);
                baseViewHolder.setText(R.id.tv_title, beanDrawerList.getTitle());
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerProjectClicked(view, beanDrawerList);
                        }
                    }
                });
            }

            private void onBindHeaderViewHolder(BaseViewHolder baseViewHolder, int i) {
                final BeanDrawerTitle beanDrawerTitle = (BeanDrawerTitle) FragmentDrawerLayout.this.get(i);
                baseViewHolder.setText(R.id.tv_title, beanDrawerTitle.getTitle());
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerTitleClicked(view, beanDrawerTitle);
                        }
                    }
                });
            }

            private void onBindProfileHeaderViewHolder(BaseViewHolder baseViewHolder, int i) {
                BeanDrawerProfile beanDrawerProfile = (BeanDrawerProfile) FragmentDrawerLayout.this.get(i);
                baseViewHolder.setText(R.id.tv_email, beanDrawerProfile.getEmail());
                baseViewHolder.setText(R.id.tv_name, beanDrawerProfile.getName());
                Glide.with(FragmentDrawerLayout.this).asBitmap().load(beanDrawerProfile.getProfileUrl()).into(baseViewHolder.getImageView(R.id.iv_profile));
                baseViewHolder.getView(R.id.btn_option).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerProfileClicked(view, 2);
                        }
                    }
                });
                baseViewHolder.getView(R.id.btn_alarm).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerProfileClicked(view, 0);
                        }
                    }
                });
                baseViewHolder.getView(R.id.btn_team).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerProfileClicked(view, 1);
                        }
                    }
                });
                baseViewHolder.getView(R.id.layout_profile).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawerLayout.1.6
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (FragmentDrawerLayout.this.onDrawerClickListener != null) {
                            FragmentDrawerLayout.this.onDrawerClickListener.onDrawerProfileClicked(view, 3);
                        }
                    }
                });
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return FragmentDrawerLayout.this.size();
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i) {
                if (FragmentDrawerLayout.this.get(i) instanceof BeanDrawerList) {
                    return 2;
                }
                return FragmentDrawerLayout.this.get(i) instanceof BeanDrawerTitle ? 1 : 0;
            }
        };
    }

    public OnDrawerClickListener getOnDrawerClickListener() {
        return this.onDrawerClickListener;
    }

    public void setOnDrawerClickListener(OnDrawerClickListener onDrawerClickListener) {
        this.onDrawerClickListener = onDrawerClickListener;
    }
}
