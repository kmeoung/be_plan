package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfcz;
import com.google.android.gms.internal.zzfda;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/* loaded from: classes.dex */
public abstract class zzfcz<MessageType extends zzfcz<MessageType, BuilderType>, BuilderType extends zzfda<MessageType, BuilderType>> implements zzffi {
    private static boolean zzpag = false;
    protected int zzpaf = 0;

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzfda.zza(iterable, list);
    }

    @Override // com.google.android.gms.internal.zzffi
    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzhl()];
            zzfdv zzbb = zzfdv.zzbb(bArr);
            zza(zzbb);
            zzbb.zzcus();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("byte array").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    @Override // com.google.android.gms.internal.zzffi
    public final zzfdh toByteString() {
        try {
            zzfdm zzke = zzfdh.zzke(zzhl());
            zza(zzke.zzctr());
            return zzke.zzctq();
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("ByteString").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    @Override // com.google.android.gms.internal.zzffi
    public final void writeTo(OutputStream outputStream) throws IOException {
        zzfdv zzb = zzfdv.zzb(outputStream, zzfdv.zzkr(zzhl()));
        zza(zzb);
        zzb.flush();
    }
}
