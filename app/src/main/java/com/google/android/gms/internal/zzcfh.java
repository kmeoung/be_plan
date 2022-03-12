package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes.dex */
public final class zzcfh extends zzcii {
    public zzcfh(zzchj zzchjVar) {
        super(zzchjVar);
    }

    private final Boolean zza(double d, zzckr zzckrVar) {
        try {
            return zza(new BigDecimal(d), zzckrVar, Math.ulp(d));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(long j, zzckr zzckrVar) {
        try {
            return zza(new BigDecimal(j), zzckrVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(zzckp zzckpVar, zzcky zzckyVar, long j) {
        zzckq[] zzckqVarArr;
        zzckz[] zzckzVarArr;
        zzckq[] zzckqVarArr2;
        Boolean bool;
        String str;
        Object obj;
        if (zzckpVar.zzjhf != null) {
            Boolean zza = zza(j, zzckpVar.zzjhf);
            if (zza == null) {
                return null;
            }
            if (!zza.booleanValue()) {
                return false;
            }
        }
        HashSet hashSet = new HashSet();
        for (zzckq zzckqVar : zzckpVar.zzjhd) {
            if (TextUtils.isEmpty(zzckqVar.zzjhk)) {
                zzawm().zzayt().zzj("null or empty param name in filter. event", zzawh().zzjb(zzckyVar.name));
                return null;
            }
            hashSet.add(zzckqVar.zzjhk);
        }
        ArrayMap arrayMap = new ArrayMap();
        for (zzckz zzckzVar : zzckyVar.zzjim) {
            if (hashSet.contains(zzckzVar.name)) {
                if (zzckzVar.zzjiq != null) {
                    str = zzckzVar.name;
                    obj = zzckzVar.zzjiq;
                } else if (zzckzVar.zzjgq != null) {
                    str = zzckzVar.name;
                    obj = zzckzVar.zzjgq;
                } else if (zzckzVar.zzfzi != null) {
                    str = zzckzVar.name;
                    obj = zzckzVar.zzfzi;
                } else {
                    zzawm().zzayt().zze("Unknown value for param. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(zzckzVar.name));
                    return null;
                }
                arrayMap.put(str, obj);
            }
        }
        for (zzckq zzckqVar2 : zzckpVar.zzjhd) {
            boolean equals = Boolean.TRUE.equals(zzckqVar2.zzjhj);
            String str2 = zzckqVar2.zzjhk;
            if (TextUtils.isEmpty(str2)) {
                zzawm().zzayt().zzj("Event has empty param name. event", zzawh().zzjb(zzckyVar.name));
                return null;
            }
            V v = arrayMap.get(str2);
            if (v instanceof Long) {
                if (zzckqVar2.zzjhi == null) {
                    zzawm().zzayt().zze("No number filter for long param. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                    return null;
                }
                Boolean zza2 = zza(((Long) v).longValue(), zzckqVar2.zzjhi);
                if (zza2 == null) {
                    return null;
                }
                if ((true ^ zza2.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (v instanceof Double) {
                if (zzckqVar2.zzjhi == null) {
                    zzawm().zzayt().zze("No number filter for double param. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                    return null;
                }
                Boolean zza3 = zza(((Double) v).doubleValue(), zzckqVar2.zzjhi);
                if (zza3 == null) {
                    return null;
                }
                if ((true ^ zza3.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (v instanceof String) {
                if (zzckqVar2.zzjhh != null) {
                    bool = zza((String) v, zzckqVar2.zzjhh);
                } else if (zzckqVar2.zzjhi != null) {
                    String str3 = (String) v;
                    if (zzckn.zzke(str3)) {
                        bool = zza(str3, zzckqVar2.zzjhi);
                    } else {
                        zzawm().zzayt().zze("Invalid param value for number filter. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                        return null;
                    }
                } else {
                    zzawm().zzayt().zze("No filter for String param. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                    return null;
                }
                if (bool == null) {
                    return null;
                }
                if ((true ^ bool.booleanValue()) ^ equals) {
                    return false;
                }
            } else if (v == 0) {
                zzawm().zzayx().zze("Missing param for filter. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                return false;
            } else {
                zzawm().zzayt().zze("Unknown param type. event, param", zzawh().zzjb(zzckyVar.name), zzawh().zzjc(str2));
                return null;
            }
        }
        return true;
    }

    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        boolean startsWith;
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && i != 1) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException unused) {
                    zzawm().zzayt().zzj("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                startsWith = str.startsWith(str2);
                break;
            case 3:
                startsWith = str.endsWith(str2);
                break;
            case 4:
                startsWith = str.contains(str2);
                break;
            case 5:
                startsWith = str.equals(str2);
                break;
            case 6:
                startsWith = list.contains(str);
                break;
            default:
                return null;
        }
        return Boolean.valueOf(startsWith);
    }

    private final Boolean zza(String str, zzckr zzckrVar) {
        if (!zzckn.zzke(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzckrVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(String str, zzckt zzcktVar) {
        List<String> list;
        zzbq.checkNotNull(zzcktVar);
        if (str == null || zzcktVar.zzjht == null || zzcktVar.zzjht.intValue() == 0) {
            return null;
        }
        if (zzcktVar.zzjht.intValue() == 6) {
            if (zzcktVar.zzjhw == null || zzcktVar.zzjhw.length == 0) {
                return null;
            }
        } else if (zzcktVar.zzjhu == null) {
            return null;
        }
        int intValue = zzcktVar.zzjht.intValue();
        boolean z = zzcktVar.zzjhv != null && zzcktVar.zzjhv.booleanValue();
        String upperCase = (z || intValue == 1 || intValue == 6) ? zzcktVar.zzjhu : zzcktVar.zzjhu.toUpperCase(Locale.ENGLISH);
        if (zzcktVar.zzjhw == null) {
            list = null;
        } else {
            String[] strArr = zzcktVar.zzjhw;
            if (z) {
                list = Arrays.asList(strArr);
            } else {
                ArrayList arrayList = new ArrayList();
                for (String str2 : strArr) {
                    arrayList.add(str2.toUpperCase(Locale.ENGLISH));
                }
                list = arrayList;
            }
        }
        return zza(str, intValue, z, upperCase, list, intValue == 1 ? upperCase : null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0070, code lost:
        if (r3 != null) goto L_0x0072;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Boolean zza(java.math.BigDecimal r7, com.google.android.gms.internal.zzckr r8, double r9) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfh.zza(java.math.BigDecimal, com.google.android.gms.internal.zzckr, double):java.lang.Boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x05ca  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x05cd  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x05dc  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzckx[] zza(java.lang.String r54, com.google.android.gms.internal.zzcky[] r55, com.google.android.gms.internal.zzcld[] r56) {
        /*
            Method dump skipped, instructions count: 1875
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfh.zza(java.lang.String, com.google.android.gms.internal.zzcky[], com.google.android.gms.internal.zzcld[]):com.google.android.gms.internal.zzckx[]");
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        return false;
    }
}
