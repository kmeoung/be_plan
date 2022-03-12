package com.nguyenhoanglam.imagepicker.model;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class Folder {
    private String folderName;
    private ArrayList<Image> images = new ArrayList<>();

    public Folder(String str) {
        this.folderName = str;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public ArrayList<Image> getImages() {
        return this.images;
    }

    public void setImages(ArrayList<Image> arrayList) {
        this.images = arrayList;
    }
}
