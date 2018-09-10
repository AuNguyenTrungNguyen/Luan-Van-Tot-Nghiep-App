package luanvan.luanvantotnghiep.Activity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import luanvan.luanvantotnghiep.Adapter.PositionQuestionAdapter;
import luanvan.luanvantotnghiep.Adapter.SwipeQuestionAdapter;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.ChooseAnswer;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
//    private final List<CauHoiTracNghiem> listCHTN = new ArrayList<>();
//    private CauHoiTracNghiemAdapter adapter;

    private RecyclerView mRvPossition;
    private TextView mTvTime;
    private ViewPager viewPager;

    private Button mBtnComplete;

    private PositionQuestionAdapter mPositionQuestionAdapter;
    private List<Integer> mPositionList;

    private SwipeQuestionAdapter mSwipeQuestionAdapter;

    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();

        init();

        addDataPosition();

        addDataQuestion();

        addDataAnswer();

        addDataAnswerByQuestion();

        setUpTime();

        showQuestion();
    }

    private void init() {
        mRvPossition = findViewById(R.id.rv_position_question);
        mTvTime = findViewById(R.id.tv_time);
        viewPager = findViewById(R.id.vp_question);

        mBtnComplete = findViewById(R.id.btn_complete_quiz);
        mBtnComplete.setOnClickListener(this);
    }

    private void addDataPosition() {
        mPositionList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mPositionList.add(i + 1);
        }
        mPositionQuestionAdapter = new PositionQuestionAdapter(this, mPositionList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mRvPossition.setLayoutManager(mLayoutManager);
        mRvPossition.setHasFixedSize(true);

        mRvPossition.setAdapter(mPositionQuestionAdapter);
    }

    private void addDataQuestion() {
        mQuestionList = new ArrayList<>();
        Question question;
//        for (int i = 0; i < 20; i++) {
//            question = new Question(i,String.valueOf(i+1));
//            mQuestionList.add(question);
//        }
        question = new Question(1,"Chỉ ra dãy nào chỉ gồm toàn là vật thể tự nhiên?");
        mQuestionList.add(question);

        question = new Question(2,"Chỉ ra dãy nào chỉ gồm toàn là vật thể nhân tạo?");
        mQuestionList.add(question);

        question = new Question(3,"Cho dãy các cụm từ sau, dãy nào dưới đây chỉ chất?");
        mQuestionList.add(question);

        question = new Question(4,"Nước sông hồ thuộc loại:");
        mQuestionList.add(question);

        question = new Question(5,"Để tách rượu ra khỏi hỗn hợp rượu lẫn nước, dùng cách nào sau đây?");
        mQuestionList.add(question);
    }

    private void addDataAnswerByQuestion(){
        mAnswerByQuestionList = new ArrayList<>();
        AnswerByQuestion answerByQuestion;

        answerByQuestion = new AnswerByQuestion(1,1,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1,2,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1,3,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(1,4,true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2,1,true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2,2,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2,3,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(2,4,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3,5,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3,6,true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3,7,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(3,8,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4,9,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4,10,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4,11,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(4,12,true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5,13,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5,14,false);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5,15,true);
        mAnswerByQuestionList.add(answerByQuestion);

        answerByQuestion = new AnswerByQuestion(5,16,false);
        mAnswerByQuestionList.add(answerByQuestion);


    }

    private void addDataAnswer(){

        mAnswerList = new ArrayList<>();
        Answer answer;

        answer = new Answer(1,"Ấm nhôm, bình thủy tinh, nồi đất sét.");
        mAnswerList.add(answer);

        answer = new Answer(2,"Xenlulozơ, kẽm, vàng.");
        mAnswerList.add(answer);

        answer = new Answer(3,"Thao, bút, tập, sách.");
        mAnswerList.add(answer);

        answer = new Answer(4,"Nước biển, ao, hồ, suối.");
        mAnswerList.add(answer);

        answer = new Answer(5,"Bàn ghế, đường kính, vải may áo.");
        mAnswerList.add(answer);

        answer = new Answer(6,"Muối ăn, đường kính, bột sắt, nước cất.");
        mAnswerList.add(answer);

        answer = new Answer(7,"Bút chì, thước kẻ, nước cất, vàng.");
        mAnswerList.add(answer);

        answer = new Answer(8,"Nhôm, sắt, than củi, chảo gang.");
        mAnswerList.add(answer);

        answer = new Answer(9,"Đơn chất.");
        mAnswerList.add(answer);

        answer = new Answer(10,"Hợp chất.");
        mAnswerList.add(answer);

        answer = new Answer(11,"Chất tinh khiết.");
        mAnswerList.add(answer);

        answer = new Answer(12,"Hỗn hợp.");
        mAnswerList.add(answer);

        answer = new Answer(13,"Lọc.");
        mAnswerList.add(answer);

        answer = new Answer(14,"Dùng phễu chiết.");
        mAnswerList.add(answer);

        answer = new Answer(15,"Chưng cất phân đoạn.");
        mAnswerList.add(answer);

        answer = new Answer(16,"Đốt.");
        mAnswerList.add(answer);
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpTime() {
        mCountDownTimer = new CountDownTimer(1800000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvTime.setText("" + String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                //nếu time < 5p đỏ mess: còn 5p làm bài.
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void showQuestion() {
        SwipeQuestionAdapter swipeQuestionAdapter = new SwipeQuestionAdapter(this, mQuestionList, mAnswerList, mAnswerByQuestionList);
        viewPager.setAdapter(swipeQuestionAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_complete_quiz){

        }
    }


//    private void themDuLieuCauHoiTN(MyDataHelper db) {
//
//        CauHoiTracNghiem cauHoiTracNghiem = new CauHoiTracNghiem(1, "1+1 = ?", "2", "1", "3", "4");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(2, "2+2= ?", "4", "2", "3", "5");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(3, "4 + 5 = ?", "9", "8", "6", "7");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(4, "4 + 4 = ?", "8", "9", "10", "7");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//    }

}

