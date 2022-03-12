package org.jsoup.helper;

import com.google.firebase.analytics.FirebaseAnalytics;
import cz.msebera.android.httpclient.protocol.HTTP;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.UncheckedIOException;
import org.jsoup.internal.ConstrainableInputStream;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;

/* loaded from: classes.dex */
public final class DataUtil {
    static final int boundaryLength = 32;
    static final int bufferSize = 32768;
    static final String defaultCharset = "UTF-8";
    private static final int firstReadBufferSize = 5120;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:[\"'])?([^\\s,;\"']*)");
    private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private DataUtil() {
    }

    public static Document load(File file, String str, String str2) throws IOException {
        return parseInputStream(new FileInputStream(file), str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2) throws IOException {
        return parseInputStream(inputStream, str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return parseInputStream(inputStream, str, str2, parser);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void crossStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[32768];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Document parseInputStream(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        if (inputStream == null) {
            return new Document(str2);
        }
        ConstrainableInputStream wrap = ConstrainableInputStream.wrap(inputStream, 32768, 0);
        wrap.mark(32768);
        ByteBuffer readToByteBuffer = readToByteBuffer(wrap, 5119);
        boolean z = wrap.read() == -1;
        wrap.reset();
        BomCharset detectCharsetFromBom = detectCharsetFromBom(readToByteBuffer);
        if (detectCharsetFromBom != null) {
            str = detectCharsetFromBom.charset;
        }
        Document document = null;
        if (str == null) {
            Document parseInput = parser.parseInput(Charset.forName("UTF-8").decode(readToByteBuffer).toString(), str2);
            Iterator<Element> it = parseInput.select("meta[http-equiv=content-type], meta[charset]").iterator();
            String str3 = null;
            while (it.hasNext()) {
                Element next = it.next();
                if (next.hasAttr("http-equiv")) {
                    str3 = getCharsetFromContentType(next.attr(FirebaseAnalytics.Param.CONTENT));
                }
                if (str3 == null && next.hasAttr(HttpRequest.PARAM_CHARSET)) {
                    str3 = next.attr(HttpRequest.PARAM_CHARSET);
                    continue;
                }
                if (str3 != null) {
                    break;
                }
            }
            if (str3 == null && parseInput.childNodeSize() > 0 && (parseInput.childNode(0) instanceof XmlDeclaration)) {
                XmlDeclaration xmlDeclaration = (XmlDeclaration) parseInput.childNode(0);
                if (xmlDeclaration.name().equals("xml")) {
                    str3 = xmlDeclaration.attr("encoding");
                }
            }
            String validateCharset = validateCharset(str3);
            if (validateCharset != null && !validateCharset.equalsIgnoreCase("UTF-8")) {
                str = validateCharset.trim().replaceAll("[\"']", "");
            } else if (z) {
                document = parseInput;
            }
        } else {
            Validate.notEmpty(str, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        }
        if (document == null) {
            if (str == null) {
                str = "UTF-8";
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wrap, str), 32768);
            if (detectCharsetFromBom != null && detectCharsetFromBom.offset) {
                bufferedReader.skip(1L);
            }
            try {
                document = parser.parseInput(bufferedReader, str2);
                document.outputSettings().charset(str);
            } catch (UncheckedIOException e) {
                throw e.ioException();
            }
        }
        wrap.close();
        return document;
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream, int i) throws IOException {
        Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
        return ConstrainableInputStream.wrap(inputStream, 32768, i).readToByteBuffer(i);
    }

    static ByteBuffer readToByteBuffer(InputStream inputStream) throws IOException {
        return readToByteBuffer(inputStream, 0);
    }

    static ByteBuffer readFileToByteBuffer(File file) throws IOException {
        Throwable th;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            try {
                byte[] bArr = new byte[(int) randomAccessFile.length()];
                randomAccessFile.readFully(bArr);
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                return wrap;
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getCharsetFromContentType(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = charsetPattern.matcher(str);
        if (matcher.find()) {
            return validateCharset(matcher.group(1).trim().replace("charset=", ""));
        }
        return null;
    }

    private static String validateCharset(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String replaceAll = str.trim().replaceAll("[\"']", "");
        if (Charset.isSupported(replaceAll)) {
            return replaceAll;
        }
        String upperCase = replaceAll.toUpperCase(Locale.ENGLISH);
        if (Charset.isSupported(upperCase)) {
            return upperCase;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String mimeBoundary() {
        StringBuilder sb = new StringBuilder(32);
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(mimeBoundaryChars[random.nextInt(mimeBoundaryChars.length)]);
        }
        return sb.toString();
    }

    private static BomCharset detectCharsetFromBom(ByteBuffer byteBuffer) {
        byteBuffer.mark();
        byte[] bArr = new byte[4];
        if (byteBuffer.remaining() >= bArr.length) {
            byteBuffer.get(bArr);
            byteBuffer.rewind();
        }
        if ((bArr[0] == 0 && bArr[1] == 0 && bArr[2] == -2 && bArr[3] == -1) || (bArr[0] == -1 && bArr[1] == -2 && bArr[2] == 0 && bArr[3] == 0)) {
            return new BomCharset("UTF-32", false);
        }
        if ((bArr[0] == -2 && bArr[1] == -1) || (bArr[0] == -1 && bArr[1] == -2)) {
            return new BomCharset(HTTP.UTF_16, false);
        }
        if (bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) {
            return new BomCharset("UTF-8", true);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BomCharset {
        private final String charset;
        private final boolean offset;

        public BomCharset(String str, boolean z) {
            this.charset = str;
            this.offset = z;
        }
    }
}
