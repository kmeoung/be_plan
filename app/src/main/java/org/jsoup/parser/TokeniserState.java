package org.jsoup.parser;

import android.support.v4.internal.view.SupportMenu;
import cz.msebera.android.httpclient.message.TokenParser;
import org.jsoup.nodes.DocumentType;
import org.jsoup.parser.Token;

/* loaded from: classes.dex */
public enum TokeniserState {
    Data { // from class: org.jsoup.parser.TokeniserState.1
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                tokeniser.emit(characterReader.consume());
            } else if (current == '&') {
                tokeniser.advanceTransition(CharacterReferenceInData);
            } else if (current == '<') {
                tokeniser.advanceTransition(TagOpen);
            } else if (current != 65535) {
                tokeniser.emit(characterReader.consumeData());
            } else {
                tokeniser.emit(new Token.EOF());
            }
        }
    },
    CharacterReferenceInData { // from class: org.jsoup.parser.TokeniserState.2
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readCharRef(tokeniser, Data);
        }
    },
    Rcdata { // from class: org.jsoup.parser.TokeniserState.3
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit(TokeniserState.replacementChar);
            } else if (current == '&') {
                tokeniser.advanceTransition(CharacterReferenceInRcdata);
            } else if (current == '<') {
                tokeniser.advanceTransition(RcdataLessthanSign);
            } else if (current != 65535) {
                tokeniser.emit(characterReader.consumeToAny('&', '<', TokeniserState.nullChar));
            } else {
                tokeniser.emit(new Token.EOF());
            }
        }
    },
    CharacterReferenceInRcdata { // from class: org.jsoup.parser.TokeniserState.4
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readCharRef(tokeniser, Rcdata);
        }
    },
    Rawtext { // from class: org.jsoup.parser.TokeniserState.5
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readData(tokeniser, characterReader, this, RawtextLessthanSign);
        }
    },
    ScriptData { // from class: org.jsoup.parser.TokeniserState.6
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readData(tokeniser, characterReader, this, ScriptDataLessthanSign);
        }
    },
    PLAINTEXT { // from class: org.jsoup.parser.TokeniserState.7
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit(TokeniserState.replacementChar);
            } else if (current != 65535) {
                tokeniser.emit(characterReader.consumeTo(TokeniserState.nullChar));
            } else {
                tokeniser.emit(new Token.EOF());
            }
        }
    },
    TagOpen { // from class: org.jsoup.parser.TokeniserState.8
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == '!') {
                tokeniser.advanceTransition(MarkupDeclarationOpen);
            } else if (current == '/') {
                tokeniser.advanceTransition(EndTagOpen);
            } else if (current == '?') {
                tokeniser.advanceTransition(BogusComment);
            } else if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(true);
                tokeniser.transition(TagName);
            } else {
                tokeniser.error(this);
                tokeniser.emit('<');
                tokeniser.transition(Data);
            }
        }
    },
    EndTagOpen { // from class: org.jsoup.parser.TokeniserState.9
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.emit("</");
                tokeniser.transition(Data);
            } else if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.transition(TagName);
            } else if (characterReader.matches('>')) {
                tokeniser.error(this);
                tokeniser.advanceTransition(Data);
            } else {
                tokeniser.error(this);
                tokeniser.advanceTransition(BogusComment);
            }
        }
    },
    TagName { // from class: org.jsoup.parser.TokeniserState.10
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.tagPending.appendTagName(characterReader.consumeTagName());
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.tagPending.appendTagName(TokeniserState.replacementStr);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeAttributeName);
                    return;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.tagPending.appendTagName(consume);
                    return;
            }
        }
    },
    RcdataLessthanSign { // from class: org.jsoup.parser.TokeniserState.11
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RCDATAEndTagOpen);
                return;
            }
            if (characterReader.matchesLetter() && tokeniser.appropriateEndTagName() != null) {
                if (!characterReader.containsIgnoreCase("</" + tokeniser.appropriateEndTagName())) {
                    tokeniser.tagPending = tokeniser.createTagPending(false).name(tokeniser.appropriateEndTagName());
                    tokeniser.emitTagPending();
                    characterReader.unconsume();
                    tokeniser.transition(Data);
                    return;
                }
            }
            tokeniser.emit("<");
            tokeniser.transition(Rcdata);
        }
    },
    RCDATAEndTagOpen { // from class: org.jsoup.parser.TokeniserState.12
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(characterReader.current());
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.advanceTransition(RCDATAEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(Rcdata);
        }
    },
    RCDATAEndTagName { // from class: org.jsoup.parser.TokeniserState.13
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                String consumeLetterSequence = characterReader.consumeLetterSequence();
                tokeniser.tagPending.appendTagName(consumeLetterSequence);
                tokeniser.dataBuffer.append(consumeLetterSequence);
                return;
            }
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    if (tokeniser.isAppropriateEndTagToken()) {
                        tokeniser.transition(BeforeAttributeName);
                        return;
                    } else {
                        anythingElse(tokeniser, characterReader);
                        return;
                    }
                case '/':
                    if (tokeniser.isAppropriateEndTagToken()) {
                        tokeniser.transition(SelfClosingStartTag);
                        return;
                    } else {
                        anythingElse(tokeniser, characterReader);
                        return;
                    }
                case '>':
                    if (tokeniser.isAppropriateEndTagToken()) {
                        tokeniser.emitTagPending();
                        tokeniser.transition(Data);
                        return;
                    }
                    anythingElse(tokeniser, characterReader);
                    return;
                default:
                    anythingElse(tokeniser, characterReader);
                    return;
            }
        }

        private void anythingElse(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.emit("</" + tokeniser.dataBuffer.toString());
            characterReader.unconsume();
            tokeniser.transition(Rcdata);
        }
    },
    RawtextLessthanSign { // from class: org.jsoup.parser.TokeniserState.14
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RawtextEndTagOpen);
                return;
            }
            tokeniser.emit('<');
            tokeniser.transition(Rawtext);
        }
    },
    RawtextEndTagOpen { // from class: org.jsoup.parser.TokeniserState.15
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readEndTag(tokeniser, characterReader, RawtextEndTagName, Rawtext);
        }
    },
    RawtextEndTagName { // from class: org.jsoup.parser.TokeniserState.16
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, Rawtext);
        }
    },
    ScriptDataLessthanSign { // from class: org.jsoup.parser.TokeniserState.17
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == '!') {
                tokeniser.emit("<!");
                tokeniser.transition(ScriptDataEscapeStart);
            } else if (consume != '/') {
                tokeniser.emit("<");
                characterReader.unconsume();
                tokeniser.transition(ScriptData);
            } else {
                tokeniser.createTempBuffer();
                tokeniser.transition(ScriptDataEndTagOpen);
            }
        }
    },
    ScriptDataEndTagOpen { // from class: org.jsoup.parser.TokeniserState.18
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.readEndTag(tokeniser, characterReader, ScriptDataEndTagName, ScriptData);
        }
    },
    ScriptDataEndTagName { // from class: org.jsoup.parser.TokeniserState.19
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptData);
        }
    },
    ScriptDataEscapeStart { // from class: org.jsoup.parser.TokeniserState.20
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('-')) {
                tokeniser.emit('-');
                tokeniser.advanceTransition(ScriptDataEscapeStartDash);
                return;
            }
            tokeniser.transition(ScriptData);
        }
    },
    ScriptDataEscapeStartDash { // from class: org.jsoup.parser.TokeniserState.21
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('-')) {
                tokeniser.emit('-');
                tokeniser.advanceTransition(ScriptDataEscapedDashDash);
                return;
            }
            tokeniser.transition(ScriptData);
        }
    },
    ScriptDataEscaped { // from class: org.jsoup.parser.TokeniserState.22
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit(TokeniserState.replacementChar);
            } else if (current == '-') {
                tokeniser.emit('-');
                tokeniser.advanceTransition(ScriptDataEscapedDash);
            } else if (current != '<') {
                tokeniser.emit(characterReader.consumeToAny('-', '<', TokeniserState.nullChar));
            } else {
                tokeniser.advanceTransition(ScriptDataEscapedLessthanSign);
            }
        }
    },
    ScriptDataEscapedDash { // from class: org.jsoup.parser.TokeniserState.23
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.emit(TokeniserState.replacementChar);
                tokeniser.transition(ScriptDataEscaped);
            } else if (consume == '-') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataEscapedDashDash);
            } else if (consume != '<') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataEscaped);
            } else {
                tokeniser.transition(ScriptDataEscapedLessthanSign);
            }
        }
    },
    ScriptDataEscapedDashDash { // from class: org.jsoup.parser.TokeniserState.24
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.emit(TokeniserState.replacementChar);
                tokeniser.transition(ScriptDataEscaped);
            } else if (consume == '-') {
                tokeniser.emit(consume);
            } else if (consume == '<') {
                tokeniser.transition(ScriptDataEscapedLessthanSign);
            } else if (consume != '>') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataEscaped);
            } else {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptData);
            }
        }
    },
    ScriptDataEscapedLessthanSign { // from class: org.jsoup.parser.TokeniserState.25
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTempBuffer();
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.emit("<" + characterReader.current());
                tokeniser.advanceTransition(ScriptDataDoubleEscapeStart);
            } else if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataEscapedEndTagOpen);
            } else {
                tokeniser.emit('<');
                tokeniser.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedEndTagOpen { // from class: org.jsoup.parser.TokeniserState.26
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(characterReader.current());
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.advanceTransition(ScriptDataEscapedEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(ScriptDataEscaped);
        }
    },
    ScriptDataEscapedEndTagName { // from class: org.jsoup.parser.TokeniserState.27
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscapeStart { // from class: org.jsoup.parser.TokeniserState.28
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataDoubleEscaped, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscaped { // from class: org.jsoup.parser.TokeniserState.29
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.emit(TokeniserState.replacementChar);
            } else if (current == '-') {
                tokeniser.emit(current);
                tokeniser.advanceTransition(ScriptDataDoubleEscapedDash);
            } else if (current == '<') {
                tokeniser.emit(current);
                tokeniser.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
            } else if (current != 65535) {
                tokeniser.emit(characterReader.consumeToAny('-', '<', TokeniserState.nullChar));
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    ScriptDataDoubleEscapedDash { // from class: org.jsoup.parser.TokeniserState.30
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.emit(TokeniserState.replacementChar);
                tokeniser.transition(ScriptDataDoubleEscaped);
            } else if (consume == '-') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataDoubleEscapedDashDash);
            } else if (consume == '<') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
            } else if (consume != 65535) {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataDoubleEscaped);
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    ScriptDataDoubleEscapedDashDash { // from class: org.jsoup.parser.TokeniserState.31
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.emit(TokeniserState.replacementChar);
                tokeniser.transition(ScriptDataDoubleEscaped);
            } else if (consume == '-') {
                tokeniser.emit(consume);
            } else if (consume == '<') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
            } else if (consume == '>') {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptData);
            } else if (consume != 65535) {
                tokeniser.emit(consume);
                tokeniser.transition(ScriptDataDoubleEscaped);
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    ScriptDataDoubleEscapedLessthanSign { // from class: org.jsoup.parser.TokeniserState.32
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.emit('/');
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataDoubleEscapeEnd);
                return;
            }
            tokeniser.transition(ScriptDataDoubleEscaped);
        }
    },
    ScriptDataDoubleEscapeEnd { // from class: org.jsoup.parser.TokeniserState.33
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataEscaped, ScriptDataDoubleEscaped);
        }
    },
    BeforeAttributeName { // from class: org.jsoup.parser.TokeniserState.34
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                case '\'':
                case '<':
                case '=':
                    tokeniser.error(this);
                    tokeniser.tagPending.newAttribute();
                    tokeniser.tagPending.appendAttributeName(consume);
                    tokeniser.transition(AttributeName);
                    return;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                    return;
            }
        }
    },
    AttributeName { // from class: org.jsoup.parser.TokeniserState.35
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.tagPending.appendAttributeName(characterReader.consumeToAnySorted(attributeNameCharsSorted));
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeName(TokeniserState.replacementChar);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(AfterAttributeName);
                    return;
                case '\"':
                case '\'':
                case '<':
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeName(consume);
                    return;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                case '=':
                    tokeniser.transition(BeforeAttributeValue);
                    return;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.tagPending.appendAttributeName(consume);
                    return;
            }
        }
    },
    AfterAttributeName { // from class: org.jsoup.parser.TokeniserState.36
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeName(TokeniserState.replacementChar);
                    tokeniser.transition(AttributeName);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                case '\'':
                case '<':
                    tokeniser.error(this);
                    tokeniser.tagPending.newAttribute();
                    tokeniser.tagPending.appendAttributeName(consume);
                    tokeniser.transition(AttributeName);
                    return;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                case '=':
                    tokeniser.transition(BeforeAttributeValue);
                    return;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                    return;
            }
        }
    },
    BeforeAttributeValue { // from class: org.jsoup.parser.TokeniserState.37
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue(TokeniserState.replacementChar);
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                    tokeniser.transition(AttributeValue_doubleQuoted);
                    return;
                case '&':
                    characterReader.unconsume();
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
                case '\'':
                    tokeniser.transition(AttributeValue_singleQuoted);
                    return;
                case '<':
                case '=':
                case '`':
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue(consume);
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
                case '>':
                    tokeniser.error(this);
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                default:
                    characterReader.unconsume();
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
            }
        }
    },
    AttributeValue_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.38
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String consumeToAny = characterReader.consumeToAny(attributeDoubleValueCharsSorted);
            if (consumeToAny.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(consumeToAny);
            } else {
                tokeniser.tagPending.setEmptyAttributeValue();
            }
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue(TokeniserState.replacementChar);
            } else if (consume == '\"') {
                tokeniser.transition(AfterAttributeValue_quoted);
            } else if (consume == '&') {
                int[] consumeCharacterReference = tokeniser.consumeCharacterReference(Character.valueOf(TokenParser.DQUOTE), true);
                if (consumeCharacterReference != null) {
                    tokeniser.tagPending.appendAttributeValue(consumeCharacterReference);
                } else {
                    tokeniser.tagPending.appendAttributeValue('&');
                }
            } else if (consume != 65535) {
                tokeniser.tagPending.appendAttributeValue(consume);
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    AttributeValue_singleQuoted { // from class: org.jsoup.parser.TokeniserState.39
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String consumeToAny = characterReader.consumeToAny(attributeSingleValueCharsSorted);
            if (consumeToAny.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(consumeToAny);
            } else {
                tokeniser.tagPending.setEmptyAttributeValue();
            }
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.tagPending.appendAttributeValue(TokeniserState.replacementChar);
            } else if (consume != 65535) {
                switch (consume) {
                    case '&':
                        int[] consumeCharacterReference = tokeniser.consumeCharacterReference('\'', true);
                        if (consumeCharacterReference != null) {
                            tokeniser.tagPending.appendAttributeValue(consumeCharacterReference);
                            return;
                        } else {
                            tokeniser.tagPending.appendAttributeValue('&');
                            return;
                        }
                    case '\'':
                        tokeniser.transition(AfterAttributeValue_quoted);
                        return;
                    default:
                        tokeniser.tagPending.appendAttributeValue(consume);
                        return;
                }
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    AttributeValue_unquoted { // from class: org.jsoup.parser.TokeniserState.40
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String consumeToAnySorted = characterReader.consumeToAnySorted(attributeValueUnquoted);
            if (consumeToAnySorted.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(consumeToAnySorted);
            }
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue(TokeniserState.replacementChar);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeAttributeName);
                    return;
                case '\"':
                case '\'':
                case '<':
                case '=':
                case '`':
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue(consume);
                    return;
                case '&':
                    int[] consumeCharacterReference = tokeniser.consumeCharacterReference('>', true);
                    if (consumeCharacterReference != null) {
                        tokeniser.tagPending.appendAttributeValue(consumeCharacterReference);
                        return;
                    } else {
                        tokeniser.tagPending.appendAttributeValue('&');
                        return;
                    }
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.tagPending.appendAttributeValue(consume);
                    return;
            }
        }
    },
    AfterAttributeValue_quoted { // from class: org.jsoup.parser.TokeniserState.41
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeAttributeName);
                    return;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    characterReader.unconsume();
                    tokeniser.transition(BeforeAttributeName);
                    return;
            }
        }
    },
    SelfClosingStartTag { // from class: org.jsoup.parser.TokeniserState.42
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == '>') {
                tokeniser.tagPending.selfClosing = true;
                tokeniser.emitTagPending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.error(this);
                characterReader.unconsume();
                tokeniser.transition(BeforeAttributeName);
            } else {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
            }
        }
    },
    BogusComment { // from class: org.jsoup.parser.TokeniserState.43
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            characterReader.unconsume();
            Token.Comment comment = new Token.Comment();
            comment.bogus = true;
            comment.data.append(characterReader.consumeTo('>'));
            tokeniser.emit(comment);
            tokeniser.advanceTransition(Data);
        }
    },
    MarkupDeclarationOpen { // from class: org.jsoup.parser.TokeniserState.44
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchConsume("--")) {
                tokeniser.createCommentPending();
                tokeniser.transition(CommentStart);
            } else if (characterReader.matchConsumeIgnoreCase("DOCTYPE")) {
                tokeniser.transition(Doctype);
            } else if (characterReader.matchConsume("[CDATA[")) {
                tokeniser.createTempBuffer();
                tokeniser.transition(CdataSection);
            } else {
                tokeniser.error(this);
                tokeniser.advanceTransition(BogusComment);
            }
        }
    },
    CommentStart { // from class: org.jsoup.parser.TokeniserState.45
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.data.append(TokeniserState.replacementChar);
                tokeniser.transition(Comment);
            } else if (consume == '-') {
                tokeniser.transition(CommentStartDash);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.commentPending.data.append(consume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    CommentStartDash { // from class: org.jsoup.parser.TokeniserState.46
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.commentPending.data.append(TokeniserState.replacementChar);
                tokeniser.transition(Comment);
            } else if (consume == '-') {
                tokeniser.transition(CommentStartDash);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.commentPending.data.append(consume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    Comment { // from class: org.jsoup.parser.TokeniserState.47
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char current = characterReader.current();
            if (current == 0) {
                tokeniser.error(this);
                characterReader.advance();
                tokeniser.commentPending.data.append(TokeniserState.replacementChar);
            } else if (current == '-') {
                tokeniser.advanceTransition(CommentEndDash);
            } else if (current != 65535) {
                tokeniser.commentPending.data.append(characterReader.consumeToAny('-', TokeniserState.nullChar));
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    CommentEndDash { // from class: org.jsoup.parser.TokeniserState.48
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                StringBuilder sb = tokeniser.commentPending.data;
                sb.append('-');
                sb.append(TokeniserState.replacementChar);
                tokeniser.transition(Comment);
            } else if (consume == '-') {
                tokeniser.transition(CommentEnd);
            } else if (consume != 65535) {
                StringBuilder sb2 = tokeniser.commentPending.data;
                sb2.append('-');
                sb2.append(consume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    CommentEnd { // from class: org.jsoup.parser.TokeniserState.49
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                StringBuilder sb = tokeniser.commentPending.data;
                sb.append("--");
                sb.append(TokeniserState.replacementChar);
                tokeniser.transition(Comment);
            } else if (consume == '!') {
                tokeniser.error(this);
                tokeniser.transition(CommentEndBang);
            } else if (consume == '-') {
                tokeniser.error(this);
                tokeniser.commentPending.data.append('-');
            } else if (consume == '>') {
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.error(this);
                StringBuilder sb2 = tokeniser.commentPending.data;
                sb2.append("--");
                sb2.append(consume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    CommentEndBang { // from class: org.jsoup.parser.TokeniserState.50
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                StringBuilder sb = tokeniser.commentPending.data;
                sb.append("--!");
                sb.append(TokeniserState.replacementChar);
                tokeniser.transition(Comment);
            } else if (consume == '-') {
                tokeniser.commentPending.data.append("--!");
                tokeniser.transition(CommentEndDash);
            } else if (consume == '>') {
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                StringBuilder sb2 = tokeniser.commentPending.data;
                sb2.append("--!");
                sb2.append(consume);
                tokeniser.transition(Comment);
            } else {
                tokeniser.eofError(this);
                tokeniser.emitCommentPending();
                tokeniser.transition(Data);
            }
        }
    },
    Doctype { // from class: org.jsoup.parser.TokeniserState.51
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeDoctypeName);
                    return;
                case '>':
                    break;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    break;
                default:
                    tokeniser.error(this);
                    tokeniser.transition(BeforeDoctypeName);
                    return;
            }
            tokeniser.error(this);
            tokeniser.createDoctypePending();
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    },
    BeforeDoctypeName { // from class: org.jsoup.parser.TokeniserState.52
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createDoctypePending();
                tokeniser.transition(DoctypeName);
                return;
            }
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.createDoctypePending();
                    tokeniser.doctypePending.name.append(TokeniserState.replacementChar);
                    tokeniser.transition(DoctypeName);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.createDoctypePending();
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.createDoctypePending();
                    tokeniser.doctypePending.name.append(consume);
                    tokeniser.transition(DoctypeName);
                    return;
            }
        }
    },
    DoctypeName { // from class: org.jsoup.parser.TokeniserState.53
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.doctypePending.name.append(characterReader.consumeLetterSequence());
                return;
            }
            char consume = characterReader.consume();
            switch (consume) {
                case 0:
                    tokeniser.error(this);
                    tokeniser.doctypePending.name.append(TokeniserState.replacementChar);
                    return;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(AfterDoctypeName);
                    return;
                case '>':
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.doctypePending.name.append(consume);
                    return;
            }
        }
    },
    AfterDoctypeName { // from class: org.jsoup.parser.TokeniserState.54
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (characterReader.matchesAny('\t', '\n', TokenParser.CR, '\f', TokenParser.SP)) {
                characterReader.advance();
            } else if (characterReader.matches('>')) {
                tokeniser.emitDoctypePending();
                tokeniser.advanceTransition(Data);
            } else if (characterReader.matchConsumeIgnoreCase(DocumentType.PUBLIC_KEY)) {
                tokeniser.doctypePending.pubSysKey = DocumentType.PUBLIC_KEY;
                tokeniser.transition(AfterDoctypePublicKeyword);
            } else if (characterReader.matchConsumeIgnoreCase(DocumentType.SYSTEM_KEY)) {
                tokeniser.doctypePending.pubSysKey = DocumentType.SYSTEM_KEY;
                tokeniser.transition(AfterDoctypeSystemKeyword);
            } else {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.advanceTransition(BogusDoctype);
            }
        }
    },
    AfterDoctypePublicKeyword { // from class: org.jsoup.parser.TokeniserState.55
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeDoctypePublicIdentifier);
                    return;
                case '\"':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    BeforeDoctypePublicIdentifier { // from class: org.jsoup.parser.TokeniserState.56
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                    tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    DoctypePublicIdentifier_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.57
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
            } else if (consume == '\"') {
                tokeniser.transition(AfterDoctypePublicIdentifier);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.doctypePending.publicIdentifier.append(consume);
            } else {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    DoctypePublicIdentifier_singleQuoted { // from class: org.jsoup.parser.TokeniserState.58
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
            } else if (consume == '\'') {
                tokeniser.transition(AfterDoctypePublicIdentifier);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.doctypePending.publicIdentifier.append(consume);
            } else {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    AfterDoctypePublicIdentifier { // from class: org.jsoup.parser.TokeniserState.59
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BetweenDoctypePublicAndSystemIdentifiers);
                    return;
                case '\"':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    BetweenDoctypePublicAndSystemIdentifiers { // from class: org.jsoup.parser.TokeniserState.60
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    AfterDoctypeSystemKeyword { // from class: org.jsoup.parser.TokeniserState.61
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeDoctypeSystemIdentifier);
                    return;
                case '\"':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    return;
            }
        }
    },
    BeforeDoctypeSystemIdentifier { // from class: org.jsoup.parser.TokeniserState.62
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '\"':
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    DoctypeSystemIdentifier_doubleQuoted { // from class: org.jsoup.parser.TokeniserState.63
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
            } else if (consume == '\"') {
                tokeniser.transition(AfterDoctypeSystemIdentifier);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.doctypePending.systemIdentifier.append(consume);
            } else {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    DoctypeSystemIdentifier_singleQuoted { // from class: org.jsoup.parser.TokeniserState.64
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == 0) {
                tokeniser.error(this);
                tokeniser.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
            } else if (consume == '\'') {
                tokeniser.transition(AfterDoctypeSystemIdentifier);
            } else if (consume == '>') {
                tokeniser.error(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (consume != 65535) {
                tokeniser.doctypePending.systemIdentifier.append(consume);
            } else {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    AfterDoctypeSystemIdentifier { // from class: org.jsoup.parser.TokeniserState.65
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    return;
                case '>':
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                case SupportMenu.USER_MASK /* 65535 */:
                    tokeniser.eofError(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                default:
                    tokeniser.error(this);
                    tokeniser.transition(BogusDoctype);
                    return;
            }
        }
    },
    BogusDoctype { // from class: org.jsoup.parser.TokeniserState.66
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char consume = characterReader.consume();
            if (consume == '>') {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            } else if (consume == 65535) {
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
            }
        }
    },
    CdataSection { // from class: org.jsoup.parser.TokeniserState.67
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.TokeniserState
        public void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.dataBuffer.append(characterReader.consumeTo("]]>"));
            if (characterReader.matchConsume("]]>") || characterReader.isEmpty()) {
                tokeniser.emit(new Token.CData(tokeniser.dataBuffer.toString()));
                tokeniser.transition(Data);
            }
        }
    };
    
    private static final char eof = 65535;
    static final char nullChar = 0;
    static final char[] attributeSingleValueCharsSorted = {nullChar, '&', '\''};
    static final char[] attributeDoubleValueCharsSorted = {nullChar, TokenParser.DQUOTE, '&'};
    static final char[] attributeNameCharsSorted = {nullChar, '\t', '\n', '\f', TokenParser.CR, TokenParser.SP, TokenParser.DQUOTE, '\'', '/', '<', '=', '>'};
    static final char[] attributeValueUnquoted = {nullChar, '\t', '\n', '\f', TokenParser.CR, TokenParser.SP, TokenParser.DQUOTE, '&', '\'', '<', '=', '>', '`'};
    private static final char replacementChar = 65533;
    private static final String replacementStr = String.valueOf((char) replacementChar);

    public abstract void read(Tokeniser tokeniser, CharacterReader characterReader);

    public static void handleDataEndTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState) {
        if (characterReader.matchesLetter()) {
            String consumeLetterSequence = characterReader.consumeLetterSequence();
            tokeniser.tagPending.appendTagName(consumeLetterSequence);
            tokeniser.dataBuffer.append(consumeLetterSequence);
            return;
        }
        boolean z = true;
        if (tokeniser.isAppropriateEndTagToken() && !characterReader.isEmpty()) {
            char consume = characterReader.consume();
            switch (consume) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    tokeniser.transition(BeforeAttributeName);
                    z = false;
                    break;
                case '/':
                    tokeniser.transition(SelfClosingStartTag);
                    z = false;
                    break;
                case '>':
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    z = false;
                    break;
                default:
                    tokeniser.dataBuffer.append(consume);
                    break;
            }
        }
        if (z) {
            tokeniser.emit("</" + tokeniser.dataBuffer.toString());
            tokeniser.transition(tokeniserState);
        }
    }

    public static void readData(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        char current = characterReader.current();
        if (current == 0) {
            tokeniser.error(tokeniserState);
            characterReader.advance();
            tokeniser.emit(replacementChar);
        } else if (current == '<') {
            tokeniser.advanceTransition(tokeniserState2);
        } else if (current != 65535) {
            tokeniser.emit(characterReader.consumeToAny('<', nullChar));
        } else {
            tokeniser.emit(new Token.EOF());
        }
    }

    public static void readCharRef(Tokeniser tokeniser, TokeniserState tokeniserState) {
        int[] consumeCharacterReference = tokeniser.consumeCharacterReference(null, false);
        if (consumeCharacterReference == null) {
            tokeniser.emit('&');
        } else {
            tokeniser.emit(consumeCharacterReference);
        }
        tokeniser.transition(tokeniserState);
    }

    public static void readEndTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        if (characterReader.matchesLetter()) {
            tokeniser.createTagPending(false);
            tokeniser.transition(tokeniserState);
            return;
        }
        tokeniser.emit("</");
        tokeniser.transition(tokeniserState2);
    }

    public static void handleDataDoubleEscapeTag(Tokeniser tokeniser, CharacterReader characterReader, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        if (characterReader.matchesLetter()) {
            String consumeLetterSequence = characterReader.consumeLetterSequence();
            tokeniser.dataBuffer.append(consumeLetterSequence);
            tokeniser.emit(consumeLetterSequence);
            return;
        }
        char consume = characterReader.consume();
        switch (consume) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case '/':
            case '>':
                if (tokeniser.dataBuffer.toString().equals("script")) {
                    tokeniser.transition(tokeniserState);
                } else {
                    tokeniser.transition(tokeniserState2);
                }
                tokeniser.emit(consume);
                return;
            default:
                characterReader.unconsume();
                tokeniser.transition(tokeniserState2);
                return;
        }
    }
}
