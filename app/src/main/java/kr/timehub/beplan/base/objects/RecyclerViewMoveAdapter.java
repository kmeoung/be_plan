package kr.timehub.beplan.base.objects;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class RecyclerViewMoveAdapter extends RecyclerView.Adapter {
    BaseRecyclerViewAdapterInterface mAction;
    private ArrayList mArray;

    public RecyclerViewMoveAdapter(BaseRecyclerViewAdapterInterface baseRecyclerViewAdapterInterface) {
        this.mArray = new ArrayList();
        this.mAction = baseRecyclerViewAdapterInterface;
    }

    public RecyclerViewMoveAdapter(ArrayList arrayList, BaseRecyclerViewAdapterInterface baseRecyclerViewAdapterInterface) {
        this.mArray = new ArrayList();
        this.mArray = arrayList;
        this.mAction = baseRecyclerViewAdapterInterface;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mAction.onCreateViewHolder(viewGroup, i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        this.mAction.onBindViewHolder(viewHolder, i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mAction.getItemCount();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mAction.getItemViewType(i);
    }
}
