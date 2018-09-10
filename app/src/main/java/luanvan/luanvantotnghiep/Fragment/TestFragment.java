package luanvan.luanvantotnghiep.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.SwipeAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.TestElement;
import luanvan.luanvantotnghiep.Model.TestGroup;
import luanvan.luanvantotnghiep.Model.TestHeader;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class TestFragment extends Fragment {

    private static final int[] LIST_COLOR_TYPE = new int[]{
            Color.parseColor("#ff6666"),
            Color.parseColor("#ffdead"),
            Color.parseColor("#cccccc"),
            Color.parseColor("#cccc99"),
            Color.parseColor("#ffc0c0"),
            Color.parseColor("#a0ffa0"),
            Color.parseColor("#ffff99"),
            Color.parseColor("#c0ffff"),
            Color.parseColor("#ffbfff"),
            Color.parseColor("#ff99cc"),
            Color.parseColor("#e8e8e8")
    };

    private Context mContext;

    private List<Object> mListHeader = new ArrayList<>();
    private List<Object> mList1 = new ArrayList<>();
    private List<Object> mList2 = new ArrayList<>();
    private List<Object> mList3 = new ArrayList<>();
    private List<Object> mList4 = new ArrayList<>();
    private List<Object> mList5 = new ArrayList<>();
    private List<Object> mList6 = new ArrayList<>();
    private List<Object> mList7 = new ArrayList<>();
    private List<Object> mList8 = new ArrayList<>();
    private List<Object> mList9 = new ArrayList<>();

    private TestAdapter adapterHeader;
    private TestAdapter adapter1;
    private TestAdapter adapter2;
    private TestAdapter adapter3;
    private TestAdapter adapter4;
    private TestAdapter adapter5;
    private TestAdapter adapter6;
    private TestAdapter adapter7;
    private TestAdapter adapter8;
    private TestAdapter adapter9;

    private List<Element> mElementList;
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
        mChemistryHelper = ChemistrySingle.getInstance(context);
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
        RecyclerView mRvHeader = view.findViewById(R.id.rv_head);
        RecyclerView mRv1 = view.findViewById(R.id.rv1);
        RecyclerView mRv2 = view.findViewById(R.id.rv2);
        RecyclerView mRv3 = view.findViewById(R.id.rv3);
        RecyclerView mRv4 = view.findViewById(R.id.rv4);
        RecyclerView mRv5 = view.findViewById(R.id.rv5);
        RecyclerView mRv6 = view.findViewById(R.id.rv6);
        RecyclerView mRv7 = view.findViewById(R.id.rv7);
        RecyclerView mRv8 = view.findViewById(R.id.rv8);
        RecyclerView mRv9 = view.findViewById(R.id.rv9);

        //Prepare Layout Manager
        RecyclerView.LayoutManager managerHeader = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager3 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager4 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager5 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager6 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager7 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager8 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager manager9 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRvHeader.setLayoutManager(managerHeader);
        mRv1.setLayoutManager(manager1);
        mRv2.setLayoutManager(manager2);
        mRv3.setLayoutManager(manager3);
        mRv4.setLayoutManager(manager4);
        mRv5.setLayoutManager(manager5);
        mRv6.setLayoutManager(manager6);
        mRv7.setLayoutManager(manager7);
        mRv8.setLayoutManager(manager8);
        mRv9.setLayoutManager(manager9);

        //Prepare Adapter
        adapterHeader = new TestAdapter(mContext, mListHeader);
        adapter1 = new TestAdapter(mContext, mList1);
        adapter2 = new TestAdapter(mContext, mList2);
        adapter3 = new TestAdapter(mContext, mList3);
        adapter4 = new TestAdapter(mContext, mList4);
        adapter5 = new TestAdapter(mContext, mList5);
        adapter6 = new TestAdapter(mContext, mList6);
        adapter7 = new TestAdapter(mContext, mList7);
        adapter8 = new TestAdapter(mContext, mList8);
        adapter9 = new TestAdapter(mContext, mList9);
        mRvHeader.setAdapter(adapterHeader);
        mRv1.setAdapter(adapter1);
        mRv2.setAdapter(adapter2);
        mRv3.setAdapter(adapter3);
        mRv4.setAdapter(adapter4);
        mRv5.setAdapter(adapter5);
        mRv6.setAdapter(adapter6);
        mRv7.setAdapter(adapter7);
        mRv8.setAdapter(adapter8);
        mRv9.setAdapter(adapter9);

        //Setup recycle not scroll
        mRvHeader.setNestedScrollingEnabled(false);
        mRv1.setNestedScrollingEnabled(false);
        mRv2.setNestedScrollingEnabled(false);
        mRv3.setNestedScrollingEnabled(false);
        mRv4.setNestedScrollingEnabled(false);
        mRv5.setNestedScrollingEnabled(false);
        mRv6.setNestedScrollingEnabled(false);
        mRv7.setNestedScrollingEnabled(false);
        mRv8.setNestedScrollingEnabled(false);
        mRv9.setNestedScrollingEnabled(false);
    }

    private void prepareData() {

        ChemistryHelper mChemistryHelper = ChemistrySingle.getInstance(getContext());
        mElementList = mChemistryHelper.getAllElements();

        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup("IA"));
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup());
        mListHeader.add(new TestGroup("VIIIA"));

        mList1.add(new TestHeader("1"));
        mList1.add(new TestElement(mElementList.get(0)));
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
        mList1.add(new TestElement(mElementList.get(1)));

        //mList2
        mList2.add(new TestHeader("2"));
        mList2.add(new TestElement(mElementList.get(2)));
        mList2.add(new TestElement(mElementList.get(3)));
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
        mList2.add(new TestElement(mElementList.get(4)));
        mList2.add(new TestElement(mElementList.get(5)));
        mList2.add(new TestElement(mElementList.get(6)));
        mList2.add(new TestElement(mElementList.get(7)));
        mList2.add(new TestElement(mElementList.get(8)));
        mList2.add(new TestElement(mElementList.get(9)));

        //mList3
        mList3.add(new TestHeader("3"));
        mList3.add(new TestElement(mElementList.get(10)));
        mList3.add(new TestElement(mElementList.get(11)));
        mList3.add(new TestGroup("IIIB"));
        mList3.add(new TestGroup("IVB"));
        mList3.add(new TestGroup("VB"));
        mList3.add(new TestGroup("VIB"));
        mList3.add(new TestGroup("VIIB"));
        mList3.add(new TestGroup("VIIIB"));
        mList3.add(new TestGroup("VIIIB"));
        mList3.add(new TestGroup("VIIIB"));
        mList3.add(new TestGroup("IB"));
        mList3.add(new TestGroup("IIB"));
        mList3.add(new TestElement(mElementList.get(12)));
        mList3.add(new TestElement(mElementList.get(13)));
        mList3.add(new TestElement(mElementList.get(14)));
        mList3.add(new TestElement(mElementList.get(15)));
        mList3.add(new TestElement(mElementList.get(16)));
        mList3.add(new TestElement(mElementList.get(17)));

        //mList4
        mList4.add(new TestHeader("4"));
        addDataList(mList4, 18);

        //mList5
        mList5.add(new TestHeader("5"));
        addDataList(mList5, 36);

        //mList6
        mList6.add(new TestHeader("6"));
        addDataListSpecial(mList6, 54);

        //mList7
        mList7.add(new TestHeader("7"));
        addDataListSpecial(mList7, 86);

        //mList8
        mList8.add(new TestGroup());
        mList8.add(new TestGroup());
        mList8.add(new TestGroup());
        mList8.add(new TestHeader("Họ\nLantan", true));
        addDataRelate(mList8, 57);

        //mList9
        mList9.add(new TestGroup());
        mList9.add(new TestGroup());
        mList9.add(new TestGroup());
        mList9.add(new TestHeader("Họ\nActini", true));
        addDataRelate(mList9, 89);

        //change list data to recycle
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
        adapter5.notifyDataSetChanged();
        adapter6.notifyDataSetChanged();
        adapter7.notifyDataSetChanged();
        adapter8.notifyDataSetChanged();
        adapter9.notifyDataSetChanged();

    }

    private void addDataList(List<Object> list, int start) {
        for (int i = 0; i < 18; i++) {
            int length = start + i;
            if (length < mElementList.size()) {
                list.add(new TestElement(mElementList.get(length)));
            } else {
                break;
            }
        }
    }

    private void addDataListSpecial(List<Object> list, int start) {
        for (int i = 0; i < 18; i++) {
            int length = start + i;
            if (i == 2)
                start += 14;

            if (length < mElementList.size()) {
                list.add(new TestElement(mElementList.get(length)));
            } else {
                break;
            }
        }
    }

    private void addDataRelate(List<Object> list, int start) {
        for (int i = 0; i < 14; i++) {
            int length = start + i;
            if (length < mElementList.size()) {
                list.add(new TestElement(mElementList.get(length)));
            } else {
                break;
            }
        }
    }

    public void reloadData(int type) {
        for (int i = 0; i < mList1.size(); i++) {
            Object o = mList1.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter1.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList2.size(); i++) {
            Object o = mList2.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter2.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList3.size(); i++) {
            Object o = mList3.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter3.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList4.size(); i++) {
            Object o = mList4.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter4.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList5.size(); i++) {
            Object o = mList5.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter5.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList6.size(); i++) {
            Object o = mList6.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter6.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList7.size(); i++) {
            Object o = mList7.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter7.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList8.size(); i++) {
            Object o = mList8.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter8.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < mList9.size(); i++) {
            Object o = mList9.get(i);
            if (o instanceof TestElement) {
                int id = ((TestElement) o).getElement().getIdElement();
                if (type == mChemistryHelper.getChemistryById(id).getIdType()) {
                    ((TestElement) o).setShow(true);
                } else {
                    ((TestElement) o).setShow(false);
                }
                //adapter9.notifyItemChanged(i);
            }
        }

        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
        adapter5.notifyDataSetChanged();
        adapter6.notifyDataSetChanged();
        adapter7.notifyDataSetChanged();
        adapter8.notifyDataSetChanged();
        adapter9.notifyDataSetChanged();
    }

    private static class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<Object> mList;
        private ChemistryHelper mChemistryHelper;

        private static final int TYPE_ELEMENT = 0;
        private static final int TYPE_GROUP = 1;
        private static final int TYPE_EMPTY = 2;
        private static final int TYPE_HEADER = 3;

        TestAdapter(Context mContext, List<Object> mList) {
            this.mContext = mContext;
            this.mList = mList;
            mChemistryHelper = ChemistrySingle.getInstance(mContext);
        }

        @Override
        public int getItemViewType(int position) {
            Object o = mList.get(position);

            if (o instanceof TestElement) {
                return TYPE_ELEMENT;
            } else if (o instanceof TestHeader) {
                return TYPE_HEADER;
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

                case TYPE_HEADER:
                    v = LayoutInflater.from(mContext).inflate(R.layout.item_test_header, viewGroup, false);
                    return new ViewHolderHeader(v);

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

                    TestElement testElement = (TestElement) o;
                    Element element = testElement.getElement();
                    final int id = element.getIdElement();
                    Chemistry chemistry = mChemistryHelper.getChemistryById(id);

                    //bind view set data
                    viewHolderElement.id.setText(String.valueOf(element.getIdElement()));
                    viewHolderElement.symbol.setText(chemistry.getSymbolChemistry());
                    viewHolderElement.name.setText(chemistry.getNameChemistry());

                    //handel color layout
                    if (!testElement.isShow()) {
                        viewHolderElement.layout.setBackgroundColor(Color.WHITE);
                    } else {
                        viewHolderElement.layout.setBackgroundColor(LIST_COLOR_TYPE[chemistry.getIdType() - 1]);
                    }

                    viewHolderElement.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialogInfo(id - 1);
                        }
                    });

                    //handel color id
                    if (!testElement.isShow()) {
                        viewHolderElement.id.setTextColor(Color.BLACK);
                    } else {
                        String status = chemistry.getStatusChemistry();
                        switch (status) {
                            case "Rắn":
                                viewHolderElement.id.setTextColor(Color.BLACK);
                                break;
                            case "Lỏng":
                                viewHolderElement.id.setTextColor(Color.CYAN);
                                break;
                            case "Khí":
                                viewHolderElement.id.setTextColor(Color.RED);
                                break;
                            case "Chưa xác định":
                                viewHolderElement.id.setTextColor(Color.GRAY);
                                break;
                        }
                    }

                    break;

                case TYPE_GROUP:
                    ViewHolderGroup viewHolderGroup = (ViewHolderGroup) viewHolder;
                    o = mList.get(i);
                    TestGroup testGroup = (TestGroup) o;
                    viewHolderGroup.group.setText(testGroup.getName());
                    break;

                case TYPE_HEADER:
                    ViewHolderHeader viewHolderHeader = (ViewHolderHeader) viewHolder;
                    o = mList.get(i);
                    TestHeader testHeader = (TestHeader) o;
                    viewHolderHeader.header.setText(testHeader.getName());
                    viewHolderHeader.header.setText(testHeader.getName());
                    if (!testHeader.isRelative()) {
                        viewHolderHeader.header.setTextSize(35);
                    }
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

        static class ViewHolderHeader extends RecyclerView.ViewHolder {
            private TextView header;

            ViewHolderHeader(@NonNull View itemView) {
                super(itemView);
                header = itemView.findViewById(R.id.tv_header);
            }
        }

        private void showDialogInfo(int position) {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.context_dialog_periodic);

            ViewPager viewPager;
            viewPager = dialog.findViewById(R.id.view_pager);

            SwipeAdapter adapter = new SwipeAdapter(mContext, mChemistryHelper.getAllElements());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
        }

    }
}
