package cz.msebera.android.httpclient.conn;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.concurrent.Cancellable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public interface ConnectionRequest extends Cancellable {
    HttpClientConnection get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException;
}
