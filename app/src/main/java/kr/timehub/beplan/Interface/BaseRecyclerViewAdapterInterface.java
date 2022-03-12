package kr.timehub.beplan.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public interface BaseRecyclerViewAdapterInterface {
    int getItemCount();

    int getItemViewType(int i);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i);

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i);
}
