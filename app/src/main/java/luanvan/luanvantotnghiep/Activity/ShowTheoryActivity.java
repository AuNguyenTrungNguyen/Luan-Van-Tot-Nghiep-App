package luanvan.luanvantotnghiep.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ExpandAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.Model.Description;
import luanvan.luanvantotnghiep.Model.Heading;
import luanvan.luanvantotnghiep.Model.Title;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;

public class ShowTheoryActivity extends AppCompatActivity {

    private ExpandableListView mExpandThematic;
    private TextView mTvNameChapter;

    private static final String TAG = Constraint.TAG + "ShowTheory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_theory);

        setupToolbar();

        init();

        if (getIntent() != null) {
            /*Set name chapter*/
            Bundle bundle = getIntent().getExtras();
            Chapter chapter = (Chapter) bundle.getSerializable("CHAPTER");
            mTvNameChapter.setText(chapter.getNameChapter());

            /*Get database*/
            ChemistryHelper mChemistryHelper = ChemistrySingle.getInstance(this);
            List<Heading> headingList = mChemistryHelper.getAllHeading();
            List<Title> titleList = mChemistryHelper.getAllTitle();
            List<Description> descriptionList = mChemistryHelper.getAllDescription();

            Log.i(TAG, "headingList: " + headingList.size());
            Log.i(TAG, "titleList: " + titleList.size());
            Log.i(TAG, "descriptionList: " + descriptionList.size());

            /*Prepare expand*/
            String idChap = chapter.getIdChapter();
            final List<Heading> showHeading = new ArrayList<>();
            final HashMap<Heading, List<Title>> showTitle = new HashMap<>();

            /*List heading and sort by sortOrder*/
            for (Heading heading : headingList) {
                if (heading.getIdChapter().equals(idChap)) {
                    showHeading.add(heading);
                }
            }
            Collections.sort(showHeading, new Comparator<Heading>() {
                public int compare(Heading o1, Heading o2) {
                    return Integer.parseInt(o1.getSortOrder()) - Integer.parseInt(o2.getSortOrder());
                }
            });

            /*Map title and sort bu sortOrder*/
            for (Heading heading : showHeading) {
                String idHead = heading.getIdHeading();
                List<Title> list = new ArrayList<>();
                for (Title title : titleList) {
                    if (title.getIdHeading().equals(idHead)) {
                        list.add(title);
                    }
                }
                Collections.sort(list, new Comparator<Title>() {
                    public int compare(Title o1, Title o2) {
                        return Integer.parseInt(o1.getSortOrder()) - Integer.parseInt(o2.getSortOrder());
                    }
                });
                showTitle.put(heading, list);
            }

            Log.i(TAG, "showHeading: " + showHeading.size());
            Log.i(TAG, "showTitle: " + showTitle.size());

            /*Show expand*/
            ExpandAdapter mAdapter = new ExpandAdapter(this, showHeading, showTitle);
            mExpandThematic.setAdapter(mAdapter);

            //Expand all
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                mExpandThematic.expandGroup(i);
            }

            //Disable group close and handle onclick group item
            mExpandThematic.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    Log.i(TAG, "onGroupClick: " + showHeading.get(groupPosition).getNameHeading());
                    return true;
                }
            });

            //Handle onclick child item
            mExpandThematic.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Log.i(TAG, "onChildClick: " + showTitle.get(showHeading.get(i)).get(i1).getNameTitle());
                    return false;
                }
            });

        }
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

    private void init() {
        mTvNameChapter = findViewById(R.id.tv_name_chapter);
        mExpandThematic = findViewById(R.id.elv_thematic);
    }
}
