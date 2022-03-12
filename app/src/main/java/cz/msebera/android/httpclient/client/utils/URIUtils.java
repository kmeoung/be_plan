package cz.msebera.android.httpclient.client.utils;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.routing.RouteInfo;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

@Immutable
/* loaded from: classes.dex */
public class URIUtils {
    @Deprecated
    public static URI createURI(String str, String str2, int i, String str3, String str4, String str5) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            if (str != null) {
                sb.append(str);
                sb.append("://");
            }
            sb.append(str2);
            if (i > 0) {
                sb.append(':');
                sb.append(i);
            }
        }
        if (str3 == null || !str3.startsWith("/")) {
            sb.append('/');
        }
        if (str3 != null) {
            sb.append(str3);
        }
        if (str4 != null) {
            sb.append('?');
            sb.append(str4);
        }
        if (str5 != null) {
            sb.append('#');
            sb.append(str5);
        }
        return new URI(sb.toString());
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost, boolean z) throws URISyntaxException {
        Args.notNull(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        if (httpHost != null) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        } else {
            uRIBuilder.setScheme(null);
            uRIBuilder.setHost(null);
            uRIBuilder.setPort(-1);
        }
        if (z) {
            uRIBuilder.setFragment(null);
        }
        if (TextUtils.isEmpty(uRIBuilder.getPath())) {
            uRIBuilder.setPath("/");
        }
        return uRIBuilder.build();
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost) throws URISyntaxException {
        return rewriteURI(uri, httpHost, false);
    }

    public static URI rewriteURI(URI uri) throws URISyntaxException {
        Args.notNull(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        if (uRIBuilder.getUserInfo() != null) {
            uRIBuilder.setUserInfo(null);
        }
        if (TextUtils.isEmpty(uRIBuilder.getPath())) {
            uRIBuilder.setPath("/");
        }
        if (uRIBuilder.getHost() != null) {
            uRIBuilder.setHost(uRIBuilder.getHost().toLowerCase(Locale.ROOT));
        }
        uRIBuilder.setFragment(null);
        return uRIBuilder.build();
    }

    public static URI rewriteURIForRoute(URI uri, RouteInfo routeInfo) throws URISyntaxException {
        if (uri == null) {
            return null;
        }
        if (routeInfo.getProxyHost() == null || routeInfo.isTunnelled()) {
            if (uri.isAbsolute()) {
                return rewriteURI(uri, null, true);
            }
            return rewriteURI(uri);
        } else if (!uri.isAbsolute()) {
            return rewriteURI(uri, routeInfo.getTargetHost(), true);
        } else {
            return rewriteURI(uri);
        }
    }

    public static URI resolve(URI uri, String str) {
        return resolve(uri, URI.create(str));
    }

    public static URI resolve(URI uri, URI uri2) {
        Args.notNull(uri, "Base URI");
        Args.notNull(uri2, "Reference URI");
        String uri3 = uri2.toString();
        if (uri3.startsWith("?")) {
            return resolveReferenceStartingWithQueryString(uri, uri2);
        }
        boolean isEmpty = uri3.isEmpty();
        if (isEmpty) {
            uri2 = URI.create("#");
        }
        URI resolve = uri.resolve(uri2);
        if (isEmpty) {
            String uri4 = resolve.toString();
            resolve = URI.create(uri4.substring(0, uri4.indexOf(35)));
        }
        return normalizeSyntax(resolve);
    }

    private static URI resolveReferenceStartingWithQueryString(URI uri, URI uri2) {
        String uri3 = uri.toString();
        if (uri3.indexOf(63) > -1) {
            uri3 = uri3.substring(0, uri3.indexOf(63));
        }
        return URI.create(uri3 + uri2.toString());
    }

    private static URI normalizeSyntax(URI uri) {
        if (uri.isOpaque() || uri.getAuthority() == null) {
            return uri;
        }
        Args.check(uri.isAbsolute(), "Base URI must be absolute");
        String path = uri.getPath() == null ? "" : uri.getPath();
        String[] split = path.split("/");
        Stack stack = new Stack();
        for (String str : split) {
            if (!str.isEmpty() && !".".equals(str)) {
                if (!"..".equals(str)) {
                    stack.push(str);
                } else if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = stack.iterator();
        while (it.hasNext()) {
            sb.append('/');
            sb.append((String) it.next());
        }
        if (path.lastIndexOf(47) == path.length() - 1) {
            sb.append('/');
        }
        try {
            URI uri2 = new URI(uri.getScheme().toLowerCase(Locale.ROOT), uri.getAuthority().toLowerCase(Locale.ROOT), sb.toString(), null, null);
            if (uri.getQuery() == null && uri.getFragment() == null) {
                return uri2;
            }
            StringBuilder sb2 = new StringBuilder(uri2.toASCIIString());
            if (uri.getQuery() != null) {
                sb2.append('?');
                sb2.append(uri.getRawQuery());
            }
            if (uri.getFragment() != null) {
                sb2.append('#');
                sb2.append(uri.getRawFragment());
            }
            return URI.create(sb2.toString());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static HttpHost extractHost(URI uri) {
        int indexOf;
        if (uri == null || !uri.isAbsolute()) {
            return null;
        }
        int port = uri.getPort();
        String host = uri.getHost();
        if (host == null && (host = uri.getAuthority()) != null) {
            int indexOf2 = host.indexOf(64);
            if (indexOf2 >= 0) {
                int i = indexOf2 + 1;
                if (host.length() > i) {
                    host = host.substring(i);
                } else {
                    host = null;
                }
            }
            if (host != null && (indexOf = host.indexOf(58)) >= 0) {
                int i2 = indexOf + 1;
                int i3 = 0;
                for (int i4 = i2; i4 < host.length() && Character.isDigit(host.charAt(i4)); i4++) {
                    i3++;
                }
                if (i3 > 0) {
                    try {
                        port = Integer.parseInt(host.substring(i2, i3 + i2));
                    } catch (NumberFormatException unused) {
                    }
                }
                host = host.substring(0, indexOf);
            }
        }
        String scheme = uri.getScheme();
        if (TextUtils.isBlank(host)) {
            return null;
        }
        try {
            return new HttpHost(host, port, scheme);
        } catch (IllegalArgumentException unused2) {
            return null;
        }
    }

    public static URI resolve(URI uri, HttpHost httpHost, List<URI> list) throws URISyntaxException {
        URIBuilder uRIBuilder;
        Args.notNull(uri, "Request URI");
        if (list == null || list.isEmpty()) {
            uRIBuilder = new URIBuilder(uri);
        } else {
            uRIBuilder = new URIBuilder(list.get(list.size() - 1));
            String fragment = uRIBuilder.getFragment();
            for (int size = list.size() - 1; fragment == null && size >= 0; size--) {
                fragment = list.get(size).getFragment();
            }
            uRIBuilder.setFragment(fragment);
        }
        if (uRIBuilder.getFragment() == null) {
            uRIBuilder.setFragment(uri.getFragment());
        }
        if (httpHost != null && !uRIBuilder.isAbsolute()) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        }
        return uRIBuilder.build();
    }

    private URIUtils() {
    }
}
