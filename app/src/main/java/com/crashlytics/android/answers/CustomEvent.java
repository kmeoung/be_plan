package com.crashlytics.android.answers;

import cz.msebera.android.httpclient.message.TokenParser;

/* loaded from: classes.dex */
public class CustomEvent extends AnswersEvent<CustomEvent> {
    private final String eventName;

    public CustomEvent(String str) {
        if (str == null) {
            throw new NullPointerException("eventName must not be null");
        }
        this.eventName = this.validator.limitStringLength(str);
    }

    public String getCustomType() {
        return this.eventName;
    }

    public String toString() {
        return "{eventName:\"" + this.eventName + TokenParser.DQUOTE + ", customAttributes:" + this.customAttributes + "}";
    }
}
