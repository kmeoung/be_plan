package com.google.android.gms.internal;

import cz.msebera.android.httpclient.message.TokenParser;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.List;

/* loaded from: classes.dex */
public final class zzffl {
    public static String zza(zzffi zzffiVar, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zza(zzffiVar, sb, 0);
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0198, code lost:
        if (((java.lang.Boolean) r7).booleanValue() == false) goto L_0x019a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019a, code lost:
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01aa, code lost:
        if (((java.lang.Integer) r7).intValue() == 0) goto L_0x019a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01bb, code lost:
        if (((java.lang.Float) r7).floatValue() == 0.0f) goto L_0x019a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01cd, code lost:
        if (((java.lang.Double) r7).doubleValue() == 0.0d) goto L_0x019a;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zza(com.google.android.gms.internal.zzffi r12, java.lang.StringBuilder r13, int r14) {
        /*
            Method dump skipped, instructions count: 585
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffl.zza(com.google.android.gms.internal.zzffi, java.lang.StringBuilder, int):void");
    }

    public static final void zzb(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                zzb(sb, i, str, obj2);
            }
            return;
        }
        sb.append('\n');
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(TokenParser.SP);
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            sb.append(zzfgc.zzaq(zzfdh.zztb((String) obj)));
            sb.append(TokenParser.DQUOTE);
        } else if (obj instanceof zzfdh) {
            sb.append(": \"");
            sb.append(zzfgc.zzaq((zzfdh) obj));
            sb.append(TokenParser.DQUOTE);
        } else if (obj instanceof zzfee) {
            sb.append(" {");
            zza((zzfee) obj, sb, i + 2);
            sb.append("\n");
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(TokenParser.SP);
            }
            sb.append("}");
        } else {
            sb.append(": ");
            sb.append(obj.toString());
        }
    }

    private static final String zztf(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            }
            sb.append(Character.toLowerCase(charAt));
        }
        return sb.toString();
    }
}
