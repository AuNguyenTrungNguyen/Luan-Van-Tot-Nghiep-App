package luanvan.luanvantotnghiep.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;

public class MatchActivity extends AppCompatActivity implements MatchGame {

    private RecyclerView mRvQuestion;
    private RecyclerView mRvAnswer;
    private List<Question> mQuestionList;
    private List<Answer> mListAnswer;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private List<Integer> mListUserAnswer = new ArrayList<>(); //save id answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

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
            for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
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

        RecyclerView.LayoutManager managerQuestion = new LinearLayoutManager(this);
        RecyclerView.LayoutManager managerAnswer = new LinearLayoutManager(this);

        mRvQuestion.setLayoutManager(managerQuestion);
        mRvAnswer.setLayoutManager(managerAnswer);

        mRvQuestion.setHasFixedSize(true);
        mRvAnswer.setHasFixedSize(true);
    }

    private void setUpData() {
        mQuestionList = new ArrayList<>();
        mListAnswer = new ArrayList<>();
        mAnswerByQuestionList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mQuestionList.add(new Question((i + 1), String.format("%s + %s = ?", (i + 1), (i + 1))));
        }

        for (int i = 0; i < 5; i++) {
            mListAnswer.add(new Answer((i + 1), String.format("%s chứ mấy.", (i + 1) * 2)));
        }

        for (int i = 0; i < 5; i++) {
            mAnswerByQuestionList.add(new AnswerByQuestion(i + 1, i + 1, 1));
        }

        Collections.shuffle(mQuestionList);
        Collections.shuffle(mListAnswer);
    }

    private void setUpAdapter() {

        for (int i = 0; i < mListAnswer.size(); i++) {
            mListUserAnswer.add(-1);
        }

        MatchQuestionAdapter adapterQuestion = new MatchQuestionAdapter(this, mQuestionList, mListAnswer);
        mRvQuestion.setAdapter(adapterQuestion);
        adapterQuestion.setMatchGame(this);

        AnswerMatchAdapter adapterAnswer = new AnswerMatchAdapter(this, mListAnswer);
        mRvAnswer.setAdapter(adapterAnswer);
    }

    @Override
    public void userClick(int position, int idAnswer) {
        mListUserAnswer.set(position, idAnswer);
    }

    private static class AnswerMatchAdapter extends RecyclerView.Adapter<AnswerMatchAdapter.AnswerHolder> {

        private Context mContext;
        private List<Answer> mAnswerList;

        AnswerMatchAdapter(Context mContext, List<Answer> mListData) {
            this.mAnswerList = mListData;
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
            final Answer answer = mAnswerList.get(i);
            int show = i + 65;
            answerHolder.tvContent.setText(String.format("%s. %s", (char) show, answer.getContentAnswer()));
        }

        @Override
        public int getItemCount() {
            return mAnswerList.size();
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
