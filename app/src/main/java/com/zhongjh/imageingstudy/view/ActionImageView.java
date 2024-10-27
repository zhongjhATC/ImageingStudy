package com.zhongjh.imageingstudy.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.zhongjh.imageingstudy.common.IMGMode;
import com.zhongjh.imageingstudy.core.ImageMode;

/**
 * 裁剪
 */
public class ActionImageView extends FrameLayout implements Animator.AnimatorListener  {

    private final String TAG = ActionImageView.class.getSimpleName();

    private final ActionImage mActionImage = new ActionImage();
    private IMGMode mMode = IMGMode.NONE;

    public ActionImageView(Context context) {
        this(context, null, 0);
    }

    public ActionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新在IMGView画图
     */
    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    /**
     * 标记着动画的开始
     */
    @Override
    public void onAnimationStart(Animator animator) {
    }

    @Override
    public void onAnimationEnd(Animator animator) {
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    /**
     * 赋值图片资源
     */
    public void setImageBitmap(Bitmap image) {
        mActionImage.setBitmap(image);
        invalidate();
    }

    /**
     * 设置模式
     *
     */
    public void setMode(ImageMode imageMode) {

    }

    /**
     * 是否真正修正归位
     */
    boolean isHoming() {
        return true;
    }

    /**
     * 画图
     */
    private void onDrawImages(Canvas canvas) {
        Log.d(TAG, "onDrawImages");
        canvas.save();

        // 图片
        mActionImage.onDrawImage(canvas);
    }



}
