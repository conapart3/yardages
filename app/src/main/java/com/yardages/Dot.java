package com.yardages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Conal on 09/07/2015.
 */
public class Dot extends View {
    private final float x;
    private final float y;
    private final int r = 5;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Dot(Context context, float x, float y) {
        super(context);
        mPaint.setColor(0xFFFF0000);
        this.x = x;
        this.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,r,mPaint);
    }
}
