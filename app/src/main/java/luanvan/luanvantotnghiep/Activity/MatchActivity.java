package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
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
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class MatchActivity extends AppCompatActivity implements MatchGame, View.OnClickListener {

    private RecyclerView mRvQuestion;
    private RecyclerView mRvAnswer;
    private TextView mTvTime;
    private Button mBtnComplete;

    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
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
    private int mTotalQuestion = 0;
    private PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        setupToolbar();

        init();

        if (checkGame()) {
            chooseOption();
        }
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

        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(this);
    }

    private void checkUserOut() {
        if (isPlaying) {

            dialog = new Dialog(MatchActivity.this);
            dialog.setContentView(R.layout.layout_dialog_game_submit);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                checkUserOut();
            }
        });
    }

    private void setUpData() {

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
            for (Answer answer : mAnswerList) {
                if (answer.getIdAnswer().equals(answerByQuestion.getIdAnswer())) {
                    mDataAnswerList.add(answer);
                }
            }
        }
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }

    private void setUpGame() {

        isPlaying = true;

        mCountDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                mTvTime.setText(convertLongToTime(millisUntilFinished));

                //set text color red when time <= 5m
                if (millisUntilFinished <= 90000) {
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
            //answerHolder.tvContent.setText(String.format("%s. %s", (char) show, answer.getContentAnswer()));
            answerHolder.tvContent.setText(Html.fromHtml(String.format("%s. %s", (char) show, answer.getContentAnswer())));
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
        float score = 0;

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
        dialog.setContentView(R.layout.layout_dialog_score_game);
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

        tvLevel.setText(String.format("Level %s", getLevel()));
        tvScore.setText(String.valueOf((int) score));
        tvCorrectAnswer.setText(String.format("%s/%s", (int) score, mTotalQuestion));
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

        int extent = mPreferencesManager.getIntData(Constraint.PRE_KEY_EXTENT, 1);
        if (extent == Constraint.EXTENT_EASY) {
            float scorePre = mPreferencesManager.getFloatData(Constraint.PRE_KEY_RANK_EASY, 0);
            if (scorePre == 0) {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_EASY, score);
            } else {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_EASY, (score + scorePre));
            }
        } else if (extent == Constraint.EXTENT_NORMAL) {
            float scorePre = mPreferencesManager.getFloatData(Constraint.PRE_KEY_RANK_NORMAL, 0);
            if (scorePre == 0) {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_NORMAL, score);
            } else {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_NORMAL, (score + scorePre));
            }
        } else if (extent == Constraint.EXTENT_DIFFICULT) {
            float scorePre = mPreferencesManager.getFloatData(Constraint.PRE_KEY_RANK_DIFFICULT, 0);
            if (scorePre == 0) {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_DIFFICULT, score);
            } else {
                mPreferencesManager.saveFloatData(Constraint.PRE_KEY_RANK_DIFFICULT, (score + scorePre));
            }
        }
    }

    private void reviewQuiz() {
        for (Question question : mDataQuestionList) {
            question.setAnswer(0);
        }
        mAdapterQuestion.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        checkUserOut();
    }

    private boolean checkGame() {

        int block = PreferencesManager.getInstance().getIntData(Constraint.PRE_KEY_BLOCK, 8);
        int type = PreferencesManager.getInstance().getIntData(Constraint.PRE_KEY_TYPE, 0);
        int extent = PreferencesManager.getInstance().getIntData(Constraint.PRE_KEY_EXTENT, 1);
        int level = getLevel();

        if (block != 0 && type != 0 && level != 0 && extent != 0) {
            ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(this);
            List<Question> tempList = chemistryHelper.getQuestionsByLevel(block, type, level, extent);

            //random list question
            Collections.shuffle(tempList);
            Collections.shuffle(tempList);
            Collections.shuffle(tempList);

            mQuestionList = tempList.subList(0, 5);
            mAnswerList = chemistryHelper.getAllAnswer();
            mAnswerByQuestionList = chemistryHelper.getAllAnswerByQuestion();
            return true;
        }

        return false;
    }

    private int getLevel() {
        int level = 0;
        Intent intent = this.getIntent();
        if (intent != null) {
            level = intent.getIntExtra("LEVEL", 0);
        }
        return level;
    }

    private void chooseOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='red'> Để chơi game được thoải mái:</font>"));
        builder.setMessage(Html.fromHtml("&#x2714 Dụng cụ chuẩn bị bao gồm: <b>bút, giấy nháp, máy tính,...</b><br><br>" +
                "&#x2714 Tắt các thông báo của các ứng dụng khác trên điện thoại.<br><br>" +
                "&#x2714 Căn thời gian hợp lý theo đồng hồ đếm giờ của trò chơi.<br><br>" +
                "&#x2714 Ngồi đúng vị trí học tập của bạn."));

        builder.setPositiveButton("Làm bài", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                isPlaying = true;
                mTotalQuestion = mQuestionList.size();

                findViewById(R.id.fl_start_game).setVisibility(View.GONE);

                startGame();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void startGame() {
        setUpData();
        setUpGame();
    }
}
