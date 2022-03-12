package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

@Deprecated
/* loaded from: classes.dex */
public abstract class AbstractVerifier implements X509HostnameVerifier {
    static final String[] BAD_COUNTRY_2LDS = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    static {
        Arrays.sort(BAD_COUNTRY_2LDS);
    }

    @Override // cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier
    public final void verify(String str, SSLSocket sSLSocket) throws IOException {
        Args.notNull(str, "Host");
        SSLSession session = sSLSocket.getSession();
        if (session == null) {
            sSLSocket.getInputStream().available();
            session = sSLSocket.getSession();
            if (session == null) {
                sSLSocket.startHandshake();
                session = sSLSocket.getSession();
            }
        }
        verify(str, (X509Certificate) session.getPeerCertificates()[0]);
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

    @Override // cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier
    public final void verify(String str, X509Certificate x509Certificate) throws SSLException {
        List<String> extractSubjectAlts = DefaultHostnameVerifier.extractSubjectAlts(x509Certificate, (InetAddressUtils.isIPv4Address(str) || InetAddressUtils.isIPv6Address(str)) ? 7 : 2);
        String findMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific("cn");
        String[] strArr = null;
        String[] strArr2 = findMostSpecific != null ? new String[]{findMostSpecific} : null;
        if (extractSubjectAlts != null && !extractSubjectAlts.isEmpty()) {
            strArr = (String[]) extractSubjectAlts.toArray(new String[extractSubjectAlts.size()]);
        }
        verify(str, strArr2, strArr);
    }

    public final void verify(String str, String[] strArr, String[] strArr2, boolean z) throws SSLException {
        List<String> list = null;
        String str2 = (strArr == null || strArr.length <= 0) ? null : strArr[0];
        if (strArr2 != null && strArr2.length > 0) {
            list = Arrays.asList(strArr2);
        }
        String normaliseAddress = InetAddressUtils.isIPv6Address(str) ? DefaultHostnameVerifier.normaliseAddress(str.toLowerCase(Locale.ROOT)) : str;
        if (list != null) {
            for (String str3 : list) {
                if (InetAddressUtils.isIPv6Address(str3)) {
                    str3 = DefaultHostnameVerifier.normaliseAddress(str3);
                }
                if (matchIdentity(normaliseAddress, str3, z)) {
                    return;
                }
            }
            throw new SSLException("Certificate for <" + str + "> doesn't match any of the subject alternative names: " + list);
        } else if (str2 != null) {
            if (!matchIdentity(normaliseAddress, InetAddressUtils.isIPv6Address(str2) ? DefaultHostnameVerifier.normaliseAddress(str2) : str2, z)) {
                throw new SSLException("Certificate for <" + str + "> doesn't match common name of the certificate subject: " + str2);
            }
        } else {
            throw new SSLException("Certificate subject for <" + str + "> doesn't contain a common name and does not have alternative names");
        }
    }

    private static boolean matchIdentity(String str, String str2, boolean z) {
        boolean z2;
        if (str == null) {
            return false;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        String lowerCase2 = str2.toLowerCase(Locale.ROOT);
        String[] split = lowerCase2.split("\\.");
        if (!(split.length >= 3 && split[0].endsWith("*") && (!z || validCountryWildcard(split)))) {
            return lowerCase.equals(lowerCase2);
        }
        String str3 = split[0];
        if (str3.length() > 1) {
            String substring = str3.substring(0, str3.length() - 1);
            z2 = lowerCase.startsWith(substring) && lowerCase.substring(substring.length()).endsWith(lowerCase2.substring(str3.length()));
        } else {
            z2 = lowerCase.endsWith(lowerCase2.substring(1));
        }
        if (z2) {
            return !z || countDots(lowerCase) == countDots(lowerCase2);
        }
        return false;
    }

    private static boolean validCountryWildcard(String[] strArr) {
        return (strArr.length == 3 && strArr[2].length() == 2 && Arrays.binarySearch(BAD_COUNTRY_2LDS, strArr[1]) >= 0) ? false : true;
    }

    public static boolean acceptableCountryWildcard(String str) {
        return validCountryWildcard(str.split("\\."));
    }

    public static String[] getCNs(X509Certificate x509Certificate) {
        String findMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific("cn");
        if (findMostSpecific != null) {
            return new String[]{findMostSpecific};
        }
        return null;
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        List<String> extractSubjectAlts = DefaultHostnameVerifier.extractSubjectAlts(x509Certificate, 2);
        if (extractSubjectAlts == null || extractSubjectAlts.isEmpty()) {
            return null;
        }
        return (String[]) extractSubjectAlts.toArray(new String[extractSubjectAlts.size()]);
    }

    public static int countDots(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '.') {
                i++;
            }
        }
        return i;
    }
}
