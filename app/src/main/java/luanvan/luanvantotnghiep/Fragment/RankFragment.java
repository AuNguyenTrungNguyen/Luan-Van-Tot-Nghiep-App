package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class RankFragment extends Fragment {

    private ChemistryHelper mChemistryHelper;
    private PreferencesManager mPreferencesManager;
    private Context mContext;

    private RecyclerView mRvRank;

    public RankFragment() {
    }

    public static RankFragment newInstance() {
        return new RankFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        init(view);

        return view;
    }

    private void init(View v) {
        mChemistryHelper = ChemistrySingle.getInstance(mContext);
        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(mContext);

        mRvRank = v.findViewById(R.id.rv_rank);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_rank,menu);
    }
}
