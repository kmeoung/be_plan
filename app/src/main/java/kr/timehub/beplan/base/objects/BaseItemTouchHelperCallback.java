package kr.timehub.beplan.base.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;
import kr.timehub.beplan.activity.ActivityMain;

/* loaded from: classes.dex */
public abstract class BaseItemTouchHelperCallback extends ItemTouchHelper.Callback {
    public static final int BUTTON_WIDTH = 300;
    ActivityMain context;
    BaseMoveRecyclerViewAdapter mAdapter;
    private boolean swipeBack = false;

    /* loaded from: classes.dex */
    public interface UnderlayButtonClickListener {
        void onClick(int i);
    }

    public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, UnderlayButton underlayButton);

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
        return makeMovementFlags(0, itemViewType != 1 ? 16 : 0);
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        if (viewHolder2.getAdapterPosition() == 0) {
            int itemViewType = viewHolder2.getItemViewType();
            BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
            if (itemViewType == 1) {
                return false;
            }
        }
        this.mAdapter.onItemMove(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
        return false;
    }

    public BaseItemTouchHelperCallback(ActivityMain activityMain, BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter) {
        this.mAdapter = baseMoveRecyclerViewAdapter;
        this.context = activityMain;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public int convertToAbsoluteDirection(int i, int i2) {
        if (!this.swipeBack) {
            return super.convertToAbsoluteDirection(i, i2);
        }
        this.swipeBack = false;
        return 0;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        BaseItemTouchHelperCallback baseItemTouchHelperCallback;
        View view = viewHolder.itemView;
        if (i == 1) {
            viewHolder.getAdapterPosition();
            int itemViewType = viewHolder.getItemViewType();
            baseItemTouchHelperCallback = this;
            BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = baseItemTouchHelperCallback.mAdapter;
            if (itemViewType != 1) {
                int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
            }
        } else {
            baseItemTouchHelperCallback = this;
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, f / 4.0f, f2, i, z);
    }

    private void drawButtons(Canvas canvas, View view, int i, float f, UnderlayButton underlayButton) {
        float right = view.getRight();
        underlayButton.onDraw(canvas, new RectF(right - ((f * (-1.0f)) / 1.0f), view.getTop(), right, view.getBottom()), i);
    }

    /* loaded from: classes.dex */
    public static class UnderlayButton {
        private UnderlayButtonClickListener clickListener;
        private RectF clickRegion;
        private int color;
        private Context context;
        private int imageResId;
        private int pos;
        private String text;

        public UnderlayButton(Context context, String str, int i, int i2, UnderlayButtonClickListener underlayButtonClickListener) {
            this.context = context;
            this.text = str;
            this.imageResId = i;
            this.color = i2;
            this.clickListener = underlayButtonClickListener;
        }

        public boolean onClick(float f, float f2) {
            if (this.clickRegion == null || !this.clickRegion.contains(f, f2)) {
                return false;
            }
            this.clickListener.onClick(this.pos);
            return true;
        }

        public void onDraw(Canvas canvas, RectF rectF, int i) {
            Paint paint = new Paint();
            paint.setColor(this.color);
            canvas.drawRect(rectF, paint);
            paint.setColor(-1);
            paint.setTextSize(TypedValue.applyDimension(1, 12.0f, this.context.getResources().getDisplayMetrics()));
            Rect rect = new Rect();
            float height = rectF.height();
            float width = rectF.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds(this.text, 0, this.text.length(), rect);
            canvas.drawText(this.text, rectF.left + (((width / 2.0f) - (rect.width() / 2.0f)) - rect.left), rectF.top + (((height / 2.0f) + (rect.height() / 2.0f)) - rect.bottom), paint);
            this.clickRegion = rectF;
            this.pos = i;
        }
    }
}
