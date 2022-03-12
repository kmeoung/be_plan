package org.jsoup.nodes;

import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/* loaded from: classes.dex */
public class XmlDeclaration extends LeafNode {
    private final boolean isProcessingInstruction;

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#declaration";
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

    public XmlDeclaration(String str, boolean z) {
        Validate.notNull(str);
        this.value = str;
        this.isProcessingInstruction = z;
    }

    public XmlDeclaration(String str, String str2, boolean z) {
        this(str, z);
    }

    public String name() {
        return coreValue();
    }

    public String getWholeDeclaration() {
        StringBuilder sb = new StringBuilder();
        try {
            getWholeDeclaration(sb, new Document.OutputSettings());
            return sb.toString().trim();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    private void getWholeDeclaration(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        Iterator<Attribute> it = attributes().iterator();
        while (it.hasNext()) {
            Attribute next = it.next();
            if (!next.getKey().equals(nodeName())) {
                appendable.append(TokenParser.SP);
                next.html(appendable, outputSettings);
            }
        }
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        appendable.append("<").append(this.isProcessingInstruction ? "!" : "?").append(coreValue());
        getWholeDeclaration(appendable, outputSettings);
        appendable.append(this.isProcessingInstruction ? "!" : "?").append(">");
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }
}
