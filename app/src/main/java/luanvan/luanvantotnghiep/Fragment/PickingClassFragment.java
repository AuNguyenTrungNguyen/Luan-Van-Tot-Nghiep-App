package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ChapterAdapter;
import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

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

        List<Chapter> chapterList = ChemistrySingle.getInstance(mContext).getAllChapter();
        RecyclerView rvChapter = view.findViewById(R.id.rv_chapter);
        ChapterAdapter adapter = new ChapterAdapter(mContext, chapterList);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        rvChapter.setLayoutManager(manager);
        rvChapter.setHasFixedSize(true);
        rvChapter.setAdapter(adapter);
        return view;
    }
}
