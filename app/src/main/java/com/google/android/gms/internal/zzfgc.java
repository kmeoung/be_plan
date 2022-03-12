package com.google.android.gms.internal;

import cz.msebera.android.httpclient.message.TokenParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfgc {
    public static String zzaq(zzfdh zzfdhVar) {
        String str;
        zzfgd zzfgdVar = new zzfgd(zzfdhVar);
        StringBuilder sb = new StringBuilder(zzfgdVar.size());
        for (int i = 0; i < zzfgdVar.size(); i++) {
            int zzkd = zzfgdVar.zzkd(i);
            if (zzkd == 34) {
                str = "\\\"";
            } else if (zzkd == 39) {
                str = "\\'";
            } else if (zzkd != 92) {
                switch (zzkd) {
                    case 7:
                        str = "\\a";
                        break;
                    case 8:
                        str = "\\b";
                        break;
                    case 9:
                        str = "\\t";
                        break;
                    case 10:
                        str = "\\n";
                        break;
                    case 11:
                        str = "\\v";
                        break;
                    case 12:
                        str = "\\f";
                        break;
                    case 13:
                        str = "\\r";
                        break;
                    default:
                        if (zzkd < 32 || zzkd > 126) {
                            sb.append(TokenParser.ESCAPE);
                            sb.append((char) (((zzkd >>> 6) & 3) + 48));
                            sb.append((char) (((zzkd >>> 3) & 7) + 48));
                            zzkd = (zzkd & 7) + 48;
                        }
                        sb.append((char) zzkd);
                        continue;
                        break;
                }
            } else {
                str = "\\\\";
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
