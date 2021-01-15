package com.zhongjh.imageingstudy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.zhongjh.imageingstudy.view.MatrixConstraintLayout;
import com.zhongjh.imageingstudy.view.ScaleFrameLayout;

/**
 * 这是演示矩阵的，详细说明了postTranslate和preTranslate
 */
public class MatrixActivity extends AppCompatActivity {

    private RectF mFrame = new RectF();
    Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);



        MatrixConstraintLayout matrixConstraintLayout = findViewById(R.id.matrixConstraintLayout);
        matrixConstraintLayout.setImageBitmap(drawableToBitmap(getResources().getDrawable(R.drawable.ic_lau)));
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
