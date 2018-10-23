package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Adapter.ExpandAdapter;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.Model.Description;
import luanvan.luanvantotnghiep.Model.DescriptionOfChapter;
import luanvan.luanvantotnghiep.Model.DescriptionOfHeading;
import luanvan.luanvantotnghiep.Model.DescriptionOfTitle;
import luanvan.luanvantotnghiep.Model.Heading;
import luanvan.luanvantotnghiep.Model.Title;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;

public class ShowTheoryActivity extends AppCompatActivity {

    private ExpandableListView mExpandThematic;
    private TextView mTvNameChapter;

    private static final String TAG = Constraint.TAG + "ShowTheory";
    private static final int TYPE_CHAPTER = 1;
    private static final int TYPE_HEADING = 2;
    private static final int TYPE_TITLE = 3;

    private List<Description> mDescriptionList;
    private ChemistryHelper mChemistryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_theory);

        setupToolbar();

        init();

        if (getIntent() != null) {
            /*Set name chapter*/
            Bundle bundle = getIntent().getExtras();
            final Chapter chapter = (Chapter) bundle.getSerializable("CHAPTER");
            mTvNameChapter.setText(chapter.getNameChapter());
            mTvNameChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDescription(chapter.getIdChapter(), TYPE_CHAPTER);
                }
            });

            /*Get database*/
            mChemistryHelper = ChemistrySingle.getInstance(this);
            List<Heading> headingList = mChemistryHelper.getAllHeading();
            List<Title> titleList = mChemistryHelper.getAllTitle();
            mDescriptionList = mChemistryHelper.getAllDescription();

            Log.i(TAG, "headingList: " + headingList.size());
            Log.i(TAG, "titleList: " + titleList.size());
            Log.i(TAG, "descriptionList: " + mDescriptionList.size());

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

            /*Map title and sort by sortOrder*/
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
                    showDescription(showHeading.get(groupPosition).getIdHeading(), TYPE_HEADING);
                    return true;
                }
            });

            //Handle onclick child item
            mExpandThematic.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Log.i(TAG, "onChildClick: " + showTitle.get(showHeading.get(i)).get(i1).getNameTitle());
                    showDescription(showTitle.get(showHeading.get(i)).get(i1).getIdTitle(), TYPE_TITLE);
                    return false;
                }
            });
        }
    }

    private void showDescription(String id, int type) {
        Log.i(TAG, "id: " + id);
        Log.i(TAG, "type: " + type);

        List<Description> listResult = new ArrayList<>();

        switch (type) {
            case TYPE_CHAPTER:
                List<DescriptionOfChapter> list = mChemistryHelper.getAllDescriptionOfChapter();
                for (DescriptionOfChapter descriptionOfChapter : list) {
                    if (descriptionOfChapter.getIdChapter().equals(id)) {
                        for (Description description : mDescriptionList) {
                            if (description.getIdDescription().equals(descriptionOfChapter.getIdDescription())) {
                                listResult.add(description);
                                break;
                            }
                        }
                    }
                }
                break;
            case TYPE_HEADING:
                List<DescriptionOfHeading> listDesOfHead = mChemistryHelper.getAllDescriptionOfHeading();
                for (DescriptionOfHeading descriptionOfHeading : listDesOfHead) {
                    if (descriptionOfHeading.getIdHeading().equals(id)) {
                        for (Description description : mDescriptionList) {
                            if (description.getIdDescription().equals(descriptionOfHeading.getIdDescription())) {
                                listResult.add(description);
                                break;
                            }
                        }
                    }
                }
                break;

            case TYPE_TITLE:
                List<DescriptionOfTitle> listDesOfTitle = mChemistryHelper.getAllDescriptionOfTitle();
                for (DescriptionOfTitle descriptionOfTitle : listDesOfTitle) {
                    if (descriptionOfTitle.getIdTitle().equals(id)) {
                        for (Description description : mDescriptionList) {
                            if (description.getIdDescription().equals(descriptionOfTitle.getIdDescription())) {
                                listResult.add(description);
                                break;
                            }
                        }
                    }
                }
                break;
        }

        List<Description> listContent = new ArrayList<>();
        List<Description> listExample = new ArrayList<>();
        List<Description> listQuestion = new ArrayList<>();

        //Sort by type
        Collections.sort(listResult, new Comparator<Description>() {
            public int compare(Description o1, Description o2) {
                return Integer.parseInt(o1.getIdTypeOfDescription()) - Integer.parseInt(o2.getIdTypeOfDescription());
            }
        });

        //Filter list
        for (Description description : listResult) {
            String typeDes = description.getIdTypeOfDescription();
            switch (typeDes) {
                case "1":
                    listContent.add(description);
                    break;

                case "2":
                    listExample.add(description);
                    break;

                case "3":
                    listQuestion.add(description);
                    break;
            }
        }

        //Sort by order
        Collections.sort(listContent, new Comparator<Description>() {
            public int compare(Description o1, Description o2) {
                return Integer.parseInt(o1.getSortOrder()) - Integer.parseInt(o2.getSortOrder());
            }
        });
        Collections.sort(listExample, new Comparator<Description>() {
            public int compare(Description o1, Description o2) {
                return Integer.parseInt(o1.getSortOrder()) - Integer.parseInt(o2.getSortOrder());
            }
        });
        Collections.sort(listQuestion, new Comparator<Description>() {
            public int compare(Description o1, Description o2) {
                return Integer.parseInt(o1.getSortOrder()) - Integer.parseInt(o2.getSortOrder());
            }
        });

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_description);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        TextView tvExample = dialog.findViewById(R.id.tv_example);
        TextView tvQuestion = dialog.findViewById(R.id.tv_question);

        if (listContent.size() > 0) {
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < listContent.size(); i++) {
                content.append(listContent.get(i).getNameDescription());
                if (i < listContent.size() - 1) {
                    content.append("<br><br>");
                }
            }
            tvContent.setText(Html.fromHtml(content.toString()));
            tvContent.setVisibility(View.VISIBLE);

        }

        if (listExample.size() > 0) {
            StringBuilder example = new StringBuilder();
            for (int i = 0; i < listExample.size(); i++) {
                example.append(listExample.get(i).getNameDescription());
                if (i < listExample.size() - 1) {
                    example.append("<br><br>");
                }
            }
            tvExample.setText(Html.fromHtml(example.toString()));
            tvExample.setVisibility(View.VISIBLE);

        }

        if (listQuestion.size() > 0) {
            StringBuilder question = new StringBuilder();
            for (int i = 0; i < listQuestion.size(); i++) {
                question.append(listQuestion.get(i).getNameDescription());
                if (i < listExample.size() - 1) {
                    question.append("<br><br>");
                }
            }
            tvQuestion.setText(Html.fromHtml(question.toString()));
            tvQuestion.setVisibility(View.VISIBLE);
        }

        if (listResult.size() > 0){
            dialog.show();
        }else{
            Toast.makeText(this, "Hiện tại chưa có nội dung!", Toast.LENGTH_SHORT).show();
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
