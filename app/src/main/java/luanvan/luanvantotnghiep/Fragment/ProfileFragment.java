package luanvan.luanvantotnghiep.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import luanvan.luanvantotnghiep.Activity.CheckVersionDatabaseActivity;
import luanvan.luanvantotnghiep.Activity.SignInActivity;
import luanvan.luanvantotnghiep.Adapter.RankAdapter;
import luanvan.luanvantotnghiep.Model.Rank;
import luanvan.luanvantotnghiep.Model.User;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.Helper;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private PreferencesManager mPreferencesManager;

    private TextView mTvScoreEasy;
    private TextView mTvScoreNormal;
    private TextView mTvScoreDifficult;
    private TextView mTvName;
    private TextView mTvPhone;

    private ImageView mImgEditPassword;

    private TextView mTvBlock;
    private ImageView mImgEditBlock;

    private int mBlock;
    private int mBlockTemp;

    private Helper mHelper;

    private List<User> userList = new ArrayList<>();

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);

        loadData();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        String name = mPreferencesManager.getStringData(Constraint.PRE_KEY_NAME, "");
        final String phone = mPreferencesManager.getStringData(Constraint.PRE_KEY_PHONE, "");
        mBlock = mPreferencesManager.getIntData(Constraint.PRE_KEY_BLOCK, 8);
        mBlockTemp = mBlock;

        mTvName.setText(name);
        mTvPhone.setText(phone);
        mTvBlock.setText("Lớp " + mBlock);

        DatabaseReference myRank = FirebaseDatabase.getInstance().getReference().child("RANK");
        final String keyEasy = mHelper.encodeSHA512Extent(phone, Constraint.EXTENT_EASY);
        final String keyNormal = mHelper.encodeSHA512Extent(phone, Constraint.EXTENT_NORMAL);
        final String keyDifficult = mHelper.encodeSHA512Extent(phone, Constraint.EXTENT_DIFFICULT);

        final float[] scoreEasy = {0};
        final float[] scoreNormal = {0};
        final float[] scoreDifficult = {0};

        myRank.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (keyEasy.equals(postSnapshot.getKey())) {
                        Rank rank = postSnapshot.getValue(Rank.class);
                        assert rank != null;
                        scoreEasy[0] = rank.getScore();
                    } else if (keyNormal.equals(postSnapshot.getKey())) {
                        Rank rank = postSnapshot.getValue(Rank.class);
                        assert rank != null;
                        scoreNormal[0] = rank.getScore();
                    } else if (keyDifficult.equals(postSnapshot.getKey())) {
                        Rank rank = postSnapshot.getValue(Rank.class);
                        assert rank != null;
                        scoreDifficult[0] = rank.getScore();
                    }
                }
                mTvScoreEasy.setText("" + scoreEasy[0]);
                mTvScoreNormal.setText("" + scoreNormal[0]);
                mTvScoreDifficult.setText("" + scoreDifficult[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    private void init(View view) {
        mHelper = Helper.getInstant();
        mPreferencesManager = PreferencesManager.getInstance();
        mPreferencesManager.init(mContext);

        mTvScoreEasy = view.findViewById(R.id.tv_score_easy);
        mTvScoreNormal = view.findViewById(R.id.tv_score_normal);
        mTvScoreDifficult = view.findViewById(R.id.tv_score_difficult);

        mTvName = view.findViewById(R.id.tv_name);
        mTvPhone = view.findViewById(R.id.tv_phone);

        mImgEditPassword = view.findViewById(R.id.img_edit_password);
        mImgEditPassword.setOnClickListener(this);

        mTvBlock = view.findViewById(R.id.tv_block);
        mImgEditBlock = view.findViewById(R.id.img_edit_block);
        mImgEditBlock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_edit_password:
                changePassword();
                break;

            case R.id.img_edit_block:
                changeBlock();
                break;
        }
    }

    private void changeBlock() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.layout_dialog_change_block);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final RadioButton rdb8 = dialog.findViewById(R.id.rdb_8);
        final RadioButton rdb9 = dialog.findViewById(R.id.rdb_9);
        final RadioButton rdb10 = dialog.findViewById(R.id.rdb_10);
        final RadioButton rdb11 = dialog.findViewById(R.id.rdb_11);
        final RadioButton rdb12 = dialog.findViewById(R.id.rdb_12);
        final Button btnComplete = dialog.findViewById(R.id.btn_complete);

        switch (mBlock) {
            case 8:
                rdb8.setEnabled(false);
                break;

            case 9:
                rdb9.setEnabled(false);
                break;

            case 10:
                rdb10.setEnabled(false);
                break;

            case 11:
                rdb11.setEnabled(false);
                break;

            case 12:
                rdb12.setEnabled(false);
                break;
        }

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdb8.isChecked()) {
                    mBlock = 8;
                } else if (rdb9.isChecked()) {
                    mBlock = 9;
                } else if (rdb10.isChecked()) {
                    mBlock = 10;
                } else if (rdb11.isChecked()) {
                    mBlock = 11;
                } else if (rdb12.isChecked()) {
                    mBlock = 12;

                }
                if (mBlockTemp != mBlock) {
                    mPreferencesManager.saveIntData(Constraint.PRE_KEY_BLOCK, mBlock);

                    DatabaseReference myUser = FirebaseDatabase.getInstance().getReference().child("USER");
                    String phoneEncode = mPreferencesManager.getStringData(Constraint.PRE_KEY_PHONE_ENCODE, "");
                    myUser.child(phoneEncode).child("block").setValue(mBlock);

                    mPreferencesManager.saveIntData(Constraint.KEY_GAME, 0);
                    mPreferencesManager.saveIntData(Constraint.KEY_THEMATIC, 0);

                    //change data
                    startActivity(new Intent(mContext, CheckVersionDatabaseActivity.class));
                    getActivity().finish();
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    private void changePassword() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.layout_dialog_change_password);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText edtNewPassword = dialog.findViewById(R.id.edt_new_password);
        final EditText edtConfirmPassword = dialog.findViewById(R.id.edt_confirm_password);

        final DatabaseReference myUser = FirebaseDatabase.getInstance().getReference().child("USER");
        final ValueEventListener[] myUserListener = new ValueEventListener[1];
        final String phoneEncode = mPreferencesManager.getStringData(Constraint.PRE_KEY_PHONE_ENCODE, "");
        Button btnChangePassword = dialog.findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList.clear();
                myUserListener[0] = myUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            userList.add(user);
                        }
                        for (User user : userList) {
                            if (user.getPhone().equals(phoneEncode) && checkPushData(
                                    edtNewPassword.getText().toString(),
                                    edtConfirmPassword.getText().toString(),
                                    edtNewPassword,
                                    edtConfirmPassword)) {

                                myUser.child(phoneEncode)
                                        .child("password")
                                        .setValue(mHelper.encodeSHA512(edtNewPassword.getText().toString()));
                                Toast.makeText(mContext, "Mật khẩu mới đã được cập nhật", Toast.LENGTH_SHORT).show();
                                myUser.removeEventListener(myUserListener[0]);
                                dialog.dismiss();
                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private boolean checkPushData(String newPassword,
                                  String confirmPassword,
                                  EditText edtNewPassword,
                                  EditText edtConfirmPassword) {
        if (newPassword.equals("")) {
            setFocusViewError(edtNewPassword, "Mật khẩu mới không được rỗng!");
            return false;
        } else if (newPassword.length() < 6) {
            setFocusViewError(edtNewPassword, "Mật khẩu mới chứa ít nhất 6 ký tự!");
            return false;
        } else if (confirmPassword.equals("")) {
            setFocusViewError(edtConfirmPassword, "Mật khẩu xác nhận không được rỗng!");
            return false;
        } else if (!newPassword.equals(confirmPassword)) {
            setFocusViewError(edtConfirmPassword, "Mật khẩu xác nhận không giống!");
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
