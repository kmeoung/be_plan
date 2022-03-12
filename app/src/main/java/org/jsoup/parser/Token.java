package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attributes;

/* loaded from: classes.dex */
public abstract class Token {
    TokenType type;

    /* loaded from: classes.dex */
    public enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        EOF
    }

    public abstract Token reset();

    private Token() {
    }

    public String tokenType() {
        return getClass().getSimpleName();
    }

    public static void reset(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    /* loaded from: classes.dex */
    public static final class Doctype extends Token {
        final StringBuilder name = new StringBuilder();
        String pubSysKey = null;
        final StringBuilder publicIdentifier = new StringBuilder();
        final StringBuilder systemIdentifier = new StringBuilder();
        boolean forceQuirks = false;

        public Doctype() {
            super();
            this.type = TokenType.Doctype;
        }

        @Override // org.jsoup.parser.Token
        public Token reset() {
            reset(this.name);
            this.pubSysKey = null;
            reset(this.publicIdentifier);
            reset(this.systemIdentifier);
            this.forceQuirks = false;
            return this;
        }

        public String getName() {
            return this.name.toString();
        }

        public String getPubSysKey() {
            return this.pubSysKey;
        }

        public String getPublicIdentifier() {
            return this.publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Tag extends Token {
        Attributes attributes;
        protected String normalName;
        private String pendingAttributeName;
        private String pendingAttributeValueS;
        protected String tagName;
        private StringBuilder pendingAttributeValue = new StringBuilder();
        private boolean hasEmptyAttributeValue = false;
        private boolean hasPendingAttributeValue = false;
        boolean selfClosing = false;

        Tag() {
            super();
        }

        @Override // org.jsoup.parser.Token
        public Tag reset() {
            this.tagName = null;
            this.normalName = null;
            this.pendingAttributeName = null;
            reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            this.selfClosing = false;
            this.attributes = null;
            return this;
        }

        public final void newAttribute() {
            String str;
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.pendingAttributeName != null) {
                this.pendingAttributeName = this.pendingAttributeName.trim();
                if (this.pendingAttributeName.length() > 0) {
                    if (this.hasPendingAttributeValue) {
                        str = this.pendingAttributeValue.length() > 0 ? this.pendingAttributeValue.toString() : this.pendingAttributeValueS;
                    } else {
                        str = this.hasEmptyAttributeValue ? "" : null;
                    }
                    this.attributes.put(this.pendingAttributeName, str);
                }
            }
            this.pendingAttributeName = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
        }

        public final void finaliseTag() {
            if (this.pendingAttributeName != null) {
                newAttribute();
            }
        }

        public final String name() {
            Validate.isFalse(this.tagName == null || this.tagName.length() == 0);
            return this.tagName;
        }

        public final String normalName() {
            return this.normalName;
        }

        public final Tag name(String str) {
            this.tagName = str;
            this.normalName = Normalizer.lowerCase(str);
            return this;
        }

        public final boolean isSelfClosing() {
            return this.selfClosing;
        }

        public final Attributes getAttributes() {
            return this.attributes;
        }

        public final void appendTagName(String str) {
            if (this.tagName != null) {
                str = this.tagName.concat(str);
            }
            this.tagName = str;
            this.normalName = Normalizer.lowerCase(this.tagName);
        }

        public final void appendTagName(char c) {
            appendTagName(String.valueOf(c));
        }

        public final void appendAttributeName(String str) {
            if (this.pendingAttributeName != null) {
                str = this.pendingAttributeName.concat(str);
            }
            this.pendingAttributeName = str;
        }

        public final void appendAttributeName(char c) {
            appendAttributeName(String.valueOf(c));
        }

        public final void appendAttributeValue(String str) {
            ensureAttributeValue();
            if (this.pendingAttributeValue.length() == 0) {
                this.pendingAttributeValueS = str;
            } else {
                this.pendingAttributeValue.append(str);
            }
        }

        public final void appendAttributeValue(char c) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(c);
        }

        final void appendAttributeValue(char[] cArr) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(cArr);
        }

        public final void appendAttributeValue(int[] iArr) {
            ensureAttributeValue();
            for (int i : iArr) {
                this.pendingAttributeValue.appendCodePoint(i);
            }
        }

        public final void setEmptyAttributeValue() {
            this.hasEmptyAttributeValue = true;
        }

        private void ensureAttributeValue() {
            this.hasPendingAttributeValue = true;
            if (this.pendingAttributeValueS != null) {
                this.pendingAttributeValue.append(this.pendingAttributeValueS);
                this.pendingAttributeValueS = null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class StartTag extends Tag {
        public StartTag() {
            this.attributes = new Attributes();
            this.type = TokenType.StartTag;
        }

        @Override // org.jsoup.parser.Token.Tag, org.jsoup.parser.Token
        public Tag reset() {
            super.reset();
            this.attributes = new Attributes();
            return this;
        }

        public StartTag nameAttr(String str, Attributes attributes) {
            this.tagName = str;
            this.attributes = attributes;
            this.normalName = Normalizer.lowerCase(this.tagName);
            return this;
        }

        public String toString() {
            if (this.attributes == null || this.attributes.size() <= 0) {
                return "<" + name() + ">";
            }
            return "<" + name() + " " + this.attributes.toString() + ">";
        }
    }

    /* loaded from: classes.dex */
    public static final class EndTag extends Tag {
        public EndTag() {
            this.type = TokenType.EndTag;
        }

        public String toString() {
            return "</" + name() + ">";
        }
    }

    /* loaded from: classes.dex */
    public static final class Comment extends Token {
        final StringBuilder data = new StringBuilder();
        boolean bogus = false;

        @Override // org.jsoup.parser.Token
        public Token reset() {
            reset(this.data);
            this.bogus = false;
            return this;
        }

        public Comment() {
            super();
            this.type = TokenType.Comment;
        }

        public String getData() {
            return this.data.toString();
        }

        public String toString() {
            return "<!--" + getData() + "-->";
        }
    }

    /* loaded from: classes.dex */
    public static class Character extends Token {
        private String data;

        public Character() {
            super();
            this.type = TokenType.Character;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.Token
        public Token reset() {
            this.data = null;
            return this;
        }

        public Character data(String str) {
            this.data = str;
            return this;
        }

        public String getData() {
            return this.data;
        }

        public String toString() {
            return getData();
        }
    }

    /* loaded from: classes.dex */
    public static final class CData extends Character {
        public CData(String str) {
            data(str);
        }

        @Override // org.jsoup.parser.Token.Character
        public String toString() {
            return "<![CDATA[" + getData() + "]]>";
        }
    }

    /* loaded from: classes.dex */
    static final class EOF extends Token {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.Token
        public Token reset() {
            return this;
        }

        public EOF() {
            super();
            this.type = TokenType.EOF;
        }
    }

    public final boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }

    public final Doctype asDoctype() {
        return (Doctype) this;
    }

    public final boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }

    public final StartTag asStartTag() {
        return (StartTag) this;
    }

    public final boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }

    public final EndTag asEndTag() {
        return (EndTag) this;
    }

    public final boolean isComment() {
        return this.type == TokenType.Comment;
    }

    public final Comment asComment() {
        return (Comment) this;
    }

    public final boolean isCharacter() {
        return this.type == TokenType.Character;
    }

    public final boolean isCData() {
        return this instanceof CData;
    }

    public final Character asCharacter() {
        return (Character) this;
    }

    public final boolean isEOF() {
        return this.type == TokenType.EOF;
    }
}
