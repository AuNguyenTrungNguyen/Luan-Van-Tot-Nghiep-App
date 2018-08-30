package luanvan.luanvantotnghiep.Util;

import android.content.Context;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;

public class ChemistrySingle {

    private static ChemistryHelper sChemistryHelper = null;

    private ChemistrySingle(){}

    public static ChemistryHelper getInstance(Context context) {
        if(sChemistryHelper == null){
            sChemistryHelper = new ChemistryHelper(context);
        }
        return sChemistryHelper;
    }
}
