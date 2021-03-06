package com.nguyenhoanglam.imagepicker.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.nguyenhoanglam.imagepicker.R;

/* loaded from: classes.dex */
public class ProgressWheel extends View {
    private static final String TAG = "ProgressWheel";
    private ProgressCallback callback;
    private boolean linearProgress;
    private boolean shouldAnimate;
    private final int barLength = 16;
    private final int barMaxLength = 270;
    private final long pauseGrowingTime = 200;
    private int circleRadius = 28;
    private int barWidth = 4;
    private int rimWidth = 4;
    private boolean fillRadius = false;
    private double timeStartGrowing = 0.0d;
    private double barSpinCycleTime = 460.0d;
    private float barExtraLength = 0.0f;
    private boolean barGrowingFromFront = true;
    private long pausedTimeWithoutGrowing = 0;
    private int barColor = -1442840576;
    private int rimColor = ViewCompat.MEASURED_SIZE_MASK;
    private Paint barPaint = new Paint();
    private Paint rimPaint = new Paint();
    private RectF circleBounds = new RectF();
    private float spinSpeed = 230.0f;
    private long lastTimeAnimated = 0;
    private float mProgress = 0.0f;
    private float mTargetProgress = 0.0f;
    private boolean isSpinning = false;

    /* loaded from: classes.dex */
    public interface ProgressCallback {
        void onProgressUpdate(float f);
    }

