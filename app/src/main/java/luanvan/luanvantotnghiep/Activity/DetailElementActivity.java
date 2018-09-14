package luanvan.luanvantotnghiep.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.View.ElectronView;

public class DetailElementActivity extends AppCompatActivity implements View.OnClickListener {

    private ChemistryHelper mHelper;

    private Toolbar mToolbar;

    private LinearLayout mLnElectron;
    private LinearLayout mLnProton;
    private LinearLayout mLnNeutron;

    private ImageView mImgBackground;
    private ImageView mImgWiki;

    private TextView mTvElectron;
    private TextView mTvProton;
    private TextView mTvNeutron;

    private TextView mTvElementCategory;
    private TextView mTvWeight;
    private TextView mTvEnglishName;
    private TextView mTvGroup;
    private TextView mTvPeriodic;
    private TextView mTvElectronegativity;
    //private TextView mTvWikiPaulingScale;
    private TextView mTvValence;

    private TextView mTvClass;
    private TextView mTvSimplified;
    private TextView mTvConfiguration;
    private ElectronView mElectronView;

    private TextView mTvStatus;
    private TextView mTvColor;
    private TextView mTvIsotope;
    //private ImageView mImgWikiTableIsotope;
    private TextView mTvMeltingPoint;
    private TextView mTvBoilingPoint;

    private TextView mTvDiscoverer;
    private TextView mTvYearDiscovery;

    private String mConfig;
    private String mShell;

    private Element element;

    private Dialog dialog;

    private TextView mTvNameInfo;
    private TextView mTvInfoElement;

    private String mStrCategory;

