package com.example.viewdemo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.viewdemo.LogUtils;


/**
 * Created by ly on 2018/12/31.
 */

public class SlideMenu extends FrameLayout {
    private View contentView;
    private View expandView;
    private ViewDragHelper viewDragHelper;
    private int parentWidth;
    private int parentHeight;
    private int expandWidth;

    public SlideMenu(@NonNull Context context) {
        this(context, null);
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new MyViewDragCallBack());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        expandWidth = expandView.getMeasuredWidth();
        parentHeight = getMeasuredHeight();
        parentWidth = getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        contentView.layout(0, 0, parentWidth, parentHeight);
        expandView.layout(parentWidth, 0, parentWidth + expandWidth, parentHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("child size must be 2");
        }
        contentView = getChildAt(0);
        expandView = getChildAt(1);
        contentView.bringToFront();
    }

    private class MyViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view == contentView;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return super.clampViewPositionHorizontal(child, left, dx);
            if (left >= 0) return 0;
            if (Math.abs(left) >= expandWidth) return -expandWidth;
            return left;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
//            super.onViewPositionChanged(changedView, left, top, dx, dy);
            LogUtils.e("onViewPositionChanged left  " + left + "   " + expandWidth);
            if (changedView == contentView) {
                expandView.layout(left + parentWidth, 0, left + parentWidth + expandWidth, parentHeight);
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == contentView) {

                if (Math.abs(contentView.getLeft()) <= expandWidth / 2) {
                    requestLayout();
                    viewDragHelper.settleCapturedViewAt(0, 0);
                } else if (Math.abs(contentView.getLeft()) < expandWidth) {
                    requestLayout();
                    viewDragHelper.settleCapturedViewAt(-expandWidth, 0);
                }
//                viewDragHelper.smoothSlideViewTo(expandView, parentWidth, parentWidth + expandWidth);
                invalidate();
            }
        }
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

}
