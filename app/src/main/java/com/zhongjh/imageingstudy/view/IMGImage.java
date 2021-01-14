package com.zhongjh.imageingstudy.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 这是个最基本的绘制图片的类
 */
public class IMGImage {

    private Bitmap mImage;

    /**
     * 完整图片边框
     */
    private RectF mFrame = new RectF();

    /**
     * 矩阵
     */
    private Matrix M = new Matrix();

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }

        this.mImage = bitmap;
        mFrame.set(0, 0, mImage.getWidth(), mImage.getHeight());
    }

    public void onDrawImage(Canvas canvas) {
        // 在mFrame该边框绘制图片
        canvas.drawBitmap(mImage, null, mFrame, null);
    }

    public void onScaleBegin() {
    }

    public void onScaleEnd() {
    }

    public void onScale(float factor, float focusX, float focusY) {
        if (factor == 1f) return;

        // 针对这个有图片的边框进行比例缩放
        M.setScale(factor, factor, focusX, focusY);
        M.mapRect(mFrame);
    }

}
