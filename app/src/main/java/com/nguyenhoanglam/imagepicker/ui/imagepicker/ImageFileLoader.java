package com.nguyenhoanglam.imagepicker.ui.imagepicker;

import android.content.Context;
import com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class ImageFileLoader {
    private Context context;
    private ExecutorService executorService;
    private final String[] projection = {"_id", "_display_name", "_data", "bucket_display_name"};

    public ImageFileLoader(Context context) {
        this.context = context;
    }

    public static File makeSafeFile(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        try {
            return new File(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public void loadDeviceImages(boolean z, OnImageLoaderListener onImageLoaderListener) {
        getExecutorService().execute(new ImageLoadRunnable(z, onImageLoaderListener));
    }

    public void abortLoadImages() {
        if (this.executorService != null) {
            this.executorService.shutdown();
            this.executorService = null;
        }
    }

    private ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = Executors.newSingleThreadExecutor();
        }
        return this.executorService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ImageLoadRunnable implements Runnable {
        private boolean isFolderMode;
        private OnImageLoaderListener listener;

        public ImageLoadRunnable(boolean z, OnImageLoaderListener onImageLoaderListener) {
            ImageFileLoader.this = r1;
            this.isFolderMode = z;
            this.listener = onImageLoaderListener;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00be  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r10 = this;
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r0 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                android.content.Context r0 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$100(r0)
                android.content.ContentResolver r1 = r0.getContentResolver()
                android.net.Uri r2 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r0 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                java.lang.String[] r3 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$000(r0)
                java.lang.String r6 = "date_added"
                r4 = 0
                r5 = 0
                android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6)
                if (r0 != 0) goto L_0x0027
                com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener r0 = r10.listener
                java.lang.NullPointerException r1 = new java.lang.NullPointerException
                r1.<init>()
                r0.onFailed(r1)
                return
            L_0x0027:
                java.util.ArrayList r1 = new java.util.ArrayList
                int r2 = r0.getCount()
                r1.<init>(r2)
                boolean r2 = r10.isFolderMode
                r3 = 0
                if (r2 == 0) goto L_0x003b
                java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
                r2.<init>()
                goto L_0x003c
            L_0x003b:
                r2 = r3
            L_0x003c:
                boolean r4 = r0.moveToLast()
                if (r4 == 0) goto L_0x00b9
            L_0x0042:
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r4 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                java.lang.String[] r4 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$000(r4)
                r5 = 0
                r4 = r4[r5]
                int r4 = r0.getColumnIndex(r4)
                long r4 = r0.getLong(r4)
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r6 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                java.lang.String[] r6 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$000(r6)
                r7 = 1
                r6 = r6[r7]
                int r6 = r0.getColumnIndex(r6)
                java.lang.String r6 = r0.getString(r6)
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r7 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                java.lang.String[] r7 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$000(r7)
                r8 = 2
                r7 = r7[r8]
                int r7 = r0.getColumnIndex(r7)
                java.lang.String r7 = r0.getString(r7)
                com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader r8 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.this
                java.lang.String[] r8 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$000(r8)
                r9 = 3
                r8 = r8[r9]
                int r8 = r0.getColumnIndex(r8)
                java.lang.String r8 = r0.getString(r8)
                java.io.File r9 = com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.access$200(r7)
                if (r9 == 0) goto L_0x00b3
                boolean r9 = r9.exists()
                if (r9 == 0) goto L_0x00b3
                com.nguyenhoanglam.imagepicker.model.Image r9 = new com.nguyenhoanglam.imagepicker.model.Image
                r9.<init>(r4, r6, r7)
                r1.add(r9)
                if (r2 == 0) goto L_0x00b3
                java.lang.Object r4 = r2.get(r8)
                com.nguyenhoanglam.imagepicker.model.Folder r4 = (com.nguyenhoanglam.imagepicker.model.Folder) r4
                if (r4 != 0) goto L_0x00ac
                com.nguyenhoanglam.imagepicker.model.Folder r4 = new com.nguyenhoanglam.imagepicker.model.Folder
                r4.<init>(r8)
                r2.put(r8, r4)
            L_0x00ac:
                java.util.ArrayList r4 = r4.getImages()
                r4.add(r9)
            L_0x00b3:
                boolean r4 = r0.moveToPrevious()
                if (r4 != 0) goto L_0x0042
            L_0x00b9:
                r0.close()
                if (r2 == 0) goto L_0x00c7
                java.util.ArrayList r3 = new java.util.ArrayList
                java.util.Collection r0 = r2.values()
                r3.<init>(r0)
            L_0x00c7:
                com.nguyenhoanglam.imagepicker.listener.OnImageLoaderListener r0 = r10.listener
                r0.onImageLoaded(r1, r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nguyenhoanglam.imagepicker.ui.imagepicker.ImageFileLoader.ImageLoadRunnable.run():void");
        }
    }
}
