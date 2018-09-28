package luanvan.luanvantotnghiep.Activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class SortActivity extends AppCompatActivity {
    private TextView mTvTime;
    private Button mBtnComplete;
    private TextView mTvQuestion;

    private RecyclerView mRvSort;
    private RecyclerView mRvAnswer;

    private CountDownTimer mCountDownTimer;

    private SortAdapter mSortAdapter;

    private List<Object> mDataList = new ArrayList<>();
    private List<Object> mListAnswer = new ArrayList<>();
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        init();

        setupGame();

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //init
                mBtnComplete.setEnabled(false);
                int score = 0;
                mRvAnswer.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = mRvSort.getLayoutParams();
                params.width = 0;
                mRvSort.setLayoutParams(params);

                //sort
                mListAnswer.addAll(mDataList);
                Collections.sort(mListAnswer, getComparatorByType(type));

                if (type == NUMBER_ATOM_ASC || type == NUMBER_ATOM_DEC) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        mSortAdapter.setUI(i, 1);
                        Object data = mDataList.get(i);
                        Object answer = mListAnswer.get(i);
                        if (((Chemistry) data).getIdChemistry() == ((Chemistry) answer).getIdChemistry()) {
                            score++;
                            mSortAdapter.setUI(i, 0);
                        }
                    }
                } else if (type == WEIGHT_ASC || type == WEIGHT_DEC) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        mSortAdapter.setUI(i, 1);
                        Object data = mDataList.get(i);
                        Object answer = mListAnswer.get(i);
                        if (((Chemistry) data).getWeightChemistry() == ((Chemistry) answer).getWeightChemistry()) {
                            score++;
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
                            score++;
                            mSortAdapter.setUI(i, 0);
                        }
                    }
                } else {
                    for (int i = 0; i < mDataList.size(); i++) {
                        mSortAdapter.setUI(i, 1);
                        Object data = mDataList.get(i);
                        Object answer = mListAnswer.get(i);
                        if (((ReactSeries) data).getIdReactSeries() == ((ReactSeries) answer).getIdReactSeries()) {
                            score++;
                            mSortAdapter.setUI(i, 0);
                        }
                    }
                }

                mSortAdapter.setNoMove();
                mSortAdapter.notifyDataSetChanged();

                //set data mRvAnswer
                SortAdapter adapter = new SortAdapter(SortActivity.this, mListAnswer, type);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SortActivity.this);
                mRvAnswer.setLayoutManager(layoutManager);
                mRvAnswer.setHasFixedSize(true);
                mRvAnswer.setAdapter(adapter);
            }
        });
    }

    private void init() {
        mTvTime = findViewById(R.id.tv_time_sort);
        mTvQuestion = findViewById(R.id.tv_question_sort);
        mBtnComplete = findViewById(R.id.btn_complete_sort);

        mRvSort = findViewById(R.id.rv_sort);
        mRvAnswer = findViewById(R.id.rv_answer);
    }

    private void setupGame() {

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
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvSort);

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
}
