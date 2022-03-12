package org.jsoup.nodes;

import java.util.Collections;
import java.util.List;
import org.jsoup.helper.Validate;

/* loaded from: classes.dex */
public abstract class LeafNode extends Node {
    private static final List<Node> EmptyNodes = Collections.emptyList();
    Object value;

    @Override // org.jsoup.nodes.Node
    public int childNodeSize() {
        return 0;
    }

    @Override // org.jsoup.nodes.Node
    protected void doSetBaseUri(String str) {
    }

    @Override // org.jsoup.nodes.Node
    protected final boolean hasAttributes() {
        return this.value instanceof Attributes;
    }

    @Override // org.jsoup.nodes.Node
    public final Attributes attributes() {
        ensureAttributes();
        return (Attributes) this.value;
    }

    private void ensureAttributes() {
        if (!hasAttributes()) {
            Object obj = this.value;
            Attributes attributes = new Attributes();
            this.value = attributes;
            if (obj != null) {
                attributes.put(nodeName(), (String) obj);
            }
        }
    }

    public String coreValue() {
        return attr(nodeName());
    }

    public void coreValue(String str) {
        attr(nodeName(), str);
    }

    @Override // org.jsoup.nodes.Node
    public String attr(String str) {
        Validate.notNull(str);
        if (!hasAttributes()) {
            return str.equals(nodeName()) ? (String) this.value : "";
        }
        return super.attr(str);
    }

    @Override // org.jsoup.nodes.Node
    public Node attr(String str, String str2) {
        if (hasAttributes() || !str.equals(nodeName())) {
            ensureAttributes();
            super.attr(str, str2);
        } else {
            this.value = str2;
        }
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public boolean hasAttr(String str) {
        ensureAttributes();
        return super.hasAttr(str);
    }

    @Override // org.jsoup.nodes.Node
    public Node removeAttr(String str) {
        ensureAttributes();
        return super.removeAttr(str);
    }

    @Override // org.jsoup.nodes.Node
    public String absUrl(String str) {
        ensureAttributes();
        return super.absUrl(str);
    }

    @Override // org.jsoup.nodes.Node
    public String baseUri() {
        return hasParent() ? parent().baseUri() : "";
    }

    @Override // org.jsoup.nodes.Node
    public List<Node> ensureChildNodes() {
        return EmptyNodes;
    }
}
