package com.sang.ailatrieuphu.presentation.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class StrokedTextView extends androidx.appcompat.widget.AppCompatTextView {
    public StrokedTextView(Context context) {
        super(context);
    }

    public StrokedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrokedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onDraw(Canvas canvas){
        Paint p = getPaint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(8);
        setTextColor(Color.WHITE);
        p.setStrokeCap(Paint.Cap.ROUND);
        //draw the fill part of text
        super.onDraw(canvas);
        //save the text color
        int currentTextColor = getCurrentTextColor();
        //set paint to stroke mode and specify
        //stroke color and width
        p.setStyle(Paint.Style.FILL);
        setTextColor(Color.BLACK);
        //draw text stroke
        super.onDraw(canvas);
        //revert the color back to the one
        //initially specified

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void drawText(Canvas canvas){
        Rect bound = new Rect();
        int lineNumber = 0;
        getPaint().getTextBounds(getText(),0, getText().length(), bound);

        for(int i = 0; i <= getText().length() ; i+=40){

            if ((getText().length() - i) < 40){
                lineNumber += 1;
                drawStrokedText(canvas, (String) getText().subSequence(i, getText().length()), lineNumber, bound.height());
            }else {
                lineNumber = i/40;
                drawStrokedText(canvas, (String) getText().subSequence(i, i+40), lineNumber, bound.height());
            }
        }

        setHeight(bound.height() * (lineNumber + 3) );
    }

    private void drawStrokedText(Canvas canvas, String text, int lineNumber, int textHeight){
        TextPaint stkPaint = new TextPaint();
        stkPaint.setStyle(Paint.Style.STROKE);
        if(getTextSize() >= 20){
            stkPaint.setStrokeWidth(8);
        }else {
            stkPaint.setStrokeWidth(2);
        }

        stkPaint.setTextSize(getTextSize());
        stkPaint.setColor(Color.WHITE);
        stkPaint.setStrokeCap(Paint.Cap.ROUND);
        stkPaint.setTextAlign(Paint.Align.CENTER);

        float x = getWidth()/ 2;
        float y = (canvas.getHeight() - getPaint().descent() - getPaint().ascent()) / 2 + lineNumber*textHeight;

        canvas.drawText(text, x, y, stkPaint);

        TextPaint fillPaint = new TextPaint();
        fillPaint.setColor(Color.BLACK);
        fillPaint.setTextSize(getTextSize());
        fillPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, x, y, fillPaint);

    }
}
