package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.LinkedList;

public class AStarMap2 {

    AStarTile2[][] map;

    LinkedList<AStarTile2> open, closed;

    AStarTile2 startTile, endTile;

    final int numberOfTilesX = 5, numberOfTilesY = 10;

    final int tavelCostDirect = 10, travelCostDiagonal = 14;


    public AStarMap2()
    {

        map = new AStarTile2[numberOfTilesX][numberOfTilesY];

        for(int i = 0; i < numberOfTilesX; i++)
        {
            for(int j = 0; j < numberOfTilesY; j++) {
                map[i][j] = new AStarTile2(i, j);
                map[i][j].SetPostion(new Vectror2(180 * i + 140, 180 * j + 140));
            }
        }

        startTile = map[0][0];
        endTile = map[numberOfTilesX-1][numberOfTilesY-1];

        startTile.SetColor(Color.BLUE);
        endTile.SetColor(Color.BLUE);

        open = new LinkedList<AStarTile2>();
        closed = new LinkedList<AStarTile2>();

        open.addLast(startTile);


        //GetAllNeighboringTiles(startTile);

//        Log.d("A*", "" + GetDistanceBetweenTiles(map[0][0], map[1][1]));
//        Log.d("A*", "" + GetDistanceBetweenTiles(startTile, endTile));
//        Log.d("A*", "" + GetDistanceBetweenTiles(map[0][0], map[3][1]));



        map[0][2].MakeTileWall();
        map[1][2].MakeTileWall();
        map[2][2].MakeTileWall();
        map[3][2].MakeTileWall();



        map[1][5].MakeTileWall();
        map[2][5].MakeTileWall();
        map[3][5].MakeTileWall();
        map[4][5].MakeTileWall();


    }

    public void Draw(Canvas canvas)
    {
        for(int i = 0; i < numberOfTilesX; i++)
        {
            for(int j = 0; j < numberOfTilesY; j++)
                map[i][j].Draw(canvas);
        }
    }

    public void ScreenTouched()
    {
        //Log.d("hit", "hit");
        PerformAStar();
    }

    public void PerformAStar()
    {

        if(open.size() == 0)
        {
            Log.d("A*", "No path found!");
            return;
        }


        AStarTile2 currentTile = GetTileWithLowestFCost(open);


        if(currentTile == endTile)
        {
            Log.d("A*", "Path has been found!");
            //Display Path

            while(currentTile != null) {

                currentTile.SetColor(Color.BLUE);
                currentTile = currentTile.GetConnectingShortestPathTile();
            }

            return;
        }

        open.remove(currentTile);
        closed.addLast(currentTile);
        //currentTile.SetColor(Color.RED);



        LinkedList<AStarTile2> neighbors = GetAllNeighboringTiles(currentTile);

        for(AStarTile2 n: neighbors)
        {
            if(closed.contains(n))
                continue;

            if(n.IsWall())
                continue;

            if(!open.contains(n) || n.GetGCost() > currentTile.GetGCost() + GetDistanceBetweenTiles(currentTile, n))
            {

                n.SetGCost(currentTile.GetGCost() + GetDistanceBetweenTiles(currentTile, n));
                n.SetHCost(GetDistanceBetweenTiles(n, endTile));
                n.SetFCost(n.GetGCost() + n.GetHCost());
                n.SetConnectingShortestPathTile(currentTile);

                if(!open.contains(n)) {
                    open.addLast(n);
                }

            }

        }



        for(int i = 0; i < numberOfTilesX; i++)
        {
            for(int j = 0; j < numberOfTilesY; j++) {
                map[i][j].CreateDebugTextSprites(false);

                if(!map[i][j].IsWall()) {
                    if (closed.contains(map[i][j]))
                        map[i][j].SetColor(Color.RED);
                    else if (open.contains(map[i][j]))
                        map[i][j].SetColor(Color.YELLOW);
                    else
                        map[i][j].SetColor(Color.WHITE);

                    if (map[i][j] == startTile || map[i][j] == endTile)
                        map[i][j].SetColor(Color.BLUE);
                }

            }
        }

    }

    public int GetDistanceBetweenTiles(AStarTile2 t1, AStarTile2 t2)
    {

        int t1X = t1.GetCoordX();
        int t1Y = t1.GetCoordY();
        int t2X = t2.GetCoordX();
        int t2Y = t2.GetCoordY();

        int difX = Math.abs(t1X - t2X);
        int difY = Math.abs(t1Y - t2Y);

        int directionalMoves, diagonalMoves;

        if(difX > difY)
        {
            directionalMoves = difX - difY;
            diagonalMoves = difY;
        }
        else
        {
            directionalMoves = difY - difX;
            diagonalMoves = difX;
        }


        return diagonalMoves * travelCostDiagonal + directionalMoves * tavelCostDirect;
    }

    public AStarTile2 GetTileWithLowestFCost(LinkedList<AStarTile2> list)
    {
        AStarTile2 lowest = null;

        for(AStarTile2 t: list)
        {
            if(lowest == null)
                lowest = t;
            else if (lowest.GetFCost() > t.GetFCost())
                lowest = t;
            else if(lowest.GetFCost() == t.GetFCost() && lowest.GetGCost() < t.GetGCost())
                lowest = t;
        }

        return lowest;
    }

    public LinkedList<AStarTile2> GetAllNeighboringTiles(AStarTile2 currentTile)
    {
        LinkedList<AStarTile2> neighbors = new LinkedList<AStarTile2>();


        if(currentTile.GetCoordX() -1 >= 0) {
            neighbors.addLast(map[currentTile.GetCoordX() - 1][currentTile.GetCoordY()]);

            if(currentTile.GetCoordY() -1 >= 0)
                neighbors.addLast(map[currentTile.GetCoordX() - 1][currentTile.GetCoordY() - 1]);

            if(currentTile.GetCoordY() +1 < numberOfTilesY)
                neighbors.addLast(map[currentTile.GetCoordX() - 1][currentTile.GetCoordY() + 1]);
        }

        if(currentTile.GetCoordY() +1 < numberOfTilesY)
            neighbors.addLast(map[currentTile.GetCoordX()][currentTile.GetCoordY()+1]);

        if(currentTile.GetCoordY() -1 >= 0)
            neighbors.addLast(map[currentTile.GetCoordX()][currentTile.GetCoordY()-1]);

        if(currentTile.GetCoordX() +1 < numberOfTilesX) {

            neighbors.addLast(map[currentTile.GetCoordX() + 1][currentTile.GetCoordY()]);

            if(currentTile.GetCoordY() -1 >= 0)
                neighbors.addLast(map[currentTile.GetCoordX() + 1][currentTile.GetCoordY() - 1]);

            if(currentTile.GetCoordY() +1 < numberOfTilesY)
                neighbors.addLast(map[currentTile.GetCoordX() + 1][currentTile.GetCoordY() + 1]);
        }

        return neighbors;
    }



}
