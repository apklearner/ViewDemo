package com.example.viewdemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class FloatLayout extends FrameLayout {

    private View dragView;
    private ViewDragHelper viewDragHelper;
    private int mWidth;
    private int mHeight;
    private int mChildWidth;
    private int mChildHeight;
    private boolean onDrag = true;

    public FloatLayout(@NonNull Context context) {
        this(context, null);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new MyDragCallBack());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mChildHeight = dragView.getMeasuredHeight();
        mChildWidth = dragView.getMeasuredWidth();
        Log.e("1234", mWidth + "-" + mHeight + "   " + mChildWidth + "-" + mChildHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        dragView.layout(mWidth - mChildWidth, mHeight - mChildHeight, mWidth, mHeight);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new RuntimeException("child size must be 1");
        }
        dragView = getChildAt(0);
    }


    private class MyDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            Log.e("1234", "tryCaptureView  " + onDrag);
            return dragView == view;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return clamp(left, -mChildWidth / 2, mWidth - mChildWidth / 2);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return clamp(top, -mChildHeight / 2, mHeight - mChildHeight / 2);
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
//            return super.getViewVerticalDragRange(child);
            return mHeight - mChildHeight;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
//            return super.getViewHorizontalDragRange(child);
            return mWidth - mChildWidth;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            int finalTop = dragView.getTop() <= 0 ? 0 : dragView.getBottom() >= mHeight ? mHeight - dragView.getMeasuredHeight() : dragView.getTop();

            if (Math.abs(dragView.getLeft()) <= getMeasuredWidth() / 2) {
                viewDragHelper.settleCapturedViewAt(0, finalTop);
            } else {
//                requestLayout();
                viewDragHelper.settleCapturedViewAt(getMeasuredWidth() - dragView.getMeasuredWidth(),
                        finalTop);
            }
//                viewDragHelper.smoothSlideViewTo(expandView, parentWidth, parentWidth + expandWidth);
            invalidate();
            onDrag = false;
//            if (slidrInterface != null)
//                slidrInterface.lock();
            Log.e("1234", "onViewReleased ");

        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }


    private Rect mRect = new Rect();
    private SlidrInterface slidrInterface;

    public void attachSlidr(SlidrInterface slidrInterface) {
        this.slidrInterface = slidrInterface;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
//        return viewDragHelper.shouldInterceptTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                dragView.getHitRect(mRect);
                onDrag = mRect.contains(x, y);
                //TODO
                if (slidrInterface != null && onDrag)
                    slidrInterface.unlock();
                break;
        }

        if (onDrag) return viewDragHelper.shouldInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if (onDrag) {
            viewDragHelper.processTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
