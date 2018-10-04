package luanvan.luanvantotnghiep.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String NAME = "CNHH_PRE";

    private static PreferencesManager sManager;
    private SharedPreferences mPreferences;

    private PreferencesManager() {
    }

    public static PreferencesManager getInstance() {
        if (sManager == null) {
            sManager = new PreferencesManager();
        }
        return sManager;
    }

    public void init(Context context){
        mPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public int getIntData(String key, int defValue){
        return mPreferences.getInt(key, defValue);
    }

    public void saveIntData(String key, int value){
        mPreferences.edit().putInt(key, value).apply();
    }
}
