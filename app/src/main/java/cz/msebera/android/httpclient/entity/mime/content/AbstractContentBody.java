package cz.msebera.android.httpclient.entity.mime.content;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.util.Args;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public abstract class AbstractContentBody implements ContentBody {
    private final ContentType contentType;

    public AbstractContentBody(ContentType contentType) {
        Args.notNull(contentType, "Content type");
        this.contentType = contentType;
    }

    @Deprecated
    public AbstractContentBody(String str) {
        this(ContentType.parse(str));
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    @Override // cz.msebera.android.httpclient.entity.mime.content.ContentDescriptor
    public String getMimeType() {
        return this.contentType.getMimeType();
    }

    @Override // cz.msebera.android.httpclient.entity.mime.content.ContentDescriptor
    public String getMediaType() {
        String mimeType = this.contentType.getMimeType();
        int indexOf = mimeType.indexOf(47);
        return indexOf != -1 ? mimeType.substring(0, indexOf) : mimeType;
    }

    @Override // cz.msebera.android.httpclient.entity.mime.content.ContentDescriptor
    public String getSubType() {
        String mimeType = this.contentType.getMimeType();
        int indexOf = mimeType.indexOf(47);
        if (indexOf != -1) {
            return mimeType.substring(indexOf + 1);
        }
        return null;
    }

    @Override // cz.msebera.android.httpclient.entity.mime.content.ContentDescriptor
    public String getCharset() {
        Charset charset = this.contentType.getCharset();
        if (charset != null) {
            return charset.name();
        }
        return null;
    }
}
