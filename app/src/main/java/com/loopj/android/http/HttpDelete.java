package com.loopj.android.http;

import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import java.net.URI;

/* loaded from: classes.dex */
public final class HttpDelete extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    @Override // cz.msebera.android.httpclient.client.methods.HttpRequestBase, cz.msebera.android.httpclient.client.methods.HttpUriRequest
    public String getMethod() {
        return "DELETE";
    }

    public HttpDelete() {
    }

    public HttpDelete(URI uri) {
        setURI(uri);
    }

    public HttpDelete(String str) {
        setURI(URI.create(str));
    }
}
