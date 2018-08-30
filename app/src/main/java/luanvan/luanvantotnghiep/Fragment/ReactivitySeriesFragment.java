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

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ReactSeriesAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class ReactivitySeriesFragment extends Fragment {

    private ChemistryHelper mChemistryHelper;
    private Context mContext;
    private ReactSeriesAdapter mAdapter;

    public ReactivitySeriesFragment() {
    }

    public static ReactivitySeriesFragment newInstance() {
        return new ReactivitySeriesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_metal_reactivity_series, container, false);

        init(view);

        addDataReactSeriesTable();

        showReactivityList();

        return view;
    }

    private void init(View v) {
        mChemistryHelper = ChemistrySingle.getInstance(mContext);
        List<ReactSeries> mReactSeriesList = mChemistryHelper.getAllReactSeries();

        RecyclerView mRvReactSeries = v.findViewById(R.id.rv_react_series);
        mAdapter = new ReactSeriesAdapter(mContext, mReactSeriesList);
        mRvReactSeries.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL,
                false);
        mRvReactSeries.setLayoutManager(mLayoutManager);
        mRvReactSeries.setHasFixedSize(true);
    }

    private void showReactivityList() {
        mAdapter.notifyDataSetChanged();
    }

    private void addDataReactSeriesTable() {

        List<ReactSeries> reactSeriesList = new ArrayList<>();

        ReactSeries reactSeries;
        reactSeries = new ReactSeries("Li", "+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("K", "+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Ba", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Ca", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Na", "+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Mg", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Al", "3+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Mn", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Zn", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Cr", "3+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Fe", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Sn", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Pb", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("H", "+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Cu", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Fe", "3+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Ag", "+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Hg", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Pt", "2+");
        reactSeriesList.add(reactSeries);

        reactSeries = new ReactSeries("Au", "3+");
        reactSeriesList.add(reactSeries);

        //Check and add data
        if (reactSeriesList.size() == mChemistryHelper.getAllReactSeries().size()) {
            Log.i("ANTN", "Table ReactSeries available");
        } else {
            //Add to database
            mChemistryHelper.emptyReactSeries();
            for (ReactSeries item : reactSeriesList) {
                mChemistryHelper.addReactSeries(item);
            }
        }
    }
}
