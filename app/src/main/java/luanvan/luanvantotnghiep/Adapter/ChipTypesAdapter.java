package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import luanvan.luanvantotnghiep.R;


public class ChipTypesAdapter extends RecyclerView.Adapter<ChipChemistryAdapter.ViewHolder> {
    private Context mContext;
    private String[] mDataList;

    public ChipTypesAdapter(Context mContext, String[] mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ChipChemistryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_chip_chemistry, parent, false);

        return new ChipChemistryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChipChemistryAdapter.ViewHolder holder, int position) {
        holder.tvChipChemistry.setText(mDataList[position]);
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChipChemistry;

        ViewHolder(View view) {
            super(view);
            tvChipChemistry = view.findViewById(R.id.tv_chip_chemistry);
        }
    }
}
