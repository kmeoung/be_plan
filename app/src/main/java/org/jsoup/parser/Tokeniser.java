package org.jsoup.parser;

import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Arrays;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Token;

/* loaded from: classes.dex */
public final class Tokeniser {
    static final char replacementChar = 65533;
    static final int win1252ExtensionsStart = 128;
    private Token emitPending;
    private final ParseErrorList errors;
    private String lastStartTag;
    private final CharacterReader reader;
    Token.Tag tagPending;
    private static final char[] notCharRefCharsSorted = {'\t', '\n', TokenParser.CR, '\f', TokenParser.SP, '<', '&'};
    static final int[] win1252Extensions = {8364, 129, 8218, HttpStatus.SC_PAYMENT_REQUIRED, 8222, 8230, 8224, 8225, 710, 8240, 352, 8249, 338, 141, 381, 143, 144, 8216, 8217, 8220, 8221, 8226, 8211, 8212, 732, 8482, 353, 8250, 339, 157, 382, 376};
    private TokeniserState state = TokeniserState.Data;
    private boolean isEmitPending = false;
    private String charsString = null;
    private StringBuilder charsBuilder = new StringBuilder(1024);
    StringBuilder dataBuffer = new StringBuilder(1024);
    Token.StartTag startPending = new Token.StartTag();
    Token.EndTag endPending = new Token.EndTag();
    Token.Character charPending = new Token.Character();
    Token.Doctype doctypePending = new Token.Doctype();
    Token.Comment commentPending = new Token.Comment();
    private final int[] codepointHolder = new int[1];
    private final int[] multipointHolder = new int[2];

    boolean currentNodeInHtmlNS() {
        return true;
    }

    static {
        Arrays.sort(notCharRefCharsSorted);
    }

    public Tokeniser(CharacterReader characterReader, ParseErrorList parseErrorList) {
        this.reader = characterReader;
        this.errors = parseErrorList;
    }

    public Token read() {
        while (!this.isEmitPending) {
            this.state.read(this, this.reader);
        }
        if (this.charsBuilder.length() > 0) {
            String sb = this.charsBuilder.toString();
            this.charsBuilder.delete(0, this.charsBuilder.length());
            this.charsString = null;
            return this.charPending.data(sb);
        } else if (this.charsString != null) {
            Token.Character data = this.charPending.data(this.charsString);
            this.charsString = null;
            return data;
        } else {
            this.isEmitPending = false;
            return this.emitPending;
        }
    }

    public void emit(Token token) {
        Validate.isFalse(this.isEmitPending, "There is an unread token pending!");
        this.emitPending = token;
        this.isEmitPending = true;
        if (token.type == Token.TokenType.StartTag) {
            this.lastStartTag = ((Token.StartTag) token).tagName;
        } else if (token.type == Token.TokenType.EndTag && ((Token.EndTag) token).attributes != null) {
            error("Attributes incorrectly present on end tag");
        }
    }

    public void emit(String str) {
        if (this.charsString == null) {
            this.charsString = str;
            return;
        }
        if (this.charsBuilder.length() == 0) {
            this.charsBuilder.append(this.charsString);
        }
        this.charsBuilder.append(str);
    }

    void emit(char[] cArr) {
        emit(String.valueOf(cArr));
    }

    public void emit(int[] iArr) {
        emit(new String(iArr, 0, iArr.length));
    }

    public void emit(char c) {
        emit(String.valueOf(c));
    }

    TokeniserState getState() {
        return this.state;
    }

    public void transition(TokeniserState tokeniserState) {
        this.state = tokeniserState;
    }

    public void advanceTransition(TokeniserState tokeniserState) {
        this.reader.advance();
        this.state = tokeniserState;
    }

