package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import luanvan.luanvantotnghiep.R;

public class CheckingAnswerAdapter extends ArrayAdapter{
    private Context mContext;
    private List<Integer> mListData;

    public CheckingAnswerAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
        mContext = context;
        mListData = objects;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_checking_answer, parent, false);
        }

        LinearLayout lnChecking = convertView.findViewById(R.id.ln_item_checking);
        TextView tvChecking = convertView.findViewById(R.id.tv_checking_answer);
        tvChecking.setText(String.valueOf(position+1));

        if (mListData.get(position) == - 1){
            lnChecking.setBackgroundResource(R.drawable.item_checking_false);
            tvChecking.setTextColor(Color.BLACK);
        }else{
            lnChecking.setBackgroundResource(R.drawable.item_checking_true);
            tvChecking.setTextColor(Color.WHITE);
        }

        return convertView;
    }
}
