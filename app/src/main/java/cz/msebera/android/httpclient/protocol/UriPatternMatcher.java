package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
/* loaded from: classes.dex */
public class UriPatternMatcher<T> {
    @GuardedBy("this")
    private final Map<String, T> map = new HashMap();

    public synchronized void register(String str, T t) {
        Args.notNull(str, "URI request pattern");
        this.map.put(str, t);
    }

    public synchronized void unregister(String str) {
        if (str != null) {
            this.map.remove(str);
        }
    }

    @Deprecated
    public synchronized void setHandlers(Map<String, T> map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }

    @Deprecated
    public synchronized void setObjects(Map<String, T> map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }

    @Deprecated
    public synchronized Map<String, T> getObjects() {
        return this.map;
    }

    public synchronized T lookup(String str) {
        T t;
        Args.notNull(str, "Request path");
        t = this.map.get(str);
        if (t == null) {
            String str2 = null;
            for (String str3 : this.map.keySet()) {
                if (matchUriRequestPattern(str3, str) && (str2 == null || str2.length() < str3.length() || (str2.length() == str3.length() && str3.endsWith("*")))) {
                    t = this.map.get(str3);
                    str2 = str3;
                }
            }
        }
        return t;
    }

    protected boolean matchUriRequestPattern(String str, String str2) {
        if (str.equals("*")) {
            return true;
        }
        if (!str.endsWith("*") || !str2.startsWith(str.substring(0, str.length() - 1))) {
            return str.startsWith("*") && str2.endsWith(str.substring(1, str.length()));
        }
        return true;
    }

    public String toString() {
        return this.map.toString();
    }
}
