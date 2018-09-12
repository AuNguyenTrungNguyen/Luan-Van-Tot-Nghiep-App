package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    public interface CommunicateQuiz {
        void onUserChooseAnswer(int question, int answer);
    }

    private CommunicateQuiz communicateQuiz;

    public void setOnItemClickListener(CommunicateQuiz clickListener) {
        this.communicateQuiz = clickListener;
    }

    public QuizAdapter(Context mContext,
                       List<Question> mQuestionList,
                       List<Answer> mAnswerList,
                       List<AnswerByQuestion> mAnswerByQuestionList) {
        this.mContext = mContext;
        this.mQuestionList = mQuestionList;
        this.mAnswerList = mAnswerList;
        this.mAnswerByQuestionList = mAnswerByQuestionList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_quiz, parent, false);
        return new QuizAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Question question = mQuestionList.get(position);
        holder.tvQuestion.setText("Câu " + (position + 1) + ". " + question.getContentQuestion());

        Log.i("ANTN", "onBindViewHolder: " + position + " " + question.getAnswer());
        switch (question.getAnswer()) {
            case 0:
                holder.rbAnswerA.setChecked(true);
                break;

            case 1:
                holder.rbAnswerB.setChecked(true);
                break;

            case 2:
                holder.rbAnswerC.setChecked(true);
                break;

            case 3:
                holder.rbAnswerD.setChecked(true);
                break;

            default:
                holder.rbAnswerA.setChecked(false);
                holder.rbAnswerB.setChecked(false);
                holder.rbAnswerC.setChecked(false);
                holder.rbAnswerD.setChecked(false);
                break;
        }

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
        holder.rbAnswerA.setText("A. " + listUser.get(0).getContentAnswer());
        holder.rbAnswerB.setText("B. " + listUser.get(1).getContentAnswer());
        holder.rbAnswerC.setText("C. " + listUser.get(2).getContentAnswer());
        holder.rbAnswerD.setText("D. " + listUser.get(3).getContentAnswer());

        //radiobutton group check
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                //vay lam theo viewpager duoc k - chịu rồi nãy t copy cho 1 class về viewpager
                switch (i) {
                    case R.id.rb_answer_a:
                        communicateQuiz.onUserChooseAnswer(position, listUser.get(0).getIdAnswer());
                        mQuestionList.get(position).setAnswer(0);
                        break;
                    case R.id.rb_answer_b:
                        communicateQuiz.onUserChooseAnswer(position, listUser.get(1).getIdAnswer());
                        mQuestionList.get(position).setAnswer(1);
                        break;
                    case R.id.rb_answer_c:
                        communicateQuiz.onUserChooseAnswer(position, listUser.get(2).getIdAnswer());
                        mQuestionList.get(position).setAnswer(2);
                        break;
                    case R.id.rb_answer_d:
                        communicateQuiz.onUserChooseAnswer(position, listUser.get(3).getIdAnswer());
                        mQuestionList.get(position).setAnswer(3);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        RadioGroup radioGroup;
        RadioButton rbAnswerA;
        RadioButton rbAnswerB;
        RadioButton rbAnswerC;
        RadioButton rbAnswerD;
        CardView cvQuiz;

        ViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tv_question);
            radioGroup = view.findViewById(R.id.rg_quiz);
            rbAnswerA = view.findViewById(R.id.rb_answer_a);
            rbAnswerB = view.findViewById(R.id.rb_answer_b);
            rbAnswerC = view.findViewById(R.id.rb_answer_c);
            rbAnswerD = view.findViewById(R.id.rb_answer_d);
            cvQuiz = view.findViewById(R.id.cv_quiz);
        }
    }
}
