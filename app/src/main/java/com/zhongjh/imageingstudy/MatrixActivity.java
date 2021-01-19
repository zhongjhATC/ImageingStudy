package com.zhongjh.imageingstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.zhongjh.imageingstudy.common.DisplayMetricsUtils;

/**
 * 这是演示矩阵的，详细说明了postTranslate和preTranslate
 */
public class MatrixActivity extends AppCompatActivity {

    private EditText scaleEt;
    private EditText rotateEt;
    private EditText translateEt1;
    private EditText translateEt2;
    private ImageView imageView;
    private Matrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        scaleEt = (EditText) findViewById(R.id.et_scale);
        rotateEt = (EditText) findViewById(R.id.et_rotate);
        translateEt1 = (EditText) findViewById(R.id.et_translateX);
        translateEt2 = (EditText) findViewById(R.id.et_translateY);
        imageView = (ImageView) findViewById(R.id.iv_matrix);
        matrix = new Matrix();
    }

    /**
     * 缩放
     */
    public void scaleBitmap(View view) {
        matrix.postScale(getValues(scaleEt), getValues(scaleEt));
        imageView.setImageMatrix(matrix);
    }

    /**
     * 旋转
     */
    public void rotateBitmap(View view) {
        matrix.postRotate(getValues(rotateEt));
        imageView.setImageMatrix(matrix);
    }

    /**
     * 移动
     */
    public void translateBitmap(View view) {
        matrix.postTranslate(DisplayMetricsUtils.dip2px(getValues(translateEt1)), DisplayMetricsUtils.dip2px(getValues(translateEt2)));
        imageView.setImageMatrix(matrix);
    }

    /**
     * 还原
     */
    public void clearMatrix(View view) {
        matrix.reset();
        imageView.setImageMatrix(matrix);
    }

    /**
     * 先pre-后post
     */
    public void test1(View view) {
        matrix.reset();
        matrix.preScale(2,2);
        matrix.postTranslate(DisplayMetricsUtils.dip2px(100), DisplayMetricsUtils.dip2px(100));
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }

    /**
     * 先post-后pre
     * 跟test1相比，一个是先放大，一个是先移动。为啥差距这么大
     * 可以这么理解。先平移到了（100,100）。然后进行放大一倍。但是这个放大。是将整个画布，或者是坐标系都放大了，所以到达了（200,200）坐标处
     */
    public void test2(View view) {
        matrix.reset();
        matrix.postScale(2,2);
        matrix.preTranslate(DisplayMetricsUtils.dip2px(100), DisplayMetricsUtils.dip2px(100));
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }

    /**
     * 执行顺序 preTranslate - preScale - postScale - postTranslate
     * 验证效果可以使用上面4个按钮按照上面步骤一个一个执行
     */
    public void test3(View view) {
        matrix.reset();
        matrix.preScale(2f,2f);
        matrix.preTranslate(DisplayMetricsUtils.dip2px(50), DisplayMetricsUtils.dip2px(50));
        matrix.postScale(0.5f, 0.5f);
        matrix.postTranslate(DisplayMetricsUtils.dip2px(10), DisplayMetricsUtils.dip2px(10));
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }


    /**
     * 执行顺序：因为有setScale，所以它上面的两行代码postTranslate和preScale无效，最终顺序是：preTranslate - setScale -  postScale
     */
    public void test4(View view) {
        matrix.reset();
        matrix.postTranslate(DisplayMetricsUtils.dip2px(50), DisplayMetricsUtils.dip2px(50));
        matrix.preScale(0.5f, 0.5f);
        matrix.setScale(1f, 1f);
        matrix.postScale(3f, 3f);
        matrix.preTranslate(DisplayMetricsUtils.dip2px(10), DisplayMetricsUtils.dip2px(10));
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }

    /**
     * 这是套用了IMGImage的addPath的方法，助于了解图片的绘制路径
     */
    public void test5(View view) {
        matrix.reset();
        matrix.setTranslate(DisplayMetricsUtils.dip2px(100), DisplayMetricsUtils.dip2px(100));
        matrix.postTranslate(DisplayMetricsUtils.dip2px(100), DisplayMetricsUtils.dip2px(100));
        matrix.postScale(1.5f, 1.5f);
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }

    /**
     * 这是一种特殊的缩放变换。X为负数，则表示以X轴翻转后，再进行缩放。
     * 注意： 没有Translate，只有matrix.setScale(-1,1)。 图像会翻转到X轴返方向。会看不到图像。
     *
     * Translate 最好用 postTranslate 。  如果用了setScale  和  preTranslate ，是不会看到图像的。
     * 理解：因为是preTranslate，会将图像先平移，距离X轴一段距离。然后再将图像沿X轴翻转。然后再放大（整体放大，包括刚才的平移的距离。）导致图像离X的负半轴更远。
     */
    public void test6(View view) {
        matrix.reset();
        matrix.postScale(-1, -1);
        matrix.postTranslate(DisplayMetricsUtils.dip2px(100),DisplayMetricsUtils.dip2px(100));
        imageView.setImageMatrix(matrix);
        matrix.reset();
    }


    private float getValues(EditText et) {
        return Float.parseFloat(et.getText().toString());
    }
}
