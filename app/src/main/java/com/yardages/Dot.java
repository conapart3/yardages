package com.yardages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Conal on 09/07/2015.
 */
public class Dot extends View {
    private final float x;
    private final float y;
    private final int r = 5;
    private final Paint paint;

    public Dot(Context context, float x, float y) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.WHITE);//ball
        this.x = x;
        this.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);//background
//        canvas.drawCircle(x,y,r,paint);
        canvas.drawCircle(200,200,100,paint);
    }
}
