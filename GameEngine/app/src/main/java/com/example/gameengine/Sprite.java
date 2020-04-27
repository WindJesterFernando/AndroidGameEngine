package com.example.gameengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;

public class Sprite extends AbstractSprite {

    private Bitmap spriteSheet;

    protected Rect srcRect;
    private Rect destRect;

    private int frameSizeX;
    private int frameSizeY;

    private Vectror2 postion;
    private Vectror2 scale;

    private Vectror2 lerpStart, lerpEnd;
    private long lerpTime, lerpTimeCurrent;

    private Vectror2 moveToPostition;
    private float moveToSpeed;

    private float rotation;

    public boolean checkCircleCollisionDetection;
    public float radius;

    public boolean checkBoundingBoxCollisionDetection;

    public Sprite(Bitmap SpriteSheet, int numberOfFramesX, int numberOfFramesY, int xFrame, int yFrame)
    {
        super(0);

        spriteSheet = SpriteSheet;

        frameSizeX = spriteSheet.getWidth() / numberOfFramesX;
        frameSizeY = spriteSheet.getHeight() / numberOfFramesY;

        scale = new Vectror2(1,1);
        //Log.d("Sprite", "sizeX " + frameSizeX + ", sizeY " + frameSizeY);

        SetFrame(xFrame, yFrame);

        destRect = new Rect(0,0, 600, 600);

    }

    public void SetFrame(int x, int y)
    {
        srcRect = new Rect(frameSizeX * x, frameSizeY * y, frameSizeX * (x + 1), frameSizeY * (y + 1));
//
//        Log.d("Sprite", "" + srcRect);
//        Log.d("Sprite", "sizeX " + frameSizeX + ", sizeY " + frameSizeY);
    }

    public void SetPostion(Vectror2 Postion)
    {
        postion = Postion;
        RefreshDestinationRect();
        //Log.d("test", "test " + postion.toString());
    }

    public void SetScale(Vectror2 Scale)
    {
        scale = Scale;
        RefreshDestinationRect();
    }

    private void RefreshDestinationRect()
    {
        destRect = new Rect((int)postion.x - (int)(frameSizeX / 2f * scale.x), (int)postion.y - (int)(frameSizeY / 2f * scale.y), (int)postion.x + (int)(frameSizeX / 2f * scale.x), (int)postion.y + (int)(frameSizeY / 2f * scale.y));
    }

    public Bitmap GetSpriteSheet()
    {
        return  spriteSheet;
    }

    public Rect GetSourceRect()
    {
        return srcRect;
    }

    public Rect GetDestinationRect()
    {
        return destRect;
    }

    public Vectror2 GetPosition()
    {
        return postion;
    }

    @Override
    public void Update(long deltaTime)
    {

        //Log.d("lerp", "deltaTime = " + deltaTime);

        if(lerpTimeCurrent < lerpTime)
        {
            lerpTimeCurrent += deltaTime;
            float amount = (float) lerpTimeCurrent / (float) lerpTime;
            if(amount > 1)
                amount = 1;
            else if(amount < 0)
                amount = 0;

            //Log.d("lerp", "amount = " + amount);

            Vectror2 difference = new Vectror2(lerpEnd.x - lerpStart.x, lerpEnd.y - lerpStart.y);

            //Log.d("lerp", "difference = " + difference.ToString());

            double hyp = Math.sqrt(difference.x * difference.x + difference.y * difference.y);
            Vectror2 normalizeVec = new Vectror2(difference.x / (float)hyp, difference.y / (float)hyp);

            //Log.d("lerp", "hyp = " + hyp);
            //Log.d("lerp", "normalizeVec = " + normalizeVec.ToString());

            Vectror2 newPos = new Vectror2(lerpStart.x + normalizeVec.x * amount * (float) hyp, lerpStart.y + normalizeVec.y * amount * (float)hyp);
            //Log.d("lerp", "newPos = " + newPos.ToString());

            SetPostion(newPos);

        }

        if(moveToPostition != null)
        {

            Vectror2 difference = new Vectror2(moveToPostition.x - postion.x, moveToPostition.y - postion.y);

            double hyp = Math.sqrt(difference.x * difference.x + difference.y * difference.y);
            Vectror2 normalizeVec = new Vectror2(difference.x / (float)hyp, difference.y / (float)hyp);

            Vectror2 moveAmount = new Vectror2(normalizeVec.x * moveToSpeed, normalizeVec.y * moveToSpeed);

            Vectror2 newPos = new Vectror2(postion.x + moveAmount.x, postion.y + moveAmount.y);


            if(hyp < moveToSpeed) {
                newPos = moveToPostition;
                moveToPostition = null;
            }

            SetPostion(newPos);

        }

    }

