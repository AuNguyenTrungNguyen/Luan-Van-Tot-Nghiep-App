package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Anion;
import luanvan.luanvantotnghiep.Model.Cation;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class SolubilityTableFragment extends Fragment {

    private ChemistryHelper mChemistryHelper;
    private Context mContext;

    private List<Anion> mAnionList;
    private List<Cation> mCationList;
    private List<Solute> mSoluteList;

    private TableLayout mTlSolute;
    private TableLayout mTlAnion;

    private static final int ITEM_WIDTH = 130;
    private static final int ITEM_HEIGHT = 60;

    public SolubilityTableFragment() {
    }

    public static SolubilityTableFragment newInstance() {
        return new SolubilityTableFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solubility_table, container, false);

        init(view);

        addDataAnionTable();

        addDataCationTable();

        addDataSoluteTable();

        showSolubilityTable();

        return view;
    }

    private void init(View v) {
        mChemistryHelper = ChemistrySingle.getInstance(mContext);
        mAnionList = mChemistryHelper.getAllAnion();
        mCationList = mChemistryHelper.getAllCation();
        mSoluteList = mChemistryHelper.getAllSolute();

        mTlSolute = v.findViewById(R.id.tl_solubility);
        mTlAnion = v.findViewById(R.id.tl_anion);
    }

    private String showIon(String name, String valence) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c >= '0' && c <= '9') {
                result.append("<small><sub>").append(c).append("</sub></small>");
            } else {
                result.append(c);
            }
        }

        String ionType = valence.substring(valence.length() - 1, valence.length());

        if (ionType.equals("+")) {
            valence = " <font color='red'>" + valence + "</font>";
        } else {
            valence = " <font color='blue'>" + valence + "</font>";
        }

        String temp = result + "<small><sup>" + valence + "</sup></small>";
        result = new StringBuilder("<b>" + temp + "</b>");

        return result.toString();
    }

    private String getSolubility(int anion, int cation) {
        String result = "";

        for (int i = 0; i < mSoluteList.size(); i++) {
            Solute solute = mSoluteList.get(i);
            if (solute.getAnion() == anion && solute.getCation() == cation) {
                return solute.getSolute();
            }
        }
        return result;
    }

    private void setItemValue(String text, TextView tvShow) {
        tvShow.setText(text);
        tvShow.setGravity(Gravity.CENTER);
        tvShow.setTypeface(tvShow.getTypeface(), Typeface.BOLD);
        switch (text) {
            case "T":
                tvShow.setTextColor(Color.BLUE);
                break;
            case "K":
                tvShow.setTextColor(Color.RED);
                break;
            case "I":
                break;
            case "-":
                tvShow.setTextColor(Color.DKGRAY);
                break;
        }
    }

    private void showSolubilityTable() {
        int lengthAnion = mAnionList.size();
        int lengthCation = mCationList.size();

        //Create header Cation
        TableRow rowHeader = new TableRow(mContext);
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setGravity(Gravity.CENTER);
        tvEmpty.setBackgroundResource(R.drawable.bg_empty_item_solute);
        rowHeader.addView(tvEmpty, ITEM_WIDTH, ITEM_HEIGHT);
        mTlAnion.addView(rowHeader);

        //Create header anion
        for (Anion anion : mAnionList) {
            TextView tvHeader = new TextView(mContext);
            rowHeader = new TableRow(mContext);
            tvHeader.setText(Html.fromHtml(showIon(anion.getNameAnion(), anion.getValenceAnion())));
            tvHeader.setGravity(Gravity.CENTER);
            tvHeader.setBackgroundResource(R.drawable.bg_header_anion_solute);
            rowHeader.addView(tvHeader, ITEM_WIDTH, ITEM_HEIGHT);
            mTlAnion.addView(rowHeader);
        }

        //Create header cation
        rowHeader = new TableRow(mContext);
        for (Cation cation : mCationList) {
            TextView tvHeader = new TextView(mContext);
            tvHeader.setText(Html.fromHtml(showIon(cation.getNameCation(), cation.getValenceCation())));
            tvHeader.setGravity(Gravity.CENTER);
            tvHeader.setBackgroundResource(R.drawable.bg_header_cation_solute);
            rowHeader.addView(tvHeader, ITEM_WIDTH, ITEM_HEIGHT);
        }
        mTlSolute.addView(rowHeader);

        //Add item
        for (int i = 1; i <= lengthAnion; i++) {
            TableRow row = new TableRow(mContext);

            for (int j = 1; j <= lengthCation; j++) {
                TextView tvSolubility = new TextView(mContext);
                final String solubility = getSolubility(i, j);

                if (!solubility.equals("")) {
                    setItemValue(solubility, tvSolubility);
                    tvSolubility.setBackgroundResource(R.drawable.bg_item_solute);
                }
                row.addView(tvSolubility, ITEM_WIDTH, ITEM_HEIGHT);
            }
            mTlSolute.addView(row);
        }
    }

    private void addDataAnionTable() {

        List<Anion> anionList = new ArrayList<>();

        Anion anion = new Anion(1, "Cl", "-");
        anionList.add(anion);

        anion = new Anion(2, "Br", "-");
        anionList.add(anion);

        anion = new Anion(3, "I", "-");
        anionList.add(anion);

        anion = new Anion(4, "NO3", "-");
        anionList.add(anion);

        anion = new Anion(5, "CH3COO", "-");
        anionList.add(anion);

        anion = new Anion(6, "S", "2-");
        anionList.add(anion);

        anion = new Anion(7, "SO3", "2-");
        anionList.add(anion);

        anion = new Anion(8, "SO4", "2-");
        anionList.add(anion);

        anion = new Anion(9, "CO3", "2-");
        anionList.add(anion);

        anion = new Anion(10, "SiO3", "2-");
        anionList.add(anion);

        anion = new Anion(11, "CrO4", "2-");
        anionList.add(anion);

        anion = new Anion(12, "PO4", "3-");
        anionList.add(anion);

        anion = new Anion(13, "OH", "-");
        anionList.add(anion);

        //Check and add data
        if (anionList.size() == mChemistryHelper.getAllAnion().size()) {
            Log.i("ANTN", "Table Anion available");
        } else {
            Log.i("ANTN", "Table Anion updated");
            //Add to database
            mChemistryHelper.emptyAnion();
            for (Anion item : anionList) {
                mChemistryHelper.addAnion(item);
            }
        }
    }

    private void addDataCationTable() {

        List<Cation> cationList = new ArrayList<>();

        Cation cation = new Cation(1, "Li", "+");
        cationList.add(cation);

        cation = new Cation(2, "Na", "+");
        cationList.add(cation);

        cation = new Cation(3, "K", "+");
        cationList.add(cation);

        cation = new Cation(4, "NH4", "+");
        cationList.add(cation);

        cation = new Cation(5, "Cu", "+");
        cationList.add(cation);

        cation = new Cation(6, "Ag", "+");
        cationList.add(cation);

        cation = new Cation(7, "Mg", "2+");
        cationList.add(cation);

        cation = new Cation(8, "Ca", "2+");
        cationList.add(cation);

        cation = new Cation(9, "Sr", "2+");
        cationList.add(cation);

        cation = new Cation(10, "Ba", "2+");
        cationList.add(cation);

        cation = new Cation(11, "Zn", "2+");
        cationList.add(cation);

        cation = new Cation(12, "Hg", "2+");
        cationList.add(cation);

        cation = new Cation(13, "Al", "3+");
        cationList.add(cation);

        cation = new Cation(14, "Sn", "2+");
        cationList.add(cation);

        cation = new Cation(15, "Pb", "2+");
        cationList.add(cation);

        cation = new Cation(16, "Bi", "3+");
        cationList.add(cation);

        cation = new Cation(17, "Cr", "3+");
        cationList.add(cation);

        cation = new Cation(18, "Mn", "2+");
        cationList.add(cation);

        cation = new Cation(19, "Fe", "3+");
        cationList.add(cation);

        cation = new Cation(20, "Fe", "2+");
        cationList.add(cation);

        //Check and add data
        if (cationList.size() == mChemistryHelper.getAllCation().size()) {
            Log.i("ANTN", "Table Cation available");
        } else {
            //Add to database
            Log.i("ANTN", "Table Cation updated");
            mChemistryHelper.emptyCation();
            for (Cation item : cationList) {
                mChemistryHelper.addCation(item);
            }
        }
    }

    private void addDataSoluteTable() {

        List<Solute> soluteList = new ArrayList<>();

        Solute solute;
        solute = new Solute(1, 1, "T");
        soluteList.add(solute);

        solute = new Solute(1, 2, "T");
        soluteList.add(solute);

        solute = new Solute(1, 3, "T");
        soluteList.add(solute);

        solute = new Solute(1, 4, "T");
        soluteList.add(solute);

        solute = new Solute(1, 5, "T");
        soluteList.add(solute);

        solute = new Solute(1, 6, "K");
        soluteList.add(solute);

        solute = new Solute(1, 7, "T");
        soluteList.add(solute);

        solute = new Solute(1, 8, "T");
        soluteList.add(solute);

        solute = new Solute(1, 9, "T");
        soluteList.add(solute);

        solute = new Solute(1, 10, "T");
        soluteList.add(solute);

        solute = new Solute(1, 11, "T");
        soluteList.add(solute);

        solute = new Solute(1, 12, "T");
        soluteList.add(solute);

        solute = new Solute(1, 13, "T");
        soluteList.add(solute);

        solute = new Solute(1, 14, "T");
        soluteList.add(solute);

        solute = new Solute(1, 15, "I");
        soluteList.add(solute);

        solute = new Solute(1, 16, "-");
        soluteList.add(solute);

        solute = new Solute(1, 17, "T");
        soluteList.add(solute);

        solute = new Solute(1, 18, "T");
        soluteList.add(solute);

        solute = new Solute(1, 19, "T");
        soluteList.add(solute);

        solute = new Solute(1, 20, "T");
        soluteList.add(solute);

        //-----

        solute = new Solute(2, 1, "T");
        soluteList.add(solute);

        solute = new Solute(2, 2, "T");
        soluteList.add(solute);

        solute = new Solute(2, 3, "T");
        soluteList.add(solute);

        solute = new Solute(2, 4, "T");
        soluteList.add(solute);

        solute = new Solute(2, 5, "T");
        soluteList.add(solute);

        solute = new Solute(2, 6, "K");
        soluteList.add(solute);

        solute = new Solute(2, 7, "T");
        soluteList.add(solute);

        solute = new Solute(2, 8, "T");
        soluteList.add(solute);

        solute = new Solute(2, 9, "T");
        soluteList.add(solute);

        solute = new Solute(2, 10, "T");
        soluteList.add(solute);

        solute = new Solute(2, 11, "T");
        soluteList.add(solute);

        solute = new Solute(2, 12, "I");
        soluteList.add(solute);

        solute = new Solute(2, 13, "T");
        soluteList.add(solute);

        solute = new Solute(2, 14, "T");
        soluteList.add(solute);

        solute = new Solute(2, 15, "I");
        soluteList.add(solute);

        solute = new Solute(2, 16, "-");
        soluteList.add(solute);

        solute = new Solute(2, 17, "T");
        soluteList.add(solute);

        solute = new Solute(2, 18, "T");
        soluteList.add(solute);

        solute = new Solute(2, 19, "T");
        soluteList.add(solute);

        solute = new Solute(2, 20, "T");
        soluteList.add(solute);

        //--

        solute = new Solute(3, 1, "T");
        soluteList.add(solute);

        solute = new Solute(3, 2, "T");
        soluteList.add(solute);

        solute = new Solute(3, 3, "T");
        soluteList.add(solute);

        solute = new Solute(3, 4, "T");
        soluteList.add(solute);

        solute = new Solute(3, 5, "-");
        soluteList.add(solute);

        solute = new Solute(3, 6, "K");
        soluteList.add(solute);

        solute = new Solute(3, 7, "T");
        soluteList.add(solute);

        solute = new Solute(3, 8, "T");
        soluteList.add(solute);

        solute = new Solute(3, 9, "T");
        soluteList.add(solute);

        solute = new Solute(3, 10, "T");
        soluteList.add(solute);

        solute = new Solute(3, 11, "T");
        soluteList.add(solute);

        solute = new Solute(3, 12, "K");
        soluteList.add(solute);

        solute = new Solute(3, 13, "T");
        soluteList.add(solute);

        solute = new Solute(3, 14, "T");
        soluteList.add(solute);

        solute = new Solute(3, 15, "K");
        soluteList.add(solute);

        solute = new Solute(3, 16, "-");
        soluteList.add(solute);

        solute = new Solute(3, 17, "T");
        soluteList.add(solute);

        solute = new Solute(3, 18, "K");
        soluteList.add(solute);

        solute = new Solute(3, 19, "-");
        soluteList.add(solute);

        solute = new Solute(3, 20, "T");
        soluteList.add(solute);

        //--

        solute = new Solute(4, 1, "T");
        soluteList.add(solute);

        solute = new Solute(4, 2, "T");
        soluteList.add(solute);

        solute = new Solute(4, 3, "T");
        soluteList.add(solute);

        solute = new Solute(4, 4, "T");
        soluteList.add(solute);

        solute = new Solute(4, 5, "T");
        soluteList.add(solute);

        solute = new Solute(4, 6, "T");
        soluteList.add(solute);

        solute = new Solute(4, 7, "T");
        soluteList.add(solute);

        solute = new Solute(4, 8, "T");
        soluteList.add(solute);

        solute = new Solute(4, 9, "T");
        soluteList.add(solute);

        solute = new Solute(4, 10, "T");
        soluteList.add(solute);

        solute = new Solute(4, 11, "T");
        soluteList.add(solute);

        solute = new Solute(4, 12, "T");
        soluteList.add(solute);

        solute = new Solute(4, 13, "T");
        soluteList.add(solute);

        solute = new Solute(4, 14, "-");
        soluteList.add(solute);

        solute = new Solute(4, 15, "T");
        soluteList.add(solute);

        solute = new Solute(4, 16, "T");
        soluteList.add(solute);

        solute = new Solute(4, 17, "T");
        soluteList.add(solute);

        solute = new Solute(4, 18, "T");
        soluteList.add(solute);

        solute = new Solute(4, 19, "T");
        soluteList.add(solute);

        solute = new Solute(4, 20, "T");
        soluteList.add(solute);

        //--

        solute = new Solute(5, 1, "T");
        soluteList.add(solute);

        solute = new Solute(5, 2, "T");
        soluteList.add(solute);

        solute = new Solute(5, 3, "T");
        soluteList.add(solute);

        solute = new Solute(5, 4, "T");
        soluteList.add(solute);

        solute = new Solute(5, 5, "T");
        soluteList.add(solute);

        solute = new Solute(5, 6, "T");
        soluteList.add(solute);

        solute = new Solute(5, 7, "T");
        soluteList.add(solute);

        solute = new Solute(5, 8, "T");
        soluteList.add(solute);

        solute = new Solute(5, 9, "T");
        soluteList.add(solute);

        solute = new Solute(5, 10, "T");
        soluteList.add(solute);

        solute = new Solute(5, 11, "T");
        soluteList.add(solute);

        solute = new Solute(5, 12, "T");
        soluteList.add(solute);

        solute = new Solute(5, 13, "T");
        soluteList.add(solute);

        solute = new Solute(5, 14, "-");
        soluteList.add(solute);

        solute = new Solute(5, 15, "T");
        soluteList.add(solute);

        solute = new Solute(5, 16, "-");
        soluteList.add(solute);

        solute = new Solute(5, 17, "-");
        soluteList.add(solute);

        solute = new Solute(5, 18, "T");
        soluteList.add(solute);

        solute = new Solute(5, 19, "-");
        soluteList.add(solute);

        solute = new Solute(5, 20, "T");
        soluteList.add(solute);

        ///-moi add
        solute = new Solute(6, 1, "T");
        soluteList.add(solute);

        solute = new Solute(6, 2, "T");
        soluteList.add(solute);

        solute = new Solute(6, 3, "T");
        soluteList.add(solute);

        solute = new Solute(6, 4, "T");
        soluteList.add(solute);

        solute = new Solute(6, 5, "K");
        soluteList.add(solute);

        solute = new Solute(6, 6, "K");
        soluteList.add(solute);

        solute = new Solute(6, 7, "-");
        soluteList.add(solute);

        solute = new Solute(6, 8, "T");
        soluteList.add(solute);

        solute = new Solute(6, 9, "T");
        soluteList.add(solute);

        solute = new Solute(6, 10, "T");
        soluteList.add(solute);

        solute = new Solute(6, 11, "K");
        soluteList.add(solute);

        solute = new Solute(6, 12, "K");
        soluteList.add(solute);

        solute = new Solute(6, 13, "-");
        soluteList.add(solute);

        solute = new Solute(6, 14, "K");
        soluteList.add(solute);

        solute = new Solute(6, 15, "K");
        soluteList.add(solute);

        solute = new Solute(6, 16, "K");
        soluteList.add(solute);

        solute = new Solute(6, 17, "-");
        soluteList.add(solute);

        solute = new Solute(6, 18, "K");
        soluteList.add(solute);

        solute = new Solute(6, 19, "K");
        soluteList.add(solute);

        solute = new Solute(6, 20, "K");
        soluteList.add(solute);

        //--

        solute = new Solute(7, 1, "T");
        soluteList.add(solute);

        solute = new Solute(7, 2, "T");
        soluteList.add(solute);

        solute = new Solute(7, 3, "T");
        soluteList.add(solute);

        solute = new Solute(7, 4, "T");
        soluteList.add(solute);

        solute = new Solute(7, 5, "K");
        soluteList.add(solute);

        solute = new Solute(7, 6, "K");
        soluteList.add(solute);

        solute = new Solute(7, 7, "K");
        soluteList.add(solute);

        solute = new Solute(7, 8, "K");
        soluteList.add(solute);

        solute = new Solute(7, 9, "K");
        soluteList.add(solute);

        solute = new Solute(7, 10, "K");
        soluteList.add(solute);

        solute = new Solute(7, 11, "K");
        soluteList.add(solute);

        solute = new Solute(7, 12, "K");
        soluteList.add(solute);

        solute = new Solute(7, 13, "-");
        soluteList.add(solute);

        solute = new Solute(7, 14, "-");
        soluteList.add(solute);

        solute = new Solute(7, 15, "K");
        soluteList.add(solute);

        solute = new Solute(7, 16, "K");
        soluteList.add(solute);

        solute = new Solute(7, 17, "-");
        soluteList.add(solute);

        solute = new Solute(7, 18, "K");
        soluteList.add(solute);

        solute = new Solute(7, 19, "-");
        soluteList.add(solute);

        solute = new Solute(7, 20, "K");
        soluteList.add(solute);

        //--

        solute = new Solute(8, 1, "T");
        soluteList.add(solute);

        solute = new Solute(8, 2, "T");
        soluteList.add(solute);

        solute = new Solute(8, 3, "T");
        soluteList.add(solute);

        solute = new Solute(8, 4, "T");
        soluteList.add(solute);

        solute = new Solute(8, 5, "T");
        soluteList.add(solute);

        solute = new Solute(8, 6, "I");
        soluteList.add(solute);

        solute = new Solute(8, 7, "T");
        soluteList.add(solute);

        solute = new Solute(8, 8, "K");
        soluteList.add(solute);

        solute = new Solute(8, 9, "K");
        soluteList.add(solute);

        solute = new Solute(8, 10, "K");
        soluteList.add(solute);

        solute = new Solute(8, 11, "T");
        soluteList.add(solute);

        solute = new Solute(8, 12, "-");
        soluteList.add(solute);

        solute = new Solute(8, 13, "T");
        soluteList.add(solute);

        solute = new Solute(8, 14, "T");
        soluteList.add(solute);

        solute = new Solute(8, 15, "K");
        soluteList.add(solute);

        solute = new Solute(8, 16, "-");
        soluteList.add(solute);

        solute = new Solute(8, 17, "T");
        soluteList.add(solute);

        solute = new Solute(8, 18, "T");
        soluteList.add(solute);

        solute = new Solute(8, 19, "T");
        soluteList.add(solute);

        solute = new Solute(8, 20, "T");
        soluteList.add(solute);

        //--

        solute = new Solute(9, 1, "T");
        soluteList.add(solute);

        solute = new Solute(9, 2, "T");
        soluteList.add(solute);

        solute = new Solute(9, 3, "T");
        soluteList.add(solute);

        solute = new Solute(9, 4, "T");
        soluteList.add(solute);

        solute = new Solute(9, 5, "-");
        soluteList.add(solute);

        solute = new Solute(9, 6, "K");
        soluteList.add(solute);

        solute = new Solute(9, 7, "K");
        soluteList.add(solute);

        solute = new Solute(9, 8, "K");
        soluteList.add(solute);

        solute = new Solute(9, 9, "K");
        soluteList.add(solute);

        solute = new Solute(9, 10, "K");
        soluteList.add(solute);

        solute = new Solute(9, 11, "K");
        soluteList.add(solute);

        solute = new Solute(9, 12, "-");
        soluteList.add(solute);

        solute = new Solute(9, 13, "-");
        soluteList.add(solute);

        solute = new Solute(9, 14, "-");
        soluteList.add(solute);

        solute = new Solute(9, 15, "K");
        soluteList.add(solute);

        solute = new Solute(9, 16, "K");
        soluteList.add(solute);

        solute = new Solute(9, 17, "-");
        soluteList.add(solute);

        solute = new Solute(9, 18, "K");
        soluteList.add(solute);

        solute = new Solute(9, 19, "-");
        soluteList.add(solute);

        solute = new Solute(9, 20, "K");
        soluteList.add(solute);

        //--

        solute = new Solute(10, 1, "T");
        soluteList.add(solute);

        solute = new Solute(10, 2, "T");
        soluteList.add(solute);

        solute = new Solute(10, 3, "T");
        soluteList.add(solute);

        solute = new Solute(10, 4, "-");
        soluteList.add(solute);

        solute = new Solute(10, 5, "-");
        soluteList.add(solute);

        solute = new Solute(10, 6, "-");
        soluteList.add(solute);

        solute = new Solute(10, 7, "K");
        soluteList.add(solute);

        solute = new Solute(10, 8, "K");
        soluteList.add(solute);

        solute = new Solute(10, 9, "K");
        soluteList.add(solute);

        solute = new Solute(10, 10, "K");
        soluteList.add(solute);

        solute = new Solute(10, 11, "K");
        soluteList.add(solute);

        solute = new Solute(10, 12, "-");
        soluteList.add(solute);

        solute = new Solute(10, 13, "K");
        soluteList.add(solute);

        solute = new Solute(10, 14, "-");
        soluteList.add(solute);

        solute = new Solute(10, 15, "K");
        soluteList.add(solute);

        solute = new Solute(10, 16, "-");
        soluteList.add(solute);

        solute = new Solute(10, 17, "-");
        soluteList.add(solute);

        solute = new Solute(10, 18, "K");
        soluteList.add(solute);

        solute = new Solute(10, 19, "K");
        soluteList.add(solute);

        solute = new Solute(10, 20, "K");
        soluteList.add(solute);

        //--

        solute = new Solute(11, 1, "T");
        soluteList.add(solute);

        solute = new Solute(11, 2, "T");
        soluteList.add(solute);

        solute = new Solute(11, 3, "T");
        soluteList.add(solute);

        solute = new Solute(11, 4, "T");
        soluteList.add(solute);

        solute = new Solute(11, 5, "K");
        soluteList.add(solute);

        solute = new Solute(11, 6, "K");
        soluteList.add(solute);

        solute = new Solute(11, 7, "T");
        soluteList.add(solute);

        solute = new Solute(11, 8, "I");
        soluteList.add(solute);

        solute = new Solute(11, 9, "I");
        soluteList.add(solute);

        solute = new Solute(11, 10, "K");
        soluteList.add(solute);

        solute = new Solute(11, 11, "K");
        soluteList.add(solute);

        solute = new Solute(11, 12, "K");
        soluteList.add(solute);

        solute = new Solute(11, 13, "-");
        soluteList.add(solute);

        solute = new Solute(11, 14, "-");
        soluteList.add(solute);

        solute = new Solute(11, 15, "K");
        soluteList.add(solute);

        solute = new Solute(11, 16, "K");
        soluteList.add(solute);

        solute = new Solute(11, 17, "T");
        soluteList.add(solute);

        solute = new Solute(11, 18, "K");
        soluteList.add(solute);

        solute = new Solute(11, 19, "-");
        soluteList.add(solute);

        solute = new Solute(11, 20, "-");
        soluteList.add(solute);

        //--

        solute = new Solute(12, 1, "K");
        soluteList.add(solute);

        solute = new Solute(12, 2, "T");
        soluteList.add(solute);

        solute = new Solute(12, 3, "T");
        soluteList.add(solute);

        solute = new Solute(12, 4, "T");
        soluteList.add(solute);

        solute = new Solute(12, 5, "K");
        soluteList.add(solute);

        solute = new Solute(12, 6, "K");
        soluteList.add(solute);

        solute = new Solute(12, 7, "K");
        soluteList.add(solute);

        solute = new Solute(12, 8, "K");
        soluteList.add(solute);

        solute = new Solute(12, 9, "K");
        soluteList.add(solute);

        solute = new Solute(12, 10, "K");
        soluteList.add(solute);

        solute = new Solute(12, 11, "K");
        soluteList.add(solute);

        solute = new Solute(12, 12, "K");
        soluteList.add(solute);

        solute = new Solute(12, 13, "K");
        soluteList.add(solute);

        solute = new Solute(12, 14, "K");
        soluteList.add(solute);

        solute = new Solute(12, 15, "K");
        soluteList.add(solute);

        solute = new Solute(12, 16, "K");
        soluteList.add(solute);

        solute = new Solute(12, 17, "K");
        soluteList.add(solute);

        solute = new Solute(12, 18, "K");
        soluteList.add(solute);

        solute = new Solute(12, 19, "K");
        soluteList.add(solute);

        solute = new Solute(12, 20, "K");
        soluteList.add(solute);

        //--

        solute = new Solute(13, 1, "T");
        soluteList.add(solute);

        solute = new Solute(13, 2, "T");
        soluteList.add(solute);

        solute = new Solute(13, 3, "T");
        soluteList.add(solute);

        solute = new Solute(13, 4, "T");
        soluteList.add(solute);

        solute = new Solute(13, 5, "K");
        soluteList.add(solute);

        solute = new Solute(13, 6, "-");
        soluteList.add(solute);

        solute = new Solute(13, 7, "K");
        soluteList.add(solute);

        solute = new Solute(13, 8, "I");
        soluteList.add(solute);

        solute = new Solute(13, 9, "I");
        soluteList.add(solute);

        solute = new Solute(13, 10, "T");
        soluteList.add(solute);

        solute = new Solute(13, 11, "K");
        soluteList.add(solute);

        solute = new Solute(13, 12, "-");
        soluteList.add(solute);

        solute = new Solute(13, 13, "K");
        soluteList.add(solute);

        solute = new Solute(13, 14, "K");
        soluteList.add(solute);

        solute = new Solute(13, 15, "K");
        soluteList.add(solute);

        solute = new Solute(13, 16, "K");
        soluteList.add(solute);

        solute = new Solute(13, 17, "K");
        soluteList.add(solute);

        solute = new Solute(13, 18, "K");
        soluteList.add(solute);

        solute = new Solute(13, 19, "K");
        soluteList.add(solute);

        solute = new Solute(13, 20, "K");
        soluteList.add(solute);

        //Check and add data
        if (soluteList.size() == mChemistryHelper.getAllSolute().size()) {
            Log.i("ANTN", "Table Solute available");
        } else {
            //Add to database
            Log.i("ANTN", "Table Solute updated");
            mChemistryHelper.emptySolute();
            for (Solute item : soluteList) {
                mChemistryHelper.addSolute(item);
            }
        }
    }
}
