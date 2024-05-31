package com.example.hmm;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Field extends SurfaceView implements SurfaceHolder.Callback {

    float cX, cY;
    int num_active;
    ArrayList<war_object> war_objects;
    int MAX_X = 5;
    int MAX_Y = 9;
    Sell [][]sells=new Sell[MAX_X][MAX_Y];

    public Field(Context context) {
        super(context);
        getHolder().addCallback(this);
  }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.rotate(90,canvas.getWidth()/2, canvas.getHeight()/2);
    }

    public int get_numX(float koorx) {
        return (int)((koorx + cX-1)/cX);
     }

    public int get_numY(float koory) {
        return (int)((koory + cY-1)/cY);
    }


    public ArrayList<Float> opr(int koorx, int koory) {
        ArrayList<Float> a = new ArrayList<Float>();
        a.add(cX * (int) (koorx / cX));
        a.add(cY * (int) (koory / cY));
        return a;
    }
    void set_active_sells() {
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                if ((int) ((Math.abs(sells[i][j].x - war_objects.get(num_active).kx) + 10) / cX) +
                        (int) ((Math.abs((sells[i][j].y - war_objects.get(num_active).ky) + 10) / cY))
                        < war_objects.get(num_active).speed)
                    sells[i][j].active = true;
            }
        }
    }

    boolean is_r;
    class MyThread extends Thread{
        SurfaceHolder holder;
        Field field;
        MyThread(Field field) {
            this.field=field;
        }

        @Override
        public void run() {
            Bitmap image;

            float maxX;
            float maxY;
            SurfaceHolder holder = field.getHolder();
            Paint p = new Paint();
            Resources resources = getResources();
          /*  MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource ("C:\\Users\\Ivan\\AndroidStudioProjects\\Hmm\\app\\src\\main\\res\\raw\\mus1.mp3");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.start();

           */
            Canvas canvas=holder.lockCanvas();
            //canvas.rotate(90);

            float csX = canvas.getWidth();
            float csY = canvas.getHeight();
            holder.unlockCanvasAndPost(canvas);
            cX = csX / MAX_X;
            cY = csY / MAX_Y;
            image = BitmapFactory.decodeResource(resources, R.drawable.grass);

            for(int i=0 ;i<MAX_X;i++){
                for(int j=0 ;j<MAX_Y;j++){
                    sells[i][j]=new Sell(cX*i,cY*j,cX,cY);
                }
            }

            war_objects = new ArrayList<war_object>();
            war_objects.add(new Spider(0, 0, resources,0));
            //sells[0][0].busy=true;
            war_objects.add(new Spider(cX*4, 0, resources,1));
            sells[4][0].busy=true;
            sells[4][0].warObject = war_objects.get(1);
            war_objects.add(new Worm(0, cY, resources,0));
            sells[0][1].busy=true;
            sells[0][1].warObject = war_objects.get(2);
            war_objects.add(new Worm(cX*4, cY, resources, 1));
            sells[4][1].busy=true;
            sells[4][1].warObject = war_objects.get(3);

            num_active = 0;
            war_objects.get(0).sost = 1;
/*            int r= (int) (war_objects.get(num_active).kx+war_objects.get(num_active).ky);
            for (int i=0;i<5;i++){
                for (int j=0;j<9;j++){
                    if (j<=war_objects.get(num_active).ky){
                        r++;
                    }
                    else {
                        r--;
                    }
                    if (r>=war_objects.get(num_active).speed){
                        sells[i][j].active=true;
                    }
                }
                if (i<=war_objects.get(0).ky){
                    r++;
                }
                else {
                    r--;
                }
                r-=war_objects.get(0).kx;
            }
*/
            set_active_sells();

            while (is_r){
                canvas=holder.lockCanvas();
                 int xx = canvas.getWidth(), yy = canvas.getHeight();
                canvas.drawBitmap(image, xx - image.getWidth(), yy - image.getHeight(), null);
                for (int i=0; i<MAX_X; i++) {
                    for (int j = 0; j < MAX_Y; j++) {
                        if((sells[i][j].active==true) && (sells[i][j].busy==true) &&
                                (sells[i][j].warObject.hero != war_objects.get(num_active).hero)){
                            sells[i][j].attak=true;
                        }
                        if((sells[i][j].active==true)  && (!((sells[i][j].busy==true) &&
                                (sells[i][j].warObject.hero == war_objects.get(num_active).hero)))){
                            sells[i][j].draw(canvas);
                        }
                    }
                }

                p.setColor(0xFFFFFFFF);
                maxX = cX;
                maxY = cY;
                for (int i = 0; i < MAX_Y; i++) {
                    canvas.drawLine(csX, maxY, 0, maxY, p);
                    maxY += cY;
                }
                for (int i = 0; i < MAX_X; i++) {
                    canvas.drawLine(maxX, csY, maxX, 0, p);
                    maxX += cX;
                }
                if (war_objects.get(num_active).sost == 3)
                {
                    for (int i=0; i<war_objects.size(); i++){
                        if (war_objects.get(i).hp <= 0)
                            war_objects.get(i).died = true;
                        }
                }
                else if (war_objects.get(num_active).sost == 0) {
                    sells[get_numX(war_objects.get(num_active).kx)][get_numY(war_objects.get(num_active).ky)].busy = true;
                    for (int i=0;i<war_objects.size();i++){
                        if (war_objects.get(i).hp <= 0){
                            sells[get_numX(war_objects.get(i).kx)][get_numY(war_objects.get(i).ky)].busy=false;
                            war_objects.remove(i);
                        }
                    }
                    num_active++;
                    if(num_active >= war_objects.size())
                        num_active = 0;
                    war_objects.get(num_active).sost = 1;
                    sells[get_numX(war_objects.get(num_active).kx)][get_numY(war_objects.get(num_active).ky)].busy = false;
                    set_active_sells();
                }

             //   if (war_objects.get(num_active).sost == 2){ //move

            //    }

                 for (int i = 0; i < war_objects.size(); i++)
                     war_objects.get(i).draw(canvas);

                 holder.unlockCanvasAndPost(canvas);
/*
                if ((xs<x-4 ) || (xs>x+4)){
                    xs += (xs<x)?5:-5;
                    //field.invalidate();
                }
                else if((ys<y-4 ) || (ys>y+4)){
                    ys += (ys<y)?5:-5;
                    //field.invalidate();
                }

 */
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        is_r=true;
        MyThread thread=new MyThread(this);
        thread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        is_r=false;
    }
    private int round(float a, float b){
        return (int)a/(int)b*(int)b;
    }

    public boolean onTouchEvent(MotionEvent event) {

        float x =  round(event.getX(),cX);
        float y =  round(event.getY(),cY);
        int numx =  get_numX(x);
        int numy =  get_numY(y);

        if ((war_objects.get(num_active).sost == 1) && (sells[numx][numy].active == true)){
            if(sells[numx][numy].busy == true)
            {
                if (war_objects.get(num_active).hero != sells[numx][numy].warObject.hero){
                    for (int i=0;i<MAX_X; i++) {
                        for (int j = 0; j < MAX_Y; j++) {
                            if (!(((i==numx)&&(Math.abs(j-numy)<=1)) ||
                                    ((j==numy)&&(Math.abs(i-numx)<=1))))
                                sells[i][j].active = false;
                        }
                    }
                    sells[numx][numy].warObject.hp -= war_objects.get(num_active).damage;
                    if(sells[numx][numy].warObject.hp <= 0)
                        war_objects.get(num_active).kill = true;
                }
            }
            else {    //active
                war_objects.get(num_active).tx = x;
                war_objects.get(num_active).ty = y;
                war_objects.get(num_active).sost = 2; //moving
                // sells[get_numX(war_objects.get(num_active).kx)][get_numY(war_objects.get(num_active).ky)].busy == false
                sells[numx][numy].busy = true;
                sells[numx][numy].warObject = war_objects.get(num_active);
                for (int i = 0; i < MAX_X; i++) {
                    for (int j = 0; j < MAX_Y; j++) {
                        sells[i][j].active = false;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

}

