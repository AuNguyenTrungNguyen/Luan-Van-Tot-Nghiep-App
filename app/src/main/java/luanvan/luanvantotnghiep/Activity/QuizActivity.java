package luanvan.luanvantotnghiep.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import luanvan.luanvantotnghiep.R;

public class QuizActivity extends AppCompatActivity {

    private Toolbar mToolbar;
//    private final List<CauHoiTracNghiem> listCHTN = new ArrayList<>();
//    private CauHoiTracNghiemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    private void themDuLieuCauHoiTN(MyDataHelper db) {
//
//        CauHoiTracNghiem cauHoiTracNghiem = new CauHoiTracNghiem(1, "1+1 = ?", "2", "1", "3", "4");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(2, "2+2= ?", "4", "2", "3", "5");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(3, "4 + 5 = ?", "9", "8", "6", "7");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//        cauHoiTracNghiem = new CauHoiTracNghiem(4, "4 + 4 = ?", "8", "9", "10", "7");
//        db.themCauHoiTN(cauHoiTracNghiem);
//
//    }

}

