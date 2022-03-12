package com.bumptech.glide.request;

/* loaded from: classes.dex */
public final class ErrorRequestCoordinator implements RequestCoordinator, Request {
    private final RequestCoordinator coordinator;
    private Request error;
    private Request primary;

    public ErrorRequestCoordinator(RequestCoordinator requestCoordinator) {
        this.coordinator = requestCoordinator;
    }

    public void setRequests(Request request, Request request2) {
        this.primary = request;
        this.error = request2;
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        if (!this.primary.isRunning()) {
            this.primary.begin();
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        if (!this.primary.isFailed()) {
            this.primary.pause();
        }
        if (this.error.isRunning()) {
            this.error.pause();
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        if (this.primary.isFailed()) {
            this.error.clear();
        } else {
            this.primary.clear();
        }
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isPaused() {
        return (this.primary.isFailed() ? this.error : this.primary).isPaused();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        return (this.primary.isFailed() ? this.error : this.primary).isRunning();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        return (this.primary.isFailed() ? this.error : this.primary).isComplete();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isResourceSet() {
        return (this.primary.isFailed() ? this.error : this.primary).isResourceSet();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCancelled() {
        return (this.primary.isFailed() ? this.error : this.primary).isCancelled();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isFailed() {
        return this.primary.isFailed() && this.error.isFailed();
    }

    @Override // com.bumptech.glide.request.Request
    public void recycle() {
        this.primary.recycle();
        this.error.recycle();
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ErrorRequestCoordinator)) {
            return false;
        }
        ErrorRequestCoordinator errorRequestCoordinator = (ErrorRequestCoordinator) request;
        return this.primary.isEquivalentTo(errorRequestCoordinator.primary) && this.error.isEquivalentTo(errorRequestCoordinator.error);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canSetImage(Request request) {
        return parentCanSetImage() && isValidRequest(request);
    }

    private boolean parentCanSetImage() {
        return this.coordinator == null || this.coordinator.canSetImage(this);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyStatusChanged(Request request) {
        return parentCanNotifyStatusChanged() && isValidRequest(request);
    }

    private boolean parentCanNotifyStatusChanged() {
        return this.coordinator == null || this.coordinator.canNotifyStatusChanged(this);
    }

    private boolean isValidRequest(Request request) {
        return request.equals(this.primary) || (this.primary.isFailed() && request.equals(this.error));
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean isAnyResourceSet() {
        return parentIsAnyResourceSet() || isResourceSet();
    }

    private boolean parentIsAnyResourceSet() {
        return this.coordinator != null && this.coordinator.isAnyResourceSet();
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestSuccess(Request request) {
        if (this.coordinator != null) {
            this.coordinator.onRequestSuccess(this);
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestFailed(Request request) {
        if (!request.equals(this.error)) {
            if (!this.error.isRunning()) {
                this.error.begin();
            }
        } else if (this.coordinator != null) {
            this.coordinator.onRequestFailed(this.error);
        }
    }
}
