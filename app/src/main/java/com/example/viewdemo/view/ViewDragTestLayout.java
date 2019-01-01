package com.example.viewdemo.view;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.viewdemo.LogUtils;

/**
 * Created by ly on 2018/12/23.
 */

public class ViewDragTestLayout extends LinearLayout {

    private View mDragView;
    private ViewDragHelper mDragHelper;

    private View mDragViewHor;
    private View mDragViewVer;
    private View mDragNoLimit;

    private Point point;

    public ViewDragTestLayout(Context context) {
        this(context, null);
    }

    public ViewDragTestLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragTestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 3) {
            throw new RuntimeException("child size must be 2");
        }


        mDragViewHor = getChildAt(0);
        mDragViewVer = getChildAt(1);
        mDragNoLimit = getChildAt(2);

    }


    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            mDragView = view == mDragViewHor ? mDragViewHor : view == mDragViewVer ? mDragViewVer : mDragNoLimit;
            point = new Point(mDragView.getLeft(), mDragView.getTop());
            return mDragView == view;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return super.clampViewPositionHorizontal(child, left, dx);
            LogUtils.e("clampViewPositionHorizontal  " + left + "  " + dx);
            if (mDragView == mDragViewVer) return point.x;

            int minLeft = 0;
            int maxLeft = getWidth() - child.getMeasuredWidth();

            return Math.min(Math.max(minLeft, left), maxLeft);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//            return super.clampViewPositionVertical(child, top, dy);
            LogUtils.e("clampViewPositionVertical  " + top + "  " + dy);
            if (mDragView == mDragViewHor) return point.y;

            int minTop = 0;
            int maxTop = getHeight() - child.getMeasuredHeight();
            return Math.min(Math.max(minTop, top), maxTop);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {

            mDragHelper.settleCapturedViewAt(point.x, point.y);
            invalidate();
//                mDragHelper.smoothSlideViewTo(mDragView, point.x, point.y);
//                invalidate();

            super.onViewReleased(releasedChild, xvel, yvel);

        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            LogUtils.e("onEdgeDragStarted  " + edgeFlags + "  " + pointerId);
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            LogUtils.e("onEdgeLock  " +edgeFlags);

            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            LogUtils.e("onEdgeTouched  " +edgeFlags+ "  " + pointerId);
            super.onEdgeTouched(edgeFlags, pointerId);
        }
    }


    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        mDragHelper.processTouchEvent(event);
        return true;
    }
}
