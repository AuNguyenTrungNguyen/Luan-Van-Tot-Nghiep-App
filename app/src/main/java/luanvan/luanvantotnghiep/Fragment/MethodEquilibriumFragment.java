package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import luanvan.luanvantotnghiep.Activity.EquilibriumActivity;
import luanvan.luanvantotnghiep.Adapter.MethodEquilibriumAdapter;
import luanvan.luanvantotnghiep.R;

public class MethodEquilibriumFragment extends Fragment {
    private Context mContext;

    public MethodEquilibriumFragment() {
    }

    public static MethodEquilibriumFragment newInstance() {
        return new MethodEquilibriumFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_method_equilibrium, container, false);

        RecyclerView rvMethod = view.findViewById(R.id.rv_method);
        MethodEquilibriumAdapter adapter = new MethodEquilibriumAdapter(mContext);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvMethod.setLayoutManager(manager);
        rvMethod.setHasFixedSize(true);
        rvMethod.setAdapter(adapter);

        view.findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, EquilibriumActivity.class));
            }
        });

        return view;
    }


}
