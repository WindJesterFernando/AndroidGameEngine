package com.example.gameengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DebugUtils;
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

    AStarMap2 aStarMap2;

    LineSprite inputLine;

    TouchTracker touchTracker;

    AbstractScene currentScene;
    BattleScene battleScene;
    TitleScene titleScene;
    AdventureScene adventureScene;


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
        avatar.SetPostion(new Vectror2(700, 800));
        avatar.SetTag("avatar");

        //lookAtAvatar.SetScale(new Vectror2(0.33f, 0.33f));
        avatar.SetScale(new Vectror2(0.33f, 0.33f));
        //avatar.SetCollisionResponseAction(CollisionResponseActions.SlideAlong);
        //avatar.SetSlideFriction(0.25f);
        avatar.SetCollisionResponseAction(CollisionResponseActions.BounceOffOf);
        avatar.SetBounceAtude(30);

        //aStarMap = new AStarMap();

        //aStarMap2 = new AStarMap2();

        //AbstractScene currentScene;
        battleScene = new BattleScene();
        titleScene = new TitleScene();
        adventureScene = new AdventureScene();

        currentScene = battleScene;

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

        canvas.drawColor(Color.BLUE);


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

        if(aStarMap2 != null)
            aStarMap2.Draw(canvas);

        if(inputLine != null)
            inputLine.Draw(canvas);


//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//
//
//
//        if(touchTracker != null)
//        {
//            for(Vectror2 p: touchTracker.movingPosition)
//                canvas.drawPoint(p.x, p.y, paint);
//        }



//
//        for (int i = 0; i < 200 ; i++)
//            canvas.drawPoint(100 + i, 400, paint);
//
//
//
//        float radius = 300;
//        Vectror2 centerPoint = new Vectror2(500, 400);
//        LinkedList<Vectror2> circlePoints = new LinkedList<Vectror2>();
//
//        for(float angle = 0; angle < 360; angle = angle + 30.0f)
//        {
//            //Log.d("deubg", "angle = " + angle);
//            Vectror2 p = new Vectror2(0,0);
//            p.x = (float)Math.sin(Math.toRadians(angle)) * radius + centerPoint.x;
//            p.y = -(float)Math.cos(Math.toRadians(angle)) * radius + centerPoint.y;
//
//            //Math.toDegrees(
//
//            //Log.d("deubg", "p = " + p.ToString());
//
//            circlePoints.addLast(p);
//        }



        //Debug For Circle Gestures

//        Paint paint4 = new Paint();
//        paint4.setColor(Color.GRAY);
//
//
//        canvas.drawCircle(500, 400,300f*1.5f, paint4);
//
//        LinkedList<Vectror2> circlePoints = GetCirclePoints(300f, new Vectror2(500, 400), true, false, 12);
//
//
//        Paint paint2 = new Paint();
//        paint2.setColor(Color.RED);
//
//        Paint paint3 = new Paint();
//        paint3.setColor(Color.BLUE);
//
//        int count = 0;
//        for(Vectror2 p: circlePoints) {
//            //canvas.drawPoint(p.x, p.y, paint);
//            if(count == 0)
//            canvas.drawCircle(p.x, p.y,100, paint2);
//            else if(count == 1)
//                canvas.drawCircle(p.x, p.y,100, paint3);
//            else
//                canvas.drawCircle(p.x, p.y,100, paint);
//
//            count++;
//        }
//
//        canvas.drawCircle(500, 400,300f/2f, paint);


        currentScene.Draw(canvas);

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


