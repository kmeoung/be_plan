package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.util.List;
import java.util.Queue;

/* loaded from: classes.dex */
public class ListPreloader<T> implements AbsListView.OnScrollListener {
    private boolean isIncreasing = true;
    private int lastEnd;
    private int lastFirstVisible;
    private int lastStart;
    private final int maxPreload;
    private final PreloadSizeProvider<T> preloadDimensionProvider;
    private final PreloadModelProvider<T> preloadModelProvider;
    private final PreloadTargetQueue preloadTargetQueue;
    private final RequestManager requestManager;
    private int totalItemCount;

    /* loaded from: classes.dex */
    public interface PreloadModelProvider<U> {
        @NonNull
        List<U> getPreloadItems(int i);

        @Nullable
        RequestBuilder getPreloadRequestBuilder(U u);
    }

    /* loaded from: classes.dex */
    public interface PreloadSizeProvider<T> {
        @Nullable
        int[] getPreloadSize(T t, int i, int i2);
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public ListPreloader(RequestManager requestManager, PreloadModelProvider<T> preloadModelProvider, PreloadSizeProvider<T> preloadSizeProvider, int i) {
        this.requestManager = requestManager;
        this.preloadModelProvider = preloadModelProvider;
        this.preloadDimensionProvider = preloadSizeProvider;
        this.maxPreload = i;
        this.preloadTargetQueue = new PreloadTargetQueue(i + 1);
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        this.totalItemCount = i3;
        if (i > this.lastFirstVisible) {
            preload(i2 + i, true);
        } else if (i < this.lastFirstVisible) {
            preload(i, false);
        }
        this.lastFirstVisible = i;
    }

    private void preload(int i, boolean z) {
        if (this.isIncreasing != z) {
            this.isIncreasing = z;
            cancelAll();
        }
        preload(i, (z ? this.maxPreload : -this.maxPreload) + i);
    }

    private void preload(int i, int i2) {
        int i3;
        int i4;
        if (i < i2) {
            i3 = Math.max(this.lastEnd, i);
            i4 = i2;
        } else {
            i4 = Math.min(this.lastStart, i);
            i3 = i2;
        }
        int min = Math.min(this.totalItemCount, i4);
        int min2 = Math.min(this.totalItemCount, Math.max(0, i3));
        if (i < i2) {
            for (int i5 = min2; i5 < min; i5++) {
                preloadAdapterPosition(this.preloadModelProvider.getPreloadItems(i5), i5, true);
            }
        } else {
            for (int i6 = min - 1; i6 >= min2; i6--) {
                preloadAdapterPosition(this.preloadModelProvider.getPreloadItems(i6), i6, false);
            }
        }
        this.lastStart = min2;
        this.lastEnd = min;
    }

    private void preloadAdapterPosition(List<T> list, int i, boolean z) {
        int size = list.size();
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                preloadItem(list.get(i2), i, i2);
            }
            return;
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            preloadItem(list.get(i3), i, i3);
        }
    }

    private void preloadItem(@Nullable T t, int i, int i2) {
        int[] preloadSize;
        RequestBuilder preloadRequestBuilder;
        if (t != null && (preloadSize = this.preloadDimensionProvider.getPreloadSize(t, i, i2)) != null && (preloadRequestBuilder = this.preloadModelProvider.getPreloadRequestBuilder(t)) != null) {
            preloadRequestBuilder.into((RequestBuilder) this.preloadTargetQueue.next(preloadSize[0], preloadSize[1]));
        }
    }

    private void cancelAll() {
        for (int i = 0; i < this.maxPreload; i++) {
            this.requestManager.clear(this.preloadTargetQueue.next(0, 0));
        }
    }

    /* loaded from: classes.dex */
    public static final class PreloadTargetQueue {
        private final Queue<PreloadTarget> queue;

        PreloadTargetQueue(int i) {
            this.queue = Util.createQueue(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.queue.offer(new PreloadTarget());
            }
        }

        public PreloadTarget next(int i, int i2) {
            PreloadTarget poll = this.queue.poll();
            this.queue.offer(poll);
            poll.photoWidth = i;
            poll.photoHeight = i2;
            return poll;
        }
    }

    /* loaded from: classes.dex */
    public static final class PreloadTarget extends BaseTarget<Object> {
        int photoHeight;
        int photoWidth;

        @Override // com.bumptech.glide.request.target.Target
        public void onResourceReady(Object obj, Transition<? super Object> transition) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void removeCallback(SizeReadyCallback sizeReadyCallback) {
        }

        PreloadTarget() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void getSize(SizeReadyCallback sizeReadyCallback) {
            sizeReadyCallback.onSizeReady(this.photoWidth, this.photoHeight);
        }
    }
}
