package org.devfleet.mob.app.ui.routes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.util.EveFormat;
import org.devfleet.mob.app.widgets.ListRecyclerViewAdapter;

import java.util.Collections;

class RouteGridAdapter extends ListRecyclerViewAdapter<EveLocation> {

    static class RouteHolder extends ListRecyclerViewAdapter.ViewHolder<EveLocation> {
        public RouteHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(EveLocation location) {
            this.itemView.setBackgroundColor(EveFormat.getSecurityLevelColor(location.getSecurityStatus()));
        }
    }

    @Override
    public ViewHolder<EveLocation> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RouteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_route_grid, parent, false));
    }

    public void setRoute(final EveRoute route) {
        setItems((null == route) ? Collections.emptyList() : route.getLocations());
    }
}
