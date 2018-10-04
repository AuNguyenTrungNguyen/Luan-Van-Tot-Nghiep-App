package luanvan.luanvantotnghiep.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.Model.Question;
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.Model.ReactWith;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.Model.TypeOfQuestion;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;

public class CheckVersionDatabaseActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private ChemistryHelper mChemistryHelper;
    private static final String TAG = Constraint.TAG + "CheckVer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_version_database);

        ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Đợi chút xíu xìu xiu...");
        mDialog.show();

        mPreferences = getSharedPreferences("CNHH", MODE_PRIVATE);

        int oldVersion = mPreferences.getInt("DB_VER", 0);
        int version = ChemistrySingle.getInstance(this).getWritableDatabase().getVersion();

        if (version != oldVersion) {
            mChemistryHelper = ChemistrySingle.getInstance(this);
            saveVersion(version);

            //Data use PERIODIC_TABLE
            addDataTypeTable();

            addDataChemistryTable();

            addDataGroupTable();

            addDataElementTable();

            //Data use search
            addDataCompound();

            addDataProducedBy();

            addDataChemicalReaction();

            addDataCreatedReaction();

            addDataReactWith();

            //Data use SOLUBILITY_TABLE
            addDataAnionTable();

            addDataCationTable();

            addDataSoluteTable();

            //Data use REACT_SERIES_TABLE
            addDataReactSeriesTable();

            //Data GAME
            addDataBlockTable();

            addDataTypeOfGameTable();

            addDataAnswerTable();

            addDataQuestionTable();

            addDataAnswerByQuestionTable();

            addDataChapterTable();

            Log.i(TAG, "onCreate: ADD DATA FINISH!!!");

        } else {
            Log.i(TAG, "onCreate: NOTHING UPDATE!!!");
//            Log.i(TAG, "onCreate: getAllTypes " + mChemistryHelper.getAllTypes().size());
//            Log.i(TAG, "onCreate: getAllChemistry " + mChemistryHelper.getAllChemistry().size());
//            Log.i(TAG, "onCreate: getAllGroups " + mChemistryHelper.getAllGroups().size());
//            Log.i(TAG, "onCreate: getAllElements " + mChemistryHelper.getAllElements().size());
//            Log.i(TAG, "onCreate: getAllCompound " + mChemistryHelper.getAllCompound().size());
//            Log.i(TAG, "onCreate: getAllProducedBy " + mChemistryHelper.getAllProducedBy().size());
//            Log.i(TAG, "onCreate: getAllChemicalReaction " + mChemistryHelper.getAllChemicalReaction().size());
//            Log.i(TAG, "onCreate: getAllCreatedReaction " + mChemistryHelper.getAllCreatedReaction().size());
//            Log.i(TAG, "onCreate: getAllReactWith " + mChemistryHelper.getAllReactWith().size());
//            Log.i(TAG, "onCreate: getAllAnion " + mChemistryHelper.getAllAnion().size());
//            Log.i(TAG, "onCreate: getAllCation " + mChemistryHelper.getAllCation().size());
//            Log.i(TAG, "onCreate: getAllSolute " + mChemistryHelper.getAllSolute().size());
//            Log.i(TAG, "onCreate: getAllReactSeries " + mChemistryHelper.getAllReactSeries().size());
//            Log.i(TAG, "onCreate: getAllBlock " + mChemistryHelper.getAllBlock().size());
//            Log.i(TAG, "onCreate: getAllTypeOfQuestion " + mChemistryHelper.getAllTypeOfQuestion().size());
//            Log.i(TAG, "onCreate: getAllAnswer " + mChemistryHelper.getAllAnswer().size());
//            Log.i(TAG, "onCreate: getAllQuestion " + mChemistryHelper.getAllQuestion().size());
//            Log.i(TAG, "onCreate: getAllAnswerByQuestion " + mChemistryHelper.getAllAnswerByQuestion().size());
        }

        startActivity(new Intent(this, MainActivity.class));
        mDialog.dismiss();
        finish();
    }

    private void saveVersion(int version) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("DB_VER", version);
        editor.apply();

        Log.i(TAG, "saveVersion: Update new version!");
    }

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
                if(o.has("mDiscoverer")){
                    item.setDiscoverer(o.getString("mDiscoverer"));
                }else{
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
                if(o.has("mDiscoverer")){
                    item.setOtherNames(o.getString("mOtherNames"));
                }else{
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
                if(o.has("mDiscoverer")){
                    item.setConditions(o.getString("mConditions"));
                }else{
                    item.setConditions("");
                }

                //handle phenomena is null property
                if(o.has("mPhenomena")){
                    item.setPhenomena(o.getString("mPhenomena"));
                }else{
                    item.setPhenomena("");
                }

                //handle reaction types is null property
                if(o.has("mReactionTypes")){
                    item.setReactionTypes(o.getString("mReactionTypes"));
                }else{
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

    private void addDataBlockTable() {

        List<Block> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
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
            Log.i(TAG, "onCreate: UPDATE Block Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataTypeOfGameTable() {

        List<TypeOfQuestion> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
            JSONArray array = (JSONArray) obj.get("TypeOfQuestion");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                TypeOfQuestion item = new TypeOfQuestion();
                item.setIdType(Integer.parseInt(o.getString("mIdType")));
                item.setNameType(o.getString("mNameType"));
                list.add(item);
            }

            mChemistryHelper.emptyTypeOfQuestion();
            for (TypeOfQuestion item : list) {
                mChemistryHelper.addTypeOfQuestion(item);
            }
            Log.i(TAG, "onCreate: UPDATE TypeOfQuestion Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataAnswerTable() {

        List<Answer> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
            JSONArray array = (JSONArray) obj.get("Answer");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Answer item = new Answer();
                item.setIdAnswer(Integer.parseInt(o.getString("mIdAnswer")));
                item.setContentAnswer(o.getString("mContentAnswer"));
                list.add(item);
            }

            mChemistryHelper.emptyAnswer();
            for (Answer item : list) {
                mChemistryHelper.addAnswer(item);
            }
            Log.i(TAG, "onCreate: UPDATE Answer Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataQuestionTable() {

        List<Question> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
            JSONArray array = (JSONArray) obj.get("Question");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Question item = new Question();
                item.setIdQuestion(Integer.parseInt(o.getString("mIdQuestion")));
                item.setContentQuestion(o.getString("mContentQuestion"));
                item.setIdLevel(Integer.parseInt(o.getString("mIdLevel")));
                item.setIdBlock(Integer.parseInt(o.getString("mIdBlock")));
                item.setIdType(Integer.parseInt(o.getString("mIdType")));
                item.setExtent(Integer.parseInt(o.getString("mExtent")));
                list.add(item);
            }

            mChemistryHelper.emptyQuestion();
            for (Question item : list) {
                mChemistryHelper.addQuestion(item);
            }
            Log.i(TAG, "onCreate: UPDATE Question Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataAnswerByQuestionTable() {

        List<AnswerByQuestion> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
            JSONArray array = (JSONArray) obj.get("AnswerByQuestion");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                AnswerByQuestion item = new AnswerByQuestion();
                item.setIdQuestion(Integer.parseInt(o.getString("mIdQuestion")));
                item.setIdAnswer(Integer.parseInt(o.getString("mIdAnswer")));
                item.setCorrect(Integer.parseInt(o.getString("mCorrect")));
                list.add(item);
            }

            mChemistryHelper.emptyAnswerByQuestion();
            for (AnswerByQuestion item : list) {
                mChemistryHelper.addAnswerByQuestion(item);
            }
            Log.i(TAG, "onCreate: UPDATE AnswerByQuestion Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void addDataChapterTable() {

        List<Chapter> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("game.json"));
            JSONArray array = (JSONArray) obj.get("Chapter");

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Chapter item = new Chapter();
                item.setIdChapter(Integer.parseInt(o.getString("mIdChapter")));
                item.setNameChapter(o.getString("mNameChapter"));
                item.setIdBlock(Integer.parseInt(o.getString("mIdBlock")));
                item.setContentChapter(o.getString("mContentChapter"));
                list.add(item);
            }

            mChemistryHelper.emptyChapter();
            for (Chapter item : list) {
                mChemistryHelper.addChapter(item);
            }
            Log.i(TAG, "onCreate: UPDATE Chapter Table!!!");

        } catch (JSONException e) {
            Log.i(TAG, "JSONException: " + e.getMessage());
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
}
