package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class AdvertisingInfoServiceStrategy implements AdvertisingInfoStrategy {
    public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
    public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
    private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
    private final Context context;

    public AdvertisingInfoServiceStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override // io.fabric.sdk.android.services.common.AdvertisingInfoStrategy
    public AdvertisingInfo getAdvertisingInfo() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Fabric.getLogger().d(Fabric.TAG, "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        try {
            this.context.getPackageManager().getPackageInfo("com.android.vending", 0);
            AdvertisingConnection advertisingConnection = new AdvertisingConnection();
            Intent intent = new Intent(GOOGLE_PLAY_SERVICES_INTENT);
            intent.setPackage("com.google.android.gms");
            try {
                if (this.context.bindService(intent, advertisingConnection, 1)) {
                    try {
                        AdvertisingInterface advertisingInterface = new AdvertisingInterface(advertisingConnection.getBinder());
                        AdvertisingInfo advertisingInfo = new AdvertisingInfo(advertisingInterface.getId(), advertisingInterface.isLimitAdTrackingEnabled());
                        this.context.unbindService(advertisingConnection);
                        return advertisingInfo;
                    } catch (Exception e) {
                        Fabric.getLogger().w(Fabric.TAG, "Exception in binding to Google Play Service to capture AdvertisingId", e);
                        this.context.unbindService(advertisingConnection);
                    }
                } else {
                    Fabric.getLogger().d(Fabric.TAG, "Could not bind to Google Play Service to capture AdvertisingId");
                }
            } catch (Throwable th) {
                Fabric.getLogger().d(Fabric.TAG, "Could not bind to Google Play Service to capture AdvertisingId", th);
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            Fabric.getLogger().d(Fabric.TAG, "Unable to find Google Play Services package name");
            return null;
        } catch (Exception e2) {
            Fabric.getLogger().d(Fabric.TAG, "Unable to determine if Google Play Services is available", e2);
            return null;
        }
    }

    /* loaded from: classes.dex */
    private static final class AdvertisingConnection implements ServiceConnection {
        private static final int QUEUE_TIMEOUT_IN_MS = 200;
        private final LinkedBlockingQueue<IBinder> queue;
        private boolean retrieved;

        private AdvertisingConnection() {
            this.retrieved = false;
            this.queue = new LinkedBlockingQueue<>(1);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.queue.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.queue.clear();
        }

        public IBinder getBinder() {
            if (this.retrieved) {
                Fabric.getLogger().e(Fabric.TAG, "getBinder already called");
            }
            this.retrieved = true;
            try {
                return this.queue.poll(200L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                return null;
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class AdvertisingInterface implements IInterface {
        public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
        private static final int AD_TRANSACTION_CODE_ID = 1;
        private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
        private static final int FLAGS_NONE = 0;
        private final IBinder binder;

        public AdvertisingInterface(IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.binder;
        }

        /* JADX WARN: Finally extract failed */
        public String getId() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                try {
                    obtain.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                    this.binder.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } catch (Exception unused) {
                    Fabric.getLogger().d(Fabric.TAG, "Could not get parcel from Google Play Service to capture AdvertisingId");
                    obtain2.recycle();
                    obtain.recycle();
                    return null;
                }
            } catch (Throwable th) {
                obtain2.recycle();
                obtain.recycle();
                throw th;
            }
        }

        public boolean isLimitAdTrackingEnabled() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            boolean z = false;
            try {
                try {
                    obtain.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                    obtain.writeInt(1);
                    this.binder.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                } catch (Exception unused) {
                    Fabric.getLogger().d(Fabric.TAG, "Could not get parcel from Google Play Service to capture Advertising limitAdTracking");
                }
                return z;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }
}
