package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class Utils {
    private static final FilenameFilter ALL_FILES_FILTER = new FilenameFilter() { // from class: com.crashlytics.android.core.Utils.1
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return true;
        }
    };

    private Utils() {
    }

    public static int capFileCount(File file, int i, Comparator<File> comparator) {
        return capFileCount(file, ALL_FILES_FILTER, i, comparator);
    }

    public static int capFileCount(File file, FilenameFilter filenameFilter, int i, Comparator<File> comparator) {
        File[] listFiles = file.listFiles(filenameFilter);
        if (listFiles == null) {
            return 0;
        }
        int length = listFiles.length;
        Arrays.sort(listFiles, comparator);
        for (File file2 : listFiles) {
            if (length <= i) {
                return length;
            }
            file2.delete();
            length--;
        }
        return length;
    }
}
