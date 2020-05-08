package com.example.gameengine;

import android.graphics.Canvas;
import android.util.Log;

public class BattleScene extends AbstractScene {

    public BattleScene(GameCanvas GameCanvas) {
        super(GameCanvas);
    }

    @Override
    public void Draw(Canvas canvas) {
        //Log.d("Scene", "Battle being drawn");
    }

    @Override
    public void Update(long deltaTime) {
        //Log.d("Scene", "Battle being updated");
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
