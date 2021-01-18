package com.zhongjh.imageingstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnMatrix:
                intent.setClass(MainActivity.this, MatrixActivity.class);
                startActivity(intent);
                break;
            case R.id.btnScrolly:
                intent.setClass(MainActivity.this, ScrollyActivity.class);
                startActivity(intent);
                break;
            case R.id.btnScale:
                intent.setClass(MainActivity.this, ScaleActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDoodle:
                intent.setClass(MainActivity.this, DoodleActivity.class);
                startActivity(intent);
                break;
        }
    }
}