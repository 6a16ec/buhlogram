package com.flappy.user.finalbeta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by user on 26.05.18.
 */

public class DrawOnTop extends View {

    double x = 0;
    double y = 0;
    boolean beDrawn = false;

    public DrawOnTop(Context context) {
        super(context);

    }

    public void setPercentCoordinate(double width, double height){
        x = getWidth() * width ;
        y = getHeight() * height;
        beDrawn = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        if(beDrawn) {
            canvas.drawCircle((float) x, (float) y, 30, paint);
            beDrawn = false;
        }

        super.onDraw(canvas);
    }

}