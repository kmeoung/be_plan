package com.loopj.android.http;

import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class JsonHttpResponseHandler extends TextHttpResponseHandler {
    private static final String LOG_TAG = "JsonHttpRH";
    private boolean useRFC5179CompatibilityMode;

    public JsonHttpResponseHandler() {
        super("UTF-8");
        this.useRFC5179CompatibilityMode = true;
    }

    public JsonHttpResponseHandler(String str) {
        super(str);
        this.useRFC5179CompatibilityMode = true;
    }

    public JsonHttpResponseHandler(boolean z) {
        super("UTF-8");
        this.useRFC5179CompatibilityMode = true;
        this.useRFC5179CompatibilityMode = z;
    }

    public JsonHttpResponseHandler(String str, boolean z) {
        super(str);
        this.useRFC5179CompatibilityMode = true;
        this.useRFC5179CompatibilityMode = z;
    }

    public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
    }

    public void onSuccess(int i, Header[] headerArr, JSONArray jSONArray) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONArray) was not overriden, but callback was received");
    }

    public void onFailure(int i, Header[] headerArr, Throwable th, JSONObject jSONObject) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", th);
    }

    public void onFailure(int i, Header[] headerArr, Throwable th, JSONArray jSONArray) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONArray) was not overriden, but callback was received", th);
    }

    @Override // com.loopj.android.http.TextHttpResponseHandler
    public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], String, Throwable) was not overriden, but callback was received", th);
    }

    @Override // com.loopj.android.http.TextHttpResponseHandler
    public void onSuccess(int i, Header[] headerArr, String str) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], String) was not overriden, but callback was received");
    }

    @Override // com.loopj.android.http.TextHttpResponseHandler, com.loopj.android.http.AsyncHttpResponseHandler
    public final void onSuccess(final int i, final Header[] headerArr, final byte[] bArr) {
        if (i != 204) {
            Runnable runnable = new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final Object parseResponse = JsonHttpResponseHandler.this.parseResponse(bArr);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!JsonHttpResponseHandler.this.useRFC5179CompatibilityMode && parseResponse == null) {
                                    JsonHttpResponseHandler.this.onSuccess(i, headerArr, (String) null);
                                } else if (parseResponse instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onSuccess(i, headerArr, (JSONObject) parseResponse);
                                } else if (parseResponse instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onSuccess(i, headerArr, (JSONArray) parseResponse);
                                } else if (!(parseResponse instanceof String)) {
                                    JsonHttpResponseHandler jsonHttpResponseHandler = JsonHttpResponseHandler.this;
                                    int i2 = i;
                                    Header[] headerArr2 = headerArr;
                                    jsonHttpResponseHandler.onFailure(i2, headerArr2, new JSONException("Unexpected response type " + parseResponse.getClass().getName()), (JSONObject) null);
                                } else if (JsonHttpResponseHandler.this.useRFC5179CompatibilityMode) {
                                    JsonHttpResponseHandler.this.onFailure(i, headerArr, (String) parseResponse, new JSONException("Response cannot be parsed as JSON data"));
                                } else {
                                    JsonHttpResponseHandler.this.onSuccess(i, headerArr, (String) parseResponse);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(i, headerArr, e, (JSONObject) null);
                            }
                        });
                    }
                }
            };
            if (getUseSynchronousMode() || getUsePoolThread()) {
                runnable.run();
            } else {
                new Thread(runnable).start();
            }
        } else {
            onSuccess(i, headerArr, new JSONObject());
        }
    }

    @Override // com.loopj.android.http.TextHttpResponseHandler, com.loopj.android.http.AsyncHttpResponseHandler
    public final void onFailure(final int i, final Header[] headerArr, final byte[] bArr, final Throwable th) {
        if (bArr != null) {
            Runnable runnable = new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final Object parseResponse = JsonHttpResponseHandler.this.parseResponse(bArr);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!JsonHttpResponseHandler.this.useRFC5179CompatibilityMode && parseResponse == null) {
                                    JsonHttpResponseHandler.this.onFailure(i, headerArr, (String) null, th);
                                } else if (parseResponse instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onFailure(i, headerArr, th, (JSONObject) parseResponse);
                                } else if (parseResponse instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onFailure(i, headerArr, th, (JSONArray) parseResponse);
                                } else if (parseResponse instanceof String) {
                                    JsonHttpResponseHandler.this.onFailure(i, headerArr, (String) parseResponse, th);
                                } else {
                                    JsonHttpResponseHandler jsonHttpResponseHandler = JsonHttpResponseHandler.this;
                                    int i2 = i;
                                    Header[] headerArr2 = headerArr;
                                    jsonHttpResponseHandler.onFailure(i2, headerArr2, new JSONException("Unexpected response type " + parseResponse.getClass().getName()), (JSONObject) null);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable() { // from class: com.loopj.android.http.JsonHttpResponseHandler.2.2
                            @Override // java.lang.Runnable
                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(i, headerArr, e, (JSONObject) null);
                            }
                        });
                    }
                }
            };
            if (getUseSynchronousMode() || getUsePoolThread()) {
                runnable.run();
            } else {
                new Thread(runnable).start();
            }
        } else {
            AsyncHttpClient.log.v(LOG_TAG, "response body is null, calling onFailure(Throwable, JSONObject)");
            onFailure(i, headerArr, th, (JSONObject) null);
        }
    }

    protected Object parseResponse(byte[] bArr) throws JSONException {
        Object obj = null;
        if (bArr == null) {
            return null;
        }
        String responseString = getResponseString(bArr, getCharset());
        if (responseString != null) {
            responseString = responseString.trim();
            if (this.useRFC5179CompatibilityMode) {
                if (responseString.startsWith("{") || responseString.startsWith("[")) {
                    obj = new JSONTokener(responseString).nextValue();
                }
            } else if ((responseString.startsWith("{") && responseString.endsWith("}")) || (responseString.startsWith("[") && responseString.endsWith("]"))) {
                obj = new JSONTokener(responseString).nextValue();
            } else if (responseString.startsWith("\"") && responseString.endsWith("\"")) {
                obj = responseString.substring(1, responseString.length() - 1);
            }
        }
        return obj == null ? responseString : obj;
    }

    public boolean isUseRFC5179CompatibilityMode() {
        return this.useRFC5179CompatibilityMode;
    }

    public void setUseRFC5179CompatibilityMode(boolean z) {
        this.useRFC5179CompatibilityMode = z;
    }
}
