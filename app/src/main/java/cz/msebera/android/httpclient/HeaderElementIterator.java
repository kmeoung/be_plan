package cz.msebera.android.httpclient;

import java.util.Iterator;

/* loaded from: classes.dex */
public interface HeaderElementIterator extends Iterator<Object> {
    @Override // java.util.Iterator
    boolean hasNext();

    HeaderElement nextElement();
}