    public int[] consumeCharacterReference(Character ch, boolean z) {
        int i;
        if (this.reader.isEmpty()) {
            return null;
        }
        if ((ch != null && ch.charValue() == this.reader.current()) || this.reader.matchesAnySorted(notCharRefCharsSorted)) {
            return null;
        }
        int[] iArr = this.codepointHolder;
        this.reader.mark();
        if (this.reader.matchConsume("#")) {
            boolean matchConsumeIgnoreCase = this.reader.matchConsumeIgnoreCase("X");
            String consumeHexSequence = matchConsumeIgnoreCase ? this.reader.consumeHexSequence() : this.reader.consumeDigitSequence();
            if (consumeHexSequence.length() == 0) {
                characterReferenceError("numeric reference with no numerals");
                this.reader.rewindToMark();
                return null;
            }
            if (!this.reader.matchConsume(";")) {
                characterReferenceError("missing semicolon");
            }
            try {
                i = Integer.valueOf(consumeHexSequence, matchConsumeIgnoreCase ? 16 : 10).intValue();
            } catch (NumberFormatException unused) {
                i = -1;
            }
            if (i == -1 || ((i >= 55296 && i <= 57343) || i > 1114111)) {
                characterReferenceError("character outside of valid range");
                iArr[0] = 65533;
                return iArr;
            }
            if (i >= 128 && i < win1252Extensions.length + 128) {
                characterReferenceError("character is not a valid unicode code point");
                i = win1252Extensions[i - 128];
            }
            iArr[0] = i;
            return iArr;
        }
        String consumeLetterThenDigitSequence = this.reader.consumeLetterThenDigitSequence();
        boolean matches = this.reader.matches(';');
        if (!(Entities.isBaseNamedEntity(consumeLetterThenDigitSequence) || (Entities.isNamedEntity(consumeLetterThenDigitSequence) && matches))) {
            this.reader.rewindToMark();
            if (matches) {
                characterReferenceError(String.format("invalid named referenece '%s'", consumeLetterThenDigitSequence));
            }
            return null;
        } else if (!z || (!this.reader.matchesLetter() && !this.reader.matchesDigit() && !this.reader.matchesAny('=', '-', '_'))) {
            if (!this.reader.matchConsume(";")) {
                characterReferenceError("missing semicolon");
            }
            int codepointsForName = Entities.codepointsForName(consumeLetterThenDigitSequence, this.multipointHolder);
            if (codepointsForName == 1) {
                iArr[0] = this.multipointHolder[0];
                return iArr;
            } else if (codepointsForName == 2) {
                return this.multipointHolder;
            } else {
                Validate.fail("Unexpected characters returned for " + consumeLetterThenDigitSequence);
                return this.multipointHolder;
            }
        } else {
            this.reader.rewindToMark();
            return null;
        }
    }

    public Token.Tag createTagPending(boolean z) {
        this.tagPending = z ? this.startPending.reset() : this.endPending.reset();
        return this.tagPending;
    }

    public void emitTagPending() {
        this.tagPending.finaliseTag();
        emit(this.tagPending);
    }

    public void createCommentPending() {
        this.commentPending.reset();
    }

    public void emitCommentPending() {
        emit(this.commentPending);
    }

    public void createDoctypePending() {
        this.doctypePending.reset();
    }

    public void emitDoctypePending() {
        emit(this.doctypePending);
    }

    public void createTempBuffer() {
        Token.reset(this.dataBuffer);
    }

    public boolean isAppropriateEndTagToken() {
        return this.lastStartTag != null && this.tagPending.name().equalsIgnoreCase(this.lastStartTag);
    }

    public String appropriateEndTagName() {
        return this.lastStartTag;
    }

    public void error(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected character '%s' in input state [%s]", Character.valueOf(this.reader.current()), tokeniserState));
        }
    }

    public void eofError(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpectedly reached end of file (EOF) in input state [%s]", tokeniserState));
        }
    }

    private void characterReferenceError(String str) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Invalid character reference: %s", str));
        }
    }

    public void error(String str) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), str));
        }
    }

    public String unescapeEntities(boolean z) {
        StringBuilder stringBuilder = StringUtil.stringBuilder();
        while (!this.reader.isEmpty()) {
            stringBuilder.append(this.reader.consumeTo('&'));
            if (this.reader.matches('&')) {
                this.reader.consume();
                int[] consumeCharacterReference = consumeCharacterReference(null, z);
                if (consumeCharacterReference == null || consumeCharacterReference.length == 0) {
                    stringBuilder.append('&');
                } else {
                    stringBuilder.appendCodePoint(consumeCharacterReference[0]);
                    if (consumeCharacterReference.length == 2) {
                        stringBuilder.appendCodePoint(consumeCharacterReference[1]);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
