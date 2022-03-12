package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class zzn<T> extends IObjectWrapper.zza {
    private final T mWrappedObject;

    private zzn(T t) {
        this.mWrappedObject = t;
    }

    public static <T> T zzx(IObjectWrapper iObjectWrapper) {
        if (iObjectWrapper instanceof zzn) {
            return ((zzn) iObjectWrapper).mWrappedObject;
        }
        IBinder asBinder = iObjectWrapper.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        Field field = null;
        int i = 0;
        for (Field field2 : declaredFields) {
            if (!field2.isSynthetic()) {
                i++;
                field = field2;
            }
        }
        if (i != 1) {
            int length = declaredFields.length;
            StringBuilder sb = new StringBuilder(64);
            sb.append("Unexpected number of IObjectWrapper declared fields: ");
            sb.append(length);
            throw new IllegalArgumentException(sb.toString());
        } else if (!field.isAccessible()) {
            field.setAccessible(true);
            try {
                return (T) field.get(asBinder);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Could not access the field in remoteBinder.", e);
            } catch (NullPointerException e2) {
                throw new IllegalArgumentException("Binder object is null.", e2);
            }
        } else {
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
    }

    public static <T> IObjectWrapper zzy(T t) {
        return new zzn(t);
    }
}
