package com.zhongjh.imageingstudy.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.zhongjh.imageingstudy.common.IMGMode;
import com.zhongjh.imageingstudy.area.anim.IMGHomingAnimator;

/**
 * 裁剪
 */
public class ClipFrameLayout extends FrameLayout implements Animator.AnimatorListener  {

    private final String TAG = ClipFrameLayout.class.getSimpleName();

    private IMGImage mImage = new IMGImage();
    private IMGMode mMode = IMGMode.NONE;

    private IMGHomingAnimator mHomingAnimator;

    public ClipFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ClipFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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

        // clip 中心旋转
        RectF clipFrame = mImage.getClipFrame();
        canvas.rotate(mImage.getRotate(), clipFrame.centerX(), clipFrame.centerY());

        // 图片
        mImage.onDrawImage(canvas);

        // 裁剪
        if (mMode == IMGMode.CLIP) {
            canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            mImage.onDrawClip(canvas, getScrollX(), getScrollY());
            canvas.restore();
        }
    }

    /**
     * 标记着动画的开始
     */
    @Override
    public void onAnimationStart(Animator animator) {
        Log.d(TAG, "onAnimationStart");
        mImage.onHomingStart(mHomingAnimator.isRotate());
    }

    @Override
    public void onAnimationEnd(Animator animator) {
//        if (mImage.onHomingEnd(getScrollX(), getScrollY(), mHomingAnimator.isRotate())) {
//            toApplyHoming(mImage.clip(getScrollX(), getScrollY()));
//        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

}
