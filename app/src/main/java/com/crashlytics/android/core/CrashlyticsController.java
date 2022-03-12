package com.crashlytics.android.core;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.ReportUploader;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class CrashlyticsController {
    private static final int ANALYZER_VERSION = 1;
    private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    static final String FATAL_SESSION_DIR = "fatal-sessions";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
    static final int MAX_INVALID_SESSIONS = 4;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_OPEN_SESSIONS = 8;
    static final int MAX_STACK_SIZE = 1024;
    static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
    static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
    private final AppData appData;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsCore crashlyticsCore;
    private final DevicePowerStateListener devicePowerStateListener;
    private final FileStore fileStore;
    private final HttpRequestFactory httpRequestFactory;
    private final IdManager idManager;
    private final LogFileDirectoryProvider logFileDirectoryProvider;
    private final LogFileManager logFileManager;
    private final PreferenceManager preferenceManager;
    private final String unityVersion;
    static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter() { // from class: com.crashlytics.android.core.CrashlyticsController.1
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return str.length() == ClsFileOutputStream.SESSION_FILE_EXTENSION.length() + 35 && str.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    static final Comparator<File> LARGEST_FILE_NAME_FIRST = new Comparator<File>() { // from class: com.crashlytics.android.core.CrashlyticsController.2
        public int compare(File file, File file2) {
            return file2.getName().compareTo(file.getName());
        }
    };
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST = new Comparator<File>() { // from class: com.crashlytics.android.core.CrashlyticsController.3
        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    };
    static final FilenameFilter ANY_SESSION_FILENAME_FILTER = new FilenameFilter() { // from class: com.crashlytics.android.core.CrashlyticsController.4
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return CrashlyticsController.SESSION_FILE_PATTERN.matcher(str).matches();
        }
    };
    private static final Pattern SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    static final String SESSION_USER_TAG = "SessionUser";
    static final String SESSION_APP_TAG = "SessionApp";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    private static final String[] INITIAL_SESSION_PART_TAGS = {SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG};
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private final ReportUploader.ReportFilesProvider reportFilesProvider = new ReportUploaderFilesProvider();
    private final ReportUploader.HandlingExceptionCheck handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck();
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));

    /* loaded from: classes.dex */
    public static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String str) {
            this.string = str;
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return str.contains(this.string) && !str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    /* loaded from: classes.dex */
    public static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String str) {
            this.sessionId = str;
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.sessionId);
            sb.append(ClsFileOutputStream.SESSION_FILE_EXTENSION);
            return !str.equals(sb.toString()) && str.contains(this.sessionId) && !str.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    /* loaded from: classes.dex */
    public static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return !CrashlyticsController.SESSION_FILE_FILTER.accept(file, str) && CrashlyticsController.SESSION_FILE_PATTERN.matcher(str).matches();
        }
    }

    /* loaded from: classes.dex */
    public static class InvalidPartFileFilter implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, str) || str.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG);
        }
    }

    public CrashlyticsController(CrashlyticsCore crashlyticsCore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker, HttpRequestFactory httpRequestFactory, IdManager idManager, PreferenceManager preferenceManager, FileStore fileStore, AppData appData, UnityVersionProvider unityVersionProvider) {
        this.crashlyticsCore = crashlyticsCore;
        this.backgroundWorker = crashlyticsBackgroundWorker;
        this.httpRequestFactory = httpRequestFactory;
        this.idManager = idManager;
        this.preferenceManager = preferenceManager;
        this.fileStore = fileStore;
        this.appData = appData;
        this.unityVersion = unityVersionProvider.getUnityVersion();
        Context context = crashlyticsCore.getContext();
        this.logFileDirectoryProvider = new LogFileDirectoryProvider(fileStore);
        this.logFileManager = new LogFileManager(context, this.logFileDirectoryProvider);
        this.devicePowerStateListener = new DevicePowerStateListener(context);
    }

    public void enableExceptionHandling(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        openSession();
        this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new CrashlyticsUncaughtExceptionHandler.CrashListener() { // from class: com.crashlytics.android.core.CrashlyticsController.5
            @Override // com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.CrashListener
            public void onUncaughtException(Thread thread, Throwable th) {
                CrashlyticsController.this.handleUncaughtException(thread, th);
            }
        }, uncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
    }

    synchronized void handleUncaughtException(final Thread thread, final Throwable th) {
        Logger logger = Fabric.getLogger();
        logger.d(CrashlyticsCore.TAG, "Crashlytics is handling uncaught exception \"" + th + "\" from thread " + thread.getName());
        this.devicePowerStateListener.dispose();
        final Date date = new Date();
        this.backgroundWorker.submitAndWait(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.6
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                CrashlyticsController.this.crashlyticsCore.createCrashMarker();
                CrashlyticsController.this.writeFatal(date, thread, th);
                SettingsData awaitSettingsData = Settings.getInstance().awaitSettingsData();
                SessionSettingsData sessionSettingsData = awaitSettingsData != null ? awaitSettingsData.sessionData : null;
                CrashlyticsController.this.doCloseSessions(sessionSettingsData);
                CrashlyticsController.this.doOpenSession();
                if (sessionSettingsData != null) {
                    CrashlyticsController.this.trimSessionFiles(sessionSettingsData.maxCompleteSessionsCount);
                }
                if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(awaitSettingsData)) {
                    CrashlyticsController.this.sendSessionReports(awaitSettingsData);
                }
                return null;
            }
        });
    }

    public void submitAllReports(float f, SettingsData settingsData) {
        if (settingsData == null) {
            Fabric.getLogger().w(CrashlyticsCore.TAG, "Could not send reports. Settings are not available.");
            return;
        }
        new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(settingsData.appData.reportsUrl), this.reportFilesProvider, this.handlingExceptionCheck).uploadReports(f, shouldPromptUserBeforeSendingCrashReports(settingsData) ? new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, settingsData.promptData) : new ReportUploader.AlwaysSendCheck());
    }

    public void writeToLog(final long j, final String str) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.7
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    return null;
                }
                CrashlyticsController.this.logFileManager.writeToLog(j, str);
                return null;
            }
        });
    }

    public void writeNonFatalException(final Thread thread, final Throwable th) {
        final Date date = new Date();
        this.backgroundWorker.submit(new Runnable() { // from class: com.crashlytics.android.core.CrashlyticsController.8
            @Override // java.lang.Runnable
            public void run() {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteNonFatal(date, thread, th);
                }
            }
        });
    }

    public void cacheUserData(final String str, final String str2, final String str3) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.9
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(CrashlyticsController.this.getCurrentSessionId(), new UserMetaData(str, str2, str3));
                return null;
            }
        });
    }

    public void cacheKeyData(final Map<String, String> map) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.10
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(CrashlyticsController.this.getCurrentSessionId(), map);
                return null;
            }
        });
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.11
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    public String getCurrentSessionId() {
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (listSortedSessionBeginFiles.length > 0) {
            return getSessionIdFromSessionFile(listSortedSessionBeginFiles[0]);
        }
        return null;
    }

    private String getPreviousSessionId() {
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (listSortedSessionBeginFiles.length > 1) {
            return getSessionIdFromSessionFile(listSortedSessionBeginFiles[1]);
        }
        return null;
    }

    public static String getSessionIdFromSessionFile(File file) {
        return file.getName().substring(0, 35);
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    public boolean finalizeSessions(final SessionSettingsData sessionSettingsData) {
        return ((Boolean) this.backgroundWorker.submitAndWait(new Callable<Boolean>() { // from class: com.crashlytics.android.core.CrashlyticsController.12
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    Fabric.getLogger().d(CrashlyticsCore.TAG, "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Finalizing previously open sessions.");
                CrashlyticsController.this.doCloseSessions(sessionSettingsData, true);
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        })).booleanValue();
    }

    public void doOpenSession() throws Exception {
        Date date = new Date();
        String clsuuid = new CLSUUID(this.idManager).toString();
        Logger logger = Fabric.getLogger();
        logger.d(CrashlyticsCore.TAG, "Opening a new session with ID " + clsuuid);
        writeBeginSession(clsuuid, date);
        writeSessionApp(clsuuid);
        writeSessionOS(clsuuid);
        writeSessionDevice(clsuuid);
        this.logFileManager.setCurrentSession(clsuuid);
    }

    void doCloseSessions(SessionSettingsData sessionSettingsData) throws Exception {
        doCloseSessions(sessionSettingsData, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void doCloseSessions(SessionSettingsData sessionSettingsData, boolean z) throws Exception {
        trimOpenSessions((z ? 1 : 0) + 8);
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        if (listSortedSessionBeginFiles.length <= z) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No open sessions to be closed.");
            return;
        }
        writeSessionUser(getSessionIdFromSessionFile(listSortedSessionBeginFiles[z ? 1 : 0]));
        if (sessionSettingsData == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Unable to close session. Settings are not loaded.");
        } else {
            closeOpenSessions(listSortedSessionBeginFiles, z, sessionSettingsData.maxCustomExceptionEvents);
        }
    }

    private void closeOpenSessions(File[] fileArr, int i, int i2) {
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Closing open sessions.");
        while (i < fileArr.length) {
            File file = fileArr[i];
            String sessionIdFromSessionFile = getSessionIdFromSessionFile(file);
            Logger logger = Fabric.getLogger();
            logger.d(CrashlyticsCore.TAG, "Closing session: " + sessionIdFromSessionFile);
            writeSessionPartsToSessionFile(file, sessionIdFromSessionFile, i2);
            i++;
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream != null) {
            try {
                clsFileOutputStream.closeInProgressStream();
            } catch (IOException e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error closing session file stream in the presence of an exception", e);
            }
        }
    }

    private void deleteSessionPartFilesFor(String str) {
        for (File file : listSessionPartFilesFor(str)) {
            file.delete();
        }
    }

    private File[] listSessionPartFilesFor(String str) {
        return listFilesMatching(new SessionPartFileFilter(str));
    }

    File[] listCompleteSessionFiles() {
        LinkedList linkedList = new LinkedList();
        Collections.addAll(linkedList, listFilesMatching(getFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, listFilesMatching(getNonFatalSessionFilesDir(), SESSION_FILE_FILTER));
        Collections.addAll(linkedList, listFilesMatching(getFilesDir(), SESSION_FILE_FILTER));
        return (File[]) linkedList.toArray(new File[linkedList.size()]);
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(new FileNameContainsFilter(SESSION_BEGIN_TAG));
    }

    private File[] listSortedSessionBeginFiles() {
        File[] listSessionBeginFiles = listSessionBeginFiles();
        Arrays.sort(listSessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return listSessionBeginFiles;
    }

    public File[] listFilesMatching(FilenameFilter filenameFilter) {
        return listFilesMatching(getFilesDir(), filenameFilter);
    }

    private File[] listFilesMatching(File file, FilenameFilter filenameFilter) {
        return ensureFileArrayNotNull(file.listFiles(filenameFilter));
    }

    private File[] listFiles(File file) {
        return ensureFileArrayNotNull(file.listFiles());
    }

    private File[] ensureFileArrayNotNull(File[] fileArr) {
        return fileArr == null ? new File[0] : fileArr;
    }

    private void trimSessionEventFiles(String str, int i) {
        File filesDir = getFilesDir();
        Utils.capFileCount(filesDir, new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG), i, SMALLEST_FILE_NAME_FIRST);
    }

    void trimSessionFiles(int i) {
        int capFileCount = i - Utils.capFileCount(getFatalSessionFilesDir(), i, SMALLEST_FILE_NAME_FIRST);
        Utils.capFileCount(getFilesDir(), SESSION_FILE_FILTER, capFileCount - Utils.capFileCount(getNonFatalSessionFilesDir(), capFileCount, SMALLEST_FILE_NAME_FIRST), SMALLEST_FILE_NAME_FIRST);
    }

    private void trimOpenSessions(int i) {
        HashSet hashSet = new HashSet();
        File[] listSortedSessionBeginFiles = listSortedSessionBeginFiles();
        int min = Math.min(i, listSortedSessionBeginFiles.length);
        for (int i2 = 0; i2 < min; i2++) {
            hashSet.add(getSessionIdFromSessionFile(listSortedSessionBeginFiles[i2]));
        }
        this.logFileManager.discardOldLogFiles(hashSet);
        retainSessions(listFilesMatching(new AnySessionPartFileFilter()), hashSet);
    }

    private void retainSessions(File[] fileArr, Set<String> set) {
        for (File file : fileArr) {
            String name = file.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(name);
            if (!matcher.matches()) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Deleting unknown file: " + name);
                file.delete();
                return;
            }
            if (!set.contains(matcher.group(1))) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Trimming session file: " + name);
                file.delete();
            }
        }
    }

    private File[] getTrimmedNonFatalFiles(String str, File[] fileArr, int i) {
        if (fileArr.length <= i) {
            return fileArr;
        }
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Trimming down to %d logged exceptions.", Integer.valueOf(i)));
        trimSessionEventFiles(str, i);
        return listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
    }

    public void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable() { // from class: com.crashlytics.android.core.CrashlyticsController.13
            @Override // java.lang.Runnable
            public void run() {
                CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] fileArr) {
        File[] listFilesMatching;
        final HashSet hashSet = new HashSet();
        for (File file : fileArr) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Found invalid session part file: " + file);
            hashSet.add(getSessionIdFromSessionFile(file));
        }
        if (!hashSet.isEmpty()) {
            File invalidFilesDir = getInvalidFilesDir();
            if (!invalidFilesDir.exists()) {
                invalidFilesDir.mkdir();
            }
            for (File file2 : listFilesMatching(new FilenameFilter() { // from class: com.crashlytics.android.core.CrashlyticsController.14
                @Override // java.io.FilenameFilter
                public boolean accept(File file3, String str) {
                    if (str.length() < 35) {
                        return false;
                    }
                    return hashSet.contains(str.substring(0, 35));
                }
            })) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Moving session file: " + file2);
                if (!file2.renameTo(new File(invalidFilesDir, file2.getName()))) {
                    Fabric.getLogger().d(CrashlyticsCore.TAG, "Could not move session file. Deleting " + file2);
                    file2.delete();
                }
            }
            trimInvalidSessionFiles();
        }
    }

    private void trimInvalidSessionFiles() {
        File invalidFilesDir = getInvalidFilesDir();
        if (invalidFilesDir.exists()) {
            File[] listFilesMatching = listFilesMatching(invalidFilesDir, new InvalidPartFileFilter());
            Arrays.sort(listFilesMatching, Collections.reverseOrder());
            HashSet hashSet = new HashSet();
            for (int i = 0; i < listFilesMatching.length && hashSet.size() < 4; i++) {
                hashSet.add(getSessionIdFromSessionFile(listFilesMatching[i]));
            }
            retainSessions(listFiles(invalidFilesDir), hashSet);
        }
    }

    public void writeFatal(Date date, Thread thread, Throwable th) {
        Throwable th2;
        ClsFileOutputStream clsFileOutputStream;
        Exception e;
        String currentSessionId;
        CodedOutputStream newInstance;
        CodedOutputStream codedOutputStream = null;
        try {
            currentSessionId = getCurrentSessionId();
        } catch (Exception e2) {
            e = e2;
            clsFileOutputStream = null;
        } catch (Throwable th3) {
            th2 = th3;
            clsFileOutputStream = null;
        }
        if (currentSessionId == null) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a fatal exception while no session was open.", null);
            CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
            return;
        }
        recordFatalExceptionAnswersEvent(currentSessionId, th.getClass().getName());
        clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), currentSessionId + SESSION_FATAL_TAG);
        try {
            try {
                newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th4) {
            th2 = th4;
        }
        try {
            writeSessionEvent(newInstance, date, thread, th, "crash", true);
            CommonUtils.flushOrLog(newInstance, "Failed to flush to session begin file.");
        } catch (Exception e4) {
            e = e4;
            codedOutputStream = newInstance;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Throwable th5) {
            th2 = th5;
            codedOutputStream = newInstance;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            throw th2;
        }
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
    }

    public void writeExternalCrashEvent(final SessionEventData sessionEventData) {
        this.backgroundWorker.submit(new Callable<Void>() { // from class: com.crashlytics.android.core.CrashlyticsController.15
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    return null;
                }
                CrashlyticsController.this.doWriteExternalCrashEvent(sessionEventData);
                return null;
            }
        });
    }

    public void doWriteExternalCrashEvent(SessionEventData sessionEventData) throws IOException {
        Throwable th;
        ClsFileOutputStream clsFileOutputStream;
        Exception e;
        String previousSessionId;
        CodedOutputStream newInstance;
        CodedOutputStream codedOutputStream = null;
        try {
            previousSessionId = getPreviousSessionId();
        } catch (Exception e2) {
            e = e2;
            clsFileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            clsFileOutputStream = null;
        }
        if (previousSessionId == null) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a native crash while no session was open.", null);
            CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
            return;
        }
        boolean z = false;
        recordFatalExceptionAnswersEvent(previousSessionId, String.format(Locale.US, "<native-crash [%s (%s)]>", sessionEventData.signal.code, sessionEventData.signal.name));
        if (sessionEventData.binaryImages != null && sessionEventData.binaryImages.length > 0) {
            z = true;
        }
        String str = z ? SESSION_FATAL_TAG : SESSION_EVENT_MISSING_BINARY_IMGS_TAG;
        clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), previousSessionId + str);
        try {
            try {
                newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            NativeCrashWriter.writeNativeCrash(sessionEventData, new LogFileManager(this.crashlyticsCore.getContext(), this.logFileDirectoryProvider, previousSessionId), new MetaDataStore(getFilesDir()).readKeyData(previousSessionId), newInstance);
            CommonUtils.flushOrLog(newInstance, "Failed to flush to session begin file.");
        } catch (Exception e4) {
            e = e4;
            codedOutputStream = newInstance;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Throwable th4) {
            th = th4;
            codedOutputStream = newInstance;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            throw th;
        }
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
    }

    public void doWriteNonFatal(Date date, Thread thread, Throwable th) {
        Throwable th2;
        ClsFileOutputStream clsFileOutputStream;
        Exception e;
        CodedOutputStream newInstance;
        String currentSessionId = getCurrentSessionId();
        CodedOutputStream codedOutputStream = null;
        if (currentSessionId == null) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a non-fatal exception while no session was open.", null);
            return;
        }
        recordLoggedExceptionAnswersEvent(currentSessionId, th.getClass().getName());
        try {
            try {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics is logging non-fatal exception \"" + th + "\" from thread " + thread.getName());
                clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), currentSessionId + SESSION_NON_FATAL_TAG + CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement()));
                try {
                    newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th3) {
                th2 = th3;
            }
            try {
                writeSessionEvent(newInstance, date, thread, th, EVENT_TYPE_LOGGED, false);
                CommonUtils.flushOrLog(newInstance, "Failed to flush to non-fatal file.");
            } catch (Exception e3) {
                e = e3;
                codedOutputStream = newInstance;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                trimSessionEventFiles(currentSessionId, 64);
            } catch (Throwable th4) {
                th2 = th4;
                codedOutputStream = newInstance;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                throw th2;
            }
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
        } catch (Throwable th5) {
            th2 = th5;
            clsFileOutputStream = null;
        }
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
        try {
            trimSessionEventFiles(currentSessionId, 64);
        } catch (Exception e5) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred when trimming non-fatal files.", e5);
        }
    }

    private void writeBeginSession(String str, Date date) throws Exception {
        Throwable th;
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStream = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_BEGIN_TAG);
            try {
                CodedOutputStream newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                try {
                    SessionProtobufHelper.writeBeginSession(newInstance, str, String.format(Locale.US, GENERATOR_FORMAT, this.crashlyticsCore.getVersion()), date.getTime() / 1000);
                    CommonUtils.flushOrLog(newInstance, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
                } catch (Throwable th2) {
                    th = th2;
                    codedOutputStream = newInstance;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
        }
    }

    private void writeSessionApp(String str) throws Exception {
        CodedOutputStream codedOutputStream;
        ClsFileOutputStream clsFileOutputStream;
        Throwable th;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_APP_TAG);
            try {
                codedOutputStream = CodedOutputStream.newInstance(clsFileOutputStream);
                try {
                    SessionProtobufHelper.writeSessionApp(codedOutputStream, this.idManager.getAppIdentifier(), this.appData.apiKey, this.appData.versionCode, this.appData.versionName, this.idManager.getAppInstallIdentifier(), DeliveryMechanism.determineFrom(this.appData.installerPackageName).getId(), this.unityVersion);
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                codedOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            codedOutputStream = null;
        }
    }

    private void writeSessionOS(String str) throws Exception {
        Throwable th;
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStream = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_OS_TAG);
            try {
                CodedOutputStream newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                try {
                    SessionProtobufHelper.writeSessionOS(newInstance, CommonUtils.isRooted(this.crashlyticsCore.getContext()));
                    CommonUtils.flushOrLog(newInstance, "Failed to flush to session OS file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
                } catch (Throwable th2) {
                    th = th2;
                    codedOutputStream = newInstance;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session OS file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
        }
    }

    private void writeSessionDevice(String str) throws Exception {
        CodedOutputStream codedOutputStream;
        ClsFileOutputStream clsFileOutputStream;
        Throwable th;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_DEVICE_TAG);
            try {
                codedOutputStream = CodedOutputStream.newInstance(clsFileOutputStream);
                try {
                    Context context = this.crashlyticsCore.getContext();
                    StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                    SessionProtobufHelper.writeSessionDevice(codedOutputStream, this.idManager.getDeviceUUID(), CommonUtils.getCpuArchitectureInt(), Build.MODEL, Runtime.getRuntime().availableProcessors(), CommonUtils.getTotalRamInBytes(), statFs.getBlockCount() * statFs.getBlockSize(), CommonUtils.isEmulator(context), this.idManager.getDeviceIdentifiers(), CommonUtils.getDeviceState(context), Build.MANUFACTURER, Build.PRODUCT);
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                codedOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            codedOutputStream = null;
        }
    }

    private void writeSessionUser(String str) throws Exception {
        Throwable th;
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStream = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(getFilesDir(), str + SESSION_USER_TAG);
            try {
                CodedOutputStream newInstance = CodedOutputStream.newInstance(clsFileOutputStream);
                try {
                    UserMetaData userMetaData = getUserMetaData(str);
                    if (userMetaData.isEmpty()) {
                        CommonUtils.flushOrLog(newInstance, "Failed to flush session user file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                        return;
                    }
                    SessionProtobufHelper.writeSessionUser(newInstance, userMetaData.id, userMetaData.name, userMetaData.email);
                    CommonUtils.flushOrLog(newInstance, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                } catch (Throwable th2) {
                    th = th2;
                    codedOutputStream = newInstance;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
        }
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeSessionEvent(com.crashlytics.android.core.CodedOutputStream r26, java.util.Date r27, java.lang.Thread r28, java.lang.Throwable r29, java.lang.String r30, boolean r31) throws java.lang.Exception {
        /*
            r25 = this;
            r0 = r25
            com.crashlytics.android.core.TrimmedThrowableData r5 = new com.crashlytics.android.core.TrimmedThrowableData
            com.crashlytics.android.core.StackTraceTrimmingStrategy r1 = r0.stackTraceTrimmingStrategy
            r2 = r29
            r5.<init>(r2, r1)
            com.crashlytics.android.core.CrashlyticsCore r1 = r0.crashlyticsCore
            android.content.Context r1 = r1.getContext()
            long r2 = r27.getTime()
            r6 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r6
            java.lang.Float r16 = io.fabric.sdk.android.services.common.CommonUtils.getBatteryLevel(r1)
            com.crashlytics.android.core.DevicePowerStateListener r4 = r0.devicePowerStateListener
            boolean r4 = r4.isPowerConnected()
            int r17 = io.fabric.sdk.android.services.common.CommonUtils.getBatteryVelocity(r1, r4)
            boolean r18 = io.fabric.sdk.android.services.common.CommonUtils.getProximitySensorEnabled(r1)
            android.content.res.Resources r4 = r1.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r13 = r4.orientation
            long r6 = io.fabric.sdk.android.services.common.CommonUtils.getTotalRamInBytes()
            long r8 = io.fabric.sdk.android.services.common.CommonUtils.calculateFreeRamInBytes(r1)
            long r19 = r6 - r8
            java.io.File r4 = android.os.Environment.getDataDirectory()
            java.lang.String r4 = r4.getPath()
            long r21 = io.fabric.sdk.android.services.common.CommonUtils.calculateUsedDiskSpaceInBytes(r4)
            java.lang.String r4 = r1.getPackageName()
            android.app.ActivityManager$RunningAppProcessInfo r12 = io.fabric.sdk.android.services.common.CommonUtils.getAppProcessInfo(r4, r1)
            java.util.LinkedList r9 = new java.util.LinkedList
            r9.<init>()
            java.lang.StackTraceElement[] r7 = r5.stacktrace
            com.crashlytics.android.core.AppData r4 = r0.appData
            java.lang.String r15 = r4.buildId
            io.fabric.sdk.android.services.common.IdManager r4 = r0.idManager
            java.lang.String r14 = r4.getAppIdentifier()
            r4 = 0
            if (r31 == 0) goto L_0x00a1
            java.util.Map r8 = java.lang.Thread.getAllStackTraces()
            int r10 = r8.size()
            java.lang.Thread[] r10 = new java.lang.Thread[r10]
            java.util.Set r8 = r8.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x0078:
            boolean r11 = r8.hasNext()
            if (r11 == 0) goto L_0x009e
            java.lang.Object r11 = r8.next()
            java.util.Map$Entry r11 = (java.util.Map.Entry) r11
            java.lang.Object r23 = r11.getKey()
            java.lang.Thread r23 = (java.lang.Thread) r23
            r10[r4] = r23
            com.crashlytics.android.core.StackTraceTrimmingStrategy r6 = r0.stackTraceTrimmingStrategy
            java.lang.Object r11 = r11.getValue()
            java.lang.StackTraceElement[] r11 = (java.lang.StackTraceElement[]) r11
            java.lang.StackTraceElement[] r6 = r6.getTrimmedStackTrace(r11)
            r9.add(r6)
            r6 = 1
            int r4 = r4 + r6
            goto L_0x0078
        L_0x009e:
            r6 = 1
            r8 = r10
            goto L_0x00a5
        L_0x00a1:
            r6 = 1
            java.lang.Thread[] r4 = new java.lang.Thread[r4]
            r8 = r4
        L_0x00a5:
            java.lang.String r4 = "com.crashlytics.CollectCustomKeys"
            boolean r1 = io.fabric.sdk.android.services.common.CommonUtils.getBooleanResourceValue(r1, r4, r6)
            if (r1 != 0) goto L_0x00b4
            java.util.TreeMap r1 = new java.util.TreeMap
            r1.<init>()
        L_0x00b2:
            r10 = r1
            goto L_0x00c8
        L_0x00b4:
            com.crashlytics.android.core.CrashlyticsCore r1 = r0.crashlyticsCore
            java.util.Map r1 = r1.getAttributes()
            if (r1 == 0) goto L_0x00b2
            int r4 = r1.size()
            if (r4 <= r6) goto L_0x00b2
            java.util.TreeMap r4 = new java.util.TreeMap
            r4.<init>(r1)
            r10 = r4
        L_0x00c8:
            com.crashlytics.android.core.LogFileManager r11 = r0.logFileManager
            r1 = r26
            r4 = r30
            r6 = r28
            com.crashlytics.android.core.SessionProtobufHelper.writeSessionEvent(r1, r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r21)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.core.CrashlyticsController.writeSessionEvent(com.crashlytics.android.core.CodedOutputStream, java.util.Date, java.lang.Thread, java.lang.Throwable, java.lang.String, boolean):void");
    }

    private void writeSessionPartsToSessionFile(File file, String str, int i) {
        Logger logger = Fabric.getLogger();
        logger.d(CrashlyticsCore.TAG, "Collecting session parts for ID " + str);
        File[] listFilesMatching = listFilesMatching(new FileNameContainsFilter(str + SESSION_FATAL_TAG));
        boolean z = listFilesMatching != null && listFilesMatching.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has fatal exception: %s", str, Boolean.valueOf(z)));
        File[] listFilesMatching2 = listFilesMatching(new FileNameContainsFilter(str + SESSION_NON_FATAL_TAG));
        boolean z2 = listFilesMatching2 != null && listFilesMatching2.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has non-fatal exceptions: %s", str, Boolean.valueOf(z2)));
        if (z || z2) {
            synthesizeSessionFile(file, str, getTrimmedNonFatalFiles(str, listFilesMatching2, i), z ? listFilesMatching[0] : null);
        } else {
            Logger logger2 = Fabric.getLogger();
            logger2.d(CrashlyticsCore.TAG, "No events present for session ID " + str);
        }
        Logger logger3 = Fabric.getLogger();
        logger3.d(CrashlyticsCore.TAG, "Removing session part files for ID " + str);
        deleteSessionPartFilesFor(str);
    }

    private void synthesizeSessionFile(File file, String str, File[] fileArr, File file2) {
        Throwable th;
        ClsFileOutputStream clsFileOutputStream;
        Exception e;
        boolean z = file2 != null;
        File fatalSessionFilesDir = z ? getFatalSessionFilesDir() : getNonFatalSessionFilesDir();
        if (!fatalSessionFilesDir.exists()) {
            fatalSessionFilesDir.mkdirs();
        }
        CodedOutputStream codedOutputStream = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(fatalSessionFilesDir, str);
            try {
                try {
                    codedOutputStream = CodedOutputStream.newInstance(clsFileOutputStream);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Collecting SessionStart data for session ID " + str);
                writeToCosFromFile(codedOutputStream, file);
                codedOutputStream.writeUInt64(4, new Date().getTime() / 1000);
                codedOutputStream.writeBool(5, z);
                codedOutputStream.writeUInt32(11, 1);
                codedOutputStream.writeEnum(12, 3);
                writeInitialPartsTo(codedOutputStream, str);
                writeNonFatalEventsTo(codedOutputStream, fileArr, str);
                if (z) {
                    writeToCosFromFile(codedOutputStream, file2);
                }
                CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
            } catch (Exception e3) {
                e = e3;
                codedOutputStream = codedOutputStream;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Failed to write session file for session ID: " + str, e);
                CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                closeWithoutRenamingOrLog(clsFileOutputStream);
            } catch (Throwable th3) {
                th = th3;
                CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            codedOutputStream = null;
            clsFileOutputStream = null;
        }
    }

    private static void writeNonFatalEventsTo(CodedOutputStream codedOutputStream, File[] fileArr, String str) {
        Arrays.sort(fileArr, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File file : fileArr) {
            try {
                Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", str, file.getName()));
                writeToCosFromFile(codedOutputStream, file);
            } catch (Exception e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeInitialPartsTo(CodedOutputStream codedOutputStream, String str) throws IOException {
        String[] strArr;
        for (String str2 : INITIAL_SESSION_PART_TAGS) {
            File[] listFilesMatching = listFilesMatching(new FileNameContainsFilter(str + str2));
            if (listFilesMatching.length == 0) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Can't find " + str2 + " data for session ID " + str, null);
            } else {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Collecting " + str2 + " data for session ID " + str);
                writeToCosFromFile(codedOutputStream, listFilesMatching[0]);
            }
        }
    }

    private static void writeToCosFromFile(CodedOutputStream codedOutputStream, File file) throws IOException {
        Throwable th;
        FileInputStream fileInputStream;
        if (!file.exists()) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to include a file that doesn't exist: " + file.getName(), null);
            return;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                copyToCodedOutputStream(fileInputStream, codedOutputStream, (int) file.length());
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
            } catch (Throwable th2) {
                th = th2;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    private static void copyToCodedOutputStream(InputStream inputStream, CodedOutputStream codedOutputStream, int i) throws IOException {
        int read;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < bArr.length && (read = inputStream.read(bArr, i2, bArr.length - i2)) >= 0) {
            i2 += read;
        }
        codedOutputStream.writeRawBytes(bArr);
    }

    private UserMetaData getUserMetaData(String str) {
        if (isHandlingException()) {
            return new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
        }
        return new MetaDataStore(getFilesDir()).readUserData(str);
    }

    boolean isHandlingException() {
        return this.crashHandler != null && this.crashHandler.isHandlingException();
    }

    File getFilesDir() {
        return this.fileStore.getFilesDir();
    }

    File getFatalSessionFilesDir() {
        return new File(getFilesDir(), FATAL_SESSION_DIR);
    }

    File getNonFatalSessionFilesDir() {
        return new File(getFilesDir(), NONFATAL_SESSION_DIR);
    }

    File getInvalidFilesDir() {
        return new File(getFilesDir(), INVALID_CLS_CACHE_DIR);
    }

    public boolean shouldPromptUserBeforeSendingCrashReports(SettingsData settingsData) {
        return settingsData != null && settingsData.featuresData.promptEnabled && !this.preferenceManager.shouldAlwaysSendReports();
    }

    private CreateReportSpiCall getCreateReportSpiCall(String str) {
        return new DefaultCreateReportSpiCall(this.crashlyticsCore, CommonUtils.getStringsFileValue(this.crashlyticsCore.getContext(), CRASHLYTICS_API_ENDPOINT), str, this.httpRequestFactory);
    }

    public void sendSessionReports(SettingsData settingsData) {
        if (settingsData == null) {
            Fabric.getLogger().w(CrashlyticsCore.TAG, "Cannot send reports. Settings are unavailable.");
            return;
        }
        Context context = this.crashlyticsCore.getContext();
        ReportUploader reportUploader = new ReportUploader(this.appData.apiKey, getCreateReportSpiCall(settingsData.appData.reportsUrl), this.reportFilesProvider, this.handlingExceptionCheck);
        for (File file : listCompleteSessionFiles()) {
            this.backgroundWorker.submit(new SendReportRunnable(context, new SessionReport(file, SEND_AT_CRASHTIME_HEADER), reportUploader));
        }
    }

    private static void recordLoggedExceptionAnswersEvent(String str, String str2) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new Crash.LoggedException(str, str2));
        }
    }

    private static void recordFatalExceptionAnswersEvent(String str, String str2) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Answers is not available");
        } else {
            answers.onException(new Crash.FatalException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class ReportUploaderHandlingExceptionCheck implements ReportUploader.HandlingExceptionCheck {
        private ReportUploaderHandlingExceptionCheck() {
            CrashlyticsController.this = r1;
        }

        @Override // com.crashlytics.android.core.ReportUploader.HandlingExceptionCheck
        public boolean isHandlingException() {
            return CrashlyticsController.this.isHandlingException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class ReportUploaderFilesProvider implements ReportUploader.ReportFilesProvider {
        private ReportUploaderFilesProvider() {
            CrashlyticsController.this = r1;
        }

        @Override // com.crashlytics.android.core.ReportUploader.ReportFilesProvider
        public File[] getCompleteSessionFiles() {
            return CrashlyticsController.this.listCompleteSessionFiles();
        }

        @Override // com.crashlytics.android.core.ReportUploader.ReportFilesProvider
        public File[] getInvalidSessionFiles() {
            return CrashlyticsController.this.getInvalidFilesDir().listFiles();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class PrivacyDialogCheck implements ReportUploader.SendCheck {
        private final Kit kit;
        private final PreferenceManager preferenceManager;
        private final PromptSettingsData promptData;

        public PrivacyDialogCheck(Kit kit, PreferenceManager preferenceManager, PromptSettingsData promptSettingsData) {
            this.kit = kit;
            this.preferenceManager = preferenceManager;
            this.promptData = promptSettingsData;
        }

        @Override // com.crashlytics.android.core.ReportUploader.SendCheck
        public boolean canSendReports() {
            Activity currentActivity = this.kit.getFabric().getCurrentActivity();
            if (currentActivity == null || currentActivity.isFinishing()) {
                return true;
            }
            final CrashPromptDialog create = CrashPromptDialog.create(currentActivity, this.promptData, new CrashPromptDialog.AlwaysSendCallback() { // from class: com.crashlytics.android.core.CrashlyticsController.PrivacyDialogCheck.1
                @Override // com.crashlytics.android.core.CrashPromptDialog.AlwaysSendCallback
                public void sendUserReportsWithoutPrompting(boolean z) {
                    PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(z);
                }
            });
            currentActivity.runOnUiThread(new Runnable() { // from class: com.crashlytics.android.core.CrashlyticsController.PrivacyDialogCheck.2
                @Override // java.lang.Runnable
                public void run() {
                    create.show();
                }
            });
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Waiting for user opt-in.");
            create.await();
            return create.getOptIn();
        }
    }

    /* loaded from: classes.dex */
    public static final class SendReportRunnable implements Runnable {
        private final Context context;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CommonUtils.canTryConnection(this.context)) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Attempting to send crash report at time of crash...");
                this.reportUploader.forceUpload(this.report);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class LogFileDirectoryProvider implements LogFileManager.DirectoryProvider {
        private static final String LOG_FILES_DIR = "log-files";
        private final FileStore rootFileStore;

        public LogFileDirectoryProvider(FileStore fileStore) {
            this.rootFileStore = fileStore;
        }

        @Override // com.crashlytics.android.core.LogFileManager.DirectoryProvider
        public File getLogFileDir() {
            File file = new File(this.rootFileStore.getFilesDir(), LOG_FILES_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
    }
}
