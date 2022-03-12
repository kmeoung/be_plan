package kr.timehub.beplan.entry;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class BeanProjectRequest {
    private ArrayList<BeanProjectRequestContent> list;
    private String title;

    public BeanProjectRequest() {
    }

    public BeanProjectRequest(String str) {
        this.title = str;
    }

    public ArrayList<BeanProjectRequestContent> getList() {
        return this.list;
    }

    public void setList(ArrayList<BeanProjectRequestContent> arrayList) {
        this.list = arrayList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
