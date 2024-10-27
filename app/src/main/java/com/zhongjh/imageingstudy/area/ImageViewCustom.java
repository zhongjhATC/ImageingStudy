package com.zhongjh.imageingstudy.area;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zhongjh.imageingstudy.area.anim.ImageHomingAnimator;
import com.zhongjh.imageingstudy.area.homing.ImageHoming;

/**
 * 包含ImageView
 * @author zhongjh
 * @date 2021/12/17
 */
public class ImageViewCustom  extends FrameLayout implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private ImageHomingAnimator mHomingAnimator;
    private final ImageCustom mImage = new ImageCustom();


    public ImageViewCustom(Context context) {
        this(context, null, 0);
    }

    public ImageViewCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }

    /**
     * 重新在IMGView画图
     */
    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mImage.onWindowChanged(right - left, bottom - top);
        }
    }

    /**
     * 重新在IMGView画图
     */
    private void onDrawImages(Canvas canvas) {
        canvas.save();

        // clip 中心旋转
        RectF clipFrame = mImage.getClipFrame();
        canvas.rotate(mImage.getRotate(), clipFrame.centerX(), clipFrame.centerY());

        // 图片
        mImage.onDrawImage(canvas);
    }

    /**
     * 回到原位
     */
    public void onHoming() {
        invalidate();
        // 停止当前所有动画操作
        stopHoming();
        // 开始回到中间的动画
        startHoming(mImage.getStartHoming(getScrollX(), getScrollY()),
                mImage.getEndHoming(getScrollX(), getScrollY()));
    }

    /**
     * 停止当前的矫正区域动画
     */
    private void stopHoming() {
        if (mHomingAnimator != null) {
            mHomingAnimator.cancel();
        }
    }

    /**
     * 开始了矫正动画
     */
    private void startHoming(ImageHoming sHoming, ImageHoming eHoming) {
        eHoming.x = 0;
        eHoming.y = 0;
        eHoming.scale = 0.5F;
        if (mHomingAnimator == null) {
            mHomingAnimator = new ImageHomingAnimator();
            mHomingAnimator.addUpdateListener(this);
            mHomingAnimator.addListener(this);
        }
        mHomingAnimator.setHomingValues(sHoming, eHoming);
        mHomingAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation, boolean isReverse) {

    }

    @Override
    public void onAnimationEnd(Animator animation, boolean isReverse) {

    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
