package com.example.hmm;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class war_object {
    int hero;
    int damage;
    int hill;
    int speed;
    int hp;
    int ckolko;
   // boolean active;

  //  float x;
 //   float y;

    float kx;
    float ky;
    float tx;
    float ty;
    Bitmap bitmap;
//    Paint pa;
    int sost; //0-empty, 1-active, 2-move, 3 - kill
   // public void draw (Canvas canvas, float x, float y);
    boolean died;
    boolean kill;
    private int count;
    private int vis;

    Bitmap image_rip;
    war_object object;
    public war_object(float kx, float ky, Resources resources, int hero) {
        this.kx = this.tx = kx;
        this.ky = this.ty = ky;
      //  this.bitmap = bitmap;
        this.hero=hero;
       // Matrix mat = new Matrix();
       // mat.postScale()
        sost = 0;
        count = 0;
        vis = 0;
        died = false;
        kill = false;
        image_rip =  BitmapFactory.decodeResource(resources, R.drawable.rip);
        //  sost = 1;
        //     pa = new Paint();
    }
    public void move()
    {

    }
    public void draw (Canvas canvas) {

     //   Paint pa = new Paint();
        if(sost == 0)
        {
            count = 0;
            vis = 0;
         } else if(sost == 2) { //mooving
            count = 0;
            move();
        } else if (sost == 1) {
            count++;
            if (count > 10) {
                vis = 1-vis;
                count = 0;
            }
        }else if(sost == 3){
            count++;
            if (count >= 30) {
                sost = 0;
                kill = false;
            }

        }

        if(died)
            canvas.drawBitmap(image_rip, kx, ky, null);
        else if((sost == 0) || (sost == 2) || (vis == 1) || (sost == 3))
            canvas.drawBitmap(bitmap, kx, ky, null);
     }
}
