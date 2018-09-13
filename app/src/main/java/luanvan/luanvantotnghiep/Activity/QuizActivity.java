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
import luanvan.luanvantotnghiep.Adapter.QuizAdapter;
import luanvan.luanvantotnghiep.Helper.StartSnapHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

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
    private List<Integer> mListUserAnswer;
    private CheckingAnswerAdapter mCheckingAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();

        init();

        addDataQuestion();

        addDataAnswer();

        addDataAnswerByQuestion();

    }

    @Override
    protected void onStart() {
        super.onStart();
        chooseOption();
    }

    private void chooseOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("QUẤT LUÔN KHÔNG?");

        builder.setPositiveButton("Quất", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                isPlaying = true;
                mTotalQuestion = mQuestionList.size();

                //prepare data score
                mListUserAnswer = new ArrayList<>();
                for (int i = 0; i < mTotalQuestion; i++) {
                    mListUserAnswer.add(i, -1);
                }

                findViewById(R.id.ln_start_game).setVisibility(View.GONE);

                startGame();
            }
        });
        builder.setNegativeButton("Xin lui", new DialogInterface.OnClickListener() {
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

            final Dialog dialog = new Dialog(QuizActivity.this);
            dialog.setContentView(R.layout.layout_dialog_submit_quiz);

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
        int score = 0;

        for (int i = 0; i < mQuestionList.size(); i++) {
            for (int j = 0; j < mAnswerByQuestionList.size(); j++) {
                if (mQuestionList.get(i).getIdQuestion() == mAnswerByQuestionList.get(j).getIdQuestion()
                        && mListUserAnswer.get(i) == mAnswerByQuestionList.get(j).getIdAnswer()) {
                    if (mAnswerByQuestionList.get(j).isCorrect()) {
                        score++;
                        break;
                    }
                }
            }
        }

        isPlaying = false;
        mBtnComplete.setVisibility(View.INVISIBLE);

        final Dialog dialog = new Dialog(QuizActivity.this);
        dialog.setContentView(R.layout.layout_dialog_score_quiz);
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

        if (score >= 1 && score < 3) {
            imgStarTwo.setVisibility(View.VISIBLE);
        } else if (score >= 3 && score < 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        } else if (score >= 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarTwo.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        }

        //tvLevel.setText("Text level");
        tvScore.setText(String.valueOf(score));
        tvCorrectAnswer.setText(score + "/" + mTotalQuestion);
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

        if (score >= 5) {
            imgReview.setVisibility(View.VISIBLE);
        } else {
            imgReview.setVisibility(View.INVISIBLE);
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
    }

    private void addDataQuestion() {
        mQuestionList = new ArrayList<>();
        Question question;

        question = new Question(1, "Chỉ ra dãy nào chỉ gồm toàn là vật thể tự nhiên?");
        mQuestionList.add(question);

        question = new Question(2, "Chỉ ra dãy nào chỉ gồm toàn là vật thể nhân tạo?");
        mQuestionList.add(question);

        question = new Question(3, "Cho dãy các cụm từ sau, dãy nào dưới đây chỉ chất?");
        mQuestionList.add(question);

        question = new Question(4, "Nước sông hồ thuộc loại:");
        mQuestionList.add(question);

        question = new Question(5, "Để tách rượu ra khỏi hỗn hợp rượu lẫn nước, dùng cách nào sau đây?");
        mQuestionList.add(question);

        question = new Question(6, "Những nhận xét nào sau đây đúng?");
        mQuestionList.add(question);

        question = new Question(7, "Những nhận xét nào sau đây đúng?");
        mQuestionList.add(question);

        question = new Question(8, "Những nhận xét nào sau đây đúng?");
        mQuestionList.add(question);
    }

    private void addDataAnswerByQuestion() {
        mAnswerByQuestionList = new ArrayList<>();
        AnswerByQuestion answerByQuestion;

        answerByQuestion = new AnswerByQuestion(1, 1, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1, 2, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1, 3, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1, 4, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 1, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 2, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 3, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2, 4, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 5, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 6, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 7, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3, 8, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 9, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 10, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 11, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4, 12, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 13, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 14, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 15, true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5, 16, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 17, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 18, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 19, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(6, 20, true);
        mAnswerByQuestionList.add(answerByQuestion);

        //7777777
        answerByQuestion = new AnswerByQuestion(7, 17, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(7, 18, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(7, 19, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(7, 20, true);
        mAnswerByQuestionList.add(answerByQuestion);

        //8888888888
        answerByQuestion = new AnswerByQuestion(8, 17, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(8, 18, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(8, 19, false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(8, 20, true);
        mAnswerByQuestionList.add(answerByQuestion);

    }

    private void addDataAnswer() {

        mAnswerList = new ArrayList<>();
        Answer answer;

        answer = new Answer(1, "Ấm nhôm, bình thủy tinh, nồi đất sét.");
        mAnswerList.add(answer);

        answer = new Answer(2, "Xenlulozơ, kẽm, vàng.");
        mAnswerList.add(answer);

        answer = new Answer(3, "Thao, bút, tập, sách.");
        mAnswerList.add(answer);

        answer = new Answer(4, "Nước biển, ao, hồ, suối.");
        mAnswerList.add(answer);

        answer = new Answer(5, "Bàn ghế, đường kính, vải may áo.");
        mAnswerList.add(answer);

        answer = new Answer(6, "Muối ăn, đường kính, bột sắt, nước cất.");
        mAnswerList.add(answer);

        answer = new Answer(7, "Bút chì, thước kẻ, nước cất, vàng.");
        mAnswerList.add(answer);

        answer = new Answer(8, "Nhôm, sắt, than củi, chảo gang.");
        mAnswerList.add(answer);

        answer = new Answer(9, "Đơn chất.");
        mAnswerList.add(answer);

        answer = new Answer(10, "Hợp chất.");
        mAnswerList.add(answer);

        answer = new Answer(11, "Chất tinh khiết.");
        mAnswerList.add(answer);

        answer = new Answer(12, "Hỗn hợp.");
        mAnswerList.add(answer);

        answer = new Answer(13, "Lọc.");
        mAnswerList.add(answer);

        answer = new Answer(14, "Dùng phễu chiết.");
        mAnswerList.add(answer);

        answer = new Answer(15, "Chưng cất phân đoạn.");
        mAnswerList.add(answer);

        answer = new Answer(16, "Đốt.");
        mAnswerList.add(answer);

        answer = new Answer(17, "Xăng, khí nitơ, muối ăn, nước tự nhiên là hỗn hợp.");
        mAnswerList.add(answer);

        answer = new Answer(18, "Sữa, không khí, nước chanh, trà đá là hợp chất.");
        mAnswerList.add(answer);

        answer = new Answer(19, "Muối ăn, đường, khí cacbonic, nước cất là chất tinh khiết.");
        mAnswerList.add(answer);

        answer = new Answer(20, "Dựa vào sự khác nhau về tính chất vật lý có thể tách một chất ra khỏi hỗn hợp.");
        mAnswerList.add(answer);
    }

    private void setUpGame() {
        mCountDownTimer = new CountDownTimer(1800000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                mTvTime.setText(convertLongToTime(millisUntilFinished));
                //nếu time < 5p đỏ mess: còn 5p làm bài.
            }

            @Override
            public void onFinish() {

                showScore();

            }
        }.start();

        mTvTotal.setText("0/" + mTotalQuestion);
    }

    private void showQuestion() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mRvQuestion.setLayoutManager(mLayoutManager);
//        PageVisibleLinearLayoutManager pageVisibleLinearLayoutManager = new PageVisibleLinearLayoutManager(this,
//                LinearLayoutManager.HORIZONTAL,
//                false);
//        mRvQuestion.setLayoutManager(pageVisibleLinearLayoutManager);

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
                if (question.getIdQuestion() == answerByQuestion.getIdQuestion() && answerByQuestion.isCorrect()) {
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
    public void onUserChooseAnswer(int question, int answer) {
        mListUserAnswer.set(question, answer);
        updateNumberAnswered(mTvTotal);
        mCheckingAnswerAdapter.notifyDataSetChanged();
    }

    private void updateNumberAnswered(TextView textView) {
        int count = 0;

        for (int i = 0; i < mListUserAnswer.size(); i++) {
            if (mListUserAnswer.get(i) != -1) {
                count++;
            }
        }
        textView.setText(count + "/" + mTotalQuestion);
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
                mCheckingAnswerAdapter = new CheckingAnswerAdapter(this,R.layout.item_checking_answer, mListUserAnswer);
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
                }else{
                    popupWindow.showAtLocation(viewToolbar, Gravity.CENTER, 0, 0);
                }
                break;
        }
        return true;
    }
}

