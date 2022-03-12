package eu.davidea.viewholders;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/* loaded from: classes.dex */
public abstract class ContentViewHolder extends RecyclerView.ViewHolder {
    private View contentView;
    private int mBackupPosition;

    public ContentViewHolder(View view, FlexibleAdapter flexibleAdapter, boolean z) {
        super(z ? new FrameLayout(view.getContext()) : view);
        this.mBackupPosition = -1;
        if (z) {
            this.itemView.setLayoutParams(flexibleAdapter.getRecyclerView().getLayoutManager().generateLayoutParams(view.getLayoutParams()));
            ((FrameLayout) this.itemView).addView(view);
            float elevation = ViewCompat.getElevation(view);
            if (elevation > 0.0f) {
                ViewCompat.setBackground(this.itemView, view.getBackground());
                ViewCompat.setElevation(this.itemView, elevation);
            }
            this.contentView = view;
        }
    }

    public final View getContentView() {
        return this.contentView != null ? this.contentView : this.itemView;
    }

    public final int getFlexibleAdapterPosition() {
        int adapterPosition = getAdapterPosition();
        return adapterPosition == -1 ? this.mBackupPosition : adapterPosition;
    }

    public final void setBackupPosition(int i) {
        this.mBackupPosition = i;
    }
}
