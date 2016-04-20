package org.devfleet.mob.app.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class ListRecyclerViewAdapter<T> extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {
    private static final Logger LOG = LoggerFactory.getLogger(ListRecyclerViewAdapter.class);
    public static class ViewHolder<T> extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(final T t) {}
    }

    private final List<T> items;

    public ListRecyclerViewAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(final List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        LOG.error("setItems: {}", items.size());
        notifyDataSetChanged();
    }

    @Override
    public abstract ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.render(this.items.get(position));
    }

    @Override
    public final int getItemCount() {
        return this.items.size();
    }
}
