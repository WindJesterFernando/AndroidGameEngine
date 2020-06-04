package com.example.gameengine;


import android.graphics.Bitmap;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class StateAnimatingSprite extends AnimatingSprite {


    int state;

    LinkedList<AnimationFrame> walkingDownAnimationFrames, walkingUpAnimationFrames, walkingRightAnimationFrames, walkingLeftAnimationFrames;
    LinkedList<AnimationFrame> idleDownAnimationFrames, idleUpAnimationFrames, idleRightAnimationFrames, idleLeftAnimationFrames;

    public StateAnimatingSprite(Bitmap SpriteSheet, int numberOfFramesX, int numberOfFramesY, int xFrame, int yFrame, long TimeBetweenAnimationFrames) {
        super(SpriteSheet, numberOfFramesX, numberOfFramesY, xFrame, yFrame, TimeBetweenAnimationFrames);

        walkingDownAnimationFrames = new LinkedList<AnimationFrame>();
        walkingUpAnimationFrames = new LinkedList<AnimationFrame>();
        walkingRightAnimationFrames = new LinkedList<AnimationFrame>();
        walkingLeftAnimationFrames = new LinkedList<AnimationFrame>();

        idleDownAnimationFrames = new LinkedList<AnimationFrame>();
        idleUpAnimationFrames = new LinkedList<AnimationFrame>();
        idleRightAnimationFrames = new LinkedList<AnimationFrame>();
        idleLeftAnimationFrames = new LinkedList<AnimationFrame>();
    }

    public void AddFrameBasedOnState(AnimationFrame animationFrame, int stateToAddTo)
    {
        if(stateToAddTo == SpriteStates.WalkingDown)
            walkingDownAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.WalkingUp)
            walkingUpAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.WalkingRight)
            walkingRightAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.WalkingLeft)
            walkingLeftAnimationFrames.add(animationFrame);

        else if(stateToAddTo == SpriteStates.IdleDown)
            idleDownAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.IdleUp)
            idleUpAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.IdleRight)
            idleRightAnimationFrames.add(animationFrame);
        else if(stateToAddTo == SpriteStates.IdleLeft)
            idleLeftAnimationFrames.add(animationFrame);
    }

    public void SetState(int State)
    {
        if(state != State) {

            state = State;

            if (state == SpriteStates.WalkingDown) {
                animationFrames = walkingDownAnimationFrames;
            }
            else if (state == SpriteStates.WalkingUp) {
                animationFrames = walkingUpAnimationFrames;
            }
            else if (state == SpriteStates.WalkingRight) {
                animationFrames = walkingRightAnimationFrames;
            }
            else if (state == SpriteStates.WalkingLeft) {
                animationFrames = walkingLeftAnimationFrames;
            }

            else if (state == SpriteStates.IdleDown) {
                animationFrames = idleDownAnimationFrames;
            }
            else if (state == SpriteStates.IdleUp) {
                animationFrames = idleUpAnimationFrames;
            }
            else if (state == SpriteStates.IdleRight) {
                animationFrames = idleRightAnimationFrames;
            }
            else if (state == SpriteStates.IdleLeft) {
                animationFrames = idleLeftAnimationFrames;
            }

            currentFrame = animationFrames.getFirst();
            SetFrame(currentFrame.frameX, currentFrame.frameY);
            timeSinceLastAnimationFrameChange = 0;
        }
    }


    @Override
    public void Update(long deltaTime)
    {
        super.Update(deltaTime);

        boolean useIdle = false;

        if(speed != null) {

            if(speed.x == 0 && speed.y == 0)
                useIdle = true;
            else if(Math.abs(speed.x) > Math.abs(speed.y)) {
                if (speed.x > 0)
                    SetState(SpriteStates.WalkingRight);
                else
                    SetState(SpriteStates.WalkingLeft);
            }
            else {
                if (speed.y > 0)
                    SetState(SpriteStates.WalkingDown);
                else
                    SetState(SpriteStates.WalkingUp);
            }
        }
        else
            useIdle = true;

        if(useIdle)
        {
            if(state == SpriteStates.WalkingUp)
                SetState(SpriteStates.IdleUp);
            else if(state == SpriteStates.WalkingDown)
                SetState(SpriteStates.IdleDown);
            else if(state == SpriteStates.WalkingRight)
                SetState(SpriteStates.IdleRight);
            else if(state == SpriteStates.WalkingLeft)
                SetState(SpriteStates.IdleLeft);


        }

    }

}
