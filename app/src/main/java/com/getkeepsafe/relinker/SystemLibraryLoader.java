package com.getkeepsafe.relinker;

import android.os.Build;
import com.getkeepsafe.relinker.ReLinker;

/* loaded from: classes.dex */
public final class SystemLibraryLoader implements ReLinker.LibraryLoader {
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadLibrary(String str) {
        System.loadLibrary(str);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadPath(String str) {
        System.load(str);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String mapLibraryName(String str) {
        return (!str.startsWith("lib") || !str.endsWith(".so")) ? System.mapLibraryName(str) : str;
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String unmapLibraryName(String str) {
        return str.substring(3, str.length() - 3);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String[] supportedAbis() {
        if (Build.VERSION.SDK_INT < 21 || Build.SUPPORTED_ABIS.length <= 0) {
            return !TextUtils.isEmpty(Build.CPU_ABI2) ? new String[]{Build.CPU_ABI, Build.CPU_ABI2} : new String[]{Build.CPU_ABI};
        }
        return Build.SUPPORTED_ABIS;
    }
}
