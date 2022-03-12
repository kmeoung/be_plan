package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Immutable
/* loaded from: classes.dex */
public class Wire {
    private final String id;
    public HttpClientAndroidLog log;

    public Wire(HttpClientAndroidLog httpClientAndroidLog, String str) {
        this.log = httpClientAndroidLog;
        this.id = str;
    }

    public Wire(HttpClientAndroidLog httpClientAndroidLog) {
        this(httpClientAndroidLog, "");
    }

    private void wire(String str, InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                break;
            } else if (read == 13) {
                sb.append("[\\r]");
            } else if (read == 10) {
                sb.append("[\\n]\"");
                sb.insert(0, "\"");
                sb.insert(0, str);
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                httpClientAndroidLog.debug(this.id + " " + sb.toString());
                sb.setLength(0);
            } else if (read < 32 || read > 127) {
                sb.append("[0x");
                sb.append(Integer.toHexString(read));
                sb.append("]");
            } else {
                sb.append((char) read);
            }
        }
        if (sb.length() > 0) {
            sb.append(TokenParser.DQUOTE);
            sb.insert(0, TokenParser.DQUOTE);
            sb.insert(0, str);
            HttpClientAndroidLog httpClientAndroidLog2 = this.log;
            httpClientAndroidLog2.debug(this.id + " " + sb.toString());
        }
    }

    public boolean enabled() {
        return this.log.isDebugEnabled();
    }

    public void output(InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Output");
        wire(">> ", inputStream);
    }

    public void input(InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Input");
        wire("<< ", inputStream);
    }

    public void output(byte[] bArr, int i, int i2) throws IOException {
        Args.notNull(bArr, "Output");
        wire(">> ", new ByteArrayInputStream(bArr, i, i2));
    }

    public void input(byte[] bArr, int i, int i2) throws IOException {
        Args.notNull(bArr, "Input");
        wire("<< ", new ByteArrayInputStream(bArr, i, i2));
    }

    public void output(byte[] bArr) throws IOException {
        Args.notNull(bArr, "Output");
        wire(">> ", new ByteArrayInputStream(bArr));
    }

    public void input(byte[] bArr) throws IOException {
        Args.notNull(bArr, "Input");
        wire("<< ", new ByteArrayInputStream(bArr));
    }

    public void output(int i) throws IOException {
        output(new byte[]{(byte) i});
    }

    public void input(int i) throws IOException {
        input(new byte[]{(byte) i});
    }

    public void output(String str) throws IOException {
        Args.notNull(str, "Output");
        output(str.getBytes());
    }

    public void input(String str) throws IOException {
        Args.notNull(str, "Input");
        input(str.getBytes());
    }
}
