package org.jsoup.nodes;

import cz.msebera.android.httpclient.client.config.CookieSpecs;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/* loaded from: classes.dex */
public class Attribute implements Map.Entry<String, String>, Cloneable {
    private static final String[] booleanAttributes = {"allowfullscreen", "async", "autofocus", "checked", "compact", "declare", CookieSpecs.DEFAULT, "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch"};
    private String key;
    Attributes parent;
    private String val;

    public Attribute(String str, String str2) {
        this(str, str2, null);
    }

    public Attribute(String str, String str2, Attributes attributes) {
        Validate.notNull(str);
        this.key = str.trim();
        Validate.notEmpty(str);
        this.val = str2;
        this.parent = attributes;
    }

    @Override // java.util.Map.Entry
    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        int indexOfKey;
        Validate.notNull(str);
        String trim = str.trim();
        Validate.notEmpty(trim);
        if (!(this.parent == null || (indexOfKey = this.parent.indexOfKey(this.key)) == -1)) {
            this.parent.keys[indexOfKey] = trim;
        }
        this.key = trim;
    }

    @Override // java.util.Map.Entry
    public String getValue() {
        return this.val;
    }

    public String setValue(String str) {
        int indexOfKey;
        String str2 = this.parent.get(this.key);
        if (!(this.parent == null || (indexOfKey = this.parent.indexOfKey(this.key)) == -1)) {
            this.parent.vals[indexOfKey] = str;
        }
        this.val = str;
        return str2;
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        try {
            html(sb, new Document("").outputSettings());
            return sb.toString();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    protected static void html(String str, String str2, Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        appendable.append(str);
        if (!shouldCollapseAttribute(str, str2, outputSettings)) {
            appendable.append("=\"");
            Entities.escape(appendable, Attributes.checkNotNull(str2), outputSettings, true, false, false);
            appendable.append(TokenParser.DQUOTE);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void html(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        html(this.key, this.val, appendable, outputSettings);
    }

    public String toString() {
        return html();
    }

    public static Attribute createFromEncoded(String str, String str2) {
        return new Attribute(str, Entities.unescape(str2, true), null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isDataAttribute() {
        return isDataAttribute(this.key);
    }

    protected static boolean isDataAttribute(String str) {
        return str.startsWith("data-") && str.length() > "data-".length();
    }

    protected final boolean shouldCollapseAttribute(Document.OutputSettings outputSettings) {
        return shouldCollapseAttribute(this.key, this.val, outputSettings);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean shouldCollapseAttribute(String str, String str2, Document.OutputSettings outputSettings) {
        return outputSettings.syntax() == Document.OutputSettings.Syntax.html && (str2 == null || (("".equals(str2) || str2.equalsIgnoreCase(str)) && isBooleanAttribute(str)));
    }

    protected boolean isBooleanAttribute() {
        return Arrays.binarySearch(booleanAttributes, this.key) >= 0 || this.val == null;
    }

    protected static boolean isBooleanAttribute(String str) {
        return Arrays.binarySearch(booleanAttributes, str) >= 0;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attribute attribute = (Attribute) obj;
        if (this.key == null ? attribute.key == null : this.key.equals(attribute.key)) {
            return this.val != null ? this.val.equals(attribute.val) : attribute.val == null;
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        int i = 0;
        int hashCode = (this.key != null ? this.key.hashCode() : 0) * 31;
        if (this.val != null) {
            i = this.val.hashCode();
        }
        return hashCode + i;
    }

    public Attribute clone() {
        try {
            return (Attribute) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
