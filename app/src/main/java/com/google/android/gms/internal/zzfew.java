package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public class zzfew extends IOException {
    private zzffi zzpcu = null;

    public zzfew(String str) {
        super(str);
    }

    public static zzfew zzcvr() {
        return new zzfew("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    public static zzfew zzcvs() {
        return new zzfew("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    public static zzfew zzcvt() {
        return new zzfew("CodedInputStream encountered a malformed varint.");
    }

    public static zzfew zzcvu() {
        return new zzfew("Protocol message contained an invalid tag (zero).");
    }

    public static zzfew zzcvv() {
        return new zzfew("Protocol message end-group tag did not match expected tag.");
    }

    public static zzfex zzcvw() {
        return new zzfex("Protocol message tag had invalid wire type.");
    }

    public static zzfew zzcvx() {
        return new zzfew("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    public static zzfew zzcvy() {
        return new zzfew("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    public static zzfew zzcvz() {
        return new zzfew("Protocol message had invalid UTF-8.");
    }

    public final zzfew zzh(zzffi zzffiVar) {
        this.zzpcu = zzffiVar;
        return this;
    }
}
