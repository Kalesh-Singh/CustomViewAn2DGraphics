package com.techexchange.mobileapps.lecture14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SimpleSimulation  extends View {
    private Paint rectColor = new Paint();
    private int rectX = 100;
    private int rectY = 200;
    private int rectW = 400;
    private int rectH = 300;

    private static final long DELAY_MS = 30;
    private static final float TIME_STEP = DELAY_MS / 1000.f;

    private int speedX = 200;      // 50px/s

    private int screenWidth;
    private int screenHeight;


    public SimpleSimulation(Context context) {
        super(context);
        rectColor.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Note canvas is passed to u sin te onDraw method.
        super.onDraw(canvas);

        canvas.drawRect(rectX, rectY,rectX + rectW, rectY + rectH, rectColor);

        update();

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();       // Forces a redraw.
    }

    private void update() {
        rectX += speedX * TIME_STEP;

        if (rectX >= (screenWidth - rectW)) {       // Right penetration
            speedX = -speedX;
            float depth = rectX + rectW - screenWidth;
            rectX -= 2 * depth;
        } else if (rectX <= 0) {
            speedX = -speedX;
            rectX = -rectX;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;
    }
}
