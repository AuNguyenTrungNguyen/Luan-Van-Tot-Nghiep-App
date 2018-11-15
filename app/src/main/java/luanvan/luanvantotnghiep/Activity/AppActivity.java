package luanvan.luanvantotnghiep.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import luanvan.luanvantotnghiep.R;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Glide.with(this).load(R.drawable.bg_chemistry_2).into((ImageView) findViewById(R.id.img_app));
        new CountDownTimer(3000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(AppActivity.this, SignInActivity.class));
                finish();
            }
        }.start();
    }
}
