package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.impl.conn.AbstractPoolEntry;
import cz.msebera.android.httpclient.impl.conn.AbstractPooledConnAdapter;

@Deprecated
/* loaded from: classes.dex */
public class BasicPooledConnAdapter extends AbstractPooledConnAdapter {
    public BasicPooledConnAdapter(ThreadSafeClientConnManager threadSafeClientConnManager, AbstractPoolEntry abstractPoolEntry) {
        super(threadSafeClientConnManager, abstractPoolEntry);
        markReusable();
    }

    @Override // cz.msebera.android.httpclient.impl.conn.AbstractClientConnAdapter
    public ClientConnectionManager getManager() {
        return super.getManager();
    }

    @Override // cz.msebera.android.httpclient.impl.conn.AbstractPooledConnAdapter
    public AbstractPoolEntry getPoolEntry() {
        return super.getPoolEntry();
    }

    @Override // cz.msebera.android.httpclient.impl.conn.AbstractPooledConnAdapter, cz.msebera.android.httpclient.impl.conn.AbstractClientConnAdapter
    public void detach() {
        super.detach();
    }
}
