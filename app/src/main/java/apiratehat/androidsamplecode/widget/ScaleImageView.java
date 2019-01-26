package apiratehat.androidsamplecode.widget;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import java.net.Socket;


/**
 * Created by PirateHat on 2019/1/26.
 */

public class ScaleImageView extends android.support.v7.widget.AppCompatImageView
        implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener {


    private boolean mOnce;

    //初始化时缩放的值
    private float mInitScale;
    //双击放大的值
    private float mMaxScale;

    //双击缩小的值
    private float mMinScale;

    //多点触控
    private ScaleGestureDetector mScaleGestureDetector;


    //--------自由移动--------
    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    private Matrix mScaleMatrix;

    private boolean isAutoScale;

    //-----------双击放大缩小------------
    private GestureDetector mGestureDetector;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        //注意设置这个
        setOnTouchListener(this);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                if (isAutoScale) {
                    return true;
                }
                if (getScale() < mMinScale) {
//                    mScaleMatrix.postScale(mMinScale/getScale(),mMinScale/getScale(),x,y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mMinScale, x, y), 16);
                    isAutoScale = true;
                } else {
//                    mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }


                return super.onDoubleTap(e);
            }
        });

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);

    }

    /**
     * 获取 ImageView 加载完成的 图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //获取控件的宽和高
            int width = getWidth();
            int height = getHeight();


            //获取图片的宽和高
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;

            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            if (dw < width && dh < height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            //初始化的缩放的比例
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMinScale = mInitScale * 2;

            //移动图片至控件中心

            int dx = width / 2 - dw / 2;
            int dy = height / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);
            mOnce = true;
        }

    }

    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);

        return values[Matrix.MSCALE_X];
    }

    //缩放的区间  init --- max
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            return true;
        }
        // 允许放大，允许缩小
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {

            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            //缩放的时候需要不断调整距离
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            //在缩放的时候进行边界控制和位置的控制
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }


        return true;
    }

    /**
     * 获取改变后的图片后的大小，高宽
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable d = getDrawable();

        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            // TODO: 2019/1/26 这个是啥？
            matrix.mapRect(rectF);
        }

        return rectF;
    }

    /**
     * 缩放的时候检测，边界和中心
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();


        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();


        //平移至旁边没有空白
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }

            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }

            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }


        }

        if (rectF.width() < width) {
            deltaX = width / 2f + rectF.width() / 2f - rectF.right;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f + rectF.height() / 2f - rectF.bottom;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);


    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //这里返回 true
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        mScaleGestureDetector.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        //多点触控的数量
        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;
        if (mLastPointerCount != pointerCount) {
            mLastX = x;
            mLastY = y;

        }

        mLastPointerCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    isCheckLeftAndRight = isCheckTopAndBottom = true;
                    if (getDrawable() != null) {
                        // 宽高比较小的时候不移动
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }

                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                    }
                }


                mScaleMatrix.postTranslate(dx, dy);
                checkBorderWhenTranslate();
                setImageMatrix(mScaleMatrix);

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;

        }
        //
        //返回 true
        return true;
    }

    /**
     * 平移的时候检测白边
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();


        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();


        //平移至旁边没有空白
        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }


    private class AutoScaleRunnable implements Runnable {
        //缩放的目标值
        private float mTargetScale;
        private float x;
        private float y;

        private final float LARGE = 1.07f;

        private final float SMALL = 0.93f;

        private float tmpScale;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = LARGE;
            }
           else if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }


        @Override
        public void run() {

            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float current = getScale();
            if ((tmpScale > 1.0f && current < mTargetScale) || (tmpScale < 1.0f && current > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = mTargetScale / current;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

}
