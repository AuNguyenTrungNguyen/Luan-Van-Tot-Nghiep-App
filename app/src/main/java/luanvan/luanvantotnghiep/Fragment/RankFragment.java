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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.RankAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Rank;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class RankFragment extends Fragment {

    private ChemistryHelper mChemistryHelper;
    private PreferencesManager mPreferencesManager;
    private Context mContext;

    private RecyclerView mRvRank;
    private RankAdapter mRankAdapter;
    private List<Rank> mRankList;
    private FirebaseDatabase mFirebaseDatabase;
    private TextView mTvExtent;

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

        loadData(Constraint.EXTENT_EASY);

        return view;
    }

    private void loadData(final int extent) {
        final int block = mPreferencesManager.getIntData(Constraint.PRE_KEY_BLOCK, 8);
        DatabaseReference reference = mFirebaseDatabase.getReference("RANK");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRankList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Rank rank = postSnapshot.getValue(Rank.class);
                    assert rank != null;
                    if (block == rank.getBlock() && extent == rank.getExtent()) {
                        mRankList.add(rank);
                    }
                }
                for (Rank rank : mRankList) {
                    Log.i("hns", "onDataChange: " + rank.getExtent());
                }
                Collections.sort(mRankList, new Comparator<Rank>() {
                    public int compare(Rank o1, Rank o2) {
                        return Float.compare(o2.getScore(), o1.getScore());
                    }
                });
                mRankAdapter = new RankAdapter(mContext, mRankList);
                mRvRank.setAdapter(mRankAdapter);
                mRankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init(View v) {
        mChemistryHelper = ChemistrySingle.getInstance(mContext);
        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(mContext);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mRvRank = v.findViewById(R.id.rv_rank);
        mRankList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRvRank.setLayoutManager(manager);
        mRvRank.setHasFixedSize(true);

        mTvExtent = v.findViewById(R.id.tv_extent_rank);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_rank, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_rank_easy:
                loadData(Constraint.EXTENT_EASY);
                mTvExtent.setText("Xếp hạng: Dễ");
                return true;

            case R.id.mn_rank_normal:
                loadData(Constraint.EXTENT_NORMAL);
                mTvExtent.setText("Xếp hạng: Trung bình");
                return true;

            case R.id.mn_rank_difficult:
                loadData(Constraint.EXTENT_DIFFICULT);
                mTvExtent.setText("Xếp hạng: Khó");
                return true;
        }
        return false;
    }
}
