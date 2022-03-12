package org.jsoup.nodes;

import com.naver.temy123.baseproject.base.Utils.HW_Params;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.UrlUtils;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/* loaded from: classes.dex */
public class Document extends Element {
    private String location;
    private OutputSettings outputSettings = new OutputSettings();
    private QuirksMode quirksMode = QuirksMode.noQuirks;
    private boolean updateMetaCharset = false;

    /* loaded from: classes.dex */
    public enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public String nodeName() {
        return "#document";
    }

    public Document(String str) {
        super(Tag.valueOf("#root", ParseSettings.htmlDefault), str);
        this.location = str;
    }

    public static Document createShell(String str) {
        Validate.notNull(str);
        Document document = new Document(str);
        Element appendElement = document.appendElement("html");
        appendElement.appendElement("head");
        appendElement.appendElement(HW_Params.HW_NETWORK_EXTRA_BODY);
        return document;
    }

    public String location() {
        return this.location;
    }

    public Element head() {
        return findFirstElementByTagName("head", this);
    }

    public Element body() {
        return findFirstElementByTagName(HW_Params.HW_NETWORK_EXTRA_BODY, this);
    }

    public String title() {
        Element first = getElementsByTag(SettingsJsonConstants.PROMPT_TITLE_KEY).first();
        return first != null ? StringUtil.normaliseWhitespace(first.text()).trim() : "";
    }

    public void title(String str) {
        Validate.notNull(str);
        Element first = getElementsByTag(SettingsJsonConstants.PROMPT_TITLE_KEY).first();
        if (first == null) {
            head().appendElement(SettingsJsonConstants.PROMPT_TITLE_KEY).text(str);
        } else {
            first.text(str);
        }
    }

    public Element createElement(String str) {
        return new Element(Tag.valueOf(str, ParseSettings.preserveCase), baseUri());
    }

    public Document normalise() {
        Element findFirstElementByTagName = findFirstElementByTagName("html", this);
        if (findFirstElementByTagName == null) {
            findFirstElementByTagName = appendElement("html");
        }
        if (head() == null) {
            findFirstElementByTagName.prependElement("head");
        }
        if (body() == null) {
            findFirstElementByTagName.appendElement(HW_Params.HW_NETWORK_EXTRA_BODY);
        }
        normaliseTextNodes(head());
        normaliseTextNodes(findFirstElementByTagName);
        normaliseTextNodes(this);
        normaliseStructure("head", findFirstElementByTagName);
        normaliseStructure(HW_Params.HW_NETWORK_EXTRA_BODY, findFirstElementByTagName);
        ensureMetaCharsetElement();
        return this;
    }

    private void normaliseTextNodes(Element element) {
        ArrayList arrayList = new ArrayList();
        for (Node node : element.childNodes) {
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                if (!textNode.isBlank()) {
                    arrayList.add(textNode);
                }
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Node node2 = (Node) arrayList.get(size);
            element.removeChild(node2);
            body().prependChild(new TextNode(" "));
            body().prependChild(node2);
        }
    }

    private void normaliseStructure(String str, Element element) {
        Elements elementsByTag = getElementsByTag(str);
        Element first = elementsByTag.first();
        if (elementsByTag.size() > 1) {
            ArrayList<Node> arrayList = new ArrayList();
            for (int i = 1; i < elementsByTag.size(); i++) {
                Element element2 = elementsByTag.get(i);
                arrayList.addAll(element2.ensureChildNodes());
                element2.remove();
            }
            for (Node node : arrayList) {
                first.appendChild(node);
            }
        }
        if (!first.parent().equals(element)) {
            element.appendChild(first);
        }
    }

    private Element findFirstElementByTagName(String str, Node node) {
        if (node.nodeName().equals(str)) {
            return (Element) node;
        }
        int childNodeSize = node.childNodeSize();
        for (int i = 0; i < childNodeSize; i++) {
            Element findFirstElementByTagName = findFirstElementByTagName(str, node.childNode(i));
            if (findFirstElementByTagName != null) {
                return findFirstElementByTagName;
            }
        }
        return null;
    }

    @Override // org.jsoup.nodes.Node
    public String outerHtml() {
        return super.html();
    }

    @Override // org.jsoup.nodes.Element
    public Element text(String str) {
        body().text(str);
        return this;
    }

