package com.example.gameengine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    long startTime;
    long lastUpdateTime;
    boolean gameOn;
    Handler gameThread;

    GameCanvas gameCanvas;

    long timeSinceLastRender;

    MediaPlayer arrowSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        MakeFullScreen();





        //GetDisplaySize
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //Select Level to play - Could have a level select screen class to handle this instead.
        gameCanvas = new GameCanvas(this, size.x, size.y);
        setContentView(gameCanvas);






        //How many milliseconds is it since the UNIX epoch
        startTime = System.currentTimeMillis();
        lastUpdateTime = startTime;


        gameThread = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (gameOn) {
                    long deltaTime = System.currentTimeMillis() - lastUpdateTime;
                    lastUpdateTime = System.currentTimeMillis();
                    GameThreadUpdate(deltaTime);
                }

                gameThread.sendEmptyMessageDelayed(0, 1);
            }
        };

        gameOn = true;
        gameThread.sendEmptyMessage(0);




        arrowSound = MediaPlayer.create(MainActivity.this, R.raw.wind);
        //arrowSound.start();


    }

    //Sample to cover using Input!
    //private int mActivePointerId;
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                gameCanvas.TouchDown(event.getX(), event.getY());
                //Log.d("input", "Action was DOWN");
                break;
            case (MotionEvent.ACTION_UP):
                //Log.d("input", "Action was Up");
                gameCanvas.TouchUp(event.getX(), event.getY());
                break;
            case (MotionEvent.ACTION_MOVE):
                //Log.d("input", "Action was MOVE");
                gameCanvas.TouchMove(event.getX(), event.getY());
                break;

//            case (MotionEvent.AXIS_X) :
//                Log.d("input", "X axis changed");

        }

        //Log.d("input", "data = " + event.getAxisValue(MotionEvent.AXIS_VSCROLL));

        float x = event.getX();
        float y = event.getY();

        //Log.d("input", x + ", " + y);




        //Multitouch

//        // Get the pointer ID
//        mActivePointerId = event.getPointerId(0);
//
//        // ... Many touch events later...
//
//        // Use the pointer ID to find the index of the active pointer
//        // and fetch its position
//
//        int pointerIndex = event.findPointerIndex(mActivePointerId);
//        // Get the pointer's current position
//        float x = event.getX(pointerIndex);
//        float y = event.getY(pointerIndex);
//
//        Log.d("input", x + ", " + y);

        return  true;
    }

    private void MakeFullScreen()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void GameThreadUpdate(long deltaTime)
    {

        gameCanvas.Update(deltaTime);

        timeSinceLastRender += deltaTime;

        if(timeSinceLastRender >= 16)  //60 frames a second
        {
            try {

                Canvas currentCanvas = null;
                currentCanvas = gameCanvas.getHolder().lockCanvas();


                if (currentCanvas != null)
                    gameCanvas.draw(currentCanvas);

                gameCanvas.getHolder().unlockCanvasAndPost(currentCanvas);
            }
            catch (Exception e)
            {

                Log.d("Render", "Missed Frame! " + e.toString());
            }
            finally {
                //Log.d("Render", "Missed Frame!");

            }

        }


        //startTime = System.currentTimeMillis();


    }

    @Override
    protected void onPause(){

        //Log.d("testing", "Pause hit");

        super.onPause();



//        if(currentCanvasLevel != null){
//            currentCanvasLevel.onPause();
//        }
    }

    @Override
    protected void onResume(){

        //Log.d("testing", "Resume hit");

        super.onResume();



//        if(currentCanvasLevel != null){
//            currentCanvasLevel.onResume();
//        }

        MakeFullScreen();   //Set to fullscreen when users jump back to our game.
    }




}
