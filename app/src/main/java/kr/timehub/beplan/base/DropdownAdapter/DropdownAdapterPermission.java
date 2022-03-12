package kr.timehub.beplan.base.DropdownAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanInitProject;

/* loaded from: classes.dex */
public class DropdownAdapterPermission extends ArrayAdapter<BeanInitProject.Auth> {
    private Context context;
    List<BeanInitProject.Auth> data;

    public DropdownAdapterPermission(Context context, List<BeanInitProject.Auth> list) {
        super(context, (int) R.layout.listitem_permission, list);
        this.data = null;
        this.context = context;
        this.data = list;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    @NonNull
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.listitem_permission, (ViewGroup) null);
        }
        ((TextView) view.findViewById(R.id.tv_permissions)).setText(this.data.get(i).Value);
        return view;
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Context context = this.context;
            Context context2 = this.context;
            view = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.listitem_permission, viewGroup, false);
        }
        if (view.getTag() == null) {
            Holder holder = new Holder();
            holder.setPermission((TextView) view.findViewById(R.id.tv_permissions));
            view.setTag(holder);
        }
        ((Holder) view.getTag()).getPermission().setText(this.data.get(i).Value);
        return view;
    }

    /* loaded from: classes.dex */
    public static class Holder {
        TextView permission;

        public TextView getPermission() {
            return this.permission;
        }

        public void setPermission(TextView textView) {
            this.permission = textView;
        }
    }
}
