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
import luanvan.luanvantotnghiep.Model.ReactWith;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class ReactWithAdapter extends RecyclerView.Adapter<ReactWithAdapter.ViewHolder> {
    private Context mContext;
    private List<ChemicalReaction> mDataList = new ArrayList<>();
    private List<Type> mDataTypeList = new ArrayList<>();

    public ReactWithAdapter(Context mContext, Integer mIdChemistry) {
        this.mContext = mContext;

        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(mContext);

        List<ReactWith> reactWithList = new ArrayList<>();
        reactWithList.addAll(chemistryHelper.getAllReactWith());

        List<ChemicalReaction> chemicalReactionList = new ArrayList<>();
        chemicalReactionList.addAll(chemistryHelper.getAllChemicalReaction());

        List<Chemistry> chemistryList = new ArrayList<>();
        chemistryList.addAll(chemistryHelper.getAllChemistry());

        List<Type> typeList = new ArrayList<>();
        typeList.addAll(chemistryHelper.getAllTypes());

        for (ReactWith reactWith : reactWithList) {
            if (mIdChemistry == reactWith.getIdChemistry_1()) {
                for (ChemicalReaction chemicalReaction : chemicalReactionList) {
                    if (reactWith.getIdChemicalReaction() == chemicalReaction.getIdChemicalReaction()) {
                        mDataList.add(chemicalReaction);
                    }
                }

                for (Chemistry chemistry : chemistryList) {
                    if (reactWith.getIdChemistry_2() == chemistry.getIdChemistry()) {
                        for (Type type : typeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                mDataTypeList.add(type);
                            }

                        }
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_react_with, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String reactants = mDataList.get(position).getReactants();
        String products = mDataList.get(position).getProducts();

        Helper helper = Helper.getInstant();
        holder.tvWithReaction.setText(Html.fromHtml(helper.handelReactor(reactants, products)));
        if (mDataTypeList.get(position).getIdType() == 3
                || mDataTypeList.get(position).getIdType() == 4
                || mDataTypeList.get(position).getIdType() == 5) {
            holder.tvReacttype.setText(" + Tác dụng với kim loại");

        }else if(mDataTypeList.get(position).getIdType() == 19) {
            holder.tvReacttype.setText(" + Tác dụng với nước");

        }else {
            holder.tvReacttype.setText(" + Tác dụng với " + mDataTypeList.get(position).getNameType().toLowerCase());
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReacttype;
        TextView tvWithReaction;

        ViewHolder(View view) {
            super(view);

            tvReacttype = view.findViewById(R.id.tv_react_type);
            tvWithReaction = view.findViewById(R.id.tv_with_reaction);
        }
    }

    public int getShowReactWith() {
        if (getItemCount() > 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
