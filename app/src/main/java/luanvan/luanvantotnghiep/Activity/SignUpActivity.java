package luanvan.luanvantotnghiep.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
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
import java.util.concurrent.TimeUnit;

import luanvan.luanvantotnghiep.Model.User;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Constraint.TAG + "Login";

    private FirebaseAuth mAuth;
    private DatabaseReference myUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;

    private TextView mTvStatus;
    private EditText mEdtPhone;
    private Button mBtnSendPhone;

    private LinearLayout mViewVerify;
    private EditText mEdtVerify;
    private Button mBtnVerify;
    private Button mBtnReSend;

    private LinearLayout mViewInfo;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private EditText mEdtName;
    private RadioButton mRdb8;
    private RadioButton mRdb9;
    private RadioButton mRdb10;
    private RadioButton mRdb11;
    private RadioButton mRdb12;

    private Button mBtnSignUp;

    private String phoneKey = "";
    private List<User> userList = new ArrayList<>();
    private PreferencesManager mPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        mBtnSendPhone.setOnClickListener(this);
        mBtnVerify.setOnClickListener(this);
        mBtnReSend.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);

    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();
        myUser = FirebaseDatabase.getInstance().getReference().child("USER");
        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(this);

        mTvStatus = findViewById(R.id.tv_status_sign_in);

        mEdtPhone = findViewById(R.id.edt_phone);
        mBtnSendPhone = findViewById(R.id.btn_send_phone);

        mViewVerify = findViewById(R.id.view_verify);
        mEdtVerify = findViewById(R.id.edt_verify);
        mBtnVerify = findViewById(R.id.btn_verify);
        mBtnReSend = findViewById(R.id.btn_re_send);

        mViewInfo = findViewById(R.id.view_info);
        mEdtPassword = findViewById(R.id.edt_password);
        mEdtConfirmPassword = findViewById(R.id.edt_confirm_password);
        mEdtName = findViewById(R.id.edt_name);

        mRdb8 = findViewById(R.id.rdb_8);
        mRdb9 = findViewById(R.id.rdb_9);
        mRdb10 = findViewById(R.id.rdb_10);
        mRdb11 = findViewById(R.id.rdb_11);
        mRdb12 = findViewById(R.id.rdb_12);

        mBtnSignUp = findViewById(R.id.btn_sign_up);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i(TAG, "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.i(TAG, "onCodeSent: ");
                mVerificationId = verificationId;
            }
        };

        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBtnSendPhone.setEnabled(true);
                mBtnVerify.setEnabled(true);
                mViewVerify.setVisibility(View.INVISIBLE);
                mViewInfo.setVisibility(View.INVISIBLE);
                mBtnReSend.setEnabled(false);

                mTvStatus.setText("");
                mEdtVerify.setText("");
                mEdtPassword.setText("");
                mEdtConfirmPassword.setText("");
                mEdtName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEdtVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBtnVerify.setEnabled(true);
                mViewInfo.setVisibility(View.INVISIBLE);

                mTvStatus.setText("");
                mEdtPassword.setText("");
                mEdtConfirmPassword.setText("");
                mEdtName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_phone:
                getListUser();
                break;

            case R.id.btn_verify:
                if(mEdtVerify.getText().toString().equals("")){
                    setFocusViewError(mEdtVerify,"Mã xác nhận rỗng");
                }else if(!(mEdtVerify.getText().toString().length() == 6 )){
                    setFocusViewError(mEdtVerify,"Mã xác nhận phải có 6 số");
                }else {
                    verifyPhoneNumberWithCode(mVerificationId, mEdtVerify.getText().toString());
                    mBtnVerify.setEnabled(false);
                }
                break;

            case R.id.btn_re_send:
                resendCode();
                break;

            case R.id.btn_sign_up:
                signUp();
                break;
        }
    }

    private void resendCode(){
        sendPhone(phoneKey);
        mBtnReSend.setEnabled(false);
        mTvStatus.setText("Hãy đợi mã xác nhận và nhập!");
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long l) {
                mBtnReSend.setText("" + (l/1000) + "(s)");
            }

            @Override
            public void onFinish() {
                mBtnReSend.setEnabled(true);
                mBtnReSend.setText("RESEND");
            }
        }.start();
    }

    private void getListUser() {

        userList.clear();
        myUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                }
                checkExist();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkExist() {
        phoneKey = mEdtPhone.getText().toString();
        if (checkFormatPhone(phoneKey)) {
            boolean isExist = false;
            for (User user : userList) {
                if (user.getPhone().equals(encodeSHA512(phoneKey))) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText("Số điện thoại đã được sử dụng!");
                mViewVerify.setVisibility(View.INVISIBLE);
                mViewInfo.setVisibility(View.INVISIBLE);
            } else {
                sendPhone(phoneKey);
                mBtnSendPhone.setEnabled(false);
                mBtnReSend.setEnabled(false);
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText("Hãy đợi mã xác nhận và nhập!");
                mViewVerify.setVisibility(View.VISIBLE);
                new CountDownTimer(120000, 1000){
                    @Override
                    public void onTick(long l) {
                        mBtnReSend.setText("" + (l/1000) + "(s)");
                    }

                    @Override
                    public void onFinish() {
                        mBtnReSend.setEnabled(true);
                        mBtnReSend.setText("RESEND");
                    }
                }.start();
            }
        }
    }

    private void signUp() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        String password = mEdtPassword.getText().toString();
        String confirmPassword = mEdtConfirmPassword.getText().toString();
        String name = mEdtName.getText().toString();

        int block = 0;
        if (mRdb8.isChecked()) {
            block = 8;
        } else if (mRdb9.isChecked()) {
            block = 9;
        } else if (mRdb10.isChecked()) {
            block = 10;
        } else if (mRdb11.isChecked()) {
            block = 11;
        } else if (mRdb12.isChecked()) {
            block = 12;
        }

        if (checkPushData(password, confirmPassword, name)) {
            final String phoneEncode = encodeSHA512(phoneKey);
            String passwordEncode = encodeSHA512(password);

            User user = new User();
            user.setPhone(phoneEncode);
            user.setPassword(passwordEncode);
            user.setName(name);
            user.setBlock(block);

            String key = myUser.push().getKey();
            final int finalBlock = block;
            myUser.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mTvStatus.setText("");
                    mPreferencesManager.saveIntData(Constraint.PRE_KEY_BLOCK, finalBlock);
                    mPreferencesManager.saveStringData(Constraint.PRE_KEY_PHONE, phoneEncode);
                    startActivity(new Intent(SignUpActivity.this, CheckVersionDatabaseActivity.class));
                }
            });
        }
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

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.i(TAG, "signInWithPhoneAuthCredential: ");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mViewInfo.setVisibility(View.VISIBLE);
                        } else {
                            Log.i(TAG, "onComplete", task.getException());
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "onFailure" + e.getMessage());
                mTvStatus.setText("Mã xác nhận không hợp lệ");
            }
        });
    }

    private void sendPhone(String phoneNumber) {
        String phone = handelPhone(phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private String handelPhone(String phone) {
        String result = phone;
        if (phone.substring(0, 1).equals("0")) {
            result = "+84" + phone.substring(1);
        }
        return result;
    }

    private boolean checkFormatPhone(String phone) {
        if (!phone.equals("")) {
            if (!phone.substring(0, 1).equals("0")) {
                mEdtPhone.setError("Bắt đầu bằng số 0!");
                return false;
            } else if (phone.length() != 10) {
                mEdtPhone.setError("Số điện thoại phải 10 số!");
                return false;
            }
        } else {
            mEdtPhone.setError("Số điện thoại rỗng!");
            return false;
        }
        return true;
    }

    private boolean checkPushData(String password, String confirmPassword, String name) {
        if (password.equals("")) {
            setFocusViewError(mEdtPassword, "Mật khẩu không được rỗng!");
            return false;
        } else if (password.length() < 6) {
            setFocusViewError(mEdtPassword, "Mật khẩu chứa ít nhất 6 ký tự!");
            return false;
        } else if (confirmPassword.equals("")) {
            setFocusViewError(mEdtConfirmPassword, "Mật khẩu xác nhận không được rỗng!");
            return false;
        } else if (!password.equals(confirmPassword)) {
            setFocusViewError(mEdtConfirmPassword, "Mật khẩu xác nhận không giống!");
            return false;
        } else if (name.equals("")) {
            setFocusViewError(mEdtName, "Tên hiển thị không được rỗng!");
            return false;
        }
        return true;
    }

    private void setFocusViewError(EditText editText, String message) {
        editText.setError(message);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

}
