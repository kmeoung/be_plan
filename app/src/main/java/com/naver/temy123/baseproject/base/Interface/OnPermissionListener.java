package com.naver.temy123.baseproject.base.Interface;

import java.util.List;

/* loaded from: classes.dex */
public interface OnPermissionListener {
    void onDenied(int i, List<String> list);

    void onGranted(int i, List<String> list);
}
