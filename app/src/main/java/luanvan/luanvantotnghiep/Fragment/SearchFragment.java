package luanvan.luanvantotnghiep.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ChipChemistryAdapter;
import luanvan.luanvantotnghiep.Adapter.CreatedReactionAdapter;
import luanvan.luanvantotnghiep.Adapter.ReactWithAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Compound;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class SearchFragment extends Fragment implements ChipChemistryAdapter.CommunicateChip {

    private SearchView mSearchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private TextView mTvSymbolChemistry;
    private TextView mTvNameChemistry;
    private TextView mTvWeightChemistry;
    private TextView mTvNameType;
    private TextView mTvStatusChemistry;
    private TextView mTvColorChemistry;
    private TextView mTvOtherNames;

    private CardView mCvChipChemistry;
    private CardView mCvCreatedReaction;
    private CardView mCvReactWith;

    private RecyclerView mRvChipChemistry;
    private RecyclerView mRvCreatedReaction;
    private RecyclerView mRvReactWith;

    private CardView mCvHide;
    private LinearLayout mLnShow;

    //private boolean mIsElement;

    private List<Chemistry> mChemistryList = new ArrayList<>();
    private List<Element> mElementList = new ArrayList<>();
    private List<Compound> mCompoundList = new ArrayList<>();
    private List<Type> mTypeList = new ArrayList<>();
    private List<ProducedBy> mProducedByList = new ArrayList<>();

    private ChemistryHelper mChemistryHelper;

    private Context mContext;

    private SearchView.SearchAutoComplete searchAutoComplete;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        mTvSymbolChemistry = view.findViewById(R.id.tv_symbol_chemistry);
        mTvNameChemistry = view.findViewById(R.id.tv_name_chemistry);
        mTvWeightChemistry = view.findViewById(R.id.tv_weight_chemistry);
        mTvNameType = view.findViewById(R.id.tv_name_type);
        mTvStatusChemistry = view.findViewById(R.id.tv_status_chemistry);
        mTvColorChemistry = view.findViewById(R.id.tv_color_chemistry);
        mTvOtherNames = view.findViewById(R.id.tv_other_names);

        mCvChipChemistry = view.findViewById(R.id.cv_chip_chemistry);
        mCvCreatedReaction = view.findViewById(R.id.cv_created_reaction);
        mCvReactWith = view.findViewById(R.id.cv_react_with);

        mRvChipChemistry = view.findViewById(R.id.rv_chip_chemistry);
        mRvCreatedReaction = view.findViewById(R.id.rv_created_reaction);
        mRvReactWith = view.findViewById(R.id.rv_react_with);

        mChemistryHelper = ChemistrySingle.getInstance(mContext);

        mCvHide = view.findViewById(R.id.cv_hide);
        mLnShow = view.findViewById(R.id.ln_show);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final String[] oldText = {""};

        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    //Log.i("onQueryTextChange", newText);
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchAutoComplete.dismissDropDown();
                    if (!TextUtils.equals(query.toLowerCase(), oldText[0].toLowerCase())) {
                        textSubmit(query);
                        oldText[0] = query;

                    }
                    return false;
                }
            };
            mSearchView.setOnQueryTextListener(queryTextListener);

            //Auto complete
            searchAutoComplete = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchAutoComplete.setThreshold(1);

            // Create a new ArrayAdapter and add data to search auto complete object.
            List<String> list = handleComplete();
            ArrayAdapter<String> newsAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, list);
            searchAutoComplete.setAdapter(newsAdapter);

            searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                    String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                    //String param = queryString.split(" - ")[0];
                    textSubmit(queryString);
                }
            });

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        mSearchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private List<String> handleComplete() {
        List<String> result = new ArrayList<>();
        List<Element> elementList = mChemistryHelper.getAllElements();
        for (Chemistry chemistry : mChemistryHelper.getAllChemistry()) {
            boolean isFindElement = false;
            for (Element element : elementList) {
                if (chemistry.getIdChemistry() == element.getIdElement()) {
                    result.add(element.getMolecularFormula() + " - " + chemistry.getNameChemistry());
                    isFindElement = true;
                    break;
                }
            }
            if (!isFindElement) {
                result.add(chemistry.getSymbolChemistry() + " - " + chemistry.getNameChemistry());
            }
        }
        return result;
    }

    private void textSubmit(String string) {
        mCvHide.setVisibility(View.GONE);
        mLnShow.setVisibility(View.VISIBLE);
        String[] tmp = string.split(" - ");
        String param = tmp[0];
        if (tmp.length > 1) {
            param = tmp[1];
        }

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

        mChemistryList.addAll(mChemistryHelper.getAllChemistry());
        mElementList.addAll(mChemistryHelper.getAllElements());
        mCompoundList.addAll(mChemistryHelper.getAllCompound());
        mTypeList.addAll(mChemistryHelper.getAllTypes());
        mProducedByList.addAll(mChemistryHelper.getAllProducedBy());

        boolean mIsElement = false;
        boolean mIsCompound = false;

        Chemistry chemistryEle = null;
        Type typeDataEml = null;
        Chemistry chemistryCom = null;
        Type typeDataCom = null;
        Element elementData = null;
        Compound compoundData = null;

        //search by symbol
        for (Element element : mElementList) {
            if (param.toLowerCase().equals(element.getMolecularFormula().toLowerCase())) {
                for (Chemistry chemistry : mChemistryList) {
                    if (element.getIdElement() == chemistry.getIdChemistry()) {
                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                typeDataEml = type;
                                break;
                            }
                        }
                        mIsElement = true;
                        chemistryEle = chemistry;
                        elementData = element;
                        break;
                    }
                }
                break;
            }
        }

        //search by name
        for (Chemistry chemistry : mChemistryList) {
            if (param.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                for (Element element : mElementList) {
                    if (chemistry.getIdChemistry() == element.getIdElement()) {
                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                typeDataEml = type;
                                break;
                            }
                        }
                        mIsElement = true;
                        elementData = element;
                        chemistryEle = chemistry;
                        break;
                    }
                }
                break;
            }
        }

        //Comp
        for (Chemistry chemistry : mChemistryList) {
            if (param.toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())
                    || param.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                for (Compound compound : mCompoundList) {
                    if (chemistry.getIdChemistry() == compound.getIdCompound()) {
                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                typeDataCom = type;
                                break;
                            }
                        }
                        mIsCompound = true;
                        compoundData = compound;
                        chemistryCom = chemistry;
                        break;
                    }
                }
            }
        }

        if (mIsElement && mIsCompound) { // 2 thằng
            showDialog(chemistryEle, chemistryCom);
        } else if (!mIsElement && !mIsCompound) { //ko có rồi
            mCvHide.setVisibility(View.VISIBLE);
            mLnShow.setVisibility(View.GONE);
            Toast.makeText(mContext, "Hiện chưa có trong cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
        } else { //1 trong 2
            //mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(param)), false);
            if (mIsElement) {
                mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(elementData.getMolecularFormula())));
                mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistryEle.getNameChemistry() + "</font>"));
                mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistryEle.getStatusChemistry() + "</font>"));
                mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistryEle.getColorChemistry() + "</font>"));
                mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + typeDataEml.getNameType() + "</font>"));
                if (elementData.getMolecularFormula().toLowerCase().equals(chemistryEle.getSymbolChemistry().toLowerCase())) {
                    mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistryEle.getWeightChemistry()) + " g/mol</font>"));
                } else {
                    mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistryEle.getWeightChemistry() * 2) + " g/mol</font>"));
                }
                mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(elementData.getMolecularFormula())), false);
                setParamAdapter(chemistryEle.getIdChemistry());
                mTvOtherNames.setVisibility(View.GONE);


            } else if (mIsCompound) {
                mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(chemistryCom.getSymbolChemistry())));
                mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistryCom.getNameChemistry() + "</font>"));
                mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistryCom.getWeightChemistry()) + " g/mol</font>"));
                mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistryCom.getStatusChemistry() + "</font>"));
                mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistryCom.getColorChemistry() + "</font>"));
                if (!compoundData.getOtherNames().equals("")) {
                    mTvOtherNames.setVisibility(View.VISIBLE);
                    mTvOtherNames.setText(Html.fromHtml("<font color='gray'>Tên khác: </font><font color='black'>" + compoundData.getOtherNames() + "</font>"));
                } else {
                    mTvOtherNames.setVisibility(View.GONE);
                }

                mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + typeDataCom.getNameType() + "</font>"));
                mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(chemistryCom.getSymbolChemistry())), false);
                setParamAdapter(chemistryCom.getIdChemistry());
            }
        }
        searchAutoComplete.dismissDropDown();
    }

    //SEND DATA TO ADAPTER
    private void setParamAdapter(Integer idChemistry) {
        //chip
        ChipChemistryAdapter mChipChemistryAdapter = new ChipChemistryAdapter(mContext, idChemistry);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRvChipChemistry.setLayoutManager(layoutManager);
        mRvChipChemistry.setHasFixedSize(true);

        mRvChipChemistry.setAdapter(mChipChemistryAdapter);

        mChipChemistryAdapter.setOnItemClickListener(this);

        mCvChipChemistry.setVisibility(mChipChemistryAdapter.getShowChipChemistry());

        //phương trình tạo thành
        CreatedReactionAdapter mCreatedReactionAdapter = new CreatedReactionAdapter(mContext, idChemistry);
        RecyclerView.LayoutManager layoutCreatedReaction = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);
        mRvCreatedReaction.setLayoutManager(layoutCreatedReaction);
        mRvCreatedReaction.setHasFixedSize(true);
        mRvCreatedReaction.setAdapter(mCreatedReactionAdapter);

        mCvCreatedReaction.setVisibility(mCreatedReactionAdapter.getShowCreatedReaction());

        //được phản ứng với chất nào
        ReactWithAdapter mReactWithAdapter = new ReactWithAdapter(mContext, idChemistry);
        RecyclerView.LayoutManager layoutReactWith = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);

        mRvReactWith.setLayoutManager(layoutReactWith);
        mRvReactWith.setHasFixedSize(true);
        mRvReactWith.setAdapter(mReactWithAdapter);

        mCvReactWith.setVisibility(mReactWithAdapter.getShowReactWith());
    }

    @Override
    public void onReloadData(String chipText) {
        textSubmit(chipText.toLowerCase());
    }

    private void showDialog(final Chemistry chemistryEle, final Chemistry chemistryCom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Bạn muốn tìm gì?");

        builder.setPositiveButton(chemistryEle.getNameChemistry(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                textSubmit(chemistryEle.getNameChemistry());
                searchAutoComplete.dismissDropDown();
            }
        });
        builder.setNegativeButton(chemistryCom.getNameChemistry(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                textSubmit(chemistryCom.getNameChemistry());
                searchAutoComplete.dismissDropDown();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
