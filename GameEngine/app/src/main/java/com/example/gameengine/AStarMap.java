package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.LinkedList;

public class AStarMap {

    AStarTile[][] map;

    AStarTile startTile, endTile;

    LinkedList<AStarTile> open;
    LinkedList<AStarTile> closed;

    final int xTiles = 6, yTiles = 11;

    final int travelCost = 10, diagonalTravelCost = 14;

    public AStarMap()
    {


        map = new AStarTile[xTiles][yTiles];

        for(int i = 0; i < xTiles; i++)
        {
            for(int j = 0; j < yTiles; j++)
            {
                map[i][j] = new AStarTile(i, j);
                map[i][j].tile.SetPostion(new Vectror2(170 * i + 100, 170 * j + 100));
            }
        }

        startTile = map[0][0];
        startTile.tile.SetColor(Color.BLUE);

        endTile = map[xTiles - 1][yTiles - 1];
        endTile.tile.SetColor(Color.BLUE);

        open = new LinkedList<AStarTile>();
        closed = new LinkedList<AStarTile>();
        open.addLast(startTile);



        //Debug testing
        for(int i = 1; i < xTiles; i++)
            map[i][4].MakeWall();

        for(int i = yTiles-1; i > 5; i--)
            map[2][i].MakeWall();

//        for(int i = 0; i < xTiles-1; i++)
//            map[i][2].MakeWall();
//
//        map[3][yTiles - 5].MakeWall();
//        map[4][yTiles - 5].MakeWall();
//        map[4][yTiles - 3].MakeWall();
//        map[5][yTiles - 3].MakeWall();



    }

    public void Draw(Canvas canvas)
    {
        for(int i = 0; i < xTiles; i++)
        {
            for(int j = 0; j < yTiles; j++)
            {
                map[i][j].Draw(canvas);
            }
        }
    }

    public void ProcessNextMove()
    {
        Calculate(true);
    }

    private void Calculate(boolean displayDebug)
    {

        //while(true)  //loop while not found and open has values



        AStarTile current = GetTileWithLowestSumOfDistances(open);


        if(current == null)
        {
            Log.d("A*", "No Possible Path");
            return;
        }

        if (current == endTile)
        {
            Log.d("A*", "Path Found");

            while (current != null) {
                current.tile.SetColor(Color.BLUE);
                current = current.pathingParent;
            }

            return;
        }

        open.remove(current);

        closed.addLast(current);
        if(current != startTile && current != endTile)
            current.tile.SetColor(Color.GREEN);



        for(AStarTile n: GetAllNeighborNodes(current))
        {
            if(n.IsWall() || closed.contains(n))
                continue;

            if(!open.contains(n) || current.distanceFromStartingNode + GetDistanceBetweenTiles(current, n) < n.distanceFromStartingNode)
            {
                n.distanceFromStartingNode = current.distanceFromStartingNode + GetDistanceBetweenTiles(current, n);//GetDistanceBetweenTiles(startTile, n);
                n.distanceFromEndNode = GetDistanceBetweenTiles(endTile, n);
                n.sumOfDistances = n.distanceFromEndNode + n.distanceFromStartingNode;
                n.pathingParent = current;

                if(!open.contains(n)) {
                    if(n != startTile && n != endTile)
                        n.tile.SetColor(Color.YELLOW);
                    open.addLast(n);
                }
            }
        }




        if(displayDebug)
        {
            for(int i = 0; i < xTiles; i++)
            {
                for(int j = 0; j < yTiles; j++)
                {
                    map[i][j].CreateDebugTextSprites(false);
                }
            }
        }
    }


    private AStarTile GetTileWithLowestSumOfDistances(LinkedList<AStarTile> tiles)
    {
        AStarTile lowest = null;
        for(AStarTile t: tiles)
        {
            if(lowest == null)
                lowest = t;
            else if(lowest.sumOfDistances > t.sumOfDistances)
                lowest = t;
            else if(lowest.sumOfDistances == t.sumOfDistances && lowest.distanceFromEndNode > t.distanceFromEndNode)
                lowest = t;
        }

        return lowest;
    }

    private LinkedList<AStarTile> GetAllNeighborNodes(AStarTile tile)
    {
        LinkedList<AStarTile> neighbors = new LinkedList<AStarTile>();

        int x = tile.xCoord;
        int y = tile.yCoord;

        boolean hasLeftSide = (x> 0), hasRightSide = (x + 1 < xTiles);
        boolean hasTopSide = (y> 0), hasBottomSide = (y + 1 < yTiles);

        if(hasLeftSide) {
            neighbors.addLast(map[x - 1][y]);

            if(hasTopSide)
                neighbors.addLast(map[x - 1][y - 1]);
            if(hasBottomSide)
                neighbors.addLast(map[x - 1][y + 1]);
        }

        if(hasRightSide) {
            neighbors.addLast(map[x + 1][y]);

            if(hasTopSide)
                neighbors.addLast(map[x + 1][y - 1]);
            if(hasBottomSide)
                neighbors.addLast(map[x + 1][y + 1]);
        }

        if(hasTopSide)
            neighbors.addLast(map[x][y - 1]);
        if(hasBottomSide)
            neighbors.addLast(map[x][y + 1]);


        return neighbors;

    }

    private float GetDistanceBetweenTiles(AStarTile t1, AStarTile t2)
    {

//        //Direct Physical Distance Consideration
//
//        Vectror2 p1 = t1.tile.GetPosition();
//        Vectror2 p2 = t2.tile.GetPosition();
//        float distX = p1.x - p2.x;
//        float distY = p1.y - p2.y;
//
//        return (int)Math.sqrt(distX * distX + distY * distY);



        float sideMoveCost = 10;
        float diagonalMoveCost = 14;//(float)Math.sqrt(sideMoveCost * sideMoveCost + sideMoveCost * sideMoveCost);

        int travelX = Math.abs(t1.xCoord - t2.xCoord);
        int travelY = Math.abs(t1.yCoord - t2.yCoord);

        int numberOfDiagonalMoves;
        int numberOfSideMoves;

        if(travelX > travelY)
        {
            numberOfDiagonalMoves = travelY;
            numberOfSideMoves = travelX - travelY;
        }
        else
        {
            numberOfDiagonalMoves = travelX;
            numberOfSideMoves = travelY - travelX;
        }

        return numberOfDiagonalMoves * diagonalMoveCost +  sideMoveCost * numberOfSideMoves;



    }

}
