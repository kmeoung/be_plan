package com.google.android.gms.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzffh<K, V> extends LinkedHashMap<K, V> {
    private static final zzffh zzpdd;
    private boolean zzpah = true;

    static {
        zzffh zzffhVar = new zzffh();
        zzpdd = zzffhVar;
        zzffhVar.zzpah = false;
    }

    private zzffh() {
    }

    private zzffh(Map<K, V> map) {
        super(map);
    }

    private static int zzcm(Object obj) {
        if (obj instanceof byte[]) {
            return zzfer.hashCode((byte[]) obj);
        }
        if (!(obj instanceof zzfes)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public static <K, V> zzffh<K, V> zzcwc() {
        return zzpdd;
    }

    private final void zzcwe() {
        if (!this.zzpah) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void clear() {
        zzcwe();
        super.clear();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005c A[RETURN] */
    @Override // java.util.AbstractMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof java.util.Map
            r1 = 0
            if (r0 == 0) goto L_0x005d
            java.util.Map r7 = (java.util.Map) r7
            r0 = 1
            if (r6 == r7) goto L_0x0059
            int r2 = r6.size()
            int r3 = r7.size()
            if (r2 == r3) goto L_0x0016
        L_0x0014:
            r7 = 0
            goto L_0x005a
        L_0x0016:
            java.util.Set r2 = r6.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x001e:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0059
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            boolean r4 = r7.containsKey(r4)
            if (r4 != 0) goto L_0x0035
            goto L_0x0014
        L_0x0035:
            java.lang.Object r4 = r3.getValue()
            java.lang.Object r3 = r3.getKey()
            java.lang.Object r3 = r7.get(r3)
            boolean r5 = r4 instanceof byte[]
            if (r5 == 0) goto L_0x0052
            boolean r5 = r3 instanceof byte[]
            if (r5 == 0) goto L_0x0052
            byte[] r4 = (byte[]) r4
            byte[] r3 = (byte[]) r3
            boolean r3 = java.util.Arrays.equals(r4, r3)
            goto L_0x0056
        L_0x0052:
            boolean r3 = r4.equals(r3)
        L_0x0056:
            if (r3 != 0) goto L_0x001e
            goto L_0x0014
        L_0x0059:
            r7 = 1
        L_0x005a:
            if (r7 == 0) goto L_0x005d
            return r0
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffh.equals(java.lang.Object):boolean");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        int i = 0;
        for (Map.Entry<K, V> entry : entrySet()) {
            i += zzcm(entry.getValue()) ^ zzcm(entry.getKey());
        }
        return i;
    }

    public final boolean isMutable() {
        return this.zzpah;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V put(K k, V v) {
        zzcwe();
        zzfer.checkNotNull(k);
        zzfer.checkNotNull(v);
        return (V) super.put(k, v);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        zzcwe();
        for (Object obj : map.keySet()) {
            zzfer.checkNotNull(obj);
            zzfer.checkNotNull(map.get(obj));
        }
        super.putAll(map);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final V remove(Object obj) {
        zzcwe();
        return (V) super.remove(obj);
    }

    public final void zza(zzffh<K, V> zzffhVar) {
        zzcwe();
        if (!zzffhVar.isEmpty()) {
            putAll(zzffhVar);
        }
    }

    public final void zzbim() {
        this.zzpah = false;
    }

    public final zzffh<K, V> zzcwd() {
        return isEmpty() ? new zzffh<>() : new zzffh<>(this);
    }
}
