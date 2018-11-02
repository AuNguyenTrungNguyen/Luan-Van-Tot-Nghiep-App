package luanvan.luanvantotnghiep.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import luanvan.luanvantotnghiep.R;

public class AboutUsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private LinearLayout mLnMail;
    private LinearLayout mLnFacebook;

    public AboutUsFragment() {
    }

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        mLnMail = view.findViewById(R.id.ln_mail);
        mLnFacebook = view.findViewById(R.id.ln_facebook);

        mLnMail.setOnClickListener(this);
        mLnFacebook.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_mail:
                email();
                break;

            case R.id.ln_facebook:
                facebook();
                break;
        }
    }

    private void email() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, "luanvan.cnhh@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi góp ý");
        PackageInfo info = null;

        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        assert info != null;
        intent.putExtra(Intent.EXTRA_TEXT, "Góp ý về cho nhà phát hành " + info.versionName.toString() + " ");
        intent.setData(Uri.parse("mailto: luanvan.cnhh@gmail.com"));
        startActivity(Intent.createChooser(intent, "Phản hồi với"));
    }

    public void facebook() {
        String uri = "https://www.facebook.com/Job-Management-2126265037390832";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

}
