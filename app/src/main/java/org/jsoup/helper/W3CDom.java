package org.jsoup.helper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* loaded from: classes.dex */
public class W3CDom {
    protected DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public Document fromJsoup(org.jsoup.nodes.Document document) {
        Validate.notNull(document);
        try {
            this.factory.setNamespaceAware(true);
            Document newDocument = this.factory.newDocumentBuilder().newDocument();
            convert(document, newDocument);
            return newDocument;
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public void convert(org.jsoup.nodes.Document document, Document document2) {
        if (!StringUtil.isBlank(document.location())) {
            document2.setDocumentURI(document.location());
        }
        NodeTraversor.traverse(new W3CBuilder(document2), document.child(0));
    }

    /* loaded from: classes.dex */
    public static class W3CBuilder implements NodeVisitor {
        private static final String xmlnsKey = "xmlns";
        private static final String xmlnsPrefix = "xmlns:";
        private Element dest;
        private final Document doc;
        private final Stack<HashMap<String, String>> namespacesStack = new Stack<>();

        public W3CBuilder(Document document) {
            this.doc = document;
            this.namespacesStack.push(new HashMap<>());
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i) {
            this.namespacesStack.push(new HashMap<>(this.namespacesStack.peek()));
            if (node instanceof org.jsoup.nodes.Element) {
                org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) node;
                String updateNamespaces = updateNamespaces(element);
                Element createElementNS = this.doc.createElementNS(this.namespacesStack.peek().get(updateNamespaces), element.tagName());
                copyAttributes(element, createElementNS);
                if (this.dest == null) {
                    this.doc.appendChild(createElementNS);
                } else {
                    this.dest.appendChild(createElementNS);
                }
                this.dest = createElementNS;
            } else if (node instanceof TextNode) {
                this.dest.appendChild(this.doc.createTextNode(((TextNode) node).getWholeText()));
            } else if (node instanceof Comment) {
                this.dest.appendChild(this.doc.createComment(((Comment) node).getData()));
            } else if (node instanceof DataNode) {
                this.dest.appendChild(this.doc.createTextNode(((DataNode) node).getWholeData()));
            }
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i) {
            if ((node instanceof org.jsoup.nodes.Element) && (this.dest.getParentNode() instanceof Element)) {
                this.dest = (Element) this.dest.getParentNode();
            }
            this.namespacesStack.pop();
        }

        private void copyAttributes(Node node, Element element) {
            Iterator<Attribute> it = node.attributes().iterator();
            while (it.hasNext()) {
                Attribute next = it.next();
                String replaceAll = next.getKey().replaceAll("[^-a-zA-Z0-9_:.]", "");
                if (replaceAll.matches("[a-zA-Z_:][-a-zA-Z0-9_:.]*")) {
                    element.setAttribute(replaceAll, next.getValue());
                }
            }
        }

        private String updateNamespaces(org.jsoup.nodes.Element element) {
            String str;
            Iterator<Attribute> it = element.attributes().iterator();
            while (it.hasNext()) {
                Attribute next = it.next();
                String key = next.getKey();
                if (key.equals(xmlnsKey)) {
                    str = "";
                } else if (key.startsWith(xmlnsPrefix)) {
                    str = key.substring(xmlnsPrefix.length());
                }
                this.namespacesStack.peek().put(str, next.getValue());
            }
            int indexOf = element.tagName().indexOf(":");
            return indexOf > 0 ? element.tagName().substring(0, indexOf) : "";
        }
    }

    public String asString(Document document) {
        try {
            DOMSource dOMSource = new DOMSource(document);
            StringWriter stringWriter = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform(dOMSource, new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }
}
