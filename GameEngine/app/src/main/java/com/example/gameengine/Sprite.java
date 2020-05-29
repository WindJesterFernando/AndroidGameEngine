package com.example.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.DebugUtils;
import android.util.Log;

public class Sprite extends AbstractSprite {

    private Bitmap spriteSheet;

    protected Rect srcRect;
    private Rect destRect;

    private int frameSizeX;
    private int frameSizeY;

    private Vectror2 position;
    private Vectror2 scale;

    private Vectror2 lerpStart, lerpEnd;
    private long lerpTime, lerpTimeCurrent;

    private Vectror2 moveToPostition;
    private float moveToSpeed;

    private float rotation;

    public boolean checkCircleCollisionDetection;
    public float radius;

    public boolean checkBoundingBoxCollisionDetection;

    protected Vectror2 speed;

    private int collisionResponseAction;

    private float bounceAtude = 30;
    private Vectror2 bounceEffect;

    private float slideFriction = 1;

    public Sprite(Bitmap SpriteSheet, int numberOfFramesX, int numberOfFramesY, int xFrame, int yFrame) {
        super(0);

        spriteSheet = SpriteSheet;

        frameSizeX = spriteSheet.getWidth() / numberOfFramesX;
        frameSizeY = spriteSheet.getHeight() / numberOfFramesY;

        scale = new Vectror2(1, 1);
        //Log.d("Sprite", "sizeX " + frameSizeX + ", sizeY " + frameSizeY);

        SetFrame(xFrame, yFrame);

        destRect = new Rect(0, 0, 600, 600);

    }

    public void SetFrame(int x, int y) {
        srcRect = new Rect(frameSizeX * x, frameSizeY * y, frameSizeX * (x + 1), frameSizeY * (y + 1));
//
//        Log.d("Sprite", "" + srcRect);
//        Log.d("Sprite", "sizeX " + frameSizeX + ", sizeY " + frameSizeY);
    }

    public void SetPostion(Vectror2 Postion) {
        position = Postion;
        RefreshDestinationRect();
        //Log.d("test", "test " + postion.toString());
    }

    public void SetScale(Vectror2 Scale) {
        scale = Scale;
        RefreshDestinationRect();
    }

    private void RefreshDestinationRect() {
        destRect = new Rect((int) position.x - (int) (frameSizeX / 2f * scale.x), (int) position.y - (int) (frameSizeY / 2f * scale.y), (int) position.x + (int) (frameSizeX / 2f * scale.x), (int) position.y + (int) (frameSizeY / 2f * scale.y));
    }

    public Bitmap GetSpriteSheet() {
        return spriteSheet;
    }

    public Rect GetSourceRect() {
        return srcRect;
    }

    public Rect GetDestinationRect() {
        return destRect;
    }

    public Vectror2 GetPosition() {
        return position;
    }

