package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import luanvan.luanvantotnghiep.CustomTree.Tree;
import luanvan.luanvantotnghiep.CustomTree.TreeAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.Model.TypeOfQuestion;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class ChooseLevelActivity extends AppCompatActivity {

    private ChemistryHelper mHelper;

    private RecyclerView mRvTree;
    private List<Tree> mList;
    private TreeAdapter mAdapter;
    private TextView mTvExtent;
    private List<TypeOfQuestion> mListTypeOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        PreferencesManager.getInstance().init(this);

        init();

        setupToolbar();

        //Get TypeOfGame from Fragment
        int type = getType();

        //Save Preference
        saveType(type);

        //Get all levels with type exit
        if (type != 4) {
            setupLevel(type, 1);
            saveExtent(1);
        } else {
            mRvTree.setVisibility(View.GONE);
            findViewById(R.id.ln_description_sort).setVisibility(View.VISIBLE);
            TextView tvDes = findViewById(R.id.tv_des);
            //tvDes.setText(Html.fromHtml(mListTypeOfQuestion.get(getType() - 1).getDescription()));
            tvDes.setText(Html.fromHtml("&#x2714 Dụng cụ chuẩn bị bao gồm: <b>bút, giấy nháp, máy tính,...</b><br><br>" +
                    "&#x2714 Tắt các thông báo của các ứng dụng khác trên điện thoại.<br><br>" +
                    "&#x2714 Căn thời gian hợp lý theo đồng hồ đếm giờ của trò chơi.<br><br>" +
                    "&#x2714 Ngồi đúng vị trí học tập của bạn."));
            findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            findViewById(R.id.btn_play_game).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ChooseLevelActivity.this, SortActivity.class));
                }
            });
        }

    }

    private void init() {

        mHelper = ChemistrySingle.getInstance(this);

        mListTypeOfQuestion = mHelper.getAllTypeOfQuestion();

        mRvTree = findViewById(R.id.rv_tree);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        mRvTree.setLayoutManager(manager);
        mRvTree.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new TreeAdapter(this, mList);
        mRvTree.setAdapter(mAdapter);

        mTvExtent = findViewById(R.id.tv_extent_choose_level);
    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupLevel(int type, int extent) {
        saveExtent(extent);
        mList.clear();
        List<Question> list = mHelper.getAllQuestion();

        List<Integer> temp = new ArrayList<>();
        for (Question question : list) {
            if (question.getIdType() == type && question.getExtent() == extent)
                temp.add(question.getIdLevel());
        }

        List<Integer> listLevel = new ArrayList<>(new HashSet<>(temp));
        Collections.sort(listLevel);

        for (int i = 0; i < listLevel.size(); i++) {
            boolean isLeft = listLevel.get(i) % 2 == 0;
            mList.add(new Tree(isLeft, "Level: " + listLevel.get(i), listLevel.get(i)));
        }

        mAdapter.notifyDataSetChanged();
    }

    private int getType() {
        int type = 0;
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("TYPE", 0);
        }
        return type;
    }

    private void saveType(int type) {
        PreferencesManager.getInstance().saveIntData(Constraint.PRE_KEY_TYPE, type);
    }

    private void saveExtent(int extent) {
        PreferencesManager.getInstance().saveIntData(Constraint.PRE_KEY_EXTENT, extent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_extent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_extent_easy:
                setupLevel(getType(), Constraint.EXTENT_EASY);
                mTvExtent.setText("Mức độ: Dễ");
                break;

            case R.id.mn_extent_normal:
                setupLevel(getType(), Constraint.EXTENT_NORMAL);
                mTvExtent.setText("Mức độ: Trung bình");
                break;

            case R.id.mn_extent_difficult:
                setupLevel(getType(), Constraint.EXTENT_DIFFICULT);
                mTvExtent.setText("Mức độ: Khó");
                break;

            case R.id.mn_description:
                showDialogDescription();
                break;
        }
        return true;
    }

    private void showDialogDescription() {
        TypeOfQuestion typeOfQuestion = mListTypeOfQuestion.get(getType() - 1);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_description);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvShow = dialog.findViewById(R.id.tv_content);
        tvShow.setVisibility(View.VISIBLE);
        tvShow.setText(Html.fromHtml(typeOfQuestion.getDescription()));
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
