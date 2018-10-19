package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import luanvan.luanvantotnghiep.CustomTree.Tree;
import luanvan.luanvantotnghiep.CustomTree.TreeAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class ChooseLevelActivity extends AppCompatActivity {

    private ChemistryHelper mHelper;

    private RecyclerView mRvTree;
    private List<Tree> mList;
    private TreeAdapter mAdapter;

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
        setupLevel(type, 1);

//        final Intent finalIntent = intent;
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (finalIntent != null){
//                    finalIntent.putExtra("LEVEL", Integer.parseInt(mLevelList.get(i)));
//                    startActivity(finalIntent);
//                    finish();
//                }
//            }
//        });
    }

    private void init() {

        mHelper = ChemistrySingle.getInstance(this);

        mRvTree = findViewById(R.id.rv_tree);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        mRvTree.setLayoutManager(manager);
        mRvTree.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new TreeAdapter(this, mList);
        mRvTree.setAdapter(mAdapter);
    }

    private void setupLevel(int type, int extent) {
        mList.clear();
        List<Question> list = mHelper.getAllQuestion();

        List<Integer> temp = new ArrayList<>();
        for (Question question : list) {
            if (question.getIdType() == type && question.getExtent() == extent)
                temp.add(question.getIdLevel());
        }

        List<Integer> listLevel = new ArrayList<>(new HashSet<>(temp));

        for (int i = 0; i < listLevel.size(); i++) {
            boolean isLeft = listLevel.get(i) % 2 == 0;
            mList.add(new Tree(isLeft, "Leaf: " + listLevel.get(i), listLevel.get(i)));
        }

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

    private void saveType(int type) {
        PreferencesManager.getInstance().saveIntData(Constraint.PRE_KEY_TYPE, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_extent,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_extent_easy:
                setupLevel(getType(),Constraint.EXTENT_EASY);
                break;

            case R.id.mn_extent_normal:
                setupLevel(getType(),Constraint.EXTENT_NORMAL);
                break;

            case R.id.mn_extent_difficult:
                setupLevel(getType(),Constraint.EXTENT_DIFFICULT);
                break;
        }
        return true;
    }
}
