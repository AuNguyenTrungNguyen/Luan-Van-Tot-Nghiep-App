package luanvan.luanvantotnghiep.Activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import luanvan.luanvantotnghiep.R;

public class ContentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_method);

        setupToolbar();

        TextView tvContentMethod = findViewById(R.id.tv_content_method);
        TextView tvMethod = findViewById(R.id.tv_method);
        String name = getIntent().getStringExtra("METHOD");
        String fileName = getIntent().getStringExtra("CONTENT_METHOD");

        try {
            InputStream fis = this.getAssets().open(fileName);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(fis));
            String data = "";
            StringBuilder builder = new StringBuilder();
            while ((data = reader.readLine()) != null) {
                builder.append(data);
                builder.append("\n");
            }
            fis.close();
            tvContentMethod.setText(Html.fromHtml(builder.toString()));
        } catch (FileNotFoundException e) {
            Log.i("hns", "FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            Log.i("hns", "IOException: " + e.getMessage());
        }

        tvMethod.setText(name);

    }

    private void setupToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
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
