package org.jsoup.select;

import org.jsoup.nodes.Node;

/* loaded from: classes.dex */
public interface NodeFilter {

    /* loaded from: classes.dex */
    public enum FilterResult {
        CONTINUE,
        SKIP_CHILDREN,
        SKIP_ENTIRELY,
        REMOVE,
        STOP
    }

    FilterResult head(Node node, int i);

    FilterResult tail(Node node, int i);
}
