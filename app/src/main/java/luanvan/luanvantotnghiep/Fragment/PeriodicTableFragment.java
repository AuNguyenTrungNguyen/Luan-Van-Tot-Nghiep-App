package luanvan.luanvantotnghiep.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.SwipeAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class PeriodicTableFragment extends Fragment {

    private List<Element> mElementList;
    private List<Group> mGroupList;
    private Context mContext;

    private TableLayout mTlPeriodic;
    private TextView tvId;
    private TextView tvSymbol;
    private TextView tvName;

    private ChemistryHelper mChemistryHelper;

    public PeriodicTableFragment() {
    }

    public static PeriodicTableFragment newInstance() {
        return new PeriodicTableFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_periodic_table, container, false);

        init(view);

        showPeriodicTable();

        return view;
    }

    private void init(View v) {
        mGroupList = new ArrayList<>();
        mElementList = new ArrayList<>();
        mTlPeriodic = v.findViewById(R.id.tl_periodic);

        mChemistryHelper = ChemistrySingle.getInstance(getContext());

        mGroupList = mChemistryHelper.getAllGroups();
        mElementList = mChemistryHelper.getAllElements();
    }

    private void createPeriodicTable() {
        TableRow header = new TableRow(mContext);

        LayoutInflater l = getLayoutInflater();
        View view;

        for (int i = 0; i < 19; i++) {

            view = inflateView(l);
            if (i == 1 || i == 18) {
                tvSymbol.setText(mGroupList.get(i - 1).getNameGroup());
                tvSymbol.setTextSize(20);
                tvSymbol.setGravity(Gravity.BOTTOM | Gravity.CENTER);
            } else {
                tvSymbol.setText("");
            }
            tvId.setText("");
            tvName.setText("");
            view.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
            if (i == 0 || i == 18) {
                view.setBackgroundResource(R.drawable.backgroud_item_empty_first_lantan_3a8a);
            }
            if (i == 1) {
                view.setBackgroundResource(R.drawable.backgroud_item_group);
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            header.addView(view, 120, 190);
        }
        mTlPeriodic.addView(header);

        for (int i = 1; i < 8; i++) {
            TableRow row = new TableRow(mContext);
            view = inflateView(l);
            tvId.setText("");
            tvName.setText("");
            tvSymbol.setText(String.format("%s", i));
            view.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
            view.setBackgroundResource(R.drawable.backgroud_item_cycle_first_actini);
            row.addView(view, 120, 190);

            for (int j = 1; j < 19; j++) {
                view = inflateView(l);
                final int position = checkPeriodicEmpty(i, j);

                if (position != -1) {
                    Element element = mElementList.get(position);
                    tvId.setText(String.valueOf(element.getIdElement()));
//                    tvSymbol.setText(element.getSymbolChemistry());
//                    tvName.setText(element.getNameChemistry());
                    Chemistry chemistry = mChemistryHelper.getChemistryById(element.getIdElement());
                    tvSymbol.setText(chemistry.getSymbolChemistry());
                    tvName.setText(chemistry.getNameChemistry());
                    LinearLayout layout = view.findViewById(R.id.ln_item);
                    layout.setBackgroundResource(R.drawable.background_item_chat_hoa_hoc);
                    if (position == 4) {
                        layout.setBackgroundResource(R.drawable.backgroud_item_cycle_first_actini);
                    }

                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialogInfo(position);
                        }
                    });

                } else {
                    tvId.setText("");
                    tvName.setText("");
                    view.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
                    if (i == 1 && (j < 3 || j > 12)
                            || i == 3 && (j > 2 && j < 13)) {
                        if (i == 1 && j == 13) {
                            view.setBackgroundResource(R.drawable.backgroud_item_empty_first_lantan_3a8a);
                        } else {
                            view.setBackgroundResource(R.drawable.backgroud_item_group);
                        }
                        tvSymbol.setText(mGroupList.get(j - 1).getNameGroup());
                        tvSymbol.setTextSize(20);
                        tvSymbol.setGravity(Gravity.BOTTOM | Gravity.CENTER);
                    } else {
                        tvSymbol.setText("");
                    }
                }
                row.addView(view, 120, 190);
            }
            mTlPeriodic.addView(row);
        }
    }

    private int checkPeriodicEmpty(int period, int positionShow) {

        for (int i = 0; i < mElementList.size(); i++) {
            Element element = mElementList.get(i);
            if (element.getPeriod() == period && element.getIdGroup() == positionShow) {
                return i;
            }
        }
        return -1;
    }

    private void createRelative() {

        TableRow row = new TableRow(mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        LayoutInflater li = getLayoutInflater();
        View v;

        //Create 3 space cell
        for (int i = 0; i < 3; i++) {
            v = inflateView(li);
            tvId.setText("");
            tvName.setText("");
            tvSymbol.setText("");
            v.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
            row.addView(v, 120, 190);
        }

        //Create cell "LanTan"
        v = inflateView(li);
        tvId.setText("");
        tvName.setText("");
        tvSymbol.setText("Lantan");
        tvSymbol.setTextSize(16);
        v.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
        v.setBackgroundResource(R.drawable.backgroud_item_empty_first_lantan_3a8a);
        row.addView(v, 120, 190);

        //Data lantan
        for (int i = 58; i < 72; i++) {
            v = inflateView(li);
            Element element = mElementList.get(i);
            tvId.setText(String.valueOf(element.getIdElement()));
//            tvSymbol.setText(element.getSymbolChemistry());
//            tvName.setText(element.getNameChemistry());
            Chemistry chemistry = mChemistryHelper.getChemistryById(element.getIdElement());
            tvSymbol.setText(chemistry.getSymbolChemistry());
            tvName.setText(chemistry.getNameChemistry());
            LinearLayout layout = v.findViewById(R.id.ln_item);
            layout.setBackgroundResource(R.drawable.backgroud_item_group);

            final int position = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogInfo(position);
                }
            });

            row.addView(v, 120, 190);
        }

        mTlPeriodic.addView(row);

        row = new TableRow(mContext);

        //Create 3 space cell
        for (int i = 0; i < 3; i++) {
            v = inflateView(li);
            tvId.setText("");
            tvName.setText("");
            tvSymbol.setText("");
            v.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
            row.addView(v, 120, 190);
        }

        //Create cell "Actini"
        v = inflateView(li);
        tvId.setText("");
        tvName.setText("");
        tvSymbol.setText("Actini");
        tvSymbol.setTextSize(16);
        v.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
        v.setBackgroundResource(R.drawable.backgroud_item_cycle_first_actini);
        row.addView(v, 120, 190);

        //Data Actini
        for (int i = 90; i < 104; i++) {
            v = inflateView(li);
            Element element = mElementList.get(i);
            tvId.setText(String.valueOf(element.getIdElement()));
//            tvSymbol.setText(element.getSymbolChemistry());
//            tvName.setText(element.getNameChemistry());
            Chemistry chemistry = mChemistryHelper.getChemistryById(element.getIdElement());
            tvSymbol.setText(chemistry.getSymbolChemistry());
            tvName.setText(chemistry.getNameChemistry());
            LinearLayout layout = v.findViewById(R.id.ln_item);
            layout.setBackgroundResource(R.drawable.background_item_chat_hoa_hoc);

            final int position = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogInfo(position);
                }
            });

            row.addView(v, 120, 190);
        }

        mTlPeriodic.addView(row);

    }

    private View inflateView(LayoutInflater li) {
        View v = li.inflate(R.layout.item_element, null);

        tvId = (TextView) v.findViewById(R.id.tv_id_element);
        tvSymbol = (TextView) v.findViewById(R.id.tv_symbol_element);
        tvName = (TextView) v.findViewById(R.id.tv_name_element);

        return v;
    }

    private void showPeriodicTable() {

        createPeriodicTable();

        createSpaceLine();

        createRelative();

        createSpaceLine();
    }

    private void createSpaceLine() {
        TableRow row = new TableRow(mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        LayoutInflater vi = getLayoutInflater();
        View v = vi.inflate(R.layout.item_element, null);

        TextView tvId = (TextView) v.findViewById(R.id.tv_id_element);
        TextView tvKyHieu = (TextView) v.findViewById(R.id.tv_symbol_element);
        TextView tvTen = (TextView) v.findViewById(R.id.tv_name_element);

        tvId.setText("");
        tvKyHieu.setText("");
        tvTen.setText("");
        v.findViewById(R.id.tv_show).setVisibility(View.INVISIBLE);
        row.addView(v, 100, 190);
        mTlPeriodic.addView(row);
    }

    private void showDialogInfo(int position) {

        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.context_dialog_periodic);

        ViewPager viewPager;
        viewPager = dialog.findViewById(R.id.view_pager);

        SwipeAdapter adapter = new SwipeAdapter(mContext, mElementList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        dialog.show();
    }

}
