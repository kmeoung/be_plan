package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;

@Immutable
/* loaded from: classes.dex */
public class BasicResponseHandler extends AbstractResponseHandler<String> {
    @Override // cz.msebera.android.httpclient.impl.client.AbstractResponseHandler
    public String handleEntity(HttpEntity httpEntity) throws IOException {
        return EntityUtils.toString(httpEntity);
    }

    @Override // cz.msebera.android.httpclient.impl.client.AbstractResponseHandler, cz.msebera.android.httpclient.client.ResponseHandler
    public String handleResponse(HttpResponse httpResponse) throws HttpResponseException, IOException {
        return (String) super.handleResponse(httpResponse);
    }
}
