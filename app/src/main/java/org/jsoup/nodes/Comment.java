package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.nodes.Document;

/* loaded from: classes.dex */
public class Comment extends LeafNode {
    private static final String COMMENT_KEY = "comment";

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#comment";
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) {
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ String absUrl(String str) {
        return super.absUrl(str);
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ String attr(String str) {
        return super.attr(str);
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ Node attr(String str, String str2) {
        return super.attr(str, str2);
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ String baseUri() {
        return super.baseUri();
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ int childNodeSize() {
        return super.childNodeSize();
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ boolean hasAttr(String str) {
        return super.hasAttr(str);
    }

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    public /* bridge */ /* synthetic */ Node removeAttr(String str) {
        return super.removeAttr(str);
    }

    public Comment(String str) {
        this.value = str;
    }

    public Comment(String str, String str2) {
        this(str);
    }

    public String getData() {
        return coreValue();
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        if (outputSettings.prettyPrint()) {
            indent(appendable, i, outputSettings);
        }
        appendable.append("<!--").append(getData()).append("-->");
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }
}
