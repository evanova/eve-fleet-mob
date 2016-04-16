package org.devfleet.mob.app.ui.fittings;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.devfleet.mob.app.model.EveFitting;

import java.util.List;

public class FittingWidget extends RecyclerView {

    public interface Listener {
        void onFittingSelected(final EveFitting fitting);
    }

    private FittingAdapter adapter;
    private Listener listener;

    public FittingWidget(Context context) {
        super(context);
        init();
    }

    public FittingWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FittingWidget(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.adapter = new FittingAdapter();
        this.setLayoutManager(new LinearLayoutManager(getContext()));
        this.setAdapter(this.adapter);
    }

    public void setFittings(final List<EveFitting> fittings) {
        this.adapter.setItems(fittings);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
