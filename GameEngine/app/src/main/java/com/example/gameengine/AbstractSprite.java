package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractSprite {

    protected int orderInDrawLayer;
    protected Paint localPaint;
    protected String tag;

    public AbstractSprite(int OrderInDrawLayer)
    {
        localPaint = new Paint();
        localPaint.setColor(Color.WHITE);
    }

    public int GetOrderInDrawLayer()
    {
        return orderInDrawLayer;
    }

    public void SetOrderInLayer(int OrderInLayer)
    {
        orderInDrawLayer = OrderInLayer;
    }

    public void SetColor(int ColorConstant)
    {
        localPaint.setColor(ColorConstant);
    }

    abstract public void Draw(Canvas canvas);
    abstract public void Draw(Canvas canvas, Paint paint);
    abstract public void Update(long deltaTime);

    public int GetBottomPosition()
    {
        return 0;
    }

    public void SetTag(String Tag)
    {
        tag = Tag;
    }

    public String GetTag()
    {
        return tag;
    }

}
