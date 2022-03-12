package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class BaseMainToolBar extends Toolbar {
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.ll_my_cash)
    LinearLayout mLlMyCash;
    @BindView(R.id.tv_cash)
    TextView mTvCash;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private OnToolbarClickListener onToolbarClickListener;
    boolean menuSwitch = false;
    boolean closeSwitch = false;
    boolean addSwitch = false;
    boolean moreSwitch = false;

    /* loaded from: classes.dex */
    public interface OnToolbarClickListener {
        void onToolbarAddClicked(View view);

        void onToolbarCloseClicked(View view);

        void onToolbarDrawerClicked(View view);

        void onToolbarMenuClicked(View view);
    }

    public BaseMainToolBar(Context context) {
        super(context);
        inflate();
    }

    public BaseMainToolBar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate();
    }

    public BaseMainToolBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        inflate();
    }

    public void setToolbarVisibleState(boolean z, boolean z2, boolean z3, boolean z4) {
        this.menuSwitch = z;
        this.closeSwitch = z2;
        this.addSwitch = z3;
        this.moreSwitch = z4;
        this.mLlMyCash.setVisibility(8);
        ToolbarState();
    }

    public void setTypePlan(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        this.menuSwitch = z;
        this.closeSwitch = z2;
        this.addSwitch = z3;
        this.moreSwitch = z4;
        this.mLlMyCash.setVisibility(0);
        this.mTvCash.setText(String.format("내 캐쉬 : %,d", Integer.valueOf(i)));
        ToolbarState();
    }

    public void inflate() {
        inflate(getContext(), R.layout.layout_main_toolbar, this);
        ButterKnife.bind(this);
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
    }

    @Override // android.support.v7.widget.Toolbar
    public void setTitle(@StringRes int i) {
        if (i == 0) {
            this.mTvTitle.setVisibility(4);
        } else {
            this.mTvTitle.setVisibility(0);
            this.mTvTitle.setText(i);
        }
        this.mTvTitle.setGravity(0);
        this.mIvClose.setImageResource(R.drawable.btn_close_yel);
        Utils.setTextColorGradient(getContext(), this.mTvTitle);
        this.mIvMore.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.base.widgets.BaseMainToolBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    @Override // android.support.v7.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mTvTitle.setVisibility(4);
        } else {
            this.mTvTitle.setVisibility(0);
            this.mTvTitle.setText(charSequence);
        }
        this.mTvTitle.setGravity(0);
        this.mIvClose.setImageResource(R.drawable.btn_close_yel);
        Utils.setTextColorGradient(getContext(), this.mTvTitle);
    }

    public void setMyInfo(boolean z) {
        this.mTvTitle.setGravity(17);
        if (z) {
            this.mTvTitle.setText(R.string.settings_my_info);
        } else {
            this.mTvTitle.setText(R.string.settings_push);
        }
        setToolbarVisibleState(false, true, false, false);
        this.mIvClose.setImageResource(R.drawable.btn_to_back);
    }

    private TextView getTitleTextView() {
        if (this.mTvTitle == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof TextView) {
                    this.mTvTitle = (TextView) childAt;
                }
            }
        }
        return this.mTvTitle;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (TextUtils.isEmpty(this.mTvTitle.getText().toString())) {
            setTitle(" ");
        }
    }

    public void ToolbarState() {
        if (this.menuSwitch) {
            this.mIvMenu.setVisibility(0);
        } else {
            this.mIvMenu.setVisibility(8);
        }
        if (this.closeSwitch) {
            this.mIvClose.setVisibility(0);
        } else {
            this.mIvClose.setVisibility(8);
        }
        if (this.addSwitch) {
            this.mIvAdd.setVisibility(0);
        } else {
            this.mIvAdd.setVisibility(8);
        }
        if (this.moreSwitch) {
            this.mIvMore.setVisibility(0);
        } else {
            this.mIvMore.setVisibility(8);
        }
    }

    public ImageView getIvMoreView() {
        return this.mIvMore;
    }

    @OnClick({R.id.iv_menu, R.id.iv_close, R.id.iv_add, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add /* 2131361951 */:
                if (this.onToolbarClickListener != null) {
                    this.onToolbarClickListener.onToolbarAddClicked(view);
                    return;
                }
                return;
            case R.id.iv_close /* 2131361954 */:
                if (this.onToolbarClickListener != null) {
                    this.onToolbarClickListener.onToolbarCloseClicked(view);
                    return;
                }
                return;
            case R.id.iv_menu /* 2131361978 */:
                if (this.onToolbarClickListener != null) {
                    this.onToolbarClickListener.onToolbarDrawerClicked(view);
                    return;
                }
                return;
            case R.id.iv_more /* 2131361979 */:
                if (this.onToolbarClickListener != null) {
                    this.onToolbarClickListener.onToolbarMenuClicked(view);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public OnToolbarClickListener getOnToolbarClickListener() {
        return this.onToolbarClickListener;
    }

    public void setOnToolbarClickListener(OnToolbarClickListener onToolbarClickListener) {
        this.onToolbarClickListener = onToolbarClickListener;
    }
}
