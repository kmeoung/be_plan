package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.util.Date;

@Immutable
/* loaded from: classes.dex */
public class CachedResponseSuitabilityChecker {
    private final float heuristicCoefficient;
    private final long heuristicDefaultLifetime;
    public HttpClientAndroidLog log;
    private final boolean sharedCache;
    private final boolean useHeuristicCaching;
    private final CacheValidityPolicy validityStrategy;

    public CachedResponseSuitabilityChecker(CacheValidityPolicy cacheValidityPolicy, CacheConfig cacheConfig) {
        this.log = new HttpClientAndroidLog(getClass());
        this.validityStrategy = cacheValidityPolicy;
        this.sharedCache = cacheConfig.isSharedCache();
        this.useHeuristicCaching = cacheConfig.isHeuristicCachingEnabled();
        this.heuristicCoefficient = cacheConfig.getHeuristicCoefficient();
        this.heuristicDefaultLifetime = cacheConfig.getHeuristicDefaultLifetime();
    }

    CachedResponseSuitabilityChecker(CacheConfig cacheConfig) {
        this(new CacheValidityPolicy(), cacheConfig);
    }

    private boolean isFreshEnough(HttpCacheEntry httpCacheEntry, HttpRequest httpRequest, Date date) {
        if (this.validityStrategy.isResponseFresh(httpCacheEntry, date)) {
            return true;
        }
        if (this.useHeuristicCaching && this.validityStrategy.isResponseHeuristicallyFresh(httpCacheEntry, date, this.heuristicCoefficient, this.heuristicDefaultLifetime)) {
            return true;
        }
        if (originInsistsOnFreshness(httpCacheEntry)) {
            return false;
        }
        long maxStale = getMaxStale(httpRequest);
        return maxStale != -1 && maxStale > this.validityStrategy.getStalenessSecs(httpCacheEntry, date);
    }

    private boolean originInsistsOnFreshness(HttpCacheEntry httpCacheEntry) {
        if (this.validityStrategy.mustRevalidate(httpCacheEntry)) {
            return true;
        }
        if (!this.sharedCache) {
            return false;
        }
        return this.validityStrategy.proxyRevalidate(httpCacheEntry) || this.validityStrategy.hasCacheControlDirective(httpCacheEntry, "s-maxage");
    }

