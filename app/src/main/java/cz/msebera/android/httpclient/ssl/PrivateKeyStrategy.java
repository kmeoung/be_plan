package cz.msebera.android.httpclient.ssl;

import java.net.Socket;
import java.util.Map;

/* loaded from: classes.dex */
public interface PrivateKeyStrategy {
    String chooseAlias(Map<String, PrivateKeyDetails> map, Socket socket);
}
