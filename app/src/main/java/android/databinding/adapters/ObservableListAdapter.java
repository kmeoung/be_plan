package android.databinding.adapters;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ObservableListAdapter<T> extends BaseAdapter {
    private final Context mContext;
    private final int mDropDownResourceId;
    private final LayoutInflater mLayoutInflater;
    private List<T> mList;
    private ObservableList.OnListChangedCallback mListChangedCallback;
    private final int mResourceId;
    private final int mTextViewResourceId;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public ObservableListAdapter(Context context, List<T> list, int i, int i2, int i3) {
        this.mContext = context;
        this.mResourceId = i;
        this.mDropDownResourceId = i2;
        this.mTextViewResourceId = i3;
        this.mLayoutInflater = i == 0 ? null : (LayoutInflater) context.getSystemService("layout_inflater");
        setList(list);
    }

    public void setList(List<T> list) {
        if (this.mList != list) {
            if (this.mList instanceof ObservableList) {
                ((ObservableList) this.mList).removeOnListChangedCallback(this.mListChangedCallback);
            }
            this.mList = list;
            if (this.mList instanceof ObservableList) {
                if (this.mListChangedCallback == null) {
                    this.mListChangedCallback = new ObservableList.OnListChangedCallback() { // from class: android.databinding.adapters.ObservableListAdapter.1
                        @Override // android.databinding.ObservableList.OnListChangedCallback
                        public void onChanged(ObservableList observableList) {
                            ObservableListAdapter.this.notifyDataSetChanged();
                        }

                        @Override // android.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeChanged(ObservableList observableList, int i, int i2) {
                            ObservableListAdapter.this.notifyDataSetChanged();
                        }

                        @Override // android.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeInserted(ObservableList observableList, int i, int i2) {
                            ObservableListAdapter.this.notifyDataSetChanged();
                        }

                        @Override // android.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeMoved(ObservableList observableList, int i, int i2, int i3) {
                            ObservableListAdapter.this.notifyDataSetChanged();
                        }

                        @Override // android.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeRemoved(ObservableList observableList, int i, int i2) {
                            ObservableListAdapter.this.notifyDataSetChanged();
                        }
                    };
                }
                ((ObservableList) this.mList).addOnListChangedCallback(this.mListChangedCallback);
            }
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getViewForResource(this.mResourceId, i, view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getViewForResource(this.mDropDownResourceId, i, view, viewGroup);
    }

    public View getViewForResource(int i, int i2, View view, ViewGroup viewGroup) {
        CharSequence charSequence;
        if (view == null) {
            if (i == 0) {
                view = new TextView(this.mContext);
            } else {
                view = this.mLayoutInflater.inflate(i, viewGroup, false);
            }
        }
        TextView textView = (TextView) (this.mTextViewResourceId == 0 ? view : view.findViewById(this.mTextViewResourceId));
        T t = this.mList.get(i2);
        if (t instanceof CharSequence) {
            charSequence = (CharSequence) t;
        } else {
            charSequence = String.valueOf(t);
        }
        textView.setText(charSequence);
        return view;
    }
}
