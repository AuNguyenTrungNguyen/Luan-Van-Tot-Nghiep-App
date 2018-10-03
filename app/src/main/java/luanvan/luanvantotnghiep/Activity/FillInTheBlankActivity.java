package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.format.DateFormat;
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
import java.util.Date;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.CheckingAnswerAdapter;
import luanvan.luanvantotnghiep.Adapter.FillInTheBlankAdapter;
import luanvan.luanvantotnghiep.Helper.StartSnapHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class FillInTheBlankActivity extends AppCompatActivity implements View.OnClickListener, FillInTheBlankAdapter.CommunicateQuiz {

    private TextView mTvTime;
    private TextView mTvTotal;
    private Button mBtnComplete;

    private FillInTheBlankAdapter mFillInTheBlankAdapter;
    private RecyclerView mRvQuestion;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    private CountDownTimer mCountDownTimer;
    private boolean isPlaying = false;
    private long mCurrentTime = 0;
    private int mTotalQuestion = 0;

    //List use update UI and handle score
    private List<Integer> mListUserAnswer;
    private CheckingAnswerAdapter mCheckingAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_blank);

        setupToolbar();

        init();

        addDataDK();

        addDataAnswerDK();

        addDataAnswerByQuestionDK();

        chooseOption();
    }

    private void chooseOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn đã sẵn sàng?");

        builder.setPositiveButton("Bắt đầu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                isPlaying = true;
                mTotalQuestion = mQuestionList.size();

                //prepare data score
                mListUserAnswer = new ArrayList<>();
                for (int i = 0; i < mTotalQuestion; i++) {
                    mListUserAnswer.add(i, -1);
                }

                //random list question
//                Collections.shuffle(mQuestionList);
//                Collections.shuffle(mQuestionList);
//                Collections.shuffle(mQuestionList);

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

    //Function check when user out game or submit
    private void checkUserOut() {
        if (isPlaying) {

            final Dialog dialog = new Dialog(FillInTheBlankActivity.this);
            dialog.setContentView(R.layout.layout_dialog_game_submit);

            TextView tvAnswered = dialog.findViewById(R.id.tv_answered);
            TextView tvTimeLeft = dialog.findViewById(R.id.tv_time_left);
            Button btnSubmit = dialog.findViewById(R.id.btn_submit);
            Button btnContinue = dialog.findViewById(R.id.btn_continue);

            updateNumberAnswered(tvAnswered);

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

    private void showScore() {
        int score = 0;

        for (int i = 0; i < mQuestionList.size(); i++) {
            for (int j = 0; j < mAnswerByQuestionList.size(); j++) {
                if (mQuestionList.get(i).getIdQuestion() == mAnswerByQuestionList.get(j).getIdQuestion()
                        && mListUserAnswer.get(i) == -999) {
                    score++;
                    break;
                }
            }
        }

        isPlaying = false;
        mBtnComplete.setVisibility(View.INVISIBLE);
        mCountDownTimer.cancel();

        final Dialog dialog = new Dialog(this);
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

    private void init() {
        //Activity
        mTvTime = findViewById(R.id.tv_time);
        mTvTotal = findViewById(R.id.tv_total);
        mBtnComplete = findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(this);

        //Adapter
        mRvQuestion = findViewById(R.id.rv_question);
        mQuestionList = new ArrayList<>();
        mAnswerList = new ArrayList<>();
        mAnswerByQuestionList = new ArrayList<>();
    }

    private void addDataDK() {
        mQuestionList = new ArrayList<>();
        Question question;

        question = new Question(1, "⁅⁆ là khoa học nghiên cứu các chất, sự biến đổi và ứng dụng.");
        mQuestionList.add(question);

        question = new Question(2, "Hóa học có vai trò ⁅⁆ trong cuộc sống chúng ta.");
        mQuestionList.add(question);

        question = new Question(3, "Chất có khắp mọi nơi, ở đâu có vật thể ở đó có ⁅⁆.");
        mQuestionList.add(question);

        question = new Question(4, "Nước ⁅⁆ gồm nhiều chất trộn lẫn là một ⁅⁆.");
        mQuestionList.add(question);

        question = new Question(5, "⁅⁆ là chất tinh khiết.");
        mQuestionList.add(question);

        question = new Question(6, "Dựa vào sự ⁅⁆ về tính chất ⁅⁆ có thể tách một chất ra khỏi ⁅⁆.");
        mQuestionList.add(question);

        question = new Question(7, "⁅⁆ là ⁅⁆ vô cùng nhỏ và ⁅⁆ về điện.");
        mQuestionList.add(question);

        question = new Question(8, "Nguyên tử là hạt nhân mang ⁅⁆ và vỏ tạo bởi một hay nhiều ⁅⁆ mang ⁅⁆.");
        mQuestionList.add(question);

        question = new Question(9, "Hạt nhân tạo bởi ⁅⁆ và nơtron.");
        mQuestionList.add(question);

        question = new Question(10, "Trong mỗi nguyên tử, số proton bằng số ⁅⁆.");
        mQuestionList.add(question);
    }

    private void addDataAnswerDK() {

        mAnswerList = new ArrayList<>();
        Answer answer;

        answer = new Answer(1, "{hóa học}");
        mAnswerList.add(answer);

        answer = new Answer(2, "{rất quan trọng}");
        mAnswerList.add(answer);

        answer = new Answer(3, "{chất}");
        mAnswerList.add(answer);

        answer = new Answer(4, "{tự nhiên},{hỗn hợp}");
        mAnswerList.add(answer);

        answer = new Answer(5, "{nước cất}");
        mAnswerList.add(answer);

        answer = new Answer(6, "{khác nhau},{vật lý},{hỗn hợp}");
        mAnswerList.add(answer);

        answer = new Answer(7, "{nguyên tử},{hạt},{trung hòa}");
        mAnswerList.add(answer);

        answer = new Answer(8, "{điện tích dương},{electron},{điện tích âm}");
        mAnswerList.add(answer);

        answer = new Answer(9, "{proton}");
        mAnswerList.add(answer);

        answer = new Answer(10, "{electron}");
        mAnswerList.add(answer);
    }

    private void addDataAnswerByQuestionDK() {
        mAnswerByQuestionList = new ArrayList<>();
        AnswerByQuestion answerByQuestion;

        answerByQuestion = new AnswerByQuestion(1, 1, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 2, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 3, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 4, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 5, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 6, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(7, 7, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(8, 8, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(9, 9, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(10, 10, true);
        mAnswerByQuestionList.add(answerByQuestion);
    }

    private void showQuestion() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mRvQuestion.setLayoutManager(mLayoutManager);

        mFillInTheBlankAdapter = new FillInTheBlankAdapter(this,
                mQuestionList, mAnswerList, mAnswerByQuestionList);
        mRvQuestion.setAdapter(mFillInTheBlankAdapter);

        mFillInTheBlankAdapter.setOnItemClickListener(this);

        mRvQuestion.setHasFixedSize(true);

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(mRvQuestion);
    }

    private void reviewQuiz() {

        for (int i = 0; i < mQuestionList.size(); i++) {
            Question question = mQuestionList.get(i);
            question.setIdCorrect(mListUserAnswer.get(i));
            if (mListUserAnswer.get(i) == -1) {
                question.setIdCorrect(0);
            }
        }
        mRvQuestion.scrollToPosition(0);
        mFillInTheBlankAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_complete:
                checkUserOut();
        }
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }

    @Override
    public void onUserChooseAnswer(int question, int answer) {
        mListUserAnswer.set(question, answer);
        updateNumberAnswered(mTvTotal);
        if (mCheckingAnswerAdapter != null) {
            mCheckingAnswerAdapter.notifyDataSetChanged();
        }
    }

    private void updateNumberAnswered(TextView textView) {
        int count = 0;

        for (int i = 0; i < mListUserAnswer.size(); i++) {
            if (mListUserAnswer.get(i) != -1) {
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
}
