package kr.timehub.beplan.entry.database;

import io.realm.DbStopWatchRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

/* loaded from: classes.dex */
public class DbStopWatch extends RealmObject implements DbStopWatchRealmProxyInterface {
    private int CGSEQ;
    private int PSEQ;
    private int TSEQ;
    private String cgTitle;
    private long ell;
    private long endTime;
    private String pTitle;
    private long startTime;
    private String tTitle;

    public int realmGet$CGSEQ() {
        return this.CGSEQ;
    }

    public int realmGet$PSEQ() {
        return this.PSEQ;
    }

    public int realmGet$TSEQ() {
        return this.TSEQ;
    }

    public String realmGet$cgTitle() {
        return this.cgTitle;
    }

    public long realmGet$ell() {
        return this.ell;
    }

    public long realmGet$endTime() {
        return this.endTime;
    }

    public String realmGet$pTitle() {
        return this.pTitle;
    }

    public long realmGet$startTime() {
        return this.startTime;
    }

    public String realmGet$tTitle() {
        return this.tTitle;
    }

    public void realmSet$CGSEQ(int i) {
        this.CGSEQ = i;
    }

    public void realmSet$PSEQ(int i) {
        this.PSEQ = i;
    }

    public void realmSet$TSEQ(int i) {
        this.TSEQ = i;
    }

    public void realmSet$cgTitle(String str) {
        this.cgTitle = str;
    }

    public void realmSet$ell(long j) {
        this.ell = j;
    }

    public void realmSet$endTime(long j) {
        this.endTime = j;
    }

    public void realmSet$pTitle(String str) {
        this.pTitle = str;
    }

    public void realmSet$startTime(long j) {
        this.startTime = j;
    }

    public void realmSet$tTitle(String str) {
        this.tTitle = str;
    }

    public DbStopWatch() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public DbStopWatch(int i, int i2, int i3, String str, String str2, String str3, long j, long j2, long j3) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$PSEQ(i);
        realmSet$CGSEQ(i2);
        realmSet$TSEQ(i3);
        realmSet$pTitle(str);
        realmSet$cgTitle(str2);
        realmSet$tTitle(str3);
        realmSet$startTime(j);
        realmSet$endTime(j2);
        realmSet$ell(j3);
    }

    public int getPSEQ() {
        return realmGet$PSEQ();
    }

    public void setPSEQ(int i) {
        realmSet$PSEQ(i);
    }

    public int getCGSEQ() {
        return realmGet$CGSEQ();
    }

    public void setCGSEQ(int i) {
        realmSet$CGSEQ(i);
    }

    public int getTSEQ() {
        return realmGet$TSEQ();
    }

    public void setTSEQ(int i) {
        realmSet$TSEQ(i);
    }

    public String getpTitle() {
        return realmGet$pTitle();
    }

    public void setpTitle(String str) {
        realmSet$pTitle(str);
    }

    public String getCgTitle() {
        return realmGet$cgTitle();
    }

    public void setCgTitle(String str) {
        realmSet$cgTitle(str);
    }

    public String gettTitle() {
        return realmGet$tTitle();
    }

    public void settTitle(String str) {
        realmSet$tTitle(str);
    }

    public long getStartTime() {
        return realmGet$startTime();
    }

    public void setStartTime(long j) {
        realmSet$startTime(j);
    }

    public long getEndTime() {
        return realmGet$endTime();
    }

    public void setEndTime(long j) {
        realmSet$endTime(j);
    }

    public long getEll() {
        return realmGet$ell();
    }

    public void setEll(long j) {
        realmSet$ell(j);
    }
}
