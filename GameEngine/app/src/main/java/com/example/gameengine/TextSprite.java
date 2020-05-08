package com.example.gameengine;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TextSprite extends AbstractSprite {

    Vectror2 topLeft;
    String text;

    public TextSprite(String Text, Vectror2 TopLeft)
    {
        super(0);
        text = Text;
        topLeft = TopLeft;
        localPaint.setTextSize(60);
    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawText(text, topLeft.x, topLeft.y, localPaint);
    }

    public void SetTopLeft(Vectror2 TopLeft) {
        topLeft = TopLeft;
    }

//    public void SetColor(int ColorConstant)
//    {
//        paint.setColor(ColorConstant);
//    }

    public void SetTextSize(int Size)
    {
        localPaint.setTextSize(Size);
    }

    @Override
    public void Update(long deltaTime)
    {

    }

    @Override
    public void Draw(Canvas canvas, Paint paint)
    {
        canvas.drawText(text, topLeft.x, topLeft.y, paint);
    }


}
