package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.R;

public class PositionQuestionAdapter extends RecyclerView.Adapter<PositionQuestionAdapter.ViewHolder> {
    private Context mContext;
    private List<Integer> mPositionList;

    public PositionQuestionAdapter(Context mContext, List<Integer> mPositionList) {
        this.mContext = mContext;
        this.mPositionList = mPositionList;
    }

    @NonNull
    @Override
    public PositionQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_position_question, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int position = mPositionList.get(i);

        viewHolder.mTvPosition.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return mPositionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvPosition;
        private ImageView mImgFlag;

        ViewHolder(View view) {
            super(view);
            mTvPosition = view.findViewById(R.id.tv_position_question);
            mImgFlag = view.findViewById(R.id.img_flag);

        }

    }
}
