package kr.timehub.beplan.entry;

import java.util.ArrayList;
import kr.timehub.beplan.entry.common.BeanBusinessContent;

/* loaded from: classes.dex */
public class BeanProject {
    private ArrayList<BeanBusinessContent> list;
    private String title;

    public BeanProject() {
    }

    public BeanProject(String str) {
        this.title = str;
    }

    public ArrayList<BeanBusinessContent> getList() {
        return this.list;
    }

    public void setList(ArrayList<BeanBusinessContent> arrayList) {
        this.list = arrayList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
