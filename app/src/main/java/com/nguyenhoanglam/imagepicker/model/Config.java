package com.nguyenhoanglam.imagepicker.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Config implements Parcelable {
    public static final Parcelable.Creator<Config> CREATOR = new Parcelable.Creator<Config>() { // from class: com.nguyenhoanglam.imagepicker.model.Config.1
        @Override // android.os.Parcelable.Creator
        public Config createFromParcel(Parcel parcel) {
            return new Config(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Config[] newArray(int i) {
            return new Config[i];
        }
    };
    public static final String EXTRA_CONFIG = "ImagePickerConfig";
    public static final String EXTRA_IMAGES = "ImagePickerImages";
    public static final int MAX_SIZE = Integer.MAX_VALUE;
    public static final int RC_CAMERA_PERMISSION = 103;
    public static final int RC_CAPTURE_IMAGE = 101;
    public static final int RC_PICK_IMAGES = 100;
    public static final int RC_WRITE_EXTERNAL_STORAGE_PERMISSION = 102;
    private String backgroundColor;
    private String doneTitle;
    private String folderTitle;
    private String imageTitle;
    private boolean isCameraOnly;
    private boolean isFolderMode;
    private boolean isKeepScreenOn;
    private boolean isMultipleMode;
    private boolean isShowCamera;
    private String limitMessage;
    private int maxSize;
    private String progressBarColor;
    private SavePath savePath;
    private ArrayList<Image> selectedImages;
    private String statusBarColor;
    private String toolbarColor;
    private String toolbarIconColor;
    private String toolbarTextColor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Config() {
    }

    protected Config(Parcel parcel) {
        this.toolbarColor = parcel.readString();
        this.statusBarColor = parcel.readString();
        this.toolbarTextColor = parcel.readString();
        this.toolbarIconColor = parcel.readString();
        this.progressBarColor = parcel.readString();
        this.backgroundColor = parcel.readString();
        boolean z = false;
        this.isCameraOnly = parcel.readByte() != 0;
        this.isMultipleMode = parcel.readByte() != 0;
        this.isFolderMode = parcel.readByte() != 0;
        this.isShowCamera = parcel.readByte() != 0;
        this.maxSize = parcel.readInt();
        this.doneTitle = parcel.readString();
        this.folderTitle = parcel.readString();
        this.imageTitle = parcel.readString();
        this.limitMessage = parcel.readString();
        this.savePath = (SavePath) parcel.readParcelable(SavePath.class.getClassLoader());
        this.isKeepScreenOn = parcel.readByte() != 0 ? true : z;
        this.selectedImages = parcel.createTypedArrayList(Image.CREATOR);
    }

    public int getToolbarColor() {
        if (TextUtils.isEmpty(this.toolbarColor)) {
            return Color.parseColor("#212121");
        }
        return Color.parseColor(this.toolbarColor);
    }

    public void setToolbarColor(String str) {
        this.toolbarColor = str;
    }

    public int getStatusBarColor() {
        if (TextUtils.isEmpty(this.statusBarColor)) {
            return Color.parseColor("#000000");
        }
        return Color.parseColor(this.statusBarColor);
    }

    public void setStatusBarColor(String str) {
        this.statusBarColor = str;
    }

    public int getToolbarTextColor() {
        if (TextUtils.isEmpty(this.toolbarTextColor)) {
            return Color.parseColor("#FFFFFF");
        }
        return Color.parseColor(this.toolbarTextColor);
    }

    public void setToolbarTextColor(String str) {
        this.toolbarTextColor = str;
    }

    public int getToolbarIconColor() {
        if (TextUtils.isEmpty(this.toolbarIconColor)) {
            return Color.parseColor("#FFFFFF");
        }
        return Color.parseColor(this.toolbarIconColor);
    }

    public void setToolbarIconColor(String str) {
        this.toolbarIconColor = str;
    }

    public int getProgressBarColor() {
        if (TextUtils.isEmpty(this.progressBarColor)) {
            return Color.parseColor("#4CAF50");
        }
        return Color.parseColor(this.progressBarColor);
    }

    public void setProgressBarColor(String str) {
        this.progressBarColor = str;
    }

    public int getBackgroundColor() {
        if (TextUtils.isEmpty(this.backgroundColor)) {
            return Color.parseColor("#FFFFFF");
        }
        return Color.parseColor(this.backgroundColor);
    }

    public void setBackgroundColor(String str) {
        this.backgroundColor = str;
    }

    public boolean isCameraOnly() {
        return this.isCameraOnly;
    }

    public void setCameraOnly(boolean z) {
        this.isCameraOnly = z;
    }

    public boolean isMultipleMode() {
        return this.isMultipleMode;
    }

    public void setMultipleMode(boolean z) {
        this.isMultipleMode = z;
    }

    public boolean isFolderMode() {
        return this.isFolderMode;
    }

    public void setFolderMode(boolean z) {
        this.isFolderMode = z;
    }

    public boolean isShowCamera() {
        return this.isShowCamera;
    }

    public void setShowCamera(boolean z) {
        this.isShowCamera = z;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int i) {
        this.maxSize = i;
    }

    public String getDoneTitle() {
        return this.doneTitle;
    }

    public void setDoneTitle(String str) {
        this.doneTitle = str;
    }

    public String getFolderTitle() {
        return this.folderTitle;
    }

    public void setFolderTitle(String str) {
        this.folderTitle = str;
    }

    public String getImageTitle() {
        return this.imageTitle;
    }

    public void setImageTitle(String str) {
        this.imageTitle = str;
    }

    public String getLimitMessage() {
        return this.limitMessage;
    }

    public void setLimitMessage(String str) {
        this.limitMessage = str;
    }

    public SavePath getSavePath() {
        return this.savePath;
    }

    public void setSavePath(SavePath savePath) {
        this.savePath = savePath;
    }

    public boolean isKeepScreenOn() {
        return this.isKeepScreenOn;
    }

    public void setKeepScreenOn(boolean z) {
        this.isKeepScreenOn = z;
    }

    public ArrayList<Image> getSelectedImages() {
        return this.selectedImages;
    }

    public void setSelectedImages(ArrayList<Image> arrayList) {
        this.selectedImages = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.toolbarColor);
        parcel.writeString(this.statusBarColor);
        parcel.writeString(this.toolbarTextColor);
        parcel.writeString(this.toolbarIconColor);
        parcel.writeString(this.progressBarColor);
        parcel.writeString(this.backgroundColor);
        parcel.writeByte(this.isCameraOnly ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isMultipleMode ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isFolderMode ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isShowCamera ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.maxSize);
        parcel.writeString(this.doneTitle);
        parcel.writeString(this.folderTitle);
        parcel.writeString(this.imageTitle);
        parcel.writeString(this.limitMessage);
        parcel.writeParcelable(this.savePath, i);
        parcel.writeByte(this.isKeepScreenOn ? (byte) 1 : (byte) 0);
        parcel.writeTypedList(this.selectedImages);
    }
}
