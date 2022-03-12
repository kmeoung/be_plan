package org.jsoup.parser;

import java.io.Reader;
import java.util.ArrayList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Token;

/* loaded from: classes.dex */
public abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    protected ParseErrorList errors;
    CharacterReader reader;
    protected ParseSettings settings;
    protected ArrayList<Element> stack;
    Tokeniser tokeniser;
    private Token.StartTag start = new Token.StartTag();
    private Token.EndTag end = new Token.EndTag();

    public abstract ParseSettings defaultSettings();

    public abstract boolean process(Token token);

    public void initialiseParse(Reader reader, String str, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        Validate.notNull(reader, "String input must not be null");
        Validate.notNull(str, "BaseURI must not be null");
        this.doc = new Document(str);
        this.settings = parseSettings;
        this.reader = new CharacterReader(reader);
        this.errors = parseErrorList;
        this.currentToken = null;
        this.tokeniser = new Tokeniser(this.reader, parseErrorList);
        this.stack = new ArrayList<>(32);
        this.baseUri = str;
    }

    public Document parse(Reader reader, String str, ParseErrorList parseErrorList, ParseSettings parseSettings) {
        initialiseParse(reader, str, parseErrorList, parseSettings);
        runParser();
        return this.doc;
    }

    public void runParser() {
        Token read;
        do {
            read = this.tokeniser.read();
            process(read);
            read.reset();
        } while (read.type != Token.TokenType.EOF);
    }

    public boolean processStartTag(String str) {
        if (this.currentToken == this.start) {
            return process(new Token.StartTag().name(str));
        }
        return process(this.start.reset().name(str));
    }

    public boolean processStartTag(String str, Attributes attributes) {
        if (this.currentToken == this.start) {
            return process(new Token.StartTag().nameAttr(str, attributes));
        }
        this.start.reset();
        this.start.nameAttr(str, attributes);
        return process(this.start);
    }

    public boolean processEndTag(String str) {
        if (this.currentToken == this.end) {
            return process(new Token.EndTag().name(str));
        }
        return process(this.end.reset().name(str));
    }

    public Element currentElement() {
        int size = this.stack.size();
        if (size > 0) {
            return this.stack.get(size - 1);
        }
        return null;
    }
}
