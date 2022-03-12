package com.nguyenhoanglam.imagepicker.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.model.Config;

/* loaded from: classes.dex */
public class ImagePickerToolbar extends RelativeLayout {
    private AppCompatImageView backImage;
    private AppCompatImageView cameraImage;
    private TextView doneText;
    private TextView titleText;

    public ImagePickerToolbar(Context context) {
        super(context);
        init(context);
    }

    public ImagePickerToolbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ImagePickerToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.imagepicker_toolbar, this);
        if (!isInEditMode()) {
            this.titleText = (TextView) findViewById(R.id.text_toolbar_title);
            this.doneText = (TextView) findViewById(R.id.text_toolbar_done);
            this.backImage = (AppCompatImageView) findViewById(R.id.image_toolbar_back);
            this.cameraImage = (AppCompatImageView) findViewById(R.id.image_toolbar_camera);
        }
    }

    public void config(Config config) {
        setBackgroundColor(config.getToolbarColor());
        this.titleText.setText(config.isFolderMode() ? config.getFolderTitle() : config.getImageTitle());
        this.titleText.setTextColor(config.getToolbarTextColor());
        this.doneText.setText(config.getDoneTitle());
        this.doneText.setTextColor(config.getToolbarTextColor());
        this.backImage.setColorFilter(config.getToolbarIconColor());
        this.cameraImage.setColorFilter(config.getToolbarIconColor());
        this.cameraImage.setVisibility(config.isShowCamera() ? 0 : 8);
        this.doneText.setVisibility(8);
    }

    public void setTitle(String str) {
        this.titleText.setText(str);
    }

    public void showDoneButton(boolean z) {
        this.doneText.setVisibility(z ? 0 : 8);
    }

    public void setOnBackClickListener(View.OnClickListener onClickListener) {
        this.backImage.setOnClickListener(onClickListener);
    }

    public void setOnCameraClickListener(View.OnClickListener onClickListener) {
        this.cameraImage.setOnClickListener(onClickListener);
    }

    public void setOnDoneClickListener(View.OnClickListener onClickListener) {
        this.doneText.setOnClickListener(onClickListener);
    }
}
