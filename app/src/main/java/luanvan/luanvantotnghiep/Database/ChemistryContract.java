package luanvan.luanvantotnghiep.Database;

import android.provider.BaseColumns;


final class ChemistryContract {

    private ChemistryContract() {
    }

    static class TypeEntry implements BaseColumns {
        static final String TABLE_NAME = "type";
        static final String COLUMN_ID = "type_id";
        static final String COLUMN_NAME = "type_name";
    }

    static class ChemistryEntry implements BaseColumns {
        static final String TABLE_NAME = "chemistry";
        static final String COLUMN_ID = "chemistry_id";
        static final String COLUMN_TYPE_ID = "chemistry_type_id";
        static final String COLUMN_SYMBOL = "chemistry_symbol";
        static final String COLUMN_NAME = "chemistry_name";
        static final String COLUMN_STATUS = "chemistry_status";
        static final String COLUMN_COLOR = "chemistry_color";
        static final String COLUMN_WEIGHT = "chemistry_weight";
    }

    static class GroupEntry implements BaseColumns {
        static final String TABLE_NAME = "group_table";
        static final String COLUMN_ID = "group_id";
        static final String COLUMN_NAME = "group_name";
    }

    static class ElementEntry implements BaseColumns {
        static final String TABLE_NAME = "element";
        static final String COLUMN_ID = "element_id";
        static final String COLUMN_GROUP_ID = "element_group_id";
        static final String COLUMN_MOLECULAR_FORMULA = "element_molecular_formula";
        static final String COLUMN_PERIOD = "element_period";
        static final String COLUMN_CLASS = "element_class";
        static final String COLUMN_NEUTRON = "element_neutron";
        static final String COLUMN_SIMPLE_CONFIG = "element_simple_config";
        static final String COLUMN_CONFIG = "element_config";
        static final String COLUMN_SHELL = "element_shell";
        static final String COLUMN_ELECTRONEGATIVITY = "element_electronegativity";
        static final String COLUMN_VALENCE = "element_valence";
        static final String COLUMN_ENGLISH_NAME = "element_english_name";
        static final String COLUMN_MELTING_POINT = "element_melting_point";
        static final String COLUMN_BOILING_POINT = "element_boiling_point";
        static final String COLUMN_DISCOVERER = "element_discoverer";
        static final String COLUMN_YEAR_DISCOVERY = "element_year_discovery";
        static final String COLUMN_ISOTOPES = "element_isotopes";
        static final String COLUMN_PICTURE = "element_picture";
    }

    static class CompoundEntry implements BaseColumns {
        static final String TABLE_NAME = "compound";
        static final String COLUMN_ID = "compound_id";
        static final String COLUMN_OTHER_NAMES = "compound_other_names";
    }

    static class AnionEntry implements BaseColumns {
        static final String TABLE_NAME = "anion";
        static final String COLUMN_ID = "anion_id";
        static final String COLUMN_NAME = "anion_name";
        static final String COLUMN_VALENCE = "anion_valence";
    }

    static class CationEntry implements BaseColumns {
        static final String TABLE_NAME = "cation";
        static final String COLUMN_ID = "cation_id";
        static final String COLUMN_NAME = "cation_name";
        static final String COLUMN_VALENCE = "cation_valence";
    }

    static class SoluteEntry implements BaseColumns {
        static final String TABLE_NAME = "solute";
        static final String COLUMN_ANION_ID = "solute_anion_id";
        static final String COLUMN_CATION_ID = "solute_cation_id";
        static final String COLUMN_SOLUTE = "solute";
    }

    static class ReactSeriesEntry implements BaseColumns {
        static final String TABLE_NAME = "react_series";
        static final String COLUMN_ID = "react_series_id";
        static final String COLUMN_ION = "react_series_ion";
        static final String COLUMN_VALENCE = "react_series_valence";
    }

    static class ProducedByEntry implements BaseColumns {
        static final String TABLE_NAME = "produced_by";
        static final String COLUMN_LEFT_REACTION_ID = "left_reaction_id";
        static final String COLUMN_RIGHT_REACTION_ID = "right_reaction_id";
    }

    static class ChemicalReactionEntry implements BaseColumns {
        static final String TABLE_NAME = "chemical_reaction";
        static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
        static final String COLUMN_REACTANTS = "reactants";
        static final String COLUMN_PRODUCTS = "products";
        static final String COLUMN_CONDITIONS = "conditions";
        static final String COLUMN_PHENOMENA = "phenomena";
        static final String COLUMN_TWO_WAY = "two_way";
        static final String COLUMN_REACTION_TYPES = "reaction_types";
    }

