package org.devfleet.mob.app.domain.eve;

import org.devfleet.dotlan.DotlanJump;
import org.devfleet.dotlan.DotlanOptions;
import org.devfleet.dotlan.DotlanRoute;
import org.devfleet.dotlan.DotlanSolarSystem;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;

final class DotlanMapper {

    public static DotlanOptions options(final EveRoute route) {
        if (null == route) {
            return null;
        }
        DotlanOptions options = new DotlanOptions();
        for (EveLocation l: route.getLocations()) {
            options.addWaypoint(l.getSolarSystemName());
        }
        return options;
    }

    public static EveRoute transform(final DotlanRoute route, final int type) {
        final EveRoute returned = new EveRoute();
        returned.setType(type);
        for (DotlanJump j: route.getRoute()) {
            returned.addLocation(map(j));
        }
        return returned;
    }

    public static EveLocation map(final DotlanJump jump) {
        final DotlanSolarSystem from = jump.getFrom();
        final EveLocation location = new EveLocation();
        location.setSolarSystemID(from.getSolarSystemID());
        location.setSolarSystemName(from.getSolarSystemName());
        location.setHolderID(from.getHolderID());
        location.setHolderName(from.getHolderName());
        location.setRegionID(from.getRegionID());
        location.setRegionName(from.getRegionName());
        location.setShipJumps(from.getShipJumps());
        location.setShipKills(from.getShipKills());
        location.setSecurityStatus(from.getSecurityStatus());
        return location;
    }
}
