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


}
