package com.naver.temy123.baseproject.base.Utils;

/* loaded from: classes.dex */
public class HWException extends Exception {
    public static HWException newInstance(String str) {
        return new HWException("======== HWException ========\n" + str);
    }

    private HWException(String str) {
        super(str);
    }
}
