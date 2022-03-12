package kr.timehub.beplan.base.objects;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private ArrayList<View> mViewArray = new ArrayList<>();

    public static final BaseViewHolder newInstance(Context context, int i) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(i, (ViewGroup) null, false));
    }

    public static final BaseViewHolder newInstance(Context context, int i, ViewGroup viewGroup) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(i, viewGroup, false));
    }

    public static final BaseViewHolder newInstance(View view) {
        return new BaseViewHolder(view);
    }

    private BaseViewHolder(View view) {
        super(view);
    }

    public <T extends View> T getView(int i) {
        for (int i2 = 0; i2 < this.mViewArray.size(); i2++) {
            T t = (T) this.mViewArray.get(i2);
            if (t.getId() == i) {
                return t;
            }
        }
        T t2 = (T) this.itemView.findViewById(i);
        if (t2 != null) {
            this.mViewArray.add(t2);
        } else {
            ThrowableExtension.printStackTrace(new Exception(String.format("(getView (%d) ) is NULL!!", Integer.valueOf(i))));
        }
        return t2;
    }

    public void setText(int i, CharSequence charSequence) {
        View view = getView(i);
        if (view instanceof TextView) {
            ((TextView) view).setText(charSequence);
        } else if (view instanceof EditText) {
            ((EditText) view).setText(charSequence);
        }
    }

    public void setHint(int i, CharSequence charSequence) {
        View view = getView(i);
        if (view instanceof TextView) {
            ((TextView) view).setHint(charSequence);
        } else if (view instanceof EditText) {
            ((EditText) view).setHint(charSequence);
        }
    }

    public void setWidth(int i, int i2) {
        View view = getView(i);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i2;
        view.setLayoutParams(layoutParams);
    }

    public void setHeight(int i, int i2) {
        View view = getView(i);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    public void setSize(int i, int i2, int i3) {
        View view = getView(i);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i3;
        view.setLayoutParams(layoutParams);
    }

    public int getMeasureWidth(int i) {
        View view = getView(i);
        view.measure(0, 0);
        return view.getMeasuredWidth();
    }

    public int getMeasureHeight(int i) {
        View view = getView(i);
        view.measure(0, 0);
        return view.getMeasuredHeight();
    }

    public void setTextColor(int i, Object obj) {
        View view = getView(i);
        if (view instanceof TextView) {
            if (obj instanceof Integer) {
                ((TextView) view).setTextColor(((Integer) obj).intValue());
            } else if (obj instanceof ColorStateList) {
                ((TextView) view).setTextColor((ColorStateList) obj);
            }
        } else if (!(view instanceof EditText)) {
        } else {
            if (obj instanceof Integer) {
                ((EditText) view).setTextColor(((Integer) obj).intValue());
            } else if (obj instanceof ColorStateList) {
                ((EditText) view).setTextColor((ColorStateList) obj);
            }
        }
    }

    public void setHintColor(int i, Object obj) {
        View view = getView(i);
        if (view instanceof TextView) {
            if (obj instanceof Integer) {
                ((TextView) view).setHintTextColor(((Integer) obj).intValue());
            } else if (obj instanceof ColorStateList) {
                ((TextView) view).setHintTextColor((ColorStateList) obj);
            }
        } else if (!(view instanceof EditText)) {
        } else {
            if (obj instanceof Integer) {
                ((EditText) view).setHintTextColor(((Integer) obj).intValue());
            } else if (obj instanceof ColorStateList) {
                ((EditText) view).setHintTextColor((ColorStateList) obj);
            }
        }
    }

    public void setImage(int i, Object obj) {
        if (obj instanceof Drawable) {
            getImageView(i).setImageDrawable((Drawable) obj);
        } else if (obj instanceof Integer) {
            getImageView(i).setImageResource(((Integer) obj).intValue());
        } else if (obj instanceof Bitmap) {
            getImageView(i).setImageBitmap((Bitmap) obj);
        }
    }

    public void setBackground(int i, Object obj) {
        if (obj instanceof Drawable) {
            getView(i).setBackground((Drawable) obj);
        } else if (obj instanceof Integer) {
            getView(i).setBackgroundResource(((Integer) obj).intValue());
        }
    }

    @Deprecated
    public ImageView getImageView(int i) {
        return (ImageView) this.itemView.findViewById(i);
    }

    @Deprecated
    public TextView getTextView(int i) {
        return (TextView) this.itemView.findViewById(i);
    }

    @Deprecated
    public EditText getEditText(int i) {
        return (EditText) this.itemView.findViewById(i);
    }
}
