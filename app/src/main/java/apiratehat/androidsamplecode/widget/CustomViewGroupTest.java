package apiratehat.androidsamplecode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PirateHat on 2019/1/24.
 */

public class CustomViewGroupTest extends ViewGroup {

    public CustomViewGroupTest(Context context) {
        super(context);
    }

    public CustomViewGroupTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroupTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomViewGroupTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //将所有的 子 View 进行测量，这会触发每个子 View 的 onMeasure 函数
        //注意要与 measureChild 区分，measureChild 是对单个 View 进行测量
        // TODO: 2019/1/24 这里三个相似的方法的区别
        //measureChildren();
        //measureChildWithMargins();
        //measureChild();

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount == 0){
            setMeasuredDimension(0,0);
        }

        //ViewGroup 宽 wrap_content
        //          高 wrap_content
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            int height = getTotalHeight();
            int width = getMaxChildWidth();
            setMeasuredDimension(width,height);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,getTotalHeight());

        }else if (widthMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(getMaxChildWidth(),heightSize);
        }



    }

    public int getTotalHeight(){
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }

    public int getMaxChildWidth(){
        int childCount = getChildCount();
        int maxWid = 0;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWid){
                maxWid = childView.getMeasuredWidth();
            }

        }
        return maxWid;
    }

    //实现 ViewGroup 就要实现 onLayout 方法,这是 ViewGroup 的抽象方法。

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int curHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();

            child.layout(l,curHeight,l+width,curHeight+height);
            curHeight += height;
        }

    }
}
