package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import luanvan.luanvantotnghiep.Activity.FillInTheBlankActivity;
import luanvan.luanvantotnghiep.Activity.MatchSentencesActivity;
import luanvan.luanvantotnghiep.Activity.QuizActivity;
import luanvan.luanvantotnghiep.Activity.SortActivity;
import luanvan.luanvantotnghiep.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.cv_quiz).setOnClickListener(this);
        view.findViewById(R.id.cv_fill_in_the_blank).setOnClickListener(this);
        view.findViewById(R.id.cv_match_sentences).setOnClickListener(this);
        view.findViewById(R.id.cv_sort).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_quiz:
                mContext.startActivity(new Intent(mContext, QuizActivity.class));
                break;

            case R.id.cv_fill_in_the_blank:
                mContext.startActivity(new Intent(mContext, FillInTheBlankActivity.class));
                break;

            case R.id.cv_match_sentences:
                mContext.startActivity(new Intent(mContext, MatchSentencesActivity.class));
                break;

            case R.id.cv_sort:
                mContext.startActivity(new Intent(mContext, SortActivity.class));
                break;
        }
    }
}
