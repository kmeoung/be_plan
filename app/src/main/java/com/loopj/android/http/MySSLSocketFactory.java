package com.loopj.android.http;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.conn.scheme.PlainSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.conn.tsccm.ThreadSafeClientConnManager;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public class MySSLSocketFactory extends SSLSocketFactory {
    final SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(keyStore);
        this.sslContext.init(null, new TrustManager[]{new X509TrustManager() { // from class: com.loopj.android.http.MySSLSocketFactory.1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x004c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.security.KeyStore getKeystoreOfCA(java.io.InputStream r3) {
        /*
            r0 = 0
            java.lang.String r1 = "X.509"
            java.security.cert.CertificateFactory r1 = java.security.cert.CertificateFactory.getInstance(r1)     // Catch: all -> 0x001d, CertificateException -> 0x001f
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: all -> 0x001d, CertificateException -> 0x001f
            r2.<init>(r3)     // Catch: all -> 0x001d, CertificateException -> 0x001f
            java.security.cert.Certificate r3 = r1.generateCertificate(r2)     // Catch: CertificateException -> 0x001b, all -> 0x0048
            if (r2 == 0) goto L_0x002f
            r2.close()     // Catch: IOException -> 0x0016
            goto L_0x002f
        L_0x0016:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x002f
        L_0x001b:
            r3 = move-exception
            goto L_0x0021
        L_0x001d:
            r3 = move-exception
            goto L_0x004a
        L_0x001f:
            r3 = move-exception
            r2 = r0
        L_0x0021:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)     // Catch: all -> 0x0048
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch: IOException -> 0x002a
            goto L_0x002e
        L_0x002a:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
        L_0x002e:
            r3 = r0
        L_0x002f:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch: Exception -> 0x0042
            r1.load(r0, r0)     // Catch: Exception -> 0x0040
            java.lang.String r0 = "ca"
            r1.setCertificateEntry(r0, r3)     // Catch: Exception -> 0x0040
            goto L_0x0047
        L_0x0040:
            r3 = move-exception
            goto L_0x0044
        L_0x0042:
            r3 = move-exception
            r1 = r0
        L_0x0044:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
        L_0x0047:
            return r1
        L_0x0048:
            r3 = move-exception
            r0 = r2
        L_0x004a:
            if (r0 == 0) goto L_0x0054
            r0.close()     // Catch: IOException -> 0x0050
            goto L_0x0054
        L_0x0050:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
        L_0x0054:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loopj.android.http.MySSLSocketFactory.getKeystoreOfCA(java.io.InputStream):java.security.KeyStore");
    }

    public static KeyStore getKeystore() {
        Throwable th;
        KeyStore keyStore = null;
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                instance.load(null, null);
                return instance;
            } catch (Throwable th2) {
                th = th2;
                keyStore = instance;
                ThrowableExtension.printStackTrace(th);
                return keyStore;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static SSLSocketFactory getFixedSocketFactory() {
        try {
            MySSLSocketFactory mySSLSocketFactory = new MySSLSocketFactory(getKeystore());
            mySSLSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return mySSLSocketFactory;
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static DefaultHttpClient getNewHttpClient(KeyStore keyStore) {
        try {
            MySSLSocketFactory mySSLSocketFactory = new MySSLSocketFactory(keyStore);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", mySSLSocketFactory, 443));
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        } catch (Exception unused) {
            return new DefaultHttpClient();
        }
    }

    @Override // cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory, cz.msebera.android.httpclient.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return this.sslContext.getSocketFactory().createSocket(socket, str, i, z);
    }

    @Override // cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory, cz.msebera.android.httpclient.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        return this.sslContext.getSocketFactory().createSocket();
    }

    public void fixHttpsURLConnection() {
        HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
    }
}
