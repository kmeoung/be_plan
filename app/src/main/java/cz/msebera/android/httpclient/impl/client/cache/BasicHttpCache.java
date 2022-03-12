package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateException;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class BasicHttpCache implements HttpCache {
    private static final Set<String> safeRequestMethods = new HashSet(Arrays.asList("HEAD", "GET", "OPTIONS", "TRACE"));
    private final CacheEntryUpdater cacheEntryUpdater;
    private final HttpCacheInvalidator cacheInvalidator;
    public HttpClientAndroidLog log;
    private final long maxObjectSizeBytes;
    private final ResourceFactory resourceFactory;
    private final CachedHttpResponseGenerator responseGenerator;
    private final HttpCacheStorage storage;
    private final CacheKeyGenerator uriExtractor;

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig, CacheKeyGenerator cacheKeyGenerator, HttpCacheInvalidator httpCacheInvalidator) {
        this.log = new HttpClientAndroidLog(getClass());
        this.resourceFactory = resourceFactory;
        this.uriExtractor = cacheKeyGenerator;
        this.cacheEntryUpdater = new CacheEntryUpdater(resourceFactory);
        this.maxObjectSizeBytes = cacheConfig.getMaxObjectSize();
        this.responseGenerator = new CachedHttpResponseGenerator();
        this.storage = httpCacheStorage;
        this.cacheInvalidator = httpCacheInvalidator;
    }

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig, CacheKeyGenerator cacheKeyGenerator) {
        this(resourceFactory, httpCacheStorage, cacheConfig, cacheKeyGenerator, new CacheInvalidator(cacheKeyGenerator, httpCacheStorage));
    }

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig) {
        this(resourceFactory, httpCacheStorage, cacheConfig, new CacheKeyGenerator());
    }

    public BasicHttpCache(CacheConfig cacheConfig) {
        this(new HeapResourceFactory(), new BasicHttpCacheStorage(cacheConfig), cacheConfig);
    }

    public BasicHttpCache() {
        this(CacheConfig.DEFAULT);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public void flushCacheEntriesFor(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        if (!safeRequestMethods.contains(httpRequest.getRequestLine().getMethod())) {
            this.storage.removeEntry(this.uriExtractor.getURI(httpHost, httpRequest));
        }
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public void flushInvalidatedCacheEntriesFor(HttpHost httpHost, HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!safeRequestMethods.contains(httpRequest.getRequestLine().getMethod())) {
            this.cacheInvalidator.flushInvalidatedCacheEntries(httpHost, httpRequest, httpResponse);
        }
    }

    void storeInCache(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) throws IOException {
        if (httpCacheEntry.hasVariants()) {
            storeVariantEntry(httpHost, httpRequest, httpCacheEntry);
        } else {
            storeNonVariantEntry(httpHost, httpRequest, httpCacheEntry);
        }
    }

    void storeNonVariantEntry(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) throws IOException {
        this.storage.putEntry(this.uriExtractor.getURI(httpHost, httpRequest), httpCacheEntry);
    }

    void storeVariantEntry(HttpHost httpHost, final HttpRequest httpRequest, final HttpCacheEntry httpCacheEntry) throws IOException {
        String uri = this.uriExtractor.getURI(httpHost, httpRequest);
        final String variantURI = this.uriExtractor.getVariantURI(httpHost, httpRequest, httpCacheEntry);
        this.storage.putEntry(variantURI, httpCacheEntry);
        try {
            this.storage.updateEntry(uri, new HttpCacheUpdateCallback() { // from class: cz.msebera.android.httpclient.impl.client.cache.BasicHttpCache.1
                @Override // cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback
                public HttpCacheEntry update(HttpCacheEntry httpCacheEntry2) throws IOException {
                    return BasicHttpCache.this.doGetUpdatedParentEntry(httpRequest.getRequestLine().getUri(), httpCacheEntry2, httpCacheEntry, BasicHttpCache.this.uriExtractor.getVariantKey(httpRequest, httpCacheEntry), variantURI);
                }
            });
        } catch (HttpCacheUpdateException e) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.warn("Could not update key [" + uri + "]", e);
        }
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public void reuseVariantEntryFor(HttpHost httpHost, final HttpRequest httpRequest, Variant variant) throws IOException {
        String uri = this.uriExtractor.getURI(httpHost, httpRequest);
        final HttpCacheEntry entry = variant.getEntry();
        final String variantKey = this.uriExtractor.getVariantKey(httpRequest, entry);
        final String cacheKey = variant.getCacheKey();
        try {
            this.storage.updateEntry(uri, new HttpCacheUpdateCallback() { // from class: cz.msebera.android.httpclient.impl.client.cache.BasicHttpCache.2
                @Override // cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback
                public HttpCacheEntry update(HttpCacheEntry httpCacheEntry) throws IOException {
                    return BasicHttpCache.this.doGetUpdatedParentEntry(httpRequest.getRequestLine().getUri(), httpCacheEntry, entry, variantKey, cacheKey);
                }
            });
        } catch (HttpCacheUpdateException e) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.warn("Could not update key [" + uri + "]", e);
        }
    }

    boolean isIncompleteResponse(HttpResponse httpResponse, Resource resource) {
        Header firstHeader;
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if ((statusCode != 200 && statusCode != 206) || (firstHeader = httpResponse.getFirstHeader("Content-Length")) == null) {
            return false;
        }
        try {
            return resource != null && resource.length() < ((long) Integer.parseInt(firstHeader.getValue()));
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    CloseableHttpResponse generateIncompleteResponseError(HttpResponse httpResponse, Resource resource) {
        Integer valueOf = Integer.valueOf(httpResponse.getFirstHeader("Content-Length").getValue());
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, (int) HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
        basicHttpResponse.setHeader("Content-Type", "text/plain;charset=UTF-8");
        byte[] bytes = String.format("Received incomplete response with Content-Length %d but actual body length %d", valueOf, Long.valueOf(resource.length())).getBytes();
        basicHttpResponse.setHeader("Content-Length", Integer.toString(bytes.length));
        basicHttpResponse.setEntity(new ByteArrayEntity(bytes));
        return Proxies.enhanceResponse(basicHttpResponse);
    }

    HttpCacheEntry doGetUpdatedParentEntry(String str, HttpCacheEntry httpCacheEntry, HttpCacheEntry httpCacheEntry2, String str2, String str3) throws IOException {
        if (httpCacheEntry == null) {
            httpCacheEntry = httpCacheEntry2;
        }
        Resource resource = null;
        if (httpCacheEntry.getResource() != null) {
            resource = this.resourceFactory.copy(str, httpCacheEntry.getResource());
        }
        HashMap hashMap = new HashMap(httpCacheEntry.getVariantMap());
        hashMap.put(str2, str3);
        return new HttpCacheEntry(httpCacheEntry.getRequestDate(), httpCacheEntry.getResponseDate(), httpCacheEntry.getStatusLine(), httpCacheEntry.getAllHeaders(), resource, hashMap, httpCacheEntry.getRequestMethod());
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public HttpCacheEntry updateCacheEntry(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2) throws IOException {
        HttpCacheEntry updateCacheEntry = this.cacheEntryUpdater.updateCacheEntry(httpRequest.getRequestLine().getUri(), httpCacheEntry, date, date2, httpResponse);
        storeInCache(httpHost, httpRequest, updateCacheEntry);
        return updateCacheEntry;
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public HttpCacheEntry updateVariantCacheEntry(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2, String str) throws IOException {
        HttpCacheEntry updateCacheEntry = this.cacheEntryUpdater.updateCacheEntry(httpRequest.getRequestLine().getUri(), httpCacheEntry, date, date2, httpResponse);
        this.storage.putEntry(str, updateCacheEntry);
        return updateCacheEntry;
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public HttpResponse cacheAndReturnResponse(HttpHost httpHost, HttpRequest httpRequest, HttpResponse httpResponse, Date date, Date date2) throws IOException {
        return cacheAndReturnResponse(httpHost, httpRequest, Proxies.enhanceResponse(httpResponse), date, date2);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public CloseableHttpResponse cacheAndReturnResponse(HttpHost httpHost, HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse, Date date, Date date2) throws IOException {
        SizeLimitedResponseReader responseReader = getResponseReader(httpRequest, closeableHttpResponse);
        try {
            responseReader.readResponse();
            if (responseReader.isLimitReached()) {
                return responseReader.getReconstructedResponse();
            }
            Resource resource = responseReader.getResource();
            if (isIncompleteResponse(closeableHttpResponse, resource)) {
                CloseableHttpResponse generateIncompleteResponseError = generateIncompleteResponseError(closeableHttpResponse, resource);
                closeableHttpResponse.close();
                return generateIncompleteResponseError;
            }
            HttpCacheEntry httpCacheEntry = new HttpCacheEntry(date, date2, closeableHttpResponse.getStatusLine(), closeableHttpResponse.getAllHeaders(), resource, httpRequest.getRequestLine().getMethod());
            storeInCache(httpHost, httpRequest, httpCacheEntry);
            CloseableHttpResponse generateResponse = this.responseGenerator.generateResponse(HttpRequestWrapper.wrap(httpRequest, httpHost), httpCacheEntry);
            closeableHttpResponse.close();
            return generateResponse;
        } catch (Throwable th) {
            if (1 != 0) {
                closeableHttpResponse.close();
            }
            throw th;
        }
    }

    SizeLimitedResponseReader getResponseReader(HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse) {
        return new SizeLimitedResponseReader(this.resourceFactory, this.maxObjectSizeBytes, httpRequest, closeableHttpResponse);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public HttpCacheEntry getCacheEntry(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        HttpCacheEntry entry = this.storage.getEntry(this.uriExtractor.getURI(httpHost, httpRequest));
        if (entry == null) {
            return null;
        }
        if (!entry.hasVariants()) {
            return entry;
        }
        String str = entry.getVariantMap().get(this.uriExtractor.getVariantKey(httpRequest, entry));
        if (str == null) {
            return null;
        }
        return this.storage.getEntry(str);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public void flushInvalidatedCacheEntriesFor(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        this.cacheInvalidator.flushInvalidatedCacheEntries(httpHost, httpRequest);
    }

    @Override // cz.msebera.android.httpclient.impl.client.cache.HttpCache
    public Map<String, Variant> getVariantCacheEntriesWithEtags(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        HashMap hashMap = new HashMap();
        HttpCacheEntry entry = this.storage.getEntry(this.uriExtractor.getURI(httpHost, httpRequest));
        if (entry == null || !entry.hasVariants()) {
            return hashMap;
        }
        for (Map.Entry<String, String> entry2 : entry.getVariantMap().entrySet()) {
            addVariantWithEtag(entry2.getKey(), entry2.getValue(), hashMap);
        }
        return hashMap;
    }

    private void addVariantWithEtag(String str, String str2, Map<String, Variant> map) throws IOException {
        Header firstHeader;
        HttpCacheEntry entry = this.storage.getEntry(str2);
        if (entry != null && (firstHeader = entry.getFirstHeader("ETag")) != null) {
            map.put(firstHeader.getValue(), new Variant(str, str2, entry));
        }
    }
}
