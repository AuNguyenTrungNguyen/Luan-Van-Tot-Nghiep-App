package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.ChooseAnswer;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class SwipeQuestionAdapter extends PagerAdapter {

    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    private LayoutInflater mInflater;
    RadioGroup rbGroup;
    private static final String TAG = "ANTN";
    private boolean show = false;

    // trạng thái chọn đáp án
    private List<ChooseAnswer> mChooseAnswerList = new ArrayList<>();

    public SwipeQuestionAdapter(Context context, List<Question> questionList, List<Answer> answerList, List<AnswerByQuestion> answerByQuestionList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.mQuestionList = questionList;
        this.mAnswerList = answerList;
        this.mAnswerByQuestionList = answerByQuestionList;

        for (int i = 0; i < mQuestionList.size(); i++) {
            mChooseAnswerList.add(new ChooseAnswer(i));
        }

    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = mInflater.inflate(R.layout.item_quiz, container, false);

        TextView tvQuestion = view.findViewById(R.id.tv_question);

        rbGroup = view.findViewById(R.id.rg_quiz);
        RadioButton rbAnswerA = view.findViewById(R.id.rb_answer_a);
        RadioButton rbAnswerB = view.findViewById(R.id.rb_answer_b);
        RadioButton rbAnswerC = view.findViewById(R.id.rb_answer_c);
        RadioButton rbAnswerD = view.findViewById(R.id.rb_answer_d);

        final Question question = mQuestionList.get(position);
        tvQuestion.setText(question.getContentQuestion());

        //get answer
        List<AnswerByQuestion> list = new ArrayList();
        int idQuestion = question.getIdQuestion();
        for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
            if (idQuestion == answerByQuestion.getIdQuestion()) {
                list.add(answerByQuestion);
            }
        }

        //get content answer
        final List<Answer> listUser = new ArrayList<>();
        for (Answer answer : mAnswerList) {
            int idAnswer = answer.getIdAnswer();
            for (AnswerByQuestion answerByQuestion : list) {
                if (idAnswer == answerByQuestion.getIdAnswer()) {
                    listUser.add(answer);
                }
            }
        }

        //set data
        rbAnswerA.setText(listUser.get(0).getContentAnswer());
        rbAnswerB.setText(listUser.get(1).getContentAnswer());
        rbAnswerC.setText(listUser.get(2).getContentAnswer());
        rbAnswerD.setText(listUser.get(3).getContentAnswer());

        if (show) {
            //no user click
            for (int i = 0; i < rbGroup.getChildCount(); i++){
                rbGroup.getChildAt(i).setEnabled(false);
            }

            //set background
            int posi = -1;
            for (int i = 0; i < mAnswerByQuestionList.size(); i++) {
                if(mAnswerByQuestionList.get(i).isCorrect()){
                    int id = mAnswerByQuestionList.get(i).getIdAnswer();
                    Log.i(TAG, "id: " + id);
                    for (int j = 0; j < listUser.size(); j++) {
                        if (id == listUser.get(j).getIdAnswer()){
                            posi = j;
                            Log.i(TAG, "j: " + j);
                            Log.i(TAG, "listUser.get(j).getIdAnswer()" + listUser.get(j).getIdAnswer());
                            break;
                        }
                    }
                }
            }

            switch (posi){
                case 0:
                    rbAnswerA.setBackgroundColor(Color.CYAN);
                    break;

                case 1:
                    rbAnswerB.setBackgroundColor(Color.CYAN);
                    break;

                case 2:
                    rbAnswerC.setBackgroundColor(Color.CYAN);
                    break;

                case 3:
                    rbAnswerD.setBackgroundColor(Color.CYAN);
                    break;
            }
        }

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                mChooseAnswerList.get(position).setNumberChooseAnswer(i);

                switch (i) {
                    case R.id.rb_answer_a:
                        mChooseAnswerList.get(position).setIdChooseAnswer(listUser.get(0).getIdAnswer());
                        break;

                    case R.id.rb_answer_b:
                        mChooseAnswerList.get(position).setIdChooseAnswer(listUser.get(1).getIdAnswer());
                        break;

                    case R.id.rb_answer_c:
                        mChooseAnswerList.get(position).setIdChooseAnswer(listUser.get(2).getIdAnswer());
                        break;

                    case R.id.rb_answer_d:
                        mChooseAnswerList.get(position).setIdChooseAnswer(listUser.get(3).getIdAnswer());
                        break;
                }
            }
        });

        ChooseAnswer chooseAnswer = mChooseAnswerList.get(position);
        if (position == chooseAnswer.getNumberQuestion() && chooseAnswer.getNumberChooseAnswer() != -1) {
            rbGroup.check(chooseAnswer.getNumberChooseAnswer());
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public int getScore() {

        int score = 0;

        for (int i = 0; i < mQuestionList.size(); i++) {
            for (int j = 0; j < mAnswerByQuestionList.size(); j++) {
                if (mQuestionList.get(i).getIdQuestion() == mAnswerByQuestionList.get(j).getIdQuestion()
                        && mChooseAnswerList.get(i).getIdChooseAnswer() == mAnswerByQuestionList.get(j).getIdAnswer()) {
                    if (mAnswerByQuestionList.get(j).isCorrect()) {
                        score++;
                        break;
                    }
                }
            }
        }

        show = true;

        return score;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
