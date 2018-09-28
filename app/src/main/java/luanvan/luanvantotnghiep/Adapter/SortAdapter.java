package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.Helper;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> implements Action {
    //CASE TYPE LIST
    private static final int CHEMISTRY = 0;
    private static final int REACT_SERIES = 1;

    private Context mContext;
    private List<Object> mListData;
    private int mType;

    private List<Integer> mListUI = new ArrayList<>();
    private boolean mIsMove = true;

    public SortAdapter(Context mContext, List<Object> mListData, int type) {
        this.mContext = mContext;
        this.mListData = mListData;
        mType = type;

        for (int i = 0; i < mListData.size(); i++) {
            mListUI.add(-1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mListData.get(position);
        if (o instanceof ReactSeries) {
            return REACT_SERIES;
        }
        return CHEMISTRY;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_sentences, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Object o = mListData.get(position);
        if (getItemViewType(position) == REACT_SERIES) {

            if (mType == Constraint.OXIDATION_ASC || mType == Constraint.OXIDATION_DEC) {
                String oxidation = handleOxidation((ReactSeries) o);
                holder.textView.setText(Html.fromHtml(oxidation));
            } else if (mType == Constraint.REDUCTION_ASC || mType == Constraint.REDUCTION_DEC) {
                String reduction = handleReduction((ReactSeries) o);
                holder.textView.setText(Html.fromHtml(reduction));
            }
        }
        else {
            String symbol = ((Chemistry) o).getSymbolChemistry();
            holder.textView.setText(symbol);
        }

        //handle show answer add value
        if (mListUI.get(position) == 0) {
            holder.layout.setBackgroundColor(Color.CYAN);
        } else if (mListUI.get(position) == 1) {
            holder.layout.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onMove(int oldPosition, int newPosition) {
        if (mIsMove) {
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

    public void setUI(int position, int value) {
        mListUI.set(position, value);
    }

    public void setNoMove() {
        mIsMove = false;
    }

    private String handleOxidation(ReactSeries reactSeries) {

        String ion = reactSeries.getIon();
        String valence = reactSeries.getValence();
        String valenceShow = "<small><sup>" + valence + "</sup></small>";

        if (ion.equals("H")) {

            return "2" + ion + valenceShow;
        }

        return ion + valenceShow;
    }

    private String handleReduction(ReactSeries reactSeries) {

        String ion = reactSeries.getIon();
        String valence = reactSeries.getValence();

        //Case ion H+ - 2H
        if (ion.equals("H")) {
            return ion + "<small><sub>2</sub></small>";
        }

        //Case ion Fe3+ - Fe2+
        if (ion.equals("Fe") && valence.equals("3+")) {
            return ion + "<small><sup>2+</sup></small>";
        }

        return ion;
    }
}
