package luanvan.luanvantotnghiep.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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

    //private boolean mIsElement;

    private List<Chemistry> mChemistryList = new ArrayList<>();
    private List<Element> mElementList = new ArrayList<>();
    private List<Compound> mCompoundList = new ArrayList<>();
    private List<Type> mTypeList = new ArrayList<>();
    private List<ProducedBy> mProducedByList = new ArrayList<>();

    private ChemistryHelper mChemistryHelper;

    private Context mContext;

    private ChipChemistryAdapter mChipChemistryAdapter;
    private CreatedReactionAdapter mCreatedReactionAdapter;
    private ReactWithAdapter mReactWithAdapter;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            //mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    //Log.i("onQueryTextChange", newText);

                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    textSubmit(query);
                    return true;
                }
            };
            mSearchView.setOnQueryTextListener(queryTextListener);
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

    private void textSubmit(String string) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

        mChemistryList.addAll(mChemistryHelper.getAllChemistry());
        mElementList.addAll(mChemistryHelper.getAllElements());
        mCompoundList.addAll(mChemistryHelper.getAllCompound());
        mTypeList.addAll(mChemistryHelper.getAllTypes());
        mProducedByList.addAll(mChemistryHelper.getAllProducedBy());

        boolean mIsElement = false;
        boolean mIsCompound = false;

        Chemistry chemistryEml = null;
        Type typeDataEml = null;
        Chemistry chemistryCom = null;
        Type typeDataCom = null;
        Element elementData = null;
        Compound compoundData = null;

        //search by symbol
        for (Element element : mElementList) {
            if (string.toLowerCase().equals(element.getMolecularFormula().toLowerCase())) {
                mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(element.getMolecularFormula())));

                for (Chemistry chemistry : mChemistryList) {
                    if (element.getIdElement() == chemistry.getIdChemistry()) {
//                        mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
//                        mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
//                        mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));

                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
//                                mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                                typeDataEml = type;
                                break;
                            }
                        }
//                        if (element.getMolecularFormula().toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())) {
//                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
//                        } else {
//                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry() * 2) + " g/mol</font>"));
//                        }
                        chemistryEml = chemistry;
                        elementData = element;
                        mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(element.getMolecularFormula())), false);
                        setParamAdapter(chemistry.getIdChemistry());
                    }
                }
                mIsElement = true;
                mTvOtherNames.setVisibility(View.GONE);
                break;
            }
        }

        //search by name
        for (Chemistry chemistry : mChemistryList) {
            if (string.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                for (Element element : mElementList) {
                    if (chemistry.getIdChemistry() == element.getIdElement()) {
//                        mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(element.getMolecularFormula())));
//                        mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
//                        mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
//                        mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));

                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                //mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                                typeDataEml = type;
                                break;
                            }
                        }
//                        if (element.getMolecularFormula().toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())) {
//                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
//                        } else {
//                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry() * 2) + " g/mol</font>"));
//                        }
                        elementData = element;
                        chemistryEml = chemistry;
                        mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(element.getMolecularFormula())), false);
                        setParamAdapter(chemistry.getIdChemistry());
                    }
                }
                mIsElement = true;
                mTvOtherNames.setVisibility(View.GONE);
                break;
            }
        }


        for (Chemistry chemistry : mChemistryList) {
            if (string.toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())
                    || string.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                for (Compound compound : mCompoundList) {
                    if (chemistry.getIdChemistry() == compound.getIdCompound()) {
//                        mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(chemistry.getSymbolChemistry())));
//                        mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
//                        mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
//                        mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
//                        mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
//                        if (!compound.getOtherNames().equals("")) {
//                            mTvOtherNames.setVisibility(View.VISIBLE);
//                            mTvOtherNames.setText(Html.fromHtml("<font color='gray'>Tên khác: </font><font color='black'>" + compound.getOtherNames() + "</font>"));
//                        } else {
//                            mTvOtherNames.setVisibility(View.GONE);
//                        }
                        for (Type type : mTypeList) {
                            if (chemistry.getIdType() == type.getIdType()) {
                                //mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                                typeDataCom = type;
                                break;
                            }
                        }
                        compoundData = compound;
                        chemistryCom = chemistry;
//                        mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(chemistry.getSymbolChemistry())), false);
//                        setParamAdapter(chemistry.getIdChemistry());
                    }
                }
                break;
            }
        }


        //case: not is element == compound
//        if (!mIsElement) {
//            for (Chemistry chemistry : mChemistryList) {
//                if (string.toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())
//                        || string.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
//                    for (Compound compound : mCompoundList) {
//                        if (chemistry.getIdChemistry() == compound.getIdCompound()) {
//                            mTvSymbolChemistry.setText(Html.fromHtml(Helper.getInstant().handelText(chemistry.getSymbolChemistry())));
//                            mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
//                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
//                            mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
//                            mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
//                            if (!compound.getOtherNames().equals("")) {
//                                mTvOtherNames.setVisibility(View.VISIBLE);
//                                mTvOtherNames.setText(Html.fromHtml("<font color='gray'>Tên khác: </font><font color='black'>" + compound.getOtherNames() + "</font>"));
//                            } else {
//                                mTvOtherNames.setVisibility(View.GONE);
//                            }
//                            for (Type type : mTypeList) {
//                                if (chemistry.getIdType() == type.getIdType()) {
//                                    mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
//                                    break;
//                                }
//                            }
//                            mSearchView.setQuery(Html.fromHtml(Helper.getInstant().handelText(chemistry.getSymbolChemistry())), false);
//                            setParamAdapter(chemistry.getIdChemistry());
//                        }
//                    }
//                    break;
//                }
//            }
//        }
    }

    //SEND DATA TO ADAPTER
    private void setParamAdapter(Integer idChemistry) {
        //chip
        mChipChemistryAdapter = new ChipChemistryAdapter(mContext, idChemistry);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRvChipChemistry.setLayoutManager(layoutManager);
        mRvChipChemistry.setHasFixedSize(true);

        mRvChipChemistry.setAdapter(mChipChemistryAdapter);

        mChipChemistryAdapter.setOnItemClickListener(this);

        mCvChipChemistry.setVisibility(mChipChemistryAdapter.getShowChipChemistry());

        //phương trình tạo thành
        mCreatedReactionAdapter = new CreatedReactionAdapter(mContext, idChemistry);
        RecyclerView.LayoutManager layoutCreatedReaction = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,
                false);
        mRvCreatedReaction.setLayoutManager(layoutCreatedReaction);
        mRvCreatedReaction.setHasFixedSize(true);
        mRvCreatedReaction.setAdapter(mCreatedReactionAdapter);

        mCvCreatedReaction.setVisibility(mCreatedReactionAdapter.getShowCreatedReaction());

        //được phản ứng với chất nào
        mReactWithAdapter = new ReactWithAdapter(mContext, idChemistry);
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
        Log.i("hns", "onReloadData");
        Log.i("hns", "ChipText: " + chipText);

        textSubmit(chipText.toLowerCase());
    }

}
