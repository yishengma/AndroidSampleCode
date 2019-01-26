package apiratehat.androidsamplecode.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import apiratehat.androidsamplecode.R;

/**
 * Created by PirateHat on 2019/1/25.
 */

public class CanvasViewTest extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public CanvasViewTest(Context context) {
        this(context, null);
    }

    public CanvasViewTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    // TODO: 2019/1/25 drawPoints 会出现这个, 这是什么东西
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//<-------------画布背景色----------->
//        canvas.drawColor(Color.GREEN);

//<-------------画布点，点数组----------->
//         canvas.drawPoint(200,200,mPaint);
//
//         canvas.drawPoints(new float[]{
//                 500,600,
//                 500,700,
//                 500,800
//
//         },mPaint);

        //<------------画线-------------->
//        canvas.drawLine(300,300,500,600,mPaint);
//        canvas.drawLines(new float[]{100,200,200,200,
//                100,300,200,300},mPaint);

        //<--------------矩形-------------------->
//        canvas.drawRect(100,100,800,400,mPaint);
//
//        //
//        Rect rect = new Rect(100,100,800,400);
//        canvas.drawRect(rect,mPaint);
//        //
//        RectF rectF = new RectF(100,100,800,400);
//        canvas.drawRect(rectF,mPaint);

        //<-----------------圆角矩形---------------------->
//        RectF rectF = new RectF(100,100,800,400);
//        canvas.drawRoundRect(rectF,30,30,mPaint);
//
//        canvas.drawRoundRect(100,100,800,400,30,30,mPaint);


        //<-----------------圆形/平移---------------------->
//        mPaint.setColor(Color.BLACK);
//        canvas.translate(200, 200);
//        canvas.drawCircle(0, 0, 100, mPaint);
//
//        mPaint.setColor(Color.BLUE);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,mPaint);

        //<------------------缩放--------------------------->
//        canvas.translate(mWidth/2,mHeight/2);
//
//        RectF rectF = new RectF(0,-400,400,0);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawRect(rectF,mPaint);
//
//       canvas.scale(0.5f,0.5f);
//
//       mPaint.setColor(Color.BLUE);
//       canvas.drawRect(rectF,mPaint);

        //反方向缩放
//       canvas.scale(-0.5f,-0.5f);
//
//       mPaint.setColor(Color.BLUE);
//       canvas.drawRect(rectF,mPaint);

        //
        canvas.translate(mWidth/2,mHeight/2);
//
        RectF rectF = new RectF(-400,-400,400,400);
        for (int i = 0; i <= 20; i++) {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,mPaint);
        }





    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);

    }
}
