package cz.msebera.android.httpclient;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

/* loaded from: classes.dex */
public interface ExceptionLogger {
    public static final ExceptionLogger NO_OP = new ExceptionLogger() { // from class: cz.msebera.android.httpclient.ExceptionLogger.1
        @Override // cz.msebera.android.httpclient.ExceptionLogger
        public void log(Exception exc) {
        }
    };
    public static final ExceptionLogger STD_ERR = new ExceptionLogger() { // from class: cz.msebera.android.httpclient.ExceptionLogger.2
        @Override // cz.msebera.android.httpclient.ExceptionLogger
        public void log(Exception exc) {
            ThrowableExtension.printStackTrace(exc);
        }
    };

    void log(Exception exc);
}
