package luanvan.luanvantotnghiep.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

public class ChemistryHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Chemistry.db";

    public ChemistryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        } else {
            Log.i("ANTN", "SQLiteDatabase foreign is not support!");
        }
    }

    public void onCreate(SQLiteDatabase db) {
        /*
            PERIODIC TABLE
         */
        db.execSQL(SQL_CREATE_TYPE);
        db.execSQL(SQL_CREATE_CHEMISTRY);
        db.execSQL(SQL_CREATE_GROUP);
        db.execSQL(SQL_CREATE_ELEMENT);

         /*
            SOLUBILITY TABLE
         */
        db.execSQL(SQL_CREATE_ANION);
        db.execSQL(SQL_CREATE_CATION);
        db.execSQL(SQL_CREATE_SOLUTE);

        /*
            REACTIVITY SERIES
         */
        db.execSQL(SQL_CREATE_REACT_SERIES);

        /*
            SEARCH CHEMISTRY
         */
        db.execSQL(SQL_CREATE_COMPOUND);
        db.execSQL(SQL_CREATE_PRODUCED_BY);
        db.execSQL(SQL_CREATE_CHEMICAL_REACTION);
        db.execSQL(SQL_CREATE_REACT_WITH);
        db.execSQL(SQL_CREATE_CREATED_REACTION);

        /*
            GAME
         */
        db.execSQL(SQL_CREATE_BLOCK);
        db.execSQL(SQL_CREATE_CHAPTER);
        db.execSQL(SQL_CREATE_TYPE_OF_QUESTION);
        db.execSQL(SQL_CREATE_ANSWER);
        db.execSQL(SQL_CREATE_QUESTION);
        db.execSQL(SQL_CREATE_ANSWER_BY_QUESTION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*
            PERIODIC TABLE
         */
        db.execSQL(SQL_DELETE_ELEMENT);
        ///
        db.execSQL(SQL_DELETE_GROUP);
        db.execSQL(SQL_DELETE_CHEMISTRY);
        db.execSQL(SQL_DELETE_TYPE);

         /*
            SOLUBILITY TABLE
         */
        db.execSQL(SQL_DELETE_SOLUTE);
        db.execSQL(SQL_DELETE_CATION);
        db.execSQL(SQL_DELETE_ANION);

        /*
            REACTIVITY SERIES
         */
        db.execSQL(SQL_DELETE_REACT_SERIES);

        /*
            SEARCH CHEMISTRY
         */
        db.execSQL(SQL_DELETE_COMPOUND);
        db.execSQL(SQL_DELETE_PRODUCED_BY);

        db.execSQL(SQL_DELETE_REACT_WITH);
        db.execSQL(SQL_DELETE_CREATED_REACTION);
        db.execSQL(SQL_DELETE_CHEMICAL_REACTION);

        /*
            GAME
         */
        db.execSQL(SQL_DELETE_BLOCK);
        db.execSQL(SQL_DELETE_CHAPTER);
        db.execSQL(SQL_DELETE_TYPE_OF_QUESTION);
        db.execSQL(SQL_DELETE_ANSWER);
        db.execSQL(SQL_DELETE_QUESTION);
        db.execSQL(SQL_DELETE_ANSWER_BY_QUESTION);


        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*
        GAME
     */

    //[CREATE] table block
    private static final String SQL_CREATE_BLOCK =
            "CREATE TABLE " + ChemistryContract.BlockEntry.TABLE_NAME + " (" +
                    ChemistryContract.BlockEntry.COLUMN_BLOCK_ID + " INTEGER PRIMARY KEY )";

    //[CREATE] table chapter
    private static final String SQL_CREATE_CHAPTER =
            "CREATE TABLE " + ChemistryContract.ChapterEntry.TABLE_NAME + " (" +
                    ChemistryContract.ChapterEntry.COLUMN_CHAPTER_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.ChapterEntry.COLUMN_CHAPTER_NAME + " TEXT NOT NULL ," +
                    ChemistryContract.ChapterEntry.COLUMN_BLOCK_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.ChapterEntry.COLUMN_CHAPTER_CONTENT + " TEXT NOT NULL ," +
                    "FOREIGN KEY(" + ChemistryContract.ChapterEntry.COLUMN_BLOCK_ID + ") " +
                    "REFERENCES " + ChemistryContract.BlockEntry.TABLE_NAME + "(" + ChemistryContract.BlockEntry.COLUMN_BLOCK_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table type of question
    private static final String SQL_CREATE_TYPE_OF_QUESTION =
            "CREATE TABLE " + ChemistryContract.TypeOfQuestionEntry.TABLE_NAME + " (" +
                    ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_NAME + " INTEGER NOT NULL ," +
                    ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_DESCRIPTION + " TEXT)";

    //[CREATE] table answer
    private static final String SQL_CREATE_ANSWER =
            "CREATE TABLE " + ChemistryContract.AnswerEntry.TABLE_NAME + " (" +
                    ChemistryContract.AnswerEntry.COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.AnswerEntry.COLUMN_ANSWER_CONTENT + " TEXT NOT NULL)";


    //[CREATE] table question
    private static final String SQL_CREATE_QUESTION =
            "CREATE TABLE " + ChemistryContract.QuestionEntry.TABLE_NAME + " (" +
                    ChemistryContract.QuestionEntry.COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.QuestionEntry.COLUMN_QUESTION_CONTENT + " TEXT NOT NULL ," +
                    ChemistryContract.QuestionEntry.COLUMN_LEVEL_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.QuestionEntry.COLUMN_BLOCK_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.QuestionEntry.COLUMN_TYPE_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.QuestionEntry.COLUMN_EXTENT + " INTEGER NOT NULL ," +
                    "FOREIGN KEY(" + ChemistryContract.QuestionEntry.COLUMN_BLOCK_ID + ") " +
                    "REFERENCES " + ChemistryContract.BlockEntry.TABLE_NAME + "(" + ChemistryContract.BlockEntry.COLUMN_BLOCK_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.QuestionEntry.COLUMN_TYPE_ID + ") " +
                    "REFERENCES " + ChemistryContract.TypeOfQuestionEntry.TABLE_NAME + "(" + ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table answer by question
    private static final String SQL_CREATE_ANSWER_BY_QUESTION =
            "CREATE TABLE " + ChemistryContract.AnswerByQuestionEntry.TABLE_NAME + " (" +
                    ChemistryContract.AnswerByQuestionEntry.COLUMN_QUESTION_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.AnswerByQuestionEntry.COLUMN_ANSWER_ID + " INTEGER NOT NULL ," +
                    ChemistryContract.AnswerByQuestionEntry.COLUMN_CORRECT + " INTERGER NOT NULL ," +
                    "PRIMARY KEY(" + ChemistryContract.AnswerByQuestionEntry.COLUMN_QUESTION_ID + ", " +
                    ChemistryContract.AnswerByQuestionEntry.COLUMN_ANSWER_ID + "), " +
                    "FOREIGN KEY(" + ChemistryContract.AnswerByQuestionEntry.COLUMN_QUESTION_ID + ") " +
                    "REFERENCES " + ChemistryContract.QuestionEntry.TABLE_NAME + "(" + ChemistryContract.QuestionEntry.COLUMN_QUESTION_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.AnswerByQuestionEntry.COLUMN_ANSWER_ID + ") " +
                    "REFERENCES " + ChemistryContract.AnswerEntry.TABLE_NAME + "(" + ChemistryContract.AnswerEntry.COLUMN_ANSWER_ID + ") " +
                    "ON DELETE CASCADE)";

    //[DROP] table block
    private static final String SQL_DELETE_BLOCK =
            "DROP TABLE IF EXISTS " + ChemistryContract.BlockEntry.TABLE_NAME;

    //[DROP] table block
    private static final String SQL_DELETE_CHAPTER =
            "DROP TABLE IF EXISTS " + ChemistryContract.ChapterEntry.TABLE_NAME;

    //[DROP] table block
    private static final String SQL_DELETE_TYPE_OF_QUESTION =
            "DROP TABLE IF EXISTS " + ChemistryContract.TypeOfQuestionEntry.TABLE_NAME;

    //[DROP] table block
    private static final String SQL_DELETE_ANSWER =
            "DROP TABLE IF EXISTS " + ChemistryContract.AnswerEntry.TABLE_NAME;

    //[DROP] table block
    private static final String SQL_DELETE_QUESTION =
            "DROP TABLE IF EXISTS " + ChemistryContract.QuestionEntry.TABLE_NAME;

    //[DROP] table block
    private static final String SQL_DELETE_ANSWER_BY_QUESTION =
            "DROP TABLE IF EXISTS " + ChemistryContract.AnswerByQuestionEntry.TABLE_NAME;


    /*
     * TABLES PERIODIC_TABLE
     */

    //[CREATE] table type
    private static final String SQL_CREATE_TYPE =
            "CREATE TABLE " + ChemistryContract.TypeEntry.TABLE_NAME + " (" +
                    ChemistryContract.TypeEntry.COLUMN_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.TypeEntry.COLUMN_NAME + " TEXT)";

    //[CREATE] table chemistry
    private static final String SQL_CREATE_CHEMISTRY =
            "CREATE TABLE " + ChemistryContract.ChemistryEntry.TABLE_NAME + " (" +
                    ChemistryContract.ChemistryEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    ChemistryContract.ChemistryEntry.COLUMN_TYPE_ID + " INTEGER NOT NULL, " +
                    ChemistryContract.ChemistryEntry.COLUMN_SYMBOL + " TEXT, " +
                    ChemistryContract.ChemistryEntry.COLUMN_NAME + " TEXT, " +
                    ChemistryContract.ChemistryEntry.COLUMN_STATUS + " TEXT, " +
                    ChemistryContract.ChemistryEntry.COLUMN_COLOR + " TEXT, " +
                    ChemistryContract.ChemistryEntry.COLUMN_WEIGHT + " DOUBLE, " +
                    "FOREIGN KEY(" + ChemistryContract.ChemistryEntry.COLUMN_TYPE_ID + ") " +
                    "REFERENCES " + ChemistryContract.TypeEntry.TABLE_NAME + "(" + ChemistryContract.TypeEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table group
    private static final String SQL_CREATE_GROUP =
            "CREATE TABLE " + ChemistryContract.GroupEntry.TABLE_NAME + " (" +
                    ChemistryContract.GroupEntry.COLUMN_ID + " INTEGER PRIMARY KEY ," +
                    ChemistryContract.GroupEntry.COLUMN_NAME + " TEXT)";

    //[CREATE] table element
    private static final String SQL_CREATE_ELEMENT = "CREATE TABLE " + ChemistryContract.ElementEntry.TABLE_NAME + " (" +
            ChemistryContract.ElementEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            ChemistryContract.ElementEntry.COLUMN_GROUP_ID + " INTEGER NOT NULL, " +
            ChemistryContract.ElementEntry.COLUMN_MOLECULAR_FORMULA + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_PERIOD + " INTEGER, " +
            ChemistryContract.ElementEntry.COLUMN_CLASS + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_NEUTRON + " INTEGER, " +
            ChemistryContract.ElementEntry.COLUMN_SIMPLE_CONFIG + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_CONFIG + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_SHELL + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_ELECTRONEGATIVITY + " DOUBLE, " +
            ChemistryContract.ElementEntry.COLUMN_VALENCE + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_ENGLISH_NAME + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_MELTING_POINT + " DOUBLE, " +
            ChemistryContract.ElementEntry.COLUMN_BOILING_POINT + " DOUBLE, " +
            ChemistryContract.ElementEntry.COLUMN_DISCOVERER + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_YEAR_DISCOVERY + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_ISOTOPES + " TEXT, " +
            ChemistryContract.ElementEntry.COLUMN_PICTURE + " TEXT, " +
            "FOREIGN KEY(" + ChemistryContract.ElementEntry.COLUMN_GROUP_ID + ") " +
            "REFERENCES " + ChemistryContract.GroupEntry.TABLE_NAME + "(" + ChemistryContract.GroupEntry.COLUMN_ID + ") " +
            "ON DELETE CASCADE," +
            "FOREIGN KEY(" + ChemistryContract.ElementEntry.COLUMN_ID + ") " +
            "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
            "ON DELETE CASCADE)";

    //[DROP] table type
    private static final String SQL_DELETE_TYPE =
            "DROP TABLE IF EXISTS " + ChemistryContract.TypeEntry.TABLE_NAME;

    //[DROP] table chemistry
    private static final String SQL_DELETE_CHEMISTRY =
            "DROP TABLE IF EXISTS " + ChemistryContract.ChemistryEntry.TABLE_NAME;

    //[DROP] table group
    private static final String SQL_DELETE_GROUP =
            "DROP TABLE IF EXISTS " + ChemistryContract.GroupEntry.TABLE_NAME;

    //[DROP] table element
    private static final String SQL_DELETE_ELEMENT =
            "DROP TABLE IF EXISTS " + ChemistryContract.ElementEntry.TABLE_NAME;

    /*
     * TABLES SOLUBILITY_TABLE
     */

    //{CREATE] table anion
    private static final String SQL_CREATE_ANION = "CREATE TABLE " + ChemistryContract.AnionEntry.TABLE_NAME + " (" +
            ChemistryContract.AnionEntry.COLUMN_ID + " INTEGER PRIMARY KEY ," +
            ChemistryContract.AnionEntry.COLUMN_NAME + " TEXT ," +
            ChemistryContract.AnionEntry.COLUMN_VALENCE + " TEXT)";

    //{CREATE] table cation
    private static final String SQL_CREATE_CATION = "CREATE TABLE " + ChemistryContract.CationEntry.TABLE_NAME + " (" +
            ChemistryContract.CationEntry.COLUMN_ID + " INTEGER PRIMARY KEY ," +
            ChemistryContract.CationEntry.COLUMN_NAME + " TEXT ," +
            ChemistryContract.CationEntry.COLUMN_VALENCE + " TEXT)";

    //{CREATE] table solute
    private static final String SQL_CREATE_SOLUTE = "CREATE TABLE " + ChemistryContract.SoluteEntry.TABLE_NAME + " (" +
            ChemistryContract.SoluteEntry.COLUMN_ANION_ID + " INTEGER NOT NULL ," +
            ChemistryContract.SoluteEntry.COLUMN_CATION_ID + " INTEGER NOT NULL ," +
            ChemistryContract.SoluteEntry.COLUMN_SOLUTE + " TEXT," +
            "FOREIGN KEY(" + ChemistryContract.SoluteEntry.COLUMN_ANION_ID + ") " +
            "REFERENCES " + ChemistryContract.AnionEntry.TABLE_NAME + "(" + ChemistryContract.AnionEntry.COLUMN_ID + ") " +
            "ON DELETE CASCADE," +
            "FOREIGN KEY(" + ChemistryContract.SoluteEntry.COLUMN_CATION_ID + ") " +
            "REFERENCES " + ChemistryContract.CationEntry.TABLE_NAME + "(" + ChemistryContract.CationEntry.COLUMN_ID + ") " +
            "ON DELETE CASCADE)";

    //[DROP] table anion
    private static final String SQL_DELETE_ANION =
            "DROP TABLE IF EXISTS " + ChemistryContract.AnionEntry.TABLE_NAME;

    //[DROP] table cation
    private static final String SQL_DELETE_CATION =
            "DROP TABLE IF EXISTS " + ChemistryContract.CationEntry.TABLE_NAME;

    //[DROP] table solute
    private static final String SQL_DELETE_SOLUTE =
            "DROP TABLE IF EXISTS " + ChemistryContract.SoluteEntry.TABLE_NAME;

    /*
     * TABLES REACTIVITY SERIES
     */

    //{CREATE] table react_series
    private static final String SQL_CREATE_REACT_SERIES = "CREATE TABLE " + ChemistryContract.ReactSeriesEntry.TABLE_NAME + " (" +
            ChemistryContract.ReactSeriesEntry.COLUMN_ID + " INTEGER PRIMARY KEY ," +
            ChemistryContract.ReactSeriesEntry.COLUMN_ION + " TEXT ," +
            ChemistryContract.ReactSeriesEntry.COLUMN_VALENCE + " TEXT)";

    //[DROP] table react_series
    private static final String SQL_DELETE_REACT_SERIES =
            "DROP TABLE IF EXISTS " + ChemistryContract.ReactSeriesEntry.TABLE_NAME;

    /*
     * SEARCH CHEMISTRY
     */

    //[CREATE] table compound
    private static final String SQL_CREATE_COMPOUND =
            "CREATE TABLE " + ChemistryContract.CompoundEntry.TABLE_NAME + " (" +
                    ChemistryContract.CompoundEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    ChemistryContract.CompoundEntry.COLUMN_OTHER_NAMES + " TEXT, " +
                    "FOREIGN KEY(" + ChemistryContract.CompoundEntry.COLUMN_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table Produced by
    private static final String SQL_CREATE_PRODUCED_BY =
            "CREATE TABLE " + ChemistryContract.ProducedByEntry.TABLE_NAME + " (" +
                    ChemistryContract.ProducedByEntry.COLUMN_RIGHT_REACTION_ID + " INTEGER, " +
                    ChemistryContract.ProducedByEntry.COLUMN_LEFT_REACTION_ID + " INTEGER, " +
                    "PRIMARY KEY(" + ChemistryContract.ProducedByEntry.COLUMN_RIGHT_REACTION_ID + ", " +
                    ChemistryContract.ProducedByEntry.COLUMN_LEFT_REACTION_ID + "), " +
                    "FOREIGN KEY(" + ChemistryContract.ProducedByEntry.COLUMN_RIGHT_REACTION_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.ProducedByEntry.COLUMN_LEFT_REACTION_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table Chemical Reaction
    private static final String SQL_CREATE_CHEMICAL_REACTION =
            "CREATE TABLE " + ChemistryContract.ChemicalReactionEntry.TABLE_NAME + " (" +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_CHEMICAL_REACTION_ID + " INTEGER PRIMARY KEY, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_REACTANTS + " TEXT, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_PRODUCTS + " TEXT, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_CONDITIONS + " TEXT, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_PHENOMENA + " TEXT, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_TWO_WAY + " INTEGER, " +
                    ChemistryContract.ChemicalReactionEntry.COLUMN_REACTION_TYPES + " TEXT) ";

    //[CREATE] table React With
    private static final String SQL_CREATE_REACT_WITH =
            "CREATE TABLE " + ChemistryContract.ReactWithEntry.TABLE_NAME + " (" +
                    ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_1_ID + " INTEGER, " +
                    ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_2_ID + " INTEGER, " +
                    ChemistryContract.ReactWithEntry.COLUMN_CHEMICAL_REACTION_ID + " INTEGER, " +
                    "PRIMARY KEY(" + ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_1_ID + ", " +
                    ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_2_ID + ", " +
                    ChemistryContract.ReactWithEntry.COLUMN_CHEMICAL_REACTION_ID + "), " +
                    "FOREIGN KEY(" + ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_1_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_2_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.ReactWithEntry.COLUMN_CHEMICAL_REACTION_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemicalReactionEntry.TABLE_NAME + "(" + ChemistryContract.ChemicalReactionEntry.COLUMN_CHEMICAL_REACTION_ID + ") " +
                    "ON DELETE CASCADE)";

    //[CREATE] table Created Reaction
    private static final String SQL_CREATE_CREATED_REACTION =
            "CREATE TABLE " + ChemistryContract.CreatedReactionEntry.TABLE_NAME + " (" +
                    ChemistryContract.CreatedReactionEntry.COLUMN_CREATE_RIGHT_ID + " INTEGER, " +
                    ChemistryContract.CreatedReactionEntry.COLUMN_CHEMICAL_REACTION_ID + " INTEGER, " +
                    "PRIMARY KEY(" + ChemistryContract.CreatedReactionEntry.COLUMN_CREATE_RIGHT_ID + ", " +
                    ChemistryContract.CreatedReactionEntry.COLUMN_CHEMICAL_REACTION_ID + "), " +
                    "FOREIGN KEY(" + ChemistryContract.CreatedReactionEntry.COLUMN_CREATE_RIGHT_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemistryEntry.TABLE_NAME + "(" + ChemistryContract.ChemistryEntry.COLUMN_ID + ") " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(" + ChemistryContract.CreatedReactionEntry.COLUMN_CHEMICAL_REACTION_ID + ") " +
                    "REFERENCES " + ChemistryContract.ChemicalReactionEntry.TABLE_NAME + "(" + ChemistryContract.ChemicalReactionEntry.COLUMN_CHEMICAL_REACTION_ID + ") " +
                    "ON DELETE CASCADE)";

    //[DROP] table compound
    private static final String SQL_DELETE_COMPOUND =
            "DROP TABLE IF EXISTS " + ChemistryContract.CompoundEntry.TABLE_NAME;

    //[DROP] table produced by
    private static final String SQL_DELETE_PRODUCED_BY =
            "DROP TABLE IF EXISTS " + ChemistryContract.ProducedByEntry.TABLE_NAME;

    //[DROP] table React With
    private static final String SQL_DELETE_CHEMICAL_REACTION =
            "DROP TABLE IF EXISTS " + ChemistryContract.ChemicalReactionEntry.TABLE_NAME;

    //[DROP] table Chemical Reaction
    private static final String SQL_DELETE_REACT_WITH =
            "DROP TABLE IF EXISTS " + ChemistryContract.ReactWithEntry.TABLE_NAME;

    //[DROP] table Created Reaction
    private static final String SQL_DELETE_CREATED_REACTION =
            "DROP TABLE IF EXISTS " + ChemistryContract.CreatedReactionEntry.TABLE_NAME;


    /*
     * FUNCTION UES IN DATABASE
     */

    //[EMPTY DATA]
    public void emptyType() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.TypeEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyChemistry() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ChemistryEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyGroup() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.GroupEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyElement() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ElementEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyCompound() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.CompoundEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyProducedBy() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ProducedByEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyChemicalReaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ChemicalReactionEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyReactWith() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ReactWithEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyCreatedReaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.CreatedReactionEntry.TABLE_NAME, null, null);
        db.close();
    }


    public void emptyAnion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.AnionEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyCation() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.CationEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptySolute() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.SoluteEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyReactSeries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ReactSeriesEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyBlock() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.BlockEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyChapter() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.ChapterEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyTypeOfQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.TypeOfQuestionEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyAnswer() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.AnswerEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.QuestionEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void emptyAnswerByQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChemistryContract.AnswerByQuestionEntry.TABLE_NAME, null, null);
        db.close();
    }

    //GAME
    //{Add}
    public void addBlock(Block block) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.BlockEntry.COLUMN_BLOCK_ID, block.getIdBlock());

        db.insert(ChemistryContract.BlockEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addChapter(Chapter chapter) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ChapterEntry.COLUMN_CHAPTER_ID, chapter.getIdChapter());
        values.put(ChemistryContract.ChapterEntry.COLUMN_CHAPTER_NAME, chapter.getNameChapter());
        values.put(ChemistryContract.ChapterEntry.COLUMN_BLOCK_ID, chapter.getIdBlock());
        values.put(ChemistryContract.ChapterEntry.COLUMN_CHAPTER_CONTENT, chapter.getContentChapter());

        db.insert(ChemistryContract.ChapterEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addTypeOfQuestion(TypeOfQuestion typeOfQuestion) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_ID, typeOfQuestion.getIdType());
        values.put(ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_NAME, typeOfQuestion.getNameType());
        values.put(ChemistryContract.TypeOfQuestionEntry.COLUMN_TYPE_DESCRIPTION, typeOfQuestion.getDescription());

        db.insert(ChemistryContract.TypeOfQuestionEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addAnswer(Answer answer) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.AnswerEntry.COLUMN_ANSWER_ID, answer.getIdAnswer());
        values.put(ChemistryContract.AnswerEntry.COLUMN_ANSWER_CONTENT, answer.getContentAnswer());

        db.insert(ChemistryContract.AnswerEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addQuestion(Question question) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.QuestionEntry.COLUMN_QUESTION_ID, question.getIdQuestion());
        values.put(ChemistryContract.QuestionEntry.COLUMN_QUESTION_CONTENT, question.getContentQuestion());
        values.put(ChemistryContract.QuestionEntry.COLUMN_LEVEL_ID, question.getIdLevel());
        values.put(ChemistryContract.QuestionEntry.COLUMN_BLOCK_ID, question.getIdBlock());
        values.put(ChemistryContract.QuestionEntry.COLUMN_TYPE_ID, question.getIdType());
        values.put(ChemistryContract.QuestionEntry.COLUMN_EXTENT, question.getExtent());

        db.insert(ChemistryContract.QuestionEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addAnswerByQuestion(AnswerByQuestion answerByQuestion) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.AnswerByQuestionEntry.COLUMN_QUESTION_ID, answerByQuestion.getIdQuestion());
        values.put(ChemistryContract.AnswerByQuestionEntry.COLUMN_ANSWER_ID, answerByQuestion.getIdAnswer());
        values.put(ChemistryContract.AnswerByQuestionEntry.COLUMN_CORRECT, answerByQuestion.getCorrect());

        db.insert(ChemistryContract.AnswerByQuestionEntry.TABLE_NAME, null, values);
        db.close();
    }

    //[Get all]
    public List<Block> getAllBlock() {
        List<Block> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.BlockEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Block block;
        while (cursor.moveToNext()) {
            block = new Block();
            block.setIdBlock(Integer.parseInt(cursor.getString(0)));
            list.add(block);

        }
        cursor.close();

        return list;
    }

    public List<Chapter> getAllChapter() {
        List<Chapter> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ChapterEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Chapter chapter;
        while (cursor.moveToNext()) {
            chapter = new Chapter();
            chapter.setIdChapter(Integer.parseInt(cursor.getString(0)));
            chapter.setNameChapter(cursor.getString(1));
            chapter.setIdBlock(Integer.parseInt(cursor.getString(2)));
            chapter.setContentChapter(cursor.getString(3));
            list.add(chapter);

        }
        cursor.close();

        return list;
    }

    public List<TypeOfQuestion> getAllTypeOfQuestion() {
        List<TypeOfQuestion> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.TypeOfQuestionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        TypeOfQuestion typeOfQuestion;
        while (cursor.moveToNext()) {
            typeOfQuestion = new TypeOfQuestion();
            typeOfQuestion.setIdType(Integer.parseInt(cursor.getString(0)));
            typeOfQuestion.setNameType(cursor.getString(1));
            typeOfQuestion.setDescription(cursor.getString(2));
            list.add(typeOfQuestion);

        }
        cursor.close();

        return list;
    }

    public List<Answer> getAllAnswer() {
        List<Answer> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.AnswerEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Answer answer;
        while (cursor.moveToNext()) {
            answer = new Answer();
            answer.setIdAnswer(Integer.parseInt(cursor.getString(0)));
            answer.setContentAnswer(cursor.getString(1));
            list.add(answer);

        }
        cursor.close();

        return list;
    }

    public List<Question> getAllQuestion() {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.QuestionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Question question;
        while (cursor.moveToNext()) {
            question = new Question();
            question.setIdQuestion(Integer.parseInt(cursor.getString(0)));
            question.setContentQuestion(cursor.getString(1));
            question.setIdLevel(Integer.parseInt(cursor.getString(2)));
            question.setIdBlock(Integer.parseInt(cursor.getString(3)));
            question.setIdType(Integer.parseInt(cursor.getString(4)));
            question.setExtent(Integer.parseInt(cursor.getString(5)));
            list.add(question);

        }
        cursor.close();

        return list;
    }

    public List<AnswerByQuestion> getAllAnswerByQuestion() {
        List<AnswerByQuestion> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.AnswerByQuestionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        AnswerByQuestion answerByQuestion;
        while (cursor.moveToNext()) {
            answerByQuestion = new AnswerByQuestion();
            answerByQuestion.setIdQuestion(Integer.parseInt(cursor.getString(0)));
            answerByQuestion.setIdAnswer(Integer.parseInt(cursor.getString(1)));
            answerByQuestion.setCorrect(Integer.parseInt(cursor.getString(2)));
            list.add(answerByQuestion);

        }
        cursor.close();

        return list;
    }

    //Get Questions by Level
    public List<Question> getQuestionsByLevel(int block, int type, int level) {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = ChemistryContract.QuestionEntry.COLUMN_BLOCK_ID + " =  ? and "
                + ChemistryContract.QuestionEntry.COLUMN_TYPE_ID + " = ? and "
                + ChemistryContract.QuestionEntry.COLUMN_LEVEL_ID + " = ?";
        String selectionArgs[] = {String.valueOf(block), String.valueOf(type), String.valueOf(level)};
        Cursor cursor = db.query(
                ChemistryContract.QuestionEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Question question;
        while (cursor.moveToNext()) {
            question = new Question();
            question.setIdQuestion(Integer.parseInt(cursor.getString(0)));
            question.setContentQuestion(cursor.getString(1));
            question.setIdLevel(Integer.parseInt(cursor.getString(2)));
            question.setIdBlock(Integer.parseInt(cursor.getString(3)));
            question.setIdType(Integer.parseInt(cursor.getString(4)));
            question.setExtent(Integer.parseInt(cursor.getString(5)));
            list.add(question);
        }

        cursor.close();
        return list;
    }

    //PERIODIC TABLE
    //{Add]
    public void addType(Type type) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.TypeEntry.COLUMN_ID, type.getIdType());
        values.put(ChemistryContract.TypeEntry.COLUMN_NAME, type.getNameType());

        db.insert(ChemistryContract.TypeEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addChemistry(Chemistry chemistry) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ChemistryEntry.COLUMN_ID, chemistry.getIdChemistry());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_TYPE_ID, chemistry.getIdType());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_SYMBOL, chemistry.getSymbolChemistry());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_NAME, chemistry.getNameChemistry());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_STATUS, chemistry.getStatusChemistry());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_COLOR, chemistry.getColorChemistry());
        values.put(ChemistryContract.ChemistryEntry.COLUMN_WEIGHT, chemistry.getWeightChemistry());

        db.insert(ChemistryContract.ChemistryEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addGroup(Group group) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.GroupEntry.COLUMN_ID, group.getIdGroup());
        values.put(ChemistryContract.GroupEntry.COLUMN_NAME, group.getNameGroup());

        db.insert(ChemistryContract.GroupEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addElement(Element element) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ElementEntry.COLUMN_ID, element.getIdElement());
        values.put(ChemistryContract.ElementEntry.COLUMN_GROUP_ID, element.getIdGroup());
        values.put(ChemistryContract.ElementEntry.COLUMN_MOLECULAR_FORMULA, element.getMolecularFormula());
        values.put(ChemistryContract.ElementEntry.COLUMN_PERIOD, element.getPeriod());
        values.put(ChemistryContract.ElementEntry.COLUMN_CLASS, element.getClassElement());
        values.put(ChemistryContract.ElementEntry.COLUMN_NEUTRON, element.getNeutron());
        values.put(ChemistryContract.ElementEntry.COLUMN_SIMPLE_CONFIG, element.getSimplifiedConfiguration());
        values.put(ChemistryContract.ElementEntry.COLUMN_CONFIG, element.getConfiguration());
        values.put(ChemistryContract.ElementEntry.COLUMN_SHELL, element.getShell());
        values.put(ChemistryContract.ElementEntry.COLUMN_ELECTRONEGATIVITY, element.getElectronegativity());
        values.put(ChemistryContract.ElementEntry.COLUMN_VALENCE, element.getValence());
        values.put(ChemistryContract.ElementEntry.COLUMN_ENGLISH_NAME, element.getEnglishName());
        values.put(ChemistryContract.ElementEntry.COLUMN_MELTING_POINT, element.getMeltingPoint());
        values.put(ChemistryContract.ElementEntry.COLUMN_BOILING_POINT, element.getBoilingPoint());
        values.put(ChemistryContract.ElementEntry.COLUMN_DISCOVERER, element.getDiscoverer());
        values.put(ChemistryContract.ElementEntry.COLUMN_YEAR_DISCOVERY, element.getYearDiscovery());
        values.put(ChemistryContract.ElementEntry.COLUMN_ISOTOPES, element.getIsotopes());
        values.put(ChemistryContract.ElementEntry.COLUMN_PICTURE, element.getPicture());

        db.insert(ChemistryContract.ElementEntry.TABLE_NAME, null, values);
        db.close();
    }


    //[Get all]
    public List<Type> getAllTypes() {
        List<Type> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.TypeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Type type;
        while (cursor.moveToNext()) {
            type = new Type();
            type.setIdType(Integer.parseInt(cursor.getString(0)));
            type.setNameType(cursor.getString(1));
            list.add(type);

        }
        cursor.close();

        return list;
    }

    public List<Chemistry> getAllChemistry() {
        List<Chemistry> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ChemistryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Chemistry chemistry;
        while (cursor.moveToNext()) {
            chemistry = new Chemistry();
            chemistry.setIdChemistry(Integer.parseInt(cursor.getString(0)));
            chemistry.setIdType(Integer.parseInt(cursor.getString(1)));
            chemistry.setSymbolChemistry(cursor.getString(2));
            chemistry.setNameChemistry(cursor.getString(3));
            chemistry.setStatusChemistry(cursor.getString(4));
            chemistry.setColorChemistry(cursor.getString(5));
            chemistry.setWeightChemistry(Double.parseDouble(cursor.getString(6)));
            list.add(chemistry);

        }
        cursor.close();

        return list;
    }

    public List<Group> getAllGroups() {
        List<Group> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.GroupEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Group group;
        while (cursor.moveToNext()) {
            group = new Group();
            group.setIdGroup(Integer.parseInt(cursor.getString(0)));
            group.setNameGroup(cursor.getString(1));
            list.add(group);

        }
        cursor.close();

        return list;
    }

    public List<Element> getAllElements() {
        List<Element> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ElementEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Element element;
        while (cursor.moveToNext()) {
            element = new Element();
            element.setIdElement(Integer.parseInt(cursor.getString(0)));
            element.setIdGroup(Integer.parseInt(cursor.getString(1)));
            element.setMolecularFormula(cursor.getString(2));
            element.setPeriod(Integer.parseInt(cursor.getString(3)));
            element.setClassElement(cursor.getString(4));
            element.setNeutron(Integer.parseInt(cursor.getString(5)));
            element.setSimplifiedConfiguration(cursor.getString(6));
            element.setConfiguration(cursor.getString(7));
            element.setShell(cursor.getString(8));
            element.setElectronegativity(Double.parseDouble(cursor.getString(9)));
            element.setValence(cursor.getString(10));
            element.setEnglishName(cursor.getString(11));
            element.setMeltingPoint(Double.parseDouble(cursor.getString(12)));
            element.setBoilingPoint(Double.parseDouble(cursor.getString(13)));
            element.setDiscoverer(cursor.getString(14));
            element.setYearDiscovery(cursor.getString(15));
            element.setIsotopes(cursor.getString(16));
            element.setPicture(cursor.getString(17));

            list.add(element);
        }
        cursor.close();

        return list;
    }

    //ANTN
    //Get Chemistry by id
    public Chemistry getChemistryById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = ChemistryContract.ChemistryEntry.COLUMN_ID + " =  ?";
        String selectionArgs[] = {String.valueOf(id)};
        Cursor cursor = db.query(
                ChemistryContract.ChemistryEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Chemistry chemistry = null;
        if (cursor.moveToFirst()) {

            chemistry = new Chemistry();
            chemistry.setIdChemistry(Integer.parseInt(cursor.getString(0)));
            chemistry.setIdType(Integer.parseInt(cursor.getString(1)));
            chemistry.setSymbolChemistry(cursor.getString(2));
            chemistry.setNameChemistry(cursor.getString(3));
            chemistry.setStatusChemistry(cursor.getString(4));
            chemistry.setColorChemistry(cursor.getString(5));
            chemistry.setWeightChemistry(Double.parseDouble(cursor.getString(6)));
        }

        cursor.close();
        return chemistry;
    }

    //SEARCH CHEMISTRY
    //[Add]
    public void addCompound(Compound compound) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.CompoundEntry.COLUMN_ID, compound.getIdCompound());
        values.put(ChemistryContract.CompoundEntry.COLUMN_OTHER_NAMES, compound.getOtherNames());

        db.insert(ChemistryContract.CompoundEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addProducedBy(ProducedBy producedBy) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ProducedByEntry.COLUMN_RIGHT_REACTION_ID, producedBy.getIdRightReaction());
        values.put(ChemistryContract.ProducedByEntry.COLUMN_LEFT_REACTION_ID, producedBy.getIdLeftReaction());

        db.insert(ChemistryContract.ProducedByEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addChemicalReaction(ChemicalReaction chemicalReaction) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_CHEMICAL_REACTION_ID, chemicalReaction.getIdChemicalReaction());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_REACTANTS, chemicalReaction.getReactants());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_PRODUCTS, chemicalReaction.getProducts());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_CONDITIONS, chemicalReaction.getConditions());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_PHENOMENA, chemicalReaction.getPhenomena());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_TWO_WAY, chemicalReaction.getTwoWay());
        values.put(ChemistryContract.ChemicalReactionEntry.COLUMN_REACTION_TYPES, chemicalReaction.getReactionTypes());

        db.insert(ChemistryContract.ChemicalReactionEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addReactWith(ReactWith reactWith) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_1_ID, reactWith.getIdChemistry_1());
        values.put(ChemistryContract.ReactWithEntry.COLUMN_CHEMISTRY_2_ID, reactWith.getIdChemistry_2());
        values.put(ChemistryContract.ReactWithEntry.COLUMN_CHEMICAL_REACTION_ID, reactWith.getIdChemicalReaction());

        db.insert(ChemistryContract.ReactWithEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addCreatedReaction(CreatedReaction createdReaction) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.CreatedReactionEntry.COLUMN_CREATE_RIGHT_ID, createdReaction.getIdCreatedRight());
        values.put(ChemistryContract.CreatedReactionEntry.COLUMN_CHEMICAL_REACTION_ID, createdReaction.getIdChemicalReaction());

        db.insert(ChemistryContract.CreatedReactionEntry.TABLE_NAME, null, values);
        db.close();
    }

    //[Get all]
    public List<Compound> getAllCompound() {
        List<Compound> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.CompoundEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Compound compound;
        while (cursor.moveToNext()) {
            compound = new Compound();
            compound.setIdCompound(Integer.parseInt(cursor.getString(0)));
            compound.setOtherNames(cursor.getString(1));
            list.add(compound);

        }
        cursor.close();

        return list;
    }

    public List<ProducedBy> getAllProducedBy() {
        List<ProducedBy> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ProducedByEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ProducedBy producedBy;
        while (cursor.moveToNext()) {
            producedBy = new ProducedBy();
            producedBy.setIdRightReaction(Integer.parseInt(cursor.getString(0)));
            producedBy.setIdLeftReaction(Integer.parseInt(cursor.getString(1)));
            list.add(producedBy);

        }
        cursor.close();

        return list;
    }

    public List<ChemicalReaction> getAllChemicalReaction() {
        List<ChemicalReaction> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ChemicalReactionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ChemicalReaction chemicalReaction;
        while (cursor.moveToNext()) {
            chemicalReaction = new ChemicalReaction();
            chemicalReaction.setIdChemicalReaction(Integer.parseInt(cursor.getString(0)));
            chemicalReaction.setReactants((cursor.getString(1)));
            chemicalReaction.setProducts((cursor.getString(2)));
            chemicalReaction.setConditions((cursor.getString(3)));
            chemicalReaction.setPhenomena((cursor.getString(4)));
            chemicalReaction.setTwoWay(Integer.parseInt(cursor.getString(5)));
            chemicalReaction.setReactionTypes((cursor.getString(6)));
            list.add(chemicalReaction);

        }
        cursor.close();

        return list;
    }

    public List<ReactWith> getAllReactWith() {
        List<ReactWith> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ReactWithEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ReactWith reactWith;
        while (cursor.moveToNext()) {
            reactWith = new ReactWith();
            reactWith.setIdChemistry_1(Integer.parseInt(cursor.getString(0)));
            reactWith.setIdChemistry_2(Integer.parseInt(cursor.getString(1)));
            reactWith.setIdChemicalReaction(Integer.parseInt(cursor.getString(2)));
            list.add(reactWith);

        }
        cursor.close();

        return list;
    }

    public List<CreatedReaction> getAllCreatedReaction() {
        List<CreatedReaction> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.CreatedReactionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        CreatedReaction createdReaction;
        while (cursor.moveToNext()) {
            createdReaction = new CreatedReaction();
            createdReaction.setIdCreatedRight(Integer.parseInt(cursor.getString(0)));
            createdReaction.setIdChemicalReaction(Integer.parseInt(cursor.getString(1)));
            list.add(createdReaction);

        }
        cursor.close();

        return list;
    }

    //SOLUBILITY TABLE
    //[Add]
    public void addAnion(Anion anion) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.AnionEntry.COLUMN_ID, anion.getIdAnion());
        values.put(ChemistryContract.AnionEntry.COLUMN_NAME, anion.getNameAnion());
        values.put(ChemistryContract.AnionEntry.COLUMN_VALENCE, anion.getValenceAnion());

        db.insert(ChemistryContract.AnionEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addCation(Cation cation) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.CationEntry.COLUMN_ID, cation.getIdCation());
        values.put(ChemistryContract.CationEntry.COLUMN_NAME, cation.getNameCation());
        values.put(ChemistryContract.CationEntry.COLUMN_VALENCE, cation.getValenceCation());

        db.insert(ChemistryContract.CationEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addSolute(Solute solute) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.SoluteEntry.COLUMN_ANION_ID, solute.getAnion());
        values.put(ChemistryContract.SoluteEntry.COLUMN_CATION_ID, solute.getCation());
        values.put(ChemistryContract.SoluteEntry.COLUMN_SOLUTE, solute.getSolute());

        db.insert(ChemistryContract.SoluteEntry.TABLE_NAME, null, values);
        db.close();
    }

    //[Get all]
    public List<Anion> getAllAnion() {
        List<Anion> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.AnionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Anion anion;
        while (cursor.moveToNext()) {
            anion = new Anion();
            anion.setIdAnion(Integer.parseInt(cursor.getString(0)));
            anion.setNameAnion(cursor.getString(1));
            anion.setValenceAnion(cursor.getString(2));
            list.add(anion);

        }
        cursor.close();

        return list;
    }

    public List<Cation> getAllCation() {
        List<Cation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.CationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Cation cation;
        while (cursor.moveToNext()) {
            cation = new Cation();
            cation.setIdCation(Integer.parseInt(cursor.getString(0)));
            cation.setNameCation(cursor.getString(1));
            cation.setValenceCation(cursor.getString(2));
            list.add(cation);

        }
        cursor.close();

        return list;
    }

    public List<Solute> getAllSolute() {
        List<Solute> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.SoluteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Solute solute;
        while (cursor.moveToNext()) {
            solute = new Solute();
            solute.setAnion(Integer.parseInt(cursor.getString(0)));
            solute.setCation(Integer.parseInt(cursor.getString(1)));
            solute.setSolute(cursor.getString(2));
            list.add(solute);

        }
        cursor.close();

        return list;
    }

    //REACTIVITY TABLE
    //[Add]
    public void addReactSeries(ReactSeries reactSeries) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChemistryContract.ReactSeriesEntry.COLUMN_ID, reactSeries.getIdReactSeries());
        values.put(ChemistryContract.ReactSeriesEntry.COLUMN_ION, reactSeries.getIon());
        values.put(ChemistryContract.ReactSeriesEntry.COLUMN_VALENCE, reactSeries.getValence());

        db.insert(ChemistryContract.ReactSeriesEntry.TABLE_NAME, null, values);
        db.close();
    }

    //[Get all]
    public List<ReactSeries> getAllReactSeries() {
        List<ReactSeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ChemistryContract.ReactSeriesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ReactSeries reactSeries;
        while (cursor.moveToNext()) {
            reactSeries = new ReactSeries();
            reactSeries.setIdReactSeries(Integer.parseInt(cursor.getString(0)));
            reactSeries.setIon(cursor.getString(1));
            reactSeries.setValence(cursor.getString(2));
            list.add(reactSeries);

        }
        cursor.close();

        return list;
    }

    //    Solution - 1
//    private static final String SQL_CREATE_CHEMISTRY = "CREATE TABLE chemistry (chemistry_id INTEGER PRIMARY KEY," +
//            " chemistry_type_id INTEGER NOT NULL CONSTRAINT chemistry_type_id REFERENCES type(type_id) ON DELETE CASCADE," +
//            " chemistry_name TEXT)";

}
