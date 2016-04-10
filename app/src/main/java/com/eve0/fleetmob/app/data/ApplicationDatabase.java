package com.eve0.fleetmob.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

final class ApplicationDatabase extends OrmLiteSqliteOpenHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDatabase.class);
    private static final String DATABASE_NAME = "fm.db";
    private static final int DATABASE_VERSION = 1;

    private static ApplicationDatabase database;

    private Dao<CharacterEntity, Long> characterDAO;

    private ApplicationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            this.characterDAO = getDao(CharacterEntity.class);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ApplicationDatabase from(final Context context) {
        if (null == database) {
            database = new ApplicationDatabase(context);
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CharacterEntity.class);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onDelete(database, connectionSource);
        onCreate(database, connectionSource);
    }

    public void onDelete(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.dropTable(connectionSource, CharacterEntity.class, true);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<CharacterEntity> listCharacters() {
        try {
            return this.characterDAO.queryForAll();
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Collections.emptyList();
        }
    }

    public void addCharacter(CharacterEntity entity) {
        try {
            this.characterDAO.createOrUpdate(entity);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public void removeCharacter(long characterID) {
        try {
            this.characterDAO.deleteById(characterID);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }
}
