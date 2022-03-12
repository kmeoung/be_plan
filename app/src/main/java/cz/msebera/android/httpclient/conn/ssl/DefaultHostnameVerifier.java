package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcher;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

@Immutable
/* loaded from: classes.dex */
public final class DefaultHostnameVerifier implements HostnameVerifier {
    static final int DNS_NAME_TYPE = 2;
    static final int IP_ADDRESS_TYPE = 7;
    public HttpClientAndroidLog log;
    private final PublicSuffixMatcher publicSuffixMatcher;

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
        this.log = new HttpClientAndroidLog(getClass());
        this.publicSuffixMatcher = publicSuffixMatcher;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException e) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(e.getMessage(), e);
            }
            return false;
        }
    }

    public final void verify(String str, X509Certificate x509Certificate) throws SSLException {
        boolean isIPv4Address = InetAddressUtils.isIPv4Address(str);
        boolean isIPv6Address = InetAddressUtils.isIPv6Address(str);
        List<String> extractSubjectAlts = extractSubjectAlts(x509Certificate, (isIPv4Address || isIPv6Address) ? 7 : 2);
        if (extractSubjectAlts == null || extractSubjectAlts.isEmpty()) {
            String findMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific("cn");
            if (findMostSpecific == null) {
                throw new SSLException("Certificate subject for <" + str + "> doesn't contain a common name and does not have alternative names");
            }
            matchCN(str, findMostSpecific, this.publicSuffixMatcher);
        } else if (isIPv4Address) {
            matchIPAddress(str, extractSubjectAlts);
        } else if (isIPv6Address) {
            matchIPv6Address(str, extractSubjectAlts);
        } else {
            matchDNSName(str, extractSubjectAlts, this.publicSuffixMatcher);
        }
    }

    static void matchIPAddress(String str, List<String> list) throws SSLException {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i))) {
                return;
            }
        }
        throw new SSLException("Certificate for <" + str + "> doesn't match any of the subject alternative names: " + list);
    }

    static void matchIPv6Address(String str, List<String> list) throws SSLException {
        String normaliseAddress = normaliseAddress(str);
        for (int i = 0; i < list.size(); i++) {
            if (normaliseAddress.equals(normaliseAddress(list.get(i)))) {
                return;
            }
        }
        throw new SSLException("Certificate for <" + str + "> doesn't match any of the subject alternative names: " + list);
    }

    static void matchDNSName(String str, List<String> list, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        for (int i = 0; i < list.size(); i++) {
            if (matchIdentityStrict(lowerCase, list.get(i).toLowerCase(Locale.ROOT), publicSuffixMatcher)) {
                return;
            }
        }
        throw new SSLException("Certificate for <" + str + "> doesn't match any of the subject alternative names: " + list);
    }

    static void matchCN(String str, String str2, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        if (!matchIdentityStrict(str, str2, publicSuffixMatcher)) {
            throw new SSLException("Certificate for <" + str + "> doesn't match common name of the certificate subject: " + str2);
        }
    }

    static boolean matchDomainRoot(String str, String str2) {
        if (str2 != null && str.endsWith(str2)) {
            return str.length() == str2.length() || str.charAt((str.length() - str2.length()) - 1) == '.';
        }
        return false;
    }

    private static boolean matchIdentity(String str, String str2, PublicSuffixMatcher publicSuffixMatcher, boolean z) {
        if (publicSuffixMatcher != null && str.contains(".") && !matchDomainRoot(str, publicSuffixMatcher.getDomainRoot(str2))) {
            return false;
        }
        int indexOf = str2.indexOf(42);
        if (indexOf == -1) {
            return str.equalsIgnoreCase(str2);
        }
        String substring = str2.substring(0, indexOf);
        String substring2 = str2.substring(indexOf + 1);
        if (!substring.isEmpty() && !str.startsWith(substring)) {
            return false;
        }
        if (substring2.isEmpty() || str.endsWith(substring2)) {
            return !z || !str.substring(substring.length(), str.length() - substring2.length()).contains(".");
        }
        return false;
    }

    static boolean matchIdentity(String str, String str2, PublicSuffixMatcher publicSuffixMatcher) {
        return matchIdentity(str, str2, publicSuffixMatcher, false);
    }

    static boolean matchIdentity(String str, String str2) {
        return matchIdentity(str, str2, null, false);
    }

    static boolean matchIdentityStrict(String str, String str2, PublicSuffixMatcher publicSuffixMatcher) {
        return matchIdentity(str, str2, publicSuffixMatcher, true);
    }

    static boolean matchIdentityStrict(String str, String str2) {
        return matchIdentity(str, str2, null, true);
    }

    public static List<String> extractSubjectAlts(X509Certificate x509Certificate, int i) {
        Collection<List<?>> collection;
        ArrayList arrayList = null;
        try {
            collection = x509Certificate.getSubjectAlternativeNames();
        } catch (CertificateParsingException unused) {
            collection = null;
        }
        if (collection != null) {
            for (List<?> list : collection) {
                if (((Integer) list.get(0)).intValue() == i) {
                    String str = (String) list.get(1);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public static String normaliseAddress(String str) {
        if (str == null) {
            return str;
        }
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
            return str;
        }
    }
}
