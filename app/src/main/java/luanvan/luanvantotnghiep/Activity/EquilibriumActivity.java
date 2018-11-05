package luanvan.luanvantotnghiep.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.EquilibriumAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Equilibrium;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Helper;

public class EquilibriumActivity extends AppCompatActivity implements EquilibriumAdapter.OnClickButtonEquilibrium {

    private static final String TAG = "ANTN";
    List<Equilibrium> equilibriumList;
    TextView tvEquilibrium;
    int positionSymbol = 0; //Position symbol "->"
    String userAnswer; //Save user answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equilibrium);

        tvEquilibrium = findViewById(R.id.tv_equilibrium);
        RecyclerView rvEquilibrium = findViewById(R.id.rv_equilibrium);

        ChemistryHelper helper = ChemistrySingle.getInstance(this);
        ChemicalReaction chemicalReaction = helper.getAllChemicalReaction().get(11);
        String reactant = chemicalReaction.getReactants();
        String product = chemicalReaction.getProducts();

        /*Set data recycle*/
        equilibriumList = new ArrayList<>();
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

        positionSymbol = left.length - 1;

        EquilibriumAdapter equilibriumAdapter = new EquilibriumAdapter(this, equilibriumList);
        rvEquilibrium.setAdapter(equilibriumAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvEquilibrium.setLayoutManager(manager);
        rvEquilibrium.setHasFixedSize(true);

        /*Set listener recycle*/
        equilibriumAdapter.setListener(this);

        /*Handel text and show */
        String show = handelTextToShow();
        tvEquilibrium.setText(Html.fromHtml(show));

        String question = handelUserReaction(equilibriumList);
        userAnswer = question;
        String temp = reactant + " -> " + product;
        final String correctAnswer = temp.replace(":", "");

        /*Submit button*/
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(userAnswer, correctAnswer);
            }
        });
    }

    @Override
    public void updateUI() {

        String user = handelUserReaction(equilibriumList);
        String show = handelTextToShow();

        userAnswer = user;
        tvEquilibrium.setText(Html.fromHtml(show));
//        StringBuilder user = new StringBuilder();
//        StringBuilder show = new StringBuilder();
//        for (int i = 0; i < equilibriumList.size(); i++) {
//            Equilibrium equilibrium = equilibriumList.get(i);
//
//            if (equilibrium.getNumber() != 1) {
//                user.append(equilibrium.getNumber());
//                show.append("<font color='red'>").append(equilibrium.getNumber()).append("</font>");
//            }
//            user.append(equilibrium.getName().trim());
//            show.append(Helper.getInstant().handelText(equilibrium.getName().trim()));
//
//            if (i == positionSymbol) {
//                user.append(" -> ");
//                show.append(" -> ");
//            } else if (i == equilibriumList.size() - 1) {
//                user.append("");
//                show.append("");
//            } else {
//                user.append(" + ");
//                show.append(" + ");
//            }
//        }
//        userAnswer = user.toString();
//        tvEquilibrium.setText(Html.fromHtml(show.toString()));
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
                show.append(" -> ");
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

            if (equilibrium.getNumber() != 1) {
                user.append(equilibrium.getNumber());
            }
            user.append(equilibrium.getName().trim());

            if (i == positionSymbol) {
                user.append(" -> ");
            } else if (i == equilibriumList.size() - 1) {
                user.append("");
            } else {
                user.append(" + ");
            }
        }
        return user.toString();
    }

    public void submit(String userAnswer, String correctAnswer) {
        if (checkSimplifiedFraction(userAnswer, correctAnswer)) {
            Toast.makeText(this, "Correct ^.^", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fail T_T", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkSimplifiedFraction(String userAnswer, String correctAnswer) {

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
}
