package kr.timehub.beplan.base.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import kr.timehub.beplan.activity.ActivityMain;

@Deprecated
/* loaded from: classes.dex */
public abstract class SwipeHelper extends ItemTouchHelper.Callback {
    public static final int BUTTON_WIDTH = 300;
    ActivityMain context;
    private GestureDetector gestureDetector;
    BaseMoveRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private int swipedPos = -1;
    private float swipeThreshold = 0.5f;
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: kr.timehub.beplan.base.objects.SwipeHelper.1
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            Iterator it = SwipeHelper.this.buttons.iterator();
            while (it.hasNext() && !((UnderlayButton) it.next()).onClick(motionEvent.getX(), motionEvent.getY())) {
            }
            return true;
        }
    };
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: kr.timehub.beplan.base.objects.SwipeHelper.2
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (SwipeHelper.this.swipedPos < 0) {
                return false;
            }
            Point point = new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = SwipeHelper.this.recyclerView.findViewHolderForAdapterPosition(SwipeHelper.this.swipedPos);
            if (findViewHolderForAdapterPosition.itemView != null) {
                View view2 = findViewHolderForAdapterPosition.itemView;
                Rect rect = new Rect();
                view2.getGlobalVisibleRect(rect);
                if (motionEvent.getAction() == 0 || motionEvent.getAction() == 1 || motionEvent.getAction() == 2) {
                    if (rect.top >= point.y || rect.bottom <= point.y) {
                        SwipeHelper.this.recoverQueue.add(Integer.valueOf(SwipeHelper.this.swipedPos));
                        SwipeHelper.this.swipedPos = -1;
                        SwipeHelper.this.recoverSwipedItem();
                    } else {
                        SwipeHelper.this.gestureDetector.onTouchEvent(motionEvent);
                    }
                }
            }
            return false;
        }
    };
    private List<UnderlayButton> buttons = new ArrayList();
    private Map<Integer, List<UnderlayButton>> buttonsBuffer = new HashMap();
    private Queue<Integer> recoverQueue = new LinkedList<Integer>() { // from class: kr.timehub.beplan.base.objects.SwipeHelper.3
        public boolean add(Integer num) {
            if (contains(num)) {
                return false;
            }
            return super.add((AnonymousClass3) num);
        }
    };

    /* loaded from: classes.dex */
    public interface UnderlayButtonClickListener {
        void onClick(int i);
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public float getSwipeEscapeVelocity(float f) {
        return f * 0.1f;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public float getSwipeVelocityThreshold(float f) {
        return f * 5.0f;
    }

    public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> list);

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter = this.mAdapter;
        return makeMovementFlags(0, itemViewType != 1 ? 16 : 0);
    }

    public SwipeHelper(ActivityMain activityMain, RecyclerView recyclerView, BaseMoveRecyclerViewAdapter baseMoveRecyclerViewAdapter) {
        this.recyclerView = recyclerView;
        this.gestureDetector = new GestureDetector(activityMain, this.gestureListener);
        this.recyclerView.setOnTouchListener(this.onTouchListener);
        this.mAdapter = baseMoveRecyclerViewAdapter;
        this.context = activityMain;
        attachSwipe();
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

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (this.swipedPos != adapterPosition) {
            this.recoverQueue.add(Integer.valueOf(this.swipedPos));
        }
        this.swipedPos = adapterPosition;
        if (this.buttonsBuffer.containsKey(Integer.valueOf(this.swipedPos))) {
            this.buttons = this.buttonsBuffer.get(Integer.valueOf(this.swipedPos));
        } else {
            this.buttons.clear();
        }
        this.buttonsBuffer.clear();
        this.swipeThreshold = this.buttons.size() * 0.5f * 300.0f;
        recoverSwipedItem();
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return this.swipeThreshold;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        float f3;
        int adapterPosition = viewHolder.getAdapterPosition();
        View view = viewHolder.itemView;
        if (adapterPosition < 0) {
            this.swipedPos = adapterPosition;
            return;
        }
        if (i != 1 || f >= 0.0f) {
            f3 = f;
        } else {
            List<UnderlayButton> arrayList = new ArrayList<>();
            if (!this.buttonsBuffer.containsKey(Integer.valueOf(adapterPosition))) {
                instantiateUnderlayButton(viewHolder, arrayList);
                this.buttonsBuffer.put(Integer.valueOf(adapterPosition), arrayList);
            } else {
                arrayList = this.buttonsBuffer.get(Integer.valueOf(adapterPosition));
            }
            float size = ((f * arrayList.size()) * 300.0f) / view.getWidth();
            drawButtons(canvas, view, arrayList, adapterPosition, size);
            f3 = size;
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, f3, f2, i, z);
    }

    public synchronized void recoverSwipedItem() {
        while (!this.recoverQueue.isEmpty()) {
            int intValue = this.recoverQueue.poll().intValue();
            if (intValue > -1) {
                this.recyclerView.getAdapter().notifyItemChanged(intValue);
            }
        }
    }

    private void drawButtons(Canvas canvas, View view, List<UnderlayButton> list, int i, float f) {
        float right = view.getRight();
        float size = (f * (-1.0f)) / list.size();
        for (UnderlayButton underlayButton : list) {
            float f2 = right - size;
            underlayButton.onDraw(canvas, new RectF(f2, view.getTop(), right, view.getBottom()), i);
            right = f2;
        }
    }

    public void attachSwipe() {
        new ItemTouchHelper(this).attachToRecyclerView(this.recyclerView);
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
