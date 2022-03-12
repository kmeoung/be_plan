package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfed;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzfeb<FieldDescriptorType extends zzfed<FieldDescriptorType>> {
    private static final zzfeb zzpbp = new zzfeb(true);
    private boolean zzkqq;
    private boolean zzpbo = false;
    private final zzffu<FieldDescriptorType, Object> zzpbn = zzffu.zzlp(16);

    private zzfeb() {
    }

    private zzfeb(boolean z) {
        if (!this.zzkqq) {
            this.zzpbn.zzbim();
            this.zzkqq = true;
        }
    }

    public static int zza(zzfgr zzfgrVar, int i, Object obj) {
        int i2;
        int zzkw = zzfdv.zzkw(i);
        if (zzfgrVar == zzfgr.GROUP) {
            zzfer.zzg((zzffi) obj);
            zzkw <<= 1;
        }
        switch (zzfgrVar) {
            case DOUBLE:
                i2 = zzfdv.zzn(((Double) obj).doubleValue());
                break;
            case FLOAT:
                i2 = zzfdv.zzf(((Float) obj).floatValue());
                break;
            case INT64:
                i2 = zzfdv.zzcv(((Long) obj).longValue());
                break;
            case UINT64:
                i2 = zzfdv.zzcw(((Long) obj).longValue());
                break;
            case INT32:
                i2 = zzfdv.zzkx(((Integer) obj).intValue());
                break;
            case FIXED64:
                i2 = zzfdv.zzcy(((Long) obj).longValue());
                break;
            case FIXED32:
                i2 = zzfdv.zzla(((Integer) obj).intValue());
                break;
            case BOOL:
                i2 = zzfdv.zzda(((Boolean) obj).booleanValue());
                break;
            case GROUP:
                i2 = zzfdv.zzf((zzffi) obj);
                break;
            case MESSAGE:
                if (!(obj instanceof zzfey)) {
                    i2 = zzfdv.zze((zzffi) obj);
                    break;
                } else {
                    i2 = zzfdv.zza((zzfey) obj);
                    break;
                }
            case STRING:
                if (!(obj instanceof zzfdh)) {
                    i2 = zzfdv.zztd((String) obj);
                    break;
                }
                i2 = zzfdv.zzan((zzfdh) obj);
                break;
            case BYTES:
                if (!(obj instanceof zzfdh)) {
                    i2 = zzfdv.zzbc((byte[]) obj);
                    break;
                }
                i2 = zzfdv.zzan((zzfdh) obj);
                break;
            case UINT32:
                i2 = zzfdv.zzky(((Integer) obj).intValue());
                break;
            case SFIXED32:
                i2 = zzfdv.zzlb(((Integer) obj).intValue());
                break;
            case SFIXED64:
                i2 = zzfdv.zzcz(((Long) obj).longValue());
                break;
            case SINT32:
                i2 = zzfdv.zzkz(((Integer) obj).intValue());
                break;
            case SINT64:
                i2 = zzfdv.zzcx(((Long) obj).longValue());
                break;
            case ENUM:
                i2 = zzfdv.zzlc(obj instanceof zzfes ? ((zzfes) obj).zzhn() : ((Integer) obj).intValue());
                break;
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
        return zzkw + i2;
    }

    public static Object zza(zzfdq zzfdqVar, zzfgr zzfgrVar, boolean z) throws IOException {
        zzfgx zzfgxVar = zzfgx.STRICT;
        switch (zzfgrVar) {
            case DOUBLE:
                return Double.valueOf(zzfdqVar.readDouble());
            case FLOAT:
                return Float.valueOf(zzfdqVar.readFloat());
            case INT64:
                return Long.valueOf(zzfdqVar.zzctu());
            case UINT64:
                return Long.valueOf(zzfdqVar.zzctt());
            case INT32:
                return Integer.valueOf(zzfdqVar.zzctv());
            case FIXED64:
                return Long.valueOf(zzfdqVar.zzctw());
            case FIXED32:
                return Integer.valueOf(zzfdqVar.zzctx());
            case BOOL:
                return Boolean.valueOf(zzfdqVar.zzcty());
            case BYTES:
                return zzfdqVar.zzcua();
            case UINT32:
                return Integer.valueOf(zzfdqVar.zzcub());
            case SFIXED32:
                return Integer.valueOf(zzfdqVar.zzcud());
            case SFIXED64:
                return Long.valueOf(zzfdqVar.zzcue());
            case SINT32:
                return Integer.valueOf(zzfdqVar.zzcuf());
            case SINT64:
                return Long.valueOf(zzfdqVar.zzcug());
            case STRING:
                return zzfgxVar.zzb(zzfdqVar);
            case GROUP:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case MESSAGE:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case ENUM:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static void zza(zzfdv zzfdvVar, zzfgr zzfgrVar, int i, Object obj) throws IOException {
        if (zzfgrVar == zzfgr.GROUP) {
            zzffi zzffiVar = (zzffi) obj;
            zzfer.zzg(zzffiVar);
            zzfdvVar.zzz(i, 3);
            zzffiVar.zza(zzfdvVar);
            zzfdvVar.zzz(i, 4);
            return;
        }
        zzfdvVar.zzz(i, zzfgrVar.zzcxd());
        switch (zzfgrVar) {
            case DOUBLE:
                zzfdvVar.zzcu(Double.doubleToRawLongBits(((Double) obj).doubleValue()));
                return;
            case FLOAT:
                zzfdvVar.zzkv(Float.floatToRawIntBits(((Float) obj).floatValue()));
                return;
            case INT64:
                zzfdvVar.zzcs(((Long) obj).longValue());
                return;
            case UINT64:
                zzfdvVar.zzcs(((Long) obj).longValue());
                return;
            case INT32:
                zzfdvVar.zzks(((Integer) obj).intValue());
                return;
            case FIXED64:
                zzfdvVar.zzcu(((Long) obj).longValue());
                return;
            case FIXED32:
                zzfdvVar.zzkv(((Integer) obj).intValue());
                return;
            case BOOL:
                zzfdvVar.zzb(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
                return;
            case GROUP:
                ((zzffi) obj).zza(zzfdvVar);
                return;
            case MESSAGE:
                zzfdvVar.zzd((zzffi) obj);
                return;
            case STRING:
                if (obj instanceof zzfdh) {
                    zzfdvVar.zzam((zzfdh) obj);
                    return;
                } else {
                    zzfdvVar.zztc((String) obj);
                    return;
                }
            case BYTES:
                if (obj instanceof zzfdh) {
                    zzfdvVar.zzam((zzfdh) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzfdvVar.zzi(bArr, 0, bArr.length);
                return;
            case UINT32:
                zzfdvVar.zzkt(((Integer) obj).intValue());
                return;
            case SFIXED32:
                zzfdvVar.zzkv(((Integer) obj).intValue());
                return;
            case SFIXED64:
                zzfdvVar.zzcu(((Long) obj).longValue());
                return;
            case SINT32:
                zzfdvVar.zzku(((Integer) obj).intValue());
                return;
            case SINT64:
                zzfdvVar.zzct(((Long) obj).longValue());
                return;
            case ENUM:
                if (obj instanceof zzfes) {
                    zzfdvVar.zzks(((zzfes) obj).zzhn());
                    return;
                } else {
                    zzfdvVar.zzks(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    private void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (!fielddescriptortype.zzcvc()) {
            zza(fielddescriptortype.zzcvb(), obj);
        } else if (!(obj instanceof List)) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fielddescriptortype.zzcvb(), obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof zzfey) {
            this.zzpbo = true;
        }
        this.zzpbn.zza((zzffu<FieldDescriptorType, Object>) fielddescriptortype, (FieldDescriptorType) obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0024, code lost:
        if ((r3 instanceof com.google.android.gms.internal.zzfes) == false) goto L_0x0043;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
        if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
        if ((r3 instanceof com.google.android.gms.internal.zzfey) == false) goto L_0x0043;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zza(com.google.android.gms.internal.zzfgr r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.zzfer.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.zzfec.zzpbq
            com.google.android.gms.internal.zzfgw r2 = r2.zzcxc()
            int r2 = r2.ordinal()
            r2 = r0[r2]
            r0 = 1
            r1 = 0
            switch(r2) {
                case 1: goto L_0x0040;
                case 2: goto L_0x003d;
                case 3: goto L_0x003a;
                case 4: goto L_0x0037;
                case 5: goto L_0x0034;
                case 6: goto L_0x0031;
                case 7: goto L_0x0028;
                case 8: goto L_0x001e;
                case 9: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0043
        L_0x0015:
            boolean r2 = r3 instanceof com.google.android.gms.internal.zzffi
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.zzfey
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x001e:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.zzfes
            if (r2 == 0) goto L_0x0043
        L_0x0026:
            r1 = 1
            goto L_0x0043
        L_0x0028:
            boolean r2 = r3 instanceof com.google.android.gms.internal.zzfdh
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x0031:
            boolean r0 = r3 instanceof java.lang.String
            goto L_0x0042
        L_0x0034:
            boolean r0 = r3 instanceof java.lang.Boolean
            goto L_0x0042
        L_0x0037:
            boolean r0 = r3 instanceof java.lang.Double
            goto L_0x0042
        L_0x003a:
            boolean r0 = r3 instanceof java.lang.Float
            goto L_0x0042
        L_0x003d:
            boolean r0 = r3 instanceof java.lang.Long
            goto L_0x0042
        L_0x0040:
            boolean r0 = r3 instanceof java.lang.Integer
        L_0x0042:
            r1 = r0
        L_0x0043:
            if (r1 != 0) goto L_0x004d
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            throw r2
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfeb.zza(com.google.android.gms.internal.zzfgr, java.lang.Object):void");
    }

    public static <T extends zzfed<T>> zzfeb<T> zzcva() {
        return new zzfeb<>();
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzfeb zzfebVar = new zzfeb();
        for (int i = 0; i < this.zzpbn.zzcwj(); i++) {
            Map.Entry<FieldDescriptorType, Object> zzlq = this.zzpbn.zzlq(i);
            zzfebVar.zza((zzfeb) zzlq.getKey(), zzlq.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zzpbn.zzcwk()) {
            zzfebVar.zza((zzfeb) entry.getKey(), entry.getValue());
        }
        zzfebVar.zzpbo = this.zzpbo;
        return zzfebVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfeb)) {
            return false;
        }
        return this.zzpbn.equals(((zzfeb) obj).zzpbn);
    }

    public final int hashCode() {
        return this.zzpbn.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzpbo ? new zzffb(this.zzpbn.entrySet().iterator()) : this.zzpbn.entrySet().iterator();
    }
}
