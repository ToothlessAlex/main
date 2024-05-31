package com.example.hmm;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Worm extends war_object{
    public Worm(float kx, float ky, Resources resources, int hero) {
        super(kx, ky, resources, hero);
        if(hero == 0)
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.worm);
        else
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.worm1);
        speed=10;
        hp=45;
        damage=15;
    }

    public void move(){
        kx = tx;
        ky = ty;
        if (kill)
            sost = 3;
        else
            sost = 0;
    }
}
