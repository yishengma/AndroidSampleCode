package apiratehat.androidsamplecode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import apiratehat.androidsamplecode.R;

/**
 * Created by PirateHat on 2019/1/27.
 */

public class NewtonCradle extends View {

    private static  int MAX_BALL_COUNT ; // 最大的球的数量
    private static final int MIN_BALL_COUNT = 3;
    private static final int DEFAULT_BALL_COUNT =  6;
    private static final int DEFAULT_BALL_COLOR = 0xff_ff_ff;
    private static final int DEFAULT_CRADLE_COLOR = 0xff_ff_ff;
    private static final int DEFAULT_BASE_COLOR = 0xff_ff_ff;
    private static final int DEFAULT_LINE_COLOR = 0xff_ff_ff ;

    private int mBallCount;
    private int mBallColor;
    private int mCradleColor;
    private int mBaseColor;
    private int mLineColor;



    private Paint mBallPaint;
    private Paint mLinePaint;
    private Paint mBasePaint;
    private Paint mCradlePaint;


    private float mWidth;
    private float mHeight;

    private static final String TAG = "NewtonCradle";





    public NewtonCradle(Context context) {
        this(context, null);
    }

    public NewtonCradle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewtonCradle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
        initPaint();
    }

    /**
     * 初始化属性
     *
     * @param context 上下文
     * @param attrs 属性
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NewtonCradle);

        //颜色
        mBallColor = typedArray.getColor(R.styleable.NewtonCradle_ball_color, DEFAULT_BALL_COLOR);
        mBaseColor = typedArray.getColor(R.styleable.NewtonCradle_base_color, DEFAULT_BASE_COLOR);
        mCradleColor = typedArray.getColor(R.styleable.NewtonCradle_cradle_color, DEFAULT_CRADLE_COLOR);
        mLineColor = typedArray.getColor(R.styleable.NewtonCradle_line_color,DEFAULT_LINE_COLOR);
        //球的数量
        mBallCount = typedArray.getColor(R.styleable.NewtonCradle_ball_count, DEFAULT_BALL_COUNT);

        typedArray.recycle();

    }

    /**
     * 初始化 画笔
     */

    //数字的单位是 px
    //dp和px的换算公式 ：
    //dp*ppi/160 = px。比如1dp x 320ppi/160 = 2px。
    //在320x480分辨率，像素密度为160,1dp=1px
    //在480x800分辨率，像素密度为240,1dp=1.5px
    private void initPaint(){
        mBallPaint = new Paint();
        mBallPaint.setColor(Color.GREEN);
        mBallPaint.setStrokeWidth(20);
        mBallPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.FILL);

        mBasePaint = new Paint();
        mBasePaint.setColor(mBaseColor);
        mBasePaint.setStrokeWidth(2);
        mBasePaint.setStyle(Paint.Style.FILL);


        mCradlePaint = new Paint();
        mCradlePaint.setColor(mCradleColor);
        mCradlePaint.setStrokeWidth(2);
        mCradlePaint.setStyle(Paint.Style.FILL);




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w * 1.0f;
        mHeight = h * 1.0f;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先把画布平移至中心
        //坐标轴原点就在中心
        canvas.translate(mWidth/2,mHeight/2);

        drawBalls(canvas);
    }

    private void drawBase(){


    }

    private void drawBalls(Canvas canvas){
        //单数个球
        int radius = 20;
        int x = 0;
        int y = 0;


        //偶数个球

        int half = mBallCount /2;
        if ((mBallCount&1)==0){
            x = - 2 * half * radius + radius;

            for (int i = 0; i < mBallCount; i++) {
                canvas.drawCircle(x,y,radius,mBallPaint);

                x += radius * 2;
            }

        }else {
            x = - 2 * half * radius;

            for (int i = 0; i < mBallCount; i++) {
                canvas.drawCircle(x,y,radius,mBallPaint);
                x += radius * 2;
            }

        }


    }


}
