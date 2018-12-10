package com.example.viewdemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AutoFlowLayout extends ViewGroup {

    private int DefaultVerticalMargin = 20;
    private int DefaultHorMargin = 80;
    private List<Rect> childRects = new ArrayList<>();


    private int totalHeight;

    public AutoFlowLayout(Context context) {
        super(context);
    }

    public AutoFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int resTotalHeight = 0;

        int childTopIdex = getPaddingTop();
        int childLeftIndex = 0;


        childRects.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childReqWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childReqHeight = child.getMeasuredHeight();
            if (resTotalHeight == 0)
                resTotalHeight = childReqHeight + getPaddingTop() + getPaddingBottom();

            if (childLeftIndex == 0)
                childLeftIndex = getPaddingLeft() + lp.leftMargin;


            Log.e("1234","child width  "  + child.getMeasuredWidth());

            if (childLeftIndex + childReqWidth > parentWidthSize - getPaddingLeft() - getPaddingRight()) {
                childLeftIndex = getPaddingLeft() + lp.leftMargin;
                childTopIdex += childReqHeight + DefaultVerticalMargin;

//                childRects.add(new Rect(childLeftIndex, childTopIdex, childLeftIndex + child.getMeasuredWidth(), childTopIdex + child.getMeasuredHeight()));

                resTotalHeight += childReqHeight + DefaultVerticalMargin;
            }
            Log.e("1234","childLeftIndex    "  + childLeftIndex);
            childRects.add(new Rect(childLeftIndex, childTopIdex, childLeftIndex + child.getMeasuredWidth(), childTopIdex + child.getMeasuredHeight()));

            childLeftIndex += childReqWidth + lp.leftMargin + DefaultHorMargin;

        }


        setMeasuredDimension(parentWidthSize, parentHeightMode == MeasureSpec.EXACTLY ? parentHeightSize : resTotalHeight + getPaddingTop() + getPaddingBottom());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            Rect rect = childRects.get(i);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
        }

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }


}
