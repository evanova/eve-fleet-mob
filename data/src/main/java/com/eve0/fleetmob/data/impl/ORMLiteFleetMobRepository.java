package com.eve0.fleetmob.data.impl;

import java.sql.SQLException;
import com.eve0.fleetmob.data.FleetMobRepository;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ORMLiteFleetMobRepository implements FleetMobRepository {

    public ORMLiteFleetMobRepository() throws SQLException {
        this("jdbc:sqlite:" + ORMLiteFleetMobRepository.class.getResource("/fm.db").getFile());
    }

    public ORMLiteFleetMobRepository(final String jdbcUrl) throws SQLException {
        this(new JdbcPooledConnectionSource(jdbcUrl));
    }

    public ORMLiteFleetMobRepository(final ConnectionSource source) throws SQLException {

    }

}
