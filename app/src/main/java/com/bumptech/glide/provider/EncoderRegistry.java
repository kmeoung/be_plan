package com.bumptech.glide.provider;

import android.support.annotation.Nullable;
import com.bumptech.glide.load.Encoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class EncoderRegistry {
    private final List<Entry<?>> encoders = new ArrayList();

    @Nullable
    public synchronized <T> Encoder<T> getEncoder(Class<T> cls) {
        for (Entry<?> entry : this.encoders) {
            if (entry.handles(cls)) {
                return (Encoder<T>) entry.encoder;
            }
        }
        return null;
    }

    public synchronized <T> void append(Class<T> cls, Encoder<T> encoder) {
        this.encoders.add(new Entry<>(cls, encoder));
    }

    public synchronized <T> void prepend(Class<T> cls, Encoder<T> encoder) {
        this.encoders.add(0, new Entry<>(cls, encoder));
    }

    /* loaded from: classes.dex */
    public static final class Entry<T> {
        private final Class<T> dataClass;
        final Encoder<T> encoder;

        public Entry(Class<T> cls, Encoder<T> encoder) {
            this.dataClass = cls;
            this.encoder = encoder;
        }

        public boolean handles(Class<?> cls) {
            return this.dataClass.isAssignableFrom(cls);
        }
    }
}
