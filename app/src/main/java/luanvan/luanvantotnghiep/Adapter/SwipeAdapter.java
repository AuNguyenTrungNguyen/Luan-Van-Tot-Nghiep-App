package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import luanvan.luanvantotnghiep.Activity.DetailElementActivity;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class SwipeAdapter extends PagerAdapter {

    private List<Element> mListData;
    private LayoutInflater mInflater;
    private Context mContext;

    public SwipeAdapter(Context context, List<Element> listData) {
        this.mListData = listData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = mInflater.inflate(R.layout.item_dialog_periodic, container, false);
        TextView tvSymbol = view.findViewById(R.id.tv_symbol_detail);
        TextView tvName = view.findViewById(R.id.tv_name_detail);
        TextView tvWeight = view.findViewById(R.id.tv_weight_detail);
        final TextView tvElectronegativity = view.findViewById(R.id.tv_electronegativity);
        final TextView tvSimplifiedConfiguration = view.findViewById(R.id.tv_simplified_configuration);
        final TextView tvValence = view.findViewById(R.id.tv_valence);

        ImageView imgPicture = view.findViewById(R.id.img_picture);
        Button btnGoInfo = view.findViewById(R.id.btn_go_info);

        final Element element = mListData.get(position);
        final Chemistry chemistry = ChemistrySingle.getInstance(mContext).getChemistryById(element.getIdElement());

        String symbol = "<small><sup>" + element.getIdElement() + "</small></sup>" + chemistry.getSymbolChemistry();

        tvSymbol.setText(Html.fromHtml(symbol));
        tvName.setText(chemistry.getNameChemistry());
        tvWeight.setText(String.valueOf(chemistry.getWeightChemistry()) + " (g/mol)");
        tvElectronegativity.setText("Độ âm điện: " + String.valueOf(element.getElectronegativity()));
        tvSimplifiedConfiguration.setText(Html.fromHtml("Cấu hình electron: " + element.getSimplifiedConfiguration()));
        tvValence.setText(Html.fromHtml("Hóa trị: " + element.getValence()));

        //Dung Glide load picture
        int resID = mContext.getResources().getIdentifier(element.getPicture() , "drawable", mContext.getPackageName());
        Glide.with(mContext)
                .load(resID)
                .into(imgPicture);

        btnGoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DetailElementActivity.class );
                intent.putExtra("ELEMENT_INTENT", element);
                intent.putExtra("CHEMISTRY_INTENT", chemistry);
                mContext.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
