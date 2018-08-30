package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.View.CustomView;

public class ConfigElectronActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private static final String[] LIST_COLORS = new String[]{
            "#b8860b",
            "#800000",
            "#000080",
            "#008b8b",
            "#008000",
            "#dc143c",
            "#BC8F8F"
    };

    private TextView mTvConfig;
    private TextView mTvShell;
    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_electron);

        setupToolbar();

        init();

        showInfo();
    }

    private void showInfo() {
        Intent intent = getIntent();

        if (intent != null) {
            String configData = intent.getStringExtra("CONFIG");
            String shellData = intent.getStringExtra("SHELL");

            Log.i("ANTN", configData);
            Log.i("ANTN", shellData);

            handelConfig(configData);

            handelShell(shellData);

            mCustomView.setShellToView(shellData);

        }
    }

    private void handelConfig(String config){

        StringBuilder result = new StringBuilder();

        final String itemConfig[] = config.split(" ");
        for (String anItemConfig : itemConfig) {
            String number = anItemConfig.substring(0, 1);
            String shell = anItemConfig.substring(1, 2);
            String orbital = anItemConfig.substring(2);
            int color = -1;
            switch (number) {
                case "1":
                    color = 0;
                    break;
                case "2":
                    color = 1;
                    break;
                case "3":
                    color = 2;
                    break;
                case "4":
                    color = 3;
                    break;
                case "5":
                    color = 4;
                    break;
                case "6":
                    color = 5;
                    break;
                case "7":
                    color = 6;
                    break;
            }

//            Log.i("ANTN", "number: " + number);
//            Log.i("ANTN", "shell: " + shell);
//            Log.i("ANTN", "orbital: " + orbital);
//            Log.i("ANTN", "color: " + color);

            result.append("<font color='").append(LIST_COLORS[color]).append("'>")
                    .append(number).append(shell).append("<small><sup>")
                    .append(orbital).append("</sup></small>").append("</font>  ");
        }
        mTvConfig.setText(Html.fromHtml(result.toString()));
    }

    private void handelShell(String shell){
        StringBuilder result = new StringBuilder();
        final String itemConfig[] = shell.split(" ");
        for (String anItemConfig : itemConfig) {
            String border = anItemConfig.substring(0, 1);
            String number = anItemConfig.substring(1);
            int color = -1;
            switch (border) {
                case "K":
                    color = 0;
                    break;
                case "L":
                    color = 1;
                    break;
                case "M":
                    color = 2;
                    break;
                case "N":
                    color = 3;
                    break;
                case "O":
                    color = 4;
                    break;
                case "P":
                    color = 5;
                    break;
                case "Q":
                    color = 6;
                    break;
            }

            result.append("<font color='").append(LIST_COLORS[color]).append("'>")
                    .append(border).append(number).append("</font>  ");
        }
        mTvShell.setText(Html.fromHtml(result.toString()));
    }

    private void init() {
        mTvConfig = findViewById(R.id.tv_configuration_atom);
        mTvShell = findViewById(R.id.tv_shell_atom);
        mCustomView = findViewById(R.id.draw);
    }

    private void setupToolbar() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
