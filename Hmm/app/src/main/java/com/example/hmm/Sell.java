package com.example.hmm;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Sell {

    int ix;
    int iy;
    boolean active; //0-empty, 1-active,
    boolean attak;
    boolean busy;// 2-busy
    float x;
    float y;
    float cX;
    float cY;
    war_object warObject;
    Paint p;
    Sell(float x,float y,float cX,float cY){
        active=false;
        busy=false;
        this.x=x;
        this.y=y;
        this.cX=cX;
        this.cY=cY;
        p=new Paint();
        p.setColor(0x400000FF);
        attak=false;
    }

    public void draw (Canvas canvas) {
        if (attak==true){
            p.setColor(0x40FF0000);
        }
        canvas.drawRect(x, y, x+cX, y+ cY, p);
        p.setColor(0x400000FF);
        attak=false;
    }
}
