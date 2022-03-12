package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.ArrayList;
import java.util.Iterator;
import okhttp3.internal.http.StatusLine;

/* loaded from: classes.dex */
public class BasePlaceView extends LinearLayout {
    public static final int DIRECTION_LINE_1 = 0;
    public static final int DIRECTION_LINE_2 = 1;
    public static final int DIRECTION_LINE_3 = 2;
    public static final int PART_TO_LEFT = 2;
    public static final int PART_TO_RIGHT = 1;
    public static final int PLACE_TYPE_BLANK = Integer.MIN_VALUE;
    public static final int PLACE_TYPE_DEFAULT = 0;
    public static final int PLACE_TYPE_NO_SPACE = -2147483647;
    public static final int PLACE_TYPE_NO_SPACE_CENTER = -2147483646;
    public static final int START_POSITION_LEFT_BOTTOM = 2;
    public static final int START_POSITION_LEFT_TOP = 0;
    public static final int START_POSITION_RIGHT_BOTTOM = 3;
    public static final int START_POSITION_RIGHT_TOP = 1;
    private BaseNoneHorizontalScrollView mHorizontalScrollView;
    private BaseNoneScrollView mScrollView;
    float mx;
    float my;
    private int mWidthMargin = 0;
    private int mHeightMargin = 0;
    private int mWidthBlankMargin = 0;
    private int mHeightBlankMargin = 0;
    private int mCellWidth = StatusLine.HTTP_PERM_REDIRECT;
    private int mCellHeight = StatusLine.HTTP_PERM_REDIRECT;
    private int mCellMargin = 0;
    private int mCellMinWidth = this.mCellWidth;
    private int mCellMinHeight = this.mCellHeight;
    private int mCellMaxWidth = this.mCellWidth * 5;
    private int mCellMaxHeight = this.mCellHeight * 5;
    private int mTextSize = 14;
    private int mTextColor = -1;
    private Object mBackground = null;
    private TableLayout mTableLayout = null;
    private OnPlaceClickListener onPlaceClickListener = null;
    private OnPlaceTextListener onPlaceTextListener = null;
    private OnPlaceViewCreatedListener onPlaceViewCreatedListener = null;
    private ScaleGestureDetector onScaleGestureDetector = null;
    private int mStartPosition = 0;
    private int mBlankTotal = 0;
    private int mDirection = -1;
    private float mScaleFactor = 1.0f;
    private int[][] mTableData = null;
    private ArrayList<Button> mButtons = new ArrayList<>();
    private boolean mCollision = false;
    private boolean mIsCanMove = false;
    private int clickCount = 0;
    private int mMaxMove = 0;
    private float mDragNewDist = 0.0f;
    private float mDragOldDist = 0.0f;

    /* loaded from: classes.dex */
    public interface OnPlaceClickListener {
        void onPlaceClicked(View view, int i);
    }

    /* loaded from: classes.dex */
    public interface OnPlaceTextListener {
        String onPlaceTextListener(View view, int i);
    }

    /* loaded from: classes.dex */
    public interface OnPlaceViewCreatedListener {
        void onPlaceViewCreate(View view, int i);
    }

    private boolean collisionCheck(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return i3 >= i5 && i7 >= i && i8 >= i2 && i4 >= i6;
    }

    private int getDirectionLine3(int i) {
        return i;
    }

    public BasePlaceView(Context context) {
        super(context);
        inflateBase();
    }

