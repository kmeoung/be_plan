package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzeug;
import com.google.android.gms.internal.zzeuh;
import com.google.android.gms.internal.zzeui;
import com.google.android.gms.internal.zzeuj;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private final Context mApplicationContext;
    private final String mName;
    private final FirebaseOptions zzlwk;
    private zzeui zzlwq;
    private static final List<String> zzlwf = Arrays.asList("com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId");
    private static final List<String> zzlwg = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzlwh = Arrays.asList("com.google.android.gms.measurement.AppMeasurement");
    private static final List<String> zzlwi = Arrays.asList(new String[0]);
    private static final Set<String> zzlwj = Collections.emptySet();
    private static final Object sLock = new Object();
    static final Map<String, FirebaseApp> zzick = new ArrayMap();
    private final AtomicBoolean zzlwl = new AtomicBoolean(false);
    private final AtomicBoolean zzlwm = new AtomicBoolean();
    private final List<zzb> zzlwn = new CopyOnWriteArrayList();
    private final List<zza> zzlwo = new CopyOnWriteArrayList();
    private final List<Object> zzlwp = new CopyOnWriteArrayList();
    private zzc zzlwr = new zzeug();

    /* loaded from: classes.dex */
    public interface zza {
        void zzbe(boolean z);
    }

    /* loaded from: classes.dex */
    public interface zzb {
        void zzb(@NonNull zzeuj zzeujVar);
    }

    /* loaded from: classes.dex */
    public interface zzc {
    }

    @TargetApi(24)
    /* loaded from: classes.dex */
    public static class zzd extends BroadcastReceiver {
        private static AtomicReference<zzd> zzlws = new AtomicReference<>();
        private final Context mApplicationContext;

        private zzd(Context context) {
            this.mApplicationContext = context;
        }

        public static void zzeo(Context context) {
            if (zzlws.get() == null) {
                zzd zzdVar = new zzd(context);
                if (zzlws.compareAndSet(null, zzdVar)) {
                    context.registerReceiver(zzdVar, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.sLock) {
                for (FirebaseApp firebaseApp : FirebaseApp.zzick.values()) {
                    firebaseApp.zzbpa();
                }
            }
            this.mApplicationContext.unregisterReceiver(this);
        }
    }

    private FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.mApplicationContext = (Context) zzbq.checkNotNull(context);
        this.mName = zzbq.zzgh(str);
        this.zzlwk = (FirebaseOptions) zzbq.checkNotNull(firebaseOptions);
    }

    public static List<FirebaseApp> getApps(Context context) {
        ArrayList arrayList;
        zzeuh.zzew(context);
        synchronized (sLock) {
            arrayList = new ArrayList(zzick.values());
            zzeuh.zzchr();
            Set<String> zzchs = zzeuh.zzchs();
            zzchs.removeAll(zzick.keySet());
            for (String str : zzchs) {
                zzeuh.zzra(str);
                arrayList.add(initializeApp(context, null, str));
            }
        }
        return arrayList;
    }

    @Nullable
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (sLock) {
            firebaseApp = zzick.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                String zzamc = zzs.zzamc();
                StringBuilder sb = new StringBuilder(String.valueOf(zzamc).length() + 116);
                sb.append("Default FirebaseApp is not initialized in this process ");
                sb.append(zzamc);
                sb.append(". Make sure to call FirebaseApp.initializeApp(Context) first.");
                throw new IllegalStateException(sb.toString());
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(@NonNull String str) {
        FirebaseApp firebaseApp;
        String str2;
        synchronized (sLock) {
            firebaseApp = zzick.get(str.trim());
            if (firebaseApp == null) {
                List<String> zzboz = zzboz();
                if (zzboz.isEmpty()) {
                    str2 = "";
                } else {
                    String valueOf = String.valueOf(TextUtils.join(", ", zzboz));
                    str2 = valueOf.length() != 0 ? "Available app names: ".concat(valueOf) : new String("Available app names: ");
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", str, str2));
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(Context context) {
        synchronized (sLock) {
            if (zzick.containsKey(DEFAULT_APP_NAME)) {
                return getInstance();
            }
            FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
            if (fromResource == null) {
                return null;
            }
            return initializeApp(context, fromResource);
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions, String str) {
        FirebaseApp firebaseApp;
        zzeuh.zzew(context);
        if (context.getApplicationContext() instanceof Application) {
            zzk.zza((Application) context.getApplicationContext());
            zzk.zzagp().zza(new zza());
        }
        String trim = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (sLock) {
            StringBuilder sb = new StringBuilder(String.valueOf(trim).length() + 33);
            sb.append("FirebaseApp name ");
            sb.append(trim);
            sb.append(" already exists!");
            zzbq.zza(!zzick.containsKey(trim), sb.toString());
            zzbq.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, trim, firebaseOptions);
            zzick.put(trim, firebaseApp);
        }
        zzeuh.zzf(firebaseApp);
        firebaseApp.zza(FirebaseApp.class, firebaseApp, zzlwf);
        if (firebaseApp.zzbox()) {
            firebaseApp.zza(FirebaseApp.class, firebaseApp, zzlwg);
            firebaseApp.zza(Context.class, firebaseApp.getApplicationContext(), zzlwh);
        }
        return firebaseApp;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <T> void zza(Class<T> cls, T t, Iterable<String> iterable) {
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.mApplicationContext);
        if (isDeviceProtectedStorage) {
            zzd.zzeo(this.mApplicationContext);
        }
        for (String str : iterable) {
            if (isDeviceProtectedStorage) {
                try {
                } catch (ClassNotFoundException unused) {
                    if (zzlwj.contains(str)) {
                        throw new IllegalStateException(String.valueOf(str).concat(" is missing, but is required. Check if it has been removed by Proguard."));
                    }
                    Log.d("FirebaseApp", String.valueOf(str).concat(" is not linked. Skipping initialization."));
                } catch (IllegalAccessException e) {
                    String valueOf = String.valueOf(str);
                    Log.wtf("FirebaseApp", valueOf.length() != 0 ? "Failed to initialize ".concat(valueOf) : new String("Failed to initialize "), e);
                } catch (NoSuchMethodException unused2) {
                    throw new IllegalStateException(String.valueOf(str).concat("#getInstance has been removed by Proguard. Add keep rule to prevent it."));
                } catch (InvocationTargetException e2) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e2);
                }
                if (zzlwi.contains(str)) {
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", cls);
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, t);
            }
        }
    }

    public static void zzbe(boolean z) {
        synchronized (sLock) {
            ArrayList arrayList = new ArrayList(zzick.values());
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                FirebaseApp firebaseApp = (FirebaseApp) obj;
                if (firebaseApp.zzlwl.get()) {
                    firebaseApp.zzca(z);
                }
            }
        }
    }

    private final void zzbow() {
        zzbq.zza(!this.zzlwm.get(), "FirebaseApp was deleted");
    }

    private static List<String> zzboz() {
        ArraySet arraySet = new ArraySet();
        synchronized (sLock) {
            for (FirebaseApp firebaseApp : zzick.values()) {
                arraySet.add(firebaseApp.getName());
            }
            if (zzeuh.zzchr() != null) {
                arraySet.addAll(zzeuh.zzchs());
            }
        }
        ArrayList arrayList = new ArrayList(arraySet);
        Collections.sort(arrayList);
        return arrayList;
    }

    public final void zzbpa() {
        zza(FirebaseApp.class, this, zzlwf);
        if (zzbox()) {
            zza(FirebaseApp.class, this, zzlwg);
            zza(Context.class, this.mApplicationContext, zzlwh);
        }
    }

    private final void zzca(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        for (zza zzaVar : this.zzlwo) {
            zzaVar.zzbe(z);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FirebaseApp)) {
            return false;
        }
        return this.mName.equals(((FirebaseApp) obj).getName());
    }

    @NonNull
    public Context getApplicationContext() {
        zzbow();
        return this.mApplicationContext;
    }

    @NonNull
    public String getName() {
        zzbow();
        return this.mName;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        zzbow();
        return this.zzlwk;
    }

    public final Task<GetTokenResult> getToken(boolean z) {
        zzbow();
        return this.zzlwq == null ? Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.")) : this.zzlwq.zzcb(z);
    }

    @Nullable
    public final String getUid() throws FirebaseApiNotAvailableException {
        zzbow();
        if (this.zzlwq != null) {
            return this.zzlwq.getUid();
        }
        throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public void setAutomaticResourceManagementEnabled(boolean z) {
        zzbow();
        if (this.zzlwl.compareAndSet(!z, z)) {
            boolean zzagq = zzk.zzagp().zzagq();
            if (z && zzagq) {
                zzca(true);
            } else if (!z && zzagq) {
                zzca(false);
            }
        }
    }

    public String toString() {
        return zzbg.zzw(this).zzg("name", this.mName).zzg("options", this.zzlwk).toString();
    }

    public final void zza(@NonNull zzeui zzeuiVar) {
        this.zzlwq = (zzeui) zzbq.checkNotNull(zzeuiVar);
    }

    @UiThread
    public final void zza(@NonNull zzeuj zzeujVar) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        int i = 0;
        for (zzb zzbVar : this.zzlwn) {
            zzbVar.zzb(zzeujVar);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", Integer.valueOf(i)));
    }

    public final void zza(zza zzaVar) {
        zzbow();
        if (this.zzlwl.get() && zzk.zzagp().zzagq()) {
            zzaVar.zzbe(true);
        }
        this.zzlwo.add(zzaVar);
    }

    public final void zza(@NonNull zzb zzbVar) {
        zzbow();
        zzbq.checkNotNull(zzbVar);
        this.zzlwn.add(zzbVar);
        this.zzlwn.size();
    }

    public final boolean zzbox() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    public final String zzboy() {
        String zzl = com.google.android.gms.common.util.zzb.zzl(getName().getBytes());
        String zzl2 = com.google.android.gms.common.util.zzb.zzl(getOptions().getApplicationId().getBytes());
        StringBuilder sb = new StringBuilder(String.valueOf(zzl).length() + 1 + String.valueOf(zzl2).length());
        sb.append(zzl);
        sb.append("+");
        sb.append(zzl2);
        return sb.toString();
    }
}
