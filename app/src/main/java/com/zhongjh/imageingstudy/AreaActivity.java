package com.zhongjh.imageingstudy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zhongjh.imageingstudy.view.AreaFrameLayout;

/**
 * 这是演示裁剪里面的矩阵区域如何变形的
 */
public class AreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        AreaFrameLayout areaFrameLayout = findViewById(R.id.areaFrameLayout);
        areaFrameLayout.setImageBitmap(drawableToBitmap(getResources().getDrawable(R.drawable.ic_lau)));
        areaFrameLayout.setMode();
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
