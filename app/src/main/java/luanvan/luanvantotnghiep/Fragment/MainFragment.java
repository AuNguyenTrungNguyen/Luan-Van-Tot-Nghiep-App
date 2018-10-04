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

import luanvan.luanvantotnghiep.Activity.ChooseLevelActivity;
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

        Intent intent = new Intent(mContext, ChooseLevelActivity.class);
        switch (view.getId()) {
            case R.id.cv_quiz:
                intent.putExtra("TYPE", 1);
                mContext.startActivity(intent);
                break;

            case R.id.cv_fill_in_the_blank:
                intent.putExtra("TYPE", 2);
                mContext.startActivity(intent);
                break;

            case R.id.cv_match_sentences:
                intent.putExtra("TYPE", 3);
                mContext.startActivity(intent);
                break;

            case R.id.cv_sort:
//                intent.putExtra("TYPE", 4);
//                mContext.startActivity(intent);
                startActivity(new Intent(mContext, SortActivity.class));
                break;
        }
    }
}
