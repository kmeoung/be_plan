package com.bumptech.glide.request;

import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public class ThumbnailRequestCoordinator implements RequestCoordinator, Request {
    @Nullable
    private RequestCoordinator coordinator;
    private Request full;
    private boolean isRunning;
    private Request thumb;

    public ThumbnailRequestCoordinator() {
        this(null);
    }

    public ThumbnailRequestCoordinator(RequestCoordinator requestCoordinator) {
        this.coordinator = requestCoordinator;
    }

    public void setRequests(Request request, Request request2) {
        this.full = request;
        this.thumb = request2;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canSetImage(Request request) {
        return parentCanSetImage() && (request.equals(this.full) || !this.full.isResourceSet());
    }

    private boolean parentCanSetImage() {
        return this.coordinator == null || this.coordinator.canSetImage(this);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyStatusChanged(Request request) {
        return parentCanNotifyStatusChanged() && request.equals(this.full) && !isAnyResourceSet();
    }

    private boolean parentCanNotifyStatusChanged() {
        return this.coordinator == null || this.coordinator.canNotifyStatusChanged(this);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean isAnyResourceSet() {
        return parentIsAnyResourceSet() || isResourceSet();
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestSuccess(Request request) {
        if (!request.equals(this.thumb)) {
            if (this.coordinator != null) {
                this.coordinator.onRequestSuccess(this);
            }
            if (!this.thumb.isComplete()) {
                this.thumb.clear();
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestFailed(Request request) {
        if (request.equals(this.full) && this.coordinator != null) {
            this.coordinator.onRequestFailed(this);
        }
    }

    private boolean parentIsAnyResourceSet() {
        return this.coordinator != null && this.coordinator.isAnyResourceSet();
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        this.isRunning = true;
        if (!this.thumb.isRunning()) {
            this.thumb.begin();
        }
        if (this.isRunning && !this.full.isRunning()) {
            this.full.begin();
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        this.isRunning = false;
        this.full.pause();
        this.thumb.pause();
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        this.isRunning = false;
        this.thumb.clear();
        this.full.clear();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isPaused() {
        return this.full.isPaused();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        return this.full.isRunning();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        return this.full.isComplete() || this.thumb.isComplete();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isResourceSet() {
        return this.full.isResourceSet() || this.thumb.isResourceSet();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCancelled() {
        return this.full.isCancelled();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isFailed() {
        return this.full.isFailed();
    }

    @Override // com.bumptech.glide.request.Request
    public void recycle() {
        this.full.recycle();
        this.thumb.recycle();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ThumbnailRequestCoordinator)) {
            return false;
        }
        ThumbnailRequestCoordinator thumbnailRequestCoordinator = (ThumbnailRequestCoordinator) request;
        if (this.full == null) {
            if (thumbnailRequestCoordinator.full != null) {
                return false;
            }
        } else if (!this.full.isEquivalentTo(thumbnailRequestCoordinator.full)) {
            return false;
        }
        if (this.thumb == null) {
            if (thumbnailRequestCoordinator.thumb != null) {
                return false;
            }
        } else if (!this.thumb.isEquivalentTo(thumbnailRequestCoordinator.thumb)) {
            return false;
        }
        return true;
    }
}
