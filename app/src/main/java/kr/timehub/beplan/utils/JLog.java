package kr.timehub.beplan.utils;

import android.util.Log;

/* loaded from: classes.dex */
public class JLog {
    private static boolean DEBUG = false;
    private static final String TAG = "jinu";

    public static boolean isDebugMode() {
        return DEBUG;
    }

    public static void e() {
        if (DEBUG) {
            Log.e(TAG, buildLogMsg());
        }
    }

    public static void e(String str) {
        if (DEBUG) {
            Log.e(TAG, buildLogMsg(str));
        }
    }

    public static void i() {
        if (DEBUG) {
            Log.i(TAG, buildLogMsg());
        }
    }

    public static void i(String str) {
        if (DEBUG) {
            Log.i(TAG, buildLogMsg(str));
        }
    }

    public static void d() {
        if (DEBUG) {
            Log.d(TAG, buildLogMsg());
        }
    }

    public static void d(String str) {
        if (DEBUG) {
            Log.d(TAG, buildLogMsg(str));
        }
    }

    private static String buildLogMsg() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        return stackTraceElement.getFileName().replace(".java", "") + " " + stackTraceElement.getMethodName() + "(): ";
    }

    private static String buildLogMsg(String str) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        return stackTraceElement.getFileName().replace(".java", "") + " " + stackTraceElement.getMethodName() + "(): " + str;
    }
}
