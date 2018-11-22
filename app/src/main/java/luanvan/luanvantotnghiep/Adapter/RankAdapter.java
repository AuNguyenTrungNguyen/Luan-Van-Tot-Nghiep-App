package luanvan.luanvantotnghiep.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import luanvan.luanvantotnghiep.Model.Rank;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankHolder> {

    private Context mContext;
    private List<Rank> mListData;

    public RankAdapter(Context mContext, List<Rank> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public RankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank, viewGroup, false);
        return new RankHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RankHolder rankHolder, int position) {
        Rank rank = mListData.get(position);

        rankHolder.mTvRank.setText("" + (position + 1));
        rankHolder.mTvName.setText(rank.getName());
        rankHolder.mTvScore.setText("" + (int) rank.getScore());

        switch (position + 1) {
            case Constraint.EXTENT_EASY:
                rankHolder.mTvRank.setTextColor(mContext.getResources().getColor(R.color.rank_1));
                rankHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.rank_1));
                rankHolder.mTvScore.setTextColor(mContext.getResources().getColor(R.color.rank_1));
                break;

            case Constraint.EXTENT_NORMAL:
                rankHolder.mTvRank.setTextColor(mContext.getResources().getColor(R.color.rank_2));
                rankHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.rank_2));
                rankHolder.mTvScore.setTextColor(mContext.getResources().getColor(R.color.rank_2));
                break;

            case Constraint.EXTENT_DIFFICULT:
                rankHolder.mTvRank.setTextColor(mContext.getResources().getColor(R.color.rank_3));
                rankHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.rank_3));
                rankHolder.mTvScore.setTextColor(mContext.getResources().getColor(R.color.rank_3));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class RankHolder extends RecyclerView.ViewHolder {
        private TextView mTvRank;
        private TextView mTvName;
        private TextView mTvScore;

        RankHolder(@NonNull View itemView) {
            super(itemView);

            mTvRank = itemView.findViewById(R.id.tv_rank);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
