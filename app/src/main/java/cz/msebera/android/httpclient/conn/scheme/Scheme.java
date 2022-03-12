package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.util.Locale;

@Immutable
@Deprecated
/* loaded from: classes.dex */
public final class Scheme {
    private final int defaultPort;
    private final boolean layered;
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private String stringRep;

    public Scheme(String str, int i, SchemeSocketFactory schemeSocketFactory) {
        Args.notNull(str, "Scheme name");
        Args.check(i > 0 && i <= 65535, "Port is invalid");
        Args.notNull(schemeSocketFactory, "Socket factory");
        this.name = str.toLowerCase(Locale.ENGLISH);
        this.defaultPort = i;
        if (schemeSocketFactory instanceof SchemeLayeredSocketFactory) {
            this.layered = true;
            this.socketFactory = schemeSocketFactory;
        } else if (schemeSocketFactory instanceof LayeredSchemeSocketFactory) {
            this.layered = true;
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor2((LayeredSchemeSocketFactory) schemeSocketFactory);
        } else {
            this.layered = false;
            this.socketFactory = schemeSocketFactory;
        }
    }

    @Deprecated
    public Scheme(String str, SocketFactory socketFactory, int i) {
        Args.notNull(str, "Scheme name");
        Args.notNull(socketFactory, "Socket factory");
        Args.check(i > 0 && i <= 65535, "Port is invalid");
        this.name = str.toLowerCase(Locale.ENGLISH);
        if (socketFactory instanceof LayeredSocketFactory) {
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor((LayeredSocketFactory) socketFactory);
            this.layered = true;
        } else {
            this.socketFactory = new SchemeSocketFactoryAdaptor(socketFactory);
            this.layered = false;
        }
        this.defaultPort = i;
    }

    public final int getDefaultPort() {
        return this.defaultPort;
    }

    @Deprecated
    public final SocketFactory getSocketFactory() {
        if (this.socketFactory instanceof SchemeSocketFactoryAdaptor) {
            return ((SchemeSocketFactoryAdaptor) this.socketFactory).getFactory();
        }
        if (this.layered) {
            return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory) this.socketFactory);
        }
        return new SocketFactoryAdaptor(this.socketFactory);
    }

    public final SchemeSocketFactory getSchemeSocketFactory() {
        return this.socketFactory;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean isLayered() {
        return this.layered;
    }

    public final int resolvePort(int i) {
        return i <= 0 ? this.defaultPort : i;
    }

    public final String toString() {
        if (this.stringRep == null) {
            this.stringRep = this.name + ':' + Integer.toString(this.defaultPort);
        }
        return this.stringRep;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scheme)) {
            return false;
        }
        Scheme scheme = (Scheme) obj;
        return this.name.equals(scheme.name) && this.defaultPort == scheme.defaultPort && this.layered == scheme.layered;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.defaultPort), this.name), this.layered);
    }
}