    public void PerformLerp(Vectror2 LerpStart, Vectror2 LerpEnd, long LerpTime)
    {
        lerpStart = LerpStart;
        lerpEnd = LerpEnd;
        lerpTime = LerpTime;
        lerpTimeCurrent = 0;
    }

    public void PerformMoveTo(Vectror2 MoveTo, float Speed)
    {
        moveToPostition = MoveTo;
        moveToSpeed = Speed;
    }

    @Override
    public void Draw(Canvas canvas)
    {

        if(rotation != 0) {
            canvas.save();//Saving the canvas and later restoring it so only this image will be rotated.
            canvas.rotate(rotation, postion.x, postion.y);

            canvas.drawBitmap(spriteSheet, srcRect, destRect, paint);

            canvas.restore();
        }
        else
            canvas.drawBitmap(spriteSheet, srcRect, destRect, paint);
    }

    public void SetRotation(float Rotation)
    {
        rotation = Rotation;
    }

    public float GetRotation()
    {
        return rotation;
    }

    @Override
    public void SetColor(int colorConstant)
    {

        ColorFilter filter = new LightingColorFilter(colorConstant, 1);
        paint.setColorFilter(filter);

        //Log.d("investigate", "" + PorterDuff.Mode..ADD + "   " + PorterDuff.Mode.MULTIPLY);

        //int color = Color.BLUE;
        //PorterDuff.Mode mode = PorterDuff.Mode.DST_OVER;

        //paint.setColorFilter(color, mode);

        //paint.setColor(colorConstant);
    }

    @Override
    public int GetBottomPosition()
    {
        return (int)(postion.y + (float) frameSizeY * scale.y / 2f);
    }

    public boolean CheckForCircleCollision(Sprite spr)
    {

        if(!checkCircleCollisionDetection)
            return false;

        float xDif = spr.GetPosition().x - postion.x;
        float yDif = spr.GetPosition().y - postion.y;

        double hyp = Math.sqrt(xDif * xDif + yDif * yDif);

        if(radius + spr.GetRadiusForCicleCollisionDetection() > hyp)
            return true;

        return false;
    }

    public float GetRadiusForCicleCollisionDetection()
    {
        return radius;
    }

    public void SetRadiusForCicleCollisionDetection(float Radius)
    {
        radius = Radius;
        checkCircleCollisionDetection = true;
    }

    public void SetForBoundingBoxCollisionDetection()
    {
        checkBoundingBoxCollisionDetection = true;
    }

    public boolean CheckForBoundingBoxCollision(Sprite spr)
    {

        if(!checkBoundingBoxCollisionDetection)
            return false;

        boolean xCol = false;
        boolean yCol = false;


        float x1 = spr.GetPosition().x - (spr.GetFrameSizeX() / 2f * spr.GetScale().x);
        float x2 = spr.GetPosition().x + (spr.GetFrameSizeX() / 2f * spr.GetScale().x);

        float ox1 = postion.x - (frameSizeX / 2f * scale.x);
        float ox2 = postion.x + (frameSizeX / 2f * scale.x);

        if (x1 > ox1 && x1 < ox2)
            xCol = true;

        if (x2 > ox1 && x2 < ox2)
            xCol = true;



        float y1 = spr.GetPosition().y - (spr.GetFrameSizeY() / 2f * spr.GetScale().y);
        float y2 = spr.GetPosition().y + (spr.GetFrameSizeY() / 2f * spr.GetScale().y);

        float oy1 = postion.y - (frameSizeY / 2f * scale.y);
        float oy2 = postion.y + (frameSizeY / 2f * scale.y);

        if (y1 > oy1 && y1 < oy2)
            yCol = true;

        if (y2 > oy1 && y2 < oy2)
            yCol = true;



        if(xCol && yCol)
            return true;



        return false;
    }

    public Vectror2 GetScale()
    {
        return scale;
    }

    public int GetFrameSizeX()
    {
        return frameSizeX;
    }

    public int GetFrameSizeY()
    {
        return frameSizeY;
    }

    public void SetRotationToLookAtSprite(Sprite sprToLookAt)
    {

        float xDif = sprToLookAt.GetPosition().x - postion.x;
        float yDif = sprToLookAt.GetPosition().y - postion.y;

        double angle = Math.atan2(xDif, yDif);

        rotation = (float) Math.toDegrees(angle) * -1 + 180;

    }

}
