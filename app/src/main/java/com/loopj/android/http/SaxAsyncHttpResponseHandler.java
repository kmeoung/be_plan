package com.loopj.android.http;

import cz.msebera.android.httpclient.Header;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public abstract class SaxAsyncHttpResponseHandler<T extends DefaultHandler> extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "SaxAsyncHttpRH";
    private T handler;

    public abstract void onFailure(int i, Header[] headerArr, T t);

    public abstract void onSuccess(int i, Header[] headerArr, T t);

    public SaxAsyncHttpResponseHandler(T t) {
        this.handler = null;
        if (t == null) {
            throw new Error("null instance of <T extends DefaultHandler> passed to constructor");
        }
        this.handler = t;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0030, code lost:
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
        if (r2 == null) goto L_0x0068;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005b, code lost:
        if (r2 == null) goto L_0x0068;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002e, code lost:
        if (r2 != null) goto L_0x0030;
     */
    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected byte[] getResponseData(cz.msebera.android.httpclient.HttpEntity r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto L_0x0068
            java.io.InputStream r7 = r7.getContent()
            if (r7 == 0) goto L_0x0068
            javax.xml.parsers.SAXParserFactory r1 = javax.xml.parsers.SAXParserFactory.newInstance()     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            javax.xml.parsers.SAXParser r1 = r1.newSAXParser()     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            org.xml.sax.XMLReader r1 = r1.getXMLReader()     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            T extends org.xml.sax.helpers.DefaultHandler r2 = r6.handler     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            r1.setContentHandler(r2)     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            java.lang.String r3 = r6.getCharset()     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            r2.<init>(r7, r3)     // Catch: all -> 0x0038, ParserConfigurationException -> 0x003c, SAXException -> 0x004d
            org.xml.sax.InputSource r3 = new org.xml.sax.InputSource     // Catch: ParserConfigurationException -> 0x0034, SAXException -> 0x0036, all -> 0x005e
            r3.<init>(r2)     // Catch: ParserConfigurationException -> 0x0034, SAXException -> 0x0036, all -> 0x005e
            r1.parse(r3)     // Catch: ParserConfigurationException -> 0x0034, SAXException -> 0x0036, all -> 0x005e
            com.loopj.android.http.AsyncHttpClient.silentCloseInputStream(r7)
            if (r2 == 0) goto L_0x0068
        L_0x0030:
            r2.close()     // Catch: IOException -> 0x0068
            goto L_0x0068
        L_0x0034:
            r1 = move-exception
            goto L_0x003e
        L_0x0036:
            r1 = move-exception
            goto L_0x004f
        L_0x0038:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x005f
        L_0x003c:
            r1 = move-exception
            r2 = r0
        L_0x003e:
            com.loopj.android.http.LogInterface r3 = com.loopj.android.http.AsyncHttpClient.log     // Catch: all -> 0x005e
            java.lang.String r4 = "SaxAsyncHttpRH"
            java.lang.String r5 = "getResponseData exception"
            r3.e(r4, r5, r1)     // Catch: all -> 0x005e
            com.loopj.android.http.AsyncHttpClient.silentCloseInputStream(r7)
            if (r2 == 0) goto L_0x0068
            goto L_0x0030
        L_0x004d:
            r1 = move-exception
            r2 = r0
        L_0x004f:
            com.loopj.android.http.LogInterface r3 = com.loopj.android.http.AsyncHttpClient.log     // Catch: all -> 0x005e
            java.lang.String r4 = "SaxAsyncHttpRH"
            java.lang.String r5 = "getResponseData exception"
            r3.e(r4, r5, r1)     // Catch: all -> 0x005e
            com.loopj.android.http.AsyncHttpClient.silentCloseInputStream(r7)
            if (r2 == 0) goto L_0x0068
            goto L_0x0030
        L_0x005e:
            r0 = move-exception
        L_0x005f:
            com.loopj.android.http.AsyncHttpClient.silentCloseInputStream(r7)
            if (r2 == 0) goto L_0x0067
            r2.close()     // Catch: IOException -> 0x0067
        L_0x0067:
            throw r0
        L_0x0068:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loopj.android.http.SaxAsyncHttpResponseHandler.getResponseData(cz.msebera.android.httpclient.HttpEntity):byte[]");
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
        onSuccess(i, headerArr, (Header[]) this.handler);
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        onFailure(i, headerArr, this.handler);
    }
}
