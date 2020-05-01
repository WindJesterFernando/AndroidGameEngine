package com.example.gameengine;

import android.graphics.Canvas;
import android.util.Log;

import java.util.LinkedList;

public class AdventureScene extends AbstractScene {

    LinkedList<AbstractSprite> sprites;

    @Override
    public void Draw(Canvas canvas) {

    }

    @Override
    public void Update(long deltaTime) {
        Log.d("Scene", "Adventure being updated");
    }

    @Override
    public void TouchDown(float x, float y) {

    }

    @Override
    public void TouchMove(float x, float y) {

    }

    @Override
    public void TouchUp(float x, float y) {

    }

    @Override
    public void SwipeUp() {

    }

    @Override
    public void SwipeUpRight() {

    }

    @Override
    public void SwipeRight() {

    }

    @Override
    public void SwipeDownRight() {

    }

    @Override
    public void SwipeDown() {

    }

    @Override
    public void SwipeDownLeft() {

    }

    @Override
    public void SwipeLeft() {

    }

    @Override
    public void SwipeUpLeft() {

    }

    @Override
    public void CircleGesture(int circleID) {

    }
}
