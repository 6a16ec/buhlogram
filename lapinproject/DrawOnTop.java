package com.flappy.user.finalbeta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by user on 26.05.18.
 */

public class DrawOnTop extends View {


    UsualScreen u;
    UsualScreen usualScreen;
    UsualScreen planeInfo;

    public DrawOnTop(Context context) {
        super(context);
        usualScreen = new UsualScreen();
        //planeInfo = new PlaneInfo();
        u =  new UsualScreen();
    }

    /*public void setPercentCoordinate(double width, double height){
        x = getWidth() * width ;
        y = getHeight() * height;
        beDrawn = true;
    }*/

    @Override
    protected void onDraw(Canvas canvas) {


        //canvas.drawText(stroka,10,10,paint);
        u.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        u.performClick(event.getX(),event.getY());

        return false;

    }


    ArrayList<Plane> planes = new ArrayList<Plane>();




    class UsualScreen{

        //double x[] = {100};
        //double y[] = {100};
        //!!!!boolean beDrawn = false;
        private Bitmap plane;
        protected Bitmap foninfo;

        UsualScreen(){
        plane  = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        plane = Bitmap.createScaledBitmap(plane, 111, 57, false);
        foninfo = BitmapFactory.decodeResource(getResources(), R.drawable.foninfo);
        foninfo = Bitmap.createScaledBitmap(foninfo,592, 390,false);
        }

        void performClick(float xScreen, float yScreen) {
            for(int i=0;i<planes.size();i++)
                if((planes.get(i).percentX- plane.getWidth()/2)<xScreen && xScreen < (planes.get(i).percentX + plane.getWidth()/2) && (planes.get(i).percentY- plane.getHeight()/2)<yScreen && yScreen <(planes.get(i).percentY + plane.getHeight()/2)){
                u = new PlaneInfo(i);
                }
        }


        void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            for(int i=0;i<planes.size();i++) {
                //canvas.drawCircle((float) x, (float) y, 30, paint);
                canvas.drawBitmap(plane, (float) planes.get(i).percentX - plane.getWidth()/2, (float) planes.get(i).percentY-plane.getHeight()/2, paint);

            }
        }
    }

    class PlaneInfo extends UsualScreen{
        int i;


        PlaneInfo(int i){
            super();
            this.i = i;
        }

        @Override
        void performClick(float x, float y) {
            if(x<(getWidth()-foninfo.getWidth())/2 || y<(getHeight()-foninfo.getHeight())/2 || x>(getWidth()+foninfo.getWidth())/2 || y>(getHeight()+foninfo.getHeight())/2)
                u = usualScreen;

        }

        @Override
        void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawBitmap(foninfo,(getWidth()-foninfo.getWidth())/2,(getHeight()-foninfo.getHeight())/2,paint);
            Paint textPaint = new Paint();
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(40);
            textPaint.setColor(Color.WHITE);

            int width = getWidth()/2;
            int height = (getHeight()-foninfo.getHeight())/2+ 110;


            int xPos = (width);
            int yPos = (int) ((height) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;

            canvas.drawText("company: "+planes.get(i).company, xPos, yPos, textPaint);
            yPos+=60;
            canvas.drawText("airport from: "+planes.get(i).airport_from, xPos, yPos, textPaint);
            yPos+=60;
            canvas.drawText("airport to: "+planes.get(i).airport_to, xPos, yPos, textPaint);
            yPos+=60;
            canvas.drawText("model: "+planes.get(i).model, xPos, yPos, textPaint);


        }

    }



}

class Plane{
    double percentX;
    double percentY;
    String model;
    String airport_from;
    String airport_to;
    String company;
    boolean beDrawn;
    Plane(double percentX,double percentY,String model,String airport_from,String airport_to,String company,boolean beDrawn){
        this.percentX = percentX ;
        this.percentY = percentY;
        this.model = model;
        this.airport_from = airport_from;
        this.airport_to=airport_to;
        this.company = company;
        this.beDrawn = beDrawn;
    }

}
