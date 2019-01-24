package apiratehat.androidsamplecode.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PirateHat on 2019/1/24.
 */

public class PieView extends View {
    private static final String TAG = "PieView";
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private float mStartAngle = 0;

    private ArrayList<PieData> mData;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;



    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null){
            return;
        }
        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth/2,mHeight/2);

        float radius = (float) (Math.min(mWidth,mHeight)/2 * 0.8);

        RectF rectF = new RectF(-radius,-radius,radius,radius);

        for (int i = 0,size = mData.size(); i < size; i++) {
            PieData data = mData.get(i);
            mPaint.setColor(data.mColor);
            canvas.drawArc(rectF,currentStartAngle,data.mAngle,true,mPaint);
            currentStartAngle += data.mAngle;
        }
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);


     }

     public void setStartAngle(int angle){
        mStartAngle = angle;
        invalidate();
     }

     public void setData(List<PieData> data){
         mData = (ArrayList<PieData>) data;
         initData(data);
         invalidate();
     }

     private void initData(List<PieData> data){
        if (data == null || data.size() == 0){
            return;
        }

        float sumValue = 0;

         for (int i = 0,size = data.size(); i < size; i++) {
              PieData pieData = mData.get(i);
              sumValue += pieData.mValue;

              pieData.mColor = mColors[i%mColors.length];


         }

         float sumAngle = 0;
         for (int i = 0,size =mData.size() ; i < size; i++) {
             PieData pieData = mData.get(i);

             float percentage = pieData.mValue / sumValue;

             float angle = percentage * 360;

             pieData.mPercentage = percentage;
             pieData.mAngle = angle;

             sumAngle += angle;

         }
         Log.i(TAG, "initData: " + sumAngle);
     }

     static class PieData{

        private String mName;
        private float mValue;
        private float mPercentage;

        private int mColor = 0;
        private float mAngle = 0;

        public PieData(@NonNull  String name,@NonNull float value) {
            mName = name;
            mValue = value;
        }

    }
}
