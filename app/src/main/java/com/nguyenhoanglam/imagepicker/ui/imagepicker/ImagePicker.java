package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import com.nguyenhoanglam.imagepicker.R;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.model.SavePath;
import com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ImagePicker {
    protected Config config;

    public ImagePicker(Builder builder) {
        this.config = builder.config;
    }

    public static Builder with(Activity activity) {
        return new ActivityBuilder(activity);
    }

    public static Builder with(Fragment fragment) {
        return new FragmentBuilder(fragment);
    }

    /* loaded from: classes.dex */
    public static class ActivityBuilder extends Builder {
        private Activity activity;

        public ActivityBuilder(Activity activity) {
            super(activity);
            this.activity = activity;
        }

        @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker.Builder
        public void start() {
            Intent intent = getIntent();
            if (!this.config.isCameraOnly()) {
                this.activity.startActivityForResult(intent, 100);
                return;
            }
            this.activity.overridePendingTransition(0, 0);
            this.activity.startActivityForResult(intent, 100);
        }

        @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker.Builder
        public Intent getIntent() {
            if (!this.config.isCameraOnly()) {
                Intent intent = new Intent(this.activity, ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, this.config);
                return intent;
            }
            Intent intent2 = new Intent(this.activity, CameraActivty.class);
            intent2.putExtra(Config.EXTRA_CONFIG, this.config);
            intent2.addFlags(65536);
            return intent2;
        }
    }

    /* loaded from: classes.dex */
    public static class FragmentBuilder extends Builder {
        private Fragment fragment;

        public FragmentBuilder(Fragment fragment) {
            super(fragment);
            this.fragment = fragment;
        }

        @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker.Builder
        public void start() {
            Intent intent = getIntent();
            if (!this.config.isCameraOnly()) {
                this.fragment.startActivityForResult(intent, 100);
                return;
            }
            this.fragment.getActivity().overridePendingTransition(0, 0);
            this.fragment.startActivityForResult(intent, 100);
        }

        @Override // com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker.Builder
        public Intent getIntent() {
            if (!this.config.isCameraOnly()) {
                Intent intent = new Intent(this.fragment.getActivity(), ImagePickerActivity.class);
                intent.putExtra(Config.EXTRA_CONFIG, this.config);
                return intent;
            }
            Intent intent2 = new Intent(this.fragment.getActivity(), CameraActivty.class);
            intent2.putExtra(Config.EXTRA_CONFIG, this.config);
            intent2.addFlags(65536);
            return intent2;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Builder extends BaseBuilder {
        public abstract Intent getIntent();

        public abstract void start();

        public Builder(Activity activity) {
            super(activity);
        }

        public Builder(Fragment fragment) {
            super(fragment.getContext());
        }

        public Builder setToolbarColor(String str) {
            this.config.setToolbarColor(str);
            return this;
        }

        public Builder setStatusBarColor(String str) {
            this.config.setStatusBarColor(str);
            return this;
        }

        public Builder setToolbarTextColor(String str) {
            this.config.setToolbarTextColor(str);
            return this;
        }

        public Builder setToolbarIconColor(String str) {
            this.config.setToolbarIconColor(str);
            return this;
        }

        public Builder setProgressBarColor(String str) {
            this.config.setProgressBarColor(str);
            return this;
        }

        public Builder setBackgroundColor(String str) {
            this.config.setBackgroundColor(str);
            return this;
        }

        public Builder setCameraOnly(boolean z) {
            this.config.setCameraOnly(z);
            return this;
        }

        public Builder setMultipleMode(boolean z) {
            this.config.setMultipleMode(z);
            return this;
        }

        public Builder setFolderMode(boolean z) {
            this.config.setFolderMode(z);
            return this;
        }

        public Builder setShowCamera(boolean z) {
            this.config.setShowCamera(z);
            return this;
        }

        public Builder setMaxSize(int i) {
            this.config.setMaxSize(i);
            return this;
        }

        public Builder setDoneTitle(String str) {
            this.config.setDoneTitle(str);
            return this;
        }

        public Builder setFolderTitle(String str) {
            this.config.setFolderTitle(str);
            return this;
        }

        public Builder setImageTitle(String str) {
            this.config.setImageTitle(str);
            return this;
        }

        public Builder setLimitMessage(String str) {
            this.config.setLimitMessage(str);
            return this;
        }

        public Builder setSavePath(String str) {
            this.config.setSavePath(new SavePath(str, false));
            return this;
        }

        public Builder setKeepScreenOn(boolean z) {
            this.config.setKeepScreenOn(z);
            return this;
        }

        public Builder setSelectedImages(ArrayList<Image> arrayList) {
            this.config.setSelectedImages(arrayList);
            return this;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class BaseBuilder {
        protected Config config = new Config();

        public BaseBuilder(Context context) {
            Resources resources = context.getResources();
            this.config.setCameraOnly(false);
            this.config.setMultipleMode(true);
            this.config.setFolderMode(true);
            this.config.setShowCamera(true);
            this.config.setMaxSize(Integer.MAX_VALUE);
            this.config.setDoneTitle(resources.getString(R.string.imagepicker_action_done));
            this.config.setFolderTitle(resources.getString(R.string.imagepicker_title_folder));
            this.config.setImageTitle(resources.getString(R.string.imagepicker_title_image));
            this.config.setLimitMessage(resources.getString(R.string.imagepicker_msg_limit_images));
            this.config.setSavePath(SavePath.DEFAULT);
            this.config.setKeepScreenOn(false);
            this.config.setSelectedImages(new ArrayList<>());
        }
    }
}
