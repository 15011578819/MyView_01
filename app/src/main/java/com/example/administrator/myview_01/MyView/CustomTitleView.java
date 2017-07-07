package com.example.administrator.myview_01.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.myview_01.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2017-7-6.
 */

public class CustomTitleView extends View {

    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;


    public CustomTitleView(Context context) {
        this(context, null);
    }

    /**
     * 布局文件默认用两个参数的构造方法
     * @param context
     * @param attrs
     */
    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     *
     * @param context
     * @param attrs 对应attrs.xml文件中的<declare-styleable/>标签
     * @param defStyle
     */
    public CustomTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        获得自定义样式属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        //最后记得将TypedArray对象回收
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint=new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound=new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText=randomText();
                //更新界面
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth=mBound.width();
            int desired=(int)(getPaddingLeft()+textWidth+getPaddingRight());
            width=desired;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeight=mBound.height();
            int desired=(int)(getPaddingTop()+textHeight+getPaddingBottom());
            height=desired;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);

        //添加噪点
        int [] point;
        for(int i=0;i<100;i++){
            point=getPoint(getHeight(),getWidth());
            canvas.drawCircle(point[0],point[1],1,mPaint);
        }

        int [] lines;
        for(int i=0;i<100;i++){
            lines=getLine(getHeight(),getWidth());
            canvas.drawLine(lines[0],lines[1],lines[2],lines[3],mPaint);
        }
    }

    private String randomText(){
        Random random=new Random();
        Set<Integer> set=new HashSet<>();
        while(set.size()<4){
            int randomInt=random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb=new StringBuffer();
        for(Integer i:set){
            sb.append(""+i);
        }
        return sb.toString();
    }

    private int[] getLine(int height,int width){
        int [] tempCheckNum={0,0,0,0};
        for(int i=0;i<4;i+=2){
            tempCheckNum[i]=(int)(Math.random()*width);
            tempCheckNum[i+1]=(int)(Math.random()*height);
        }
        return tempCheckNum;
    }

    private int[] getPoint(int height,int width){
        int [] tempCheckNum={0,0,0,0};
        tempCheckNum[0]=(int)(Math.random()*width);
        tempCheckNum[1]=(int)(Math.random()*height);
        return tempCheckNum;
    }
}
