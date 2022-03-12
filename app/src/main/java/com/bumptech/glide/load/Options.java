package com.bumptech.glide.load;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import java.security.MessageDigest;
import java.util.Map;

/* loaded from: classes.dex */
public final class Options implements Key {
    private final ArrayMap<Option<?>, Object> values = new ArrayMap<>();

    public void putAll(Options options) {
        this.values.putAll((SimpleArrayMap<? extends Option<?>, ? extends Object>) options.values);
    }

    public <T> Options set(Option<T> option, T t) {
        this.values.put(option, t);
        return this;
    }

    public <T> T get(Option<T> option) {
        return this.values.containsKey(option) ? (T) this.values.get(option) : option.getDefaultValue();
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof Options) {
            return this.values.equals(((Options) obj).values);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        for (Map.Entry<Option<?>, Object> entry : this.values.entrySet()) {
            updateDiskCacheKey(entry.getKey(), entry.getValue(), messageDigest);
        }
    }

    public String toString() {
        return "Options{values=" + this.values + '}';
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void updateDiskCacheKey(Option<T> option, Object obj, MessageDigest messageDigest) {
        option.update(obj, messageDigest);
    }
}
