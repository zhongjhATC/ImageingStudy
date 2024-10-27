package com.zhongjh.imageingstudy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhongjh.imageingstudy.core.ImageMode;
import com.zhongjh.imageingstudy.view.ActionImage;
import com.zhongjh.imageingstudy.view.ActionImageView;

/**
 * 裁剪的Activity
 */
public class CropActivity extends AppCompatActivity {

    ActionImageView actionImageView;
    Button btnCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        allFindViewById();
        initImage();
        addAllListener();
    }

    private void allFindViewById() {
        actionImageView = findViewById(R.id.actionImageView);
        btnCrop = findViewById(R.id.btnCrop);
    }

    private void addAllListener() {
        btnCrop.setOnClickListener(view -> crop());
    }

    private void initImage() {
        Glide.with(getApplication()).asBitmap().load(R.drawable.ic_lau).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                actionImageView.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    private void crop() {
        Log.i("tag", "crop");
        preHide();
        actionImageView.setMode(ImageMode.CLIP);
//        cropImageView.setRatioCropRect(actionImageView.getCurrentRotateRectF(), -1);
//        cropImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏相关控件
     */
    private void preHide() {

    }

}
