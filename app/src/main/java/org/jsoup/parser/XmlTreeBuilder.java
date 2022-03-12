package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Token;

/* loaded from: classes.dex */
public class XmlTreeBuilder extends TreeBuilder {
    @Override // org.jsoup.parser.TreeBuilder
    public /* bridge */ /* synthetic */ boolean processStartTag(String str, Attributes attributes) {
        return super.processStartTag(str, attributes);
    }

    @Override // org.jsoup.parser.TreeBuilder
    public ParseSettings defaultSettings() {
        return ParseSettings.preserveCase;
    }

    Document parse(Reader reader, String str) {
        return parse(reader, str, ParseErrorList.noTracking(), ParseSettings.preserveCase);
    }

    Document parse(String str, String str2) {
        return parse(new StringReader(str), str2, ParseErrorList.noTracking(), ParseSettings.preserveCase);
    }

    @Override // org.jsoup.parser.TreeBuilder
    public void initialiseParse(Reader reader, String str, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        super.initialiseParse(reader, str, parseErrorList, parseSettings);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    }

    @Override // org.jsoup.parser.TreeBuilder
    public boolean process(Token token) {
        switch (token.type) {
            case StartTag:
                insert(token.asStartTag());
                return true;
            case EndTag:
                popStackToClose(token.asEndTag());
                return true;
            case Comment:
                insert(token.asComment());
                return true;
            case Character:
                insert(token.asCharacter());
                return true;
            case Doctype:
                insert(token.asDoctype());
                return true;
            case EOF:
                return true;
            default:
                Validate.fail("Unexpected token type: " + token.type);
                return true;
        }
    }

    private void insertNode(Node node) {
        currentElement().appendChild(node);
    }

    Element insert(Token.StartTag startTag) {
        Tag valueOf = Tag.valueOf(startTag.name(), this.settings);
        Element element = new Element(valueOf, this.baseUri, this.settings.normalizeAttributes(startTag.attributes));
        insertNode(element);
        if (!startTag.isSelfClosing()) {
            this.stack.add(element);
        } else if (!valueOf.isKnownTag()) {
            valueOf.setSelfClosing();
        }
        return element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [org.jsoup.nodes.Node, org.jsoup.nodes.XmlDeclaration] */
    /* JADX WARN: Type inference failed for: r4v0, types: [org.jsoup.parser.XmlTreeBuilder] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void insert(org.jsoup.parser.Token.Comment r5) {
        /*
            r4 = this;
            org.jsoup.nodes.Comment r0 = new org.jsoup.nodes.Comment
            java.lang.String r1 = r5.getData()
            r0.<init>(r1)
            boolean r5 = r5.bogus
            if (r5 == 0) goto L_0x007d
            java.lang.String r5 = r0.getData()
            int r1 = r5.length()
            r2 = 1
            if (r1 <= r2) goto L_0x007d
            java.lang.String r1 = "!"
            boolean r1 = r5.startsWith(r1)
            if (r1 != 0) goto L_0x0028
            java.lang.String r1 = "?"
            boolean r1 = r5.startsWith(r1)
            if (r1 == 0) goto L_0x007d
        L_0x0028:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "<"
            r1.append(r3)
            int r3 = r5.length()
            int r3 = r3 - r2
            java.lang.String r2 = r5.substring(r2, r3)
            r1.append(r2)
            java.lang.String r2 = ">"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = r4.baseUri
            org.jsoup.parser.Parser r3 = org.jsoup.parser.Parser.xmlParser()
            org.jsoup.nodes.Document r1 = org.jsoup.Jsoup.parse(r1, r2, r3)
            int r2 = r1.childNodeSize()
            if (r2 <= 0) goto L_0x007d
            r0 = 0
            org.jsoup.nodes.Element r0 = r1.child(r0)
            org.jsoup.nodes.XmlDeclaration r1 = new org.jsoup.nodes.XmlDeclaration
            org.jsoup.parser.ParseSettings r2 = r4.settings
            java.lang.String r3 = r0.tagName()
            java.lang.String r2 = r2.normalizeTag(r3)
            java.lang.String r3 = "!"
            boolean r5 = r5.startsWith(r3)
            r1.<init>(r2, r5)
            org.jsoup.nodes.Attributes r5 = r1.attributes()
            org.jsoup.nodes.Attributes r0 = r0.attributes()
            r5.addAll(r0)
            r0 = r1
        L_0x007d:
            r4.insertNode(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.XmlTreeBuilder.insert(org.jsoup.parser.Token$Comment):void");
    }

    void insert(Token.Character character) {
        String data = character.getData();
        insertNode(character.isCData() ? new CDataNode(data) : new TextNode(data));
    }

    void insert(Token.Doctype doctype) {
        DocumentType documentType = new DocumentType(this.settings.normalizeTag(doctype.getName()), doctype.getPublicIdentifier(), doctype.getSystemIdentifier());
        documentType.setPubSysKey(doctype.getPubSysKey());
        insertNode(documentType);
    }

    private void popStackToClose(Token.EndTag endTag) {
        Element element;
        String normalizeTag = this.settings.normalizeTag(endTag.tagName);
        int size = this.stack.size() - 1;
        while (true) {
            if (size < 0) {
                element = null;
                break;
            }
            element = this.stack.get(size);
            if (element.nodeName().equals(normalizeTag)) {
                break;
            }
            size--;
        }
        if (element != null) {
            for (int size2 = this.stack.size() - 1; size2 >= 0; size2--) {
                Element element2 = this.stack.get(size2);
                this.stack.remove(size2);
                if (element2 == element) {
                    return;
                }
            }
        }
    }

    public List<Node> parseFragment(String str, String str2, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        initialiseParse(new StringReader(str), str2, parseErrorList, parseSettings);
        runParser();
        return this.doc.childNodes();
    }
}
