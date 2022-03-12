package cz.msebera.android.httpclient.conn.util;

import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

@Immutable
/* loaded from: classes.dex */
public final class PublicSuffixListParser {
    private static final int MAX_LINE_LEN = 256;

    public PublicSuffixList parse(Reader reader) throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder(256);
        boolean z = true;
        while (z) {
            z = readLine(bufferedReader, sb);
            String sb2 = sb.toString();
            if (!sb2.isEmpty() && !sb2.startsWith("//")) {
                if (sb2.startsWith(".")) {
                    sb2 = sb2.substring(1);
                }
                boolean startsWith = sb2.startsWith("!");
                if (startsWith) {
                    sb2 = sb2.substring(1);
                }
                if (startsWith) {
                    arrayList2.add(sb2);
                } else {
                    arrayList.add(sb2);
                }
            }
        }
        return new PublicSuffixList(arrayList, arrayList2);
    }

    private boolean readLine(Reader reader, StringBuilder sb) throws IOException {
        char c;
        sb.setLength(0);
        boolean z = false;
        do {
            int read = reader.read();
            if (read == -1 || (c = (char) read) == '\n') {
                return read != -1;
            }
            if (Character.isWhitespace(c)) {
                z = true;
            }
            if (!z) {
                sb.append(c);
            }
        } while (sb.length() <= 256);
        return false;
    }
}
