package org.devfleet.mob.app.ui.routes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveRoute;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RouteWidget extends FrameLayout {

    @Bind(R.id.fRouteGrid)
    RecyclerView gridView;

    @Bind(R.id.fRouteList)
    RecyclerView listView;

    public RouteWidget(Context context) {
        super(context);
        init();
    }

    public RouteWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RouteWidget(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        final View view = inflate(getContext(), R.layout.w_routes, this);
        ButterKnife.bind(this, view);

        this.gridView.setAdapter(new RouteGridAdapter());
        this.gridView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        this.listView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.listView.setAdapter(new RouteListAdapter());

    }

    public void setRoute(final EveRoute route) {
        ((RouteGridAdapter)this.gridView.getAdapter()).setRoute(route);
        ((RouteListAdapter)this.listView.getAdapter()).setRoute(route);
    }
}
