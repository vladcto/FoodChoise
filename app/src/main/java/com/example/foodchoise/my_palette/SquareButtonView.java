package com.example.foodchoise.my_palette;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

public class SquareButtonView extends AppCompatImageButton {
    public SquareButtonView(Context context) {
        super(context);
    }

    public SquareButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}