package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ExpandAdapter;
import luanvan.luanvantotnghiep.R;

public class PickingClassFragment extends Fragment {

    private Context mContext;

    public PickingClassFragment() {
    }

    public static PickingClassFragment newInstance() {
        return new PickingClassFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picking_class, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        //Prepare view
        ExpandableListView mExpand = view.findViewById(R.id.expand_class);
        List<String> mListHeader = new ArrayList<>();
        HashMap<String, List<String>> mListItem = new HashMap<>();
        ExpandAdapter adapter = new ExpandAdapter(mContext, mListHeader, mListItem);
        mExpand.setAdapter(adapter);

        //Add header
        for (int i = 8; i < 13; i++) {
            mListHeader.add(String.format("Lớp %s", i));
        }


        //Add item by header
        for (int i = 8; i < 13; i++) {
            List<String> item = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                item.add(String.format("Chương %s", j));
            }
            mListItem.put(mListHeader.get(i-8), item);
        }

        adapter.notifyDataSetChanged();
    }
}
