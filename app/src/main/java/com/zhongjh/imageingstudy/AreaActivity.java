package com.zhongjh.imageingstudy;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zhongjh.imageingstudy.area.anim.IMGHomingAnimator;

/**
 * 这是演示裁剪里面的矩阵区域如何变形的
 */
public class AreaActivity extends AppCompatActivity {

    private IMGHomingAnimator mHomingAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
    }

    /**
     * 回到原位
     */
    private void onHoming() {
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

}