    private ImageButton mIBInfoElectron;
    private ImageButton mIBInfoProton;
    private ImageButton mIBInfoNeutron;
    private ImageButton mIBElementCategory;
    private ImageButton mIBWeight;
    private ImageButton mIBGroup;
    private ImageButton mIBPeriodic;
    private ImageButton mIBElectronegativity;
    private ImageButton mIBClass;
    private ImageButton mIBValence;
    private ImageButton mIBConfiguration;
    private ImageButton mIBIsotope;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_element);

        setupToolbar();

        init();

        showInfo();

        addEvents();

    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar_detail_element);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addEvents() {
        mImgWiki.setOnClickListener(this);
        //mTvWikiPaulingScale.setOnClickListener(this);
        //mImgWikiTableIsotope.setOnClickListener(this);
        mElectronView.setOnClickListener(this);

        mLnElectron.setOnClickListener(this);
        mLnProton.setOnClickListener(this);
        mLnNeutron.setOnClickListener(this);

        mTvElementCategory.setOnClickListener(this);
        mTvWeight.setOnClickListener(this);
        mTvGroup.setOnClickListener(this);
        mTvPeriodic.setOnClickListener(this);
        mTvElectronegativity.setOnClickListener(this);
        mTvValence.setOnClickListener(this);

        mTvClass.setOnClickListener(this);
        mTvConfiguration.setOnClickListener(this);
        mTvSimplified.setOnClickListener(this);

        mTvIsotope.setOnClickListener(this);

        mIBInfoElectron.setOnClickListener(this);
        mIBInfoProton.setOnClickListener(this);
        mIBInfoNeutron.setOnClickListener(this);
        mIBElementCategory.setOnClickListener(this);
        mIBWeight.setOnClickListener(this);
        mIBGroup.setOnClickListener(this);
        mIBPeriodic.setOnClickListener(this);
        mIBElectronegativity.setOnClickListener(this);
        mIBValence.setOnClickListener(this);
        mIBClass.setOnClickListener(this);
        mIBConfiguration.setOnClickListener(this);
        mIBIsotope.setOnClickListener(this);
    }

    private void init() {

        mHelper = ChemistrySingle.getInstance(this);

        mLnElectron = findViewById(R.id.ln_electron);
        mLnProton = findViewById(R.id.ln_proton);
        mLnNeutron = findViewById(R.id.ln_neutron);

        mImgBackground = findViewById(R.id.img_background);
        mImgWiki = findViewById(R.id.img_wiki);

        mTvElectron = findViewById(R.id.tv_electron);
        mTvProton = findViewById(R.id.tv_proton);
        mTvNeutron = findViewById(R.id.tv_neutron);

        mTvElementCategory = findViewById(R.id.tv_element_category);
        mTvWeight = findViewById(R.id.tv_weight_chemical);
        mTvEnglishName = findViewById(R.id.tv_english_name);
        mTvGroup = findViewById(R.id.tv_group);
        mTvPeriodic = findViewById(R.id.tv_period);
        mTvElectronegativity = findViewById(R.id.tv_electronegativity);
        //mTvWikiPaulingScale = findViewById(R.id.tv_wiki_pauling_scale);
        mTvValence = findViewById(R.id.tv_valence);

        mTvClass = findViewById(R.id.tv_class);
        mTvSimplified = findViewById(R.id.tv_simplified_configuration);
        mTvConfiguration = findViewById(R.id.tv_configuration);
        mElectronView = findViewById(R.id.electron_view);

        mTvStatus = findViewById(R.id.tv_status_chemical);
        mTvColor = findViewById(R.id.tv_color_chemical);
        mTvIsotope = findViewById(R.id.tv_isotope);
        //mImgWikiTableIsotope = findViewById(R.id.img_wiki_table_isotope);
        mTvMeltingPoint = findViewById(R.id.tv_melting_point);
        mTvBoilingPoint = findViewById(R.id.tv_boiling_point);

        mTvDiscoverer = findViewById(R.id.tv_discoverer);
        mTvYearDiscovery = findViewById(R.id.tv_year_discovery);

        mIBInfoElectron = findViewById(R.id.ib_info_electron);
        mIBInfoProton = findViewById(R.id.ib_info_proton);
        mIBInfoNeutron = findViewById(R.id.ib_info_neutron);
        mIBElementCategory = findViewById(R.id.ib_info_category);
        mIBWeight = findViewById(R.id.ib_info_weight);
        mIBGroup = findViewById(R.id.ib_info_group);
        mIBPeriodic = findViewById(R.id.ib_info_period);
        mIBElectronegativity = findViewById(R.id.ib_info_electronegativity);
        mIBClass = findViewById(R.id.ib_info_class);
        mIBValence = findViewById(R.id.ib_info_valence);
        mIBConfiguration = findViewById(R.id.ib_info_configuration);
        mIBIsotope = findViewById(R.id.ib_info_isotope);
    }

    private String getNameTypeById(int id) {
        List<Type> typeList = mHelper.getAllTypes();
        for (Type type : typeList) {
            if (type.getIdType() == id) {
                return type.getNameType();
            }
        }
        return "";
    }

    private void showInfo() {
        Intent intent = getIntent();

        if (intent != null) {

            element = (Element) intent.getSerializableExtra("ELEMENT_INTENT");
            Chemistry chemistry = (Chemistry) intent.getSerializableExtra("CHEMISTRY_INTENT");

            if (element != null && chemistry != null) {
                int resID = getResources().getIdentifier(element.getPicture(), "drawable", getPackageName());
                Glide.with(this)
                        .load(resID)
                        .into(mImgBackground);

                mTvElectron.setText(String.valueOf(element.getIdElement()));
                mTvProton.setText(String.valueOf(element.getIdElement()));
                mTvNeutron.setText(String.valueOf(element.getNeutron()));

                mStrCategory = String.valueOf(getNameTypeById(chemistry.getIdType()));
                mTvElementCategory.setText(Html.fromHtml("<font color='gray'>Phân loại: </font><font color='black'>" + mStrCategory + "</font>"));
                mTvWeight.setText(Html.fromHtml("<font color='gray'>Khối lượng: </font><font color='black'>" + String.valueOf(chemistry.getWeightChemistry()) + " (g/mol)</font>"));
                mTvEnglishName.setText(Html.fromHtml("<font color='gray'>Tên Tiếng Anh: </font><font color='black'>" + element.getEnglishName() + "</font>"));
                mTvPeriodic.setText(Html.fromHtml("<font color='gray'>Chu kỳ: </font><font color='black'>" + element.getPeriod() + "</font>"));
                mTvElectronegativity.setText(Html.fromHtml("<font color='gray'>Độ âm điện: </font><font color='black'>" + String.valueOf(element.getElectronegativity()) + "</font>"));
                mTvValence.setText(Html.fromHtml("<font color='gray'><b>Hóa trị: </b></font><font color='black'>" + element.getValence() + "</font>"));

                mTvClass.setText(Html.fromHtml("<font color='gray'>Phân lớp: </font><font color='black'>" + element.getClassElement() + "</font>"));
                mTvSimplified.setText(Html.fromHtml("<font color='black'>" + element.getSimplifiedConfiguration() + "</font>"));

                mTvStatus.setText(Html.fromHtml("<font color='gray'>Trạng thái: </font><font color='black'>" + chemistry.getStatusChemistry() + "</font>"));
                mTvColor.setText(Html.fromHtml("<font color='gray'>Màu sắc: </font><font color='black'>" + chemistry.getColorChemistry() + "</font>"));
                mTvIsotope.setText(Html.fromHtml("<font color='gray'>Đồng vị (bền): </font><font color='black'>" + element.getIsotopes() + "</font>"));

                mTvMeltingPoint.setText(Html.fromHtml("<font color='gray'>Nhiệt độ nóng chảy: </font><font color='black'>" + String.valueOf(element.getMeltingPoint() + "°C") + "</font>"));
                mTvBoilingPoint.setText(Html.fromHtml("<font color='gray'>Nhiệt độ sội: </font><font color='black'>" + String.valueOf(element.getBoilingPoint() + "°C") + "</font>"));

                String discoverer = element.getDiscoverer();
                mTvDiscoverer.setText(Html.fromHtml("<font color='gray'>Khám phá bởi:  </font><font color='black'>" + discoverer + "</font>"));
                if (discoverer.equals("")) {
                    mTvDiscoverer.setVisibility(View.GONE);
                }
                mTvYearDiscovery.setText(Html.fromHtml("<font color='gray'>Năm khám phá: </font><font color='black'>" + element.getYearDiscovery() + "</font>"));

                //Handel config HTML
                String config = element.getConfiguration();
                mTvConfiguration.setText(Html.fromHtml("<font color='gray'>Cấu hình electron: </font><font color='black'>" + handelConfigElectron(config) + "</font>"));
                mConfig = config;

                mShell = element.getShell();
                mElectronView.setShellToView(mShell);

                List<Group> list = mHelper.getAllGroups();

                String group = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIdGroup() == element.getIdGroup()) {
                        group = list.get(i).getNameGroup();
                        break;
                    }
                }
                mTvGroup.setText(Html.fromHtml("<font color='gray'>Nhóm:  </font><font color='black'>" + group + "</font>"));

                //mToolbar.setTitle(chemistry.getNameChemistry());
                getSupportActionBar().setTitle(chemistry.getNameChemistry());


            } else {
                Log.i("ANTN", "Null!");
            }
        }
    }

    private String handelConfigElectron(String config) {
        StringBuilder result = new StringBuilder();
        final String itemConfig[] = config.split(" ");
        for (String anItemConfig : itemConfig) {
            String number = anItemConfig.substring(0, 1);
            String shell = anItemConfig.substring(1, 2);
            String orbital = anItemConfig.substring(2);

            result.append(number).append(shell).append("<small><sup>")
                    .append(orbital).append("</sup></small>  ");
        }
        return result.toString();
    }

    @Override
    public void onClick(View view) {
        String nameEnglish;
        if (!element.getEnglishName().equals("Mercury")) {
            nameEnglish = element.getEnglishName();
        } else {
            nameEnglish = "Mercury_(element)";
        }

        String uri;
        Intent intent;
        switch (view.getId()) {
            case R.id.img_wiki:
                uri = "https://en.wikipedia.org/wiki/" + nameEnglish;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;

//            case R.id.tv_wiki_pauling_scale:
//                uri = "https://en.wikipedia.org/wiki/Electronegativities_of_the_elements_(data_page)";
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//                break;
//
//            case R.id.img_wiki_table_isotope:
//                uri = "https://en.wikipedia.org/wiki/Table_of_nuclides_(combined)";
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//                break;

            case R.id.electron_view:
                intent = new Intent(this, ConfigElectronActivity.class);
                intent.putExtra("CONFIG", mConfig);
                intent.putExtra("SHELL", mShell);
                startActivity(intent);
                break;

            case R.id.ln_electron:
                showDialog(Constraint.FILE_ELECTRON, "Electron");
                break;

            case R.id.ib_info_electron:
                showDialog(Constraint.FILE_ELECTRON, "Electron");
                break;

            case R.id.ln_proton:
                showDialog(Constraint.FILE_PROTON, "Proton");
                break;

            case R.id.ib_info_proton:
                showDialog(Constraint.FILE_PROTON, "Proton");
                break;

            case R.id.ln_neutron:
                showDialog(Constraint.FILE_NEUTRON, "Nơtron");
                break;

            case R.id.ib_info_neutron:
                showDialog(Constraint.FILE_NEUTRON, "Nơtron");
                break;

            case R.id.tv_element_category:
                handleCategory(mStrCategory);
                break;

            case R.id.ib_info_category:
                handleCategory(mStrCategory);
                break;

            case R.id.tv_weight_chemical:
                showDialog(Constraint.FILE_WEIGHT, "Nguyên tử khối");
                break;

            case R.id.ib_info_weight:
                showDialog(Constraint.FILE_WEIGHT, "Nguyên tử khối");
                break;

            case R.id.tv_group:
                showDialog(Constraint.FILE_GROUP, "Nhóm");
                break;

            case R.id.ib_info_group:
                showDialog(Constraint.FILE_GROUP, "Nhóm");
                break;

            case R.id.tv_period:
                showDialog(Constraint.FILE_PERIODIC, "Chu kỳ");
                break;

            case R.id.ib_info_period:
                showDialog(Constraint.FILE_PERIODIC, "Chu kỳ");
                break;

            case R.id.tv_electronegativity:
                showDialog(Constraint.FILE_ELECTRONEGATIVITY, "Độ âm điện");
                break;

            case R.id.ib_info_electronegativity:
                showDialog(Constraint.FILE_ELECTRONEGATIVITY, "Độ âm điện");
                break;

            case R.id.tv_valence:
                showDialog(Constraint.FILE_VALENCE, "Hóa trị");
                break;

            case R.id.ib_info_valence:
                showDialog(Constraint.FILE_VALENCE, "Hóa trị");
                break;

            case R.id.tv_class:
                showDialog(Constraint.FILE_CLASS, "Phân lớp");
                break;

            case R.id.ib_info_class:
                showDialog(Constraint.FILE_CLASS, "Phân lớp");
                break;

            case R.id.tv_simplified_configuration:
                showDialog(Constraint.FILE_CONFIGURATION, "Cấu hình electron");
                break;

            case R.id.ib_info_configuration:
                showDialog(Constraint.FILE_CONFIGURATION, "Cấu hình electron");
                break;

            case R.id.tv_configuration:
                showDialog(Constraint.FILE_CONFIGURATION, "Cấu hình electron");
                break;

            case R.id.tv_isotope:
                showDialog(Constraint.FILE_ISOTOPE, "Đồng vị");
                break;

            case R.id.ib_info_isotope:
                showDialog(Constraint.FILE_ISOTOPE, "Đồng vị");
                break;
        }

    }

    private void showDialog(String value, String name) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_dialog_info_element);
        mTvInfoElement = dialog.findViewById(R.id.tv_info_element);
        mTvNameInfo = dialog.findViewById(R.id.tv_name_info);

        try {
            InputStream fis = this.getAssets().open(value);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(fis));
            String data = "";
            StringBuilder builder = new StringBuilder();
            while ((data = reader.readLine()) != null) {
                builder.append(data);
                builder.append("\n");
            }
            fis.close();
            mTvInfoElement.setText(Html.fromHtml(builder.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mTvNameInfo.setText(name);

        mTvInfoElement.setClickable(true);
        mTvInfoElement.setMovementMethod(LinkMovementMethod.getInstance());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void handleCategory(String category) {
        switch (category) {
            case "Kim loại kiềm":
                showDialog(Constraint.FILE_ALKALI_METAL, category);
                break;
            case "Kim loại kiềm thổ":
                showDialog(Constraint.FILE_ALKALINE_EARTH_METAL, category);
                break;
            case "Kim loại yếu":
                showDialog(Constraint.FILE_POST_TRANSITION_METAL, category);
                break;
            case "Á kim":
                showDialog(Constraint.FILE_METALLOID, category);
                break;
            case "Kim loại chuyển tiếp":
                showDialog(Constraint.FILE_TRANSITION_METAL, category);
                break;
            case "Phi kim":
                showDialog(Constraint.FILE_NONMETAL, category);
                break;
            case "Halogen":
                showDialog(Constraint.FILE_HALOGEN, category);
                break;
            case "Khí trơ":
                showDialog(Constraint.FILE_NOBLE_GAS, category);
                break;
            case "Họ Lantan":
                showDialog(Constraint.FILE_LANTHANIDE, category);
                break;
            case "Họ Actini":
                showDialog(Constraint.FILE_ACTINIDE, category);
                break;
        }
    }

}
