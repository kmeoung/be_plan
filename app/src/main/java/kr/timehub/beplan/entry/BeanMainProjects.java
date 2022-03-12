package kr.timehub.beplan.entry;

/* loaded from: classes.dex */
public class BeanMainProjects {
    private int seq;
    private String title;

    public BeanMainProjects() {
    }

    public BeanMainProjects(String str, int i) {
        this.title = str;
        this.seq = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int i) {
        this.seq = i;
    }
}
