package com.example.gameengine;

import android.graphics.Canvas;
import android.util.Log;

import java.util.LinkedList;

public class AdventureScene extends AbstractScene {

    LinkedList<AbstractSprite> sprites;

    public AdventureScene(GameCanvas GameCanvas) {
        super(GameCanvas);
    }

    @Override
    public void Draw(Canvas canvas) {
        //Log.d("Scene", "Adventure being drawn");
    }

    @Override
    public void Update(long deltaTime) {
        //Log.d("Scene", "Adventure being updated");
    }

    @Override
    public void TouchDown(float x, float y) {
        gameCanvas.CreateNewState(GameStates.DialogScene);
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
