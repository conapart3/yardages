package com.yardages;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class ScatterView extends View {

    private Context context;
    private final int paintColor = Color.WHITE;
    private Paint paint;
    private ArrayList<PointF> pointList;
    private PointF teePoint, targetPoint;

    public ScatterView(Context context, AttributeSet attrs){
        super(context,attrs);

        this.context = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
//        init(context);
    }

    private void setupPaint(){
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
//        paint.setStrokeWidth(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(pointList != null) {
            paint.setColor(Color.WHITE);
            for (PointF p : pointList) {
                canvas.drawCircle(p.x, p.y, 15, paint);
            }
        }

        paint.setColor(Color.RED);
        canvas.drawCircle(targetPoint.x, targetPoint.y, 20, paint);

        paint.setColor(Color.GRAY);
        canvas.drawRect(teePoint.x-30, teePoint.y-30, teePoint.x+30, teePoint.y+30, paint);

    }

    public ArrayList<PointF> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<PointF> pointList) {
        this.pointList = pointList;
    }

    public PointF getTeePoint() {
        return teePoint;
    }

    public void setTeePoint(PointF teePoint) {
        this.teePoint = teePoint;
    }

    public PointF getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(PointF targetPoint) {
        this.targetPoint = targetPoint;
    }
//    private void init(Context context){
//        View inflate = LayoutInflater.from(context).inflate(R.layout.scatter_view, this, true);
//    }
}
