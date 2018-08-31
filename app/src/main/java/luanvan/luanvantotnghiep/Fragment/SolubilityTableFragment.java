package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private TextView mTvSoluble;
    private TextView mTvInfoSoluble;
    private TextView mTvInSoluble;
    private TextView mTvInfoInSoluble;
    private TextView mTvLessSoluble;
    private TextView mTvInfoLessSoluble;
    private TextView mTvUnExist;

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

        handleTextView();

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

        mTvSoluble = v.findViewById(R.id.tv_soluble);
        mTvInfoSoluble = v.findViewById(R.id.tv_info_soluble);
        mTvInSoluble = v.findViewById(R.id.tv_insoluble);
        mTvInfoInSoluble = v.findViewById(R.id.tv_info_insoluble);
        mTvLessSoluble = v.findViewById(R.id.tv_less_soluble);
        mTvInfoLessSoluble = v.findViewById(R.id.tv_info_less_soluble);
        mTvUnExist = v.findViewById(R.id.tv_un_exist);
    }

    private void handleTextView() {
        mTvSoluble.setText(Html.fromHtml("<font color='blue'>T</font> : tan"));
        mTvInfoSoluble.setText(Html.fromHtml("trên 1g trong 100g H<small><sub>2</sub></small>O"));
        mTvInSoluble.setText(Html.fromHtml("<font color='red'>K</font> : không tan"));
        mTvInfoInSoluble.setText(Html.fromHtml("từ 0,001g đến 1g trong 100g H<small><sub>2</sub></small>O"));
        mTvLessSoluble.setText(Html.fromHtml("<font color='gray'>I</font> : ít tan"));
        mTvInfoLessSoluble.setText(Html.fromHtml("dưới 1g trong 100g H<small><sub>2</sub></small>O"));
        mTvUnExist.setText(Html.fromHtml("<font color='black'>—</font> : bị phân hủy hoặc không tồn tại"));
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
                tvShow.setTextColor(Color.GRAY);
                break;
            case "—":
                tvShow.setTextColor(Color.BLACK);
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
}
