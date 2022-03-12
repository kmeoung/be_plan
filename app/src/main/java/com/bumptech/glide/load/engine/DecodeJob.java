package com.bumptech.glide.load.engine;

import android.os.Build;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.Pools;
import android.util.Log;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class DecodeJob<R> implements DataFetcherGenerator.FetcherReadyCallback, Runnable, Comparable<DecodeJob<?>>, FactoryPools.Poolable {
    private static final String TAG = "DecodeJob";
    private Callback<R> callback;
    private Key currentAttemptingKey;
    private Object currentData;
    private DataSource currentDataSource;
    private DataFetcher<?> currentFetcher;
    private volatile DataFetcherGenerator currentGenerator;
    Key currentSourceKey;
    private Thread currentThread;
    private final DiskCacheProvider diskCacheProvider;
    DiskCacheStrategy diskCacheStrategy;
    private GlideContext glideContext;
    int height;
    private volatile boolean isCallbackNotified;
    private volatile boolean isCancelled;
    private EngineKey loadKey;
    private boolean onlyRetrieveFromCache;
    Options options;
    private int order;
    private final Pools.Pool<DecodeJob<?>> pool;
    private Priority priority;
    private RunReason runReason;
    Key signature;
    private Stage stage;
    private long startFetchTime;
    int width;
    final DecodeHelper<R> decodeHelper = new DecodeHelper<>();
    private final List<Throwable> throwables = new ArrayList();
    private final StateVerifier stateVerifier = StateVerifier.newInstance();
    final DeferredEncodeManager<?> deferredEncodeManager = new DeferredEncodeManager<>();
    private final ReleaseManager releaseManager = new ReleaseManager();

    /* loaded from: classes.dex */
    public interface Callback<R> {
        void onLoadFailed(GlideException glideException);

        void onResourceReady(Resource<R> resource, DataSource dataSource);

        void reschedule(DecodeJob<?> decodeJob);
    }

    /* loaded from: classes.dex */
    public interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    /* loaded from: classes.dex */
    public enum RunReason {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    /* loaded from: classes.dex */
    public enum Stage {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    public DecodeJob(DiskCacheProvider diskCacheProvider, Pools.Pool<DecodeJob<?>> pool) {
        this.diskCacheProvider = diskCacheProvider;
        this.pool = pool;
    }

    public DecodeJob<R> init(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, Callback<R> callback, int i3) {
        this.decodeHelper.init(glideContext, obj, key, i, i2, diskCacheStrategy, cls, cls2, priority, options, map, z, z2, this.diskCacheProvider);
        this.glideContext = glideContext;
        this.signature = key;
        this.priority = priority;
        this.loadKey = engineKey;
        this.width = i;
        this.height = i2;
        this.diskCacheStrategy = diskCacheStrategy;
        this.onlyRetrieveFromCache = z3;
        this.options = options;
        this.callback = callback;
        this.order = i3;
        this.runReason = RunReason.INITIALIZE;
        return this;
    }

    public boolean willDecodeFromCache() {
        Stage nextStage = getNextStage(Stage.INITIALIZE);
        return nextStage == Stage.RESOURCE_CACHE || nextStage == Stage.DATA_CACHE;
    }

    public void release(boolean z) {
        if (this.releaseManager.release(z)) {
            releaseInternal();
        }
    }

    private void onEncodeComplete() {
        if (this.releaseManager.onEncodeComplete()) {
            releaseInternal();
        }
    }

    private void onLoadFailed() {
        if (this.releaseManager.onFailed()) {
            releaseInternal();
        }
    }

    private void releaseInternal() {
        this.releaseManager.reset();
        this.deferredEncodeManager.clear();
        this.decodeHelper.clear();
        this.isCallbackNotified = false;
        this.glideContext = null;
        this.signature = null;
        this.options = null;
        this.priority = null;
        this.loadKey = null;
        this.callback = null;
        this.stage = null;
        this.currentGenerator = null;
        this.currentThread = null;
        this.currentSourceKey = null;
        this.currentData = null;
        this.currentDataSource = null;
        this.currentFetcher = null;
        this.startFetchTime = 0L;
        this.isCancelled = false;
        this.throwables.clear();
        this.pool.release(this);
    }

    public int compareTo(DecodeJob<?> decodeJob) {
        int priority = getPriority() - decodeJob.getPriority();
        return priority == 0 ? this.order - decodeJob.order : priority;
    }

    private int getPriority() {
        return this.priority.ordinal();
    }

    public void cancel() {
        this.isCancelled = true;
        DataFetcherGenerator dataFetcherGenerator = this.currentGenerator;
        if (dataFetcherGenerator != null) {
            dataFetcherGenerator.cancel();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001a, code lost:
        if (r0 != null) goto L_0x001c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001c, code lost:
        r0.cleanup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x001f, code lost:
        android.support.v4.os.TraceCompat.endSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0064, code lost:
        if (r0 != null) goto L_0x001c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0067, code lost:
        return;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r5 = this;
            java.lang.String r0 = "DecodeJob#run"
            android.support.v4.os.TraceCompat.beginSection(r0)
            com.bumptech.glide.load.data.DataFetcher<?> r0 = r5.currentFetcher
            boolean r1 = r5.isCancelled     // Catch: Throwable -> 0x0025
            if (r1 == 0) goto L_0x0017
            r5.notifyFailed()     // Catch: Throwable -> 0x0025
            if (r0 == 0) goto L_0x0013
            r0.cleanup()
        L_0x0013:
            android.support.v4.os.TraceCompat.endSection()
            return
        L_0x0017:
            r5.runWrapped()     // Catch: Throwable -> 0x0025
            if (r0 == 0) goto L_0x001f
        L_0x001c:
            r0.cleanup()
        L_0x001f:
            android.support.v4.os.TraceCompat.endSection()
            goto L_0x0067
        L_0x0023:
            r1 = move-exception
            goto L_0x0068
        L_0x0025:
            r1 = move-exception
            java.lang.String r2 = "DecodeJob"
            r3 = 3
            boolean r2 = android.util.Log.isLoggable(r2, r3)     // Catch: all -> 0x0023
            if (r2 == 0) goto L_0x0051
            java.lang.String r2 = "DecodeJob"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: all -> 0x0023
            r3.<init>()     // Catch: all -> 0x0023
            java.lang.String r4 = "DecodeJob threw unexpectedly, isCancelled: "
            r3.append(r4)     // Catch: all -> 0x0023
            boolean r4 = r5.isCancelled     // Catch: all -> 0x0023
            r3.append(r4)     // Catch: all -> 0x0023
            java.lang.String r4 = ", stage: "
            r3.append(r4)     // Catch: all -> 0x0023
            com.bumptech.glide.load.engine.DecodeJob$Stage r4 = r5.stage     // Catch: all -> 0x0023
            r3.append(r4)     // Catch: all -> 0x0023
            java.lang.String r3 = r3.toString()     // Catch: all -> 0x0023
            android.util.Log.d(r2, r3, r1)     // Catch: all -> 0x0023
        L_0x0051:
            com.bumptech.glide.load.engine.DecodeJob$Stage r2 = r5.stage     // Catch: all -> 0x0023
            com.bumptech.glide.load.engine.DecodeJob$Stage r3 = com.bumptech.glide.load.engine.DecodeJob.Stage.ENCODE     // Catch: all -> 0x0023
            if (r2 == r3) goto L_0x005f
            java.util.List<java.lang.Throwable> r2 = r5.throwables     // Catch: all -> 0x0023
            r2.add(r1)     // Catch: all -> 0x0023
            r5.notifyFailed()     // Catch: all -> 0x0023
        L_0x005f:
            boolean r2 = r5.isCancelled     // Catch: all -> 0x0023
            if (r2 != 0) goto L_0x0064
            throw r1     // Catch: all -> 0x0023
        L_0x0064:
            if (r0 == 0) goto L_0x001f
            goto L_0x001c
        L_0x0067:
            return
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r0.cleanup()
        L_0x006d:
            android.support.v4.os.TraceCompat.endSection()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodeJob.run():void");
    }

    private void runWrapped() {
        switch (this.runReason) {
            case INITIALIZE:
                this.stage = getNextStage(Stage.INITIALIZE);
                this.currentGenerator = getNextGenerator();
                runGenerators();
                return;
            case SWITCH_TO_SOURCE_SERVICE:
                runGenerators();
                return;
            case DECODE_DATA:
                decodeFromRetrievedData();
                return;
            default:
                throw new IllegalStateException("Unrecognized run reason: " + this.runReason);
        }
    }

    private DataFetcherGenerator getNextGenerator() {
        switch (this.stage) {
            case RESOURCE_CACHE:
                return new ResourceCacheGenerator(this.decodeHelper, this);
            case DATA_CACHE:
                return new DataCacheGenerator(this.decodeHelper, this);
            case SOURCE:
                return new SourceGenerator(this.decodeHelper, this);
            case FINISHED:
                return null;
            default:
                throw new IllegalStateException("Unrecognized stage: " + this.stage);
        }
    }

    private void runGenerators() {
        this.currentThread = Thread.currentThread();
        this.startFetchTime = LogTime.getLogTime();
        boolean z = false;
        while (!this.isCancelled && this.currentGenerator != null && !(z = this.currentGenerator.startNext())) {
            this.stage = getNextStage(this.stage);
            this.currentGenerator = getNextGenerator();
            if (this.stage == Stage.SOURCE) {
                reschedule();
                return;
            }
        }
        if ((this.stage == Stage.FINISHED || this.isCancelled) && !z) {
            notifyFailed();
        }
    }

    private void notifyFailed() {
        setNotifiedOrThrow();
        this.callback.onLoadFailed(new GlideException("Failed to load resource", new ArrayList(this.throwables)));
        onLoadFailed();
    }

    private void notifyComplete(Resource<R> resource, DataSource dataSource) {
        setNotifiedOrThrow();
        this.callback.onResourceReady(resource, dataSource);
    }

    private void setNotifiedOrThrow() {
        this.stateVerifier.throwIfRecycled();
        if (this.isCallbackNotified) {
            throw new IllegalStateException("Already notified");
        }
        this.isCallbackNotified = true;
    }

    private Stage getNextStage(Stage stage) {
        switch (stage) {
            case RESOURCE_CACHE:
                return this.diskCacheStrategy.decodeCachedData() ? Stage.DATA_CACHE : getNextStage(Stage.DATA_CACHE);
            case DATA_CACHE:
                return this.onlyRetrieveFromCache ? Stage.FINISHED : Stage.SOURCE;
            case SOURCE:
            case FINISHED:
                return Stage.FINISHED;
            case INITIALIZE:
                return this.diskCacheStrategy.decodeCachedResource() ? Stage.RESOURCE_CACHE : getNextStage(Stage.RESOURCE_CACHE);
            default:
                throw new IllegalArgumentException("Unrecognized stage: " + stage);
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void reschedule() {
        this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
        this.callback.reschedule(this);
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.currentSourceKey = key;
        this.currentData = obj;
        this.currentFetcher = dataFetcher;
        this.currentDataSource = dataSource;
        this.currentAttemptingKey = key2;
        if (Thread.currentThread() != this.currentThread) {
            this.runReason = RunReason.DECODE_DATA;
            this.callback.reschedule(this);
            return;
        }
        TraceCompat.beginSection("DecodeJob.decodeFromRetrievedData");
        try {
            decodeFromRetrievedData();
        } finally {
            TraceCompat.endSection();
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        dataFetcher.cleanup();
        GlideException glideException = new GlideException("Fetching data failed", exc);
        glideException.setLoggingDetails(key, dataSource, dataFetcher.getDataClass());
        this.throwables.add(glideException);
        if (Thread.currentThread() != this.currentThread) {
            this.runReason = RunReason.SWITCH_TO_SOURCE_SERVICE;
            this.callback.reschedule(this);
            return;
        }
        runGenerators();
    }

    private void decodeFromRetrievedData() {
        if (Log.isLoggable(TAG, 2)) {
            long j = this.startFetchTime;
            logWithTimeAndKey("Retrieved data", j, "data: " + this.currentData + ", cache key: " + this.currentSourceKey + ", fetcher: " + this.currentFetcher);
        }
        Resource<R> resource = null;
        try {
            resource = decodeFromData(this.currentFetcher, this.currentData, this.currentDataSource);
        } catch (GlideException e) {
            e.setLoggingDetails(this.currentAttemptingKey, this.currentDataSource);
            this.throwables.add(e);
        }
        if (resource != null) {
            notifyEncodeAndRelease(resource, this.currentDataSource);
        } else {
            runGenerators();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void notifyEncodeAndRelease(Resource<R> resource, DataSource dataSource) {
        if (resource instanceof Initializable) {
            ((Initializable) resource).initialize();
        }
        LockedResource lockedResource = 0;
        if (this.deferredEncodeManager.hasResourceToEncode()) {
            resource = LockedResource.obtain(resource);
            lockedResource = resource;
        }
        notifyComplete(resource, dataSource);
        this.stage = Stage.ENCODE;
        try {
            if (this.deferredEncodeManager.hasResourceToEncode()) {
                this.deferredEncodeManager.encode(this.diskCacheProvider, this.options);
            }
        } finally {
            if (lockedResource != 0) {
                lockedResource.unlock();
            }
            onEncodeComplete();
        }
    }

    private <Data> Resource<R> decodeFromData(DataFetcher<?> dataFetcher, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            return null;
        }
        try {
            long logTime = LogTime.getLogTime();
            Resource<R> decodeFromFetcher = decodeFromFetcher(data, dataSource);
            if (Log.isLoggable(TAG, 2)) {
                logWithTimeAndKey("Decoded result " + decodeFromFetcher, logTime);
            }
            return decodeFromFetcher;
        } finally {
            dataFetcher.cleanup();
        }
    }

    private <Data> Resource<R> decodeFromFetcher(Data data, DataSource dataSource) throws GlideException {
        return runLoadPath(data, dataSource, (LoadPath<Data, ?, R>) this.decodeHelper.getLoadPath(data.getClass()));
    }

    private Options getOptionsWithHardwareConfig(DataSource dataSource) {
        Options options = this.options;
        if (Build.VERSION.SDK_INT < 26 || options.get(Downsampler.ALLOW_HARDWARE_CONFIG) != null) {
            return options;
        }
        if (dataSource != DataSource.RESOURCE_DISK_CACHE && !this.decodeHelper.isScaleOnlyOrNoTransform()) {
            return options;
        }
        Options options2 = new Options();
        options2.putAll(this.options);
        options2.set(Downsampler.ALLOW_HARDWARE_CONFIG, true);
        return options2;
    }

    private <Data, ResourceType> Resource<R> runLoadPath(Data data, DataSource dataSource, LoadPath<Data, ResourceType, R> loadPath) throws GlideException {
        Options optionsWithHardwareConfig = getOptionsWithHardwareConfig(dataSource);
        DataRewinder<Data> rewinder = this.glideContext.getRegistry().getRewinder(data);
        try {
            return loadPath.load(rewinder, optionsWithHardwareConfig, this.width, this.height, new DecodeCallback(dataSource));
        } finally {
            rewinder.cleanup();
        }
    }

    private void logWithTimeAndKey(String str, long j) {
        logWithTimeAndKey(str, j, null);
    }

    private void logWithTimeAndKey(String str, long j, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(LogTime.getElapsedMillis(j));
        sb.append(", load key: ");
        sb.append(this.loadKey);
        if (str2 != null) {
            str3 = ", " + str2;
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v(TAG, sb.toString());
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    /* loaded from: classes.dex */
    public final class DecodeCallback<Z> implements DecodePath.DecodeCallback<Z> {
        private final DataSource dataSource;

        DecodeCallback(DataSource dataSource) {
            DecodeJob.this = r1;
            this.dataSource = dataSource;
        }

        @Override // com.bumptech.glide.load.engine.DecodePath.DecodeCallback
        public Resource<Z> onResourceDecoded(Resource<Z> resource) {
            Resource<Z> resource2;
            Transformation<Z> transformation;
            EncodeStrategy encodeStrategy;
            Key key;
            Class<Z> resourceClass = getResourceClass(resource);
            ResourceEncoder resourceEncoder = null;
            if (this.dataSource != DataSource.RESOURCE_DISK_CACHE) {
                Transformation<Z> transformation2 = DecodeJob.this.decodeHelper.getTransformation(resourceClass);
                resource2 = transformation2.transform(DecodeJob.this.glideContext, resource, DecodeJob.this.width, DecodeJob.this.height);
                transformation = transformation2;
            } else {
                resource2 = resource;
                transformation = null;
            }
            if (!resource.equals(resource2)) {
                resource.recycle();
            }
            if (DecodeJob.this.decodeHelper.isResourceEncoderAvailable(resource2)) {
                resourceEncoder = DecodeJob.this.decodeHelper.getResultEncoder(resource2);
                encodeStrategy = resourceEncoder.getEncodeStrategy(DecodeJob.this.options);
            } else {
                encodeStrategy = EncodeStrategy.NONE;
            }
            if (!DecodeJob.this.diskCacheStrategy.isResourceCacheable(!DecodeJob.this.decodeHelper.isSourceKey(DecodeJob.this.currentSourceKey), this.dataSource, encodeStrategy)) {
                return resource2;
            }
            if (resourceEncoder == null) {
                throw new Registry.NoResultEncoderAvailableException(resource2.get().getClass());
            }
            if (encodeStrategy == EncodeStrategy.SOURCE) {
                key = new DataCacheKey(DecodeJob.this.currentSourceKey, DecodeJob.this.signature);
            } else if (encodeStrategy == EncodeStrategy.TRANSFORMED) {
                key = new ResourceCacheKey(DecodeJob.this.currentSourceKey, DecodeJob.this.signature, DecodeJob.this.width, DecodeJob.this.height, transformation, resourceClass, DecodeJob.this.options);
            } else {
                throw new IllegalArgumentException("Unknown strategy: " + encodeStrategy);
            }
            LockedResource obtain = LockedResource.obtain(resource2);
            DecodeJob.this.deferredEncodeManager.init(key, resourceEncoder, obtain);
            return obtain;
        }

        private Class<Z> getResourceClass(Resource<Z> resource) {
            return (Class<Z>) resource.get().getClass();
        }
    }

    /* loaded from: classes.dex */
    public static class ReleaseManager {
        private boolean isEncodeComplete;
        private boolean isFailed;
        private boolean isReleased;

        ReleaseManager() {
        }

        synchronized boolean release(boolean z) {
            this.isReleased = true;
            return isComplete(z);
        }

        synchronized boolean onEncodeComplete() {
            this.isEncodeComplete = true;
            return isComplete(false);
        }

        synchronized boolean onFailed() {
            this.isFailed = true;
            return isComplete(false);
        }

        synchronized void reset() {
            this.isEncodeComplete = false;
            this.isReleased = false;
            this.isFailed = false;
        }

        private boolean isComplete(boolean z) {
            return (this.isFailed || z || this.isEncodeComplete) && this.isReleased;
        }
    }

    /* loaded from: classes.dex */
    public static class DeferredEncodeManager<Z> {
        private ResourceEncoder<Z> encoder;
        private Key key;
        private LockedResource<Z> toEncode;

        DeferredEncodeManager() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        <X> void init(Key key, ResourceEncoder<X> resourceEncoder, LockedResource<X> lockedResource) {
            this.key = key;
            this.encoder = resourceEncoder;
            this.toEncode = lockedResource;
        }

        void encode(DiskCacheProvider diskCacheProvider, Options options) {
            TraceCompat.beginSection("DecodeJob.encode");
            try {
                diskCacheProvider.getDiskCache().put(this.key, new DataCacheWriter(this.encoder, this.toEncode, options));
            } finally {
                this.toEncode.unlock();
                TraceCompat.endSection();
            }
        }

        boolean hasResourceToEncode() {
            return this.toEncode != null;
        }

        void clear() {
            this.key = null;
            this.encoder = null;
            this.toEncode = null;
        }
    }
}
