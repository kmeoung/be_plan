package io.realm;

/* loaded from: classes.dex */
public interface ObjectChangeSet {
    String[] getChangedFields();

    boolean isDeleted();

    boolean isFieldChanged(String str);
}
