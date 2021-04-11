package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

//TODO future releases would have a class for trash and class for trash can
//  THIS IS A DEMO

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap apple = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
    Bitmap bottle = BitmapFactory.decodeResource(getResources(),R.drawable.bottle);
    Bitmap plane = BitmapFactory.decodeResource(getResources(),R.drawable.plane1);
    Bitmap plastic = BitmapFactory.decodeResource(getResources(),R.drawable.plastic);
    Bitmap organic = BitmapFactory.decodeResource(getResources(),R.drawable.organic);
    Bitmap paper = BitmapFactory.decodeResource(getResources(),R.drawable.paper);
    private static String timer = "6000";
    DrawThread thread;
    boolean flag = false;
    Context mContext;
    boolean won;
    int score = 0;
    Trash trash_apple;
    Trash trash_bottle;
    Trash trash_plane;
    Bin bin_plastic;
    Bin bin_organic;
    Bin bin_paper;

    RectF myRect = new RectF();

// getters && sorry its in awkward position in code
    public static String getTimer() {
        return timer;
    }

    public boolean getWon(){
        return won;
    }

    public int getScore() {
        return score;
    }

    class DrawThread extends Thread {


        boolean runFlag = true;



        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;

            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer = String.valueOf(millisUntilFinished / 1000);
                    invalidate(); // Force the View to redraw
                }

                public void onFinish() {}
            }.start();
        }


        SurfaceHolder holder;


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            super.run();
            trash_apple = new Trash(apple, 10, 200, "organic");
            trash_bottle = new Trash(bottle, 300, 300, "plastic");
            trash_plane = new Trash(plane, 600, 400, "paper");
            bin_organic = new Bin(organic,
                    0,
                    1900,
                    "organic");
            bin_paper = new Bin(paper,
                    500,
                    1900,
                    "paper");
            bin_plastic = new Bin(plastic,
                    1000,
                    1900,
                    "plastic");

            // выполняем цикл (рисуем кадры) пока флаг включен
            while (runFlag) {
                Canvas c = holder.lockCanvas();
                // если успешно захватили канву
                if (c != null) {
                    c.drawColor(Color.rgb(202,145,145)); // display color


                    bin_organic.draw(c);
                    bin_paper.draw(c);
                    bin_plastic.draw(c);

                    Paint ptimer = new Paint();
                    ptimer.setColor(Color.BLACK);
                    ptimer.setTextSize(100);

                    trash_apple.OnCollisionBin(bin_organic);
                    trash_bottle.OnCollisionBin(bin_organic);
                    trash_plane.OnCollisionBin(bin_organic);

                    trash_apple.OnCollisionBin(bin_paper);
                    trash_bottle.OnCollisionBin(bin_paper);
                    trash_plane.OnCollisionBin(bin_paper);

                    trash_apple.OnCollisionBin(bin_plastic);
                    trash_bottle.OnCollisionBin(bin_plastic);
                    trash_plane.OnCollisionBin(bin_plastic);
                    score +=0;
                    c.drawText(timer, 50,   150, ptimer);
                    c.drawText(String.valueOf(score), -1*(300 - c.getWidth()),  150, ptimer);

                    trash_apple.move(
                            c,
                            trash_apple.x + trash_apple.dx,
                            trash_apple.y + trash_apple.dy
                    );
                    trash_bottle.move(
                            c,
                            trash_bottle.x + trash_bottle.dx,
                            trash_bottle.y + trash_bottle.dy
                    );
                    trash_plane.move(
                            c,
                            trash_plane.x + trash_plane.dx,
                            trash_plane.y + trash_plane.dy
                    );

                    if (score == 200){
                        Paint pt = new Paint();
                        pt.setColor(Color.CYAN);
                        pt.setTextSize(100);
                        c.drawText("You Won!!", 300, 200, pt);
                        won = true;
                        runFlag=false;
                        mContext = getContext();
                        Intent intent = new Intent(mContext, ScoreScreen.class);
                        mContext.startActivity(intent);
                    }

                    if (timer.equals("0")) {
                        Paint pfail = new Paint();
                        pfail.setColor(Color.CYAN);
                        pfail.setTextSize(100);
                        c.drawText("Times is out! Try again :^)", 300, 200, pfail);
                        won = false;
                        runFlag=false;
                        notify();
                        mContext = getContext();
                        Intent intent = new Intent(mContext, ScoreScreen.class);
                        mContext.startActivity(intent);
                    }


                    holder.unlockCanvasAndPost(c);

                    // нужна пауза на каждом кадре
                    try {
                        Thread.sleep(100); }
                    catch (InterruptedException e) {}
                }
            }

        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x_new = (int) event.getX();
        int y_new = (int) event.getY();
        switch( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                return true;

            case MotionEvent.ACTION_MOVE:

                if(flag) {

                    //if (setTrue(trash_apple, x_new)) {

                        trash_apple.x = x_new;
                        trash_apple.y = y_new;
                    //

                    if (setTrue(trash_bottle, x_new)) {

                        trash_bottle.x = x_new;
                        trash_bottle.y = y_new;
                    }
                    if (setTrue(trash_plane, x_new)) {

                        trash_plane.x = x_new;
                        trash_plane.y = y_new;
                    }


                }
                return true;

                case MotionEvent.ACTION_UP:
                    flag = false;
                    return true;
            }
            return true;
    }


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }
    public boolean setTrue(Trash trash, int x){
        if(x + 100 == trash.x + 100){
            return true;
        } else { return false; }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        thread = new DrawThread(holder);
        thread.start();
        // убеждаемся, что поток запускается
        Log.d("mytag", "DrawThread is running");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // при изменении конфигурации поверхности поток нужно перезапустить
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }

    // поверхность уничтожается - поток останавливаем
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }

    class Trash {

        int dist = 50;
        int vector = 1;
        int x= vector * dist - 10;
        int y= vector * dist - 10;
        int dx = 0, dy = 10;
        Paint paint;
        String status;
        private Bitmap bitmap;
        boolean flag;

        public Trash(Bitmap _bitmap, int _x, int _y, String _status) {
            x = _x;
            y = _y;
            bitmap = _bitmap;
            status = _status;
        }

        private void draw(Canvas canvas) {
//            c.drawBitmap(apple, x, y, new Paint());
            canvas.drawBitmap(this.bitmap, this.x, this.y, new Paint());
        }


        public void move(Canvas canvas, int _x, int _y) {
            this.x = _x;
            this.y = _y;

            this.draw(canvas);
        }


        public void OnCollisionBin(Bin bin) {
            int radius = 10;
            //  else if ((x >= myRect.left && x <= myRect.right) && (y >= myRect.top && y <= myRect.bottom) )
            if (this.x + 100  == bin.x + 100 || this.y + 100 == bin.y + 100 ) {
                if (this.status == bin.status) {
                    score += 200;
                }
            }
        }
    }

    class Bin {
        int x = 50;
        int y = 550;
        Bitmap bitmap;
        String status;

        public Bin(Bitmap _bitmap, int _x, int _y, String _status) {
            x = _x;
            y = _y;
            bitmap = _bitmap;
            status = _status;
        }


        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.bitmap, this.x, this.y, new Paint());
        }
    }


}