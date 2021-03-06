package com.example.gilcunningham.airlines.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gil.cunningham on 9/21/2016.
 * Implementation for ViewPager with ability to disable paging
 */
public class AirlinesViewPager extends ViewPager {

    private boolean mEnabled = true;

    public AirlinesViewPager(Context context) {
        super(context);
    }

    public AirlinesViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return mEnabled && super.canScrollHorizontally(direction);
    }

}
