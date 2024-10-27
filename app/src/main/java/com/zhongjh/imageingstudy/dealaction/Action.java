package com.zhongjh.imageingstudy.dealaction;

import android.graphics.Canvas;

public interface Action {

    void execute(Canvas canvas);

    void start(Object... params);

    void next(Object... params);

    void stop(Object... params);
}
