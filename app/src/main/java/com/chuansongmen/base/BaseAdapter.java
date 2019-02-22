package com.chuansongmen.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> data;
    private int itemResId;
    private View mHeader, mFooter;
    private RecyclerView.LayoutManager layoutManager;


    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_FOOTER = 2000;


    public BaseAdapter(@LayoutRes int itemResId) {
        this.itemResId = itemResId;
    }

    public BaseAdapter(List<T> data, @LayoutRes int itemResId) {
        this.data = data;
        this.itemResId = itemResId;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    protected void onBind(BaseViewHolder holder, int viewType, int position, T item) {
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeader);
        } else if (viewType == TYPE_FOOTER) {
            return new BaseViewHolder(mFooter);
        }
        return new BaseViewHolder(View.inflate(parent.getContext(), itemResId, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_FOOTER && getItemViewType(position) != TYPE_HEADER) {
            onBind(holder,
                    getItemViewType(position),
                    hasHeader() ? --position : position,
                    data.get(hasHeader() ? --position : position));
        }
    }

    @Override
    public int getItemCount() {
        int size = data == null ? 0 : data.size();
        if (hasFooter()) {
            size++;
        }
        if (hasHeader()) {
            size++;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        layoutManager = recyclerView.getLayoutManager();
    }

    public View getHeader() {
        return mHeader;
    }

    public void addHeader(Context context, @LayoutRes int headerId) {
        mHeader = View.inflate(context, headerId, null);
        setHeaderFooterParams(mHeader);
        notifyItemInserted(0);
    }

    public int getHeaderHeight() {
        return mHeader.getHeight();
    }

    public void addFooter(Context context, @LayoutRes int footerId) {
        mFooter = View.inflate(context, footerId, null);
        setHeaderFooterParams(mFooter);
        notifyItemInserted(getItemCount() - 1);
    }

    private void setHeaderFooterParams(View view) {
        if (view == mHeader || view == mFooter) {
            if (layoutManager.canScrollVertically()) {
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    public boolean hasHeader() {
        return mHeader != null;
    }

    public boolean hasFooter() {
        return mFooter != null;
    }

    private boolean isHeader(int position) {
        return hasHeader() && position == 0;
    }

    private boolean isFooter(int position) {
        return hasFooter() && position == getItemCount() - 1;
    }
}
