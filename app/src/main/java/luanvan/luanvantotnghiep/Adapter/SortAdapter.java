package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import luanvan.luanvantotnghiep.ControlRecycle.Action;
import luanvan.luanvantotnghiep.ControlRecycle.ActionView;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Helper;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> implements Action {
    private Context mContext;
    private List<Element> mListData;
    private Helper helper;

    private List<Integer> mListUI = new ArrayList<>();
    private boolean mIsMove = true;

    public SortAdapter(Context mContext, List<Element> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
        helper = Helper.getInstant();

        for (int i = 0; i < mListData.size(); i++) {
            mListUI.add(-1);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_sentences, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(Html.fromHtml(helper.handelText(mListData.get(position).getMolecularFormula())));

        if (mListUI.get(position) == 0){
            holder.layout.setBackgroundColor(Color.CYAN);
        }else if (mListUI.get(position) == 1){
            holder.layout.setBackgroundColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onMove(int oldPosition, int newPosition) {
        if (mIsMove){
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

    public void setUI(int position, int value){
        mListUI.set(position, value);
    }

    public void setNoMove(){
        mIsMove = false;
    }
}
