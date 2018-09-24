package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import luanvan.luanvantotnghiep.Util.Constraint;

public class FillInTheBlankAdapter extends RecyclerView.Adapter<FillInTheBlankAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mQuestionList;
    private List<Answer> mAnswerList;
    private List<AnswerByQuestion> mAnswerByQuestionList;
    private List<SpannableString> mUIList = new ArrayList<>();

    private static final Character START_CODE = '&';
    private static final Character END_CODE = '|';
    private static final Character START_SHOW = '{';
    private static final Character END_SHOW = '}';
    private List<PositionCode> positionCodeList = new ArrayList<>();

    private static final String TAG = Constraint.TAG;

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

        SpannableString spannableString = handleClickQuestion(question.getContentQuestion(), holder, position);

        holder.tvQuestion.setText(spannableString);
        holder.tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());

        if (mUIList.get(position) != null) {
            holder.tvQuestion.setText(mUIList.get(position));
            holder.tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private SpannableString handleClickQuestion(String question, final ViewHolder holder, final int position) {

        question = question.replace(START_SHOW, START_CODE);
        question = question.replace(END_SHOW, END_CODE);

        positionCodeList.clear();
        for (int i = 0; i < question.length() - 1; i++) {
            if (question.substring(i, i + 1).equals("&")) {
                for (int j = i + 1; j < question.length() - 1; j++) {
                    if (question.substring(j, j + 1).equals("|")) {
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
                            String result = temp + "&" + youEditTextValue + "|";
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

    private int checkAnswerUser(ViewHolder holder, int position) {

        String text = holder.tvQuestion.getText().toString();

        String dapAn = "";
        for (AnswerByQuestion byQuestion : mAnswerByQuestionList) {
            for (Answer answer : mAnswerList) {
                if (mQuestionList.get(position).getIdQuestion() == byQuestion.getIdQuestion()
                        && byQuestion.getIdAnswer() == answer.getIdAnswer()) {
                    dapAn = answer.getContentAnswer();
                }
            }
        }

        String strDapAn[] = dapAn.split(",");

        StringBuilder temp = new StringBuilder();
        int index = 0;

        for (int i = 0; i < positionCodeList.size(); i++) {
            PositionCode positionCode = positionCodeList.get(i);
            temp.append(text.substring(index, positionCode.start));

            if (!text.substring(positionCode.start, positionCode.end).toLowerCase().equals(strDapAn[i].toLowerCase())) {
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

    private class PositionCode {
        int start;
        int end;

        PositionCode(int s, int e) {
            start = s;
            end = e;
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;

        ViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tv_question);
        }
    }

    //    private void setRadio(ViewHolder holder, int selection) {
//
//        RadioGroup rg = holder.radioGroup;
//        RadioButton b1 = holder.rbAnswerA;
//        RadioButton b2 = holder.rbAnswerB;
//        RadioButton b3 = holder.rbAnswerC;
//        RadioButton b4 = holder.rbAnswerD;
//
//        if (selection == 0) {
//            b1.setChecked(true);
//        } else if (selection == 1) {
//            b2.setChecked(true);
//        } else if (selection == 2) {
//            b3.setChecked(true);
//        } else if (selection == 3) {
//            b4.setChecked(true);
//        } else if (selection == -1) {
//            rg.clearCheck();
//        }
//    }
//
//    private void setColorAnswer(ViewHolder holder, int correct, int user) {
//
//        RadioButton b1 = holder.rbAnswerA;
//        RadioButton b2 = holder.rbAnswerB;
//        RadioButton b3 = holder.rbAnswerC;
//        RadioButton b4 = holder.rbAnswerD;
//
//        b1.setBackgroundColor(Color.WHITE);
//        b2.setBackgroundColor(Color.WHITE);
//        b3.setBackgroundColor(Color.WHITE);
//        b4.setBackgroundColor(Color.WHITE);
//
//        b1.setEnabled(false);
//        b2.setEnabled(false);
//        b3.setEnabled(false);
//        b4.setEnabled(false);
//
//        if (user == 0) {
//            b1.setEnabled(true);
//        } else if (user == 1) {
//            b2.setEnabled(true);
//        } else if (user == 2) {
//            b3.setEnabled(true);
//        } else if (user == 3) {
//            b4.setEnabled(true);
//        }
//
//        if (correct == 0) {
//            b1.setBackgroundColor(Color.CYAN);
//        } else if (correct == 1) {
//            b2.setBackgroundColor(Color.CYAN);
//        } else if (correct == 2) {
//            b3.setBackgroundColor(Color.CYAN);
//        } else if (correct == 3) {
//            b4.setBackgroundColor(Color.CYAN);
//        }
//
//        if (user == correct){
//            if (correct == 0) {
//                b1.setEnabled(true);
//            } else if (correct == 1) {
//                b2.setEnabled(true);
//            } else if (correct == 2) {
//                b3.setEnabled(true);
//            } else if (correct == 3) {
//                b4.setEnabled(true);
//            }
//        }
//    }
}
