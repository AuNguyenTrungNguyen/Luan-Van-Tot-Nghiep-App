package luanvan.luanvantotnghiep.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextDelay extends TextView {

    private String[] mTextList;
    private int mIndex;
    private long mDelay = 1000;
    public boolean flag = false;

    public TextDelay(Context context) {
        super(context);
    }

    public TextDelay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            handelText();
            mIndex++;
            if (mIndex <= mTextList.length) {
                mHandler.postDelayed(characterAdder, mDelay);
            }else{
                flag = true;
            }
        }
    };

    public void animateText(String text) {
        mIndex = 0;
        mTextList = text.split(" ");

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    private void handelText() {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < mIndex; i++){
            if(i < mTextList.length){
                result.append(mTextList[i]).append(" ");
            }
        }

        setText(result.toString());
    }
}
