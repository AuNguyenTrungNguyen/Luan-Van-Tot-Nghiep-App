package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class ChooseLevelActivity extends AppCompatActivity {

    private ChemistryHelper mHelper;

    private ListView mListView;
    private List<String> mLevelList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        PreferencesManager.getInstance().init(this);

        init();

        //Get TypeOfGame from Fragment
        int type = getType();

        //Save Preference
        saveType(type);

        //Get all levels with type exit
        setupLevel(type);

        Intent intent = null;
        switch (type){
            case 1:
                intent = new Intent(ChooseLevelActivity.this, QuizActivity.class);
                break;

            case 2:
                intent = new Intent(ChooseLevelActivity.this, FillInTheBlankActivity.class);
                break;

            case 3:
                intent = new Intent(ChooseLevelActivity.this, MatchActivity.class);
                break;

            case 4:
                intent = new Intent(ChooseLevelActivity.this, SortActivity.class);
                break;
        }

        final Intent finalIntent = intent;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (finalIntent != null){
                    finalIntent.putExtra("LEVEL", Integer.parseInt(mLevelList.get(i)));
                    startActivity(finalIntent);
                    finish();
                }
            }
        });
    }

    private void init() {

        mHelper = ChemistrySingle.getInstance(this);

        mListView = findViewById(R.id.lv_level);
        mLevelList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLevelList);

        mListView.setAdapter(mAdapter);
    }

    private void setupLevel(int type) {
        List<Question> list = mHelper.getAllQuestion();

        List<String> temp = new ArrayList<>();
        for (Question question : list) {
            if (question.getIdType() == type)
                temp.add(String.valueOf(question.getIdLevel()));
        }

        mLevelList.addAll(new HashSet<>(temp));
        Collections.sort(mLevelList);
        mAdapter.notifyDataSetChanged();
    }

    private int getType() {
        int type = 0;
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("TYPE", 0);
        }
        return type;
    }

    private void saveType(int type){
        PreferencesManager.getInstance().saveIntData(Constraint.PRE_KEY_TYPE, type);
    }
}
