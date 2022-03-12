package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.execchain.ClientExecChain;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class CachingHttpClientBuilder extends HttpClientBuilder {
    private CacheConfig cacheConfig;
    private File cacheDir;
    private boolean deleteCache = true;
    private HttpCacheInvalidator httpCacheInvalidator;
    private ResourceFactory resourceFactory;
    private SchedulingStrategy schedulingStrategy;
    private HttpCacheStorage storage;

    public static CachingHttpClientBuilder create() {
        return new CachingHttpClientBuilder();
    }

    protected CachingHttpClientBuilder() {
    }

    public final CachingHttpClientBuilder setResourceFactory(ResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        return this;
    }

    public final CachingHttpClientBuilder setHttpCacheStorage(HttpCacheStorage httpCacheStorage) {
        this.storage = httpCacheStorage;
        return this;
    }

    public final CachingHttpClientBuilder setCacheDir(File file) {
        this.cacheDir = file;
        return this;
    }

    public final CachingHttpClientBuilder setCacheConfig(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
        return this;
    }

    public final CachingHttpClientBuilder setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
        return this;
    }

    public final CachingHttpClientBuilder setHttpCacheInvalidator(HttpCacheInvalidator httpCacheInvalidator) {
        this.httpCacheInvalidator = httpCacheInvalidator;
        return this;
    }

    public CachingHttpClientBuilder setDeleteCache(boolean z) {
        this.deleteCache = z;
        return this;
    }

    @Override // cz.msebera.android.httpclient.impl.client.HttpClientBuilder
    protected ClientExecChain decorateMainExec(ClientExecChain clientExecChain) {
        CacheConfig cacheConfig = this.cacheConfig != null ? this.cacheConfig : CacheConfig.DEFAULT;
        ResourceFactory resourceFactory = this.resourceFactory;
        if (resourceFactory == null) {
            if (this.cacheDir == null) {
                resourceFactory = new HeapResourceFactory();
            } else {
                resourceFactory = new FileResourceFactory(this.cacheDir);
            }
        }
        HttpCacheStorage httpCacheStorage = this.storage;
        BasicHttpCacheStorage basicHttpCacheStorage = httpCacheStorage;
        if (httpCacheStorage == null) {
            if (this.cacheDir == null) {
                basicHttpCacheStorage = new BasicHttpCacheStorage(cacheConfig);
            } else {
                final ManagedHttpCacheStorage managedHttpCacheStorage = new ManagedHttpCacheStorage(cacheConfig);
                if (this.deleteCache) {
                    addCloseable(new Closeable() { // from class: cz.msebera.android.httpclient.impl.client.cache.CachingHttpClientBuilder.1
                        @Override // java.io.Closeable, java.lang.AutoCloseable
                        public void close() throws IOException {
                            managedHttpCacheStorage.shutdown();
                        }
                    });
                    basicHttpCacheStorage = managedHttpCacheStorage;
                } else {
                    addCloseable(managedHttpCacheStorage);
                    basicHttpCacheStorage = managedHttpCacheStorage;
                }
            }
        }
        AsynchronousValidator createAsynchronousRevalidator = createAsynchronousRevalidator(cacheConfig);
        CacheKeyGenerator cacheKeyGenerator = new CacheKeyGenerator();
        HttpCacheInvalidator httpCacheInvalidator = this.httpCacheInvalidator;
        if (httpCacheInvalidator == null) {
            httpCacheInvalidator = new CacheInvalidator(cacheKeyGenerator, basicHttpCacheStorage);
        }
        return new CachingExec(clientExecChain, new BasicHttpCache(resourceFactory, basicHttpCacheStorage, cacheConfig, cacheKeyGenerator, httpCacheInvalidator), cacheConfig, createAsynchronousRevalidator);
    }

    private AsynchronousValidator createAsynchronousRevalidator(CacheConfig cacheConfig) {
        if (cacheConfig.getAsynchronousWorkersMax() <= 0) {
            return null;
        }
        AsynchronousValidator asynchronousValidator = new AsynchronousValidator(createSchedulingStrategy(cacheConfig));
        addCloseable(asynchronousValidator);
        return asynchronousValidator;
    }

    private SchedulingStrategy createSchedulingStrategy(CacheConfig cacheConfig) {
        return this.schedulingStrategy != null ? this.schedulingStrategy : new ImmediateSchedulingStrategy(cacheConfig);
    }
}
