package org.jsoup.nodes;

import com.naver.temy123.baseproject.base.Utils.HW_Params;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/* loaded from: classes.dex */
public class FormElement extends Element {
    private final Elements elements = new Elements();

    public FormElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
    }

    public Elements elements() {
        return this.elements;
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public void removeChild(Node node) {
        super.removeChild(node);
        this.elements.remove(node);
    }

    public Connection submit() {
        String absUrl = hasAttr("action") ? absUrl("action") : baseUri();
        Validate.notEmpty(absUrl, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        return Jsoup.connect(absUrl).data(formData()).method(attr(HW_Params.HW_NETWORK_EXTRA_METHOD).toUpperCase().equals("POST") ? Connection.Method.POST : Connection.Method.GET);
    }

    public List<Connection.KeyVal> formData() {
        Element first;
        ArrayList arrayList = new ArrayList();
        Iterator<Element> it = this.elements.iterator();
        while (it.hasNext()) {
            Element next = it.next();
            if (next.tag().isFormSubmittable() && !next.hasAttr("disabled")) {
                String attr = next.attr("name");
                if (attr.length() != 0) {
                    String attr2 = next.attr("type");
                    if ("select".equals(next.tagName())) {
                        boolean z = false;
                        Iterator<Element> it2 = next.select("option[selected]").iterator();
                        while (it2.hasNext()) {
                            arrayList.add(HttpConnection.KeyVal.create(attr, it2.next().val()));
                            z = true;
                        }
                        if (!z && (first = next.select("option").first()) != null) {
                            arrayList.add(HttpConnection.KeyVal.create(attr, first.val()));
                        }
                    } else if (!"checkbox".equalsIgnoreCase(attr2) && !"radio".equalsIgnoreCase(attr2)) {
                        arrayList.add(HttpConnection.KeyVal.create(attr, next.val()));
                    } else if (next.hasAttr("checked")) {
                        arrayList.add(HttpConnection.KeyVal.create(attr, next.val().length() > 0 ? next.val() : "on"));
                    }
                }
            }
        }
        return arrayList;
    }
}
