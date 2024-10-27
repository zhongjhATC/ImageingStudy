package com.zhongjh.imageingstudy.area;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.zhongjh.imageingstudy.area.homing.ImageHoming;
import com.zhongjh.imageingstudy.core.clip.ImageClipView;
import com.zhongjh.imageingstudy.core.util.ImageUtils;

/**
 * 自定义View
 *
 * @author zhongjh
 * @date 2021/12/17
 */
public class ImageCustom {

    private Bitmap mImage;
    private float mRotate = 0, mTargetRotate = 0;

    /**
     * 是否初始位置
     */
    private boolean isInitialHoming = false;

    /**
     * 裁剪窗口
     */
    private final ImageClipView mClipWin = new ImageClipView();

    /**
     * 裁剪图片边框（显示的图片区域）
     */
    private final RectF mClipFrame = new RectF();

    /**
     * 可视区域，无Scroll 偏移区域
     */
    private final RectF mWindow = new RectF();

    public RectF getClipFrame() {
        return mClipFrame;
    }

    private final Matrix mMatrix = new Matrix();

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mImage = bitmap;

        mFrame.set(0, 0, mImage.getWidth(), mImage.getHeight());
    }

    /**
     * 完整图片边框
     */
    private final RectF mFrame = new RectF();

    public float getScale() {
        return 1f * mFrame.width() / mImage.getWidth();
    }

    public float getRotate() {
        return mRotate;
    }

    public float getTargetRotate() {
        return mTargetRotate;
    }

    public ImageHoming getStartHoming(float scrollX, float scrollY) {
        return new ImageHoming(scrollX, scrollY, getScale(), getRotate());
    }

    public ImageHoming getEndHoming(float scrollX, float scrollY) {
        ImageHoming homing = new ImageHoming(scrollX, scrollY, getScale(), getTargetRotate());

        RectF frame = new RectF(mClipWin.getTargetFrame());
        frame.offset(scrollX, scrollY);
        if (mClipWin.isResetting()) {

            RectF clipFrame = new RectF();
            mMatrix.setRotate(getTargetRotate(), mClipFrame.centerX(), mClipFrame.centerY());
            mMatrix.mapRect(clipFrame, mClipFrame);

            homing.rConcat(ImageUtils.fill(frame, clipFrame));
        } else {
            RectF cFrame = new RectF();

            // cFrame要是一个暂时clipFrame
            if (mClipWin.isHoming()) {

                mMatrix.setRotate(getTargetRotate() - getRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                mMatrix.mapRect(cFrame, mClipWin.getOffsetFrame(scrollX, scrollY));

                homing.rConcat(ImageUtils.fitHoming(frame, cFrame, mClipFrame.centerX(), mClipFrame.centerY()));


            } else {
                mMatrix.setRotate(getTargetRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                mMatrix.mapRect(cFrame, mFrame);
                homing.rConcat(ImageUtils.fillHoming(frame, cFrame, mClipFrame.centerX(), mClipFrame.centerY()));
            }

        }

        return homing;
    }

    public void onDrawImage(Canvas canvas) {
        // 裁剪区域
        canvas.clipRect(mClipWin.isClipping() ? mFrame : mClipFrame);
        // 在mFrame该边框绘制图片
        canvas.drawBitmap(mImage, null, mFrame, null);
    }

    public void onInitialHoming() {
        mFrame.set(0, 0, 300, 300);
        mClipFrame.set(mFrame);

        toBaseHoming();
        isInitialHoming = true;
    }

    private void toBaseHoming() {
        if (mClipFrame.isEmpty()) {
            // Bitmap invalidate.
            return;
        }

        float scale = Math.min(
                mWindow.width() / mClipFrame.width(),
                mWindow.height() / mClipFrame.height()
        );

        // Scale to fit window.
        mMatrix.setScale(scale, scale, mClipFrame.centerX(), mClipFrame.centerY());
        mMatrix.postTranslate(mWindow.centerX() - mClipFrame.centerX(), mWindow.centerY() - mClipFrame.centerY());
        mMatrix.mapRect(mFrame);
        mMatrix.mapRect(mClipFrame);
    }

    public void onWindowChanged(float width, float height) {
        if (width == 0 || height == 0) {
            return;
        }

        mWindow.set(0, 0, width, height);

        if (!isInitialHoming) {
            onInitialHoming();
        } else {
            // Pivot to fit window.
            mMatrix.setTranslate(mWindow.centerX() - mClipFrame.centerX(), mWindow.centerY() - mClipFrame.centerY());
            mMatrix.mapRect(mFrame);
            mMatrix.mapRect(mClipFrame);
        }

        mClipWin.setClipWinSize(width, height);
    }

}
