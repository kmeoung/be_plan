package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Deprecated
/* loaded from: classes.dex */
public final class SchemeRegistry {
    private final ConcurrentHashMap<String, Scheme> registeredSchemes = new ConcurrentHashMap<>();

    public final Scheme getScheme(String str) {
        Scheme scheme = get(str);
        if (scheme != null) {
            return scheme;
        }
        throw new IllegalStateException("Scheme '" + str + "' not registered.");
    }

    public final Scheme getScheme(HttpHost httpHost) {
        Args.notNull(httpHost, "Host");
        return getScheme(httpHost.getSchemeName());
    }

    public final Scheme get(String str) {
        Args.notNull(str, "Scheme name");
        return this.registeredSchemes.get(str);
    }

    public final Scheme register(Scheme scheme) {
        Args.notNull(scheme, "Scheme");
        return this.registeredSchemes.put(scheme.getName(), scheme);
    }

    public final Scheme unregister(String str) {
        Args.notNull(str, "Scheme name");
        return this.registeredSchemes.remove(str);
    }

    public final List<String> getSchemeNames() {
        return new ArrayList(this.registeredSchemes.keySet());
    }

    public void setItems(Map<String, Scheme> map) {
        if (map != null) {
            this.registeredSchemes.clear();
            this.registeredSchemes.putAll(map);
        }
    }
}
