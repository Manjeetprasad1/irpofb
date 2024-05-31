package com.root.irpofb.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.root.irpofb.R;


public class CircleDrawable extends Drawable {
    private final Paint circlePaint;
    private final Paint textPaint;
    private final String text;

    public CircleDrawable(Context context, String text, Float textSize) {
        this.text = text;
        // Circle paint settings
        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(context, R.color.black_text_color)); // Set the desired background color
        circlePaint.setAntiAlias(true);

        // Text paint settings
        textPaint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
        textPaint.setColor(ContextCompat.getColor(context, R.color.brand_color));
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        // Get the dimensions of the drawable
        int width = getBounds().width();
        int height = getBounds().height();
        // Draw the circle
        float radius = Math.min(width, height) / 2f;
        float centerX = width / 2f;
        float centerY = height / 2f;
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
        // Draw the text
        float textOffsetY = (textPaint.descent() + textPaint.ascent()) / 2f;
        canvas.drawText(text, centerX, centerY - textOffsetY, textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        // Not needed for this example
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // Not needed for this example
    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        // Not needed for this example
        return 0;
    }
}