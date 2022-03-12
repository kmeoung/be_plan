package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GifFrameLoader {
    private final BitmapPool bitmapPool;
    private final List<FrameCallback> callbacks;
    private DelayTarget current;
    private Bitmap firstFrame;
    private final GifDecoder gifDecoder;
    private final Handler handler;
    private boolean isCleared;
    private boolean isLoadPending;
    private boolean isRunning;
    private DelayTarget next;
    @Nullable
    private OnEveryFrameListener onEveryFrameListener;
    private DelayTarget pendingTarget;
    private RequestBuilder<Bitmap> requestBuilder;
    final RequestManager requestManager;
    private boolean startFromFirstFrame;
    private Transformation<Bitmap> transformation;

    /* loaded from: classes.dex */
    public interface FrameCallback {
        void onFrameReady();
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    public interface OnEveryFrameListener {
        void onFrameReady();
    }

    public GifFrameLoader(Glide glide, GifDecoder gifDecoder, int i, int i2, Transformation<Bitmap> transformation, Bitmap bitmap) {
        this(glide.getBitmapPool(), Glide.with(glide.getContext()), gifDecoder, null, getRequestBuilder(Glide.with(glide.getContext()), i, i2), transformation, bitmap);
    }

    GifFrameLoader(BitmapPool bitmapPool, RequestManager requestManager, GifDecoder gifDecoder, Handler handler, RequestBuilder<Bitmap> requestBuilder, Transformation<Bitmap> transformation, Bitmap bitmap) {
        this.callbacks = new ArrayList();
        this.isRunning = false;
        this.isLoadPending = false;
        this.startFromFirstFrame = false;
        this.requestManager = requestManager;
        handler = handler == null ? new Handler(Looper.getMainLooper(), new FrameLoaderCallback()) : handler;
        this.bitmapPool = bitmapPool;
        this.handler = handler;
        this.requestBuilder = requestBuilder;
        this.gifDecoder = gifDecoder;
        setFrameTransformation(transformation, bitmap);
    }

    public void setFrameTransformation(Transformation<Bitmap> transformation, Bitmap bitmap) {
        this.transformation = (Transformation) Preconditions.checkNotNull(transformation);
        this.firstFrame = (Bitmap) Preconditions.checkNotNull(bitmap);
        this.requestBuilder = this.requestBuilder.apply(new RequestOptions().transform(transformation));
    }

    public Transformation<Bitmap> getFrameTransformation() {
        return this.transformation;
    }

    public Bitmap getFirstFrame() {
        return this.firstFrame;
    }

    public void subscribe(FrameCallback frameCallback) {
        if (this.isCleared) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        }
        boolean isEmpty = this.callbacks.isEmpty();
        if (this.callbacks.contains(frameCallback)) {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
        this.callbacks.add(frameCallback);
        if (isEmpty) {
            start();
        }
    }

    public void unsubscribe(FrameCallback frameCallback) {
        this.callbacks.remove(frameCallback);
        if (this.callbacks.isEmpty()) {
            stop();
        }
    }

    public int getWidth() {
        return getCurrentFrame().getWidth();
    }

    public int getHeight() {
        return getCurrentFrame().getHeight();
    }

    public int getSize() {
        return this.gifDecoder.getByteSize() + getFrameSize();
    }

    public int getCurrentIndex() {
        if (this.current != null) {
            return this.current.index;
        }
        return -1;
    }

    private int getFrameSize() {
        return Util.getBitmapByteSize(getCurrentFrame().getWidth(), getCurrentFrame().getHeight(), getCurrentFrame().getConfig());
    }

    public ByteBuffer getBuffer() {
        return this.gifDecoder.getData().asReadOnlyBuffer();
    }

    public int getFrameCount() {
        return this.gifDecoder.getFrameCount();
    }

    public int getLoopCount() {
        return this.gifDecoder.getTotalIterationCount();
    }

    private void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.isCleared = false;
            loadNextFrame();
        }
    }

    private void stop() {
        this.isRunning = false;
    }

    public void clear() {
        this.callbacks.clear();
        recycleFirstFrame();
        stop();
        if (this.current != null) {
            this.requestManager.clear(this.current);
            this.current = null;
        }
        if (this.next != null) {
            this.requestManager.clear(this.next);
            this.next = null;
        }
        if (this.pendingTarget != null) {
            this.requestManager.clear(this.pendingTarget);
            this.pendingTarget = null;
        }
        this.gifDecoder.clear();
        this.isCleared = true;
    }

    public Bitmap getCurrentFrame() {
        return this.current != null ? this.current.getResource() : this.firstFrame;
    }

    private void loadNextFrame() {
        if (this.isRunning && !this.isLoadPending) {
            if (this.startFromFirstFrame) {
                Preconditions.checkArgument(this.pendingTarget == null, "Pending target must be null when starting from the first frame");
                this.gifDecoder.resetFrameIndex();
                this.startFromFirstFrame = false;
            }
            if (this.pendingTarget != null) {
                DelayTarget delayTarget = this.pendingTarget;
                this.pendingTarget = null;
                onFrameReady(delayTarget);
                return;
            }
            this.isLoadPending = true;
            long uptimeMillis = SystemClock.uptimeMillis() + this.gifDecoder.getNextDelay();
            this.gifDecoder.advance();
            this.next = new DelayTarget(this.handler, this.gifDecoder.getCurrentFrameIndex(), uptimeMillis);
            this.requestBuilder.apply(RequestOptions.signatureOf(getFrameSignature())).load(this.gifDecoder).into((RequestBuilder<Bitmap>) this.next);
        }
    }

    private void recycleFirstFrame() {
        if (this.firstFrame != null) {
            this.bitmapPool.put(this.firstFrame);
            this.firstFrame = null;
        }
    }

    public void setNextStartFromFirstFrame() {
        Preconditions.checkArgument(!this.isRunning, "Can't restart a running animation");
        this.startFromFirstFrame = true;
        if (this.pendingTarget != null) {
            this.requestManager.clear(this.pendingTarget);
            this.pendingTarget = null;
        }
    }

    @VisibleForTesting
    void setOnEveryFrameReadyListener(@Nullable OnEveryFrameListener onEveryFrameListener) {
        this.onEveryFrameListener = onEveryFrameListener;
    }

    @VisibleForTesting
    void onFrameReady(DelayTarget delayTarget) {
        if (this.onEveryFrameListener != null) {
            this.onEveryFrameListener.onFrameReady();
        }
        this.isLoadPending = false;
        if (this.isCleared) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
        } else if (!this.isRunning) {
            this.pendingTarget = delayTarget;
        } else {
            if (delayTarget.getResource() != null) {
                recycleFirstFrame();
                DelayTarget delayTarget2 = this.current;
                this.current = delayTarget;
                for (int size = this.callbacks.size() - 1; size >= 0; size--) {
                    this.callbacks.get(size).onFrameReady();
                }
                if (delayTarget2 != null) {
                    this.handler.obtainMessage(2, delayTarget2).sendToTarget();
                }
            }
            loadNextFrame();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class FrameLoaderCallback implements Handler.Callback {
        public static final int MSG_CLEAR = 2;
        public static final int MSG_DELAY = 1;

        FrameLoaderCallback() {
            GifFrameLoader.this = r1;
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                GifFrameLoader.this.onFrameReady((DelayTarget) message.obj);
                return true;
            } else if (message.what != 2) {
                return false;
            } else {
                GifFrameLoader.this.requestManager.clear((DelayTarget) message.obj);
                return false;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class DelayTarget extends SimpleTarget<Bitmap> {
        private final Handler handler;
        final int index;
        private Bitmap resource;
        private final long targetTime;

        @Override // com.bumptech.glide.request.target.Target
        public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
            onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
        }

        DelayTarget(Handler handler, int i, long j) {
            this.handler = handler;
            this.index = i;
            this.targetTime = j;
        }

        Bitmap getResource() {
            return this.resource;
        }

        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
            this.resource = bitmap;
            this.handler.sendMessageAtTime(this.handler.obtainMessage(1, this), this.targetTime);
        }
    }

    private static RequestBuilder<Bitmap> getRequestBuilder(RequestManager requestManager, int i, int i2) {
        return requestManager.asBitmap().apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).useAnimationPool(true).skipMemoryCache(true).override(i, i2));
    }

    private static Key getFrameSignature() {
        return new ObjectKey(Double.valueOf(Math.random()));
    }
}
