package org.jsoup.parser;

import com.naver.temy123.baseproject.base.Utils.HW_Params;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import io.realm.BuildConfig;
import java.util.ArrayList;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Token;

/* loaded from: classes.dex */
public enum HtmlTreeBuilderState {
    Initial { // from class: org.jsoup.parser.HtmlTreeBuilderState.1
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                Token.Doctype asDoctype = token.asDoctype();
                DocumentType documentType = new DocumentType(htmlTreeBuilder.settings.normalizeTag(asDoctype.getName()), asDoctype.getPublicIdentifier(), asDoctype.getSystemIdentifier());
                documentType.setPubSysKey(asDoctype.getPubSysKey());
                htmlTreeBuilder.getDocument().appendChild(documentType);
                if (asDoctype.isForceQuirks()) {
                    htmlTreeBuilder.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                htmlTreeBuilder.transition(BeforeHtml);
            } else {
                htmlTreeBuilder.transition(BeforeHtml);
                return htmlTreeBuilder.process(token);
            }
            return true;
        }
    },
    BeforeHtml { // from class: org.jsoup.parser.HtmlTreeBuilderState.2
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            } else {
                if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                    htmlTreeBuilder.insert(token.asStartTag());
                    htmlTreeBuilder.transition(BeforeHead);
                } else if (token.isEndTag() && StringUtil.in(token.asEndTag().normalName(), "head", HW_Params.HW_NETWORK_EXTRA_BODY, "html", "br")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    if (!token.isEndTag()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
            return true;
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.insertStartTag("html");
            htmlTreeBuilder.transition(BeforeHead);
            return htmlTreeBuilder.process(token);
        }
    },
    BeforeHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.3
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return InBody.process(token, htmlTreeBuilder);
            } else {
                if (token.isStartTag() && token.asStartTag().normalName().equals("head")) {
                    htmlTreeBuilder.setHeadElement(htmlTreeBuilder.insert(token.asStartTag()));
                    htmlTreeBuilder.transition(InHead);
                } else if (token.isEndTag() && StringUtil.in(token.asEndTag().normalName(), "head", HW_Params.HW_NETWORK_EXTRA_BODY, "html", "br")) {
                    htmlTreeBuilder.processStartTag("head");
                    return htmlTreeBuilder.process(token);
                } else if (token.isEndTag()) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    htmlTreeBuilder.processStartTag("head");
                    return htmlTreeBuilder.process(token);
                }
            }
            return true;
        }
    },
    InHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.4
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            switch (token.type) {
                case Comment:
                    htmlTreeBuilder.insert(token.asComment());
                    break;
                case Doctype:
                    htmlTreeBuilder.error(this);
                    return false;
                case StartTag:
                    Token.StartTag asStartTag = token.asStartTag();
                    String normalName = asStartTag.normalName();
                    if (normalName.equals("html")) {
                        return InBody.process(token, htmlTreeBuilder);
                    }
                    if (StringUtil.in(normalName, BuildConfig.FLAVOR, "basefont", "bgsound", "command", "link")) {
                        Element insertEmpty = htmlTreeBuilder.insertEmpty(asStartTag);
                        if (normalName.equals(BuildConfig.FLAVOR) && insertEmpty.hasAttr("href")) {
                            htmlTreeBuilder.maybeSetBaseUri(insertEmpty);
                            break;
                        }
                    } else if (normalName.equals("meta")) {
                        htmlTreeBuilder.insertEmpty(asStartTag);
                        break;
                    } else if (normalName.equals(SettingsJsonConstants.PROMPT_TITLE_KEY)) {
                        HtmlTreeBuilderState.handleRcData(asStartTag, htmlTreeBuilder);
                        break;
                    } else if (StringUtil.in(normalName, "noframes", "style")) {
                        HtmlTreeBuilderState.handleRawtext(asStartTag, htmlTreeBuilder);
                        break;
                    } else if (normalName.equals("noscript")) {
                        htmlTreeBuilder.insert(asStartTag);
                        htmlTreeBuilder.transition(InHeadNoscript);
                        break;
                    } else if (normalName.equals("script")) {
                        htmlTreeBuilder.tokeniser.transition(TokeniserState.ScriptData);
                        htmlTreeBuilder.markInsertionMode();
                        htmlTreeBuilder.transition(Text);
                        htmlTreeBuilder.insert(asStartTag);
                        break;
                    } else if (!normalName.equals("head")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    break;
                case EndTag:
                    String normalName2 = token.asEndTag().normalName();
                    if (normalName2.equals("head")) {
                        htmlTreeBuilder.pop();
                        htmlTreeBuilder.transition(AfterHead);
                        break;
                    } else if (StringUtil.in(normalName2, HW_Params.HW_NETWORK_EXTRA_BODY, "html", "br")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                default:
                    return anythingElse(token, htmlTreeBuilder);
            }
            return true;
        }

        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            treeBuilder.processEndTag("head");
            return treeBuilder.process(token);
        }
    },
    InHeadNoscript { // from class: org.jsoup.parser.HtmlTreeBuilderState.5
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            } else {
                if (token.isEndTag() && token.asEndTag().normalName().equals("noscript")) {
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(InHead);
                } else if (HtmlTreeBuilderState.isWhitespace(token) || token.isComment() || (token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "basefont", "bgsound", "link", "meta", "noframes", "style"))) {
                    return htmlTreeBuilder.process(token, InHead);
                } else {
                    if (token.isEndTag() && token.asEndTag().normalName().equals("br")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if ((!token.isStartTag() || !StringUtil.in(token.asStartTag().normalName(), "head", "noscript")) && !token.isEndTag()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
            return true;
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.insert(new Token.Character().data(token.toString()));
            return true;
        }
    },
    AfterHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.6
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
            } else if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("html")) {
                    return htmlTreeBuilder.process(token, InBody);
                }
                if (normalName.equals(HW_Params.HW_NETWORK_EXTRA_BODY)) {
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.framesetOk(false);
                    htmlTreeBuilder.transition(InBody);
                } else if (normalName.equals("frameset")) {
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(InFrameset);
                } else if (StringUtil.in(normalName, BuildConfig.FLAVOR, "basefont", "bgsound", "link", "meta", "noframes", "script", "style", SettingsJsonConstants.PROMPT_TITLE_KEY)) {
                    htmlTreeBuilder.error(this);
                    Element headElement = htmlTreeBuilder.getHeadElement();
                    htmlTreeBuilder.push(headElement);
                    htmlTreeBuilder.process(token, InHead);
                    htmlTreeBuilder.removeFromStack(headElement);
                } else if (normalName.equals("head")) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    anythingElse(token, htmlTreeBuilder);
                }
            } else if (!token.isEndTag()) {
                anythingElse(token, htmlTreeBuilder);
            } else if (StringUtil.in(token.asEndTag().normalName(), HW_Params.HW_NETWORK_EXTRA_BODY, "html")) {
                anythingElse(token, htmlTreeBuilder);
            } else {
                htmlTreeBuilder.error(this);
                return false;
            }
            return true;
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.processStartTag(HW_Params.HW_NETWORK_EXTRA_BODY);
            htmlTreeBuilder.framesetOk(true);
            return htmlTreeBuilder.process(token);
        }
    },
    InBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.7
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Removed duplicated region for block: B:433:0x00c3 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:50:0x00ce  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0122  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x012f  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0167 A[LOOP:3: B:76:0x0165->B:77:0x0167, LOOP_END] */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean process(org.jsoup.parser.Token r18, org.jsoup.parser.HtmlTreeBuilder r19) {
            /*
                Method dump skipped, instructions count: 2298
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass7.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        boolean anyOtherEndTag(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            String normalizeTag = htmlTreeBuilder.settings.normalizeTag(token.asEndTag().name());
            ArrayList<Element> stack = htmlTreeBuilder.getStack();
            int size = stack.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                Element element = stack.get(size);
                if (element.nodeName().equals(normalizeTag)) {
                    htmlTreeBuilder.generateImpliedEndTags(normalizeTag);
                    if (!normalizeTag.equals(htmlTreeBuilder.currentElement().nodeName())) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(normalizeTag);
                } else if (htmlTreeBuilder.isSpecial(element)) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    size--;
                }
            }
            return true;
        }
    },
    Text { // from class: org.jsoup.parser.HtmlTreeBuilderState.8
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            } else if (token.isEOF()) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            } else if (!token.isEndTag()) {
                return true;
            } else {
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return true;
            }
        }
    },
    InTable { // from class: org.jsoup.parser.HtmlTreeBuilderState.9
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.newPendingTableCharacters();
                htmlTreeBuilder.markInsertionMode();
                htmlTreeBuilder.transition(InTableText);
                return htmlTreeBuilder.process(token);
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("caption")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(InCaption);
                } else if (normalName.equals("colgroup")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(InColumnGroup);
                } else if (normalName.equals("col")) {
                    htmlTreeBuilder.processStartTag("colgroup");
                    return htmlTreeBuilder.process(token);
                } else if (StringUtil.in(normalName, "tbody", "tfoot", "thead")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(InTableBody);
                } else if (StringUtil.in(normalName, "td", "th", "tr")) {
                    htmlTreeBuilder.processStartTag("tbody");
                    return htmlTreeBuilder.process(token);
                } else if (normalName.equals("table")) {
                    htmlTreeBuilder.error(this);
                    if (htmlTreeBuilder.processEndTag("table")) {
                        return htmlTreeBuilder.process(token);
                    }
                } else if (StringUtil.in(normalName, "style", "script")) {
                    return htmlTreeBuilder.process(token, InHead);
                } else {
                    if (normalName.equals("input")) {
                        if (!asStartTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                            return anythingElse(token, htmlTreeBuilder);
                        }
                        htmlTreeBuilder.insertEmpty(asStartTag);
                    } else if (!normalName.equals("form")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder.error(this);
                        if (htmlTreeBuilder.getFormElement() != null) {
                            return false;
                        }
                        htmlTreeBuilder.insertForm(asStartTag, false);
                    }
                }
                return true;
            } else if (token.isEndTag()) {
                String normalName2 = token.asEndTag().normalName();
                if (normalName2.equals("table")) {
                    if (!htmlTreeBuilder.inTableScope(normalName2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.popStackToClose("table");
                    htmlTreeBuilder.resetInsertionMode();
                    return true;
                } else if (!StringUtil.in(normalName2, HW_Params.HW_NETWORK_EXTRA_BODY, "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            } else if (!token.isEOF()) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                }
                return true;
            }
        }

        boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            if (!StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return htmlTreeBuilder.process(token, InBody);
            }
            htmlTreeBuilder.setFosterInserts(true);
            boolean process = htmlTreeBuilder.process(token, InBody);
            htmlTreeBuilder.setFosterInserts(false);
            return process;
        }
    },
    InTableText { // from class: org.jsoup.parser.HtmlTreeBuilderState.10
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()] != 5) {
                if (htmlTreeBuilder.getPendingTableCharacters().size() > 0) {
                    for (String str : htmlTreeBuilder.getPendingTableCharacters()) {
                        if (!HtmlTreeBuilderState.isWhitespace(str)) {
                            htmlTreeBuilder.error(this);
                            if (StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                htmlTreeBuilder.setFosterInserts(true);
                                htmlTreeBuilder.process(new Token.Character().data(str), InBody);
                                htmlTreeBuilder.setFosterInserts(false);
                            } else {
                                htmlTreeBuilder.process(new Token.Character().data(str), InBody);
                            }
                        } else {
                            htmlTreeBuilder.insert(new Token.Character().data(str));
                        }
                    }
                    htmlTreeBuilder.newPendingTableCharacters();
                }
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            }
            Token.Character asCharacter = token.asCharacter();
            if (asCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.getPendingTableCharacters().add(asCharacter.getData());
            return true;
        }
    },
    InCaption { // from class: org.jsoup.parser.HtmlTreeBuilderState.11
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (!token.isEndTag() || !token.asEndTag().normalName().equals("caption")) {
                if ((token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) || (token.isEndTag() && token.asEndTag().normalName().equals("table"))) {
                    htmlTreeBuilder.error(this);
                    if (htmlTreeBuilder.processEndTag("caption")) {
                        return htmlTreeBuilder.process(token);
                    }
                } else if (!token.isEndTag() || !StringUtil.in(token.asEndTag().normalName(), HW_Params.HW_NETWORK_EXTRA_BODY, "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return htmlTreeBuilder.process(token, InBody);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            } else if (!htmlTreeBuilder.inTableScope(token.asEndTag().normalName())) {
                htmlTreeBuilder.error(this);
                return false;
            } else {
                htmlTreeBuilder.generateImpliedEndTags();
                if (!htmlTreeBuilder.currentElement().nodeName().equals("caption")) {
                    htmlTreeBuilder.error(this);
                }
                htmlTreeBuilder.popStackToClose("caption");
                htmlTreeBuilder.clearFormattingElementsToLastMarker();
                htmlTreeBuilder.transition(InTable);
            }
            return true;
        }
    },
    InColumnGroup { // from class: org.jsoup.parser.HtmlTreeBuilderState.12
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0073, code lost:
            if (r3.equals("html") == false) goto L_0x0080;
         */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x008d  */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean process(org.jsoup.parser.Token r8, org.jsoup.parser.HtmlTreeBuilder r9) {
            /*
                r7 = this;
                boolean r0 = org.jsoup.parser.HtmlTreeBuilderState.access$100(r8)
                r1 = 1
                if (r0 == 0) goto L_0x000f
                org.jsoup.parser.Token$Character r8 = r8.asCharacter()
                r9.insert(r8)
                return r1
            L_0x000f:
                int[] r0 = org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType
                org.jsoup.parser.Token$TokenType r2 = r8.type
                int r2 = r2.ordinal()
                r0 = r0[r2]
                r2 = 6
                if (r0 == r2) goto L_0x00a0
                r2 = 0
                switch(r0) {
                    case 1: goto L_0x0098;
                    case 2: goto L_0x0094;
                    case 3: goto L_0x0055;
                    case 4: goto L_0x0025;
                    default: goto L_0x0020;
                }
            L_0x0020:
                boolean r8 = r7.anythingElse(r8, r9)
                return r8
            L_0x0025:
                org.jsoup.parser.Token$EndTag r0 = r8.asEndTag()
                java.lang.String r0 = r0.normalName
                java.lang.String r3 = "colgroup"
                boolean r0 = r0.equals(r3)
                if (r0 == 0) goto L_0x0050
                org.jsoup.nodes.Element r8 = r9.currentElement()
                java.lang.String r8 = r8.nodeName()
                java.lang.String r0 = "html"
                boolean r8 = r8.equals(r0)
                if (r8 == 0) goto L_0x0047
                r9.error(r7)
                return r2
            L_0x0047:
                r9.pop()
                org.jsoup.parser.HtmlTreeBuilderState r8 = org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass12.InTable
                r9.transition(r8)
                goto L_0x009f
            L_0x0050:
                boolean r8 = r7.anythingElse(r8, r9)
                return r8
            L_0x0055:
                org.jsoup.parser.Token$StartTag r0 = r8.asStartTag()
                java.lang.String r3 = r0.normalName()
                r4 = -1
                int r5 = r3.hashCode()
                r6 = 98688(0x18180, float:1.38291E-40)
                if (r5 == r6) goto L_0x0076
                r6 = 3213227(0x3107ab, float:4.50269E-39)
                if (r5 == r6) goto L_0x006d
                goto L_0x0080
            L_0x006d:
                java.lang.String r5 = "html"
                boolean r3 = r3.equals(r5)
                if (r3 == 0) goto L_0x0080
                goto L_0x0081
            L_0x0076:
                java.lang.String r2 = "col"
                boolean r2 = r3.equals(r2)
                if (r2 == 0) goto L_0x0080
                r2 = 1
                goto L_0x0081
            L_0x0080:
                r2 = -1
            L_0x0081:
                switch(r2) {
                    case 0: goto L_0x008d;
                    case 1: goto L_0x0089;
                    default: goto L_0x0084;
                }
            L_0x0084:
                boolean r8 = r7.anythingElse(r8, r9)
                return r8
            L_0x0089:
                r9.insertEmpty(r0)
                goto L_0x009f
            L_0x008d:
                org.jsoup.parser.HtmlTreeBuilderState r0 = org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass12.InBody
                boolean r8 = r9.process(r8, r0)
                return r8
            L_0x0094:
                r9.error(r7)
                goto L_0x009f
            L_0x0098:
                org.jsoup.parser.Token$Comment r8 = r8.asComment()
                r9.insert(r8)
            L_0x009f:
                return r1
            L_0x00a0:
                org.jsoup.nodes.Element r0 = r9.currentElement()
                java.lang.String r0 = r0.nodeName()
                java.lang.String r2 = "html"
                boolean r0 = r0.equals(r2)
                if (r0 == 0) goto L_0x00b1
                return r1
            L_0x00b1:
                boolean r8 = r7.anythingElse(r8, r9)
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass12.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.processEndTag("colgroup")) {
                return treeBuilder.process(token);
            }
            return true;
        }
    },
    InTableBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.13
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                case 3:
                    Token.StartTag asStartTag = token.asStartTag();
                    String normalName = asStartTag.normalName();
                    if (normalName.equals("template")) {
                        htmlTreeBuilder.insert(asStartTag);
                        break;
                    } else if (normalName.equals("tr")) {
                        htmlTreeBuilder.clearStackToTableBodyContext();
                        htmlTreeBuilder.insert(asStartTag);
                        htmlTreeBuilder.transition(InRow);
                        break;
                    } else if (StringUtil.in(normalName, "th", "td")) {
                        htmlTreeBuilder.error(this);
                        htmlTreeBuilder.processStartTag("tr");
                        return htmlTreeBuilder.process(asStartTag);
                    } else if (StringUtil.in(normalName, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                        return exitTableBody(token, htmlTreeBuilder);
                    } else {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                case 4:
                    String normalName2 = token.asEndTag().normalName();
                    if (StringUtil.in(normalName2, "tbody", "tfoot", "thead")) {
                        if (htmlTreeBuilder.inTableScope(normalName2)) {
                            htmlTreeBuilder.clearStackToTableBodyContext();
                            htmlTreeBuilder.pop();
                            htmlTreeBuilder.transition(InTable);
                            break;
                        } else {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                    } else if (normalName2.equals("table")) {
                        return exitTableBody(token, htmlTreeBuilder);
                    } else {
                        if (!StringUtil.in(normalName2, HW_Params.HW_NETWORK_EXTRA_BODY, "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                            return anythingElse(token, htmlTreeBuilder);
                        }
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                default:
                    return anythingElse(token, htmlTreeBuilder);
            }
            return true;
        }

        private boolean exitTableBody(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope("tbody") || htmlTreeBuilder.inTableScope("thead") || htmlTreeBuilder.inScope("tfoot")) {
                htmlTreeBuilder.clearStackToTableBodyContext();
                htmlTreeBuilder.processEndTag(htmlTreeBuilder.currentElement().nodeName());
                return htmlTreeBuilder.process(token);
            }
            htmlTreeBuilder.error(this);
            return false;
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InTable);
        }
    },
    InRow { // from class: org.jsoup.parser.HtmlTreeBuilderState.14
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("template")) {
                    htmlTreeBuilder.insert(asStartTag);
                } else if (StringUtil.in(normalName, "th", "td")) {
                    htmlTreeBuilder.clearStackToTableRowContext();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(InCell);
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                } else if (StringUtil.in(normalName, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                    return handleMissingTr(token, htmlTreeBuilder);
                } else {
                    return anythingElse(token, htmlTreeBuilder);
                }
            } else if (!token.isEndTag()) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                String normalName2 = token.asEndTag().normalName();
                if (normalName2.equals("tr")) {
                    if (!htmlTreeBuilder.inTableScope(normalName2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.clearStackToTableRowContext();
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(InTableBody);
                } else if (normalName2.equals("table")) {
                    return handleMissingTr(token, htmlTreeBuilder);
                } else {
                    if (StringUtil.in(normalName2, "tbody", "tfoot", "thead")) {
                        if (!htmlTreeBuilder.inTableScope(normalName2)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.processEndTag("tr");
                        return htmlTreeBuilder.process(token);
                    } else if (!StringUtil.in(normalName2, HW_Params.HW_NETWORK_EXTRA_BODY, "caption", "col", "colgroup", "html", "td", "th")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InTable);
        }

        private boolean handleMissingTr(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.processEndTag("tr")) {
                return treeBuilder.process(token);
            }
            return false;
        }
    },
    InCell { // from class: org.jsoup.parser.HtmlTreeBuilderState.15
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isEndTag()) {
                String normalName = token.asEndTag().normalName();
                if (StringUtil.in(normalName, "td", "th")) {
                    if (!htmlTreeBuilder.inTableScope(normalName)) {
                        htmlTreeBuilder.error(this);
                        htmlTreeBuilder.transition(InRow);
                        return false;
                    }
                    htmlTreeBuilder.generateImpliedEndTags();
                    if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName)) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(normalName);
                    htmlTreeBuilder.clearFormattingElementsToLastMarker();
                    htmlTreeBuilder.transition(InRow);
                    return true;
                } else if (StringUtil.in(normalName, HW_Params.HW_NETWORK_EXTRA_BODY, "caption", "col", "colgroup", "html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else if (!StringUtil.in(normalName, "table", "tbody", "tfoot", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    if (!htmlTreeBuilder.inTableScope(normalName)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
            } else if (!token.isStartTag() || !StringUtil.in(token.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                if (htmlTreeBuilder.inTableScope("td") || htmlTreeBuilder.inTableScope("th")) {
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InBody);
        }

        private void closeCell(HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope("td")) {
                htmlTreeBuilder.processEndTag("td");
            } else {
                htmlTreeBuilder.processEndTag("th");
            }
        }
    },
    InSelect { // from class: org.jsoup.parser.HtmlTreeBuilderState.16
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
            if (r0.equals("select") != false) goto L_0x0080;
         */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0083  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x009a  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00b4  */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean process(org.jsoup.parser.Token r8, org.jsoup.parser.HtmlTreeBuilder r9) {
            /*
                Method dump skipped, instructions count: 488
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass16.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    InSelectInTable { // from class: org.jsoup.parser.HtmlTreeBuilderState.17
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.processEndTag("select");
                return htmlTreeBuilder.process(token);
            } else if (!token.isEndTag() || !StringUtil.in(token.asEndTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                return htmlTreeBuilder.process(token, InSelect);
            } else {
                htmlTreeBuilder.error(this);
                if (!htmlTreeBuilder.inTableScope(token.asEndTag().normalName())) {
                    return false;
                }
                htmlTreeBuilder.processEndTag("select");
                return htmlTreeBuilder.process(token);
            }
        }
    },
    AfterBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.18
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            } else {
                if (!token.isEndTag() || !token.asEndTag().normalName().equals("html")) {
                    if (token.isEOF()) {
                        return true;
                    }
                    htmlTreeBuilder.error(this);
                    htmlTreeBuilder.transition(InBody);
                    return htmlTreeBuilder.process(token);
                } else if (htmlTreeBuilder.isFragmentParsing()) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    htmlTreeBuilder.transition(AfterAfterBody);
                    return true;
                }
            }
        }
    },
    InFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.19
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Removed duplicated region for block: B:36:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x008d  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean process(org.jsoup.parser.Token r7, org.jsoup.parser.HtmlTreeBuilder r8) {
            /*
                Method dump skipped, instructions count: 268
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass19.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }
    },
    AfterFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.20
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            } else {
                if (token.isEndTag() && token.asEndTag().normalName().equals("html")) {
                    htmlTreeBuilder.transition(AfterAfterFrameset);
                    return true;
                } else if (token.isStartTag() && token.asStartTag().normalName().equals("noframes")) {
                    return htmlTreeBuilder.process(token, InHead);
                } else {
                    if (token.isEOF()) {
                        return true;
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
        }
    },
    AfterAfterBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.21
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().normalName().equals("html"))) {
                return htmlTreeBuilder.process(token, InBody);
            } else {
                if (token.isEOF()) {
                    return true;
                }
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.transition(InBody);
                return htmlTreeBuilder.process(token);
            }
        }
    },
    AfterAfterFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.22
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().normalName().equals("html"))) {
                return htmlTreeBuilder.process(token, InBody);
            } else {
                if (token.isEOF()) {
                    return true;
                }
                if (token.isStartTag() && token.asStartTag().normalName().equals("noframes")) {
                    return htmlTreeBuilder.process(token, InHead);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
        }
    },
    ForeignContent { // from class: org.jsoup.parser.HtmlTreeBuilderState.23
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return true;
        }
    };
    
    private static String nullString = String.valueOf((char) 0);

    public abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);

    public static boolean isWhitespace(Token token) {
        if (token.isCharacter()) {
            return isWhitespace(token.asCharacter().getData());
        }
        return false;
    }

    public static boolean isWhitespace(String str) {
        return StringUtil.isBlank(str);
    }

    public static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
        htmlTreeBuilder.insert(startTag);
    }

    public static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rawtext);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
        htmlTreeBuilder.insert(startTag);
    }

    /* loaded from: classes.dex */
    static final class Constants {
        static final String[] InBodyStartToHead = {BuildConfig.FLAVOR, "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", SettingsJsonConstants.PROMPT_TITLE_KEY};
        static final String[] InBodyStartPClosers = {"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
        static final String[] Headings = {"h1", "h2", "h3", "h4", "h5", "h6"};
        static final String[] InBodyStartPreListing = {"listing", "pre"};
        static final String[] InBodyStartLiBreakers = {"address", "div", "p"};
        static final String[] DdDt = {"dd", "dt"};
        static final String[] Formatters = {"b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u"};
        static final String[] InBodyStartApplets = {"applet", "marquee", "object"};
        static final String[] InBodyStartEmptyFormatters = {"area", "br", "embed", "img", "keygen", "wbr"};
        static final String[] InBodyStartMedia = {"param", "source", "track"};
        static final String[] InBodyStartInputAttribs = {"action", "name", SettingsJsonConstants.PROMPT_KEY};
        static final String[] InBodyStartOptions = {"optgroup", "option"};
        static final String[] InBodyStartRuby = {"rp", "rt"};
        static final String[] InBodyStartDrop = {"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
        static final String[] InBodyEndClosers = {"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
        static final String[] InBodyEndAdoptionFormatters = {"a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"};
        static final String[] InBodyEndTableFosters = {"table", "tbody", "tfoot", "thead", "tr"};

        Constants() {
        }
    }
}
