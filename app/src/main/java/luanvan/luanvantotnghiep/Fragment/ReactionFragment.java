package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ReactionAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class ReactionFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private EditText mEdtReactants;
    private EditText mEdtProducts;
    private Button mBtnSearchReaction;

    private CardView mCvTitleInput;

    private RecyclerView mRvReaction;

    private ChemistryHelper mChemistryHelper;
    private List<ChemicalReaction> mChemicalReactionList;

    private ReactionAdapter mReactionAdapter;

    public ReactionFragment() {
    }

    public static ReactionFragment newInstance() {
        return new ReactionFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reaction, container, false);
        //stateAlwaysHidden|stateVisible|adjustPan
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                |WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init(view);

        addEvent();

        return view;
    }

    private void addEvent() {
        mBtnSearchReaction.setOnClickListener(this);
    }

    private void init(View view) {
        mEdtReactants = view.findViewById(R.id.edt_reactants);
        mEdtProducts = view.findViewById(R.id.edt_products);
        mBtnSearchReaction = view.findViewById(R.id.btn_search_reaction);

        mCvTitleInput = view.findViewById(R.id.cv_title_input);

        mRvReaction = view.findViewById(R.id.rv_reaction);
    }
    /*
     * 2:H2 + 1:O2
     * --> 2:H20
     * */

    private void setAdapter() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBtnSearchReaction.getWindowToken(), 0);

        mChemistryHelper = ChemistrySingle.getInstance(mContext);
        mChemicalReactionList = mChemistryHelper.getAllChemicalReaction();

        Helper helper = Helper.getInstant();
        List<ChemicalReaction> listFilter = new ArrayList<>();
        List<ChemicalReaction> listTemp = new ArrayList<>();
        List<ChemicalReaction> listProductTemp = new ArrayList<>();

        boolean isReactant = false;
        boolean isContinue = true;

        if (mEdtReactants.getText().toString().equals("") && mEdtProducts.getText().toString().equals("")) {
            mCvTitleInput.setVisibility(View.VISIBLE);
            Toast.makeText(mContext, "Chưa nhập dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            if (!mEdtReactants.getText().toString().equals("")) {
                isReactant = true;

                String[] inputReactant = mEdtReactants.getText().toString().trim().replaceAll("\\s+", " ").split(" ");
                for (int i = 0; i < inputReactant.length; i++) {
                    if (i == 0) {
                        for (ChemicalReaction chemicalReaction : mChemicalReactionList) {
                            String[] reactant = chemicalReaction.getReactants().split(" \\+ ");
                            for (String aReactant : reactant) {
                                if (inputReactant[i].toLowerCase().trim().equals(helper.handelChemistryInReaction(aReactant))) {
                                    listFilter.add(chemicalReaction);
                                    break;
                                }
                            }
                        }
                    } else {
                        listTemp.addAll(listFilter);
                        listFilter.clear();
                        for (ChemicalReaction chemicalReaction : listTemp) {
                            String[] reactant = chemicalReaction.getReactants().split(" \\+ ");
                            for (String aReactant : reactant) {
                                if (inputReactant[i].toLowerCase().trim().equals(helper.handelChemistryInReaction(aReactant))) {
                                    listFilter.add(chemicalReaction);
                                    break;
                                }
                            }
                        }
                        listTemp.clear();
                    }
                }
            }
            if (!isReactant) {
                listProductTemp.addAll(mChemicalReactionList);
            } else {
                if (listFilter.size() == 0) {
                    isContinue = false;
                } else {
                    listProductTemp.addAll(listFilter);
                }
            }

            if (!mEdtProducts.getText().toString().equals("") && isContinue) {
                listFilter.clear();
                String[] inputReactant = mEdtProducts.getText().toString().trim().replaceAll("\\s+", " ").split(" ");

                for (int i = 0; i < inputReactant.length; i++) {
                    if (i == 0) {
                        for (ChemicalReaction chemicalReaction : listProductTemp) {
                            String[] reactant = chemicalReaction.getProducts().split(" \\+ ");
                            for (String aReactant : reactant) {
                                if (inputReactant[i].toLowerCase().trim().equals(helper.handelChemistryInReaction(aReactant))) {
                                    listFilter.add(chemicalReaction);
                                    break;
                                }
                            }
                        }
                    } else {
                        listTemp.addAll(listFilter);
                        listFilter.clear();
                        for (ChemicalReaction chemicalReaction : listTemp) {
                            String[] reactant = chemicalReaction.getProducts().split(" \\+ ");
                            for (String aReactant : reactant) {
                                if (inputReactant[i].toLowerCase().trim().equals(helper.handelChemistryInReaction(aReactant))) {
                                    listFilter.add(chemicalReaction);
                                    break;
                                }
                            }
                        }
                        listTemp.clear();
                    }
                }
            }
            mReactionAdapter = new ReactionAdapter(mContext, listFilter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext,
                    LinearLayoutManager.VERTICAL,
                    false);
            mRvReaction.setHasFixedSize(true);
            mRvReaction.setAdapter(mReactionAdapter);
            mRvReaction.setLayoutManager(manager);

            if (listFilter.size() > 0) {
                mCvTitleInput.setVisibility(View.GONE);
            } else {
                Toast.makeText(mContext, "Hiện chưa có trong cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
                mCvTitleInput.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_reaction:
                setAdapter();
                break;

        }
    }
}
