package kr.timehub.beplan.entry;

/* loaded from: classes.dex */
public class BeanTempFormCategory {
    private int count;
    private String title;

    public BeanTempFormCategory(String str, int i) {
        this.title = str;
        this.count = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }
}
