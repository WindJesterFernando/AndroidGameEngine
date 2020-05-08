package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineSprite extends AbstractSprite {

    Vectror2 topLeft, bottomRight;


    public LineSprite(Vectror2 TopLeft, Vectror2 BottomRight)
    {
        super(0);
        topLeft = TopLeft;
        bottomRight = BottomRight;

    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawLine(topLeft.x,topLeft.y, bottomRight.x , bottomRight.y, localPaint);
    }

    public void SetTopLeft(Vectror2 TopLeft) {
        topLeft = TopLeft;
    }

    public void SetBottomRight(Vectror2 BottomRight) {
        bottomRight = BottomRight;
    }

    @Override
    public void Update(long deltaTime)
    {

    }

    @Override
    public void Draw(Canvas canvas, Paint paint)
    {
        canvas.drawLine(topLeft.x,topLeft.y, bottomRight.x , bottomRight.y, paint);
    }


}
