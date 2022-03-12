package com.naver.temy123.baseproject.base.Widgets;

import android.app.Dialog;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BaseDialogManager {
    private static BaseDialogManager instance;
    private ArrayList<Dialog> dialogs = new ArrayList<>();

    public static BaseDialogManager getInstance() {
        if (instance == null) {
            instance = new BaseDialogManager();
        }
        return instance;
    }

    private BaseDialogManager() {
    }

    public ArrayList<Dialog> getRunningDialog() {
        ArrayList<Dialog> arrayList = new ArrayList<>();
        if (this.dialogs != null) {
            Iterator<Dialog> it = this.dialogs.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        return arrayList;
    }

    public void show(Dialog dialog) {
        if (dialog != null) {
            this.dialogs.add(dialog);
            dialog.show();
        }
    }

    public boolean isShowing(int i) {
        Dialog dialog = this.dialogs.get(i);
        if (dialog != null) {
            return dialog.isShowing();
        }
        this.dialogs.remove(i);
        return false;
    }

    public Dialog lastDialog() {
        if (this.dialogs.size() > 0) {
            return this.dialogs.get(this.dialogs.size() - 1);
        }
        return null;
    }

    private Dialog firstDialog() {
        if (this.dialogs.size() > 0) {
            return this.dialogs.get(0);
        }
        return null;
    }

    public void dismiss(int i) {
        Log.d("DialogManager", "-- dismiss, size() : " + this.dialogs.size() + ", index : " + i);
        if (this.dialogs.size() > 0) {
            Dialog dialog = this.dialogs.get(i);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (this.dialogs.size() > i) {
                this.dialogs.remove(i);
            }
        }
    }
}
