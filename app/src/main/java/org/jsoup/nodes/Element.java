package org.jsoup.nodes;

import com.google.firebase.analytics.FirebaseAnalytics;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jsoup.helper.ChangeNotifyingArrayList;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.QueryParser;
import org.jsoup.select.Selector;

/* loaded from: classes.dex */
public class Element extends Node {
    private static final List<Node> EMPTY_NODES = Collections.emptyList();
    private static final Pattern classSplit = Pattern.compile("\\s+");
    private Attributes attributes;
    private String baseUri;
    List<Node> childNodes;
    private WeakReference<List<Element>> shadowChildrenRef;
    private Tag tag;

    public Element(String str) {
        this(Tag.valueOf(str), "", new Attributes());
    }

    public Element(Tag tag, String str, Attributes attributes) {
        Validate.notNull(tag);
        Validate.notNull(str);
        this.childNodes = EMPTY_NODES;
        this.baseUri = str;
        this.attributes = attributes;
        this.tag = tag;
    }

    public Element(Tag tag, String str) {
        this(tag, str, null);
    }

    @Override // org.jsoup.nodes.Node
    protected List<Node> ensureChildNodes() {
        if (this.childNodes == EMPTY_NODES) {
            this.childNodes = new NodeList(this, 4);
        }
        return this.childNodes;
    }

    @Override // org.jsoup.nodes.Node
    protected boolean hasAttributes() {
        return this.attributes != null;
    }

    @Override // org.jsoup.nodes.Node
    public Attributes attributes() {
        if (!hasAttributes()) {
            this.attributes = new Attributes();
        }
        return this.attributes;
    }

    @Override // org.jsoup.nodes.Node
    public String baseUri() {
        return this.baseUri;
    }

    @Override // org.jsoup.nodes.Node
    protected void doSetBaseUri(String str) {
        this.baseUri = str;
    }

    @Override // org.jsoup.nodes.Node
    public int childNodeSize() {
        return this.childNodes.size();
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return this.tag.getName();
    }

    public String tagName() {
        return this.tag.getName();
    }

    public Element tagName(String str) {
        Validate.notEmpty(str, "Tag name must not be empty.");
        this.tag = Tag.valueOf(str, ParseSettings.preserveCase);
        return this;
    }

    public Tag tag() {
        return this.tag;
    }

    public boolean isBlock() {
        return this.tag.isBlock();
    }

    public String id() {
        return attributes().getIgnoreCase("id");
    }

    @Override // org.jsoup.nodes.Node
    public Element attr(String str, String str2) {
        super.attr(str, str2);
        return this;
    }

    public Element attr(String str, boolean z) {
        attributes().put(str, z);
        return this;
    }

    public Map<String, String> dataset() {
        return attributes().dataset();
    }

    @Override // org.jsoup.nodes.Node
    public final Element parent() {
        return (Element) this.parentNode;
    }

    public Elements parents() {
        Elements elements = new Elements();
        accumulateParents(this, elements);
        return elements;
    }

    private static void accumulateParents(Element element, Elements elements) {
        Element parent = element.parent();
        if (parent != null && !parent.tagName().equals("#root")) {
            elements.add(parent);
            accumulateParents(parent, elements);
        }
    }

    public Element child(int i) {
        return childElementsList().get(i);
    }

    public Elements children() {
        return new Elements(childElementsList());
    }

