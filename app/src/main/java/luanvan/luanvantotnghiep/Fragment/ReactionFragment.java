package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ReactionAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class ReactionFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRvReaction;

    ChemistryHelper chemistryHelper;
    List<ChemicalReaction> chemicalReactionList;

    ReactionAdapter reactionAdapter;

    public ReactionFragment() {
    }

    public static ReactionFragment newInstance() {
        return new ReactionFragment();
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
        View view = inflater.inflate(R.layout.fragment_reaction, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        mRvReaction = view.findViewById(R.id.rv_reaction);

        chemistryHelper = ChemistrySingle.getInstance(mContext);
        chemicalReactionList = chemistryHelper.getAllChemicalReaction();

        for (ChemicalReaction chemicalReaction: chemicalReactionList){
            Log.i("hns",""+ chemicalReaction.getReactants() + " " +chemicalReaction.getProducts());
        }

        reactionAdapter = new ReactionAdapter(mContext, chemicalReactionList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);
        mRvReaction.setHasFixedSize(true);
        mRvReaction.setAdapter(reactionAdapter);
        mRvReaction.setLayoutManager(manager);
    }

}
