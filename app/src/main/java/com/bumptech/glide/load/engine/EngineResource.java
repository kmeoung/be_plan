package com.bumptech.glide.load.engine;

import android.os.Looper;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class EngineResource<Z> implements Resource<Z> {
    private int acquired;
    private final boolean isCacheable;
    private boolean isRecycled;
    private Key key;
    private ResourceListener listener;
    private final Resource<Z> resource;

    /* loaded from: classes.dex */
    public interface ResourceListener {
        void onResourceReleased(Key key, EngineResource<?> engineResource);
    }

    public EngineResource(Resource<Z> resource, boolean z) {
        this.resource = (Resource) Preconditions.checkNotNull(resource);
        this.isCacheable = z;
    }

    public void setResourceListener(Key key, ResourceListener resourceListener) {
        this.key = key;
        this.listener = resourceListener;
    }

    public boolean isCacheable() {
        return this.isCacheable;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public Class<Z> getResourceClass() {
        return this.resource.getResourceClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public Z get() {
        return this.resource.get();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.resource.getSize();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
        if (this.acquired > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (this.isRecycled) {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        } else {
            this.isRecycled = true;
            this.resource.recycle();
        }
    }

    public void acquire() {
        if (this.isRecycled) {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        } else if (!Looper.getMainLooper().equals(Looper.myLooper())) {
            throw new IllegalThreadStateException("Must call acquire on the main thread");
        } else {
            this.acquired++;
        }
    }

    public void release() {
        if (this.acquired <= 0) {
            throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
        } else if (!Looper.getMainLooper().equals(Looper.myLooper())) {
            throw new IllegalThreadStateException("Must call release on the main thread");
        } else {
            int i = this.acquired - 1;
            this.acquired = i;
            if (i == 0) {
                this.listener.onResourceReleased(this.key, this);
            }
        }
    }

    public String toString() {
        return "EngineResource{isCacheable=" + this.isCacheable + ", listener=" + this.listener + ", key=" + this.key + ", acquired=" + this.acquired + ", isRecycled=" + this.isRecycled + ", resource=" + this.resource + '}';
    }
}
