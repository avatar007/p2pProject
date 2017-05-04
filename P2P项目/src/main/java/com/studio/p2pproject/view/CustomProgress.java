package com.studio.p2pproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.studio.p2pproject.R;

/**
 * 自定义中间带进度文字,并且可以弧形展示进度变化的进度条
 */
public class CustomProgress extends View {

    private Paint paint;
    private int mRoundColor;//外圆颜色
    private int mRoundProColor;//弧度颜色
    private int mTextColor;//文字颜色
    private float mTextSize;//文字大小
    private static final int MAX_PROCESS = 100;//设置最大进度
    private float mThickness;//圆的厚度
    private int progress;//进度值

    public CustomProgress(Context context) {
        this(context, null);
    }

    //有些控件不能写this,要看attrs参数父类是否有值,有的话不能写成this
    public CustomProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgress);
        mRoundColor = typedArray.getColor(R.styleable.CustomProgress_roundColor, Color.DKGRAY);
        mRoundProColor = typedArray.getColor(R.styleable.CustomProgress_roundProgressColor, Color.RED);
        mThickness = typedArray.getDimension(R.styleable.CustomProgress_thickness, 0);
        mTextColor = typedArray.getColor(R.styleable.CustomProgress_textColor, Color.RED);
        progress = typedArray.getInteger(R.styleable.CustomProgress_progress, 50);
        mTextSize = typedArray.getDimension(R.styleable.CustomProgress_textSize, 20);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //一.绘制空心圆
        paint.setColor(mRoundColor);//圆外环的颜色
        paint.setStrokeWidth(mThickness);//设置外圆厚度
        paint.setStyle(Paint.Style.STROKE);//空心圆
        int center = getWidth() / 2;//圆心(宽度或者高度的一半)
        float radius = center - mThickness / 2;//半径(厚度是外圆半径减去内圆半径的长度所以这里是一半的厚度)
        canvas.drawCircle(center, center, radius, paint);

        //二.绘制圆内文字
        paint.setTextSize(mTextSize);
        paint.setColor(mTextColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        float textWidth = paint.measureText(progress + "%");
        //注:这里的x是文字left的值,但y值不是bottom(bottom也比较居中),是基准线,在bottom上方
        //这样获取基准线相对比较居中,但也不是完全居中.文字的大小也是文字的高度
        float baseline = center + mTextSize / 2 - paint.getFontMetrics().descent;
        canvas.drawText(progress + "%", center - textWidth / 2, (baseline+center + mTextSize / 2)/2, paint);
        //这样也比较居中(取两个值相加的一半更居中)
        //canvas.drawText(progress + "%", center - textWidth / 2, center + mTextSize / 2, paint);

        //三.绘制弧形
        paint.setColor(mRoundProColor);//弧形颜色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mThickness);
        //弧形的基准矩形,以内圆中的基准矩形的四个点作为left,top,right,bottom
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        //这样写不行
//        RectF rectF = new RectF(getLeft() + mThickness, getTop() + mThickness,
//                getRight() - mThickness, getBottom() - mThickness);
        //参1:基准矩形,也可直接传入左上右下值,参2:开始角度从0开始,
        //参3:结束角度(360度,设置一个最大进度,再根据传入的进度相比),参4:是否要画入圆心
        canvas.drawArc(rectF, 0, 360 * progress / MAX_PROCESS, false, paint);
    }

    //提供set方法,也可过方法传递进来,通过循环不断的重绘界面(耗时操作放在子线程中)
    public void setProgress(int progress) {
        if (progress >= 0 && progress <= MAX_PROCESS) {
            for (int i = 0; i <= progress; i++) {
                this.progress = i;
                postInvalidate();//非UI线程绘制使用postInvalidate(),UI线程使用Invalidate()
            }
        }
    }
}
