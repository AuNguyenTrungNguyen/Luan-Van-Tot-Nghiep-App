package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.CheckingAnswerAdapter;
import luanvan.luanvantotnghiep.Adapter.QuizAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Helper.StartSnapHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener, QuizAdapter.CommunicateQuiz {

    private TextView mTvTime;
    private TextView mTvTotal;
    private Button mBtnComplete;

    private QuizAdapter mQuizAdapter;
    private RecyclerView mRvQuestion;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    private CountDownTimer mCountDownTimer;
    private boolean isPlaying = false;
    private long mCurrentTime = 0;
    private int mTotalQuestion = 0;

    //List use update UI and handle score
    private List<String> mListUserAnswer;
    private CheckingAnswerAdapter mCheckingAnswerAdapter;

    private Dialog dialog;

    private PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();

        init();

        if (checkGame()) {
            chooseOption();
        }
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

                //prepare data score
                mListUserAnswer = new ArrayList<>();
                for (int i = 0; i < mTotalQuestion; i++) {
                    mListUserAnswer.add(i, "");
                }

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
        setUpGame();
        showQuestion();
    }

    private void setUpGame() {
        mCountDownTimer = new CountDownTimer(330000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                mTvTime.setText(convertLongToTime(millisUntilFinished));

                //set text color  time <= 5m
                if (millisUntilFinished <= 300000) {
                    mTvTime.setTextColor(Color.RED);
                }

            }

            @Override
            public void onFinish() {

                showScore();

            }
        }.start();

        mTvTotal.setText("0/" + mTotalQuestion);
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

    @Override
    public void onBackPressed() {
        checkUserOut();
    }

    //Function check when user out game or submit quiz
    private void checkUserOut() {
        if (isPlaying) {

            dialog = new Dialog(QuizActivity.this);
            dialog.setContentView(R.layout.layout_dialog_game_submit);

            TextView tvAnswered = dialog.findViewById(R.id.tv_answered);
            final TextView tvTimeLeft = dialog.findViewById(R.id.tv_time_left);
            Button btnSubmit = dialog.findViewById(R.id.btn_submit);
            Button btnContinue = dialog.findViewById(R.id.btn_continue);

            updateNumberAnswered(tvAnswered);

            //handel time running in dialog
            tvTimeLeft.setText("Bạn dừng lúc: " + convertLongToTime(mCurrentTime));

            //when user submit quiz
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

    private void showScore() {
        float score = 0;

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        for (int i = 0; i < mQuestionList.size(); i++) {
            for (int j = 0; j < mAnswerByQuestionList.size(); j++) {
                if (mQuestionList.get(i).getIdQuestion().equals(mAnswerByQuestionList.get(j).getIdQuestion())
                        && mListUserAnswer.get(i).equals(mAnswerByQuestionList.get(j).getIdAnswer())) {
                    if (mAnswerByQuestionList.get(j).getCorrect() == 1) {
                        score++;
                        break;
                    }
                }
            }
        }

        isPlaying = false;
        mBtnComplete.setVisibility(View.INVISIBLE);
        mCountDownTimer.cancel();

        final Dialog dialog = new Dialog(QuizActivity.this);
        dialog.setContentView(R.layout.layout_dialog_score_game);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvLevel = dialog.findViewById(R.id.tv_level);
        TextView tvScore = dialog.findViewById(R.id.tv_score);
        TextView tvCorrectAnswer = dialog.findViewById(R.id.tv_correct_answer);
        ImageView imgReview = dialog.findViewById(R.id.img_review);
        ImageView imgFinish = dialog.findViewById(R.id.img_finish);

        //Handel start by score
        ImageView imgStarOne = dialog.findViewById(R.id.img_star_one);
        ImageView imgStarTwo = dialog.findViewById(R.id.img_star_two);
        ImageView imgStarThree = dialog.findViewById(R.id.img_star_three);

        if (score >= 9 && score < 13) {
            imgStarTwo.setVisibility(View.VISIBLE);
        } else if (score >= 13 && score < 17) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        } else if (score >= 17) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarTwo.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        }

        tvLevel.setText(String.format("Level %s", getLevel()));
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

    private void init() {
        //Activity
        mTvTime = findViewById(R.id.tv_time);
        mTvTotal = findViewById(R.id.tv_total);
        mBtnComplete = findViewById(R.id.btn_complete_quiz);
        mBtnComplete.setOnClickListener(this);

        //Adapter
        mRvQuestion = findViewById(R.id.rv_question);
        mQuestionList = new ArrayList<>();
        mAnswerList = new ArrayList<>();
        mAnswerByQuestionList = new ArrayList<>();

        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(this);
    }

    private void showQuestion() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mRvQuestion.setLayoutManager(mLayoutManager);

        mQuizAdapter = new QuizAdapter(this,
                mQuestionList, mAnswerList, mAnswerByQuestionList);
        mRvQuestion.setAdapter(mQuizAdapter);

        mQuizAdapter.setOnItemClickListener(this);

        mRvQuestion.setHasFixedSize(true);

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(mRvQuestion);
    }

    private void reviewQuiz() {

        for (int i = 0; i < mQuestionList.size(); i++) {
            Question question = mQuestionList.get(i);
            for (int j = 0; j < mAnswerByQuestionList.size(); j++) {
                AnswerByQuestion answerByQuestion = mAnswerByQuestionList.get(j);
                if (question.getIdQuestion().equals(answerByQuestion.getIdQuestion()) && answerByQuestion.getCorrect() == 1) {
                    question.setIdCorrect(answerByQuestion.getIdAnswer());
                    break;
                }
            }

        }
        mRvQuestion.scrollToPosition(0);
        mQuizAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_complete_quiz:
                checkUserOut();
        }
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }

    @Override
    public void onUserChooseAnswer(int question, String answer) {
        mListUserAnswer.set(question, answer);
        updateNumberAnswered(mTvTotal);
        if (mCheckingAnswerAdapter != null) {
            mCheckingAnswerAdapter.notifyDataSetChanged();
        }
    }

    private void updateNumberAnswered(TextView textView) {
        int count = 0;

        for (int i = 0; i < mListUserAnswer.size(); i++) {
            if (!mListUserAnswer.get(i).equals("")) {
                count++;
            }
        }
        textView.setText(String.format("Đã làm: %s/%s", count, mTotalQuestion));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_quiz:

                final PopupWindow popupWindow = new PopupWindow(this);
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_popup_quiz, null, false);

                GridView gridView = view.findViewById(R.id.gv_question);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < mListUserAnswer.size(); i++) {
                    list.add(String.valueOf(i));
                }
                mCheckingAnswerAdapter = new CheckingAnswerAdapter(this, R.layout.item_checking_answer, mListUserAnswer);
                gridView.setAdapter(mCheckingAnswerAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mRvQuestion.scrollToPosition(i);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setContentView(view);
                popupWindow.setFocusable(true);
                Toolbar viewToolbar = findViewById(R.id.toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popupWindow.showAsDropDown(viewToolbar, 0, 0, Gravity.RIGHT);
                } else {
                    popupWindow.showAtLocation(viewToolbar, Gravity.CENTER, 0, 0);
                }
                break;
        }
        return true;
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

            //mQuestionList = tempList.subList(0, 20);
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
}

