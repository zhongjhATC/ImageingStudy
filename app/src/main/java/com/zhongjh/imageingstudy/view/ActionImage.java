package com.zhongjh.imageingstudy.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class ActionImage {

    /**
     * 原图
     */
    Bitmap originBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    /**
     * 完整图片边框
     */
    public RectF mFrame = new RectF();
    /**
     * 是否初始化完成,图片是否加载完成
     */
    private boolean isComplete = false;

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        originBitmap = bitmap;
        mFrame.set(0, 0, originBitmap.getWidth(), originBitmap.getHeight());
    }

    public void onDrawImage(Canvas canvas) {
        // 在mFrame该边框绘制图片
        canvas.drawBitmap(originBitmap, null, mFrame, null);
    }
}
