package com.zhongjh.imageingstudy.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.zhongjh.imageingstudy.common.IMGPath;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是个最基本的绘制图片的类
 */
public class IMGImage {

    private Bitmap mImage;
    private Paint mPaint;

    /**
     * 完整图片边框
     */
    private RectF mFrame = new RectF();

    /**
     * 矩阵
     */
    private Matrix M = new Matrix();

    /**
     * 涂鸦路径
     */
    private List<IMGPath> mDoodles = new ArrayList<>();

    {
        // Doodle&Mosaic 's paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(IMGPath.BASE_DOODLE_WIDTH);
        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new CornerPathEffect(IMGPath.BASE_DOODLE_WIDTH));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

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

    /**
     * 涂鸦
     */
    public void onDrawDoodles(Canvas canvas) {
        canvas.save();
        float scale = getScale();
        canvas.translate(mFrame.left, mFrame.top);
        canvas.scale(scale, scale);
        for (IMGPath path : mDoodles) {
            path.onDrawDoodle(canvas, mPaint);
        }
        canvas.restore();
    }

    /**
     * addPath方法详解：
     * M.setTranslate(sx, sy);
     * 矩阵平移到跟view的xy轴一样
     * M.postTranslate(-mFrame.left, -mFrame.top);
     *
     *
     *
     */
    public void addPath(IMGPath path, float sx, float sy) {
        if (path == null) return;

        float scale = 1f / getScale();
        M.setTranslate(sx, sy);
        M.postTranslate(-mFrame.left, -mFrame.top);
        M.postScale(scale, scale);
        // 矩阵变换
        path.transform(M);

        mDoodles.add(path);
    }

    public float getScale() {
        return 1f * mFrame.width() / mImage.getWidth();
    }
}
