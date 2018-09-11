package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class RecyclerViewQuestionAdapter extends RecyclerView.Adapter<RecyclerViewQuestionAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;

    int lastPosition = -1;

    public RecyclerViewQuestionAdapter(Context mContext,
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
        return new RecyclerViewQuestionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = mQuestionList.get(position);
        holder.tvQuestion.setText("Câu " + (position + 1) + ". " + question.getContentQuestion());

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

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.quiz_anim);
        holder.cvQuiz.startAnimation(animation);

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
            cvQuiz =view.findViewById(R.id.cv_quiz);
        }
    }
}
