package com.example.gameengine;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class ContentLoader {

    static private Context context;

    static public void Init(Context Context)
    {
        context = Context;
    }

    static public Sprite CreateNewSprite(int id)
    {
        Sprite spr = null;

        if(id == SpriteID.test1) {
            spr = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2), 5, 1, 0, 0);


            spr.SetFrame(2, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(0.25f, 0.25f));

            spr.SetColor(Color.GREEN);
            spr.SetOrderInLayer(4);
            spr.SetTag("0");
        }
        else if(id == SpriteID.test2)
        {
            spr = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2), 5, 1, 0,0);
            spr.SetFrame(3, 0);
            spr.SetPostion(new Vectror2(400,600));
            spr.SetScale(new Vectror2(1f, 1f));

            spr.SetColor(Color.RED);
            spr.SetOrderInLayer(4);
            spr.SetTag("1");
        }
        else if(id == SpriteID.test3)
        {
            AnimatingSprite as = new AnimatingSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2), 5, 1, 0,0, 500);
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
            spr = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2), 5, 1, 2,0);
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
            spr = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.gems2), 50, 10, 0, 0);


            spr.SetFrame(0, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(2.5f, 2.5f));

            spr.SetColor(Color.WHITE);

            spr.SetOrderInLayer(9);
            spr.SetTag("aStarTile");
        }
        else if(id == SpriteID.blackMage) {
            spr = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.mage2), 1, 1, 0, 0);


            spr.SetFrame(0, 0);
            spr.SetPostion(new Vectror2(100, 100));
            spr.SetScale(new Vectror2(2.5f, 2.5f));

            spr.SetColor(Color.WHITE);

            spr.SetOrderInLayer(9);
            spr.SetTag("blackMage");
        }

        return spr;
    }


}