//        Log.d("Collision", "--------");
//        Log.d("Collision", "Check Start");

        for (AbstractSprite s: sprites) {
            if(s instanceof Sprite)// && s != avatar) {
            {
                for (AbstractSprite s2: sprites) {

                    if(s != s2)
                    {
                        ((Sprite) s2).CheckForCircleCollision((Sprite)s);
                        ((Sprite) s2).CheckForBoundingBoxCollision((Sprite)s);
                    }
                }
                //((Sprite) s).CheckForCircleCollision(avatar);
                //((Sprite) s).CheckForBoundingBoxCollision(avatar);

//                if (((Sprite) s).CheckForCircleCollision(avatar))
//                    Log.d("Collision", "Circle Collision Was Detected");
//                if (((Sprite) s).CheckForBoundingBoxCollision(avatar))
//                    Log.d("Collision", "Bounding Box Was Detected");
            }
        }

        for (AbstractSprite s: onStageSprites) {
            if(s instanceof Sprite)// && s != avatar)
            {
                for (AbstractSprite s2: onStageSprites) {

                    if(s != s2)
                    {
                        ((Sprite) s2).CheckForCircleCollision((Sprite)s);
                        ((Sprite) s2).CheckForBoundingBoxCollision((Sprite)s);
                    }
                }

//                ((Sprite) avatar).CheckForCircleCollision((Sprite)s);
//                ((Sprite) avatar).CheckForBoundingBoxCollision((Sprite)s);

//                if (((Sprite) s).CheckForCircleCollision(avatar))
//                    Log.d("Collision", "Circle Collision Was Detected");
//                if (((Sprite) s).CheckForBoundingBoxCollision(avatar))
//                    Log.d("Collision", "Bounding Box Was Detected");
            }
        }

