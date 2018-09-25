package luanvan.luanvantotnghiep.Database;

import android.provider.BaseColumns;


public final class ChemistryContract {

    private ChemistryContract() {
    }

    public static class TypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "type";
        public static final String COLUMN_ID = "type_id";
        public static final String COLUMN_NAME = "type_name";
    }

    public static class ChemistryEntry implements BaseColumns {
        public static final String TABLE_NAME = "chemistry";
        public static final String COLUMN_ID = "chemistry_id";
        public static final String COLUMN_TYPE_ID = "chemistry_type_id";
        public static final String COLUMN_SYMBOL = "chemistry_symbol";
        public static final String COLUMN_NAME = "chemistry_name";
        public static final String COLUMN_STATUS = "chemistry_status";
        public static final String COLUMN_COLOR = "chemistry_color";
        public static final String COLUMN_WEIGHT = "chemistry_weight";
    }

    public static class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "group_table";
        public static final String COLUMN_ID = "group_id";
        public static final String COLUMN_NAME = "group_name";
    }

    public static class ElementEntry implements BaseColumns {
        public static final String TABLE_NAME = "element";
        public static final String COLUMN_ID = "element_id";
        public static final String COLUMN_GROUP_ID = "element_group_id";
        public static final String COLUMN_MOLECULAR_FORMULA = "element_molecular_formula";
        public static final String COLUMN_PERIOD = "element_period";
        public static final String COLUMN_CLASS = "element_class";
        public static final String COLUMN_NEUTRON = "element_neutron";
        public static final String COLUMN_SIMPLE_CONFIG = "element_simple_config";
        public static final String COLUMN_CONFIG = "element_config";
        public static final String COLUMN_SHELL = "element_shell";
        public static final String COLUMN_ELECTRONEGATIVITY = "element_electronegativity";
        public static final String COLUMN_VALENCE = "element_valence";
        public static final String COLUMN_ENGLISH_NAME = "element_english_name";
        public static final String COLUMN_MELTING_POINT = "element_melting_point";
        public static final String COLUMN_BOILING_POINT = "element_boiling_point";
        public static final String COLUMN_DISCOVERER = "element_discoverer";
        public static final String COLUMN_YEAR_DISCOVERY = "element_year_discovery";
        public static final String COLUMN_ISOTOPES = "element_isotopes";
        public static final String COLUMN_PICTURE = "element_picture";
    }

    public static class CompoundEntry implements BaseColumns {
        public static final String TABLE_NAME = "compound";
        public static final String COLUMN_ID = "compound_id";
        public static final String COLUMN_OTHER_NAMES = "compound_other_names";
    }

    public static class AnionEntry implements BaseColumns {
        public static final String TABLE_NAME = "anion";
        public static final String COLUMN_ID = "anion_id";
        public static final String COLUMN_NAME = "anion_name";
        public static final String COLUMN_VALENCE = "anion_valence";
    }

    public static class CationEntry implements BaseColumns {
        public static final String TABLE_NAME = "cation";
        public static final String COLUMN_ID = "cation_id";
        public static final String COLUMN_NAME = "cation_name";
        public static final String COLUMN_VALENCE = "cation_valence";
    }

    public static class SoluteEntry implements BaseColumns {
        public static final String TABLE_NAME = "solute";
        public static final String COLUMN_ANION_ID = "solute_anion_id";
        public static final String COLUMN_CATION_ID = "solute_cation_id";
        public static final String COLUMN_SOLUTE = "solute";
    }

    public static class ReactSeriesEntry implements BaseColumns {
        public static final String TABLE_NAME = "react_series";
        public static final String COLUMN_ID = "react_series_id";
        public static final String COLUMN_ION = "react_series_ion";
        public static final String COLUMN_VALENCE = "react_series_valence";
    }

    public static class ProducedByEntry implements BaseColumns {
        public static final String TABLE_NAME = "produced_by";
        public static final String COLUMN_LEFT_REACTION_ID = "left_reaction_id";
        public static final String COLUMN_RIGHT_REACTION_ID = "right_reaction_id";
    }

    public static class ChemicalReactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "chemical_reaction";
        public static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
        public static final String COLUMN_REACTANTS = "reactants";
        public static final String COLUMN_PRODUCTS = "products";
        public static final String COLUMN_CONDITIONS = "conditions";
        public static final String COLUMN_PHENOMENA = "phenomena";
        public static final String COLUMN_TWO_WAY= "two_way";
        public static final String COLUMN_REACTION_TYPES= "reaction_types";
    }

    public static class ReactWithEntry implements BaseColumns {
        public static final String TABLE_NAME = "react_with";
        public static final String COLUMN_CHEMISTRY_1_ID = "chemistry_1_id";
        public static final String COLUMN_CHEMISTRY_2_ID = "chemistry_2_id";
        public static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
    }

    public static class CreatedReactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "created_reaction";
        public static final String COLUMN_CREATE_RIGHT_ID= "created_right_id";
        public static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
    }

}