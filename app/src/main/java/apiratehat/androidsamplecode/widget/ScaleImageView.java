package apiratehat.androidsamplecode.widget;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.ViewTreeObserver;


/**
 * Created by PirateHat on 2019/1/26.
 */

public class ScaleImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean mOnce;

    //初始化时缩放的值
    private float mInitScale;
    //双击放大的值
    private float mMaxScale;

    //双击缩小的值
    private float mMinScale;


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

            mScaleMatrix.postTranslate(dx,dy);
            mScaleMatrix.postScale(mInitScale,mInitScale,width/2,height/2);
            setImageMatrix(mScaleMatrix);
            mOnce = true;
        }

  }
}
