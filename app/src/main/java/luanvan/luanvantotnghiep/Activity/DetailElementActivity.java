package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class DetailElementActivity extends AppCompatActivity implements View.OnClickListener {

    private ChemistryHelper mHelper;

    private Toolbar mToolbar;

    private ImageView mImgBackground;
    private ImageView mImgWiki;

    private TextView mTvElectron;
    private TextView mTvProton;
    private TextView mTvNeutron;

    private TextView mTvWeight;
    private TextView mTvEnglishName;
    private TextView mTvGroup;
    private TextView mTvPeriodic;
    private TextView mTvElectronegativity;
    private TextView mTvWikiPaulingScale;
    private TextView mTvValence;

    private TextView mTvClass;
    private TextView mTvSimplified;
    private TextView mTvConfiguration;
    private TextView mTvShell;
    private ImageView mImgConfig;

    private TextView mTvStatus;
    private TextView mTvColor;
    private TextView mTvIsotope;
    private ImageView mImgWikiTableIsotope;
    private TextView mTvMeltingPoint;
    private TextView mTvBoilingPoint;

    private TextView mTvDiscoverer;
    private TextView mTvYearDiscovery;

    private String mConfig;
    private String mShell;


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
        mTvWikiPaulingScale.setOnClickListener(this);
        mImgWikiTableIsotope.setOnClickListener(this);
        mImgConfig.setOnClickListener(this);
    }

    private void init() {

        mHelper = ChemistrySingle.getInstance(this);

        mImgBackground = findViewById(R.id.img_background);
        mImgWiki = findViewById(R.id.img_wiki);

        mTvElectron = findViewById(R.id.tv_electron);
        mTvProton = findViewById(R.id.tv_proton);
        mTvNeutron = findViewById(R.id.tv_neutron);

        mTvWeight = findViewById(R.id.tv_weight_chemical);
        mTvEnglishName = findViewById(R.id.tv_english_name);
        mTvGroup = findViewById(R.id.tv_group);
        mTvPeriodic = findViewById(R.id.tv_period);
        mTvElectronegativity = findViewById(R.id.tv_electronegativity);
        mTvWikiPaulingScale = findViewById(R.id.tv_wiki_pauling_scale);
        mTvValence = findViewById(R.id.tv_valence);

        mTvClass = findViewById(R.id.tv_class);
        mTvSimplified = findViewById(R.id.tv_simplified_configuration);
        mTvConfiguration = findViewById(R.id.tv_configuration);
        mTvShell = findViewById(R.id.tv_shell);
        mImgConfig = findViewById(R.id.img_config);

        mTvStatus = findViewById(R.id.tv_status_chemical);
        mTvColor = findViewById(R.id.tv_color_chemical);
        mTvIsotope = findViewById(R.id.tv_isotope);
        mImgWikiTableIsotope = findViewById(R.id.img_wiki_table_isotope);
        mTvMeltingPoint = findViewById(R.id.tv_melting_point);
        mTvBoilingPoint = findViewById(R.id.tv_boiling_point);

        mTvDiscoverer = findViewById(R.id.tv_discoverer);
        mTvYearDiscovery = findViewById(R.id.tv_year_discovery);

    }

    private void showInfo() {
        Intent intent = getIntent();

        if (intent != null) {

            Element element = (Element) intent.getSerializableExtra("ELEMENT_INTENT");
            Chemistry chemistry = (Chemistry) intent.getSerializableExtra("CHEMISTRY_INTENT");

            if (element != null && chemistry != null) {
                int resID = getResources().getIdentifier(element.getPicture(), "drawable", getPackageName());
                Glide.with(this)
                        .load(resID)
                        .into(mImgBackground);

                mTvElectron.setText(String.valueOf(element.getIdElement()));
                mTvProton.setText(String.valueOf(element.getIdElement()));
                mTvNeutron.setText(String.valueOf(element.getNeutron()));

                mTvWeight.setText("Khối lượng: " + String.valueOf(chemistry.getWeightChemistry())+ " (g/mol)");
                mTvEnglishName.setText("Tên Tiếng Anh: " + element.getEnglishName());
                mTvPeriodic.setText("Chu kỳ: " + String.valueOf(element.getPeriod()));
                mTvElectronegativity.setText("Độ âm điện: " + String.valueOf(element.getElectronegativity()));
                mTvValence.setText(Html.fromHtml("<b>Hóa trị: </b><center>" + element.getValence() + "</center>"));

                mTvClass.setText("Phân lớp: " + element.getClassElement());
                mTvSimplified.setText(Html.fromHtml("Electron rút rọn: <center>" + element.getSimplifiedConfiguration() + "</center>"));

                mTvStatus.setText("Trạng thái: " + chemistry.getStatusChemistry());
                mTvColor.setText("Màu sắc: " + chemistry.getColorChemistry());
                mTvIsotope.setText(Html.fromHtml("Đồng vị (bền): <center>" + element.getIsotopes() + "</center>"));

                mTvMeltingPoint.setText("Nhiệt độ nóng chảy: " + String.valueOf(element.getMeltingPoint() + "°C"));
                mTvBoilingPoint.setText("Nhiệt độ sội: " + String.valueOf(element.getBoilingPoint() + "°C"));

                mTvDiscoverer.setText(Html.fromHtml("Khám phá bởi: <center>" + element.getDiscoverer() + "</center>"));
                mTvYearDiscovery.setText("Năm khám phá: " + element.getYearDiscovery());

                //Handel config HTML
                String config = element.getConfiguration();
                mTvConfiguration.setText(Html.fromHtml("Cấu hình electron: " + handelConfigElectron(config)));
                mConfig = config;
                mTvShell.setText(element.getShell());
                mShell = element.getShell();

                List<Group> list = mHelper.getAllGroups();

                String group = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIdGroup() == element.getIdGroup()) {
                        group = list.get(i).getNameGroup();
                        break;
                    }
                }
                mTvGroup.setText("Nhóm: " + group);

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

            result.append("<center>").append(number).append(shell).append("<small><sup>")
                    .append(orbital).append("</sup></small></center>  ");
        }
        return result.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_wiki:
                Toast.makeText(this, "img_wiki onClick", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_wiki_pauling_scale:
                Toast.makeText(this, "tv_wiki_pauling_scale onClick", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_wiki_table_isotope:
                Toast.makeText(this, "img_wiki_table_isotope onClick", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_config:
                Intent intent = new Intent(this, ConfigElectronActivity.class);
                intent.putExtra("CONFIG", mConfig);
                intent.putExtra("SHELL", mShell);

                startActivity(intent);
                break;
        }
    }

}
