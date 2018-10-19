package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.MatchQuestionAdapter;
import luanvan.luanvantotnghiep.Communicate.MatchGame;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class MatchActivity extends AppCompatActivity implements MatchGame, View.OnClickListener {

    private RecyclerView mRvQuestion;
    private RecyclerView mRvAnswer;
    private TextView mTvTime;
    private Button mBtnComplete;

    private List<Question> mQuestionList;
    private List<Answer> mListAnswer;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private List<String> mListUserAnswer = new ArrayList<>(); //save id answer

    private MatchQuestionAdapter mAdapterQuestion;

    private List<Question> mDataQuestionList;
    private List<Answer> mDataAnswerList;
    private List<AnswerByQuestion> mDataABQList;

    private CountDownTimer mCountDownTimer;
    private boolean isPlaying = false;
    private long mCurrentTime = 0;
    private Dialog dialog;
    private int mTotalQuestion = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        setupToolbar();

        init();

        setUpData();

        setUpGame();
    }


    private void init() {
        mRvQuestion = findViewById(R.id.rv_question);
        mRvAnswer = findViewById(R.id.rv_answer);
        mTvTime = findViewById(R.id.tv_time_match);
        mBtnComplete = findViewById(R.id.btn_submit);
        mBtnComplete.setOnClickListener(this);

        RecyclerView.LayoutManager managerQuestion = new LinearLayoutManager(this);
        RecyclerView.LayoutManager managerAnswer = new LinearLayoutManager(this);

        mRvQuestion.setLayoutManager(managerQuestion);
        mRvAnswer.setLayoutManager(managerAnswer);

        mRvQuestion.setHasFixedSize(true);
        mRvAnswer.setHasFixedSize(true);
    }

    private void checkUserOut() {
        if (isPlaying) {

            dialog = new Dialog(MatchActivity.this);
            dialog.setContentView(R.layout.layout_dialog_game_submit);

            TextView tvAnswered = dialog.findViewById(R.id.tv_answered);
            TextView tvTimeLeft = dialog.findViewById(R.id.tv_time_left);
            Button btnSubmit = dialog.findViewById(R.id.btn_submit);
            Button btnContinue = dialog.findViewById(R.id.btn_continue);

            tvAnswered.setVisibility(View.GONE);

            //handel time running in dialog
            tvTimeLeft.setText("Bạn dừng lúc: " + convertLongToTime(mCurrentTime));

            //when user submit
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showScore();
                    dialog.dismiss();
                }
            });

            //when user continue game
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setCancelable(false);
            dialog.show();

        } else {
            finish();
        }
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

        for (Question question : mQuestionList) {
            if (question.getIdType() == 3) {
                mDataQuestionList.add(question);
            }
        }

        for (Question question : mDataQuestionList) {
            for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
                if (answerByQuestion.getIdQuestion().equals(question.getIdQuestion())) {
                    mDataABQList.add(answerByQuestion);
                }
            }
        }

        for (AnswerByQuestion answerByQuestion : mDataABQList) {
            for (Answer answer : mListAnswer) {
                if (answer.getIdAnswer().equals(answerByQuestion.getIdAnswer())) {
                    mDataAnswerList.add(answer);
                }
            }
        }


        Collections.shuffle(mDataQuestionList);
        Collections.shuffle(mDataAnswerList);
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }

    private void setUpGame() {

        isPlaying = true;

        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                mTvTime.setText(convertLongToTime(millisUntilFinished));

                //set text color red when time <= 5m
                if (millisUntilFinished <= 300000) {
                    mTvTime.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFinish() {
                showScore();
            }
        }.start();

        for (int i = 0; i < mDataAnswerList.size(); i++) {
            mListUserAnswer.add("");
        }
        Log.i("hns", " size " + mDataAnswerList.size());

        mAdapterQuestion = new MatchQuestionAdapter(this, mDataQuestionList, mDataAnswerList, mListUserAnswer);
        mRvQuestion.setAdapter(mAdapterQuestion);
        mAdapterQuestion.setMatchGame(this);

        AnswerMatchAdapter adapterAnswer = new AnswerMatchAdapter(this, mDataAnswerList);
        mRvAnswer.setAdapter(adapterAnswer);
    }

    @Override
    public void userClick(int position, String idAnswer) {
        mListUserAnswer.set(position, idAnswer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                checkUserOut();
        }
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

    private void showScore() {
        int score = 0;

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        for (int i = 0; i < mDataQuestionList.size(); i++) {
            for (AnswerByQuestion answerByQuestion : mDataABQList) {
                if (answerByQuestion.getIdQuestion().equals(mDataQuestionList.get(i).getIdQuestion())
                        && answerByQuestion.getIdAnswer().equals(mListUserAnswer.get(i))) {
                    score++;
                    break;
                }
            }
        }

        isPlaying = false;
        mBtnComplete.setVisibility(View.INVISIBLE);
        mCountDownTimer.cancel();

        final Dialog dialog = new Dialog(MatchActivity.this);
        dialog.setContentView(R.layout.layout_dialog_score_quiz);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvLevel = dialog.findViewById(R.id.tv_level);
        TextView tvScore = dialog.findViewById(R.id.tv_score);
        TextView tvCorrectAnswer = dialog.findViewById(R.id.tv_correct_answer);
        ImageView imgReview = dialog.findViewById(R.id.img_review);
        ImageView imgFinish = dialog.findViewById(R.id.img_finish);

        //Handel stat by score
        ImageView imgStarOne = dialog.findViewById(R.id.img_star_one);
        ImageView imgStarTwo = dialog.findViewById(R.id.img_star_two);
        ImageView imgStarThree = dialog.findViewById(R.id.img_star_three);

        if (score >= 1 && score < 2) {
            imgStarTwo.setVisibility(View.VISIBLE);
        } else if (score >= 2 && score < 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        } else if (score >= 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarTwo.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        }

        //tvLevel.setText("Text level");
        tvScore.setText(String.valueOf(score));
        tvCorrectAnswer.setText(String.format("%s/%s", score, mTotalQuestion));
        dialog.setCancelable(false);
        dialog.show();

        imgReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reviewQuiz();
            }
        });

        imgFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (score >= 0) {
            imgReview.setVisibility(View.VISIBLE);
        } else {
            imgReview.setVisibility(View.INVISIBLE);
        }
    }

    private void reviewQuiz() {
        for (Question question : mDataQuestionList) {
            question.setAnswer(0);
        }
        mAdapterQuestion.notifyDataSetChanged();

        for (int i = 0; i < mListUserAnswer.size(); i++) {
            Log.i("hns", "" + mListUserAnswer.get(i));

        }
    }

    @Override
    public void onBackPressed() {
        checkUserOut();
    }
}
