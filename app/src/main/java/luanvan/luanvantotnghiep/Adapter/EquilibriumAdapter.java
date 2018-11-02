package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import luanvan.luanvantotnghiep.Model.Equilibrium;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Helper;

public class EquilibriumAdapter extends RecyclerView.Adapter<EquilibriumAdapter.EquilibriumHolder> {

    private static final String TAG = "ANTN";

    private Context mContext;
    private List<Equilibrium> mListEquilibrium;
    private OnClickButtonEquilibrium mListener;

    public interface OnClickButtonEquilibrium {
        void updateUI();
    }

    public void setListener(OnClickButtonEquilibrium listener) {
        this.mListener = listener;
    }

    public EquilibriumAdapter(Context mContext, List<Equilibrium> mListEquilibrium) {
        this.mContext = mContext;
        this.mListEquilibrium = mListEquilibrium;
    }

    @NonNull
    @Override
    public EquilibriumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_equilibrium, viewGroup, false);
        return new EquilibriumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EquilibriumHolder equilibriumHolder, int position) {
        final Equilibrium equilibrium = mListEquilibrium.get(position);

        equilibriumHolder.tvNameEquilibrium.setText(Html.fromHtml(Helper.getInstant().handelText(equilibrium.getName())));
        equilibriumHolder.tvNumberEquilibrium.setText(String.format("%s", equilibrium.getNumber()));

        equilibriumHolder.btnSubEquilibrium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = equilibrium.getNumber();
                if (number == 2) {
                    equilibriumHolder.btnSubEquilibrium.setEnabled(false);
                }
                equilibrium.setNumber(number - 1);
                notifyDataSetChanged();
                mListener.updateUI();
            }
        });

        equilibriumHolder.btnAddEquilibrium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = equilibrium.getNumber();
                equilibriumHolder.btnSubEquilibrium.setEnabled(true);
                equilibrium.setNumber(number + 1);
                notifyDataSetChanged();
                mListener.updateUI();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListEquilibrium.size();
    }

    static class EquilibriumHolder extends RecyclerView.ViewHolder {

        private TextView tvNameEquilibrium;
        private Button btnSubEquilibrium;
        private TextView tvNumberEquilibrium;
        private Button btnAddEquilibrium;

        EquilibriumHolder(@NonNull View itemView) {
            super(itemView);
            tvNameEquilibrium = itemView.findViewById(R.id.tv_name_equilibrium);
            btnSubEquilibrium = itemView.findViewById(R.id.btn_sub_equilibrium);
            tvNumberEquilibrium = itemView.findViewById(R.id.tv_number_equilibrium);
            btnAddEquilibrium = itemView.findViewById(R.id.btn_add_equilibrium);

        }
    }
}
