package com.zhongjh.imageingstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.zhongjh.imageingstudy.common.IMGMode;

public class AreaFrameLayout extends FrameLayout {

    private final String TAG = AreaFrameLayout.class.getSimpleName();

    private IMGImage mImage = new IMGImage();

    public AreaFrameLayout(Context context) {
        this(context, null, 0);
    }

    public AreaFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AreaFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }

    /**
     * 设置模式
     *
     */
    public void setMode() {
        // 设置新的编辑模式
        mImage.setMode(IMGMode.CLIP);
    }

    /**
     * 重新在IMGView画图
     */
    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    /**
     * 重新在IMGView画图
     */
    private void onDrawImages(Canvas canvas) {
        Log.d(TAG, "onDrawImages");
        canvas.save();

        // 图片
        mImage.onDrawImage(canvas);

        canvas.save();
        canvas.translate(getScrollX(), getScrollY());
        mImage.onDrawClipTest(canvas, getScrollX(), getScrollY());
        canvas.restore();
    }

}
