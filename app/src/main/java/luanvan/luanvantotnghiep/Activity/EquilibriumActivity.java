package luanvan.luanvantotnghiep.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.EquilibriumAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Equilibrium;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.Helper;

public class EquilibriumActivity extends AppCompatActivity implements EquilibriumAdapter.OnClickButtonEquilibrium {

    private static final String TAG = "ANTN";
    private List<ChemicalReaction> mChemicalReactionList;
    private EquilibriumAdapter mAdapter;
    private RecyclerView mRvEquilibrium;
    private TextView mTvEquilibrium;

    private List<Equilibrium> equilibriumList;
    private int positionSymbol = 0; //Position symbol "-> or <->"
    private String userAnswer; //Save user answer

    private int twoWay = 0;
    private String reactant;
    private String product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equilibrium);

        setupToolbar();

        mTvEquilibrium = findViewById(R.id.tv_equilibrium);
        mRvEquilibrium = findViewById(R.id.rv_equilibrium);

        ChemistryHelper helper = ChemistrySingle.getInstance(this);
        mChemicalReactionList = helper.getAllChemicalReaction();

        equilibriumList = new ArrayList<>();
        mAdapter = new EquilibriumAdapter(this, equilibriumList);
        mRvEquilibrium.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvEquilibrium.setLayoutManager(manager);
        mRvEquilibrium.setHasFixedSize(true);

        /*Set listener recycle*/
        mAdapter.setListener(this);

        ChemicalReaction chemicalReaction = mChemicalReactionList.get(0);

        showQuestion(chemicalReaction);
    }

    @Override
    public void updateUI() {

        String user = handelUserReaction(equilibriumList);
        String show = handelTextToShow();

        userAnswer = user;
        mTvEquilibrium.setText(Html.fromHtml(show));
    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String handelTextToShow() {
        StringBuilder show = new StringBuilder();
        for (int i = 0; i < equilibriumList.size(); i++) {
            Equilibrium equilibrium = equilibriumList.get(i);

            if (equilibrium.getNumber() != 1) {
                show.append("<font color='red'>").append(equilibrium.getNumber()).append("</font>");
            }
            show.append(Helper.getInstant().handelText(equilibrium.getName().trim()));

            if (i == positionSymbol) {
                if (twoWay == 1) {
                    show.append(" " + Constraint.SYMBOL_TWO_WAY + " ");
                } else {
                    show.append(" " + Constraint.SYMBOL + " ");
                }
            } else if (i == equilibriumList.size() - 1) {
                show.append("");
            } else {
                show.append(" + ");
            }
        }
        return show.toString();
    }

    private String handelUserReaction(List<Equilibrium> equilibriumList) {
        StringBuilder user = new StringBuilder();
        for (int i = 0; i < equilibriumList.size(); i++) {
            Equilibrium equilibrium = equilibriumList.get(i);

            user.append(equilibrium.getNumber());
            user.append(equilibrium.getName().trim());

            if (i == positionSymbol) {
                if (twoWay == 1) {
                    user.append(" " + Constraint.SYMBOL_TWO_WAY + " ");
                } else {
                    user.append(" " + Constraint.SYMBOL + " ");
                }
            } else if (i == equilibriumList.size() - 1) {
                user.append("");
            } else {
                user.append(" + ");
            }
        }
        return user.toString();
    }

    private void submit(String userAnswer, final String correctAnswer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (checkSimplifiedFraction(userAnswer, correctAnswer)) {
            builder.setMessage("Chính xác!");
            builder.setNegativeButton("Làm bài mới", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    equilibriumList.clear();
                    mRvEquilibrium.setVisibility(View.VISIBLE);

                    //random list
                    showQuestion(mChemicalReactionList.get(1));
                }
            });
        } else {
            builder.setMessage("Chưa chính xác!");
            builder.setNegativeButton("Xem đáp án", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    //show answer
                }
            });

            builder.setNeutralButton("Làm tiếp", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private boolean checkSimplifiedFraction(String userAnswer, String correctAnswer) {

        /*Get numberLowest reaction of user*/
        int numberLowest = equilibriumList.get(0).getNumber();
        for (int i = 1; i < equilibriumList.size(); i++) {
            if (numberLowest > equilibriumList.get(i).getNumber()) {
                numberLowest = equilibriumList.get(i).getNumber();
            }
        }

        boolean isSimplified = true;

        List<Equilibrium> equilibriumListUser = new ArrayList<>();
        for (Equilibrium equilibrium : equilibriumList) {
            int number = equilibrium.getNumber() % numberLowest;

            if (number != 0) {
                isSimplified = false;
                break;
            }

            Equilibrium equilibriumUser = new Equilibrium();
            equilibriumUser.setName(equilibrium.getName());
            equilibriumUser.setNumber(equilibrium.getNumber() / numberLowest);
            equilibriumListUser.add(equilibriumUser);
        }

        if (isSimplified) {
            String userSimplified = handelUserReaction(equilibriumListUser);
            return userSimplified.trim().equals(correctAnswer.trim());
        } else {
            return userAnswer.trim().equals(correctAnswer.trim());
        }
    }

    private void showQuestion(ChemicalReaction chemicalReaction) {
        twoWay = chemicalReaction.getTwoWay();
        reactant = chemicalReaction.getReactants();
        product = chemicalReaction.getProducts();

        /*Set data recycle*/
        String left[] = reactant.split("\\+");
        String right[] = product.split("\\+");

        for (String name : left) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setName(name.split(":")[1]);
            equilibriumList.add(equilibrium);
        }

        for (String name : right) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setName(name.split(":")[1]);
            equilibriumList.add(equilibrium);
        }
        mAdapter.notifyDataSetChanged();

        positionSymbol = left.length - 1;

        StringBuilder temp = new StringBuilder();
        temp.append(reactant);
        if (twoWay == 1) {
            temp.append(" " + Constraint.SYMBOL_TWO_WAY + " ");
        } else {
            temp.append(" " + Constraint.SYMBOL + " ");
        }
        temp.append(product);

        final String correctAnswer = temp.toString().replace(":", "");

        /*Handel text and show */
        String show = handelTextToShow();
        mTvEquilibrium.setText(Html.fromHtml(show));

        userAnswer = handelUserReaction(equilibriumList);

        /*Submit button*/
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(userAnswer, correctAnswer);
            }
        });
    }
}