    private List<Element> childElementsList() {
        List<Element> list;
        if (!(this.shadowChildrenRef == null || (list = this.shadowChildrenRef.get()) == null)) {
            return list;
        }
        int size = this.childNodes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            Node node = this.childNodes.get(i);
            if (node instanceof Element) {
                arrayList.add((Element) node);
            }
        }
        this.shadowChildrenRef = new WeakReference<>(arrayList);
        return arrayList;
    }

    @Override // org.jsoup.nodes.Node
    void nodelistChanged() {
        super.nodelistChanged();
        this.shadowChildrenRef = null;
    }

    public List<TextNode> textNodes() {
        ArrayList arrayList = new ArrayList();
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                arrayList.add((TextNode) node);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<DataNode> dataNodes() {
        ArrayList arrayList = new ArrayList();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                arrayList.add((DataNode) node);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Elements select(String str) {
        return Selector.select(str, this);
    }

    public Element selectFirst(String str) {
        return Selector.selectFirst(str, this);
    }

    public boolean is(String str) {
        return is(QueryParser.parse(str));
    }

    public boolean is(Evaluator evaluator) {
        return evaluator.matches((Element) root(), this);
    }

    public Element appendChild(Node node) {
        Validate.notNull(node);
        reparentChild(node);
        ensureChildNodes();
        this.childNodes.add(node);
        node.setSiblingIndex(this.childNodes.size() - 1);
        return this;
    }

    public Element appendTo(Element element) {
        Validate.notNull(element);
        element.appendChild(this);
        return this;
    }

    public Element prependChild(Node node) {
        Validate.notNull(node);
        addChildren(0, node);
        return this;
    }

    public Element insertChildren(int i, Collection<? extends Node> collection) {
        Validate.notNull(collection, "Children collection to be inserted must not be null.");
        int childNodeSize = childNodeSize();
        if (i < 0) {
            i += childNodeSize + 1;
        }
        Validate.isTrue(i >= 0 && i <= childNodeSize, "Insert position out of bounds.");
        ArrayList arrayList = new ArrayList(collection);
        addChildren(i, (Node[]) arrayList.toArray(new Node[arrayList.size()]));
        return this;
    }

    public Element insertChildren(int i, Node... nodeArr) {
        Validate.notNull(nodeArr, "Children collection to be inserted must not be null.");
        int childNodeSize = childNodeSize();
        if (i < 0) {
            i += childNodeSize + 1;
        }
        Validate.isTrue(i >= 0 && i <= childNodeSize, "Insert position out of bounds.");
        addChildren(i, nodeArr);
        return this;
    }

    public Element appendElement(String str) {
        Element element = new Element(Tag.valueOf(str), baseUri());
        appendChild(element);
        return element;
    }

    public Element prependElement(String str) {
        Element element = new Element(Tag.valueOf(str), baseUri());
        prependChild(element);
        return element;
    }

    public Element appendText(String str) {
        Validate.notNull(str);
        appendChild(new TextNode(str));
        return this;
    }

    public Element prependText(String str) {
        Validate.notNull(str);
        prependChild(new TextNode(str));
        return this;
    }

    public Element append(String str) {
        Validate.notNull(str);
        List<Node> parseFragment = Parser.parseFragment(str, this, baseUri());
        addChildren((Node[]) parseFragment.toArray(new Node[parseFragment.size()]));
        return this;
    }

    public Element prepend(String str) {
        Validate.notNull(str);
        List<Node> parseFragment = Parser.parseFragment(str, this, baseUri());
        addChildren(0, (Node[]) parseFragment.toArray(new Node[parseFragment.size()]));
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element before(String str) {
        return (Element) super.before(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element before(Node node) {
        return (Element) super.before(node);
    }

    @Override // org.jsoup.nodes.Node
    public Element after(String str) {
        return (Element) super.after(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element after(Node node) {
        return (Element) super.after(node);
    }

    public Element empty() {
        this.childNodes.clear();
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element wrap(String str) {
        return (Element) super.wrap(str);
    }

    public String cssSelector() {
        if (id().length() > 0) {
            return "#" + id();
        }
        StringBuilder sb = new StringBuilder(tagName().replace(':', '|'));
        String join = StringUtil.join(classNames(), ".");
        if (join.length() > 0) {
            sb.append('.');
            sb.append(join);
        }
        if (parent() == null || (parent() instanceof Document)) {
            return sb.toString();
        }
        sb.insert(0, " > ");
        if (parent().select(sb.toString()).size() > 1) {
            sb.append(String.format(":nth-child(%d)", Integer.valueOf(elementSiblingIndex() + 1)));
        }
        return parent().cssSelector() + sb.toString();
    }

    public Elements siblingElements() {
        if (this.parentNode == null) {
            return new Elements(0);
        }
        List<Element> childElementsList = parent().childElementsList();
        Elements elements = new Elements(childElementsList.size() - 1);
        for (Element element : childElementsList) {
            if (element != this) {
                elements.add(element);
            }
        }
        return elements;
    }

    public Element nextElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Element> childElementsList = parent().childElementsList();
        Integer valueOf = Integer.valueOf(indexInList(this, childElementsList));
        Validate.notNull(valueOf);
        if (childElementsList.size() > valueOf.intValue() + 1) {
            return childElementsList.get(valueOf.intValue() + 1);
        }
        return null;
    }

    public Element previousElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Element> childElementsList = parent().childElementsList();
        Integer valueOf = Integer.valueOf(indexInList(this, childElementsList));
        Validate.notNull(valueOf);
        if (valueOf.intValue() > 0) {
            return childElementsList.get(valueOf.intValue() - 1);
        }
        return null;
    }

    public Element firstElementSibling() {
        List<Element> childElementsList = parent().childElementsList();
        if (childElementsList.size() > 1) {
            return childElementsList.get(0);
        }
        return null;
    }

    public int elementSiblingIndex() {
        if (parent() == null) {
            return 0;
        }
        return indexInList(this, parent().childElementsList());
    }

    public Element lastElementSibling() {
        List<Element> childElementsList = parent().childElementsList();
        if (childElementsList.size() > 1) {
            return childElementsList.get(childElementsList.size() - 1);
        }
        return null;
    }

    private static <E extends Element> int indexInList(Element element, List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == element) {
                return i;
            }
        }
        return 0;
    }

    public Elements getElementsByTag(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Tag(Normalizer.normalize(str)), this);
    }

    public Element getElementById(String str) {
        Validate.notEmpty(str);
        Elements collect = Collector.collect(new Evaluator.Id(str), this);
        if (collect.size() > 0) {
            return collect.get(0);
        }
        return null;
    }

    public Elements getElementsByClass(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Class(str), this);
    }

    public Elements getElementsByAttribute(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Attribute(str.trim()), this);
    }

    public Elements getElementsByAttributeStarting(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.AttributeStarting(str.trim()), this);
    }

    public Elements getElementsByAttributeValue(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValue(str, str2), this);
    }

    public Elements getElementsByAttributeValueNot(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(str, str2), this);
    }

    public Elements getElementsByAttributeValueStarting(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(str, str2), this);
    }

    public Elements getElementsByAttributeValueEnding(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(str, str2), this);
    }

    public Elements getElementsByAttributeValueContaining(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(str, str2), this);
    }

    public Elements getElementsByAttributeValueMatching(String str, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(str, pattern), this);
    }

    public Elements getElementsByAttributeValueMatching(String str, String str2) {
        try {
            return getElementsByAttributeValueMatching(str, Pattern.compile(str2));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str2, e);
        }
    }

    public Elements getElementsByIndexLessThan(int i) {
        return Collector.collect(new Evaluator.IndexLessThan(i), this);
    }

    public Elements getElementsByIndexGreaterThan(int i) {
        return Collector.collect(new Evaluator.IndexGreaterThan(i), this);
    }

    public Elements getElementsByIndexEquals(int i) {
        return Collector.collect(new Evaluator.IndexEquals(i), this);
    }

    public Elements getElementsContainingText(String str) {
        return Collector.collect(new Evaluator.ContainsText(str), this);
    }

    public Elements getElementsContainingOwnText(String str) {
        return Collector.collect(new Evaluator.ContainsOwnText(str), this);
    }

    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    public Elements getElementsMatchingText(String str) {
        try {
            return getElementsMatchingText(Pattern.compile(str));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e);
        }
    }

    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    public Elements getElementsMatchingOwnText(String str) {
        try {
            return getElementsMatchingOwnText(Pattern.compile(str));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e);
        }
    }

    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    public String text() {
        final StringBuilder sb = new StringBuilder();
        NodeTraversor.traverse(new NodeVisitor() { // from class: org.jsoup.nodes.Element.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int i) {
                if (node instanceof TextNode) {
                    Element.appendNormalisedText(sb, (TextNode) node);
                } else if (node instanceof Element) {
                    Element element = (Element) node;
                    if (sb.length() <= 0) {
                        return;
                    }
                    if ((element.isBlock() || element.tag.getName().equals("br")) && !TextNode.lastCharIsWhitespace(sb)) {
                        sb.append(TokenParser.SP);
                    }
                }
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int i) {
                if ((node instanceof Element) && ((Element) node).isBlock() && (node.nextSibling() instanceof TextNode) && !TextNode.lastCharIsWhitespace(sb)) {
                    sb.append(TokenParser.SP);
                }
            }
        }, this);
        return sb.toString().trim();
    }

    public String wholeText() {
        final StringBuilder sb = new StringBuilder();
        NodeTraversor.traverse(new NodeVisitor() { // from class: org.jsoup.nodes.Element.2
            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int i) {
            }

            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int i) {
                if (node instanceof TextNode) {
                    sb.append(((TextNode) node).getWholeText());
                }
            }
        }, this);
        return sb.toString();
    }

    public String ownText() {
        StringBuilder sb = new StringBuilder();
        ownText(sb);
        return sb.toString().trim();
    }

    private void ownText(StringBuilder sb) {
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                appendNormalisedText(sb, (TextNode) node);
            } else if (node instanceof Element) {
                appendWhitespaceIfBr((Element) node, sb);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendNormalisedText(StringBuilder sb, TextNode textNode) {
        String wholeText = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode) || (textNode instanceof CDataNode)) {
            sb.append(wholeText);
        } else {
            StringUtil.appendNormalisedWhitespace(sb, wholeText, TextNode.lastCharIsWhitespace(sb));
        }
    }

    private static void appendWhitespaceIfBr(Element element, StringBuilder sb) {
        if (element.tag.getName().equals("br") && !TextNode.lastCharIsWhitespace(sb)) {
            sb.append(" ");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean preserveWhitespace(Node node) {
        if (node != null && (node instanceof Element)) {
            Element element = (Element) node;
            int i = 0;
            while (!element.tag.preserveWhitespace()) {
                element = element.parent();
                i++;
                if (i < 6) {
                    if (element == null) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Element text(String str) {
        Validate.notNull(str);
        empty();
        appendChild(new TextNode(str));
        return this;
    }

    public boolean hasText() {
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                if (!((TextNode) node).isBlank()) {
                    return true;
                }
            } else if ((node instanceof Element) && ((Element) node).hasText()) {
                return true;
            }
        }
        return false;
    }

    public String data() {
        StringBuilder sb = new StringBuilder();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                sb.append(((DataNode) node).getWholeData());
            } else if (node instanceof Comment) {
                sb.append(((Comment) node).getData());
            } else if (node instanceof Element) {
                sb.append(((Element) node).data());
            } else if (node instanceof CDataNode) {
                sb.append(((CDataNode) node).getWholeText());
            }
        }
        return sb.toString();
    }

    public String className() {
        return attr("class").trim();
    }

    public Set<String> classNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet(Arrays.asList(classSplit.split(className())));
        linkedHashSet.remove("");
        return linkedHashSet;
    }

    public Element classNames(Set<String> set) {
        Validate.notNull(set);
        if (set.isEmpty()) {
            attributes().remove("class");
        } else {
            attributes().put("class", StringUtil.join(set, " "));
        }
        return this;
    }

    public boolean hasClass(String str) {
        String ignoreCase = attributes().getIgnoreCase("class");
        int length = ignoreCase.length();
        int length2 = str.length();
        if (length == 0 || length < length2) {
            return false;
        }
        if (length == length2) {
            return str.equalsIgnoreCase(ignoreCase);
        }
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isWhitespace(ignoreCase.charAt(i2))) {
                if (!z) {
                    continue;
                } else if (i2 - i == length2 && ignoreCase.regionMatches(true, i, str, 0, length2)) {
                    return true;
                } else {
                    z = false;
                }
            } else if (!z) {
                i = i2;
                z = true;
            }
        }
        if (!z || length - i != length2) {
            return false;
        }
        return ignoreCase.regionMatches(true, i, str, 0, length2);
    }

    public Element addClass(String str) {
        Validate.notNull(str);
        Set<String> classNames = classNames();
        classNames.add(str);
        classNames(classNames);
        return this;
    }

    public Element removeClass(String str) {
        Validate.notNull(str);
        Set<String> classNames = classNames();
        classNames.remove(str);
        classNames(classNames);
        return this;
    }

    public Element toggleClass(String str) {
        Validate.notNull(str);
        Set<String> classNames = classNames();
        if (classNames.contains(str)) {
            classNames.remove(str);
        } else {
            classNames.add(str);
        }
        classNames(classNames);
        return this;
    }

    public String val() {
        if (tagName().equals("textarea")) {
            return text();
        }
        return attr(FirebaseAnalytics.Param.VALUE);
    }

    public Element val(String str) {
        if (tagName().equals("textarea")) {
            text(str);
        } else {
            attr(FirebaseAnalytics.Param.VALUE, str);
        }
        return this;
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        if (outputSettings.prettyPrint() && (this.tag.formatAsBlock() || ((parent() != null && parent().tag().formatAsBlock()) || outputSettings.outline()))) {
            if (!(appendable instanceof StringBuilder)) {
                indent(appendable, i, outputSettings);
            } else if (((StringBuilder) appendable).length() > 0) {
                indent(appendable, i, outputSettings);
            }
        }
        appendable.append('<').append(tagName());
        if (this.attributes != null) {
            this.attributes.html(appendable, outputSettings);
        }
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            appendable.append('>');
        } else if (outputSettings.syntax() != Document.OutputSettings.Syntax.html || !this.tag.isEmpty()) {
            appendable.append(" />");
        } else {
            appendable.append('>');
        }
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            if (outputSettings.prettyPrint() && !this.childNodes.isEmpty() && (this.tag.formatAsBlock() || (outputSettings.outline() && (this.childNodes.size() > 1 || (this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode)))))) {
                indent(appendable, i, outputSettings);
            }
            appendable.append("</").append(tagName()).append('>');
        }
    }

    public String html() {
        StringBuilder stringBuilder = StringUtil.stringBuilder();
        html(stringBuilder);
        return getOutputSettings().prettyPrint() ? stringBuilder.toString().trim() : stringBuilder.toString();
    }

    private void html(StringBuilder sb) {
        for (Node node : this.childNodes) {
            node.outerHtml(sb);
        }
    }

    @Override // org.jsoup.nodes.Node
    public <T extends Appendable> T html(T t) {
        for (Node node : this.childNodes) {
            node.outerHtml(t);
        }
        return t;
    }

    public Element html(String str) {
        empty();
        append(str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    @Override // org.jsoup.nodes.Node
    public Element clone() {
        return (Element) super.clone();
    }

    @Override // org.jsoup.nodes.Node
    public Element shallowClone() {
        return new Element(this.tag, this.baseUri, this.attributes);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jsoup.nodes.Node
    public Element doClone(Node node) {
        Element element = (Element) super.doClone(node);
        element.attributes = this.attributes != null ? this.attributes.clone() : null;
        element.baseUri = this.baseUri;
        element.childNodes = new NodeList(element, this.childNodes.size());
        element.childNodes.addAll(this.childNodes);
        return element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class NodeList extends ChangeNotifyingArrayList<Node> {
        private final Element owner;

        NodeList(Element element, int i) {
            super(i);
            this.owner = element;
        }

        @Override // org.jsoup.helper.ChangeNotifyingArrayList
        public void onContentsChanged() {
            this.owner.nodelistChanged();
        }
    }
}
