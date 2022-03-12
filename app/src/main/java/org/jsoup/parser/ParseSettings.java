package org.jsoup.parser;

import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attributes;

/* loaded from: classes.dex */
public class ParseSettings {
    public static final ParseSettings htmlDefault = new ParseSettings(false, false);
    public static final ParseSettings preserveCase = new ParseSettings(true, true);
    private final boolean preserveAttributeCase;
    private final boolean preserveTagCase;

    public ParseSettings(boolean z, boolean z2) {
        this.preserveTagCase = z;
        this.preserveAttributeCase = z2;
    }

    public String normalizeTag(String str) {
        String trim = str.trim();
        return !this.preserveTagCase ? Normalizer.lowerCase(trim) : trim;
    }

    String normalizeAttribute(String str) {
        String trim = str.trim();
        return !this.preserveAttributeCase ? Normalizer.lowerCase(trim) : trim;
    }

    public Attributes normalizeAttributes(Attributes attributes) {
        if (!this.preserveAttributeCase) {
            attributes.normalize();
        }
        return attributes;
    }
}
