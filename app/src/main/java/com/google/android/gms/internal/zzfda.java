package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfcz;
import com.google.android.gms.internal.zzfda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public abstract class zzfda<MessageType extends zzfcz<MessageType, BuilderType>, BuilderType extends zzfda<MessageType, BuilderType>> implements zzffj {
    public static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzfer.checkNotNull(iterable);
        if (iterable instanceof zzffd) {
            List<?> zzcwb = ((zzffd) iterable).zzcwb();
            zzffd zzffdVar = (zzffd) list;
            int size = list.size();
            for (Object obj : zzcwb) {
                if (obj == null) {
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(zzffdVar.size() - size);
                    sb.append(" is null.");
                    String sb2 = sb.toString();
                    for (int size2 = zzffdVar.size() - 1; size2 >= size; size2--) {
                        zzffdVar.remove(size2);
                    }
                    throw new NullPointerException(sb2);
                } else if (!(obj instanceof zzfdh)) {
                    zzffdVar.add((String) obj);
                }
            }
        } else if (iterable instanceof zzffn) {
            list.addAll((Collection) iterable);
        } else {
            zzb(iterable, list);
        }
    }

    private static <T> void zzb(Iterable<T> iterable, List<? super T> list) {
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size = list.size();
        for (T t : iterable) {
            if (t == null) {
                StringBuilder sb = new StringBuilder(37);
                sb.append("Element at index ");
                sb.append(list.size() - size);
                sb.append(" is null.");
                String sb2 = sb.toString();
                for (int size2 = list.size() - 1; size2 >= size; size2--) {
                    list.remove(size2);
                }
                throw new NullPointerException(sb2);
            }
            list.add(t);
        }
    }

    protected abstract BuilderType zza(MessageType messagetype);

    /* renamed from: zza */
    public abstract BuilderType zzb(zzfdq zzfdqVar, zzfea zzfeaVar) throws IOException;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.zzffj
    public final /* synthetic */ zzffj zzc(zzffi zzffiVar) {
        if (zzcvh().getClass().isInstance(zzffiVar)) {
            return zza((zzfcz) zzffiVar);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }

    /* renamed from: zzctg */
    public abstract BuilderType clone();
}
