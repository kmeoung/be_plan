package jp.wasabeef.glide.transformations.internal;

import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class FastBlur {
    public static Bitmap blur(Bitmap bitmap, int i, boolean z) {
        int i2 = i;
        Bitmap copy = z ? bitmap : bitmap.copy(bitmap.getConfig(), true);
        if (i2 < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i3 = width * height;
        int[] iArr = new int[i3];
        copy.getPixels(iArr, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr2 = new int[i3];
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr6 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr6[i10] = i10 / i8;
        }
        int[][] iArr7 = (int[][]) Array.newInstance(int.class, i6, 3);
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            int i15 = -i2;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            while (i15 <= i2) {
                int i25 = iArr[i13 + Math.min(i4, Math.max(i15, 0))];
                int[] iArr8 = iArr7[i15 + i2];
                iArr8[0] = (i25 & 16711680) >> 16;
                iArr8[1] = (i25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr8[2] = i25 & 255;
                int abs = i11 - Math.abs(i15);
                i16 += iArr8[0] * abs;
                i17 += iArr8[1] * abs;
                i18 += iArr8[2] * abs;
                if (i15 > 0) {
                    i19 += iArr8[0];
                    i20 += iArr8[1];
                    i21 += iArr8[2];
                } else {
                    i22 += iArr8[0];
                    i23 += iArr8[1];
                    i24 += iArr8[2];
                }
                i15++;
                height = height;
                i5 = i5;
            }
            int i26 = i2;
            for (int i27 = 0; i27 < width; i27++) {
                iArr2[i13] = iArr6[i16];
                iArr3[i13] = iArr6[i17];
                iArr4[i13] = iArr6[i18];
                int i28 = i16 - i22;
                int i29 = i17 - i23;
                int i30 = i18 - i24;
                int[] iArr9 = iArr7[((i26 - i2) + i6) % i6];
                int i31 = i22 - iArr9[0];
                int i32 = i23 - iArr9[1];
                int i33 = i24 - iArr9[2];
                if (i12 == 0) {
                    iArr6 = iArr6;
                    iArr5[i27] = Math.min(i27 + i2 + 1, i4);
                } else {
                    iArr6 = iArr6;
                }
                int i34 = iArr[i14 + iArr5[i27]];
                iArr9[0] = (i34 & 16711680) >> 16;
                iArr9[1] = (i34 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr9[2] = i34 & 255;
                int i35 = i19 + iArr9[0];
                int i36 = i20 + iArr9[1];
                int i37 = i21 + iArr9[2];
                i16 = i28 + i35;
                i17 = i29 + i36;
                i18 = i30 + i37;
                i26 = (i26 + 1) % i6;
                int[] iArr10 = iArr7[i26 % i6];
                i22 = i31 + iArr10[0];
                i23 = i32 + iArr10[1];
                i24 = i33 + iArr10[2];
                i19 = i35 - iArr10[0];
                i20 = i36 - iArr10[1];
                i21 = i37 - iArr10[2];
                i13++;
            }
            i14 += width;
            i12++;
            copy = copy;
            height = height;
            i5 = i5;
        }
        int i38 = i5;
        int i39 = height;
        int i40 = 0;
        while (i40 < width) {
            int i41 = -i2;
            int i42 = i41 * width;
            int i43 = 0;
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            while (i41 <= i2) {
                int max = Math.max(0, i42) + i40;
                int[] iArr11 = iArr7[i41 + i2];
                iArr11[0] = iArr2[max];
                iArr11[1] = iArr3[max];
                iArr11[2] = iArr4[max];
                int abs2 = i11 - Math.abs(i41);
                i43 += iArr2[max] * abs2;
                i44 += iArr3[max] * abs2;
                i45 += iArr4[max] * abs2;
                if (i41 > 0) {
                    i46 += iArr11[0];
                    i47 += iArr11[1];
                    i48 += iArr11[2];
                } else {
                    i49 += iArr11[0];
                    i50 += iArr11[1];
                    i51 += iArr11[2];
                }
                if (i41 < i38) {
                    i42 += width;
                }
                i41++;
                i38 = i38;
                iArr5 = iArr5;
            }
            int i52 = i40;
            int i53 = i47;
            int i54 = i48;
            int i55 = 0;
            int i56 = i2;
            int i57 = i46;
            int i58 = i45;
            int i59 = i44;
            int i60 = i43;
            while (i55 < i39) {
                iArr[i52] = (iArr[i52] & ViewCompat.MEASURED_STATE_MASK) | (iArr6[i60] << 16) | (iArr6[i59] << 8) | iArr6[i58];
                int i61 = i60 - i49;
                int i62 = i59 - i50;
                int i63 = i58 - i51;
                int[] iArr12 = iArr7[((i56 - i2) + i6) % i6];
                int i64 = i49 - iArr12[0];
                int i65 = i50 - iArr12[1];
                int i66 = i51 - iArr12[2];
                if (i40 == 0) {
                    iArr5[i55] = Math.min(i55 + i11, i38) * width;
                }
                int i67 = iArr5[i55] + i40;
                iArr12[0] = iArr2[i67];
                iArr12[1] = iArr3[i67];
                iArr12[2] = iArr4[i67];
                int i68 = i57 + iArr12[0];
                int i69 = i53 + iArr12[1];
                int i70 = i54 + iArr12[2];
                i60 = i61 + i68;
                i59 = i62 + i69;
                i58 = i63 + i70;
                i56 = (i56 + 1) % i6;
                int[] iArr13 = iArr7[i56];
                i49 = i64 + iArr13[0];
                i50 = i65 + iArr13[1];
                i51 = i66 + iArr13[2];
                i57 = i68 - iArr13[0];
                i53 = i69 - iArr13[1];
                i54 = i70 - iArr13[2];
                i52 += width;
                i55++;
                i2 = i;
            }
            i40++;
            i38 = i38;
            i39 = i39;
            iArr5 = iArr5;
            i2 = i;
        }
        copy.setPixels(iArr, 0, width, 0, 0, width, i39);
        return copy;
    }
}
