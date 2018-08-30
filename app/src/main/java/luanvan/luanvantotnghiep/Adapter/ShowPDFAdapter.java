package luanvan.luanvantotnghiep.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import luanvan.luanvantotnghiep.R;

public class ShowPDFAdapter extends ArrayAdapter{
    private Context mContext;
    private List<Bitmap> mList;

    public ShowPDFAdapter(@NonNull Context context, @NonNull List<Bitmap> objects) {
        super(context, R.layout.item_pdf, objects);
        mContext = context;
        mList = objects;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pdf, parent, false);
        ImageView imageView = view.findViewById(R.id.img_show_pdf);
        imageView.setImageBitmap(mList.get(position));

        return view;
    }
}
