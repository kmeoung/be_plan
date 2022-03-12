package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Task;
import java.io.File;
import java.util.Collection;

/* loaded from: classes.dex */
public abstract class Kit<Result> implements Comparable<Kit> {
    Context context;
    Fabric fabric;
    IdManager idManager;
    InitializationCallback<Result> initializationCallback;
    InitializationTask<Result> initializationTask = new InitializationTask<>(this);
    final DependsOn dependsOnAnnotation = (DependsOn) getClass().getAnnotation(DependsOn.class);

    public abstract Result doInBackground();

    public abstract String getIdentifier();

    public abstract String getVersion();

    public void onCancelled(Result result) {
    }

    public void onPostExecute(Result result) {
    }

    public boolean onPreExecute() {
        return true;
    }

    public void injectParameters(Context context, Fabric fabric, InitializationCallback<Result> initializationCallback, IdManager idManager) {
        this.fabric = fabric;
        this.context = new FabricContext(context, getIdentifier(), getPath());
        this.initializationCallback = initializationCallback;
        this.idManager = idManager;
    }

    public final void initialize() {
        this.initializationTask.executeOnExecutor(this.fabric.getExecutorService(), (Object[]) new Void[]{null});
    }

    public IdManager getIdManager() {
        return this.idManager;
    }

    public Context getContext() {
        return this.context;
    }

    public Fabric getFabric() {
        return this.fabric;
    }

    public String getPath() {
        return ".Fabric" + File.separator + getIdentifier();
    }

    public int compareTo(Kit kit) {
        if (containsAnnotatedDependency(kit)) {
            return 1;
        }
        if (kit.containsAnnotatedDependency(this)) {
            return -1;
        }
        if (!hasAnnotatedDependency() || kit.hasAnnotatedDependency()) {
            return (hasAnnotatedDependency() || !kit.hasAnnotatedDependency()) ? 0 : -1;
        }
        return 1;
    }

    boolean containsAnnotatedDependency(Kit kit) {
        if (hasAnnotatedDependency()) {
            for (Class<?> cls : this.dependsOnAnnotation.value()) {
                if (cls.isAssignableFrom(kit.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean hasAnnotatedDependency() {
        return this.dependsOnAnnotation != null;
    }

    public Collection<Task> getDependencies() {
        return this.initializationTask.getDependencies();
    }
}
