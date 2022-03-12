package cz.msebera.android.httpclient.client.entity;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.entity.AbstractHttpEntity;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.FileEntity;
import cz.msebera.android.httpclient.entity.InputStreamEntity;
import cz.msebera.android.httpclient.entity.SerializableEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@NotThreadSafe
/* loaded from: classes.dex */
public class EntityBuilder {
    private byte[] binary;
    private boolean chunked;
    private String contentEncoding;
    private ContentType contentType;
    private File file;
    private boolean gzipCompress;
    private List<NameValuePair> parameters;
    private Serializable serializable;
    private InputStream stream;
    private String text;

    EntityBuilder() {
    }

    public static EntityBuilder create() {
        return new EntityBuilder();
    }

    private void clearContent() {
        this.text = null;
        this.binary = null;
        this.stream = null;
        this.parameters = null;
        this.serializable = null;
        this.file = null;
    }

    public String getText() {
        return this.text;
    }

    public EntityBuilder setText(String str) {
        clearContent();
        this.text = str;
        return this;
    }

    public byte[] getBinary() {
        return this.binary;
    }

    public EntityBuilder setBinary(byte[] bArr) {
        clearContent();
        this.binary = bArr;
        return this;
    }

    public InputStream getStream() {
        return this.stream;
    }

    public EntityBuilder setStream(InputStream inputStream) {
        clearContent();
        this.stream = inputStream;
        return this;
    }

    public List<NameValuePair> getParameters() {
        return this.parameters;
    }

    public EntityBuilder setParameters(List<NameValuePair> list) {
        clearContent();
        this.parameters = list;
        return this;
    }

    public EntityBuilder setParameters(NameValuePair... nameValuePairArr) {
        return setParameters(Arrays.asList(nameValuePairArr));
    }

    public Serializable getSerializable() {
        return this.serializable;
    }

    public EntityBuilder setSerializable(Serializable serializable) {
        clearContent();
        this.serializable = serializable;
        return this;
    }

    public File getFile() {
        return this.file;
    }

    public EntityBuilder setFile(File file) {
        clearContent();
        this.file = file;
        return this;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public EntityBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public EntityBuilder setContentEncoding(String str) {
        this.contentEncoding = str;
        return this;
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public EntityBuilder chunked() {
        this.chunked = true;
        return this;
    }

    public boolean isGzipCompress() {
        return this.gzipCompress;
    }

    public EntityBuilder gzipCompress() {
        this.gzipCompress = true;
        return this;
    }

    private ContentType getContentOrDefault(ContentType contentType) {
        return this.contentType != null ? this.contentType : contentType;
    }

    public HttpEntity build() {
        AbstractHttpEntity abstractHttpEntity;
        if (this.text != null) {
            abstractHttpEntity = new StringEntity(this.text, getContentOrDefault(ContentType.DEFAULT_TEXT));
        } else if (this.binary != null) {
            abstractHttpEntity = new ByteArrayEntity(this.binary, getContentOrDefault(ContentType.DEFAULT_BINARY));
        } else if (this.stream != null) {
            abstractHttpEntity = new InputStreamEntity(this.stream, -1L, getContentOrDefault(ContentType.DEFAULT_BINARY));
        } else if (this.parameters != null) {
            abstractHttpEntity = new UrlEncodedFormEntity(this.parameters, this.contentType != null ? this.contentType.getCharset() : null);
        } else if (this.serializable != null) {
            abstractHttpEntity = new SerializableEntity(this.serializable);
            abstractHttpEntity.setContentType(ContentType.DEFAULT_BINARY.toString());
        } else if (this.file != null) {
            abstractHttpEntity = new FileEntity(this.file, getContentOrDefault(ContentType.DEFAULT_BINARY));
        } else {
            abstractHttpEntity = new BasicHttpEntity();
        }
        if (!(abstractHttpEntity.getContentType() == null || this.contentType == null)) {
            abstractHttpEntity.setContentType(this.contentType.toString());
        }
        abstractHttpEntity.setContentEncoding(this.contentEncoding);
        abstractHttpEntity.setChunked(this.chunked);
        return this.gzipCompress ? new GzipCompressingEntity(abstractHttpEntity) : abstractHttpEntity;
    }
}
