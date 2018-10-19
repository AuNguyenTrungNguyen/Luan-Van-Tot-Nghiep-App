package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import luanvan.luanvantotnghiep.Activity.ShowTheoryActivity;
import luanvan.luanvantotnghiep.Model.Chapter;
import luanvan.luanvantotnghiep.R;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterHolder> {

    private Context mContext;
    private List<Chapter> mChapterList;

    public ChapterAdapter(Context mContext, List<Chapter> mChapterList) {
        this.mContext = mContext;
        this.mChapterList = mChapterList;
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter, viewGroup, false);
        return new ChapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder chapterHolder, final int i) {
        final Chapter chapter = mChapterList.get(i);
        chapterHolder.tvChapter.setText(chapter.getNameChapter());
        chapterHolder.tvChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowTheoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHAPTER", chapter);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChapterList.size();
    }

    static class ChapterHolder extends RecyclerView.ViewHolder {

        TextView tvChapter;

        ChapterHolder(@NonNull View itemView) {
            super(itemView);
            tvChapter = itemView.findViewById(R.id.tv_chapter);
        }
    }
}
