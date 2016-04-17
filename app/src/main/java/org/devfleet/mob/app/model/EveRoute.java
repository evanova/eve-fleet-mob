package org.devfleet.mob.app.model;

import java.util.ArrayList;
import java.util.List;

public class EveRoute {

    private final List<EveLocation> locations = new ArrayList<>();

    public List<EveLocation> getLocations() {
        return locations;
    }

    public EveRoute addLocation(final EveLocation location) {
        this.locations.add(location);
        return this;
    }
}
