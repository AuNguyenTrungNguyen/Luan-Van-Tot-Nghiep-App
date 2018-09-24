package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Helper;

public class ReactionAdapter extends RecyclerView.Adapter<ReactionAdapter.ViewHolder> {
    private Context mContext;
    private List<ChemicalReaction> mListData;

    public ReactionAdapter(Context mContext, List<ChemicalReaction> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_reaction, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Helper helper = Helper.getInstant();
        ChemicalReaction chemicalReaction = mListData.get(position);
        String reactants = chemicalReaction.getReactants();
        String products = chemicalReaction.getProducts();
        holder.tvChemicalReaction.setText(Html.fromHtml(helper.handelReactor(reactants, products)));

        if (chemicalReaction.getConditions().equals("")) {
            holder.lnCondition.setVisibility(View.GONE);
        } else {
            holder.lnCondition.setVisibility(View.VISIBLE);
            holder.tvConditions.setText(chemicalReaction.getConditions());
            holder.tvTitleCondition.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_condition, 0, 0, 0);
        }

        if (chemicalReaction.getPhenomena().equals("")) {
            holder.lnPhenomena.setVisibility(View.GONE);
        } else {
            holder.lnPhenomena.setVisibility(View.VISIBLE);
            holder.tvPhenomena.setText(chemicalReaction.getPhenomena());
            holder.tvTitlePhenomena.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_phenomena, 0, 0, 0);
        }

        if (chemicalReaction.getReactionTypes().equals("")) {
            holder.lnReactionTypes.setVisibility(View.GONE);
        } else {
            holder.lnReactionTypes.setVisibility(View.VISIBLE);
            holder.tvTitleReactionTypes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_reaction_types, 0, 0, 0);

            String[] types = chemicalReaction.getReactionTypes().split(",");
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            holder.rvReactionTypes.setLayoutManager(layoutManager);
            holder.rvReactionTypes.setHasFixedSize(true);
            ChipTypesAdapter adapter = new ChipTypesAdapter(mContext, types);
            holder.rvReactionTypes.setAdapter(adapter);
        }


    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChemicalReaction;

        LinearLayout lnCondition;
        TextView tvTitleCondition;
        TextView tvConditions;

        LinearLayout lnPhenomena;
        TextView tvTitlePhenomena;
        TextView tvPhenomena;

        LinearLayout lnReactionTypes;
        TextView tvTitleReactionTypes;
        RecyclerView rvReactionTypes;

        ViewHolder(View view) {
            super(view);

            tvChemicalReaction = view.findViewById(R.id.tv_chemical_reaction);

            lnCondition = view.findViewById(R.id.ln_condition);
            tvTitleCondition = view.findViewById(R.id.tv_title_condition);
            tvConditions = view.findViewById(R.id.tv_conditions);

            lnPhenomena = view.findViewById(R.id.ln_phenomena);
            tvTitlePhenomena = view.findViewById(R.id.tv_title_phenomena);
            tvPhenomena = view.findViewById(R.id.tv_phenomena);

            lnReactionTypes = view.findViewById(R.id.ln_reaction_types);
            tvTitleReactionTypes = view.findViewById(R.id.tv_title_reaction_types);
            rvReactionTypes = view.findViewById(R.id.rv_reaction_types);
        }
    }
}
