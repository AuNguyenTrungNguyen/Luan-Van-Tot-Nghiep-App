package luanvan.luanvantotnghiep.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private EquilibriumAdapter mAdapter;
    private RecyclerView mRvEquilibrium;
    private TextView mTvEquilibrium;
    private Button mBtnSubmit;

    private ChemistryHelper mHelper;
    private List<ChemicalReaction> mChemicalReactionList;

    private List<Equilibrium> equilibriumList;
    private int positionSymbol = 0; //Position symbol "-> or <->"
    private ChemicalReaction mCurrentReaction;
    private String userAnswer;

    private int positionReaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equilibrium);

        setupToolbar();

        mTvEquilibrium = findViewById(R.id.tv_equilibrium);
        mRvEquilibrium = findViewById(R.id.rv_equilibrium);
        mBtnSubmit = findViewById(R.id.btn_submit);

        /*Setup list reaction*/
        mHelper = ChemistrySingle.getInstance(this);
        mChemicalReactionList = mHelper.getAllChemicalReaction();

        /*Setup adapter*/
        equilibriumList = new ArrayList<>();
        mAdapter = new EquilibriumAdapter(this, equilibriumList);
        mAdapter.setListener(this);
        mRvEquilibrium.setAdapter(mAdapter);

        /*Setup recycle*/
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvEquilibrium.setLayoutManager(manager);
        mRvEquilibrium.setHasFixedSize(true);

        /*Setup data*/
        positionReaction = new Random().nextInt(mChemicalReactionList.size());
        ChemicalReaction chemicalReaction = mChemicalReactionList.get(positionReaction);
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
        int twoWay = mCurrentReaction.getTwoWay();
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
        int twoWay = mCurrentReaction.getTwoWay();
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

    private void submit(String userAnswer, String correctAnswer) {
        Log.i(TAG, "userAnswer: " + userAnswer);
        Log.i(TAG, "correctAnswer: " + correctAnswer);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (checkSimplifiedFraction(userAnswer, correctAnswer)) {
            builder.setMessage(Html.fromHtml("<font color='red'>&#x2713 Chính xác!</font>"));
            builder.setNegativeButton("Làm bài mới", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    equilibriumList.clear();
                    mRvEquilibrium.setVisibility(View.VISIBLE);
                    mBtnSubmit.setVisibility(View.VISIBLE);

                    //random list
                    showQuestion(mChemicalReactionList.get(positionReaction));
                }
            });
        } else {
            builder.setMessage(Html.fromHtml("<font color='red'>&#x2717 Chưa chính xác!</font>"));
            builder.setNegativeButton("Xem đáp án", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    mRvEquilibrium.setVisibility(View.INVISIBLE);
                    mBtnSubmit.setVisibility(View.INVISIBLE);

                    //show correct answer
                    showCorrectAnswer();
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
        mChemicalReactionList.remove(positionReaction);
        positionReaction = new Random().nextInt(mChemicalReactionList.size());
        Collections.shuffle(mChemicalReactionList);

        Log.i(TAG, "mChemicalReactionList.size(): " + mChemicalReactionList.size());

        if (mChemicalReactionList.size() < 10){
            mChemicalReactionList.clear();
            mChemicalReactionList = mHelper.getAllChemicalReaction();
        }
        Log.i(TAG, "mChemicalReactionList.size() checked: " + mChemicalReactionList.size());


        mCurrentReaction = chemicalReaction;
        int twoWay = chemicalReaction.getTwoWay();
        String reactant = chemicalReaction.getReactants();
        String product = chemicalReaction.getProducts();

        /*Set data recycle*/
        String left[] = reactant.split("\\+");
        String right[] = product.split("\\+");

        for (String name : left) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setName(name.split(":")[1].trim());
            equilibriumList.add(equilibrium);
        }

        for (String name : right) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setName(name.split(":")[1].trim());
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
        userAnswer = handelUserReaction(equilibriumList);

        /*Handel text and show */
        String show = handelTextToShow();
        mTvEquilibrium.setText(Html.fromHtml(show));

        /*Submit button*/
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(userAnswer, correctAnswer);
            }
        });
    }

    private void showCorrectAnswer() {
        int twoWay = mCurrentReaction.getTwoWay();
        String reactant = mCurrentReaction.getReactants();
        String product = mCurrentReaction.getProducts();

        String left[] = reactant.split("\\+");
        String right[] = product.split("\\+");
        List<Equilibrium> equilibriumList = new ArrayList<>();

        for (String name : left) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setNumber(Integer.parseInt(name.split(":")[0].trim()));
            equilibrium.setName(name.split(":")[1]);
            equilibriumList.add(equilibrium);
        }

        for (String name : right) {
            Equilibrium equilibrium = new Equilibrium();
            equilibrium.setNumber(Integer.parseInt(name.split(":")[0].trim()));
            equilibrium.setName(name.split(":")[1]);
            equilibriumList.add(equilibrium);
        }

        StringBuilder show = new StringBuilder();
        for (int i = 0; i < equilibriumList.size(); i++) {
            Equilibrium equilibrium = equilibriumList.get(i);

            show.append("<font color='red'>").append(equilibrium.getNumber()).append("</font>");
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
        mTvEquilibrium.setText(Html.fromHtml(show.toString()));
    }
}
