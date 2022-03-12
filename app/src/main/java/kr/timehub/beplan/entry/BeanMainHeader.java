package kr.timehub.beplan.entry;

/* loaded from: classes.dex */
public class BeanMainHeader {
    private int myNotificationSize;
    private int myProjectSize;
    private int myWorkSize;

    public BeanMainHeader(int i, int i2, int i3) {
        this.myProjectSize = i;
        this.myWorkSize = i2;
        this.myNotificationSize = i3;
    }

    public int getMyProjectSize() {
        return this.myProjectSize;
    }

    public void setMyProjectSize(int i) {
        this.myProjectSize = i;
    }

    public int getMyWorkSize() {
        return this.myWorkSize;
    }

    public void setMyWorkSize(int i) {
        this.myWorkSize = i;
    }

    public int getMyNotificationSize() {
        return this.myNotificationSize;
    }

    public void setMyNotificationSize(int i) {
        this.myNotificationSize = i;
    }
}
