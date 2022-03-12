package org.jsoup.parser;

import com.naver.temy123.baseproject.base.Utils.HW_Params;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import io.realm.BuildConfig;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Token;
import org.jsoup.select.Elements;

/* loaded from: classes.dex */
public class HtmlTreeBuilder extends TreeBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int MaxScopeSearchDepth = 100;
    private boolean baseUriSetFromDoc;
    private Element contextElement;
    private Token.EndTag emptyEnd;
    private FormElement formElement;
    private ArrayList<Element> formattingElements;
    private boolean fosterInserts;
    private boolean fragmentParsing;
    private boolean framesetOk;
    private Element headElement;
    private HtmlTreeBuilderState originalState;
    private List<String> pendingTableCharacters;
    private String[] specificScopeTarget = {null};
    private HtmlTreeBuilderState state;
    static final String[] TagsSearchInScope = {"applet", "caption", "html", "marquee", "object", "table", "td", "th"};
    static final String[] TagSearchList = {"ol", "ul"};
    static final String[] TagSearchButton = {"button"};
    static final String[] TagSearchTableScope = {"html", "table"};
    static final String[] TagSearchSelectScope = {"optgroup", "option"};
    static final String[] TagSearchEndTags = {"dd", "dt", "li", "optgroup", "option", "p", "rp", "rt"};
    static final String[] TagSearchSpecial = {"address", "applet", "area", "article", "aside", BuildConfig.FLAVOR, "basefont", "bgsound", "blockquote", HW_Params.HW_NETWORK_EXTRA_BODY, "br", "button", "caption", "center", "col", "colgroup", "command", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", "style", "summary", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", SettingsJsonConstants.PROMPT_TITLE_KEY, "tr", "ul", "wbr", "xmp"};

    @Override // org.jsoup.parser.TreeBuilder
    public /* bridge */ /* synthetic */ boolean processStartTag(String str, Attributes attributes) {
        return super.processStartTag(str, attributes);
    }

    @Override // org.jsoup.parser.TreeBuilder
    public ParseSettings defaultSettings() {
        return ParseSettings.htmlDefault;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jsoup.parser.TreeBuilder
    public void initialiseParse(Reader reader, String str, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        super.initialiseParse(reader, str, parseErrorList, parseSettings);
        this.state = HtmlTreeBuilderState.Initial;
        this.originalState = null;
        this.baseUriSetFromDoc = false;
        this.headElement = null;
        this.formElement = null;
        this.contextElement = null;
        this.formattingElements = new ArrayList<>();
        this.pendingTableCharacters = new ArrayList();
        this.emptyEnd = new Token.EndTag();
        this.framesetOk = true;
        this.fosterInserts = false;
        this.fragmentParsing = false;
    }

    public List<Node> parseFragment(String str, Element element, String str2, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        Element element2;
        this.state = HtmlTreeBuilderState.Initial;
        initialiseParse(new StringReader(str), str2, parseErrorList, parseSettings);
        this.contextElement = element;
        this.fragmentParsing = true;
        if (element != null) {
            if (element.ownerDocument() != null) {
                this.doc.quirksMode(element.ownerDocument().quirksMode());
            }
            String tagName = element.tagName();
            if (StringUtil.in(tagName, SettingsJsonConstants.PROMPT_TITLE_KEY, "textarea")) {
                this.tokeniser.transition(TokeniserState.Rcdata);
            } else if (StringUtil.in(tagName, "iframe", "noembed", "noframes", "style", "xmp")) {
                this.tokeniser.transition(TokeniserState.Rawtext);
            } else if (tagName.equals("script")) {
                this.tokeniser.transition(TokeniserState.ScriptData);
            } else if (tagName.equals("noscript")) {
                this.tokeniser.transition(TokeniserState.Data);
            } else if (tagName.equals("plaintext")) {
                this.tokeniser.transition(TokeniserState.Data);
            } else {
                this.tokeniser.transition(TokeniserState.Data);
            }
            element2 = new Element(Tag.valueOf("html", parseSettings), str2);
            this.doc.appendChild(element2);
            this.stack.add(element2);
            resetInsertionMode();
            Elements parents = element.parents();
            parents.add(0, element);
            Iterator<Element> it = parents.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Element next = it.next();
                if (next instanceof FormElement) {
                    this.formElement = (FormElement) next;
                    break;
                }
            }
        } else {
            element2 = null;
        }
        runParser();
        if (element != null) {
            return element2.childNodes();
        }
        return this.doc.childNodes();
    }

    @Override // org.jsoup.parser.TreeBuilder
    public boolean process(Token token) {
        this.currentToken = token;
        return this.state.process(token, this);
    }

    public boolean process(Token token, HtmlTreeBuilderState htmlTreeBuilderState) {
        this.currentToken = token;
        return htmlTreeBuilderState.process(token, this);
    }

    public void transition(HtmlTreeBuilderState htmlTreeBuilderState) {
        this.state = htmlTreeBuilderState;
    }

    public HtmlTreeBuilderState state() {
        return this.state;
    }

    public void markInsertionMode() {
        this.originalState = this.state;
    }

    public HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    public void framesetOk(boolean z) {
        this.framesetOk = z;
    }

    public boolean framesetOk() {
        return this.framesetOk;
    }

    public Document getDocument() {
        return this.doc;
    }

    public String getBaseUri() {
        return this.baseUri;
    }

    public void maybeSetBaseUri(Element element) {
        if (!this.baseUriSetFromDoc) {
            String absUrl = element.absUrl("href");
            if (absUrl.length() != 0) {
                this.baseUri = absUrl;
                this.baseUriSetFromDoc = true;
                this.doc.setBaseUri(absUrl);
            }
        }
    }

    public boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    public void error(HtmlTreeBuilderState htmlTreeBuilderState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [%s] when in state [%s]", this.currentToken.tokenType(), htmlTreeBuilderState));
        }
    }

    public Element insert(Token.StartTag startTag) {
        if (startTag.isSelfClosing()) {
            Element insertEmpty = insertEmpty(startTag);
            this.stack.add(insertEmpty);
            this.tokeniser.transition(TokeniserState.Data);
            this.tokeniser.emit(this.emptyEnd.reset().name(insertEmpty.tagName()));
            return insertEmpty;
        }
        Element element = new Element(Tag.valueOf(startTag.name(), this.settings), this.baseUri, this.settings.normalizeAttributes(startTag.attributes));
        insert(element);
        return element;
    }

    public Element insertStartTag(String str) {
        Element element = new Element(Tag.valueOf(str, this.settings), this.baseUri);
        insert(element);
        return element;
    }

    void insert(Element element) {
        insertNode(element);
        this.stack.add(element);
    }

    public Element insertEmpty(Token.StartTag startTag) {
        Tag valueOf = Tag.valueOf(startTag.name(), this.settings);
        Element element = new Element(valueOf, this.baseUri, startTag.attributes);
        insertNode(element);
        if (startTag.isSelfClosing()) {
            if (!valueOf.isKnownTag()) {
                valueOf.setSelfClosing();
            } else if (!valueOf.isEmpty()) {
                this.tokeniser.error("Tag cannot be self closing; not a void tag");
            }
        }
        return element;
    }

    public FormElement insertForm(Token.StartTag startTag, boolean z) {
        FormElement formElement = new FormElement(Tag.valueOf(startTag.name(), this.settings), this.baseUri, startTag.attributes);
        setFormElement(formElement);
        insertNode(formElement);
        if (z) {
            this.stack.add(formElement);
        }
        return formElement;
    }

    public void insert(Token.Comment comment) {
        insertNode(new Comment(comment.getData()));
    }

    public void insert(Token.Character character) {
        Node node;
        String tagName = currentElement().tagName();
        String data = character.getData();
        if (character.isCData()) {
            node = new CDataNode(data);
        } else if (tagName.equals("script") || tagName.equals("style")) {
            node = new DataNode(data);
        } else {
            node = new TextNode(data);
        }
        currentElement().appendChild(node);
    }

    private void insertNode(Node node) {
        if (this.stack.size() == 0) {
            this.doc.appendChild(node);
        } else if (isFosterInserts()) {
            insertInFosterParent(node);
        } else {
            currentElement().appendChild(node);
        }
        if (node instanceof Element) {
            Element element = (Element) node;
            if (element.tag().isFormListed() && this.formElement != null) {
                this.formElement.addElement(element);
            }
        }
    }

    public Element pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

    public void push(Element element) {
        this.stack.add(element);
    }

    public ArrayList<Element> getStack() {
        return this.stack;
    }

    public boolean onStack(Element element) {
        return isElementInQueue(this.stack, element);
    }

    private boolean isElementInQueue(ArrayList<Element> arrayList, Element element) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == element) {
                return true;
            }
        }
        return false;
    }

    public Element getFromStack(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element element = this.stack.get(size);
            if (element.nodeName().equals(str)) {
                return element;
            }
        }
        return null;
    }

    public boolean removeFromStack(Element element) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            if (this.stack.get(size) == element) {
                this.stack.remove(size);
                return true;
            }
        }
        return false;
    }

    public void popStackToClose(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            this.stack.remove(size);
            if (this.stack.get(size).nodeName().equals(str)) {
                return;
            }
        }
    }

    public void popStackToClose(String... strArr) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            this.stack.remove(size);
            if (StringUtil.inSorted(this.stack.get(size).nodeName(), strArr)) {
                return;
            }
        }
    }

    public void popStackToBefore(String str) {
        for (int size = this.stack.size() - 1; size >= 0 && !this.stack.get(size).nodeName().equals(str); size--) {
            this.stack.remove(size);
        }
    }

    public void clearStackToTableContext() {
        clearStackToContext("table");
    }

    public void clearStackToTableBodyContext() {
        clearStackToContext("tbody", "tfoot", "thead", "template");
    }

    public void clearStackToTableRowContext() {
        clearStackToContext("tr", "template");
    }

    private void clearStackToContext(String... strArr) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element element = this.stack.get(size);
            if (!StringUtil.in(element.nodeName(), strArr) && !element.nodeName().equals("html")) {
                this.stack.remove(size);
            } else {
                return;
            }
        }
    }

    public Element aboveOnStack(Element element) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            if (this.stack.get(size) == element) {
                return this.stack.get(size - 1);
            }
        }
        return null;
    }

    public void insertOnStackAfter(Element element, Element element2) {
        int lastIndexOf = this.stack.lastIndexOf(element);
        Validate.isTrue(lastIndexOf != -1);
        this.stack.add(lastIndexOf + 1, element2);
    }

    public void replaceOnStack(Element element, Element element2) {
        replaceInQueue(this.stack, element, element2);
    }

    private void replaceInQueue(ArrayList<Element> arrayList, Element element, Element element2) {
        int lastIndexOf = arrayList.lastIndexOf(element);
        Validate.isTrue(lastIndexOf != -1);
        arrayList.set(lastIndexOf, element2);
    }

    public void resetInsertionMode() {
        boolean z = false;
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            Element element = this.stack.get(size);
            if (size == 0) {
                element = this.contextElement;
                z = true;
            }
            String nodeName = element.nodeName();
            if ("select".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InSelect);
                return;
            } else if ("td".equals(nodeName) || ("th".equals(nodeName) && !z)) {
                transition(HtmlTreeBuilderState.InCell);
                return;
            } else if ("tr".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InRow);
                return;
            } else if ("tbody".equals(nodeName) || "thead".equals(nodeName) || "tfoot".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InTableBody);
                return;
            } else if ("caption".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InCaption);
                return;
            } else if ("colgroup".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InColumnGroup);
                return;
            } else if ("table".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InTable);
                return;
            } else if ("head".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            } else if (HW_Params.HW_NETWORK_EXTRA_BODY.equals(nodeName)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            } else if ("frameset".equals(nodeName)) {
                transition(HtmlTreeBuilderState.InFrameset);
                return;
            } else if ("html".equals(nodeName)) {
                transition(HtmlTreeBuilderState.BeforeHead);
                return;
            } else if (z) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            }
        }
    }

    private boolean inSpecificScope(String str, String[] strArr, String[] strArr2) {
        this.specificScopeTarget[0] = str;
        return inSpecificScope(this.specificScopeTarget, strArr, strArr2);
    }

    private boolean inSpecificScope(String[] strArr, String[] strArr2, String[] strArr3) {
        int size = this.stack.size() - 1;
        int i = size > 100 ? size - 100 : 0;
        while (size >= i) {
            String nodeName = this.stack.get(size).nodeName();
            if (StringUtil.inSorted(nodeName, strArr)) {
                return true;
            }
            if (StringUtil.inSorted(nodeName, strArr2)) {
                return false;
            }
            if (strArr3 != null && StringUtil.inSorted(nodeName, strArr3)) {
                return false;
            }
            size--;
        }
        return false;
    }

    public boolean inScope(String[] strArr) {
        return inSpecificScope(strArr, TagsSearchInScope, (String[]) null);
    }

    public boolean inScope(String str) {
        return inScope(str, null);
    }

    boolean inScope(String str, String[] strArr) {
        return inSpecificScope(str, TagsSearchInScope, strArr);
    }

    public boolean inListItemScope(String str) {
        return inScope(str, TagSearchList);
    }

    public boolean inButtonScope(String str) {
        return inScope(str, TagSearchButton);
    }

    public boolean inTableScope(String str) {
        return inSpecificScope(str, TagSearchTableScope, (String[]) null);
    }

    public boolean inSelectScope(String str) {
        for (int size = this.stack.size() - 1; size >= 0; size--) {
            String nodeName = this.stack.get(size).nodeName();
            if (nodeName.equals(str)) {
                return true;
            }
            if (!StringUtil.inSorted(nodeName, TagSearchSelectScope)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    public void setHeadElement(Element element) {
        this.headElement = element;
    }

    public Element getHeadElement() {
        return this.headElement;
    }

    boolean isFosterInserts() {
        return this.fosterInserts;
    }

    public void setFosterInserts(boolean z) {
        this.fosterInserts = z;
    }

    public FormElement getFormElement() {
        return this.formElement;
    }

    public void setFormElement(FormElement formElement) {
        this.formElement = formElement;
    }

    public void newPendingTableCharacters() {
        this.pendingTableCharacters = new ArrayList();
    }

    public List<String> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    void setPendingTableCharacters(List<String> list) {
        this.pendingTableCharacters = list;
    }

    public void generateImpliedEndTags(String str) {
        while (str != null && !currentElement().nodeName().equals(str) && StringUtil.inSorted(currentElement().nodeName(), TagSearchEndTags)) {
            pop();
        }
    }

    public void generateImpliedEndTags() {
        generateImpliedEndTags(null);
    }

    public boolean isSpecial(Element element) {
        return StringUtil.inSorted(element.nodeName(), TagSearchSpecial);
    }

    Element lastFormattingElement() {
        if (this.formattingElements.size() > 0) {
            return this.formattingElements.get(this.formattingElements.size() - 1);
        }
        return null;
    }

    Element removeLastFormattingElement() {
        int size = this.formattingElements.size();
        if (size > 0) {
            return this.formattingElements.remove(size - 1);
        }
        return null;
    }

    public void pushActiveFormattingElements(Element element) {
        int size = this.formattingElements.size() - 1;
        int i = 0;
        while (true) {
            if (size >= 0) {
                Element element2 = this.formattingElements.get(size);
                if (element2 == null) {
                    break;
                }
                if (isSameFormattingElement(element, element2)) {
                    i++;
                }
                if (i == 3) {
                    this.formattingElements.remove(size);
                    break;
                }
                size--;
            } else {
                break;
            }
        }
        this.formattingElements.add(element);
    }

    private boolean isSameFormattingElement(Element element, Element element2) {
        return element.nodeName().equals(element2.nodeName()) && element.attributes().equals(element2.attributes());
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reconstructFormattingElements() {
        /*
            r7 = this;
            org.jsoup.nodes.Element r0 = r7.lastFormattingElement()
            if (r0 == 0) goto L_0x0059
            boolean r1 = r7.onStack(r0)
            if (r1 == 0) goto L_0x000d
            goto L_0x0059
        L_0x000d:
            java.util.ArrayList<org.jsoup.nodes.Element> r1 = r7.formattingElements
            int r1 = r1.size()
            r2 = 1
            int r1 = r1 - r2
            r3 = r0
            r0 = r1
        L_0x0017:
            r4 = 0
            if (r0 != 0) goto L_0x001b
            goto L_0x002e
        L_0x001b:
            java.util.ArrayList<org.jsoup.nodes.Element> r3 = r7.formattingElements
            int r0 = r0 + (-1)
            java.lang.Object r3 = r3.get(r0)
            org.jsoup.nodes.Element r3 = (org.jsoup.nodes.Element) r3
            if (r3 == 0) goto L_0x002d
            boolean r5 = r7.onStack(r3)
            if (r5 == 0) goto L_0x0017
        L_0x002d:
            r2 = 0
        L_0x002e:
            if (r2 != 0) goto L_0x003b
            java.util.ArrayList<org.jsoup.nodes.Element> r2 = r7.formattingElements
            int r0 = r0 + 1
            java.lang.Object r2 = r2.get(r0)
            org.jsoup.nodes.Element r2 = (org.jsoup.nodes.Element) r2
            r3 = r2
        L_0x003b:
            org.jsoup.helper.Validate.notNull(r3)
            java.lang.String r2 = r3.nodeName()
            org.jsoup.nodes.Element r2 = r7.insertStartTag(r2)
            org.jsoup.nodes.Attributes r5 = r2.attributes()
            org.jsoup.nodes.Attributes r6 = r3.attributes()
            r5.addAll(r6)
            java.util.ArrayList<org.jsoup.nodes.Element> r5 = r7.formattingElements
            r5.set(r0, r2)
            if (r0 != r1) goto L_0x002d
            return
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilder.reconstructFormattingElements():void");
    }

    public void clearFormattingElementsToLastMarker() {
        while (!this.formattingElements.isEmpty() && removeLastFormattingElement() != null) {
        }
    }

    public void removeFromActiveFormattingElements(Element element) {
        for (int size = this.formattingElements.size() - 1; size >= 0; size--) {
            if (this.formattingElements.get(size) == element) {
                this.formattingElements.remove(size);
                return;
            }
        }
    }

    public boolean isInActiveFormattingElements(Element element) {
        return isElementInQueue(this.formattingElements, element);
    }

    public Element getActiveFormattingElement(String str) {
        for (int size = this.formattingElements.size() - 1; size >= 0; size--) {
            Element element = this.formattingElements.get(size);
            if (element == null) {
                return null;
            }
            if (element.nodeName().equals(str)) {
                return element;
            }
        }
        return null;
    }

    public void replaceActiveFormattingElement(Element element, Element element2) {
        replaceInQueue(this.formattingElements, element, element2);
    }

    public void insertMarkerToFormattingElements() {
        this.formattingElements.add(null);
    }

    public void insertInFosterParent(Node node) {
        Element element;
        Element fromStack = getFromStack("table");
        boolean z = false;
        if (fromStack == null) {
            element = this.stack.get(0);
        } else if (fromStack.parent() != null) {
            element = fromStack.parent();
            z = true;
        } else {
            element = aboveOnStack(fromStack);
        }
        if (z) {
            Validate.notNull(fromStack);
            fromStack.before(node);
            return;
        }
        element.appendChild(node);
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + currentElement() + '}';
    }
}
