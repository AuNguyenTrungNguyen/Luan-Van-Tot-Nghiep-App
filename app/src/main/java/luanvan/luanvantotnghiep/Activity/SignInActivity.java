package luanvan.luanvantotnghiep.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import luanvan.luanvantotnghiep.CheckInternet.AsyncTaskListener;
import luanvan.luanvantotnghiep.CheckInternet.InternetCheck;
import luanvan.luanvantotnghiep.Model.User;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private PreferencesManager mPreferencesManager;

    private LinearLayout mLnSignIn;
    private EditText mEdtPhone;
    private EditText mEdtPassword;
    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private TextView mTvForgotPassword;

    private ProgressDialog mProgressDialog;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUser();
    }

    private void signIn() {
        mProgressDialog = new ProgressDialog(SignInActivity.this);
        mProgressDialog.setTitle("Xin hãy chờ một tý...");
        mProgressDialog.setMessage("Đang kiểm tra đăng nhập...");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        final String phone = mEdtPhone.getText().toString();
        String password = mEdtPassword.getText().toString();

        if (checkSignIn(phone, password)) {
            final String phoneEncode = encodeSHA512(phone);
            final String passwordEncode = encodeSHA512(password);
            DatabaseReference myUser = FirebaseDatabase.getInstance().getReference().child("USER");
            userList.clear();
            mProgressDialog.show();
            myUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        userList.add(user);
                    }
                    boolean isPhoneExist = false;
                    boolean isPasswordCorrect = false;
                    boolean isDisableAccount = false;
                    for (User user : userList) {
                        if (user.getPhone().equals(phoneEncode)) {
                            isPhoneExist = true;
                            if (user.getDisable() == 1) {
                                isDisableAccount = true;
                                break;
                            } else if (user.getPassword().equals(passwordEncode)) {
                                isPasswordCorrect = true;
                                mPreferencesManager.saveStringData(Constraint.PRE_KEY_PHONE, phoneEncode);
                                mProgressDialog.dismiss();
                                startActivity(new Intent(SignInActivity.this, CheckVersionDatabaseActivity.class));
                                finish();
                                break;
                            }
                        }
                    }
                    if (!isPhoneExist) {
                        mProgressDialog.dismiss();
                        setFocusViewError(mEdtPhone, "Sai số điện thoại!");
                    } else if (isDisableAccount) {
                        mProgressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
                    } else if (!isPasswordCorrect) {
                        mProgressDialog.dismiss();
                        setFocusViewError(mEdtPassword, "Sai mật khẩu!");
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

        mLnSignIn = findViewById(R.id.ln_sign_in);
//        Glide.with(this).load(R.drawable.bg_sign_in).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    mLnSignIn.setBackground(resource);
//                }
//            }
//        });

        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtPassword = findViewById(R.id.edt_password);
        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(this);
        mBtnSignUp = findViewById(R.id.btn_sign_up);
        mBtnSignUp.setOnClickListener(this);

        mTvForgotPassword = findViewById(R.id.tv_forgot_password);
        SpannableString content = new SpannableString(mTvForgotPassword.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mTvForgotPassword.setText(content);

        mTvForgotPassword.setOnClickListener(this);
    }

    private void setFocusViewError(EditText editText, String message) {
        editText.setError(message);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                InternetCheck internetCheck = new InternetCheck();
                internetCheck.setListener(new AsyncTaskListener() {
                    @Override
                    public void passResultInternet(Boolean internet) {
                        if (internet){
                            signIn();
                        }else{
                            Toast.makeText(SignInActivity.this, "Vui lòng kiểm tra mạng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                internetCheck.execute();
                break;

            case R.id.btn_sign_up:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;

            case R.id.tv_forgot_password:
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }
}
