package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Activity.ShowTheoryActivity;
import luanvan.luanvantotnghiep.R;

public class ExpandAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private List<String> mListHeader;
    private HashMap<String, List<String>> mListItem;

    public ExpandAdapter(Context mContext, List<String> mListHeader, HashMap<String, List<String>> mListItem) {
        this.mContext = mContext;
        this.mListHeader = mListHeader;
        this.mListItem = mListItem;
    }

    @Override
    public int getGroupCount() {
        return mListHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mListItem.get(mListHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mListHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mListItem.get(mListHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        final String header = (String) getGroup(i);
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_header, viewGroup, false);
        }

        TextView tvHeader = view.findViewById(R.id.tv_expand_header);
        tvHeader.setText(header);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String item = (String) getChild(i, i1);
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_item, viewGroup, false);
        }

        LinearLayout layout = view.findViewById(R.id.expand_item);
        TextView tvItem = view.findViewById(R.id.tv_expand_item);
        tvItem.setText(item);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ShowTheoryActivity.class));
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