    @Override
    public void Update(long deltaTime) {

        //Log.d("lerp", "deltaTime = " + deltaTime);

        if (lerpTimeCurrent < lerpTime) {
            lerpTimeCurrent += deltaTime;
            float amount = (float) lerpTimeCurrent / (float) lerpTime;
            if (amount > 1)
                amount = 1;
            else if (amount < 0)
                amount = 0;

            //Log.d("lerp", "amount = " + amount);

            Vectror2 difference = new Vectror2(lerpEnd.x - lerpStart.x, lerpEnd.y - lerpStart.y);

            //Log.d("lerp", "difference = " + difference.ToString());

            double hyp = Math.sqrt(difference.x * difference.x + difference.y * difference.y);
            Vectror2 normalizeVec = new Vectror2(difference.x / (float) hyp, difference.y / (float) hyp);

            //Log.d("lerp", "hyp = " + hyp);
            //Log.d("lerp", "normalizeVec = " + normalizeVec.ToString());

            Vectror2 newPos = new Vectror2(lerpStart.x + normalizeVec.x * amount * (float) hyp, lerpStart.y + normalizeVec.y * amount * (float) hyp);
            //Log.d("lerp", "newPos = " + newPos.ToString());


            speed = new Vectror2(newPos.x - position.x, newPos.y - position.y);
            //SetPostion(newPos);

        }

        if (moveToPostition != null) {

            Vectror2 difference = new Vectror2(moveToPostition.x - position.x, moveToPostition.y - position.y);

            double hyp = Math.sqrt(difference.x * difference.x + difference.y * difference.y);
            Vectror2 normalizeVec = new Vectror2(difference.x / (float) hyp, difference.y / (float) hyp);

            Vectror2 moveAmount = new Vectror2(normalizeVec.x * moveToSpeed, normalizeVec.y * moveToSpeed);

            Vectror2 newPos = new Vectror2(position.x + moveAmount.x, position.y + moveAmount.y);

            if (hyp < moveToSpeed) {
                newPos = moveToPostition;
                moveToPostition = null;
            }

            speed = new Vectror2(newPos.x - position.x, newPos.y - position.y);

            //SetPostion(newPos);

        }

        if (bounceEffect != null) {
            if (speed == null)
                speed = new Vectror2(bounceEffect.x, bounceEffect.y);
            else {
                speed.x += bounceEffect.x;
                speed.y += bounceEffect.y;
            }

            bounceEffect.x *= 0.9f;
            bounceEffect.y *= 0.9f;
        }

    }

    public void PerformLerp(Vectror2 LerpStart, Vectror2 LerpEnd, long LerpTime) {
        lerpStart = LerpStart;
        lerpEnd = LerpEnd;
        lerpTime = LerpTime;
        lerpTimeCurrent = 0;
    }

    public void PerformMoveTo(Vectror2 MoveTo, float Speed) {
        moveToPostition = MoveTo;
        moveToSpeed = Speed;
    }

    @Override
    public void Draw(Canvas canvas) {

        if (rotation != 0) {
            canvas.save();//Saving the canvas and later restoring it so only this image will be rotated.
            canvas.rotate(rotation, position.x, position.y);

            canvas.drawBitmap(spriteSheet, srcRect, destRect, localPaint);

            canvas.restore();
        } else
            canvas.drawBitmap(spriteSheet, srcRect, destRect, localPaint);
    }

    public void SetRotation(float Rotation) {
        rotation = Rotation;
    }

    public float GetRotation() {
        return rotation;
    }

    @Override
    public void SetColor(int colorConstant) {

        ColorFilter filter = new LightingColorFilter(colorConstant, 1);
        localPaint.setColorFilter(filter);

        //Log.d("investigate", "" + PorterDuff.Mode..ADD + "   " + PorterDuff.Mode.MULTIPLY);

        //int color = Color.BLUE;
        //PorterDuff.Mode mode = PorterDuff.Mode.DST_OVER;

        //paint.setColorFilter(color, mode);

        //paint.setColor(colorConstant);
    }

    @Override
    public int GetBottomPosition() {
        return (int) (position.y + (float) frameSizeY * scale.y / 2f);
    }

    public boolean CheckForCircleCollision(Sprite spr) {

        if (!checkCircleCollisionDetection)
            return false;

        float xDif = spr.GetPosition().x - position.x;
        float yDif = spr.GetPosition().y - position.y;

        double hyp = Math.sqrt(xDif * xDif + yDif * yDif);

        if (radius + spr.GetRadiusForCicleCollisionDetection() > hyp) {
            Log.d("Collision", "Circle Detected");
            speed = null;
            return true;
        }

        return false;
    }

    public float GetRadiusForCicleCollisionDetection() {
        return radius;
    }

    public void SetRadiusForCicleCollisionDetection(float Radius) {
        radius = Radius;
        checkCircleCollisionDetection = true;
    }

    public void SetForBoundingBoxCollisionDetection() {
        checkBoundingBoxCollisionDetection = true;
    }

