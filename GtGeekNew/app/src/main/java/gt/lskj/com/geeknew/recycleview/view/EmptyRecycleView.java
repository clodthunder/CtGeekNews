package gt.lskj.com.geeknew.recycleview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Home on 16/9/22.
 */

public class EmptyRecycleView extends RecyclerView {
    //emptyView
    private View emptyView;

    public EmptyRecycleView(Context context) {
        super(context);
    }

    public EmptyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
