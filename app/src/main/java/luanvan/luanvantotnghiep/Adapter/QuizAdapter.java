package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private HashMap<Integer, List<Answer>> map = new HashMap<>();

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

        for (int i = 0; i < mQuestionList.size(); i++) {
            map.put(i, null);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_quiz, parent, false);
        return new QuizAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Question question = mQuestionList.get(position);
        holder.tvQuestion.setText(Html.fromHtml("Câu " + (position + 1) + ". " + question.getContentQuestion()));

        //list: content 4 ids answer by question
        List<AnswerByQuestion> list = new ArrayList<>();
        int idQuestion = question.getIdQuestion();
        for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
            if (idQuestion == answerByQuestion.getIdQuestion()) {
                list.add(answerByQuestion);
            }
        }

        //listAnswer: content 4 answers by list
        if (map.get(position) == null) {
            List<Answer> listMap = new ArrayList<>();
            for (Answer answer : mAnswerList) {
                int idAnswer = answer.getIdAnswer();
                for (AnswerByQuestion answerByQuestion : list) {
                    if (idAnswer == answerByQuestion.getIdAnswer()) {
                        listMap.add(answer);
                    }
                }
            }
            //random answer
            Collections.shuffle(listMap);
            Collections.shuffle(listMap);
            Collections.shuffle(listMap);
            map.put(position, listMap);
        }

        final List<Answer> listAnswer = map.get(position);

        //set data
        holder.rbAnswerA.setText(Html.fromHtml("A. " + listAnswer.get(0).getContentAnswer()));
        holder.rbAnswerB.setText(Html.fromHtml("B. " + listAnswer.get(1).getContentAnswer()));
        holder.rbAnswerC.setText(Html.fromHtml("C. " + listAnswer.get(2).getContentAnswer()));
        holder.rbAnswerD.setText(Html.fromHtml("D. " + listAnswer.get(3).getContentAnswer()));

        setRadio(holder, mQuestionList.get(position).getAnswer());

        //radiobutton group check
        holder.rbAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setAnswer(0);
                setRadio(holder, question.getAnswer());
                communicateQuiz.onUserChooseAnswer(holder.getAdapterPosition(), listAnswer.get(0).getIdAnswer());
            }
        });
        holder.rbAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setAnswer(1);
                setRadio(holder, question.getAnswer());
                communicateQuiz.onUserChooseAnswer(holder.getAdapterPosition(), listAnswer.get(1).getIdAnswer());
            }
        });
        holder.rbAnswerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setAnswer(2);
                setRadio(holder, question.getAnswer());
                communicateQuiz.onUserChooseAnswer(holder.getAdapterPosition(), listAnswer.get(2).getIdAnswer());
            }
        });
        holder.rbAnswerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setAnswer(3);
                setRadio(holder, question.getAnswer());
                communicateQuiz.onUserChooseAnswer(holder.getAdapterPosition(), listAnswer.get(3).getIdAnswer());
            }
        });

        if (question.getIdCorrect() != -1) {
            int ui = -1;
            for (int i = 0; i < map.get(position).size(); i++) {
                if (map.get(position).get(i).getIdAnswer() == question.getIdCorrect()) {
                    ui = i;
                    break;
                }
            }
            setColorAnswer(holder, ui, question.getAnswer());
        }
    }

    private void setRadio(ViewHolder holder, int selection) {

        RadioGroup rg = holder.radioGroup;
        RadioButton b1 = holder.rbAnswerA;
        RadioButton b2 = holder.rbAnswerB;
        RadioButton b3 = holder.rbAnswerC;
        RadioButton b4 = holder.rbAnswerD;

        if (selection == 0) {
            b1.setChecked(true);
        } else if (selection == 1) {
            b2.setChecked(true);
        } else if (selection == 2) {
            b3.setChecked(true);
        } else if (selection == 3) {
            b4.setChecked(true);
        } else if (selection == -1) {
            rg.clearCheck();
        }
    }

    private void setColorAnswer(ViewHolder holder, int correct, int user) {

        RadioButton b1 = holder.rbAnswerA;
        RadioButton b2 = holder.rbAnswerB;
        RadioButton b3 = holder.rbAnswerC;
        RadioButton b4 = holder.rbAnswerD;

        b1.setBackgroundColor(Color.WHITE);
        b2.setBackgroundColor(Color.WHITE);
        b3.setBackgroundColor(Color.WHITE);
        b4.setBackgroundColor(Color.WHITE);

        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        if (user == 0) {
            b1.setEnabled(true);
        } else if (user == 1) {
            b2.setEnabled(true);
        } else if (user == 2) {
            b3.setEnabled(true);
        } else if (user == 3) {
            b4.setEnabled(true);
        }

        if (correct == 0) {
            b1.setBackgroundColor(Color.CYAN);
        } else if (correct == 1) {
            b2.setBackgroundColor(Color.CYAN);
        } else if (correct == 2) {
            b3.setBackgroundColor(Color.CYAN);
        } else if (correct == 3) {
            b4.setBackgroundColor(Color.CYAN);
        }

        if (user == correct) {
            if (correct == 0) {
                b1.setEnabled(true);
            } else if (correct == 1) {
                b2.setEnabled(true);
            } else if (correct == 2) {
                b3.setEnabled(true);
            } else if (correct == 3) {
                b4.setEnabled(true);
            }
        }
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

        ViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tv_question);
            radioGroup = view.findViewById(R.id.rg_quiz);
            rbAnswerA = view.findViewById(R.id.rb_answer_a);
            rbAnswerB = view.findViewById(R.id.rb_answer_b);
            rbAnswerC = view.findViewById(R.id.rb_answer_c);
            rbAnswerD = view.findViewById(R.id.rb_answer_d);
        }
    }
}
