package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.util.PublicSuffixList;
import java.io.IOException;
import java.io.Reader;

@Immutable
@Deprecated
/* loaded from: classes.dex */
public class PublicSuffixListParser {
    private final PublicSuffixFilter filter;
    private final cz.msebera.android.httpclient.conn.util.PublicSuffixListParser parser = new cz.msebera.android.httpclient.conn.util.PublicSuffixListParser();

    PublicSuffixListParser(PublicSuffixFilter publicSuffixFilter) {
        this.filter = publicSuffixFilter;
    }

    public void parse(Reader reader) throws IOException {
        PublicSuffixList parse = this.parser.parse(reader);
        this.filter.setPublicSuffixes(parse.getRules());
        this.filter.setExceptions(parse.getExceptions());
    }
}
