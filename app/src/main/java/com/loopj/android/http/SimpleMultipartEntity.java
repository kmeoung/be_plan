package com.loopj.android.http;

import android.text.TextUtils;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes.dex */
public class SimpleMultipartEntity implements HttpEntity {
    private static final String LOG_TAG = "SimpleMultipartEntity";
    private final String boundary;
    private final byte[] boundaryEnd;
    private final byte[] boundaryLine;
    private long bytesWritten;
    private boolean isRepeatable;
    private final ResponseHandlerInterface progressHandler;
    private long totalSize;
    private static final String STR_CR_LF = "\r\n";
    private static final byte[] CR_LF = STR_CR_LF.getBytes();
    private static final byte[] TRANSFER_ENCODING_BINARY = "Content-Transfer-Encoding: binary\r\n".getBytes();
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final List<FilePart> fileParts = new ArrayList();
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private String normalizeContentType(String str) {
        return str == null ? "application/octet-stream" : str;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public Header getContentEncoding() {
        return null;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public boolean isChunked() {
        return false;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    public SimpleMultipartEntity(ResponseHandlerInterface responseHandlerInterface) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            sb.append(MULTIPART_CHARS[random.nextInt(MULTIPART_CHARS.length)]);
        }
        this.boundary = sb.toString();
        this.boundaryLine = ("--" + this.boundary + STR_CR_LF).getBytes();
        this.boundaryEnd = ("--" + this.boundary + "--" + STR_CR_LF).getBytes();
        this.progressHandler = responseHandlerInterface;
    }

    public void addPart(String str, String str2, String str3) {
        try {
            this.out.write(this.boundaryLine);
            this.out.write(createContentDisposition(str));
            this.out.write(createContentType(str3));
            this.out.write(CR_LF);
            this.out.write(str2.getBytes());
            this.out.write(CR_LF);
        } catch (IOException e) {
            AsyncHttpClient.log.e(LOG_TAG, "addPart ByteArrayOutputStream exception", e);
        }
    }

    public void addPartWithCharset(String str, String str2, String str3) {
        if (str3 == null) {
            str3 = "UTF-8";
        }
        addPart(str, str2, "text/plain; charset=" + str3);
    }

    public void addPart(String str, String str2) {
        addPartWithCharset(str, str2, null);
    }

    public void addPart(String str, File file) {
        addPart(str, file, (String) null);
    }

    public void addPart(String str, File file, String str2) {
        this.fileParts.add(new FilePart(str, file, normalizeContentType(str2)));
    }

    public void addPart(String str, File file, String str2, String str3) {
        this.fileParts.add(new FilePart(str, file, normalizeContentType(str2), str3));
    }

    public void addPart(String str, String str2, InputStream inputStream, String str3) throws IOException {
        this.out.write(this.boundaryLine);
        this.out.write(createContentDisposition(str, str2));
        this.out.write(createContentType(str3));
        this.out.write(TRANSFER_ENCODING_BINARY);
        this.out.write(CR_LF);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                this.out.write(bArr, 0, read);
            } else {
                this.out.write(CR_LF);
                this.out.flush();
                return;
            }
        }
    }

    public byte[] createContentType(String str) {
        return ("Content-Type: " + normalizeContentType(str) + STR_CR_LF).getBytes();
    }

    private byte[] createContentDisposition(String str) {
        return ("Content-Disposition: form-data; name=\"" + str + "\"" + STR_CR_LF).getBytes();
    }

    public byte[] createContentDisposition(String str, String str2) {
        return ("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"" + STR_CR_LF).getBytes();
    }

    public void updateProgress(long j) {
        this.bytesWritten += j;
        this.progressHandler.sendProgressMessage(this.bytesWritten, this.totalSize);
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public long getContentLength() {
        long size = this.out.size();
        for (FilePart filePart : this.fileParts) {
            long totalLength = filePart.getTotalLength();
            if (totalLength < 0) {
                return -1L;
            }
            size += totalLength;
        }
        return size + this.boundaryEnd.length;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    public void setIsRepeatable(boolean z) {
        this.isRepeatable = z;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public boolean isRepeatable() {
        return this.isRepeatable;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        this.bytesWritten = 0L;
        this.totalSize = (int) getContentLength();
        this.out.writeTo(outputStream);
        updateProgress(this.out.size());
        for (FilePart filePart : this.fileParts) {
            filePart.writeTo(outputStream);
        }
        outputStream.write(this.boundaryEnd);
        updateProgress(this.boundaryEnd.length);
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }

    /* loaded from: classes.dex */
    public class FilePart {
        public final File file;
        public final byte[] header;

        public FilePart(String str, File file, String str2, String str3) {
            SimpleMultipartEntity.this = r1;
            this.header = createHeader(str, TextUtils.isEmpty(str3) ? file.getName() : str3, str2);
            this.file = file;
        }

        public FilePart(String str, File file, String str2) {
            SimpleMultipartEntity.this = r1;
            this.header = createHeader(str, file.getName(), str2);
            this.file = file;
        }

        private byte[] createHeader(String str, String str2, String str3) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(SimpleMultipartEntity.this.boundaryLine);
                byteArrayOutputStream.write(SimpleMultipartEntity.this.createContentDisposition(str, str2));
                byteArrayOutputStream.write(SimpleMultipartEntity.this.createContentType(str3));
                byteArrayOutputStream.write(SimpleMultipartEntity.TRANSFER_ENCODING_BINARY);
                byteArrayOutputStream.write(SimpleMultipartEntity.CR_LF);
            } catch (IOException e) {
                AsyncHttpClient.log.e(SimpleMultipartEntity.LOG_TAG, "createHeader ByteArrayOutputStream exception", e);
            }
            return byteArrayOutputStream.toByteArray();
        }

        public long getTotalLength() {
            return this.header.length + this.file.length() + SimpleMultipartEntity.CR_LF.length;
        }

        public void writeTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.header);
            SimpleMultipartEntity.this.updateProgress(this.header.length);
            FileInputStream fileInputStream = new FileInputStream(this.file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                    SimpleMultipartEntity.this.updateProgress(read);
                } else {
                    outputStream.write(SimpleMultipartEntity.CR_LF);
                    SimpleMultipartEntity.this.updateProgress(SimpleMultipartEntity.CR_LF.length);
                    outputStream.flush();
                    AsyncHttpClient.silentCloseInputStream(fileInputStream);
                    return;
                }
            }
        }
    }
}
