package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class QueueFileLogStore implements FileLogStore {
    private QueueFile logFile;
    private final int maxLogSize;
    private final File workingFile;

    public QueueFileLogStore(File file, int i) {
        this.workingFile = file;
        this.maxLogSize = i;
    }

    @Override // com.crashlytics.android.core.FileLogStore
    public void writeToLog(long j, String str) {
        openLogFile();
        doWriteToLog(j, str);
    }

    @Override // com.crashlytics.android.core.FileLogStore
    public ByteString getLogAsByteString() {
        if (!this.workingFile.exists()) {
            return null;
        }
        openLogFile();
        if (this.logFile == null) {
            return null;
        }
        final int[] iArr = {0};
        final byte[] bArr = new byte[this.logFile.usedBytes()];
        try {
            this.logFile.forEach(new QueueFile.ElementReader() { // from class: com.crashlytics.android.core.QueueFileLogStore.1
                @Override // io.fabric.sdk.android.services.common.QueueFile.ElementReader
                public void read(InputStream inputStream, int i) throws IOException {
                    try {
                        inputStream.read(bArr, iArr[0], i);
                        int[] iArr2 = iArr;
                        iArr2[0] = iArr2[0] + i;
                    } finally {
                        inputStream.close();
                    }
                }
            });
        } catch (IOException e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "A problem occurred while reading the Crashlytics log file.", e);
        }
        return ByteString.copyFrom(bArr, 0, iArr[0]);
    }

    @Override // com.crashlytics.android.core.FileLogStore
    public void closeLogFile() {
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    @Override // com.crashlytics.android.core.FileLogStore
    public void deleteLogFile() {
        closeLogFile();
        this.workingFile.delete();
    }

    private void openLogFile() {
        if (this.logFile == null) {
            try {
                this.logFile = new QueueFile(this.workingFile);
            } catch (IOException e) {
                Logger logger = Fabric.getLogger();
                logger.e(CrashlyticsCore.TAG, "Could not open log file: " + this.workingFile, e);
            }
        }
    }

    private void doWriteToLog(long j, String str) {
        int i;
        if (this.logFile != null) {
            if (str == null) {
                str = "null";
            }
            try {
                if (str.length() > this.maxLogSize / 4) {
                    str = "..." + str.substring(str.length() - i);
                }
                this.logFile.add(String.format(Locale.US, "%d %s%n", Long.valueOf(j), str.replaceAll("\r", " ").replaceAll("\n", " ")).getBytes("UTF-8"));
                while (!this.logFile.isEmpty() && this.logFile.usedBytes() > this.maxLogSize) {
                    this.logFile.remove();
                }
            } catch (IOException e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }
}
