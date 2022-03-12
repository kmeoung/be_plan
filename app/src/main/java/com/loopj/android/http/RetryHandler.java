package com.loopj.android.http;

import android.os.SystemClock;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.SSLException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class RetryHandler implements HttpRequestRetryHandler {
    private final int maxRetries;
    private final int retrySleepTimeMS;
    private static final HashSet<Class<?>> exceptionWhitelist = new HashSet<>();
    private static final HashSet<Class<?>> exceptionBlacklist = new HashSet<>();

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLException.class);
    }

    public RetryHandler(int i, int i2) {
        this.maxRetries = i;
        this.retrySleepTimeMS = i2;
    }

    public static void addClassToWhitelist(Class<?> cls) {
        exceptionWhitelist.add(cls);
    }

    public static void addClassToBlacklist(Class<?> cls) {
        exceptionBlacklist.add(cls);
    }

    @Override // cz.msebera.android.httpclient.client.HttpRequestRetryHandler
    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        Boolean bool = (Boolean) httpContext.getAttribute("http.request_sent");
        boolean z = true;
        if (bool == null || !bool.booleanValue()) {
        }
        if (i > this.maxRetries || (!isInList(exceptionWhitelist, iOException) && isInList(exceptionBlacklist, iOException))) {
            z = false;
        }
        if (z && ((HttpUriRequest) httpContext.getAttribute("http.request")) == null) {
            return false;
        }
        if (z) {
            SystemClock.sleep(this.retrySleepTimeMS);
        } else {
            ThrowableExtension.printStackTrace(iOException);
        }
        return z;
    }

    protected boolean isInList(HashSet<Class<?>> hashSet, Throwable th) {
        Iterator<Class<?>> it = hashSet.iterator();
        while (it.hasNext()) {
            if (it.next().isInstance(th)) {
                return true;
            }
        }
        return false;
    }
}
