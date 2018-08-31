package luanvan.luanvantotnghiep.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CustomView extends View {

    private static final float RADIUS_ATOM = 50;
    private static final float RADIUS_PROTON = 25;
    private static final float RADIUS_ELECTRON = 10;
    private static final int ODD = 180;
    private static final int EVEN = 90;
    private static final int[] LIST_COLORS = new int[]{
            Color.parseColor("#b8860b"),
            Color.parseColor("#800000"),
            Color.parseColor("#000080"),
            Color.parseColor("#008b8b"),
            Color.parseColor("#008000"),
            Color.parseColor("#dc143c"),
            Color.parseColor("#bc8f8f")
    };

    private float mWidth;
    private float mHeight;

    private Paint mPaintAtom;
    private Paint mPaintBorder;
    private Paint mPaintElectron;

    private Animation mAnimation;
    private String mShell = null;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mPaintAtom = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAtom.setColor(Color.BLACK);
        mPaintAtom.setStrokeWidth(4f);

        mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBorder.setColor(Color.BLACK);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(4f);

        mPaintElectron = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintElectron.setColor(Color.BLACK);
        mPaintElectron.setStrokeWidth(4f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth() / 2.0f;
        mHeight = getHeight() / 2.0f;

        if (mShell != null) {
            //Draw Proton
            canvas.drawCircle(mWidth, mHeight, RADIUS_PROTON, mPaintAtom);

            drawAtomStruct(mShell
                    , canvas);

            if (mAnimation == null) {
                initAnimation();
            }
        } else {
            Log.i("ANTN", "onDraw is null");
        }
    }

    private void initAnimation() {
        mAnimation = new RotateAnimation(0, 360, mWidth, mHeight);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setDuration(7500L);
        mAnimation.setInterpolator(new LinearInterpolator());
        this.startAnimation(mAnimation);
    }

    private void drawWithNumberElectron(int num, int border, Canvas canvas) {

        int style = border % 2 != 0 ? ODD : EVEN;

        for (int i = 1; i <= num; i++) {
            int angle = i * (360 / num);
            float x = (float) (mWidth + RADIUS_ATOM * border * cos(Math.toRadians(angle - style)));
            float y = (float) (mHeight + RADIUS_ATOM * border * sin(Math.toRadians(angle - style)));
            canvas.drawCircle(x, y, RADIUS_ELECTRON, mPaintElectron);
        }
    }

    private void drawAtomStruct(String shell, Canvas canvas) {

        String atoms[] = shell.split(" ");

        for (int i = 0; i < atoms.length; i++) {

            int border = i + 1;

            //Draw border
            mPaintBorder.setColor(LIST_COLORS[i]);
            mPaintElectron.setColor(LIST_COLORS[i]);
            canvas.drawCircle(mWidth, mHeight, RADIUS_ATOM * border, mPaintBorder);

            String electrons = atoms[i].substring(1);
            int num = Integer.parseInt(electrons);
            drawWithNumberElectron(num, border, canvas);
        }
    }

    public void setShellToView(String shell) {
        mShell = shell;
    }

    public void stopAnimate(boolean stop){
        if (stop){
            this.clearAnimation();
        }else{
            startAnimation(mAnimation);
        }
    }
}