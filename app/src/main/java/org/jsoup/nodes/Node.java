package org.jsoup.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.SerializationException;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/* loaded from: classes.dex */
public abstract class Node implements Cloneable {
    static final String EmptyString = "";
    Node parentNode;
    int siblingIndex;

    public abstract Attributes attributes();

    public abstract String baseUri();

    public abstract int childNodeSize();

    protected abstract void doSetBaseUri(String str);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract List<Node> ensureChildNodes();

    public boolean equals(Object obj) {
        return this == obj;
    }

    protected abstract boolean hasAttributes();

    public abstract String nodeName();

    /* JADX INFO: Access modifiers changed from: package-private */
    public void nodelistChanged() {
    }

    abstract void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException;

    abstract void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException;

    public boolean hasParent() {
        return this.parentNode != null;
    }

    public String attr(String str) {
        Validate.notNull(str);
        if (!hasAttributes()) {
            return "";
        }
        String ignoreCase = attributes().getIgnoreCase(str);
        return ignoreCase.length() > 0 ? ignoreCase : str.startsWith("abs:") ? absUrl(str.substring("abs:".length())) : "";
    }

    public Node attr(String str, String str2) {
        attributes().putIgnoreCase(str, str2);
        return this;
    }

    public boolean hasAttr(String str) {
        Validate.notNull(str);
        if (str.startsWith("abs:")) {
            String substring = str.substring("abs:".length());
            if (attributes().hasKeyIgnoreCase(substring) && !absUrl(substring).equals("")) {
                return true;
            }
        }
        return attributes().hasKeyIgnoreCase(str);
    }

    public Node removeAttr(String str) {
        Validate.notNull(str);
        attributes().removeIgnoreCase(str);
        return this;
    }

    public Node clearAttributes() {
        Iterator<Attribute> it = attributes().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        return this;
    }

