package kr.timehub.beplan.entry.plugin;

/* loaded from: classes.dex */
public class BeanViewPager {
    private int CACSEQ;
    private String TACSEQ;
    private String imageUrl;
    private boolean isDelete;
    private boolean isEdit = false;
    private String serverUrl;
    private String type;
    private String webUrl;

    public BeanViewPager() {
    }

    public BeanViewPager(String str, String str2, String str3, String str4, boolean z) {
        this.type = str;
        this.webUrl = str2;
        this.serverUrl = str3;
        this.imageUrl = str4;
        this.isDelete = z;
    }

    public String getTACSEQ() {
        return this.TACSEQ;
    }

    public void setTACSEQ(String str) {
        this.TACSEQ = str;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String str) {
        this.webUrl = str;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public void setServerUrl(String str) {
        this.serverUrl = str;
    }

    public boolean isDelete() {
        return this.isDelete;
    }

    public void setDelete(boolean z) {
        this.isDelete = z;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public int getCACSEQ() {
        return this.CACSEQ;
    }

    public void setCACSEQ(int i) {
        this.CACSEQ = i;
    }
}
