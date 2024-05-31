package com.example.hmm;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Spider extends war_object{
    Bitmap[] bitmap_up;
    Bitmap[] bitmap_down;
    Bitmap[] bitmap_left;
    Bitmap[] bitmap_right;

    Bitmap bitmap_main;
    private int count;

    public Spider(float kx, float ky, Resources resources, int hero) {
        super(kx, ky, resources, hero);
        if(hero == 0)
            bitmap_main = bitmap = BitmapFactory.decodeResource(resources, R.drawable.spider);
        else
            bitmap_main =bitmap = BitmapFactory.decodeResource(resources, R.drawable.spider1);
        speed=4;
        hp=75;
        damage=25;
        bitmap_up = new Bitmap[2];
        bitmap_down = new Bitmap[2];
        bitmap_left = new Bitmap[2];
        bitmap_right = new Bitmap[2];
        bitmap_up[0] = BitmapFactory.decodeResource(resources, R.drawable.spider_u1);
        bitmap_up[1] = BitmapFactory.decodeResource(resources, R.drawable.spider_u2);
        bitmap_down[0] = BitmapFactory.decodeResource(resources, R.drawable.spider_d1);
        bitmap_down[1] = BitmapFactory.decodeResource(resources, R.drawable.spider_d2);
        bitmap_right[0] = BitmapFactory.decodeResource(resources, R.drawable.spider_r1);
        bitmap_right[1] = BitmapFactory.decodeResource(resources, R.drawable.spider_r2);
        bitmap_left[0] = BitmapFactory.decodeResource(resources, R.drawable.spider_l1);
        bitmap_left[1] = BitmapFactory.decodeResource(resources, R.drawable.spider_l2);
        count = 0;

    }


    public void move() {
        if ((kx < tx - 5) || (kx > tx + 5)) {
            if(kx < tx){
                bitmap = bitmap_right[count];
                kx += 8;
            }
            else{
                bitmap = bitmap_left[count];
                kx -= 8;
            }
            count = 1- count;
           // kx += (kx < tx) ? 5 : -5;
            //field.invalidate();
        } else if ((ky < ty - 5) || (ky > ty + 5)) {
            if(ky < ty){
                bitmap = bitmap_down[count];
                ky += 8;
            }
            else{
                bitmap = bitmap_up[count];
                ky -= 8;
            }
            count = 1- count;
           // ky += (ky < ty) ? 5 : -5;
            //field.invalidate();
        } else {
            bitmap = bitmap_main;
            kx = tx;
            ky = ty;
            if (kill)
                sost = 3;
            else
                sost = 0;
        }
    }
/*
    public void draw (Canvas canvas, float x, float y) {
       // Paint pa = new Paint();
        pa.setColor(0xFFFFFFFF);
        canvas.drawBitmap(bitmap, x, y, pa);
    }

 */

/*   public Rect getBoundingBoxRect () {
       return new Rect((int)x+padding, (int)y+padding, (int)(x + frameWidth - 2 * padding), (int)(y + frameHeight - 2 * padding));
    }

 */
}
