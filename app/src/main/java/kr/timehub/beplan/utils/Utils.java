package kr.timehub.beplan.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import com.naver.temy123.baseproject.base.Utils.Log;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import kr.timehub.beplan.Interface.IOnOnceDataResponseListener;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseApplication;
import kr.timehub.beplan.base.objects.BaseRecyclerViewAdapter;
import kr.timehub.beplan.entry.plugin.BeanProjectList;
import kr.timehub.beplan.entry.plugin.BeanTaskReceiveList;
import kr.timehub.beplan.entry.plugin.BeanTaskSendList;
import kr.timehub.beplan.http.HttpBindTempData;

/* loaded from: classes.dex */
public class Utils {
    public static void filterReceiveList(Context context, String str, BaseRecyclerViewAdapter baseRecyclerViewAdapter, BeanTaskReceiveList beanTaskReceiveList) {
    }

    public static void filterSendList(Context context, String str, BaseRecyclerViewAdapter baseRecyclerViewAdapter, BeanTaskSendList beanTaskSendList) {
    }

    public static final boolean isEqualsVisibleFragment(FragmentManager fragmentManager, int i, Fragment fragment) {
        Fragment findFragmentById = fragmentManager.findFragmentById(i);
        return findFragmentById != null && findFragmentById.getClass().equals(fragment.getClass());
    }

    public static final View setFontWithTextView(Context context, AttributeSet attributeSet, TextView textView) {
        textView.setTypeface(Comm_Params.FONTS.get(Integer.valueOf(context.obtainStyledAttributes(attributeSet, R.styleable.font).getInt(0, -1))));
        return textView;
    }

    public static int getColor(Resources resources, Resources.Theme theme, @ColorRes int i) {
        if (Build.VERSION.SDK_INT < 23) {
            return resources.getColor(i);
        }
        return resources.getColor(i, theme);
    }

