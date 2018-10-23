package luanvan.luanvantotnghiep.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Communicate.MatchGame;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class MatchQuestionAdapter extends RecyclerView.Adapter<MatchQuestionAdapter.QuestionHolder> {

    private Context mContext;
    private List<Question> mQuestionList;
    private List<Answer> mListAnswer;
    private List<AnswerByQuestion> mAnswerByQuestionList = new ArrayList<>();
    private List<String> mListUserAnswer;
    private ChemistryHelper mChemistryHelper;

    public MatchQuestionAdapter(Context mContext, List<Question> mListData, List<Answer> mListHandle, List<String> mListUserAnswer) {
        this.mContext = mContext;
        this.mQuestionList = mListData;
        this.mListAnswer = mListHandle;
        this.mListUserAnswer = mListUserAnswer;
        mChemistryHelper = ChemistrySingle.getInstance(mContext);
    }

    private MatchGame mMatchGame;

    public void setMatchGame(MatchGame matchGame) {
        this.mMatchGame = matchGame;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_question, viewGroup, false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionHolder questionHolder, @SuppressLint("RecyclerView") final int position) {
        Question question = mQuestionList.get(position);
        questionHolder.tvContent.setText(question.getContentQuestion());
        questionHolder.lnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(questionHolder, position);
            }
        });

        //Review
        if (question.getAnswer() != -1) {
            questionHolder.lnChoose.setEnabled(false);
            questionHolder.tvReviewAnswer.setVisibility(View.VISIBLE);

            mAnswerByQuestionList.addAll(mChemistryHelper.getAllAnswerByQuestion());
            // correct answer
            String review = "";
            for (int j = 0; j < mQuestionList.size(); j++) {
                for (AnswerByQuestion answerByQuestion : mAnswerByQuestionList) {
                    if (answerByQuestion.getIdQuestion().equals(question.getIdQuestion())
                            && answerByQuestion.getIdAnswer().equals(mListAnswer.get(j).getIdAnswer())) {
                        review = mListAnswer.get(j).getIdAnswer();
                        break;
                    }
                }
            }
            for (int j = 0; j < mListAnswer.size(); j++) {
                if (mListAnswer.get(j).getIdAnswer() == review) {
                    int show = j + 65;
                    questionHolder.tvReviewAnswer.setText(String.valueOf((char) show));
                }
            }

            // user choose
            for (int i = 0; i < mListAnswer.size(); i++) {
                if (mListUserAnswer.get(position) == mListAnswer.get(i).getIdAnswer()) {
                    int show = i + 65;
                    questionHolder.tvChoose.setText(String.valueOf((char) show));
                    break;
                }else {
                    questionHolder.tvChoose.setText("X");
                }
            }

            if (mListUserAnswer.get(position) == review) {
                questionHolder.tvChoose.setBackgroundColor(Color.CYAN);
            } else {
                questionHolder.tvChoose.setBackgroundColor(Color.RED);
            }
        }
    }

    private void showDialog(final QuestionHolder questionHolder, final int position) {
        Question question = mQuestionList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(question.getContentQuestion());

        final String data[] = new String[mListAnswer.size()];
        for (int i = 0; i < mListAnswer.size(); i++) {
            int show = i + 65;
            data[i] = String.valueOf((char) show) + ". " + mListAnswer.get(i).getContentAnswer();
        }
        builder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                questionHolder.tvChoose.setText(data[i].split(". ")[0]);
                mMatchGame.userClick(position, mListAnswer.get(i).getIdAnswer());
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    static class QuestionHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvChoose;
        TextView tvReviewAnswer;
        LinearLayout lnChoose;

        QuestionHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content_question_match);
            tvChoose = itemView.findViewById(R.id.tv_user_choose);
            tvChoose.setText("?");
            tvReviewAnswer = itemView.findViewById(R.id.tv_review_answer);
            lnChoose = itemView.findViewById(R.id.ln_question_match);
        }
    }
}
