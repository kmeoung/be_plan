package com.crashlytics.android.answers;

/* loaded from: classes.dex */
public class SignUpEvent extends PredefinedEvent<SignUpEvent> {
    static final String METHOD_ATTRIBUTE = "method";
    static final String SUCCESS_ATTRIBUTE = "success";
    static final String TYPE = "signUp";

    @Override // com.crashlytics.android.answers.PredefinedEvent
    public String getPredefinedType() {
        return TYPE;
    }

    public SignUpEvent putMethod(String str) {
        this.predefinedAttributes.put("method", str);
        return this;
    }

    public SignUpEvent putSuccess(boolean z) {
        this.predefinedAttributes.put(SUCCESS_ATTRIBUTE, Boolean.toString(z));
        return this;
    }
}
