package com.zhongjh.imageingstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 移动view
 * 该类演示了如何scrolly当前view
 * 通过GestureDetector手势监听类，传递MotionEvent来执行相关onScroll方法
 * 通过GestureDetector传递的xy，在当前view的xy基础上叠加
 * 最后得到的值，通过当前view的scrollTo方法调用
 */
public class ScrollyFrameLayout extends FrameLayout {

    private static final String TAG = "ScrollyFrameLayout";

    private GestureDetector mGDetector;

    public ScrollyFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ScrollyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        // 手势监听类
        mGDetector = new GestureDetector(context, new MoveAdapter());
    }

    /**
     * 处理触屏事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTouch(event);
    }

    /**
     * 处理触屏事件.详情
     */
    boolean onTouch(MotionEvent event) {
        Log.d(TAG, "onTouch");
        return onTouchNONE(event);
    }

    private boolean onTouchNONE(MotionEvent event) {
        Log.d(TAG, "onTouchNONE");
        return mGDetector.onTouchEvent(event);
    }

    private boolean onScroll(float dx, float dy) {
        return onScrollTo(getScrollX() + Math.round(dx), getScrollY() + Math.round(dy));
    }

    private boolean onScrollTo(int x, int y) {
        if (getScrollX() != x || getScrollY() != y) {
            scrollTo(x, y);
            return true;
        }
        return false;
    }

    private class MoveAdapter extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return ScrollyFrameLayout.this.onScroll(distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // TODO
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

}
