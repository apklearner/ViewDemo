package com.example.viewdemo.view;

import android.content.Context;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * Created by ly on 2018/11/15.
 * 思路是 下层的容器大小尺寸不变 ，动态比例改变父容器的高度
 * 需要判比 上层容器和父容器的尺寸比例
 */

public class BehindGroupView extends FrameLayout {

    private final String TAG = BehindGroupView.class.getSimpleName();

    public interface OnRangeChangeListener {
        void onRangeChanged(int range, int maxRange);
    }


    private View frontView;
    private View behindView;
    private Scroller mScroller;
    private float frontScale = 0.5f; // 0 - ? 以自身为比例，增大相应的尺寸;比如 0.6就是从原先的 1 开始， 结束时大小是 1.6

    private int behindHeight;
    private int frontHeight;
    private int parentHeight;
    private int parentWidth;

    private int duration = 650;
    private boolean isClosed = true;
    private boolean isFinish = true;
    private final int maxRange = 650;

    private OnRangeChangeListener listener;

    public void setOnRangeChangeListener(OnRangeChangeListener listener) {
        this.listener = listener;
    }

    public BehindGroupView(Context context) {
        this(context, null);
    }

    public BehindGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BehindGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() != 2) {
            throw new RuntimeException("child size max be 2 !");
        } else {
            frontView = getChildAt(0);
            behindView = getChildAt(1);
            frontView.bringToFront();
        }
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(frontView, widthMeasureSpec, heightMeasureSpec);
        measureChild(behindView, widthMeasureSpec, heightMeasureSpec);

        if (frontHeight == 0) {
            frontHeight = frontView.getMeasuredHeight();
            parentHeight = frontHeight;
        }
        if (behindHeight == 0) {
            behindHeight = behindView.getMeasuredHeight();
            LayoutParams params = (LayoutParams) behindView.getLayoutParams();
            behindHeight += params.topMargin;
            behindHeight += params.bottomMargin;
        }

        if (parentWidth == 0)
            parentWidth = getContext().getResources().getDisplayMetrics().widthPixels;

        setMeasuredDimension(parentWidth, parentHeight);


        Log.d(TAG, "w f b p " + getMeasuredWidth() + "  " + frontHeight + "  " + behindHeight + "   " + parentHeight);
    }

    private void caculateParentHeight(int curValue, int maxValue) {
        parentHeight = (int) (((float) curValue / (float) maxValue) * (behindHeight + frontHeight * frontScale) + frontHeight);
    }


    private int lastY;
    private Runnable taskRunner = new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                int curY = mScroller.getCurrY();
                if (lastY == curY) {
                    post(this);
                    return;
                }

                if (listener != null) listener.onRangeChanged(curY, maxRange);
                caculateParentHeight(curY, maxRange);
                float stepRange = (float) curY / (float) maxRange;
                int extraFrontHeight = (int) (frontHeight * stepRange * frontScale);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) behindView.getLayoutParams();
                params.topMargin = (int) (stepRange * frontHeight + extraFrontHeight);
                behindView.setLayoutParams(params);


                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) frontView.getLayoutParams();
                params1.height = frontHeight + extraFrontHeight;
                frontView.setLayoutParams(params1);
                requestLayout();
                lastY = curY;


                post(this);
            } else {
                isClosed = !isClosed;
                isFinish = true;
            }
        }
    };

    public void toggleRunner() {
        if (!isFinish) return;
        isFinish = false;

        lastY = 0;
        if (isClosed) {
            openRunner();
        } else {
            closeRunner();
        }
    }

    public boolean isOpen() {
        return !isClosed;
    }

    private void openRunner() {
        mScroller.startScroll(0, 0, 0, maxRange, duration);
        post(taskRunner);
    }

    private void closeRunner() {
        mScroller.startScroll(0, maxRange, 0, -maxRange, duration);
        post(taskRunner);
    }

}
