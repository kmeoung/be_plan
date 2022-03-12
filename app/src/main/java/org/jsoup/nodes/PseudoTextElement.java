package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;

/* loaded from: classes.dex */
public class PseudoTextElement extends Element {
    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
    }

    public PseudoTextElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
    }
}
