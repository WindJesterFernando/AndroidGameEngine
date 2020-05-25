package com.example.gameengine;

public class GameLoopThread extends Thread {
    private Thread t;
    private String threadName;
    MainActivity mainActivity;

    long startTime;
    long lastUpdateTime;
    boolean gameOn;

    GameLoopThread(MainActivity MainActivity) {
        threadName = "Game Loop Thread";
        System.out.println("Creating " +  threadName );
        mainActivity = MainActivity;
        startTime = System.currentTimeMillis();
        lastUpdateTime = startTime;
    }

    public void run() {

        while (true) {
            System.out.println("Running " + threadName);
            try {
                long deltaTime = System.currentTimeMillis() - lastUpdateTime;
                lastUpdateTime = System.currentTimeMillis();
                mainActivity.GameThreadUpdate(deltaTime);
//            for(int i = 4; i > 0; i--) {
//                System.out.println("Thread: " + threadName + ", " + i);
//                // Let the thread sleep for a while.
//                Thread.sleep(50);
//            }
            } catch (Exception e) {//InterruptedException e) {
                System.out.println("Thread " + threadName + " interrupted.");
            }
        }
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
