package com.zhongjh.imageingstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MatrixConstraintLayout extends ConstraintLayout {

    private IMGImage mImage = new IMGImage();

    public MatrixConstraintLayout(Context context) {
        this(context, null, 0);
    }

    public MatrixConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    private void onDrawImages(Canvas canvas) {
        mImage.onDrawImage(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mImage.onWindowChanged(right - left, bottom - top);
        }
    }

}
