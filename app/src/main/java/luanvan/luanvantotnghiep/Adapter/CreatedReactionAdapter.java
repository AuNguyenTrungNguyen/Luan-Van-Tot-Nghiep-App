package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.CreatedReaction;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class CreatedReactionAdapter extends RecyclerView.Adapter<CreatedReactionAdapter.ViewHolder> {
    private Context mContext;
    private List<ChemicalReaction> mDataList = new ArrayList<>();

    public CreatedReactionAdapter(Context mContext, Integer mIdChemistry) {
        this.mContext = mContext;

        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(mContext);
        
        List<CreatedReaction> createdReactionList = new ArrayList<>();
        createdReactionList.addAll(chemistryHelper.getAllCreatedReaction());

        List<ChemicalReaction> chemicalReactionList = new ArrayList<>();
        chemicalReactionList.addAll(chemistryHelper.getAllChemicalReaction());

        for (CreatedReaction createdReaction : createdReactionList) {
            if (mIdChemistry == createdReaction.getIdCreatedRight()) {
                for (ChemicalReaction chemicalReaction : chemicalReactionList) {
                    if (createdReaction.getIdChemicalReaction() == chemicalReaction.getIdChemicalReaction()) {
                        mDataList.add(chemicalReaction);
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_created_reaction, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String reactants = mDataList.get(position).getReactants();
        String products = mDataList.get(position).getProducts();
        
        Helper helper = Helper.getInstant();
        holder.tvCreatedReaction.setText(Html.fromHtml(helper.handelReactor(reactants, products)));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreatedReaction;

        ViewHolder(View view) {
            super(view);

            tvCreatedReaction = view.findViewById(R.id.tv_created_reaction);
        }
    }

    public int getShowCreatedReaction() {
        if (getItemCount() > 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
