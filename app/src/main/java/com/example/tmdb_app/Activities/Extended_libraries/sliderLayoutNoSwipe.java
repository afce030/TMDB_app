package com.example.tmdb_app.Activities.Extended_libraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daimajia.slider.library.SliderLayout;

public class sliderLayoutNoSwipe extends SliderLayout {

    public sliderLayoutNoSwipe(Context context) {
        super(context);
    }

    public sliderLayoutNoSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public sliderLayoutNoSwipe(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
