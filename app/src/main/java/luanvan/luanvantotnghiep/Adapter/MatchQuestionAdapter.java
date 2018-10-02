package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Communicate.MatchGame;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.R;

public class MatchQuestionAdapter extends RecyclerView.Adapter<MatchQuestionAdapter.QuestionHolder> {

    private Context mContext;
    private List<Question> mQuestionList;
    private HashMap<String, Integer> mMap;

    public MatchQuestionAdapter(Context mContext, List<Question> mListData, HashMap<String, Integer> map) {
        this.mContext = mContext;
        this.mQuestionList = mListData;
        this.mMap = map;
    }

    private MatchGame mMatchGame;
    public void setMatchGame(MatchGame matchGame){
        this.mMatchGame = matchGame;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_question, viewGroup, false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionHolder questionHolder, final int i) {
        Question question = mQuestionList.get(i);
        questionHolder.tvContent.setText(question.getContentQuestion());
        questionHolder.tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(questionHolder, i);
            }
        });
    }

    private void showDialog(final QuestionHolder questionHolder, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Choose Your Answer");

        final String data[] = new String[mMap.size()];
        for (int i = 0;  i < mMap.size(); i++){
            data[i] = String.valueOf((char) (i+65));
        }
        builder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                questionHolder.tvChoose.setText(data[i]);
                mMatchGame.userClick(position, mMap.get(data[i]));
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

        QuestionHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content_question_match);
            tvChoose = itemView.findViewById(R.id.tv_user_choose);
        }
    }
}
