package luanvan.luanvantotnghiep.Activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import luanvan.luanvantotnghiep.Adapter.MatchSentencesAdapter;
import luanvan.luanvantotnghiep.Adapter.SortAdapter;
import luanvan.luanvantotnghiep.ControlRecycle.MyItemTouch;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class SortActivity extends AppCompatActivity {
    private TextView mTvTime;
    private Button mBtnComplete;
    private TextView mTvQuestion;

    private RecyclerView mRvSort;
    private RecyclerView mRvAnswer;

    private CountDownTimer mCountDownTimer;

    private SortAdapter mSortAdapter;

    private List<Element> mDataList;
    private List<Element> mListAnswer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        init();

        setupGame();

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBtnComplete.setEnabled(false);
                int score = 0;
                mRvAnswer.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = mRvSort.getLayoutParams();
                params.width = 0;
                mRvSort.setLayoutParams(params);

                //sort
                mListAnswer.addAll(mDataList);
                Collections.sort(mListAnswer, new Comparator<Element>() {
                    public int compare(Element o1, Element o2) {
                        Integer idE1 = o1.getIdElement();
                        Integer idE2 = o2.getIdElement();
                        return idE1.compareTo(idE2);
                    }
                });

                //get score
                for (int i = 0; i < mDataList.size(); i++) {
                    mSortAdapter.setUI(i, 1);
                    if (mDataList.get(i).getIdElement() == mListAnswer.get(i).getIdElement()) {
                        score++;
                        mSortAdapter.setUI(i, 0);
                    }
                }
                mSortAdapter.setNoMove();
                mSortAdapter.notifyDataSetChanged();

                //setData mRvAnswer
                SortAdapter adapter = new SortAdapter(SortActivity.this, mListAnswer);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SortActivity.this);
                mRvAnswer.setLayoutManager(layoutManager);
                mRvAnswer.setHasFixedSize(true);
                mRvAnswer.setAdapter(adapter);
            }
        });
    }

    private void init() {
        mTvTime = findViewById(R.id.tv_time_sort);
        mTvQuestion = findViewById(R.id.tv_question_sort);
        mBtnComplete = findViewById(R.id.btn_complete_sort);

        mRvSort = findViewById(R.id.rv_sort);
        mRvAnswer = findViewById(R.id.rv_answer);
    }

    private void setupGame() {
        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(this);

        List<Element> mChemistryList = new ArrayList<>();
        mChemistryList.addAll(chemistryHelper.getAllElements());

        List<Element> mFilter = new ArrayList<>(mChemistryList.subList(0, 35));

        Collections.shuffle(mFilter);
        Collections.shuffle(mFilter);
        Collections.shuffle(mFilter);

        mDataList = new ArrayList<>(mFilter.subList(0, 5));

        int type = randomQuestion();
        if (type == 0) {
            mTvQuestion.setText("xếp theo stt");
        } else {
            mTvQuestion.setText("xếp theo khối lượng");
        }
        mSortAdapter = new SortAdapter(this, mDataList);

        ItemTouchHelper.Callback callback =
                new MyItemTouch(mSortAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvSort);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvSort.setLayoutManager(layoutManager);
        mRvSort.setHasFixedSize(true);
        mRvSort.setAdapter(mSortAdapter);
    }

    private int randomQuestion() {
        Random random = new Random();
        int n = random.nextInt(50) + 1;

        return n % 2;
    }
}
