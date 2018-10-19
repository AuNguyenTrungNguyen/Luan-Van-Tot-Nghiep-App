package luanvan.luanvantotnghiep.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.MainActivity;
import luanvan.luanvantotnghiep.Model.Anion;
import luanvan.luanvantotnghiep.Model.Answer;
import luanvan.luanvantotnghiep.Model.AnswerByQuestion;
import luanvan.luanvantotnghiep.Model.Block;
import luanvan.luanvantotnghiep.Model.Cation;
import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Compound;
import luanvan.luanvantotnghiep.Model.CreatedReaction;
import luanvan.luanvantotnghiep.Model.Description;
import luanvan.luanvantotnghiep.Model.DescriptionOfChapter;
import luanvan.luanvantotnghiep.Model.DescriptionOfHeading;
import luanvan.luanvantotnghiep.Model.DescriptionOfTitle;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.Heading;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.Model.ReactWith;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.Model.Title;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.Model.TypeOfQuestion;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class CheckVersionDatabaseActivity extends AppCompatActivity {

    private ChemistryHelper mChemistryHelper;
    private static final String TAG = Constraint.TAG + "CheckVer";

    private static final String PRE_NAME = "Firebase_Database";
    private static final String KEY_GAME = "Version_Game";
    private static final String KEY_THEMATIC = "Version_Thematic";
    private static final String KEY_OFFLINE = "Version_Offline";
    private ProgressDialog dialog;

    //Reference Thematic
    private DatabaseReference myVersionThematic;
    private DatabaseReference myChapter;
    private DatabaseReference myHeading;
    private DatabaseReference myTitle;
    private DatabaseReference myDescription;
    private DatabaseReference myDescriptionOfChapter;
    private DatabaseReference myDescriptionOfHeading;
    private DatabaseReference myDescriptionOfTitle;

    //Listener Thematic
    private ValueEventListener mListenerChapter;
    private ValueEventListener mListenerHeading;
    private ValueEventListener mListenerTitle;
    private ValueEventListener mListenerDescription;
    private ValueEventListener mListenerDescriptionOfChapter;
    private ValueEventListener mListenerDescriptionOfHeading;
    private ValueEventListener mListenerDescriptionOfTitle;

    //Reference Game
    private DatabaseReference myVersionGame;
    private DatabaseReference myTypeOfQuestion;
    private DatabaseReference myAnswer;
    private DatabaseReference myQuestion;
    private DatabaseReference myAnswerByQuestion;

    //Listener Game
    private ValueEventListener myListenerTypeOfQuestion;
    private ValueEventListener myListenerAnswer;
    private ValueEventListener myListenerQuestion;
    private ValueEventListener myListenerAnswerByQuestion;

    //Check success online
    private boolean gameSuccess = false;
    private boolean thematicSuccess = false;
    private int newVersionGame = 0;
    private int newVersionThematic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_version_database);

        init();


        /*get version share pre*/
        int oldVersionOffline = PreferencesManager.getInstance().getIntData(KEY_OFFLINE, 0);
        int versionOffline = getVersionOffline();
        if (versionOffline != oldVersionOffline) {
            /*Data use PERIODIC_TABLE*/
            addDataTypeTable();

            addDataChemistryTable();

            addDataGroupTable();

            addDataElementTable();

            /*Data use search*/
            addDataCompound();

            addDataProducedBy();

            addDataChemicalReaction();

            addDataCreatedReaction();

            addDataReactWith();

            /*Data use SOLUBILITY_TABLE*/
            addDataAnionTable();

            addDataCationTable();

            addDataSoluteTable();

            /*Data use REACT_SERIES_TABLE*/
            addDataReactSeriesTable();

            /*Save new version offline*/
            saveVersion(KEY_OFFLINE, versionOffline);

            /*Check data online*/
            checkGame();
            checkThematic();

        } else {
            /*Check data online*/
            checkGame();
            checkThematic();
        }
    }

    private void init() {

        PreferencesManager.getInstance().init(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải dữ liệu...");
        dialog.setCancelable(false);
        dialog.show();

        //mPreferences = getSharedPreferences(PRE_NAME, MODE_PRIVATE);
        mChemistryHelper = ChemistrySingle.getInstance(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //THEMATIC
        myVersionThematic = database.getReference("VERSION_THEMATIC");
        myChapter = database.getReference("CHAPTER");
        myHeading = database.getReference("HEADING");
        myTitle = database.getReference("TITLE");
        myDescription = database.getReference("DESCRIPTION");
        myDescriptionOfChapter = database.getReference("DES_CHAPTER");
        myDescriptionOfHeading = database.getReference("DES_HEADING");
        myDescriptionOfTitle = database.getReference("DES_TITLE");

        //GAME
        myVersionGame = database.getReference("VERSION_GAME");
        myTypeOfQuestion = database.getReference("TYPE_QUESTION");
        myAnswer = database.getReference("ANSWER");
        myQuestion = database.getReference("QUESTION");
        myAnswerByQuestion = database.getReference("ANSWER_BY_QUESTION");

        //Data BLOCK
        boolean isAddBlock = PreferencesManager.getInstance().getBooleanData("ADD_BLOCK", true);
        addDataBlockTable(isAddBlock);
        if (isAddBlock)
            PreferencesManager.getInstance().saveBooleanData("ADD_BLOCK", false);
    }

    //Get version offline
    private int getVersionOffline() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            String version = (String) obj.get("Version");
            return Integer.parseInt(version);

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
            return -1;
        }
    }

    //Save version to SharePre
    private void saveVersion(String key, int version) {
        PreferencesManager.getInstance().saveIntData(key, version);
        Log.i(TAG, "saveVersion: Update " + key);
    }

    //Check and handle data game
    private void checkGame() {
        myVersionGame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                int versionGame = Integer.parseInt(s);
                Log.i(TAG, "versionGame: " + versionGame);

                //Get old version
                int oldVersionGame = PreferencesManager.getInstance().getIntData(KEY_GAME, 0);
                Log.i(TAG, "oldVersionGame: " + oldVersionGame);
                newVersionGame = versionGame;
                if (versionGame != oldVersionGame) {
                    mChemistryHelper.emptyAnswerByQuestion();
                    mChemistryHelper.emptyAnswer();
                    mChemistryHelper.emptyQuestion();
                    mChemistryHelper.emptyTypeOfQuestion();
                    getDataTypeOfQuestion();
                } else {
                    gameSuccess = true;
                    goMain();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value myVersionGame.", error.toException());
            }
        });
    }

    //Check and handle data thematic
    private void checkThematic() {
        myVersionThematic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                int versionThematic = Integer.parseInt(s);
                Log.i(TAG, "versionThematic: " + versionThematic);

                //Get old version
                int oldVersionThematic = PreferencesManager.getInstance().getIntData(KEY_THEMATIC, 0);
                Log.i(TAG, "oldVersionThematic: " + oldVersionThematic);
                newVersionThematic = versionThematic;
                if (versionThematic != oldVersionThematic) {
                    mChemistryHelper.emptyDescriptionOfTitle();
                    mChemistryHelper.emptyDescriptionOfHeading();
                    mChemistryHelper.emptyDescriptionOfChapter();
                    mChemistryHelper.emptyDescription();
                    mChemistryHelper.emptyTitle();
                    mChemistryHelper.emptyHeading();
                    mChemistryHelper.emptyChapter();
                    getDataChapter();
                } else {
                    thematicSuccess = true;
                    goMain();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value myVersionGame.", error.toException());
            }
        });
    }

    //Offline
    private void addDataTypeTable() {

        List<Type> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Type");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Type item = new Type();
                item.setIdType(Integer.parseInt(o.getString("mIdType")));
                item.setNameType(o.getString("mNameType"));
                list.add(item);
            }

            mChemistryHelper.emptyType();
            for (Type item : list) {
                mChemistryHelper.addType(item);
            }
            Log.i(TAG, "onCreate: UPDATE Type Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataChemistryTable() {

        List<Chemistry> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Chemistry");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Chemistry item = new Chemistry();
                item.setIdChemistry(Integer.parseInt(o.getString("mIdChemistry")));
                item.setIdType(Integer.parseInt(o.getString("mIdType")));
                item.setSymbolChemistry(o.getString("mSymbolChemistry"));
                item.setNameChemistry(o.getString("mNameChemistry"));
                item.setStatusChemistry(o.getString("mStatusChemistry"));
                item.setColorChemistry(o.getString("mColorChemistry"));
                item.setWeightChemistry(Double.parseDouble(o.getString("mWeightChemistry")));
                list.add(item);
            }

            mChemistryHelper.emptyChemistry();
            for (Chemistry item : list) {
                mChemistryHelper.addChemistry(item);
            }
            Log.i(TAG, "onCreate: UPDATE Chemistry Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataGroupTable() {

        List<Group> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Group");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Group item = new Group();
                item.setIdGroup(Integer.parseInt(o.getString("mIdGroup")));
                item.setNameGroup(o.getString("mNameGroup"));
                list.add(item);
            }

            mChemistryHelper.emptyGroup();
            for (Group item : list) {
                mChemistryHelper.addGroup(item);
            }
            Log.i(TAG, "onCreate: UPDATE Group Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataElementTable() {

        List<Element> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Element");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Element item = new Element();
                item.setIdElement(Integer.parseInt(o.getString("mIdElement")));
                item.setIdGroup(Integer.parseInt(o.getString("mIdGroup")));
                item.setMolecularFormula(o.getString("mMolecularFormula"));
                item.setPeriod(Integer.parseInt(o.getString("mPeriod")));
                item.setClassElement(o.getString("mClassElement"));
                item.setNeutron(Integer.parseInt(o.getString("mNeutron")));
                item.setSimplifiedConfiguration(o.getString("mSimplifiedConfiguration"));
                item.setConfiguration(o.getString("mConfiguration"));
                item.setShell(o.getString("mShell"));
                item.setElectronegativity(Double.parseDouble(o.getString("mElectronegativity")));
                item.setValence(o.getString("mValence"));
                item.setEnglishName(o.getString("mEnglishName"));
                item.setMeltingPoint(Double.parseDouble(o.getString("mMeltingPoint")));
                item.setBoilingPoint(Double.parseDouble(o.getString("mBoilingPoint")));

                //handle discoverer is null property
                if (o.has("mDiscoverer")) {
                    item.setDiscoverer(o.getString("mDiscoverer"));
                } else {
                    item.setDiscoverer("");
                }

                item.setYearDiscovery(o.getString("mYearDiscovery"));
                item.setIsotopes(o.getString("mIsotopes"));
                item.setPicture(o.getString("mPicture"));
                list.add(item);
            }

            mChemistryHelper.emptyElement();
            for (Element item : list) {
                mChemistryHelper.addElement(item);
            }
            Log.i(TAG, "onCreate: UPDATE Element Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataCompound() {

        List<Compound> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Compound");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Compound item = new Compound();
                item.setIdCompound(Integer.parseInt(o.getString("mIdCompound")));

                //handle other names is null property
                if (o.has("mDiscoverer")) {
                    item.setOtherNames(o.getString("mOtherNames"));
                } else {
                    item.setOtherNames("");
                }

                list.add(item);
            }

            mChemistryHelper.emptyCompound();
            for (Compound item : list) {
                mChemistryHelper.addCompound(item);
            }
            Log.i(TAG, "onCreate: UPDATE Compound Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataProducedBy() {

        List<ProducedBy> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("ProducedBy");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                ProducedBy item = new ProducedBy();
                item.setIdRightReaction(Integer.parseInt(o.getString("mIdRightReaction")));
                item.setIdLeftReaction(Integer.parseInt(o.getString("mIdLeftReaction")));
                ;

                list.add(item);
            }

            mChemistryHelper.emptyProducedBy();
            for (ProducedBy item : list) {
                mChemistryHelper.addProducedBy(item);
            }
            Log.i(TAG, "onCreate: UPDATE ProducedBy Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataChemicalReaction() {

        List<ChemicalReaction> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("ChemicalReaction");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                ChemicalReaction item = new ChemicalReaction();
                item.setIdChemicalReaction(Integer.parseInt(o.getString("mIdChemicalReaction")));
                item.setReactants(o.getString("mReactants"));
                item.setProducts(o.getString("mProducts"));
                item.setTwoWay(Integer.parseInt(o.getString("mTwoWay")));

                //handle conditions is null property
                if (o.has("mDiscoverer")) {
                    item.setConditions(o.getString("mConditions"));
                } else {
                    item.setConditions("");
                }

                //handle phenomena is null property
                if (o.has("mPhenomena")) {
                    item.setPhenomena(o.getString("mPhenomena"));
                } else {
                    item.setPhenomena("");
                }

                //handle reaction types is null property
                if (o.has("mReactionTypes")) {
                    item.setReactionTypes(o.getString("mReactionTypes"));
                } else {
                    item.setReactionTypes("");
                }

                list.add(item);
            }

            mChemistryHelper.emptyChemicalReaction();
            for (ChemicalReaction item : list) {
                mChemistryHelper.addChemicalReaction(item);
            }
            Log.i(TAG, "onCreate: UPDATE ChemicalReaction Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataCreatedReaction() {

        List<CreatedReaction> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("CreatedReaction");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                CreatedReaction item = new CreatedReaction();
                item.setIdCreatedRight(Integer.parseInt(o.getString("mIdCreatedRight")));
                item.setIdChemicalReaction(Integer.parseInt(o.getString("mIdChemicalReaction")));

                list.add(item);
            }

            mChemistryHelper.emptyCreatedReaction();
            for (CreatedReaction item : list) {
                mChemistryHelper.addCreatedReaction(item);
            }
            Log.i(TAG, "onCreate: UPDATE CreatedReaction Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataReactWith() {

        List<ReactWith> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("ReactWith");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                ReactWith item = new ReactWith();
                item.setIdChemistry_1(Integer.parseInt(o.getString("mIdChemistry_1")));
                item.setIdChemistry_2(Integer.parseInt(o.getString("mIdChemistry_2")));
                item.setIdChemicalReaction(Integer.parseInt(o.getString("mIdChemicalReaction")));

                list.add(item);
            }

            mChemistryHelper.emptyReactWith();
            for (ReactWith item : list) {
                mChemistryHelper.addReactWith(item);
            }
            Log.i(TAG, "onCreate: UPDATE ReactWith Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataAnionTable() {

        List<Anion> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Anion");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Anion item = new Anion();
                item.setIdAnion(Integer.parseInt(o.getString("mIdAnion")));
                item.setNameAnion(o.getString("mNameAnion"));
                item.setValenceAnion(o.getString("mValenceAnion"));
                list.add(item);
            }

            mChemistryHelper.emptyAnion();
            for (Anion item : list) {
                mChemistryHelper.addAnion(item);
            }
            Log.i(TAG, "onCreate: UPDATE Anion Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataCationTable() {

        List<Cation> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Cation");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Cation item = new Cation();
                item.setIdCation(Integer.parseInt(o.getString("mIdCation")));
                item.setNameCation(o.getString("mNameCation"));
                item.setValenceCation(o.getString("mValenceCation"));
                list.add(item);
            }

            mChemistryHelper.emptyCation();
            for (Cation item : list) {
                mChemistryHelper.addCation(item);
            }
            Log.i(TAG, "onCreate: UPDATE Cation Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataSoluteTable() {

        List<Solute> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("Solute");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Solute item = new Solute();
                item.setAnion(Integer.parseInt(o.getString("mAnion")));
                item.setCation(Integer.parseInt(o.getString("mCation")));
                item.setSolute(o.getString("mSolute"));
                list.add(item);
            }

            mChemistryHelper.emptySolute();
            for (Solute item : list) {
                mChemistryHelper.addSolute(item);
            }
            Log.i(TAG, "onCreate: UPDATE Solute Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataReactSeriesTable() {

        List<ReactSeries> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
            JSONArray array = (JSONArray) obj.get("ReactSeries");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                ReactSeries item = new ReactSeries();
                item.setIdReactSeries(Integer.parseInt(o.getString("mIdReactSeries")));
                item.setIon(o.getString("mIon"));
                item.setValence(o.getString("mValence"));
                list.add(item);
            }

            mChemistryHelper.emptyReactSeries();
            for (ReactSeries item : list) {
                mChemistryHelper.addReactSeries(item);
            }
            Log.i(TAG, "onCreate: UPDATE ReactSeries Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataBlockTable(boolean flag) {

        if (flag) {
            List<Block> list = new ArrayList<>();
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset("chemistry.json"));
                JSONArray array = (JSONArray) obj.get("Block");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = (JSONObject) array.get(i);
                    Block item = new Block();

                    item.setIdBlock(Integer.parseInt(o.getString("mIdBlock")));
                    list.add(item);
                }

                mChemistryHelper.emptyBlock();
                for (Block item : list) {
                    mChemistryHelper.addBlock(item);
                }

                Log.i(TAG, "addDataBlockTable: CREATE Block Table!!!");

            } catch (JSONException e) {
                Log.i(TAG, "JSONException: " + e.getMessage());
            }
        } else {
            Log.i(TAG, "addDataBlockTable: Block Table Exist!!!");
        }
    }

    public String loadJSONFromAsset(String path) {
        String json;
        try {
            InputStream is = this.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //ONLINE
    //Thematic
    private void getDataChapter() {
        mListenerChapter = myChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Chapter chapter = postSnapshot.getValue(Chapter.class);
                    assert chapter != null;
                    if (chapter.getConfirm() == 1) {
                        mChemistryHelper.addChapter(chapter);
                    }
                }
                getDataHeading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataHeading() {
        mListenerHeading = myHeading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Heading heading = postSnapshot.getValue(Heading.class);
                    mChemistryHelper.addHeading(heading);
                }
                getDataTitle();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataTitle() {
        mListenerTitle = myTitle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Title title = postSnapshot.getValue(Title.class);
                    mChemistryHelper.addTitle(title);
                }
                getDataDescription();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataDescription() {
        mListenerDescription = myDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Description description = postSnapshot.getValue(Description.class);
                    mChemistryHelper.addDescription(description);
                }
                getDataDescriptionOfChapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataDescriptionOfChapter() {
        mListenerDescriptionOfChapter = myDescriptionOfChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DescriptionOfChapter descriptionOfChapter = postSnapshot.getValue(DescriptionOfChapter.class);
                    mChemistryHelper.addDescriptionOfChapter(descriptionOfChapter);
                }
                getDataDescriptionOfHeading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataDescriptionOfHeading() {
        mListenerDescriptionOfHeading = myDescriptionOfHeading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DescriptionOfHeading descriptionOfHeading = postSnapshot.getValue(DescriptionOfHeading.class);
                    mChemistryHelper.addDescriptionOfHeading(descriptionOfHeading);
                }
                getDataDescriptionOfTitle();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataDescriptionOfTitle() {
        mListenerDescriptionOfTitle = myDescriptionOfTitle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DescriptionOfTitle descriptionOfTitle = postSnapshot.getValue(DescriptionOfTitle.class);
                    mChemistryHelper.addDescriptionOfTitle(descriptionOfTitle);
                }
                handleSuccessThematic();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void handleSuccessThematic() {
        saveVersion(KEY_THEMATIC, newVersionThematic);

        myChapter.removeEventListener(mListenerChapter);
        myHeading.removeEventListener(mListenerHeading);
        myTitle.removeEventListener(mListenerTitle);

        myDescription.removeEventListener(mListenerDescription);
        myDescriptionOfChapter.removeEventListener(mListenerDescriptionOfChapter);
        myDescriptionOfHeading.removeEventListener(mListenerDescriptionOfHeading);
        myDescriptionOfTitle.removeEventListener(mListenerDescriptionOfTitle);

        thematicSuccess = true;
        goMain();
    }

    //Game
    private void getDataTypeOfQuestion() {
        myListenerTypeOfQuestion = myTypeOfQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TypeOfQuestion typeOfQuestion = postSnapshot.getValue(TypeOfQuestion.class);
                    mChemistryHelper.addTypeOfQuestion(typeOfQuestion);
                }
                getDataQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataQuestion() {
        myListenerQuestion = myQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Question question = postSnapshot.getValue(Question.class);
                    mChemistryHelper.addQuestion(question);
                }
                getDataAnswer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataAnswer() {
        myListenerAnswer = myAnswer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Answer answer = postSnapshot.getValue(Answer.class);
                    mChemistryHelper.addAnswer(answer);
                }
                getDataAnswerByQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataAnswerByQuestion() {
        myListenerAnswerByQuestion = myAnswerByQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AnswerByQuestion answerByQuestion = postSnapshot.getValue(AnswerByQuestion.class);
                    mChemistryHelper.addAnswerByQuestion(answerByQuestion);
                }
                handleSuccessGame();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ANTN", "Failed to read value.", error.toException());
            }
        });
    }

    private void handleSuccessGame() {
        saveVersion(KEY_GAME, newVersionGame);

        myTypeOfQuestion.removeEventListener(myListenerTypeOfQuestion);
        myQuestion.removeEventListener(myListenerQuestion);
        myAnswer.removeEventListener(myListenerAnswer);
        myAnswerByQuestion.removeEventListener(myListenerAnswerByQuestion);

        gameSuccess = true;
        goMain();
    }

    private void goMain() {
        if (gameSuccess && thematicSuccess) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            //Block
            Log.i(TAG, "onCreate: getAllBlock " + mChemistryHelper.getAllBlock().size());

            //Chemistry
            Log.i(TAG, "onCreate: getAllTypesChemistry " + mChemistryHelper.getAllTypes().size());
            Log.i(TAG, "onCreate: getAllChemistry " + mChemistryHelper.getAllChemistry().size());
            Log.i(TAG, "onCreate: getAllGroups " + mChemistryHelper.getAllGroups().size());
            Log.i(TAG, "onCreate: getAllElements " + mChemistryHelper.getAllElements().size());
            Log.i(TAG, "onCreate: getAllCompound " + mChemistryHelper.getAllCompound().size());
            Log.i(TAG, "onCreate: getAllProducedBy " + mChemistryHelper.getAllProducedBy().size());
            Log.i(TAG, "onCreate: getAllChemicalReaction " + mChemistryHelper.getAllChemicalReaction().size());
            Log.i(TAG, "onCreate: getAllCreatedReaction " + mChemistryHelper.getAllCreatedReaction().size());
            Log.i(TAG, "onCreate: getAllReactWith " + mChemistryHelper.getAllReactWith().size());
            Log.i(TAG, "onCreate: getAllAnion " + mChemistryHelper.getAllAnion().size());
            Log.i(TAG, "onCreate: getAllCation " + mChemistryHelper.getAllCation().size());
            Log.i(TAG, "onCreate: getAllSolute " + mChemistryHelper.getAllSolute().size());
            Log.i(TAG, "onCreate: getAllReactSeries " + mChemistryHelper.getAllReactSeries().size());

            //Thematic
            Log.i(TAG, "onCreate: getAllChapter: " + mChemistryHelper.getAllChapter().size());
            Log.i(TAG, "onCreate: getAllHeading: " + mChemistryHelper.getAllHeading().size());
            Log.i(TAG, "onCreate: getAllTitle: " + mChemistryHelper.getAllTitle().size());
            Log.i(TAG, "onCreate: getAllDescription: " + mChemistryHelper.getAllDescription().size());
            Log.i(TAG, "onCreate: getAllDescriptionOfChapter: " + mChemistryHelper.getAllDescriptionOfChapter().size());
            Log.i(TAG, "onCreate: getAllDescriptionOfHeading: " + mChemistryHelper.getAllDescriptionOfHeading().size());
            Log.i(TAG, "onCreate: getAllDescriptionOfTitle: " + mChemistryHelper.getAllDescriptionOfTitle().size());

            //Game
            Log.i(TAG, "onCreate: getAllTypeOfQuestion " + mChemistryHelper.getAllTypeOfQuestion().size());
            Log.i(TAG, "onCreate: getAllQuestion " + mChemistryHelper.getAllQuestion().size());
            Log.i(TAG, "onCreate: getAllAnswer " + mChemistryHelper.getAllAnswer().size());
            Log.i(TAG, "onCreate: getAllAnswerByQuestion " + mChemistryHelper.getAllAnswerByQuestion().size());

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}