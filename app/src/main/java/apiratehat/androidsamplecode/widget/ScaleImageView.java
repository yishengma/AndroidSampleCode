package apiratehat.androidsamplecode.widget;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
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

    private Matrix mScaleMatrix;


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
     * @return
     */
    private RectF getMatirxRectF(){
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

       Drawable d = getDrawable();

       if (d != null){
           rectF.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
           // TODO: 2019/1/26 这个是啥？
           matrix.mapRect(rectF);
       }

       return rectF;
    }

    private void checkBorderAndCenterWhenScale(){
         RectF rectF = getMatirxRectF();


         float deltaX = 0;
        float deltaY = 0;

         int width = getWidth();
         int height = getHeight();


         //平移至旁边没有空白
         if (rectF.width() >= width){
             if (rectF.left > 0){
                 deltaX = - rectF.left;
             }

             if (rectF.right < width){
                 deltaX = width - rectF.right;
             }
         }

         if (rectF.height() >= height){
             if (rectF.top > 0){
                 deltaY = -rectF.top;
             }

             if (rectF.bottom < height){
                 deltaY = height - rectF.bottom;
             }


         }

         if (rectF.width() < width){
             deltaX = width/2f + rectF.width()/2f - rectF.right;
         }

         if (rectF.height() < height){
             deltaY = height/2f + rectF.height()/2f- rectF.bottom;
         }

         mScaleMatrix.postTranslate(deltaX,deltaY);


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
        mScaleGestureDetector.onTouchEvent(event);
        //返回 true
        return true;
    }

}
