package cz.msebera.android.httpclient.concurrent;

/* loaded from: classes.dex */
public interface FutureCallback<T> {
    void cancelled();

    void completed(T t);

    void failed(Exception exc);
}
