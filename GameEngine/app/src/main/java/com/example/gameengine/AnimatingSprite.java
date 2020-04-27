package com.example.gameengine;

import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;

import java.util.LinkedList;

public class AnimatingSprite extends Sprite {

    LinkedList<AnimationFrame> animationFrames;
    long timeBetweenAnimationFrames, timeSinceLastAnimationFrameChange;
    AnimationFrame currentFrame;

    public AnimatingSprite(Bitmap SpriteSheet, int numberOfFramesX, int numberOfFramesY, int xFrame, int yFrame, long TimeBetweenAnimationFrames)
    {
        super(SpriteSheet, numberOfFramesX, numberOfFramesY, xFrame, yFrame);
        animationFrames = new LinkedList<AnimationFrame>();
        animationFrames.add(new AnimationFrame(xFrame, yFrame));
        timeBetweenAnimationFrames = TimeBetweenAnimationFrames;
        currentFrame = animationFrames.getFirst();
    }

    @Override
    public void Update(long deltaTime)
    {
        super.Update(deltaTime);

        timeSinceLastAnimationFrameChange += deltaTime;
        if(timeSinceLastAnimationFrameChange >= timeBetweenAnimationFrames)
        {
            timeSinceLastAnimationFrameChange = 0;
            //Log.d("Animation", "Flipping New Frame");

            boolean useNext = false;

            if(currentFrame == animationFrames.getLast())
            {
                AnimationFrame af = animationFrames.getFirst();
                SetFrame(af.frameX, af.frameY);
                currentFrame = af;
            }
            else {
                for (AnimationFrame af : animationFrames) {
                    if (useNext) {
                        SetFrame(af.frameX, af.frameY);
                        currentFrame = af;
                        break;
                    }
                    if (currentFrame == af)
                        useNext = true;
                }
            }
        }
    }

    public void AddFrame(AnimationFrame animationFrame)
    {
        animationFrames.add(animationFrame);
    }

}

