package kr.timehub.beplan.entry;

import java.io.Serializable;

/* loaded from: classes.dex */
public class BeanStopWatch implements Serializable {
    private int CGSEQ;
    private int PSEQ;
    private int TSEQ;
    private String cgTitle;
    private long ell;
    private long endTime;
    private String pTitle;
    private long startTime;
    private String tTitle;

    public BeanStopWatch(int i, int i2, int i3, String str, String str2, String str3, long j, long j2, long j3) {
        this.PSEQ = i;
        this.CGSEQ = i2;
        this.TSEQ = i3;
        this.pTitle = str;
        this.cgTitle = str2;
        this.tTitle = str3;
        this.startTime = j;
        this.endTime = j2;
        this.ell = j3;
    }

    public int getPSEQ() {
        return this.PSEQ;
    }

    public void setPSEQ(int i) {
        this.PSEQ = i;
    }

    public int getCGSEQ() {
        return this.CGSEQ;
    }

    public void setCGSEQ(int i) {
        this.CGSEQ = i;
    }

    public int getTSEQ() {
        return this.TSEQ;
    }

    public void setTSEQ(int i) {
        this.TSEQ = i;
    }

    public String getpTitle() {
        return this.pTitle;
    }

    public void setpTitle(String str) {
        this.pTitle = str;
    }

    public String getCgTitle() {
        return this.cgTitle;
    }

    public void setCgTitle(String str) {
        this.cgTitle = str;
    }

    public String gettTitle() {
        return this.tTitle;
    }

    public void settTitle(String str) {
        this.tTitle = str;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public long getEll() {
        return this.ell;
    }

    public void setEll(long j) {
        this.ell = j;
    }
}
