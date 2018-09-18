package luanvan.luanvantotnghiep.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Compound;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class SearchFragment extends Fragment {

    private SearchView mSearchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private TextView mTvSymbolChemistry;
    private TextView mTvNameChemistry;
    private TextView mTvWeightChemistry;
    private TextView mTvNameType;
    private TextView mTvStatusChemistry;
    private TextView mTvColorChemistry;
    private TextView mTvOtherNames;

    private boolean mIsElement;

    List<Chemistry> mChemistryList = new ArrayList<>();
    List<Element> mElementList = new ArrayList<>();
    List<Compound> mCompoundList = new ArrayList<>();
    List<Type> mTypeList = new ArrayList<>();
    List<ProducedBy> mProducedByList = new ArrayList<>();

    private ChemistryHelper mChemistryHelper;

    private Context mContext;

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

        addDataGas();

        return view;
    }

    private void addDataGas() {

    }

    private void init(View view) {
        mTvSymbolChemistry = view.findViewById(R.id.tv_symbol_chemistry);
        mTvNameChemistry = view.findViewById(R.id.tv_name_chemistry);
        mTvWeightChemistry = view.findViewById(R.id.tv_weight_chemistry);
        mTvNameType = view.findViewById(R.id.tv_name_type);
        mTvStatusChemistry = view.findViewById(R.id.tv_status_chemistry);
        mTvColorChemistry = view.findViewById(R.id.tv_color_chemistry);
        mTvOtherNames = view.findViewById(R.id.tv_other_names);

        mChemistryHelper = ChemistrySingle.getInstance(mContext);
//        mProducedByList.addAll(mChemistryHelper.getAllProducedBy());
//        for (ProducedBy producedBy : mProducedByList) {
//            Log.i("hns right", ""+producedBy.getIdRightReaction());
//            Log.i("hns left", ""+producedBy.getIdLeftReaction());
//            Log.i("hns", "-----------");
//        }
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

        mChemistryList.addAll(mChemistryHelper.getAllChemistry());
        mElementList.addAll(mChemistryHelper.getAllElements());
        mCompoundList.addAll(mChemistryHelper.getAllCompound());
        mTypeList.addAll(mChemistryHelper.getAllTypes());
        mProducedByList.addAll(mChemistryHelper.getAllProducedBy());


        mIsElement = false;

        for (Element element : mElementList) {
            if (string.toLowerCase().equals(element.getMolecularFormula().toLowerCase())) {
                mTvSymbolChemistry.setText(Html.fromHtml(handelText(element.getMolecularFormula())));

                for (Chemistry chemistry : mChemistryList) {
                    if (element.getIdElement() == chemistry.getIdChemistry()) {
                        mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
                        mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
                        mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
                        for (Type type : mTypeList){
                            if(chemistry.getIdType() == type.getIdType()){
                                mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                            }
                        }
                        if (element.getMolecularFormula().toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())) {
                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
                        } else {
                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry() * 2) + " g/mol</font>"));
                        }
                    }
                }
                mIsElement = true;
                break;
            }
        }

        for (Chemistry chemistry : mChemistryList) {
            if (string.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                for (Element element : mElementList) {
                    if (chemistry.getIdChemistry() == element.getIdElement()) {
                        mTvSymbolChemistry.setText(Html.fromHtml(handelText(element.getMolecularFormula())));
                        mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
                        mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
                        mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
                        for (Type type : mTypeList){
                            if(chemistry.getIdType() == type.getIdType()){
                                mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                            }
                        }
                        if (element.getMolecularFormula().toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())) {
                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
                        } else {
                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry() * 2) + " g/mol</font>"));
                        }
                    }
                }
                mIsElement = true;
                break;
            }
        }
        if (!mIsElement) {
            for (Chemistry chemistry : mChemistryList) {
                if (string.toLowerCase().equals(chemistry.getSymbolChemistry().toLowerCase())
                        || string.toLowerCase().equals(chemistry.getNameChemistry().toLowerCase())) {
                    for (Compound compound : mCompoundList) {
                        if (chemistry.getIdChemistry() == compound.getIdCompound()) {
                            mTvSymbolChemistry.setText(Html.fromHtml(handelText(chemistry.getSymbolChemistry())));
                            mTvNameChemistry.setText(Html.fromHtml("<font color='gray'>Tên: </font><font color='black'>" + chemistry.getNameChemistry() + "</font>"));
                            mTvWeightChemistry.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + Double.toString(chemistry.getWeightChemistry()) + " g/mol</font>"));
                            mTvStatusChemistry.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
                            mTvColorChemistry.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
                            if(!compound.getOtherNames().equals("")){
                                mTvOtherNames.setVisibility(View.VISIBLE);
                                mTvOtherNames.setText(Html.fromHtml("<font color='gray'>Tên khác: </font><font color='black'>" + compound.getOtherNames() + "</font>"));
                            }else {
                                mTvOtherNames.setVisibility(View.GONE);
                            }
                            for (Type type : mTypeList){
                                if(chemistry.getIdType() == type.getIdType()){
                                    mTvNameType.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + type.getNameType() + "</font>"));
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private String handelText(String textInput) {
        String result = "";

        for (int j = 0; j < textInput.length(); j++) {
            char c = textInput.charAt(j);

            if (c >= '0' && c <= '9') {
                result += "<small><sub>" + c + "</sub></small>";
            } else {
                result += c;
            }
        }

        return result;
    }
}
