package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Activity.FillInTheBlankActivity;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class FillInTheBlankAdapter extends RecyclerView.Adapter<FillInTheBlankAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private List<SpannableString> mUIList = new ArrayList<>();

    private static final Character START_CODE = '⁅';
    private static final Character END_CODE = '⁆';
    private static final Character START_SHOW = '⇠';
    private static final Character END_SHOW = '⇢';
    private List<PositionCode> positionCodeList = new ArrayList<>();

    public interface CommunicateQuiz {
        void onUserChooseAnswer(int question, int answer);
    }

    private CommunicateQuiz communicateQuiz;

    public void setOnItemClickListener(FillInTheBlankActivity clickListener) {
        this.communicateQuiz = clickListener;
    }

    public FillInTheBlankAdapter(Context mContext,
                                 List<Question> mQuestionList,
                                 List<Answer> mAnswerList,
                                 List<AnswerByQuestion> mAnswerByQuestionList) {
        this.mContext = mContext;
        this.mQuestionList = mQuestionList;
        this.mAnswerList = mAnswerList;
        this.mAnswerByQuestionList = mAnswerByQuestionList;
        for (int i = 0; i < mQuestionList.size(); i++) {
            mUIList.add(null);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_fill_in_the_blank, parent, false);
        return new FillInTheBlankAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Question question = mQuestionList.get(position);

        //FIRST load data
        SpannableString spannableString = handleClickQuestion(question.getContentQuestion(), holder, position);
        holder.tvQuestion.setText(spannableString);
        holder.tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
        holder.tvNumberQuestion.setText(String.format("Câu %s", position + 1));

        //WHEN user update data
        if (mUIList.get(position) != null) {
            holder.tvQuestion.setText(mUIList.get(position));
            holder.tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (question.getIdCorrect() != -1) {
            showAnswer(holder.tvQuestion.getText().toString(), position, holder);
        }
    }

    private void showAnswer(String question, int position, ViewHolder holder) {

        question = question.replace(START_SHOW, START_CODE);
        question = question.replace(END_SHOW, END_CODE);

        positionCodeList.clear();
        for (int i = 0; i < question.length() - 1; i++) {
            if (question.substring(i, i + 1).equals(String.valueOf(START_CODE))) {
                for (int j = i + 1; j < question.length() - 1; j++) {
                    if (question.substring(j, j + 1).equals(String.valueOf(END_CODE))) {
                        positionCodeList.add(new PositionCode(i, j + 1));
                        break;
                    }
                }
            }
        }

        question = question.replace(START_CODE, START_SHOW);
        question = question.replace(END_CODE, END_SHOW);

        String correctAnswer = "";
        for (AnswerByQuestion byQuestion : mAnswerByQuestionList) {
            for (Answer answer : mAnswerList) {
                if (mQuestionList.get(position).getIdQuestion() == byQuestion.getIdQuestion()
                        && byQuestion.getIdAnswer() == answer.getIdAnswer()) {
                    correctAnswer = answer.getContentAnswer();
                }
            }
        }

        String correctAnswerArr[] = correctAnswer.split(",");
        StringBuilder temp = new StringBuilder();
        int index = 0;
        for (int i = 0; i < positionCodeList.size(); i++) {
            PositionCode positionCode = positionCodeList.get(i);
            temp.append(question.substring(index, positionCode.start));
            if (question.substring(positionCode.start, positionCode.end).toLowerCase().equals(correctAnswerArr[i].toLowerCase())) {
                temp.append("<font color='green'>").append(question.substring(positionCode.start, positionCode.end).toLowerCase()).append("</font>");
            } else {
                temp.append("<font color='red'>").append(question.substring(positionCode.start, positionCode.end).toLowerCase()).append("</font>");
            }

            index = positionCode.end;
        }
        if (index < question.length()) {
            temp.append(question.substring(index, question.length()));
        }

        holder.tvQuestion.setText(Html.fromHtml(temp.toString()));

        //Show answer
        temp = new StringBuilder();
        index = 0;
        for (int i = 0; i < positionCodeList.size(); i++) {
            PositionCode positionCode = positionCodeList.get(i);
            temp.append(question.substring(index, positionCode.start));
            temp.append("<b><i><u>").append(correctAnswerArr[i]).append("</u></i></b>");
            index = positionCode.end;
        }
        if (index < question.length()) {
            temp.append(question.substring(index, question.length()));

        }
        String result = temp.toString();

        result = result.replace(String.valueOf(START_SHOW), "");
        result = result.replace(String.valueOf(END_SHOW), "");

        holder.tvShowAnswered.setText(Html.fromHtml(result));
        holder.tvTextAnswer.setVisibility(View.VISIBLE);
        holder.tvShowAnswered.setVisibility(View.VISIBLE);
    }

    private SpannableString handleClickQuestion(String question, final ViewHolder holder, final int position) {

        question = question.replace(START_SHOW, START_CODE);
        question = question.replace(END_SHOW, END_CODE);

        positionCodeList.clear();
        for (int i = 0; i < question.length() - 1; i++) {
            if (question.substring(i, i + 1).equals(String.valueOf(START_CODE))) {
                for (int j = i + 1; j < question.length() - 1; j++) {
                    if (question.substring(j, j + 1).equals(String.valueOf(END_CODE))) {
                        positionCodeList.add(new PositionCode(i, j + 1));
                        break;
                    }
                }
            }
        }

        question = question.replace(START_CODE, START_SHOW);
        question = question.replace(END_CODE, END_SHOW);

        SpannableString ss = new SpannableString(question);
        ClickableSpan span;
        for (int i = 0; i < positionCodeList.size(); i++) {
            final PositionCode positionCode = positionCodeList.get(i);
            final String finalQuestion = question;
            span = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    final EditText edittext = new EditText(mContext);
                    alert.setTitle("Enter Your Answer");
                    alert.setView(edittext);

                    alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String youEditTextValue = edittext.getText().toString();
                            youEditTextValue = standardizeString(youEditTextValue);
                            String temp = finalQuestion.substring(0, positionCode.start);
                            String result = temp + START_CODE + youEditTextValue + END_CODE;
                            temp = finalQuestion.substring(positionCode.end, finalQuestion.length());
                            result += temp;

                            SpannableString ss = handleClickQuestion(result, holder, position);
                            holder.tvQuestion.setText(ss);
                            holder.tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());

                            mUIList.set(position, ss);
                            notifyDataSetChanged();

                            communicateQuiz.onUserChooseAnswer(position, checkAnswerUser(holder, position));
                        }
                    });

                    alert.show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }

            };
            ss.setSpan(span, positionCode.start, positionCode.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /*
    * PARAM:    @holder: current view
    *           @position: position of current question
    * RETURN:   -999 if answer user is correct else return 0
    * */
    private int checkAnswerUser(ViewHolder holder, int position) {

        String text = holder.tvQuestion.getText().toString();
        String correctAnswer = "";
        for (AnswerByQuestion byQuestion : mAnswerByQuestionList) {
            for (Answer answer : mAnswerList) {
                if (mQuestionList.get(position).getIdQuestion() == byQuestion.getIdQuestion()
                        && byQuestion.getIdAnswer() == answer.getIdAnswer()) {
                    correctAnswer = answer.getContentAnswer();
                }
            }
        }

        String correctAnswerArr[] = correctAnswer.split(",");

        StringBuilder temp = new StringBuilder();
        int index = 0;

        for (int i = 0; i < positionCodeList.size(); i++) {
            PositionCode positionCode = positionCodeList.get(i);
            temp.append(text.substring(index, positionCode.start));

            if (!text.substring(positionCode.start+1, positionCode.end-1).toLowerCase().equals(correctAnswerArr[i].toLowerCase())) {
                return 0;
            }
            index = positionCode.end;
        }
        return -999;
    }

    private String standardizeString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    //Inner class save position each question
    private class PositionCode {
        int start;
        int end;

        PositionCode(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public String toString() {
            return "start: " + start + " - end: " + end;
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumberQuestion;
        TextView tvQuestion;
        TextView tvTextAnswer;
        TextView tvShowAnswered;

        ViewHolder(View view) {
            super(view);
            tvNumberQuestion = view.findViewById(R.id.tv_number_question);
            tvQuestion = view.findViewById(R.id.tv_question);
            tvTextAnswer = view.findViewById(R.id.tv_text_answer);
            tvShowAnswered = view.findViewById(R.id.tv_show_answered);
        }
    }
}
