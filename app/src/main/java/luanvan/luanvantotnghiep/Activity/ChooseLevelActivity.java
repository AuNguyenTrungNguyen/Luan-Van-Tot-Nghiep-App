package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import luanvan.luanvantotnghiep.R;

public class ChooseLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        final Intent intent = new Intent(ChooseLevelActivity.this, FillInTheBlankActivity.class);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("LEVEL", 1);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("LEVEL", 2);
                startActivity(intent);
            }
        });
    }
}
