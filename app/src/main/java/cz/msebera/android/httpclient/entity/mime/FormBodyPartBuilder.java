package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.content.AbstractContentBody;
import cz.msebera.android.httpclient.entity.mime.content.ContentBody;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;

/* loaded from: classes.dex */
public class FormBodyPartBuilder {
    private ContentBody body;
    private final Header header;
    private String name;

    public static FormBodyPartBuilder create(String str, ContentBody contentBody) {
        return new FormBodyPartBuilder(str, contentBody);
    }

    public static FormBodyPartBuilder create() {
        return new FormBodyPartBuilder();
    }

    FormBodyPartBuilder(String str, ContentBody contentBody) {
        this();
        this.name = str;
        this.body = contentBody;
    }

    FormBodyPartBuilder() {
        this.header = new Header();
    }

    public FormBodyPartBuilder setName(String str) {
        this.name = str;
        return this;
    }

    public FormBodyPartBuilder setBody(ContentBody contentBody) {
        this.body = contentBody;
        return this;
    }

    public FormBodyPartBuilder addField(String str, String str2) {
        Args.notNull(str, "Field name");
        this.header.addField(new MinimalField(str, str2));
        return this;
    }

    public FormBodyPartBuilder setField(String str, String str2) {
        Args.notNull(str, "Field name");
        this.header.setField(new MinimalField(str, str2));
        return this;
    }

    public FormBodyPartBuilder removeFields(String str) {
        Args.notNull(str, "Field name");
        this.header.removeFields(str);
        return this;
    }

    public FormBodyPart build() {
        Asserts.notBlank(this.name, "Name");
        Asserts.notNull(this.body, "Content body");
        Header header = new Header();
        for (MinimalField minimalField : this.header.getFields()) {
            header.addField(minimalField);
        }
        if (header.getField("Content-Disposition") == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("form-data; name=\"");
            sb.append(this.name);
            sb.append("\"");
            if (this.body.getFilename() != null) {
                sb.append("; filename=\"");
                sb.append(this.body.getFilename());
                sb.append("\"");
            }
            header.addField(new MinimalField("Content-Disposition", sb.toString()));
        }
        if (header.getField("Content-Type") == null) {
            ContentType contentType = this.body instanceof AbstractContentBody ? ((AbstractContentBody) this.body).getContentType() : null;
            if (contentType != null) {
                header.addField(new MinimalField("Content-Type", contentType.toString()));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.body.getMimeType());
                if (this.body.getCharset() != null) {
                    sb2.append(HTTP.CHARSET_PARAM);
                    sb2.append(this.body.getCharset());
                }
                header.addField(new MinimalField("Content-Type", sb2.toString()));
            }
        }
        if (header.getField(MIME.CONTENT_TRANSFER_ENC) == null) {
            header.addField(new MinimalField(MIME.CONTENT_TRANSFER_ENC, this.body.getTransferEncoding()));
        }
        return new FormBodyPart(this.name, this.body, header);
    }
}
