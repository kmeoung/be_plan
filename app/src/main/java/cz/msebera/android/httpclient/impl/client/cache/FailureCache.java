package cz.msebera.android.httpclient.impl.client.cache;

/* loaded from: classes.dex */
public interface FailureCache {
    int getErrorCount(String str);

    void increaseErrorCount(String str);

    void resetErrorCount(String str);
}
