package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/* loaded from: classes.dex */
abstract class AbstractMultipartForm {
    final String boundary;
    final Charset charset;
    private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
    private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
    private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");

    protected abstract void formatMultipartHeader(FormBodyPart formBodyPart, OutputStream outputStream) throws IOException;

    public abstract List<FormBodyPart> getBodyParts();

    private static ByteArrayBuffer encode(Charset charset, String str) {
        ByteBuffer encode = charset.encode(CharBuffer.wrap(str));
        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(encode.remaining());
        byteArrayBuffer.append(encode.array(), encode.position(), encode.remaining());
        return byteArrayBuffer;
    }

    private static void writeBytes(ByteArrayBuffer byteArrayBuffer, OutputStream outputStream) throws IOException {
        outputStream.write(byteArrayBuffer.buffer(), 0, byteArrayBuffer.length());
    }

    private static void writeBytes(String str, Charset charset, OutputStream outputStream) throws IOException {
        writeBytes(encode(charset, str), outputStream);
    }

    private static void writeBytes(String str, OutputStream outputStream) throws IOException {
        writeBytes(encode(MIME.DEFAULT_CHARSET, str), outputStream);
    }

    public static void writeField(MinimalField minimalField, OutputStream outputStream) throws IOException {
        writeBytes(minimalField.getName(), outputStream);
        writeBytes(FIELD_SEP, outputStream);
        writeBytes(minimalField.getBody(), outputStream);
        writeBytes(CR_LF, outputStream);
    }

    public static void writeField(MinimalField minimalField, Charset charset, OutputStream outputStream) throws IOException {
        writeBytes(minimalField.getName(), charset, outputStream);
        writeBytes(FIELD_SEP, outputStream);
        writeBytes(minimalField.getBody(), charset, outputStream);
        writeBytes(CR_LF, outputStream);
    }

    public AbstractMultipartForm(Charset charset, String str) {
        Args.notNull(str, "Multipart boundary");
        this.charset = charset == null ? MIME.DEFAULT_CHARSET : charset;
        this.boundary = str;
    }

    public AbstractMultipartForm(String str) {
        this(null, str);
    }

    void doWriteTo(OutputStream outputStream, boolean z) throws IOException {
        ByteArrayBuffer encode = encode(this.charset, this.boundary);
        for (FormBodyPart formBodyPart : getBodyParts()) {
            writeBytes(TWO_DASHES, outputStream);
            writeBytes(encode, outputStream);
            writeBytes(CR_LF, outputStream);
            formatMultipartHeader(formBodyPart, outputStream);
            writeBytes(CR_LF, outputStream);
            if (z) {
                formBodyPart.getBody().writeTo(outputStream);
            }
            writeBytes(CR_LF, outputStream);
        }
        writeBytes(TWO_DASHES, outputStream);
        writeBytes(encode, outputStream);
        writeBytes(TWO_DASHES, outputStream);
        writeBytes(CR_LF, outputStream);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        doWriteTo(outputStream, true);
    }

    public long getTotalLength() {
        ByteArrayOutputStream byteArrayOutputStream;
        long j = 0;
        for (FormBodyPart formBodyPart : getBodyParts()) {
            long contentLength = formBodyPart.getBody().getContentLength();
            if (contentLength < 0) {
                return -1L;
            }
            j += contentLength;
        }
        try {
            doWriteTo(new ByteArrayOutputStream(), false);
            return j + byteArrayOutputStream.toByteArray().length;
        } catch (IOException unused) {
            return -1L;
        }
    }
}
