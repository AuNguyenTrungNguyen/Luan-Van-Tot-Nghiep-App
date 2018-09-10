package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    // trạng thái chọn đáp án
    private List<ChooseAnswer> mChooseAnswerList = new ArrayList<>();

    public SwipeQuestionAdapter(Context context, List<Question> questionList, List<Answer> answerList, List<AnswerByQuestion> answerByQuestionList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.mQuestionList = questionList;
        this.mAnswerList = answerList;
        this.mAnswerByQuestionList = answerByQuestionList;

        for (int i = 0; i < mQuestionList.size(); i++) {
            mChooseAnswerList.add(new ChooseAnswer(i, -1));
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

        RadioGroup rbGroup = view.findViewById(R.id.rg_quiz);
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
        List<String> listContent = new ArrayList<>();
        for (Answer answer : mAnswerList) {
            int idAnswer = answer.getIdAnswer();
            for (AnswerByQuestion answerByQuestion : list) {
                if (idAnswer == answerByQuestion.getIdAnswer()) {
                    listContent.add(answer.getContentAnswer());
                }
            }
        }

        //set data
        rbAnswerA.setText(listContent.get(0));
        rbAnswerB.setText(listContent.get(1));
        rbAnswerC.setText(listContent.get(2));
        rbAnswerD.setText(listContent.get(3));

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                //UI
                switch (i) {
                    case R.id.rb_answer_a:
                        mChooseAnswerList.get(position).setNumberChooseAnswer(0);
                        break;

                    case R.id.rb_answer_b:
                        mChooseAnswerList.get(position).setNumberChooseAnswer(1);
                        break;

                    case R.id.rb_answer_c:
                        mChooseAnswerList.get(position).setNumberChooseAnswer(2);
                        break;

                    case R.id.rb_answer_d:
                        mChooseAnswerList.get(position).setNumberChooseAnswer(3);
                        break;
                }
            }
        });

        ChooseAnswer chooseAnswer = mChooseAnswerList.get(position);
        if (position == chooseAnswer.getNumberQuestion() && chooseAnswer.getNumberChooseAnswer() != -1) {
            rbGroup.check(rbGroup.getChildAt(chooseAnswer.getNumberChooseAnswer()).getId());
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private int getScore(){

        int score = 0;

        for (int i = 0; i < mQuestionList.size(); i++) {

        }

        return score;
    }
}
