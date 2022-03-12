package com.github.chrisbanes.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.OverScroller;

/* loaded from: classes.dex */
public class PhotoViewAttacher implements View.OnTouchListener, OnGestureListener, View.OnLayoutChangeListener {
    private static float DEFAULT_MAX_SCALE = 3.0f;
    private static float DEFAULT_MID_SCALE = 1.75f;
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private static int DEFAULT_ZOOM_DURATION = 200;
    private static final int EDGE_BOTH = 2;
    private static final int EDGE_LEFT = 0;
    private static final int EDGE_NONE = -1;
    private static final int EDGE_RIGHT = 1;
    private static int SINGLE_TOUCH = 1;
    private float mBaseRotation;
    private FlingRunnable mCurrentFlingRunnable;
    private GestureDetector mGestureDetector;
    private ImageView mImageView;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private View.OnClickListener mOnClickListener;
    private OnOutsidePhotoTapListener mOutsidePhotoTapListener;
    private OnPhotoTapListener mPhotoTapListener;
    private OnScaleChangedListener mScaleChangeListener;
    private CustomGestureDetector mScaleDragDetector;
    private OnSingleFlingListener mSingleFlingListener;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private int mZoomDuration = DEFAULT_ZOOM_DURATION;
    private float mMinScale = DEFAULT_MIN_SCALE;
    private float mMidScale = DEFAULT_MID_SCALE;
    private float mMaxScale = DEFAULT_MAX_SCALE;
    private boolean mAllowParentInterceptOnEdge = true;
    private boolean mBlockParentIntercept = false;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private int mScrollEdge = 2;
    private boolean mZoomEnabled = true;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    public PhotoViewAttacher(ImageView imageView) {
        this.mImageView = imageView;
        imageView.setOnTouchListener(this);
        imageView.addOnLayoutChangeListener(this);
        if (!imageView.isInEditMode()) {
            this.mBaseRotation = 0.0f;
            this.mScaleDragDetector = new CustomGestureDetector(imageView.getContext(), this);
            this.mGestureDetector = new GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.chrisbanes.photoview.PhotoViewAttacher.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent motionEvent) {
                    if (PhotoViewAttacher.this.mLongClickListener != null) {
                        PhotoViewAttacher.this.mLongClickListener.onLongClick(PhotoViewAttacher.this.mImageView);
                    }
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (PhotoViewAttacher.this.mSingleFlingListener == null || PhotoViewAttacher.this.getScale() > PhotoViewAttacher.DEFAULT_MIN_SCALE || MotionEventCompat.getPointerCount(motionEvent) > PhotoViewAttacher.SINGLE_TOUCH || MotionEventCompat.getPointerCount(motionEvent2) > PhotoViewAttacher.SINGLE_TOUCH) {
                        return false;
                    }
                    return PhotoViewAttacher.this.mSingleFlingListener.onFling(motionEvent, motionEvent2, f, f2);
                }
            });
            this.mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.github.chrisbanes.photoview.PhotoViewAttacher.2
                @Override // android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    return false;
                }

                @Override // android.view.GestureDetector.OnDoubleTapListener
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                    if (PhotoViewAttacher.this.mOnClickListener != null) {
                        PhotoViewAttacher.this.mOnClickListener.onClick(PhotoViewAttacher.this.mImageView);
                    }
                    RectF displayRect = PhotoViewAttacher.this.getDisplayRect();
                    if (displayRect == null) {
                        return false;
                    }
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (displayRect.contains(x, y)) {
                        float width = (x - displayRect.left) / displayRect.width();
                        float height = (y - displayRect.top) / displayRect.height();
                        if (PhotoViewAttacher.this.mPhotoTapListener == null) {
                            return true;
                        }
                        PhotoViewAttacher.this.mPhotoTapListener.onPhotoTap(PhotoViewAttacher.this.mImageView, width, height);
                        return true;
                    } else if (PhotoViewAttacher.this.mOutsidePhotoTapListener == null) {
                        return false;
                    } else {
                        PhotoViewAttacher.this.mOutsidePhotoTapListener.onOutsidePhotoTap(PhotoViewAttacher.this.mImageView);
                        return false;
                    }
                }

                @Override // android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    try {
                        float scale = PhotoViewAttacher.this.getScale();
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (scale < PhotoViewAttacher.this.getMediumScale()) {
                            PhotoViewAttacher.this.setScale(PhotoViewAttacher.this.getMediumScale(), x, y, true);
                        } else if (scale < PhotoViewAttacher.this.getMediumScale() || scale >= PhotoViewAttacher.this.getMaximumScale()) {
                            PhotoViewAttacher.this.setScale(PhotoViewAttacher.this.getMinimumScale(), x, y, true);
                        } else {
                            PhotoViewAttacher.this.setScale(PhotoViewAttacher.this.getMaximumScale(), x, y, true);
                        }
                    } catch (ArrayIndexOutOfBoundsException unused) {
                    }
                    return true;
                }
            });
        }
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mGestureDetector.setOnDoubleTapListener(onDoubleTapListener);
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        this.mScaleChangeListener = onScaleChangedListener;
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        this.mSingleFlingListener = onSingleFlingListener;
    }

    public boolean isZoomEnabled() {
        return this.mZoomEnabled;
    }

    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    public boolean setDisplayMatrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        } else if (this.mImageView.getDrawable() == null) {
            return false;
        } else {
            this.mSuppMatrix.set(matrix);
            setImageViewMatrix(getDrawMatrix());
            checkMatrixBounds();
            return true;
        }
    }

    public void setBaseRotation(float f) {
        this.mBaseRotation = f % 360.0f;
        update();
        setRotationBy(this.mBaseRotation);
        checkAndDisplayMatrix();
    }

    public void setRotationTo(float f) {
        this.mSuppMatrix.setRotate(f % 360.0f);
        checkAndDisplayMatrix();
    }

    public void setRotationBy(float f) {
        this.mSuppMatrix.postRotate(f % 360.0f);
        checkAndDisplayMatrix();
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public float getScale() {
        return (float) Math.sqrt(((float) Math.pow(getValue(this.mSuppMatrix, 0), 2.0d)) + ((float) Math.pow(getValue(this.mSuppMatrix, 3), 2.0d)));
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override // com.github.chrisbanes.photoview.OnGestureListener
    public void onDrag(float f, float f2) {
        if (!this.mScaleDragDetector.isScaling()) {
            this.mSuppMatrix.postTranslate(f, f2);
            checkAndDisplayMatrix();
            ViewParent parent = this.mImageView.getParent();
            if (!this.mAllowParentInterceptOnEdge || this.mScaleDragDetector.isScaling() || this.mBlockParentIntercept) {
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            } else if ((this.mScrollEdge == 2 || ((this.mScrollEdge == 0 && f >= 1.0f) || (this.mScrollEdge == 1 && f <= -1.0f))) && parent != null) {
                parent.requestDisallowInterceptTouchEvent(false);
            }
        }
    }

    @Override // com.github.chrisbanes.photoview.OnGestureListener
    public void onFling(float f, float f2, float f3, float f4) {
        this.mCurrentFlingRunnable = new FlingRunnable(this.mImageView.getContext());
        this.mCurrentFlingRunnable.fling(getImageViewWidth(this.mImageView), getImageViewHeight(this.mImageView), (int) f3, (int) f4);
        this.mImageView.post(this.mCurrentFlingRunnable);
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateBaseMatrix(this.mImageView.getDrawable());
    }

    @Override // com.github.chrisbanes.photoview.OnGestureListener
    public void onScale(float f, float f2, float f3) {
        if (getScale() >= this.mMaxScale && f >= 1.0f) {
            return;
        }
        if (getScale() > this.mMinScale || f > 1.0f) {
            if (this.mScaleChangeListener != null) {
                this.mScaleChangeListener.onScaleChange(f, f2, f3);
            }
            this.mSuppMatrix.postScale(f, f, f2, f3);
            checkAndDisplayMatrix();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r11, android.view.MotionEvent r12) {
        /*
            r10 = this;
            boolean r0 = r10.mZoomEnabled
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0099
            r0 = r11
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            boolean r0 = com.github.chrisbanes.photoview.Util.hasDrawable(r0)
            if (r0 == 0) goto L_0x0099
            int r0 = r12.getAction()
            r3 = 3
            if (r0 == r3) goto L_0x0027
            switch(r0) {
                case 0: goto L_0x001a;
                case 1: goto L_0x0027;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x0051
        L_0x001a:
            android.view.ViewParent r11 = r11.getParent()
            if (r11 == 0) goto L_0x0023
            r11.requestDisallowInterceptTouchEvent(r2)
        L_0x0023:
            r10.cancelFling()
            goto L_0x0051
        L_0x0027:
            float r0 = r10.getScale()
            float r3 = r10.mMinScale
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x0051
            android.graphics.RectF r0 = r10.getDisplayRect()
            if (r0 == 0) goto L_0x0051
            com.github.chrisbanes.photoview.PhotoViewAttacher$AnimatedZoomRunnable r9 = new com.github.chrisbanes.photoview.PhotoViewAttacher$AnimatedZoomRunnable
            float r5 = r10.getScale()
            float r6 = r10.mMinScale
            float r7 = r0.centerX()
            float r8 = r0.centerY()
            r3 = r9
            r4 = r10
            r3.<init>(r5, r6, r7, r8)
            r11.post(r9)
            r11 = 1
            goto L_0x0052
        L_0x0051:
            r11 = 0
        L_0x0052:
            com.github.chrisbanes.photoview.CustomGestureDetector r0 = r10.mScaleDragDetector
            if (r0 == 0) goto L_0x008b
            com.github.chrisbanes.photoview.CustomGestureDetector r11 = r10.mScaleDragDetector
            boolean r11 = r11.isScaling()
            com.github.chrisbanes.photoview.CustomGestureDetector r0 = r10.mScaleDragDetector
            boolean r0 = r0.isDragging()
            com.github.chrisbanes.photoview.CustomGestureDetector r3 = r10.mScaleDragDetector
            boolean r3 = r3.onTouchEvent(r12)
            if (r11 != 0) goto L_0x0074
            com.github.chrisbanes.photoview.CustomGestureDetector r11 = r10.mScaleDragDetector
            boolean r11 = r11.isScaling()
            if (r11 != 0) goto L_0x0074
            r11 = 1
            goto L_0x0075
        L_0x0074:
            r11 = 0
        L_0x0075:
            if (r0 != 0) goto L_0x0081
            com.github.chrisbanes.photoview.CustomGestureDetector r0 = r10.mScaleDragDetector
            boolean r0 = r0.isDragging()
            if (r0 != 0) goto L_0x0081
            r0 = 1
            goto L_0x0082
        L_0x0081:
            r0 = 0
        L_0x0082:
            if (r11 == 0) goto L_0x0087
            if (r0 == 0) goto L_0x0087
            r1 = 1
        L_0x0087:
            r10.mBlockParentIntercept = r1
            r1 = r3
            goto L_0x008c
        L_0x008b:
            r1 = r11
        L_0x008c:
            android.view.GestureDetector r11 = r10.mGestureDetector
            if (r11 == 0) goto L_0x0099
            android.view.GestureDetector r11 = r10.mGestureDetector
            boolean r11 = r11.onTouchEvent(r12)
            if (r11 == 0) goto L_0x0099
            r1 = 1
        L_0x0099:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.chrisbanes.photoview.PhotoViewAttacher.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public void setAllowParentInterceptOnEdge(boolean z) {
        this.mAllowParentInterceptOnEdge = z;
    }

    public void setMinimumScale(float f) {
        Util.checkZoomLevels(f, this.mMidScale, this.mMaxScale);
        this.mMinScale = f;
    }

    public void setMediumScale(float f) {
        Util.checkZoomLevels(this.mMinScale, f, this.mMaxScale);
        this.mMidScale = f;
    }

    public void setMaximumScale(float f) {
        Util.checkZoomLevels(this.mMinScale, this.mMidScale, f);
        this.mMaxScale = f;
    }

    public void setScaleLevels(float f, float f2, float f3) {
        Util.checkZoomLevels(f, f2, f3);
        this.mMinScale = f;
        this.mMidScale = f2;
        this.mMaxScale = f3;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.mMatrixChangeListener = onMatrixChangedListener;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener) {
        this.mPhotoTapListener = onPhotoTapListener;
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener onOutsidePhotoTapListener) {
        this.mOutsidePhotoTapListener = onOutsidePhotoTapListener;
    }

    public void setScale(float f) {
        setScale(f, false);
    }

    public void setScale(float f, boolean z) {
        setScale(f, this.mImageView.getRight() / 2, this.mImageView.getBottom() / 2, z);
    }

    public void setScale(float f, float f2, float f3, boolean z) {
        if (f < this.mMinScale || f > this.mMaxScale) {
            throw new IllegalArgumentException("Scale must be within the range of minScale and maxScale");
        } else if (z) {
            this.mImageView.post(new AnimatedZoomRunnable(getScale(), f, f2, f3));
        } else {
            this.mSuppMatrix.setScale(f, f, f2, f3);
            checkAndDisplayMatrix();
        }
    }

    public void setZoomInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (Util.isSupportedScaleType(scaleType) && scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            update();
        }
    }

    public void setZoomable(boolean z) {
        this.mZoomEnabled = z;
        update();
    }

    public void update() {
        if (this.mZoomEnabled) {
            updateBaseMatrix(this.mImageView.getDrawable());
        } else {
            resetMatrix();
        }
    }

    public void getDisplayMatrix(Matrix matrix) {
        matrix.set(getDrawMatrix());
    }

    public void getSuppMatrix(Matrix matrix) {
        matrix.set(this.mSuppMatrix);
    }

    public Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    public Matrix getImageMatrix() {
        return this.mDrawMatrix;
    }

    public void setZoomTransitionDuration(int i) {
        this.mZoomDuration = i;
    }

    private float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        setRotationBy(this.mBaseRotation);
        setImageViewMatrix(getDrawMatrix());
        checkMatrixBounds();
    }

    public void setImageViewMatrix(Matrix matrix) {
        RectF displayRect;
        this.mImageView.setImageMatrix(matrix);
        if (this.mMatrixChangeListener != null && (displayRect = getDisplayRect(matrix)) != null) {
            this.mMatrixChangeListener.onMatrixChanged(displayRect);
        }
    }

    private void checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            setImageViewMatrix(getDrawMatrix());
        }
    }

    private RectF getDisplayRect(Matrix matrix) {
        Drawable drawable = this.mImageView.getDrawable();
        if (drawable == null) {
            return null;
        }
        this.mDisplayRect.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        matrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
    }

    private void updateBaseMatrix(Drawable drawable) {
        if (drawable != null) {
            float imageViewWidth = getImageViewWidth(this.mImageView);
            float imageViewHeight = getImageViewHeight(this.mImageView);
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            this.mBaseMatrix.reset();
            float f = intrinsicWidth;
            float f2 = imageViewWidth / f;
            float f3 = intrinsicHeight;
            float f4 = imageViewHeight / f3;
            if (this.mScaleType != ImageView.ScaleType.CENTER) {
                if (this.mScaleType != ImageView.ScaleType.CENTER_CROP) {
                    if (this.mScaleType != ImageView.ScaleType.CENTER_INSIDE) {
                        RectF rectF = new RectF(0.0f, 0.0f, f, f3);
                        RectF rectF2 = new RectF(0.0f, 0.0f, imageViewWidth, imageViewHeight);
                        if (((int) this.mBaseRotation) % 180 != 0) {
                            rectF = new RectF(0.0f, 0.0f, f3, f);
                        }
                        switch (AnonymousClass3.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                            case 1:
                                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
                                break;
                            case 2:
                                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.START);
                                break;
                            case 3:
                                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.END);
                                break;
                            case 4:
                                this.mBaseMatrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
                                break;
                        }
                    } else {
                        float min = Math.min(1.0f, Math.min(f2, f4));
                        this.mBaseMatrix.postScale(min, min);
                        this.mBaseMatrix.postTranslate((imageViewWidth - (f * min)) / 2.0f, (imageViewHeight - (f3 * min)) / 2.0f);
                    }
                } else {
                    float max = Math.max(f2, f4);
                    this.mBaseMatrix.postScale(max, max);
                    this.mBaseMatrix.postTranslate((imageViewWidth - (f * max)) / 2.0f, (imageViewHeight - (f3 * max)) / 2.0f);
                }
            } else {
                this.mBaseMatrix.postTranslate((imageViewWidth - f) / 2.0f, (imageViewHeight - f3) / 2.0f);
            }
            resetMatrix();
        }
    }

    /* renamed from: com.github.chrisbanes.photoview.PhotoViewAttacher$3 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ImageView.ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private boolean checkMatrixBounds() {
        float f;
        RectF displayRect = getDisplayRect(getDrawMatrix());
        if (displayRect == null) {
            return false;
        }
        float height = displayRect.height();
        float width = displayRect.width();
        float imageViewHeight = getImageViewHeight(this.mImageView);
        float f2 = 0.0f;
        if (height <= imageViewHeight) {
            switch (AnonymousClass3.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                case 2:
                    f = -displayRect.top;
                    break;
                case 3:
                    f = (imageViewHeight - height) - displayRect.top;
                    break;
                default:
                    f = ((imageViewHeight - height) / 2.0f) - displayRect.top;
                    break;
            }
        } else if (displayRect.top > 0.0f) {
            f = -displayRect.top;
        } else {
            f = displayRect.bottom < imageViewHeight ? imageViewHeight - displayRect.bottom : 0.0f;
        }
        float imageViewWidth = getImageViewWidth(this.mImageView);
        if (width <= imageViewWidth) {
            switch (AnonymousClass3.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                case 2:
                    f2 = -displayRect.left;
                    break;
                case 3:
                    f2 = (imageViewWidth - width) - displayRect.left;
                    break;
                default:
                    f2 = ((imageViewWidth - width) / 2.0f) - displayRect.left;
                    break;
            }
            this.mScrollEdge = 2;
        } else if (displayRect.left > 0.0f) {
            this.mScrollEdge = 0;
            f2 = -displayRect.left;
        } else if (displayRect.right < imageViewWidth) {
            f2 = imageViewWidth - displayRect.right;
            this.mScrollEdge = 1;
        } else {
            this.mScrollEdge = -1;
        }
        this.mSuppMatrix.postTranslate(f2, f);
        return true;
    }

    private int getImageViewWidth(ImageView imageView) {
        return (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
    }

    private int getImageViewHeight(ImageView imageView) {
        return (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
    }

    private void cancelFling() {
        if (this.mCurrentFlingRunnable != null) {
            this.mCurrentFlingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    /* loaded from: classes.dex */
    public class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime = System.currentTimeMillis();
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(float f, float f2, float f3, float f4) {
            PhotoViewAttacher.this = r1;
            this.mFocalX = f3;
            this.mFocalY = f4;
            this.mZoomStart = f;
            this.mZoomEnd = f2;
        }

        @Override // java.lang.Runnable
        public void run() {
            float interpolate = interpolate();
            PhotoViewAttacher.this.onScale((this.mZoomStart + ((this.mZoomEnd - this.mZoomStart) * interpolate)) / PhotoViewAttacher.this.getScale(), this.mFocalX, this.mFocalY);
            if (interpolate < 1.0f) {
                Compat.postOnAnimation(PhotoViewAttacher.this.mImageView, this);
            }
        }

        private float interpolate() {
            return PhotoViewAttacher.this.mInterpolator.getInterpolation(Math.min(1.0f, (((float) (System.currentTimeMillis() - this.mStartTime)) * 1.0f) / PhotoViewAttacher.this.mZoomDuration));
        }
    }

    /* loaded from: classes.dex */
    public class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final OverScroller mScroller;

        public FlingRunnable(Context context) {
            PhotoViewAttacher.this = r1;
            this.mScroller = new OverScroller(context);
        }

        public void cancelFling() {
            this.mScroller.forceFinished(true);
        }

        public void fling(int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7;
            int i8;
            RectF displayRect = PhotoViewAttacher.this.getDisplayRect();
            if (displayRect != null) {
                int round = Math.round(-displayRect.left);
                float f = i;
                if (f < displayRect.width()) {
                    i5 = Math.round(displayRect.width() - f);
                    i6 = 0;
                } else {
                    i6 = round;
                    i5 = i6;
                }
                int round2 = Math.round(-displayRect.top);
                float f2 = i2;
                if (f2 < displayRect.height()) {
                    i7 = Math.round(displayRect.height() - f2);
                    i8 = 0;
                } else {
                    i8 = round2;
                    i7 = i8;
                }
                this.mCurrentX = round;
                this.mCurrentY = round2;
                if (round != i5 || round2 != i7) {
                    this.mScroller.fling(round, round2, i3, i4, i6, i5, i8, i7, 0, 0);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                PhotoViewAttacher.this.mSuppMatrix.postTranslate(this.mCurrentX - currX, this.mCurrentY - currY);
                PhotoViewAttacher.this.setImageViewMatrix(PhotoViewAttacher.this.getDrawMatrix());
                this.mCurrentX = currX;
                this.mCurrentY = currY;
                Compat.postOnAnimation(PhotoViewAttacher.this.mImageView, this);
            }
        }
    }
}
