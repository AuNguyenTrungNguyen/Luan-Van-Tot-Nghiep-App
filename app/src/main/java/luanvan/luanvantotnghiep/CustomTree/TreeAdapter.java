package luanvan.luanvantotnghiep.CustomTree;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import luanvan.luanvantotnghiep.Activity.FillInTheBlankActivity;
import luanvan.luanvantotnghiep.Activity.MatchActivity;
import luanvan.luanvantotnghiep.Activity.QuizActivity;
import luanvan.luanvantotnghiep.Activity.SortActivity;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class TreeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Tree> mList;

    public TreeAdapter(Context mContext, List<Tree> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        Tree tree = mList.get(position);
        if (tree.isLeft()) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_left, viewGroup, false);
            return new TreeHolderLeft(view);
        } else if (type == 1) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_right, viewGroup, false);
            return new TreeHolderRight(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final Tree tree = mList.get(position);
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                TreeHolderLeft holderLeft = ((TreeHolderLeft) viewHolder);
                holderLeft.textView.setText(tree.getText());
                holderLeft.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendDataGame(tree.getLevel());
                    }
                });
                break;
            case 1:
                TreeHolderRight holderRight = ((TreeHolderRight) viewHolder);
                holderRight.textView.setText(tree.getText());
                holderRight.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendDataGame(tree.getLevel());
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class TreeHolderLeft extends RecyclerView.ViewHolder {
        TextView textView;

        TreeHolderLeft(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }

    static class TreeHolderRight extends RecyclerView.ViewHolder {
        TextView textView;

        TreeHolderRight(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }

    private void sendDataGame(int level) {
        Intent intent;
        PreferencesManager.getInstance().init(mContext);
        int type = PreferencesManager.getInstance().getIntData(Constraint.PRE_KEY_TYPE, 0);
        switch (type) {
            case 1:
                intent = new Intent(mContext,QuizActivity.class);
                intent.putExtra("LEVEL",level);
                mContext.startActivity(intent);
                break;
            case 2:
                intent = new Intent(mContext,FillInTheBlankActivity.class);
                intent.putExtra("LEVEL",level);
                mContext.startActivity(intent);
                break;
            case 3:
                intent = new Intent(mContext,MatchActivity.class);
                intent.putExtra("LEVEL",level);
                mContext.startActivity(intent);
                break;
            case 4:
                intent = new Intent(mContext,SortActivity.class);
                intent.putExtra("LEVEL",level);
                mContext.startActivity(intent);
                break;
        }
    }
}
