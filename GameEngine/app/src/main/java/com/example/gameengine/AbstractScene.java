package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;

public abstract class AbstractScene {


    public AbstractScene()
    {

    }

    abstract public void Draw(Canvas canvas);
    abstract public void Update(long deltaTime);

    abstract public void TouchDown(float x, float y);
    abstract public void TouchMove(float x, float y);
    abstract public void TouchUp(float x, float y);

    abstract public void SwipeUp();
    abstract public void SwipeUpRight();
    abstract public void SwipeRight();
    abstract public void SwipeDownRight();
    abstract public void SwipeDown();
    abstract public void SwipeDownLeft();
    abstract public void SwipeLeft();
    abstract public void SwipeUpLeft();

    abstract public void CircleGesture(int circleID);


}
