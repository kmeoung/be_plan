package com.nguyenhoanglam.imagepicker.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nguyenhoanglam.imagepicker.R;

/* loaded from: classes.dex */
public class SnackBarView extends RelativeLayout {
    private static final int ANIM_DURATION = 200;
    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();
    private Button actionButton;
    private boolean isShowing;
    private TextView messageText;

    public SnackBarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SnackBarView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SnackBarView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.imagepicker_snackbar, this);
        if (!isInEditMode()) {
            setBackgroundColor(Color.parseColor("#323232"));
            setTranslationY(getHeight());
            setAlpha(0.0f);
            this.isShowing = false;
            int convertDpToPixels = convertDpToPixels(context, 24.0f);
            int convertDpToPixels2 = convertDpToPixels(context, 14.0f);
            setPadding(convertDpToPixels, convertDpToPixels2, convertDpToPixels, convertDpToPixels2);
            this.messageText = (TextView) findViewById(R.id.text_snackbar_message);
            this.actionButton = (Button) findViewById(R.id.button_snackbar_action);
        }
    }

    private void setText(int i) {
        this.messageText.setText(i);
    }

    private void setOnActionClickListener(String str, final View.OnClickListener onClickListener) {
        this.actionButton.setText(str);
        this.actionButton.setOnClickListener(new View.OnClickListener() { // from class: com.nguyenhoanglam.imagepicker.widget.SnackBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(final View view) {
                SnackBarView.this.hide(new Runnable() { // from class: com.nguyenhoanglam.imagepicker.widget.SnackBarView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        onClickListener.onClick(view);
                    }
                });
            }
        });
    }

    public void show(int i, View.OnClickListener onClickListener) {
        setText(i);
        setOnActionClickListener(getContext().getString(R.string.imagepicker_action_ok), onClickListener);
        ViewCompat.animate(this).translationY(0.0f).setDuration(200L).setInterpolator(INTERPOLATOR).alpha(1.0f);
        this.isShowing = true;
    }

    public void hide(Runnable runnable) {
        ViewCompat.animate(this).translationY(getHeight()).setDuration(200L).alpha(0.5f).withEndAction(runnable);
        this.isShowing = false;
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    private int convertDpToPixels(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}
