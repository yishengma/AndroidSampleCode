package apiratehat.androidsamplecode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import apiratehat.androidsamplecode.R;

/**
 * Created by PirateHat on 2019/1/24.
 */

public class CustomViewTest extends View {


    private int mDefalutSize ;
    // TODO: 2019/1/24 四个构造器的区别

    public CustomViewTest(Context context) {
        this(context, null);
    }

    public CustomViewTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewTest);

        mDefalutSize = typedArray.getDimensionPixelSize(R.styleable.CustomViewTest_default_size,100);

        typedArray.recycle();
    }

    public CustomViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getSize(mDefalutSize, widthMeasureSpec);
        int height = getSize(mDefalutSize, heightMeasureSpec);

        if (width < height){
            width = height;
        }else {
            height = width;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = getMeasuredHeight() / 2;
        int centerX = radius;
        int centerY = radius;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(centerX,centerY,radius,paint);


    }

    public int getSize(int defaultSize, int measureSpec) {
        int childSize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                childSize = size;
                break;
            }
            case MeasureSpec.AT_MOST: {
                childSize = defaultSize;
                break;
            }
            case MeasureSpec.EXACTLY: {
                childSize = defaultSize;
                break;
            }

        }
        return childSize;
    }
}
