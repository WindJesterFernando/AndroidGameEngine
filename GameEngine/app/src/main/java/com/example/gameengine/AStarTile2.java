package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.Telephony;

import java.util.LinkedList;

public class AStarTile2 {

    private Sprite tileSprite;
    private int xCoord, yCoord;

    private int fCost = -1, gCost, hCost;

    private AStarTile2 connectingShortestPathTile;

    private boolean isWall;

    LinkedList<TextSprite> debugTexts;

    public AStarTile2(int XCoord, int YCoord)
    {
        xCoord = XCoord;
        yCoord = YCoord;
        tileSprite = ContentLoader.CreateNewSprite(SpriteID.aStarTile);
    }

    public void MakeTileWall()
    {
        tileSprite.SetColor(Color.BLACK);
        isWall = true;
    }

    public Boolean IsWall()
    {
        return isWall;
    }

    public void SetFCost(int FCost)
    {
        fCost = FCost;
    }

    public void SetGCost(int GCost)
    {
        gCost = GCost;
    }

    public void SetHCost(int HCost)
    {
        hCost = HCost;
    }

    public int GetFCost()
    {
        return fCost;
    }

    public int GetGCost()
    {
        return gCost;
    }

    public int GetHCost()
    {
        return hCost;
    }

    public void SetConnectingShortestPathTile(AStarTile2 ConnectingShortestPathTile)
    {
        connectingShortestPathTile = ConnectingShortestPathTile;
    }

    public void SetColor(int c)
    {
        tileSprite.SetColor(c);
    }

    public void Draw(Canvas canvas)
    {
        tileSprite.Draw(canvas);

        if(debugTexts != null) {
            for (TextSprite ts : debugTexts)
                ts.Draw(canvas);
        }

    }

    public void SetPostion(Vectror2 p)
    {
        tileSprite.SetPostion(p);
    }

    public int GetCoordX()
    {
        return xCoord;
    }

    public int GetCoordY()
    {
        return yCoord;
    }

    public void CreateDebugTextSprites(boolean onlyDisplayFCost) {

        if (fCost != -1) {
            if (debugTexts == null)
                debugTexts = new LinkedList<TextSprite>();
            else
                debugTexts.clear();

            TextSprite ts;

            if (onlyDisplayFCost) {
                ts = new TextSprite("" + (int) fCost, new Vectror2(tileSprite.GetPosition().x - 80, tileSprite.GetPosition().y - 0));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(80);
                debugTexts.addLast(ts);
            }
            else {
                ts = new TextSprite("f " + (int) fCost, new Vectror2(tileSprite.GetPosition().x - 80, tileSprite.GetPosition().y - 20));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(50);
                debugTexts.addLast(ts);

                ts = new TextSprite("g " + (int) gCost, new Vectror2(tileSprite.GetPosition().x - 80, tileSprite.GetPosition().y + 20));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(30);
                debugTexts.addLast(ts);


                ts = new TextSprite("h " + (int) hCost, new Vectror2(tileSprite.GetPosition().x - 80, tileSprite.GetPosition().y + 60));
                ts.SetColor(Color.BLACK);
                ts.SetTextSize(30);
                debugTexts.addLast(ts);

            }


        }
    }

    public AStarTile2 GetConnectingShortestPathTile()
    {
        return connectingShortestPathTile;
    }

}