    private long getMaxStale(HttpRequest httpRequest) {
        HeaderElement[] elements;
        Header[] headers = httpRequest.getHeaders("Cache-Control");
        int length = headers.length;
        long j = -1;
        int i = 0;
        while (i < length) {
            long j2 = j;
            for (HeaderElement headerElement : headers[i].getElements()) {
                j2 = 0;
                if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(headerElement.getName())) {
                    if ((headerElement.getValue() == null || "".equals(headerElement.getValue().trim())) && j2 == -1) {
                        j2 = Long.MAX_VALUE;
                    } else {
                        try {
                            long parseLong = Long.parseLong(headerElement.getValue());
                            if (parseLong >= 0) {
                                j2 = parseLong;
                            }
                            if (j2 != -1 && j2 >= j2) {
                            }
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
            i++;
            j = j2;
        }
        return j;
    }

    public boolean canCachedResponseBeUsed(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        HeaderElement[] elements;
        boolean z = false;
        if (!isFreshEnough(httpCacheEntry, httpRequest, date)) {
            this.log.trace("Cache entry was not fresh enough");
            return false;
        } else if (isGet(httpRequest) && !this.validityStrategy.contentLengthHeaderMatchesActualLength(httpCacheEntry)) {
            this.log.debug("Cache entry Content-Length and header information do not match");
            return false;
        } else if (hasUnsupportedConditionalHeaders(httpRequest)) {
            this.log.debug("Request contained conditional headers we don't handle");
            return false;
        } else if (!isConditional(httpRequest) && httpCacheEntry.getStatusCode() == 304) {
            return false;
        } else {
            if (isConditional(httpRequest) && !allConditionalsMatch(httpRequest, httpCacheEntry, date)) {
                return false;
            }
            if (hasUnsupportedCacheEntryForGet(httpRequest, httpCacheEntry)) {
                this.log.debug("HEAD response caching enabled but the cache entry does not contain a request method, entity or a 204 response");
                return false;
            }
            Header[] headers = httpRequest.getHeaders("Cache-Control");
            int length = headers.length;
            int i = 0;
            while (i < length) {
                for (HeaderElement headerElement : headers[i].getElements()) {
                    if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName())) {
                        this.log.trace("Response contained NO CACHE directive, cache was not suitable");
                        return z;
                    } else if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(headerElement.getName())) {
                        this.log.trace("Response contained NO STORE directive, cache was not suitable");
                        return z;
                    } else {
                        if ("max-age".equals(headerElement.getName())) {
                            try {
                                i = i;
                                if (this.validityStrategy.getCurrentAgeSecs(httpCacheEntry, date) > Integer.parseInt(headerElement.getValue())) {
                                    this.log.trace("Response from cache was NOT suitable due to max age");
                                    return false;
                                }
                            } catch (NumberFormatException e) {
                                this.log.debug("Response from cache was malformed" + e.getMessage());
                                return false;
                            }
                        } else {
                            i = i;
                        }
                        if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(headerElement.getName())) {
                            try {
                                if (this.validityStrategy.getFreshnessLifetimeSecs(httpCacheEntry) > Integer.parseInt(headerElement.getValue())) {
                                    this.log.trace("Response from cache was not suitable due to Max stale freshness");
                                    return false;
                                }
                            } catch (NumberFormatException e2) {
                                this.log.debug("Response from cache was malformed: " + e2.getMessage());
                                return false;
                            }
                        }
                        if (HeaderConstants.CACHE_CONTROL_MIN_FRESH.equals(headerElement.getName())) {
                            try {
                                long parseLong = Long.parseLong(headerElement.getValue());
                                if (parseLong < 0) {
                                    return false;
                                }
                                if (this.validityStrategy.getFreshnessLifetimeSecs(httpCacheEntry) - this.validityStrategy.getCurrentAgeSecs(httpCacheEntry, date) < parseLong) {
                                    this.log.trace("Response from cache was not suitable due to min fresh freshness requirement");
                                    return false;
                                }
                            } catch (NumberFormatException e3) {
                                this.log.debug("Response from cache was malformed: " + e3.getMessage());
                                return false;
                            }
                        }
                        z = false;
                    }
                }
                i++;
            }
            this.log.trace("Response from cache was suitable");
            return true;
        }
    }

    private boolean isGet(HttpRequest httpRequest) {
        return httpRequest.getRequestLine().getMethod().equals("GET");
    }

    private boolean entryIsNotA204Response(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry.getStatusCode() != 204;
    }

    private boolean cacheEntryDoesNotContainMethodAndEntity(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry.getRequestMethod() == null && httpCacheEntry.getResource() == null;
    }

    private boolean hasUnsupportedCacheEntryForGet(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        return isGet(httpRequest) && cacheEntryDoesNotContainMethodAndEntity(httpCacheEntry) && entryIsNotA204Response(httpCacheEntry);
    }

    public boolean isConditional(HttpRequest httpRequest) {
        return hasSupportedEtagValidator(httpRequest) || hasSupportedLastModifiedValidator(httpRequest);
    }

    public boolean allConditionalsMatch(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        boolean hasSupportedEtagValidator = hasSupportedEtagValidator(httpRequest);
        boolean hasSupportedLastModifiedValidator = hasSupportedLastModifiedValidator(httpRequest);
        boolean z = hasSupportedEtagValidator && etagValidatorMatches(httpRequest, httpCacheEntry);
        boolean z2 = hasSupportedLastModifiedValidator && lastModifiedValidatorMatches(httpRequest, httpCacheEntry, date);
        if (hasSupportedEtagValidator && hasSupportedLastModifiedValidator && (!z || !z2)) {
            return false;
        }
        if (!hasSupportedEtagValidator || z) {
            return !hasSupportedLastModifiedValidator || z2;
        }
        return false;
    }

    private boolean hasUnsupportedConditionalHeaders(HttpRequest httpRequest) {
        return (httpRequest.getFirstHeader("If-Range") == null && httpRequest.getFirstHeader("If-Match") == null && !hasValidDateField(httpRequest, "If-Unmodified-Since")) ? false : true;
    }

    private boolean hasSupportedEtagValidator(HttpRequest httpRequest) {
        return httpRequest.containsHeader("If-None-Match");
    }

    private boolean hasSupportedLastModifiedValidator(HttpRequest httpRequest) {
        return hasValidDateField(httpRequest, "If-Modified-Since");
    }

    private boolean etagValidatorMatches(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("ETag");
        String value = firstHeader != null ? firstHeader.getValue() : null;
        Header[] headers = httpRequest.getHeaders("If-None-Match");
        if (headers != null) {
            for (Header header : headers) {
                for (HeaderElement headerElement : header.getElements()) {
                    String obj = headerElement.toString();
                    if (("*".equals(obj) && value != null) || obj.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean lastModifiedValidatorMatches(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Last-Modified");
        Date parseDate = firstHeader != null ? DateUtils.parseDate(firstHeader.getValue()) : null;
        if (parseDate == null) {
            return false;
        }
        for (Header header : httpRequest.getHeaders("If-Modified-Since")) {
            Date parseDate2 = DateUtils.parseDate(header.getValue());
            if (parseDate2 != null && (parseDate2.after(date) || parseDate.after(parseDate2))) {
                return false;
            }
        }
        return true;
    }

    private boolean hasValidDateField(HttpRequest httpRequest, String str) {
        Header[] headers = httpRequest.getHeaders(str);
        return headers.length > 0 && DateUtils.parseDate(headers[0].getValue()) != null;
    }
}
