package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.LinkedList;

public class AStarTile {


    Sprite tile;
    LinkedList<TextSprite> debugTexts;

    //G Cost
    public float distanceFromStartingNode = -1;
    //H Cost
    public float distanceFromEndNode = -1;
    //F Cost
    public float sumOfDistances = -1;

    int xCoord, yCoord;
    public boolean isWall;
    AStarTile pathingParent;


    public AStarTile(int XCoord, int YCoord)
    {
        tile = ContentLoader.CreateNewSprite(SpriteID.aStarTile);
        xCoord = XCoord;
        yCoord = YCoord;

    }

    public void Draw(Canvas canvas)
    {
        tile.Draw(canvas);

        if(debugTexts != null)
        {
            for(TextSprite ts: debugTexts)
                ts.Draw(canvas);
        }

    }

    public void CreateDebugTextSprites(boolean onlyDisplaySumOfDistances)
    {
        if(distanceFromStartingNode != -1)
        {
            if(debugTexts == null)
                debugTexts = new LinkedList<TextSprite>();
            else
                debugTexts.clear();

            TextSprite ts;

            if(onlyDisplaySumOfDistances)
            {
                ts = new TextSprite("" + (int)sumOfDistances, new Vectror2(tile.GetPosition().x - 80, tile.GetPosition().y - 0));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(80);
                debugTexts.addLast(ts);
            }
            else
            {
                ts = new TextSprite("f " + (int)sumOfDistances, new Vectror2(tile.GetPosition().x - 80, tile.GetPosition().y - 20));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(50);
                debugTexts.addLast(ts);

                ts = new TextSprite("g " + (int)distanceFromStartingNode, new Vectror2(tile.GetPosition().x - 80, tile.GetPosition().y + 20));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(30);
                debugTexts.addLast(ts);


                ts = new TextSprite("h " + (int)distanceFromEndNode, new Vectror2(tile.GetPosition().x - 80, tile.GetPosition().y + 60));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(30);
                debugTexts.addLast(ts);

            }


        }
    }

    public void MakeWall()
    {
        isWall = true;
        tile.SetColor(Color.BLACK);
    }

    public boolean IsWall()
    {
        return isWall;
    }




}
