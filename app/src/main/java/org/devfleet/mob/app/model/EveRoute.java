package org.devfleet.mob.app.model;

import java.util.ArrayList;
import java.util.List;

public class EveRoute {

    public static final int FASTEST = 0;
    public static final int HIGHSEC = 1;
    public static final int LOWSEC = 2;

    private final List<EveLocation> locations = new ArrayList<>();
    private int type;

    public List<EveLocation> getLocations() {
        return locations;
    }

    public EveRoute addLocation(final EveLocation location) {
        this.locations.add(location);
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
