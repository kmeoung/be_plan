package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

@NotThreadSafe
@Deprecated
/* loaded from: classes.dex */
public class SSLContextBuilder {
    static final String SSL = "SSL";
    static final String TLS = "TLS";
    private String protocol;
    private SecureRandom secureRandom;
    private Set<KeyManager> keymanagers = new LinkedHashSet();
    private Set<TrustManager> trustmanagers = new LinkedHashSet();

    public SSLContextBuilder useTLS() {
        this.protocol = "TLS";
        return this;
    }

    public SSLContextBuilder useSSL() {
        this.protocol = "SSL";
        return this;
    }

    public SSLContextBuilder useProtocol(String str) {
        this.protocol = str;
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore);
        TrustManager[] trustManagers = instance.getTrustManagers();
        if (trustManagers != null) {
            if (trustStrategy != null) {
                for (int i = 0; i < trustManagers.length; i++) {
                    TrustManager trustManager = trustManagers[i];
                    if (trustManager instanceof X509TrustManager) {
                        trustManagers[i] = new TrustManagerDelegate((X509TrustManager) trustManager, trustStrategy);
                    }
                }
            }
            for (TrustManager trustManager2 : trustManagers) {
                this.trustmanagers.add(trustManager2);
            }
        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
        return loadTrustMaterial(keyStore, null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        loadKeyMaterial(keyStore, cArr, null);
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr, PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory instance = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore, cArr);
        KeyManager[] keyManagers = instance.getKeyManagers();
        if (keyManagers != null) {
            if (privateKeyStrategy != null) {
                for (int i = 0; i < keyManagers.length; i++) {
                    KeyManager keyManager = keyManagers[i];
                    if (keyManager instanceof X509KeyManager) {
                        keyManagers[i] = new KeyManagerDelegate((X509KeyManager) keyManager, privateKeyStrategy);
                    }
                }
            }
            for (KeyManager keyManager2 : keyManagers) {
                this.keymanagers.add(keyManager2);
            }
        }
        return this;
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext instance = SSLContext.getInstance(this.protocol != null ? this.protocol : "TLS");
        TrustManager[] trustManagerArr = null;
        KeyManager[] keyManagerArr = !this.keymanagers.isEmpty() ? (KeyManager[]) this.keymanagers.toArray(new KeyManager[this.keymanagers.size()]) : null;
        if (!this.trustmanagers.isEmpty()) {
            trustManagerArr = (TrustManager[]) this.trustmanagers.toArray(new TrustManager[this.trustmanagers.size()]);
        }
        instance.init(keyManagerArr, trustManagerArr, this.secureRandom);
        return instance;
    }

    /* loaded from: classes.dex */
    public static class TrustManagerDelegate implements X509TrustManager {
        private final X509TrustManager trustManager;
        private final TrustStrategy trustStrategy;

        TrustManagerDelegate(X509TrustManager x509TrustManager, TrustStrategy trustStrategy) {
            this.trustManager = x509TrustManager;
            this.trustStrategy = trustStrategy;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.trustManager.checkClientTrusted(x509CertificateArr, str);
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            if (!this.trustStrategy.isTrusted(x509CertificateArr, str)) {
                this.trustManager.checkServerTrusted(x509CertificateArr, str);
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return this.trustManager.getAcceptedIssuers();
        }
    }

    /* loaded from: classes.dex */
    public static class KeyManagerDelegate implements X509KeyManager {
        private final PrivateKeyStrategy aliasStrategy;
        private final X509KeyManager keyManager;

        KeyManagerDelegate(X509KeyManager x509KeyManager, PrivateKeyStrategy privateKeyStrategy) {
            this.keyManager = x509KeyManager;
            this.aliasStrategy = privateKeyStrategy;
        }

        @Override // javax.net.ssl.X509KeyManager
        public String[] getClientAliases(String str, Principal[] principalArr) {
            return this.keyManager.getClientAliases(str, principalArr);
        }

        @Override // javax.net.ssl.X509KeyManager
        public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
            HashMap hashMap = new HashMap();
            for (String str : strArr) {
                String[] clientAliases = this.keyManager.getClientAliases(str, principalArr);
                if (clientAliases != null) {
                    for (String str2 : clientAliases) {
                        hashMap.put(str2, new PrivateKeyDetails(str, this.keyManager.getCertificateChain(str2)));
                    }
                }
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }

        @Override // javax.net.ssl.X509KeyManager
        public String[] getServerAliases(String str, Principal[] principalArr) {
            return this.keyManager.getServerAliases(str, principalArr);
        }

        @Override // javax.net.ssl.X509KeyManager
        public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
            HashMap hashMap = new HashMap();
            String[] serverAliases = this.keyManager.getServerAliases(str, principalArr);
            if (serverAliases != null) {
                for (String str2 : serverAliases) {
                    hashMap.put(str2, new PrivateKeyDetails(str, this.keyManager.getCertificateChain(str2)));
                }
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }

        @Override // javax.net.ssl.X509KeyManager
        public X509Certificate[] getCertificateChain(String str) {
            return this.keyManager.getCertificateChain(str);
        }

        @Override // javax.net.ssl.X509KeyManager
        public PrivateKey getPrivateKey(String str) {
            return this.keyManager.getPrivateKey(str);
        }
    }
}
