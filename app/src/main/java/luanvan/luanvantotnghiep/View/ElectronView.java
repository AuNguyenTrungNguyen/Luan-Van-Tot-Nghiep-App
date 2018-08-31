package luanvan.luanvantotnghiep.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ElectronView extends View {

    private static final float RADIUS_ATOM = 12.5f;
    private static final float RADIUS_PROTON = 6.25f;
    private static final float RADIUS_ELECTRON = 2.5f;
    private static final int ODD = 180;
    private static final int EVEN = 90;

    private float mWidth;
    private float mHeight;

    private Paint mPaintAtom;
    private Paint mPaintBorder;
    private Paint mPaintElectron;

    private String mShell = null;

    public ElectronView(Context context) {
        super(context);
    }

    public ElectronView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mPaintAtom = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAtom.setColor(Color.BLACK);
        mPaintAtom.setStrokeWidth(2f);

        mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBorder.setColor(Color.BLACK);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(2f);

        mPaintElectron = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintElectron.setColor(Color.BLACK);
        mPaintElectron.setStrokeWidth(2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth() / 2.0f;
        mHeight = getHeight() / 2.0f;

        if(mShell != null){
            //Draw Proton
            canvas.drawCircle(mWidth, mHeight, RADIUS_PROTON, mPaintAtom);

            drawAtomStruct(mShell
                    , canvas);

        }else{
            Log.i("ANTN", "onDraw is null");
        }
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
            canvas.drawCircle(mWidth, mHeight, RADIUS_ATOM * border, mPaintBorder);

            String electrons = atoms[i].substring(1);
            int num = Integer.parseInt(electrons);
            drawWithNumberElectron(num, border, canvas);
        }
    }

    public void setShellToView(String shell){
        mShell = shell;
    }

}