package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class Fabric {
    static final boolean DEFAULT_DEBUGGABLE = false;
    static final Logger DEFAULT_LOGGER = new DefaultLogger();
    static final String ROOT_DIR = ".Fabric";
    public static final String TAG = "Fabric";
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private ActivityLifecycleManager activityLifecycleManager;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    final Logger logger;
    private final Handler mainHandler;

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public String getVersion() {
        return "1.3.17.dev";
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;
        private PriorityThreadPoolExecutor threadPoolExecutor;

        @Deprecated
        public Builder executorService(ExecutorService executorService) {
            return this;
        }

        @Deprecated
        public Builder handler(Handler handler) {
            return this;
        }

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context;
        }

        public Builder kits(Kit... kitArr) {
            if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.kits = kitArr;
            return this;
        }

        public Builder threadPoolExecutor(PriorityThreadPoolExecutor priorityThreadPoolExecutor) {
            if (priorityThreadPoolExecutor == null) {
                throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
            } else if (this.threadPoolExecutor != null) {
                throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
            } else {
                this.threadPoolExecutor = priorityThreadPoolExecutor;
                return this;
            }
        }

        public Builder logger(Logger logger) {
            if (logger == null) {
                throw new IllegalArgumentException("Logger must not be null.");
            } else if (this.logger != null) {
                throw new IllegalStateException("Logger already set.");
            } else {
                this.logger = logger;
                return this;
            }
        }

        public Builder appIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appIdentifier must not be null.");
            } else if (this.appIdentifier != null) {
                throw new IllegalStateException("appIdentifier already set.");
            } else {
                this.appIdentifier = str;
                return this;
            }
        }

        public Builder appInstallIdentifier(String str) {
            if (str == null) {
                throw new IllegalArgumentException("appInstallIdentifier must not be null.");
            } else if (this.appInstallIdentifier != null) {
                throw new IllegalStateException("appInstallIdentifier already set.");
            } else {
                this.appInstallIdentifier = str;
                return this;
            }
        }

        public Builder debuggable(boolean z) {
            this.debuggable = z;
            return this;
        }

        public Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
            if (initializationCallback == null) {
                throw new IllegalArgumentException("initializationCallback must not be null.");
            } else if (this.initializationCallback != null) {
                throw new IllegalStateException("initializationCallback already set.");
            } else {
                this.initializationCallback = initializationCallback;
                return this;
            }
        }

        public Fabric build() {
            Map kitMap;
            if (this.threadPoolExecutor == null) {
                this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                if (this.debuggable) {
                    this.logger = new DefaultLogger(3);
                } else {
                    this.logger = new DefaultLogger();
                }
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            if (this.kits == null) {
                kitMap = new HashMap();
            } else {
                kitMap = Fabric.getKitMap(Arrays.asList(this.kits));
            }
            Context applicationContext = this.context.getApplicationContext();
            return new Fabric(applicationContext, kitMap, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, new IdManager(applicationContext, this.appIdentifier, this.appInstallIdentifier, kitMap.values()), Fabric.extractActivity(this.context));
        }
    }

    static Fabric singleton() {
        if (singleton != null) {
            return singleton;
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    Fabric(Context context, Map<Class<? extends Kit>, Kit> map, PriorityThreadPoolExecutor priorityThreadPoolExecutor, Handler handler, Logger logger, boolean z, InitializationCallback initializationCallback, IdManager idManager, Activity activity) {
        this.context = context;
        this.kits = map;
        this.executorService = priorityThreadPoolExecutor;
        this.mainHandler = handler;
        this.logger = logger;
        this.debuggable = z;
        this.initializationCallback = initializationCallback;
        this.kitInitializationCallback = createKitInitializationCallback(map.size());
        this.idManager = idManager;
        setCurrentActivity(activity);
    }

    public static Fabric with(Context context, Kit... kitArr) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(new Builder(context).kits(kitArr).build());
                }
            }
        }
        return singleton;
    }

    public static Fabric with(Fabric fabric) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(fabric);
                }
            }
        }
        return singleton;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference<>(activity);
        return this;
    }

    public Activity getCurrentActivity() {
        if (this.activity != null) {
            return this.activity.get();
        }
        return null;
    }

    private void init() {
        this.activityLifecycleManager = new ActivityLifecycleManager(this.context);
        this.activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks() { // from class: io.fabric.sdk.android.Fabric.1
            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        initializeKits(this.context);
    }

    void initializeKits(Context context) {
        StringBuilder sb;
        Future<Map<String, KitInfo>> kitsFinderFuture = getKitsFinderFuture(context);
        Collection<Kit> kits = getKits();
        Onboarding onboarding = new Onboarding(kitsFinderFuture, kits);
        ArrayList<Kit> arrayList = new ArrayList(kits);
        Collections.sort(arrayList);
        onboarding.injectParameters(context, this, InitializationCallback.EMPTY, this.idManager);
        for (Kit kit : arrayList) {
            kit.injectParameters(context, this, this.kitInitializationCallback, this.idManager);
        }
        onboarding.initialize();
        if (getLogger().isLoggable(TAG, 3)) {
            sb = new StringBuilder("Initializing ");
            sb.append(getIdentifier());
            sb.append(" [Version: ");
            sb.append(getVersion());
            sb.append("], with the following kits:\n");
        } else {
            sb = null;
        }
        for (Kit kit2 : arrayList) {
            kit2.initializationTask.addDependency(onboarding.initializationTask);
            addAnnotatedDependencies(this.kits, kit2);
            kit2.initialize();
            if (sb != null) {
                sb.append(kit2.getIdentifier());
                sb.append(" [Version: ");
                sb.append(kit2.getVersion());
                sb.append("]\n");
            }
        }
        if (sb != null) {
            getLogger().d(TAG, sb.toString());
        }
    }

    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> map, Kit kit) {
        Class<?>[] value;
        DependsOn dependsOn = kit.dependsOnAnnotation;
        if (dependsOn != null) {
            for (Class<?> cls : dependsOn.value()) {
                if (cls.isInterface()) {
                    for (Kit kit2 : map.values()) {
                        if (cls.isAssignableFrom(kit2.getClass())) {
                            kit.initializationTask.addDependency(kit2.initializationTask);
                        }
                    }
                } else if (map.get(cls) == null) {
                    throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                } else {
                    kit.initializationTask.addDependency(map.get(cls).initializationTask);
                }
            }
        }
    }

    public static Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.activityLifecycleManager;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public Handler getMainHandler() {
        return this.mainHandler;
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    public static <T extends Kit> T getKit(Class<T> cls) {
        return (T) singleton().kits.get(cls);
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return singleton.logger;
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return singleton.debuggable;
    }

    public static boolean isInitialized() {
        return singleton != null && singleton.initialized.get();
    }

    public String getAppIdentifier() {
        return this.idManager.getAppIdentifier();
    }

    public String getAppInstallIdentifier() {
        return this.idManager.getAppInstallIdentifier();
    }

    public static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> collection) {
        HashMap hashMap = new HashMap(collection.size());
        addToKitMap(hashMap, collection);
        return hashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> collection) {
        for (Kit kit : collection) {
            map.put(kit.getClass(), kit);
            if (kit instanceof KitGroup) {
                addToKitMap(map, ((KitGroup) kit).getKits());
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(final int i) {
        return new InitializationCallback() { // from class: io.fabric.sdk.android.Fabric.2
            final CountDownLatch kitInitializedLatch;

            {
                Fabric.this = this;
                this.kitInitializedLatch = new CountDownLatch(i);
            }

            @Override // io.fabric.sdk.android.InitializationCallback
            public void success(Object obj) {
                this.kitInitializedLatch.countDown();
                if (this.kitInitializedLatch.getCount() == 0) {
                    Fabric.this.initialized.set(true);
                    Fabric.this.initializationCallback.success(Fabric.this);
                }
            }

            @Override // io.fabric.sdk.android.InitializationCallback
            public void failure(Exception exc) {
                Fabric.this.initializationCallback.failure(exc);
            }
        };
    }

    Future<Map<String, KitInfo>> getKitsFinderFuture(Context context) {
        return getExecutorService().submit(new FabricKitsFinder(context.getPackageCodePath()));
    }
}
