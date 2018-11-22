package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.Model.Heading;
import luanvan.luanvantotnghiep.Model.Title;
import luanvan.luanvantotnghiep.R;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Heading> mListHeadeing;
    private HashMap<Heading, List<Title>> mMapTitle;

    public ExpandAdapter(Context mContext, List<Heading> mListHeadeing, HashMap<Heading, List<Title>> mMapTitle) {
        this.mContext = mContext;
        this.mListHeadeing = mListHeadeing;
        this.mMapTitle = mMapTitle;
    }

    @Override
    public int getGroupCount() {
        return mListHeadeing.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mMapTitle.get(mListHeadeing.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mListHeadeing.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mMapTitle.get(mListHeadeing.get(i)).get(i1);
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
        final Heading heading = (Heading) getGroup(i);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_header, viewGroup, false);
        }

        TextView tvHeader = view.findViewById(R.id.tv_expand_header);
        tvHeader.setText(Html.fromHtml(heading.getNameHeading()+ " &#x261A"));

        return view;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Title title = (Title) getChild(i, i1);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.expand_item, viewGroup, false);
        }

        TextView tvItem = view.findViewById(R.id.tv_expand_item);
        tvItem.setText(title.getNameTitle());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
