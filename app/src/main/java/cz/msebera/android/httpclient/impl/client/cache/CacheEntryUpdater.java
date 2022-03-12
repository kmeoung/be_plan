package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: Access modifiers changed from: package-private */
@Immutable
/* loaded from: classes.dex */
public class CacheEntryUpdater {
    private final ResourceFactory resourceFactory;

    CacheEntryUpdater() {
        this(new HeapResourceFactory());
    }

    public CacheEntryUpdater(ResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    public HttpCacheEntry updateCacheEntry(String str, HttpCacheEntry httpCacheEntry, Date date, Date date2, HttpResponse httpResponse) throws IOException {
        Args.check(httpResponse.getStatusLine().getStatusCode() == 304, "Response must have 304 status code");
        Header[] mergeHeaders = mergeHeaders(httpCacheEntry, httpResponse);
        Resource resource = null;
        if (httpCacheEntry.getResource() != null) {
            resource = this.resourceFactory.copy(str, httpCacheEntry.getResource());
        }
        return new HttpCacheEntry(date, date2, httpCacheEntry.getStatusLine(), mergeHeaders, resource, httpCacheEntry.getRequestMethod());
    }

    protected Header[] mergeHeaders(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        if (entryAndResponseHaveDateHeader(httpCacheEntry, httpResponse) && entryDateHeaderNewerThenResponse(httpCacheEntry, httpResponse)) {
            return httpCacheEntry.getAllHeaders();
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(httpCacheEntry.getAllHeaders()));
        removeCacheHeadersThatMatchResponse(arrayList, httpResponse);
        removeCacheEntry1xxWarnings(arrayList, httpCacheEntry);
        arrayList.addAll(Arrays.asList(httpResponse.getAllHeaders()));
        return (Header[]) arrayList.toArray(new Header[arrayList.size()]);
    }

    private void removeCacheHeadersThatMatchResponse(List<Header> list, HttpResponse httpResponse) {
        Header[] allHeaders;
        for (Header header : httpResponse.getAllHeaders()) {
            ListIterator<Header> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.next().getName().equals(header.getName())) {
                    listIterator.remove();
                }
            }
        }
    }

    private void removeCacheEntry1xxWarnings(List<Header> list, HttpCacheEntry httpCacheEntry) {
        ListIterator<Header> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if ("Warning".equals(listIterator.next().getName())) {
                for (Header header : httpCacheEntry.getHeaders("Warning")) {
                    if (header.getValue().startsWith("1")) {
                        listIterator.remove();
                    }
                }
            }
        }
    }

    private boolean entryDateHeaderNewerThenResponse(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        Date parseDate = DateUtils.parseDate(httpCacheEntry.getFirstHeader("Date").getValue());
        Date parseDate2 = DateUtils.parseDate(httpResponse.getFirstHeader("Date").getValue());
        return (parseDate == null || parseDate2 == null || !parseDate.after(parseDate2)) ? false : true;
    }

    private boolean entryAndResponseHaveDateHeader(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        return (httpCacheEntry.getFirstHeader("Date") == null || httpResponse.getFirstHeader("Date") == null) ? false : true;
    }
}
