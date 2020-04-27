package com.example.gameengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

import java.util.LinkedList;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {

    Context context;

    LinkedList<AbstractSprite> sprites;
    Sprite avatar;
    Sprite lookAtAvatar;

    LinkedList<AbstractSprite> onStageSprites;

    AStarMap aStarMap;

    public GameCanvas(Context Context, int screenLength, int screenHeight) {
        super(Context);

        getHolder().addCallback(this);

        context = Context;
        ContentLoader.Init(context);

        sprites = new LinkedList<AbstractSprite>();

//        AbstractSprite as;
//
//        sprites.add(as = ContentLoader.CreateNewSprite(0));
//        as.SetOrderInLayer(9);
//        sprites.add(ContentLoader.CreateNewSprite(1));
//
//        sprites.add(as = new LineSprite(new Vectror2(0,0), new Vectror2(screenLength, screenHeight)));
//        as.SetOrderInLayer(8);
//        sprites.add(new LineSprite(new Vectror2(screenLength,0), new Vectror2(0, screenHeight)));
//
//        sprites.add(new TextSprite("Hello World", new Vectror2(100, 100)));
//
//        sprites.add(avatar = ContentLoader.CreateNewSprite(2));
//        avatar.SetOrderInLayer(10);


        onStageSprites = new LinkedList<AbstractSprite>();

        onStageSprites.add(ContentLoader.CreateNewSprite(SpriteID.test1));
        //onStageSprites.add(ContentLoader.CreateNewSprite(SpriteID.test2));
        onStageSprites.add(lookAtAvatar = ContentLoader.CreateNewSprite(SpriteID.test4));
        onStageSprites.add(avatar = ContentLoader.CreateNewSprite(SpriteID.test4));



        //aStarMap = new AStarMap();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("surface", "created");

        //this.getHolder()
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Empty
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Empty

        Log.d("surface", "destroyed");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.GRAY);


        for (int layer = 0 ; layer <= Constants.NumberOfLayers ; layer++) {

            for (AbstractSprite s : sprites) {
                if(s.GetOrderInDrawLayer() == layer)
                    s.Draw(canvas);
            }

            if(layer == Constants.StageSpriteLayer)
            {
                for (AbstractSprite s : onStageSprites) {
                        s.Draw(canvas);
                }
            }

        }

        if(aStarMap != null)
            aStarMap.Draw(canvas);

    }

    public void Update(long deltaTime)
    {
        //spr.SetPostion(new Vectror2(spr.GetPosition().x + (0.125f * (float)deltaTime), spr.GetPosition().y));

        for (AbstractSprite s: sprites) {
            s.Update(deltaTime);
        }

        for (AbstractSprite s: onStageSprites) {
            s.Update(deltaTime);
        }



        LinkedList<AbstractSprite> sorted = new LinkedList<>();

        while (onStageSprites.size() > 0)
        {
            int botPos = 99999;
            AbstractSprite next = null;
            for (AbstractSprite s : onStageSprites)
            {
                if(s.GetBottomPosition() < botPos)
                {
                    next = s;
                    botPos = s.GetBottomPosition();
                }
            }

            if(next != null) {
                sorted.addLast(next);
                onStageSprites.remove(next);
            }
        }

        onStageSprites = sorted;


        Log.d("Collision", "--------");
        Log.d("Collision", "Check Start");

        for (AbstractSprite s: sprites) {
            if(s instanceof Sprite && s != avatar) {
                if (((Sprite) s).CheckForCircleCollision(avatar))
                    Log.d("Collision", "Circle Collision Was Detected");
                if (((Sprite) s).CheckForBoundingBoxCollision(avatar))
                    Log.d("Collision", "Bounding Box Was Detected");
            }
        }

        for (AbstractSprite s: onStageSprites) {
            if(s instanceof Sprite && s != avatar)
            {
                if (((Sprite) s).CheckForCircleCollision(avatar))
                    Log.d("Collision", "Circle Collision Was Detected");
                if (((Sprite) s).CheckForBoundingBoxCollision(avatar))
                    Log.d("Collision", "Bounding Box Was Detected");
            }
        }

        lookAtAvatar.SetRotationToLookAtSprite(avatar);

        //avatar.SetRotation(avatar.GetRotation() + (0.25f * (float)deltaTime));

    }

    public void TouchDown(float x, float y)
    {
        //Teleport
        //spr2.SetPostion(new Vectror2(x, y));


        //spr2.PerformLerp(spr2.GetPosition(), new Vectror2(x, y), 4000);
        avatar.PerformMoveTo(new Vectror2(x, y), 5f);

        //avatar.PerformLerp(avatar.GetPosition(), new Vectror2(x, y), 4000);


        if(aStarMap != null)
            aStarMap.ProcessNextMove();

    }


}
