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
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class ChipChemistryAdapter extends RecyclerView.Adapter<ChipChemistryAdapter.ViewHolder> {
    private Context mContext;
    private List<Chemistry> mDataList = new ArrayList<>();
    private List<Element> mElementList = new ArrayList<>();

    public interface CommunicateChip {
        void onReloadData(String chipText);
    }

    private CommunicateChip communicateChip;

    public void setOnItemClickListener(CommunicateChip clickListener) {
        this.communicateChip = clickListener;
    }

    public ChipChemistryAdapter(Context mContext, Integer mIdChemistry) {
        this.mContext = mContext;

        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(mContext);

        List<Chemistry> chemistryList = new ArrayList<>();
        chemistryList.addAll(chemistryHelper.getAllChemistry());

        List<ProducedBy> producedByList = new ArrayList<>();
        producedByList.addAll(chemistryHelper.getAllProducedBy());

        mElementList.addAll(chemistryHelper.getAllElements());

        for (ProducedBy producedBy : producedByList) {
            if (mIdChemistry == producedBy.getIdRightReaction()) {
                for (Chemistry chemistry : chemistryList) {
                    if (chemistry.getIdChemistry() == producedBy.getIdLeftReaction()) {
                        mDataList.add(chemistry);
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_chip_chemistry, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String symbol = mDataList.get(position).getSymbolChemistry();

        for (int i = 0; i < mElementList.size(); i++) {
            if (mDataList.get(position).getIdChemistry() == mElementList.get(i).getIdElement()) {
                symbol = mElementList.get(i).getMolecularFormula();
                break;
            }
        }

        holder.tvChipChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(symbol)));
        final String finalSymbol = symbol;
        holder.tvChipChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                communicateChip.onReloadData(finalSymbol);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChipChemistry;

        ViewHolder(View view) {
            super(view);
            tvChipChemistry = view.findViewById(R.id.tv_chip_chemistry);
        }
    }

    public int getShowChipChemistry() {
        if (getItemCount() > 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
