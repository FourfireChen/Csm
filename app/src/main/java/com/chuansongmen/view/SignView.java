package com.chuansongmen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignView extends View {
    private float mX, mY;
    private Paint mPaint = new Paint();
    private Path path = new Path();
    private Canvas cacheCanvas;
    private Bitmap cacheBitmap;
    private int paintWidth = 10;
    private int paintColor = Color.BLACK;
    private int backColor = Color.TRANSPARENT;


    public SignView(Context context) {
        super(context);

    }

    public SignView(Context context,
                    @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SignView(Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置描边
        mPaint.setStyle(Paint.Style.STROKE);
        //设置宽度
        mPaint.setStrokeWidth(paintWidth);
        //设置颜色
        mPaint.setColor(paintColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(cacheBitmap, 0, 0, mPaint);
        canvas.drawPath(path, mPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里创建了一个bitmap用来保存绘制图形
        //并且要通过canvas来操作绘制
        //然后先把背景颜色画了
        cacheBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
        cacheCanvas.drawColor(backColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                //这里是通过canvas来把路径画进bitmap的，设置在onsizechange中。
                cacheCanvas.drawPath(path, mPaint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    private void touchMove(MotionEvent event) {
        //获取本次的点击坐标
        float thisX = event.getX();
        float thisY = event.getY();
        //获取和上一次点击的差值
        float dx = Math.abs(thisX - mX);
        float dy = Math.abs(thisY - mY);
        //在一定范围内触发重绘贝塞尔曲线
        if (dx >= 2 || dy >= 2){
            //算贝塞尔曲线的操作点
            float oX = (mX + thisX) / 2;
            float oY = (mY + thisY) / 2;
            path.quadTo(mX, mY, oX, oY);
            mX = thisX;
            mY = thisY;
        }
    }

    private void touchDown(MotionEvent event) {
        path.reset();
        mX = event.getX();
        mY = event.getY();
        path.moveTo(mX, mY);
    }

    public void clear(){
        if (cacheCanvas != null){
            cacheCanvas.drawColor(backColor, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }

    public Bitmap getBitmap(){
        return cacheBitmap;
    }

    public int getPaintWidth() {
        return paintWidth;
    }

    public void setPaintWidth(int paintWidth) {
        this.paintWidth = paintWidth;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }
}
