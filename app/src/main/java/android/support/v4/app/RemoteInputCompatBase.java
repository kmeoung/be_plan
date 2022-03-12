package android.support.v4.app;

import android.os.Bundle;
import java.util.Set;

@Deprecated
/* loaded from: classes.dex */
class RemoteInputCompatBase {

    /* loaded from: classes.dex */
    public static abstract class RemoteInput {

        /* loaded from: classes.dex */
        public interface Factory {
            RemoteInput build(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set);

            RemoteInput[] newArray(int i);
        }

        public abstract boolean getAllowFreeFormInput();

        public abstract Set<String> getAllowedDataTypes();

        public abstract CharSequence[] getChoices();

        public abstract Bundle getExtras();

        public abstract CharSequence getLabel();

        public abstract String getResultKey();
    }

    RemoteInputCompatBase() {
    }
}
