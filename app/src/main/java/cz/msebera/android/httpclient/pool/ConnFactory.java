package cz.msebera.android.httpclient.pool;

import java.io.IOException;

/* loaded from: classes.dex */
public interface ConnFactory<T, C> {
    C create(T t) throws IOException;
}
