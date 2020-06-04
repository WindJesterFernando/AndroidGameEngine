package com.example.gameengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class ContentLoader {

    static private Context context;

    static private Bitmap bitmap1, bitmap2, bitmap3;

    static public void Init(Context Context)
    {
        context = Context;

        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mage2);
        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.demospritesheet);

    }

    static public Sprite CreateNewSprite(int id)
    {
        Sprite spr = null;

        if(id == SpriteID.test1) {
            spr = new Sprite(bitmap1, 5, 1, 0, 0);


            spr.SetFrame(2, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(0.25f, 0.25f));

            spr.SetColor(Color.GREEN);
            spr.SetOrderInLayer(4);
            spr.SetTag("0");
        }
        else if(id == SpriteID.test2)
        {
            spr = new Sprite(bitmap1, 5, 1, 0,0);
            spr.SetFrame(3, 0);
            spr.SetPostion(new Vectror2(400,600));
            spr.SetScale(new Vectror2(1f, 1f));

            spr.SetColor(Color.RED);
            spr.SetOrderInLayer(4);
            spr.SetTag("1");
        }
        else if(id == SpriteID.test3)
        {
            AnimatingSprite as = new AnimatingSprite(bitmap1, 5, 1, 0,0, 500);
            as.SetFrame(3, 0);
            as.SetPostion(new Vectror2(400,600));
            as.SetScale(new Vectror2(0.25f, 0.25f));

            as.SetColor(Color.WHITE);
            as.SetOrderInLayer(6);

            as.AddFrame(new AnimationFrame(1,0));
            as.AddFrame(new AnimationFrame(2,0));
            as.AddFrame(new AnimationFrame(3,0));
            as.AddFrame(new AnimationFrame(4,0));

            as.SetTag("2");

            return as;
        }
        else if(id == SpriteID.test4)
        {
            spr = new Sprite(bitmap1, 5, 1, 2,0);
            //spr.SetFrame(3, 0);
            spr.SetPostion(new Vectror2(400,900));
            spr.SetScale(new Vectror2(0.25f, 0.25f));

            //spr.SetColor(Color.RED);
            spr.SetOrderInLayer(7);
            spr.SetTag("3");

            //spr.SetRadiusForCicleCollisionDetection(70f);
            spr.SetForBoundingBoxCollisionDetection();

        }

        else if(id == SpriteID.aStarTile) {
            spr = new Sprite(bitmap1, 50, 10, 0, 0);


            spr.SetFrame(0, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(2.5f, 2.5f));

            spr.SetColor(Color.WHITE);

            spr.SetOrderInLayer(9);
            spr.SetTag("aStarTile");
        }
        else if(id == SpriteID.blackMage) {
            spr = new Sprite(bitmap2, 1, 1, 0, 0);


            spr.SetFrame(0, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(2.5f, 2.5f));

            spr.SetColor(Color.WHITE);

            spr.SetOrderInLayer(9);
            spr.SetTag("blackMage");
        }

        else if(id == SpriteID.DemoSprite)
        {
            StateAnimatingSprite sas = new StateAnimatingSprite(bitmap3, 6, 4, 0,0, 120);
            sas.SetFrame(0, 0);
            sas.SetPostion(new Vectror2(400,1200));
            sas.SetScale(new Vectror2(2f, 2f));

            sas.SetColor(Color.WHITE);
            sas.SetOrderInLayer(8);


            sas.AddFrameBasedOnState(new AnimationFrame(0,0), SpriteStates.WalkingDown);
            sas.AddFrameBasedOnState(new AnimationFrame(1,0), SpriteStates.WalkingDown);
            sas.AddFrameBasedOnState(new AnimationFrame(2,0), SpriteStates.WalkingDown);
            sas.AddFrameBasedOnState(new AnimationFrame(3,0), SpriteStates.WalkingDown);
            sas.AddFrameBasedOnState(new AnimationFrame(4,0), SpriteStates.WalkingDown);
            sas.AddFrameBasedOnState(new AnimationFrame(5,0), SpriteStates.WalkingDown);

            sas.AddFrameBasedOnState(new AnimationFrame(0,1), SpriteStates.WalkingUp);
            sas.AddFrameBasedOnState(new AnimationFrame(1,1), SpriteStates.WalkingUp);
            sas.AddFrameBasedOnState(new AnimationFrame(2,1), SpriteStates.WalkingUp);
            sas.AddFrameBasedOnState(new AnimationFrame(3,1), SpriteStates.WalkingUp);
            sas.AddFrameBasedOnState(new AnimationFrame(4,1), SpriteStates.WalkingUp);
            sas.AddFrameBasedOnState(new AnimationFrame(5,1), SpriteStates.WalkingUp);

            sas.AddFrameBasedOnState(new AnimationFrame(0,2), SpriteStates.WalkingRight);
            sas.AddFrameBasedOnState(new AnimationFrame(1,2), SpriteStates.WalkingRight);
            sas.AddFrameBasedOnState(new AnimationFrame(2,2), SpriteStates.WalkingRight);
            sas.AddFrameBasedOnState(new AnimationFrame(3,2), SpriteStates.WalkingRight);
            sas.AddFrameBasedOnState(new AnimationFrame(4,2), SpriteStates.WalkingRight);
            sas.AddFrameBasedOnState(new AnimationFrame(5,2), SpriteStates.WalkingRight);

            sas.AddFrameBasedOnState(new AnimationFrame(0,3), SpriteStates.WalkingLeft);
            sas.AddFrameBasedOnState(new AnimationFrame(2,3), SpriteStates.WalkingLeft);
            sas.AddFrameBasedOnState(new AnimationFrame(3,3), SpriteStates.WalkingLeft);
            sas.AddFrameBasedOnState(new AnimationFrame(4,3), SpriteStates.WalkingLeft);
            sas.AddFrameBasedOnState(new AnimationFrame(1,3), SpriteStates.WalkingLeft);
            sas.AddFrameBasedOnState(new AnimationFrame(5,3), SpriteStates.WalkingLeft);

            sas.AddFrameBasedOnState(new AnimationFrame(2,0), SpriteStates.IdleDown);
            sas.AddFrameBasedOnState(new AnimationFrame(2,1), SpriteStates.IdleUp);
            sas.AddFrameBasedOnState(new AnimationFrame(2,2), SpriteStates.IdleRight);
            sas.AddFrameBasedOnState(new AnimationFrame(2,3), SpriteStates.IdleLeft);

            sas.SetState(SpriteStates.WalkingUp);

//            as.AddFrame(new AnimationFrame(1,0));
//            as.AddFrame(new AnimationFrame(2,0));
//            as.AddFrame(new AnimationFrame(3,0));
//            as.AddFrame(new AnimationFrame(4,0));
//            as.AddFrame(new AnimationFrame(5,0));

            sas.SetTag("DemoSprite");

            sas.SetForBoundingBoxCollisionDetection();

            return sas;
        }

        return spr;
    }


}
