package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.Test;
import luanvan.luanvantotnghiep.Model.TestGroup;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class TestFragment extends Fragment {

    private Context mContext;

    private List<Object> mList1 = new ArrayList<>();
    private List<Object> mList2 = new ArrayList<>();
    private List<Object> mList3 = new ArrayList<>();
    private List<Object> mList4 = new ArrayList<>();
    private List<Object> mList5 = new ArrayList<>();
    private List<Object> mList6 = new ArrayList<>();
    private List<Object> mList7 = new ArrayList<>();

    private List<Element> mElementList;
    private List<Group> mGroupList;
    private ChemistryHelper mChemistryHelper;

    public TestFragment() {
    }

    public static TestFragment newInstance() {
        return new TestFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);

        init(view);

        prepareData();

        return view;
    }

    private void init(View view) {
        final RecyclerView mRv1 = view.findViewById(R.id.rv1);
        RecyclerView mRv2 = view.findViewById(R.id.rv2);
        RecyclerView mRv3 = view.findViewById(R.id.rv3);
        RecyclerView mRv4 = view.findViewById(R.id.rv4);
        RecyclerView mRv5 = view.findViewById(R.id.rv5);
        RecyclerView mRv6 = view.findViewById(R.id.rv6);
        RecyclerView mRv7 = view.findViewById(R.id.rv7);
        
        //Prepare Layout Manager
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager3 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager4 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager5 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager6 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager7 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRv1.setLayoutManager(manager1);
        mRv2.setLayoutManager(manager2);
        mRv3.setLayoutManager(manager3);
        mRv4.setLayoutManager(manager4);
        mRv5.setLayoutManager(manager5);
        mRv6.setLayoutManager(manager6);
        mRv7.setLayoutManager(manager7);
        
        //Prepare Adapter
        TestAdapter adapter1 = new TestAdapter(mContext, mList1);
        TestAdapter adapter2 = new TestAdapter(mContext, mList2);
        TestAdapter adapter3 = new TestAdapter(mContext, mList3);
        TestAdapter adapter4 = new TestAdapter(mContext, mList4);
        TestAdapter adapter5 = new TestAdapter(mContext, mList5);
        TestAdapter adapter6 = new TestAdapter(mContext, mList6);
        TestAdapter adapter7 = new TestAdapter(mContext, mList7);
        mRv1.setAdapter(adapter1);
        mRv2.setAdapter(adapter2);
        mRv3.setAdapter(adapter3);
        mRv4.setAdapter(adapter4);
        mRv5.setAdapter(adapter5);
        mRv6.setAdapter(adapter6);
        mRv7.setAdapter(adapter7);

        //Setup recycle not scroll
        mRv1.setNestedScrollingEnabled(false);
        mRv2.setNestedScrollingEnabled(false);
        mRv3.setNestedScrollingEnabled(false);
        mRv4.setNestedScrollingEnabled(false);
        mRv5.setNestedScrollingEnabled(false);
        mRv6.setNestedScrollingEnabled(false);
        mRv7.setNestedScrollingEnabled(false);
        

    }

    private void prepareData() {

        mChemistryHelper = ChemistrySingle.getInstance(getContext());
        mGroupList = mChemistryHelper.getAllGroups();
        mElementList = mChemistryHelper.getAllElements();

        for (Group group: mGroupList) {
            Log.i("ANTN", "prepareData: " + group.getNameGroup());
        }

        mList1.add(new Test(2, "H", "Hydro", 0));
        mList1.add(new TestGroup("IIA"));
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup());
        mList1.add(new TestGroup("IIIA"));
        mList1.add(new TestGroup("IVA"));
        mList1.add(new TestGroup("VA"));
        mList1.add(new TestGroup("VIA"));
        mList1.add(new TestGroup("VIIA"));
        mList1.add(new Test(2, "He", "Heli", 0));

        //mList2
        mList2.add(new Test(3, "Li", "Liti", 0));
        mList2.add(new Test(4, "Be", "Beri", 0));
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new TestGroup());
        mList2.add(new Test(5, "Bo", "Bo", 0));
        mList2.add(new Test(6, "C", "Cacbon", 0));
        mList2.add(new Test(7, "N", "Nito", 0));
        mList2.add(new Test(8, "O", "Oxi", 0));
        mList2.add(new Test(9, "F", "Flo", 0));
        mList2.add(new Test(10, "Ne", "Neon", 0));
    }

    private static class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<Object> mList;

        private static final int TYPE_ELEMENT = 0;
        private static final int TYPE_GROUP = 1;
        private static final int TYPE_EMPTY = 2;


        TestAdapter(Context mContext, List<Object> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public int getItemViewType(int position) {
            Object o = mList.get(position);

            if (o instanceof Test) {
                return TYPE_ELEMENT;
            } else if (o instanceof TestGroup) {
                if (((TestGroup) o).getName() != null) {
                    return TYPE_GROUP;
                }
            }
            return TYPE_EMPTY;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v;
            switch (i) {
                case TYPE_ELEMENT:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item_test_element, viewGroup, false);
                    return new ViewHolderElement(v);

                case TYPE_EMPTY:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item_test_empty, viewGroup, false);
                    return new ViewHolderEmpty(v);

                case TYPE_GROUP:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item_test_group, viewGroup, false);
                    return new ViewHolderGroup(v);

            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            Object o;
            switch (viewHolder.getItemViewType()) {
                case TYPE_ELEMENT:
                    ViewHolderElement viewHolderElement = (ViewHolderElement) viewHolder;
                    o = mList.get(i);
                    Test test = (Test) o;
                    viewHolderElement.id.setText(String.valueOf(test.getId()));
                    viewHolderElement.symbol.setText(test.getSymbol());
                    viewHolderElement.name.setText(test.getName());
                    if (test.isLight()) {
                        viewHolderElement.layout.setBackgroundColor(Color.BLUE);
                    } else {
                        viewHolderElement.layout.setBackgroundColor(Color.CYAN);
                    }
                    break;

                case TYPE_EMPTY:
                    ViewHolderEmpty viewHolderEmpty = (ViewHolderEmpty) viewHolder;
                    break;

                case TYPE_GROUP:
                    ViewHolderGroup viewHolderGroup = (ViewHolderGroup) viewHolder;
                    o = mList.get(i);
                    TestGroup testGroup = (TestGroup) o;
                    viewHolderGroup.group.setText(testGroup.getName());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        static class ViewHolderElement extends RecyclerView.ViewHolder {
            private TextView id;
            private TextView symbol;
            private TextView name;
            private LinearLayout layout;

            ViewHolderElement(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.tv_id_element);
                symbol = itemView.findViewById(R.id.tv_symbol_element);
                name = itemView.findViewById(R.id.tv_name_element);
                layout = itemView.findViewById(R.id.lu_item);
            }
        }

        static class ViewHolderGroup extends RecyclerView.ViewHolder {
            private TextView group;

            ViewHolderGroup(@NonNull View itemView) {
                super(itemView);
                group = itemView.findViewById(R.id.tv_group);
            }
        }

        static class ViewHolderEmpty extends RecyclerView.ViewHolder {
            ViewHolderEmpty(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
