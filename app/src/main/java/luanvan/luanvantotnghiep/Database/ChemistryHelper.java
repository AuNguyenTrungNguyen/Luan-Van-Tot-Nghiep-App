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
import luanvan.luanvantotnghiep.Model.Cation;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.ReactSeries;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.Model.Type;

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
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*
            PERIODIC TABLE
         */
        db.execSQL(SQL_DELETE_ELEMENT);
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

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

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
            ChemistryContract.ReactSeriesEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ChemistryContract.ReactSeriesEntry.COLUMN_ION + " TEXT ," +
            ChemistryContract.ReactSeriesEntry.COLUMN_VALENCE + " TEXT)";

    //[DROP] table react_series
    private static final String SQL_DELETE_REACT_SERIES =
            "DROP TABLE IF EXISTS " + ChemistryContract.ReactSeriesEntry.TABLE_NAME;

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