    public static void setTextColorGradient(Context context, TextView textView) {
        int color = getColor(context.getResources(), context.getTheme(), R.color.gradient_left);
        int color2 = getColor(context.getResources(), context.getTheme(), R.color.gradient_right);
        textView.measure(0, 0);
        float measuredWidth = textView.getMeasuredWidth();
        textView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, measuredWidth - (measuredWidth / 18.0f), textView.getLineHeight(), color, color2, Shader.TileMode.CLAMP));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kr.timehub.beplan.utils.Utils$1] */
    public static void getPicture(final Context context, final String str, final IOnOnceDataResponseListener iOnOnceDataResponseListener) {
        new AsyncTask<Void, Void, Bitmap>() { // from class: kr.timehub.beplan.utils.Utils.1
            /* JADX INFO: Access modifiers changed from: protected */
            public Bitmap doInBackground(Void... voidArr) {
                try {
                    return Glide.with(context).asBitmap().load(str).into(100, 100).get();
                } catch (InterruptedException e) {
                    ThrowableExtension.printStackTrace(e);
                    return null;
                } catch (ExecutionException e2) {
                    ThrowableExtension.printStackTrace(e2);
                    return null;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void onPostExecute(Bitmap bitmap) {
                iOnOnceDataResponseListener.onSuccessDataResponsed(null, bitmap);
            }
        }.execute(new Void[0]);
    }

    public static void hideKeyboard(Context context, EditText editText) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @TargetApi(21)
    public static ImageView setCircleImageView(ImageView imageView) {
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        imageView.setClipToOutline(true);
        return imageView;
    }

    public static Date ConvertDate(String str) {
        long longValue = Long.valueOf(str.substring(str.indexOf("(") + 1, str.indexOf("+"))).longValue();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(longValue);
        return instance.getTime();
    }

    public static void SaveBitmapToFileCache(Bitmap bitmap, String str, String str2) {
        Throwable th;
        Exception e;
        FileOutputStream fileOutputStream;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str + str2);
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    file2.createNewFile();
                    fileOutputStream = new FileOutputStream(file2);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } catch (Exception e3) {
                e = e3;
                fileOutputStream2 = fileOutputStream;
                ThrowableExtension.printStackTrace(e);
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                return;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e4) {
                        ThrowableExtension.printStackTrace(e4);
                    }
                }
                throw th;
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e5) {
            ThrowableExtension.printStackTrace(e5);
        }
    }

    public static void filterProjectList(Context context, String str, BaseRecyclerViewAdapter baseRecyclerViewAdapter, BeanProjectList beanProjectList) {
        int i;
        baseRecyclerViewAdapter.clear();
        ArrayList arrayList = new ArrayList();
        String string = context.getString(R.string.str_view_all);
        String string2 = context.getString(R.string.str_view_running);
        String string3 = context.getString(R.string.str_view_wating);
        String string4 = context.getString(R.string.str_view_refuse);
        String string5 = context.getString(R.string.str_view_request_cancel);
        String string6 = context.getString(R.string.str_view_complete);
        Iterator<BeanProjectList.CategoryListBean> it = beanProjectList.getCategoryList().iterator();
        while (true) {
            if (it.hasNext()) {
                BeanProjectList.CategoryListBean next = it.next();
                BeanProjectList.CategoryListBean categoryListBean = new BeanProjectList.CategoryListBean();
                categoryListBean.setCGSEQ(next.getCGSEQ());
                categoryListBean.setCateGoryTitle(next.getCateGoryTitle());
                categoryListBean.setTaskList(next.getTaskList());
                categoryListBean.setMakeID(next.getMakeID());
                categoryListBean.setIsModify(next.isIsModify());
                categoryListBean.setIsDelete(next.isIsDelete());
                categoryListBean.setIsRunnerModify(next.isIsRunnerModify());
                arrayList.add(categoryListBean);
                Iterator<BeanProjectList.CategoryListBean.TaskListBean> it2 = next.getTaskList().iterator();
                while (true) {
                    boolean z = false;
                    while (it2.hasNext()) {
                        BeanProjectList.CategoryListBean.TaskListBean next2 = it2.next();
                        next2 = null;
                        if (!TextUtils.isEmpty(next2.getTaskState().trim())) {
                            if (!TextUtils.equals(str, string)) {
                                if (TextUtils.equals(str, string2)) {
                                }
                            }
                            if (next2 != null) {
                                arrayList.add(next2);
                            }
                            if (z) {
                                continue;
                            } else if (next2 != null) {
                                z = true;
                            }
                        }
                    }
                }
            }
        }
        for (i = 0; i < arrayList.size(); i++) {
            baseRecyclerViewAdapter.add(arrayList.get(i));
        }
        baseRecyclerViewAdapter.notifyDataSetChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0086 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void filterProjectMyList(android.content.Context r10, java.lang.String r11, kr.timehub.beplan.base.objects.BaseRecyclerViewAdapter r12, kr.timehub.beplan.entry.plugin.BeanProjectList r13) {
        /*
            r12.clear()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 2131689604(0x7f0f0084, float:1.9008228E38)
            java.lang.String r1 = r10.getString(r1)
            r2 = 2131689608(0x7f0f0088, float:1.9008236E38)
            java.lang.String r2 = r10.getString(r2)
            r3 = 2131689609(0x7f0f0089, float:1.9008238E38)
            r10.getString(r3)
            r3 = 2131689606(0x7f0f0086, float:1.9008232E38)
            r10.getString(r3)
            r3 = 2131689607(0x7f0f0087, float:1.9008234E38)
            r10.getString(r3)
            r3 = 2131689605(0x7f0f0085, float:1.900823E38)
            java.lang.String r10 = r10.getString(r3)
            java.util.List r13 = r13.getCategoryList()
            java.util.Iterator r13 = r13.iterator()
        L_0x0037:
            boolean r3 = r13.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x00f3
            java.lang.Object r3 = r13.next()
            kr.timehub.beplan.entry.plugin.BeanProjectList$CategoryListBean r3 = (kr.timehub.beplan.entry.plugin.BeanProjectList.CategoryListBean) r3
            kr.timehub.beplan.entry.plugin.BeanProjectList$CategoryListBean r5 = new kr.timehub.beplan.entry.plugin.BeanProjectList$CategoryListBean
            r5.<init>()
            int r6 = r3.getCGSEQ()
            r5.setCGSEQ(r6)
            java.lang.String r6 = r3.getCateGoryTitle()
            r5.setCateGoryTitle(r6)
            java.util.List r6 = r3.getTaskList()
            r5.setTaskList(r6)
            int r6 = r3.getMakeID()
            r5.setMakeID(r6)
            boolean r6 = r3.isIsModify()
            r5.setIsModify(r6)
            boolean r6 = r3.isIsDelete()
            r5.setIsDelete(r6)
            boolean r6 = r3.isIsRunnerModify()
            r5.setIsRunnerModify(r6)
            r0.add(r5)
            java.util.List r3 = r3.getTaskList()
            java.util.Iterator r3 = r3.iterator()
        L_0x0085:
            r5 = 0
        L_0x0086:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x0037
            java.lang.Object r6 = r3.next()
            kr.timehub.beplan.entry.plugin.BeanProjectList$CategoryListBean$TaskListBean r6 = (kr.timehub.beplan.entry.plugin.BeanProjectList.CategoryListBean.TaskListBean) r6
            r7 = 0
            java.lang.String r8 = r6.getTaskState()
            java.lang.String r8 = r8.trim()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 == 0) goto L_0x00a2
            goto L_0x0086
        L_0x00a2:
            boolean r8 = android.text.TextUtils.equals(r11, r1)
            if (r8 == 0) goto L_0x00c1
            java.lang.String r8 = r6.getTaskState()
            java.lang.String r9 = "I"
            boolean r8 = r8.contains(r9)
            if (r8 != 0) goto L_0x00e8
            java.lang.String r8 = r6.getTaskState()
            java.lang.String r9 = "F"
            boolean r8 = r8.contains(r9)
            if (r8 == 0) goto L_0x00e7
            goto L_0x00e8
        L_0x00c1:
            boolean r8 = android.text.TextUtils.equals(r11, r2)
            if (r8 == 0) goto L_0x00d4
            java.lang.String r8 = r6.getTaskState()
            java.lang.String r9 = "I"
            boolean r8 = r8.contains(r9)
            if (r8 == 0) goto L_0x00e7
            goto L_0x00e8
        L_0x00d4:
            boolean r8 = android.text.TextUtils.equals(r11, r10)
            if (r8 == 0) goto L_0x00e7
            java.lang.String r8 = r6.getTaskState()
            java.lang.String r9 = "F"
            boolean r8 = r8.contains(r9)
            if (r8 == 0) goto L_0x00e7
            goto L_0x00e8
        L_0x00e7:
            r6 = r7
        L_0x00e8:
            if (r6 == 0) goto L_0x00ed
            r0.add(r6)
        L_0x00ed:
            if (r5 != 0) goto L_0x0086
            if (r6 == 0) goto L_0x0085
            r5 = 1
            goto L_0x0086
        L_0x00f3:
            int r10 = r0.size()
            if (r4 >= r10) goto L_0x0103
            java.lang.Object r10 = r0.get(r4)
            r12.add(r10)
            int r4 = r4 + 1
            goto L_0x00f3
        L_0x0103:
            r12.notifyDataSetChanged()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kr.timehub.beplan.utils.Utils.filterProjectMyList(android.content.Context, java.lang.String, kr.timehub.beplan.base.objects.BaseRecyclerViewAdapter, kr.timehub.beplan.entry.plugin.BeanProjectList):void");
    }

    public static String getImgRealPath(String str) {
        if (!str.contains("@")) {
            return "";
        }
        String substring = str.substring(0, str.indexOf("@"));
        return HttpBindTempData.PostFileImageUrl + substring;
    }

    public static void Logout(final ActivityMain activityMain, final Context context) {
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= 21) {
            instance.removeSessionCookies(null);
            instance.removeAllCookies(new ValueCallback<Boolean>() { // from class: kr.timehub.beplan.utils.Utils.2
                public void onReceiveValue(Boolean bool) {
                    Log.d("", "## 롤리팝 이상 버전의 removeSessionCookie() 호출 후");
                    HWOkHttpClient.getInstance(context).removeCookies();
                    context.startActivity(new Intent(context, ActivityIntro.class));
                    activityMain.finish();
                }
            });
            return;
        }
        instance.removeSessionCookie();
        instance.removeAllCookie();
        HWOkHttpClient.getInstance(context).removeCookies();
        Log.d("", "## 롤리팝 미만 버전의 removeSessionCookie() 호출 후");
        context.startActivity(new Intent(context, ActivityIntro.class));
        activityMain.finish();
    }

    public static void setDropDownHeight(Spinner spinner, int i) {
        try {
            Field declaredField = Spinner.class.getDeclaredField("mPopup");
            declaredField.setAccessible(true);
            ((ListPopupWindow) declaredField.get(spinner)).setHeight(i);
        } catch (ClassCastException | IllegalAccessException | NoClassDefFoundError | NoSuchFieldException unused) {
        }
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            try {
                int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                query.moveToFirst();
                String string = query.getString(columnIndexOrThrow);
                if (query != null) {
                    query.close();
                }
                return string;
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void callImagePicker(Fragment fragment) {
        callImagePicker(fragment, true);
    }

    public static void callImagePicker(Fragment fragment, boolean z) {
        ImagePicker.with(fragment).setToolbarColor("#FFFFFF").setStatusBarColor("#00FFFFFF").setToolbarTextColor("#FFFF6D2D").setToolbarIconColor("#FFFF6D2D").setProgressBarColor("#FFFF6D2D").setBackgroundColor("#FFFFFF").setCameraOnly(false).setMultipleMode(z).setFolderMode(true).setShowCamera(false).setFolderTitle("갤러리").setImageTitle("갤러리").setDoneTitle("완료").setLimitMessage("이미지를 최대수까지 지정하셨습니다").setSavePath("ImagePicker").setKeepScreenOn(true).start();
    }

    public static void callCamera(Fragment fragment) {
        ImagePicker.with(fragment).setToolbarColor("#FFFFFF").setStatusBarColor("#00FFFFFF").setToolbarTextColor("#FFFF6D2D").setToolbarIconColor("#FFFF6D2D").setProgressBarColor("#FFFF6D2D").setBackgroundColor("#FFFFFF").setCameraOnly(true).setMultipleMode(true).setFolderMode(true).setShowCamera(true).setFolderTitle("카메라").setImageTitle("카메라").setDoneTitle("완료").setLimitMessage("이미지를 최대수까지 지정하셨습니다").setSavePath("ImagePicker").setKeepScreenOn(true).start();
    }

    public static void callImagePicker(Activity activity) {
        ImagePicker.with(activity).setToolbarColor("#FFFFFF").setStatusBarColor("#00FFFFFF").setToolbarTextColor("#FFFF6D2D").setToolbarIconColor("#FFFF6D2D").setProgressBarColor("#FFFF6D2D").setBackgroundColor("#FFFFFF").setCameraOnly(false).setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("갤러리").setImageTitle("갤러리").setDoneTitle("완료").setLimitMessage("이미지를 최대수까지 지정하셨습니다").setSavePath("ImagePicker").setKeepScreenOn(true).start();
    }

    public static Bitmap FileToBitmap(File file) {
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getPath());
        if (decodeFile != null) {
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                decodeFile.compress(Bitmap.CompressFormat.JPEG, 50, bufferedOutputStream);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (FileNotFoundException e) {
                ThrowableExtension.printStackTrace(e);
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            } catch (NullPointerException e3) {
                ThrowableExtension.printStackTrace(e3);
            }
        }
        return decodeFile;
    }

    public static final boolean isConnectedInternet(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnectedOrConnecting()) ? false : true;
    }

    public static void displayDialog(Dialog dialog, Activity activity) {
        if (dialog != null && activity != null && !activity.isFinishing()) {
            dialog.show();
        }
    }

    public static void startGoogleMarket() {
        Context appContext = BaseApplication.getAppContext();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=" + appContext.getPackageName()));
        appContext.startActivity(intent);
    }
}
