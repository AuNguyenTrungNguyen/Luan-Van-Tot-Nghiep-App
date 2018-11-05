package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Activity.ContentMethodActivity;
import luanvan.luanvantotnghiep.Model.MethodEquilibrium;
import luanvan.luanvantotnghiep.R;

public class MethodEquilibriumAdapter extends RecyclerView.Adapter<MethodEquilibriumAdapter.MethodEquilibriumHolder> {

    private Context mContext;
    private List<MethodEquilibrium> mMethodList;

    public MethodEquilibriumAdapter(Context mContext) {
        this.mContext = mContext;
        setUpData();
    }

    @NonNull
    @Override
    public MethodEquilibriumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_method_equilibrium, viewGroup, false);
        return new MethodEquilibriumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MethodEquilibriumHolder holder, int position) {
        final MethodEquilibrium equilibrium = mMethodList.get(position);
        holder.tvMethod.setText(equilibrium.getMethod());
        holder.lnMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ContentMethodActivity.class);
                intent.putExtra("CONTENT_METHOD", equilibrium.getContentMethod());
                intent.putExtra("METHOD", equilibrium.getMethod());
                mContext.startActivity(intent);
            }
        });
    }

    private void setUpData() {
        mMethodList = new ArrayList<>();
        MethodEquilibrium equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Nguyên tử nguyên tố");
        equilibrium.setContentMethod("method_1");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Hóa trị tác dụng");
        equilibrium.setContentMethod("method_2");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Dùng hệ số phân số");
        equilibrium.setContentMethod("method_3");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("“chẵn – lẻ”");
        equilibrium.setContentMethod("method_4");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Xuất phát từ nguyên tố chung nhất");
        equilibrium.setContentMethod("method_5");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Cân bằng theo “nguyên tố tiêu biểu");
        equilibrium.setContentMethod("method_6");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Cân bằng theo trình tự kim loại – phi kim");
        equilibrium.setContentMethod("method_7");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Xuất phát từ bản chất hóa học của phản ứng");
        equilibrium.setContentMethod("method_8");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Cân bằng phản ứng cháy của chất hữu cơ");
        equilibrium.setContentMethod("method_9");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Cân bằng electron");
        equilibrium.setContentMethod("method_10");
        mMethodList.add(equilibrium);

        equilibrium = new MethodEquilibrium();
        equilibrium.setMethod("Cân bằng đại số");
        equilibrium.setContentMethod("method_11");
        mMethodList.add(equilibrium);
    }

    @Override
    public int getItemCount() {
        return mMethodList.size();
    }

    static class MethodEquilibriumHolder extends RecyclerView.ViewHolder {

        TextView tvMethod;
        LinearLayout lnMethod;

        MethodEquilibriumHolder(@NonNull View itemView) {
            super(itemView);

            tvMethod = itemView.findViewById(R.id.tv_method);
            lnMethod = itemView.findViewById(R.id.ln_method);
        }
    }
}