    public void charset(Charset charset) {
        updateMetaCharsetElement(true);
        this.outputSettings.charset(charset);
        ensureMetaCharsetElement();
    }

    public Charset charset() {
        return this.outputSettings.charset();
    }

    public void updateMetaCharsetElement(boolean z) {
        this.updateMetaCharset = z;
    }

    public boolean updateMetaCharsetElement() {
        return this.updateMetaCharset;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public Document clone() {
        Document document = (Document) super.clone();
        document.outputSettings = this.outputSettings.clone();
        return document;
    }

    private void ensureMetaCharsetElement() {
        if (this.updateMetaCharset) {
            OutputSettings.Syntax syntax = outputSettings().syntax();
            if (syntax == OutputSettings.Syntax.html) {
                Element first = select("meta[charset]").first();
                if (first != null) {
                    first.attr(HttpRequest.PARAM_CHARSET, charset().displayName());
                } else {
                    Element head = head();
                    if (head != null) {
                        head.appendElement("meta").attr(HttpRequest.PARAM_CHARSET, charset().displayName());
                    }
                }
                select("meta[name=charset]").remove();
            } else if (syntax == OutputSettings.Syntax.xml) {
                Node node = childNodes().get(0);
                if (node instanceof XmlDeclaration) {
                    XmlDeclaration xmlDeclaration = (XmlDeclaration) node;
                    if (xmlDeclaration.name().equals("xml")) {
                        xmlDeclaration.attr("encoding", charset().displayName());
                        if (xmlDeclaration.attr(ClientCookie.VERSION_ATTR) != null) {
                            xmlDeclaration.attr(ClientCookie.VERSION_ATTR, "1.0");
                            return;
                        }
                        return;
                    }
                    XmlDeclaration xmlDeclaration2 = new XmlDeclaration("xml", false);
                    xmlDeclaration2.attr(ClientCookie.VERSION_ATTR, "1.0");
                    xmlDeclaration2.attr("encoding", charset().displayName());
                    prependChild(xmlDeclaration2);
                    return;
                }
                XmlDeclaration xmlDeclaration3 = new XmlDeclaration("xml", false);
                xmlDeclaration3.attr(ClientCookie.VERSION_ATTR, "1.0");
                xmlDeclaration3.attr("encoding", charset().displayName());
                prependChild(xmlDeclaration3);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class OutputSettings implements Cloneable {
        private Charset charset;
        Entities.CoreCharset coreCharset;
        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private ThreadLocal<CharsetEncoder> encoderThreadLocal = new ThreadLocal<>();
        private boolean prettyPrint = true;
        private boolean outline = false;
        private int indentAmount = 1;
        private Syntax syntax = Syntax.html;

        /* loaded from: classes.dex */
        public enum Syntax {
            html,
            xml
        }

        public OutputSettings() {
            charset(Charset.forName(UrlUtils.UTF8));
        }

        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        public Charset charset() {
            return this.charset;
        }

        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public OutputSettings charset(String str) {
            charset(Charset.forName(str));
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CharsetEncoder prepareEncoder() {
            CharsetEncoder newEncoder = this.charset.newEncoder();
            this.encoderThreadLocal.set(newEncoder);
            this.coreCharset = Entities.CoreCharset.byName(newEncoder.charset().name());
            return newEncoder;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CharsetEncoder encoder() {
            CharsetEncoder charsetEncoder = this.encoderThreadLocal.get();
            return charsetEncoder != null ? charsetEncoder : prepareEncoder();
        }

        public Syntax syntax() {
            return this.syntax;
        }

        public OutputSettings syntax(Syntax syntax) {
            this.syntax = syntax;
            return this;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }

        public OutputSettings prettyPrint(boolean z) {
            this.prettyPrint = z;
            return this;
        }

        public boolean outline() {
            return this.outline;
        }

        public OutputSettings outline(boolean z) {
            this.outline = z;
            return this;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        public OutputSettings indentAmount(int i) {
            Validate.isTrue(i >= 0);
            this.indentAmount = i;
            return this;
        }

        public OutputSettings clone() {
            try {
                OutputSettings outputSettings = (OutputSettings) super.clone();
                outputSettings.charset(this.charset.name());
                outputSettings.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
                return outputSettings;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public Document outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    public Document quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }
}
