package luanvan.luanvantotnghiep.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PageVisibleLinearLayoutManager extends LinearLayoutManager {
    public PageVisibleLinearLayoutManager(Context context) {
        super(context);
    }

    public PageVisibleLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public PageVisibleLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private boolean pageVisible = true;

    void setPageVisible(boolean pageVisible) {
        boolean change = (this.pageVisible != pageVisible);
        this.pageVisible = pageVisible;
        if(change) requestLayout();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(pageVisible) {
            super.onLayoutChildren(recycler, state);
        } else {
            removeAndRecycleAllViews(recycler);
        }
    }
}