    static class ReactWithEntry implements BaseColumns {
        static final String TABLE_NAME = "react_with";
        static final String COLUMN_CHEMISTRY_1_ID = "chemistry_1_id";
        static final String COLUMN_CHEMISTRY_2_ID = "chemistry_2_id";
        static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
    }

    static class CreatedReactionEntry implements BaseColumns {
        static final String TABLE_NAME = "created_reaction";
        static final String COLUMN_CREATE_RIGHT_ID = "created_right_id";
        static final String COLUMN_CHEMICAL_REACTION_ID = "chemical_reaction_id";
    }

    static class BlockEntry implements BaseColumns {
        static final String TABLE_NAME = "block";
        static final String COLUMN_BLOCK_ID = "block_id";
    }

    //Game
    static class TypeOfQuestionEntry implements BaseColumns {
        static final String TABLE_NAME = "type_of_question";
        static final String COLUMN_TYPE_ID = "type_id";
        static final String COLUMN_TYPE_NAME = "type_name";
        static final String COLUMN_TYPE_DESCRIPTION = "type_description";
    }

    static class AnswerEntry implements BaseColumns {
        static final String TABLE_NAME = "answer";
        static final String COLUMN_ANSWER_ID = "answer_id";
        static final String COLUMN_ANSWER_CONTENT = "answer_content";
    }

    static class QuestionEntry implements BaseColumns {
        static final String TABLE_NAME = "question";
        static final String COLUMN_QUESTION_ID = "question_id";
        static final String COLUMN_QUESTION_CONTENT = "question_content";
        static final String COLUMN_LEVEL_ID = "level_id";
        static final String COLUMN_BLOCK_ID = "block_id";
        static final String COLUMN_TYPE_ID = "type_id";
        static final String COLUMN_EXTENT = "extent";
    }

    static class AnswerByQuestionEntry implements BaseColumns {
        static final String TABLE_NAME = "answer_by_question";
        static final String COLUMN_QUESTION_ID = "question_id";
        static final String COLUMN_ANSWER_ID = "answer_id";
        static final String COLUMN_CORRECT = "correct";
    }

    //Thematic
    static class ChapterEntry implements BaseColumns {
        static final String TABLE_NAME = "chapter";
        static final String COLUMN_CHAPTER_ID = "chapter_id";
        static final String COLUMN_CHAPTER_NAME = "chapter_name";
        static final String COLUMN_BLOCK_ID = "block_id";
        static final String COLUMN_CHAPTER_CONFIRM = "chapter_confirm";
    }

    static class HeadingEntry implements BaseColumns {
        static final String TABLE_NAME = "heading";
        static final String COLUMN_HEADING_ID = "heading_id";
        static final String COLUMN_CHAPTER_ID = "chapter_id";
        static final String COLUMN_HEADING_NAME = "heading_name";
        static final String COLUMN_SORT_ORDER = "sort_order";
    }

    static class TitleEntry implements BaseColumns {
        static final String TABLE_NAME = "title";
        static final String COLUMN_TITLE_ID = "title_id";
        static final String COLUMN_HEADING_ID = "heading_id";
        static final String COLUMN_TITLE_NAME = "title_name";
        static final String COLUMN_SORT_ORDER = "sort_order";
    }

    static class DescriptionEntry implements BaseColumns {
        static final String TABLE_NAME = "description";
        static final String COLUMN_DESCRIPTION_ID = "description_id";
        static final String COLUMN_TYPE_OF_DESCRIPTION_ID = "type_of_description_id";
        static final String COLUMN_DESCRIPTION_NAME = "description_name";
        static final String COLUMN_SORT_ORDER = "sort_order";
    }

    static class DescriptionOfChapterEntry implements BaseColumns {
        static final String TABLE_NAME = "description_of_chapter";
        static final String COLUMN_CHAPTER_ID = "chapter_id";
        static final String COLUMN_DESCRIPTION_ID = "description_id";
    }

    static class DescriptionOfHeadingEntry implements BaseColumns {
        static final String TABLE_NAME = "description_of_heading";
        static final String COLUMN_HEADING_ID = "heading_id";
        static final String COLUMN_DESCRIPTION_ID = "description_id";
    }

    static class DescriptionOfTitleEntry implements BaseColumns {
        static final String TABLE_NAME = "description_of_title";
        static final String COLUMN_TITLE_ID = "title_id";
        static final String COLUMN_DESCRIPTION_ID = "description_id";
    }
}