package com.nguyenhoanglam.imagepicker.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Image implements Parcelable {
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() { // from class: com.nguyenhoanglam.imagepicker.model.Image.1
        @Override // android.os.Parcelable.Creator
        public Image createFromParcel(Parcel parcel) {
            return new Image(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Image[] newArray(int i) {
            return new Image[i];
        }
    };
    private long id;
    private String name;
    private String path;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Image(long j, String str, String str2) {
        this.id = j;
        this.name = str;
        this.path = str2;
    }

    protected Image(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.path = parcel.readString();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Image) obj).getPath().equalsIgnoreCase(getPath());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.path);
    }
}
