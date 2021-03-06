package io.realm;

/* loaded from: classes.dex */
public enum Case {
    SENSITIVE(true),
    INSENSITIVE(false);
    
    private final boolean value;

    Case(boolean z) {
        this.value = z;
    }

    public boolean getValue() {
        return this.value;
    }
}
