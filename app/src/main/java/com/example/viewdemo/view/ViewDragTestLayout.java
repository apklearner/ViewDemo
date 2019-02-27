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

import com.example.viewdemo.utils.DensityUtils;
import com.r0adkll.slidr.model.SlidrInterface;

public class ViewDragTestLayout extends FrameLayout {

    private final int NONE = -1;
    private View dragView;
    private ViewDragHelper viewDragHelper;
    private int mWidth;
    private int mHeight;
    private int mChildWidth;
    private int mChildHeight;
    private boolean onDrag = true;


    private int lastChildX;
    private int lastChildY;

    private int topFinalOffset;
    private int bottomFinalOffset;
    private int leftFinalOffset;
    private int rightFinalOffset;


    private int leftDragOffset = NONE;
    private int rightDragOffset = NONE;
    private int topDragOffset = NONE;
    private int bottomDragOffset = NONE;


    public ViewDragTestLayout(@NonNull Context context) {
        this(context, null);
    }

    public ViewDragTestLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragTestLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new MyDragCallBack());
    }

    public void setBottomDragOffset(int dpValue) {
        Log.e("1234", "setBottomDragOffset");

        this.bottomDragOffset = DensityUtils.dp2px(getContext(), dpValue);
    }

    public void setFinalOffsets(int value) {
        setFinalOffsets(value, value, value, value);
    }

    public void setFinalOffsets(int left, int top, int right, int bottom) {
        setLeftFinalOffset(left);
        setTopFinalOffset(top);
        setRightFinalOffset(right);
        setBottomFinalOffset(bottom);
    }

    public void setLeftFinalOffset(int dpValue) {
        this.leftFinalOffset = DensityUtils.dp2px(getContext(), dpValue);
    }

    public void setRightFinalOffset(int dpValue) {
        this.rightFinalOffset = DensityUtils.dp2px(getContext(), dpValue);
    }

    public void setBottomFinalOffset(int dpValue) {
        this.bottomFinalOffset = DensityUtils.dp2px(getContext(), dpValue);
    }

    public void setTopFinalOffset(int dpValue) {
        this.topFinalOffset = DensityUtils.dp2px(getContext(), dpValue);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mChildHeight = dragView.getMeasuredHeight();
        mChildWidth = dragView.getMeasuredWidth();

        leftDragOffset = leftDragOffset == NONE ? mChildWidth / 2 : leftDragOffset;
        rightDragOffset = rightDragOffset == NONE ? mChildWidth / 2 : rightDragOffset;
        topDragOffset = topDragOffset == NONE ? mChildHeight / 2 : topDragOffset;
        bottomDragOffset = bottomDragOffset == NONE ? mChildHeight / 2 : bottomDragOffset;


        Log.e("1234", mWidth + "-" + mHeight + "   " + mChildWidth + "-" + mChildHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (lastChildX == 0 && lastChildY == 0) {
            lastChildX = mWidth - mChildWidth - rightFinalOffset;
            lastChildY = mHeight - mChildHeight - bottomFinalOffset;
        }
        dragView.layout(lastChildX, lastChildY, lastChildX + mChildWidth, lastChildY + mChildHeight);

        Log.e("1234", "onlayout   " + lastChildX + "   " + lastChildY);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        if (getChildCount() > 1) {
//            throw new RuntimeException("child size must be 1");
//        }
        dragView = getChildAt(0);
        dragView.bringToFront();
    }


    private class MyDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return dragView == view;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return clamp(left, -mChildWidth / 2, mWidth - mChildWidth / 2);

            leftDragOffset = leftDragOffset > mChildWidth ? mChildWidth : leftDragOffset;
            rightDragOffset = rightDragOffset > mChildWidth ? mChildWidth : rightDragOffset;

            return clamp(left, -leftDragOffset, mWidth - mChildWidth + rightDragOffset);

        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//            return clamp(top, -mChildHeight / 2, mHeight - mChildHeight / 2);
            topDragOffset = topDragOffset > mChildHeight ? mChildHeight : topDragOffset;
            bottomDragOffset = bottomDragOffset > mChildHeight ? mChildHeight : bottomDragOffset;

            return clamp(top, -topDragOffset, mHeight - mChildHeight + bottomDragOffset);

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

            int finalTop = dragView.getTop() <= topFinalOffset ? topFinalOffset : dragView.getBottom() >= mHeight - bottomFinalOffset ? mHeight - dragView.getMeasuredHeight() - bottomFinalOffset : dragView.getTop();
            lastChildY = finalTop;
            if (Math.abs(dragView.getLeft()) <= (getMeasuredWidth() - dragView.getMeasuredWidth()) / 2) {
                lastChildX = leftFinalOffset;
                viewDragHelper.settleCapturedViewAt(lastChildX, finalTop);
            } else {
                lastChildX = getMeasuredWidth() - dragView.getMeasuredWidth() - rightFinalOffset;
                viewDragHelper.settleCapturedViewAt(lastChildX,
                        finalTop);
            }
//                viewDragHelper.smoothSlideViewTo(expandView, parentWidth, parentWidth + expandWidth);
            invalidate();
            onDrag = false;
//            if (slidrInterface != null)
//                slidrInterface.lock();

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
