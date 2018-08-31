package luanvan.luanvantotnghiep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Fragment.MainFragment;
import luanvan.luanvantotnghiep.Fragment.PeriodicTableFragment;
import luanvan.luanvantotnghiep.Fragment.ReactivitySeriesFragment;
import luanvan.luanvantotnghiep.Fragment.SolubilityTableFragment;
import luanvan.luanvantotnghiep.Model.Anion;
import luanvan.luanvantotnghiep.Model.Cation;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private Toolbar mToolbarMain;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private NavigationView mNavigationRight;

    private ChemistryHelper mChemistryHelper;

    private Fragment mFragmentToSet = null;

    private MenuItem mMnRight = null;
    private Bundle mBundle = new Bundle();
    private int mData = 0;
    private int mLastClick = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        init();

        setupNavigate();

        //Load MainFragment to MainActivity
        mTransaction.replace(R.id.container, MainFragment.newInstance());
        mTransaction.commit();

        //Data use PERIODIC_TABLE
        addDataTypeTable();

        addDataChemistryTable();

        addDataGroupTable();

        addDataElementTable();

        //Data use PERIODIC_TABLE
        addDataAnionTable();

        addDataCationTable();

        addDataSoluteTable();

    }

    private void addDataTypeTable() {
        List<Type> typeList = new ArrayList<>();

        Type type;
        type = new Type(1, "Kim loại kiềm");
        typeList.add(type);

        type = new Type(2, "Kim loại kiềm thổ");
        typeList.add(type);

        type = new Type(3, "Kim loại yếu");
        typeList.add(type);

        type = new Type(4, "Á kim");
        typeList.add(type);

        type = new Type(5, "Kim loại chuyển tiếp");
        typeList.add(type);

        type = new Type(6, "Phi kim");
        typeList.add(type);

        type = new Type(7, "Halogen");
        typeList.add(type);

        type = new Type(8, "Khí trơ");
        typeList.add(type);

        type = new Type(9, "Họ Lantan");
        typeList.add(type);

        type = new Type(10, "Họ Actini");
        typeList.add(type);

        type = new Type(11, "Thuộc tính hóa học chưa rõ");
        typeList.add(type);

        //Check and add data
        if (typeList.size() == mChemistryHelper.getAllTypes().size()) {
            Log.i("ANTN", "Table Type available");
        } else {
            //Add to database
            mChemistryHelper.emptyType();
            for (Type item : typeList) {
                mChemistryHelper.addType(item);
            }
        }
    }

    private void addDataChemistryTable() {

        List<Chemistry> chemistryList = new ArrayList<>();

        Chemistry chemical;
        chemical = new Chemistry(
                1,
                6,
                "H",
                "Hidro",
                "Khí",
                "Không màu",
                1
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                2,
                8,
                "He",
                "Heli",
                "Khí",
                "Không màu",
                4
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                3,
                1,
                "Li",
                "Liti",
                "Rắn",
                "Trắng bạc",
                7
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                4,
                2,
                "Be",
                "Beri",
                "Rắn",
                "Ánh kim trắng xám",
                9
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                5,
                4,
                "B",
                "Bo",
                "Rắn",
                "Đen nâu",
                11
        );
        chemistryList.add(chemical);
        chemical = new Chemistry(
                6,
                6,
                "C",
                "Cacbon",
                "Rắn",
                "trong suốt (kim cương) và đen (than chì)",
                12
        );
        chemistryList.add(chemical);
        chemical = new Chemistry(
                7,
                6,
                "N",
                "Nitơ",
                "Khí",
                "Không màu",
                14
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                8,
                6,
                "O",
                "Oxi",
                "Khí",
                "không màu, trong suốt",
                16
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                9,
                7,
                "F",
                "Flo",
                "Khí",
                "Màu vàng lục nhạt",
                19
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                10,
                8,
                "Ne",
                "Neon",
                "Khí",
                "Không màu",
                20
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                11,
                1,
                "Na",
                "Natri",
                "Rắn",
                "Ánh kim trắng bạc",
                23
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                12,
                2,
                "Mg",
                "Magie",
                "Rắn",
                "Ánh kim xám",
                24
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                13,
                3,
                "Al",
                "Nhôm",
                "Rắn",
                "Ánh kim trắng bạc",
                27
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                14,
                4,
                "Si",
                "Silic",
                "Rắn",
                "Ánh kim xám sẫm ánh xanh",
                28
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                15,
                6,
                "P",
                "Photpho",
                "Rắn",
                "Không màu, trắng sáp, đỏ tươi hơi vàng, đỏ, tím, đen",
                31
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                16,
                6,
                "S",
                "Lưu huỳnh",
                "Rắn",
                "Vàng chanh",
                32
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                17,
                7,
                "Cl",
                "Clo",
                "Khí",
                "Vàng lục nhạt",
                35.5
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                18,
                8,
                "Ar",
                "Argon",
                "Khí",
                "Không màu",
                40
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                19,
                1,
                "K",
                "Kali",
                "Rắn",
                "Ánh kim trắng bạc",
                39
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                20,
                2,
                "Ca",
                "Caxi",
                "Rắn",
                "Ánh kim xám bạc",
                40
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                21,
                5,
                "Sc",
                "Scandi",
                "Rắn",
                "Ánh kim trắng bạc",
                45
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                22,
                5,
                "Ti",
                "TiTan",
                "Rắn",
                "Ánh kim bạc xám-trắng",
                48
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                23,
                5,
                "V",
                "Vanadi",
                "Rắn",
                "Ánh kim xanh bạc xám",
                51
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                24,
                5,
                "Cr",
                "Crom",
                "Rắn",
                "Ánh bạc",
                52
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                25,
                5,
                "Mn",
                "Mangan",
                "Rắn",
                "Ánh kim bạc",
                55
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                26,
                5,
                "Fe",
                "Sắt",
                "Rắn",
                "Ánh kim xám nhẹ",
                56
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                27,
                5,
                "Co",
                "Coban",
                "Rắn",
                "Ánh kim xám nhẹ",
                59
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                28,
                5,
                "Ni",
                "Niken",
                "Rắn",
                "Ánh kim bạc ánh vàng",
                59
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                29,
                5,
                "Cu",
                "Đồng",
                "Rắn",
                "Ánh kim đỏ cam",
                64
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                30,
                5,
                "Zn",
                "Kẽm",
                "Rắn",
                "Ánh kim bạc xám",
                65
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                31,
                3,
                "Ga",
                "Gali",
                "Rắn",
                "Ánh kim bạc trắng",
                70
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                32,
                4,
                "Ge",
                "Gecmani",
                "Rắn",
                "Ánh kim xám trắng",
                73
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                33,
                4,
                "As",
                "Asen",
                "Rắn",
                "Ánh kim xám",
                75
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                34,
                6,
                "Se",
                "Selen",
                "Rắn",
                "Có ba màu đen, xám và đỏ",
                79
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                35,
                7,
                "Br",
                "Brom",
                "Lỏng",
                "Đỏ nâu",
                80
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                36,
                8,
                "Kr",
                "Krypton",
                "Khí",
                "Không màu",
                84
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                37,
                1,
                "Rb",
                "Rubiđi",
                "Rắn",
                "Xám trắng",
                85
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                38,
                2,
                "Sr",
                "Stronti",
                "Rắn",
                "Ánh kim bạc trắng",
                88
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                39,
                5,
                "Y",
                "Yttri",
                "Rắn",
                "Bạc trắng",
                89
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                40,
                5,
                "Zr",
                "Zirconi",
                "Rắn",
                "Bạc trắng",
                91
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                41,
                5,
                "Nb",
                "Niobi",
                "Rắn",
                "Ánh kim xám",
                93
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                42,
                5,
                "Mo",
                "Molypden",
                "Rắn",
                "Ánh kim xám",
                96
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                43,
                5,
                "Tc",
                "Tecneti",
                "Rắn",
                "Ánh kim xám bóng",
                98
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                44,
                5,
                "Ru",
                "Rutheni",
                "Rắn",
                "Ánh kim bạc trắng",
                101
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                45,
                5,
                "Rh",
                "Rhodi",
                "Rắn",
                "Ánh kim bạc trắng",
                103
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                46,
                5,
                "Pd",
                "Paladi",
                "Rắn",
                "Ánh kim bạc trắng",
                106
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                47,
                5,
                "Ag",
                "Bạc",
                "Rắn",
                "Ánh kim trắng bóng",
                108
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                48,
                5,
                "Cd",
                "Cadimi",
                "Rắn",
                "Ánh kim bạc hơi xanh xám",
                112
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                49,
                3,
                "In",
                "Indi",
                "Rắn",
                "Bạc xám bóng",
                115
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                50,
                3,
                "Sn",
                "Thiếc",
                "Rắn",
                "Bạc hoặc xám",
                119
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                51,
                4,
                "Sb",
                "Antimon",
                "Rắn",
                "Bạc hoặc xám",
                122
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                52,
                4,
                "Te",
                "Telua",
                "Rắn",
                "Bạc xám bóng",
                128
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                53,
                7,
                "I",
                "Iốt",
                "Rắn",
                "Ánh kim xám bóng khi ở thể rắn, tím khi ở thể khí",
                127
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                54,
                8,
                "Xe",
                "Xenon",
                "Khí",
                "Không màu",
                131
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                55,
                1,
                "Cs",
                "Xêsi",
                "Rắn",
                "Bạc ngà",
                133
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                56,
                2,
                "Ba",
                "Bari",
                "Rắn",
                "Bạc xám",
                137
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                57,
                5,
                "La",
                "Lanta",
                "Rắn",
                "Bạc trắng",
                139
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                58,
                9,
                "Ce",
                "Xeri",
                "Rắn",
                "Bạc trắng",
                140
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                59,
                9,
                "Pr",
                "Praseodymi",
                "Rắn",
                "Xám trắng",
                141
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                60,
                9,
                "Nd",
                "Neodymi",
                "Rắn",
                "Bạc trắng",
                144
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                61,
                9,
                "Nd",
                "Neodymi",
                "Rắn",
                "Ánh kim",
                145
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                62,
                9,
                "Sm",
                "Neodymi",
                "Rắn",
                "Bạc trắng",
                150
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                63,
                9,
                "Eu",
                "Europi",
                "Rắn",
                "Bạc trắng",
                152
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                64,
                9,
                "Gd",
                "Gadolini",
                "Rắn",
                "Bạc trắng",
                157
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                65,
                9,
                "Tb",
                "Terbi",
                "Rắn",
                "Bạc trắng",
                159
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                66,
                9,
                "Dy",
                "Dysprosi",
                "Rắn",
                "Bạc trắng",
                162
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                67,
                9,
                "Ho",
                "Holmi",
                "Rắn",
                "Bạc trắng",
                165
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                68,
                9,
                "Er",
                "Erbi",
                "Rắn",
                "Bạc trắng",
                167
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                69,
                9,
                "Tm",
                "Thuli",
                "Rắn",
                "Bạc xám",
                169
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                70,
                9,
                "Yb",
                "Ytterbi",
                "Rắn",
                "Bạc trắng",
                173
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                71,
                9,
                "Lu",
                "Luteti",
                "Rắn",
                "Bạc trắng",
                175
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                72,
                5,
                "Hf",
                "Hafni",
                "Rắn",
                "Thép xám",
                178
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                73,
                5,
                "Ta",
                "TanTan",
                "Rắn",
                "Xám xanh",
                181
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                74,
                5,
                "W",
                "Wolfram",
                "Rắn",
                "Xám trắng bóng",
                184
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                75,
                5,
                "Re",
                "Rheni",
                "Rắn",
                "Xám trắng",
                186
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                76,
                5,
                "Os",
                "Osmi",
                "Rắn",
                "Bạc ánh xanh",
                190
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                77,
                5,
                "Ir",
                "Iridi",
                "Rắn",
                "Bạc trắng",
                192
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                78,
                5,
                "Pt",
                "Platin",
                "Rắn",
                "Bạc trắng",
                195
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                79,
                5,
                "Au",
                "Vàng",
                "Rắn",
                "Ánh kim vàng",
                197
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                80,
                5,
                "Hg",
                "Thủy ngân",
                "Lỏng",
                "Ánh bạc",
                201
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                81,
                3,
                "Tl",
                "Tali",
                "Rắn",
                "Bạc trắng",
                204
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                82,
                3,
                "Pb",
                "Chì",
                "Rắn",
                "Ánh kim xám",
                207
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                83,
                3,
                "Bi",
                "Bismut",
                "Rắn",
                "Bạc bóng",
                209
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                84,
                4,
                "Po",
                "Poloni",
                "Rắn",
                "Bạc",
                209
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                85,
                7,
                "At",
                "Astatin",
                "Rắn",
                "Đen",
                210
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                86,
                8,
                "Rn",
                "Radon",
                "Khí",
                "Không màu",
                222
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                87,
                1,
                "Fr",
                "Franxi",
                "Rắn",
                "Ánh kim",
                223
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                88,
                2,
                "Ra",
                "Radi",
                "Rắn",
                "Ánh kim bạc trắng",
                226
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                89,
                5,
                "Ac",
                "Actini",
                "Rắn",
                "Bạc",
                227
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                90,
                10,
                "Th",
                "Thori",
                "Rắn",
                "Bạc, thường có màu đen xỉn",
                232
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                91,
                10,
                "Pa",
                "Protactini",
                "Rắn",
                "Bạc, thường có màu đen xỉn",
                232
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                92,
                10,
                "U",
                "Urani",
                "Rắn",
                "Xám bạc",
                238
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                93,
                10,
                "Np",
                "Neptuni",
                "Rắn",
                "Ánh kim bạc trắng",
                237
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                94,
                10,
                "Pu",
                "Plutoni",
                "Rắn",
                "Trắng bạc",
                244
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                95,
                10,
                "Am",
                "Americi",
                "Rắn",
                "Ánh kim trắng bạc",
                243
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                96,
                10,
                "Cm",
                "Curi",
                "Rắn",
                "Bạc",
                247
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                97,
                10,
                "Bk",
                "Berkeli",
                "Rắn",
                "Ánh kim bạc",
                247
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                98,
                10,
                "Cf",
                "Californi",
                "Rắn",
                "Ánh kim bạc",
                251
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                99,
                10,
                "Es",
                "Einsteini",
                "Rắn",
                "Ánh bạc, phát sáng trong bóng tối",
                252
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                100,
                10,
                "Fm",
                "Fermi",
                "Chưa xác định",
                "",
                257
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                101,
                10,
                "Md",
                "Mendelevi",
                "Chưa xác định",
                "",
                258
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                102,
                10,
                "No",
                "Nobeli",
                "Chưa xác định",
                "",
                259
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                103,
                10,
                "Lr",
                "Lawrenci",
                "Chưa xác định",
                "",
                262
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                104,
                5,
                "Rf",
                "Rutherfordi",
                "Chưa xác định",
                "",
                262
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                105,
                5,
                "Db",
                "Dubni",
                "Chưa xác định",
                "",
                268
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                106,
                5,
                "Sg",
                "Seaborgi",
                "Chưa xác định",
                "",
                269
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                107,
                5,
                "Bh",
                "Bohri",
                "Chưa xác định",
                "",
                270
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                108,
                5,
                "Hs",
                "Hassi",
                "Chưa xác định",
                "",
                269
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                109,
                11,
                "Mt",
                "Meitneri",
                "Chưa xác định",
                "",
                278
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                110,
                11,
                "Ds",
                "Darmstadti",
                "Chưa xác định",
                "",
                281
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                111,
                11,
                "Rg",
                "Roentgeni",
                "Chưa xác định",
                "",
                281
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                112,
                5,
                "Cn",
                "Copernixi",
                "Chưa xác định",
                "",
                285
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                113,
                11,
                "Nh",
                "Nihoni",
                "Chưa xác định",
                "",
                286
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                114,
                11,
                "Fl",
                "Flerovi",
                "Chưa xác định",
                "",
                289
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                115,
                11,
                "Mc",
                "Moscovi",
                "Chưa xác định",
                "",
                289
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                116,
                11,
                "Lv",
                "Livermori",
                "Chưa xác định",
                "",
                293
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                117,
                11,
                "Ts",
                "Tennessine",
                "Chưa xác định",
                "Á kim",
                293
        );
        chemistryList.add(chemical);

        chemical = new Chemistry(
                118,
                11,
                "Og",
                "Oganesson",
                "Chưa xác định",
                "",
                293
        );
        chemistryList.add(chemical);

        //Check and add data
        if (chemistryList.size() == mChemistryHelper.getAllChemistry().size()) {
            Log.i("ANTN", "Table Chemistry available");
        } else {
            //Add database
            mChemistryHelper.emptyChemistry();
            for (Chemistry item : chemistryList) {
                mChemistryHelper.addChemistry(item);
            }
        }
    }

    private void addDataGroupTable() {

        List<Group> groupList = new ArrayList<>();

        Group group;
        group = new Group(1, "IA");
        groupList.add(group);

        group = new Group(2, "IIA");
        groupList.add(group);

        group = new Group(3, "IIIB");
        groupList.add(group);

        group = new Group(4, "IVB");
        groupList.add(group);

        group = new Group(5, "VB");
        groupList.add(group);

        group = new Group(6, "VIB");
        groupList.add(group);

        group = new Group(7, "VIIB");
        groupList.add(group);

        group = new Group(8, "VIIIB");
        groupList.add(group);

        group = new Group(9, "VIIIB");
        groupList.add(group);

        group = new Group(10, "VIIIB");
        groupList.add(group);

        group = new Group(11, "IB");
        groupList.add(group);

        group = new Group(12, "IIB");
        groupList.add(group);

        group = new Group(13, "IIIA");
        groupList.add(group);

        group = new Group(14, "IVA");
        groupList.add(group);

        group = new Group(15, "VA");
        groupList.add(group);

        group = new Group(16, "VIA");
        groupList.add(group);

        group = new Group(17, "VIIA");
        groupList.add(group);

        group = new Group(18, "VIIIA");
        groupList.add(group);

        //Check and add data
        if (groupList.size() == mChemistryHelper.getAllGroups().size()) {
            Log.i("ANTN", "Table Group available");
        } else {
            mChemistryHelper.emptyGroup();
            //Add to database
            for (Group item : groupList) {
                mChemistryHelper.addGroup(item);
            }
        }
    }

    private void addDataElementTable() {

        List<Element> elementList = new ArrayList<>();

        Element element;
        element = new Element();
        element.setIdElement(1);
        element.setMolecularFormula("H2");
        element.setPeriod(1);
        element.setClassElement("s");
        element.setNeutron(0);
        element.setSimplifiedConfiguration("1s<small><sup>1</sup></small>");
        element.setConfiguration("1s1");
        element.setShell("K1");
        element.setElectronegativity(2.300);
        element.setValence("<b>1</b>, <b>-1</b>");
        element.setEnglishName("Hydrogen");
        element.setMeltingPoint(-259.16);
        element.setBoilingPoint(-252.879);
        element.setDiscoverer("Henry Cavendish");
        element.setYearDiscovery("1766");
        element.setIdGroup(1);
        element.setIsotopes("proti <b><small><sup>1</sup></small>H</b>, " +
                "doteri <b><small><sup>2</sup></small>H</b>");
        element.setPicture("hydrogen");
        elementList.add(element);

        element = new Element();
        element.setIdElement(2);
        element.setMolecularFormula("He");
        element.setPeriod(1);
        element.setClassElement("s");
        element.setNeutron(2);
        element.setSimplifiedConfiguration("1s<small><sup>2</sup></small>");
        element.setConfiguration("1s2");
        element.setShell("K2");
        element.setElectronegativity(4.160);
        element.setValence("0");
        element.setEnglishName("helium");
        element.setMeltingPoint(-272.20);
        element.setBoilingPoint(-268.928);
        element.setDiscoverer("William Ramsay, Per Teodor Cleve, Abraham Langlet");
        element.setYearDiscovery("1895");
        element.setIdGroup(18);
        element.setIsotopes("<b><small><sup>3</sup></small>He</b>, " +
                "<b><small><sup>4</sup></small>H</b> là b?n");
        element.setPicture("helium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(3);
        element.setMolecularFormula("Li");
        element.setPeriod(2);
        element.setClassElement("s");
        element.setNeutron(4);
        element.setSimplifiedConfiguration("[He]2s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s1");
        element.setShell("K2 L1");
        element.setElectronegativity(0.912);
        element.setValence("<b>+1</b>");
        element.setEnglishName("lithium");
        element.setMeltingPoint(180.50);
        element.setBoilingPoint(1330);
        element.setDiscoverer("Johan August Arfwedson");
        element.setYearDiscovery("1817");
        element.setIdGroup(1);
        element.setIsotopes("<b><small><sup>6</sup></small>Li</b>, " +
                "<b><small><sup>7</sup></small>Li</b>");
        element.setPicture("lithium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(4);
        element.setMolecularFormula("Be");
        element.setPeriod(2);
        element.setClassElement("s");
        element.setNeutron(5);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2");
        element.setShell("K2 L2");
        element.setElectronegativity(1.576);
        element.setValence("<b>+2</b>, +1");
        element.setEnglishName("Beryllium");
        element.setMeltingPoint(1287);
        element.setBoilingPoint(2469);
        element.setDiscoverer("Louis Nicolas Vau quelin");
        element.setYearDiscovery("1798");
        element.setIdGroup(2);
        element.setIsotopes("<b><small><sup>9</sup></small>Be</b>");
        element.setPicture("beryllium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(5);
        element.setMolecularFormula("B");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(6);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p1");
        element.setShell("K2 L3");
        element.setElectronegativity(2.051);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Boron");
        element.setMeltingPoint(2076);
        element.setBoilingPoint(3927);
        element.setDiscoverer("Joseph Louis Gay-Lussac và Louis Jacques Thénard");
        element.setYearDiscovery("1808");
        element.setIdGroup(13);
        element.setIsotopes("<b><small><sup>10</sup></small>Bo</b>, " +
                "<b><small><sup>11</sup></small>Bo</b>");
        element.setPicture("boron");
        elementList.add(element);

        element = new Element();
        element.setIdElement(6);
        element.setMolecularFormula("C");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(6);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p2");
        element.setShell("K2 L4");
        element.setElectronegativity(2.544);
        element.setValence("<b>+4</b>, <b>+3</b>, <b>+2</b>, <b>+1</b>, <b>0</b>, <b>-1</b>, <b>-2</b>, <b>-3</b>, <b>-4</b>");
        element.setEnglishName("Carbon");
        element.setMeltingPoint(3526.85);
        element.setBoilingPoint(4026.85);
        element.setDiscoverer("Người Ai Cập và người Sumer");
        element.setYearDiscovery("3750 TCN");
        element.setIdGroup(14);
        element.setIsotopes("<b><small><sup>12</sup></small>C</b>, " +
                "<b><small><sup>13</sup></small>C</b>");
        element.setPicture("carbon");
        elementList.add(element);

        element = new Element();
        element.setIdElement(7);
        element.setMolecularFormula("N2");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(7);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p3");
        element.setShell("K2 L5");
        element.setElectronegativity(3.066);
        element.setValence("<b>+5</b>, <b>+4</b>, <b>+3</b>, <b>+2</b>, <b>+1</b>, -1, -2, <b>-3</b>");
        element.setEnglishName("Nitrogen");
        element.setMeltingPoint(-210.00);
        element.setBoilingPoint(-195.795);
        element.setDiscoverer("Daniel Rutherford");
        element.setYearDiscovery("1772");
        element.setIdGroup(15);
        element.setIsotopes("<b><small><sup>14</sup></small>N</b>, " +
                "<b><small><sup>15</sup></small>N</b>");
        element.setPicture("nitrogen");
        elementList.add(element);

        element = new Element();
        element.setIdElement(8);
        element.setMolecularFormula("O2");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(8);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p4");
        element.setShell("K2 L6");
        element.setElectronegativity(3.610);
        element.setValence("<b>+2</b>, +1, -1, <b>23</b>");
        element.setEnglishName("Oxygen");
        element.setMeltingPoint(-218.79);
        element.setBoilingPoint(-182.962);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1771");
        element.setIdGroup(16);
        element.setIsotopes("<b><small><sup>16</sup></small>O</b>, " +
                "<b><small><sup>17</sup></small>O</b>, " +
                "<b><small><sup>18</sup></small>O</b>");
        element.setPicture("oxygen");
        elementList.add(element);

        element = new Element();
        element.setIdElement(9);
        element.setMolecularFormula("F2");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(10);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p5");
        element.setShell("K2 L7");
        element.setElectronegativity(4.193);
        element.setValence("<b>-1</b>");
        element.setEnglishName("Fluorine");
        element.setMeltingPoint(-219.67);
        element.setBoilingPoint(-188.11);
        element.setDiscoverer("André-Marie Ampère");
        element.setYearDiscovery("1810");
        element.setIdGroup(17);
        element.setIsotopes("<b><small><sup>19</sup></small>F</b>");
        element.setPicture("fluorine");
        elementList.add(element);

        element = new Element();
        element.setIdElement(10);
        element.setMolecularFormula("Ne");
        element.setPeriod(2);
        element.setClassElement("p");
        element.setNeutron(10);
        element.setSimplifiedConfiguration("[He]2s<small><sup>2</sup></small> 2p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6");
        element.setShell("K2 L8");
        element.setElectronegativity(4.787);
        element.setValence("0");
        element.setEnglishName("Neon");
        element.setMeltingPoint(-248.59);
        element.setBoilingPoint(-246.046);
        element.setDiscoverer("William Ramsay và Morris Travers");
        element.setYearDiscovery("1898");
        element.setIdGroup(18);
        element.setIsotopes("<b><small><sup>20</sup></small>Ne</b>, " +
                "<b><small><sup>21</sup></small>Ne</b>, " +
                "<b><small><sup>22</sup></small>Ne</b>");
        element.setPicture("neon");
        elementList.add(element);

        element = new Element();
        element.setIdElement(11);
        element.setMolecularFormula("Ne");
        element.setPeriod(3);
        element.setClassElement("s");
        element.setNeutron(12);
        element.setSimplifiedConfiguration("[Ne]3s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s1");
        element.setShell("K2 L8 M1");
        element.setElectronegativity(0.869);
        element.setValence("<b>+1</b>, 0, -1");
        element.setEnglishName("Sodium");
        element.setMeltingPoint(97.794);
        element.setBoilingPoint(882.940);
        element.setDiscoverer("Humphry Davy");
        element.setYearDiscovery("1807");
        element.setIdGroup(1);
        element.setIsotopes("<b><small><sup>23</sup></small>Na</b>");
        element.setPicture("sodium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(12);
        element.setMolecularFormula("Mg");
        element.setPeriod(3);
        element.setClassElement("s");
        element.setNeutron(12);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2");
        element.setShell("K2 L8 M2");
        element.setElectronegativity(1.293);
        element.setValence("<b>+2</b>, +1");
        element.setEnglishName("Magnesium");
        element.setMeltingPoint(650);
        element.setBoilingPoint(1091);
        element.setDiscoverer("Joseph Black");
        element.setYearDiscovery("1755");
        element.setIdGroup(2);
        element.setIsotopes("<b><small><sup>24</sup></small>Mg</b>, " +
                "<b><small><sup>25</sup></small>Mg</b>, " +
                "<b><small><sup>26</sup></small>Mg</b>");
        element.setPicture("magnesium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(13);
        element.setMolecularFormula("Al");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(14);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p1");
        element.setShell("K2 L8 M3");
        element.setElectronegativity(1.613);
        element.setValence("<b>+3<b>, +2, +1, -1, -2");
        element.setEnglishName("Aluminium");
        element.setMeltingPoint(660.32);
        element.setBoilingPoint(2470);
        element.setDiscoverer("Hans Christian Ørsted");
        element.setYearDiscovery("1824");
        element.setIdGroup(13);
        element.setIsotopes("<b><small><sup>27</sup></small>Al</b>");
        element.setPicture("aluminium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(14);
        element.setMolecularFormula("Si");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(14);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p2");
        element.setShell("K2 L8 M4");
        element.setElectronegativity(1.916);
        element.setValence("<b>+4<b>, +3, +2, +1, -1, -2, -3, -4");
        element.setEnglishName("Silicon");
        element.setMeltingPoint(1414);
        element.setBoilingPoint(3265);
        element.setDiscoverer("Jöns Jacob Berzelius");
        element.setYearDiscovery("1823");
        element.setIdGroup(14);
        element.setIsotopes("<b><small><sup>28</sup></small>Si</b>, " +
                "<b><small><sup>29</sup></small>Si</b>," +
                "<b><small><sup>30</sup></small>Si</b>");
        element.setPicture("silicon");
        elementList.add(element);

        element = new Element();
        element.setIdElement(15);
        element.setMolecularFormula("P");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(16);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p3");
        element.setShell("K2 L8 M5");
        element.setElectronegativity(2.253);
        element.setValence("<b>+5</b>, <b>+4</b>, <b>+3</b>, +2, +1, -1, -2, <b>-3</b>");
        element.setEnglishName("Phosphorus");
        element.setMeltingPoint(44.15);
        element.setBoilingPoint(276.85);
        element.setDiscoverer("Hennig Brand");
        element.setYearDiscovery("1669");
        element.setIdGroup(15);
        element.setIsotopes("<b><small><sup>31</sup></small>P</b>");
        element.setPicture("phosphorus");
        elementList.add(element);

        element = new Element();
        element.setIdElement(16);
        element.setMolecularFormula("S");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(16);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p4");
        element.setShell("K2 L8 M6");
        element.setElectronegativity(2.589);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, +3, <b>+2</b>, +1, -1, <b>-2</b>");
        element.setEnglishName("Sulfur");
        element.setMeltingPoint(115.21);
        element.setBoilingPoint(444.6);
        element.setDiscoverer("Antoine Lavoisier");
        element.setYearDiscovery("1777");
        element.setIdGroup(16);
        element.setIsotopes("<b><small><sup>32</sup></small>S</b>, " +
                "<b><small><sup>33</sup></small>S</b>, " +
                "<b><small><sup>34</sup></small>S</b>, " +
                "<b><small><sup>36</sup></small>S</b>");
        element.setPicture("sulfur");
        elementList.add(element);

        element = new Element();
        element.setIdElement(17);
        element.setMolecularFormula("Cl2");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(18);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p5");
        element.setShell("K2 L8 M7");
        element.setElectronegativity(2.869);
        element.setValence("<b>+7</b>, +6, <b>+5</b>,+4, <b>+3</b>, +2, <b>+1</b>, <b>-1</b>");
        element.setEnglishName("Chlorine");
        element.setMeltingPoint(-101.5);
        element.setBoilingPoint(-34.04);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1774");
        element.setIdGroup(17);
        element.setIsotopes("<b><small><sup>35</sup></small>Cl</b>, " +
                "<b><small><sup>37</sup></small>Cl</b>");
        element.setPicture("chlorine");
        elementList.add(element);

        element = new Element();
        element.setIdElement(18);
        element.setMolecularFormula("Ar");
        element.setPeriod(3);
        element.setClassElement("p");
        element.setNeutron(22);
        element.setSimplifiedConfiguration("[He]3s<small><sup>2</sup></small> 3p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8");
        element.setShell("K2 L8 M8");
        element.setElectronegativity(3.242);
        element.setValence("0");
        element.setEnglishName("Argon");
        element.setMeltingPoint(-189.34);
        element.setBoilingPoint(-185.848);
        element.setDiscoverer("Lord Rayleigh và William Ramsay");
        element.setYearDiscovery("1894");
        element.setIdGroup(18);
        element.setIsotopes("<b><small><sup>36</sup></small>Ar</b>, " +
                "<b><small><sup>38</sup></small>Ar</b>, " +
                "<b><small><sup>40</sup></small>Ar</b>");
        element.setPicture("argon");
        elementList.add(element);

        element = new Element();
        element.setIdElement(19);
        element.setMolecularFormula("K");
        element.setPeriod(4);
        element.setClassElement("s");
        element.setNeutron(20);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s1");
        element.setShell("K2 L8 M8 N1");
        element.setElectronegativity(0.734);
        element.setValence("<b>+1</b>, -1");
        element.setEnglishName("Potassium");
        element.setMeltingPoint(63.5);
        element.setBoilingPoint(759);
        element.setDiscoverer("Humphry Davy");
        element.setYearDiscovery("1807");
        element.setIdGroup(1);
        element.setIsotopes("<b><small><sup>39</sup></small>K</b>, " +
                "<b><small><sup>41</sup></small>K</b>");
        element.setPicture("potassium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(20);
        element.setMolecularFormula("Ca");
        element.setPeriod(4);
        element.setClassElement("s");
        element.setNeutron(20);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s2");
        element.setShell("K2 L8 M8 N2");
        element.setElectronegativity(1.034);
        element.setValence("<b>+2</b>, +1");
        element.setEnglishName("Calcium");
        element.setMeltingPoint(842);
        element.setBoilingPoint(1484);
        element.setDiscoverer("Humphry Davy");
        element.setYearDiscovery("1808");
        element.setIdGroup(2);
        element.setIsotopes("<b><small><sup>40</sup></small>Ca</b>, " +
                "<b><small><sup>42</sup></small>Ca</b>, " +
                "<b><small><sup>43</sup></small>Ca</b>, " +
                "<b><small><sup>44</sup></small>Ca</b>, " +
                "<b><small><sup>46</sup></small>Ca</b>");
        element.setPicture("calcium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(21);
        element.setMolecularFormula("Sc");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(24);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s2 3d1");
        element.setShell("K2 L8 M9 N2");
        element.setElectronegativity(1.19);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Scandium");
        element.setMeltingPoint(1541);
        element.setBoilingPoint(2836);
        element.setDiscoverer("Lars Fredrik Nilson");
        element.setYearDiscovery("1879");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>45</sup></small>Sc</b>");
        element.setPicture("scandium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(22);
        element.setMolecularFormula("Ti");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(26);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s2 3d2");
        element.setShell("K2 L8 M10 N2");
        element.setElectronegativity(1.38);
        element.setValence("<b>+4</b>, +3, +2, +1, -1, -2");
        element.setEnglishName("Titanium");
        element.setMeltingPoint(1668);
        element.setBoilingPoint(3287);
        element.setDiscoverer("William Gregor");
        element.setYearDiscovery("1791");
        element.setIdGroup(4);
        element.setIsotopes("<b><small><sup>46</sup></small>Ti</b>, " +
                "<b><small><sup>47</sup></small>Ti</b>, " +
                "<b><small><sup>48</sup></small>Ti</b>, " +
                "<b><small><sup>49</sup></small>Ti</b>, " +
                "<b><small><sup>50</sup></small>Ti</b>");
        element.setPicture("titanium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(23);
        element.setMolecularFormula("V");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(28);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s2 3d3");
        element.setShell("K2 L8 M11 N2");
        element.setElectronegativity(1.63);
        element.setValence("<b>+5</b>, <b>+4</b>, +3, <b>+2</b>, +1, -1, -3");
        element.setEnglishName("Vanadium");
        element.setMeltingPoint(1910);
        element.setBoilingPoint(3407);
        element.setDiscoverer("Andrés Manuel del Río");
        element.setYearDiscovery("1801");
        element.setIdGroup(5);
        element.setIsotopes("<b><small><sup>51</sup></small>V</b>");
        element.setPicture("vanadium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(24);
        element.setMolecularFormula("Cr");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(28);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>1</sup></small> 3d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s1 3d5");
        element.setShell("K2 L8 M13 N1");
        element.setElectronegativity(1.66);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, -1, -2, -4");
        element.setEnglishName("Chromium");
        element.setMeltingPoint(1907);
        element.setBoilingPoint(2671);
        element.setDiscoverer("Louis Nicolas Vauquelin");
        element.setYearDiscovery("1794");
        element.setIdGroup(6);
        element.setIsotopes("<b><small><sup>50</sup></small>Cr</b>, " +
                "<b><small><sup>52</sup></small>Cr</b>, " +
                "<b><small><sup>53</sup></small>Cr</b>, " +
                "<b><small><sup>54</sup></small>Cr</b>");
        element.setPicture("chromium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(25);
        element.setMolecularFormula("Mn");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(30);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p8 4s2 3d5");
        element.setShell("K2 L8 M13 N2");
        element.setElectronegativity(1.55);
        element.setValence("<b>+7</b>, <b>+6</b>, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, -1, -2, -3");
        element.setEnglishName("Manganese");
        element.setMeltingPoint(1246);
        element.setBoilingPoint(2061);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1774");
        element.setIdGroup(7);
        element.setIsotopes("<b><small><sup>55</sup></small>Mn</b>");
        element.setPicture("manganese");
        elementList.add(element);

        element = new Element();
        element.setIdElement(26);
        element.setMolecularFormula("Fe");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(30);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d6");
        element.setShell("K2 L8 M14 N2");
        element.setElectronegativity(1.83);
        element.setValence("+7, +6, +5, +4, <b>+3</b>, <b>+2</b>, +1, -1, -2, -4");
        element.setEnglishName("Iron");
        element.setMeltingPoint(1538);
        element.setBoilingPoint(2862);
        element.setIdGroup(8);
        element.setDiscoverer("");
        element.setYearDiscovery("Trước 5000 TCN");
        element.setIsotopes("<b><small><sup>54</sup></small>Fe</b>, " +
                "<b><small><sup>56</sup></small>Fe</b>," +
                "<b><small><sup>57</sup></small>Fe</b>, " +
                "<b><small><sup>58</sup></small>Fe</b>");
        element.setPicture("iron");
        elementList.add(element);

        element = new Element();
        element.setIdElement(27);
        element.setMolecularFormula("Co");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(30);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d7");
        element.setShell("K2 L8 M15 N2");
        element.setElectronegativity(1.88);
        element.setValence("+5, +4, <b>+3</b>, <b>+2</b>, +1, -1, -3");
        element.setEnglishName("Cobalt");
        element.setMeltingPoint(1495);
        element.setBoilingPoint(2927);
        element.setDiscoverer("Georg Brandt");
        element.setYearDiscovery("1735");
        element.setIdGroup(9);
        element.setIsotopes("<b><small><sup>59</sup></small>Co</b>");
        element.setPicture("cobalt");
        elementList.add(element);

        element = new Element();
        element.setIdElement(28);
        element.setMolecularFormula("Ni");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(31);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>8</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d8");
        element.setShell("K2 L8 M16 N2");
        element.setElectronegativity(1.91);
        element.setValence("+4, +3, <b>+2</b>, +1, -1, -2");
        element.setEnglishName("Nickel");
        element.setMeltingPoint(1455);
        element.setBoilingPoint(2730);
        element.setDiscoverer("Axel Fredrik Cronstedt");
        element.setYearDiscovery("1751");
        element.setIdGroup(10);
        element.setIsotopes("<b><small><sup>58</sup></small>Ni</b>, " +
                "<b><small><sup>60</sup></small>Ni</b>, " +
                "<b><small><sup>61</sup></small>Ni</b>, " +
                "<b><small><sup>62</sup></small>Ni</b>, " +
                "<b><small><sup>64</sup></small>Ni</b>");
        element.setPicture("nickel");
        elementList.add(element);

        element = new Element();
        element.setIdElement(29);
        element.setMolecularFormula("Cu");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(35);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>1</sup></small> 3d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s1 3d10");
        element.setShell("K2 L8 M18 N1");
        element.setElectronegativity(1.90);
        element.setValence("+4, +3, <b>+2</b>, <b>+1</b>, -2");
        element.setEnglishName("Copper");
        element.setMeltingPoint(1084.62);
        element.setBoilingPoint(2562);
        element.setDiscoverer("Người Trung Ðông");
        element.setYearDiscovery("9000 TCN");
        element.setIdGroup(11);
        element.setIsotopes("<b><small><sup>63</sup></small>Cu</b>, " +
                "<b><small><sup>65</sup></small>Cu</b>");
        element.setPicture("copper");
        elementList.add(element);

        element = new Element();
        element.setIdElement(30);
        element.setMolecularFormula("Zn");
        element.setPeriod(4);
        element.setClassElement("d");
        element.setNeutron(35);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10");
        element.setShell("K2 L8 M18 N2");
        element.setElectronegativity(1.65);
        element.setValence("<b>+2</b>, +1, 0, -2");
        element.setEnglishName("Zinc");
        element.setMeltingPoint(419.53);
        element.setBoilingPoint(907);
        element.setDiscoverer("Các nhà luyện kim Ấn Ðộ");
        element.setYearDiscovery("Trước 1000 TCN");
        element.setIdGroup(12);
        element.setIsotopes("<b><small><sup>64</sup></small>Zn</b>, " +
                "<b><small><sup>66</sup></small>Zn</b>, " +
                "<b><small><sup>67</sup></small>Zn</b>, " +
                "<b><small><sup>68</sup></small>Zn</b>, " +
                "<b><small><sup>70</sup></small>Zn</b>");
        element.setPicture("zinc");
        elementList.add(element);

        element = new Element();
        element.setIdElement(31);
        element.setMolecularFormula("Ga");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(39);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p1");
        element.setShell("K2 L8 M18 N3");
        element.setElectronegativity(1.81);
        element.setValence("<b>+3</b>, +2, +1, -1, -2, -4, -5");
        element.setEnglishName("Gallium");
        element.setMeltingPoint(29.7646);
        element.setBoilingPoint(2400);
        element.setDiscoverer("Lecoq de Boisbaudran");
        element.setYearDiscovery("1875");
        element.setIdGroup(13);
        element.setIsotopes("<b><small><sup>69</sup></small>Ga</b>, " +
                "<b><small><sup>71</sup></small>Ga</b>");
        element.setPicture("gallium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(32);
        element.setMolecularFormula("Ge");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(41);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p2");
        element.setShell("K2 L8 M18 N4");
        element.setElectronegativity(2.01);
        element.setValence("<b>+4</b>, +3, <b>+2</b>, +1, 0, -1, -2, -3, -4");
        element.setEnglishName("Germanium");
        element.setMeltingPoint(938.25);
        element.setBoilingPoint(2833);
        element.setDiscoverer("Clemens Winkler");
        element.setYearDiscovery("1886");
        element.setIdGroup(14);
        element.setIsotopes("<b><small><sup>70</sup></small>Ge</b>, " +
                "<b><small><sup>72</sup></small>Ge</b>, " +
                "<b><small><sup>73</sup></small>Ge</b>, " +
                "<b><small><sup>74</sup></small>Ge</b>");
        element.setPicture("germanium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(33);
        element.setMolecularFormula("As");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(42);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p3");
        element.setShell("K2 L8 M18 N5");
        element.setElectronegativity(2.18);
        element.setValence("+5, <b>+4</b>, +3, <b>+2</b>, +1, 0, -1, -2, -3, -4");
        element.setEnglishName("Arsenic");
        element.setMeltingPoint(816.8);
        element.setBoilingPoint(613);
        element.setIdGroup(15);
        element.setDiscoverer("");
        element.setYearDiscovery("Trước 300 CE");
        element.setIsotopes("<b><small><sup>75</sup></small>As</b>");
        element.setPicture("arsenic");
        elementList.add(element);

        element = new Element();
        element.setIdElement(34);
        element.setMolecularFormula("Se");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(45);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p4");
        element.setShell("K2 L8 M18 N6");
        element.setElectronegativity(2.55);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, +3, +2, +1, -1, <b>-2</b>");
        element.setEnglishName("Selenium");
        element.setMeltingPoint(221);
        element.setBoilingPoint(685);
        element.setDiscoverer("Jöns Jakob Berzelius và Johann Gottlieb Gahn");
        element.setYearDiscovery("1817");
        element.setIdGroup(16);
        element.setIsotopes("<b><small><sup>74</sup></small>Se</b>, " +
                "<b><small><sup>76</sup></small>Se</b>, " +
                "<b><small><sup>77</sup></small>Se</b>, " +
                "<b><small><sup>78</sup></small>Se</b>, " +
                "<b><small><sup>80</sup></small>Se</b>");
        element.setPicture("selenium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(35);
        element.setMolecularFormula("Br");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(45);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p5");
        element.setShell("K2 L8 M18 N7");
        element.setElectronegativity(2.96);
        element.setValence("+7, <b>+5</b>, +4, +3, <b>+1</b>, <b>-1</b>");
        element.setEnglishName("Bromine");
        element.setMeltingPoint(-7.2);
        element.setBoilingPoint(58.8);
        element.setDiscoverer("Antoine Jérôme Balard và Carl Jacob Löwig");
        element.setYearDiscovery("1825");
        element.setIdGroup(17);
        element.setIsotopes("<b><small><sup>79</sup></small>Br</b>, " +
                "<b><small><sup>81</sup></small>Br</b>");
        element.setPicture("bromine");
        elementList.add(element);

        element = new Element();
        element.setIdElement(36);
        element.setMolecularFormula("Kr");
        element.setPeriod(4);
        element.setClassElement("p");
        element.setNeutron(45);
        element.setSimplifiedConfiguration("[Ar]4s<small><sup>2</sup></small> 3d<small><sup>10</sup></small> 4p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6");
        element.setShell("K2 L8 M18 N8");
        element.setElectronegativity(3.00);
        element.setValence("+2, +1, <b>0</b>");
        element.setEnglishName("Krypton");
        element.setMeltingPoint(-157.37);
        element.setBoilingPoint(-153.415);
        element.setDiscoverer("William Ramsay và Morris Travers");
        element.setYearDiscovery("1898");
        element.setIdGroup(18);
        element.setIsotopes("<b><small><sup>80</sup></small>Kr</b>, " +
                "<b><small><sup>82</sup></small>Kr</b>, " +
                "<b><small><sup>83</sup></small>Kr</b>, " +
                "<b><small><sup>84</sup></small>Kr</b>");
        element.setPicture("krypton");
        elementList.add(element);

        element = new Element();
        element.setIdElement(37);
        element.setMolecularFormula("Rb");
        element.setPeriod(5);
        element.setClassElement("s");
        element.setNeutron(48);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1");
        element.setShell("K2 L8 M18 N8 O1");
        element.setElectronegativity(0.82);
        element.setValence("<b>+1</b>, -1");
        element.setEnglishName("Rubidium");
        element.setMeltingPoint(39.30);
        element.setBoilingPoint(688);
        element.setDiscoverer("Robert Bunsen và Gustav Kirchhoff");
        element.setYearDiscovery("1861");
        element.setIdGroup(1);
        element.setIsotopes("<b><small><sup>85</sup></small>Rb</b>");
        element.setPicture("rubidium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(38);
        element.setMolecularFormula("Sr");
        element.setPeriod(5);
        element.setClassElement("s");
        element.setNeutron(49);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2");
        element.setShell("K2 L8 M18 N8 O2");
        element.setElectronegativity(0.95);
        element.setValence("<b>+2</b>, +1");
        element.setEnglishName("Strontium");
        element.setMeltingPoint(777);
        element.setBoilingPoint(1377);
        element.setDiscoverer("William Cruickshank");
        element.setYearDiscovery("1787");
        element.setIdGroup(2);
        element.setIsotopes("<b><small><sup>84</sup></small>Sr</b>, " +
                "<b><small><sup>86</sup></small>Sr</b>, " +
                "<b><small><sup>87</sup></small>Sr</b>, " +
                "<b><small><sup>88</sup></small>Sr</b>");
        element.setPicture("strontium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(39);
        element.setMolecularFormula("Y");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(49);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d1");
        element.setShell("K2 L8 M18 N9 O2");
        element.setElectronegativity(1.22);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Yttrium");
        element.setMeltingPoint(1526);
        element.setBoilingPoint(2930);
        element.setDiscoverer("Johan Gadolin");
        element.setYearDiscovery("1794");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>89</sup></small>Y</b>");
        element.setPicture("yttrium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(40);
        element.setMolecularFormula("Zr");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(51);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d2");
        element.setShell("K2 L8 M18 N10 O2");
        element.setElectronegativity(1.33);
        element.setValence("<b>+4</b>, +3, +2, +1, -2");
        element.setEnglishName("Zirconium");
        element.setMeltingPoint(1855);
        element.setBoilingPoint(4377);
        element.setDiscoverer("Martin Heinrich Klaproth");
        element.setYearDiscovery("1789");
        element.setIdGroup(4);
        element.setIsotopes("<b><small><sup>90</sup></small>Zr</b>, " +
                "<b><small><sup>91</sup></small>Zr</b>, " +
                "<b><small><sup>92</sup></small>Zr</b>, " +
                "<b><small><sup>94</sup></small>Zr</b>");
        element.setPicture("zirconium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(41);
        element.setMolecularFormula("Nb");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(51);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small> 4d<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1 4d4");
        element.setShell("K2 L8 M18 N12 O1");
        element.setElectronegativity(1.33);
        element.setValence("<b>+5</b>, +4, +3, +2, +1, -1, -3");
        element.setEnglishName("Niobium");
        element.setMeltingPoint(2477);
        element.setBoilingPoint(4744);
        element.setDiscoverer("Charles Hatchett");
        element.setYearDiscovery("1801");
        element.setIdGroup(5);
        element.setIsotopes("<b><small><sup>93</sup></small>Nb</b>");
        element.setPicture("niobium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(42);
        element.setMolecularFormula("Mo");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(54);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small> 4d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1 4d5");
        element.setShell("K2 L8 M18 N13 O1");
        element.setElectronegativity(2.16);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, -1, -2, -4");
        element.setEnglishName("Molybdenum");
        element.setMeltingPoint(2623);
        element.setBoilingPoint(4639);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1778");
        element.setIdGroup(6);
        element.setIsotopes("<b><small><sup>92</sup></small>Mo</b>, " +
                "<b><small><sup>94</sup></small>Mo</b>, " +
                "<b><small><sup>95</sup></small>Mo</b>, " +
                "<b><small><sup>96</sup></small>Mo</b>, " +
                "<b><small><sup>97</sup></small>Mo</b>, " +
                "<b><small><sup>98</sup></small>Mo</b>");
        element.setPicture("molybdenum");
        elementList.add(element);

        element = new Element();
        element.setIdElement(43);
        element.setMolecularFormula("Tc");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(55);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d5");
        element.setShell("K2 L8 M18 N13 O2");
        element.setElectronegativity(1.9);
        element.setValence("<b>+7</b>, +6, +5, <b>+4</b>, <b>+3</b>, +2, +1, -1, -3");
        element.setEnglishName("Technetium");
        element.setMeltingPoint(2157);
        element.setBoilingPoint(4265);
        element.setDiscoverer("Emilio Segrè and Carlo Perrier");
        element.setYearDiscovery("1937");
        element.setIdGroup(7);
        element.setIsotopes("Không có");
        element.setPicture("technetium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(44);
        element.setMolecularFormula("Ru");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(58);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small> 4d<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1 4d7");
        element.setShell("K2 L8 M18 N15 O1");
        element.setElectronegativity(2.2);
        element.setValence("<b>+8</b>, +7, +6, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, -2, -4");
        element.setEnglishName("Ruthenium");
        element.setMeltingPoint(2334);
        element.setBoilingPoint(4150);
        element.setDiscoverer("Karl Ernst Claus");
        element.setYearDiscovery("1844");
        element.setIdGroup(8);
        element.setIsotopes("<b><small><sup>96</sup></small>Ru</b>, " +
                "<b><small><sup>98</sup></small>Ru</b>, " +
                "<b><small><sup>99</sup></small>Ru</b>, " +
                "<b><small><sup>100</sup></small>Ru</b>, " +
                "<b><small><sup>101</sup></small>Ru</b>, " +
                "<b><small><sup>102</sup></small>Ru</b>, " +
                "<b><small><sup>104</sup></small>Ru</b>");
        element.setPicture("ruthenium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(45);
        element.setMolecularFormula("Rh");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(57);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small> 4d<small><sup>8</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1 4d8");
        element.setShell("K2 L8 M18 N16 O1");
        element.setElectronegativity(2.28);
        element.setValence("+6, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, -1, -3");
        element.setEnglishName("Rhodium");
        element.setMeltingPoint(1964);
        element.setBoilingPoint(3695);
        element.setDiscoverer("William Hyde Wollaston");
        element.setYearDiscovery("1804");
        element.setIdGroup(9);
        element.setIsotopes("<b><small><sup>103</sup></small>Rh</b>");
        element.setPicture("rhodium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(46);
        element.setMolecularFormula("Pd");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(60);
        element.setSimplifiedConfiguration("[Kr]4d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 4d10");
        element.setShell("K2 L8 M18 N18");
        element.setElectronegativity(2.20);
        element.setValence("<b>+4</b>, +3, <b>+2</b>, +1, 0");
        element.setEnglishName("Palladium");
        element.setMeltingPoint(1554.9);
        element.setBoilingPoint(2963);
        element.setDiscoverer("William Hyde Wollaston");
        element.setYearDiscovery("1802");
        element.setIdGroup(10);
        element.setIsotopes("<b><small><sup>102</sup></small>Ru</b>, " +
                "<b><small><sup>104</sup></small>Ru</b>, " +
                "<b><small><sup>105</sup></small>Ru</b>, " +
                "<b><small><sup>106</sup></small>Ru</b>, " +
                "<b><small><sup>108</sup></small>Ru</b>, " +
                "<b><small><sup>110</sup></small>Ru</b>");
        element.setPicture("palladium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(47);
        element.setMolecularFormula("Ag");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(61);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>1</sup></small> 4d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s1 4d10");
        element.setShell("K2 L8 M18 N18 O1");
        element.setElectronegativity(1.93);
        element.setValence("+3, +2, <b>+1</b>, -1, -2");
        element.setEnglishName("Silver");
        element.setMeltingPoint(961.78);
        element.setBoilingPoint(2162);
        element.setDiscoverer("");
        element.setYearDiscovery("Trước 5000 BC");
        element.setIdGroup(11);
        element.setIsotopes("<b><small><sup>107</sup></small>Ag</b>, " +
                "<b><small><sup>109</sup></small>Ag</b>");
        element.setPicture("silver");
        elementList.add(element);

        element = new Element();
        element.setIdElement(48);
        element.setMolecularFormula("Cd");
        element.setPeriod(5);
        element.setClassElement("d");
        element.setNeutron(64);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10");
        element.setShell("K2 L8 M18 N18 O2");
        element.setElectronegativity(1.69);
        element.setValence("<b>+2</b>, +1, -2");
        element.setEnglishName("Cadmium");
        element.setMeltingPoint(321.07);
        element.setBoilingPoint(767);
        element.setDiscoverer("Karl Samuel Leberecht Hermann và Friedrich Stromeyer");
        element.setYearDiscovery("1817");
        element.setIdGroup(12);
        element.setIsotopes("<b><small><sup>106</sup></small>Cd</b>, " +
                "<b><small><sup>108</sup></small>Cd</b>, " +
                "<b><small><sup>110</sup></small>Cd</b>, " +
                "<b><small><sup>111</sup></small>Cd</b>, " +
                "<b><small><sup>112</sup></small>Cd</b>, " +
                "<b><small><sup>104</sup></small>Cd</b>");
        element.setPicture("cadmium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(49);
        element.setMolecularFormula("In");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(66);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p1");
        element.setShell("K2 L8 M18 N18 O3");
        element.setElectronegativity(1.78);
        element.setValence("<b>+3</b>, +2, <b>+1</b>, -1, -2, -5");
        element.setEnglishName("Indium");
        element.setMeltingPoint(156.5985);
        element.setBoilingPoint(2072);
        element.setDiscoverer("Ferdinand Reich and Hieronymous Theodor Richter");
        element.setYearDiscovery("1863");
        element.setIdGroup(13);
        element.setIsotopes("<b><small><sup>113</sup></small>Cd</b>");
        element.setPicture("indium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(50);
        element.setMolecularFormula("Sn");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(69);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p2");
        element.setShell("K2 L8 M18 N18 O4");
        element.setElectronegativity(1.96);
        element.setValence("<b>+4</b>, +3, <b>+2</b>, +1, -1, -2, -3, -4");
        element.setEnglishName("Tin");
        element.setMeltingPoint(231.93);
        element.setBoilingPoint(2602);
        element.setDiscoverer("");
        element.setYearDiscovery("Khoảng 3500 TCN");
        element.setIdGroup(14);
        element.setIsotopes("<b><small><sup>112</sup></small>Sn</b>, " +
                "<b><small><sup>114</sup></small>Sn</b>, " +
                "<b><small><sup>115</sup></small>Sn</b>, " +
                "<b><small><sup>116</sup></small>Sn</b>, " +
                "<b><small><sup>117</sup></small>Sn</b>, " +
                "<b><small><sup>118</sup></small>Sn</b>, " +
                "<b><small><sup>119</sup></small>Sn</b>, " +
                "<b><small><sup>120</sup></small>Sn</b>, " +
                "<b><small><sup>122</sup></small>Sn</b>, " +
                "<b><small><sup>124</sup></small>Sn</b>");
        element.setPicture("tin");
        elementList.add(element);

        element = new Element();
        element.setIdElement(51);
        element.setMolecularFormula("Sb");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(70);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p3");
        element.setShell("K2 L8 M18 N18 O5");
        element.setElectronegativity(2.05);
        element.setValence("<b>+5</b>, +4>, <b>+3</b>, +2, +1, -1, -2, <b>-3</b>");
        element.setEnglishName("Antimony");
        element.setMeltingPoint(630.63);
        element.setBoilingPoint(1635);
        element.setDiscoverer("");
        element.setYearDiscovery("Trước 800 CE");
        element.setIdGroup(15);
        element.setIsotopes("<b><small><sup>121</sup></small>Sb</b>, " +
                "<b><small><sup>123</sup></small>Sn</b>");
        element.setPicture("antimony");
        elementList.add(element);

        element = new Element();
        element.setIdElement(52);
        element.setMolecularFormula("Te");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(75);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p4");
        element.setShell("K2 L8 M18 N18 O6");
        element.setElectronegativity(2.1);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, +3, +2, +1, -1, <b>-2</b>");
        element.setEnglishName("Tellurium");
        element.setMeltingPoint(449.51);
        element.setBoilingPoint(988);
        element.setDiscoverer("Franz-Joseph Müller von Reichenstein");
        element.setYearDiscovery("1782");
        element.setIdGroup(16);
        element.setIsotopes("<b><small><sup>120</sup></small>Te</b>, " +
                "<b><small><sup>122</sup></small>Te</b>, " +
                "<b><small><sup>123</sup></small>Te</b>, " +
                "<b><small><sup>124</sup></small>Te</b>, " +
                "<b><small><sup>125</sup></small>Te</b>, " +
                "<b><small><sup>126</sup></small>Te</b>");
        element.setPicture("tellurium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(53);
        element.setMolecularFormula("I");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(74);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p5");
        element.setShell("K2 L8 M18 N18 O7");
        element.setElectronegativity(2.66);
        element.setValence("<b>+7</b>, +6, <b>+5</b>, +4, <b>+3</b>, <b>+1</b>, <b>-1</b>");
        element.setEnglishName("Iodine");
        element.setMeltingPoint(113.7);
        element.setBoilingPoint(184.3);
        element.setDiscoverer("Bernard Courtois");
        element.setYearDiscovery("1811");
        element.setIdGroup(17);
        element.setIsotopes("<b><small><sup>127</sup></small>I</b>");
        element.setPicture("iodine");
        elementList.add(element);

        element = new Element();
        element.setIdElement(54);
        element.setMolecularFormula("Xe");
        element.setPeriod(5);
        element.setClassElement("p");
        element.setNeutron(77);
        element.setSimplifiedConfiguration("[Kr]5s<small><sup>2</sup></small> 4d<small><sup>10</sup></small> 5p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6");
        element.setShell("K2 L8 M18 N18 O8");
        element.setElectronegativity(2.60);
        element.setValence("<b>+8</b>, +6, +5, <b>+4</b>, <b>+2</b>, +1, <b>0</b>");
        element.setEnglishName("Xenon");
        element.setMeltingPoint(-111.75);
        element.setBoilingPoint(-108.099);
        element.setDiscoverer("William Ramsay và Morris Travers");
        element.setYearDiscovery("1898");
        element.setIdGroup(18);
        element.setIsotopes("<b><small><sup>124</sup></small>Xe</b>, " +
                "<b><small><sup>126</sup></small>Xe</b>, " +
                "<b><small><sup>128</sup></small>Xe</b>, " +
                "<b><small><sup>129</sup></small>Xe</b>, " +
                "<b><small><sup>130</sup></small>Xe</b>, " +
                "<b><small><sup>131</sup></small>Xe</b>, " +
                "<b><small><sup>132</sup></small>Xe</b>, " +
                "<b><small><sup>134</sup></small>Xe</b>");
        element.setPicture("xenon");
        elementList.add(element);

        element = new Element();
        element.setIdElement(55);
        element.setMolecularFormula("Cs");
        element.setPeriod(6);
        element.setClassElement("s");
        element.setNeutron(78);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 3d10 4s2 4p6 4d10 5s2 5p6 6s1");
        element.setShell("K2 L8 M18 N18 O8 P1");
        element.setElectronegativity(0.79);
        element.setValence("<b>+1</b>, -1");
        element.setEnglishName("Caesium");
        element.setMeltingPoint(28.5);
        element.setBoilingPoint(671);
        element.setDiscoverer("Robert Bunsen và Gustav Kirchhoff");
        element.setYearDiscovery("1860");
        element.setIdGroup(1);
        element.setIsotopes("<b><small><sup>133</sup></small>Cs</b>");
        element.setPicture("caesium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(56);
        element.setMolecularFormula("Ba");
        element.setPeriod(6);
        element.setClassElement("s");
        element.setNeutron(81);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2");
        element.setShell("K2 L8 M18 N18 O8 P2");
        element.setElectronegativity(0.89);
        element.setValence("<b>+2</b>, +1");
        element.setEnglishName("Barium");
        element.setMeltingPoint(727);
        element.setBoilingPoint(1845);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1772");
        element.setIdGroup(2);
        element.setIsotopes("<b><small><sup>132</sup></small>Ba</b>, " +
                "<b><small><sup>134</sup></small>Ba</b>, " +
                "<b><small><sup>135</sup></small>Ba</b>, " +
                "<b><small><sup>136</sup></small>Ba</b>, " +
                "<b><small><sup>137</sup></small>Ba</b>, " +
                "<b><small><sup>138</sup></small>Ba</b>, ");
        element.setPicture("barium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(57);
        element.setMolecularFormula("La");
        element.setPeriod(6);
        element.setClassElement("s");
        element.setNeutron(82);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 5d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 5d1");
        element.setShell("K2 L8 M18 N18 O9 P2");
        element.setElectronegativity(1.1);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Lanthanum");
        element.setMeltingPoint(920);
        element.setBoilingPoint(3464);
        element.setDiscoverer("Carl Gustaf Mosander");
        element.setYearDiscovery("1838");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>139</sup></small>La</b>");
        element.setPicture("lanthanum");
        elementList.add(element);

        element = new Element();
        element.setIdElement(58);
        element.setMolecularFormula("Ce");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(82);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>1</sup></small> 5d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f1 5d1");
        element.setShell("K2 L8 M18 N19 O9 P2");
        element.setElectronegativity(1.12);
        element.setValence("<b>+4</b>, <b>+3</b>, +2, +1");
        element.setEnglishName("Cerium");
        element.setMeltingPoint(795);
        element.setBoilingPoint(3443);
        element.setDiscoverer("Martin Heinrich Klaproth, Jöns Jakob Berzelius và Wilhelm Hisinger");
        element.setYearDiscovery("1803");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>136</sup></small>Ce</b>, " +
                "<b><small><sup>138</sup></small>Ce</b>, " +
                "<b><small><sup>140</sup></small>Ce</b>, " +
                "<b><small><sup>142</sup></small>Ce</b>");
        element.setPicture("cerium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(59);
        element.setMolecularFormula("Pr");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(82);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f3");
        element.setShell("K2 L8 M18 N21 O8 P2");
        element.setElectronegativity(1.13);
        element.setValence("+5, <b>+4</b>, <b>+3</b>, +2");
        element.setEnglishName("Praseodymium");
        element.setMeltingPoint(935);
        element.setBoilingPoint(3130);
        element.setDiscoverer("Carl Auer von Welsbach");
        element.setYearDiscovery("1885");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>141</sup></small>Pr</b>");
        element.setPicture("praseodymium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(60);
        element.setMolecularFormula("Nd");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(84);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f4");
        element.setShell("K2 L8 M18 N22 O8 P2");
        element.setElectronegativity(1.14);
        element.setValence("<b>+4</b>, <b>+3</b>, +2");
        element.setEnglishName("Neodymium");
        element.setMeltingPoint(1024);
        element.setBoilingPoint(3074);
        element.setDiscoverer("Carl Auer von Welsbach");
        element.setYearDiscovery("1885");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>142</sup></small>Nd</b>, " +
                "<b><small><sup>143</sup></small>Nd</b>, " +
                "<b><small><sup>145</sup></small>Nd</b>, " +
                "<b><small><sup>146</sup></small>Nd</b>, " +
                "<b><small><sup>148</sup></small>Nd</b>");
        element.setPicture("neodymium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(61);
        element.setMolecularFormula("Pm");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(85);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f5");
        element.setShell("K2 L8 M18 N23 O8 P2");
        element.setElectronegativity(1.13);
        element.setValence("<b>+3</b>, +2");
        element.setEnglishName("Promethium");
        element.setMeltingPoint(1024);
        element.setBoilingPoint(3000);
        element.setDiscoverer("Chien Shiung Wu, Emilio Segrè và Hans Bethe");
        element.setYearDiscovery("1942");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("promethium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(62);
        element.setMolecularFormula("Sm");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(88);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f6");
        element.setShell("K2 L8 M18 N24 O8 P2");
        element.setElectronegativity(1.17);
        element.setValence("+4, <b>+3</b>, <b>+2</b>, +1");
        element.setEnglishName("Samarium");
        element.setMeltingPoint(1072);
        element.setBoilingPoint(1900);
        element.setDiscoverer("Lecoq de Boisbaudran");
        element.setYearDiscovery("1879");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>144</sup></small>Sm</b>, " +
                "<b><small><sup>149</sup></small>Sm</b>, " +
                "<b><small><sup>150</sup></small>Sm</b>, " +
                "<b><small><sup>152</sup></small>Sm</b>, " +
                "<b><small><sup>154</sup></small>Sm</b>");
        element.setPicture("samarium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(63);
        element.setMolecularFormula("Eu");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(89);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f7");
        element.setShell("K2 L8 M18 N25 O8 P2");
        element.setElectronegativity(1.2);
        element.setValence("<b>+3</b>, <b>+2</b>, +1");
        element.setEnglishName("Europium");
        element.setMeltingPoint(826);
        element.setBoilingPoint(1529);
        element.setDiscoverer("Eugène-Anatole Demarçay");
        element.setYearDiscovery("1896");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>153</sup></small>Eu</b>");
        element.setPicture("europium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(64);
        element.setMolecularFormula("Gd");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(93);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>7</sup></small> 5d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f7 5d1");
        element.setShell("K2 L8 M18 N25 O9 P2");
        element.setElectronegativity(1.2);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Gadolinium");
        element.setMeltingPoint(1312);
        element.setBoilingPoint(3000);
        element.setDiscoverer("Jean Charles Galissard de Marignac");
        element.setYearDiscovery("1880");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>154</sup></small>Gd</b>, " +
                "<b><small><sup>155</sup></small>Gd</b>, " +
                "<b><small><sup>156</sup></small>Gd</b>, " +
                "<b><small><sup>157</sup></small>Gd</b>, " +
                "<b><small><sup>158</sup></small>Gd</b>, " +
                "<b><small><sup>160</sup></small>Gd</b>");
        element.setPicture("gadolinium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(65);
        element.setMolecularFormula("Tb");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(93);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>9</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f9");
        element.setShell("K2 L8 M18 N27 O8 P2");
        element.setElectronegativity(1.1);
        element.setValence("<b>+4</b>, <b>+3</b>, +2, +1");
        element.setEnglishName("Terbium");
        element.setMeltingPoint(1356);
        element.setBoilingPoint(3123);
        element.setDiscoverer("Carl Gustaf Mosander");
        element.setYearDiscovery("1843");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>159</sup></small>Tb</b>");
        element.setPicture("terbium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(66);
        element.setMolecularFormula("Dy");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(96);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f10");
        element.setShell("K2 L8 M18 N28 O8 P2");
        element.setElectronegativity(1.22);
        element.setValence("<b>+4</b>, <b>+3</b>, +2, +1");
        element.setEnglishName("Dysprosium");
        element.setMeltingPoint(1407);
        element.setBoilingPoint(2562);
        element.setDiscoverer("Lecoq de Boisbaudran");
        element.setYearDiscovery("1886");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>156</sup></small>Dy</b>, " +
                "<b><small><sup>158</sup></small>Dy</b>, " +
                "<b><small><sup>160</sup></small>Dy</b>, " +
                "<b><small><sup>161</sup></small>Dy</b>, " +
                "<b><small><sup>162</sup></small>Dy</b>, " +
                "<b><small><sup>163</sup></small>Dy</b>, " +
                "<b><small><sup>164</sup></small>Dy</b>");
        element.setPicture("dysprosium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(67);
        element.setMolecularFormula("Ho");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(96);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>11</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f11");
        element.setShell("K2 L8 M18 N29 O8 P2");
        element.setElectronegativity(1.23);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Holmium");
        element.setMeltingPoint(1461);
        element.setBoilingPoint(2600);
        element.setDiscoverer("Jacques-Louis Soret");
        element.setYearDiscovery("1878");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>165</sup></small>Ho</b>");
        element.setPicture("holmium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(68);
        element.setMolecularFormula("Er");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(99);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>12</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f12");
        element.setShell("K2 L8 M18 N30 O8 P2");
        element.setElectronegativity(1.24);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Erbium");
        element.setMeltingPoint(1529);
        element.setBoilingPoint(2868);
        element.setDiscoverer("Carl Gustaf Mosander");
        element.setYearDiscovery("1843");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>162</sup></small>Er</b>, " +
                "<b><small><sup>164</sup></small>Er</b>, " +
                "<b><small><sup>166</sup></small>Er</b>, " +
                "<b><small><sup>167</sup></small>Er</b>, " +
                "<b><small><sup>168</sup></small>Er</b>, " +
                "<b><small><sup>170</sup></small>Er</b>");
        element.setPicture("erbium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(69);
        element.setMolecularFormula("Tm");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(99);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>13</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f13");
        element.setShell("K2 L8 M18 N31 O8 P2");
        element.setElectronegativity(1.25);
        element.setValence("<b>+3</b>, +2");
        element.setEnglishName("Thulium");
        element.setMeltingPoint(1545);
        element.setBoilingPoint(1950);
        element.setDiscoverer("Per Teodor Cleve");
        element.setYearDiscovery("1879");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>169</sup></small>Tm</b>");
        element.setPicture("thulium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(70);
        element.setMolecularFormula("Yb");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(99);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14");
        element.setShell("K2 L8 M18 N32 O8 P2");
        element.setElectronegativity(1.1);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Ytterbium");
        element.setMeltingPoint(824);
        element.setBoilingPoint(1196);
        element.setDiscoverer("Jean Charles Galissard de Marignac");
        element.setYearDiscovery("1878");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>168</sup></small>Yb</b>, " +
                "<b><small><sup>170</sup></small>Yb</b>, " +
                "<b><small><sup>171</sup></small>Yb</b>, " +
                "<b><small><sup>172</sup></small>Yb</b>, " +
                "<b><small><sup>173</sup></small>Yb</b>, " +
                "<b><small><sup>174</sup></small>Yb</b>, " +
                "<b><small><sup>176</sup></small>Yb</b>");
        element.setPicture("ytterbium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(71);
        element.setMolecularFormula("Lu");
        element.setPeriod(6);
        element.setClassElement("f");
        element.setNeutron(104);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d1");
        element.setShell("K2 L8 M18 N32 O9 P2");
        element.setElectronegativity(1.27);
        element.setValence("<b>+3</b>, +2, +1");
        element.setEnglishName("Lutetium");
        element.setMeltingPoint(1652);
        element.setBoilingPoint(3402);
        element.setDiscoverer("Carl Auer von Welsbach và Georges Urbain");
        element.setYearDiscovery("1906");
        element.setIdGroup(3);
        element.setIsotopes("<b><small><sup>175</sup></small>Lu</b>");
        element.setPicture("lutetium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(72);
        element.setMolecularFormula("Hf");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(106);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d2");
        element.setShell("K2 L8 M18 N32 O10 P2");
        element.setElectronegativity(1.3);
        element.setValence("<b>+4</b>, +3, +2, +1, -2");
        element.setEnglishName("Hafnium");
        element.setMeltingPoint(2233);
        element.setBoilingPoint(4603);
        element.setDiscoverer("Dirk Coster và George de Hevesy");
        element.setYearDiscovery("1922");
        element.setIdGroup(4);
        element.setIsotopes("<b><small><sup>176</sup></small>Hf</b>, " +
                "<b><small><sup>177</sup></small>Hf</b>, " +
                "<b><small><sup>178</sup></small>Hf</b>, " +
                "<b><small><sup>179</sup></small>Hf</b>, " +
                "<b><small><sup>180</sup></small>Hf</b>");
        element.setPicture("hafnium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(73);
        element.setMolecularFormula("Ta");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(107);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d3");
        element.setShell("K2 L8 M18 N32 O11 P2");
        element.setElectronegativity(1.5);
        element.setValence("<b>+5</b>, +4, +3, +2, +1, -1, -3");
        element.setEnglishName("Tantalum");
        element.setMeltingPoint(3017);
        element.setBoilingPoint(5458);
        element.setDiscoverer("Anders Gustaf Ekeberg");
        element.setYearDiscovery("1802");
        element.setIdGroup(5);
        element.setIsotopes("<b><small><sup>181</sup></small>Ta</b>");
        element.setPicture("tantalum");
        elementList.add(element);

        element = new Element();
        element.setIdElement(74);
        element.setMolecularFormula("V");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(110);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d4");
        element.setShell("K2 L8 M18 N32 O12 P2");
        element.setElectronegativity(2.36);
        element.setValence("<b>+6</b>, +5, +4, +3, <b>+2</b>, +1, 0, -1, -2, -4");
        element.setEnglishName("Tungsten");
        element.setMeltingPoint(3422);
        element.setBoilingPoint(5930);
        element.setDiscoverer("Carl Wilhelm Scheele");
        element.setYearDiscovery("1781");
        element.setIdGroup(6);
        element.setIsotopes("<b><small><sup>182</sup></small>V</b>, " +
                "<b><small><sup>183</sup></small>V</b>, " +
                "<b><small><sup>184</sup></small>V</b>, " +
                "<b><small><sup>186</sup></small>V</b>");
        element.setPicture("tungsten");
        elementList.add(element);

        element = new Element();
        element.setIdElement(75);
        element.setMolecularFormula("Re");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(111);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d5");
        element.setShell("K2 L8 M18 N32 O13 P2");
        element.setElectronegativity(1.9);
        element.setValence("<b>+7</b>, +6, +5, <b>+4</b>, <b>+3</b>, +2, +1, 0, -1, -3");
        element.setEnglishName("Rhenium");
        element.setMeltingPoint(3186);
        element.setBoilingPoint(5630);
        element.setDiscoverer("Masataka Ogawa");
        element.setYearDiscovery("1908");
        element.setIdGroup(7);
        element.setIsotopes("<b><small><sup>185</sup></small>Re</b>");
        element.setPicture("rhenium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(76);
        element.setMolecularFormula("Os");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(114);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d6");
        element.setShell("K2 L8 M18 N32 O14 P2");
        element.setElectronegativity(2.2);
        element.setValence("<b>+8</b>, +7, +6, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, 0, -1, -2, -4");
        element.setEnglishName("Osmium");
        element.setMeltingPoint(3033);
        element.setBoilingPoint(5012);
        element.setDiscoverer("Smithson Tennant");
        element.setYearDiscovery("1803");
        element.setIdGroup(8);
        element.setIsotopes("<b><small><sup>184</sup></small>Og</b>, " +
                "<b><small><sup>187</sup></small>Og</b>, " +
                "<b><small><sup>188</sup></small>Og</b>, " +
                "<b><small><sup>189</sup></small>Og</b>, " +
                "<b><small><sup>190</sup></small>Og</b>, " +
                "<b><small><sup>192</sup></small>Og</b>");
        element.setPicture("osmium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(77);
        element.setMolecularFormula("Ir");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(115);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d7");
        element.setShell("K2 L8 M18 N32 O15 P2");
        element.setElectronegativity(2.20);
        element.setValence("+9, +8, +7, +6, +5, <b>+4</b>, <b>+3</b>, <b>+2</b>, +1, 0, -1, -3");
        element.setEnglishName("Iridium");
        element.setMeltingPoint(2446);
        element.setBoilingPoint(4130);
        element.setDiscoverer("Smithson Tennant");
        element.setYearDiscovery("1803");
        element.setIdGroup(9);
        element.setIsotopes("<b><small><sup>191</sup></small>Ir</b>, " +
                "<b><small><sup>193</sup></small>Ir</b>");
        element.setPicture("iridium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(78);
        element.setMolecularFormula("Pt");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(117);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>1</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>9</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s1 4f14 5d9");
        element.setShell("K2 L8 M18 N32 O17 P1");
        element.setElectronegativity(2.28);
        element.setValence("+6, +5, <b>+4</b>, +3, <b>+2</b>, +1, -1, -2, -3");
        element.setEnglishName("Platinum");
        element.setMeltingPoint(1768.3);
        element.setBoilingPoint(3825);
        element.setDiscoverer("Antonio de Ulloa");
        element.setYearDiscovery("1735");
        element.setIdGroup(10);
        element.setIsotopes("<b><small><sup>192</sup></small>Pt</b>, " +
                "<b><small><sup>194</sup></small>Pt</b>, " +
                "<b><small><sup>195</sup></small>Pt</b>, " +
                "<b><small><sup>196</sup></small>Pt</b>, " +
                "<b><small><sup>198</sup></small>Pt</b>");
        element.setPicture("platinum");
        elementList.add(element);

        element = new Element();
        element.setIdElement(79);
        element.setMolecularFormula("Au");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(118);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>1</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s1 4f14 5d10");
        element.setShell("K2 L8 M18 N32 O18 P1");
        element.setElectronegativity(2.54);
        element.setValence("+5, <b>+3</b>, +2, <b>+1</b>, -1, -2, -3");
        element.setEnglishName("Gold");
        element.setMeltingPoint(1064.18);
        element.setBoilingPoint(2970);
        element.setDiscoverer("Ở Trung Đông");
        element.setYearDiscovery("6000 TCN");
        element.setIdGroup(11);
        element.setIsotopes("<b><small><sup>197</sup></small>Au</b>");
        element.setPicture("gold");
        elementList.add(element);

        element = new Element();
        element.setIdElement(80);
        element.setMolecularFormula("Hg");
        element.setPeriod(6);
        element.setClassElement("d");
        element.setNeutron(120);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10");
        element.setShell("K2 L8 M18 N32 O18 P2");
        element.setElectronegativity(2.00);
        element.setValence("<b>+2</b>, <b>+1</b>, -2");
        element.setEnglishName("Mercury");
        element.setMeltingPoint(-38.8290);
        element.setBoilingPoint(356.73);
        element.setDiscoverer("Người Trung Quốc và người Ấn Độ cổ đại");
        element.setYearDiscovery("2000 TCN");
        element.setIdGroup(12);
        element.setIsotopes("<b><small><sup>196</sup></small>Hg</b>, " +
                "<b><small><sup>198</sup></small>Hg</b>, " +
                "<b><small><sup>199</sup></small>Hg</b>, " +
                "<b><small><sup>200</sup></small>Hg</b>, " +
                "<b><small><sup>201</sup></small>Hg</b>, " +
                "<b><small><sup>202</sup></small>Hg</b>, " +
                "<b><small><sup>204</sup></small>Hg</b>");
        element.setPicture("mercury");
        elementList.add(element);

        element = new Element();
        element.setIdElement(81);
        element.setMolecularFormula("Ti");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(123);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p1");
        element.setShell("K2 L8 M18 N32 O18 P3");
        element.setElectronegativity(1.62);
        element.setValence("<b>+3</b>, +2, <b>+1</b>, -1, -2, -5");
        element.setEnglishName("Thallium");
        element.setMeltingPoint(304);
        element.setBoilingPoint(1473);
        element.setDiscoverer("William Crookes");
        element.setYearDiscovery("1861");
        element.setIdGroup(13);
        element.setIsotopes("<b><small><sup>203</sup></small>Ti</b>, " +
                "<b><small><sup>205</sup></small>Ti</b>");
        element.setPicture("thallium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(82);
        element.setMolecularFormula("Pb");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(125);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p2");
        element.setShell("K2 L8 M18 N32 O18 P4");
        element.setElectronegativity(1.87);
        element.setValence("<b>+4</b>, +3, <b>+2</b>, +1, -1, -2, -4");
        element.setEnglishName("Lead");
        element.setMeltingPoint(327.46);
        element.setBoilingPoint(1749);
        element.setDiscoverer("Ở Trung Đông");
        element.setYearDiscovery("7000 TCN");
        element.setIdGroup(14);
        element.setIsotopes("<b><small><sup>204</sup></small>Pb</b>, " +
                "<b><small><sup>206</sup></small>Pb</b>, " +
                "<b><small><sup>207</sup></small>Pb</b>, " +
                "<b><small><sup>208</sup></small>Pb</b>");
        element.setPicture("lead");
        elementList.add(element);

        element = new Element();
        element.setIdElement(83);
        element.setMolecularFormula("Bi");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(126);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p3");
        element.setShell("K2 L8 M18 N32 O18 P5");
        element.setElectronegativity(2.02);
        element.setValence("<b>+5</b>, +4, <b>+3</b>, +2, +1, -1, -2, -3");
        element.setEnglishName("Bismuth");
        element.setMeltingPoint(271.5);
        element.setBoilingPoint(1564);
        element.setDiscoverer("Claude François Geoffroy");
        element.setYearDiscovery("1753");
        element.setIdGroup(15);
        element.setIsotopes("Không có");
        element.setPicture("bismuth");
        elementList.add(element);

        element = new Element();
        element.setIdElement(84);
        element.setMolecularFormula("Po");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(126);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p4");
        element.setShell("K2 L8 M18 N32 O18 P6");
        element.setElectronegativity(2.0);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, <b>+2</b>, <b>-2</b>");
        element.setEnglishName("Polonium");
        element.setMeltingPoint(254);
        element.setBoilingPoint(962);
        element.setDiscoverer("Pierre and Marie Curie");
        element.setYearDiscovery("1898");
        element.setIdGroup(16);
        element.setIsotopes("Không có");
        element.setPicture("polonium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(85);
        element.setMolecularFormula("At");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(126);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p5");
        element.setShell("K2 L8 M18 N32 O18 P7");
        element.setElectronegativity(2.2);
        element.setValence("<b>+7</b>, +5, <b>+3</b>, <b>+1</b>, <b>-1</b>");
        element.setEnglishName("Astatine");
        element.setMeltingPoint(302);
        element.setBoilingPoint(337);
        element.setDiscoverer("Dale R. Corson, Kenneth Ross MacKenzie và Emilio Segrè");
        element.setYearDiscovery("1940");
        element.setIdGroup(17);
        element.setIsotopes("Không có");
        element.setPicture("astatine");
        elementList.add(element);

        element = new Element();
        element.setIdElement(86);
        element.setMolecularFormula("Rn");
        element.setPeriod(6);
        element.setClassElement("p");
        element.setNeutron(136);
        element.setSimplifiedConfiguration("[Xe]6s<small><sup>2</sup></small> 4f<small><sup>14</sup></small> 5d<small><sup>10</sup></small> 6p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6");
        element.setShell("K2 L8 M18 N32 O18 P8");
        element.setElectronegativity(2.2);
        element.setValence("+6, +2, <b>0</b>");
        element.setEnglishName("Radon");
        element.setMeltingPoint(-71);
        element.setBoilingPoint(-61.7);
        element.setDiscoverer("Ernest Rutherford và Robert B. Owens");
        element.setYearDiscovery("1899");
        element.setIdGroup(18);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(87);
        element.setMolecularFormula("Fr");
        element.setPeriod(7);
        element.setClassElement("s");
        element.setNeutron(136);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s1");
        element.setShell("K2 L8 M18 N32 O18 P8 Q1");
        element.setElectronegativity(0.7);
        element.setValence("<b>+1</b>");
        element.setEnglishName("Francium");
        element.setMeltingPoint(30);
        element.setBoilingPoint(680);
        element.setDiscoverer("Marguerite Perey");
        element.setYearDiscovery("1939");
        element.setIdGroup(1);
        element.setIsotopes("Không có");
        element.setPicture("francium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(88);
        element.setMolecularFormula("Ra");
        element.setPeriod(7);
        element.setClassElement("s");
        element.setNeutron(138);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2");
        element.setShell("K2 L8 M18 N32 O18 P8 Q2");
        element.setElectronegativity(0.9);
        element.setValence("<b>+2</b>");
        element.setEnglishName("Radium");
        element.setMeltingPoint(700);
        element.setBoilingPoint(1737);
        element.setDiscoverer("Pierre and Marie Curie");
        element.setYearDiscovery("1898");
        element.setIdGroup(2);
        element.setIsotopes("Không có");
        element.setPicture("radium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(89);
        element.setMolecularFormula("Ac");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(138);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 6d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 6d1");
        element.setShell("K2 L8 M18 N32 O18 P9 Q2");
        element.setElectronegativity(0.9);
        element.setValence("<b>+2</b>");
        element.setEnglishName("Actinium");
        element.setMeltingPoint(1050);
        element.setBoilingPoint(3198);
        element.setDiscoverer("Friedrich Oskar Giesel");
        element.setYearDiscovery("1902");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("actinium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(90);
        element.setMolecularFormula("Th");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(142);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 6d<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 6d2");
        element.setShell("K2 L8 M18 N32 O18 P10 Q2");
        element.setElectronegativity(1.3);
        element.setValence("<b>+4</b>, +3, +2, +1");
        element.setEnglishName("Thorium");
        element.setMeltingPoint(1750);
        element.setBoilingPoint(4788);
        element.setDiscoverer("Jöns Jakob Berzelius");
        element.setYearDiscovery("1829");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("thorium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(91);
        element.setMolecularFormula("Pa");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(140);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>2</sup></small> 6d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f2 6d1");
        element.setShell("K2 L8 M18 N32 O20 P9 Q2");
        element.setElectronegativity(1.5);
        element.setValence("<b>+5</b>, <b>+4</b>, +3, +2");
        element.setEnglishName("Protactinium");
        element.setMeltingPoint(1568);
        element.setBoilingPoint(4027);
        element.setDiscoverer("Kasimir Fajans và Oswald Helmuth Göhring");
        element.setYearDiscovery("1913");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("protactinium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(92);
        element.setMolecularFormula("U");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(146);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>3</sup></small> 6d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f3 6d1");
        element.setShell("K2 L8 M18 N32 O21 P9 Q2");
        element.setElectronegativity(1.38);
        element.setValence("<b>+6</b>, +5, <b>+4</b>, +3, +2, +1");
        element.setEnglishName("Uranium");
        element.setMeltingPoint(1132.2);
        element.setBoilingPoint(4131);
        element.setDiscoverer("Martin Heinrich Klaproth");
        element.setYearDiscovery("1789");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("uranium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(93);
        element.setMolecularFormula("Np");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(146);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>4</sup></small> 6d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f4 6d1");
        element.setShell("K2 L8 M18 N32 O22 P9 Q2");
        element.setElectronegativity(1.36);
        element.setValence("+7, <b>+6</b>, <b>+5</b>, <b>+4</b>, +3, +2, +1");
        element.setEnglishName("Neptunium");
        element.setMeltingPoint(639);
        element.setBoilingPoint(4174);
        element.setDiscoverer("Edwin McMillan và Philip H. Abelson");
        element.setYearDiscovery("1940");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("neptunium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(94);
        element.setMolecularFormula("Pu");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(150);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f6");
        element.setShell("K2 L8 M18 N32 O24 P8 Q2");
        element.setElectronegativity(1.28);
        element.setValence("+7, <b>+6</b>, <b>+5</b>, <b>+4</b>, +3, +2, +1");
        element.setEnglishName("Plutonium");
        element.setMeltingPoint(639.4);
        element.setBoilingPoint(3228);
        element.setDiscoverer("Glenn T. Seaborg, Arthur Wahl, Joseph W. Kennedy và Edwin McMillan");
        element.setYearDiscovery("1940–1");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("plutonium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(95);
        element.setMolecularFormula("Am");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(148);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f7");
        element.setShell("K2 L8 M18 N32 O25 P8 Q2");
        element.setElectronegativity(1.13);
        element.setValence("+7, <b>+6</b>, <b>+5</b>, <b>+4</b>, +3, +2");
        element.setEnglishName("Americium");
        element.setMeltingPoint(1176);
        element.setBoilingPoint(2607);
        element.setDiscoverer("Glenn T. Seaborg, Ralph A. James, Leon O. Morgan, Albert Ghiorso");
        element.setYearDiscovery("1944");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("americium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(96);
        element.setMolecularFormula("Cm");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(151);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>7</sup></small> 6d<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f7 6d1");
        element.setShell("K2 L8 M18 N32 O25 P9 Q2");
        element.setElectronegativity(1.28);
        element.setValence("+6, +5, +4, <b>+3</b>, +2");
        element.setEnglishName("Curium");
        element.setMeltingPoint(1340);
        element.setBoilingPoint(3110);
        element.setDiscoverer("Glenn T. Seaborg, Ralph A. James, Albert Ghiorso");
        element.setYearDiscovery("1944");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("curium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(97);
        element.setMolecularFormula("Bk");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(150);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>9</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f9");
        element.setShell("K2 L8 M18 N32 O27 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("+5, <b>+4</b>, <b>+3</b>, +2");
        element.setEnglishName("Berkelium");
        element.setMeltingPoint(986);
        element.setBoilingPoint(2627);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1949");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("berkelium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(98);
        element.setMolecularFormula("Cf");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(153);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f10");
        element.setShell("K2 L8 M18 N32 O28 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("+5, +4, <b>+3</b>, +2");
        element.setEnglishName("Californium");
        element.setMeltingPoint(900);
        element.setBoilingPoint(1470);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1950");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("californium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(99);
        element.setMolecularFormula("Es");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(153);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>11</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f11");
        element.setShell("K2 L8 M18 N32 O29 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("+4, <b>+3</b>, +2");
        element.setEnglishName("Einsteinium");
        element.setMeltingPoint(860);
        element.setBoilingPoint(996);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1950");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("einsteinium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(100);
        element.setMolecularFormula("Fm");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(157);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>12</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f12");
        element.setShell("K2 L8 M18 N32 O30 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("<b>+3</b>, +2");
        element.setEnglishName("Fermium");
        element.setMeltingPoint(1527);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1952");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(101);
        element.setMolecularFormula("Md");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(157);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>13</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f13");
        element.setShell("K2 L8 M18 N32 O31 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("<b>+3</b>, <b>+2</b>");
        element.setEnglishName("Mendelevium");
        element.setMeltingPoint(1955);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1952");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("mendelevium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(102);
        element.setMolecularFormula("No");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(157);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14");
        element.setShell("K2 L8 M18 N32 O32 P8 Q2");
        element.setElectronegativity(1.3);
        element.setValence("<b>+3</b>, <b>+2</b>");
        element.setEnglishName("Nobelium");
        element.setMeltingPoint(1955);
        element.setDiscoverer("Phòng thí nghiệm phản ứng hạt nhân Dubna");
        element.setYearDiscovery("1966");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("nobelium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(103);
        element.setMolecularFormula("Lr");
        element.setPeriod(7);
        element.setClassElement("f");
        element.setNeutron(159);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 7p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 7p1");
        element.setShell("K2 L8 M18 N32 O32 P8 Q3");
        element.setElectronegativity(1.3);
        element.setValence("<b>+3</b>, <b>+2</b>");
        element.setEnglishName("Lawrencium");
        element.setMeltingPoint(1955);
        element.setBoilingPoint(0);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley và phòng thí nghiệm phản ứng hạt nhân Dubna");
        element.setYearDiscovery("1961–1971");
        element.setIdGroup(3);
        element.setIsotopes("Không có");
        element.setPicture("lawrencium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(104);
        element.setMolecularFormula("Rf");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(163);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d2");
        element.setShell("K2 L8 M18 N32 O32 P10 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+4</b>, (Dự đoán: +3, +2)");
        element.setEnglishName("Rutherfordium");
        element.setMeltingPoint(2100);
        element.setBoilingPoint(5500);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley và phòng thí nghiệm phản ứng hạt nhân Dubna");
        element.setYearDiscovery("1964, 1969");
        element.setIdGroup(4);
        element.setIsotopes("Không có");
        element.setPicture("rutherfordium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(105);
        element.setMolecularFormula("Db");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(163);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d3");
        element.setShell("K2 L8 M18 N32 O32 P11 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+5</b>, (Dự đoán: +4, +3)");
        element.setEnglishName("Dubnium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley và phòng thí nghiệm phản ứng hạt nhân Dubna");
        element.setYearDiscovery("1970");
        element.setIdGroup(5);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(106);
        element.setMolecularFormula("Sg");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(165);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d4");
        element.setShell("K2 L8 M18 N32 O32 P12 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+6</b>, (Dự đoán: +5, +4, +3), <b>0</b>");
        element.setEnglishName("Seaborgium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Phòng thí nghiệm quốc gia Lawrence Berkeley");
        element.setYearDiscovery("1974");
        element.setIdGroup(6);
        element.setIsotopes("Không có");
        element.setPicture("seaborgium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(107);
        element.setMolecularFormula("Bh");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(163);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d5");
        element.setShell("K2 L8 M18 N32 O32 P13 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+7</b>, (Dự đoán: +5, +4, +3)");
        element.setEnglishName("Bohrium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1981");
        element.setIdGroup(7);
        element.setIsotopes("Không có");
        element.setPicture("bohrium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(108);
        element.setMolecularFormula("Hs");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(163);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d6");
        element.setShell("K2 L8 M18 N32 O32 P14 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+8</b>, (Dự đoán: +6, +5, +4, +3, +2)");
        element.setEnglishName("Hassium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1984");
        element.setIdGroup(8);
        element.setIsotopes("Không có");
        element.setPicture("hassium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(109);
        element.setMolecularFormula("Mt");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(169);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>7</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d7");
        element.setShell("K2 L8 M18 N32 O32 P15 Q2");
        element.setElectronegativity(0);
        element.setValence("+9, +8, <b>+6</b>, +4, +3, <b>+1</b> (Dự đoán)");
        element.setEnglishName("Meitnerium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1984");
        element.setIdGroup(9);
        element.setIsotopes("Không có");
        element.setPicture("meitnerium");
        elementList.add(element);

        element = new Element();
        element.setIdElement(110);
        element.setMolecularFormula("Ds");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(171);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>8</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d8");
        element.setShell("K2 L8 M18 N32 O32 P16 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+8</b>, +6, +4, <b>+2</b>, <b>0</b> (Dự đoán)");
        element.setEnglishName("Darmstadtium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1994");
        element.setIdGroup(10);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(111);
        element.setMolecularFormula("Rg");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(170);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>9</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d9");
        element.setShell("K2 L8 M18 N32 O32 P17 Q2");
        element.setElectronegativity(0);
        element.setValence("+5, <b>+3</b>, +1, -1 (Dự đoán)");
        element.setEnglishName("Roentgenium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1994");
        element.setIdGroup(11);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(112);
        element.setMolecularFormula("Cn");
        element.setPeriod(7);
        element.setClassElement("d");
        element.setNeutron(173);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10");
        element.setShell("K2 L8 M18 N32 O32 P18 Q2");
        element.setElectronegativity(0);
        element.setValence("<b>+2</b>, <b>0</b>, (Dự đoán : +4, +1)");
        element.setEnglishName("Copernicium");
        element.setMeltingPoint(0);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu ion nặng, GSI Helmholtz");
        element.setYearDiscovery("1996");
        element.setIdGroup(12);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(113);
        element.setMolecularFormula("Nh");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(173);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>1</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p1");
        element.setShell("K2 L8 M18 N32 O32 P18 Q3");
        element.setElectronegativity(0);
        element.setValence("+5, +3, <b>+1</b>, -1 (Dự đoán)");
        element.setEnglishName("Nihonium");
        element.setMeltingPoint(430);
        element.setBoilingPoint(1130);
        element.setDiscoverer("Viện nghiên cứu RIKEN (Nhật Bản)");
        element.setYearDiscovery("1996");
        element.setIdGroup(13);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(114);
        element.setMolecularFormula("Fl");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(175);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>2</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p2");
        element.setShell("K2 L8 M18 N32 O32 P18 Q4");
        element.setElectronegativity(0);
        element.setValence("+5, +3, <b>+1</b>, -1 (Dự đoán)");
        element.setEnglishName("Flerovium");
        element.setMeltingPoint(-60);
        element.setBoilingPoint(0);
        element.setDiscoverer("Viện nghiên cứu hạt nhân chung và Phòng thí nghiệm quốc gia Lawrence Livermore");
        element.setYearDiscovery("1999");
        element.setIdGroup(14);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(115);
        element.setMolecularFormula("Mc");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(174);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>3</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p3");
        element.setShell("K2 L8 M18 N32 O32 P18 Q5");
        element.setElectronegativity(0);
        element.setValence("<b>+3</b>, <b>+1</b> (Dự đoán)");
        element.setEnglishName("Moscovium");
        element.setMeltingPoint(400);
        element.setBoilingPoint(1100);
        element.setDiscoverer("Viện nghiên cứu hạt nhân chung và Phòng thí nghiệm quốc gia Lawrence Livermore");
        element.setYearDiscovery("2003");
        element.setIdGroup(15);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(116);
        element.setMolecularFormula("Lv");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(176);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>4</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p4");
        element.setShell("K2 L8 M18 N32 O32 P18 Q6");
        element.setElectronegativity(0);
        element.setValence("+4, <b>+2</b>, -2(Dự đoán)");
        element.setEnglishName("Livermorium");
        element.setMeltingPoint(435.5);
        element.setBoilingPoint(794);
        element.setDiscoverer("Viện nghiên cứu hạt nhân chung và Phòng thí nghiệm quốc gia Lawrence Livermore");
        element.setYearDiscovery("2000");
        element.setIdGroup(16);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(117);
        element.setMolecularFormula("Ts");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(176);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>5</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p5");
        element.setShell("K2 L8 M18 N32 O32 P18 Q7");
        element.setElectronegativity(0);
        element.setValence("+5, <b>+3</b>, <b>+1</b>, -1(Dự đoán)");
        element.setEnglishName("Tennessine");
        element.setMeltingPoint(450);
        element.setBoilingPoint(610);
        element.setDiscoverer("Viện nghiên cứu hạt nhân chung, " +
                "Phòng thí nghiệm quốc gia Lawrence Livermore, " +
                "Đại học Vanderbilt và " +
                "Phòng thí nghiệm quốc gia Oak Ridge");
        element.setYearDiscovery("2009");
        element.setIdGroup(17);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        element = new Element();
        element.setIdElement(118);
        element.setMolecularFormula("Os");
        element.setPeriod(7);
        element.setClassElement("p");
        element.setNeutron(176);
        element.setSimplifiedConfiguration("[Rn]7s<small><sup>2</sup></small> 5f<small><sup>14</sup></small> 6d<small><sup>10</sup></small> 7p<small><sup>6</sup></small>");
        element.setConfiguration("1s2 2s2 2p6 3s2 3p6 4s2 3d10 4p6 5s2 4d10 5p6 6s2 4f14 5d10 6p6 7s2 5f14 6d10 7p6");
        element.setShell("K2 L8 M18 N32 O32 P18 Q8");
        element.setElectronegativity(0);
        element.setValence("+6, <b>+4</b>, <b>+2</b>, +1, 0, -1(Dự đoán)");
        element.setEnglishName("Oganesson");
        element.setMeltingPoint(0);
        element.setBoilingPoint(80);
        element.setDiscoverer("Viện nghiên cứu hạt nhân chung, " +
                "Phòng thí nghiệm quốc gia Lawrence Livermore");
        element.setYearDiscovery("2002");
        element.setIdGroup(18);
        element.setIsotopes("Không có");
        element.setPicture("no_image_available");
        elementList.add(element);

        //Check and add data
        if (elementList.size() == mChemistryHelper.getAllElements().size()) {
            Log.i("ANTN", "Table Element available");
        } else {
            //Add to database
            mChemistryHelper.emptyElement();
            for (Element item : elementList) {
                mChemistryHelper.addElement(item);
            }
        }
    }

    private void addDataAnionTable() {

        List<Anion> anionList = new ArrayList<>();

        Anion anion = new Anion(1, "Cl", "-");
        anionList.add(anion);

        anion = new Anion(2, "Br", "-");
        anionList.add(anion);

        anion = new Anion(3, "I", "-");
        anionList.add(anion);

        anion = new Anion(4, "NO3", "-");
        anionList.add(anion);

        anion = new Anion(5, "CH3COO", "-");
        anionList.add(anion);

        anion = new Anion(6, "S", "2-");
        anionList.add(anion);

        anion = new Anion(7, "SO3", "2-");
        anionList.add(anion);

        anion = new Anion(8, "SO4", "2-");
        anionList.add(anion);

        anion = new Anion(9, "CO3", "2-");
        anionList.add(anion);

        anion = new Anion(10, "SiO3", "2-");
        anionList.add(anion);

        anion = new Anion(11, "CrO4", "2-");
        anionList.add(anion);

        anion = new Anion(12, "PO4", "3-");
        anionList.add(anion);

        anion = new Anion(13, "OH", "-");
        anionList.add(anion);

        //Check and add data
        if (anionList.size() == mChemistryHelper.getAllAnion().size()) {
            Log.i("ANTN", "Table Anion available");
        } else {
            Log.i("ANTN", "Table Anion updated");
            //Add to database
            mChemistryHelper.emptyAnion();
            for (Anion item : anionList) {
                mChemistryHelper.addAnion(item);
            }
        }
    }

    private void addDataCationTable() {

        List<Cation> cationList = new ArrayList<>();

        Cation cation = new Cation(1, "Li", "+");
        cationList.add(cation);

        cation = new Cation(2, "Na", "+");
        cationList.add(cation);

        cation = new Cation(3, "K", "+");
        cationList.add(cation);

        cation = new Cation(4, "NH4", "+");
        cationList.add(cation);

        cation = new Cation(5, "Cu", "+");
        cationList.add(cation);

        cation = new Cation(6, "Ag", "+");
        cationList.add(cation);

        cation = new Cation(7, "Mg", "2+");
        cationList.add(cation);

        cation = new Cation(8, "Ca", "2+");
        cationList.add(cation);

        cation = new Cation(9, "Sr", "2+");
        cationList.add(cation);

        cation = new Cation(10, "Ba", "2+");
        cationList.add(cation);

        cation = new Cation(11, "Zn", "2+");
        cationList.add(cation);

        cation = new Cation(12, "Hg", "2+");
        cationList.add(cation);

        cation = new Cation(13, "Al", "3+");
        cationList.add(cation);

        cation = new Cation(14, "Sn", "2+");
        cationList.add(cation);

        cation = new Cation(15, "Pb", "2+");
        cationList.add(cation);

        cation = new Cation(16, "Bi", "3+");
        cationList.add(cation);

        cation = new Cation(17, "Cr", "3+");
        cationList.add(cation);

        cation = new Cation(18, "Mn", "2+");
        cationList.add(cation);

        cation = new Cation(19, "Fe", "3+");
        cationList.add(cation);

        cation = new Cation(20, "Fe", "2+");
        cationList.add(cation);

        //Check and add data
        if (cationList.size() == mChemistryHelper.getAllCation().size()) {
            Log.i("ANTN", "Table Cation available");
        } else {
            //Add to database
            Log.i("ANTN", "Table Cation updated");
            mChemistryHelper.emptyCation();
            for (Cation item : cationList) {
                mChemistryHelper.addCation(item);
            }
        }
    }

    private void addDataSoluteTable() {

        List<Solute> soluteList = new ArrayList<>();

        Solute solute;
        solute = new Solute(1, 1, "T");
        soluteList.add(solute);

        solute = new Solute(1, 2, "T");
        soluteList.add(solute);

        solute = new Solute(1, 3, "T");
        soluteList.add(solute);

        solute = new Solute(1, 4, "T");
        soluteList.add(solute);

        solute = new Solute(1, 5, "T");
        soluteList.add(solute);

        solute = new Solute(1, 6, "K");
        soluteList.add(solute);

        solute = new Solute(1, 7, "T");
        soluteList.add(solute);

        solute = new Solute(1, 8, "T");
        soluteList.add(solute);

        solute = new Solute(1, 9, "T");
        soluteList.add(solute);

        solute = new Solute(1, 10, "T");
        soluteList.add(solute);

        solute = new Solute(1, 11, "T");
        soluteList.add(solute);

        solute = new Solute(1, 12, "T");
        soluteList.add(solute);

        solute = new Solute(1, 13, "T");
        soluteList.add(solute);

        solute = new Solute(1, 14, "T");
        soluteList.add(solute);

        solute = new Solute(1, 15, "I");
        soluteList.add(solute);

        solute = new Solute(1, 16, "—");
        soluteList.add(solute);

        solute = new Solute(1, 17, "T");
        soluteList.add(solute);

        solute = new Solute(1, 18, "T");
        soluteList.add(solute);

        solute = new Solute(1, 19, "T");
        soluteList.add(solute);

        solute = new Solute(1, 20, "T");
        soluteList.add(solute);

        //—————

        solute = new Solute(2, 1, "T");
        soluteList.add(solute);

        solute = new Solute(2, 2, "T");
        soluteList.add(solute);

        solute = new Solute(2, 3, "T");
        soluteList.add(solute);

        solute = new Solute(2, 4, "T");
        soluteList.add(solute);

        solute = new Solute(2, 5, "T");
        soluteList.add(solute);

        solute = new Solute(2, 6, "K");
        soluteList.add(solute);

        solute = new Solute(2, 7, "T");
        soluteList.add(solute);

        solute = new Solute(2, 8, "T");
        soluteList.add(solute);

        solute = new Solute(2, 9, "T");
        soluteList.add(solute);

        solute = new Solute(2, 10, "T");
        soluteList.add(solute);

        solute = new Solute(2, 11, "T");
        soluteList.add(solute);

        solute = new Solute(2, 12, "I");
        soluteList.add(solute);

        solute = new Solute(2, 13, "T");
        soluteList.add(solute);

        solute = new Solute(2, 14, "T");
        soluteList.add(solute);

        solute = new Solute(2, 15, "I");
        soluteList.add(solute);

        solute = new Solute(2, 16, "—");
        soluteList.add(solute);

        solute = new Solute(2, 17, "T");
        soluteList.add(solute);

        solute = new Solute(2, 18, "T");
        soluteList.add(solute);

        solute = new Solute(2, 19, "T");
        soluteList.add(solute);

        solute = new Solute(2, 20, "T");
        soluteList.add(solute);

        //——

        solute = new Solute(3, 1, "T");
        soluteList.add(solute);

        solute = new Solute(3, 2, "T");
        soluteList.add(solute);

        solute = new Solute(3, 3, "T");
        soluteList.add(solute);

        solute = new Solute(3, 4, "T");
        soluteList.add(solute);

        solute = new Solute(3, 5, "—");
        soluteList.add(solute);

        solute = new Solute(3, 6, "K");
        soluteList.add(solute);

        solute = new Solute(3, 7, "T");
        soluteList.add(solute);

        solute = new Solute(3, 8, "T");
        soluteList.add(solute);

        solute = new Solute(3, 9, "T");
        soluteList.add(solute);

        solute = new Solute(3, 10, "T");
        soluteList.add(solute);

        solute = new Solute(3, 11, "T");
        soluteList.add(solute);

        solute = new Solute(3, 12, "K");
        soluteList.add(solute);

        solute = new Solute(3, 13, "T");
        soluteList.add(solute);

        solute = new Solute(3, 14, "T");
        soluteList.add(solute);

        solute = new Solute(3, 15, "K");
        soluteList.add(solute);

        solute = new Solute(3, 16, "—");
        soluteList.add(solute);

        solute = new Solute(3, 17, "T");
        soluteList.add(solute);

        solute = new Solute(3, 18, "K");
        soluteList.add(solute);

        solute = new Solute(3, 19, "—");
        soluteList.add(solute);

        solute = new Solute(3, 20, "T");
        soluteList.add(solute);

        //——

        solute = new Solute(4, 1, "T");
        soluteList.add(solute);

        solute = new Solute(4, 2, "T");
        soluteList.add(solute);

        solute = new Solute(4, 3, "T");
        soluteList.add(solute);

        solute = new Solute(4, 4, "T");
        soluteList.add(solute);

        solute = new Solute(4, 5, "T");
        soluteList.add(solute);

        solute = new Solute(4, 6, "T");
        soluteList.add(solute);

        solute = new Solute(4, 7, "T");
        soluteList.add(solute);

        solute = new Solute(4, 8, "T");
        soluteList.add(solute);

        solute = new Solute(4, 9, "T");
        soluteList.add(solute);

        solute = new Solute(4, 10, "T");
        soluteList.add(solute);

        solute = new Solute(4, 11, "T");
        soluteList.add(solute);

        solute = new Solute(4, 12, "T");
        soluteList.add(solute);

        solute = new Solute(4, 13, "T");
        soluteList.add(solute);

        solute = new Solute(4, 14, "—");
        soluteList.add(solute);

        solute = new Solute(4, 15, "T");
        soluteList.add(solute);

        solute = new Solute(4, 16, "T");
        soluteList.add(solute);

        solute = new Solute(4, 17, "T");
        soluteList.add(solute);

        solute = new Solute(4, 18, "T");
        soluteList.add(solute);

        solute = new Solute(4, 19, "T");
        soluteList.add(solute);

        solute = new Solute(4, 20, "T");
        soluteList.add(solute);

        //——

        solute = new Solute(5, 1, "T");
        soluteList.add(solute);

        solute = new Solute(5, 2, "T");
        soluteList.add(solute);

        solute = new Solute(5, 3, "T");
        soluteList.add(solute);

        solute = new Solute(5, 4, "T");
        soluteList.add(solute);

        solute = new Solute(5, 5, "T");
        soluteList.add(solute);

        solute = new Solute(5, 6, "T");
        soluteList.add(solute);

        solute = new Solute(5, 7, "T");
        soluteList.add(solute);

        solute = new Solute(5, 8, "T");
        soluteList.add(solute);

        solute = new Solute(5, 9, "T");
        soluteList.add(solute);

        solute = new Solute(5, 10, "T");
        soluteList.add(solute);

        solute = new Solute(5, 11, "T");
        soluteList.add(solute);

        solute = new Solute(5, 12, "T");
        soluteList.add(solute);

        solute = new Solute(5, 13, "T");
        soluteList.add(solute);

        solute = new Solute(5, 14, "—");
        soluteList.add(solute);

        solute = new Solute(5, 15, "T");
        soluteList.add(solute);

        solute = new Solute(5, 16, "—");
        soluteList.add(solute);

        solute = new Solute(5, 17, "—");
        soluteList.add(solute);

        solute = new Solute(5, 18, "T");
        soluteList.add(solute);

        solute = new Solute(5, 19, "—");
        soluteList.add(solute);

        solute = new Solute(5, 20, "T");
        soluteList.add(solute);

        ///—moi add
        solute = new Solute(6, 1, "T");
        soluteList.add(solute);

        solute = new Solute(6, 2, "T");
        soluteList.add(solute);

        solute = new Solute(6, 3, "T");
        soluteList.add(solute);

        solute = new Solute(6, 4, "T");
        soluteList.add(solute);

        solute = new Solute(6, 5, "K");
        soluteList.add(solute);

        solute = new Solute(6, 6, "K");
        soluteList.add(solute);

        solute = new Solute(6, 7, "—");
        soluteList.add(solute);

        solute = new Solute(6, 8, "T");
        soluteList.add(solute);

        solute = new Solute(6, 9, "T");
        soluteList.add(solute);

        solute = new Solute(6, 10, "T");
        soluteList.add(solute);

        solute = new Solute(6, 11, "K");
        soluteList.add(solute);

        solute = new Solute(6, 12, "K");
        soluteList.add(solute);

        solute = new Solute(6, 13, "—");
        soluteList.add(solute);

        solute = new Solute(6, 14, "K");
        soluteList.add(solute);

        solute = new Solute(6, 15, "K");
        soluteList.add(solute);

        solute = new Solute(6, 16, "K");
        soluteList.add(solute);

        solute = new Solute(6, 17, "—");
        soluteList.add(solute);

        solute = new Solute(6, 18, "K");
        soluteList.add(solute);

        solute = new Solute(6, 19, "K");
        soluteList.add(solute);

        solute = new Solute(6, 20, "K");
        soluteList.add(solute);

        //——

        solute = new Solute(7, 1, "T");
        soluteList.add(solute);

        solute = new Solute(7, 2, "T");
        soluteList.add(solute);

        solute = new Solute(7, 3, "T");
        soluteList.add(solute);

        solute = new Solute(7, 4, "T");
        soluteList.add(solute);

        solute = new Solute(7, 5, "K");
        soluteList.add(solute);

        solute = new Solute(7, 6, "K");
        soluteList.add(solute);

        solute = new Solute(7, 7, "K");
        soluteList.add(solute);

        solute = new Solute(7, 8, "K");
        soluteList.add(solute);

        solute = new Solute(7, 9, "K");
        soluteList.add(solute);

        solute = new Solute(7, 10, "K");
        soluteList.add(solute);

        solute = new Solute(7, 11, "K");
        soluteList.add(solute);

        solute = new Solute(7, 12, "K");
        soluteList.add(solute);

        solute = new Solute(7, 13, "—");
        soluteList.add(solute);

        solute = new Solute(7, 14, "—");
        soluteList.add(solute);

        solute = new Solute(7, 15, "K");
        soluteList.add(solute);

        solute = new Solute(7, 16, "K");
        soluteList.add(solute);

        solute = new Solute(7, 17, "—");
        soluteList.add(solute);

        solute = new Solute(7, 18, "K");
        soluteList.add(solute);

        solute = new Solute(7, 19, "—");
        soluteList.add(solute);

        solute = new Solute(7, 20, "K");
        soluteList.add(solute);

        //——

        solute = new Solute(8, 1, "T");
        soluteList.add(solute);

        solute = new Solute(8, 2, "T");
        soluteList.add(solute);

        solute = new Solute(8, 3, "T");
        soluteList.add(solute);

        solute = new Solute(8, 4, "T");
        soluteList.add(solute);

        solute = new Solute(8, 5, "T");
        soluteList.add(solute);

        solute = new Solute(8, 6, "I");
        soluteList.add(solute);

        solute = new Solute(8, 7, "T");
        soluteList.add(solute);

        solute = new Solute(8, 8, "K");
        soluteList.add(solute);

        solute = new Solute(8, 9, "K");
        soluteList.add(solute);

        solute = new Solute(8, 10, "K");
        soluteList.add(solute);

        solute = new Solute(8, 11, "T");
        soluteList.add(solute);

        solute = new Solute(8, 12, "—");
        soluteList.add(solute);

        solute = new Solute(8, 13, "T");
        soluteList.add(solute);

        solute = new Solute(8, 14, "T");
        soluteList.add(solute);

        solute = new Solute(8, 15, "K");
        soluteList.add(solute);

        solute = new Solute(8, 16, "—");
        soluteList.add(solute);

        solute = new Solute(8, 17, "T");
        soluteList.add(solute);

        solute = new Solute(8, 18, "T");
        soluteList.add(solute);

        solute = new Solute(8, 19, "T");
        soluteList.add(solute);

        solute = new Solute(8, 20, "T");
        soluteList.add(solute);

        //——

        solute = new Solute(9, 1, "T");
        soluteList.add(solute);

        solute = new Solute(9, 2, "T");
        soluteList.add(solute);

        solute = new Solute(9, 3, "T");
        soluteList.add(solute);

        solute = new Solute(9, 4, "T");
        soluteList.add(solute);

        solute = new Solute(9, 5, "—");
        soluteList.add(solute);

        solute = new Solute(9, 6, "K");
        soluteList.add(solute);

        solute = new Solute(9, 7, "K");
        soluteList.add(solute);

        solute = new Solute(9, 8, "K");
        soluteList.add(solute);

        solute = new Solute(9, 9, "K");
        soluteList.add(solute);

        solute = new Solute(9, 10, "K");
        soluteList.add(solute);

        solute = new Solute(9, 11, "K");
        soluteList.add(solute);

        solute = new Solute(9, 12, "—");
        soluteList.add(solute);

        solute = new Solute(9, 13, "—");
        soluteList.add(solute);

        solute = new Solute(9, 14, "—");
        soluteList.add(solute);

        solute = new Solute(9, 15, "K");
        soluteList.add(solute);

        solute = new Solute(9, 16, "K");
        soluteList.add(solute);

        solute = new Solute(9, 17, "—");
        soluteList.add(solute);

        solute = new Solute(9, 18, "K");
        soluteList.add(solute);

        solute = new Solute(9, 19, "—");
        soluteList.add(solute);

        solute = new Solute(9, 20, "K");
        soluteList.add(solute);

        //——

        solute = new Solute(10, 1, "T");
        soluteList.add(solute);

        solute = new Solute(10, 2, "T");
        soluteList.add(solute);

        solute = new Solute(10, 3, "T");
        soluteList.add(solute);

        solute = new Solute(10, 4, "—");
        soluteList.add(solute);

        solute = new Solute(10, 5, "—");
        soluteList.add(solute);

        solute = new Solute(10, 6, "—");
        soluteList.add(solute);

        solute = new Solute(10, 7, "K");
        soluteList.add(solute);

        solute = new Solute(10, 8, "K");
        soluteList.add(solute);

        solute = new Solute(10, 9, "K");
        soluteList.add(solute);

        solute = new Solute(10, 10, "K");
        soluteList.add(solute);

        solute = new Solute(10, 11, "K");
        soluteList.add(solute);

        solute = new Solute(10, 12, "—");
        soluteList.add(solute);

        solute = new Solute(10, 13, "K");
        soluteList.add(solute);

        solute = new Solute(10, 14, "—");
        soluteList.add(solute);

        solute = new Solute(10, 15, "K");
        soluteList.add(solute);

        solute = new Solute(10, 16, "—");
        soluteList.add(solute);

        solute = new Solute(10, 17, "—");
        soluteList.add(solute);

        solute = new Solute(10, 18, "K");
        soluteList.add(solute);

        solute = new Solute(10, 19, "K");
        soluteList.add(solute);

        solute = new Solute(10, 20, "K");
        soluteList.add(solute);

        //——

        solute = new Solute(11, 1, "T");
        soluteList.add(solute);

        solute = new Solute(11, 2, "T");
        soluteList.add(solute);

        solute = new Solute(11, 3, "T");
        soluteList.add(solute);

        solute = new Solute(11, 4, "T");
        soluteList.add(solute);

        solute = new Solute(11, 5, "K");
        soluteList.add(solute);

        solute = new Solute(11, 6, "K");
        soluteList.add(solute);

        solute = new Solute(11, 7, "T");
        soluteList.add(solute);

        solute = new Solute(11, 8, "I");
        soluteList.add(solute);

        solute = new Solute(11, 9, "I");
        soluteList.add(solute);

        solute = new Solute(11, 10, "K");
        soluteList.add(solute);

        solute = new Solute(11, 11, "K");
        soluteList.add(solute);

        solute = new Solute(11, 12, "K");
        soluteList.add(solute);

        solute = new Solute(11, 13, "—");
        soluteList.add(solute);

        solute = new Solute(11, 14, "—");
        soluteList.add(solute);

        solute = new Solute(11, 15, "K");
        soluteList.add(solute);

        solute = new Solute(11, 16, "K");
        soluteList.add(solute);

        solute = new Solute(11, 17, "T");
        soluteList.add(solute);

        solute = new Solute(11, 18, "K");
        soluteList.add(solute);

        solute = new Solute(11, 19, "—");
        soluteList.add(solute);

        solute = new Solute(11, 20, "—");
        soluteList.add(solute);

        //——

        solute = new Solute(12, 1, "K");
        soluteList.add(solute);

        solute = new Solute(12, 2, "T");
        soluteList.add(solute);

        solute = new Solute(12, 3, "T");
        soluteList.add(solute);

        solute = new Solute(12, 4, "T");
        soluteList.add(solute);

        solute = new Solute(12, 5, "K");
        soluteList.add(solute);

        solute = new Solute(12, 6, "K");
        soluteList.add(solute);

        solute = new Solute(12, 7, "K");
        soluteList.add(solute);

        solute = new Solute(12, 8, "K");
        soluteList.add(solute);

        solute = new Solute(12, 9, "K");
        soluteList.add(solute);

        solute = new Solute(12, 10, "K");
        soluteList.add(solute);

        solute = new Solute(12, 11, "K");
        soluteList.add(solute);

        solute = new Solute(12, 12, "K");
        soluteList.add(solute);

        solute = new Solute(12, 13, "K");
        soluteList.add(solute);

        solute = new Solute(12, 14, "K");
        soluteList.add(solute);

        solute = new Solute(12, 15, "K");
        soluteList.add(solute);

        solute = new Solute(12, 16, "K");
        soluteList.add(solute);

        solute = new Solute(12, 17, "K");
        soluteList.add(solute);

        solute = new Solute(12, 18, "K");
        soluteList.add(solute);

        solute = new Solute(12, 19, "K");
        soluteList.add(solute);

        solute = new Solute(12, 20, "K");
        soluteList.add(solute);

        //——

        solute = new Solute(13, 1, "T");
        soluteList.add(solute);

        solute = new Solute(13, 2, "T");
        soluteList.add(solute);

        solute = new Solute(13, 3, "T");
        soluteList.add(solute);

        solute = new Solute(13, 4, "T");
        soluteList.add(solute);

        solute = new Solute(13, 5, "K");
        soluteList.add(solute);

        solute = new Solute(13, 6, "—");
        soluteList.add(solute);

        solute = new Solute(13, 7, "K");
        soluteList.add(solute);

        solute = new Solute(13, 8, "I");
        soluteList.add(solute);

        solute = new Solute(13, 9, "I");
        soluteList.add(solute);

        solute = new Solute(13, 10, "T");
        soluteList.add(solute);

        solute = new Solute(13, 11, "K");
        soluteList.add(solute);

        solute = new Solute(13, 12, "—");
        soluteList.add(solute);

        solute = new Solute(13, 13, "K");
        soluteList.add(solute);

        solute = new Solute(13, 14, "K");
        soluteList.add(solute);

        solute = new Solute(13, 15, "K");
        soluteList.add(solute);

        solute = new Solute(13, 16, "K");
        soluteList.add(solute);

        solute = new Solute(13, 17, "K");
        soluteList.add(solute);

        solute = new Solute(13, 18, "K");
        soluteList.add(solute);

        solute = new Solute(13, 19, "K");
        soluteList.add(solute);

        solute = new Solute(13, 20, "K");
        soluteList.add(solute);

        //Check and add data
        if (soluteList.size() == mChemistryHelper.getAllSolute().size()) {
        } else {
            //Add to database

            Log.i("ANTN", "Table Solute updated");
            mChemistryHelper.emptySolute();
            for (Solute item : soluteList) {
                mChemistryHelper.addSolute(item);
            }
        }
    }

    private void setupToolbar() {
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbarMain);

    }

    private void init() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationRight = findViewById(R.id.nav_view_right);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();

        mChemistryHelper = ChemistrySingle.getInstance(this);

        if (mMnRight != null) {
            mMnRight.setVisible(false);
        }
    }

    private void setupNavigate() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationRight.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                if (mFragmentToSet != null) {
                    mBundle.putInt("ID_TYPE", mData);
                    mFragmentToSet.setArguments(mBundle);
                    mTransaction = mManager.beginTransaction();
                    mTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    mTransaction.replace(R.id.container, mFragmentToSet);
                    mTransaction.commit();
                    mFragmentToSet = null;
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        mNavigationView.setItemIconTintList(null);
        mNavigationRight.setItemIconTintList(null);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNavigationRight);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int idGroup = item.getGroupId();

        //default is not show nav right
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNavigationRight);

        //handle hide and show nav right by item
        if (idGroup == R.id.group_left_knowledge || idGroup == R.id.group_left_my_info || idGroup == R.id.group_left_app) {

            if (id == R.id.nav_periodic_table) {
                mMnRight.setVisible(true);
            } else {
                mMnRight.setVisible(false);
                mLastClick = -1;
            }

        } else if (idGroup == R.id.group_right_type || idGroup == R.id.group_right_state_matter) {
            mMnRight.setVisible(true);
        }

        //handle load fragment
        if (id == R.id.nav_periodic_table) {
            putParamToActivity(0);
            mNavigationRight.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_solubility_table) {
            mFragmentToSet = SolubilityTableFragment.newInstance();
        } else if (id == R.id.nav_reactivity_series) {
            mFragmentToSet = ReactivitySeriesFragment.newInstance();
        } else if (id == R.id.nav_alkali_metal) {
            putParamToActivity(1);
        } else if (id == R.id.nav_alkaline_earth_metal) {
            putParamToActivity(2);
        } else if (id == R.id.nav_post_transition_metal) {
            putParamToActivity(3);
        } else if (id == R.id.nav_metalloid) {
            putParamToActivity(4);
        } else if (id == R.id.nav_transition_metal) {
            putParamToActivity(5);
        } else if (id == R.id.nav_nonmetal) {
            putParamToActivity(6);
        } else if (id == R.id.nav_halogen) {
            putParamToActivity(7);
        } else if (id == R.id.nav_noble_gas) {
            putParamToActivity(8);
        } else if (id == R.id.nav_lanthanide) {
            putParamToActivity(9);
        } else if (id == R.id.nav_actinide) {
            putParamToActivity(10);
        } else if (id == R.id.nav_unknown_chemical_properties) {
            putParamToActivity(11);
        }else if (id == R.id.nav_solid) {
            putParamToActivity(12);
        }else if (id == R.id.nav_liquid) {
            putParamToActivity(13);
        }else if (id == R.id.nav_gas) {
            putParamToActivity(14);
        }else if (id == R.id.nav_unknown) {
            putParamToActivity(15);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMnRight = menu.findItem(R.id.mn_right);
        mMnRight.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_right:
                mDrawerLayout.openDrawer(mNavigationRight);
                break;
        }
        return true;
    }

    private void putParamToActivity(int data) {
        mData = data;
        if (mLastClick == mData) {
            mFragmentToSet = null;
        } else {
            mFragmentToSet = PeriodicTableFragment.newInstance();
            mLastClick = mData;
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mNavigationRight);
    }
}