    public boolean CheckForBoundingBoxCollision(Sprite spr)//, boolean manageSpeed)
    {

        if (!checkBoundingBoxCollisionDetection)
            return false;

        boolean xColLeftWithSpeed = false, xColRightWithSpeed = false;
        boolean yColTopWithSpeed = false, yColBottomWithSpeed = false;

        boolean xColLeft = false, xColRight = false;
        boolean yColTop = false, yColBottom = false;

        Vectror2 unModifiedSpeed = null;
        if (speed != null)
            unModifiedSpeed = new Vectror2(speed.x, speed.y);
        boolean xSpeedWasModifiedToSlideIntoLedge = false, ySpeedWasModifiedToSlideIntoLedge = false;

        Vectror2 posWithSpeed;

        if (speed != null) {
            posWithSpeed = new Vectror2(position.x + speed.x, position.y + speed.y);
        } else
            posWithSpeed = position;

        float x1 = spr.GetPosition().x - (spr.GetFrameSizeX() / 2f * spr.GetScale().x);
        float x2 = spr.GetPosition().x + (spr.GetFrameSizeX() / 2f * spr.GetScale().x);

        float ox1 = posWithSpeed.x - (frameSizeX / 2f * scale.x);
        float ox2 = posWithSpeed.x + (frameSizeX / 2f * scale.x);

        if (x1 > ox1 && x1 < ox2) {
            xColRightWithSpeed = true;
            //Log.d("Col", "xColRightWithSpeed " + tag);
        }

        if (x2 > ox1 && x2 < ox2) {
            xColLeftWithSpeed = true;
            //Log.d("Col", "xColLeftWithSpeed " + tag);
        }


        /////FIX
        if (ox1 > x1 && ox1 < x2) {
            xColRightWithSpeed = true;
            //Log.d("Col", "xColRightWithSpeed " + tag);
        }

        if (ox2 > x1 && ox2 < x2) {
            xColLeftWithSpeed = true;
            //Log.d("Col", "xColLeftWithSpeed " + tag);
        }
        //////////


        x1 = spr.GetPosition().x - (spr.GetFrameSizeX() / 2f * spr.GetScale().x);
        x2 = spr.GetPosition().x + (spr.GetFrameSizeX() / 2f * spr.GetScale().x);

        ox1 = position.x - (frameSizeX / 2f * scale.x);
        ox2 = position.x + (frameSizeX / 2f * scale.x);

        if (x1 > ox1 && x1 < ox2) {
            xColRight = true;
            //Log.d("Col", "xColRight "  + tag);
        }

        if (x2 > ox1 && x2 < ox2) {
            xColLeft = true;
            //Log.d("Col", "xColLeft "  + tag);
        }



        /////FIX
        if (ox1 > x1 && ox1 < x2) {
            xColRight = true;
            //Log.d("Col", "xColRight "  + tag);
        }

        if (ox2 > x1 && ox2 < x2) {
            xColLeft = true;
            //Log.d("Col", "xColLeft "  + tag);
        }
        /////


        float y1 = spr.GetPosition().y - (spr.GetFrameSizeY() / 2f * spr.GetScale().y);
        float y2 = spr.GetPosition().y + (spr.GetFrameSizeY() / 2f * spr.GetScale().y);

        float oy1 = posWithSpeed.y - (frameSizeY / 2f * scale.y);
        float oy2 = posWithSpeed.y + (frameSizeY / 2f * scale.y);

        if (y1 > oy1 && y1 < oy2)
        {
            yColBottomWithSpeed = true;
            //Log.d("Col", "yColBottomWithSpeed "  + tag);
        }

        if (y2 > oy1 && y2 < oy2)
        {
            yColTopWithSpeed = true;
            //Log.d("Col", "yColTopWithSpeed "  + tag);
        }


        /////FIX
        if (oy1 > y1 && oy1 < y2)
        {
            yColBottomWithSpeed = true;
            //Log.d("Col", "yColBottomWithSpeed "  + tag);
        }

        if (oy2 > y1 && oy2 < y2)
        {
            yColTopWithSpeed = true;
            //Log.d("Col", "yColTopWithSpeed "  + tag);
        }
        /////


        y1 = spr.GetPosition().y - (spr.GetFrameSizeY() / 2f * spr.GetScale().y);
        y2 = spr.GetPosition().y + (spr.GetFrameSizeY() / 2f * spr.GetScale().y);

        oy1 = position.y - (frameSizeY / 2f * scale.y);
        oy2 = position.y + (frameSizeY / 2f * scale.y);

        if (y1 > oy1 && y1 < oy2)
        {
            yColBottom = true;
            //Log.d("Col", "yColBottom "  + tag);
        }
        if (y2 > oy1 && y2 < oy2)
        {
            yColTop = true;
            //Log.d("Col", "yColTop "  + tag);
        }


        /////FIX
        if (oy1 > y1 && oy1 < y2)
        {
            yColBottom = true;
            //Log.d("Col", "yColBottom "  + tag);
        }
        if (oy2 > y1 && oy2 < y2)
        {
            yColTop = true;
            //Log.d("Col", "yColTop "  + tag);
        }
        /////


//        if(y1 < oy1 && y1 > oy2)
//        //if(y1 < oy1 && oy1 < y2)
//        {
//            yColBottom = true;
//            yColTop = true;
//            Log.d("Col", "yColTopAndBottom "  + tag);
//        }



        if ((xColLeftWithSpeed || xColRightWithSpeed) && (yColBottomWithSpeed || yColTopWithSpeed)) {
            //Log.d("Collision","Bounding Box Detected");

            //WE NEED: Where the collision is, and by how much


            if (collisionResponseAction == CollisionResponseActions.HoldInPlace) {
                speed = null;
                //Log.d("Collision","Setting Speed to 0 on " + tag);
            }
            //speed.y = 0;
            //speed.x = 0;

            if (collisionResponseAction == CollisionResponseActions.BounceOffOf) {

                speed = null;

                bounceEffect = new Vectror2(0, 0);
                if(xColRightWithSpeed && !xColRight) {
                    //Log.d("Collision", "Bouncing off the right, to the left");
                    bounceEffect.x = -bounceAtude;
                }
                if(xColLeftWithSpeed && !xColLeft){
                    //Log.d("Collision", "Bouncing off the left side, to the right");
                    bounceEffect.x = bounceAtude;
                }
                if(yColTopWithSpeed && !yColTop){
                    //Log.d("Collision", "Bouncing off the top, to the bottom");
                    bounceEffect.y = bounceAtude;
                }
                if(yColBottomWithSpeed && !yColBottom)
                {
                    //Log.d("Collision", "Bouncing off the bottom, to the top");
                    bounceEffect.y = -bounceAtude;
                }



            }


            if (collisionResponseAction == CollisionResponseActions.SlideAlong) {

                if (speed != null) {
                    if (xColRightWithSpeed) {

                        if (xColRight)
                            ;//speed.x = 0;
                        else {
                            float suggestedNewSpeed = (spr.GetPosition().x - (spr.GetFrameSizeX() / 2f * spr.GetScale().x)) - (position.x + (frameSizeX / 2f * scale.x));

                            if (Math.abs(speed.x) >= Math.abs(suggestedNewSpeed)) {
                                speed.x = suggestedNewSpeed;
                                xSpeedWasModifiedToSlideIntoLedge = true;
                            }
                        }

                    }
                    if (xColLeftWithSpeed) {

                        if (xColLeft)
                            ;//speed.x = 0;
                        else {

                            float suggestedNewSpeed = (spr.GetPosition().x + (spr.GetFrameSizeX() / 2f * spr.GetScale().x)) - (position.x - (frameSizeX / 2f * scale.x));

                            if (Math.abs(speed.x) >= Math.abs(suggestedNewSpeed)) {
                                speed.x = suggestedNewSpeed;
                                xSpeedWasModifiedToSlideIntoLedge = true;
                            }
                        }

                    }
                    if (yColTopWithSpeed) {

                        if (yColTop)
                            ;//speed.y = 0;
                        else {

                            float suggestedNewSpeed = (spr.GetPosition().y + (spr.GetFrameSizeY() / 2f * spr.GetScale().y)) - (position.y - (frameSizeY / 2f * scale.y));

                            if (Math.abs(speed.y) >= Math.abs(suggestedNewSpeed)) {
                                speed.y = suggestedNewSpeed;
                                ySpeedWasModifiedToSlideIntoLedge = true;
                            }
                        }

                    }
                    if (yColBottomWithSpeed) {

                        if (yColBottom)
                            ;//speed.y = 0;
                        else {

                            float suggestedNewSpeed = (spr.GetPosition().y - (spr.GetFrameSizeY() / 2f * spr.GetScale().y)) - (position.y + (frameSizeY / 2f * scale.y));

                            if (Math.abs(speed.y) >= Math.abs(suggestedNewSpeed)) {
                                speed.y = suggestedNewSpeed;
                                ySpeedWasModifiedToSlideIntoLedge = true;
                            }
                        }
                    }
                }


                if (speed != null) {
                    if (speed.x != unModifiedSpeed.x || speed.y != unModifiedSpeed.y) {

                        if (!xSpeedWasModifiedToSlideIntoLedge) {
                            if (speed.x < 0) {
                                speed.x = -(float) Math.sqrt(unModifiedSpeed.x * unModifiedSpeed.x + unModifiedSpeed.y * unModifiedSpeed.y) * slideFriction;

                                if (moveToPostition.x > position.x + speed.x)
                                    speed.x = moveToPostition.x - position.x;
                            } else {
                                speed.x = (float) Math.sqrt(unModifiedSpeed.x * unModifiedSpeed.x + unModifiedSpeed.y * unModifiedSpeed.y) * slideFriction;

                                if (moveToPostition.x < position.x + speed.x)
                                    speed.x = moveToPostition.x - position.x;
                            }
                        } else if (!ySpeedWasModifiedToSlideIntoLedge) {

                            if (speed.y < 0) {
                                speed.y = -(float) Math.sqrt(unModifiedSpeed.x * unModifiedSpeed.x + unModifiedSpeed.y * unModifiedSpeed.y) * slideFriction;

                                if (moveToPostition.y > position.y + speed.y)
                                    speed.y = moveToPostition.y - position.y;
                            } else {
                                speed.y = (float) Math.sqrt(unModifiedSpeed.x * unModifiedSpeed.x + unModifiedSpeed.y * unModifiedSpeed.y) * slideFriction;

                                if (moveToPostition.y < position.y + speed.y)
                                    speed.y = moveToPostition.y - position.y;
                            }
                        }
                    }
                }
            }


            return true;
        }


        return false;
    }

