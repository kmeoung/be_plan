package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class Button extends AppCompatButton {
    public Button(Context context) {
        super(context);
    }

    public Button(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Utils.setFontWithTextView(context, attributeSet, this);
    }

    public Button(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Utils.setFontWithTextView(context, attributeSet, this);
    }
}
