package luanvan.luanvantotnghiep.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.MatchQuestionAdapter;
import luanvan.luanvantotnghiep.Communicate.MatchGame;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;

public class MatchActivity extends AppCompatActivity implements MatchGame {

    private RecyclerView mRvQuestion;
    private RecyclerView mRvAnswer;
    private TextView mTvTime;

    private List<Question> mQuestionList;
    private List<Answer> mListAnswer;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private List<Integer> mListUserAnswer = new ArrayList<>(); //save id answer

    private List<Question> mDataQuestionList;
    private List<Answer> mDataAnswerList;
    private List<AnswerByQuestion> mDataABQList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        setupToolbar();

        init();

        setUpData();

        setUpAdapter();

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(Constraint.TAG, "onClick: " + showScore());
            }
        });
    }

    private int showScore() {
        int score = 0;

        for (int i = 0; i < mQuestionList.size(); i++) {
            for (AnswerByQuestion answerByQuestion : mDataABQList) {
                if (answerByQuestion.getIdQuestion() == mQuestionList.get(i).getIdQuestion()
                        && answerByQuestion.getIdAnswer() == mListUserAnswer.get(i)) {
                    score++;
                    break;
                }
            }
        }

        return score;
    }

    private void init() {
        mRvQuestion = findViewById(R.id.rv_question);
        mRvAnswer = findViewById(R.id.rv_answer);
        mTvTime = findViewById(R.id.tv_time_match);

        RecyclerView.LayoutManager managerQuestion = new LinearLayoutManager(this);
        RecyclerView.LayoutManager managerAnswer = new LinearLayoutManager(this);

        mRvQuestion.setLayoutManager(managerQuestion);
        mRvAnswer.setLayoutManager(managerAnswer);

        mRvQuestion.setHasFixedSize(true);
        mRvAnswer.setHasFixedSize(true);
    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkUserOut();
            }
        });
    }

    private void setUpData() {
        mQuestionList = new ArrayList<>();
        mListAnswer = new ArrayList<>();
        mAnswerByQuestionList = new ArrayList<>();

        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(this);
        mQuestionList.addAll(chemistryHelper.getAllQuestion());
        mAnswerByQuestionList.addAll(chemistryHelper.getAllAnswerByQuestion());
        mListAnswer.addAll(chemistryHelper.getAllAnswer());

        mDataQuestionList = new ArrayList<>();
        mDataAnswerList = new ArrayList<>();
        mDataABQList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            mQuestionList.add(new Question((i + 1), String.format("%s + %s = ?", (i + 1), (i + 1))));
//        }
//
//        for (int i = 0; i < 5; i++) {
//            mListAnswer.add(new Answer((i + 1), String.format("%s chứ mấy.", (i + 1) * 2)));
//        }
//
//        for (int i = 0; i < 5; i++) {
//            mAnswerByQuestionList.add(new AnswerByQuestion(i + 1, i + 1, 1));
//        }
        for (Question question : mQuestionList) {
            if (question.getIdType() == 3) {
                mDataQuestionList.add(question);
            }
        }

        for (Question question : mDataQuestionList) {
            for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
                if (answerByQuestion.getIdQuestion() == question.getIdQuestion()) {
                    mDataABQList.add(answerByQuestion);
                }
            }
        }

        for (AnswerByQuestion answerByQuestion : mDataABQList) {
            for (Answer answer : mListAnswer) {
                if (answer.getIdAnswer() == answerByQuestion.getIdAnswer()) {
                    mDataAnswerList.add(answer);
                }
            }
        }


        Collections.shuffle(mDataQuestionList);
        Collections.shuffle(mDataAnswerList);
    }

    private void setUpAdapter() {

        for (int i = 0; i < mListAnswer.size(); i++) {
            mListUserAnswer.add(-1);
        }

        MatchQuestionAdapter adapterQuestion = new MatchQuestionAdapter(this, mDataQuestionList, mDataAnswerList);
        mRvQuestion.setAdapter(adapterQuestion);
        adapterQuestion.setMatchGame(this);

        AnswerMatchAdapter adapterAnswer = new AnswerMatchAdapter(this, mDataAnswerList);
        mRvAnswer.setAdapter(adapterAnswer);
    }

    @Override
    public void userClick(int position, int idAnswer) {
        mListUserAnswer.set(position, idAnswer);
    }

    private static class AnswerMatchAdapter extends RecyclerView.Adapter<AnswerMatchAdapter.AnswerHolder> {

        private Context mContext;
        private List<Answer> mDataAnswerList;

        AnswerMatchAdapter(Context mContext, List<Answer> mListData) {
            this.mDataAnswerList = mListData;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public AnswerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_answer, viewGroup, false);
            return new AnswerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final AnswerHolder answerHolder, final int i) {
            final Answer answer = mDataAnswerList.get(i);
            int show = i + 65;
            answerHolder.tvContent.setText(String.format("%s. %s", (char) show, answer.getContentAnswer()));
        }

        @Override
        public int getItemCount() {
            return mDataAnswerList.size();
        }

        static class AnswerHolder extends RecyclerView.ViewHolder {
            TextView tvContent;

            AnswerHolder(@NonNull View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tv_content_answer_match);
            }
        }
    }
}
