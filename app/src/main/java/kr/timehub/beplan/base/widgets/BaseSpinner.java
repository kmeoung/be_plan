package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class BaseSpinner extends AppCompatSpinner {
    public BaseSpinner(Context context) {
        super(context);
    }

    public BaseSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.AbsSpinner
    public void setSelection(int i, boolean z) {
        boolean z2 = i == getSelectedItemPosition();
        super.setSelection(i, z);
        if (z2) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), i, getSelectedItemId());
        }
    }

    @Override // android.widget.AbsSpinner, android.widget.AdapterView
    public void setSelection(int i) {
        boolean z = i == getSelectedItemPosition();
        super.setSelection(i);
        if (z) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), i, getSelectedItemId());
        }
    }
}
