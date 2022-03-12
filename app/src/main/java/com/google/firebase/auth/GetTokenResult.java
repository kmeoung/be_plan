package com.google.firebase.auth;

import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public class GetTokenResult {
    private String zzdzn;

    public GetTokenResult(String str) {
        this.zzdzn = str;
    }

    @Nullable
    public String getToken() {
        return this.zzdzn;
    }
}
