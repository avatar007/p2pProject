package com.studio.p2pproject.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 自定义拉出顶部和底部放手回弹的ScrollView
 */
public class MyScrollView extends ScrollView {
    private float downY = 0;
    private View mInnerView;//ScrollView内部空间
    private Rect mRect = new Rect();
    private boolean isAnimation = false;//判断是否在执行动画

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    //此方法表示内部子View加载完毕
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 0) {
            //获取子View
            mInnerView = getChildAt(0);
        }
    }


    @Override
    //重写触摸事件
    public boolean onTouchEvent(MotionEvent ev) {
        if (mInnerView == null) {
            return super.onTouchEvent(ev);
        } else {
            customMotionEvent(ev);//自定义触摸事件
        }
        return super.onTouchEvent(ev);
    }

    private void customMotionEvent(MotionEvent ev) {
        if (!isAnimation) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float preY = downY == 0 ? ev.getY() : downY;
                    float moveY = ev.getY();
                    int disY = (int) (moveY - preY);
                    //开始拉出顶部或底部
                    if (isNeedMove()) {
                        if (mRect.isEmpty()) {
                            //记录内部控件原来的left,top,right,bottom
                            mRect.set(mInnerView.getLeft(), mInnerView.getTop(), mInnerView.getRight(), mInnerView.getBottom());
                        }
                        //拉出顶部或底部为距离的一半
                        mInnerView.layout(mInnerView.getLeft(), mInnerView.getTop() + disY / 2,
                                mInnerView.getRight(), mInnerView.getBottom() + disY / 2);
                    }
                    downY = moveY;
                    break;
                case MotionEvent.ACTION_UP:
                    downY = 0;
                    //放手自动弹回,动画弹回
                    //mInnerView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
                    if (!mRect.isEmpty()) {//按下若没有移动抬起时无需做动画
                        backAnimation();
                    }
                    break;
            }
        }

    }

    private void backAnimation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, mRect.top - mInnerView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimation = true;//开始执行动画
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mInnerView.clearAnimation();
                mInnerView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
                mRect.setEmpty();
                isAnimation = false;//动画完毕
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mInnerView.setAnimation(ta);
    }

    private boolean isNeedMove() {
        //获取滚动的距离,若子View未超过ScrollView的显示区域,滚动一直为0;
        int scrollY = getScrollY();
        //获取滚动的偏移量getWidth是自定义scrollView的高度(屏幕顶部开始计算)
        //getMeasuredHeight()是控件的高度,可用超出屏幕(和屏幕无关)
        int offset = mInnerView.getMeasuredHeight() - getWidth();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
}
