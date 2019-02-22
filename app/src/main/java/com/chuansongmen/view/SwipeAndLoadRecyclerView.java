package com.chuansongmen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeAndLoadRecyclerView extends RecyclerView {
    private RefreshListener listener;
    private BaseAdapter mBaseAdapter;
    private boolean isRefresh = false;
    private static final String TAG = "SwipeLoadRecyclerView";

    public SwipeAndLoadRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SwipeAndLoadRecyclerView(@NonNull Context context,
                                    @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeAndLoadRecyclerView(@NonNull Context context,
                                    @Nullable AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter instanceof BaseAdapter) {
            mBaseAdapter = (BaseAdapter) adapter;
        } else {
            Log.e(TAG, "setAdapter: " + "要使用BaseAdapter");
        }
        super.setAdapter(adapter);
    }

    public void switchOnRefresh(RefreshListener listener) {
        this.listener = listener;

        addHeaderProgressBar();

        View header = mBaseAdapter.getHeader();

        ProgressBar progressBar = header.findViewById(R.id.refreshing);

        addOnScrollListener(new OnScrollListener() {
            private int nowState = -1;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_SETTLING && header.getTop() <= 0) {
                    progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.refreshing,
                            null));
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.refreshing,
                            null));
                    listener.onRefreshing(SwipeAndLoadRecyclerView.this);
                    isRefresh = true;
                }
                nowState = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void addHeaderProgressBar() {
        mBaseAdapter.addHeader(getContext(), R.layout.refresh_header);
    }

    public void stopRefresh() {
        smoothScrollBy(0, mBaseAdapter.getHeaderHeight());
    }

    public interface RefreshListener {
        void onRefreshing(SwipeAndLoadRecyclerView swipeAndLoadRecyclerView);
    }
}
