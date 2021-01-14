package com.zhongjh.imageingstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

/**
 * 缩放view
 */
public class ScaleFrameLayout extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "ScrollyFrameLayout";

    private IMGImage mImage = new IMGImage();

    private ScaleGestureDetector mSGDetector;
    private int mPointerCount = 0;

    public ScaleFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        // 用于处理缩放的工具类
        mSGDetector = new ScaleGestureDetector(context, this);
    }

    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        onDrawImages(canvas);
    }

    private void onDrawImages(Canvas canvas) {
        mImage.onDrawImage(canvas);
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
        mPointerCount = event.getPointerCount();
        return mSGDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            mImage.onScale(detector.getScaleFactor(),
                    getScrollX() + detector.getFocusX(),
                    getScrollY() + detector.getFocusY());
            invalidate();
            return true;
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        if (mPointerCount > 1) {
            mImage.onScaleBegin();
            return true;
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        mImage.onScaleEnd();
    }
}
