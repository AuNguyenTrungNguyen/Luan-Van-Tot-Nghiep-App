package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import luanvan.luanvantotnghiep.ControlRecycle.Action;
import luanvan.luanvantotnghiep.ControlRecycle.ActionView;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class MatchSentencesAdapter extends RecyclerView.Adapter<MatchSentencesAdapter.ViewHolder> implements Action {

    private Context mContext;
    private List<Object> mListData;

    public MatchSentencesAdapter(Context context, List<Object> listData) {
        this.mContext = context;
        this.mListData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_sentences, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String text = String.valueOf(i);

        Object object = mListData.get(i);
        if (object instanceof Question){
            text = ((Question) object).getContentQuestion();
        }else if (object instanceof Answer){
            text = ((Answer) object).getContentAnswer();
            int show = ((Answer) object).getShow();
            if (show != 0 && show != -1){
                viewHolder.layout.setBackgroundColor(Color.CYAN);
                text = text + "(" + show + ")";
            }else if(show == 0){
                viewHolder.layout.setBackgroundColor(Color.RED);
                text = text + "(" + show + ")";
            }
        }

        viewHolder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onMove(int oldPosition, int newPosition) {

        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(mListData, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(mListData, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ActionView {
        TextView textView;
        LinearLayout layout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_drag);
            layout = itemView.findViewById(R.id.ln_drag);
        }

        @Override
        public void change() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void clear() {
            itemView.setBackgroundColor(0);
        }
    }
}
