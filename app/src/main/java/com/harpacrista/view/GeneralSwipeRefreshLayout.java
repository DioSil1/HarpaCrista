package com.harpacrista.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class GeneralSwipeRefreshLayout extends SwipeRefreshLayout {

    private ListView mListView;

    public GeneralSwipeRefreshLayout(Context context) {
        super(context);
    }

    public GeneralSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        return mListView.getFirstVisiblePosition() > 0 ||
                mListView.getChildAt(0) == null ||
                mListView.getChildAt(0).getTop() < 0;
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }
}