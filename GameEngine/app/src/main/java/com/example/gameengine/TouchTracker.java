package com.example.gameengine;

import java.util.LinkedList;

public class TouchTracker {

//    public Vectror2 startPosition;
//    public Vectror2 endPosition;
    public LinkedList<Vectror2> movingPosition;
    public long startTime;

    public TouchTracker()
    {
        movingPosition = new LinkedList<Vectror2>();
        startTime = System.currentTimeMillis();
    }

}
