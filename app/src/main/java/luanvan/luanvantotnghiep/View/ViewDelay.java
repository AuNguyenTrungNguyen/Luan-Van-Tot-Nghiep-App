package luanvan.luanvantotnghiep.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ViewDelay extends View {

    private static final float RADIUS_BORDER = 50;
    private static final float RADIUS_ATOM = 25;
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
    private List<String> mList = new ArrayList<>();

    public ViewDelay(Context context) {
        super(context);
    }

    public ViewDelay(Context context, AttributeSet attributeSet) {
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

    //Delay
    private int mDelay = 1000;
    private int mLength;
    private int mBorder;
    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            mBorder++;
            if (mBorder <= mLength) {
                invalidate();
                mHandler.postDelayed(characterAdder, mDelay);
            }else{
                flag = true;
            }
        }
    };
    public boolean flag = false;

    public void drawIt(String text) {
        String atoms[] = text.split(" ");
        mLength = atoms.length;
        mBorder = 1;

        Collections.addAll(mList, atoms);

        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth() / 2.0f;
        mHeight = getHeight() / 2.0f;

        //Draw atom
        canvas.drawCircle(mWidth, mHeight, RADIUS_ATOM, mPaintAtom);

        //Draw border
        for (int i = 0; i < mBorder; i++){
            if(i < LIST_COLORS.length && i < mList.size()){

                mPaintBorder.setColor(LIST_COLORS[i]);
                mPaintElectron.setColor(LIST_COLORS[i]);
                canvas.drawCircle(mWidth, mHeight, RADIUS_BORDER * (i+1), mPaintBorder);

                String electrons = mList.get(i).substring(1);
                int num = Integer.parseInt(electrons);
                drawWithNumberElectron(num, i+1, canvas);

            }
        }
    }

    private void drawWithNumberElectron(int num, int border, Canvas canvas) {

        int style = border % 2 != 0 ? ODD : EVEN;

        for (int i = 1; i <= num; i++) {
            int angle = i * (360 / num);
            float x = (float) (mWidth + RADIUS_BORDER * border * cos(Math.toRadians(angle - style)));
            float y = (float) (mHeight + RADIUS_BORDER * border * sin(Math.toRadians(angle - style)));
            canvas.drawCircle(x, y, RADIUS_ELECTRON, mPaintElectron);
        }
    }

}
