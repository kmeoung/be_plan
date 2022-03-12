package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
/* loaded from: classes.dex */
public class DefaultFailureCache implements FailureCache {
    static final int DEFAULT_MAX_SIZE = 1000;
    static final int MAX_UPDATE_TRIES = 10;
    private final int maxSize;
    private final ConcurrentMap<String, FailureCacheValue> storage;

    public DefaultFailureCache() {
        this(1000);
    }

    public DefaultFailureCache(int i) {
        this.maxSize = i;
        this.storage = new ConcurrentHashMap();
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.FailureCache
    public int getErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        FailureCacheValue failureCacheValue = this.storage.get(str);
        if (failureCacheValue != null) {
            return failureCacheValue.getErrorCount();
        }
        return 0;
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.FailureCache
    public void resetErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        this.storage.remove(str);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.FailureCache
    public void increaseErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        updateValue(str);
        removeOldestEntryIfMapSizeExceeded();
    }

    private void updateValue(String str) {
        for (int i = 0; i < 10; i++) {
            FailureCacheValue failureCacheValue = this.storage.get(str);
            if (failureCacheValue == null) {
                if (this.storage.putIfAbsent(str, new FailureCacheValue(str, 1)) == null) {
                    return;
                }
            } else {
                int errorCount = failureCacheValue.getErrorCount();
                if (errorCount != Integer.MAX_VALUE) {
                    if (this.storage.replace(str, failureCacheValue, new FailureCacheValue(str, errorCount + 1))) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void removeOldestEntryIfMapSizeExceeded() {
        FailureCacheValue findValueWithOldestTimestamp;
        if (this.storage.size() > this.maxSize && (findValueWithOldestTimestamp = findValueWithOldestTimestamp()) != null) {
            this.storage.remove(findValueWithOldestTimestamp.getKey(), findValueWithOldestTimestamp);
        }
    }

    private FailureCacheValue findValueWithOldestTimestamp() {
        long j = Long.MAX_VALUE;
        FailureCacheValue failureCacheValue = null;
        for (Map.Entry<String, FailureCacheValue> entry : this.storage.entrySet()) {
            long creationTimeInNanos = entry.getValue().getCreationTimeInNanos();
            if (creationTimeInNanos < j) {
                failureCacheValue = entry.getValue();
                j = creationTimeInNanos;
            }
        }
        return failureCacheValue;
    }
}
