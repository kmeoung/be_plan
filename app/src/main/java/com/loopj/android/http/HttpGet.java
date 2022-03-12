package com.loopj.android.http;

import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import java.net.URI;

/* loaded from: classes.dex */
public final class HttpGet extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "GET";

    @Override // cz.msebera.android.httpclient.client.methods.HttpRequestBase, cz.msebera.android.httpclient.client.methods.HttpUriRequest
    public String getMethod() {
        return "GET";
    }

    public HttpGet() {
    }

    public HttpGet(URI uri) {
        setURI(uri);
    }

    public HttpGet(String str) {
        setURI(URI.create(str));
    }
}
