package luanvan.luanvantotnghiep.CustomTree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Bamboo extends View {
    public Bamboo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void drawOvalAndArrow(Canvas canvas){

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(2);
        circlePaint.setColor(Color.WHITE);
        float centerWidth = canvas.getWidth()/2;

        Path arrowPath = new Path();
        final RectF arrowOval = new RectF();
        arrowOval.set(0 - centerWidth/2,
                0,
                centerWidth/4,
                getHeight());
        arrowPath.addArc(arrowOval,-90,180);
        canvas.drawPath(arrowPath, circlePaint);


        arrowOval.set(centerWidth + 3*centerWidth/4,
                0,
                getWidth() + centerWidth/2,
                getHeight());
        arrowPath.addArc(arrowOval,90,180);
        canvas.drawPath(arrowPath, circlePaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRGB(97, 172, 29);

        drawOvalAndArrow(canvas);
    }
}