    public BasePlaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflateBase();
    }

    public BasePlaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        inflateBase();
    }

    @TargetApi(21)
    public BasePlaceView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        inflateBase();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        int i;
        this.onScaleGestureDetector.onTouchEvent(motionEvent);
        Log.d("BasePlaceView", "event.getPointerCount():" + motionEvent.getPointerCount());
        float f2 = 0.0f;
        switch (motionEvent.getAction()) {
            case 0:
                this.mx = motionEvent.getX();
                this.my = motionEvent.getY();
                f = 0.0f;
                break;
            case 1:
                f2 = motionEvent.getX();
                f = motionEvent.getY();
                Button collisionButton = getCollisionButton(this.mx, this.my);
                if (!this.mIsCanMove && collisionButton != null) {
                    collisionButton.performClick();
                    collisionButton.setPressed(false);
                    this.mCollision = true;
                }
                this.mScrollView.scrollBy((int) (this.mx - f2), (int) (this.my - f));
                this.mHorizontalScrollView.scrollBy((int) (this.mx - f2), (int) (this.my - f));
                this.clickCount = 0;
                this.mIsCanMove = false;
                this.mCollision = false;
                break;
            case 2:
                if (motionEvent.getPointerCount() < 2) {
                    f2 = motionEvent.getX();
                    f = motionEvent.getY();
                    Button collisionButton2 = getCollisionButton(this.mx, this.my);
                    if (this.clickCount >= 5) {
                        i = 5;
                    } else {
                        i = this.clickCount + 1;
                        this.clickCount = i;
                    }
                    this.clickCount = i;
                    Log.d("BasePlaceView", "clickCount:" + this.clickCount);
                    int abs = (int) Math.abs(this.mx - f2);
                    int abs2 = (int) Math.abs(this.my - f);
                    if (abs >= 20 || abs2 >= 20) {
                        this.mIsCanMove = true;
                    }
                    if (this.clickCount < 5 || this.mIsCanMove) {
                        if (collisionButton2 != null) {
                            collisionButton2.setPressed(false);
                        }
                    } else if (collisionButton2 != null) {
                        collisionButton2.setPressed(true);
                    }
                    this.mScrollView.scrollBy((int) (this.mx - f2), (int) (this.my - f));
                    this.mHorizontalScrollView.scrollBy((int) (this.mx - f2), (int) (this.my - f));
                    this.mx = f2;
                    this.my = f;
                    break;
                } else {
                    if (motionEvent.getPointerCount() >= 2) {
                        this.mDragNewDist = getSpacing(motionEvent);
                        for (int i2 = 0; i2 < this.mTableLayout.getChildCount(); i2++) {
                            TableRow tableRow = (TableRow) this.mTableLayout.getChildAt(i2);
                            for (int i3 = 0; i3 < tableRow.getChildCount(); i3++) {
                                View childAt = tableRow.getChildAt(i3);
                                childAt.measure(0, 0);
                                TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) childAt.getLayoutParams();
                                this.mCellWidth = childAt.getWidth();
                                this.mCellHeight = childAt.getHeight();
                                int i4 = (int) (this.mCellWidth * this.mScaleFactor);
                                int i5 = (int) (this.mCellHeight * this.mScaleFactor);
                                if (this.mCellMinHeight <= i5 && i5 <= this.mCellMaxHeight) {
                                    layoutParams.height = i5;
                                }
                                if (this.mCellMinWidth <= i4 && i4 <= this.mCellMaxWidth) {
                                    layoutParams.width = i4;
                                }
                                childAt.setLayoutParams(layoutParams);
                            }
                        }
                    }
                    f = 0.0f;
                    break;
                }
                break;
            default:
                f = 0.0f;
                break;
        }
        Log.d("BasePlaceView", "============================");
        Log.d("BasePlaceView", "event.getAction():" + motionEvent.getAction());
        Log.d("BasePlaceView", "mx:" + this.mx);
        Log.d("BasePlaceView", "my:" + this.my);
        Log.d("BasePlaceView", "curX:" + f2);
        Log.d("BasePlaceView", "curY:" + f);
        Log.d("BasePlaceView", "event.getRawX():" + motionEvent.getRawX());
        Log.d("BasePlaceView", "event.getRawY():" + motionEvent.getRawY());
        Log.d("BasePlaceView", "mScrollView.getScrollX():" + this.mScrollView.getScrollX());
        Log.d("BasePlaceView", "mScrollView.getScrollY():" + this.mScrollView.getScrollY());
        return true;
    }

    private Button getCollisionButton(float f, float f2) {
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        Iterator<Button> it = this.mButtons.iterator();
        while (it.hasNext()) {
            Button next = it.next();
            int[] iArr2 = new int[2];
            next.getLocationInWindow(iArr2);
            int i = (int) f;
            int i2 = ((int) f2) + 5;
            if (collisionCheck(i - 5, i2, i + 5, i2, iArr2[0] - iArr[0], iArr2[1] - iArr[1], (iArr2[0] + getCellWidth()) - iArr[0], (iArr2[1] + getCellHeight()) - iArr[1]) && !this.mCollision) {
                return next;
            }
        }
        return null;
    }

    private void inflateBase() {
        this.onScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        if (getChildCount() <= 0) {
            this.mMaxMove = (int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics());
            this.mTableLayout = new TableLayout(getContext());
            this.mScrollView = new BaseNoneScrollView(getContext());
            this.mHorizontalScrollView = new BaseNoneHorizontalScrollView(getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            this.mTableLayout.setLayoutParams(layoutParams);
            this.mScrollView.setLayoutParams(layoutParams);
            this.mHorizontalScrollView.setLayoutParams(layoutParams);
            this.mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.naver.temy123.baseproject.base.Widgets.BasePlaceView.1
                @Override // android.view.ViewTreeObserver.OnScrollChangedListener
                public void onScrollChanged() {
                    int scrollY = BasePlaceView.this.mScrollView.getScrollY();
                    Log.d("BasePlaceView", "scrollY:" + scrollY);
                    Log.d("BasePlaceView", "ScrollChanged");
                }
            });
            this.mHorizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.naver.temy123.baseproject.base.Widgets.BasePlaceView.2
                @Override // android.view.ViewTreeObserver.OnScrollChangedListener
                public void onScrollChanged() {
                    int scrollX = BasePlaceView.this.mHorizontalScrollView.getScrollX();
                    Log.d("BasePlaceView", "scrollX:" + scrollX);
                    Log.d("BasePlaceView", "ScrollChanged");
                }
            });
            this.mHorizontalScrollView.addView(this.mTableLayout);
            this.mScrollView.addView(this.mHorizontalScrollView);
            addView(this.mScrollView);
        }
    }

    private int getLineMax(int[][] iArr) {
        int i = 0;
        for (int[] iArr2 : iArr) {
            int i2 = 0;
            for (int i3 : iArr2) {
                if (i3 >= 0) {
                    i2 += i3;
                }
            }
            if (i <= i2) {
                i = i2;
            }
        }
        return i;
    }

    public void setTable(int[][] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(-2, -2));
            for (int i2 = 0; i2 < iArr[i].length; i2++) {
                addTableView(tableRow, iArr[i][i2], this.onPlaceClickListener);
            }
            this.mTableLayout.addView(tableRow);
        }
        Iterator<Button> it = this.mButtons.iterator();
        while (it.hasNext()) {
            it.next().setClickable(false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setTable(int[][] r10, int r11) {
        /*
            r9 = this;
            r9.mTableData = r10
            r9.mDirection = r11
            r11 = 0
            r0 = 1
            r1 = 0
            r2 = 1
        L_0x0008:
            int r3 = r10.length
            if (r1 >= r3) goto L_0x0076
            android.widget.TableRow r3 = new android.widget.TableRow
            android.content.Context r4 = r9.getContext()
            r3.<init>(r4)
            r4 = 0
        L_0x0015:
            r5 = r10[r1]
            int r5 = r5.length
            if (r4 >= r5) goto L_0x0065
            android.widget.TableRow$LayoutParams r5 = new android.widget.TableRow$LayoutParams
            r6 = -2
            r5.<init>(r6, r6)
            r3.setLayoutParams(r5)
            r5 = r10[r1]
            r5 = r5[r4]
            if (r5 >= 0) goto L_0x003d
            int r5 = java.lang.Math.abs(r5)
            r6 = 0
        L_0x002e:
            if (r6 >= r5) goto L_0x0062
            int r7 = r9.mBlankTotal
            int r7 = r7 + r0
            r9.mBlankTotal = r7
            r7 = -1
            r8 = 0
            r9.addTableView(r3, r7, r8)
            int r6 = r6 + 1
            goto L_0x002e
        L_0x003d:
            r6 = 0
        L_0x003e:
            if (r6 >= r5) goto L_0x0062
            int r7 = r9.mDirection
            switch(r7) {
                case 0: goto L_0x0051;
                case 1: goto L_0x004c;
                case 2: goto L_0x0047;
                default: goto L_0x0045;
            }
        L_0x0045:
            r7 = 0
            goto L_0x0055
        L_0x0047:
            int r7 = r9.getDirectionLine3(r6)
            goto L_0x0055
        L_0x004c:
            int r7 = r9.getDirectionLine2(r1, r4, r6, r2)
            goto L_0x0055
        L_0x0051:
            int r7 = r9.getDirectionLine1(r1, r4, r6)
        L_0x0055:
            int r8 = r9.mStartPosition
            switch(r8) {
                case 0: goto L_0x005a;
                case 1: goto L_0x005a;
                case 2: goto L_0x005a;
                default: goto L_0x005a;
            }
        L_0x005a:
            com.naver.temy123.baseproject.base.Widgets.BasePlaceView$OnPlaceClickListener r8 = r9.onPlaceClickListener
            r9.addTableView(r3, r7, r8)
            int r6 = r6 + 1
            goto L_0x003e
        L_0x0062:
            int r4 = r4 + 1
            goto L_0x0015
        L_0x0065:
            if (r2 != r0) goto L_0x0069
            r2 = 2
            goto L_0x006a
        L_0x0069:
            r2 = 1
        L_0x006a:
            android.widget.TableLayout r4 = r9.mTableLayout
            if (r4 == 0) goto L_0x0073
            android.widget.TableLayout r4 = r9.mTableLayout
            r4.addView(r3)
        L_0x0073:
            int r1 = r1 + 1
            goto L_0x0008
        L_0x0076:
            java.util.ArrayList<android.widget.Button> r10 = r9.mButtons
            java.util.Iterator r10 = r10.iterator()
        L_0x007c:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto L_0x008c
            java.lang.Object r0 = r10.next()
            android.widget.Button r0 = (android.widget.Button) r0
            r0.setClickable(r11)
            goto L_0x007c
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naver.temy123.baseproject.base.Widgets.BasePlaceView.setTable(int[][], int):void");
    }

    private int getTotal() {
        int[] iArr;
        int[][] iArr2 = this.mTableData;
        int length = iArr2.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2;
            for (int i4 : iArr2[i]) {
                if (i4 > 0) {
                    i3 += i4;
                }
            }
            i++;
            i2 = i3;
        }
        return i2;
    }

    private int getDirectionLine1(int i, int i2, int i3) {
        return getPositionCount(i, i2) + i3;
    }

    private int getDirectionLine2(int i, int i2, int i3, int i4) {
        if (i4 == 2) {
            return ((((getDirectionLine1(i, i2, 0) - getTotalInLine(i, i2)) + getTotalInLine(i)) - (i3 + getTotalInLine(i, i2))) - 1) - this.mBlankTotal;
        }
        if (i4 == 1) {
            return (getDirectionLine1(i, i2, 0) + i3) - this.mBlankTotal;
        }
        return 0;
    }

    private int getLineCount(int i) {
        int i2 = 0;
        for (int i3 : this.mTableData[i]) {
            i2 += i3;
        }
        return i2;
    }

    private int getPositionCount(int i, int i2) {
        if (this.mTableData == null) {
            return -1;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 <= i) {
            int[] iArr = this.mTableData[i3];
            int i5 = i4;
            for (int i6 = 0; i6 < iArr.length && (i3 != i || i6 < i2); i6++) {
                if (iArr[i6] >= 0) {
                    i5 += iArr[i6];
                }
            }
            i3++;
            i4 = i5;
        }
        return i4;
    }

    private int getTotalInLine(int i) {
        int i2 = 0;
        for (int i3 : this.mTableData[i]) {
            i2 += i3;
        }
        return i2;
    }

    private float getSpacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private int getTotalInLine(int i, int i2) {
        int[] iArr = this.mTableData[i];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += iArr[i4];
        }
        return i3;
    }

    private int getTotalPrivous(int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i3;
            for (int i5 : this.mTableData[i2]) {
                i4 += i5;
            }
            i2++;
            i3 = i4;
        }
        return i3;
    }

    private int getPosition(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int i5 = i4;
            for (int i6 = 0; i6 < this.mTableData[i3].length; i6++) {
                int i7 = this.mTableData[i3][i6];
                if (i3 != i || i6 < i2) {
                    if (i7 >= 0) {
                        i5 += i7;
                    }
                }
            }
            i3++;
            i4 = i5;
        }
        return i4;
    }

    private int getMaxValueInLine(int i) {
        int[] iArr;
        for (int i2 : this.mTableData[i]) {
        }
        return 0;
    }

    private void addTableView(TableRow tableRow, final int i, final OnPlaceClickListener onPlaceClickListener) {
        String valueOf = String.valueOf(i);
        Button button = new Button(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(getCellWidth(), getCellHeight());
        layoutParams.setMargins(this.mCellMargin, this.mCellMargin, this.mCellMargin, this.mCellMargin);
        setTableBackground(button);
        button.setText(valueOf);
        button.setTextSize(this.mTextSize);
        button.setTextColor(this.mTextColor);
        button.setLayoutParams(layoutParams);
        if (i == Integer.MIN_VALUE) {
            button.setVisibility(4);
        }
        if (getOnPlaceTextListener() != null) {
            button.setText(getOnPlaceTextListener().onPlaceTextListener(button, i));
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.naver.temy123.baseproject.base.Widgets.BasePlaceView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (i != Integer.MIN_VALUE && onPlaceClickListener != null) {
                    onPlaceClickListener.onPlaceClicked(view, i);
                }
            }
        });
        if (getOnPlaceViewCreatedListener() != null) {
            getOnPlaceViewCreatedListener().onPlaceViewCreate(button, i);
        }
        this.mButtons.add(button);
        tableRow.addView(button);
    }

    private void setTableBackground(View view) {
        if (this.mBackground instanceof Drawable) {
            view.setBackground((Drawable) this.mBackground);
        } else if (this.mBackground instanceof Integer) {
            view.setBackgroundResource(((Integer) this.mBackground).intValue());
        }
    }

    /* loaded from: classes.dex */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
            BasePlaceView.this = r1;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            BasePlaceView.this.mScaleFactor = scaleGestureDetector.getScaleFactor();
            Log.d("ScaleListener", "detector.getScaleFactor():" + scaleGestureDetector.getScaleFactor());
            BasePlaceView.this.invalidate();
            return true;
        }
    }

    public void setBackground(Object obj) {
        this.mBackground = obj;
    }

    public TableLayout getTableLayout() {
        return this.mTableLayout;
    }

    public void setTableLayout(TableLayout tableLayout) {
        this.mTableLayout = tableLayout;
    }

    public int getCellHeight() {
        return this.mCellHeight;
    }

    public void setCellHeight(int i) {
        this.mCellHeight = i;
        this.mCellMinHeight = i;
        this.mCellMaxHeight = i * 5;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setCellWidth(int i) {
        this.mCellWidth = i;
        this.mCellMinWidth = i;
        this.mCellMaxWidth = i * 5;
    }

    public int getHeightBlankMargin() {
        return this.mHeightBlankMargin;
    }

    public void setHeightBlankMargin(int i) {
        this.mHeightBlankMargin = i;
    }

    public int getWidthBlankMargin() {
        return this.mWidthBlankMargin;
    }

    public void setWidthBlankMargin(int i) {
        this.mWidthBlankMargin = i;
    }

    public int getHeightMargin() {
        return this.mHeightMargin;
    }

    public void setHeightMargin(int i) {
        this.mHeightMargin = i;
    }

    public int getWidthMargin() {
        return this.mWidthMargin;
    }

    public void setWidthMargin(int i) {
        this.mWidthMargin = i;
    }

    public OnPlaceClickListener getOnPlaceClickListener() {
        return this.onPlaceClickListener;
    }

    public void setOnPlaceClickListener(OnPlaceClickListener onPlaceClickListener) {
        this.onPlaceClickListener = onPlaceClickListener;
    }

    public int getStartPosition() {
        return this.mStartPosition;
    }

    public void setStartPosition(int i) {
        this.mStartPosition = i;
    }

    public OnPlaceTextListener getOnPlaceTextListener() {
        return this.onPlaceTextListener;
    }

    public void setOnPlaceTextListener(OnPlaceTextListener onPlaceTextListener) {
        this.onPlaceTextListener = onPlaceTextListener;
    }

    public OnPlaceViewCreatedListener getOnPlaceViewCreatedListener() {
        return this.onPlaceViewCreatedListener;
    }

    public void setOnPlaceViewCreatedListener(OnPlaceViewCreatedListener onPlaceViewCreatedListener) {
        this.onPlaceViewCreatedListener = onPlaceViewCreatedListener;
    }
}
