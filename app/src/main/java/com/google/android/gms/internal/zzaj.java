package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Map;
import kr.timehub.beplan.utils.Comm_Params;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public final class zzaj implements zzam {
    private HttpClient zzca;

    public zzaj(HttpClient httpClient) {
        this.zzca = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzp<?> zzpVar) throws zza {
        byte[] zzg = zzpVar.zzg();
        if (zzg != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(zzg));
        }
    }

    private static void zza(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, map.get(str));
        }
    }

    @Override // com.google.android.gms.internal.zzam
    public final HttpResponse zza(zzp<?> zzpVar, Map<String, String> map) throws IOException, zza {
        zzak zzakVar;
        switch (zzpVar.getMethod()) {
            case -1:
                zzakVar = new HttpGet(zzpVar.getUrl());
                break;
            case 0:
                zzakVar = new HttpGet(zzpVar.getUrl());
                break;
            case 1:
                zzakVar = new HttpPost(zzpVar.getUrl());
                zzakVar.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) zzakVar, zzpVar);
                break;
            case 2:
                zzakVar = new HttpPut(zzpVar.getUrl());
                zzakVar.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) zzakVar, zzpVar);
                break;
            case 3:
                zzakVar = new HttpDelete(zzpVar.getUrl());
                break;
            case 4:
                zzakVar = new HttpHead(zzpVar.getUrl());
                break;
            case 5:
                zzakVar = new HttpOptions(zzpVar.getUrl());
                break;
            case 6:
                zzakVar = new HttpTrace(zzpVar.getUrl());
                break;
            case 7:
                zzakVar = new zzak(zzpVar.getUrl());
                zzakVar.addHeader("Content-Type", zzp.zzf());
                zza((HttpEntityEnclosingRequestBase) zzakVar, zzpVar);
                break;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        zza((HttpUriRequest) zzakVar, map);
        zza((HttpUriRequest) zzakVar, zzpVar.getHeaders());
        HttpParams params = zzakVar.getParams();
        int zzi = zzpVar.zzi();
        HttpConnectionParams.setConnectionTimeout(params, Comm_Params.NETWORK_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, zzi);
        return this.zzca.execute(zzakVar);
    }
}
