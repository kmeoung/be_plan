package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.AppGlideModule;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class GeneratedAppGlideModule extends AppGlideModule {
    @NonNull
    @Deprecated
    public abstract Set<Class<?>> getExcludedModuleClasses();

    @Nullable
    public RequestManagerRetriever.RequestManagerFactory getRequestManagerFactory() {
        return null;
    }

    GeneratedAppGlideModule() {
    }
}
