package org.devfleet.mob.app.ui.routes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.util.EveFormat;
import org.devfleet.mob.app.widgets.ListRecyclerViewAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

class RouteListAdapter extends ListRecyclerViewAdapter<EveLocation> {
    private static final Logger LOG = LoggerFactory.getLogger(RouteListAdapter.class);
    static class RouteHolder extends ListRecyclerViewAdapter.ViewHolder<EveLocation> {

        @Bind(R.id.rowJumpPlanRegionView)
        TextView regionView;

        @Bind(R.id.rowJumpPlanSolarSystemView)
        TextView solarSystemView;

        @Bind(R.id.rowJumpPlanSecurityView)
        TextView securityView;

        @Bind(R.id.rowJumpPlanKillsView)
        TextView killsView;

        @Bind(R.id.rowJumpPlanJumpsView)
        TextView jumpsView;

        @Bind(R.id.rowJumpPlanOwnerView)
        TextView ownerView;

        public RouteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(EveLocation location) {
            this.regionView.setText(location.getRegionName());
            this.regionView.setVisibility(View.VISIBLE);
            this.solarSystemView.setText(location.getSolarSystemName());
            if (StringUtils.isBlank(location.getHolderName())) {
                this.ownerView.setText("");
            } else {
                this.ownerView.setText(location.getHolderName());
            }

            this.securityView.setText("" + location.getSecurityStatus());

            if (location.getShipKills() == 0) {
                this.killsView.setText("");
            } else {
                this.killsView.setText(location.getShipKills() + " kills");
            }
            this.killsView.setVisibility(View.VISIBLE);

            if (location.getShipJumps() == 0) {
                this.jumpsView.setText("");
            } else {
                this.jumpsView.setText(location.getShipJumps() + " ship jumps");
            }
            this.jumpsView.setVisibility(View.VISIBLE);

            try {
                int color = EveFormat.getSecurityLevelColor(location.getSecurityStatus());
                this.solarSystemView.setTextColor(color);
                this.securityView.setTextColor(color);
            } catch (NumberFormatException e) {
                this.solarSystemView.setTextColor(Color.WHITE);
                this.securityView.setTextColor(Color.GRAY);
            }
        }
    }

    @Override
    public ViewHolder<EveLocation> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RouteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_route_list, parent, false));
    }

    public void setRoute(final EveRoute route) {
        LOG.error("setRoute: {}", ToStringBuilder.reflectionToString(route));
        setItems((null == route) ? Collections.emptyList() : route.getLocations());
    }
}
