package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankHolder>{

    private Context mContext;
    private List mListData;

    @NonNull
    @Override
    public RankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RankHolder rankHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class RankHolder extends RecyclerView.ViewHolder{

        public RankHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
