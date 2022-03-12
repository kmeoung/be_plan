package jp.wasabeef.glide.transformations.internal;

/* loaded from: classes.dex */
public class RSBlur {
    /* JADX WARN: Removed duplicated region for block: B:29:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0067  */
    @android.annotation.TargetApi(18)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap blur(android.content.Context r4, android.graphics.Bitmap r5, int r6) throws android.renderscript.RSRuntimeException {
        /*
            r0 = 0
            android.renderscript.RenderScript r4 = android.renderscript.RenderScript.create(r4)     // Catch: all -> 0x0052
            android.renderscript.RenderScript$RSMessageHandler r1 = new android.renderscript.RenderScript$RSMessageHandler     // Catch: all -> 0x004f
            r1.<init>()     // Catch: all -> 0x004f
            r4.setMessageHandler(r1)     // Catch: all -> 0x004f
            android.renderscript.Allocation$MipmapControl r1 = android.renderscript.Allocation.MipmapControl.MIPMAP_NONE     // Catch: all -> 0x004f
            r2 = 1
            android.renderscript.Allocation r1 = android.renderscript.Allocation.createFromBitmap(r4, r5, r1, r2)     // Catch: all -> 0x004f
            android.renderscript.Type r2 = r1.getType()     // Catch: all -> 0x004c
            android.renderscript.Allocation r2 = android.renderscript.Allocation.createTyped(r4, r2)     // Catch: all -> 0x004c
            android.renderscript.Element r3 = android.renderscript.Element.U8_4(r4)     // Catch: all -> 0x0048
            android.renderscript.ScriptIntrinsicBlur r3 = android.renderscript.ScriptIntrinsicBlur.create(r4, r3)     // Catch: all -> 0x0048
            r3.setInput(r1)     // Catch: all -> 0x0046
            float r6 = (float) r6     // Catch: all -> 0x0046
            r3.setRadius(r6)     // Catch: all -> 0x0046
            r3.forEach(r2)     // Catch: all -> 0x0046
            r2.copyTo(r5)     // Catch: all -> 0x0046
            if (r4 == 0) goto L_0x0036
            r4.destroy()
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.destroy()
        L_0x003b:
            if (r2 == 0) goto L_0x0040
            r2.destroy()
        L_0x0040:
            if (r3 == 0) goto L_0x0045
            r3.destroy()
        L_0x0045:
            return r5
        L_0x0046:
            r5 = move-exception
            goto L_0x004a
        L_0x0048:
            r5 = move-exception
            r3 = r0
        L_0x004a:
            r0 = r2
            goto L_0x0056
        L_0x004c:
            r5 = move-exception
            r3 = r0
            goto L_0x0056
        L_0x004f:
            r5 = move-exception
            r1 = r0
            goto L_0x0055
        L_0x0052:
            r5 = move-exception
            r4 = r0
            r1 = r4
        L_0x0055:
            r3 = r1
        L_0x0056:
            if (r4 == 0) goto L_0x005b
            r4.destroy()
        L_0x005b:
            if (r1 == 0) goto L_0x0060
            r1.destroy()
        L_0x0060:
            if (r0 == 0) goto L_0x0065
            r0.destroy()
        L_0x0065:
            if (r3 == 0) goto L_0x006a
            r3.destroy()
        L_0x006a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.wasabeef.glide.transformations.internal.RSBlur.blur(android.content.Context, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }
}