    public ProgressWheel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseAttributes(context.obtainStyledAttributes(attributeSet, R.styleable.imagepicker_ProgressWheel));
        setAnimationEnabled();
    }

    public ProgressWheel(Context context) {
        super(context);
        setAnimationEnabled();
    }

    @TargetApi(17)
    private void setAnimationEnabled() {
        float f;
        if (Build.VERSION.SDK_INT >= 17) {
            f = Settings.Global.getFloat(getContext().getContentResolver(), "animator_duration_scale", 1.0f);
        } else {
            f = Settings.System.getFloat(getContext().getContentResolver(), "animator_duration_scale", 1.0f);
        }
        this.shouldAnimate = f != 0.0f;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int paddingLeft = this.circleRadius + getPaddingLeft() + getPaddingRight();
        int paddingTop = this.circleRadius + getPaddingTop() + getPaddingBottom();
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            paddingLeft = size;
        } else if (mode == Integer.MIN_VALUE) {
            paddingLeft = Math.min(paddingLeft, size);
        }
        if (mode2 == 1073741824 || mode == 1073741824) {
            paddingTop = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            paddingTop = Math.min(paddingTop, size2);
        }
        setMeasuredDimension(paddingLeft, paddingTop);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        setupBounds(i, i2);
        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        this.barPaint.setColor(this.barColor);
        this.barPaint.setAntiAlias(true);
        this.barPaint.setStyle(Paint.Style.STROKE);
        this.barPaint.setStrokeWidth(this.barWidth);
        this.rimPaint.setColor(this.rimColor);
        this.rimPaint.setAntiAlias(true);
        this.rimPaint.setStyle(Paint.Style.STROKE);
        this.rimPaint.setStrokeWidth(this.rimWidth);
    }

    private void setupBounds(int i, int i2) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (!this.fillRadius) {
            int i3 = (i - paddingLeft) - paddingRight;
            int min = Math.min(Math.min(i3, (i2 - paddingBottom) - paddingTop), (this.circleRadius * 2) - (this.barWidth * 2));
            int i4 = ((i3 - min) / 2) + paddingLeft;
            int i5 = ((((i2 - paddingTop) - paddingBottom) - min) / 2) + paddingTop;
            this.circleBounds = new RectF(this.barWidth + i4, this.barWidth + i5, (i4 + min) - this.barWidth, (i5 + min) - this.barWidth);
            return;
        }
        this.circleBounds = new RectF(paddingLeft + this.barWidth, paddingTop + this.barWidth, (i - paddingRight) - this.barWidth, (i2 - paddingBottom) - this.barWidth);
    }

    private void parseAttributes(TypedArray typedArray) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.barWidth = (int) TypedValue.applyDimension(1, this.barWidth, displayMetrics);
        this.rimWidth = (int) TypedValue.applyDimension(1, this.rimWidth, displayMetrics);
        this.circleRadius = (int) TypedValue.applyDimension(1, this.circleRadius, displayMetrics);
        this.circleRadius = (int) typedArray.getDimension(R.styleable.imagepicker_ProgressWheel_matProg_circleRadius, this.circleRadius);
        this.fillRadius = typedArray.getBoolean(R.styleable.imagepicker_ProgressWheel_matProg_fillRadius, false);
        this.barWidth = (int) typedArray.getDimension(R.styleable.imagepicker_ProgressWheel_matProg_barWidth, this.barWidth);
        this.rimWidth = (int) typedArray.getDimension(R.styleable.imagepicker_ProgressWheel_matProg_rimWidth, this.rimWidth);
        this.spinSpeed = typedArray.getFloat(R.styleable.imagepicker_ProgressWheel_matProg_spinSpeed, this.spinSpeed / 360.0f) * 360.0f;
        this.barSpinCycleTime = typedArray.getInt(R.styleable.imagepicker_ProgressWheel_matProg_barSpinCycleTime, (int) this.barSpinCycleTime);
        this.barColor = typedArray.getColor(R.styleable.imagepicker_ProgressWheel_matProg_barColor, this.barColor);
        this.rimColor = typedArray.getColor(R.styleable.imagepicker_ProgressWheel_matProg_rimColor, this.rimColor);
        this.linearProgress = typedArray.getBoolean(R.styleable.imagepicker_ProgressWheel_matProg_linearProgress, false);
        if (typedArray.getBoolean(R.styleable.imagepicker_ProgressWheel_matProg_progressIndeterminate, false)) {
            spin();
        }
        typedArray.recycle();
    }

    public void setCallback(ProgressCallback progressCallback) {
        this.callback = progressCallback;
        if (!this.isSpinning) {
            runCallback();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.circleBounds, 360.0f, 360.0f, false, this.rimPaint);
        if (this.shouldAnimate) {
            float f = 0.0f;
            boolean z = true;
            if (this.isSpinning) {
                long uptimeMillis = SystemClock.uptimeMillis() - this.lastTimeAnimated;
                updateBarLength(uptimeMillis);
                this.mProgress += (((float) uptimeMillis) * this.spinSpeed) / 1000.0f;
                if (this.mProgress > 360.0f) {
                    this.mProgress -= 360.0f;
                    runCallback(-1.0f);
                }
                this.lastTimeAnimated = SystemClock.uptimeMillis();
                float f2 = this.mProgress - 90.0f;
                float f3 = this.barExtraLength + 16.0f;
                if (isInEditMode()) {
                    f2 = 0.0f;
                    f3 = 135.0f;
                }
                canvas.drawArc(this.circleBounds, f2, f3, false, this.barPaint);
            } else {
                float f4 = this.mProgress;
                if (this.mProgress != this.mTargetProgress) {
                    this.mProgress = Math.min(this.mProgress + ((((float) (SystemClock.uptimeMillis() - this.lastTimeAnimated)) / 1000.0f) * this.spinSpeed), this.mTargetProgress);
                    this.lastTimeAnimated = SystemClock.uptimeMillis();
                } else {
                    z = false;
                }
                if (f4 != this.mProgress) {
                    runCallback();
                }
                float f5 = this.mProgress;
                if (!this.linearProgress) {
                    f = ((float) (1.0d - Math.pow(1.0f - (this.mProgress / 360.0f), 4.0f))) * 360.0f;
                    f5 = ((float) (1.0d - Math.pow(1.0f - (this.mProgress / 360.0f), 2.0f))) * 360.0f;
                }
                if (isInEditMode()) {
                    f5 = 360.0f;
                }
                canvas.drawArc(this.circleBounds, f - 90.0f, f5, false, this.barPaint);
            }
            if (z) {
                invalidate();
            }
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.lastTimeAnimated = SystemClock.uptimeMillis();
        }
    }

    private void updateBarLength(long j) {
        if (this.pausedTimeWithoutGrowing >= 200) {
            this.timeStartGrowing += j;
            if (this.timeStartGrowing > this.barSpinCycleTime) {
                this.timeStartGrowing -= this.barSpinCycleTime;
                this.pausedTimeWithoutGrowing = 0L;
                this.barGrowingFromFront = !this.barGrowingFromFront;
            }
            float cos = (((float) Math.cos(((this.timeStartGrowing / this.barSpinCycleTime) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
            if (this.barGrowingFromFront) {
                this.barExtraLength = cos * 254.0f;
                return;
            }
            float f = (1.0f - cos) * 254.0f;
            this.mProgress += this.barExtraLength - f;
            this.barExtraLength = f;
            return;
        }
        this.pausedTimeWithoutGrowing += j;
    }

    public boolean isSpinning() {
        return this.isSpinning;
    }

    public void resetCount() {
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        invalidate();
    }

    public void stopSpinning() {
        this.isSpinning = false;
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        invalidate();
    }

    public void spin() {
        this.lastTimeAnimated = SystemClock.uptimeMillis();
        this.isSpinning = true;
        invalidate();
    }

    private void runCallback(float f) {
        if (this.callback != null) {
            this.callback.onProgressUpdate(f);
        }
    }

    private void runCallback() {
        if (this.callback != null) {
            this.callback.onProgressUpdate(Math.round((this.mProgress * 100.0f) / 360.0f) / 100.0f);
        }
    }

    public void setInstantProgress(float f) {
        if (this.isSpinning) {
            this.mProgress = 0.0f;
            this.isSpinning = false;
        }
        if (f > 1.0f) {
            f -= 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        if (f != this.mTargetProgress) {
            this.mTargetProgress = Math.min(f * 360.0f, 360.0f);
            this.mProgress = this.mTargetProgress;
            this.lastTimeAnimated = SystemClock.uptimeMillis();
            invalidate();
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        WheelSavedState wheelSavedState = new WheelSavedState(super.onSaveInstanceState());
        wheelSavedState.mProgress = this.mProgress;
        wheelSavedState.mTargetProgress = this.mTargetProgress;
        wheelSavedState.isSpinning = this.isSpinning;
        wheelSavedState.spinSpeed = this.spinSpeed;
        wheelSavedState.barWidth = this.barWidth;
        wheelSavedState.barColor = this.barColor;
        wheelSavedState.rimWidth = this.rimWidth;
        wheelSavedState.rimColor = this.rimColor;
        wheelSavedState.circleRadius = this.circleRadius;
        wheelSavedState.linearProgress = this.linearProgress;
        wheelSavedState.fillRadius = this.fillRadius;
        return wheelSavedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof WheelSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        WheelSavedState wheelSavedState = (WheelSavedState) parcelable;
        super.onRestoreInstanceState(wheelSavedState.getSuperState());
        this.mProgress = wheelSavedState.mProgress;
        this.mTargetProgress = wheelSavedState.mTargetProgress;
        this.isSpinning = wheelSavedState.isSpinning;
        this.spinSpeed = wheelSavedState.spinSpeed;
        this.barWidth = wheelSavedState.barWidth;
        this.barColor = wheelSavedState.barColor;
        this.rimWidth = wheelSavedState.rimWidth;
        this.rimColor = wheelSavedState.rimColor;
        this.circleRadius = wheelSavedState.circleRadius;
        this.linearProgress = wheelSavedState.linearProgress;
        this.fillRadius = wheelSavedState.fillRadius;
        this.lastTimeAnimated = SystemClock.uptimeMillis();
    }

    public float getProgress() {
        if (this.isSpinning) {
            return -1.0f;
        }
        return this.mProgress / 360.0f;
    }

    public void setProgress(float f) {
        if (this.isSpinning) {
            this.mProgress = 0.0f;
            this.isSpinning = false;
            runCallback();
        }
        if (f > 1.0f) {
            f -= 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        if (f != this.mTargetProgress) {
            if (this.mProgress == this.mTargetProgress) {
                this.lastTimeAnimated = SystemClock.uptimeMillis();
            }
            this.mTargetProgress = Math.min(f * 360.0f, 360.0f);
            invalidate();
        }
    }

    public void setLinearProgress(boolean z) {
        this.linearProgress = z;
        if (!this.isSpinning) {
            invalidate();
        }
    }

    public int getCircleRadius() {
        return this.circleRadius;
    }

    public void setCircleRadius(int i) {
        this.circleRadius = i;
        if (!this.isSpinning) {
            invalidate();
        }
    }

    public int getBarWidth() {
        return this.barWidth;
    }

    public void setBarWidth(int i) {
        this.barWidth = i;
        if (!this.isSpinning) {
            invalidate();
        }
    }

    public int getBarColor() {
        return this.barColor;
    }

    public void setBarColor(int i) {
        this.barColor = i;
        setupPaints();
        if (!this.isSpinning) {
            invalidate();
        }
    }

    public int getRimColor() {
        return this.rimColor;
    }

    public void setRimColor(int i) {
        this.rimColor = i;
        setupPaints();
        if (!this.isSpinning) {
            invalidate();
        }
    }

    public float getSpinSpeed() {
        return this.spinSpeed / 360.0f;
    }

    public void setSpinSpeed(float f) {
        this.spinSpeed = f * 360.0f;
    }

    public int getRimWidth() {
        return this.rimWidth;
    }

    public void setRimWidth(int i) {
        this.rimWidth = i;
        if (!this.isSpinning) {
            invalidate();
        }
    }

    /* loaded from: classes.dex */
    public static class WheelSavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<WheelSavedState> CREATOR = new Parcelable.Creator<WheelSavedState>() { // from class: com.nguyenhoanglam.imagepicker.widget.ProgressWheel.WheelSavedState.1
            @Override // android.os.Parcelable.Creator
            public WheelSavedState createFromParcel(Parcel parcel) {
                return new WheelSavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public WheelSavedState[] newArray(int i) {
                return new WheelSavedState[i];
            }
        };
        int barColor;
        int barWidth;
        int circleRadius;
        boolean fillRadius;
        boolean isSpinning;
        boolean linearProgress;
        float mProgress;
        float mTargetProgress;
        int rimColor;
        int rimWidth;
        float spinSpeed;

        WheelSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private WheelSavedState(Parcel parcel) {
            super(parcel);
            this.mProgress = parcel.readFloat();
            this.mTargetProgress = parcel.readFloat();
            boolean z = false;
            this.isSpinning = parcel.readByte() != 0;
            this.spinSpeed = parcel.readFloat();
            this.barWidth = parcel.readInt();
            this.barColor = parcel.readInt();
            this.rimWidth = parcel.readInt();
            this.rimColor = parcel.readInt();
            this.circleRadius = parcel.readInt();
            this.linearProgress = parcel.readByte() != 0;
            this.fillRadius = parcel.readByte() != 0 ? true : z;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.mProgress);
            parcel.writeFloat(this.mTargetProgress);
            parcel.writeByte(this.isSpinning ? (byte) 1 : (byte) 0);
            parcel.writeFloat(this.spinSpeed);
            parcel.writeInt(this.barWidth);
            parcel.writeInt(this.barColor);
            parcel.writeInt(this.rimWidth);
            parcel.writeInt(this.rimColor);
            parcel.writeInt(this.circleRadius);
            parcel.writeByte(this.linearProgress ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.fillRadius ? (byte) 1 : (byte) 0);
        }
    }
}
