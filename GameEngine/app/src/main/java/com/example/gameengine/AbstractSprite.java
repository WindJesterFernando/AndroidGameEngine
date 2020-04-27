package com.example.gameengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractSprite {

    protected int orderInDrawLayer;
    protected Paint paint;
    protected String tag;

    public AbstractSprite(int OrderInDrawLayer)
    {
        paint = new Paint();
        paint.setColor(Color.WHITE);
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
        paint.setColor(ColorConstant);
    }

    abstract public void Draw(Canvas canvas);
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