    public Vectror2 GetScale() {
        return scale;
    }

    public int GetFrameSizeX() {
        return frameSizeX;
    }

    public int GetFrameSizeY() {
        return frameSizeY;
    }

    public void SetRotationToLookAtSprite(Sprite sprToLookAt) {

        float xDif = sprToLookAt.GetPosition().x - position.x;
        float yDif = sprToLookAt.GetPosition().y - position.y;

        double angle = Math.atan2(xDif, yDif);

        rotation = (float) Math.toDegrees(angle) * -1 + 180;

    }

//    public void SetSpeed(Vectror2 Speed)
//    {
//        speed = Speed;
//    }

//    public Vectror2 GetSpeed()
//    {
//        return speed;
//    }

    public void ApplySpeedToPosition() {

        if (speed != null) {
            //Log.d("speed test", "" + speed);
            SetPostion(new Vectror2(speed.x + position.x, speed.y + position.y));
        }
        speed = null;

    }

    public void SetCollisionResponseAction(int CollisionResponseAction) {
        collisionResponseAction = CollisionResponseAction;
    }

    public void SetBounceAtude(float BounceAtude)
    {
        bounceAtude = BounceAtude;
    }

    public void SetSlideFriction(float  SlideFriction)
    {
         slideFriction = SlideFriction;
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {

        if (rotation != 0) {
            canvas.save();//Saving the canvas and later restoring it so only this image will be rotated.
            canvas.rotate(rotation, position.x, position.y);

            canvas.drawBitmap(spriteSheet, srcRect, destRect, paint);

            canvas.restore();
        } else
            canvas.drawBitmap(spriteSheet, srcRect, destRect, paint);
    }

}
