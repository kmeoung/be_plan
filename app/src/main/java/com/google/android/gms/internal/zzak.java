package com.google.android.gms.internal;

import cz.msebera.android.httpclient.client.methods.HttpPatch;
import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/* loaded from: classes.dex */
public final class zzak extends HttpEntityEnclosingRequestBase {
    public zzak() {
    }

    public zzak(String str) {
        setURI(URI.create(str));
    }

    public final String getMethod() {
        return HttpPatch.METHOD_NAME;
    }
}
