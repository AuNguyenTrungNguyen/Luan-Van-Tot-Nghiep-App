package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import luanvan.luanvantotnghiep.Adapter.SortAdapter;
import luanvan.luanvantotnghiep.ControlRecycle.MyItemTouch;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

import static luanvan.luanvantotnghiep.Util.Constraint.ELECTRONEGATIVITY_ASC;
import static luanvan.luanvantotnghiep.Util.Constraint.ELECTRONEGATIVITY_DEC;
import static luanvan.luanvantotnghiep.Util.Constraint.NUMBER_ATOM_ASC;
import static luanvan.luanvantotnghiep.Util.Constraint.NUMBER_ATOM_DEC;
import static luanvan.luanvantotnghiep.Util.Constraint.OXIDATION_ASC;
import static luanvan.luanvantotnghiep.Util.Constraint.OXIDATION_DEC;
import static luanvan.luanvantotnghiep.Util.Constraint.REDUCTION_ASC;
import static luanvan.luanvantotnghiep.Util.Constraint.REDUCTION_DEC;
import static luanvan.luanvantotnghiep.Util.Constraint.WEIGHT_ASC;
import static luanvan.luanvantotnghiep.Util.Constraint.WEIGHT_DEC;

public class SortActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTime;
    private Button mBtnComplete;
    private TextView mTvQuestion;

    private RecyclerView mRvSort;
    private RecyclerView mRvAnswer;

    private CountDownTimer mCountDownTimer;
    private boolean isPlaying = false;
    private long mCurrentTime = 0;
    private int mTotalQuestion = 5;

    private SortAdapter mSortAdapter;

    private List<Object> mDataList = new ArrayList<>();
    private List<Object> mListAnswer = new ArrayList<>();
    private int type = -1;

    private ItemTouchHelper mTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        setupToolbar();

        init();

        setupGame();
    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserOut();
            }
        });
    }

    private void checkUserOut() {
        if (isPlaying) {

            final Dialog dialog = new Dialog(SortActivity.this);
            dialog.setContentView(R.layout.layout_dialog_game_submit);

            TextView tvAnswered = dialog.findViewById(R.id.tv_answered);
            TextView tvTimeLeft = dialog.findViewById(R.id.tv_time_left);
            Button btnSubmit = dialog.findViewById(R.id.btn_submit);
            Button btnContinue = dialog.findViewById(R.id.btn_continue);

            tvAnswered.setVisibility(View.GONE);

            //handel time running in dialog
            tvTimeLeft.setText("Bạn dừng lúc: " + convertLongToTime(mCurrentTime));

            //when user submit
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showScore();
                    dialog.dismiss();
                }
            });

            //when user continue game
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setCancelable(false);
            dialog.show();

        } else {
            finish();
        }
    }

    private void init() {
        mTvTime = findViewById(R.id.tv_time_sort);
        mTvQuestion = findViewById(R.id.tv_question_sort);
        mBtnComplete = findViewById(R.id.btn_complete_sort);
        mBtnComplete.setOnClickListener(this);

        mRvSort = findViewById(R.id.rv_sort);
        mRvAnswer = findViewById(R.id.rv_answer);
    }

    private static String convertLongToTime(long value) {
        return DateFormat.format("mm:ss", new Date(value)).toString();
    }

    private void setupGame() {
        isPlaying = true;

        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                mTvTime.setText(convertLongToTime(millisUntilFinished));

                //set text color red when time <= 5m
                if (millisUntilFinished <= 300000) {
                    mTvTime.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFinish() {
                showScore();
            }
        }.start();

        type = randomQuestion();
        switch (type) {
            //Chemistry
            case NUMBER_ATOM_ASC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>số nguyên tử tăng dần</b>"));
                break;

            case NUMBER_ATOM_DEC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>số nguyên tử giảm dần</b>"));
                break;

            case WEIGHT_ASC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>khối lượng tăng dần</b>"));
                break;

            case WEIGHT_DEC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>khối lượng giảm dần</b>"));
                break;

            //Element
            case ELECTRONEGATIVITY_ASC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>độ âm điện tăng dần</b>"));
                break;

            case ELECTRONEGATIVITY_DEC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các chất theo <b>độ âm điện giảm dần</b>"));
                break;

            //ReactSeries
            case OXIDATION_ASC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các ion kim loại sau theo <b>tính chất oxi hóa tăng dần</b>"));
                break;

            case OXIDATION_DEC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các ion kim loại sau theo <b>tính chất oxi hóa giảm dần</b>"));
                break;

            case REDUCTION_ASC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các kim loại sau theo <b>tính chất khử tăng dần</b>"));
                break;

            case REDUCTION_DEC:
                mTvQuestion.setText(Html.fromHtml("Hãy sắp xếp các kim loại sau theo <b>tính chất khử giảm dần</b>"));
                break;
        }
        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(this);
        List<Object> list = new ArrayList<>();

        List<Object> mFilter = new ArrayList<>();

        if (type == OXIDATION_ASC || type == OXIDATION_DEC || type == REDUCTION_ASC || type == REDUCTION_DEC) {
            list.addAll(chemistryHelper.getAllReactSeries());
            mFilter.addAll(list);
        } else {
            list.addAll(chemistryHelper.getAllChemistry());
            mFilter = new ArrayList<>(list.subList(0, 31));

            //popular elements
            List<Object> temp = new ArrayList<>();
            temp.add(list.get(35));
            temp.add(list.get(46));
            temp.add(list.get(49));
            temp.add(list.get(52));
            temp.add(list.get(56));
            temp.add(list.get(78));
            temp.add(list.get(79));
            temp.add(list.get(81));
            mFilter.addAll(temp);
        }

        if (mFilter.size() > 0) {
            Collections.shuffle(mFilter);
            Collections.shuffle(mFilter);
            Collections.shuffle(mFilter);
            mDataList = new ArrayList<>(mFilter.subList(0, 5));
        }

        mSortAdapter = new SortAdapter(this, mDataList, type);

        ItemTouchHelper.Callback callback =
                new MyItemTouch(mSortAdapter);
        mTouchHelper = new ItemTouchHelper(callback);
        mTouchHelper.attachToRecyclerView(mRvSort);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvSort.setLayoutManager(layoutManager);
        mRvSort.setHasFixedSize(true);
        mRvSort.setAdapter(mSortAdapter);
    }

    private int randomQuestion() {
        Random random = new Random();
        int n = random.nextInt(500) + 1;
        return n % 10;
        //return WEIGHT_ASC;
    }

    private Comparator getComparatorByType(final int type) {
        Comparator comparator = null;
        switch (type) {
            //Chemistry
            case NUMBER_ATOM_ASC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Integer id1 = o.getIdChemistry();
                        Integer id2 = t1.getIdChemistry();
                        return id1.compareTo(id2);
                    }
                };
                break;

            case NUMBER_ATOM_DEC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Integer id1 = o.getIdChemistry();
                        Integer id2 = t1.getIdChemistry();
                        return id2.compareTo(id1);
                    }
                };
                break;

            case WEIGHT_ASC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Double id1 = o.getWeightChemistry();
                        Double id2 = t1.getWeightChemistry();
                        return id1.compareTo(id2);
                    }
                };
                break;

            case WEIGHT_DEC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Double id1 = o.getWeightChemistry();
                        Double id2 = t1.getWeightChemistry();
                        return id2.compareTo(id1);
                    }
                };
                break;

            //Element
            case ELECTRONEGATIVITY_ASC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Element element1 = getElementById(o.getIdChemistry());
                        Element element2 = getElementById(t1.getIdChemistry());
                        Double eletron1 = element1.getElectronegativity();
                        Double eletron2 = element2.getElectronegativity();
                        return eletron1.compareTo(eletron2);
                    }
                };
                break;

            case ELECTRONEGATIVITY_DEC:
                comparator = new Comparator<Chemistry>() {
                    @Override
                    public int compare(Chemistry o, Chemistry t1) {
                        Element element1 = getElementById(o.getIdChemistry());
                        Element element2 = getElementById(t1.getIdChemistry());
                        Double eletron1 = element1.getElectronegativity();
                        Double eletron2 = element2.getElectronegativity();
                        return eletron2.compareTo(eletron1);
                    }
                };
                break;

            //ReactSeries
            case OXIDATION_ASC:
                comparator = new Comparator<ReactSeries>() {
                    @Override
                    public int compare(ReactSeries o, ReactSeries t1) {

                        Integer id1 = o.getIdReactSeries();
                        Integer id2 = t1.getIdReactSeries();
                        return id1.compareTo(id2);
                    }
                };
                break;

            case OXIDATION_DEC:
                comparator = new Comparator<ReactSeries>() {
                    @Override
                    public int compare(ReactSeries o, ReactSeries t1) {
                        Integer id1 = o.getIdReactSeries();
                        Integer id2 = t1.getIdReactSeries();
                        return id2.compareTo(id1);
                    }
                };
                break;

            case REDUCTION_ASC:
                comparator = new Comparator<ReactSeries>() {
                    @Override
                    public int compare(ReactSeries o, ReactSeries t1) {
                        Integer id1 = o.getIdReactSeries();
                        Integer id2 = t1.getIdReactSeries();
                        return id2.compareTo(id1);
                    }
                };
                break;

            case REDUCTION_DEC:
                comparator = new Comparator<ReactSeries>() {
                    @Override
                    public int compare(ReactSeries o, ReactSeries t1) {
                        Integer id1 = o.getIdReactSeries();
                        Integer id2 = t1.getIdReactSeries();
                        return id1.compareTo(id2);
                    }
                };
                break;
        }
        return comparator;
    }

    private Element getElementById(int id) {
        Element element = null;
        ChemistryHelper helper = ChemistrySingle.getInstance(this);
        List<Element> list = helper.getAllElements();

        for (Element item : list) {
            if (item.getIdElement() == id) {
                return item;
            }
        }

        return element;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_complete_sort:
                checkUserOut();
                break;
        }
    }

    private void showScore() {
        int score = 0;

        mListAnswer.addAll(mDataList);
        Collections.sort(mListAnswer, getComparatorByType(type));

        if (type == NUMBER_ATOM_ASC || type == NUMBER_ATOM_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((Chemistry) data).getIdChemistry() == ((Chemistry) answer).getIdChemistry()) {
                    score++;
                }
            }
        } else if (type == WEIGHT_ASC || type == WEIGHT_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((Chemistry) data).getWeightChemistry() == ((Chemistry) answer).getWeightChemistry()) {
                    score++;
                }
            }
        } else if (type == ELECTRONEGATIVITY_ASC || type == ELECTRONEGATIVITY_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                Element elementData = getElementById(((Chemistry) data).getIdChemistry());
                Element elementAnswer = getElementById(((Chemistry) answer).getIdChemistry());
                if (elementData.getElectronegativity() == elementAnswer.getElectronegativity()) {
                    score++;
                }
            }
        } else {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((ReactSeries) data).getIdReactSeries() == ((ReactSeries) answer).getIdReactSeries()) {
                    score++;
                }
            }
        }

        isPlaying = false;
        mBtnComplete.setVisibility(View.INVISIBLE);
        mCountDownTimer.cancel();

        final Dialog dialog = new Dialog(SortActivity.this);
        dialog.setContentView(R.layout.layout_dialog_score_quiz);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvLevel = dialog.findViewById(R.id.tv_level);
        TextView tvScore = dialog.findViewById(R.id.tv_score);
        TextView tvCorrectAnswer = dialog.findViewById(R.id.tv_correct_answer);
        ImageView imgReview = dialog.findViewById(R.id.img_review);
        ImageView imgFinish = dialog.findViewById(R.id.img_finish);

        //Handel stat by score
        ImageView imgStarOne = dialog.findViewById(R.id.img_star_one);
        ImageView imgStarTwo = dialog.findViewById(R.id.img_star_two);
        ImageView imgStarThree = dialog.findViewById(R.id.img_star_three);

        if (score >= 1 && score < 2) {
            imgStarTwo.setVisibility(View.VISIBLE);
        } else if (score >= 2 && score < 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        } else if (score >= 5) {
            imgStarOne.setVisibility(View.VISIBLE);
            imgStarTwo.setVisibility(View.VISIBLE);
            imgStarThree.setVisibility(View.VISIBLE);
        }

        //tvLevel.setText("Text level");
        tvScore.setText(String.valueOf(score));
        tvCorrectAnswer.setText(String.format("%s/%s", score, mTotalQuestion));
        dialog.setCancelable(false);
        dialog.show();

        imgReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reviewQuiz();
            }
        });

        imgFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (score >= 0) {
            imgReview.setVisibility(View.VISIBLE);
        } else {
            imgReview.setVisibility(View.INVISIBLE);
        }
    }

    private void reviewQuiz() {
        mBtnComplete.setEnabled(false);

        mRvAnswer.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = mRvSort.getLayoutParams();
        params.width = 0;
        mRvSort.setLayoutParams(params);

        //sort
        mListAnswer.clear();
        mListAnswer.addAll(mDataList);
        Collections.sort(mListAnswer, getComparatorByType(type));

        if (type == NUMBER_ATOM_ASC || type == NUMBER_ATOM_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((Chemistry) data).getIdChemistry() == ((Chemistry) answer).getIdChemistry()) {
                    mSortAdapter.setUI(i, 0);
                }
            }
        } else if (type == WEIGHT_ASC || type == WEIGHT_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((Chemistry) data).getWeightChemistry() == ((Chemistry) answer).getWeightChemistry()) {
                    mSortAdapter.setUI(i, 0);
                }
            }
        } else if (type == ELECTRONEGATIVITY_ASC || type == ELECTRONEGATIVITY_DEC) {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                Element elementData = getElementById(((Chemistry) data).getIdChemistry());
                Element elementAnswer = getElementById(((Chemistry) answer).getIdChemistry());
                if (elementData.getElectronegativity() == elementAnswer.getElectronegativity()) {
                    mSortAdapter.setUI(i, 0);
                }
            }
        } else {
            for (int i = 0; i < mDataList.size(); i++) {
                mSortAdapter.setUI(i, 1);
                Object data = mDataList.get(i);
                Object answer = mListAnswer.get(i);
                if (((ReactSeries) data).getIdReactSeries() == ((ReactSeries) answer).getIdReactSeries()) {
                    mSortAdapter.setUI(i, 0);
                }
            }
        }

        mSortAdapter.notifyDataSetChanged();
        mTouchHelper.attachToRecyclerView(null);

        //set data mRvAnswer
        SortAdapter adapter = new SortAdapter(SortActivity.this, mListAnswer, type);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SortActivity.this);
        mRvAnswer.setLayoutManager(layoutManager);
        mRvAnswer.setHasFixedSize(true);
        mRvAnswer.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        checkUserOut();
    }
}
