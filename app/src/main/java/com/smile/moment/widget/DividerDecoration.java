package com.smile.moment.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * A simple divider decoration with customizable colour, height, and left and right padding.
 * @author Smile Wei
 * @since 2016/04/16
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {

    private int height;
    private int paddingLeft;
    private int paddingRight;
    private Paint paint;

    private DividerDecoration(int height, int paddingLeft, int paddingRight, int color) {
        this.height = height;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            final int top = child.getBottom();
            final int bottom = top + height;

            int left = child.getLeft() + paddingLeft;
            int right = child.getRight() - paddingRight;

            c.save();
            c.drawRect(left, top, right, bottom, paint);
            c.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, height);
    }

    /**
     * A basic builder for divider decorations. The default builder creates a 1px thick black divider decoration.
     */
    public static class Builder {
        private Context context;
        private Resources resources;
        private int height;
        private int paddingLeft;
        private int paddingRight;
        private int color;

        public Builder(Context context) {
            this.context = context;
            resources = context.getResources();
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1f, context.getResources().getDisplayMetrics());
            paddingLeft = 0;
            paddingRight = 0;
            color = Color.BLACK;
        }

        /**
         * Set the divider height in pixels
         *
         * @param pixels height in pixels
         * @return the current instance of the Builder
         */
        public Builder setHeight(float pixels) {
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixels, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Set the divider height in dp
         *
         * @param resource height resource id
         * @return the current instance of the Builder
         */
        public Builder setHeight(@DimenRes int resource) {
            height = resources.getDimensionPixelSize(resource);
            return this;
        }

        /**
         * Sets both the left and right padding in pixels
         *
         * @param pixels padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setPadding(float pixels) {
            setLeftPadding(pixels);
            setRightPadding(pixels);

            return this;
        }

        /**
         * Sets the left and right padding in dp
         *
         * @param resource padding resource id
         * @return the current instance of the Builder
         */
        public Builder setPadding(@DimenRes int resource) {
            setLeftPadding(resource);
            setRightPadding(resource);
            return this;
        }

        /**
         * Sets the left padding in pixels
         *
         * @param pixelPadding left padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setLeftPadding(float pixelPadding) {
            paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the right padding in pixels
         *
         * @param pixelPadding right padding in pixels
         * @return the current instance of the Builder
         */
        public Builder setRightPadding(float pixelPadding) {
            paddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixelPadding, resources.getDisplayMetrics());

            return this;
        }

        /**
         * Sets the left padding in dp
         *
         * @param resource left padding resource id
         * @return the current instance of the Builder
         */
        public Builder setLeftPadding(@DimenRes int resource) {
            paddingLeft = resources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the right padding in dp
         *
         * @param resource right padding resource id
         * @return the current instance of the Builder
         */
        public Builder setRightPadding(@DimenRes int resource) {
            paddingRight = resources.getDimensionPixelSize(resource);

            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        public Builder setColorResource(@ColorRes int resource) {
            setColor(ContextCompat.getColor(context, resource));

            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param color the colour
         * @return the current instance of the Builder
         */
        public Builder setColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         *
         * @return a properly initialized DividerDecoration instance
         */
        public DividerDecoration build() {
            return new DividerDecoration(height, paddingLeft, paddingRight, color);
        }
    }
}
