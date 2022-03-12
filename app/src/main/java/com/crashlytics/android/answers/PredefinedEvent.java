package com.crashlytics.android.answers;

import com.crashlytics.android.answers.PredefinedEvent;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class PredefinedEvent<T extends PredefinedEvent> extends AnswersEvent<T> {
    final AnswersAttributes predefinedAttributes = new AnswersAttributes(this.validator);

    public abstract String getPredefinedType();

    public Map<String, Object> getPredefinedAttributes() {
        return this.predefinedAttributes.attributes;
    }

    public String toString() {
        return "{type:\"" + getPredefinedType() + TokenParser.DQUOTE + ", predefinedAttributes:" + this.predefinedAttributes + ", customAttributes:" + this.customAttributes + "}";
    }
}