    public void setBaseUri(final String str) {
        Validate.notNull(str);
        traverse(new NodeVisitor() { // from class: org.jsoup.nodes.Node.1
            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int i) {
            }

            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int i) {
                node.doSetBaseUri(str);
            }
        });
    }

    public String absUrl(String str) {
        Validate.notEmpty(str);
        return !hasAttr(str) ? "" : StringUtil.resolve(baseUri(), attr(str));
    }

    public Node childNode(int i) {
        return ensureChildNodes().get(i);
    }

    public List<Node> childNodes() {
        return Collections.unmodifiableList(ensureChildNodes());
    }

    public List<Node> childNodesCopy() {
        List<Node> ensureChildNodes = ensureChildNodes();
        ArrayList arrayList = new ArrayList(ensureChildNodes.size());
        for (Node node : ensureChildNodes) {
            arrayList.add(node.clone());
        }
        return arrayList;
    }

    protected Node[] childNodesAsArray() {
        return (Node[]) ensureChildNodes().toArray(new Node[childNodeSize()]);
    }

    public Node parent() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    public Node root() {
        Node node = this;
        while (node.parentNode != null) {
            node = node.parentNode;
        }
        return node;
    }

    public Document ownerDocument() {
        Node root = root();
        if (root instanceof Document) {
            return (Document) root;
        }
        return null;
    }

    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }

    public Node before(String str) {
        addSiblingHtml(this.siblingIndex, str);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex, node);
        return this;
    }

    public Node after(String str) {
        addSiblingHtml(this.siblingIndex + 1, str);
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex + 1, node);
        return this;
    }

    private void addSiblingHtml(int i, String str) {
        Validate.notNull(str);
        Validate.notNull(this.parentNode);
        List<Node> parseFragment = Parser.parseFragment(str, parent() instanceof Element ? (Element) parent() : null, baseUri());
        this.parentNode.addChildren(i, (Node[]) parseFragment.toArray(new Node[parseFragment.size()]));
    }

    public Node wrap(String str) {
        Validate.notEmpty(str);
        List<Node> parseFragment = Parser.parseFragment(str, parent() instanceof Element ? (Element) parent() : null, baseUri());
        Node node = parseFragment.get(0);
        if (node == null || !(node instanceof Element)) {
            return null;
        }
        Element element = (Element) node;
        Element deepChild = getDeepChild(element);
        this.parentNode.replaceChild(this, element);
        deepChild.addChildren(this);
        if (parseFragment.size() > 0) {
            for (int i = 0; i < parseFragment.size(); i++) {
                Node node2 = parseFragment.get(i);
                node2.parentNode.removeChild(node2);
                element.appendChild(node2);
            }
        }
        return this;
    }

    public Node unwrap() {
        Validate.notNull(this.parentNode);
        List<Node> ensureChildNodes = ensureChildNodes();
        Node node = ensureChildNodes.size() > 0 ? ensureChildNodes.get(0) : null;
        this.parentNode.addChildren(this.siblingIndex, childNodesAsArray());
        remove();
        return node;
    }

    private Element getDeepChild(Element element) {
        Elements children = element.children();
        return children.size() > 0 ? getDeepChild(children.get(0)) : element;
    }

    public void replaceWith(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, node);
    }

    protected void setParentNode(Node node) {
        Validate.notNull(node);
        if (this.parentNode != null) {
            this.parentNode.removeChild(this);
        }
        this.parentNode = node;
    }

    protected void replaceChild(Node node, Node node2) {
        Validate.isTrue(node.parentNode == this);
        Validate.notNull(node2);
        if (node2.parentNode != null) {
            node2.parentNode.removeChild(node2);
        }
        int i = node.siblingIndex;
        ensureChildNodes().set(i, node2);
        node2.parentNode = this;
        node2.setSiblingIndex(i);
        node.parentNode = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeChild(Node node) {
        Validate.isTrue(node.parentNode == this);
        int i = node.siblingIndex;
        ensureChildNodes().remove(i);
        reindexChildren(i);
        node.parentNode = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addChildren(Node... nodeArr) {
        List<Node> ensureChildNodes = ensureChildNodes();
        for (Node node : nodeArr) {
            reparentChild(node);
            ensureChildNodes.add(node);
            node.setSiblingIndex(ensureChildNodes.size() - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addChildren(int i, Node... nodeArr) {
        Validate.noNullElements(nodeArr);
        List<Node> ensureChildNodes = ensureChildNodes();
        for (Node node : nodeArr) {
            reparentChild(node);
        }
        ensureChildNodes.addAll(i, Arrays.asList(nodeArr));
        reindexChildren(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void reparentChild(Node node) {
        node.setParentNode(this);
    }

    private void reindexChildren(int i) {
        List<Node> ensureChildNodes = ensureChildNodes();
        while (i < ensureChildNodes.size()) {
            ensureChildNodes.get(i).setSiblingIndex(i);
            i++;
        }
    }

    public List<Node> siblingNodes() {
        if (this.parentNode == null) {
            return Collections.emptyList();
        }
        List<Node> ensureChildNodes = this.parentNode.ensureChildNodes();
        ArrayList arrayList = new ArrayList(ensureChildNodes.size() - 1);
        for (Node node : ensureChildNodes) {
            if (node != this) {
                arrayList.add(node);
            }
        }
        return arrayList;
    }

    public Node nextSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Node> ensureChildNodes = this.parentNode.ensureChildNodes();
        int i = this.siblingIndex + 1;
        if (ensureChildNodes.size() > i) {
            return ensureChildNodes.get(i);
        }
        return null;
    }

    public Node previousSibling() {
        if (this.parentNode != null && this.siblingIndex > 0) {
            return this.parentNode.ensureChildNodes().get(this.siblingIndex - 1);
        }
        return null;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setSiblingIndex(int i) {
        this.siblingIndex = i;
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        NodeTraversor.traverse(nodeVisitor, this);
        return this;
    }

    public Node filter(NodeFilter nodeFilter) {
        Validate.notNull(nodeFilter);
        NodeTraversor.filter(nodeFilter, this);
        return this;
    }

    public String outerHtml() {
        StringBuilder sb = new StringBuilder(128);
        outerHtml(sb);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void outerHtml(Appendable appendable) {
        NodeTraversor.traverse(new OuterHtmlVisitor(appendable, getOutputSettings()), this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Document.OutputSettings getOutputSettings() {
        Document ownerDocument = ownerDocument();
        if (ownerDocument == null) {
            ownerDocument = new Document("");
        }
        return ownerDocument.outputSettings();
    }

    public <T extends Appendable> T html(T t) {
        outerHtml(t);
        return t;
    }

    public String toString() {
        return outerHtml();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void indent(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        appendable.append('\n').append(StringUtil.padding(i * outputSettings.indentAmount()));
    }

    public boolean hasSameValue(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return outerHtml().equals(((Node) obj).outerHtml());
    }

    public Node clone() {
        Node doClone = doClone(null);
        LinkedList linkedList = new LinkedList();
        linkedList.add(doClone);
        while (!linkedList.isEmpty()) {
            Node node = (Node) linkedList.remove();
            int childNodeSize = node.childNodeSize();
            for (int i = 0; i < childNodeSize; i++) {
                List<Node> ensureChildNodes = node.ensureChildNodes();
                Node doClone2 = ensureChildNodes.get(i).doClone(node);
                ensureChildNodes.set(i, doClone2);
                linkedList.add(doClone2);
            }
        }
        return doClone;
    }

    public Node shallowClone() {
        return doClone(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Node doClone(Node node) {
        try {
            Node node2 = (Node) super.clone();
            node2.parentNode = node;
            node2.siblingIndex = node == null ? 0 : this.siblingIndex;
            return node2;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class OuterHtmlVisitor implements NodeVisitor {
        private Appendable accum;
        private Document.OutputSettings out;

        OuterHtmlVisitor(Appendable appendable, Document.OutputSettings outputSettings) {
            this.accum = appendable;
            this.out = outputSettings;
            outputSettings.prepareEncoder();
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i) {
            try {
                node.outerHtmlHead(this.accum, i, this.out);
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i) {
            if (!node.nodeName().equals("#text")) {
                try {
                    node.outerHtmlTail(this.accum, i, this.out);
                } catch (IOException e) {
                    throw new SerializationException(e);
                }
            }
        }
    }
}
