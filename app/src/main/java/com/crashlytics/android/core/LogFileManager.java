package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.util.Set;

/* loaded from: classes.dex */
public class LogFileManager {
    private static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
    private static final String LOGFILE_EXT = ".temp";
    private static final String LOGFILE_PREFIX = "crashlytics-userlog-";
    static final int MAX_LOG_SIZE = 65536;
    private static final NoopLogStore NOOP_LOG_STORE = new NoopLogStore();
    private final Context context;
    private FileLogStore currentLog;
    private final DirectoryProvider directoryProvider;

    /* loaded from: classes.dex */
    public interface DirectoryProvider {
        File getLogFileDir();
    }

    public LogFileManager(Context context, DirectoryProvider directoryProvider) {
        this(context, directoryProvider, null);
    }

    public LogFileManager(Context context, DirectoryProvider directoryProvider, String str) {
        this.context = context;
        this.directoryProvider = directoryProvider;
        this.currentLog = NOOP_LOG_STORE;
        setCurrentSession(str);
    }

    public final void setCurrentSession(String str) {
        this.currentLog.closeLogFile();
        this.currentLog = NOOP_LOG_STORE;
        if (str != null) {
            if (!CommonUtils.getBooleanResourceValue(this.context, COLLECT_CUSTOM_LOGS, true)) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Preferences requested no custom logs. Aborting log file creation.");
            } else {
                setLogFile(getWorkingFileForSession(str), 65536);
            }
        }
    }

    public void writeToLog(long j, String str) {
        this.currentLog.writeToLog(j, str);
    }

    public ByteString getByteStringForLog() {
        return this.currentLog.getLogAsByteString();
    }

    public void clearLog() {
        this.currentLog.deleteLogFile();
    }

    public void discardOldLogFiles(Set<String> set) {
        File[] listFiles = this.directoryProvider.getLogFileDir().listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!set.contains(getSessionIdForFile(file))) {
                    file.delete();
                }
            }
        }
    }

    void setLogFile(File file, int i) {
        this.currentLog = new QueueFileLogStore(file, i);
    }

    private File getWorkingFileForSession(String str) {
        return new File(this.directoryProvider.getLogFileDir(), LOGFILE_PREFIX + str + LOGFILE_EXT);
    }

    private String getSessionIdForFile(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(LOGFILE_EXT);
        return lastIndexOf == -1 ? name : name.substring(LOGFILE_PREFIX.length(), lastIndexOf);
    }

    /* loaded from: classes.dex */
    public static final class NoopLogStore implements FileLogStore {
        @Override // com.crashlytics.android.core.FileLogStore
        public void closeLogFile() {
        }

        @Override // com.crashlytics.android.core.FileLogStore
        public void deleteLogFile() {
        }

        @Override // com.crashlytics.android.core.FileLogStore
        public ByteString getLogAsByteString() {
            return null;
        }

        @Override // com.crashlytics.android.core.FileLogStore
        public void writeToLog(long j, String str) {
        }

        private NoopLogStore() {
        }
    }
}
