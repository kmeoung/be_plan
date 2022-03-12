package kr.timehub.beplan.entry;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class BeanRank {
    private ArrayList<BeanRankItem> list;
    private String title;

    public BeanRank() {
    }

    public BeanRank(String str) {
        this.title = str;
    }

    public BeanRank(String str, ArrayList<BeanRankItem> arrayList) {
        this.title = str;
        this.list = arrayList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ArrayList<BeanRankItem> getList() {
        return this.list;
    }

    public void setList(ArrayList<BeanRankItem> arrayList) {
        this.list = arrayList;
    }
}
