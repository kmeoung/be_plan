package com.nguyenhoanglam.imagepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SavePath implements Parcelable {
    private final boolean isFullPath;
    private final String path;
    public static final SavePath DEFAULT = new SavePath("Camera", false);
    public static final Parcelable.Creator<SavePath> CREATOR = new Parcelable.Creator<SavePath>() { // from class: com.nguyenhoanglam.imagepicker.model.SavePath.1
        @Override // android.os.Parcelable.Creator
        public SavePath createFromParcel(Parcel parcel) {
            return new SavePath(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SavePath[] newArray(int i) {
            return new SavePath[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SavePath(String str, boolean z) {
        this.path = str;
        this.isFullPath = z;
    }

    protected SavePath(Parcel parcel) {
        this.path = parcel.readString();
        this.isFullPath = parcel.readByte() != 0;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isFullPath() {
        return this.isFullPath;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.path);
        parcel.writeByte(this.isFullPath ? (byte) 1 : (byte) 0);
    }
}
