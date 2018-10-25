package luanvan.luanvantotnghiep.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Model.User;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class SignInActivity extends AppCompatActivity {

    private PreferencesManager mPreferencesManager;

    private EditText mEdtPhone;
    private EditText mEdtPassword;
    private Button mBtnSignIn;
    private Button mBtnSignUp;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        checkUser();

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }

    private void signIn() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        final String phone = mEdtPhone.getText().toString();
        String password = mEdtPassword.getText().toString();

        if (checkSignIn(phone, password)) {
            final String phoneEncode = encodeSHA512(phone);
            final String passwordEncode = encodeSHA512(password);
            DatabaseReference myUser = FirebaseDatabase.getInstance().getReference().child("USER");
            userList.clear();
            myUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        userList.add(user);
                    }

                    for (User user : userList) {
                        if (user.getPhone().equals(phoneEncode) && user.getPassword().equals(passwordEncode)) {
                            mPreferencesManager.saveStringData(Constraint.PRE_KEY_PHONE, phoneEncode);
                            startActivity(new Intent(SignInActivity.this, CheckVersionDatabaseActivity.class));
                            finish();
                        } else if (!user.getPhone().equals(phoneEncode)) {
                            setFocusViewError(mEdtPhone, "Sai số điện thoại!");
                        } else if (!user.getPassword().equals(passwordEncode)) {
                            setFocusViewError(mEdtPassword, "Sai mật khẩu!");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private boolean checkSignIn(String phone, String password) {
        if (!phone.equals("")) {
            if (!phone.substring(0, 1).equals("0")) {
                setFocusViewError(mEdtPhone, "Bắt đầu bằng số 0!");
                return false;
            } else if (phone.length() != 10) {
                setFocusViewError(mEdtPhone, "Số điện thoại phải 10 số!");
                return false;
            } else if (password.equals("")) {
                setFocusViewError(mEdtPassword, "Mật khẩu không được rỗng!");
                return false;
            } else if (password.length() < 6) {
                setFocusViewError(mEdtPassword, "Mật khẩu chứa ít nhất 6 ký tự!");
                return false;
            }
        } else {
            setFocusViewError(mEdtPhone, "Số điện thoại rỗng!");
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    private String encodeSHA512(String text) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(Constraint.SLAT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            for (byte aDigest : bytes) {
                //sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
                sb.append(String.format("%02x", aDigest));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void checkUser() {
        String phone = mPreferencesManager.getStringData(Constraint.PRE_KEY_PHONE, "");
        if (!phone.equals("")) {
            startActivity(new Intent(this, CheckVersionDatabaseActivity.class));
            finish();
        }
    }

    private void init() {
        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(this);

        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtPassword = findViewById(R.id.edt_password);
        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mBtnSignUp = findViewById(R.id.btn_sign_up);
    }

    private void setFocusViewError(EditText editText, String message) {
        editText.setError(message);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }
}
