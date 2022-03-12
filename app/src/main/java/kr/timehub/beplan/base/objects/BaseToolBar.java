package kr.timehub.beplan.base.objects;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class BaseToolBar extends Toolbar {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    OnRightClickListener onRightClickListener;

    /* loaded from: classes.dex */
    public interface OnRightClickListener {
        void rightClicked();
    }

    public BaseToolBar(Context context) {
        super(context);
        inflate();
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate();
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        inflate();
    }

    public void inflate() {
        inflate(getContext(), R.layout.layout_intro_toolbar, this);
        ButterKnife.bind(this);
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
    }

    private void setTitleFontAll() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof android.widget.TextView) {
                ((android.widget.TextView) viewGroup.getChildAt(i)).setTypeface(Comm_Params.FONTS.get(1));
            }
        }
    }

    @Override // android.support.v7.widget.Toolbar
    public void setTitle(@StringRes int i) {
        super.setTitle(i);
    }

    @Override // android.support.v7.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTitle(" ");
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.mIvNext.setVisibility(0);
        this.onRightClickListener = onRightClickListener;
    }

    @OnClick({R.id.iv_back, R.id.iv_next})
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.iv_back) {
            if (id == R.id.iv_next && this.onRightClickListener != null) {
                this.onRightClickListener.rightClicked();
            }
        } else if (getContext() instanceof Activity) {
            ((Activity) getContext()).onBackPressed();
        }
    }
}
