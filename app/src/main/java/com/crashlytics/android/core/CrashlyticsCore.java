package com.crashlytics.android.core;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@DependsOn({CrashEventDataProvider.class})
/* loaded from: classes.dex */
public class CrashlyticsCore extends Kit<Void> {
    static final float CLS_DEFAULT_PROCESS_DELAY = 1.0f;
    static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
    static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
    static final String CRASH_MARKER_FILE_NAME = "crash_marker";
    static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
    private static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
    static final int MAX_ATTRIBUTES = 64;
    static final int MAX_ATTRIBUTE_SIZE = 1024;
    private static final String MISSING_BUILD_ID_MSG = "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
    private static final String PREFERENCE_STORE_NAME = "com.crashlytics.android.core.CrashlyticsCore";
    public static final String TAG = "CrashlyticsCore";
    private final ConcurrentHashMap<String, String> attributes;
    private CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsController controller;
    private CrashlyticsFileMarker crashMarker;
    private float delay;
    private boolean disabled;
    private CrashEventDataProvider externalCrashEventDataProvider;
    private HttpRequestFactory httpRequestFactory;
    private CrashlyticsFileMarker initializationMarker;
    private CrashlyticsListener listener;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail;
    private String userId;
    private String userName;

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "2.3.17.dev";
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private float delay = -1.0f;
        private boolean disabled = false;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public Builder delay(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException("delay must be greater than 0");
            } else if (this.delay > 0.0f) {
                throw new IllegalStateException("delay already set.");
            } else {
                this.delay = f;
                return this;
            }
        }

        public Builder listener(CrashlyticsListener crashlyticsListener) {
            if (crashlyticsListener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            } else if (this.listener != null) {
                throw new IllegalStateException("listener already set.");
            } else {
                this.listener = crashlyticsListener;
                return this;
            }
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            if (pinningInfoProvider == null) {
                throw new IllegalArgumentException("pinningInfoProvider must not be null.");
            } else if (this.pinningInfoProvider != null) {
                throw new IllegalStateException("pinningInfoProvider already set.");
            } else {
                this.pinningInfoProvider = pinningInfoProvider;
                return this;
            }
        }

        public Builder disabled(boolean z) {
            this.disabled = z;
            return this;
        }

        public CrashlyticsCore build() {
            if (this.delay < 0.0f) {
                this.delay = CrashlyticsCore.CLS_DEFAULT_PROCESS_DELAY;
            }
            return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }
    }

    public CrashlyticsCore() {
        this(CLS_DEFAULT_PROCESS_DELAY, null, null, false);
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean z) {
        this(f, crashlyticsListener, pinningInfoProvider, z, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean z, ExecutorService executorService) {
        this.userId = null;
        this.userEmail = null;
        this.userName = null;
        this.delay = f;
        this.listener = crashlyticsListener == null ? new NoOpListener() : crashlyticsListener;
        this.pinningInfo = pinningInfoProvider;
        this.disabled = z;
        this.backgroundWorker = new CrashlyticsBackgroundWorker(executorService);
        this.attributes = new ConcurrentHashMap<>();
        this.startTime = System.currentTimeMillis();
    }

    @Override // io.fabric.sdk.android.Kit
    public boolean onPreExecute() {
        return onPreExecute(super.getContext());
    }

    boolean onPreExecute(Context context) {
        String value;
        if (this.disabled || (value = new ApiKey().getValue(context)) == null) {
            return false;
        }
        String resolveBuildId = CommonUtils.resolveBuildId(context);
        if (!isBuildIdValid(resolveBuildId, CommonUtils.getBooleanResourceValue(context, CRASHLYTICS_REQUIRE_BUILD_ID, true))) {
            throw new UnmetDependencyException(MISSING_BUILD_ID_MSG);
        }
        try {
            Logger logger = Fabric.getLogger();
            logger.i(TAG, "Initializing Crashlytics " + getVersion());
            FileStoreImpl fileStoreImpl = new FileStoreImpl(this);
            this.crashMarker = new CrashlyticsFileMarker(CRASH_MARKER_FILE_NAME, fileStoreImpl);
            this.initializationMarker = new CrashlyticsFileMarker(INITIALIZATION_MARKER_FILE_NAME, fileStoreImpl);
            PreferenceManager create = PreferenceManager.create(new PreferenceStoreImpl(getContext(), PREFERENCE_STORE_NAME), this);
            CrashlyticsPinningInfoProvider crashlyticsPinningInfoProvider = this.pinningInfo != null ? new CrashlyticsPinningInfoProvider(this.pinningInfo) : null;
            this.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
            this.httpRequestFactory.setPinningInfoProvider(crashlyticsPinningInfoProvider);
            IdManager idManager = getIdManager();
            AppData create2 = AppData.create(context, idManager, value, resolveBuildId);
            ManifestUnityVersionProvider manifestUnityVersionProvider = new ManifestUnityVersionProvider(context, create2.packageName);
            Logger logger2 = Fabric.getLogger();
            logger2.d(TAG, "Installer package name is: " + create2.installerPackageName);
            this.controller = new CrashlyticsController(this, this.backgroundWorker, this.httpRequestFactory, idManager, create, fileStoreImpl, create2, manifestUnityVersionProvider);
            boolean didPreviousInitializationFail = didPreviousInitializationFail();
            checkForPreviousCrash();
            this.controller.enableExceptionHandling(Thread.getDefaultUncaughtExceptionHandler());
            if (!didPreviousInitializationFail || !CommonUtils.canTryConnection(context)) {
                Fabric.getLogger().d(TAG, "Exception handling initialization successful");
                return true;
            }
            Fabric.getLogger().d(TAG, "Crashlytics did not finish previous background initialization. Initializing synchronously.");
            finishInitSynchronously();
            return false;
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Crashlytics was not started due to an exception during initialization", e);
            this.controller = null;
            return false;
        }
    }

    @Override // io.fabric.sdk.android.Kit
    public Void doInBackground() {
        SettingsData awaitSettingsData;
        markInitializationStarted();
        SessionEventData externalCrashEventData = getExternalCrashEventData();
        if (externalCrashEventData != null) {
            this.controller.writeExternalCrashEvent(externalCrashEventData);
        }
        this.controller.cleanInvalidTempFiles();
        try {
            try {
                awaitSettingsData = Settings.getInstance().awaitSettingsData();
            } catch (Exception e) {
                Fabric.getLogger().e(TAG, "Crashlytics encountered a problem during asynchronous initialization.", e);
            }
            if (awaitSettingsData == null) {
                Fabric.getLogger().w(TAG, "Received null settings, skipping report submission!");
                return null;
            } else if (!awaitSettingsData.featuresData.collectReports) {
                Fabric.getLogger().d(TAG, "Collection of crash reports disabled in Crashlytics settings.");
                return null;
            } else {
                if (!this.controller.finalizeSessions(awaitSettingsData.sessionData)) {
                    Fabric.getLogger().d(TAG, "Could not finalize previous sessions.");
                }
                this.controller.submitAllReports(this.delay, awaitSettingsData);
                return null;
            }
        } finally {
            markInitializationComplete();
        }
    }

    public static CrashlyticsCore getInstance() {
        return (CrashlyticsCore) Fabric.getKit(CrashlyticsCore.class);
    }

    public PinningInfoProvider getPinningInfoProvider() {
        if (!this.disabled) {
            return this.pinningInfo;
        }
        return null;
    }

    public void logException(Throwable th) {
        if (this.disabled || !ensureFabricWithCalled("prior to logging exceptions.")) {
            return;
        }
        if (th == null) {
            Fabric.getLogger().log(5, TAG, "Crashlytics is ignoring a request to log a null exception.");
        } else {
            this.controller.writeNonFatalException(Thread.currentThread(), th);
        }
    }

    public void log(String str) {
        doLog(3, TAG, str);
    }

    private void doLog(int i, String str, String str2) {
        if (!this.disabled && ensureFabricWithCalled("prior to logging messages.")) {
            this.controller.writeToLog(System.currentTimeMillis() - this.startTime, formatLogMessage(i, str, str2));
        }
    }

    public void log(int i, String str, String str2) {
        doLog(i, str, str2);
        Fabric.getLogger().log(i, "" + str, "" + str2, true);
    }

    public void setUserIdentifier(String str) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userId = sanitizeAttribute(str);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setUserName(String str) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userName = sanitizeAttribute(str);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setUserEmail(String str) {
        if (!this.disabled && ensureFabricWithCalled("prior to setting user data.")) {
            this.userEmail = sanitizeAttribute(str);
            this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
        }
    }

    public void setString(String str, String str2) {
        if (this.disabled || !ensureFabricWithCalled("prior to setting keys.")) {
            return;
        }
        if (str == null) {
            Context context = getContext();
            if (context == null || !CommonUtils.isAppDebuggable(context)) {
                Fabric.getLogger().e(TAG, "Attempting to set custom attribute with null key, ignoring.", null);
                return;
            }
            throw new IllegalArgumentException("Custom attribute key must not be null.");
        }
        String sanitizeAttribute = sanitizeAttribute(str);
        if (this.attributes.size() < 64 || this.attributes.containsKey(sanitizeAttribute)) {
            this.attributes.put(sanitizeAttribute, str2 == null ? "" : sanitizeAttribute(str2));
            this.controller.cacheKeyData(this.attributes);
            return;
        }
        Fabric.getLogger().d(TAG, "Exceeded maximum number of custom attributes (64)");
    }

    public void setBool(String str, boolean z) {
        setString(str, Boolean.toString(z));
    }

    public void setDouble(String str, double d) {
        setString(str, Double.toString(d));
    }

    public void setFloat(String str, float f) {
        setString(str, Float.toString(f));
    }

    public void setInt(String str, int i) {
        setString(str, Integer.toString(i));
    }

    public void setLong(String str, long j) {
        setString(str, Long.toString(j));
    }

    public void crash() {
        new CrashTest().indexOutOfBounds();
    }

    public boolean verifyPinning(URL url) {
        try {
            return internalVerifyPinning(url);
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Could not verify SSL pinning", e);
            return false;
        }
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener crashlyticsListener) {
        Fabric.getLogger().w(TAG, "Use of setListener is deprecated.");
        if (crashlyticsListener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        this.listener = crashlyticsListener;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    CrashlyticsController getController() {
        return this.controller;
    }

    public String getUserIdentifier() {
        if (getIdManager().canCollectUserIds()) {
            return this.userId;
        }
        return null;
    }

    public String getUserEmail() {
        if (getIdManager().canCollectUserIds()) {
            return this.userEmail;
        }
        return null;
    }

    public String getUserName() {
        if (getIdManager().canCollectUserIds()) {
            return this.userName;
        }
        return null;
    }

    private void finishInitSynchronously() {
        PriorityCallable<Void> priorityCallable = new PriorityCallable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsCore.1
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                return CrashlyticsCore.this.doInBackground();
            }

            @Override // io.fabric.sdk.android.services.concurrency.PriorityTask, io.fabric.sdk.android.services.concurrency.PriorityProvider
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        for (Task task : getDependencies()) {
            priorityCallable.addDependency(task);
        }
        Future submit = getFabric().getExecutorService().submit(priorityCallable);
        Fabric.getLogger().d(TAG, "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            submit.get(4L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Fabric.getLogger().e(TAG, "Crashlytics was interrupted during initialization.", e);
        } catch (ExecutionException e2) {
            Fabric.getLogger().e(TAG, "Problem encountered during Crashlytics initialization.", e2);
        } catch (TimeoutException e3) {
            Fabric.getLogger().e(TAG, "Crashlytics timed out during initialization.", e3);
        }
    }

    void markInitializationStarted() {
        this.backgroundWorker.submitAndWait(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsCore.2
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                CrashlyticsCore.this.initializationMarker.create();
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Initialization marker file created.");
                return null;
            }
        });
    }

    void markInitializationComplete() {
        this.backgroundWorker.submit(new Callable<Boolean>() { // from class: com.crashlytics.android.core.CrashlyticsCore.3
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                try {
                    boolean remove = CrashlyticsCore.this.initializationMarker.remove();
                    Logger logger = Fabric.getLogger();
                    logger.d(CrashlyticsCore.TAG, "Initialization marker file removed: " + remove);
                    return Boolean.valueOf(remove);
                } catch (Exception e) {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Problem encountered deleting Crashlytics initialization marker.", e);
                    return false;
                }
            }
        });
    }

    boolean didPreviousInitializationFail() {
        return ((Boolean) this.backgroundWorker.submitAndWait(new Callable<Boolean>() { // from class: com.crashlytics.android.core.CrashlyticsCore.4
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                return Boolean.valueOf(CrashlyticsCore.this.initializationMarker.isPresent());
            }
        })).booleanValue();
    }

    void setExternalCrashEventDataProvider(CrashEventDataProvider crashEventDataProvider) {
        this.externalCrashEventDataProvider = crashEventDataProvider;
    }

    SessionEventData getExternalCrashEventData() {
        if (this.externalCrashEventDataProvider != null) {
            return this.externalCrashEventDataProvider.getCrashEventData();
        }
        return null;
    }

    boolean internalVerifyPinning(URL url) {
        if (getPinningInfoProvider() == null) {
            return false;
        }
        HttpRequest buildHttpRequest = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, url.toString());
        ((HttpsURLConnection) buildHttpRequest.getConnection()).setInstanceFollowRedirects(false);
        buildHttpRequest.code();
        return true;
    }

    private void checkForPreviousCrash() {
        if (Boolean.TRUE.equals((Boolean) this.backgroundWorker.submitAndWait(new CrashMarkerCheck(this.crashMarker)))) {
            try {
                this.listener.crashlyticsDidDetectCrashDuringPreviousExecution();
            } catch (Exception e) {
                Fabric.getLogger().e(TAG, "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
            }
        }
    }

    public void createCrashMarker() {
        this.crashMarker.create();
    }

    private static String formatLogMessage(int i, String str, String str2) {
        return CommonUtils.logPriorityToString(i) + "/" + str + " " + str2;
    }

    private static boolean ensureFabricWithCalled(String str) {
        CrashlyticsCore instance = getInstance();
        if (instance != null && instance.controller != null) {
            return true;
        }
        Logger logger = Fabric.getLogger();
        logger.e(TAG, "Crashlytics must be initialized by calling Fabric.with(Context) " + str, null);
        return false;
    }

    private static String sanitizeAttribute(String str) {
        if (str == null) {
            return str;
        }
        String trim = str.trim();
        return trim.length() > 1024 ? trim.substring(0, 1024) : trim;
    }

    /* loaded from: classes.dex */
    public static final class CrashMarkerCheck implements Callable<Boolean> {
        private final CrashlyticsFileMarker crashMarker;

        public CrashMarkerCheck(CrashlyticsFileMarker crashlyticsFileMarker) {
            this.crashMarker = crashlyticsFileMarker;
        }

        @Override // java.util.concurrent.Callable
        public Boolean call() throws Exception {
            if (!this.crashMarker.isPresent()) {
                return Boolean.FALSE;
            }
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Found previous crash marker.");
            this.crashMarker.remove();
            return Boolean.TRUE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class NoOpListener implements CrashlyticsListener {
        @Override // com.crashlytics.android.core.CrashlyticsListener
        public void crashlyticsDidDetectCrashDuringPreviousExecution() {
        }

        private NoOpListener() {
        }
    }

    static boolean isBuildIdValid(String str, boolean z) {
        if (!z) {
            Fabric.getLogger().d(TAG, "Configured not to require a build ID.");
            return true;
        } else if (!CommonUtils.isNullOrEmpty(str)) {
            return true;
        } else {
            Log.e(TAG, ".");
            Log.e(TAG, ".     |  | ");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".   \\ |  | /");
            Log.e(TAG, ".    \\    /");
            Log.e(TAG, ".     \\  /");
            Log.e(TAG, ".      \\/");
            Log.e(TAG, ".");
            Log.e(TAG, MISSING_BUILD_ID_MSG);
            Log.e(TAG, ".");
            Log.e(TAG, ".      /\\");
            Log.e(TAG, ".     /  \\");
            Log.e(TAG, ".    /    \\");
            Log.e(TAG, ".   / |  | \\");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".     |  |");
            Log.e(TAG, ".");
            return false;
        }
    }
}
