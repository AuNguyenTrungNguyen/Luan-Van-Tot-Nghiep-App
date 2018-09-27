package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.MatchSentencesAdapter;
import luanvan.luanvantotnghiep.ControlRecycle.MyItemTouch;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class MatchSentencesActivity extends AppCompatActivity {

    private TextView mTvTime;
    private Button mBtnComplete;

    private RecyclerView mRvQuestion;
    private RecyclerView mRvAnswer;
    private List<Question> mQuestionList;
    private List<Answer> mListAnswer;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private MatchSentencesAdapter matchSentencesAdapterQ;
    private MatchSentencesAdapter matchSentencesAdapterA;

    private CountDownTimer mCountDownTimer;
    private long mCurrentTime = 0;

    private boolean isPlaying = false;

    List<Object> listQ;
    List<Object> listA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_sentences);

        setupToolbar();

        init();

        chooseOption();
    }

    private void showScore() {
        int score = 0;

        //handle score
        for (int i = 0; i < listQ.size(); i++) {
            int idQuestion = ((Question) listQ.get(i)).getIdQuestion();
            int idAnswer = ((Answer) listA.get(i)).getIdAnswer();

            for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
                int idQuestionCorrect = answerByQuestion.getIdQuestion();
                int idAnswerCorrect = answerByQuestion.getIdAnswer();

                if (idAnswer == idAnswerCorrect && idQuestion == idQuestionCorrect && answerByQuestion.isCorrect()){
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

        //TextView tvLevel = dialog.findViewById(R.id.tv_level);
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

        //tvLevel.setText("Text level");
        tvScore.setText(String.valueOf(score));
        tvCorrectAnswer.setText(String.format("%s / %s", score, mQuestionList.size()));
        dialog.setCancelable(false);
        dialog.show();

        imgReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                review();
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

    private void review() {
        for (int i = 0; i < listQ.size(); i++) {
            int idQuestion = ((Question) listQ.get(i)).getIdQuestion();
            int idAnswer = ((Answer) listA.get(i)).getIdAnswer();

            for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
                int idQuestionCorrect = answerByQuestion.getIdQuestion();
                int idAnswerCorrect = answerByQuestion.getIdAnswer();

                if (idAnswer == idAnswerCorrect && idQuestion == idQuestionCorrect && answerByQuestion.isCorrect()){
                    //tìm sao cho ra position của nó
                    ((Answer) listA.get(i)).setShow(getPosition(idQuestion));
                    break;
                }
                ((Answer) listA.get(i)).setShow(0);
            }
        }
        matchSentencesAdapterA.notifyDataSetChanged();
        matchSentencesAdapterQ.notifyDataSetChanged();
    }

    private int getPosition(int idQuestion){
        for (int i = 0; i < listQ.size(); i++) {
            if (idQuestion == ((Question) listQ.get(i)).getIdQuestion()){
                return (i+1);
            }
        }
        return 0;
    }

    private void chooseOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn đã sẵn sàng");

        builder.setPositiveButton("Làm bài", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                isPlaying = true;

                //random list question
                Collections.shuffle(mQuestionList);
                Collections.shuffle(mQuestionList);
                Collections.shuffle(mQuestionList);

                findViewById(R.id.ln_start_game).setVisibility(View.GONE);

                setupGame();

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

    private void setupGame() {

        //Setup time
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

        //TestData
        //[START]
        for (int i = 0; i < 10; i++) {
            mQuestionList.add(new Question((i + 1), String.format("%s + %s = ?", (i + 1), (i + 1))));
        }

        for (int i = 0; i < 10; i++) {
            mListAnswer.add(new Answer((i + 1), String.format("%s chứ mấy", (i + 1) * 2)));
        }

        listQ = new ArrayList<>();
        listQ.addAll(mQuestionList);

        listA = new ArrayList<>();
        listA.addAll(mListAnswer);

        Collections.shuffle(listQ);
        Collections.shuffle(listQ);
        Collections.shuffle(listA);
        Collections.shuffle(listA);

        addDataAnswerByQuestion();
        //[END]

        //setup data
        matchSentencesAdapterQ = new MatchSentencesAdapter(this, listQ);
        matchSentencesAdapterA = new MatchSentencesAdapter(this, listA);
        RecyclerView.LayoutManager mLayoutManagerQ = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManagerA = new LinearLayoutManager(this);

        ItemTouchHelper.Callback callback =
                new MyItemTouch(matchSentencesAdapterQ);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvQuestion);

        ItemTouchHelper.Callback callbackA =
                new MyItemTouch(matchSentencesAdapterA);
        ItemTouchHelper touchHelperA = new ItemTouchHelper(callbackA);
        touchHelperA.attachToRecyclerView(mRvAnswer);

        mRvQuestion.setLayoutManager(mLayoutManagerQ);
        mRvQuestion.setHasFixedSize(true);
        mRvQuestion.setAdapter(matchSentencesAdapterQ);

        mRvAnswer.setLayoutManager(mLayoutManagerA);
        mRvAnswer.setHasFixedSize(true);
        mRvAnswer.setAdapter(matchSentencesAdapterA);
    }

    private void checkUserOut() {
        if (isPlaying) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn muốn nộp bài?");
            builder.setMessage("Bạn dừng lúc " + convertLongToTime(mCurrentTime));

            builder.setPositiveButton("Nộp bài", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    isPlaying = false;
                    showScore();
                }
            });
            builder.setNegativeButton("Làm tiếp", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();

        } else {
            finish();
        }
    }

    private void addDataAnswerByQuestion() {
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

    @Override
    public void onBackPressed() {
        checkUserOut();
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

    private void init() {
        //Activity
        mTvTime = findViewById(R.id.tv_time);
        mBtnComplete = findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserOut();
            }
        });

        //Recycle
        mRvQuestion = findViewById(R.id.rv_question);
        mRvAnswer = findViewById(R.id.rv_answer);
        mQuestionList = new ArrayList<>();
        mListAnswer = new ArrayList<>();
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }
}
