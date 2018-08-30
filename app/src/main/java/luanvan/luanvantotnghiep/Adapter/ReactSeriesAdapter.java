package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.R;

public class ReactSeriesAdapter extends RecyclerView.Adapter<ReactSeriesAdapter.ViewHolder> {

    private Context mContext;
    private List<ReactSeries> mListData;

    public ReactSeriesAdapter(Context context, List<ReactSeries> list) {
        mContext = context;
        mListData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_react_series, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReactSeries reactSeries = mListData.get(position);

        String ion = reactSeries.getIon();
        String valence = reactSeries.getValence();
        String valenceShow = "<small><sup>" + valence + "</sup></small>";

        holder.tvIon.setText(Html.fromHtml(ion + valenceShow));
        holder.tvPeriodic.setText(ion);

        //Case ion H+ - 2H
        if (ion.equals("H")) {
            holder.tvPeriodic.setText(Html.fromHtml(ion + "<small><sub>2</sub></small>"));
            holder.tvIon.setText(Html.fromHtml("2" + ion + valenceShow));
        }

        //Case ion Fe3+ - Fe2+
        if (ion.equals("Fe") && valence.equals("3+")) {
            holder.tvPeriodic.setText(Html.fromHtml(ion + "<small><sup>2+</sup></small>"));
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIon;
        TextView tvPeriodic;

        ViewHolder(View view) {
            super(view);
            tvIon = view.findViewById(R.id.tv_ion);
            tvPeriodic = view.findViewById(R.id.tv_periodic);
        }
    }
}
