package com.zhongjh.imageingstudy.core.clip;

import android.graphics.RectF;

/**
 * 裁剪区域接口
 */
public interface ImageClip {

    /**
     * 裁剪区域的边距
     */
    float CLIP_MARGIN = 60f;

    /**
     * 角尺寸
     */
    float CLIP_CORNER_SIZE = 48f;

    /**
     * 裁剪区域最小尺寸
     */
    float CLIP_FRAME_MIN = CLIP_CORNER_SIZE * 3.14f;

    /**
     * 内边厚度
     */
    float CLIP_THICKNESS_CELL = 3f;

    /**
     * 外边框厚度
     */
    float CLIP_THICKNESS_FRAME = 8f;

    /**
     * 角边厚度
     */
    float CLIP_THICKNESS_SEWING = 14f;

    /**
     * 比例尺，用于计算出 {0, width, 1/3 width, 2/3 width} & {0, height, 1/3 height, 2/3 height}
     */
    float[] CLIP_SIZE_RATIO = {0, 1, 0.33f, 0.66f};

    int CLIP_CELL_STRIDES = 0x7362DC98;

    int CLIP_CORNER_STRIDES = 0x0AAFF550;

    float[] CLIP_CORNER_STEPS = {0, 3, -3};

    float[] CLIP_CORNER_SIZES = {0, CLIP_CORNER_SIZE, -CLIP_CORNER_SIZE};

    byte[] CLIP_CORNERS = {
            0x8, 0x8, 0x9, 0x8,
            0x6, 0x8, 0x4, 0x8,
            0x4, 0x8, 0x4, 0x1,
            0x4, 0xA, 0x4, 0x8,
            0x4, 0x4, 0x6, 0x4,
            0x9, 0x4, 0x8, 0x4,
            0x8, 0x4, 0x8, 0x6,
            0x8, 0x9, 0x8, 0x8
    };

    enum Anchor {
        /**
         * 左
         */
        LEFT(1),
        /**
         * 右
         */
        RIGHT(2),
        /**
         * 上
         */
        TOP(4),
        /**
         * 下
         */
        BOTTOM(8),
        /**
         * 左上
         */
        LEFT_TOP(5),
        /**
         * 右上
         */
        RIGHT_TOP(6),
        /**
         * 左下
         */
        LEFT_BOTTOM(9),
        /**
         * 右下
         */
        RIGHT_BOTTOM(10);

        final int value;

        /**
         * LEFT: 0
         * TOP: 2
         * RIGHT: 1
         * BOTTOM: 3
         */
        final static int[] PN = {1, -1};

        Anchor(int value) {
            this.value = value;
        }

        final static int COUNT = 4;

        /**
         * 控制某个点移动，移动某个点后，相邻两个点会跟着移动
         *
         * @param win       裁剪总窗口
         * @param frame     裁剪区域
         * @param distanceX 手指在x轴上的移动
         * @param distanceY 手指在y轴上的移动
         */
        public void move(RectF win, RectF frame, float distanceX, float distanceY) {
            // 计算最大值,4个角以CLIP_MARGIN为计算
            float[] maxFrame = cohesion(win, CLIP_MARGIN);
            // 计算最小值,4个角以CLIP_FRAME_MIN为计算,裁剪窗体
            float[] minFrame = cohesion(frame, CLIP_FRAME_MIN);
            // 移动后的theFrame
            float[] theFrame = cohesion(frame, 0);

            float[] dxy = {distanceX, 0, distanceY};
            // 循环4个角
            for (int i = 0; i < COUNT; i++) {
                // 1 << i 分别得出1、2、4、8（左右上下）,比如value是LEFT_BOTTOM值的话，那么1、8就满足条件
                if (((1 << i) & value) != 0) {

                    int pn = PN[i & 1];
                    // 计算移动后的值 - 通过revise限制了最大值和最小值
                    theFrame[i] = pn * revise(pn * (theFrame[i] + dxy[i & 2]),
                            pn * maxFrame[i], pn * minFrame[i + PN[i & 1]]);
                }
            }
            // 赋值裁剪区域
            frame.set(theFrame[0], theFrame[2], theFrame[1], theFrame[3]);
        }

        public static float revise(float v, float min, float max) {
            return Math.min(Math.max(v, min), max);
        }

        public static float[] cohesion(RectF win, float v) {
            return new float[]{
                    win.left + v, win.right - v,
                    win.top + v, win.bottom - v
            };
        }

        public static boolean isCohesionContains(RectF frame, float v, float x, float y) {
            return frame.left + v < x && frame.right - v > x
                    && frame.top + v < y && frame.bottom - v > y;
        }

        public static Anchor valueOf(int v) {
            Anchor[] values = values();
            for (Anchor anchor : values) {
                if (anchor.value == v) {
                    return anchor;
                }
            }
            return null;
        }
    }
}