//        for (AbstractSprite s: onStageSprites) {
//            if(s instanceof Sprite && s != avatar)
//            {
//
//
//                ((Sprite) avatar).CheckForCircleCollision((Sprite)s);
//                ((Sprite) avatar).CheckForBoundingBoxCollision((Sprite)s);
//
//                if (((Sprite) s).CheckForCircleCollision(avatar))
//                    ;//Log.d("Collision", "Circle Collision Was Detected");
//                if (((Sprite) s).CheckForBoundingBoxCollision(avatar))
//                    ;//Log.d("Collision", "Bounding Box Was Detected");
//            }
//        }



        for (AbstractSprite s: onStageSprites) {
            if(s instanceof Sprite)
            {
                ((Sprite)s).ApplySpeedToPosition();
            }
        }
        for (AbstractSprite s: sprites) {
            if(s instanceof Sprite)
            {
                ((Sprite)s).ApplySpeedToPosition();
            }
        }


        currentScene.Update(deltaTime);

        //lookAtAvatar.SetRotationToLookAtSprite(avatar);

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

        if(aStarMap2 != null)
            aStarMap2.ScreenTouched();

        //Log.d("touch down", x + "," + y);

        inputLine = new LineSprite(new Vectror2(x,y), new Vectror2(x, y));

        touchTracker = new TouchTracker();
        touchTracker.movingPosition.addLast(new Vectror2(x,y));

        currentScene.TouchDown(x,y);

    }

    public void TouchMove(float x, float y)
    {
        //Log.d("touch move", x + "," + y);

        inputLine.bottomRight = new Vectror2(x, y);
        touchTracker.movingPosition.addLast(new Vectror2(x,y));

        currentScene.TouchMove(x,y);
    }

    public void TouchUp(float x, float y)
    {
        //Log.d("touch up", x + "," + y);

        sprites.addLast(inputLine);
        touchTracker.movingPosition.addLast(new Vectror2(x,y));
        EvaluateTouchTrackerForGestures();

        currentScene.TouchUp(x,y);
    }

    private void EvaluateTouchTrackerForGestures()
    {


        if(CheckForCircleTouchInput(CircleID.LargeCircle))
            CircleGesture(CircleID.LargeCircle);
        if(CheckForCircleTouchInput(CircleID.LargeCircleUpwards))
            CircleGesture(CircleID.LargeCircleUpwards);

        if(CheckForCircleTouchInput(CircleID.SmallCircle))
            CircleGesture(CircleID.SmallCircle);
        if(CheckForCircleTouchInput(CircleID.SmallCircleUpwards))
            CircleGesture(CircleID.SmallCircleUpwards);



        float xDif = touchTracker.movingPosition.getLast().x - touchTracker.movingPosition.getFirst().x;
        float yDif = touchTracker.movingPosition.getLast().y - touchTracker.movingPosition.getFirst().y;

        double angle = Math.atan2(xDif, yDif);

        float rotation = (float) Math.toDegrees(angle) * -1 + 180;

        //Log.d("Gesture", "roation = " + rotation);

        long timeInMilliSeconds = System.currentTimeMillis() - touchTracker.startTime;

        //Log.d("Gesture", "time = " + timeInMilliSeconds);

        if(rotation > 337.5f || rotation < 22.5f) {
            //Log.d("Gesture", "up");
            SwipeUp();
        }
        else if(rotation > 22.5f && rotation < 67.5f) {
            //Log.d("Gesture", "up and right");
            SwipeUpRight();
        }
        else if(rotation > 67.5f && rotation < 112.5f) {
            //Log.d("Gesture", "right");
            SwipeRight();
        }
        else if(rotation > 112.5f && rotation < 157.5f) {
            //Log.d("Gesture", "down and right");
            SwipeDownRight();
        }
        else if(rotation > 157.5f && rotation < 202.5f) {
            //Log.d("Gesture", "down");
            SwipeDown();
        }
        else if(rotation > 202.5f && rotation < 247.5f) {
            //Log.d("Gesture", "down and left");
            SwipeDownLeft();
        }
        else if(rotation > 247.5f && rotation < 292.5f) {
            //Log.d("Gesture", "left");
            SwipeLeft();
        }
        else if(rotation > 292.5f && rotation < 337.5f) {
            //Log.d("Gesture", "up and left");
            SwipeUpLeft();
        }


//
//        float absX = Math.abs(difX);
//        float absY = Math.abs(difY);
//
//        if(absX > absY) {
//            if (difX > 100)
//                Log.d("Gesture", "Swipe Right");
//            if (difX < -100)
//                Log.d("Gesture", "Swipe Left");
//        }
//        else if (absY > absX) {
//            if (difY > 100)
//                Log.d("Gesture", "Swipe Down");
//            if (difY < -100)
//                Log.d("Gesture", "Swipe Up");
//        }



    }

    public void SwipeUp()
    {
        inputLine.SetColor(Color.RED);
        currentScene.SwipeUp();
    }
    public void SwipeUpRight()
    {
        inputLine.SetColor(Color.GREEN);
        currentScene.SwipeUpRight();
    }
    public void SwipeRight()
    {
        inputLine.SetColor(Color.BLACK);
        currentScene.SwipeRight();
    }
    public void SwipeDownRight()
    {
        inputLine.SetColor(Color.CYAN);
        currentScene.SwipeDownRight();
    }
    public void SwipeDown()
    {
        inputLine.SetColor(Color.WHITE);
        currentScene.SwipeDown();
    }
    public void SwipeDownLeft()
    {
        inputLine.SetColor(Color.YELLOW);
        currentScene.SwipeDownLeft();
    }
    public void SwipeLeft()
    {
        inputLine.SetColor(Color.MAGENTA);
        currentScene.SwipeLeft();
    }
    public void SwipeUpLeft()
    {
        inputLine.SetColor(Color.DKGRAY);
        currentScene.SwipeUpLeft();
    }

    public void CircleGesture(int circleID)
    {
        if(circleID == CircleID.LargeCircle)
            Log.d("Gesture", "--LargeCircleDetected--");
        else if(circleID == CircleID.LargeCircleUpwards)
            Log.d("Gesture", "--LargeCircleUpwardsDetected--");
        else if(circleID == CircleID.SmallCircle)
            Log.d("Gesture", "--SmallCircleDetected--");
        else if(circleID == CircleID.SmallCircleUpwards)
            Log.d("Gesture", "--SmallCircleUpwardsDetected--");

        currentScene.CircleGesture(circleID);
    }

    private boolean CheckForCircleTouchInput(int circleID)
    {

        //Log.d("Gesture", "Starting Circle Gesture Check " + circleID);


        //touchTracker.movingPosition

        float bottomMostPoint = 0;
        float topMostPoint = 99999;

        float rightMostPos = 0;
        float leftMostPos = 9999;

        for (Vectror2 v: touchTracker.movingPosition)
        {
            if(v.y > bottomMostPoint)
                bottomMostPoint = v.y;
            if(v.y < topMostPoint)
                topMostPoint = v.y;

            if(rightMostPos < v.x)
                rightMostPos = v.x;
            if(leftMostPos > v.x)
                leftMostPos = v.x;
        }


        float radius = 300f;
        Vectror2 centerPoint = null;

        if(circleID == CircleID.SmallCircleUpwards || circleID == CircleID.SmallCircle)
            radius = 150f;

        if(circleID == CircleID.LargeCircle || circleID == CircleID.SmallCircle)
            centerPoint = new Vectror2(touchTracker.movingPosition.getFirst().x, (touchTracker.movingPosition.getFirst().y + bottomMostPoint) /2);
        else if(circleID == CircleID.LargeCircleUpwards || circleID == CircleID.SmallCircleUpwards)
            centerPoint = new Vectror2(touchTracker.movingPosition.getFirst().x, (touchTracker.movingPosition.getFirst().y + topMostPoint) /2);

//        if(circleID == CircleID.LargeCircle ||circleID == CircleID.LargeCircleUpwards)
//            radius = 300f;
//        else if(circleID == CircleID.SmallCircle ||circleID == CircleID.SmallCircleUpwards)
//            radius = 150f;

        //= new Vectror2((leftMostPos + rightMostPos) /2, (touchTracker.movingPosition.getFirst().y + bottomMostPoint) /2); //new Vectror2(500, 400);
        //centerPoint = new Vectror2(500, 400);

        //Log.d("Gesture", "centerPoint = " + centerPoint.ToString());

        boolean isUpwards = (circleID == CircleID.SmallCircleUpwards || circleID == CircleID.LargeCircleUpwards);

        LinkedList<Vectror2> circlePoints = GetCirclePoints(radius, centerPoint, true, isUpwards, 12);

        boolean isComplete = true;
        boolean found = false;

        for (Vectror2 circlePoint: circlePoints)
        {
            found = false;

            for (Vectror2 touchPoint: touchTracker.movingPosition)
            {
                float distX = circlePoint.x - touchPoint.x;
                float distY = circlePoint.y - touchPoint.y;
                double hyp = Math.sqrt(distX * distX + distY * distY);

                if(hyp < radius/3f)
                    found = true;
            }

            if(!found) {
                isComplete = false;
            }
        }

//        if(found)
//            Log.d("Gesture", "initial circle found");

        for (Vectror2 touchPoint: touchTracker.movingPosition)
        {
            //centerPoint
            //radius

            float distX = touchPoint.x - centerPoint.x;
            float distY = touchPoint.y - centerPoint.y;

            double hyp = Math.sqrt(distX * distX + distY * distY);

            if(hyp < radius/2f)
            {
                //Log.d("Gesture", "Center of circle violated");
                isComplete = false;
            }


            if(hyp > radius * 1.5f)
            {
                //Log.d("Gesture", "Outside of circle violated");
                isComplete = false;
            }


        }


        if(isComplete) {
            //Log.d("Gesture", "circle detected");
            return true;
        }
        //else
            //Log.d("Gesture", "circle NOT detected");



        return false;
    }

    private LinkedList<Vectror2> GetCirclePoints(float radius, Vectror2 centerPoint, boolean clockwise, boolean movingUp, int numberOfPoints)
    {

        LinkedList<Vectror2> circlePoints = new LinkedList<Vectror2>();

        float inc = 360f/ (float)numberOfPoints;

        if(clockwise) {
            for (float angle = 0; angle < 360; angle = angle + inc) {
                Vectror2 p = new Vectror2(0, 0);
                p.x = (float) Math.sin(Math.toRadians(angle)) * radius + centerPoint.x;
                if(movingUp)
                    p.y = (float) Math.cos(Math.toRadians(angle)) * radius + centerPoint.y;
                else
                    p.y = -(float) Math.cos(Math.toRadians(angle)) * radius + centerPoint.y;

                circlePoints.addLast(p);
            }
        }
        else
        {
            for (float angle = 360; angle > 0; angle = angle - inc) {
                Vectror2 p = new Vectror2(0, 0);
                p.x = (float) Math.sin(Math.toRadians(angle)) * radius + centerPoint.x;
                if(movingUp)
                    p.y = (float) Math.cos(Math.toRadians(angle)) * radius + centerPoint.y;
                else
                    p.y = -(float) Math.cos(Math.toRadians(angle)) * radius + centerPoint.y;

                circlePoints.addLast(p);
            }
        }

        return circlePoints;


    }

}
