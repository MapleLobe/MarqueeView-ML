package com.a95.utf_8.super_marquee_textview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * Created by zouyifeng on 2016/12/11.
 * 00:41
 */

/**
 * 纵向跑马灯 用于
 */
public class MarqueeView extends View {


    /**
     * 画笔对象
     **/
    private Paint paint = new Paint();

    /**
     * 文本高度
     **/
    private int textHeight;

    /**
     * 文本偏移量
     */
    private int offsetWidth;

    /**
     * 文本颜色
     */
    private int textColor = Color.BLACK;

    /**
     * 文本大小
     */
    private float textSize;

    /**
     * 判断滑动状态
     **/
    private boolean roll;

    /**
     * 判断是否在滚动中
     */
    private boolean isRoll;

    /**
     * String集合
     */
    private ArrayList<String> contents;

    /**
     * 隐藏文本的索引
     */
    private int position = 2;

    /**
     * 执行动画的时间
     * 默认一秒
     */
    private int duration = 1000;

    /**
     * 第一第二文本
     **/
    private String firstText;
    private String secondText;

    /**
     * 执行动画进行滚动
     **/
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            isRoll = true;

            //开始滚动动画
            ValueAnimator animator = ValueAnimator.ofInt(0, -getHeight());
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();

                    //判断当前显示的文本进行滚动
                    if (roll) {
                        firstTextHeight = invisibleHeight + value;
                        secondTextHeight = centerHeight + value;
                    } else {
                        firstTextHeight = centerHeight + value;
                        secondTextHeight = invisibleHeight + value;
                    }


                    invalidate();
                }
            });

            //执行结束进行重绘制
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    if (position >= contents.size()) {
                        position = 0;
                    }

                    if (roll) {
                        secondTextHeight = invisibleHeight;
                        secondText = contents.get(position);
                    } else {
                        firstTextHeight = invisibleHeight;
                        firstText = contents.get(position);
                    }

                    position++;

                    roll = !roll;

                    invalidate();

                    isRoll = false;
                }
            });

            animator.start();

            handler.sendEmptyMessageDelayed(0, 3000);

        }
    };


    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if (!isRoll) {
                    if (onChangeTextListener != null) {
                        int position = this.position - 2;

                        System.out.println(this.position);

                        if (this.position == 1) {
                            position = contents.size() - 1;
                        } else if (this.position == 2) {
                            position = 0;
                        }

                        onChangeTextListener.changerText(position);
                    }
                }

                break;

        }

        return true;
    }


    /**
     * 正中心的高度
     **/
    private int centerHeight;

    /**
     * 隐藏高度
     **/
    private int invisibleHeight;

    /**
     * 第一个文本的高度
     **/
    private int firstTextHeight;

    /**
     * 第二个文本的高度
     **/
    private int secondTextHeight;

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawText(firstText, getWidth() / 2 + offsetWidth, firstTextHeight, paint);
        if (contents.size() > 1) {
            canvas.drawText(secondText, getWidth() / 2 + offsetWidth, secondTextHeight, paint);
        }

        super.onDraw(canvas);
    }

    /**
     * 获取文本高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {

        Rect bounds = new Rect();

        paint.getTextBounds(text, 0, text.length(), bounds);

        return bounds.height();
    }

    /**
     * 设置滚动内容
     *
     * @param contents
     */
    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }


    /**
     * 获取正中心的高度
     *
     * @return
     */
    private int computeCenterHeight() {
        return getHeight() / 2 + textHeight / 2;
    }

    /**
     * 获取隐藏的高度
     *
     * @return
     */
    private int computeInvisibleHeight() {
        return getHeight() / 2 + textHeight / 2 + getHeight();
    }

    /**
     * 开始滚动
     */
    public void start() {
        initParams();

        if (contents.size() > 1) {
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    /**
     * 停止滚动
     */
    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        if (contents == null) {
            throw new IllegalStateException("contents is null");
        }else if(contents.isEmpty()){
            throw new IllegalStateException("At least one element");
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);

                paint.setColor(textColor);
                paint.setTextAlign(Paint.Align.CENTER);

                //设置文字大小
                System.out.println("textSize:" + textSize);
                paint.setTextSize(textSize > 0 ? textSize : getHeight() / 2);

                //初始化画笔和初始化文本高度
                textHeight = getTextHeight(contents.get(0));

                secondTextHeight = getHeight() / 2 + textHeight / 2 + getHeight();

                firstTextHeight = getHeight() / 2 + textHeight / 2;

                centerHeight = computeCenterHeight();

                invisibleHeight = computeInvisibleHeight();

                firstText = contents.get(0);

                //当集合内的元素个只有一个的时候不绘制第二个文本
                if (contents.size() > 1) {
                    secondText = contents.get(1);
                }

                invalidate();
            }
        });
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置文字颜色
     */
    public void setTextColor(int color) {
        this.textColor = color;
        paint.setColor(textColor);
    }

    /**
     * 设置偏移量 基于android坐标系
     *
     * @param offsetWidth
     */
    public void setOffsetWidth(int offsetWidth) {
        this.offsetWidth = offsetWidth;
    }

    /**
     * 设置动画执行时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 当文字切换完成时调用的监听
     */
    private OnChangeTextListener onChangeTextListener;

    public void setOnChangeTextListener(OnChangeTextListener onChangeTextListener) {
        this.onChangeTextListener = onChangeTextListener;
    }

    public interface OnChangeTextListener {
        void changerText(int position);
    }
}
