package com.naver.temy123.baseproject.base.Entry;

/* loaded from: classes.dex */
public class BeanSpinnerExtends {
    private int seq;
    private String text;

    public BeanSpinnerExtends() {
    }

    public BeanSpinnerExtends(int i, String str) {
        this.seq = i;
        this.text = str;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int i) {
        this.seq = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }
}
