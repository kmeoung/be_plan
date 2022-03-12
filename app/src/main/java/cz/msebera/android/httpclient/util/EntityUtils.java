package cz.msebera.android.httpclient.util;

import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.ParseException;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class EntityUtils {
    private EntityUtils() {
    }

    public static void consumeQuietly(HttpEntity httpEntity) {
        try {
            consume(httpEntity);
        } catch (IOException unused) {
        }
    }

    public static void consume(HttpEntity httpEntity) throws IOException {
        InputStream content;
        if (httpEntity != null && httpEntity.isStreaming() && (content = httpEntity.getContent()) != null) {
            content.close();
        }
    }

    public static void updateEntity(HttpResponse httpResponse, HttpEntity httpEntity) throws IOException {
        Args.notNull(httpResponse, "Response");
        consume(httpResponse.getEntity());
        httpResponse.setEntity(httpEntity);
    }

    public static byte[] toByteArray(HttpEntity httpEntity) throws IOException {
        Args.notNull(httpEntity, "Entity");
        InputStream content = httpEntity.getContent();
        if (content == null) {
            return null;
        }
        try {
            Args.check(httpEntity.getContentLength() <= 2147483647L, "HTTP entity too large to be buffered in memory");
            int contentLength = (int) httpEntity.getContentLength();
            if (contentLength < 0) {
                contentLength = 4096;
            }
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(contentLength);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = content.read(bArr);
                if (read == -1) {
                    return byteArrayBuffer.toByteArray();
                }
                byteArrayBuffer.append(bArr, 0, read);
            }
        } finally {
            content.close();
        }
    }

    @Deprecated
    public static String getContentCharSet(HttpEntity httpEntity) throws ParseException {
        NameValuePair parameterByName;
        Args.notNull(httpEntity, "Entity");
        if (httpEntity.getContentType() != null) {
            HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0 && (parameterByName = elements[0].getParameterByName(HttpRequest.PARAM_CHARSET)) != null) {
                return parameterByName.getValue();
            }
        }
        return null;
    }

    @Deprecated
    public static String getContentMimeType(HttpEntity httpEntity) throws ParseException {
        Args.notNull(httpEntity, "Entity");
        if (httpEntity.getContentType() != null) {
            HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0) {
                return elements[0].getName();
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0048 A[Catch: all -> 0x006b, TryCatch #1 {all -> 0x006b, blocks: (B:5:0x000d, B:9:0x001c, B:13:0x002a, B:15:0x0030, B:18:0x0038, B:19:0x0041, B:24:0x0048, B:25:0x004a, B:26:0x0058, B:28:0x005f, B:29:0x0063), top: B:37:0x000d, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005f A[Catch: all -> 0x006b, LOOP:0: B:26:0x0058->B:28:0x005f, LOOP_END, TryCatch #1 {all -> 0x006b, blocks: (B:5:0x000d, B:9:0x001c, B:13:0x002a, B:15:0x0030, B:18:0x0038, B:19:0x0041, B:24:0x0048, B:25:0x004a, B:26:0x0058, B:28:0x005f, B:29:0x0063), top: B:37:0x000d, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0063 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String toString(cz.msebera.android.httpclient.HttpEntity r7, java.nio.charset.Charset r8) throws java.io.IOException, cz.msebera.android.httpclient.ParseException {
        /*
            java.lang.String r0 = "Entity"
            cz.msebera.android.httpclient.util.Args.notNull(r7, r0)
            java.io.InputStream r0 = r7.getContent()
            r1 = 0
            if (r0 != 0) goto L_0x000d
            return r1
        L_0x000d:
            long r2 = r7.getContentLength()     // Catch: all -> 0x006b
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            r2 = 0
            if (r6 > 0) goto L_0x001b
            r3 = 1
            goto L_0x001c
        L_0x001b:
            r3 = 0
        L_0x001c:
            java.lang.String r4 = "HTTP entity too large to be buffered in memory"
            cz.msebera.android.httpclient.util.Args.check(r3, r4)     // Catch: all -> 0x006b
            long r3 = r7.getContentLength()     // Catch: all -> 0x006b
            int r3 = (int) r3
            if (r3 >= 0) goto L_0x002a
            r3 = 4096(0x1000, float:5.74E-42)
        L_0x002a:
            cz.msebera.android.httpclient.entity.ContentType r7 = cz.msebera.android.httpclient.entity.ContentType.get(r7)     // Catch: UnsupportedCharsetException -> 0x0035, all -> 0x006b
            if (r7 == 0) goto L_0x0042
            java.nio.charset.Charset r7 = r7.getCharset()     // Catch: UnsupportedCharsetException -> 0x0035, all -> 0x006b
            goto L_0x0043
        L_0x0035:
            r7 = move-exception
            if (r8 != 0) goto L_0x0042
            java.io.UnsupportedEncodingException r8 = new java.io.UnsupportedEncodingException     // Catch: all -> 0x006b
            java.lang.String r7 = r7.getMessage()     // Catch: all -> 0x006b
            r8.<init>(r7)     // Catch: all -> 0x006b
            throw r8     // Catch: all -> 0x006b
        L_0x0042:
            r7 = r1
        L_0x0043:
            if (r7 != 0) goto L_0x0046
            r7 = r8
        L_0x0046:
            if (r7 != 0) goto L_0x004a
            java.nio.charset.Charset r7 = cz.msebera.android.httpclient.protocol.HTTP.DEF_CONTENT_CHARSET     // Catch: all -> 0x006b
        L_0x004a:
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch: all -> 0x006b
            r8.<init>(r0, r7)     // Catch: all -> 0x006b
            cz.msebera.android.httpclient.util.CharArrayBuffer r7 = new cz.msebera.android.httpclient.util.CharArrayBuffer     // Catch: all -> 0x006b
            r7.<init>(r3)     // Catch: all -> 0x006b
            r1 = 1024(0x400, float:1.435E-42)
            char[] r1 = new char[r1]     // Catch: all -> 0x006b
        L_0x0058:
            int r3 = r8.read(r1)     // Catch: all -> 0x006b
            r4 = -1
            if (r3 == r4) goto L_0x0063
            r7.append(r1, r2, r3)     // Catch: all -> 0x006b
            goto L_0x0058
        L_0x0063:
            java.lang.String r7 = r7.toString()     // Catch: all -> 0x006b
            r0.close()
            return r7
        L_0x006b:
            r7 = move-exception
            r0.close()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.util.EntityUtils.toString(cz.msebera.android.httpclient.HttpEntity, java.nio.charset.Charset):java.lang.String");
    }

    public static String toString(HttpEntity httpEntity, String str) throws IOException, ParseException {
        return toString(httpEntity, str != null ? Charset.forName(str) : null);
    }

    public static String toString(HttpEntity httpEntity) throws IOException, ParseException {
        return toString(httpEntity, (Charset) null);
    }
}
